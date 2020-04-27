package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 18.09.11 0:59
 */
public class SculptureIceFairyMaker extends DefaultMaker
{
	public SculptureIceFairyMaker(int maximum_npc, String name)
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
		else if( eventId == 10025 )
		{
			i_ai0 = 0;
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(10001, i_ai0, 0);
		}
	}
}