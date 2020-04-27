package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

/**
 * This class handles following admin commands: - target name = sets player with
 * respective name as target
 */
public class AdminTarget extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_target", "usage: //target <player>") };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(args.length < 1)
		{
			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}

		L2Player target = L2ObjectsStorage.getPlayer(args[0]);
		if(target != null)
			target.onAction(activeChar, false);
		else
			Functions.sendSysMessage(activeChar, "Target: " + args[0] + " not found.");

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}