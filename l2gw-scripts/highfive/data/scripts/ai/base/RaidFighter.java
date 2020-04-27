package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 20.01.12 20:46
 */
public class RaidFighter extends RaidPrivateStandard
{
	public L2Skill PhysicalSpecial_a = SkillTable.getInstance().getInfo(458752001);

	public RaidFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(_thisActor.getMostHated() != null)
		{
			if(((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && attacker == _thisActor.getMostHated()) && Rnd.get(15) < 1)
			{
				addUseSkillDesire(attacker, PhysicalSpecial_a, 0, 1, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster == _thisActor.getMostHated() && Rnd.get(15) < 1)
		{
			addUseSkillDesire(caster, PhysicalSpecial_a, 0, 1, 1000000);
		}
		super.onEvtSeeSpell(skill, caster);
	}
}