package ai;

import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;

/**
 * Моб Mystic не использует рандом валк
 *
 * @author SYS
 */
public class NoRndWalkMystic extends Mystic
{
	public NoRndWalkMystic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}