package services.petevolve;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.tables.PetDataTable;
import ru.l2gw.util.Files;

/**
 * User: darkevil
 * Date: 16.05.2008
 * Time: 12:19:36
 */
public class wolfevolve extends Functions implements ScriptFile
{
	private static final int WOLF = PetDataTable.PET_WOLF_ID; //Чтоб было =), проверка на Wolf
	private static final int count = 1; // Чтоб было =), число забираемх ошейников Wolf
	private static final int WOLF_COLLAR = PetDataTable.getControlItemId(WOLF); // Ошейник Wolf
	private static final int GREAT_WOLF_NECKLACE = PetDataTable.getControlItemId(PetDataTable.BLACK_WOLF_ID); // Ожерелье Great Wolf

	//private static final int dist = 50; // Дистанция от игрока до пета во избежание юзания каких-нибудь читов или багов. TODO: попозже добавлю

	public void evolve()
	{
		L2Player player = (L2Player) self;
		L2Summon pl_pet = player.getPet();

		if(player.getInventory().getItemByItemId(WOLF_COLLAR) == null)
			show(Files.read("data/scripts/services/petevolve/no_item.htm", player), player);
		else if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/evolve_no.htm", player), player);
		else if(pl_pet.getNpcId() != WOLF)
			show(Files.read("data/scripts/services/petevolve/no_wolf.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/no_level.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, GREAT_WOLF_NECKLACE, count);
			show(Files.read("data/scripts/services/petevolve/yes_wolf.htm", player), player);
		}

		/**
		 if(player.getPet().getX() - player.getX() > dist || player.getPet().getY() - player.getY() > dist ||player.getPet().getZ() - player.getZ() > dist)
		 {
		 show(Files.read("data/scripts/services/petevolve/no_dist.htm", player), player);
		 }
		 **/
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Evolve Wolf");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
