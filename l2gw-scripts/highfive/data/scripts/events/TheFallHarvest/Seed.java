package events.TheFallHarvest;

import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.IItemHandler;
import ru.l2gw.gameserver.handler.ItemHandler;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

public class Seed implements IItemHandler, ScriptFile
{
	public class DeSpawnScheduleTimerTask implements Runnable
	{
		L2Spawn spawnedPlant = null;

		public DeSpawnScheduleTimerTask(L2Spawn spawn)
		{
			spawnedPlant = spawn;
		}

		public void run()
		{
			try
			{
				spawnedPlant.getLastSpawn().decayMe();
				spawnedPlant.getLastSpawn().deleteMe();
			}
			catch(Throwable t)
			{}
		}
	}

	private static int[] _itemIds = { 6389, // small seed
			6390 // large seed
	};

	private static int[] _npcIds = { 12774, // Young Pumpkin
			12777 // Large Young Pumpkin
	};

	public boolean useItem(L2Playable playable, L2ItemInstance item)
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

		L2Object target = activeChar.getTarget();
		if(target == null)
			target = activeChar;

		try
		{
			L2Spawn spawn = new L2Spawn(template);
			spawn.setConstructor(SquashInstance.class.getConstructors()[0]);
			spawn.setId(IdFactory.getInstance().getNextId());
			spawn.setLoc(activeChar.getLoc());
			L2NpcInstance npc = spawn.doSpawn(true);
			npc.setAI(new SquashAI(npc));
			npc.stopHpMpRegeneration();
			((SquashInstance) npc).setSpawner(activeChar);

			ThreadPoolManager.getInstance().scheduleGeneral(new DeSpawnScheduleTimerTask(spawn), 180000);
			activeChar.destroyItem("Consume", item.getObjectId(), 1, npc, true);
		}
		catch(Exception e)
		{
			activeChar.sendPacket(new SystemMessage(SystemMessage.TARGET_CAN_NOT_BE_FOUND));
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