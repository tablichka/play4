package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author rage
 * @date 30.11.2010 16:38:48
 */
public class EventTree extends DefaultAI
{
	public EventTree(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		addTimer(5000, 1200000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 5000)
			_thisActor.onDecay();
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
