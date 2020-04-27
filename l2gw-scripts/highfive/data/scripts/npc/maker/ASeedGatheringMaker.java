package npc.maker;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 12.12.11 15:47
 */
public class ASeedGatheringMaker extends DefaultMaker
{
	public int FieldCycle_ID = -1;

	public ASeedGatheringMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(deleted_def.respawn != 0)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle_ID);
			if(i0 == 2)
			{
				if(atomicIncrease(deleted_def, 1))
				{
					deleted_def.respawn(npc, (int) (deleted_def.respawn * 0.5), deleted_def.respawn_rand);
				}
			}
			else if(atomicIncrease(deleted_def, 1))
			{
				deleted_def.respawn(npc, deleted_def.respawn, deleted_def.respawn_rand);
			}
		}
	}
}