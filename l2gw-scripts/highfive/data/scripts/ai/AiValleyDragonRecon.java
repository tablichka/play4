package ai;

import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 03.09.11 15:42
 */
public class AiValleyDragonRecon extends DetectPartyWarrior
{
	public AiValleyDragonRecon(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
			addAttackDesire(creature, 0, 9999);
	}
}
