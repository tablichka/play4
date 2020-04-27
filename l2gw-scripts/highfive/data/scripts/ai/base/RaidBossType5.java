package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 17:52
 */
public class RaidBossType5 extends RaidBossParty
{
	public L2Skill RangeHold_a = null;

	public RaidBossType5(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && SkillTable.getAbnormalLevel(attacker, RangeHold_a) == -1) && Rnd.get(10 * 15) < 1)
		{
			addUseSkillDesire(attacker, RangeHold_a, 0, 1, 1000000);
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(SkillTable.getAbnormalLevel(caster, RangeHold_a) == -1 && Rnd.get(10 * 15) < 1)
		{
			addUseSkillDesire(caster, RangeHold_a, 0, 1, 1000000);
		}
		super.onEvtSeeSpell(skill, caster);
	}
}