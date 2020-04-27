package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 24.09.11 20:12
 */
public class PetrifyObject extends Citizen
{
	public L2Skill petrify_skill = SkillTable.getInstance().getInfo(441384961);
	public int USE_SKILL_TIME = 1000;

	public PetrifyObject(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param2 == 0)
		{
			addTimer(USE_SKILL_TIME, (3 * 1000));
		}
		if(_thisActor.param2 == 1)
		{
			addTimer(USE_SKILL_TIME, (5 * 1000));
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == USE_SKILL_TIME)
		{
			_thisActor.doCast(petrify_skill, _thisActor, false);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == petrify_skill)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15007)
		{
			_thisActor.onDecay();
		}
	}

}
