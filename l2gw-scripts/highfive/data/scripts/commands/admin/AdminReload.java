package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.TradeController;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.CubicManager;
import ru.l2gw.gameserver.instancemanager.ProductManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.*;
import ru.l2gw.util.Files;
import ru.l2gw.util.Strings;

import java.util.StringTokenizer;

public class AdminReload extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{
			new AdminCommandDescription("admin_reload_multisell", null),
			new AdminCommandDescription("admin_reload_announcements", null),
			new AdminCommandDescription("admin_reload_gmaccess", null),
			new AdminCommandDescription("admin_reload_htm", null),
			new AdminCommandDescription("admin_reload_qs", null),
			new AdminCommandDescription("admin_reload_qs_help", null),
			new AdminCommandDescription("admin_reload_loc", null),
			new AdminCommandDescription("admin_reload_skills", null),
			new AdminCommandDescription("admin_reload_npc", null),
			new AdminCommandDescription("admin_reload_spawn", null),
			new AdminCommandDescription("admin_reload_fish", null),
			new AdminCommandDescription("admin_reload_abuse", null),
			new AdminCommandDescription("admin_reload_translit", null),
			new AdminCommandDescription("admin_reload_shops", null),
			new AdminCommandDescription("admin_reload_zone", null),
			new AdminCommandDescription("admin_reload_protect", null),
			new AdminCommandDescription("admin_reload_cfg", null),
			new AdminCommandDescription("admin_reload_cubics", null),
			new AdminCommandDescription("admin_set", null),
			new AdminCommandDescription("admin_reload_geo", null),
			new AdminCommandDescription("admin_reload", null),
			new AdminCommandDescription("admin_reload_npcmaker", null),
			new AdminCommandDescription("admin_reload_productdata", null)
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{

		// грубый Хак - приводим команды к старому виду
		if(command.equals("admin_reload") && args.length > 0)
		{
			command += "_" + args[0];
		}
		
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}
		
