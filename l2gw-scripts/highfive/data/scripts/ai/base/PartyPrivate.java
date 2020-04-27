package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 21.09.11 19:30
 */
public class PartyPrivate extends PartyPrivateParam
{
	public L2Skill DDMagic = SkillTable.getInstance().getInfo(458752001);

	public PartyPrivate(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1005, 120000);
		addTimer(1006, 20000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1005)
		{
			if(!_thisActor.inMyTerritory(_thisActor) && _thisActor.isMyBossAlive() && !_thisActor.isMoving)
			{
				_thisActor.teleToLocation(Location.coordsRandomize(_thisActor.getLeader(), 50, 100));
				removeAllAttackDesire();
			}
			addTimer(1005, 120000);
		}
		else if(timerId == 1006)
		{
			if(!_thisActor.isMyBossAlive())
			{
				if(!_thisActor.isMoving)
				{
					_thisActor.onDecay();
					return;
				}
			}
			addTimer(1006, 20000);
		}
		else if(timerId == 1004)
		{
			if(!_thisActor.isMoving)
			{
				_thisActor.onDecay();
			}
			else
			{
				addTimer(1004, 20000);
			}
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(debug)
			_log.info(_thisActor + " onEvtPartyAttacked: " + attacker + " --> " + victim + " damge: " + damage);

		if(victim != _thisActor)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				float f0 = 0;
				if(SetHateGroup >= 0)
				{
					if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
					{
						f0 += SetHateGroupRatio;
					}
				}
				if(attacker.getActiveClass() == SetHateOccupation)
				{
					f0 += SetHateOccupationRatio;
				}
				if(SetHateRace == attacker.getPlayer().getRace().ordinal())
				{
					f0 += SetHateRaceRatio;
				}
				f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
				if(((L2NpcInstance) victim).weight_point < 1)
					((L2NpcInstance) victim).weight_point = 1;
				_thisActor.addDamageHate(attacker, 0, (long) (f0 * damage * ((L2NpcInstance) victim).weight_point * 10));
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(!_thisActor.isMyBossAlive())
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				float f0 = 0;
				if(SetHateGroup >= 0)
				{
					if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
					{
						f0 += SetHateGroupRatio;
					}
				}
				if(attacker.getActiveClass() == SetHateOccupation)
				{
					f0 += SetHateOccupationRatio;
				}
				if(SetHateRace == attacker.getPlayer().getRace().ordinal())
				{
					f0 += SetHateRaceRatio;
				}
				f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
				addAttackDesire(attacker, 1, (long) (f0 * 100));
			}
		}
		else if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			float f0 = 0;
			if(SetHateGroup >= 0)
			{
				if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
				{
					f0 += SetHateGroupRatio;
				}
			}
			if(attacker.getActiveClass() == SetHateOccupation)
			{
				f0 += SetHateOccupationRatio;
			}
			if(SetHateRace == attacker.getPlayer().getRace().ordinal())
			{
				f0 += SetHateRaceRatio;
			}
			f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
			_thisActor.addDamageHate(attacker, 0, (long) (f0 * 100));
			addAttackDesire(attacker, 1, DEFAULT_DESIRE);
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(!_thisActor.isMyBossAlive() && _thisActor.getLifeTime() > 7)
		{
			if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				float f0 = 0;
				if(SetHateGroup >= 0)
				{
					if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
					{
						f0 += SetHateGroupRatio;
					}
				}
				if(attacker.getActiveClass() == SetHateOccupation)
				{
					f0 += SetHateOccupationRatio;
				}
				if(SetHateRace == attacker.getPlayer().getRace().ordinal())
				{
					f0 += SetHateRaceRatio;
				}
				f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
				_thisActor.addDamageHate(attacker, 0, (long) (f0 * 30));
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
			}
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		L2Character target = caster.getCastingTarget();

		if(target != null && !_thisActor.isMyBossAlive())
		{
			if(skill.getEffectPoint() > 0)
			{
				if(!_thisActor.isMoving && _thisActor.getMostHated() == target)
				{
					int i0 = skill.getEffectPoint();
					float f0 = 0;
					if(SetHateGroup >= 0)
					{
						if(CategoryManager.isInCategory(SetHateGroup, caster.getActiveClass()))
						{
							f0 += SetHateGroupRatio;
						}
					}
					if(caster.getActiveClass() == SetHateOccupation)
					{
						f0 += SetHateOccupationRatio;
					}
					if(SetHateRace == caster.getPlayer().getRace().ordinal())
					{
						f0 += SetHateRaceRatio;
					}
					f0 = (float) (1.000000 * i0 / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * i0 / (_thisActor.getLevel() + 7));
					_thisActor.addDamageHate(caster, 0, (long) (f0 * 150));
					addAttackDesire(caster, 1, DEFAULT_DESIRE);
				}
			}
			//if( myself.GetPathfindFailCount() > 10 && speller == _thisActor.getMostHated() && (_thisActor.getCurrentHp()) != (_thisActor.getMaxHp()) )
			//{
			//	_thisActor.teleToLocation( speller.creature, speller.hate, (speller.z));
			//}
		}
	}

	@Override
	protected void onEvtPartyDead(L2NpcInstance partyPrivate)
	{
		if(partyPrivate == _thisActor.getLeader())
		{
			addTimer(1004, 20000);
		}
	}
}