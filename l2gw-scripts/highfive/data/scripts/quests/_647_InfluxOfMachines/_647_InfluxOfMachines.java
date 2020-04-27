package quests._647_InfluxOfMachines;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

public class _647_InfluxOfMachines extends Quest
{
	// NPC
	public static final int collecter_gutenhagen = 32069;

	// Items
	private static final int q_broken_golem_re = 15521;

	// Mobs
	private static final HashMap<Integer, Double> dropChances = new HashMap<>(12);
	static
	{
		dropChances.put(22801, 28.0);
		dropChances.put(22802, 22.7);
		dropChances.put(22803, 28.6);
		dropChances.put(22804, 28.8);
		dropChances.put(22805, 23.5);
		dropChances.put(22806, 29.5);
		dropChances.put(22807, 27.3);
		dropChances.put(22808, 14.3);
		dropChances.put(22809, 62.9);
		dropChances.put(22810, 46.5);
		dropChances.put(22811, 84.9);
		dropChances.put(22812, 46.3);
	}

	public _647_InfluxOfMachines()
	{
		super(647, "_647_InfluxOfMachines", "Influx Of Machines"); // Party true

		addStartNpc(collecter_gutenhagen);
		addTalkId(collecter_gutenhagen);
		addQuestItem(q_broken_golem_re);
		for(int npcId : dropChances.keySet())
			addKillId(npcId);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == collecter_gutenhagen)
		{
			if(st.isStarted() && st.getInt("ex_1") != 99)
				return "collecter_gutenhagen_q0647_101.htm";
			if(st.isCreated() && talker.getLevel() >= 70)
				return "collecter_gutenhagen_q0647_0101.htm";
			if(st.isCreated() && talker.getLevel() < 70)
				return "collecter_gutenhagen_q0647_0102.htm";
			if(st.isStarted() && st.getMemoState() >= 11 && st.getMemoState() <= 12 && st.getInt("ex_1") == 99)
			{
				if(st.getMemoState() == 12 && st.getQuestItemsCount(q_broken_golem_re) >= 500)
				{
					return "npchtm:collecter_gutenhagen_q0647_0105.htm";
				}
				else
					return "npchtm:collecter_gutenhagen_q0647_0106.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == collecter_gutenhagen)
		{
			if(reply == 647)
			{
				if(talker.getLevel() >= 70)
				{
					st.setMemoState(11);
					st.set("ex_1", 99);
					st.setCond(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("collecter_gutenhagen_q0647_0103.htm", talker);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isStarted() && st.getInt("ex_1") != 99 && st.getQuestItemsCount(8100) < 1)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showQuestPage("collecter_gutenhagen_q0647_102.htm", talker);
				}
				else if(st.isStarted() && st.getInt("ex_1") != 99 && st.getQuestItemsCount(8100) >= 500)
				{
					int i1 = Rnd.get(1000);
					st.takeItems(8100, 500);
					if(i1 < 83)
					{
						st.giveItems(4963, 1);
					}
					else if(i1 < 166)
					{
						st.giveItems(4964, 1);
					}
					else if(i1 < 249)
					{
						st.giveItems(4965, 1);
					}
					else if(i1 < 333)
					{
						st.giveItems(4966, 1);
					}
					else if(i1 < 417)
					{
						st.giveItems(4967, 1);
					}
					else if(i1 < 501)
					{
						st.giveItems(4968, 1);
					}
					else if(i1 < 584)
					{
						st.giveItems(4969, 1);
					}
					else if(i1 < 667)
					{
						st.giveItems(4970, 1);
					}
					else if(i1 < 750)
					{
						st.giveItems(4971, 1);
					}
					else if(i1 < 834)
					{
						st.giveItems(4972, 1);
					}
					else if(i1 < 917)
					{
						st.giveItems(8310, 1);
					}
					else if(i1 < 1000)
					{
						st.giveItems(8322, 1);
					}
					st.takeItems(8100, 500);
					showQuestPage("collecter_gutenhagen_q0647_103.htm", talker);
				}
				else if(st.isStarted() && st.getInt("ex_1") != 99 && st.getQuestItemsCount(8100) >= 1 && st.getQuestItemsCount(8100) < 500)
				{
					long i0 = st.getQuestItemsCount(8100);
					i0 = i0 * 411;
					st.rollAndGive(57, i0, 100);
					st.addExpAndSp(i0 * 2, i0 * 2);
					st.takeItems(8100, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showQuestPage("collecter_gutenhagen_q0647_104.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getInt("ex_1") != 99 && st.getQuestItemsCount(8100) < 1)
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showQuestPage("collecter_gutenhagen_q0647_105.htm", talker);
				}
				else if(st.isStarted() && st.getInt("ex_1") != 99 && st.getQuestItemsCount(8100) >= 1)
				{
					long i0 = st.getQuestItemsCount(8100);
					i0 = (i0 * 411);
					st.rollAndGive(57, i0, 100);
					st.addExpAndSp(i0 * 2, i0 * 2);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showQuestPage("collecter_gutenhagen_q0647_106.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getInt("ex_1") != 99)
				{
					showQuestPage("collecter_gutenhagen_q0647_107.htm", talker);
					return;
				}
			}
			if(reply == 3 && st.isStarted() && st.getMemoState() >= 11)
			{
				if(st.getQuestItemsCount(q_broken_golem_re) >= 500)
				{
					int i1 = Rnd.get(10);
					st.takeItems(q_broken_golem_re, -1);
					if(i1 == 0)
					{
						st.giveItems(6881, 1);
					}
					else if(i1 == 1)
					{
						st.giveItems(6883, 1);
					}
					else if(i1 == 2)
					{
						st.giveItems(6885, 1);
					}
					else if(i1 == 3)
					{
						st.giveItems(6887, 1);
					}
					else if(i1 == 4)
					{
						st.giveItems(7580, 1);
					}
					else if(i1 == 5)
					{
						st.giveItems(6891, 1);
					}
					else if(i1 == 6)
					{
						st.giveItems(6893, 1);
					}
					else if(i1 == 7)
					{
						st.giveItems(6895, 1);
					}
					else if(i1 == 8)
					{
						st.giveItems(6897, 1);
					}
					else if(i1 == 9)
					{
						st.giveItems(6899, 1);
					}
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("collecter_gutenhagen_q0647_0201.htm", talker);
				}
				else
				{
					showPage("collecter_gutenhagen_q0647_0202.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithMemoState(killer, 11);
			if(st != null)
			{
				if(st.rollAndGiveLimited(q_broken_golem_re, 1, dropChances.get(npc.getNpcId()), 500))
				{
					if(st.getQuestItemsCount(q_broken_golem_re) >= 500)
					{
						st.playSound(SOUND_MIDDLE);
						st.setCond(2);
						showQuestMark(st.getPlayer());
						st.setMemoState(12);
					}
					else
					{
						st.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}