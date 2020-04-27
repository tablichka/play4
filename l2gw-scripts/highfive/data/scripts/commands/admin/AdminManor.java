package commands.admin;

import javolution.text.TextBuilder;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.CastleManorManager;
import ru.l2gw.gameserver.instancemanager.CastleManorManager.CropProcure;
import ru.l2gw.gameserver.instancemanager.CastleManorManager.SeedProduction;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.Castle;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;

/**
 * Admin comand handler for Manor System
 * This class handles following admin commands:
 * - manor_info = shows info about current manor state
 * - manor_approve = approves settings for the next manor period
 * - manor_setnext = changes manor settings to the next day's
 * - manor_reset castle = resets all manor data for specified castle (or all)
 * - manor_setmaintenance = sets manor system under maintenance mode
 * - manor_save = saves all manor data into database
 * - manor_disable = disables manor system
 */
public class AdminManor extends AdminBase
{
	private static final AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_manor", null), 
					new AdminCommandDescription("admin_manor_reset", null), 
					new AdminCommandDescription("admin_manor_save", null),
					new AdminCommandDescription("admin_manor_disable", null) 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}
		
		if(command.equals("admin_manor"))
			showMainPage(activeChar);
		else if(command.equals("admin_manor_reset"))
		{
			int castleId = 0;
			try
			{
				castleId = Integer.parseInt(args[0]);
			}
			catch(Exception e)
			{}

			if(castleId > 0)
			{
				Castle castle = ResidenceManager.getInstance().getCastleById(castleId);
				if(castle.isCastle)
				{
					castle.setCropProcure(new GArray<CropProcure>(), CastleManorManager.PERIOD_CURRENT);
					castle.setCropProcure(new GArray<CropProcure>(), CastleManorManager.PERIOD_NEXT);
					castle.setSeedProduction(new GArray<SeedProduction>(), CastleManorManager.PERIOD_CURRENT);
					castle.setSeedProduction(new GArray<SeedProduction>(), CastleManorManager.PERIOD_NEXT);
					if(Config.MANOR_SAVE_ALL_ACTIONS)
					{
						castle.saveCropData();
						castle.saveSeedData();
					}
					activeChar.sendMessage("Manor data for " + castle.getName() + " was nulled");
				}
				else
					activeChar.sendMessage("Wrong id");
			}
			else
			{
				for(Castle castle : ResidenceManager.getInstance().getCastleList())
				{
					castle.setCropProcure(new GArray<CropProcure>(), CastleManorManager.PERIOD_CURRENT);
					castle.setCropProcure(new GArray<CropProcure>(), CastleManorManager.PERIOD_NEXT);
					castle.setSeedProduction(new GArray<SeedProduction>(), CastleManorManager.PERIOD_CURRENT);
					castle.setSeedProduction(new GArray<SeedProduction>(), CastleManorManager.PERIOD_NEXT);
					if(Config.MANOR_SAVE_ALL_ACTIONS)
					{
						castle.saveCropData();
						castle.saveSeedData();
					}
				}
				activeChar.sendMessage("Manor data was nulled");
			}
			showMainPage(activeChar);
		}
		else if(command.equals("admin_manor_save"))
		{
			CastleManorManager.getInstance().save();
			activeChar.sendMessage("Manor System: all data saved");
			showMainPage(activeChar);
		}
		else if(command.equals("admin_manor_disable"))
		{
			boolean mode = CastleManorManager.getInstance().isDisabled();
			CastleManorManager.getInstance().setDisabled(!mode);
			if(mode)
				activeChar.sendMessage("Manor System: enabled");
			else
				activeChar.sendMessage("Manor System: disabled");
			showMainPage(activeChar);
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	private void showMainPage(L2Player activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		TextBuilder replyMSG = new TextBuilder("<html><body>");

		replyMSG.append("<center><font color=\"LEVEL\"> [Manor System] </font></center><br>");
		replyMSG.append("<table width=\"100%\">");
		replyMSG.append("<tr><td>Disabled: " + (CastleManorManager.getInstance().isDisabled() ? "yes" : "no") + "</td>");
		replyMSG.append("<td>Under Maintenance: " + (CastleManorManager.getInstance().isUnderMaintenance() ? "yes" : "no") + "</td></tr>");
		replyMSG.append("<tr><td>Approved: " + (ServerVariables.getBool("ManorApproved") ? "yes" : "no") + "</td></tr>");
		replyMSG.append("</table>");

		replyMSG.append("<center><table>");
		replyMSG.append("<tr><td><button value=\"" + (CastleManorManager.getInstance().isDisabled() ? "Enable" : "Disable") + "\" action=\"bypass -h admin_manor_disable\" width=110 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Reset\" action=\"bypass -h admin_manor_reset\" width=110 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td><button value=\"Refresh\" action=\"bypass -h admin_manor\" width=110 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Back\" action=\"bypass -h admin_admin\" width=110 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table></center>");

		replyMSG.append("<br><center>Castle Information:<table width=\"100%\">");
		replyMSG.append("<tr><td></td><td>Current Period</td><td>Next Period</td></tr>");

		for(Castle c : ResidenceManager.getInstance().getCastleList())
			replyMSG.append("<tr><td>" + c.getName() + "</td>" + "<td>" + c.getManorCost(CastleManorManager.PERIOD_CURRENT) + "a</td>" + "<td>" + c.getManorCost(CastleManorManager.PERIOD_NEXT) + "a</td>" + "</tr>");

		replyMSG.append("</table><br>");

		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}
}