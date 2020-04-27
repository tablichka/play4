package services.multisell;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.model.Inventory;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.base.MultiSellEntry;
import ru.l2gw.gameserver.model.base.MultiSellHandler;
import ru.l2gw.gameserver.model.base.MultiSellIngredient;
import ru.l2gw.gameserver.model.base.MultiSellListContainer;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.templates.L2Armor;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.gameserver.templates.L2Item.Grade;
import ru.l2gw.gameserver.templates.L2Weapon;
import ru.l2gw.gameserver.templates.L2Weapon.WeaponType;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 10.12.2010 16:24:37
 */
public class ExchangeService extends Functions implements ScriptFile, MultiSellHandler
{
	private static int[] lists = new int[]{9999, 9998, 9997, 9996, 9995, 9994, 9993, 99944, 99966};

	@Override
	public void onLoad()
	{
		L2Multisell.getInstance().registerMultiSellHandler(this);
	}

	@Override
	public void onReload()
	{
		L2Multisell.getInstance().unregisterMultiSellHandler(this);
	}

	@Override
	public void onShutdown()
	{
	}

	@Override
	public int[] getMultiSellId()
	{
		return lists;
	}

	@Override
	public MultiSellListContainer generateMultiSellList(int listId, L2Player player, double taxRate)
	{
		MultiSellListContainer list = null;

		// Hardcoded сервис - обмена пушек на равноценные
		GArray<L2ItemInstance> _items;
		//от D до Ы84(опционально)
		if(listId == 9999)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, true, true);

