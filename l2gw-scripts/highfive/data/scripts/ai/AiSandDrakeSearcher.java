package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 06.09.11 13:09
 */
public class AiSandDrakeSearcher extends DetectPartyWizard
{

	public AiSandDrakeSearcher(L2Character actor)
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
			if( CategoryManager.isInCategory(15, creature) )
			{
				addAttackDesire(creature, 1, 900000000000000L);
			}
			else
			{
				addAttackDesire(creature, 1, 900000);
			}
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		addAttackDesire(target, 999999999, 0);
		super.onEvtManipulation(target, aggro, skill);
	}
}
