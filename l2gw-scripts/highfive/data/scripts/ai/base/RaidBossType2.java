package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 23.09.11 16:35
 */
public class RaidBossType2 extends RaidBossParty
{
	public L2Skill RangeDDMagic_a = null;

	public RaidBossType2(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && Rnd.get((10 * 15)) < 1)
		{
			addUseSkillDesire(attacker, RangeDDMagic_a, 0, 1, 1000000);
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(Rnd.get((10 * 15)) < 1)
		{
			addUseSkillDesire(caster, RangeDDMagic_a, 0, 1, 1000000);
		}
		super.onEvtSeeSpell(skill, caster);
	}
}
