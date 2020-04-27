package commands.admin;

import javolution.util.FastList;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.database.mysql;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.QuestManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.base.Experience;
import ru.l2gw.gameserver.model.base.L2Augmentation;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2PetInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.*;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.gameserver.skills.funcs.FuncSet;
import ru.l2gw.gameserver.tables.CharTemplateTable;
import ru.l2gw.gameserver.tables.SkillTable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class AdminEditChar extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {
			new AdminCommandDescription("admin_edit_character", null),
			new AdminCommandDescription("admin_character_actions", null),
			new AdminCommandDescription("admin_current_player", null),
			new AdminCommandDescription("admin_nokarma", null),
			new AdminCommandDescription("admin_setkarma", null),
			new AdminCommandDescription("admin_character_list", "usage: //character_list <name>"),
			new AdminCommandDescription("admin_show_characters", "usage: //show_characters <page>"),
			new AdminCommandDescription("admin_find_character", "usage: //find_character <name>"),
			new AdminCommandDescription("admin_save_modifications", null),
			new AdminCommandDescription("admin_rec", "usage: //rec [num rec]"),
			new AdminCommandDescription("admin_settitle", "usage: //settitle <title>"),
			new AdminCommandDescription("admin_setname", "usage: //setname <name>"),
			new AdminCommandDescription("admin_setsex", null),
			new AdminCommandDescription("admin_setcolor", "usage: //setcolor <0x112233>"),
			new AdminCommandDescription("admin_add_exp_sp_to_character", null),
			new AdminCommandDescription("admin_add_exp_sp", "usage: //add_exp_sp <exp> <sp>"),
			new AdminCommandDescription("admin_sethero", "usage: //sethero [name]"),
			new AdminCommandDescription("admin_setnoble", "usage: //setnoble [name]"),
			new AdminCommandDescription("admin_trans", "usage: //trans <transformId>"),
			new AdminCommandDescription("admin_see_effects", null),
			new AdminCommandDescription("admin_set_refl", "usage: //set_refl [name] <reflectionId>"),
			new AdminCommandDescription("admin_setaug", "usage: //setaug <id1> <id2>"),
			new AdminCommandDescription("admin_pet_add_exp", "usage: //pet_add_exp <exp> pet must be in target"),
			new AdminCommandDescription("admin_setclass", "usage: //setclass <classId>"),
			new AdminCommandDescription("admin_iz_reset", "usage: //iz_reset [iz_type]"),
			new AdminCommandDescription("admin_setparam", "usage: //setparam <param> <value>"),
			new AdminCommandDescription("admin_setquest", "usage: //setquest <quest_id> <quest_state>"),
			new AdminCommandDescription("admin_setmemostate", "usage: //setmemostate <quest_id> <state>"),
			new AdminCommandDescription("admin_setmemocond", "usage: //setmemocond <quest_id> <cond>"),
			new AdminCommandDescription("admin_setmemoex", "usage: //setmemoex <quest_id> <ex_cond> <ex_val>"),
			new AdminCommandDescription("admin_setteam", "usage: //setteam <team> [name]"),
			new AdminCommandDescription("admin_inzone", null)
	};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.startsWith("admin_settitle"))
		{
			try
			{
				String val = args[0];
				L2Player player = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;
				if(player != null)
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, player, val, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					player.setTitle(val);
					player.sendMessage("Your title has been changed by a GM");
					player.sendChanges();

					logGM.info(activeChar.toFullString() + " " + "change title for player " + player.getName() + " to " + val);
					return true;
				}

				Functions.sendSysMessage(activeChar, "Select a player target");
				return false;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.startsWith("admin_setname"))
		{
			try
			{
				String val = args[0];
				L2Player player = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;
				if(player != null)
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, player, val, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					if(mysql.simple_get_int("count(*)", "characters", "`char_name` like '" + val + "'") > 0)
					{
						Functions.sendSysMessage(activeChar, "Name already exist.");
						return false;
					}

					logGM.info(activeChar.toFullString() + " " + "set name for player " + player.getName() + " to " + val);
					player.setName(val);
					player.sendMessage("Your name has been changed by a GM");
					player.broadcastUserInfo(true);
					return true;
				}

				Functions.sendSysMessage(activeChar, "Select a player target");
				return false;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_see_effects"))
		{
			L2Object target = activeChar.getTarget();

			if(!AdminTemplateManager.checkCommand(command, activeChar, target instanceof L2Character ? (L2Character) target : null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			showEffects(activeChar, target);
			return true;
		}
		else if(command.equals("admin_current_player"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			showCharacterList(activeChar, null);
		}
		else if(command.equals("admin_character_list"))
		{
			try
			{
				String val = args[0];
				L2Player target = L2ObjectsStorage.getPlayer(val);

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				showCharacterList(activeChar, target);
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_show_characters"))
		{
			try
			{
				int page = Integer.parseInt(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				listCharacters(activeChar, page);
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_find_character"))
		{
			try
			{
				String val = args[0];

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				findCharacter(activeChar, val);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				listCharacters(activeChar, 0);
				return false;
			}
		}
		else if(command.equals("admin_edit_character"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			editCharacter(activeChar);
			return true;
		}
		else if(command.equals("admin_character_actions"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			showCharacterActions(activeChar);
			return true;
		}
		else if(command.equals("admin_nokarma"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			setTargetKarma(activeChar, 0);
			return true;
		}
		else if(command.equals("admin_setkarma"))
		{
			try
			{
				String val = args[0];
				int karma = Integer.parseInt(val);

				if(!AdminTemplateManager.checkCommand(command, activeChar, activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null, karma, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				setTargetKarma(activeChar, karma);
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_save_modifications"))
			try
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				String val = args[0];
				adminModifyCharacter(activeChar, val);

				logGM.info(activeChar.toFullString() + " " + "save modifications for player " + val);
				return true;
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Error while modifying character.");
				listCharacters(activeChar, 0);
				return false;
			}
		else if(command.equals("admin_rec"))
		{
			L2Player player = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;
			if(player == null)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
			
			int rec = 1;
			try
			{
				if(args.length > 0)
					rec = Integer.parseInt(args[0]);
			}
			catch (Exception e)
			{
				// quite
			}
			
			if(!AdminTemplateManager.checkCommand(command, activeChar, player, rec, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			player.getRecSystem().setRecommendsHave(player.getRecSystem().getRecommendsHave() + rec);
			player.sendMessage("You have been recommended by a GM");
			player.broadcastUserInfo(true);

			logGM.info(activeChar.toFullString() + " recommend player " + player.getName() + " + " + rec);
			return true;
		}
		else if(command.equals("admin_sethero"))
		{
			L2Player player;
			
			if(args.length > 0)
			{
				player = L2ObjectsStorage.getPlayer(args[0]);
				if(player == null)
				{
					Functions.sendSysMessage(activeChar, "Player: " + args[0] + " not found.");
					return false;
				}
			}
			else
				player = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;
			
			if(player == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target or type a name.");
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, player, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			if(player.isHero())
			{
				player.setHero(false);
				player.removeSkill(SkillTable.getInstance().getInfo(395, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(396, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1374, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1375, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1376, 1));
			}
			else
			{
				player.setHero(true);
				player.addSkill(SkillTable.getInstance().getInfo(395, 1));
				player.addSkill(SkillTable.getInstance().getInfo(396, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1374, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1375, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1376, 1));
			}

			player.sendPacket(new SkillList(player));

			if(player.isHero())
			{
				player.broadcastPacket(new SocialAction(player.getObjectId(), 16));
				Announcements.getInstance().announceToAll(player.getName() + " has become a hero.");
			}

			player.sendMessage("Admin changed your hero status.");
			player.broadcastUserInfo(true);

			logGM.info(activeChar.toFullString() + " add hero status to player " + player.getName());
			return true;
		}
		else if(command.equals("admin_setnoble"))
		{
			L2Player player;

			if(args.length > 0)
			{
				player = L2ObjectsStorage.getPlayer(args[0]);
				if(player == null)
				{
					Functions.sendSysMessage(activeChar, "Player: " + args[0] + " not found.");
					return false;
				}
			}
			else
				player = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;

			if(player == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target or type a name.");
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, player, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}
			
			if(player.isNoble())
			{
				player.setNoble(false);
				player.removeSkill(SkillTable.getInstance().getInfo(1323, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(325, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(326, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(327, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1324, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1325, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1326, 1));
				player.removeSkill(SkillTable.getInstance().getInfo(1327, 1));
			}
			else
			{
				player.setNoble(true);
				player.addSkill(SkillTable.getInstance().getInfo(1323, 1));
				player.addSkill(SkillTable.getInstance().getInfo(325, 1));
				player.addSkill(SkillTable.getInstance().getInfo(326, 1));
				player.addSkill(SkillTable.getInstance().getInfo(327, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1324, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1325, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1326, 1));
				player.addSkill(SkillTable.getInstance().getInfo(1327, 1));
			}

			player.sendPacket(new SkillList(player));

			if(player.isNoble())
				player.broadcastPacket(new SocialAction(player.getObjectId(), 16));

			player.sendMessage("Admin changed your noble status.");
			player.broadcastUserInfo(true);

			logGM.info(activeChar.toFullString() + " add noble status to player " + player.getName());
			return true;
		}
		else if(command.equals("admin_setsex"))
		{
			L2Player player = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;
			
			if(player == null)
			{
				Functions.sendSysMessage(activeChar, "Select a player target.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, player, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			player.changeSex();
			player.sendMessage("Your gender has been changed by a GM");
			player.broadcastUserInfo(true);

			logGM.info(activeChar.toFullString() + " " + "change gender to player " + player.getName());
			return true;
		}
		else if(command.equals("admin_setcolor"))
			try
			{
				if(args.length < 1)
				{
					Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
					return false;
				}

				L2Player player = activeChar.getTargetPlayer();
				if(player == null)
				{
					Functions.sendSysMessage(activeChar, "Select a player target.");
					return false;
				}

				player.setNameColor(Integer.decode(args[0].startsWith("0x") ? args[0] : "0x" + args[0]));
				player.sendMessage("Your name color has been changed by a GM");
				player.broadcastUserInfo(true);

				logGM.info(activeChar.toFullString() + " change name color for player " + player.getName());
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equals("admin_add_exp_sp_to_character"))
		{
			L2Player target = activeChar.getTargetPlayer();
			if(target == null)
				target = activeChar;
			
			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}
			
			addExpSp(activeChar, target);
			return true;
		}
		else if(command.equals("admin_add_exp_sp"))
		{
			if(args.length < 2)
			{
				L2Player target = activeChar.getTargetPlayer();
				if(target == null)
					target = activeChar;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}
				
				addExpSp(activeChar, target);
				return true;
			}
			
			try
			{
				L2Player target = activeChar.getTargetPlayer();
				if(target == null)
				{
					Functions.sendSysMessage(activeChar, "Select a player target.");
					return false;
				}

				long exp = Long.parseLong(args[0]);
				long sp = Long.parseLong(args[1]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, exp, sp, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				adminAddExpSp(activeChar, target, exp, sp);
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}	
		else if(command.equals("admin_trans"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			int transformId;
			try
			{
				transformId = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Specify a valid integer value.");
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, null, transformId, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			if(transformId != 0 && activeChar.getTransformation() != 0)
			{
				activeChar.sendPacket(new SystemMessage(SystemMessage.YOU_ALREADY_POLYMORPHED_AND_CANNOT_POLYMORPH_AGAIN));
				return false;
			}
			activeChar.setTransformation(transformId);
			activeChar.sendMessage("Transforming...");
			logGM.info(activeChar.toFullString() + " set transform id: " + transformId);
			return true;
		}
		else if(command.equals("admin_set_refl"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			L2Player target = null;
			int refId = 0;
			try
			{
				if(args.length < 2)
				{
					target = activeChar.getTargetPlayer();
					refId = Integer.parseInt(args[0]);
				}
				else if(args.length > 1)
				{
					target = L2ObjectsStorage.getPlayer(args[0]);
					refId = Integer.parseInt(args[1]);
				}

				if(target == null)
					throw new Exception("Target not found.");
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, refId, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			if(target.getReflection() != refId)
			{
				Functions.sendSysMessage(activeChar, "Move " + target + " to reflection id: " + refId);
				target.setReflection(refId);
				logGM.info(activeChar.toFullString() + " set reflection id: " + refId + " to " + target);
				return true;
			}

			Functions.sendSysMessage(activeChar, target + " already in reflection id: " + refId);
			return false;
		}
		else if(command.equals("admin_setaug"))
		{
			if(args.length < 2)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			int stat12, stat34;
			try
			{
				stat12 = Integer.parseInt(args[0]);
				stat34 = Integer.parseInt(args[1]);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(stat12 > 0 && stat34 > 0)
			{
				L2Player target = activeChar.getTargetPlayer();
				if(target == null)
					target = activeChar;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, stat12, stat34, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				L2ItemInstance weapon = target.getActiveWeaponInstance();

				if(weapon == null)
				{
					Functions.sendSysMessage(activeChar, "Weapon must be eqipped.");
					return false;
				}

				if(weapon.getAugmentation() != null)
				{
					weapon.getAugmentation().removeBonus(target);
					weapon.removeAugmentation();
				}

				weapon.setAugmentation(new L2Augmentation(weapon, ((stat34 << 16) + stat12), 0, true));
				InventoryUpdate iu = new InventoryUpdate();
				iu.addModifiedItem(weapon);
				target.sendPacket(iu);
				target.sendUserInfo(false);
				Functions.sendSysMessage(activeChar, "Augmenation set to " + stat12 + " " + stat34);
				logGM.info(activeChar.toFullString() + " set augmentation: " + stat12 + " " + stat34 + " to " + target + " " + weapon);
				return true;
			}

			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}
		else if(command.equals("admin_pet_add_exp"))
		{
	        if(!(activeChar.getTarget() instanceof L2PetInstance) || args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			L2PetInstance pet = (L2PetInstance) activeChar.getTarget();
			long exp = Long.parseLong(args[0]);
			
			if(!AdminTemplateManager.checkCommand(command, activeChar, pet, exp, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}
			
			pet.addExpAndSp(exp, 0);
			logGM.info(activeChar.toFullString() + " add exp: " + exp + " to pet: " + pet);
			return true;
		}
		else if(command.equals("admin_setclass"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			L2Player target = activeChar.getTargetPlayer();
			if(target == null)
				target = activeChar;

			try
			{
				short classId = Short.parseShort(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, classId, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				for(ClassId ci : ClassId.values())
					if(ci.getId() == classId)
					{
						target.setClassId(classId, true);
						target.sendChanges();
						Functions.sendSysMessage(target, "Class changed: " + CharTemplateTable.getClassNameById(classId));
						if(target != activeChar)
							Functions.sendSysMessage(activeChar, "Class changed: " + CharTemplateTable.getClassNameById(classId));
					}

				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_iz_reset"))
		{
			L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

			if(args.length > 0)
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, args[0], null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target.getVar("instance-" + args[0]) == null)
					Functions.sendSysMessage(activeChar, "No reuse for instant zone: " + args[0]);
				else
				{
					target.unsetVar("instance-" + args[0]);
					Functions.sendSysMessage(activeChar, "Reuse for instant zone: " + args[0] + " reset.");
					logGM.info(activeChar.toFullString() + " reset instance reuse: " + target + " type: " + args[0]);
					return true;
				}
			}
			else
			{
				boolean r = true;
				for(Integer type : InstanceManager.getInstance().getInstanceTypes())
					if(target.getVar("instance-" + type) != null)
					{
						if(!AdminTemplateManager.checkCommand(command, activeChar, target, String.valueOf(type), null, null))
						{
							Functions.sendSysMessage(activeChar, "Access denied.");
							return false;
						}

						target.unsetVar("instance-" + type);
						activeChar.sendMessage("Reuse for instant zone: " + type + " reset.");
						logGM.info(activeChar.toFullString() + " reset instance reuse: " + target + " type: " + type);
						r = false;
					}

				if(r)
					activeChar.sendMessage("No instant zone for reset.");
			}
		}
		else if(command.equals("admin_setparam"))
		{
			if(args.length < 2)
			{
				Functions.sendSysMessage(activeChar, "//setparam level X");
				Functions.sendSysMessage(activeChar, "//setparam exp X");
				Functions.sendSysMessage(activeChar, "//setparam sp X");
				Functions.sendSysMessage(activeChar, "//setparam hp X");
				Functions.sendSysMessage(activeChar, "//setparam mp X");
				Functions.sendSysMessage(activeChar, "//setparam cp X");
				Functions.sendSysMessage(activeChar, "//setparam karma X");
				Functions.sendSysMessage(activeChar, "//setparam pvp X");
				Functions.sendSysMessage(activeChar, "//setparam pk X");
				Functions.sendSysMessage(activeChar, "//setparam fame X");
				Functions.sendSysMessage(activeChar, "//setparam vitality X");
				Functions.sendSysMessage(activeChar, "//setparam str [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam dex [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam int [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam con [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam wit [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam men [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam vp [0-20000]");
				return false;
			}

			String param = args[0];
			long value;

			try
			{
				value = Long.parseLong(args[1]);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "//setparam level X");
				Functions.sendSysMessage(activeChar, "//setparam exp X");
				Functions.sendSysMessage(activeChar, "//setparam sp X");
				Functions.sendSysMessage(activeChar, "//setparam hp X");
				Functions.sendSysMessage(activeChar, "//setparam mp X");
				Functions.sendSysMessage(activeChar, "//setparam cp X");
				Functions.sendSysMessage(activeChar, "//setparam karma X");
				Functions.sendSysMessage(activeChar, "//setparam pvp X");
				Functions.sendSysMessage(activeChar, "//setparam pk X");
				Functions.sendSysMessage(activeChar, "//setparam fame X");
				Functions.sendSysMessage(activeChar, "//setparam vitality X");
				Functions.sendSysMessage(activeChar, "//setparam str [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam dex [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam int [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam con [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam wit [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam men [1-99]");
				Functions.sendSysMessage(activeChar, "//setparam vp [0-20000]");
				return false;
			}

			L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, param, value, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			switch(param)
			{
				case "level":
					if(value < 1 || value > (target.isSubClassActive() ? Experience.getMaxSubLevel() : Experience.getMaxLevel()))
					{
						Functions.sendSysMessage(activeChar, "//setparam level [1-" + (target.isSubClassActive() ? Experience.getMaxSubLevel() : Experience.getMaxLevel()) + "]");
						return false;
					}

					long expAdd = Experience.LEVEL[(byte) value] - target.getExp();
					target.addExpAndSp(expAdd, 0);
					Functions.sendSysMessage(activeChar, "set level=[" + value + "]");
					break;
				case "exp":
					if(value < 0 || value > Experience.LEVEL[Experience.LEVEL.length - 1])
					{
						Functions.sendSysMessage(activeChar, "//setparam exp [0-" + Experience.LEVEL[Experience.LEVEL.length - 1] + "]");
						return false;
					}

					expAdd = value - target.getExp();
					target.addExpAndSp(expAdd, 0);
					Functions.sendSysMessage(activeChar, "set exp=[" + value + "]");
					break;
				case "sp":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam sp [0-" + Integer.MAX_VALUE + "]");
						return false;
					}
					expAdd = value - target.getSp();
					target.addExpAndSp(0, expAdd);
					Functions.sendSysMessage(activeChar, "set sp=[" + value + "]");
					break;
				case "hp":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam hp [0-" + Integer.MAX_VALUE + "]");
						return false;
					}
					target.setCurrentHp(value);
					Functions.sendSysMessage(activeChar, "set hp=[" + value + "]");
					break;
				case "mp":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam mp [0-" + Integer.MAX_VALUE + "]");
						return false;
					}
					target.setCurrentMp(value);
					Functions.sendSysMessage(activeChar, "set mp=[" + value + "]");
					break;
				case "cp":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam cp [0-" + Integer.MAX_VALUE + "]");
						return false;
					}
					target.setCurrentCp(value);
					Functions.sendSysMessage(activeChar, "set cp=[" + value + "]");
					break;
				case "vp":
					if(value < 0 || value > 20000)
					{
						Functions.sendSysMessage(activeChar, "//setparam vp [0-20000]");
						return false;
					}
					target.getVitality().setPoints((int) value);
					Functions.sendSysMessage(activeChar, "set vp=[" + value + "]");
					break;
				case "karma":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam karma [0-" + Integer.MAX_VALUE + "]");
						return false;
					}

					target.setKarma((int) value);
					Functions.sendSysMessage(activeChar, "set karma=[" + value + "]");
					break;
				case "pvp":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam pvp [0-" + Integer.MAX_VALUE + "]");
						return false;
					}

					target.setPvpKills((int) value);
					target.sendUserInfo(true);
					Functions.sendSysMessage(activeChar, "set pvp=[" + value + "]");
					break;
				case "pk":
					if(value < 0 || value > Integer.MAX_VALUE)
					{
						Functions.sendSysMessage(activeChar, "//setparam pk [0-" + Integer.MAX_VALUE + "]");
						return false;
					}

					target.setPkKills((int) value);
					target.sendUserInfo(true);
					Functions.sendSysMessage(activeChar, "set pk=[" + value + "]");
					break;
				case "fame":
					if(value < 0 || value > Config.ALT_MAX_FAME_POINTS)
					{
						Functions.sendSysMessage(activeChar, "//setparam fame [0-" + Config.ALT_MAX_FAME_POINTS + "]");
						return false;
					}

					target.setFame((int) value);
					target.sendUserInfo(true);
					Functions.sendSysMessage(activeChar, "set fame=[" + value + "]");
					break;
				case "vitality":
					if(value < 0 || value > 20000)
					{
						Functions.sendSysMessage(activeChar, "//setparam vitality [0-20000]");
						return false;
					}

					target.getVitality().addPoints((int) value - target.getVitality().getPoints());
					target.sendUserInfo(true);
					Functions.sendSysMessage(activeChar, "set vitality=[" + value + "]");
					break;
				case "str":
					if(value < 1 || value > 99)
					{
						Functions.sendSysMessage(activeChar, "//setparam str [1-99]");
						return false;
					}

					try
					{
						target.addStatFunc(new FuncSet(Stats.STAT_STR, 0x100, this, (double) value));
						target.sendChanges();
					}
					catch(Exception e)
					{
					}
					Functions.sendSysMessage(activeChar, "set STR=[" + value + "]");
					break;
				case "dex":
					if(value < 1 || value > 99)
					{
						Functions.sendSysMessage(activeChar, "//setparam dex [1-99]");
						return false;
					}

					try
					{
						target.addStatFunc(new FuncSet(Stats.STAT_DEX, 0x100, this, (double) value));
						target.sendChanges();
					}
					catch(Exception e)
					{
					}
					Functions.sendSysMessage(activeChar, "set DEX=[" + value + "]");
					break;
				case "int":
					if(value < 1 || value > 99)
					{
						Functions.sendSysMessage(activeChar, "//setparam int [1-99]");
						return false;
					}

					try
					{
						target.addStatFunc(new FuncSet(Stats.STAT_INT, 0x100, this, (double) value));
						target.sendChanges();
					}
					catch(Exception e)
					{
					}
					Functions.sendSysMessage(activeChar, "set INT=[" + value + "]");
					break;
				case "con":
					if(value < 1 || value > 99)
					{
						Functions.sendSysMessage(activeChar, "//setparam con [1-99]");
						return false;
					}

					try
					{
						target.addStatFunc(new FuncSet(Stats.STAT_CON, 0x100, this, (double) value));
						target.sendChanges();
					}
					catch(Exception e)
					{
					}
					Functions.sendSysMessage(activeChar, "set CON=[" + value + "]");
					break;
				case "wit":
					if(value < 1 || value > 99)
					{
						Functions.sendSysMessage(activeChar, "//setparam wit [1-99]");
						return false;
					}

					try
					{
						target.addStatFunc(new FuncSet(Stats.STAT_WIT, 0x100, this, (double) value));
						target.sendChanges();
					}
					catch(Exception e)
					{
					}
					Functions.sendSysMessage(activeChar, "set WIT=[" + value + "]");
					break;
				case "men":
					if(value < 1 || value > 99)
					{
						Functions.sendSysMessage(activeChar, "//setparam men [1-99]");
						return false;
					}

					try
					{
						target.addStatFunc(new FuncSet(Stats.STAT_MEN, 0x100, this, (double) value));
						target.sendChanges();
					}
					catch(Exception e)
					{
					}
					Functions.sendSysMessage(activeChar, "set MEN=[" + value + "]");
					break;
				default:
					Functions.sendSysMessage(activeChar, "//setparam " + param + " " + value + " not supported.");
					break;
			}
		}
		else if(command.equals("admin_setquest"))
		{
			if(args.length < 2)
			{
				Functions.sendSysMessage(activeChar, "//setquest <quest_id> <quest_state>");
				Functions.sendSysMessage(activeChar, "where <quest_state> is 0-2");
				return true;
			}

			try
			{
				int questId = Integer.parseInt(args[0]);
				int state = Integer.parseInt(args[1]);

				Quest q = QuestManager.getQuest(questId);
				if(q == null)
				{
					Functions.sendSysMessage(activeChar, "Quest " + questId + " not found.");
					return false;
				}

				L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, questId, state, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				QuestState qs = target.getQuestState(questId);
				if(qs == null)
					qs = q.newQuestState(target);
				qs.setState(state, false);
				Functions.sendSysMessage(activeChar, "Set quest state " + state + " for quest " + questId + " to " + target.getName() + " success.");
				logGM.info(activeChar.toFullString() + " set quest state: " + state + " quest: " + questId + " to " + target);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "Command failed. " + e);
			}
		}
		else if(command.equals("admin_setmemostate"))
		{
			if(args.length < 2)
			{
				Functions.sendSysMessage(activeChar, "//setmemostate <quest_id> <state>");
				return true;
			}

			try
			{
				int questId = Integer.parseInt(args[0]);
				int state = Integer.parseInt(args[1]);

				Quest q = QuestManager.getQuest(questId);
				if(q == null)
				{
					Functions.sendSysMessage(activeChar, "Quest " + questId + " not found.");
					return false;
				}

				L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, questId, state, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				QuestState qs = target.getQuestState(questId);
				if(qs == null)
				{
					Functions.sendSysMessage(activeChar, "No quest for " + target.getName());
					return false;
				}

				qs.setMemoState(state);
				Functions.sendSysMessage(activeChar, "Set memo state " + state + " for quest " + questId + " to " + target.getName() + " success.");
				logGM.info(activeChar.toFullString() + " set memo state: " + state + " quest: " + questId + " to " + target);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "Command failed. " + e);
			}
		}
		else if(command.equals("admin_setmemocond"))
		{
			if(args.length < 2)
			{
				Functions.sendSysMessage(activeChar, "//setmemocond <quest_id> <cond>");
				return false;
			}

			try
			{
				int questId = Integer.parseInt(args[0]);
				int state = Integer.parseInt(args[1]);

				Quest q = QuestManager.getQuest(questId);
				if(q == null)
				{
					Functions.sendSysMessage(activeChar, "Quest " + questId + " not found.");
					return false;
				}

				L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, questId, state, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				QuestState qs = target.getQuestState(questId);
				if(qs == null)
				{
					Functions.sendSysMessage(activeChar, "No quest for " + target.getName());
					return true;
				}
				qs.setCond(state);
				Functions.sendSysMessage(activeChar, "Set memo cond " + state + " for quest " + questId + " to " + target.getName() + " success.");
				logGM.info(activeChar.toFullString() + " set memo cond: " + state + " quest: " + questId + " to " + target);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "Command failed. " + e);
			}
		}
		else if(command.equals("admin_setmemoex"))
		{
			if(args.length < 3)
			{
				Functions.sendSysMessage(activeChar, "//setmemoex <quest_id> <ex_cond> <ex_val>");
				return false;
			}

			try
			{
				int questId = Integer.parseInt(args[0]);
				String ex_cond = args[1];
				String ex_val = args[2];

				Quest q = QuestManager.getQuest(questId);
				if(q == null)
				{
					Functions.sendSysMessage(activeChar, "Quest " + questId + " not found.");
					return true;
				}

				L2Player target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : activeChar;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, questId, ex_cond, ex_val))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				QuestState qs = target.getQuestState(questId);
				if(qs == null)
				{
					Functions.sendSysMessage(activeChar, "No quest for " + target.getName());
					return false;
				}
				qs.set("ex_" + ex_cond, ex_val);
				Functions.sendSysMessage(activeChar, "Set memo ex cond " + ex_cond + "=" + ex_val + " for quest " + questId + " to " + target.getName() + " success.");
				logGM.info(activeChar.toFullString() + " set memo ex cond: " + ex_cond + "=" + ex_val + " quest: " + questId + " to " + target);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "Command failed. " + e);
			}
		}
		else if(command.equals("admin_setteam"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, "//setteam <team> [name]");
				return false;
			}

			try
			{
				int team = Integer.parseInt(args[0]);
				L2Character target = null;
				if(args.length > 1)
				{
					target = L2ObjectsStorage.getPlayer(args[1]);
					if(target == null)
					{
						Functions.sendSysMessage(activeChar, "player: " + args[1] + " is not online!");
						return false;
					}
				}

				if(target == null)
				{
					L2Object object = activeChar.getTarget();
					if(object instanceof L2Character)
						target = (L2Character) object;
				}

				if(target == null)
				{
					Functions.sendSysMessage(activeChar, "no target.");
					return false;
				}

				target.setTeam(team);
				Functions.sendSysMessage(activeChar, "Set team=" + team + " to " + target);
				logGM.info(activeChar.toFullString() + " set team: " + team + " to " + target);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "Command failed. " + e);
			}
		}
		else if(command.startsWith("admin_inzone"))
		{
			try
			{
				String cmd = args[0];
				int id = Integer.parseInt(args[1]);
				InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(id);
				if(it == null)
				{
					Functions.sendSysMessage(activeChar, "No instance zone id: " + id);
					return true;
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, activeChar.getTargetPlayer(), cmd, id, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				List<L2Player> party = new FastList<>();

				switch(cmd)
				{
					case "enter_individual":
						party.add(activeChar);
						break;
					case "enter_party":
						if(activeChar.getParty() == null)
						{
							Functions.sendSysMessage(activeChar, "You must be in a party for enter.");
							return true;
						}

						party.addAll(activeChar.getParty().getPartyMembers());
						break;
					case "enter_mpcc":
						if(activeChar.getParty() == null)
						{
							Functions.sendSysMessage(activeChar, "You must be in a party for enter.");
							return true;
						}

						if(activeChar.getParty().getCommandChannel() == null)
						{
							Functions.sendSysMessage(activeChar, "You must be in a command chanel for enter.");
							return true;
						}

						party.addAll(activeChar.getParty().getCommandChannel().getMembers());
						break;
				}

				Instance inst = InstanceManager.getInstance().createNewInstance(id, party);

				for(L2Player member : party)
					member.teleToLocation(inst.getStartLoc(), inst.getReflection());

				logGM.info(activeChar.toFullString() + " enter instance: " + id + " enter type: " + cmd + " target: " + activeChar.getTargetPlayer());
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "//inzone (enter_individual | enter_mpcc | enter_party) inzone_id");
				return false;
			}
		}

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	private void listCharacters(L2Player activeChar, int page)
	{
		GArray<L2Player> allPlayers = L2ObjectsStorage.getAllPlayers();
		L2Player[] players = allPlayers.toArray(new L2Player[allPlayers.size()]);

		int MaxCharactersPerPage = 20;
		int MaxPages = players.length / MaxCharactersPerPage;

		if(players.length > MaxCharactersPerPage * MaxPages)
			MaxPages++;

		// Check if number of users changed
		if(page > MaxPages)
			page = MaxPages;

		int CharactersStart = MaxCharactersPerPage * page;
		int CharactersEnd = players.length;
		if(CharactersEnd - CharactersStart > MaxCharactersPerPage)
			CharactersEnd = CharactersStart + MaxCharactersPerPage;

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td width=270>You can find a character by writing his name and</td></tr>");
		replyMSG.append("<tr><td width=270>clicking Find bellow.<br></td></tr>");
		replyMSG.append("<tr><td width=270>Note: Names should be written case sensitive.</td></tr>");
		replyMSG.append("</table><br>");
		replyMSG.append("<center><table><tr><td>");
		replyMSG.append("<edit var=\"character_name\" width=80></td><td><button value=\"Find\" action=\"bypass -h admin_find_character $character_name\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\">");
		replyMSG.append("</td></tr></table></center><br><br>");

		for(int x = 0; x < MaxPages; x++)
		{
			int pagenr = x + 1;
			replyMSG.append("<center><a action=\"bypass -h admin_show_characters " + x + "\">Page " + pagenr + "</a></center>");
		}
		replyMSG.append("<br>");

		// List Players in a Table
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td width=80>Name:</td><td width=110>Class:</td><td width=40>Level:</td></tr>");
		for(int i = CharactersStart; i < CharactersEnd; i++)
			replyMSG.append("<tr><td width=80>" + "<a action=\"bypass -h admin_character_list " + players[i].getName() + "\">" + players[i].getName() + "</a></td><td width=110>" + players[i].getTemplate().className + "</td><td width=40>" + players[i].getLevel() + "</td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	public static void showCharacterList(L2Player activeChar, L2Player player)
	{
		if(player == null)
		{
			L2Object target = activeChar.getTarget();
			if(target.isPlayer())
				player = (L2Player) target;
			else
				return;
		}
		else
			activeChar.setTarget(player);

		String clanName = "No Clan";
		if(player.getClanId() != 0)
			clanName = player.getClan().getName();

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_show_characters 0\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<center>Character Information:</center>");
		replyMSG.append("<br1>");

		// Character Player Info
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td width=135>IP: " + player.getNetConnection().getIpAddr() + "</td><td width=135>Acc: " + player.getNetConnection().getLoginName() + "</td></tr>");
		replyMSG.append("<tr><td width=135>Name: " + player.getName() + "</td><td width=135>Lv: " + player.getLevel() + " " + player.getTemplate().className + "</td></tr>");
		replyMSG.append("<tr><td width=135>Clan: " + clanName + "</td><td width=135>Exp: " + player.getExp() + "</td></tr>");
		replyMSG.append("</table>");

		// Character ClassID & Coordinates
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td width=270>Class Template Id: " + player.getClassId() + "/" + player.getClassId().getId() + "</td></tr>");
		replyMSG.append("<tr><td width=270>Character Coordinates: " + player.getX() + " " + player.getY() + " " + player.getZ() + "</td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<br>");

		NumberFormat df = NumberFormat.getNumberInstance(Locale.ENGLISH);
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(1);

		// Character Stats
		replyMSG.append("<table width=250>");
		replyMSG.append("<tr><td width=40></td><td width=70>Curent:</td><td width=70>Max:</td><td width=70></td></tr>");
		replyMSG.append("<tr><td width=40>HP:</td><td width=70>" + (int) player.getCurrentHp() + "</td><td width=70>" + player.getMaxHp() + "</td><td width=70>Karma: " + player.getKarma() + "</td></tr>");
		replyMSG.append("<tr><td width=40>MP:</td><td width=70>" + (int) player.getCurrentMp() + "</td><td width=70>" + player.getMaxMp() + "</td><td width=70>Pvp: " + player.getPvpKills() + "</td></tr>");
		replyMSG.append("<tr><td width=40>Load:</td><td width=70>" + player.getCurrentLoad() + "</td><td width=70>" + player.getMaxLoad() + "</td><td width=70>Pvp Flag: " + player.getPvpFlag() + "</td></tr>");
		replyMSG.append("<tr><td width=40>SP:</td><td width=70>" + player.getSp() + "</td><td width=70>pAtkRange</td><td width=70>" + player.getPhysicalAttackRange() + "</td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td width=60>P.ATK: " + player.getPAtk(null) + "</td><td width=100>M.ATK: " + player.getMAtk(null, null) + "</td><td width=40>Manage:</td></tr>");
		replyMSG.append("<tr><td width=60>P.DEF: " + player.getPDef(null) + "</td><td width=100>M.DEF: " + player.getMDef(null, null) + "</td><td width=40></td></tr>");
		replyMSG.append("<tr><td width=90>Accuracy: " + player.getAccuracy() + "</td><td width=70>Evasion: " + player.getEvasionRate(null) + "</td><td width=40><button value=\"Skills\" action=\"bypass -h admin_show_skills\" width=60 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td width=90>Critical: " + player.getCriticalHit(null, null) + "</td><td width=70>Speed: " + player.getWalkSpeed() + "/" + player.getRunSpeed() + "</td><td width=40><button value=\"Stats\" action=\"bypass -h admin_edit_character\" width=60 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td width=90>ATK Spd: " + player.getPAtkSpd() + "</td><td width=70>Casting Spd: " + player.getMAtkSpd() + "</td><td width=40><button value=\"Exp & Sp\" action=\"bypass -h admin_add_exp_sp_to_character\" width=60 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td width=90>MCrit: " + df.format(player.getCriticalMagic(player, null) / 10) + "%</td><td width=70> </td><td width=40><button value=\"Actions\" action=\"bypass -h admin_character_actions\" width=60 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table></body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void setTargetKarma(L2Player activeChar, int newKarma)
	{
		L2Object target = activeChar.getTarget();
		if(target == null)
		{
			activeChar.sendPacket(Msg.INVALID_TARGET);
			return;
		}

		L2Player player;
		if(target.isPlayer())
			player = (L2Player) target;
		else
			return;

		if(newKarma >= 0)
		{
			int oldKarma = player.getKarma();
			player.setKarma(newKarma);

			player.sendMessage("Admin has changed your karma from " + oldKarma + " to " + newKarma + ".");
			activeChar.sendMessage("Successfully Changed karma for " + player.getName() + " from (" + oldKarma + ") to (" + newKarma + ").");

			logGM.info(activeChar.toFullString() + " changed karma for " + player.getName() + " from (" + oldKarma + ") to (" + newKarma + ")");
		}
		else
			activeChar.sendMessage("You must enter a value for karma greater than or equal to 0.");
	}

	private void adminModifyCharacter(L2Player activeChar, String modifications)
	{
		L2Object target = activeChar.getTarget();
		L2Player player;
		if(target.isPlayer())
			player = (L2Player) target;
		else
			return;

		StringTokenizer st = new StringTokenizer(modifications);
		if(st.countTokens() != 6)
			editCharacter(player);
		else
		{
			String hp = st.nextToken();
			String mp = st.nextToken();
			String karma = st.nextToken();
			String pvpflag = st.nextToken();
			String pvpkills = st.nextToken();
			String classid = st.nextToken();
			int hpval = Integer.parseInt(hp);
			if(hpval < 1)
				hpval = 1;
			int mpval = Integer.parseInt(mp);
			// int loadval = Integer.parseInt(load);
			int karmaval = Integer.parseInt(karma);
			int pvpflagval = Integer.parseInt(pvpflag);
			int pvpkillsval = Integer.parseInt(pvpkills);
			short classidval = Short.parseShort(classid);

			// Common character information
			player.sendMessage("Admin has changed your stats. Hp: " + hpval + " Mp: " + mpval + " Karma: " + karmaval + " Pvp: " + pvpflagval + " / " + pvpkillsval + " ClassId: " + classidval);

			player.setCurrentHp(hpval);
			player.setCurrentMp(mpval);
			player.setKarma(karmaval);
			player.setPvpFlag(pvpflagval);
			player.setPvpKills(pvpkillsval);
			player.setClassId(classidval, true);

			player.sendChanges();

			// Admin information
			activeChar.sendMessage("Changed stats of " + player.getName() + ".  Hp: " + hpval + " Mp: " + mpval + " Karma: " + karmaval + " Pvp: " + pvpflagval + " / " + pvpkillsval + " ClassId: " + classidval);

			showCharacterList(activeChar, null); // Back to start
			player.broadcastUserInfo(true);
			player.decayMe();
			player.spawnMe(activeChar.getLoc());
		}
	}

	private void editCharacter(L2Player activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2Player player;
		if(target != null && target.isPlayer())
			player = (L2Player) target;
		else
			return;

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<center>Editing character: " + player.getName() + "</center><br>");
		replyMSG.append("<table width=250>");
		replyMSG.append("<tr><td width=40></td><td width=70>Curent:</td><td width=70>Max:</td><td width=70></td></tr>");
		replyMSG.append("<tr><td width=40>HP:</td><td width=70>" + player.getCurrentHp() + "</td><td width=70>" + player.getMaxHp() + "</td><td width=70>Karma: " + player.getKarma() + "</td></tr>");
		replyMSG.append("<tr><td width=40>MP:</td><td width=70>" + player.getCurrentMp() + "</td><td width=70>" + player.getMaxMp() + "</td><td width=70>Pvp Kills: " + player.getPvpKills() + "</td></tr>");
		replyMSG.append("<tr><td width=40>Load:</td><td width=70>" + player.getCurrentLoad() + "</td><td width=70>" + player.getMaxLoad() + "</td><td width=70>Pvp Flag: " + player.getPvpFlag() + "</td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<table width=270><tr><td>Class Template Id: " + player.getClassId() + "/" + player.getClassId().getId() + "</td></tr></table><br>");
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td>Note: Fill all values before saving the modifications.</td></tr>");
		replyMSG.append("</table><br>");
		replyMSG.append("<table width=270>");
		replyMSG.append("<tr><td width=50>Hp:</td><td><edit var=\"hp\" width=50></td><td width=50>Mp:</td><td><edit var=\"mp\" width=50></td></tr>");
		replyMSG.append("<tr><td width=50>Pvp Flag:</td><td><edit var=\"pvpflag\" width=50></td><td width=50>Karma:</td><td><edit var=\"karma\" width=50></td></tr>");
		replyMSG.append("<tr><td width=50>Class Id:</td><td><edit var=\"classid\" width=50></td><td width=50>Pvp Kills:</td><td><edit var=\"pvpkills\" width=50></td></tr>");
		replyMSG.append("</table><br>");
		replyMSG.append("<center><button value=\"Save Changes\" action=\"bypass -h admin_save_modifications $hp $mp $karma $pvpflag $pvpkills $classid\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></center><br>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void showCharacterActions(L2Player activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2Player player;
		if(target != null && target.isPlayer())
			player = (L2Player) target;
		else
			return;

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table><br><br>");
		replyMSG.append("<center>Admin Actions for: " + player.getName() + "</center><br>");
		replyMSG.append("<center><table width=200><tr>");
		replyMSG.append("<td width=100>Argument(*):</td><td width=100><edit var=\"arg\" width=100></td>");
		replyMSG.append("</tr></table><br></center>");
		replyMSG.append("<table width=270>");

		replyMSG.append("<tr><td width=90><button value=\"Teleport\" action=\"bypass -h admin_teleportto " + player.getName() + "\" width=85 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=90><button value=\"Recall\" action=\"bypass -h admin_recall " + player.getName() + "\" width=85 height=20 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=90></td></tr>");

		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	// FIXME: needs removal, whole thing needs to use getTarget()
	private void findCharacter(L2Player activeChar, String CharacterToFind)
	{
		GArray<L2Player> allPlayers = L2ObjectsStorage.getAllPlayers();
		L2Player[] players = allPlayers.toArray(new L2Player[allPlayers.size()]);
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		int CharactersFound = 0;

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_show_characters 0\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");

		for(L2Player element : players)
			if(element.getName().startsWith(CharacterToFind))
			{
				CharactersFound = CharactersFound + 1;
				replyMSG.append("<table width=270>");
				replyMSG.append("<tr><td width=80>Name</td><td width=110>Class</td><td width=40>Level</td></tr>");
				replyMSG.append("<tr><td width=80><a action=\"bypass -h admin_character_list " + element.getName() + "\">" + element.getName() + "</a></td><td width=110>" + element.getTemplate().className + "</td><td width=40>" + element.getLevel() + "</td></tr>");
				replyMSG.append("</table>");
			}

		if(CharactersFound == 0)
		{
			replyMSG.append("<table width=270>");
			replyMSG.append("<tr><td width=270>Your search did not find any characters.</td></tr>");
			replyMSG.append("<tr><td width=270>Please try again.<br></td></tr>");
			replyMSG.append("</table><br>");
			replyMSG.append("<center><table><tr><td>");
			replyMSG.append("<edit var=\"character_name\" width=80></td><td><button value=\"Find\" action=\"bypass -h admin_find_character $character_name\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\">");
			replyMSG.append("</td></tr></table></center>");
		}
		else
		{
			replyMSG.append("<center><br>Found " + CharactersFound + " character");

			if(CharactersFound == 1)
				replyMSG.append(".");
			else if(CharactersFound > 1)
				replyMSG.append("s.");
		}

		replyMSG.append("</center></body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void addExpSp(final L2Player activeChar, L2Player target)
	{
		final NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		final StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Character Selection Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<br><br>");
		replyMSG.append("<table width=270><tr><td>Name: " + target.getName() + "</td></tr>");
		replyMSG.append("<tr><td>Lv: " + target.getLevel() + " " + target.getTemplate().className + "</td></tr>");
		replyMSG.append("<tr><td>Exp: " + target.getExp() + "</td></tr>");
		replyMSG.append("<tr><td>Sp: " + target.getSp() + "</td></tr></table>");
		replyMSG.append("<br><table width=270><tr><td>Note: Dont forget that modifying players skills can</td></tr>");
		replyMSG.append("<tr><td>ruin the game...</td></tr></table><br>");
		replyMSG.append("<table width=270><tr><td>Note: Fill all values before saving the modifications.,</td></tr>");
		replyMSG.append("<tr><td>Note: Use 0 if no changes are needed.</td></tr></table><br>");
		replyMSG.append("<center><table><tr>");
		replyMSG.append("<td>Exp: <edit var=\"exp_to_add\" width=50></td>");
		replyMSG.append("<td>Sp:  <edit var=\"sp_to_add\" width=50></td>");
		replyMSG.append("<td>&nbsp;<button value=\"Save Changes\" action=\"bypass -h admin_add_exp_sp $exp_to_add $sp_to_add\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table></center>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void adminAddExpSp(final L2Player activeChar, L2Player target, long exp, long sp)
	{
		if(exp != 0 || sp != 0)
		{
			// Common character information
			target.sendMessage("Admin is adding you " + exp + " exp and " + sp + " SP.");
			target.addExpAndSp(exp, sp);

			// Admin information
			activeChar.sendMessage("Added " + exp + " exp and " + sp + " SP to " + target.getName() + ".");

			logGM.info(activeChar.toFullString() + " added " + exp + " exp and " + sp + " SP to " + target.getName());
		}
	}

	public static void showEffects(L2Player activeChar, L2Object tgt)
	{
		L2Player player = null;
		L2NpcInstance mob = null;
		if(tgt != null && tgt.isPlayer())
			player = (L2Player) tgt;
		if(tgt != null && tgt.isMonster())
			mob = (L2NpcInstance) tgt;

		if(player == null && mob == null)
			return;

		if(mob == null)
		{
			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

			StringBuffer replyMSG = new StringBuffer("<html><body>");
			replyMSG.append("<center>" + player.getName() + "'s Effects Information:</center>");
			replyMSG.append("<br1>");

			for(L2Effect e : player.getAllEffects())
				replyMSG.append("* " + e + "<br>");

			replyMSG.append("<br>");

			replyMSG.append("</body></html>");

			adminReply.setHtml(replyMSG.toString());
			activeChar.sendPacket(adminReply);
		}
		else
		{
			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

			System.out.println("//see_effects output: ");
			StringBuffer replyMSG = new StringBuffer("<html><body>");
			replyMSG.append("<center>" + mob.getName() + "'s Effects Information:</center>");
			replyMSG.append("<br1>");
			System.out.println("Mob Name: " + mob.getName());
			for(L2Effect e : mob.getAllEffects())
			{
				replyMSG.append("* " + e + "<br>");
				System.out.println("Effect: " + e);
			}

			replyMSG.append("<br>");

			replyMSG.append("</body></html>");
			System.out.println("//end of effects.");
			adminReply.setHtml(replyMSG.toString());
			activeChar.sendPacket(adminReply);
		}
	}
}