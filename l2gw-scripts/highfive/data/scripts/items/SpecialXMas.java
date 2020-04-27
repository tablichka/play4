package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.ShowXMasSeal;

public class SpecialXMas implements IItemHandler, ScriptFile
{
	private static int[] _itemIds = { 5555 };

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!playable.isPlayer())
			return false;
		L2Player activeChar = (L2Player) playable;
		int itemId = item.getItemId();

		if(itemId == 5555) // Token of Love
		{
			ShowXMasSeal SXS = new ShowXMasSeal(5555);
			activeChar.broadcastPacket(SXS);
		}
		return true;
	}

	public int[] getItemIds()
	{
		return _itemIds;
	}

	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	public void onReload()
	{}

	public void onShutdown()
	{}
}