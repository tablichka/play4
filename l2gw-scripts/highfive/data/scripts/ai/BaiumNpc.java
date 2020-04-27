package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;

/**
 * AI каменной статуи Байума.
 *
 * @author rage
 */
public class BaiumNpc extends DefaultAI
{
	public BaiumNpc(L2Character actor)
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
		return false;
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}