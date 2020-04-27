package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 23.09.11 19:27
 */
public class MakerDefeatdunFreya extends InzoneMaker
{

	public MakerDefeatdunFreya(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onScriptEvent(eventId, arg1, arg2);
		if(eventId == 23140101)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, 0);
			}
		}
	}
}
