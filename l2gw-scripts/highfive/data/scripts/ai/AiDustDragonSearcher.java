package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 03.09.11 14:00
 */
public class AiDustDragonSearcher extends DetectPartyWarrior
{
	public int SuperPointMethod = 0;
	public int SuperPointDesire = 50;
	public String SuperPointName = "";

	public AiDustDragonSearcher(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(!SuperPointName.isEmpty())
			addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			if(CategoryManager.isInCategory(15, creature))
			{
				_thisActor.addDamageHate(creature, 0, 9000000);
				addAttackDesire(creature, 1, 9000000000000L);
			}
			else
			{
				_thisActor.addDamageHate(creature, 0, 9000);
				addAttackDesire(creature, 1, 9000L);
			}
		}
	}
}
