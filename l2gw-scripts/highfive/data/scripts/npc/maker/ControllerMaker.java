package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 13.12.11 19:30
 */
public class ControllerMaker extends InzoneMaker
{
	public ControllerMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 989809 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if( def0 != null )
			{
				def0.sendScriptEvent(eventId, arg1, 0);
			}
		}
		super.onScriptEvent(eventId, arg1, arg2);
	}
}