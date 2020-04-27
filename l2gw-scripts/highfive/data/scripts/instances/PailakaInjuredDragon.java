package instances;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2GroupSpawn;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author rage
 * @date 13.10.2010 18:25:53
 */
public class PailakaInjuredDragon extends Instance
{
	private final FastMap<String, L2GroupSpawn> _spawns = new FastMap<String, L2GroupSpawn>().shared();
	private ZoneListener zoneListener = new ZoneListener();

	public PailakaInjuredDragon(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();
		for(int i = 1; i < 11; i++)
		{
			L2GroupSpawn gs = SpawnTable.getInstance().getEventGroupSpawn("varka_p" + i, this);
			gs.stopRespawn();
			gs.doSpawn();
		}
		getTemplate().getZone().getListenerEngine().addMethodInvokedListener(zoneListener);
	}

	@Override
	public void stopInstance()
	{
		super.stopInstance();
		getTemplate().getZone().getListenerEngine().removeMethodInvokedListener(zoneListener);
	}

	@Override
	public void notifyAttacked(L2Character cha, L2Player attacker)
	{
		L2NpcInstance npc = (L2NpcInstance) cha;
		L2GroupSpawn gs = npc.getSpawn().getGroupSpawn();
		if(gs != null && !gs.getEventName().endsWith("_1") && !_spawns.containsKey(gs.getEventName() + "_1") && Rnd.chance(5))
		{
			L2GroupSpawn gsp = SpawnTable.getInstance().getEventGroupSpawn(gs.getEventName() + "_1", this);
			_spawns.put(gs.getEventName() + "_1", gsp);
			gsp.stopRespawn();
			gsp.doSpawn();
		}
	}

	@Override
	public void notifyDecayd(L2NpcInstance npc)
	{
		L2GroupSpawn gs = npc.getSpawn().getGroupSpawn();
		if(gs != null && !gs.getEventName().endsWith("_1") && _spawns.containsKey(gs.getEventName() + "_1"))
		{
			boolean all = true;
			for(L2NpcInstance mob : gs.getAllSpawned())
				if(!mob.isDecayed())
				{
					all = false;
					break;
				}

			if(all)
				_spawns.get(gs.getEventName() + "_1").despawnAll();
		}
	}

	@Override
	public void successEnd()
	{
		_terminate = true;

		if(_endTask != null)
			_endTask.cancel(true);

		_endTime = System.currentTimeMillis() + _template.getCoolTime();
		int[] time = calcTimeForEndTask((int) (_template.getCoolTime() / 1000));
		_endTask = ThreadPoolManager.getInstance().scheduleGeneral(new EndTask(time[1]), time[0] * 1000L);
	}

	@Override
	public void onPlayerExit(L2Player player)
	{
		super.onPlayerExit(player);
		if(player.getReflection() == getReflection())
			player.teleToLocation(getStartLoc(), getReflection());
		else
			player.unEquipInappropriateItems();
	}

	private class ZoneListener extends L2ZoneEnterLeaveListener
	{
		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
			if(object instanceof L2Player && object.getReflection() == getReflection())
			{
				object.teleToLocation(getStartLoc(), getReflection());
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}
}
