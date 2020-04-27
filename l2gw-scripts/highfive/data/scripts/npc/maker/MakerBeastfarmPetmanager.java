package npc.maker;

import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 09.09.11 4:41
 */
public class MakerBeastfarmPetmanager extends DefaultMaker
{

	public MakerBeastfarmPetmanager(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onScriptEvent(eventId, arg1, arg2);
		if(eventId == 21150002)
		{
			SpawnDefine def0 = spawn_defines.get(0);
			if(def0 != null)
			{
				def0.sendScriptEvent(eventId, arg1, arg2);
			}
		}
	}
}
