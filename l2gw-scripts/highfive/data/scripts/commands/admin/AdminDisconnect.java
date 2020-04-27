package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

public class AdminDisconnect extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_disconnect", "usage: //disconnect [name]"),
					new AdminCommandDescription("admin_kick", "usage: //kick [name]")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		L2Player target = null;
		if(args.length > 0)
		{
			target = L2ObjectsStorage.getPlayer(args[0]);
			if(target == null)
			{
				Functions.sendSysMessage(activeChar, "Player: " + args[0] + " not found.");
				return false;
			}
		}
		
		target = activeChar.getTarget() instanceof L2Player ? (L2Player) activeChar.getTarget() : null;
		
		if(target == null)
		{
			Functions.sendSysMessage(activeChar, "No target.");
			return false;
		}
		
		if(target == activeChar)
		{
			Functions.sendSysMessage(activeChar, "Cannot disconnect your self.");
			return false;
		}
		
		if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		Functions.sendSysMessage(activeChar, "Character " + target.getName() + " disconnected from server.");

		if(target.isInOfflineMode())
			target.setOfflineMode(false);

		target.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_BEEN_DISCONNECTED_FROM_THE_SERVER_PLEASE_LOGIN_AGAIN));
		final L2Player player = target;

		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable(){
			public void run()
			{
				player.logout(false, false, true);
			}
		}, 500);

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}