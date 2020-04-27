package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.CameraMode;
import ru.l2gw.gameserver.serverpackets.SpecialCamera;

public class AdminCamera extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_freelook", "usage: //freelook <mode> - 1 or 0"),
					new AdminCommandDescription("admin_cinematic", "usage: //cinematic <id> <dist> <yaw> <pitch> <time> <duration>")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_freelook"))
		{
			if(args.length < 1)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			int mode = Integer.parseInt(args[0]);
			if(mode == 1)
			{
				activeChar.setInvisible(true);
				activeChar.setIsInvul(true);
				activeChar.setNoChannel(-1);
				activeChar.setFlying(true);
			}
			else
			{
				activeChar.setInvisible(false);
				activeChar.setIsInvul(false);
				activeChar.setNoChannel(0);
				activeChar.setFlying(false);
			}
			activeChar.sendPacket(new CameraMode(mode));
			return true;
		}
		else if(command.equals("admin_cinematic"))
		{
			if(args.length < 6)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
			int id = Integer.parseInt(args[0]);
			int dist = Integer.parseInt(args[1]);
			int yaw = Integer.parseInt(args[2]);
			int pitch = Integer.parseInt(args[3]);
			int time = Integer.parseInt(args[4]);
			int duration = Integer.parseInt(args[5]);

			activeChar.sendPacket(new SpecialCamera(id, dist, yaw, pitch, time, duration));
		}
		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}
