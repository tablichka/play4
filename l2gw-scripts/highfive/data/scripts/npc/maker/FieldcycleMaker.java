package npc.maker;

import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.entity.fieldcycle.IFieldCycleMaker;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 13.12.11 19:11
 */
public class FieldcycleMaker extends DefaultMaker implements IFieldCycleMaker
{
	public int FieldCycle = -1;
	public int Threshold_Min = -1;
	public int Threshold_Max = 100;
	public int Point_Min = -1;
	public int Point_Max = 2147483647;

	public FieldcycleMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
	}

	@Override
	public void onStart()
	{
		if(FieldCycle != -1)
		{
			FieldCycleManager.registerStepChanged(FieldCycle, this);
			int i0 = FieldCycleManager.getStep(FieldCycle);
			long i1 = FieldCycleManager.getPoint(FieldCycle);
			if(i0 >= Threshold_Min && i0 <= Threshold_Max && i1 >= Point_Min && i1 <= Point_Max)
			{
				i_ai0 = 1;
				if(on_start_spawn == 0)
				{
					return;
				}
				for(SpawnDefine def0 : spawn_defines)
				{
					if(def0 != null)
					{
						if(atomicIncrease(def0, def0.total))
						{
							def0.spawn(def0.total, 0, 0);
						}
					}
				}
			}
			else
			{
				i_ai0 = 0;
			}
		}
	}

	@Override
	public void onFieldCycleChanged(int fieldId, int oldStep, int newStep)
	{
		if(fieldId == FieldCycle)
		{
			long i1 = FieldCycleManager.getPoint(FieldCycle);
			if(newStep >= Threshold_Min && newStep <= Threshold_Max && i1 >= Point_Min && i1 <= Point_Max)
			{
				if(i_ai0 == 0)
				{
					onScriptEvent(1001, 0, 0);
					i_ai0 = 1;
				}
			}
			else
			{
				onScriptEvent(1000, 0, 0);
				i_ai0 = 0;
			}
		}
	}

	@Override
	public void onFieldCycleExpired(int fieldId, int oldStep, int newStep)
	{}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(deleted_def.respawn != 0)
		{
			int i3 = FieldCycleManager.getStep(FieldCycle);
			long i4 = FieldCycleManager.getPoint(FieldCycle);
			if(i3 >= Threshold_Min && i3 <= Threshold_Max && i4 >= Point_Min && i4 <= Point_Max)
			{
				if(atomicIncrease(deleted_def, 1))
				{
					deleted_def.respawn(npc, deleted_def.respawn, deleted_def.respawn_rand);
				}
			}
		}
	}

	@Override
	public void onScriptEvent(int eventId, Object arg1, Object arg2)
	{
		int i2 = FieldCycleManager.getStep(FieldCycle);
		long i3 = FieldCycleManager.getPoint(FieldCycle);
		if(i2 >= Threshold_Min && i2 <= Threshold_Max && i3 >= Point_Min && i3 <= Point_Max)
		{
			switch(eventId)
			{
				case 1000:
					despawn();
					break;
				case 1001:
					for(SpawnDefine def0 : spawn_defines)
					{
						if(def0 != null)
						{
							int i1 = (def0.total - def0.npc_count);
							if(i1 > 0)
							{
								if(atomicIncrease(def0, i1))
								{
									def0.spawn(i1, (Integer) arg1, 0);
								}
							}
						}
					}
					break;
			}
		}
		else if(eventId == 1000)
		{
			despawn();
		}
	}

	@Override
	public void onNpcCreated(L2NpcInstance npc)
	{
		int i0 = FieldCycleManager.getStep(FieldCycle);
		long i1 = FieldCycleManager.getPoint(FieldCycle);
		if(i0 < Threshold_Min || i0 > Threshold_Max || i1 < Point_Min || i1 > Point_Max)
		{
			npc.onDecay();
		}
	}
}