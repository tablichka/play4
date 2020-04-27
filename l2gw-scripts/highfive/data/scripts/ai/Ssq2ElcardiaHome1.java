package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 29.09.11 18:28
 */
public class Ssq2ElcardiaHome1 extends Citizen
{
	public int p_MAX_KILL_NUM = 2;
	public int p_TIMER_LOOK = 1000;
	public int p_TIMER_LOOK_GAP = 1000;

	public Ssq2ElcardiaHome1(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(p_TIMER_LOOK, p_TIMER_LOOK_GAP);
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null && creature.isPlayer())
		{
			_thisActor.c_ai0 = creature.getStoredId();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_LOOK)
		{
			_thisActor.lookNeighbor(300);
			addTimer(p_TIMER_LOOK, p_TIMER_LOOK_GAP);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90000)
		{
			_thisActor.i_ai0++;
			if(_thisActor.i_ai0 == p_MAX_KILL_NUM)
			{
				_thisActor.i_ai1 = 0;
				L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
				if(c0 != null)
				{
					QuestState st = c0.getQuestState(10292);
					if(st != null)
					{
						st.setMemoState(6);
						st.setCond(6);
						st.getQuest().showQuestMark(c0);
					}
				}
			}
		}
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 10282 && reply == 10)
		{
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.rescheduleEndTask(60);
			talker.teleToClosestTown();
		}
	}
}