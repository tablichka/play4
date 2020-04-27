package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.CursedWeaponsManager;
import ru.l2gw.gameserver.model.CursedWeapon;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminCursedWeapons extends AdminBase
{
	private static final AdminCommandDescription[] ADMIN_COMMANDS = 
			{ 
					new AdminCommandDescription("admin_cw_info", null),
					new AdminCommandDescription("admin_cw_remove", "usage: //cw_remove <id>"),
					new AdminCommandDescription("admin_cw_goto", "usage: //cw_goto <id>"),
					new AdminCommandDescription("admin_cw_reload", null),
					new AdminCommandDescription("admin_cw_add", "usage: //cw_add <id>") 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}
		
		CursedWeaponsManager cwm = CursedWeaponsManager.getInstance();
		int id = 0;

		if(command.equals("admin_cw_info"))
		{
			activeChar.sendMessage("======= Cursed Weapons: =======");
			for(CursedWeapon cw : cwm.getCursedWeapons())
			{
				activeChar.sendMessage("> " + cw.getName() + " (" + cw.getItemId() + ")");
				if(cw.isActivated())
				{
					L2Player pl = cw.getPlayer();
					activeChar.sendMessage("  Player holding: " + pl.getName());
					activeChar.sendMessage("  Player karma: " + cw.getPlayerKarma());
					activeChar.sendMessage("  Time Remaining: " + cw.getTimeLeft() / 60000 + " min.");
					activeChar.sendMessage("  Kills : " + cw.getNbKills());
				}
				else if(cw.isDropped())
				{
					activeChar.sendMessage("  Lying on the ground.");
					activeChar.sendMessage("  Time Remaining: " + cw.getTimeLeft() / 60000 + " min.");
					activeChar.sendMessage("  Kills : " + cw.getNbKills());
				}
				else
					activeChar.sendMessage("  Don't exist in the world.");
			}
			return true;
		}
		else if(command.equals("admin_cw_reload"))
		{
			cwm.reload();
			activeChar.sendMessage("Cursed weapons reloaded.");
			return true;
		}
		else
		{
			CursedWeapon cw = null;
			try
			{
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher regexp = pattern.matcher(args[0]);
				if(regexp.matches())
					id = Integer.parseInt(args[0]);
				else
				{
					args[0] = args[0].replace('_', ' ');
					for(CursedWeapon cwp : cwm.getCursedWeapons())
						if(cwp.getName().toLowerCase().contains(args[0].toLowerCase()))
						{
							id = cwp.getItemId();
							break;
						}
				}
				cw = cwm.getCursedWeapon(id);
				if(cw == null)
				{
					activeChar.sendMessage("Unknown cursed weapon ID.");
					return false;
				}
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Usage: //cw_remove|//cw_goto|//cw_add <itemid|name>");
			}

			if(cw == null)
				return false;

			if(command.equals("admin_cw_remove "))
				CursedWeaponsManager.getInstance().endOfLife(cw);
			else if(command.equals("admin_cw_goto "))
				activeChar.teleToLocation(cw.getLoc());
			else if(command.equals("admin_cw_add"))
			{
				if(cw.isActive())
					activeChar.sendMessage("This cursed weapon is already active.");
				else
				{
					L2Object target = activeChar.getTarget();
					if(target != null && target.isPlayer())
					{
						L2Player player = (L2Player) target;

						cwm.activate(player, player.getInventory().addItem("AdminCursedWeapons", id, 1, activeChar, null));
						cwm.showUsageTime(player, cw);
					}
				}
			}
			else
				activeChar.sendMessage("Unknown command.");
		}
		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}