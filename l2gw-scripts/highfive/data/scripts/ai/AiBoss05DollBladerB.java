package ai;

import ai.base.WarriorAggressiveCastingEnchantClan;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 15.09.11 16:54
 */
public class AiBoss05DollBladerB extends WarriorAggressiveCastingEnchantClan
{
	public L2Skill Buff = SkillTable.getInstance().getInfo(266993666);
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);

	public AiBoss05DollBladerB(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.lookNeighbor(1500);
		addTimer(1051, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1051)
		{
			if(!_thisActor.isInRange(_thisActor.getSpawnedLoc(), 15000))
			{
				clearTasks();
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				addTimer(1052, 3000);
			}
			addTimer(1051, 10000);
		}
		if(timerId == 1052)
		{
			_thisActor.lookNeighbor(1000);
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(creature.getAbnormalLevelByType(different_level_9_attacked.getId()) == -1)
			{
				if(different_level_9_attacked.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_attacked, creature);
					removeAttackDesire(creature);
					return;
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_attacked, creature);
				}
			}
		}
		if(creature.getZ() > (_thisActor.getZ() - 100) && creature.getZ() < (_thisActor.getZ() + 100))
		{
			if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
			{
				return;
			}
			if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
			{
				addAttackDesire(creature, 1, 200);
			}
			addAttackDesire(creature, 9, 1);
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(attacker.getAbnormalLevelByType(different_level_9_attacked.getId()) == -1)
			{
				if(different_level_9_attacked.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_attacked, attacker);
					removeAttackDesire(attacker);
					return;
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_attacked, attacker);
				}
			}
		}
		if(attacker.getLevel() <= (_thisActor.getLevel() + 8))
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				if(damage == 0)
				{
					damage = 1;
				}
				addAttackDesire(attacker, 1, (long) (damage / (float) _thisActor.getLevel() + 7 * 20000));
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(caster.getAbnormalLevelByType(different_level_9_attacked.getId()) == -1)
			{
				if(different_level_9_attacked.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_see_spelled, caster);
					removeAttackDesire(caster);
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_see_spelled, caster);
				}
			}
		}
	}
}
