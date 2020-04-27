package instances;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.serverpackets.Earthquake;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;

import java.util.concurrent.ScheduledFuture;

/**
 * @author: rage
 * @date: 01.09.2010 17:21:47
 */
public class DelusionChamberInstance extends Instance
{
	private static final long ROOM_TIME = 480000;
	private static final int TELEPORT_TO_RB_CHANCE = 10;
	private GArray<L2Spawn> _currentSpawn;
	public int _currentRoom = 0;
	private ScheduledFuture<?> _teleportTask;
	private ScheduledFuture<?> _earthquakeTask;
	private ScheduledFuture<?> _spawnTask;
	private TeleportTask _teleTask;
	private int _jumpCount;
	private int _type;
	private final int _maxRooms;
	private int _startRoom;
	private boolean _rbVisited = false;
	private GArray<Integer> _spawnedRooms;

	private static final Location[][] _roomTeleports =
			{
					{ // 127
							new Location(-122368, -218972, -6720),
							new Location(-122352, -218044, -6720),
							new Location(-122368, -220220, -6720),
							new Location(-121440, -218444, -6720),
							new Location(-121424, -220124, -6720)
					},
					{ // 128
							new Location(-108960, -218892, -6720),
							new Location(-108976, -218028, -6720),
							new Location(-108960, -220204, -6720),
							new Location(-108032, -218428, -6720),
							new Location(-108032, -220140, -6720)
					},
					{ // 129
							new Location(-122368, -207820, -6720),
							new Location(-122368, -206940, -6720),
							new Location(-122368, -209116, -6720),
							new Location(-121456, -207356, -6720),
							new Location(-121440, -209004, -6720)
					},
					{ // 130
							new Location(-108976, -207772, -6720),
							new Location(-108976, -206972, -6720),
							new Location(-108960, -209164, -6720),
							new Location(-108048, -207340, -6720),
							new Location(-108048, -209020, -6720)
					},
					{ // 131
							new Location(-122368, -153388, -6688),
							new Location(-122368, -152524, -6688),
							new Location(-120480, -155116, -6688),
							new Location(-120480, -154236, -6688),
							new Location(-121440, -151212, -6688),
							new Location(-120464, -152908, -6688),
							new Location(-122368, -154700, -6688),
							new Location(-121440, -152908, -6688),
							new Location(-121440, -154572, -6688)
					},
					{ // 132
							new Location(-108976, -153372, -6688),
							new Location(-108960, -152524, -6688),
							new Location(-107088, -155052, -6688),
							new Location(-107104, -154236, -6688),
							new Location(-108048, -151244, -6688),
							new Location(-107088, -152956, -6688),
							new Location(-108992, -154604, -6688),
							new Location(-108032, -152892, -6688),
							new Location(-108048, -154572, -6688)
					},
			};

