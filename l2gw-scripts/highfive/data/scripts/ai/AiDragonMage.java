package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 10:30
 */
public class AiDragonMage extends WarriorUseSkill
{
	public AiDragonMage(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(443613185);
		Skill01_Probablity = 3333;
		Skill01_Check_Dist = 1;
		Skill01_Dist_Min = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(443678721);
		Skill02_Probablity = 5000;
		Skill02_Target = 4;
		Skill02_Type = 3;
		Skill02_Check_Dist = 1;
		Skill02_Dist_Max = 2000;
		Skill02_HPTarget = 1;
		Skill02_HighHP = 80;
	}
}
