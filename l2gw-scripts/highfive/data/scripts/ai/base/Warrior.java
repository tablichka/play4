package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 06.09.11 16:43
 */
public class Warrior extends WarriorParameter
{
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;

	public Warrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if( ShoutMsg1 > 0 )
		{
			if( IsSay == 0 )
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, ShoutMsg1);
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg1);
			}
		}
		if( MoveAroundSocial > 0 || ShoutMsg2 > 0 || ShoutMsg3 > 0 )
		{
			addTimer(1001, 10000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 1001 )
		{
			if( !_thisActor.isMoving && _intention == CtrlIntention.AI_INTENTION_ACTIVE && _thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.400000 && !_thisActor.isDead())
			{
				if( MoveAroundSocial > 0 || MoveAroundSocial1 > 0 || MoveAroundSocial2 > 0 )
				{
					if( MoveAroundSocial2 > 0 && Rnd.get(100) < 20 )
					{
						addEffectActionDesire( 3,  ( MoveAroundSocial2 * 1000 ) / 30 , 50);
					}
					else if( MoveAroundSocial1 > 0 && Rnd.get(100) < 20 )
					{
						addEffectActionDesire( 2,  ( MoveAroundSocial1 * 1000 ) / 30 , 50);
					}
					else if( MoveAroundSocial > 0 && Rnd.get(100) < 20 )
					{
						addEffectActionDesire( 1,  ( MoveAroundSocial * 1000 ) / 30 , 50);
					}
				}
				if( ShoutMsg2 > 0 && Rnd.get(1000) < 17 )
				{
					if( IsSay == 0 )
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, ShoutMsg2);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg2);
					}
				}
			}
			else if( !_thisActor.isMoving )
			{
				if( ShoutMsg3 > 0 && Rnd.get(100) < 10 )
				{
					if( IsSay == 0 )
					{
						Functions.npcSay(_thisActor, Say2C.SHOUT, ShoutMsg3);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg3);
					}
				}
			}
			addTimer(1001, 10000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if( attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) )
		{
			float f0 = 0;
			if( SetHateGroup >= 0 )
			{
				if( CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()) )
				{
					f0 += SetHateGroupRatio;
				}
			}
			if( attacker.getActiveClass() == SetHateOccupation )
			{
				f0 += SetHateOccupationRatio;
			}
			if( attacker.isPlayer() && SetHateRace == ((L2Player) attacker).getRace().ordinal() )
			{
				f0 += SetHateRaceRatio;
			}
			f0 = damage / (_thisActor.getLevel() + 7) + f0 / 100 * damage / (_thisActor.getLevel() + 7);
			addAttackDesire(attacker, 1, (long) (f0 * 100));
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if( _thisActor.getLifeTime() > 7 )
		{
			float f0 = 0;
			if( SetHateGroup >= 0 )
			{
				if( CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()) )
				{
					f0 += SetHateGroupRatio;
				}
			}
			if( attacker.getActiveClass() == SetHateOccupation )
			{
				f0 += SetHateOccupationRatio;
			}
			if( attacker.isPlayer() && SetHateRace == ((L2Player) attacker).getRace().ordinal() )
			{
				f0 += SetHateRaceRatio;
			}
			f0 = damage / (_thisActor.getLevel() + 7) + f0 / 100 * damage / (_thisActor.getLevel() + 7);
			addAttackDesire(attacker, 1, (long) (f0 * 100));
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		L2Character target = caster.getCastingTarget();
		if(skill.getEffectPoint() > 0)
		{
			if(!_thisActor.isMoving && _thisActor.getMostHated() == target)
			{
				int i0 = skill.getEffectPoint();
				float f0 = 0;
				if(SetHateGroup >= 0)
				{
					if(CategoryManager.isInCategory(SetHateGroup, caster))
					{
						f0 += SetHateGroupRatio;
					}
				}
				if(caster.getActiveClass() == SetHateOccupation)
				{
					f0 += SetHateOccupationRatio;
				}

				if(caster.isPlayer() && SetHateRace == ((L2Player) caster).getRace().ordinal())
				{
					f0 += SetHateRaceRatio;
				}

				f0 = i0 / (_thisActor.getLevel() + 7) + f0 / 100 * i0 / (_thisActor.getLevel() + 7);
				addAttackDesire(caster, 1, (long) (f0 * 150));
			}
		}
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		addAttackDesire(target, aggro, 0);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if( ShoutMsg4 > 0 && Rnd.get(100) < 30 )
		{
			if( IsSay == 0 )
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, ShoutMsg4);
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.ALL, ShoutMsg4);
			}
		}
	}
}
