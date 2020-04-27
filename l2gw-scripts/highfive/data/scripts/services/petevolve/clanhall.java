package services.petevolve;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.tables.PetDataTable;
import ru.l2gw.util.Files;

/**
 * User: Keiichi
 * Date: 09.08.2008
 * Time: 15:36:59
 */
public class clanhall extends Functions implements ScriptFile
{
	// -- Pet ID --
	private static final int GREAT_WOLF = PetDataTable.BLACK_WOLF_ID;
	private static final int FENRIR = PetDataTable.FENRIR_WOLF_ID;
	private static final int WIND_STRIDER = PetDataTable.STRIDER_WIND_ID;
	private static final int STAR_STRIDER = PetDataTable.STRIDER_STAR_ID;
	private static final int TWILING_STRIDER = PetDataTable.STRIDER_TWILIGHT_ID;
	private static final int count = 1;
	// -- MAX PET CHANG LEVEL --

	// -- First Item ID --
	private static final int GREAT_WOLF_ITEM = PetDataTable.getControlItemId(PetDataTable.BLACK_WOLF_ID);
	private static final int FENRIR_ITEM = PetDataTable.getControlItemId(PetDataTable.FENRIR_WOLF_ID);
	private static final int WIND_STRIDER_ITEM = PetDataTable.getControlItemId(PetDataTable.STRIDER_WIND_ID);
	private static final int STAR_STRIDER_ITEM = PetDataTable.getControlItemId(PetDataTable.STRIDER_STAR_ID);
	private static final int TWILING_STRIDER_ITEM = PetDataTable.getControlItemId(PetDataTable.STRIDER_TWILIGHT_ID);

	// -- Second Item ID --
	private static final int GREAT_SNOW_WOLF_ITEM = PetDataTable.getControlItemId(PetDataTable.WGREAT_WOLF_ID);
	private static final int SNOW_FENRIR_ITEM = PetDataTable.getControlItemId(PetDataTable.WFENRIR_WOLF_ID);
	private static final int RED_WS_ITEM = PetDataTable.getControlItemId(PetDataTable.RED_STRIDER_WIND_ID);
	private static final int RED_SS_ITEM = PetDataTable.getControlItemId(PetDataTable.RED_STRIDER_STAR_ID);
	private static final int RED_TW_ITEM = PetDataTable.getControlItemId(PetDataTable.RED_STRIDER_TWILIGHT_ID);

	// -- dist --
	//private int dist = 50;  // Дистанция от игрока до пета во избежание юзания каких-нибудь читов или багов. TODO: попозже добавлю

	public void evolve()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;
		show(Files.read("data/scripts/services/petevolve/chamberlain.htm", player), player);
	}

	public void greatsw()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2Summon pl_pet = player.getPet();

		if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/error_1.htm", player), player);
		else if(pl_pet.getNpcId() != GREAT_WOLF)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_greatw.htm", player), player);
		else if(player.getInventory().getItemByItemId(GREAT_WOLF_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, GREAT_SNOW_WOLF_ITEM, 1);
			show(Files.read("data/scripts/services/petevolve/end_msg3_gwolf.htm", player), player);
		}
	}

	public void fenrir()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2Summon pl_pet = player.getPet();

		if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/error_1.htm", player), player);
		else if(pl_pet.getNpcId() != FENRIR)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 70)
			show(Files.read("data/scripts/services/petevolve/error_lvl_fenrir.htm", player), player);
		else if(player.getInventory().getItemByItemId(FENRIR_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, SNOW_FENRIR_ITEM, 1);
			show(Files.read("data/scripts/services/petevolve/end_msg2_fenrir.htm", player), player);
		}
	}

	public void wstrider()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2Summon pl_pet = player.getPet();

		if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/error_1.htm", player), player);
		else if(pl_pet.getNpcId() != WIND_STRIDER)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_strider.htm", player), player);
		else if(player.getInventory().getItemByItemId(WIND_STRIDER_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, RED_WS_ITEM, 1);
			show(Files.read("data/scripts/services/petevolve/end_msg_strider.htm", player), player);
		}
	}

	public void sstrider()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2Summon pl_pet = player.getPet();

		if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/error_1.htm", player), player);
		else if(pl_pet.getNpcId() != STAR_STRIDER)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_strider.htm", player), player);
		else if(player.getInventory().getItemByItemId(STAR_STRIDER_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, RED_SS_ITEM, 1);
			show(Files.read("data/scripts/services/petevolve/end_msg_strider.htm", player), player);
		}
	}

	public void tstrider()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2Summon pl_pet = player.getPet();

		if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/error_1.htm", player), player);
		else if(pl_pet.getNpcId() != TWILING_STRIDER)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_strider.htm", player), player);
		else if(player.getInventory().getItemByItemId(TWILING_STRIDER_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, RED_TW_ITEM, 1);
			show(Files.read("data/scripts/services/petevolve/end_msg_strider.htm", player), player);
		}
	}

	public void onLoad()
	{
		_log.info("Loaded Service: ClanHall Pet Evolution");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}