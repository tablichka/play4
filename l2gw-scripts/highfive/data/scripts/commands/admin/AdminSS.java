package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.SevenSigns;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminSS extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{ 
					new AdminCommandDescription("admin_ssq_change", null), 
					new AdminCommandDescription("admin_ssq_time", null) 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_ssq_change"))
		{
			if(args.length > 1)
			{
				int period = Integer.parseInt(args[0]);
				int minutes = Integer.parseInt(args[1]);
				SevenSigns.getInstance().changePeriod(period, minutes * 60);
			}
			else if(args.length > 0)
			{
				int period = Integer.parseInt(args[0]);
				SevenSigns.getInstance().changePeriod(period);
			}
			else
				SevenSigns.getInstance().changePeriod();
		}
		if(command.equals("admin_ssq_time"))
		{
			if(args.length > 0)
			{
				int time = Integer.parseInt(args[0]);
				SevenSigns.getInstance().setTimeToNextPeriodChange(time);
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