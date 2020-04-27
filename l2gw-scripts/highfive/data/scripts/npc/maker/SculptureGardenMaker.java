package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 18.09.11 0:57
 */
public class SculptureGardenMaker extends DefaultMaker
{
	public SculptureGardenMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 10005 )
		{
			SpawnDefine def0 = spawn_defines.get(0);
			def0.sendScriptEvent(11038, 0, 0);
		}
	}
}
