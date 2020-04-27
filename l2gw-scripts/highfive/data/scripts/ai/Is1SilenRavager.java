package ai;

import ai.base.IsBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 13.12.11 20:36
 */
public class Is1SilenRavager extends IsBasic
{
	public Is1SilenRavager(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(385613825);
		Skill01_Probability = 10;
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(385744897);
		Skill02_Probability = 20;
		Skill02_Target_Type = 1;
		Skill03_ID = SkillTable.getInstance().getInfo(385941505);
		Skill03_Probability = 10;
		Skill03_Target_Type = 0;
	}
}