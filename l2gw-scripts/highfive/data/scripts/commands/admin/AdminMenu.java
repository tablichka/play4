package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.util.Location;

/**
 * This class handles following admin commands: - handles ever admin menu
 * command
 */
public class AdminMenu extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_char_manage", null),
					new AdminCommandDescription("admin_teleport_character_to_menu", null),
					new AdminCommandDescription("admin_recall_char_menu", null),
					new AdminCommandDescription("admin_goto_char_menu", null),
					new AdminCommandDescription("admin_kick_menu", null),
					new AdminCommandDescription("admin_kill_menu", null),
					new AdminCommandDescription("admin_ban_menu", null),
					new AdminCommandDescription("admin_unban_menu", null),
					new AdminCommandDescription("admin_show_abnormals", null)
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_char_manage"))
			AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
		else if(command.equals("admin_teleport_character_to_menu"))
		{
			if(args.length > 3)
			{
				String playerName = args[0];
				L2Player player = L2ObjectsStorage.getPlayer(playerName);
				if(player != null)
					teleportCharacter(player, new Location(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3])), activeChar);
			}
			AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
		}
		else if(command.equals("admin_recall_char_menu"))
			try
			{
				String targetName = args[0];
				L2Player player = L2ObjectsStorage.getPlayer(targetName);
				teleportCharacter(player, activeChar.getLoc(), activeChar);
			}
			catch(Exception e)
			{}
		else if(command.equals("admin_goto_char_menu"))
			try
			{
				String targetName = args[0];
				L2Player player = L2ObjectsStorage.getPlayer(targetName);
				teleportToCharacter(activeChar, player);
			}
			catch(Exception e)
			{}
		else if(command.equals("admin_kill_menu"))
		{
			L2Object obj = activeChar.getTarget();
			if(args.length > 0)
			{
				String player = args[0];
				L2Player plyr = L2ObjectsStorage.getPlayer(player);
				if(plyr != null)
					activeChar.sendMessage("You kicked " + plyr.getName() + " from the game.");
				else
					activeChar.sendMessage("Player " + player + " not found in game.");
				obj = plyr;
			}
			if(obj != null && obj.isCharacter())
			{
				L2Character target = (L2Character) obj;
				target.reduceHp(target.getMaxHp() + 1, activeChar, true, false);
			}
			else
				activeChar.sendPacket(Msg.INVALID_TARGET);
			AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
		}
		else if(command.equals("admin_kick_menu"))
		{
			if(args.length > 0)
			{
				String player = args[0];
				L2Player plyr = L2ObjectsStorage.getPlayer(player);
				if(plyr != null)
					plyr.logout(false, false, true);
				if(plyr != null)
					activeChar.sendMessage("You kicked " + plyr.getName() + " from the game.");
				else
					activeChar.sendMessage("Player " + player + " not found in game.");
			}
			AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
		}
		else if(command.equals("admin_ban_menu"))
		{
			if(args.length > 0)
			{
				String player = args[0];
				L2Player plyr = L2ObjectsStorage.getPlayer(player);
				if(plyr != null)
				{
					plyr.setAccountAccesslevel(-100, "admin_ban_menu", -1);
					plyr.logout(false, false, true);
				}
			}
			AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
		}
		else if(command.equals("admin_unban_menu"))
		{
			if(args.length > 0)
			{
				String player = args[0];
				L2Player plyr = L2ObjectsStorage.getPlayer(player);
				if(plyr != null)
					plyr.setAccountAccesslevel(0, "admin_unban_menu", 0);
			}
			AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
		}
		else if(command.equals("admin_show_abnormals"))
			AdminHelpPage.showHelpPage(activeChar, "abnormals.htm");
		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	private void teleportCharacter(L2Player player, Location loc, L2Player activeChar)
	{
		if(player != null)
		{
			player.sendMessage("Admin is teleporting you.");
			player.teleToLocation(loc);
		}
		AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
	}

	private void teleportToCharacter(L2Player activeChar, L2Object target)
	{
		L2Player player;
		if(target != null && target.isPlayer())
			player = (L2Player) target;
		else
		{
			activeChar.sendPacket(Msg.INVALID_TARGET);
			return;
		}

		if(player.getObjectId() == activeChar.getObjectId())
			activeChar.sendMessage("You cannot self teleport.");
		else
		{
			activeChar.teleToLocation(player.getLoc());
			activeChar.sendMessage("You have teleported to character " + player.getName() + ".");
		}
		AdminHelpPage.showHelpPage(activeChar, "charmanage.htm");
	}
}