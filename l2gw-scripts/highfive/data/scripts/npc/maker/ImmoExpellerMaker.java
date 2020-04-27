package npc.maker;

import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 17.12.11 16:15
 */
public class ImmoExpellerMaker extends ImmoBasicMaker
{
	public String ech_atk_seq0_maker = "rumwarsha15_1424_echmusm1";

	public ImmoExpellerMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		enabled = eventId;
		if(eventId == 1)
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

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010069 || eventId == 78010066)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
	}
}