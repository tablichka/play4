package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 10.09.11 15:19
 */
public class CrumaDefaultMaker extends DefaultMaker
{

	public CrumaDefaultMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(npc.isDead())
			super.onNpcDeleted(npc);
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2021003 || eventId == 1001)
			super.onScriptEvent(1001, arg1, arg2);
	}
}
