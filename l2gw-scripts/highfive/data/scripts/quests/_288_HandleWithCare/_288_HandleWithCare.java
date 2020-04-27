package quests._288_HandleWithCare;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 05.02.11 18:43
 */
public class _288_HandleWithCare extends Quest
{
	// NPCs
	private static final int angkumi = 32741;

	// Items
	private static final int q_scale_of_lizard_highest = 15497;
	private static final int q_scale_of_lizard_good = 15498;

	// Mobs
	private static final int tantaar_seer_ugoros = 18863;

	public _288_HandleWithCare()
	{
		super(288, "_288_HandleWithCare", "Handle With Care");

		addStartNpc(angkumi);
		addTalkId(angkumi);
		addKillId(tantaar_seer_ugoros);
		addQuestItem(q_scale_of_lizard_highest, q_scale_of_lizard_good);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == angkumi)
		{
			if(st.isCreated())
			{
				if(reply == 288 && player.getLevel() >= 82)
				{
					st.setMemoState(1);
					st.set("ex", 1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("angkumi_q0288_04.htm", player);
				}
				else if(reply == 1 && player.getLevel() >= 82)
					showQuestPage("angkumi_q0288_03.htm", player);
			}
			else if(st.isStarted() && reply == 2 && st.getMemoState() == 2)
			{
				if(st.haveQuestItems(q_scale_of_lizard_good))
				{
					st.takeItems(q_scale_of_lizard_good, -1);
					st.setMemoState(2);
					int i0 = Rnd.get(6);
					if(i0 == 0)
						st.giveItems(959, 1);
					else if(i0 >= 1 && i0 < 4)
						st.giveItems(960, 1);
					else if(i0 >= 4 && i0 < 6)
						st.giveItems(960, 2);
					else if(i0 >= 6 && i0 < 7)
						st.giveItems(960, 3);
					else if(i0 >= 7 && i0 < 9)
						st.giveItems(9557, 1);
					else
						st.giveItems(9557, 2);

					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("angkumi_q0288_08.htm", player);
				}
				else if(st.haveQuestItems(q_scale_of_lizard_highest))
				{
					st.takeItems(q_scale_of_lizard_highest, -1);
					st.setMemoState(2);
					int i0 = Rnd.get(10);
					if(i0 < 1)
					{
						st.giveItems(959, 1);
						st.giveItems(9557, 1);
					}
					else if(i0 <= 1 && i0 < 5)
					{
						st.giveItems(960, 1);
						st.giveItems(9557, 1);
					}
					else if(i0 <= 5 && i0 < 8)
					{
						st.giveItems(960, 2);
						st.giveItems(9557, 1);
					}
					else
					{
						st.giveItems(960, 3);
						st.giveItems(9557, 1);
					}

					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("angkumi_q0288_08.htm", player);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == angkumi)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82 && st.getPlayer().isQuestComplete(250))
					return "angkumi_q0288_02.htm";

				st.exitCurrentQuest(true);
				return "angkumi_q0288_01.htm";
			}
			if(st.isStarted())
			{
				if(!st.haveQuestItems(q_scale_of_lizard_highest) && !st.haveQuestItems(q_scale_of_lizard_good))
				{
					if(cond != 1)
						st.setMemoState(1);
					return "npchtm:angkumi_q0288_05.htm";
				}
				if(cond == 2 && st.haveQuestItems(q_scale_of_lizard_good))
					return "npchtm:angkumi_q0288_06.htm";
				if(cond == 2 && st.haveQuestItems(q_scale_of_lizard_highest))
					return "npchtm:angkumi_q0288_07.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState qs = getRandomPartyMemberWithMemoState(killer, 1);
		if(qs != null)
		{
			if(npc.i_quest4 == 1 && !qs.haveQuestItems(q_scale_of_lizard_good))
			{
				qs.giveItems(q_scale_of_lizard_good, 1);
				qs.setCond(2);
				qs.setMemoState(2);
				qs.playSound(SOUND_MIDDLE);
				showQuestMark(qs.getPlayer());
			}
			else if(npc.i_quest4 != 1 && !qs.haveQuestItems(q_scale_of_lizard_highest))
			{
				qs.giveItems(q_scale_of_lizard_highest, 1);
				qs.setCond(2);
				qs.setMemoState(2);
				qs.playSound(SOUND_MIDDLE);
				showQuestMark(qs.getPlayer());
			}
		}
	}
}