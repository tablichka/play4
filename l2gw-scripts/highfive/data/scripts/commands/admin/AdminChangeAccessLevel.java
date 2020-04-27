package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

public class AdminChangeAccessLevel extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_changelvl", "usage: //changelvl [name] <access level>")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_changelvl"))
			try
			{
				if(args.length == 1)
				{
					int lvl = Integer.parseInt(args[0]);
					if(activeChar.getTarget() instanceof L2Player)
						((L2Player) activeChar.getTarget()).setAccessLevel(lvl);
				}
				else if(args.length == 2)
				{
					int lvl = Integer.parseInt(args[1]);
					L2Player player = L2ObjectsStorage.getPlayer(args[0]);
					if(player != null)
						player.setAccessLevel(lvl);
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}