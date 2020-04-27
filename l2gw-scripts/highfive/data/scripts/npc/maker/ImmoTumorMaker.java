package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 15.12.11 17:31
 */
public class ImmoTumorMaker extends ImmoBasicMaker
{
	public int TM_sboss_spawn_delay = 78079;
	public int TIME_sboss_spawn_delay = 180;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity1 = 1500;
	public int FieldCycle_Quantity2 = -1000;
	public int FieldCycle_Quantity3 = 100;

	public ImmoTumorMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		enabled = eventId;
		if(eventId == 1)
		{
			i_ai0 = 0;
			i_ai1 = 0;
			if(zone == 3 && tide == 0 && room == 304)
			{
				SpawnDefine def0 = spawn_defines.get(2);
				if(def0 != null)
				{
					if(atomicIncrease(def0, def0.total))
					{
						def0.spawn(def0.total, 0, 0);
					}
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010067)
		{
			SpawnDefine def0 = spawn_defines.get(tide);
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
			if(zone == 3 && (Integer) arg1 != 0 && Rnd.get(100) <= 33)
			{
				//if(room == 304)
				//{
					i_ai0 = (Integer) arg1;
				//}
				//else
				//{
				//	i_ai0 = (Integer) arg1 + 1;
				//}
				addTimer(TM_sboss_spawn_delay, TIME_sboss_spawn_delay * 1000);
			}
		}
		else if(eventId == 78010063 || eventId == 78010062)
		{
			SpawnDefine def0 = spawn_defines.get(1);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
		else if(eventId == 989804 && (Integer) arg1 != 99)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
		else if(eventId == 1000 && zone == 3)
		{
			enabled = 0;
			SpawnDefine def0 = spawn_defines.get(i_ai0);
			if(def0 != null && def0.npc_count > 0)
			{
				def0.despawn();
			}
		}
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		SpawnDefine created_def = npc.getSpawnDefine();
		if(created_def == spawn_defines.get(0) && i_ai1 == 1 && enabled == 1)
		{
			if(created_def != null)
			{
				created_def.sendScriptEvent(989804, 99, 0);
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(deleted_def == spawn_defines.get(0) && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(1);
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
			i_ai1 = 0;
			DefaultMaker maker0 = null;
			if(inzone_type_param == 119)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_dispatcher_maker);
			}
			else if(inzone_type_param == 120)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_dispatcher_maker);
			}
			else if(inzone_type_param == 121)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_dispatcher_maker);
			}
			else if(inzone_type_param == 122)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_dispatcher_maker);
			}
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010053, 1, room);
			}
		}
		else if(deleted_def == spawn_defines.get(1) && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
			i_ai1 = 1;
			DefaultMaker maker0 = null;
			if(inzone_type_param == 119)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_a_dispatcher_maker);
			}
			else if(inzone_type_param == 120)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z2_d_dispatcher_maker);
			}
			else if(inzone_type_param == 121)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_dispatcher_maker);
			}
			else if(inzone_type_param == 122)
			{
				maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_dispatcher_maker);
			}
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010053, 0, room);
			}
		}
		else if(deleted_def == spawn_defines.get(2) && room == 304 && enabled == 1)
		{
			i_ai1 = 1;
		}
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_sboss_spawn_delay && enabled == 1)
		{
			SpawnDefine def0 = i_ai0 < spawn_defines.size() ? spawn_defines.get(i_ai0) : null;
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
		}
	}
}