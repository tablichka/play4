package commands.admin;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.MapRegionTable;

/**
 * This class handles following admin commands: - admin = shows menu
 */
public class AdminAdmin extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_admin", "usage: //admin"),
					new AdminCommandDescription("admin_play_sounds", "show sounds"),
					new AdminCommandDescription("admin_play_sound", "usage: //play_sound sound_name"),
					new AdminCommandDescription("admin_silence", "usage: //silence (turn on/off refuse all mode)"),
					new AdminCommandDescription("admin_show_html", "usage: //show_html file.htm (show help page)"),
					new AdminCommandDescription("admin_shout", "usage: //shout on (turn on off shot voice in current region)"),
					new AdminCommandDescription("admin_getobjid", "usage: //getobjid (get target object id)")
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player player)
	{
		if(!AdminTemplateManager.checkCommand(command, player, player, null, null, null))
			return false;

		if(command.equalsIgnoreCase("admin_admin"))
			showHelpPage(player, "admin.htm");

		if(command.equalsIgnoreCase("admin_play_sounds"))
			AdminHelpPage.showHelpPage(player, "songs/songs.htm");
		else if(command.equalsIgnoreCase("admin_play_sounds"))
			try
			{
				AdminHelpPage.showHelpPage(player, "songs/songs" + args[0] + ".htm");
			}
			catch(StringIndexOutOfBoundsException e)
			{
			}
		else if(command.equals("admin_play_sound"))
			try
			{
				playAdminSound(player, args[0]);
			}
			catch(StringIndexOutOfBoundsException e)
			{
			}
		else if(command.equals("admin_silence"))
			if(player.getMessageRefusal()) // already in message refusal
			// mode
			{
				player.unsetVar("gm_silence");
				player.setMessageRefusal(false);
				player.sendPacket(new SystemMessage(SystemMessage.MESSAGE_ACCEPTANCE_MODE));
			}
			else
			{
				if(Config.SAVE_GM_EFFECTS)
					player.setVar("gm_silence", "true");
				player.setMessageRefusal(true);
				player.sendPacket(new SystemMessage(SystemMessage.MESSAGE_REFUSAL_MODE));
			}
		else if(command.equals("admin_show_html"))
		{
			try
			{
				String html = args[0];
				if(html != null)
					AdminHelpPage.showHelpPage(player, html);
				else
					player.sendMessage("Html page not found");
			}
			catch(Exception npe)
			{
				player.sendMessage("Html page not found");
			}
		}
		else if(command.equals("admin_shout"))
		{
			try
			{
				if("on".equals(args[0]))
				{
					player.sendMessage("Restriction for region " + Config.SHOUT_OFF_ON_REGION + " is cleared");
					Config.SHOUT_OFF_ON_REGION = -1;
				}
			}
			catch(Exception e)
			{
				Config.SHOUT_OFF_ON_REGION = MapRegionTable.getInstance().getMapRegion(player.getX(), player.getY());
				player.sendMessage("Shout is turned off at region " + Config.SHOUT_OFF_ON_REGION);
			}
		}
		else if(command.equals("admin_getobjid"))
		{
			if(player.getTarget() == null)
				player.sendMessage("Need target for this command");
			else
				player.sendMessage("ObjId you target=" + player.getTarget().getObjectId());
		}

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	public void playAdminSound(L2Player player, String sound)
	{
		player.broadcastPacket(new PlaySound(sound));
		AdminHelpPage.showHelpPage(player, "admin.htm");
		player.sendMessage("Playing " + sound + ".");
	}
}