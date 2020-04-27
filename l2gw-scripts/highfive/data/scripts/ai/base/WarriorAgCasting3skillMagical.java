package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 15.09.11 17:08
 */
public class WarriorAgCasting3skillMagical extends WarriorCasting3skillMagical
{
	public WarriorAgCasting3skillMagical(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor) && (creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId())))
		{
			int i6 = Rnd.get(100);
			if(_thisActor.getLoc().distance3D(creature.getLoc()) > 100 && i6 < 33 && !_thisActor.isMoving)
			{
				if(DDMagic.getMpConsume() < _thisActor.getCurrentMp() && DDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DDMagic.getId()))
				{
					addUseSkillDesire(creature, DDMagic, 0, 1, 1000000);
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
