package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 17.09.11 14:18
 */
public class InzoneMaker extends DefaultMaker
{
	public int enabled = 1;
	public int script_event_enable = 1;
	public int inzone_type_param = 0;

	public InzoneMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		enabled = script_event_enable;
		InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(inzone_type_param);
		if(it == null)
			_log.warn(this + " no instance template for id: " + inzone_type_param);
		else
			it.addMaker(this);
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		if(debug > 0)
			_log.info(this + " onInstanceZoneEvent: " + inst + " eventId: " + eventId);
		enabled = eventId;
		if(eventId == 1 && on_start_spawn == 1)
		{
			for(SpawnDefine sd : spawn_defines)
			{
				if(debug > 0)
					_log.info(this + " onInstanceZoneEvent: " + sd);
				if(maximum_npc >= npc_count + sd.total && atomicIncrease(sd, sd.total))
					sd.spawn(sd.total, 0, 0);
			}
		}
		else if(eventId == 0)
			despawn();
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		if(enabled == 0)
			npc.onDecay();
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(debug > 0)
			_log.info(this + " npc deleted: " + npc);
		if(enabled == 1)
			super.onNpcDeleted(npc);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(debug > 0)
			_log.info(this + " script event: " + eventId + " " + arg1 + " " + arg2);
		if(enabled == 1)
		{
			if(eventId == 1000)
				despawn();
			else if(eventId == 1001)
				for(SpawnDefine spawnDefine : spawn_defines)
				{
					int c = spawnDefine.total - spawnDefine.npc_count;
					if(c > 0 && maximum_npc >= npc_count + c && atomicIncrease(spawnDefine, c))
						spawnDefine.spawn(c, (Integer) arg1, 0);
				}
		}
	}
}
