package ai;

import ai.base.IsBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:49
 */
public class Is1SoulHarvester extends IsBasic
{
	public Is1SoulHarvester(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(386138113);
		Skill01_Probability = 10;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(386990081);
		Skill02_Probability = 10;
		Skill02_Target_Type = 1;
		Skill03_ID = SkillTable.getInstance().getInfo(386859009);
		Skill03_Probability = 10;
		Skill03_Target_Type = 0;
	}
}