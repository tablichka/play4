package ai.rr;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 19.01.12 17:59
 */
public class RoyalRushTreasurebox extends RoyalRushDefaultNpc
{
	public RoyalRushTreasurebox(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(3001, 300000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		addFleeDesire(attacker, 200);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3001)
		{
			_thisActor.onDecay();
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}