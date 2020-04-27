package quests._10286_ReunionwithSirra;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 26.09.11 19:06
 */
public class _10286_ReunionwithSirra extends Quest
{
	// NPC
	private static final int repre = 32020;
	private static final int jinia_npc = 32760;
	private static final int jinia_npc2 = 32781;
	private static final int sirr_npc = 32762;

	public _10286_ReunionwithSirra()
	{
		super(10286, "_10286_ReunionwithSirra", "Reunion with Sirra");
		addStartNpc(repre);
		addTalkId(repre, jinia_npc, jinia_npc2, sirr_npc);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == repre)
		{
			if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10285))
				return "repre_q10286_01.htm";
			if(st.isCompleted())
				return "npchtm:repre_q10286_02.htm";
			if(st.isCreated() && (talker.getLevel() < 82 || !talker.isQuestComplete(10285)))
				return "repre_q10286_03.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:repre_q10286_06.htm";
				if(st.getMemoState() == 2)
					return "npchtm:repre_q10286_09.htm";
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
			if(reply == 10286)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10285))
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					showQuestPage("repre_q10286_04.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(InstanceManager.enterInstance(145, talker, npc, 0))
				{
					if(st.getMemoState() == 1)
					{
						st.setCond(2);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else
				{
					showPage("repre_q10286_08.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.set("ex_1", 0);
					showPage("repre_q10286_05.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == jinia_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					showPage("jinia_npc_q10286_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					showPage("jinia_npc_q10286_03.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					showPage("jinia_npc_q10286_04.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					npc.createOnePrivate(32762, "SirrNpc", 0, 0, -23905, -8790, -5384, 56238, 0, 0, 0);
					st.set("ex_1", 1);
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 2)
				{
					st.setMemoState(2);
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.setNoUserTimeout(0);
					showPage("jinia_npc_q10286_09.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.setCond(5);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					talker.teleToClosestTown();
				}
			}
		}
		else if(npc.getNpcId() == sirr_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1)
				{
					showPage("sirr_npc_q10286_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1)
				{
					showPage("sirr_npc_q10286_03.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1)
				{
					if(st.getQuestItemsCount(15470) < 1)
					{
						st.giveItems(15470, 5);
					}
					st.set("ex_1", 2);
					showPage("sirr_npc_q10286_04.htm", talker);
					st.setCond(4);
					showQuestMark(talker);
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
					showPage("jinia_npc2_q10286_02.htm", talker);
				}
			}
			else if(reply == 2)
			{

			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("jinia_npc2_q10286_03.htm", talker);
				}
			}
		}
	}
}