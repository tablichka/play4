package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;

import java.io.File;
import java.io.FileInputStream;

/**
 * This class handles following admin commands: - help path = shows
 * /data/html/admin/path file to char, should not be used by GM's directly
 */
public class AdminServer extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_server", null), 
					new AdminCommandDescription("admin_gc", null), 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_server"))
			try
			{
				showHelpPage(activeChar, args[0]);
			}
			catch(Exception e)
			{
				// case of empty filename
			}
		else if(command.startsWith("admin_gc"))
		{
			try
			{
				System.gc();
				Thread.sleep(1000L);
				System.gc();
				Thread.sleep(1000L);
				System.gc();
			}
			catch(Exception e)
			{}
			activeChar.sendMessage("OK! - garbage collector called.");
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	// FIXME: implement method to send html to player in L2Player directly
	// PUBLIC & STATIC so other classes from package can include it directly
	public static void showHelpPage(L2Player targetChar, String filename)
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
			// problem with adminserver is ignored
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