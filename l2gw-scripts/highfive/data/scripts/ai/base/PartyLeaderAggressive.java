package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 21.09.11 20:00
 */
public class PartyLeaderAggressive extends PartyLeader
{
	public PartyLeaderAggressive(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			return;
		}
		if(SeeCreatureAttackerTime == -1)
		{
			if(SetAggressiveTime == -1)
			{
				if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
				{
					addAttackDesire(creature, 1, 200);
				}
			}
			else if(SetAggressiveTime == 0)
			{
				if(_thisActor.inMyTerritory(_thisActor))
				{
					addAttackDesire(creature, 1, 200);
				}
			}
			else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
			{
				addAttackDesire(creature, 1, 200);
			}
		}
		else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
		{
			addAttackDesire(creature, 1, 200);
		}
		super.onEvtSeeCreature(creature);
	}
}
