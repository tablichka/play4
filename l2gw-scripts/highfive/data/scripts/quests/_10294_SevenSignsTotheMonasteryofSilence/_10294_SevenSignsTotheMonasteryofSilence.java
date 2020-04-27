package quests._10294_SevenSignsTotheMonasteryofSilence;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 04.10.11 15:22
 */
public class _10294_SevenSignsTotheMonasteryofSilence extends Quest
{
	// NPC
	private static final int ssq2_elcardia_home1 = 32784;
	private static final int ssq2_solina_silence = 32793;
	private static final int ssq2_elcardia1_silence = 32787;
	private static final int ssq2_cl_tel_silence = 32815;
	private static final int ssq2_eris_silence = 32792;
	private static final int ssq2_judith_past = 32797;
	private static final int ssq2_elcardia2_silence = 32788;
	private static final int ssq2_guardian_book = 32803;
	private static final int ssq2_judith_summon = 32888;
	private static final int ssq2_judith_follow = 32889;
	private static final int ssq2_cl_east = 32817;
	private static final int ssq2_cl_west = 32818;
	private static final int ssq2_cl_north = 32819;
	private static final int ssq2_cl_south = 32820;
	private static final int ssq2_cl_book_east1 = 32821;
	private static final int ssq2_cl_book_east2 = 32822;
	private static final int ssq2_cl_book_east3 = 32823;
	private static final int ssq2_cl_book_east4 = 32824;
	private static final int ssq2_cl_book_west1 = 32825;
	private static final int ssq2_cl_book_west2 = 32826;
	private static final int ssq2_cl_book_west3 = 32827;
	private static final int ssq2_cl_book_west4 = 32828;
	private static final int ssq2_cl_book_north1 = 32829;
	private static final int ssq2_cl_book_north2 = 32830;
	private static final int ssq2_cl_book_north3 = 32831;
	private static final int ssq2_cl_book_north4 = 32832;
	private static final int ssq2_cl_book_south1 = 32833;
	private static final int ssq2_cl_book_south2 = 32834;
	private static final int ssq2_cl_book_south3 = 32835;
	private static final int ssq2_cl_book_south4 = 32836;
	private static final int ssq2_watcher1_book = 32804;
	private static final int ssq2_watcher2_book = 32805;
	private static final int ssq2_watcher3_book = 32806;
	private static final int ssq2_watcher4_book = 32807;

