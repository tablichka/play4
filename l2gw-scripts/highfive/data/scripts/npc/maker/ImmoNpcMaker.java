package npc.maker;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.SpawnDefine;

/**
 * @author: rage
 * @date: 13.12.11 19:09
 */
public class ImmoNpcMaker extends FieldcycleMaker
{
	public int seq = 0;
	public String type = "";
	public int zone = 0;
	public int TM_check_point = 78001;
	public int TIME_check_point = 60;
	public int TID_SEED_SPAWN_CHECK = 78002;
	public int TID_NPC_SPAWN_CHECK = 78003;

	public ImmoNpcMaker(int maximum_npc, String name)
	{
		super(maximum_npc, name);
		FieldCycle = 3;
		Threshold_Min = 0;
		Threshold_Max = 5;
		Point_Min = 0;
		Point_Max = 5000000;
	}

	@Override
	public void onStart()
	{
		if(FieldCycle != -1)
		{
			FieldCycleManager.registerStepChanged(FieldCycle, this);
			addTimer(TM_check_point, TIME_check_point * 1000);
			addTimer(TID_NPC_SPAWN_CHECK, 1000);
		}
	}

	public void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(FieldCycle != -1)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			long i1 = FieldCycleManager.getPoint(FieldCycle);
			if(timerId == TID_NPC_SPAWN_CHECK)
			{
				if(i0 >= Threshold_Min && i0 <= Threshold_Max && i1 >= Point_Min && i1 <= Point_Max)
				{
					if(type.equals("seed_energy"))
					{
						for(i0 = npc_count; i0 < maximum_npc; i0++)
						{
							SpawnDefine def0 = spawn_defines.get(Rnd.get(spawn_defines.size()));
							if(def0 != null)
							{
								if(maximum_npc > npc_count)
								{
									if(atomicIncrease(def0, 1))
									{
										def0.spawn(1, 0, 0);
									}
								}
							}
						}
					}
					else
					{
						onScriptEvent(1001, 0, 0);
					}
				}
				else if(npc_count > 0)
				{
					onScriptEvent(1000, 0, 0);
				}
			}
			else if(timerId == TM_check_point)
			{
				if(i0 <= 0 || i0 > 5 || i1 >= 5000000)
				{
					FieldCycleManager.setStep("npc_" + 1, FieldCycle, 1);
				}
				addTimer(TM_check_point, TIME_check_point * 1000);
			}
		}
	}

	@Override
	public void onFieldCycleChanged(int fieldId, int oldStep, int newStep)
	{
		if(fieldId == FieldCycle)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			long i1 = FieldCycleManager.getPoint(FieldCycle);
			if(i0 <= 0 || i0 > 5 || i1 >= 5000000)
			{
				FieldCycleManager.setStep("npc_" + 1, FieldCycle, 1);
			}
			else
			{
				addTimer(TID_NPC_SPAWN_CHECK, 1000);
			}
		}
	}

	@Override
	public void onNpcDeleted(L2NpcInstance npc)
	{
		SpawnDefine deleted_def = npc.getSpawnDefine();
		if(deleted_def.respawn != 0)
		{
			int i0 = FieldCycleManager.getStep(FieldCycle);
			long i1 = FieldCycleManager.getPoint(FieldCycle);
			if(i0 >= Threshold_Min && i0 <= Threshold_Max && i1 >= Point_Min && i1 <= Point_Max)
			{
				if(!type.equals("seed_energy"))
				{
					if(maximum_npc > npc_count)
					{
						if(atomicIncrease(deleted_def, 1))
						{
							deleted_def.respawn(npc, deleted_def.respawn, deleted_def.respawn_rand);
						}
					}
				}
				else
				{
					FieldCycleManager.addPoint("npc_" + 5, 3, 100, npc);
					if(maximum_npc > npc_count)
					{
						SpawnDefine def0 = spawn_defines.get(Rnd.get(spawn_defines.size()));
						if(def0 != null && atomicIncrease(def0, 1))
						{
							def0.spawn(1, deleted_def.respawn, deleted_def.respawn_rand);
						}
					}
				}
			}
		}
	}
}