package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 12.12.11 15:51
 */
public class ScriptEventSpawnMaker extends DefaultMaker
{
	public int script_event_enable = 1;

	public ScriptEventSpawnMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onStart()
	{
		i_ai0 = 0;
		super.onStart();
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		switch(eventId)
		{
			case 1000:
				for(SpawnDefine def0 : spawn_defines)
				{
					if(def0 != null)
					{
						def0.despawn();
					}
				}
				break;
			case 1001:
				for(SpawnDefine def0 : spawn_defines)
				{
					if(def0 != null)
					{
						int i1 = def0.total - def0.npc_count;
						if(i1 > 0)
						{
							if(atomicIncrease(def0, i1))
							{
								def0.spawn(i1, (Integer) arg1, 0);
							}
						}
					}
				}
				break;
			case 5:
				i_ai0 = (Integer) arg1;
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					if(maximum_npc >= npc_count + def0.total)
					{
						int i1 = def0.total - def0.npc_count;
						if(atomicIncrease(def0, (Integer) arg2))
						{
							def0.spawn((Integer) arg2, 0, 0);
						}
						addTimer(9989, 3000);
					}
				}
				break;
		}
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 9989)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(8, i_ai0, 0);
			}
		}
	}
}