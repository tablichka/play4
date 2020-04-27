package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.10.11 0:01
 */
public class LostVillager extends DefaultNpc
{
	public int TIME_LIMIT1 = 1111;
	public int CHECK_TIMER = 1113;
	public int TALK_TIME = 1114;
	public int TALK_TIME2 = 1115;
	public int TALK_TIME3 = 1116;

	public LostVillager(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_quest0 = 0;
		_thisActor.l_ai0 = 0;
		_thisActor.setRunning();
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isQuestStarted(457) && talker.getQuestState(457).getMemoState() == 2)
		{
			_thisActor.showPage(talker, "lost_villager_q0457_08.htm", 457);
			return true;
		}

		if(talker.isQuestStarted(457) && talker.getQuestState(457).getMemoState() == 3)
		{
			_thisActor.showPage(talker, "lost_villager_q0457_09.htm", 457);
			QuestState st = talker.getQuestState(457);
			st.giveItems(15716, 1);
			st.exitCurrentQuest(false, true);
			st.playSound(Quest.SOUND_FINISH);
			addFleeDesire(talker, 1000000);
			addTimer(45751, 2000);
			return true;
		}

		return super.onTalk(talker);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 45751)
		{
			_thisActor.onDecay();
		}
		else if(timerId == TIME_LIMIT1)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c0 != null)
			{
				_thisActor.notifyAiEvent(_thisActor, CtrlEvent.EVT_SCRIPT_EVENT, 45704, 0, null);
			}
		}
		else if(timerId == CHECK_TIMER)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c0 != null)
			{
				float f0 = (float) _thisActor.getLoc().distance3D(c0.getLoc());

				if(f0 > 1000)
				{
					if(f0 > 5000)
					{
						_thisActor.notifyAiEvent(_thisActor, CtrlEvent.EVT_SCRIPT_EVENT, 45702, 0, null);
					}
					else if(_thisActor.i_ai1 == 0)
					{
						Functions.whisperFStr(_thisActor, c0, 60018);
						_thisActor.i_ai1 = 1;
					}
					else if(_thisActor.i_ai1 == 1)
					{
						Functions.whisperFStr(_thisActor, c0, 60019);
						_thisActor.i_ai1 = 2;
					}
					else if(_thisActor.i_ai1 == 2)
					{
						Functions.whisperFStr(_thisActor, c0, 60020);
						Functions.whisperFStr(_thisActor, c0, 1900175);
						_thisActor.notifyAiEvent(_thisActor, CtrlEvent.EVT_SCRIPT_EVENT, 45702, 0, null);
					}
				}
			}
			addTimer(CHECK_TIMER, 2000);
		}
		else if(timerId == TALK_TIME)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c0 != null)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 60021);
			}
		}
		else if(timerId == TALK_TIME2)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c0 != null)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 60023);
			}
			addTimer(TALK_TIME3, 10000);
		}
		else if(timerId == TALK_TIME3)
		{
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c0 != null)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 60024);
			}
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 45702)
		{
			L2Player c1 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c1 != null)
			{
				QuestState st = c1.getQuestState(457);
				if(st != null)
				{
					st.exitCurrentQuest(true);
					st.playSound(Quest.SOUND_FINISH);
				}
				_thisActor.onDecay();
			}
		}
		else if(eventId == 45701)
		{
			L2Player c1 = L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0);
			if(c1 != null)
			{
				QuestState st = c1.getQuestState(457);
				if(st != null)
				{
					st.setMemoState(3);
					st.setCond(2);
					st.getQuest().showQuestMark(c1);
				}
			}
		}
		else if(eventId == 45703)
		{
			addFollowDesire2(L2ObjectsStorage.getAsPlayer(_thisActor.l_ai0), 10, 100, 180);
			addTimer(CHECK_TIMER, 1000);
			addTimer(TIME_LIMIT1, 600000);
			addTimer(TALK_TIME, 120000);
			addTimer(TALK_TIME2, 30000);
		}
		else if(eventId == 45704)
		{
			_thisActor.notifyAiEvent(_thisActor, CtrlEvent.EVT_SCRIPT_EVENT, 45702, 0, null);
		}
		else if(eventId == 45705 && _thisActor.i_ai0 == 0)
		{
			clearTasks();
			_thisActor.i_ai0 = 1;
			Functions.npcSay(_thisActor, Say2C.ALL, 60022);
			_thisActor.notifyAiEvent(_thisActor, CtrlEvent.EVT_SCRIPT_EVENT, 45701, 0, null);
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}