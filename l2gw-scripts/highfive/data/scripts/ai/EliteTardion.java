package ai;

import ai.base.AiASeedEliteMonster;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 12.12.11 17:00
 */
public class EliteTardion extends AiASeedEliteMonster
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(420610049);

	public EliteTardion(L2Character actor)
	{
		super(actor);
		Skill01_Probability = 10;
		Skill01_Probability = 10;
		Skill01_ID = SkillTable.getInstance().getInfo(420478977);
		Skill01_Target_Type = 0;
		Skill02_ID = SkillTable.getInstance().getInfo(420544513);
		Skill02_Target_Type = 2;
		FieldCycle_ID = 5;
		FieldCycle_point = 10;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.createOnePrivate(22754, "Turtlian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
		_thisActor.createOnePrivate(22756, "Tardion", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
		_thisActor.createOnePrivate(22755, "Krakian", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
		super.onEvtSpawn();
	}
}