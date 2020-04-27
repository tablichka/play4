package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.clientpackets.AbstractEnchantPacket;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.ChooseInventoryItem;

public class EnchantScrolls implements IItemHandler, ScriptFile
{
	private static int[] _itemIds = null;

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		L2Player player = (L2Player) playable;

		if(player.getEnchantScroll() != null)
		{
			player.sendActionFailed();
			return false;
		}

		player.setEnchantScroll(item);
		player.setEnchantStartTime(0);
		player.sendPacket(new ChooseInventoryItem(item.getItemId()));
		return true;
	}

	public final int[] getItemIds()
	{
		return _itemIds;
	}

	public void onLoad()
	{
		_itemIds = new int[AbstractEnchantPacket.scrolls.size()];
		int c = 0;
		for(int itemId : AbstractEnchantPacket.scrolls.keySet())
			_itemIds[c++] = itemId;

		ItemHandler.getInstance().registerItemHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}