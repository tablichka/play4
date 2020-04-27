package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.TradeController;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.NpcTradeList;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.ExBuyList;
import ru.l2gw.gameserver.serverpackets.ExSellRefundList;

/**
 * This class handles following admin commands: - gmshop = shows menu - buy id =
 * shows shop with respective id
 */
public class AdminShop extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{ 
					new AdminCommandDescription("admin_buy", "usage: //buy <listId>"),
					new AdminCommandDescription("admin_gmshop", null), 
					new AdminCommandDescription("admin_tax", null) 
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.startsWith("admin_buy"))
			try
			{
				int listId = Integer.parseInt(args[0]);
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, listId, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				NpcTradeList list = TradeController.getInstance().getSellList(listId);

				if(list != null)
				{
					activeChar.setBuyListId(listId);
					activeChar.sendPacket(new ExBuyList(list, activeChar, 0));
					activeChar.sendPacket(new ExSellRefundList(activeChar));
				}

				activeChar.sendActionFailed();

				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equals("admin_gmshop"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			AdminHelpPage.showHelpPage(activeChar, "gmshops.htm");
		}
		else if(command.equals("admin_tax"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			activeChar.sendMessage("TaxSum: " + L2World.getTaxSum());
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}