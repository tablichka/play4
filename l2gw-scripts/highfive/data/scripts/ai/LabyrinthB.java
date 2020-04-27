package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 13.10.11 20:11
 */
public class LabyrinthB extends WarriorUseSkill
{
	public LabyrinthB(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(8255, 3000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 8255 )
		{
			if( _thisActor.getSpawnedLoc().getZ() - _thisActor.getZ() > 15 || _thisActor.getSpawnedLoc().getZ() - (_thisActor.getZ()) < -500 )
			{
				removeAllAttackDesire();
				_thisActor.teleToLocation( _thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			}
			addTimer(8255, 3000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	public boolean checkBossPosition()
	{
		return false;
	}
}
