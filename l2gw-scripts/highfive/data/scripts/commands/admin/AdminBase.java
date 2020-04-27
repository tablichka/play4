package commands.admin;

import javolution.text.TextBuilder;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.handler.IAdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.util.Files;

/**
 * @author: rage
 * @date: 15.03.12 14:57
 */
public abstract class AdminBase implements IAdminCommandHandler, ScriptFile
{
	@Override
	public void onLoad()
	{
		AdminCommandHandler.getInstance().registerAdminCommandHandler(this);
	}

	@Override
	public void onReload()
	{
		AdminCommandHandler.getInstance().unregisterAdminCommandHandler(this);
	}

	public void onShutdown()
	{}

	protected static void showHelpPage(L2Player targetChar, String filename)
	{
		String content = Files.read("data/html/admin/" + filename, targetChar);

		if(filename.startsWith("help/")) // Шаблон для Help'а
		{
			content = content.replaceFirst("<html><body>", "");
			content = content.replaceFirst("</body></html>", "");

			TextBuilder tb = TextBuilder.newInstance();
			tb.append("<html><body><center>");
			tb.append("<font color=\"LEVEL\">l2gw help Page</font>");
			tb.append("<img src=\"L2UI.SquareBlank\" width=260 height=4>");
			tb.append("<img src=\"L2UI.SquareWhite\" width=260 height=1>");
			tb.append("<img src=\"L2UI.SquareBlank\" width=260 height=4></center>");
			tb.append("<br>");
			tb.append("<br>");
			tb.append(content);
			tb.append("<br>");
			tb.append("<br>");
			tb.append("<img src=\"L2UI.SquareBlank\" width=260 height=4>");
			tb.append("<img src=\"L2UI.SquareWhite\" width=260 height=1>");
			tb.append("<img src=\"L2UI.SquareBlank\" width=260 height=4>");
			tb.append("</center></body></html>");
			content = tb.toString();
			TextBuilder.recycle(tb);
		}

		if(content == null)
		{
			Functions.sendSysMessage(targetChar, "Not found filename: " + filename);
			return;
		}

		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		adminReply.setHtml(content);
		targetChar.sendPacket(adminReply);
	}
}