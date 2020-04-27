package items;

import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.serverpackets.ExGetBookMarkInfo;

/**
 * @author rage
 * @date 23.06.2010 14:12:20
 */
public class TeleportSpellbook implements IItemHandler, ScriptFile
{
		private final static int[] _itemIds = {13015};

		public boolean useItem(L2Playable playable, L2ItemInstance item)
		{
			if(!playable.isPlayer())
				return false;

			L2Player player = playable.getPlayer();
			if(player.getTeleportBook().getMaxSlots() >= Config.ALT_TELEPORT_BOOK_SIZE)
			{
				player.sendPacket(Msg.YOUR_NUMBER_OF_MY_TELEPORTS_SLOTS_HAS_REACHED_ITS_MAXIMUM_LIMIT);
				return true;
			}

			if(player.destroyItemByItemId("Consume", 13015, 1, null, true))
			{
				player.getTeleportBook().setMaxSlots(player.getTeleportBook().getMaxSlots() + 3);
				player.sendPacket(Msg.THE_NUMBER_OF_MY_TELEPORTS_SLOTS_HAS_BEEN_INCREASED);
				player.sendPacket(new ExGetBookMarkInfo(player));
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
		{}

		public void onShutdown()
		{}
}