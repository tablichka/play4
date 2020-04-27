package npc.maker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.12.11 21:40
 */
public class RandomSpawnTreasurebox extends DefaultMaker
{
	public RandomSpawnTreasurebox(int maximum_npc, String name)
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
		for(int i0 = 0; i0 < maximum_npc; i0++)
		{
			int i1 = Rnd.get(spawn_defines.size());
			SpawnDefine def0 = spawn_defines.get(i1);
			if(def0 != null)
			{
				if(atomicIncrease(def0, 1))
				{
					def0.spawn(1, 0, 0);
				}
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		int i2 = Rnd.get(spawn_defines.size());
		SpawnDefine def0 = spawn_defines.get(i2);
		if(def0 != null)
		{
			if(atomicIncrease(def0, 1))
			{
				def0.spawn(1, def0.respawn, def0.respawn_rand);
			}
		}
	}
}