package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 19.12.11 20:18
 */
public class LindviorSceneMaker extends DefaultMaker
{
	public LindviorSceneMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010057)
		{
			SpawnDefine def0 = null;
			switch((Integer) arg1)
			{
				case 1:
					def0 = spawn_defines.get(0);
					break;
				case 2:
					def0 = spawn_defines.get(1);
					break;
				case 3:
					def0 = spawn_defines.get(2);
					break;
			}
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
		super.onScriptEvent(eventId, arg1, arg2);
	}
}