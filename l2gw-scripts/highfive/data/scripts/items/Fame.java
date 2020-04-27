package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

/**
 * @author: rage
 * @date: 12.02.13 12:24
 */
public class Fame implements IItemHandler, ScriptFile
{
	@Override
	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(!(playable instanceof L2Player))
			return false;

		L2Player player = (L2Player) playable;

		int fame = 0;
		switch(item.getItemId())
		{
			case 23266:
				fame = 1000;
				break;
			case 23267:
				fame = 5000;
				break;
			case 23268:
				fame = 10000;
				break;
		}

		if(fame == 0)
			return false;

		if(player.destroyItem("Consume", item.getObjectId(), 1, null, true))
			player.addFame(fame);

		return true;
	}

	@Override
	public int[] getItemIds()
	{
		return new int[]{23266, 23267, 23268};
	}

	@Override
	public void onLoad()
	{
		ItemHandler.getInstance().registerItemHandler(this);
	}

	@Override
	public void onReload()
	{
	}

	@Override
	public void onShutdown()
	{
	}
}
