package ai;

import ai.base.AiASeedNormalMonster;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

public class Turtlian extends AiASeedNormalMonster
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(420151297);
	public L2Skill SpecialSkill02_ID = SkillTable.getInstance().getInfo(420216833);

	public Turtlian(L2Character actor)
	{
		super(actor);
		Skill01_Probability = 10;
		Skill02_Probability = 10;
		Skill01_ID = SkillTable.getInstance().getInfo(420020225);
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(420085761);
		Skill02_Target_Type = 2;
		FieldCycle_ID = 5;
		FieldCycle_point = 1;
	}
}
