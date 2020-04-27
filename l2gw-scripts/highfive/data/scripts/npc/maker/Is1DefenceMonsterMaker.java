package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 14.12.11 19:52
 */
public class Is1DefenceMonsterMaker extends InzoneMaker
{
	public int room_number = 0;
	public String controller_maker = "rumwarsha13_1424_1701";
	public String ct2_box_maker = "";

	public Is1DefenceMonsterMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1001)
		{
			if(room_number == 1)
			{
				addTimer(1001, 1);
			}
			else if(room_number == 2)
			{
				addTimer(1001, 10000);
			}
			else if(room_number == 3)
			{
				addTimer(1001, 15000);
			}
			else if(room_number == 4)
			{
				addTimer(1001, 25000);
			}
			else if(room_number == 5)
			{
				addTimer(1001, 30000);
			}
			else if(room_number == 3001)
			{
				addTimer(1001, 35000);
			}
		}
	}

	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
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
	}

	public void onAllNpcDeleted()
	{
		DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(reflectionId, controller_maker);
		if(maker0 != null)
		{
			maker0.onScriptEvent(989809, room_number, 0);
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
	}
}
