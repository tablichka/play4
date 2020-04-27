package npc.maker;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 13.12.11 19:24
 */
public class Is1AttackMaker extends InzoneMaker
{
	public int room_number = 0;
	public String controller_maker = "rumwarsha13_1424_0701";
	public String ct2_box_maker = "";

	public Is1AttackMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		_log.info(this + " onEvent 1001");
		if(eventId == 1001)
		{
			for(SpawnDefine def0 : spawn_defines)
			{
				if(def0 != null)
				{
					if(maximum_npc >= npc_count + def0.total)
					{
						if(atomicIncrease(def0, def0.total))
						{
							def0.spawn(def0.total, 0, 0);
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
}