package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 23.02.12 20:26
 */
public class RandomSpawn extends DefaultMaker
{
	public RandomSpawn(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		if(on_start_spawn == 0)
		{
			return;
		}

		SpawnDefine def0 = spawn_defines.get(Rnd.get(spawn_defines.size()));
		if(def0 != null)
		{
			if(atomicIncrease(def0, def0.total))
			{
				def0.spawn(def0.total, 0, 0);
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine def0 = spawn_defines.get(Rnd.get(spawn_defines.size()));
		if(def0 != null)
		{
			int i2 = (def0.total - def0.npc_count);
			if(i2 > 0)
			{
				if(atomicIncrease(def0, 1))
				{
					def0.spawn(1, def0.respawn, def0.respawn_rand);
				}
			}
		}
	}
}