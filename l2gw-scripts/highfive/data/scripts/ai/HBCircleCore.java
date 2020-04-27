package ai;

import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author rage
 * @date 13.12.2010 17:07:53
 */
public class HBCircleCore extends L2CharacterAI
{
	public HBCircleCore(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(1000, 10000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1000)
		{
			broadcastScriptEvent(2245587, _actor, null, 900);
			addTimer(1000, 10000);
		}
	}
}
