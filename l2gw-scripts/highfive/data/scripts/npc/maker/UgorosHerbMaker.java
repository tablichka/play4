package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 07.09.11 17:08
 */
public class UgorosHerbMaker extends DefaultMaker
{
	public UgorosHerbMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010083 && (Integer) arg1 == 0)
		{
			for(int i0 = 0; i0 < maximum_npc / 2; i0++)
			{
				SpawnDefine def0 = spawn_defines.get(Rnd.get(spawn_defines.size()));

				if(def0 != null && atomicIncrease(def0, 1))
				{
					def0.spawn(1, 0, 0);
				}
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine sd = npc.getSpawnDefine();
		if(sd == spawn_defines.get(0) && npc.isDead())
		{
			if(Rnd.get(100) <= 30 && atomicIncrease(sd, 1))
			{
				sd.spawn(1, sd.respawn, sd.respawn_rand);
			}
			else
			{
				SpawnDefine def0 = spawn_defines.get(1);
				if(def0 != null && atomicIncrease(def0, 1))
				{
					def0.spawn(1, sd.respawn, sd.respawn_rand);
				}
			}
		}
		else if(sd == spawn_defines.get(1) && npc.isDead())
		{
			if(Rnd.get(100) <= 30 && atomicIncrease(sd, 1))
			{
				sd.spawn(1, sd.respawn, sd.respawn_rand);
			}
			else
			{
				SpawnDefine def0 = spawn_defines.get(0);
				if(def0 != null && atomicIncrease(def0, 1))
				{
					def0.spawn(1, def0.respawn, def0.respawn_rand);
				}
			}
		}
	}
}
