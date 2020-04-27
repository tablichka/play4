package commands.admin;

import javolution.text.TextBuilder;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.model.Inventory;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.InventoryUpdate;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;

public class AdminEnchant extends AdminBase
{
	private static final AdminCommandDescription[] ADMIN_COMMANDS =
			{
					new AdminCommandDescription("admin_seteh", null),// 6
					new AdminCommandDescription("admin_setec", null),// 10
					new AdminCommandDescription("admin_seteg", null),// 9
					new AdminCommandDescription("admin_setel", null),// 11
					new AdminCommandDescription("admin_seteb", null),// 12
					new AdminCommandDescription("admin_setew", null),// 7
					new AdminCommandDescription("admin_setes", null),// 8
					new AdminCommandDescription("admin_setle", null),// 1
					new AdminCommandDescription("admin_setre", null),// 2
					new AdminCommandDescription("admin_setlf", null),// 4
					new AdminCommandDescription("admin_setrf", null),// 5
					new AdminCommandDescription("admin_seten", null),// 3
					new AdminCommandDescription("admin_setun", null),// 0
					new AdminCommandDescription("admin_enchant", null)
			};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_enchant"))
		{
			if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
			{
				Functions.sendSysMessage(activeChar, "Access denied.");
				return false;
			}

			showMainPage(activeChar);
		}
		else
		{
			int armorType = -1;

			if(command.equals("admin_seteh"))
				armorType = Inventory.PAPERDOLL_HEAD;
			else if(command.equals("admin_setec"))
				armorType = Inventory.PAPERDOLL_CHEST;
			else if(command.equals("admin_seteg"))
				armorType = Inventory.PAPERDOLL_GLOVES;
			else if(command.equals("admin_seteb"))
				armorType = Inventory.PAPERDOLL_FEET;
			else if(command.equals("admin_setel"))
				armorType = Inventory.PAPERDOLL_LEGS;
			else if(command.equals("admin_setew"))
				armorType = Inventory.PAPERDOLL_RHAND;
			else if(command.equals("admin_setes"))
				armorType = Inventory.PAPERDOLL_LHAND;
			else if(command.equals("admin_setle"))
				armorType = Inventory.PAPERDOLL_LEAR;
			else if(command.equals("admin_setre"))
				armorType = Inventory.PAPERDOLL_REAR;
			else if(command.equals("admin_setlf"))
				armorType = Inventory.PAPERDOLL_LFINGER;
			else if(command.equals("admin_setrf"))
				armorType = Inventory.PAPERDOLL_RFINGER;
			else if(command.equals("admin_seten"))
				armorType = Inventory.PAPERDOLL_NECK;
			else if(command.equals("admin_setun"))
				armorType = Inventory.PAPERDOLL_UNDER;

			if(armorType != -1)
				try
				{
					int ench = Integer.parseInt(args[0]);

					L2Player target = activeChar.getTargetPlayer();
					if(target == null)
						target = activeChar;

					if(!AdminTemplateManager.checkCommand(command, activeChar, target, ench, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					// check value
					if(ench < 0 || ench > 65535)
						activeChar.sendMessage("You must set the enchant level to be between 0-65535.");
					else
						setEnchant(activeChar, target, ench, armorType);
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					activeChar.sendMessage("Please specify a new enchant value.");
				}
				catch(NumberFormatException e)
				{
					activeChar.sendMessage("Please specify a valid new enchant value.");
				}

			// show the enchant menu after an action
			showMainPage(activeChar);
		}

		return true;
	}

	private void setEnchant(L2Player activeChar, L2Player target, int ench, int armorType)
	{
		// now we need to find the equipped weapon of the targeted character...
		int curEnchant = 0; // display purposes only
		L2ItemInstance itemInstance = null;

		// only attempt to enchant if there is a weapon equipped
		L2ItemInstance parmorInstance = target.getInventory().getPaperdollItem(armorType);
		if(parmorInstance != null && parmorInstance.getEquipSlot() == armorType)
			itemInstance = parmorInstance;
		else
		{
			// for bows and double handed weapons
			parmorInstance = target.getInventory().getPaperdollItem(Inventory.PAPERDOLL_LRHAND);
			if(parmorInstance != null && parmorInstance.getEquipSlot() == Inventory.PAPERDOLL_LRHAND)
				itemInstance = parmorInstance;
		}

		if(itemInstance != null)
		{
			curEnchant = itemInstance.getEnchantLevel();

			// set enchant value
			itemInstance.changeEnchantLevel("AdminEnchant", ench, activeChar, target);
			target.getInventory().refreshItemListeners(itemInstance);

			// send packets
			InventoryUpdate iu = new InventoryUpdate();
			iu.addModifiedItem(itemInstance);
			target.sendPacket(iu);
			target.broadcastUserInfo(true);

			// informations
			activeChar.sendMessage("Changed enchantment of " + target.getName() + "'s " + itemInstance.getItem().getName() + " from " + curEnchant + " to " + ench + ".");
			target.sendMessage("Admin has changed the enchantment of your " + itemInstance.getItem().getName() + " from " + curEnchant + " to " + ench + ".");

			logGM.info(activeChar.toFullString() + " " + activeChar + " change the enchantment of " + target.getName() + "'s " + itemInstance.getItem().getName() + " from " + curEnchant + " to " + ench + ".");
		}
	}

	public void showMainPage(L2Player activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		TextBuilder replyMSG = new TextBuilder("<html><body>");
		replyMSG.append("<center><table width=260><tr><td width=40>");
		replyMSG.append("<button value=\"Main\" action=\"bypass -h admin_admin\" width=45 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\">");
		replyMSG.append("</td><td width=180>");
		replyMSG.append("<center>Enchant Equip</center>");
		replyMSG.append("</td><td width=40>");
		replyMSG.append("</td></tr></table></center><br>");
		replyMSG.append("<center><table width=270><tr><td>");
		replyMSG.append("<button value=\"Underwear\" action=\"bypass -h admin_setun $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Helmet\" action=\"bypass -h admin_seteh $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Cloak\" action=\"bypass -h admin_setba $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Mask\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Necklace\" action=\"bypass -h admin_seten $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr></table>");
		replyMSG.append("</center><center><table width=270><tr><td>");
		replyMSG.append("<button value=\"Weapon\" action=\"bypass -h admin_setew $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Chest\" action=\"bypass -h admin_setec $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Shield\" action=\"bypass -h admin_setes $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Earring\" action=\"bypass -h admin_setre $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Earring\" action=\"bypass -h admin_setle $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr></table>");
		replyMSG.append("</center><center><table width=270><tr><td>");
		replyMSG.append("<button value=\"Gloves\" action=\"bypass -h admin_seteg $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Leggings\" action=\"bypass -h admin_setel $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Boots\" action=\"bypass -h admin_seteb $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Ring\" action=\"bypass -h admin_setrf $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td><td>");
		replyMSG.append("<button value=\"Ring\" action=\"bypass -h admin_setlf $menu_command\" width=50 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr></table>");
		replyMSG.append("</center><br>");
		replyMSG.append("<center>[Enchant 0-65535]</center>");
		replyMSG.append("<center><edit var=\"menu_command\" width=100 height=15></center><br>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}