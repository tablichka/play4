package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 16:57
 */
public class AiDivineFighter extends WarriorUseSkill
{
	public AiDivineFighter(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(414711809);
		Skill01_Probablity = 100;
		Skill02_ID = SkillTable.getInstance().getInfo(414777345);
		Skill02_Probablity = 100;
		Skill03_ID = SkillTable.getInstance().getInfo(414187521);
		Skill03_Probablity = 50;
		Skill03_HighHP = 50;
		Skill03_Target = 1;
		Skill03_Type = 2;
		Skill03_AttackSplash = 1;
	}
}