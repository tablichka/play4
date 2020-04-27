package ai.rr;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.01.12 14:49
 */
public class RoyalRushDefaultNpc extends DefaultNpc
{
	public RoyalRushDefaultNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(3000, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3000)
		{
			int i0 = Calendar.getInstance().get(Calendar.MINUTE);
			int i1 = Calendar.getInstance().get(Calendar.SECOND);
			if(i0 == 54 && i1 == 0)
			{
				_thisActor.onDecay();
			}
			else
			{
				addTimer(3000, 1000);
			}
		}
	}
}