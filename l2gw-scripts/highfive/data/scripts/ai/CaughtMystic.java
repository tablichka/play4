package ai;

import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

public class CaughtMystic extends Mystic
{
	private static final int TIME_TO_LIVE = 60000;
	private final long TIME_TO_DIE = System.currentTimeMillis() + TIME_TO_LIVE;

	public CaughtMystic(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(System.currentTimeMillis() >= TIME_TO_DIE)
		{
			((L2NpcInstance) _actor).deleteMe();
			return false;
		}
		return super.thinkActive();
	}
}
