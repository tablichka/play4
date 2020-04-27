package commands.admin;

import ru.l2gw.database.mysql;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.Scripts;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.RaidBossSpawnManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.GmListTable;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.lang.reflect.Constructor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class handles following admin commands: - show_spawns = shows menu -
 * spawn_index lvl = shows menu for monsters with respective level -
 * spawn_monster id = spawns monster id on target spawn1 id count - заспавнить
 * count мобов только 1 раз
 */
public class AdminSpawn extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {
			new AdminCommandDescription("admin_show_spawns", null),
			new AdminCommandDescription("admin_spawn", null),
			new AdminCommandDescription("admin_spawn_monster", null),
			new AdminCommandDescription("admin_spawn_index", null),
			new AdminCommandDescription("admin_unspawnall", null),
			new AdminCommandDescription("admin_spawn1", null),
			new AdminCommandDescription("admin_setheading", null),
			new AdminCommandDescription("admin_airship", null),
			new AdminCommandDescription("admin_setai", null),
			new AdminCommandDescription("admin_mksce", null)};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_show_spawns"))
			AdminHelpPage.showHelpPage(activeChar, "spawns.htm");
		else if(command.equals("admin_spawn_index"))
			try
			{
				String val = args[0];
				AdminHelpPage.showHelpPage(activeChar, "spawns/" + val + ".htm");
			}
			catch(Exception e)
			{}
		else if(command.equals("admin_spawn1"))
		{
			try
			{
				String id = args[0];
				int mobCount = 1;
				if(args.length > 1)
					mobCount = Integer.parseInt(args[1]);
				spawnMonster(activeChar, id, 0, mobCount);
			}
			catch(Exception e)
			{
				// Case of wrong monster data
			}
		}
		else if(command.equals("admin_spawn") || command.equals("admin_spawn_monster"))
		{
			try
			{
				String id = args[0];
				int respawnTime = 30;
				int mobCount = 1;
				if(args.length > 1)
					mobCount = Integer.parseInt(args[1]);
				if(args.length > 2)
					respawnTime = Integer.parseInt(args[2]);
				spawnMonster(activeChar, id, respawnTime, mobCount);
			}
			catch(Exception e)
			{
				// Case of wrong monster data
			}
		}
		else if(command.equals("admin_unspawnall"))
		{
			for(L2Player player : L2ObjectsStorage.getAllPlayers())
				player.sendPacket(new SystemMessage(SystemMessage.THE_NPC_SERVER_IS_NOT_OPERATING));
			L2World.deleteVisibleNpcSpawns();
			GmListTable.broadcastMessageToGMs("NPC Unspawn completed!");
		}
		else if(command.equals("admin_setai"))
		{
			if(activeChar.getTarget() == null || !activeChar.getTarget().isNpc())
			{
				activeChar.sendMessage("Please select target NPC or mob.");
				return false;
			}

			if(args.length < 1)
			{
				activeChar.sendMessage("Please specify AI name.");
				return false;
			}
			String aiName = args[0];
			L2NpcInstance target = (L2NpcInstance) activeChar.getTarget();

			Constructor aiConstructor = null;
			try
			{
				if(!aiName.equalsIgnoreCase("npc"))
					aiConstructor = Class.forName("ru.l2gw.gameserver.ai." + aiName).getConstructors()[0];
			}
			catch(Exception e)
			{
				try
				{
					aiConstructor = Scripts.getInstance().getClasses().get("ai." + aiName).getRawClass().getConstructors()[0];
				}
				catch(Exception e1)
				{
					activeChar.sendMessage("This type AI not found.");
					return false;
				}
			}

			target.detachAI();

			if(aiConstructor != null)
			{
				try
				{
					target.setAI((L2CharacterAI) aiConstructor.newInstance(target));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				target.getAI().startAITask();
			}
		}
		else if(command.equals("admin_setheading"))
		{
			L2Object obj = activeChar.getTarget();
			if(!(obj instanceof L2NpcInstance))
			{
				activeChar.sendMessage("Target is incorrect!");
				return false;
			}

			L2NpcInstance npc = (L2NpcInstance) obj;

			L2Spawn spawn = npc.getSpawn();
			if(spawn == null)
			{
				activeChar.sendMessage("Spawn for this npc == null!");
				return false;
			}

			if(!mysql.set("update spawnlist set heading = " + activeChar.getHeading() //
					+ " where npc_templateid = " + npc.getNpcId() //
					+ " and locx = " + spawn.getLocx() //
					+ " and locy = " + spawn.getLocy() //
					+ " and locz = " + spawn.getLocz() //
					+ " and loc_id = " + spawn.getLocation()))
			{
				activeChar.sendMessage("Error in mysql query!");
				return false;
			}

			npc.setHeading(activeChar.getHeading());
			npc.decayMe();
			npc.spawnMe();
			activeChar.sendMessage("New heading : " + activeChar.getHeading());
		}
		else if(command.equals("admin_mksce"))
		{
			if(args.length < 2)
			{
				activeChar.sendMessage("//mksce maker_name eventId p1 p2");
				return true;
			}

			String maker = args[0];
			int eventId = Integer.parseInt(args[1]);
			Object p1 = null;
			Object p2 = null;
			if(args.length > 2)
			{
				String pp1 = args[2];
				try
				{
					p1 = Integer.parseInt(pp1);
				}
				catch(Exception e)
				{
					p1 = pp1;
				}
				if(args.length > 3)
				{
					String pp2 = args[3];
					try
					{
						p2 = Integer.parseInt(pp2);
					}
					catch(Exception e)
					{
						p2 = pp2;
					}
				}
			}

			DefaultMaker dm = SpawnTable.getInstance().getNpcMaker(maker);
			if(dm == null)
			{
				activeChar.sendMessage("No maker: " + maker + "found.");
				return true;
			}

			dm.onScriptEvent(eventId, p1, p2);
			activeChar.sendMessage("Send maker event: " + eventId + "," + p1 + "," + p2);
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	private void spawnMonster(L2Player activeChar, String monsterId, int respawnTime, int mobCount)
	{
		L2Object target = activeChar.getTarget();
		if(target == null)
			target = activeChar;

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher regexp = pattern.matcher(monsterId);
		L2NpcTemplate template;
		if(regexp.matches())
		{
			// First parameter was an ID number
			int monsterTemplate = Integer.parseInt(monsterId);
			template = NpcTable.getTemplate(monsterTemplate);
		}
		else
		{
			// First parameter wasn't just numbers so go by name not ID
			monsterId = monsterId.replace('_', ' ');
			template = NpcTable.getTemplateByName(monsterId);
		}

		if(template == null)
		{
			activeChar.sendMessage("Incorrect monster template.");
			return;
		}

		try
		{
			L2Spawn spawn = new L2Spawn(template);
			spawn.setLoc(target.getLoc());
			spawn.setLocation(0);
			spawn.setAmount(mobCount);
			spawn.setHeading(activeChar.getHeading());
			spawn.setRespawnDelay(respawnTime);
			spawn.setReflection(activeChar.getReflection());

			if(RaidBossSpawnManager.getInstance().isDefined(spawn.getNpcId()))
				activeChar.sendMessage("Raid Boss " + template.name + " already spawned.");
			else
			{
				if(RaidBossSpawnManager.getInstance().getValidTemplate(spawn.getNpcId()) != null)
				{
					if(respawnTime != 0)
						RaidBossSpawnManager.getInstance().addNewSpawn(spawn, true, null);
				}
				else
					SpawnTable.getInstance().addNewSpawn(spawn, respawnTime != 0, activeChar);

				spawn.init();
				if(respawnTime == 0)
					spawn.stopRespawn();

				activeChar.sendMessage("Created " + template.name + " on " + target.getObjectId() + ".");

				logGM.info(activeChar.toFullString() + " " + "Created " + template.name + " on " + target.getObjectId());
			}
		}
		catch(Exception e)
		{
			activeChar.sendMessage("Target is not ingame.");
		}
	}
}