package services.petevolve;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2PetInstance;
import ru.l2gw.gameserver.serverpackets.InventoryUpdate;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.PetDataTable;
import ru.l2gw.gameserver.templates.L2Item;
import ru.l2gw.util.Files;

/**
 * User: darkevil
 * Date: 29.05.2008
 * Time: 23:25:50
 */
public class exchange extends Functions implements ScriptFile
{
	/** Билеты для обмена **/
	private static final int PEticketB = 7583;
	private static final int PEticketC = 7584;
	private static final int PEticketK = 7585;

	/** Дудки для вызова петов **/
	private static final int BbuffaloP = 6648;
	private static final int BcougarC = 6649;
	private static final int BkookaburraO = 6650;

	public void exch_1()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, PEticketB) >= 1)
		{
			removeItem(player, PEticketB, 1);
			addItem(player, BbuffaloP, 1);
			return;
		}

		show(Files.read("data/scripts/services/petevolve/exchange_no.htm", player), player);
	}

	public void exch_2()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, PEticketC) >= 1)
		{
			removeItem(player, PEticketC, 1);
			addItem(player, BcougarC, 1);
			return;
		}

		show(Files.read("data/scripts/services/petevolve/exchange_no.htm", player), player);
	}

	public void exch_3()
	{
		L2Player player = (L2Player) self;

		if(getItemCount(player, PEticketK) >= 1)
		{
			removeItem(player, PEticketK, 1);
			addItem(player, BkookaburraO, 1);
			return;
		}

		show(Files.read("data/scripts/services/petevolve/exchange_no.htm", player), player);
	}

	public void showBabyPetExchange()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!Config.SERVICES_EXCHANGE_BABY_PET_ENABLED)
		{
			show("Сервис отключен.", player);
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_EXCHANGE_BABY_PET_ITEM);

		String out = "";
		out += "<html><body>Вы можете в любое время обменять вашего Improved Baby пета на другой вид, без потери опыта. Пет при этом должен быть вызван.";
		out += "<br>Стоимость обмена: " + Config.SERVICES_EXCHANGE_BABY_PET_PRICE + " " + item.getName();
		out += "<br><button width=250 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h scripts_services.petevolve.exchange:exToCougar\" value=\"Обменять на Improved Cougar\">";
		out += "<br1><button width=250 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h scripts_services.petevolve.exchange:exToBuffalo\" value=\"Обменять на Improved Buffalo\">";
		out += "<br1><button width=250 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h scripts_services.petevolve.exchange:exToKookaburra\" value=\"Обменять на Improved Kookaburra\">";
		out += "</body></html>";

		show(out, player);
	}

	public void showErasePetName()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!Config.SERVICES_CHANGE_PET_NAME_ENABLED)
		{
			show("Сервис отключен.", player);
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_CHANGE_PET_NAME_ITEM);

		String out = "";
		out += "<html><body>Вы можете обнулить имя у пета, для того чтобы назначить новое. Пет при этом должен быть вызван.";
		out += "<br>Стоимость обнуления: " + Config.SERVICES_CHANGE_PET_NAME_PRICE + " " + item.getName();
		out += "<br><button width=100 height=15 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_CT1.Button_DF\" action=\"bypass -h scripts_services.petevolve.exchange:erasePetName\" value=\"Обнулить имя\">";
		out += "</body></html>";

		show(out, player);
	}

	public void erasePetName()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!Config.SERVICES_CHANGE_PET_NAME_ENABLED)
		{
			show("Сервис отключен.", player);
			return;
		}

		L2Summon pl_pet = player.getPet();
		if(pl_pet == null || !pl_pet.isPet())
		{
			show("Питомец должен быть вызван.", player);
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_CHANGE_PET_NAME_ITEM);
		L2ItemInstance pay = player.getInventory().getItemByItemId(item.getItemId());
		if(pay != null && pay.getCount() >= Config.SERVICES_CHANGE_PET_NAME_PRICE)
		{
			player.destroyItem("PetsEx", pay.getObjectId(), Config.SERVICES_CHANGE_PET_NAME_PRICE, null, true);
			pl_pet.setName(null);
			pl_pet.broadcastPetInfo();

			L2PetInstance _pet = (L2PetInstance) pl_pet;
			L2ItemInstance controlItem = _pet.getControlItem();
			if(controlItem != null)
			{
				controlItem.setCustomType2(1);
				controlItem.setPriceToSell(0);
				controlItem.updateDatabase();
				_pet.updateControlItem();
			}
			show("Имя стерто.", player);
		}
		else if(Config.SERVICES_CHANGE_PET_NAME_ITEM == 57)
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
		else
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
	}

	public void exToCougar()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!Config.SERVICES_EXCHANGE_BABY_PET_ENABLED)
		{
			show("Сервис отключен.", player);
			return;
		}

		L2Summon pl_pet = player.getPet();
		if(pl_pet == null || !(pl_pet.getNpcId() == PetDataTable.IMPROVED_BABY_BUFFALO_ID || pl_pet.getNpcId() == PetDataTable.IMPROVED_BABY_KOOKABURRA_ID))
		{
			show("Пет должен быть вызван.", player);
			return;
		}
		if(pl_pet.getInventory().getItems().length != 0)
		{
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_EXCHANGE_BABY_PET_ITEM);
		L2ItemInstance pay = player.getInventory().getItemByItemId(item.getItemId());
		if(pay != null && pay.getCount() >= Config.SERVICES_EXCHANGE_BABY_PET_PRICE)
		{
			player.destroyItem("PetsEx", pay.getObjectId(), Config.SERVICES_EXCHANGE_BABY_PET_PRICE, null, true);
			L2ItemInstance control = player.getInventory().getItemByObjectId(player.getPet().getControlItemObjId());
			control.setItemId(PetDataTable.getControlItemId(PetDataTable.IMPROVED_BABY_COUGAR_ID));
			control.updateDatabase(true);
			player.sendPacket(new InventoryUpdate().addModifiedItem(control));
			player.getPet().unSummon();
			show("Пет изменен.", player);
		}
		else if(Config.SERVICES_EXCHANGE_BABY_PET_ITEM == 57)
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
		else
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
	}

	public void exToBuffalo()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!Config.SERVICES_EXCHANGE_BABY_PET_ENABLED)
		{
			show("Сервис отключен.", player);
			return;
		}

		L2Summon pl_pet = player.getPet();
		if(pl_pet == null || !(pl_pet.getNpcId() == PetDataTable.IMPROVED_BABY_COUGAR_ID || pl_pet.getNpcId() == PetDataTable.IMPROVED_BABY_KOOKABURRA_ID))
		{
			show("Пет должен быть вызван.", player);
			return;
		}
		if(pl_pet.getInventory().getItems().length != 0)
		{
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
			return;
		}

		if(player.isMageClass())
		{
			show("Этот пет только для воинов.", player);
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_EXCHANGE_BABY_PET_ITEM);
		L2ItemInstance pay = player.getInventory().getItemByItemId(item.getItemId());
		if(pay != null && pay.getCount() >= Config.SERVICES_EXCHANGE_BABY_PET_PRICE)
		{
			player.destroyItem("PetsEx", pay.getObjectId(), Config.SERVICES_EXCHANGE_BABY_PET_PRICE, null, true);
			L2ItemInstance control = player.getInventory().getItemByObjectId(player.getPet().getControlItemObjId());
			control.setItemId(PetDataTable.getControlItemId(PetDataTable.IMPROVED_BABY_BUFFALO_ID));
			control.updateDatabase(true);
			player.sendPacket(new InventoryUpdate().addModifiedItem(control));
			player.getPet().unSummon();
			show("Пет изменен.", player);
		}
		else if(Config.SERVICES_EXCHANGE_BABY_PET_ITEM == 57)
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
		else
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
	}

	public void exToKookaburra()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		if(!Config.SERVICES_EXCHANGE_BABY_PET_ENABLED)
		{
			show("Сервис отключен.", player);
			return;
		}

		L2Summon pl_pet = player.getPet();
		if(pl_pet == null || !(pl_pet.getNpcId() == PetDataTable.IMPROVED_BABY_BUFFALO_ID || pl_pet.getNpcId() == PetDataTable.IMPROVED_BABY_COUGAR_ID))
		{
			show("Пет должен быть вызван.", player);
			return;
		}

		if(pl_pet.getInventory().getItems().length != 0)
		{
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
			return;
		}

		if(!player.isMageClass())
		{
			show("Этот пет только для магов.", player);
			return;
		}

		L2Item item = ItemTable.getInstance().getTemplate(Config.SERVICES_EXCHANGE_BABY_PET_ITEM);
		L2ItemInstance pay = player.getInventory().getItemByItemId(item.getItemId());
		if(pay != null && pay.getCount() >= Config.SERVICES_EXCHANGE_BABY_PET_PRICE)
		{
			player.destroyItem("PetsEx", pay.getObjectId(), Config.SERVICES_EXCHANGE_BABY_PET_PRICE, null, true);
			L2ItemInstance control = player.getInventory().getItemByObjectId(player.getPet().getControlItemObjId());
			control.setItemId(PetDataTable.getControlItemId(PetDataTable.BABY_KOOKABURRA_ID));
			control.updateDatabase(true);
			player.sendPacket(new InventoryUpdate().addModifiedItem(control));
			player.getPet().unSummon();
			show("Пет изменен.", player);
		}
		else if(Config.SERVICES_EXCHANGE_BABY_PET_ITEM == 57)
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
		else
			player.sendPacket(Msg.INCORRECT_ITEM_COUNT);
	}

	public static String DialogAppend_30731(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30827(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30828(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30829(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30830(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30831(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_30869(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31067(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31265(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31309(Integer val)
	{
		return getHtmlAppends(val);
	}

	public static String DialogAppend_31954(Integer val)
	{
		return getHtmlAppends(val);
	}

	private static String getHtmlAppends(Integer val)
	{
		String ret = "";
		if(val != 0)
			return ret;
		if(Config.SERVICES_CHANGE_PET_NAME_ENABLED)
			ret = "<br>[scripts_services.petevolve.exchange:showErasePetName|Обнулить имя у пета]";
		if(Config.SERVICES_EXCHANGE_BABY_PET_ENABLED)
			ret += "<br>[scripts_services.petevolve.exchange:showBabyPetExchange|Обменять Baby пета]";
		return ret;
	}

	public void onLoad()
	{}

	public void onReload()
	{}

	public void onShutdown()
	{}
}