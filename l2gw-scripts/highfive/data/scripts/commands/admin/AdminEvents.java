package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminEvents extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_events", null) };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkBoolean("eventMaster", activeChar))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.startsWith("admin_events"))
			AdminHelpPage.showHelpPage(activeChar, "events.htm");

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}