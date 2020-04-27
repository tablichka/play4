package ai;

import ai.base.PartyPrivate;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 23.01.12 17:34
 */
public class RoyalGuardAnt extends PartyPrivate
{
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);

	public RoyalGuardAnt(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.getZ() - _thisActor.getZ() > 5 || (attacker.getZ() - _thisActor.getZ()) < -500)
		{
		}
		else if(attacker.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(SkillTable.getAbnormalLevel(attacker, different_level_9_attacked) == -1)
			{
				if(different_level_9_attacked.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_attacked, attacker);
					removeAttackDesire(attacker);
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_attacked, attacker);
				}
			}
		}
		else if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(damage == 0)
			{
				damage = 1;
			}
			_thisActor.addDamageHate(attacker, damage, (long) (damage / (_thisActor.getLevel() + 7) * 20000));
			_thisActor.callFriends(attacker, damage);
			addAttackDesire(attacker, 1, DEFAULT_DESIRE);
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character speller)
	{
		if(speller.getZ() - _thisActor.getZ() > 5 || (speller.getZ() - _thisActor.getZ()) < -500)
		{
		}
		else if(speller.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(SkillTable.getAbnormalLevel(speller, different_level_9_see_spelled) == -1)
			{
				if(different_level_9_see_spelled.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_see_spelled, speller);
					removeAttackDesire(speller);
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_see_spelled, speller);
				}
			}
		}
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.getZ() - _thisActor.getZ() > 5 || (creature.getZ() - _thisActor.getZ()) < -500)
		{
		}
		if(creature.getLevel() > (_thisActor.getLevel() + 8))
		{
			if(SkillTable.getAbnormalLevel(creature, different_level_9_attacked) == -1)
			{
				if(different_level_9_attacked.getId() == 4515)
				{
					_thisActor.altUseSkill(different_level_9_attacked, creature);
					removeAttackDesire(creature);
				}
				else
				{
					_thisActor.altUseSkill(different_level_9_attacked, creature);
				}
			}
		}
		else
		{
			super.onEvtSeeCreature(creature);
		}
	}
}