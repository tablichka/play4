package commands.admin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

public class AdminNochannel extends AdminBase
{
	private static final Log banChatLog = LogFactory.getLog("banchat");
	private static AdminCommandDescription[] _adminCommands = {new AdminCommandDescription("admin_nochannel", "usage: //banchat <player> [min] [\"reason\"]")};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(args.length > 0)
		{
			String player = args[0];
			L2Player plr = L2ObjectsStorage.getPlayer(player);

			if(plr != null)
			{
				int timeval = 30; // if no args, then 30 min default.
				String reason = "не указана"; // if no args, then "не указана" default.

				if(args.length > 1)
				{
					String time = args[1];
					timeval = Integer.parseInt(time);
				}

				if(args.length > 2)
				{
					reason = args[2];
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, plr, timeval, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				Announcements sys = new Announcements();
				if(timeval == 0)
				{
					if(Config.MAT_ANNOUNCE)
						if(Config.MAT_ANNOUNCE_NICK)
							sys.announceToAll(activeChar.getName() + " снял бан чата с игрока " + plr.getName() + ".");
						else
							sys.announceToAll("С игрока " + plr.getName() + " снят бан чата.");
					activeChar.sendMessage("Вы сняли бан чата с игрока " + plr.getName() + ".");
					banChatLog.info(activeChar.toFullString() + " снял бан чата с игрока " + plr.getName());
				}
				else if(timeval < 0)
				{
					if(Config.MAT_ANNOUNCE)
						if(Config.MAT_ANNOUNCE_NICK)
							sys.announceToAll(activeChar.getName() + " забанил чат игроку " + plr.getName() + " на бессрочный период, причина: " + reason + ".");
						else
							sys.announceToAll("Забанен чат игроку " + plr.getName() + " на бессрочный период, причина: " + reason + ".");
					activeChar.sendMessage("Вы забанили чат игроку " + plr.getName() + " на бессрочный период, причина: " + reason + ".");
					banChatLog.info(activeChar.toFullString() + " забанил чат игроку " + plr.getName() + " на бессрочный период, причина: " + reason);
				}
				else
				{
					if(Config.MAT_ANNOUNCE)
						if(Config.MAT_ANNOUNCE_NICK)
							sys.announceToAll(activeChar.getName() + " забанил чат игроку " + plr.getName() + " на " + timeval + " минут, причина: " + reason + ".");
						else
							sys.announceToAll("Забанен чат игроку " + plr.getName() + " на " + timeval + " минут, причина: " + reason + ".");
					activeChar.sendMessage("Вы забанили чат игроку " + plr.getName() + " на " + timeval + " минут, причина: " + reason + ".");
					banChatLog.info(activeChar.toFullString() + " забанил чат игроку " + plr.getName() + " на " + timeval + " минут, причина: " + reason);
				}
				updateNoChannel(plr, timeval);
			}
			else
				activeChar.sendMessage("Игрок " + player + " не найден.");
		}
		else
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

	private void updateNoChannel(L2Player player, int time)
	{
		player.updateNoChannel(time * 60000);
		if(time == 0)
			player.sendMessage(new CustomMessage("common.ChatUnBanned", player));
		else if(time > 0)
			player.sendMessage(new CustomMessage("common.ChatBanned", player).addNumber(time));
		else
			player.sendMessage(new CustomMessage("common.ChatBannedPermanently", player));
	}
}