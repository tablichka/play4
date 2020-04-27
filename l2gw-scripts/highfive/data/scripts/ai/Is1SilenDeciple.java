package ai;

import ai.base.IsBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:43
 */
public class Is1SilenDeciple extends IsBasic
{
	public Is1SilenDeciple(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(386465793);
		Skill01_Probability = 20;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(386924545);
		Skill02_Probability = 20;
		Skill02_Target_Type = 1;
	}
}