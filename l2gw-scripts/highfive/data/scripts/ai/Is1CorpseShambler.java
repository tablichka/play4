package ai;

import ai.base.IsBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:47
 */
public class Is1CorpseShambler extends IsBasic
{
	public Is1CorpseShambler(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(385482753);
		Skill01_Probability = 15;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(385548289);
		Skill02_Probability = 15;
		Skill02_Target_Type = 0;
		Skill03_ID = SkillTable.getInstance().getInfo(385941505);
		Skill03_Probability = 15;
		Skill03_Target_Type = 0;
	}
}