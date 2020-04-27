package commands.admin;

import quests.global.Hellbound;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

/**
 * Admin Command Handler for Hellbound
 */
public class AdminHellbound extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_hb_info", null), 
					new AdminCommandDescription("admin_hb_addpoints", null), 
					new AdminCommandDescription("admin_hb_setpoints", null),
					new AdminCommandDescription("admin_hb_setstage", null) 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		int hbStage = ServerVariables.getInt("hb_stage", 0);
		long hbPoints = Hellbound.getPoints();

		String val = "";
		if(args.length > 0)
			val = args[0];

		if(command.equals("admin_hb_info"))
		{
			activeChar.sendMessage("Hellbound Stage is: " + hbStage);
			activeChar.sendMessage("Hellbound Trust Level is : " + hbPoints);
		}
		else if(command.equals("admin_hb_addpoints"))
		{
			try
			{
				long points = Long.parseLong(val);
				Hellbound.addPoints(points);
				activeChar.sendMessage(points + " added to Hellbound Trust Level.");
			}
			catch(NumberFormatException e)
			{
				activeChar.sendMessage("Command usage is //hp_addpoints <number>");
				return false;
			}
		}
		else if(command.equals("admin_hb_setpoints"))
		{

		}
		else if(command.equals("admin_hb_setstage"))
		{
			try
			{
				byte stage  = Byte.parseByte(val);
				Hellbound.setStage(stage);
				activeChar.sendMessage("Hellbound Stage set to " + stage + ".");
			}
			catch(NumberFormatException e)
			{
				activeChar.sendMessage("Command usage is //hp_setstage <number>");
				return false;
			}

		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}