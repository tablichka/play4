package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * Моб Fighter не использует рандом валк
 *
 * @author SYS
 */
public class NoRndWalkFighter extends Fighter
{
	public NoRndWalkFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}