package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 13.10.11 20:17
 */
public class MakerLabyrinthD extends InzoneMaker
{
	public MakerLabyrinthD(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		switch(eventId)
		{
			case 1624002:
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					def0.sendScriptEvent(1624002, 0, 0);
				}
				break;
			case 1624003:
				def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					def0.sendScriptEvent(1624003, 0, 0);
				}
				break;
			case 1624004:
				def0 = spawn_defines.get(0);
				if(def0 != null)
				{
					def0.sendScriptEvent(1624004, 0, 0);
				}
				break;
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
	}
}
