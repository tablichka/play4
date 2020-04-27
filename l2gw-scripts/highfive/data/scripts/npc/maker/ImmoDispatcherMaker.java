package npc.maker;

import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 15.12.11 19:26
 */
public class ImmoDispatcherMaker extends ImmoBasicMaker
{
	public ImmoDispatcherMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}
	
	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		enabled = eventId;
		if( eventId == 1 )
		{
			i_ai0 = 0;
			if( zone == 2 )
			{
				onScriptEvent(78010067, 0, 0);
			}
			else if( zone == 3 )
			{
				if( tide == 1 )
				{
					onScriptEvent(78010067, 0, 0);
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 78010067 && i_ai0 == 0 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if( def0 != null )
			{
				if( atomicIncrease(def0, def0.total) )
				{
					def0.spawn(def0.total, 0, 0);
				}
			}
			i_ai0 = 1;
		}
		else if( eventId == 1000 && i_ai0 == 1 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if( def0 != null )
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
			i_ai0 = 0;
		}
		else
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if( def0 != null )
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
	}
}