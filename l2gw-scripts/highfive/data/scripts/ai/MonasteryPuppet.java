package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 14.10.11 22:06
 */
public class MonasteryPuppet extends DefaultNpc
{
	public int TIMER = 1000;

	public MonasteryPuppet(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(TIMER, 5000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER)
		{
			broadcastScriptEvent(21140014, _thisActor.getStoredId(), null, 400);
			addTimer(TIMER, 30000);
		}
	}
}