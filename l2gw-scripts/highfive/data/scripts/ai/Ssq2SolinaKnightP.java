package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 06.10.11 16:36
 */
public class Ssq2SolinaKnightP extends WarriorUseSkill
{
	public Ssq2SolinaKnightP(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(2201, 5000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2201)
		{
			if(!_thisActor.inMyTerritory(_thisActor) && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				clearTasks();
				_thisActor.stopMove();
			}
			_thisActor.lookNeighbor(300);
			addTimer(2201, 5000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}