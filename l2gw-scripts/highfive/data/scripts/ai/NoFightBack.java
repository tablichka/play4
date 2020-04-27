package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * Моб Fighter не использует рандом валк
 *
 * @author SYS
 */
public class NoFightBack extends Fighter
{
	public NoFightBack(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onIntentionAttack(L2Character target)
	{
	}

	@Override
	protected boolean createNewTask()
	{
		return false;
	}

	@Override
	protected boolean thinkActive()
	{
	 	return false;
	}
}