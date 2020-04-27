package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.L2Augmentation;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.templates.L2Armor;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.gameserver.templates.L2Weapon;

public class AdminCreateItem extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{
					new AdminCommandDescription("admin_itemcreate", null),
					new AdminCommandDescription("admin_create_item", "usage: //create_item <itemId> [amount]"),
					new AdminCommandDescription("admin_summon", "usage: //summon <itemId> [amount]"),
					new AdminCommandDescription("admin_summon_attribute", "//summon_attribute [item_id] [type1(0~5)] [value1] [type2(0~5)] [value2] [type3(0~5)] [value3] [enchant3] [option normal] [option random]")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_itemcreate"))
			AdminHelpPage.showHelpPage(activeChar, "itemcreation.htm");
		else if(command.equals("admin_summon_attribute"))
		{
			try
			{
				int itemId = Integer.parseInt(args[0]);

				if(Config.DISABLE_CREATION_ID_LIST.contains(itemId) && !AdminTemplateManager.checkBoolean("forceCreate", activeChar))
				{
					Functions.sendSysMessage(activeChar, "You cannot create this item!");
					return false;
				}
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, itemId, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				L2Item item = ItemTable.getInstance().getTemplate(itemId);
				if(item == null)
				{
					Functions.sendSysMessage(activeChar, "Item id: " + itemId + " does not exists!");
					return false;
				}

				if(item.isStackable())
				{
					Functions.sendSysMessage(activeChar, "You cannot create stackable item with attribute");
					return false;
				}

				int a[] = new int[3];
				int v[] = new int[3];

				a[0] = Integer.parseInt(args[1]);
				v[0] = Integer.parseInt(args[2]);
				a[1] = Integer.parseInt(args[3]);
				v[1] = Integer.parseInt(args[4]);
				a[2] = Integer.parseInt(args[5]);
				v[2] = Integer.parseInt(args[6]);

				if(item instanceof L2Armor)
				{
					for(int i = 0; i < a.length; i++)
					{
						for(int j = 0; j < a.length; j++)
						{
							if(v[j] > 0 && i != j && (a[i] == a[j] || a[i] % 2 == a[j] % 2))
							{
								Functions.sendSysMessage(activeChar, "invalid attribute type; opposite type cannot be set");
								return false;
							}
						}
					}
				}

				int enchant = Integer.parseInt(args[7]);
				int aug1 = Integer.parseInt(args[8]);
				int aug2 = Integer.parseInt(args[9]);

				L2ItemInstance created = ItemTable.getInstance().createItem("AdminCreate", itemId, 1, activeChar, null);
				if(item instanceof L2Weapon && v[0] > 0)
				{
					created.changeAttributeElement("AdminCreate", a[0], v[0], activeChar, null);
				}
				else
				{
					for(int i = 0; i < a.length; i++)
					{
						if(v[i] > 0)
						{
							created.changeAttributeElement("AdminCreate", a[i], v[i], activeChar, null);
						}
					}
				}

				if(enchant > 0)
				{
					created.changeEnchantLevel("AdminCreate", enchant, activeChar, null);
				}

				if(aug1 > 0 && aug2 > 0)
				{
					created.setAugmentation(new L2Augmentation(created, (aug2 << 16) + aug1, 0, true));
				}

				activeChar.addItem("AdminCreate", created, null, true);
				return true;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_summon") || command.equals("admin_create_item"))
		{
			try
			{
				if(args.length > 1)
				{
					short idval = Short.parseShort(args[0]);
					long numval = Long.parseLong(args[1]);

					if(!AdminTemplateManager.checkCommand(command, activeChar, null, idval, numval, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					return createItem(activeChar, idval, numval);
				}
				else if(args.length > 0)
				{
					short idval = Short.parseShort(args[0]);

					if(!AdminTemplateManager.checkCommand(command, activeChar, null, idval, 1, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					return createItem(activeChar, idval, 1);
				}
			}
			catch(NumberFormatException nfe)
			{
				Functions.sendSysMessage(activeChar, "Specify a valid number.");
				return false;
			}
			catch(StringIndexOutOfBoundsException e)
			{
				Functions.sendSysMessage(activeChar, "Can't create this item.");
				return false;
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}

			if(!command.equals("admin_summon"))
				AdminHelpPage.showHelpPage(activeChar, "itemcreation.htm");
		}
		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	private boolean createItem(L2Player activeChar, short id, long num)
	{
		if(Config.DISABLE_CREATION_ID_LIST.contains(id) && !AdminTemplateManager.checkBoolean("forceCreate", activeChar))
		{
			Functions.sendSysMessage(activeChar, "You cannot create this item!");
			return false;
		}

		activeChar.addItem("AdminCreate", id, num, null, true);
		activeChar.getInventory().sendItemList(true);

		logGM.info(activeChar.toFullString() + " spawned " + num + " item(s) number " + id + " in inventory");
		return true;
	}
}