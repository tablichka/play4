package commands.admin;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.SevenSigns;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.Earthquake;
import ru.l2gw.gameserver.serverpackets.SSQInfo;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.serverpackets.StopMove;
import ru.l2gw.gameserver.tables.SkillTable;

public class AdminEffects extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_invis", null),
					new AdminCommandDescription("admin_vis", null),
					new AdminCommandDescription("admin_earthquake", "usage: //earthquake <intencity> <duration>"),
					new AdminCommandDescription("admin_unpara_all", null),
					new AdminCommandDescription("admin_para_all", null),
					new AdminCommandDescription("admin_unpara", null),
					new AdminCommandDescription("admin_para", "usage: //para [type] - 1 stone, 0 paralyze"),
					new AdminCommandDescription("admin_polyself", "usage: //polyself <poly id>"),
					new AdminCommandDescription("admin_unpolyself", null),
					new AdminCommandDescription("admin_changename", "usage: //changename <name>"),
					new AdminCommandDescription("admin_gmspeed", null),
					new AdminCommandDescription("admin_invul", null),
					new AdminCommandDescription("admin_setinvul", null),
					new AdminCommandDescription("admin_social", "usage: //social [social id]"),
					new AdminCommandDescription("admin_abnormal", "usage: //abnormal <id>"),
					new AdminCommandDescription("admin_sky", "usage: //sky <sky>"),
					new AdminCommandDescription("admin_start_ab", "usage: //start_ab <abnormal>"),
					new AdminCommandDescription("admin_stop_ab", "usage: //stop_ab <abnormal>")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_social"))
		{
			int val;
			if(args.length < 1)
				val = Rnd.get(1, 7);
			else
				try
				{
					val = Integer.parseInt(args[0]);
				}
				catch(NumberFormatException nfe)
				{
					activeChar.sendMessage("Specify a valid social action number.");
					return false;
				}

			L2Character target = (L2Character) activeChar.getTarget();

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, val, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}
			
			if(target == null || target == activeChar)
				activeChar.broadcastPacket(new SocialAction(activeChar.getObjectId(), val));
			else
				target.broadcastPacket(new SocialAction(target.getObjectId(), val));
		}
		else if(command.startsWith("admin_gmspeed"))
			try
			{
				int val = Integer.parseInt(args[0]);
				L2Effect superhaste = activeChar.getEffectBySkillId(7029);
				int sh_level = superhaste == null ? 0 : superhaste.getSkill().getLevel();

				if(val == 0)
				{
					activeChar.stopEffect(7029); //снимаем еффект
					activeChar.unsetVar("gm_gmspeed");
				}
				else if(val >= 1 && val <= 4)
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, null, val, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					if(Config.SAVE_GM_EFFECTS)
						activeChar.setVar("gm_gmspeed", String.valueOf(val));
					if(val != sh_level)
					{
						if(sh_level != 0)
							activeChar.doCast(SkillTable.getInstance().getInfo(7029, sh_level), activeChar, true); //снимаем еффект
						activeChar.doCast(SkillTable.getInstance().getInfo(7029, val), activeChar, true);
					}
					return true;
				}
				else
				{
					activeChar.sendMessage("Use //gmspeed value = [0...4].");
					return false;
				}
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Use //gmspeed value = [0...4].");
				return false;
			}
			finally
			{
				activeChar.updateEffectIcons();
			}
		else if(command.equalsIgnoreCase("admin_invis") || command.equalsIgnoreCase("admin_vis"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			if(activeChar.isInvisible())
			{
				activeChar.setInvisible(false);
				activeChar.broadcastUserInfo(true);
				if(activeChar.getPet() != null)
					activeChar.getPet().broadcastPetInfo();
			}
			else
			{
				activeChar.setInvisible(true);
				activeChar.sendUserInfo(true);
				activeChar.getKnownRelations().clear();
				if(activeChar.getCurrentRegion() != null)
					for(L2WorldRegion neighbor : activeChar.getCurrentRegion().getNeighbors())
						if(neighbor != null)
							neighbor.removePlayerFromOtherPlayers(activeChar);
			}

			return true;
		}
		else if(command.equalsIgnoreCase("admin_earthquake"))
			try
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				int intensity = Integer.parseInt(args[0]);
				int duration = Integer.parseInt(args[1]);
				activeChar.broadcastPacket(new Earthquake(activeChar.getLoc(), intensity, duration));
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equalsIgnoreCase("admin_para"))
		{
			String type = args.length > 0 ? args[0] : "1";
			try
			{
				L2Character target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target != null)
				{
					if(type.equals("1"))
						target.startAbnormalEffect(L2Skill.AbnormalVisualEffect.paralyze);
					else
						target.startAbnormalEffect(L2Skill.AbnormalVisualEffect.stone);

					target.setDisabled(true);
					target.stopMove();
					return true;
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equalsIgnoreCase("admin_unpara"))
			try
			{
				L2Character target = activeChar.getTarget() instanceof L2Character ? (L2Character) activeChar.getTarget() : null;

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target != null)
				{
					target.stopAbnormalEffect(L2Skill.AbnormalVisualEffect.paralyze);
					target.stopAbnormalEffect(L2Skill.AbnormalVisualEffect.stone);
					target.setDisabled(false);
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equalsIgnoreCase("admin_para_all"))
			try
			{
				for(L2Player player : activeChar.getAroundPlayers(1250))
				{
					if(!player.isGM())
					{
						if(!AdminTemplateManager.checkCommand(command, activeChar, player, null, null, null))
						{
							Functions.sendSysMessage(activeChar, "Access denied for: " + player);
							continue;
						}

						player.startAbnormalEffect(L2Skill.AbnormalVisualEffect.paralyze);
						player.setDisabled(true);
						player.broadcastPacket(new StopMove(player));
					}
				}
			}
			catch(Exception e)
			{}
		else if(command.equalsIgnoreCase("admin_unpara_all"))
			try
			{
				for(L2Player player : activeChar.getAroundPlayers(2500))
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, player, null, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied for: " + player);
						continue;
					}

					player.stopAbnormalEffect(L2Skill.AbnormalVisualEffect.paralyze);
					player.setDisabled(false);
				}
			}
			catch(Exception e)
			{}
		else if(command.equalsIgnoreCase("admin_polyself"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			try
			{
				activeChar.setPolyInfo("npc", args[0]);
				activeChar.teleToLocation(activeChar.getLoc());
				activeChar.broadcastUserInfo(true);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equalsIgnoreCase("admin_unpolyself"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			activeChar.setPolyInfo(null, "1");
			activeChar.decayMe();
			activeChar.spawnMe(activeChar.getLoc());
			activeChar.broadcastUserInfo(true);
		}
		else if(command.equalsIgnoreCase("admin_changename"))
			try
			{
				String name = args[0];
				String oldName;
					L2Player target = activeChar.getTargetPlayer();

					if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					if(target == null)
					{
						target = activeChar;
						oldName = activeChar.getName();
					}
					else
					{
						oldName = target.getName();
					}

					L2World.removeObject(target);
					target.decayMe();

					target.setName(name);
					target.spawnMe();

					target.broadcastUserInfo(true);
					activeChar.sendMessage("Changed name from " + oldName + " to " + name + ".");
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equalsIgnoreCase("admin_invul"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			handleInvul(activeChar);

			if(activeChar.isInvul())
			{
				if(Config.SAVE_GM_EFFECTS)
					activeChar.setVar("gm_invul", "true");
			}
			else
				activeChar.unsetVar("gm_invul");
		}
		else if(command.equalsIgnoreCase("admin_setinvul"))
		{
			L2Player target = activeChar.getTargetPlayer();

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			if(target != null)
				handleInvul(target);
		}
		else if(command.startsWith("admin_abnormal"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			int val;
			try
			{
				val = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, null, val, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			switch(val)
			{
				case 1:
					activeChar.setCustomEffect(0x01000000);
					break;
				case 2:
					activeChar.setCustomEffect(0x02000000);
					break;
				case 3:
					activeChar.setCustomEffect(0x04000000);
					break;
				case 4:
					activeChar.setCustomEffect(0x08000000);
					break;
				case 5:
					activeChar.setCustomEffect(0x10000000);
					break;
				case 6:
					activeChar.setCustomEffect(0x20000000);
					break;
				case 7:
					activeChar.setCustomEffect(0x40000000);
					break;
				case 8:
					activeChar.setCustomEffect(0x80000000);
					break;
			}

			activeChar.updateAbnormalEffect();
		}
		else if(command.equalsIgnoreCase("admin_sky"))
		{
			try
			{
				int sky = Integer.parseInt(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, sky, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				SevenSigns.setSky(sky);
				SSQInfo.sky = sky;
				SSQInfo ss = new SSQInfo();
				for(L2Player player : L2ObjectsStorage.getAllPlayers())
					player.sendPacket(ss);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_start_ab"))
		{
			int val;
			try
			{
				val = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			L2Character target = activeChar.getTarget() != null ? (L2Character) activeChar.getTarget() : activeChar;

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, val, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			if((target.getAbnormalEffect() & val) == val)
			{
				target.stopAbnormalEffect(L2Skill.AbnormalVisualEffect.getAbnormalByMask(val));
				activeChar.sendMessage("Stop abnormal " + val + " for " + target);
			}
			else
			{
				target.startAbnormalEffect(L2Skill.AbnormalVisualEffect.getAbnormalByMask(val));
				activeChar.sendMessage("Start abnormal " + val + " for " + target);
			}
			if(args.length > 1)
				AdminHelpPage.showHelpPage(activeChar, "abnormals.htm");
		}
		else if(command.startsWith("admin_stop_ab"))
		{
			int val;
			try
			{
				val = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			L2Character target = activeChar.getTarget() != null ? (L2Character) activeChar.getTarget() : activeChar;

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, val, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			target.stopAbnormalEffect(L2Skill.AbnormalVisualEffect.getAbnormalByMask(val));
			activeChar.sendMessage("Stop abnormal " + val + " for " + target);
		}
		return true;
	}

	private void handleInvul(L2Player activeChar)
	{
		if(activeChar.isInvul())
		{
			activeChar.setIsInvul(false);
			if(activeChar.getPet() != null)
				activeChar.getPet().setIsInvul(false);
			activeChar.sendMessage(activeChar.getName() + " is now mortal.");
		}
		else
		{
			activeChar.setIsInvul(true);
			if(activeChar.getPet() != null)
				activeChar.getPet().setIsInvul(true);
			activeChar.sendMessage(activeChar.getName() + " is now immortal.");
		}
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}