	public DelusionChamberInstance(InstanceTemplate template, int rId)
	{
		super(template, rId);
		_type = template.getId() - 127;
		_maxRooms = _roomTeleports[_type].length - 1;
		_startRoom = Rnd.get(_maxRooms);
		_spawnedRooms = new GArray<Integer>(_maxRooms);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();

		_currentRoom = _startRoom;
		if(Config.DEBUG_INSTANCES)
			_log.info(this + " start room: " + (_startRoom + 1));
		_jumpCount = 0;
		long time = ROOM_TIME + Rnd.get(110) * 1000;
		_teleTask = new TeleportTask();
		_teleportTask = ThreadPoolManager.getInstance().scheduleGeneral(_teleTask, time + 5000);
		_earthquakeTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			public void run()
			{
				earthquake();
			}
		}, time);
		_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			public void run()
			{
				spawnRoom();
			}
		}, 15000);
	}

	@Override
	public void stopInstance()
	{
		super.stopInstance();
		despawnRoom();

		if(_teleportTask != null)
			_teleportTask.cancel(true);

		if(_spawnTask != null)
			_spawnTask.cancel(true);

		if(_earthquakeTask != null)
			_earthquakeTask.cancel(true);
	}

	public void successEnd()
	{
		super.successEnd();

		if(_teleportTask != null)
			_teleportTask.cancel(true);
		if(_earthquakeTask != null)
			_earthquakeTask.cancel(true);
		if(_spawnTask != null)
			_spawnTask.cancel(true);

		_teleportTask = null;
		_earthquakeTask = null;
		_spawnTask = null;
	}

	@Override
	public Location getStartLoc()
	{
		return _roomTeleports[_type][_startRoom];
	}

	private class TeleportTask implements Runnable
	{
		public void run()
		{
			if(Config.DEBUG_INSTANCES)
				_log.info(DelusionChamberInstance.this + " TeleportTask: jumps: " + _jumpCount + " currentRoom: " + _currentRoom + " max rooms: " + _maxRooms);

			GArray<L2Player> players = new GArray<L2Player>(9);
			boolean live = false;
			for(L2Player player : getPlayersInside())
				if(player != null)
				{
					players.add(player);
					if(!player.isDead())
						live = true;
				}

			if(live)
			{
				_jumpCount++;

				int nextRoom;
				if(_template.getId() >= 127 && _template.getId() <= 130)
				{
					if(!_rbVisited && Rnd.chance(TELEPORT_TO_RB_CHANCE) && _currentRoom != _maxRooms)
					{
						nextRoom = _maxRooms;
						_rbVisited = true;
						if(Config.DEBUG_INSTANCES)
							_log.info(DelusionChamberInstance.this + " next teleport to RB.");
					}
					else
						do
						{
							nextRoom = Rnd.get(_maxRooms);
						}
						while(nextRoom == _currentRoom);
				}
				else if(!_rbVisited && getTimeLeft() < 600 && _currentRoom != _maxRooms)
				{
					nextRoom = _maxRooms;
					_rbVisited = true;
					if(_endTask != null)
						_endTask.cancel(true);

					_endTime += 1200000;
					int[] time = calcTimeForEndTask((int) ((_endTime - System.currentTimeMillis()) / 1000));
					_endTask = ThreadPoolManager.getInstance().scheduleGeneral(new EndTask(time[1]), time[0] * 1000L);
					if(Config.DEBUG_INSTANCES)
						_log.info(DelusionChamberInstance.this + " next teleport to RB.");

					for(L2Player player : getPlayersInside())
						if(player != null)
							player.sendMessage(new CustomMessage("fs1800881", player).toString());
				}
				else
					do
					{
						nextRoom = Rnd.get(_maxRooms);
					}
					while(nextRoom == _currentRoom);

				_currentRoom = nextRoom;
				Location loc = _roomTeleports[_type][_currentRoom];
				if(Config.DEBUG_INSTANCES)
					_log.info(DelusionChamberInstance.this + " jump: " + _jumpCount + " to room: " + (_currentRoom + 1));

				for(L2Player player : players)
					player.teleToLocation(loc);

				_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
				{
					public void run()
					{
						spawnRoom();
					}
				}, 15000);

				if(nextRoom != _maxRooms)
				{
					long time = ROOM_TIME + Rnd.get(110) * 1000;
					_earthquakeTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						public void run()
						{
							earthquake();
						}
					}, time);
					_teleportTask = ThreadPoolManager.getInstance().scheduleGeneral(this, time + 5000);
				}
			}
			else
			{
				_terminate = true;
				stopInstance();
			}
		}
	}

	public void earthquake()
	{
		for(L2Player player : getPlayersInside())
			if(player != null && _template.getZone().isInsideZone(player))
				player.sendPacket(new Earthquake(player.getLoc(), 20, 10));
	}

	private void spawnRoom()
	{
		if(!_spawnedRooms.contains(_currentRoom))
		{
			_spawnedRooms.add(_currentRoom);
			_currentSpawn = SpawnTable.getInstance().getEventSpawn("dc_" + _template.getId() + "_" + (_currentRoom + 1), this);
			for(L2Spawn spawn : _currentSpawn)
				spawn.init();
		}
	}

	private void despawnRoom()
	{
		if(_currentSpawn != null)
			for(L2Spawn spawn : _currentSpawn)
				spawn.despawnAll();

		_currentSpawn = null;
	}

	@Override
	public void notifyEvent(String event, L2Character cha, L2Player player)
	{
		if(Config.DEBUG_INSTANCES)
			_log.info(this + " notifyEvent: " + event);

		if(event.equals("box_spawn"))
		{
			if(_template.getId() >= 127 && _template.getId() <= 130)
			{
				if(_earthquakeTask != null)
					_earthquakeTask.cancel(true);
				_earthquakeTask = null;

				if(_teleportTask != null)
					_teleportTask.cancel(true);
				if(_spawnTask != null)
					_spawnTask.cancel(true);

				_teleportTask = ThreadPoolManager.getInstance().scheduleGeneral(_teleTask, 60000);
				_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
				{
					public void run()
					{
						spawnRoom();
					}
				}, 75000);
			}
			else
				successEnd();
		}
		else if(event.equals("next_room"))
		{
			if(_earthquakeTask != null)
				_earthquakeTask.cancel(true);
			_earthquakeTask = null;

			if(_teleportTask != null)
				_teleportTask.cancel(true);
			_teleportTask = null;

			if(_spawnTask != null)
				_spawnTask.cancel(true);
			_spawnTask = null;

			_teleTask.run();
		}
		else if(event.equalsIgnoreCase("party"))
		{
			if(player != null)
				player.teleToLocation(_roomTeleports[_type][_currentRoom]);
		}
	}
}
