package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 15.12.11 20:36
 */
public class ImmoTrapSpawner extends ImmoBasicMaker
{
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 100;

	public ImmoTrapSpawner(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 78010059 && enabled == 1 )
		{
			onScriptEvent(78010072, 0, 0);
		}
		else if( eventId == 78010072 && enabled == 1 )
		{
			int i0 = Rnd.get(maximum_npc) + 1;
			for(int i1 = 1; i1 < i0; i1 = ( i1 + 1 ))
			{
				SpawnDefine def0 = spawn_defines.get(Rnd.get(3));
				if( def0 != null )
				{
					if( atomicIncrease(def0, 1) )
					{
						def0.spawn(1, 0, 0);
					}
				}
			}
		}
		else if( eventId == 1000 )
		{
			enabled = 0;
			for(SpawnDefine def0 : spawn_defines)
			{
				if( def0 != null && def0.npc_count > 0 )
				{
					def0.despawn();
				}
			}
		}
		super.onScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
	}
}
