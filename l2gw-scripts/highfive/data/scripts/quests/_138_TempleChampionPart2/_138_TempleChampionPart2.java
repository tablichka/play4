package quests._138_TempleChampionPart2;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

/**
 * @author rage
 * @date 23.12.10 14:19
 */
public class _138_TempleChampionPart2 extends Quest
{
	//NPC
	private final static int SYLVAIN = 30070;
	private final static int PUPINA = 30118;
	private final static int ANGUS = 30474;
	private final static int SLA = 30666;

	//ITEMS
	private final static int MANIFESTO = 10341;
	private final static int RELIC = 10342;
	private final static int ANGUS_REC = 10343;
	private final static int PUPINA_REC = 10344;

	// MONSTER
	private final static HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(4);
	static
	{
		dropChances.put(20176, 42);
		dropChances.put(20550, 46);
		dropChances.put(20551, 46);
		dropChances.put(20552, 100);
	}

	public _138_TempleChampionPart2()
	{
		super(138, "_138_TempleChampionPart2", "Temple Champion - Part 2"); // Party true

		addStartNpc(SYLVAIN);
		addTalkId(PUPINA);
		addTalkId(ANGUS);
		addTalkId(SLA);
		for(int npcId : dropChances.keySet())
			addKillId(npcId);
		addQuestItem(MANIFESTO, RELIC, ANGUS_REC, PUPINA_REC);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		if(npcId == SYLVAIN)
		{
			if(st.isCreated() && reply == 138 && player.isQuestComplete(137) && player.getLevel() >= 36)
			{
				st.giveItems(MANIFESTO, 1);
				st.setMemoState(0);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("sylvain_q0138_04.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
			else if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 1 && memoState == 0)
				{
					st.setMemoState(1);
					showPage("sylvain_q0138_06.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 2 && memoState == 13)
				{
					st.playSound(SOUND_FINISH);
					showPage("sylvain_q0138_09.htm", player);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 84593, 100);
					if(player.getLevel() < 41)
						st.addExpAndSp(187062, 11307);
				}
			}
		}
		else if(npcId == PUPINA)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 1 && memoState == 1)
				{
					st.setMemoState(2);
					showPage("pupina_q0138_03.htm", player);
				}
				else if(reply == 2 && memoState == 2)
					showPage("pupina_q0138_04.htm", player);
				else if(reply == 3 && memoState == 2)
					showPage("pupina_q0138_06.htm", player);
				else if(reply == 4 && memoState == 2)
					showPage("pupina_q0138_07.htm", player);
				else if(reply == 5 && memoState == 2)
				{
					st.setMemoState(3);
					showPage("pupina_q0138_08.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 6 && memoState == 8)
				{
					st.giveItems(PUPINA_REC, 1);
					st.setMemoState(9);
					showPage("pupina_q0138_11.htm", player);
					st.setCond(6);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npcId == ANGUS)
		{
			if(reply == 1 && st.getMemoState() == 3)
			{
				st.setMemoState(4);
				showPage("grandmaster_angus_q0138_03.htm", player);
				st.setCond(4);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npcId == SLA)
		{
			if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 1 && memoState == 9)
				{
					st.takeItems(PUPINA_REC, -1);
					st.setMemoState(10);
					showPage("preacher_sla_q0138_03.htm", player);
				}
				else if(reply == 2 && memoState == 10)
				{
					st.takeItems(MANIFESTO, -1);
					st.setMemoState(11);
					showPage("preacher_sla_q0138_05.htm", player);
				}
				else if(reply == 3 && memoState == 11)
					showPage("preacher_sla_q0138_07.htm", player);
				else if(reply == 4 && memoState == 11)
					showPage("preacher_sla_q0138_08.htm", player);
				else if(reply == 5 && memoState == 11)
				{
					st.setMemoState(12);
					showPage("preacher_sla_q0138_09.htm", player);
				}
				else if(reply == 6 && memoState == 12)
					showPage("preacher_sla_q0138_10.htm", player);
				else if(reply == 7 && memoState == 12)
				{
					st.setMemoState(13);
					showPage("preacher_sla_q0138_12.htm", player);
					st.setCond(7);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();
		if(npcId == SYLVAIN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().isQuestComplete(137))
				{
					if(st.getPlayer().getLevel() >= 36)
						return "sylvain_q0138_01.htm";
					return "sylvain_q0138_02.htm";
				}
			}
			else if(st.isStarted())
			{
				if(cond == 0)
					return "npchtm:sylvain_q0138_05.htm";
				if(cond >= 1 && cond <13)
					return "npchtm:sylvain_q0138_07.htm";
				if(cond == 13)
					return "npchtm:sylvain_q0138_08.htm";
			}
		}
		else if(npcId == PUPINA)
		{
			if(st.isStarted())
			{
				if(cond == 0)
					return "npchtm:pupina_q0138_01.htm";
				if(cond == 1)
					return "npchtm:pupina_q0138_02.htm";
				if(cond == 2)
					return "npchtm:pupina_q0138_05.htm";
				if(cond >= 3 && cond < 7)
					return "npchtm:pupina_q0138_09.htm";
				if(cond == 7)
				{
					st.takeItems(ANGUS_REC, -1);
					st.setMemoState(8);
					return "npchtm:pupina_q0138_10.htm";
				}
				if(cond == 8)
				{
					st.giveItems(PUPINA_REC, 1);
					st.setMemoState(9);
					st.setCond(6);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:pupina_q0138_12.htm";
				}
				if(cond >= 9)
					return "npchtm:pupina_q0138_13.htm";
			}
		}
		else if(npcId == ANGUS)
		{
			if(st.isStarted())
			{
				if(cond < 3)
					return "npchtm:grandmaster_angus_q0138_01.htm";
				if(cond == 3)
					return "npchtm:grandmaster_angus_q0138_02.htm";
				if(cond == 4)
				{
					if(st.getQuestItemsCount(RELIC) >= 10)
					{
						st.giveItems(ANGUS_REC, 1);
						st.takeItems(RELIC, -1);
						st.setMemoState(7);
						st.setCond(5);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
						return "npchtm:grandmaster_angus_q0138_05.htm";
					}
					return "npchtm:grandmaster_angus_q0138_04.htm";
				}
				if(cond >= 5)
					return "npchtm:grandmaster_angus_q0138_06.htm";
			}
		}
		else if(npcId == SLA)
		{
			if(st.isStarted())
			{
				if(cond < 9)
					return "npchtm:preacher_sla_q0138_01.htm";
				if(cond == 9)
					return "npchtm:preacher_sla_q0138_02.htm";
				if(cond == 10)
					return "npchtm:preacher_sla_q0138_04.htm";
				if(cond == 11)
					return "npchtm:preacher_sla_q0138_06.htm";
				if(cond == 12)
					return "npchtm:preacher_sla_q0138_11.htm";
				if(cond == 13)
					return "npchtm:preacher_sla_q0138_13.htm";
			}
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 4);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
				{
					QuestState qs = party.get(i);
					if(qs.getQuestItemsCount(RELIC) >= 10)
						party.remove(i);
				}

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					if(qs.rollAndGiveLimited(RELIC, 1, dropChances.get(npc.getNpcId()), 10))
					{
						if(qs.getQuestItemsCount(RELIC) >= 10)
							qs.playSound(SOUND_MIDDLE);
						else
							qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}