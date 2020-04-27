package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 09.10.11 22:09
 */
public class MonasteryMinigameBurner extends Citizen
{
	public int POT_NUMBER = 0;
	public int OFF_TIMER = 5567;
	public int off_time = 2;

	public L2Skill s_trigger_mirage1 = SkillTable.getInstance().getInfo(337117185);

	public MonasteryMinigameBurner(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.changeNpcState(2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(skill != null && skill.getId() == 9059)
		{
			_thisActor.changeNpcState(1);
			_thisActor.changeNpcState(2);
			_thisActor.altUseSkill(s_trigger_mirage1, attacker);
			broadcastScriptEvent(2114005, POT_NUMBER, null, 1000);
			addTimer(OFF_TIMER, off_time * 1000);
			_thisActor.changeNpcState(3);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2114001)
		{
			_thisActor.changeNpcState(2);
			if((Integer) arg1 == POT_NUMBER)
			{
				_thisActor.changeNpcState(1);
				addTimer(OFF_TIMER, off_time * 1000);
			}
		}
		else if(eventId == 2114002)
		{
			_thisActor.changeNpcState(2);
			_thisActor.changeNpcState(1);
			addTimer(OFF_TIMER, off_time * 1000);
		}
		else if(eventId == 2114003)
		{
			_thisActor.changeNpcState(1);
			addTimer(OFF_TIMER, off_time * 1000);
			_thisActor.changeNpcState(2);
		}
		else if(eventId == 2114004)
		{
			_thisActor.changeNpcState(2);
			_thisActor.changeNpcState(1);
			addTimer(OFF_TIMER, off_time * 1000);
		}
		else if(eventId == 21140015)
		{
			_thisActor.changeNpcState(1);
			addTimer(OFF_TIMER, off_time * 1000);
			_thisActor.changeNpcState(3);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == OFF_TIMER)
		{
			_thisActor.changeNpcState(2);
		}
	}
}