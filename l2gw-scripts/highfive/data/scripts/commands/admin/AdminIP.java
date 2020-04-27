package commands.admin;

import ru.l2gw.commons.utils.StringUtil;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.loginservercon.LSConnection;
import ru.l2gw.gameserver.loginservercon.gspackets.BanIP;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;

/**
 * This class handles following admin commands:- ipbanlist - ipban, ipblock -
 * ipunban, ipunblock - ipcharban
 */
public class AdminIP extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands =
			{
					new AdminCommandDescription("admin_ipban", null),
					new AdminCommandDescription("admin_ipblock", null),
					new AdminCommandDescription("admin_ipunban", null),
					new AdminCommandDescription("admin_ipunblock", null),
					new AdminCommandDescription("admin_ipcharban", null),
					new AdminCommandDescription("admin_ipchar", null),
					new AdminCommandDescription("admin_charip", null)
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		switch(command)
		{
			case "admin_ipban":
			case "admin_ipblock":
				if(args.length < 1)
				{
					activeChar.sendMessage("Command syntax: //ipban <ip>");
					return false;
				}

				if(!validateIP(args[0]))
				{
					activeChar.sendMessage("Error: Invalid IP adress: " + args[0]);
					return false;
				}

				LSConnection.getInstance().sendPacket(new BanIP(args[0], activeChar.getName()));
				logGM.info(activeChar.toFullString() + " " + "IP " + args[0] + ", ban attempt");
				break;
			case "admin_ipcharban":
				if(args.length < 1)
				{
					activeChar.sendMessage("Command syntax: //ipcharban <char_name>");
					return false;
				}

				L2Player plr = L2ObjectsStorage.getPlayer(args[0]);

				if(plr == null)
				{
					activeChar.sendMessage("Character " + args[0] + " not found.");
					return false;
				}

				String ip = plr.getIP();
				// Проверку на валидность ip пропускаем, ибо верим серверу

				if(ip.equalsIgnoreCase("<not connected>"))
				{
					activeChar.sendMessage("Character " + args[0] + " not found.");
					return false;
				}

				LSConnection.getInstance().sendPacket(new BanIP(ip, activeChar.getName()));
				logGM.info(activeChar.toFullString() + " " + "IP " + ip + ", ban attempt");
				break;
			case "admin_ipchar":
			case "admin_charip":
				if(args.length < 1)
				{
					activeChar.sendMessage("Command syntax: //charip <char_name>");
					activeChar.sendMessage(" Gets character's IP.");
					break;
				}

				L2Player pl = L2ObjectsStorage.getPlayer(args[0]);

				if(pl == null)
				{
					activeChar.sendMessage("Character " + args[0] + " not found.");
					return false;
				}

				String ip_adr = pl.getIP();
				if(ip_adr.equalsIgnoreCase("<not connected>"))
				{
					activeChar.sendMessage("Character " + args[0] + " not found.");
					return false;
				}

				activeChar.sendMessage("Character's IP: " + ip_adr);
				break;
			case "admin_ipunban":
			case "admin_ipunblock":
				if(args.length < 1)
				{
					activeChar.sendMessage("Command syntax: //ipunban <ip>");
					return false;
				}

				if(!validateIP(args[0]))
				{
					activeChar.sendMessage("Error: Invalid IP adress: " + args[0]);
					return false;
				}

				LSConnection.getInstance().sendPacket(new BanIP(args[0], activeChar.getName()));
				logGM.info(activeChar.toFullString() + " " + "IP " + args[0] + ", ban attempt");
				break;
		}
		return true;
	}

	public boolean validateIP(String IP)
	{
		if(!StringUtil.isMatchingRegexp(IP, "[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}"))
			return false;

		// Split by dot
		IP = IP.replace(".", ",");
		String[] IP_octets = IP.split(",");

		for(String element : IP_octets)
			if(Integer.parseInt(element) > 255)
				return false;

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}