			final Inventory inv = player.getInventory();
			_items = new GArray<L2ItemInstance>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem() instanceof L2Weapon && ((L2Weapon) item.getItem()).getAdditionalName().isEmpty() // Менять можно только обычные предметы
						&& item.canBeTraded(player) // универсальная проверка
						&& checkItem(item.getItem(), false, Config.SERVICE_FREE_EXCHANGE_MAX_WEAPON_PRICE, false, false)
						&& item.getItem().getType2() == L2Item.TYPE2_WEAPON //
						)
					_items.add(item);

			for(final L2ItemInstance weaponToSell : _items)
				for(L2Weapon weaponToBuy : ItemTable.getInstance().getAllWeapons())
					if(weaponToBuy.getAdditionalName().isEmpty() // Менять можно только обычные предметы
							&& checkItem(weaponToBuy, true, false, false)
							&& weaponToBuy.getItemId() != weaponToSell.getItemId() //
							&& weaponToBuy.getType2() == L2Item.TYPE2_WEAPON //
							&& weaponToBuy.getItemType() == WeaponType.DUAL == (weaponToSell.getItem().getItemType() == WeaponType.DUAL) //
							&& weaponToSell.getItem().getCrystalType() == weaponToBuy.getCrystalType() //
							&& weaponToSell.getItem().getCrystalCount() == weaponToBuy.getCrystalCount() //
							)
						list.entries.add(addToList(list, weaponToSell, weaponToBuy));
		}

		// Hardcoded сервис - обмена пушек с доплатой за SERVICE_UPGRADE_PAY
		//от D до Ы84(опционально)
		else if(listId == 9998)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, false, true);

			final Inventory inv = player.getInventory();
			_items = new GArray<L2ItemInstance>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem() instanceof L2Weapon && ((L2Weapon) item.getItem()).getAdditionalName().isEmpty() // Менять можно только обычные предметы
						&& checkItem(item.getItem(), false, Config.SERVICE_UPGRADE_MAX_WEAPON_PRICE, false, false)
						&& item.canBeTraded(player) // универсальная проверка
						&& item.getItem().getType2() == L2Item.TYPE2_WEAPON //
						)
					_items.add(item);

			for(final L2ItemInstance weaponToSell : _items)
				for(final L2Weapon weaponToBuy : ItemTable.getInstance().getAllWeapons())
					if(weaponToBuy.getAdditionalName().isEmpty() // Менять можно только обычные предметы
							&& checkItem(weaponToBuy, true, Config.SERVICE_UPGRADE_MAX_WEAPON_PRICE, false, false)
							&& weaponToBuy.getType2() == L2Item.TYPE2_WEAPON //
							&& weaponToBuy.getItemType() == WeaponType.DUAL == (weaponToSell.getItem().getItemType() == WeaponType.DUAL) //
							&& weaponToBuy.getCrystalType().ordinal() >= weaponToSell.getItem().getCrystalType().ordinal() //
							&& weaponToSell.getItem().getReferencePrice() < weaponToBuy.getReferencePrice() //
							&& weaponToSell.getReferencePrice() * 1.7 > weaponToBuy.getReferencePrice() //
							)
					{
						MultiSellEntry possibleEntry = addToList(list, weaponToSell, weaponToBuy);
						possibleEntry.addIngredient(new MultiSellIngredient(Config.SERVICE_UPGRADE_PAY, Config.SERVICE_UPGRADE_PAY_COUNT > 0 ? Config.SERVICE_UPGRADE_PAY_COUNT : (long) ((weaponToBuy.getReferencePrice() - weaponToSell.getReferencePrice()) * Config.SERVICE_UPGRADE_PAY_COUNT_MOD), null));
						list.entries.add(possibleEntry);
					}
		}

		// Hardcoded  - обмен вещей на кристаллы
		else if(listId == 9997)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, true, false);

			final Inventory inv = player.getInventory();
			for(final L2ItemInstance item : inv.getItems())
				if(!item.isStackable() && item.getItem().isCrystallizable() && item.getItem().getCrystalType() != Grade.NONE && item.getItem().getCrystalCount() > 0 && !item.isShadowItem() && !item.isTemporalItem() && !item.isEquipped() && (item.getCustomFlags() & L2ItemInstance.FLAG_NO_CRYSTALLIZE) != L2ItemInstance.FLAG_NO_CRYSTALLIZE)
				{
					final L2Item crystal = ItemTable.getInstance().getTemplate(item.getItem().getCrystalType().cry);
					final int entry = new int[]{item.getItemId(), item.getEnchantLevel()}.hashCode();
					MultiSellEntry possibleEntry = new MultiSellEntry(entry, crystal.getItemId(), item.getItem().getCrystalCount(), null);
					possibleEntry.addIngredient(new MultiSellIngredient(item.getItemId(), 1, item));
					possibleEntry.addIngredient(new MultiSellIngredient((short) 57, (int) (item.getItem().getCrystalCount() * crystal.getReferencePrice() * 0.05), null));
					list.entries.add(possibleEntry);
				}
		}

		// Hardcoded сервис - обмен пушек на равноценные за колы
		//от D до Ы84(опционально)
		else if(listId == 9996)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, true, false);

			final Inventory inv = player.getInventory();
			_items = new GArray<L2ItemInstance>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem().getType2() == L2Item.TYPE2_WEAPON
						&& checkItem(item.getItem(), false, Config.SERVICE_EXCHANGE_MAX_WEAPON_PRICE, false, true)
						&& item.canBeTraded(player))// универсальная проверка
					_items.add(item);

			for(final L2ItemInstance weaponToSell : _items)
				for(L2Weapon weaponToBuy : ItemTable.getInstance().getAllWeapons())
					if(weaponToBuy.getItemId() != weaponToSell.getItemId()
							&& (weaponToBuy.getItemType() == WeaponType.DUAL) == (weaponToSell.getItem().getItemType() == WeaponType.DUAL)
							&& weaponToSell.getItem().getCrystalType() == weaponToBuy.getCrystalType()
							&& weaponToSell.getItem().getCrystalCount() == weaponToBuy.getCrystalCount()
							&& checkItem(weaponToBuy, true, false, true)
							&& !weaponToBuy.getName().contains("-") && !weaponToBuy.getName().contains("Monster"))
					{
						MultiSellEntry possibleEntry = addToList(list, weaponToSell, weaponToBuy);
						possibleEntry.addIngredient(new MultiSellIngredient(Config.SERVICE_EXCHANGE_WEAPON_PAY, Config.SERVICE_EXCHANGE_WEAPON_PAY_COUNT, null));
						list.entries.add(possibleEntry);
					}
		}

		// Hardcoded сервис - обмена рарных пушек на равноценные за колы
		//от D до Ы84(опционально)
		else if(listId == 9995)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, true, false);

			final Inventory inv = player.getInventory();
			_items = new GArray<L2ItemInstance>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem().getType2() == L2Item.TYPE2_WEAPON
						&& checkItem(item.getItem(), false, Config.SERVICE_EXCHANGE_RAR_WEAPON_PRICE, true, true)
						&& item.canBeTraded(player)) // универсальная проверка
					_items.add(item);

			for(final L2ItemInstance weaponToSell : _items)
				for(L2Weapon weaponToBuy : ItemTable.getInstance().getAllWeapons())
					if(weaponToBuy.getItemId() != weaponToSell.getItemId()
							&& weaponToSell.getItem().getCrystalType() == weaponToBuy.getCrystalType()
							&& weaponToSell.getItem().getCrystalCount() == weaponToBuy.getCrystalCount()
							&& weaponToBuy.getItemType() == WeaponType.DUAL == (weaponToSell.getItem().getItemType() == WeaponType.DUAL)
							&& checkItem(weaponToBuy, true, true, true) && !weaponToBuy.getName().contains("Monster"))
					{
						MultiSellEntry possibleEntry = addToList(list, weaponToSell, weaponToBuy);
						possibleEntry.addIngredient(new MultiSellIngredient(Config.SERVICE_EXCHANGE_RAR_WEAPON_PAY, Config.SERVICE_EXCHANGE_RAR_WEAPON_PAY_COUNT, null));
						list.entries.add(possibleEntry);
					}
		}
		// Hardcoded  - обмен арморов на равноценные за колы
		else if(Config.SERVICE_EXCHANGE_ARMOR && listId == 9994)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, true, false);

			final Inventory inv = player.getInventory();
			_items = new GArray<L2ItemInstance>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem().getType2() == L2Item.TYPE2_SHIELD_ARMOR
						&& checkItem(item.getItem(), false, false, false)
						&& item.canBeTraded(player) // универсальная проверка
						&& item.getItem().getBodyPart() != L2Item.SLOT_BACK
						&& item.getItem().getBodyPart() != L2Item.SLOT_BELT
						&& item.getItem().getBodyPart() != L2Item.SLOT_FORMAL_WEAR
						&& item.getItem().getBodyPart() != L2Item.SLOT_L_BRACELET
						&& item.getItem().getBodyPart() != L2Item.SLOT_R_BRACELET
						)
					_items.add(item);

			for(final L2ItemInstance armorToSell : _items)
				for(L2Armor armorToBuy : ItemTable.getInstance().getAllArmors())
					if(armorToBuy.getItemId() != armorToSell.getItemId()
							&& armorToSell.getItem().getCrystalType() == armorToBuy.getCrystalType()
							&& armorToSell.getItem().getCrystalCount() == armorToBuy.getCrystalCount()
							&& checkItem(armorToBuy, true, false, false)
							&& !armorToBuy.getName().contains("-")
							&& armorToSell.getItem().getBodyPart() == armorToBuy.getBodyPart())
					{
						MultiSellEntry possibleEntry = addToList(list, armorToSell, armorToBuy);
						possibleEntry.addIngredient(new MultiSellIngredient(Config.SERVICE_EXCHANGE_ARMOR_PAY, Config.SERVICE_EXCHANGE_ARMOR_PAY_COUNT, null));
						list.entries.add(possibleEntry);
					}
		}
		// Hardcoded  - обмен рарных арморов на равноценные за колы
		else if(Config.SERVICE_EXCHANGE_RAR_ARMOR && listId == 9993)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			setListParam(list, false, true, false);

			final Inventory inv = player.getInventory();
			_items = new GArray<L2ItemInstance>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem().getType2() == L2Item.TYPE2_SHIELD_ARMOR
						&& checkItem(item.getItem(), false, true, false)
						&& item.canBeTraded(player) // универсальная проверка
						&& item.getItem().getBodyPart() != L2Item.SLOT_BACK
						&& item.getItem().getBodyPart() != L2Item.SLOT_BELT
						&& item.getItem().getBodyPart() != L2Item.SLOT_FORMAL_WEAR
						&& item.getItem().getBodyPart() != L2Item.SLOT_L_BRACELET
						&& item.getItem().getBodyPart() != L2Item.SLOT_R_BRACELET
						)
					_items.add(item);

			for(final L2ItemInstance armorToSell : _items)
				for(L2Armor armorToBuy : ItemTable.getInstance().getAllArmors())
					if(armorToBuy.getItemId() != armorToSell.getItemId()
							&& armorToSell.getItem().getCrystalType() == armorToBuy.getCrystalType()
							&& armorToSell.getItem().getCrystalCount() == armorToBuy.getCrystalCount()
							&& checkItem(armorToBuy, true, true, false)
							&& armorToBuy.getBodyPart() == armorToBuy.getBodyPart())
					{
						MultiSellEntry possibleEntry = addToList(list, armorToSell, armorToBuy);
						possibleEntry.addIngredient(new MultiSellIngredient(Config.SERVICE_EXCHANGE_RAR_ARMOR_PAY, Config.SERVICE_EXCHANGE_RAR_ARMOR_PAY_COUNT, null));
						list.entries.add(possibleEntry);
					}
		}
		// Hardcoded  - обмен арморов на равноценные с минимальной заточкой 40 на любой с заточкой 20
		else if(listId == 99944 && Config.SERVICE_EXCHANGE_ENCHANTED_ARMOR)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			list.setShowAll(false);
			list.setKeepEnchant(true);
			list.setCheckEnchantIngredient(true);
			list.setNoTax(false);
			list.community = true;

			final Inventory inv = player.getInventory();
			_items = new GArray<>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem().getType2() == L2Item.TYPE2_SHIELD_ARMOR
						//&& checkItem(item.getItem(), false, false, false)
						&& !item.getItem().isPvP()
						&& !item.isEquipped()
						&& item.canBeTraded(player) // универсальная проверка
						&& item.getItem().getBodyPart() != L2Item.SLOT_BACK
						&& item.getItem().getBodyPart() != L2Item.SLOT_BELT
						&& item.getItem().getBodyPart() != L2Item.SLOT_FORMAL_WEAR
						&& item.getItem().getBodyPart() != L2Item.SLOT_L_BRACELET
						&& item.getItem().getBodyPart() != L2Item.SLOT_R_BRACELET
						&& item.getItem().getCrystalType().externalOrdinal == 5
						&& item.getEnchantLevel() >= Config.SERVICE_EXCHANGE_ENCHANTED_ARMOR_MIN_ENCHANT)
					_items.add(item);

			for(final L2ItemInstance armorToSell : _items)
				for(L2Armor armorToBuy : ItemTable.getInstance().getAllArmors())
					if(armorToBuy.getItemId() != armorToSell.getItemId()
						&& armorToBuy.getCrystalType().externalOrdinal == 5
						&& !armorToBuy.isPvP()
						&& armorToBuy.getCrystalCount() > 0
						&& (!armorToBuy.isMasterwork() || armorToSell.getItem().isMasterwork())
						//&& checkItem(armorToBuy, true, false, false)
						&& !armorToBuy.getName().contains("-")
						&& !armorToBuy.getName().startsWith("Sealed")
						&& (armorToSell.getItem().getBodyPart() == armorToBuy.getBodyPart() || armorToSell.getBodyPart() == L2Item.SLOT_FULL_ARMOR && armorToBuy.getBodyPart() == L2Item.SLOT_CHEST))
					{
						MultiSellEntry possibleEntry = addToList(list, armorToSell, armorToBuy, Config.SERVICE_EXCHANGE_ENCHANTED_ARMOR_PENALTY);
						list.entries.add(possibleEntry);
					}
		}
		// Hardcoded сервис - обмена S+ пушек с минимальной заточкой 40
		// На любые S+ пушки с понижением заточки на 20
		else if(listId == 99966 && Config.SERVICE_EXCHANGE_ENCHANTED_WEAPON)
		{
			list = new MultiSellListContainer();
			list.setListId(listId);
			list.setShowAll(false);
			list.setKeepEnchant(true);
			list.setCheckEnchantIngredient(true);
			list.setNoTax(false);
			list.community = true;

			final Inventory inv = player.getInventory();
			_items = new GArray<>();
			for(final L2ItemInstance item : inv.getItems())
				if(item.getItem().getType2() == L2Item.TYPE2_WEAPON
						&& item.getItem().getCrystalType().externalOrdinal == 5
						&& !item.isEquipped()
						&& item.getEnchantLevel() >= Config.SERVICE_EXCHANGE_ENCHANTED_WEAPON_MIN_ENCHANT
						&& !item.getItem().isPvP()
						&& !item.getName().contains("Monster")
						&& (item.getItem().isHaveSa() || item.getItemType() == WeaponType.DUAL || item.getItemType() == WeaponType.DUALDAGGER)
						//&& checkItem(item.getItem(), false, false, true)
						&& item.canBeTraded(player))// универсальная проверка
					_items.add(item);

			for(final L2ItemInstance weaponToSell : _items)
				for(L2Weapon weaponToBuy : ItemTable.getInstance().getAllWeapons())
					if(weaponToBuy.getItemId() != weaponToSell.getItemId()
						&& weaponToBuy.getCrystalType().externalOrdinal == 5
						&& (weaponToBuy.isHaveSa() || weaponToBuy.getItemType() == WeaponType.DUAL || weaponToBuy.getItemType() == WeaponType.DUALDAGGER)
						&& !weaponToBuy.isPvP()
						&& weaponToBuy.getCrystalCount() > 0
						&& (!weaponToBuy.isMasterwork() || weaponToSell.getItem().isMasterwork())
						//&& checkItem(weaponToBuy, true, false, true)
						&& (weaponToBuy.isMasterwork() || !weaponToBuy.getName().contains("-")))
					{
						MultiSellEntry possibleEntry = addToList(list, weaponToSell, weaponToBuy, Config.SERVICE_EXCHANGE_ENCHANTED_WEAPON_PENALTY);
						list.entries.add(possibleEntry);
					}
		}

		return list;
	}

	private boolean checkItem(L2Item item, boolean buyedItem, long maxPrice, boolean rar, boolean noSA)
	{
		return item.getReferencePrice() <= maxPrice && checkItem(item, buyedItem, rar, noSA);
	}

	private boolean checkItem(L2Item item, boolean buyedItem, boolean rar, boolean whithSA)
	{
		boolean addToList = false;
		if(!item.isStandartItem() && !item.isPvP() && (!whithSA ? !item.isHaveSa() : (item.isHaveSa() || (item.getItemType() == WeaponType.DUAL))))
		{
			if(!buyedItem && item.isMasterwork() == rar)
				addToList = !item.isStackable();
			else if(!item.isShadowItem()
					&& !item.isTemporal()
					&& item.isTradeable()
					&& item.isMasterwork() == rar
					&& item.getCrystalCount() > 0
					&& item.getCrystalType() != Grade.NONE)
				addToList = true;
		}

		return addToList;
	}

	private void setListParam(MultiSellListContainer list, boolean showAll, boolean keepEnchant, boolean noTax)
	{
		list.setShowAll(showAll);
		list.setKeepEnchant(keepEnchant);
		list.setNoTax(noTax);
		list.community = true;
	}

	private MultiSellEntry addToList(MultiSellListContainer list, L2ItemInstance itemToSell, L2Item itemToBuy)
	{
		final int entry = new int[]{itemToSell.getItemId(), itemToBuy.getItemId(), itemToSell.getEnchantLevel()}.hashCode();
		MultiSellEntry possibleEntry = new MultiSellEntry(entry, itemToBuy.getItemId(), 1, list.getKeepEnchant() ? itemToSell : null);
		possibleEntry.addIngredient(new MultiSellIngredient(itemToSell.getItemId(), 1, itemToSell));

		return possibleEntry;
	}

	private MultiSellEntry addToList(MultiSellListContainer list,L2ItemInstance itemToSell, L2Item itemToBuy, int decreaseEnchant)
	{
		final int entry = new int[] { itemToSell.getItemId(), itemToBuy.getItemId(), itemToSell.getEnchantLevel() }.hashCode();
		MultiSellEntry possibleEntry = new MultiSellEntry(entry, itemToBuy.getItemId(), 1, itemToSell);
		possibleEntry.getProduction().get(0).setItemEnchant(Math.max(0, itemToSell.getEnchantLevel() - decreaseEnchant));
		possibleEntry.addIngredient(new MultiSellIngredient(itemToSell.getItemId(), 1, itemToSell));

		return possibleEntry;
	}
}
