package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 07.09.11 15:08
 */
public class WarriorFlee extends Warrior
{
	public WarriorFlee(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		addFleeDesire(attacker, 30);
	}

}
