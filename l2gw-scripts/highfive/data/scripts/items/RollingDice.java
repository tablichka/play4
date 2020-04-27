package items;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.Dice;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

public class RollingDice implements IItemHandler, ScriptFile
{
	// all the items ids that this handler knowns
	private static final int[] _itemIds = { 4625, 4626, 4627, 4628 };

	static final SystemMessage YOU_MAY_NOT_THROW_THE_DICE_AT_THIS_TIMETRY_AGAIN_LATER = new SystemMessage(SystemMessage.YOU_MAY_NOT_THROW_THE_DICE_AT_THIS_TIMETRY_AGAIN_LATER);

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		L2Player player = (L2Player) playable;

		int itemId = item.getItemId();

		if(player.isInOlympiadMode())
		{
			player.sendPacket(Msg.THIS_ITEM_IS_NOT_AVAILABLE_FOR_THE_OLYMPIAD_EVENT);
			return false;
		}

		if(player.isSitting())
		{
			player.sendPacket(Msg.YOU_CANNOT_MOVE_WHILE_SITTING);
			return false;
		}

		if(itemId == 4625 || itemId == 4626 || itemId == 4627 || itemId == 4628)
		{
			int number = rollDice(player);
			if(number == 0)
			{
				player.sendPacket(YOU_MAY_NOT_THROW_THE_DICE_AT_THIS_TIMETRY_AGAIN_LATER);
				return false;
			}

			player.broadcastPacket(new Dice(player.getObjectId(), itemId, number, player.getX() - 30, player.getY() - 30, player.getZ()));

			SystemMessage sm = new SystemMessage(SystemMessage.S1_HAS_ROLLED_S2);
			sm.addString(player.getName());
			sm.addNumber(number);
			player.sendPacket(sm);
			player.broadcastPacketToOthers(sm);
		}
		return true;
	}

	private int rollDice(L2Player player)
	{
		// Reuse time 4000 ms
		if(System.currentTimeMillis() <= player.lastDiceThrown)
			return 0;
		player.lastDiceThrown = System.currentTimeMillis() + 4000L;
		return Rnd.get(1, 6);
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