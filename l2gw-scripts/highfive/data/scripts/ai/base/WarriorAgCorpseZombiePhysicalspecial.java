package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 29.09.11 20:14
 */
public class WarriorAgCorpseZombiePhysicalspecial extends WarriorCorpseZombiePhysicalspecial
{
	public WarriorAgCorpseZombiePhysicalspecial(L2Character actor)
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
		if(IsTeleport != null && _thisActor.getLifeTime() > 7 && _thisActor.getLoc().distance3D(creature.getLoc()) > 100 && !_thisActor.isMoving && Rnd.get(100) < 10 && _thisActor.getCurrentHp() > 0)
		{
			_thisActor.teleToLocation(creature.getX(), creature.getY(), creature.getZ());
			if(IsTeleport.getMpConsume() < _thisActor.getCurrentMp() && IsTeleport.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(IsTeleport.getId()))
			{
				addUseSkillDesire(_thisActor, IsTeleport, 1, 1, 1000000);
			}
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