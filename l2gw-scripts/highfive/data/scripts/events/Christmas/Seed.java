package events.Christmas;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.serverpackets.NpcInfo;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

public class Seed implements IItemHandler, ScriptFile
{
	public class DeSpawnScheduleTimerTask implements Runnable
	{
		private L2NpcInstance _npc = null;

		public DeSpawnScheduleTimerTask(L2NpcInstance npc)
		{
			_npc = npc;
		}

		public void run()
		{
			try
			{
				_npc.deleteMe();
			}
			catch(Throwable t)
			{}
		}
	}

	private static int[] _itemIds = { 5560, // Christmas Tree
			5561 // Special Christmas Tree
	};

	private static int[] _npcIds = { 13006, // Christmas Tree
			13007 // Special Christmas Tree
	};

	private static final int DESPAWN_TIME = 600000; //10 min

	public boolean useItem(L2Playable playable, L2ItemInstance item)
	{
		if(playable.isPlayer())
		{
			L2Player activeChar = (L2Player) playable;
			L2NpcTemplate template = null;

			int itemId = item.getItemId();
			for(int i = 0; i < _itemIds.length; i++)
				if(_itemIds[i] == itemId)
				{
					template = NpcTable.getTemplate(_npcIds[i]);
					break;
				}

			if(template == null)
				return false;

			try
			{
				L2Spawn spawn = new L2Spawn(template);
				spawn.setId(IdFactory.getInstance().getNextId());
				spawn.setLoc(activeChar.getLoc());
				spawn.setReflection(activeChar.getReflection());
				L2NpcInstance npc = spawn.spawnOne();
				npc.setTitle(activeChar.getName());
				npc.broadcastPacket(new NpcInfo(npc, activeChar));

				// АИ вещающее бафф регена устанавливается только для большой елки
				if(itemId == 5561)
				{
					npc.setAI(new ctreeAI(npc));
					npc.getAI().startAITask();
				}

				ThreadPoolManager.getInstance().scheduleGeneral(new DeSpawnScheduleTimerTask(npc), DESPAWN_TIME);
				activeChar.destroyItem("Consume", item.getObjectId(), 1, npc, true);
			}
			catch(Exception e)
			{
				activeChar.sendPacket(new SystemMessage(SystemMessage.TARGET_CAN_NOT_BE_FOUND));
			}
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