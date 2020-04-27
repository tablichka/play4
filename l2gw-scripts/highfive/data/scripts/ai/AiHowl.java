package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 06.09.11 11:28
 */
public class AiHowl extends WarriorUseSkill
{
	public AiHowl(L2Character actor)
	{
		super(actor);
		SuperPointMethod = 0;
		SuperPointDesire = 50;
		SuperPointName = "";
	}

	@Override
	protected void onEvtSpawn()
	{
		if( SuperPointName != null && !SuperPointName.isEmpty() )
		{
			addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if( creature.isPlayer() )
		{
			addAttackDesire(creature, 1, 900000);
		}
		super.onEvtSeeCreature(creature);
	}
}
