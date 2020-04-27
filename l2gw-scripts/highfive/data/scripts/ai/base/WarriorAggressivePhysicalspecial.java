package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 15.09.11 17:23
 */
public class WarriorAggressivePhysicalspecial extends WarriorPhysicalspecial
{
	public WarriorAggressivePhysicalspecial(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			super.onEvtSeeCreature(creature);
			return;
		}
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor) && !_thisActor.isMoving)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == creature)
				{
					if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
					{
						addUseSkillDesire(creature, PhysicalSpecial, 0, 1, 1000000);
					}
				}
			}
		}
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
