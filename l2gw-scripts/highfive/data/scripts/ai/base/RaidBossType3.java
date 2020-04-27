package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 17:49
 */
public class RaidBossType3 extends RaidBossParty
{
	public L2Skill PhysicalSpecial_b = null;

	public RaidBossType3(L2Character actor)
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
				addUseSkillDesire(attacker, PhysicalSpecial_b, 0, 1, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(_thisActor.getMostHated() != null)
		{
			if(caster == _thisActor.getMostHated() && Rnd.get(15) < 1)
			{
				addUseSkillDesire(caster, PhysicalSpecial_b, 0, 1, 1000000);
			}
		}
		super.onEvtSeeSpell(skill, caster);
	}
}