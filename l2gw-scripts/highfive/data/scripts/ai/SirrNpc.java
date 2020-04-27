package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 17.09.11 22:18
 */
public class SirrNpc extends Citizen
{
	public String fnInBattle = "sirr_npc002.htm";

	public SirrNpc(L2Character actor)
	{
		super(actor);
		fnHi = "sirr_npc001.htm";
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 3)
		{
			_thisActor.showPage(talker, "sirr_npc_q10285_01.htm", 10285);
		}
		else if(talker.isQuestStarted(10285) && talker.getQuestState(10285).getMemoState() == 1 && talker.getQuestState(10285).getInt("ex_1") == 4)
		{
			_thisActor.showPage(talker, "sirr_npc_q10285_09.htm", 10285);
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 1 && talker.getQuestState(10286).getInt("ex_1") == 1)
		{
			_thisActor.showPage(talker, "sirr_npc_q10286_01.htm", 10286);
		}
		else if(talker.isQuestStarted(10286) && talker.getQuestState(10286).getMemoState() == 1 && talker.getQuestState(10286).getInt("ex_1") == 2)
		{
			_thisActor.showPage(talker, "sirr_npc_q10286_05.htm", 10286);
		}
		else if(_thisActor.i_ai0 == 0)
		{
			_thisActor.showPage(talker, fnHi);
		}
		else if(_thisActor.i_ai0 == 1)
		{
			_thisActor.showPage(talker, fnInBattle);
		}

		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.getInstanceZoneId() == 141)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 528551);
		}
		else if(_thisActor.getInstanceZoneId() == 145)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 528651);
		}

		_thisActor.i_ai0 = 0;
		_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 23140022, _thisActor.getStoredId(), null);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140014)
		{
			_thisActor.i_ai0 = 1;
		}
		else if(eventId == 23140019)
		{
			_thisActor.i_ai0 = 2;
			_thisActor.onDecay();
		}
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -2316)
		{
			if(reply == 1)
			{
				talker.teleToLocation(114694, -113700, -11200);
			}
		}

		/*
		if( ask == 10285 )
		{
		}
		if( ask == 10285 )
		{
			if( reply == 1 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10285_02.htm");
				}
			}
			if( reply == 2 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10285_03.htm");
				}
			}
			if( reply == 3 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10285_04.htm");
				}
			}
			if( reply == 4 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10285_05.htm");
				}
			}
			if( reply == 5 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10285_06.htm");
				}
			}
			if( reply == 6 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10285_07.htm");
				}
			}
			if( reply == 7 )
			{
				if( talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 3 )
				{
					st.set("ex_1", 4);
					_thisActor.showPage(talker, "sirr_npc_q10285_08.htm");
					st.setCond(6);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					_thisActor.onDecay();
				}
			}
		}
		if( ask == 10286 )
		{
					}
		if( ask == 10286 )
		{
			if( reply == 1 )
			{
				if( talker.isQuestStarted(10286) && st.getMemoState() == 1 && st.getInt("ex_1") == 1 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10286_02.htm");
				}
			}
			if( reply == 2 )
			{
				if( talker.isQuestStarted(10286) && st.getMemoState() == 1 && st.getInt("ex_1") == 1 )
				{
					_thisActor.showPage(talker, "sirr_npc_q10286_03.htm");
				}
			}
			if( reply == 3 )
			{
				if( talker.isQuestStarted(10286) && st.getMemoState() == 1 && st.getInt("ex_1") == 1 )
				{
					if( st.getQuestItemsCount(15470) < 1 )
					{
						st.giveItems(15470, 5);
					}
					st.set("ex_1", 2);
					_thisActor.showPage(talker, "sirr_npc_q10286_04.htm");
					st.setCond(4);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		super.onMenuSelected(talker, ask, reply);
		*/
	}
}
