package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 27.09.11 21:17
 */
public class MakerIcequeenBasic extends InzoneMaker
{
	private int i_ai9 = 0;

	public MakerIcequeenBasic(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onInstanceZoneEvent(Instance inst, int eventId)
	{
		if(debug > 0)
			_log.info(this + " onInstanceZoneEvent: " + inst + " eventId: " + eventId);
		enabled = 1;
		reflectionId = inst.getReflection();
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onScriptEvent(eventId, arg1, arg2);
		if(eventId == 23140044)
		{
			i_ai9++;
			if(i_ai9 == 5)
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					def0.sendScriptEvent(23140044, 0, 0);
				}
			}
		}
		else if(eventId == 23140042)
		{
			for(SpawnDefine def0 : spawn_defines)
			{
				if(def0 != null)
				{
					def0.sendScriptEvent(23140042, arg1, 0);
				}
			}
		}
		else if(eventId == 23140060)
		{
			SpawnDefine def0 = spawn_defines.get(Rnd.get(2));
			if(def0 != null)
			{
				def0.spawn(1, 0, 0);
			}
		}
	}
}