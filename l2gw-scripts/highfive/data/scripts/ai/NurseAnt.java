package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 23.01.12 16:33
 */
public class NurseAnt extends DefaultNpc
{
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);

	public NurseAnt(L2Character actor)
	{
		super(actor);
		_thisActor.setRunning();
	}

	@Override
	protected void onEvtNoDesire()
	{
		if(_thisActor.isMyBossAlive())
		{
			addFollowDesire(_thisActor.getLeader(), 20);
		}
		else
		{
			addMoveAroundDesire(40, 20);
		}
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(debug)
			_log.info(_thisActor + " onEvtPartyAttacked: " + attacker + " --> " + victim + " damage: " + damage);
		if(victim.getNpcId() == 29001)
		{
			if(debug)
				_log.info(_thisActor + " onEvtPartyAttacked: " + attacker + " --> " + victim + " top_desire_target: " + (getAttackTarget() != null ? getAttackTarget().getNpcId() : "null"));

			if(victim.getCurrentHp() - damage < victim.getMaxHp())
			{
				if(_thisActor.getLoc().distance3D(victim.getLoc()) > 2500 && getAttackTarget() != null && getAttackTarget().getNpcId() == 29002)
				{
				}
				else
				{
					addUseSkillDesire(victim, 263454721, 1, 1, 1000000);
				}
			}
		}
		if(victim.getNpcId() == 29002)
		{
			if(victim.getCurrentHp() - damage < victim.getMaxHp())
			{
				addUseSkillDesire(victim, 263454721, 1, 1, 101);
				addUseSkillDesire(victim, 263716865, 1, 1, 100);
			}
			else
			{
				if(debug)
					_log.info(_thisActor + " onEvtPartyAttacked: " + attacker + " --> " + victim + " HP is full");

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

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
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
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.getZ() - _thisActor.getZ() > 5 || (creature.getZ() - _thisActor.getZ()) < -500)
		{
		}
		else if(creature.getLevel() > (_thisActor.getLevel() + 8))
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