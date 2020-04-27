package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 17.12.11 14:40
 */
public class ImmoBossMaker extends ImmoBasicMaker
{
	public String ech_atk_seq0_maker = "rumwarsha15_1424_echmusm1";
	public String ech_atk_expel_maker = "rumwarsha15_1424_expelm1";
	public String ech_def_seq0_maker = "rumwarsha15_1424_ech_dummy1m1";
	public String ech_def_seq1_maker = "rumwarsha15_1424_ech_dummy2m1";
	public String ech_def_seq2_maker = "rumwarsha15_1424_ech_dummy2m2";
	public String ech_def_seq3_maker = "rumwarsha15_1424_defwagonm1";
	public String ech_def_seq4_maker = "rumwarsha15_1424_veinm1";
	public int TM_boss_wagon_default_delay = 780001;
	public int TIME_boss_wagon_delay_default = 40;
	public int TIME_boss_wagon_delay_min = 20;
	public int TIME_boss_wagon_delay_max = 60;
	public int TM_challanger_notify_delay = 780002;
	public int TIME_challanger_notify_delay = 10;

	public ImmoBossMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		i_ai0 = 0;
		i_ai1 = 0;
		i_ai2 = 0;
		enabled = eventId;
		if(eventId == 1)
		{
			i_ai4 = TIME_boss_wagon_delay_default;
			i_ai8 = 1;
			if(zone == 3 && (seq == 0 || seq == 1))
			{
				SpawnDefine def0 = spawn_defines.get(0);
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
	public void onNpcCreated(L2NpcInstance npc)
	{
		SpawnDefine created_def = npc.getSpawnDefine();
		if(zone == 3 && tide == 0 && seq == 0 && spawn_defines.get(1) == created_def)
		{
			if(created_def != null)
			{
				i_ai0 = 1;
				if(i_ai1 != 0)
				{
					created_def.sendScriptEvent(78010067, i_ai1, i_ai2);
				}
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(zone == 3 && tide == 0 && seq == 0 && deleted_def == spawn_defines.get(1) && npc.isDead() && enabled == 1)
		{
			DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_a_dispatcher_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010071, 1, 0);
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010067 && enabled == 1)
		{
			if(zone == 3 && tide == 0 && seq == 0 && (Integer) arg1 == 0 && (Integer) arg2 == 0)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					def0.despawn();
				}
			}
			else if(zone == 3 && tide == 0 && seq == 0 && (Integer) arg1 == 1 && (Integer) arg2 == 0)
			{
				SpawnDefine def0 = spawn_defines.get(1);
				if(def0 != null)
				{
					if(atomicIncrease(def0, def0.total))
					{
						def0.spawn(def0.total, 0, 0);
					}
				}
			}
			else if(zone == 3 && tide == 0 && seq == 0)
			{
				if(i_ai0 == 1)
				{
					SpawnDefine def0 = spawn_defines.get(1);
					if(def0 != null)
					{
						def0.sendScriptEvent(eventId, arg1, arg2);
					}
				}
				else
				{
					i_ai1 = (Integer) arg1;
					i_ai2 = (Integer) arg2;
				}
			}
			else if(tide == 1 && zone == 3 && seq == 3)
			{
				addTimer(TM_boss_wagon_default_delay, 1000);
			}
		}
		if((zone == 3 && tide == 0 && seq == 0 && (eventId == 78010068 || eventId == 78010070)) && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(1);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
		if((zone == 3 && tide == 1 && seq == 3 && (eventId == 78010068 || eventId == 78010070)) && enabled == 1)
		{
			if(eventId == 78010070)
			{
				if(i_ai8 > 2)
				{
					i_ai8--;
				}
				if((i_ai4 - 5) > TIME_boss_wagon_delay_min)
				{
					i_ai4 -= 5;
				}
				else
				{
					i_ai4 = TIME_boss_wagon_delay_min;
				}
			}
			else if(i_ai8 < 4)
			{
				i_ai8++;
			}
			if((i_ai4 + 5) < TIME_boss_wagon_delay_max)
			{
				i_ai4 += 5;
			}
			else
			{
				i_ai4 = TIME_boss_wagon_delay_max;
			}
		}
		else if(zone == 3 && tide == 1 && seq == 3 && eventId == 78010073 && enabled == 1)
		{
			DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, ech_def_seq4_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010073, 0, 0);
			}
		}
		else if(zone == 3 && tide == 1 && seq == 4 && eventId == 78010073 && enabled == 1)
		{
			int i0 = Rnd.get(3);
			SpawnDefine def0 = spawn_defines.get(i0);
			if(def0 != null)
			{
				if(maximum_npc > npc_count)
				{
					if(atomicIncrease(def0, 1))
					{
						def0.spawn(1, 0, 0);
					}
				}
			}
			DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, z3_d_dispatcher_maker);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010073, 0, 0);
			}
		}
		else if(zone == 3 && tide == 1 && seq == 2 && eventId == 78010070 && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				if(atomicIncrease(def0, 1))
				{
					def0.spawn(1, 0, 0);
				}
			}
		}
		else if(zone == 3 && tide == 1 && seq == 0 && eventId == 78010070 && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
		else if(eventId == 1000)
		{
			enabled = 0;
			despawn();
		}
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_boss_wagon_default_delay && zone == 3 && tide == 1 && seq == 3 && enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				if(maximum_npc >= spawn_defines.size() + i_ai8 && atomicIncrease(def0, i_ai8))
				{
					def0.spawn(i_ai8, 0, 0);
				}
			}
			addTimer(TM_boss_wagon_default_delay, i_ai4 * 1000);
		}
	}
}