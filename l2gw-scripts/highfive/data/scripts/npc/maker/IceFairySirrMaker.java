package npc.maker;

import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 18.09.11 0:46
 */
public class IceFairySirrMaker extends CloseDoorMaker
{
	public IceFairySirrMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
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
		if( eventId == 10005 )
		{
			i_ai0++;
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(10001, i_ai0, 0);
		}
		else if( eventId == 11040 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(11040, arg1, 0);
		}
	}

	protected void onDoorEvent(L2DoorInstance door, int open)
	{
		if( open == 0 )
		{
			if( enabled  == 1)
			{
				return;
			}

			enabled = 1;
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_npc2314_1m1");
			for(SpawnDefine sd : spawn_defines)
			{
				if(debug > 0)
					_log.info(this + " onDoorEvent 0: " + sd);
				if(maximum_npc >= npc_count + sd.total && atomicIncrease(sd, sd.total))
				{
					sd.spawn(sd.total, 0, 0);
					if( maker0 != null )
					{
						maker0.onScriptEvent(11037, 0, 0);
					}
				}
			}
		}
		else if( enabled == 0 )
		{
			return;
		}
		enabled = 0;
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		i_ai0 = 0;
		super.onNpcDeleted(npc);
	}
}