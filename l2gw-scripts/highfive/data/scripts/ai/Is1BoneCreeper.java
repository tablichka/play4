package ai;

import ai.base.IsBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:45
 */
public class Is1BoneCreeper extends IsBasic
{
	public Is1BoneCreeper(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(385613825);
		Skill01_Probability = 15;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(386531329);
		Skill02_Probability = 20;
		Skill02_Target_Type = 0;
		Skill03_ID = SkillTable.getInstance().getInfo(386334721);
		Skill03_Probability = 5;
		Skill03_Target_Type = 0;
	}
}