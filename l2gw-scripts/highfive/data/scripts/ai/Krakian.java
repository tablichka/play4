package ai;

import ai.base.AiASeedNormalMonster;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

public class Krakian extends AiASeedNormalMonster
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(420413441);

	public Krakian(L2Character actor)
	{
		super(actor);
		Skill01_Probability = 10;
		Skill02_Probability = 10;
		Skill01_ID = SkillTable.getInstance().getInfo(420282369);
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(420347905);
		Skill02_Target_Type = 2;
		FieldCycle_ID = 5;
		FieldCycle_point = 1;
	}
}
