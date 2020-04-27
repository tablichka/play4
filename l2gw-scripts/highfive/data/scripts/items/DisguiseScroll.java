package items;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;

/**
 * @author: rage
 * @date: 11.07.2010 1:25:44
 */
public class DisguiseScroll implements IItemHandler, ScriptFile
{
	private final static int[] _itemIds = {
			13677,
			13678,
			13679,
			13680,
			13681,
			13682,
			13683,
			13684,
			13685};

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return false;

		L2Player player = (L2Player) playable;

		int terrId = item.getItemId() - 13596;
		if(player.getTerritoryId() != terrId)
		{
			player.sendPacket(Msg.THE_DISGUISE_SCROLL_CANNOT_BE_USED_BECAUSE_IT_IS_MEANT_FOR_USE_IN_A_DIFFERENT_TERRITORY);
			return true;
		}

		if(player.getClan() != null && player.getClan().getHasCastle() > 0)
		{
			player.sendPacket(Msg.A_TERRITORY_OWNING_CLAN_MEMBER_CANNOT_USE_A_DISGUISE_SCROLL);
			return true;
		}

		if(player.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
		{
			player.sendPacket(Msg.THE_DISGUISE_SCROLL_CANNOT_BE_USED_WHILE_YOU_ARE_ENGAGED_IN_A_PRIVATE_STORE_OR_MANUFACTURE_WORKSHOP);
			return true;
		}

		if(player.getKarma() > 0)
		{
			player.sendPacket(Msg.A_DISGUISE_CANNOT_BE_USED_WHEN_YOU_ARE_IN_A_CHAOTIC_STATE);
			return true;
		}

		if(!TerritoryWarManager.getWar().isFunctionsActive())
		{
			player.sendPacket(Msg.THE_TERRITORY_WAR_EXCLUSIVE_DISGUISE_AND_TRANSFORMATION_CAN_BE_USED_20);
			return true;
		}

		if(player.destroyItem("Consume", item.getObjectId(), 1, null, true))
		{
			player.setVar("disguised", String.valueOf(terrId));
			player.broadcastUserInfo(true);
		}
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
	{
	}

	public void onShutdown()
	{
	}
}