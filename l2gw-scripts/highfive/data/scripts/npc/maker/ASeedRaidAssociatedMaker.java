package npc.maker;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.entity.fieldcycle.IFieldCycleMaker;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 12.12.11 14:22
 */
public class ASeedRaidAssociatedMaker extends DefaultMaker implements IFieldCycleMaker
{
	public int FieldCycle_ID = -1;

	public ASeedRaidAssociatedMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onStart()
	{
		FieldCycleManager.registerStepChanged(FieldCycle_ID, this);
		int i0 = FieldCycleManager.getStep(FieldCycle_ID);
		if( i0 == 1 )
		{
			for(SpawnDefine def0 : spawn_defines)
			{
				if( def0 != null )
				{
					if( atomicIncrease(def0, def0.total) )
					{
						def0.spawn(def0.total, 0, 0);
					}
				}
			}
		}
	}

	@Override
	public void onFieldCycleChanged(int fieldId, int oldStep, int newStep)
	{
		if( fieldId == FieldCycle_ID )
		{
			int i1 = FieldCycleManager.getStep(FieldCycle_ID);
			if( i1 == 1 )
			{
				onScriptEvent(1001, 0, 0);
			}
			else if( i1 == 2 )
			{
				onScriptEvent(1000, 0, 0);
			}
		}
	}

	@Override
	public void onFieldCycleExpired(int fieldId, int oldStep, int newStep)
	{
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		if( npc.getSpawnDefine().respawn != 0 )
		{
			int i0 = FieldCycleManager.getStep(FieldCycle_ID);
			if( i0 == 1 )
			{
				if( atomicIncrease(npc.getSpawnDefine(), 1) )
				{
					npc.getSpawnDefine().respawn(npc, npc.getSpawnDefine().respawn, npc.getSpawnDefine().respawn_rand);
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		switch(eventId)
		{
			case 1000:
				despawn();
				break;
			case 1001:
				for(SpawnDefine def0 : spawn_defines)
				{
					if( def0 != null )
					{
						int i1 = def0.total - def0.npc_count;
						if( i1 > 0 )
						{
							if( maximum_npc >= npc_count + i1)
							{
								if( atomicIncrease(def0, i1) )
								{
									def0.spawn(i1, (Integer) arg1, 0);
								}
							}
						}
					}
				}
				break;
		}
	}
}