package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 03.09.11 16:04
 */
public class AiMalukMaiden extends WarriorUseSkill
{
	public L2Skill SelfHealSkill = SkillTable.getInstance().getInfo(448724993);

	public AiMalukMaiden(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5 && Rnd.chance(10))
			addUseSkillDesire(_thisActor, SelfHealSkill, 1, 0, 999999999L);

		super.onEvtAttacked(attacker, damage, skill);
	}
}
