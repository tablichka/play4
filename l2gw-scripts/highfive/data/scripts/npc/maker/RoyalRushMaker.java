package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.01.12 14:25
 */
public class RoyalRushMaker extends DefaultMaker
{
	public RoyalRushMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		addTimer(3000, 1000);
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		int i0 = Calendar.getInstance().get(Calendar.MINUTE);
		if(i0 > 49 && i0 < 60)
		{
			npc.onDecay();
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		int i0 = Calendar.getInstance().get(Calendar.MINUTE);
		if(deleted_def.respawn == 0 || (i0 > 49 && i0 < 59))
			return;

		super.onNpcDeleted(npc);
	}

	@Override
	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3000)
		{
			int i0 = Calendar.getInstance().get(Calendar.MINUTE);
			int i1 = Calendar.getInstance().get(Calendar.SECOND);
			if(i0 == 54 && i1 == 0)
			{
				onScriptEvent(1000, 0, 0);
			}
			if(i0 == 54 && i1 == 1)
			{
				if(npc_count > 0)
				{
					onScriptEvent(1000, 0, 0);
				}
			}
			if(i0 == 54 && i1 == 2)
			{
				if(npc_count > 0)
				{
					onScriptEvent(1000, 0, 0);
				}
			}
			addTimer(3000, 1000);
		}
	}
}