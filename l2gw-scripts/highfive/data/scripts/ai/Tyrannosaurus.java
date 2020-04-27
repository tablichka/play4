package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * АИ для мобов Tyrannosaurus
 *
 * @author SYS
 */
public class Tyrannosaurus extends Fighter
{
	public Tyrannosaurus(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean isSilent(L2Character target)
	{
		// Tyrannosaurus всегда видит игроков в режиме Silent Move
		return false;
	}
}