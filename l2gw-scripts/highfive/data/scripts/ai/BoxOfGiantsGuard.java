package ai;

import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 31.08.2010 23:44:17
 */
public class BoxOfGiantsGuard extends Mystic
{
	public BoxOfGiantsGuard(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return target.isPlayer() && super.checkAggression(target);
	}
}
