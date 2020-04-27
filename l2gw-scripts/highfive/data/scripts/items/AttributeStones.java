package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.clientpackets.RequestExEnchantItemAttribute;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.ExChooseInventoryAttributeItem;

/**
 * @author SYS
 */
public class AttributeStones implements IItemHandler, ScriptFile
{
	private static int[] _itemIds = null;

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		L2Player player = (L2Player) playable;

		if(player.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
		{
			player.sendPacket(Msg.YOU_CANNOT_ADD_ELEMENTAL_POWER_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
			return false;
		}

		player.setEnchantScroll(item);
		player.setEnchantStartTime(System.currentTimeMillis());
		player.sendPacket(Msg.PLEASE_SELECT_ITEM_TO_ADD_ELEMENTAL_POWER);
		player.sendPacket(new ExChooseInventoryAttributeItem(item));
		return true;
	}

	public final int[] getItemIds()
	{
		return _itemIds;
	}

	public void onLoad()
	{
		_itemIds = new int[RequestExEnchantItemAttribute._stoneLevels.size()];
		int c = 0;
		for(int itemId : RequestExEnchantItemAttribute._stoneLevels.keys())
			_itemIds[c++] = itemId;

		ItemHandler.getInstance().registerItemHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}
