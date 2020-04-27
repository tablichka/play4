package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 29.09.11 20:05
 */
public class WarriorCorpseZombieBasic extends Warrior
{
	public L2Skill SelfRangeDeBuff = SkillTable.getInstance().getInfo(274464769);
	public L2Skill IsTeleport = null;
	public int IsPrivate = 0;

	public WarriorCorpseZombieBasic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		if(IsPrivate == 1)
		{
			addTimer(1004, 20000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1004)
		{
			if(IsPrivate == 1 && !_thisActor.isMyBossAlive())
			{
				if(_intention == CtrlIntention.AI_INTENTION_ACTIVE)
				{
					_thisActor.onDecay();
					return;
				}
			}
			addTimer(1004, 20000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(Rnd.get(100) < 10)
		{
			if(SelfRangeDeBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDeBuff.getHpConsume() < _thisActor.getCurrentHp())
			{
				if(!_thisActor.isSkillDisabled(SelfRangeDeBuff.getId()))
				{
					addUseSkillDesire(_thisActor, SelfRangeDeBuff, 0, 1, 1000000);
				}
				else
				{
					addUseSkillDesire(_thisActor, SelfRangeDeBuff, 0, 1, 1000000);
				}
			}
			else
			{
				_thisActor.i_ai0 = 1;
				addAttackDesire(_thisActor, 1, 1000);
			}
		}
		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(IsTeleport != null)
		{
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100 && _thisActor.getCurrentHp() > 0)
			{
				if(_thisActor.getMostHated() == attacker && Rnd.get(100) < 10)
				{
					_thisActor.teleToLocation(attacker.getX(), attacker.getY(), attacker.getZ());
					if(IsTeleport.getMpConsume() < _thisActor.getCurrentMp() && IsTeleport.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(IsTeleport.getId()))
					{
						addUseSkillDesire(_thisActor, IsTeleport, 1, 1, 1000000);
					}
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
						_thisActor.addDamageHate(attacker, damage, (long) (f0 * 30));
						addAttackDesire(attacker, 1, DEFAULT_DESIRE);
					}
				}
			}
		}
		if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 10 && _thisActor.i_ai0 == 0)
		{
			if(Rnd.get(100) < 10)
			{
				if(SelfRangeDeBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDeBuff.getHpConsume() < _thisActor.getCurrentHp())
				{
					if(!_thisActor.isSkillDisabled(SelfRangeDeBuff.getId()))
					{
						addUseSkillDesire(_thisActor, SelfRangeDeBuff, 0, 1, 1000000);
					}
					else
					{
						addUseSkillDesire(_thisActor, SelfRangeDeBuff, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.i_ai0 = 1;
					addAttackDesire(_thisActor, 1, 1000);
				}
				_thisActor.i_ai0 = 1;
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(IsTeleport != null)
		{
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100 && !_thisActor.isMoving && Rnd.get(100) < 10 && _thisActor.getCurrentHp() > 0)
			{
				_thisActor.teleToLocation(attacker.getX(), attacker.getY(), attacker.getZ());
				if(IsTeleport.getMpConsume() < _thisActor.getCurrentMp() && IsTeleport.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(IsTeleport.getId()))
				{
					addUseSkillDesire(_thisActor, IsTeleport, 1, 1, 1000000);
				}
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
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}