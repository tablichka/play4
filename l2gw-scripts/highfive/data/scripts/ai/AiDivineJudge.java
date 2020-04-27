package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 14.10.11 22:11
 */
public class AiDivineJudge extends WarriorUseSkill
{
	public AiDivineJudge(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(413794305);
		Skill02_ID = SkillTable.getInstance().getInfo(413859841);
	}
}