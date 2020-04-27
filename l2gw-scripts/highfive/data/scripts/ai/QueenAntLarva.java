package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 23.01.12 16:50
 */
public class QueenAntLarva extends DefaultNpc
{
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);

	public QueenAntLarva(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
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
		_thisActor.callFriends(attacker, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character speller)
	{
		if(speller.getZ() - _thisActor.getZ() > 5 || (speller.getZ() - _thisActor.getZ()) < -500)
		{
		}
		else if(speller.getLevel() > _thisActor.getLevel() + 8 && _thisActor.isInRange(speller, 300))
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
	protected void onEvtPartyDead(L2NpcInstance victim)
	{
		if(victim != _thisActor)
		{
			if(victim == _thisActor.getLeader())
			{
				_thisActor.onDecay();
			}
		}
	}
}