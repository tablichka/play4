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
public class Restore extends Functions implements ScriptFile
{
	//Updated Dragons
	public final static int WGREAT_WOLF_ID = PetDataTable.WGREAT_WOLF_ID;
	public final static int WFENRIR_WOLF_ID = PetDataTable.WFENRIR_WOLF_ID;
	public final static int RED_STRIDER_WIND_ID = PetDataTable.RED_STRIDER_WIND_ID;
	public final static int RED_STRIDER_STAR_ID = PetDataTable.RED_STRIDER_STAR_ID;
	public final static int RED_STRIDER_TWILIGHT_ID = PetDataTable.RED_STRIDER_TWILIGHT_ID;
	
	// -- MAX PET CHANG LEVEL --

	// -- First Item ID --
	private static final int GREAT_WOLF_ITEM = PetDataTable.getControlItemId(PetDataTable.BLACK_WOLF_ID);
	private static final int FENRIR_ITEM = PetDataTable.getControlItemId(PetDataTable.FENRIR_WOLF_ID);
	private static final int WIND_STRIDER_ITEM = PetDataTable.getControlItemId(PetDataTable.STRIDER_WIND_ID);
	private static final int STAR_STRIDER_ITEM = PetDataTable.getControlItemId(PetDataTable.STRIDER_STAR_ID);
	private static final int TWILING_STRIDER_ITEM = PetDataTable.getControlItemId(PetDataTable.STRIDER_TWILIGHT_ID);
	private static final int count = 1;

	// -- Second Item ID --
	private static final int GREAT_SNOW_WOLF_ITEM = PetDataTable.getControlItemId(PetDataTable.WGREAT_WOLF_ID);
	private static final int SNOW_FENRIR_ITEM = PetDataTable.getControlItemId(PetDataTable.WFENRIR_WOLF_ID);
	private static final int RED_WS_ITEM = PetDataTable.getControlItemId(PetDataTable.RED_STRIDER_WIND_ID);
	private static final int RED_SS_ITEM = PetDataTable.getControlItemId(PetDataTable.RED_STRIDER_STAR_ID);
	private static final int RED_TW_ITEM = PetDataTable.getControlItemId(PetDataTable.RED_STRIDER_TWILIGHT_ID);

	// -- dist --
	//private int dist = 50;  // Дистанция от игрока до пета во избежание юзания каких-нибудь читов или багов. TODO: попозже добавлю

	public void greatsw()
	{
		L2Player player = (L2Player) self;
		if(player == null)
			return;

		L2Summon pl_pet = player.getPet();

		if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/error_1.htm", player), player);
		else if(pl_pet.getNpcId() != WGREAT_WOLF_ID)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_greatw.htm", player), player);
		else if(player.getInventory().getItemByItemId(GREAT_SNOW_WOLF_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, GREAT_WOLF_ITEM, 1);
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
		else if(pl_pet.getNpcId() != WFENRIR_WOLF_ID)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 70)
			show(Files.read("data/scripts/services/petevolve/error_lvl_fenrir.htm", player), player);
		else if(player.getInventory().getItemByItemId(SNOW_FENRIR_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, FENRIR_ITEM, 1);
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
		else if(pl_pet.getNpcId() != RED_STRIDER_WIND_ID)
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
		else if(pl_pet.getNpcId() != RED_STRIDER_STAR_ID)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_strider.htm", player), player);
		else if(player.getInventory().getItemByItemId(RED_SS_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, STAR_STRIDER_ITEM, 1);
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
		else if(pl_pet.getNpcId() != RED_STRIDER_TWILIGHT_ID)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/error_lvl_strider.htm", player), player);
		else if(player.getInventory().getItemByItemId(RED_TW_ITEM) == null)
			show(Files.read("data/scripts/services/petevolve/error_2.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, TWILING_STRIDER_ITEM, 1);
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