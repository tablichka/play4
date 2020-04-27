package ai;

import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 15.10.11 0:33
 */
public class EscortCheckerQ0457 extends Citizen
{
	public int escort_checker_q0457_TIMER = 1111;

	public EscortCheckerQ0457(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(escort_checker_q0457_TIMER, (10 * 1000));
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == escort_checker_q0457_TIMER)
		{
			broadcastScriptEvent(45705, _thisActor.getStoredId(), null, 500);
			addTimer(escort_checker_q0457_TIMER, 2 * 1000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}