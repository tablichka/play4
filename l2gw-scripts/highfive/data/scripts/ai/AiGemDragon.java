package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 14:46
 */
public class AiGemDragon extends DetectPartyWarrior
{
	public L2Skill detectSkill = SkillTable.getInstance().getInfo(450691073);

	public AiGemDragon(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(skill == detectSkill)
		{
			L2Character target = caster.getCastingTarget();
			if(target != null && _thisActor.isInRange(caster, 600))
				addUseSkillDesire(target, detectSkill, 0, 1, 99999999900000000L);
		}
	}
}
