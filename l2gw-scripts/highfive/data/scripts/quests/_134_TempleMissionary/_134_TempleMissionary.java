package quests._134_TempleMissionary;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author admin
 * @date 22.12.10 18:20
 */
public class _134_TempleMissionary extends Quest
{
	//NPC
	private static final int GLYVKA = 30067;
	private static final int ROUKE = 31418;
	//Items
	private static final short FRAGMENT = 10335;
	private static final short TOOL = 10336;
	private static final short REPORT = 10337;
	private static final short REPORT2 = 10338;
	private static final short BADGE = 10339;
	//Mobs
	private static final int CrumaMarshlandsTraitor = 27339;
	private static final HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>();
	static
	{
		dropChances.put(20231, 83);
		dropChances.put(20233, 95);
		dropChances.put(20157, 78);
		dropChances.put(20234, 96);
		dropChances.put(20232, 81);
		dropChances.put(20230, 86);
		dropChances.put(20229, 75);
	}

	public _134_TempleMissionary()
	{
		super(134, "_134_TempleMissionary", "Temple Missionary");

		addStartNpc(GLYVKA);
		addTalkId(ROUKE);
		addKillId(CrumaMarshlandsTraitor);
		for(int npcId : dropChances.keySet())
			addKillId(npcId);
		addQuestItem(FRAGMENT, TOOL, REPORT, REPORT2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equals("glyvka_q0134_03.htm"))
		{
			if(st.getPlayer().getLevel() >= 35 && st.isCreated())
			{
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				st.setCond(1);
				st.setState(STARTED);
				return event;
			}
			return null;
		}
		else if(event.equals("glyvka_q0134_06.htm"))
		{
			st.setMemoState(2);
			st.setCond(2);
			st.playSound(SOUND_MIDDLE);
			st.setState(STARTED);
		}
		else if(event.equals("glyvka_q0134_09.htm"))
		{
			if(st.getMemoState() == 6)
				return "npchtm:" + event;
			return null;
		}
		else if(event.equals("glyvka_q0134_11.htm"))
		{
			if(st.getMemoState() == 6)
			{
				st.giveItems(BADGE, 1);
				st.playSound(SOUND_FINISH);
				st.rollAndGive(57, 15100, 100);
				if(st.getPlayer().getLevel() < 41)
					st.addExpAndSp(30000, 2000);
				st.exitCurrentQuest(false);
				return "npchtm:" + event;
			}
			return null;
		}
		else if(event.equals("scroll_seller_rouke_q0134_03.htm"))
		{
			if(st.getMemoState() == 2)
			{
				st.setMemoState(3);
				st.setCond(3);
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:" + event;
			}
			return null;
		}
		else if(event.equals("scroll_seller_rouke_q0134_09.htm"))
		{
			if(st.getMemoState() == 4)
			{
				st.setMemoState(5);
				st.setCond(5);
				st.setState(STARTED);
				st.giveItems(REPORT2, 1);
				st.playSound(SOUND_MIDDLE);
				return "npchtm:" + event;
			}
			return null;
		}

		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int ex_cond = st.getMemoState();

		if(npcId == GLYVKA)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 35)
					return "glyvka_q0134_01.htm";
				else
				{
					st.exitCurrentQuest(true);
					return "glyvka_q0134_02.htm";
				}
			}
			if(st.isStarted())
			{
				if(ex_cond == 1)
					return "npchtm:glyvka_q0134_04.htm";
				if(ex_cond >= 2 && ex_cond < 5)
					return "npchtm:glyvka_q0134_07.htm";
				if(ex_cond == 5)
				{
					st.takeItems(REPORT2, -1);
					st.setMemoState(6);
					return "npchtm:glyvka_q0134_08.htm";
				}
				if(ex_cond == 6)
					return "npchtm:glyvka_q0134_10.htm";
			}
		}
		else if(npcId == ROUKE && st.isStarted())
		{
			if(ex_cond < 2)
				return "npchtm:scroll_seller_rouke_q0134_01.htm";
			if(ex_cond == 2)
				return "npchtm:scroll_seller_rouke_q0134_02.htm";
			if(ex_cond == 3)
			{
				if(st.getQuestItemsCount(FRAGMENT) < 10 && st.getQuestItemsCount(REPORT) < 3)
					return "npchtm:scroll_seller_rouke_q0134_04.htm";
				if(st.getQuestItemsCount(FRAGMENT) >= 10 && st.getQuestItemsCount(REPORT) < 3)
				{
					long i0 = st.getQuestItemsCount(FRAGMENT) / 10;
					if(i0 > 0)
					{
						st.giveItems(TOOL, i0);
						st.takeItems(FRAGMENT, i0 * 10);
					}

					return "npchtm:scroll_seller_rouke_q0134_05.htm";
				}
				if(st.getQuestItemsCount(REPORT) >= 3)
				{
					st.takeItems(FRAGMENT, -1);
					st.takeItems(TOOL, -1);
					st.takeItems(REPORT, -1);
					st.setMemoState(4);
					return "npchtm:scroll_seller_rouke_q0134_06.htm";
				}
			}
			if(ex_cond == 4)
				return "npchtm:scroll_seller_rouke_q0134_08.htm";
			if(ex_cond >= 5)
				return "npchtm:scroll_seller_rouke_q0134_10.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> party = getPartyMembersWithMemoState(killer, 3);
		if(party.size() > 0)
		{
			if(npc.getNpcId() == CrumaMarshlandsTraitor)
			{
				L2Player player = L2ObjectsStorage.getPlayer(npc.i_ai0);
				if(player != null && npc.isInRange(player, 1500))
				{
					QuestState qs = player.getQuestState(134);
					if(qs != null && qs.getMemoState() == 3 && qs.getQuestItemsCount(REPORT) < 3)
					{
						qs.giveItems(REPORT, 1);
						if(qs.getQuestItemsCount(REPORT) >= 3)
						{
							qs.setCond(4);
							qs.setState(STARTED);
							qs.playSound(SOUND_MIDDLE);
						}
						else
							qs.playSound(SOUND_ITEMGET);
					}
				}
			}
			else if(dropChances.containsKey(npc.getNpcId()))
			{
				for(int i = 0; i < party.size(); i ++)
				{
					QuestState qs = party.get(i);
					if(qs.getQuestItemsCount(REPORT) >= 3)
						party.remove(i);
				}

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					if(qs.haveQuestItems(TOOL))
					{
						L2NpcInstance mob = addSpawn(CrumaMarshlandsTraitor, npc.getLoc(), true, 60000);
						mob.getAI().setGlobalAggro(10);
						mob.i_ai0 = qs.getPlayer().getObjectId();
						qs.takeItems(TOOL, 1);
					}
					else if(Rnd.chance(dropChances.get(npc.getNpcId())))
					{
						qs.rollAndGive(FRAGMENT, 1, 100);
						qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}