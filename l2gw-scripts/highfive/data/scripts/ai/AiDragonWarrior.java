package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 05.09.11 1:21
 */
public class AiDragonWarrior extends WarriorUseSkill
{
	public AiDragonWarrior(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(443023361);
		Skill01_Probablity = 1000;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 14001)
		{
			addUseSkillDesire(_thisActor, 443154433, 1, 1, 1000000);
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
