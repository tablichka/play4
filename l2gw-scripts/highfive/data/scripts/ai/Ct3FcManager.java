package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.instancemanager.FieldCycleManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.fieldcycle.IFieldCycleMaker;

/**
 * @author: rage
 * @date: 12.12.11 13:54
 */
public class Ct3FcManager extends DefaultNpc implements IFieldCycleMaker
{
	public int RaceCycleID = 0;

	public Ct3FcManager(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		FieldCycleManager.registerStepExpired(RaceCycleID, this);
		int i0 = FieldCycleManager.getStep(RaceCycleID);
		if(i0 == 0)
		{
			FieldCycleManager.setStep("npc_" + 8, RaceCycleID, 1, _thisActor);
		}
	}

	@Override
	public void onFieldCycleExpired(int fieldId, int oldStep, int newStep)
	{
		if(fieldId == RaceCycleID)
		{
			if(oldStep == 2)
			{
				if(newStep == 0)
				{
					FieldCycleManager.setStep("npc_" + 8, RaceCycleID, 1, _thisActor);
				}
			}
			else if(oldStep == 1)
			{
				if(newStep == 0)
				{
					FieldCycleManager.setStep("npc_" + 8, RaceCycleID, 2, _thisActor);
				}
			}
		}
	}

	@Override
	public void onFieldCycleChanged(int fieldId, int oldStep, int newStep)
	{
	}
}