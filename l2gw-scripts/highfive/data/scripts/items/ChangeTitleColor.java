package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.ExRequestChangeNicknameColor;

/**
 * @author rage
 * @date 21.06.2010 14:21:16
 */
public class ChangeTitleColor implements IItemHandler, ScriptFile
{
	private final static int[] _itemIds = {13021, 13307};

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable.isPlayer())
			playable.sendPacket(new ExRequestChangeNicknameColor(item.getObjectId()));

		return true;
	}

	public final int[] getItemIds()
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