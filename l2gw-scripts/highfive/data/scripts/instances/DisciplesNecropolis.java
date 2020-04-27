package instances;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2GroupSpawn;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author rage
 * @date 27.11.2010 18:29:26
 */
public class DisciplesNecropolis extends Instance
{
	private int _currentStage = 1;
	private L2GroupSpawn _currentGroup;
	private L2GroupSpawn iz112_1724_f01;
	private L2GroupSpawn iz112_1724_f02;

	private static final int[] _doorId =
			{
			 		17240102,
					17240104,
					17240106,
					17240108,
					17240110
			};

	public DisciplesNecropolis(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();

		_currentGroup = SpawnTable.getInstance().getEventGroupSpawn("iz112_1724_1", this);
		_currentGroup.setRespawnDelay(0);
		_currentGroup.doSpawn();
	}

	@Override
	public void notifyDecayd(L2NpcInstance npc)
	{
		if(_currentGroup.isAllDecayed() && _currentStage <= 5)
		{
			L2DoorInstance door = getSpawnedDoor(_doorId[_currentStage - 1]);
			if(Config.DEBUG_INSTANCES)
				_log.info(this + " stage: " + _currentStage + " open door: " + door);
			if(door != null)
			{
				door.openMe();
				door.onOpen();
			}
			_currentStage++;
			if(_currentStage < 6)
			{
				_currentGroup = SpawnTable.getInstance().getEventGroupSpawn("iz112_1724_" + _currentStage, this);
				_currentGroup.setRespawnDelay(0);
				_currentGroup.doSpawn();
			}
		}
	}

	public void notifyEvent(String event, L2Character cha, L2Player player)
	{
		if(event.equals("spawn_iz112_1724_f01"))
		{
			iz112_1724_f01 = SpawnTable.getInstance().getEventGroupSpawn("iz112_1724_f01", this);
			iz112_1724_f01.setRespawnDelay(0);
			iz112_1724_f01.doSpawn();
		}
		else if(event.equals("spawn_iz112_1724_f02"))
		{
			iz112_1724_f02 = SpawnTable.getInstance().getEventGroupSpawn("iz112_1724_f02", this);
			iz112_1724_f02.setRespawnDelay(0);
			iz112_1724_f02.doSpawn();
		}
		else if(event.equals("despawn_iz112_1724_f01"))
			iz112_1724_f01.despawnAll();
		else if(event.equals("despawn_iz112_1724_f02"))
			iz112_1724_f02.despawnAll();
	}

	private L2DoorInstance getSpawnedDoor(int doorId)
	{
		for(L2DoorInstance door : _doors)
			if(door.getDoorId() == doorId)
				return door;

		return null;
	}
}
