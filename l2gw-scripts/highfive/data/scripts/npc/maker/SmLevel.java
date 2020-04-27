package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 14.10.11 21:39
 */
public class SmLevel extends DefaultMaker
{
	public int level = 0;

	public SmLevel(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21140013)
		{
			i_ai0 = 0;
		}
		else if(eventId == 21140012)
		{
			i_ai0 = 1;
		}
		super.onScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if(i_ai0 == 1)
		{
			if(npc.getSpawnDefine().respawn != 0)
			{
				if(maximum_npc >= npc_count + 1)
				{
					if(atomicIncrease(npc.getSpawnDefine(), 1))
					{
						npc.getSpawnDefine().respawn(npc, npc.getSpawnDefine().respawn, npc.getSpawnDefine().respawn_rand);
					}
				}
			}
		}
	}
}
