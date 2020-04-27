package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 14.10.11 22:02
 */
public class AiDivineProtector extends WarriorUseSkill
{
	public AiDivineProtector(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(414056449);
		Skill01_Probablity = 100;
		Skill01_Target = 2;
		Skill01_Type = 1;
		Skill02_ID = SkillTable.getInstance().getInfo(414121985);
		Skill02_Probablity = 100;
		Skill02_Target = 2;
		Skill02_Type = 1;
		Skill03_ID = SkillTable.getInstance().getInfo(413925377);
		Skill03_Target = 2;
		Skill03_Probablity = 100;
		Skill03_Type = 3;
		Skill03_HighHP = 50;
		Skill03_HPTarget = 1;
		Skill04_ID = SkillTable.getInstance().getInfo(438173697);
		Skill04_Probablity = 10000;
		Skill04_Target = 2;
		Skill04_HighHP = 20;
		Skill04_Type = 2;
		Skill05_ID = SkillTable.getInstance().getInfo(458752001);
		Skill05_Probablity = 100;
		Skill05_Target = 1;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_thisActor.altUseSkill(Skill04_ID, _thisActor);
		super.onEvtDead(killer);
	}
}