package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 16.12.11 14:33
 */
public class ImmoTrapMaker extends ImmoBasicMaker
{
	public int TM_trap_delay = 780001;
	public int TIME_trap_delay = 30;

	public ImmoTrapMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(enabled == 1)
		{
			SpawnDefine def0 = spawn_defines.get(Rnd.get(3));
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, def0.respawn, def0.respawn_rand);
				}
			}
		}
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_trap_delay && enabled == 1)
		{
			int i0 = Rnd.get(3);
			SpawnDefine def0 = spawn_defines.get(i0);
			if(def0 != null)
			{
				if(atomicIncrease(def0, def0.total))
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010067 && enabled == 1)
		{
			addTimer(TM_trap_delay, TIME_trap_delay * 1000);
		}
		else if(eventId == 1000)
		{
			enabled = 0;
			for(SpawnDefine def0 : spawn_defines)
			{
				if(def0 != null && def0.npc_count > 0)
				{
					def0.despawn();
				}
			}
		}
	}
}