	public _10294_SevenSignsTotheMonasteryofSilence()
	{
		super(10294, "_10294_SevenSignsTotheMonasteryofSilence", "Seven Signs, To the Monastery of Silence");
		addStartNpc(ssq2_elcardia_home1);
		addTalkId(ssq2_elcardia_home1, ssq2_cl_tel_silence, ssq2_solina_silence, ssq2_elcardia1_silence, ssq2_eris_silence, ssq2_judith_past);
		addTalkId(ssq2_elcardia2_silence, ssq2_guardian_book, ssq2_judith_follow, ssq2_judith_summon);
		addTalkId(ssq2_cl_book_east1, ssq2_cl_book_east2, ssq2_cl_book_east3, ssq2_cl_book_east4);
		addTalkId(ssq2_cl_book_west1, ssq2_cl_book_west2, ssq2_cl_book_west3, ssq2_cl_book_west4);
		addTalkId(ssq2_cl_book_north1, ssq2_cl_book_north2, ssq2_cl_book_north3, ssq2_cl_book_north4);
		addTalkId(ssq2_cl_book_south1, ssq2_cl_book_south2, ssq2_cl_book_south3, ssq2_cl_book_south4);
		addTalkId(ssq2_watcher1_book, ssq2_watcher2_book, ssq2_watcher3_book, ssq2_watcher4_book);
		addTalkId(ssq2_cl_east, ssq2_cl_west, ssq2_cl_north, ssq2_cl_south);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(st.isCreated() && talker.isQuestComplete(10293) && talker.getLevel() >= 81)
				return "ssq2_elcardia_home1_q10294_01.htm";
			if(st.isCompleted())
				return "npchtm:ssq2_elcardia_home1_q10294_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() > 1 && st.getMemoState() < 8)
					return "npchtm:ssq2_elcardia_home1_q10294_06.htm";
				if(st.getMemoState() == 8)
					return "npchtm:ssq2_elcardia_home1_q10294_07.htm";
				if(st.getMemoState() > 8)
					return "npchtm:ssq2_elcardia_home1_q10294_08.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_cl_tel_silence)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() > 7 && st.getMemoState() < 12)
					return "npchtm:ssq2_cl_tel_silence_q10294_01.htm";
				if(st.getMemoState() < 8)
					return "npchtm:ssq2_cl_tel_silence_q10294_02.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_solina_silence)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() > 8)
					return "npchtm:ssq2_solina_silence_q10294_01.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia1_silence)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 8)
					return "npchtm:ssq2_elcardia1_silence_q10294_01.htm";
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_elcardia1_silence_q10294_02.htm";
				if(st.getMemoState() == 10 || st.getMemoState() == 11)
					return "npchtm:ssq2_elcardia1_silence_q10294_03.htm";
				if(st.getMemoState() < 8)
					return "npchtm:ssq2_elcardia1_silence_q10294_04.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_eris_silence)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 8)
					return "npchtm:ssq2_eris_silence_q10294_01.htm";
				if(st.getMemoState() > 8 && st.getMemoState() < 11)
					return "npchtm:ssq2_eris_silence_q10294_08.htm";
				if(st.getMemoState() == 11)
					return "npchtm:ssq2_eris_silence_q10294_09.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_judith_past)
		{
			if(st.isStarted() && st.getMemoState() > 8)
				return "npchtm:ssq2_judith_past_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_elcardia2_silence)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_elcardia2_silence_q10294_01.htm";
				if(st.getMemoState() == 10)
					return "npchtm:ssq2_elcardia2_silence_q10294_02.htm";
				if(st.getMemoState() == 11)
					return "npchtm:ssq2_elcardia2_silence_q10294_03.htm";

			}
		}
		else if(npc.getNpcId() == ssq2_guardian_book)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_guardian_book_q10294_01.htm";
				if(st.getMemoState() == 10)
				{
					st.setMemoState(11);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:ssq2_guardian_book_q10294_04.htm";
				}
				if(st.getMemoState() == 11)
					return "npchtm:ssq2_guardian_book_q10294_05.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_judith_summon)
		{
			if(st.isStarted() && st.getMemoState() > 8)
				return "npchtm:ssq2_judith_summon_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_judith_follow)
		{
			if(st.isStarted() && st.getMemoState() > 8)
				return "npchtm:ssq2_judith_follow_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_east1)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_east1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_east2)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_east2_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_east3)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_east3_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_east4)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_east4_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_west1)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_west1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_west2)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_west1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_west3)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_west1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_west4)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_west1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_north1)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_north1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_north2)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_north2_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_north3)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_north3_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_north4)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_north4_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_south1)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_south1_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_south2)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_south2_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_south3)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_south3_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_cl_book_south4)
		{
			if(st.isStarted() && st.getMemoState() == 9)
				return "npchtm:ssq2_cl_book_south4_q10294_01.htm";
		}
		else if(npc.getNpcId() == ssq2_watcher1_book)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_watcher1_book_q10294_01.htm";
				if(st.getMemoState() == 10)
					return "npchtm:ssq2_watcher1_book_q10294_05.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_watcher2_book)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_watcher2_book_q10294_01.htm";
				if(st.getMemoState() == 10)
					return "npchtm:ssq2_watcher2_book_q10294_05.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_watcher3_book)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_watcher3_book_q10294_01.htm";
				if(st.getMemoState() == 10)
					return "npchtm:ssq2_watcher3_book_q10294_05.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_watcher4_book)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 9)
					return "npchtm:ssq2_watcher4_book_q10294_01.htm";
				if(st.getMemoState() == 10)
					return "npchtm:ssq2_watcher4_book_q10294_05.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(reply == 10294)
			{
				if(st.isCreated() && talker.isQuestComplete(10293) && talker.getLevel() >= 81)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(8);
					showQuestPage("ssq2_elcardia_home1_q10294_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.isQuestComplete(10293) && talker.getLevel() >= 81)
				{
					showQuestPage("ssq2_elcardia_home1_q10294_03.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_tel_silence)
		{
			if(reply == 1)
			{
				InstanceManager.enterInstance(151, talker, npc, 0);
			}
		}
		else if(npc.getNpcId() == ssq2_eris_silence)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 8)
				{
					showPage("ssq2_eris_silence_q10294_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 8)
				{
					st.setMemoState(9);
					showPage("ssq2_eris_silence_q10294_03.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() > 8 && st.getMemoState() < 11)
				{
					showPage("ssq2_eris_silence_q10294_04.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() > 8 && st.getMemoState() < 11)
				{
					showPage("ssq2_eris_silence_q10294_05.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && st.getMemoState() > 8 && st.getMemoState() < 11)
				{
					showPage("ssq2_eris_silence_q10294_07.htm", talker);
				}
			}
			else if(reply == 6)
			{
				talker.teleToLocation(85937, -249618, -8350);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(85937, -249618, -8350);
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90310, 0, null);
				}
			}
			else if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 11)
				{
					if(talker.isSubClassActive())
					{
						showPage("ssq2_eris_silence_q10294_11.htm", talker);
					}
					else
					{
						st.addExpAndSp(25000000, 2500000);
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("ssq2_eris_silence_q10294_10.htm", talker);
					}
				}
			}
			else if(reply == 10)
			{
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90112, 0, null);
				}

				Instance inst = npc.getInstanceZone();
				if(inst != null)
					inst.rescheduleEndTask(60);

				talker.teleToClosestTown();
			}
		}
		else if(npc.getNpcId() == ssq2_guardian_book)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					showPage("ssq2_guardian_book_q10294_03.htm", talker);
				}
			}
			else if( reply == 2 )
			{
				talker.teleToLocation( 120717, -86879, -3424);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation( 120717, -86879, -3424);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_book_east1 || npc.getNpcId() == ssq2_cl_book_east2 ||
				npc.getNpcId() == ssq2_cl_book_east3 || npc.getNpcId() == ssq2_cl_book_east4)
		{
			if(reply == 1)
			{
				if(npc.i_ai1 == 1 && npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_watcher1_book);
					if(c0 != null)
					{
						npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90100, 0, null);
						showPage("ssq2_cl_book_east1_q10294_02.htm", talker);
						npc.changeNpcState(1);
					}
				}
				else if(npc.i_ai1 == 0)
				{
					showPage("ssq2_cl_book_east1_q10294_03.htm", talker);
				}
				else if(npc.i_ai0 == 1)
				{
					showPage("ssq2_cl_book_east1_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_book_west1 || npc.getNpcId() == ssq2_cl_book_west2 ||
				npc.getNpcId() == ssq2_cl_book_west3 || npc.getNpcId() == ssq2_cl_book_west4)
		{
			if(reply == 1)
			{
				if(npc.i_ai1 == 1 && npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_watcher2_book);
					if(c0 != null)
					{
						npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90100, 0, null);
						showPage("ssq2_cl_book_east1_q10294_02.htm", talker);
						npc.changeNpcState(1);
					}
				}
				else if(npc.i_ai1 == 0)
				{
					showPage("ssq2_cl_book_east1_q10294_03.htm", talker);
				}
				else if(npc.i_ai0 == 1)
				{
					showPage("ssq2_cl_book_east1_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_book_north1 || npc.getNpcId() == ssq2_cl_book_north2 ||
				npc.getNpcId() == ssq2_cl_book_north3 || npc.getNpcId() == ssq2_cl_book_north4)
		{
			if(reply == 1)
			{
				if(npc.i_ai1 == 1 && npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_watcher3_book);
					if(c0 != null)
					{
						npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90100, 0, null);
						showPage("ssq2_cl_book_east1_q10294_02.htm", talker);
						npc.changeNpcState(1);
					}
				}
				else if(npc.i_ai1 == 0)
				{
					showPage("ssq2_cl_book_east1_q10294_03.htm", talker);
				}
				else if(npc.i_ai0 == 1)
				{
					showPage("ssq2_cl_book_east1_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_book_south1 || npc.getNpcId() == ssq2_cl_book_south2 ||
				npc.getNpcId() == ssq2_cl_book_south3 || npc.getNpcId() == ssq2_cl_book_south4)
		{
			if(reply == 1)
			{
				if(npc.i_ai1 == 1 && npc.i_ai0 == 0)
				{
					npc.i_ai0 = 1;
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_watcher4_book);
					if(c0 != null)
					{
						npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90100, 0, null);
						showPage("ssq2_cl_book_east1_q10294_02.htm", talker);
						npc.changeNpcState(1);
					}
				}
				else if(npc.i_ai1 == 0)
				{
					showPage("ssq2_cl_book_east1_q10294_03.htm", talker);
				}
				else if(npc.i_ai0 == 1)
				{
					showPage("ssq2_cl_book_east1_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_watcher1_book)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					showPage("ssq2_watcher1_book_q10294_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(npc.i_ai1 == 1)
				{
					showPage("ssq2_watcher1_book_q10294_06.htm", talker);
				}
				else
				{
					showPage("ssq2_watcher1_book_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_watcher2_book)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					showPage("ssq2_watcher2_book_q10294_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(npc.i_ai1 == 1)
				{
					showPage("ssq2_watcher2_book_q10294_06.htm", talker);
				}
				else
				{
					showPage("ssq2_watcher2_book_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_watcher3_book)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					showPage("ssq2_watcher3_book_q10294_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(npc.i_ai1 == 1)
				{
					showPage("ssq2_watcher3_book_q10294_06.htm", talker);
				}
				else
				{
					showPage("ssq2_watcher3_book_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_watcher4_book)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 9)
				{
					showPage("ssq2_watcher4_book_q10294_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(npc.i_ai1 == 1)
				{
					showPage("ssq2_watcher4_book_q10294_06.htm", talker);
				}
				else
				{
					showPage("ssq2_watcher4_book_q10294_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_east)
		{
			if(reply == 1)
			{
				if(npc.i_ai0 == 0)
				{
					talker.teleToLocation(88573, -249556, -8350);
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
					if(c0 != null)
					{
						c0.teleToLocation(88573, -249556, -8350);
					}
				}
				else
				{
					showPage("ssq2_cl_cross_finish.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_west)
		{
			if(reply == 1)
			{
				if(npc.i_ai0 == 0)
				{
					talker.teleToLocation(82434, -249546, -8350);
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
					if(c0 != null)
					{
						c0.teleToLocation(82434, -249546, -8350);
					}
					L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(npc, ssq2_watcher2_book);
					if(c1 != null)
					{
						npc.createOnePrivate(32889, "Ssq2JudithFollow", 0, 1, c1.getX(), c1.getY(), c1.getZ(), Rnd.get(65535), 0, 0, 0);
					}
				}
				else
				{
					showPage("ssq2_cl_cross_finish.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_north)
		{
			if(reply == 1)
			{
				if(npc.i_ai0 == 0)
				{
					talker.teleToLocation(85691, -252426, -8350);
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
					if(c0 != null)
					{
						c0.teleToLocation(85691, -252426, -8350);
					}
				}
				else
				{
					showPage("ssq2_cl_cross_finish.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_south)
		{
			if(reply == 1)
			{
				if(npc.i_ai0 == 0)
				{
					talker.teleToLocation(85675, -246630, -8350);
					L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
					if(c0 != null)
					{
						c0.teleToLocation(85675, -246630, -8350);
					}
				}
				else
				{
					showPage("ssq2_cl_cross_finish.htm", talker);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}
}