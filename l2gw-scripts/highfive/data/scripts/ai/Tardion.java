package ai;

import ai.base.AiASeedNormalMonster;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

public class Tardion extends AiASeedNormalMonster
{
	public int Skill01_Probablity = 10;
	public int Skill02_Probablity = 10;
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(420610049);

	public Tardion(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(420478977);
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(420544513);
		Skill02_Target_Type = 2;
		FieldCycle_ID = 5;
		FieldCycle_point = 1;
	}
}
