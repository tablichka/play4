package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 14.10.11 22:13
 */
public class AiDivineWise extends WarriorUseSkill
{
	public AiDivineWise(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(413466625);
		Skill03_ID = SkillTable.getInstance().getInfo(413925377);
		Skill03_Target = 3;
		Skill03_Type = 3;
		Skill03_HighHP = 30;
	}
}