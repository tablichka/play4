package quests._036_MakeASewingKit;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 15.01.2011 14:04
 */
public class _036_MakeASewingKit extends Quest
{
	// NPCs
	private static final int head_blacksmith_ferris = 30847;

	// Items
	private static final int q_piece_of_steel = 7163;
	private static final int artisans_frame = 1891;
	private static final int oriharukon = 1893;
	private static final int q_workbox = 7078;

	public _036_MakeASewingKit()
	{
		super(36, "_036_MakeASewingKit", "Make A Sewing Kit");

		addStartNpc(head_blacksmith_ferris);
		addTalkId(head_blacksmith_ferris);
		addKillId(20566);
		addQuestItem(q_piece_of_steel);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("completed", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == head_blacksmith_ferris)
		{
			if(reply == 36 && st.isCreated() && player.getLevel() >= 60)
			{
				st.setMemoState(11);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("head_blacksmith_ferris_q0036_0104.htm", player);
			}
			else if(reply == 1 && st.isStarted() && st.getInt("cookie") == 1)
			{
				if(st.getQuestItemsCount(q_piece_of_steel) >= 5)
				{
					st.takeItems(q_piece_of_steel, 5);
					showPage("head_blacksmith_ferris_q0036_0201.htm", player);
					st.setMemoState(21);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else
					showPage("head_blacksmith_ferris_q0036_0202.htm", player);
			}
			else if(reply == 3 && st.isStarted() && st.getInt("cookie") == 2)
			{
				if(st.getQuestItemsCount(artisans_frame) >= 10 && st.getQuestItemsCount(oriharukon) >= 10)
				{
					st.takeItems(artisans_frame, 10);
					st.takeItems(oriharukon, 10);
					st.giveItems(q_workbox, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("head_blacksmith_ferris_q0036_0301.htm", player);
				}
				else
					showPage("head_blacksmith_ferris_q0036_0302.htm", player);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		int cond = st.getMemoState();

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 60)
				return "head_blacksmith_ferris_q0036_0101.htm";

			st.exitCurrentQuest(true);
			return "npchtm:head_blacksmith_ferris_q0036_0103.htm";
		}
		else if(st.isStarted())
		{
			if(cond == 11 || cond == 12)
			{
				if(cond == 12 && st.getQuestItemsCount(q_piece_of_steel) >= 5)
				{
					st.set("cookie", 1);
					return "npchtm:head_blacksmith_ferris_q0036_0105.htm";
				}

				return "npchtm:head_blacksmith_ferris_q0036_0106.htm";
			}
			if(cond == 21)
			{
				if(st.getQuestItemsCount(artisans_frame) >= 10 && st.getQuestItemsCount(oriharukon) >= 10)
				{
					st.set("cookie", 2);
					return "npchtm:head_blacksmith_ferris_q0036_0203.htm";
				}
				return "npchtm:head_blacksmith_ferris_q0036_0204.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState qs = getRandomPartyMemberWithMemoState(killer, 11);

		if(qs != null && qs.getQuestItemsCount(q_piece_of_steel) < 5)
			if(qs.rollAndGiveLimited(q_piece_of_steel, 1, 50, 5))
			{
				if(qs.getQuestItemsCount(q_piece_of_steel) >= 5)
				{
					qs.playSound(SOUND_MIDDLE);
					qs.setCond(2);
					showQuestMark(qs.getPlayer());
					qs.setMemoState(12);
				}
				else
					qs.playSound(SOUND_ITEMGET);
			}
	}
}