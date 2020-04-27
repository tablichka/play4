package quests._461_RumbleintheBase;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 26.09.11 1:36
 */
public class _461_RumbleintheBase extends Quest
{
	// NPC
	private static final int stan = 30200;

	// Items
	private static final int q_blingbling_salmon = 15503;
	private static final int q_shoestring_of_xel = 16382;

	// Mobs
	private static final int ol_cooker = 18908;
	private static final HashMap<Integer, Double> mobs = new HashMap<>(6);
	static
	{
		mobs.put(22780, 58.1);
		mobs.put(22781, 77.2);
		mobs.put(22782, 58.1);
		mobs.put(22783, 56.3);
		mobs.put(22784, 58.1);
		mobs.put(22785, 27.1);
	}

	public _461_RumbleintheBase()
	{
		super(461, "_461_RumbleintheBase", "Rumble in the Base");
		addStartNpc(stan);
		addTalkId(stan);
		addKillId(ol_cooker);
		for(int npcId : mobs.keySet())
			addKillId(npcId);
		addQuestItem(q_blingbling_salmon, q_shoestring_of_xel);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == stan)
		{
			if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(252))
				return "stan_q0461_01.htm";
			if(st.isCreated() && (talker.getLevel() < 82 || !talker.isQuestComplete(252)))
				return "stan_q0461_02.htm";
			if(st.isCompleted())
				return "stan_q0461_03.htm";
			if(st.isStarted() && st.getMemoState() == 1)
			{
				if(st.getQuestItemsCount(q_shoestring_of_xel) < 10 || st.getQuestItemsCount(q_blingbling_salmon) < 5)
					return "npchtm:stan_q0461_06.htm";

				st.takeItems(q_shoestring_of_xel, -1);
				st.takeItems(q_blingbling_salmon, -1);
				st.addExpAndSp(224784, 342528);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false, true);
				return "npchtm:stan_q0461_07.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == stan)
		{
			if(reply == 461)
			{
				if(talker.getLevel() >= 82 && talker.isQuestComplete(252) && st.isCreated())
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					showQuestPage("stan_q0461_05.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(talker.getLevel() >= 82 && talker.isQuestComplete(252) && st.isCreated())
				{
					showQuestPage("stan_q0461_04.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == ol_cooker)
		{
			QuestState st = killer.getQuestState(461);
			if(st != null && st.getMemoState() == 1 && st.getQuestItemsCount(q_blingbling_salmon) < 5 && st.rollAndGiveLimited(q_blingbling_salmon, 1, 78.2, 5))
			{
				st.playSound(SOUND_ITEMGET);
				if(st.getQuestItemsCount(q_blingbling_salmon) == 5 && st.getQuestItemsCount(q_shoestring_of_xel) >= 10)
				{
					st.setCond(2);
					showQuestMark(killer);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(mobs.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);
			for(QuestState st : party)
				if(st.getQuestItemsCount(q_shoestring_of_xel) < 10 && st.rollAndGiveLimited(q_shoestring_of_xel, 1, mobs.get(npc.getNpcId()), 10))
				{
					st.playSound(SOUND_ITEMGET);
					if(st.getQuestItemsCount(q_shoestring_of_xel) == 10 && st.getQuestItemsCount(q_blingbling_salmon) >= 5)
					{
						st.setCond(2);
						showQuestMark(killer);
						st.playSound(SOUND_MIDDLE);
					}
				}
		}
	}
}