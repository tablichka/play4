package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 16:24
 */
public class WarriorPhysicalspecialBuff extends Warrior
{
	public L2Skill Buff = SkillTable.getInstance().getInfo(263979009);
	public L2Skill PhysicalSpecial = SkillTable.getInstance().getInfo(264241153);

	public WarriorPhysicalspecialBuff(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor) && _thisActor.getMostHated() == null)
		{
			addAttackDesire(creature, 1, 1000);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.i_ai1 == 0 && Rnd.get(100) < 10 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 50)
			{
				if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
				{
					addUseSkillDesire(_thisActor, Buff, 1, 1, 1000000);
				}
				_thisActor.i_ai1 = 1;
			}
			else if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 10 && _thisActor.getMostHated() == attacker)
				{
					if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial, 0, 1, 1000000);
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
			if(Rnd.get(100) < 10)
			{
				if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
				{
					addUseSkillDesire(attacker, PhysicalSpecial, 0, 1, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}