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
 * Date: 04.06.2008
 * Time: 1:06:26
 */
public class ibbuffalo extends Functions implements ScriptFile
{
	private static final int BABY_BUFFALO = PetDataTable.BABY_BUFFALO_ID;
	private static final int count = 1;
	private static final int BABY_BUFFALO_PANPIPE = PetDataTable.getControlItemId(BABY_BUFFALO);
	private static final int IN_BABY_BUFFALO_NECKLACE = PetDataTable.getControlItemId(PetDataTable.IMPROVED_BABY_BUFFALO_ID);

	//private int dist = 50;  // Дистанция от игрока до пета во избежание юзания каких-нибудь читов или багов. TODO: попозже добавлю

	public void evolve()
	{
		L2Player player = (L2Player) self;
		L2Summon pl_pet = player.getPet();

		if(player.getInventory().getItemByItemId(BABY_BUFFALO_PANPIPE) == null)
			show(Files.read("data/scripts/services/petevolve/no_item.htm", player), player);
		else if(pl_pet == null)
			show(Files.read("data/scripts/services/petevolve/evolve_no.htm", player), player);
		else if(pl_pet.getNpcId() != BABY_BUFFALO)
			show(Files.read("data/scripts/services/petevolve/no_pet.htm", player), player);
		else if(player.isMageClass())
			show(Files.read("data/scripts/services/petevolve/no_class_w.htm", player), player);
		else if(pl_pet.getLevel() < 55)
			show(Files.read("data/scripts/services/petevolve/no_level.htm", player), player);
		else if(pl_pet.getInventory().getItems().length != 0)
			player.sendPacket(Msg.THERE_ARE_ITEMS_IN_YOUR_PET_INVENTORY_RENDERING_YOU_UNABLE_TO_SELL_TRADE_DROP_PET_SUMMONING_ITEM_PLEASE_EMPTY_YOUR_PET_INVENTORY);
		else
		{
			player.getPet().unSummon();
			removeItemByObjId(player, pl_pet.getControlItemObjId(), count);
			addItem(player, IN_BABY_BUFFALO_NECKLACE, count);
			show(Files.read("data/scripts/services/petevolve/yes_pet.htm", player), player);
		}
	}

	public void onLoad()
	{
		_log.info("Loaded Service: Evolve Improved Baby Buffalo");
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
