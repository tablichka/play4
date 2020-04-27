package ai;

import ai.base.WizardUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 16:21
 */
public class AiDivineMagician extends WizardUseSkill
{
	public AiDivineMagician(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(414253057);
		Skill02_ID = SkillTable.getInstance().getInfo(414318593);
		Skill03_ID = SkillTable.getInstance().getInfo(414187521);
		Skill03_Probablity = 1000;
		Skill03_Target = 1;
		Skill03_Type = 2;
		Skill03_AttackSplash = 1;
	}
}
