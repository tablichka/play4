package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.Say2;
import ru.l2gw.gameserver.tables.GmListTable;

public class AdminGmChat extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_gmchat", "usage: //gmchat <message>"),
					new AdminCommandDescription("admin_snoop", "usage: //snoop player must be in target")
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_gmchat"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			String text = fullCommand.substring(13);
			Say2 cs = new Say2(0, 9, activeChar.getName(), text);
			GmListTable.broadcastToGMs(cs);
		}
		else if(command.equals("admin_snoop"))
		{
			L2Player target = activeChar.getTargetPlayer();
			if(target == null)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			target.addSnooper(activeChar);
			activeChar.addSnooped(target);
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}