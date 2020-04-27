package commands.admin;

import javolution.text.TextBuilder;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.AdminForgePacket;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;

public class AdminPForge extends AdminBase
{
	private static final AdminCommandDescription[] ADMIN_COMMANDS = 
			{ 
					new AdminCommandDescription("admin_forge", null), 
					new AdminCommandDescription("admin_forge2", null), 
					new AdminCommandDescription("admin_forge3", "uagse: //forge3 [broadcast] <format> <args...>")
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_forge"))
			showMainPage(activeChar);
		else if(command.equals("admin_forge2"))
		{
			try
			{
				showPage2(activeChar, args[0]);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				activeChar.sendMessage("Usage: //forge2 format");
			}
		}
		else if(command.equals("admin_forge3"))
		{
			try
			{
				String format = args[0];
				boolean broadcast = false;
				if(format.toLowerCase().equals("broadcast"))
				{
					format = args[1];
					broadcast = true;
				}
				AdminForgePacket sp = new AdminForgePacket();
				byte[] bytes = format.getBytes();
				for(int i = 0; i < format.length(); i++)
				{
					String val = args[i + (broadcast ? 2 : 1)];
					if(val.toLowerCase().equals("$objid"))
						val = String.valueOf(activeChar.getObjectId());
					else if(val.toLowerCase().equals("$tobjid"))
						val = String.valueOf(activeChar.getTarget().getObjectId());
					else if(val.toLowerCase().equals("$bobjid"))
					{
						if(activeChar.getVehicle() != null)
							val = String.valueOf(activeChar.getVehicle().getObjectId());
					}
					else if(val.toLowerCase().equals("$clanid"))
						val = String.valueOf(activeChar.getCharId());
					else if(val.toLowerCase().equals("$allyid"))
						val = String.valueOf(activeChar.getAllyId());
					else if(val.toLowerCase().equals("$tclanid"))
						val = String.valueOf(((L2Player) activeChar.getTarget()).getCharId());
					else if(val.toLowerCase().equals("$tallyid"))
						val = String.valueOf(((L2Player) activeChar.getTarget()).getAllyId());
					else if(val.toLowerCase().equals("$x"))
						val = String.valueOf(activeChar.getX());
					else if(val.toLowerCase().equals("$y"))
						val = String.valueOf(activeChar.getY());
					else if(val.toLowerCase().equals("$z"))
						val = String.valueOf(activeChar.getZ());
					else if(val.toLowerCase().equals("$heading"))
						val = String.valueOf(activeChar.getHeading());
					else if(val.toLowerCase().equals("$tx"))
						val = String.valueOf(activeChar.getTarget().getX());
					else if(val.toLowerCase().equals("$ty"))
						val = String.valueOf(activeChar.getTarget().getY());
					else if(val.toLowerCase().equals("$tz"))
						val = String.valueOf(activeChar.getTarget().getZ());
					else if(val.toLowerCase().equals("$theading"))
						val = String.valueOf(((L2Player) activeChar.getTarget()).getHeading());

					sp.addPart(bytes[i], val);
				}
				if(broadcast)
					activeChar.broadcastPacket(sp);
				else
				{
					if(activeChar.getTarget() == null || !(activeChar.getTarget() instanceof L2Player))
						activeChar.sendPacket(sp);
					else
						((L2Player) activeChar.getTarget()).sendPacket(sp);
				}
				showPage3(activeChar, format, command);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		return true;
	}

	private void showMainPage(L2Player player)
	{
		AdminHelpPage.showHelpPage(player, "pforge_menu1.htm");
	}

	private void showPage2(L2Player player, String format)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setFile("data/html/admin/pforge_menu2.htm");
		adminReply.replace("%format%", format);

		TextBuilder replyMSG = new TextBuilder();
		for(int i = 0; i < format.length(); i++)
			replyMSG.append(format.charAt(i) + " : <edit var=\"v" + i + "\" width=100><br1>");
		adminReply.replace("%valueditors%", replyMSG.toString());
		replyMSG.clear();
		for(int i = 0; i < format.length(); i++)
			replyMSG.append(" \\$v" + i);
		adminReply.replace("%send%", replyMSG.toString());
		player.sendPacket(adminReply);
	}

	private void showPage3(L2Player player, String format, String command)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		adminReply.setFile("data/html/admin/pforge_menu3.htm");
		adminReply.replace("%format%", format);
		adminReply.replace("%command%", command);
		player.sendPacket(adminReply);
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}