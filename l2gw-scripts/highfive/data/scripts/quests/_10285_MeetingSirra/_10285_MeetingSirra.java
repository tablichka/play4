package quests._10285_MeetingSirra;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 17.09.11 21:53
 */
public class _10285_MeetingSirra extends Quest
{
	// NPC
	private static final int repre = 32020;
	private static final int jinia_npc = 32760;
	private static final int jinia_npc2 = 32781;
	private static final int sirr_npc = 32762;
	private static final int kegor_npc = 32761;

	public _10285_MeetingSirra()
	{
		super(10285, "_10285_MeetingSirra", "Meeting Sirra");
		addStartNpc(repre);
		addTalkId(repre, jinia_npc, sirr_npc, kegor_npc, jinia_npc2);
	}

	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == repre)
		{
			if(st.isCompleted())
				return "repre_q10285_02.htm";

			if(st.isCreated())
			{
				if(talker.getLevel() >= 82 && talker.isQuestComplete(10284))
					return "repre_q10285_01.htm";

				return "repre_q10285_03.htm";
			}

			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					st.set("ex_1", 0);
					return "npchtm:repre_q10285_06.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:repre_q10285_09.htm";
				if(st.getMemoState() == 3)
				{
					st.rollAndGive(57, 283425, 100);
					st.addExpAndSp(939075, 83855);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:repre_q10285_10.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == repre)
		{
			if(reply == 10285)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10284))
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.set("ex_1", 0);
					showQuestPage("repre_q10285_05.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(InstanceManager.enterInstance(141, talker, npc, 0))
				{
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					showPage("repre_q10285_08.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10284))
				{
					showQuestPage("repre_q10285_04.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == jinia_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					st.set("ex_1", 1);
					showPage("jinia_npc_q10285_02.htm", talker);
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 2)
				{
					showPage("jinia_npc_q10285_05.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 2)
				{
					npc.createOnePrivate(32762, "SirrNpc", 0, 0, -23905, -8790, -5384, 56238, 0, 0, 0);
					st.set("ex_1", 3);
					st.setCond(5);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 21)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 4)
				{
					showPage("jinia_npc_q10285_09.htm", talker);
				}
			}
			else if(reply == 22)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 4)
				{
					showPage("jinia_npc_q10285_10.htm", talker);
				}
			}
			else if(reply == 23)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 4)
				{
					showPage("jinia_npc_q10285_11.htm", talker);
				}
			}
			else if(reply == 24)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 4)
				{
					st.set("ex_1", 5);
					showPage("jinia_npc_q10285_12.htm", talker);
					st.setCond(7);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 25)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 5)
				{
					st.set("ex_1", 0);
					st.setMemoState(2);
					showPage("jinia_npc_q10285_14.htm", talker);
					st.setCond(7);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.setNoUserTimeout(0);
				}
			}
			else if(reply == 26)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					talker.teleToClosestTown();
				}
			}
		}
		else if(npc.getNpcId() == sirr_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					showPage("sirr_npc_q10285_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					showPage("sirr_npc_q10285_03.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					showPage("sirr_npc_q10285_04.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					showPage("sirr_npc_q10285_05.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					showPage("sirr_npc_q10285_06.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					showPage("sirr_npc_q10285_07.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 3)
				{
					st.set("ex_1", 4);
					showPage("sirr_npc_q10285_08.htm", talker);
					st.setCond(6);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					npc.onDecay();
				}
			}
		}
		else if(npc.getNpcId() == kegor_npc)
		{
			if(reply == 1)
			{
				if(talker.isQuestStarted(10285) && st.getMemoState() == 1 && st.getInt("ex_1") == 1)
				{
					st.set("ex_1", 2);
					showPage("kegor_npc_q10285_02.htm", talker);
					st.setCond(4);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == jinia_npc2)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("jinia_npc2_q10285_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(InstanceManager.enterInstance(137, talker, npc, 0))
				{
					if(st.isStarted() && st.getMemoState() == 2)
					{
						st.setCond(9);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else
				{
					showPage("jinia_npc2_q10285_10.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(talker.isQuestStarted(10285) && st.getMemoState() == 3)
				{
					showPage("jinia_npc2_q10285_03.htm", talker);
				}
			}
		}
	}
}
