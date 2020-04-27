package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 14.12.11 20:06
 */
public class Is1DefenceTumorMaker extends InzoneMaker
{
	public String controller_maker = "rumwarsha13_1424_17";

	public Is1DefenceTumorMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1001)
		{
			for(SpawnDefine def0 : spawn_defines)
			{
				if(def0 != null)
				{
					int i1 = def0.total - def0.npc_count;
					if(i1 > 0)
					{
						if(atomicIncrease(def0, i1))
						{
							def0.spawn(i1, 0, 0);
						}
					}
				}
			}
		}
		else if(eventId == 998916)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, 0, 0);
			}
		}
	}

	@Override
	public void onAllNpcDeleted()
	{
	}
}
