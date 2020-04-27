package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;

public class AdminHelpPage extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_help", "Usage: //help command_name") };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_help"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}	

			for(String help : args)
			{
				if(!help.startsWith("admin_"))
					help = "admin_" + help;

				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(help));
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