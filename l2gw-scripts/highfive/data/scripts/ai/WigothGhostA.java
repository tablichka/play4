package ai;

import ru.l2gw.gameserver.model.L2Character;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.01.12 18:25
 */
public class WigothGhostA extends Citizen
{
	public WigothGhostA(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 62001)
		{
			int i0 = Calendar.getInstance().get(Calendar.MINUTE);
			if(i0 >= 54)
			{
				_thisActor.onDecay();
			}
			else
			{
				addTimer(62001, 1000);
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(62001, 1000);
		super.onEvtSpawn();
	}
}