package npc.maker;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.entity.fieldcycle.IFieldCycleMaker;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 12.12.11 14:32
 */
public class ASeedRaidBossMaker extends DefaultMaker implements IFieldCycleMaker
{
	public int FieldCycle_ID = -1;
	public int boss_respawn_time = 600;
	public String maker_name = "";
	public String maker_name1 = "";
	public String maker_name2 = "";

	public ASeedRaidBossMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		on_start_spawn = 0;
	}

	@Override
	public void onStart()
	{
		FieldCycleManager.registerStepChanged(FieldCycle_ID, this);
		int i0 = FieldCycleManager.getStep(FieldCycle_ID);
		if( i0 == 2 )
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
			if( i1 == 2 )
			{
				onScriptEvent(1001, 0, 0);
			}
			else if( i1 == 1 )
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
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if( deleted_def.respawn != 0 )
		{
			int i0 = FieldCycleManager.getStep(FieldCycle_ID);
			if( i0 == 2 )
			{
				if( i_ai0 == 0 )
				{
					if( atomicIncrease(deleted_def, 1) )
					{
						deleted_def.respawn(npc, deleted_def.respawn, deleted_def.respawn_rand);
					}
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
						int i1 = ( def0.total - def0.npc_count );
						if( i1 > 0 )
						{
							if( atomicIncrease(def0, i1) )
							{
								def0.spawn(i1, (Integer) arg1, 0);
							}
						}
					}
				}
				break;
			case 20091019:
				if( i_ai0 == 0 )
				{
					i_ai0 = 1;
				}
				despawn();
				int i0 = FieldCycleManager.getStep(FieldCycle_ID);
				if( i0 == 2 )
				{
					spawn_defines.get(0).spawn(spawn_defines.get(0).total, boss_respawn_time, 0);
				}
				break;
		}
	}
}