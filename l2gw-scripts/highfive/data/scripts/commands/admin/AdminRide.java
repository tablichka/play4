package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;

import java.io.File;
import java.io.FileInputStream;

/**
 * This class handles following admin commands: - help path = shows
 * /data/html/admin/path file to char, should not be used by GM's directly
 */
public class AdminRide extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {new AdminCommandDescription("admin_ride", null)};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		try
		{
			showRide(activeChar, args[0]);
		}
		catch(Exception e)
		{
			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	public static void showRide(L2Player targetChar, String filename)
	{
		File file = new File("./", "data/html/admin/" + filename);
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(file);
			byte[] raw = new byte[fis.available()];
			fis.read(raw);

			String content = new String(raw, "UTF-8");

			NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

			adminReply.setHtml(content);
			targetChar.sendPacket(adminReply);
		}
		catch(Exception e)
		{
			// problem with adminride is ignored
		}
		finally
		{
			try
			{
				if(fis != null)
					fis.close();
			}
			catch(Exception e1)
			{
				// problems ignored
			}
		}
	}
}