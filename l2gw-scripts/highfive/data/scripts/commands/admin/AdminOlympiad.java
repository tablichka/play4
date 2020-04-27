package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.templates.StatsSet;

public class AdminOlympiad extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_olysave", null),
					new AdminCommandDescription("admin_manualhero", null),
					new AdminCommandDescription("admin_olyend", null),
					new AdminCommandDescription("admin_olycalcend", null),
					new AdminCommandDescription("admin_olyvalidend", null),
					new AdminCommandDescription("admin_olychangeplayer", null),
					new AdminCommandDescription("admin_olyview", null)
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_olysave"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			Olympiad.saveNobleData();
			Olympiad.saveProperties();
			activeChar.sendMessage("Olympiad data saved.");
		}
		else if(command.equals("admin_manualhero"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			try
			{
				Olympiad.manualSelectHeroes();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			activeChar.sendMessage("Heroes formed.");
		}
		else if(command.equals("admin_olyend"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			Olympiad.manualOlympEnd();
			activeChar.sendMessage("Olympiad period ended.");
		}
		else if(command.equals("admin_olyvalidend"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			Olympiad.manualValidationEnd();
			activeChar.sendMessage("Olympiad validation period ended.");
		}
		else if(command.equals("admin_olycalcend"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			Olympiad.manualCalculateEnd();
			activeChar.sendMessage("Olympiad  period ended.");
		}
		else if(command.equals("admin_olychangeplayer"))
		{
			if(activeChar.getTarget() == null || !activeChar.getTarget().isPlayer() || args.length < 1)
			{
				activeChar.sendMessage("Usage: //olychangeplayer <add_points> [wins] [loos]");
				activeChar.sendMessage("Player must be in target");
				return true;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, activeChar.getTargetPlayer(), null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			int objId = activeChar.getTarget().getObjectId();
			int points = 0;
			int wins = 0;
			int loos = 0;

			try
			{
				if(args.length > 0)
					points = Integer.parseInt(args[0]);
				if(args.length > 1)
					wins = Integer.parseInt(args[1]);
				if(args.length > 2)
					loos = Integer.parseInt(args[2]);
			}
			catch(Exception e)
			{ }

			if(Olympiad.manualChangeNobleStat(objId, points, wins, loos))
				activeChar.sendMessage(activeChar.getTarget() + " add points: " + points + " wins: " + wins + " loos: " + loos);
			else
				activeChar.sendMessage("Olymp stat change filed for " + activeChar.getTarget());

			logGM.info(activeChar.toFullString() + " change olympiad add points: " + points + " wins: " + wins + " loos: " + loos + " to " + activeChar.getTarget());
		}
		else if(command.equals("admin_olyview"))
		{
			if(activeChar.getTarget() == null || !activeChar.getTarget().isPlayer())
			{
				activeChar.sendMessage("Usage: //olyvew <player in target>");
				return true;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, activeChar.getTargetPlayer(), null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			StatsSet noble = Olympiad.getNoblesData((L2Player) activeChar.getTarget());
			if(noble != null)
				activeChar.sendMessage("Olymp stat for: " + activeChar.getTarget() + " points: " + noble.getInteger("points") + " wins: " + noble.getInteger("wins") + " loos: " + noble.getInteger("loos") + " prev_points: " + noble.getInteger("prev_points"));
			else
				activeChar.sendMessage("No noble data for: " + activeChar.getTarget());
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}