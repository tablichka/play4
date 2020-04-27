package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 16.09.11 16:02
 */
public class JiniaNpc extends DefaultAI
{
	public JiniaNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isQuestStarted(10283) && talker.getQuestState(10283).getMemoState() == 2)
		{
			if(_thisActor.param2 == talker.getObjectId())
			{
				_thisActor.showPage(talker, "jinia_npc001a.htm");
			}
			else
			{
				_thisActor.showPage(talker, "jinia_npc001b.htm");
			}
		}
		else if(talker.isQuestStarted(10284) && talker.getQuestState(10284).getMemoState() == 1)
		{
			if(talker.getQuestState(10284).getInt("ex_1") == 0 && talker.getQuestState(10284).getInt("ex_2") == 0 && talker.getQuestState(10284).getInt("ex_3") == 0)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 1 && talker.getQuestState(10284).getInt("ex_2") == 0 && talker.getQuestState(10284).getInt("ex_3") == 0)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01a.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 0 && talker.getQuestState(10284).getInt("ex_2") == 1 && talker.getQuestState(10284).getInt("ex_3") == 0)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01b.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 0 && talker.getQuestState(10284).getInt("ex_2") == 0 && talker.getQuestState(10284).getInt("ex_3") == 1)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01c.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 0 && talker.getQuestState(10284).getInt("ex_2") == 1 && talker.getQuestState(10284).getInt("ex_3") == 1)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01d.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 1 && talker.getQuestState(10284).getInt("ex_2") == 0 && talker.getQuestState(10284).getInt("ex_3") == 1)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01e.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 1 && talker.getQuestState(10284).getInt("ex_2") == 1 && talker.getQuestState(10284).getInt("ex_3") == 0)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01f.htm", 10284);
			}
			else if(talker.getQuestState(10284).getInt("ex_1") == 1 && talker.getQuestState(10284).getInt("ex_2") == 1 && talker.getQuestState(10284).getInt("ex_3") == 1)
			{
				_thisActor.showPage(talker, "jinia_npc_q10284_01g.htm", 10284);
			}
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 0)
		{
			_thisActor.showPage(talker, "jinia_npc_q10285_01.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 1)
		{
			_thisActor.showPage(talker, "jinia_npc_q10285_03.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 2)
		{
			_thisActor.showPage(talker, "jinia_npc_q10285_04.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 3)
		{
			_thisActor.showPage(talker, "jinia_npc_q10285_07.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 4)
		{
			_thisActor.showPage(talker, "jinia_npc_q10285_08.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 5)
		{
			_thisActor.showPage(talker, "jinia_npc_q10285_13.htm", 10285);
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 1 && talker.getQuestState(10286).getInt("ex_1") == 0)
		{
			if(_thisActor.getInstanceZoneId() == 145)
			{
				_thisActor.showPage(talker, "jinia_npc_q10286_01.htm", 10286);
			}
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 1 && talker.getQuestState(10286).getInt("ex_1") == 1)
		{
			if(_thisActor.getInstanceZoneId() == 145)
			{
				_thisActor.showPage(talker, "jinia_npc_q10286_07.htm", 10286);
			}
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 1 && talker.getQuestState(10286).getInt("ex_1") == 2)
		{
			if(_thisActor.getInstanceZoneId() == 145)
			{
				_thisActor.showPage(talker, "jinia_npc_q10286_08.htm", 10286);
			}
		}
		else if(talker.isQuestStarted(10287) && talker.getQuestState(10287).getMemoState() == 1 && talker.getQuestState(10287).getInt("ex_1") == 0)
		{
			if(_thisActor.getInstanceZoneId() == 146)
			{
				_thisActor.showPage(talker, "jinia_npc_q10287_01.htm", 10287);
			}
		}
		else if(talker.isQuestStarted(10287) && talker.getQuestState(10287).getMemoState() == 1 && talker.getQuestState(10287).getInt("ex_1") == 1 && talker.getQuestState(10287).getInt("ex_2") == 0)
		{
			if(_thisActor.getInstanceZoneId() == 146)
			{
				_thisActor.showPage(talker, "jinia_npc_q10287_04.htm", 10287);
			}
		}
		else if(talker.isQuestStarted(10287) && talker.getQuestState(10287).getMemoState() == 1 && talker.getQuestState(10287).getInt("ex_1") == 1 && talker.getQuestState(10287).getInt("ex_2") == 1)
		{
			if(_thisActor.getInstanceZoneId() == 146)
			{
				QuestState st = talker.getQuestState(10287);
				_thisActor.showPage(talker, "jinia_npc_q10287_05.htm", 10287);
				st.setCond(5);
				st.getQuest().showQuestMark(talker);
				st.playSound(Quest.SOUND_MIDDLE);
				st.setMemoState(2);
				st.set("ex_1", 0);
				st.set("ex_2", 0);
				Instance inst = _thisActor.getInstanceZone();
				if(inst != null)
					inst.setNoUserTimeout(0);
			}
		}
		else
			return super.onTalk(talker);

		return true;
	}

	@Override
	public void onTalkSelected(L2Player talker, int choice, boolean fromChoice)
	{
		_thisActor.showQuestWindow(talker);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		L2NpcInstance npc = L2ObjectsStorage.getAsNpc(_thisActor.param3);
		if(npc != null)
			npc.i_quest0 = 0;
		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 528351)
		{
			L2NpcInstance npc0 = L2ObjectsStorage.getAsNpc(_thisActor.param3);
			if(npc0 != null)
			{
				npc0.i_quest0 = 0;
			}
			_thisActor.onDecay();
		}
		else if(timerId == 528352)
		{
			L2NpcInstance npc0 = L2ObjectsStorage.getAsNpc(_thisActor.param3);
			if(npc0 != null)
			{
				npc0.i_quest0 = 0;
			}
			_thisActor.onDecay();
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.getInstanceZoneId() != 140 && _thisActor.getInstanceZoneId() != 141 && _thisActor.getInstanceZoneId() != 145 && _thisActor.getInstanceZoneId() != 146)
		{
			addTimer(528352, 180000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}
