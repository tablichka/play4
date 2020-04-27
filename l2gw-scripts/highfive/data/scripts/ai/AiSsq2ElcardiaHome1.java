package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 10.09.11 13:19
 */
public class AiSsq2ElcardiaHome1 extends Citizen
{
	public int p_ASK_SUMMON_TEST = 10292;
	public int p_REP_SUMMON_TEST = 5;
	public int p_MAX_KILL_NUM = 2;
	public int p_TIMER_LOOK = 1000;
	public int p_TIMER_LOOK_GAP = 1000;

	public AiSsq2ElcardiaHome1(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		ServerVariables.set("GM_" + 80000, _thisActor.getStoredId());
		addTimer(p_TIMER_LOOK, p_TIMER_LOOK_GAP);
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		ServerVariables.unset("GM_" + 80000);
		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null && creature.isPlayer())
		{
			_thisActor.c_ai0 = creature.getStoredId();
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_LOOK)
		{
			_thisActor.lookNeighbor(300);
			addTimer(p_TIMER_LOOK, p_TIMER_LOOK_GAP);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == p_ASK_SUMMON_TEST && reply == p_REP_SUMMON_TEST)
		{
			if(_thisActor.i_ai1 == 0)
			{
				_thisActor.i_ai1 = 1;
				_thisActor.createOnePrivate(27422, "Ssq2TestMonster2", 0, 1, 89440, -238016, -9632, Rnd.get(360), 0, 0, 0);
				_thisActor.createOnePrivate(27424, "Ssq2TestMonster4", 0, 1, 89524, -238131, -9632, Rnd.get(360), 0, 0, 0);
			}
			else
			{
				_thisActor.showPage(talker, "ssq2_elcardia_home1_q10292_16.htm");
			}
		}
		if(ask == 10292 && reply == 10)
		{
			talker.teleToCastle();
			_thisActor.getSpawn().getInstance().stopInstance();
		}
		super.onMenuSelected(talker, ask, reply);
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
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
