package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 15.09.11 17:21
 */
public class WarriorPhysicalspecial extends Warrior
{
	public L2Skill PhysicalSpecial = SkillTable.getInstance().getInfo(264241153);

	public WarriorPhysicalspecial(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
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
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && !_thisActor.isMoving)
		{
			if(Rnd.get(100) < 33)
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