		if(command.startsWith("admin_reload_multisell"))
		{
			try
			{
				L2Multisell.getInstance().reload();
			}
			catch(Exception e)
			{
				return false;
			}
			activeChar.sendMessage("Multisell list reloaded!");
		}
		else if(command.startsWith("admin_reload_gmaccess"))
		{
			try
			{
				Config.loadGMAccess();
			}
			catch(Exception e)
			{
				return false;
			}
			activeChar.sendMessage("GMAccess reloaded!");
		}
		else if(command.startsWith("admin_reload_cubics"))
		{
			try
			{
				CubicManager.reload();
				activeChar.sendMessage("Cubics reloaded!");
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Error: " + e);
				return false;
			}
		}
		else if(command.startsWith("admin_reload_htm"))
		{
			Files.cacheClean();
			activeChar.sendMessage("HTML cache clearned.");
		}
		else if(command.equals("admin_reload_announcements"))
		{
			Announcements.getInstance().loadAnnouncements();
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		if(command.startsWith("admin_reload_qs"))
		{
			if(command.endsWith("all"))
				for(L2Player p : L2ObjectsStorage.getAllPlayers())
					reloadQuestStates(p);
			else
			{
				L2Object t = activeChar.getTarget();

				if(t != null && t.isPlayer())
				{
					L2Player p = (L2Player) t;
					reloadQuestStates(p);
				}
				else
					reloadQuestStates(activeChar);
			}
			return true;
		}
		else if(command.startsWith("admin_reload_qs_help"))
		{
			activeChar.sendMessage("");
			activeChar.sendMessage("Quest Help:");
			activeChar.sendMessage("reload_qs_help - This Message.");
			activeChar.sendMessage("reload_qs <selected target> - reload all quest states for target.");
			activeChar.sendMessage("reload_qs <no target or target is not player> - reload quests for self.");
			activeChar.sendMessage("reload_qs all - reload quests for all players in world.");
			activeChar.sendMessage("");

			return true;
		}
		else if(command.startsWith("admin_reload_loc"))
		{
			TerritoryTable.getInstance().reloadData();
//			ZoneManager.getInstance().reload();
			GmListTable.broadcastMessageToGMs("Locations and zones reloaded.");
		}
		else if(command.startsWith("admin_reload_skills"))
		{
			SkillTable.getInstance().reload();
			GmListTable.broadcastMessageToGMs("Skill table reloaded by " + activeChar.getName() + ".");
			System.out.println("Skill table reloaded by " + activeChar.getName() + ".");
		}
		else if(command.startsWith("admin_reload_npcmaker"))
		{
			SpawnTable.getInstance().reloadNpcMakers();
			activeChar.sendMessage("npc makers reloaded.");
		}
		else if(command.startsWith("admin_reload_npc"))
		{
			NpcTable.getInstance().reloadAllNpc();
			GmListTable.broadcastMessageToGMs("Npc table reloaded.");
		}
		else if(command.startsWith("admin_reload_spawn"))
		{
			SpawnTable.getInstance().reloadAll();
			GmListTable.broadcastMessageToGMs("All npc respawned.");
		}
		else if(command.startsWith("admin_reload_fish"))
		{
			FishTable.getInstance().reload();
			GmListTable.broadcastMessageToGMs("Fish table reloaded.");
		}
		else if(command.startsWith("admin_reload_abuse"))
		{
			Config.abuseLoad();
			GmListTable.broadcastMessageToGMs("Abuse reloaded.");
		}
		else if(command.startsWith("admin_reload_translit"))
		{
			Strings.reload();
			GmListTable.broadcastMessageToGMs("Translit reloaded.");
		}
		else if(command.startsWith("admin_reload_shops"))
		{
			TradeController.reload();
			GmListTable.broadcastMessageToGMs("Shops reloaded.");
		}
		else if(command.startsWith("admin_reload_zone"))
		{
			try
			{
				ZoneManager.getInstance().reloadZones();
				for(L2Player player : L2ObjectsStorage.getAllPlayers())
				{
					if(player != null)
					{
						player.clearInZones();
						player.revalidateZones(true);
					}
				}
				activeChar.sendMessage("Zones reloaded.");
			}
			catch(Exception ex)
			{
				activeChar.sendMessage("Cannot reload zone, use right type");
				ex.printStackTrace();
			}
		}
		else if(command.startsWith("admin_reload_geo"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			if(st.hasMoreTokens())
			{
				int x = Integer.parseInt(st.nextToken());
				if(st.hasMoreTokens())
				{
					int y = Integer.parseInt(st.nextToken());
					GeoEngine.reloadGeo(x, y);
					activeChar.sendMessage("Geodata reloaded.");
				}
			}
		}
		else if(command.startsWith("admin_reload_cfg"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			if(st.hasMoreTokens())
			{
				try
				{
					Config.reload(st.nextToken());
					activeChar.sendMessage("Reload config was successful");
				}
				catch(Exception e)
				{
					activeChar.sendMessage("Reload config was faild");
				}
			}
		}
		else if(command.startsWith("admin_set"))
		{
			StringTokenizer st = new StringTokenizer(command);
			st.nextToken();
			if(st.hasMoreTokens())
			{
				String key = st.nextToken();
				if(st.hasMoreTokens())
				{
					String value = st.nextToken();
					try
					{
						Object old = Config.setField(Config.class, key, value);
						Functions.sendSysMessage(activeChar, "Set: " + key + "=" + old + " => " + value);
					}
					catch(Exception e)
					{
						Functions.sendSysMessage(activeChar, "Set " + key + "=" + value + " failed: " + e.getMessage());
					}
				}
			}
		}
		else if(command.startsWith("admin_reload_productdata"))
		{
			ProductManager.reloadProductData();
			GmListTable.broadcastMessageToGMs("Productdata reloaded.");
		}
		return true;
	}

	private void reloadQuestStates(L2Player p)
	{
		for(QuestState qs : p.getAllQuestsStates())
			p.delQuestState(qs.getQuest().getName());
		Quest.playerEnter(p);
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}