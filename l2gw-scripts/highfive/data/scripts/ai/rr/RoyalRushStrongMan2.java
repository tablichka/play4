package ai.rr;

import ai.base.Warrior;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 22.01.12 12:50
 */
public class RoyalRushStrongMan2 extends Warrior
{
	public L2Skill PhysicalSpecial1 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill DDMagic1 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill SelfBuff = SkillTable.getInstance().getInfo(264241153);

	public RoyalRushStrongMan2(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.lookNeighbor(300);
		if(SelfBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff.getId()))
		{
			addUseSkillDesire(_thisActor, SelfBuff, 1, 1, 1000000);
		}
		addTimer(6001, 60000);
		super.onEvtSpawn();
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
					if(PhysicalSpecial1.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial1.getId()))
					{
						addUseSkillDesire(creature, PhysicalSpecial1, 0, 1, 1000000);
					}
				}
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

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
				{
					if(PhysicalSpecial1.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial1.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial1, 0, 1, 1000000);
					}
				}
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
				{
					if(DDMagic1.getMpConsume() < _thisActor.getCurrentMp() && DDMagic1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DDMagic1.getId()))
					{
						addUseSkillDesire(attacker, DDMagic1, 0, 1, 1000000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if((_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))) && !_thisActor.isMoving)
		{
			if(Rnd.get(100) < 33)
			{
				if(PhysicalSpecial1.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial1.getId()))
				{
					addUseSkillDesire(attacker, PhysicalSpecial1, 0, 1, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 6001)
		{
			if(Calendar.getInstance().get(Calendar.MINUTE) >= 55)
			{
				_thisActor.onDecay();
			}
			addTimer(6001, 60000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		_thisActor.createOnePrivate(31455, "rr.RoyalRushKeybox", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
	}
}