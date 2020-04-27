package instances;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 24.10.2010 18:08:26
 */
public class UrbanAreaInstance extends Instance
{
	private static final Location _baseTower = new Location(16280, 283448, -9704);
	private boolean _success = false;

	public UrbanAreaInstance(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void successEnd()
	{
		super.successEnd();
		_success = true;
	}

	@Override
	public void stopInstance()
	{
		if(_shutdown)
			return;

		synchronized(this)
		{
			_shutdown = true;
		}
		if(Config.DEBUG_INSTANCES)
			_log.info(this + " stopInstance");

		for(L2Spawn spawn : _spawns)
		{
			spawn.stopRespawn();
			if(Config.DEBUG_INSTANCES)
				_log.info(this + " stopInstance: despawn: " + spawn.getLastSpawn());
			spawn.despawnAll();
		}

		_spawns = null;

		for(L2DoorInstance door : _doors)
			door.decayMe();

		_doors = null;

		for(Integer objectId : _members)
		{
			L2Player player = L2ObjectsStorage.getPlayer(objectId);
			if(player != null && !player.isDeleting() && !player.isTeleporting() && _template.getZone().isInsideZone(player))
			{
				if(Config.DEBUG_INSTANCES)
					_log.info(this + " stopInstance: teleport " + player);

				if(_success)
				{
					player.setStablePoint(null);
					player.teleToLocation(_baseTower, 0);
				}
				else
					player.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(player, MapRegionTable.TeleportWhereType.ClosestTown), 0);
			}
		}

		InstanceManager.getInstance().removeInstance(_template.getId(), _reflection);

		if(_endTask != null)
			_endTask.cancel(true);
	}
}
