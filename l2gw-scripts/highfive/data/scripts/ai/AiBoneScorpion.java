package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 03.09.11 13:30
 */
public class AiBoneScorpion extends DetectPartyWarrior
{
	public L2Skill bossSkill = SkillTable.getInstance().getInfo(449708033);
	public L2Skill poisonSkill = SkillTable.getInstance().getInfo(449904641);

	public AiBoneScorpion(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(skill == bossSkill)
		{
			L2Character target = caster.getCastingTarget();
			if(target != null && _thisActor.isInRange(caster, 600))
				addUseSkillDesire(target, poisonSkill, 0, 1, 99999999900000000L);
		}
	}
}
