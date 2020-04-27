package commands.admin;

import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage.ScreenMessageAlign;

/**
 * This class handles following admin commands: - announce text = announces text
 * to all players - list_announcements = show menu - reload_announcements =
 * reloads announcements from txt file - announce_announcements = announce all
 * stored announcements to all players - add_announcement text = adds text to
 * startup announcements - del_announcement id = deletes announcement with
 * respective id
 */
public class AdminAnnouncements extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_list_announcements", null),
					new AdminCommandDescription("admin_announce_announcements", null),
					new AdminCommandDescription("admin_add_announcement", null),
					new AdminCommandDescription("admin_del_announcement", null),
					new AdminCommandDescription("admin_announce", "usage: //a announce message"),
					new AdminCommandDescription("admin_a", "usage: //a announce message"),
					new AdminCommandDescription("admin_announce_menu", null),
					new AdminCommandDescription("admin_crit_announce", null),
					new AdminCommandDescription("admin_toscreen", null)
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			return false;

		if(command.equals("admin_list_announcements"))
			Announcements.getInstance().listAnnouncements(activeChar);
		else if(command.equals("admin_announce_menu"))
		{
			Announcements sys = new Announcements();
			sys.handleAnnounce(fullCommand, 20);
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		else if(command.equals("admin_announce_announcements"))
		{
			for(L2Player player : L2ObjectsStorage.getAllPlayers())
				Announcements.getInstance().showAnnouncements(player);
			Announcements.getInstance().listAnnouncements(activeChar);
		}
		else if(command.equals("admin_add_announcement"))
		{
			// FIXME the player can send only 16 chars (if you try to send more it
			// sends null), remove this function or not?
			if(args.length < 1)
				return false;
			try
			{
				String val = fullCommand.substring(23);
				Announcements.getInstance().addAnnouncement(val);
				Announcements.getInstance().listAnnouncements(activeChar);
			}
			catch(StringIndexOutOfBoundsException e)
			{
			}
		}
		else if(command.equals("admin_del_announcement"))
		{
			if(args.length < 1)
				return false;
			try
			{
				int val = new Integer(fullCommand.substring(23));
				Announcements.getInstance().delAnnouncement(val);
				Announcements.getInstance().listAnnouncements(activeChar);
			}
			catch(StringIndexOutOfBoundsException e)
			{
			}
		}
		// Command is admin announce
		else if(command.equals("admin_announce"))
		{
			// Call method from another class
			Announcements sys = new Announcements();
			sys.handleAnnounce(fullCommand, 15);
		}
		else if(command.equals("admin_a"))
		{
			// Call method from another class
			Announcements sys = new Announcements();
			sys.handleAnnounce(fullCommand, 8);
		}
		else if(command.equals("admin_crit_announce") || command.equals("admin_c"))
		{
			// Call method from another class
			Announcements sys = new Announcements();
			sys.handleAnnounce(fullCommand, 20, Say2C.CRITICAL_ANNOUNCEMENT);
		}
		else if(command.equals("admin_toscreen") || command.equals("admin_s"))
		{
			if(args.length < 2)
				return false;
			String _text = fullCommand.replaceFirst("admin_toscreen ", "");
			int _time = 3000 + _text.length() * 100; // 3 секунды + 100мс на символ
			boolean _font_big = _text.length() < 64;

			ExShowScreenMessage sm = new ExShowScreenMessage(_text, _time, ScreenMessageAlign.TOP_CENTER, _font_big);
			for(L2Player player : L2ObjectsStorage.getAllPlayers())
				player.sendPacket(sm);
		}

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}