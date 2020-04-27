package quests._139_ShadowFoxPart1;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

/**
 * @author rage
 * @date 23.12.10 15:37
 */
public class _139_ShadowFoxPart1 extends Quest
{
	//NPC
	private final static int MIA = 30896;

	//ITEM
	private final static int FRAGMENT = 10345;
	private final static int CHEST = 10346;

	//MONSTER
	private final static HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(4);
	static
	{
		dropChances.put(20784, 68);
		dropChances.put(20785, 65);
		dropChances.put(21639, 100);
		dropChances.put(21640, 68);
	}

	public _139_ShadowFoxPart1()
	{
		super(139, "_139_ShadowFoxPart1", "Shadow Fox Part 1");

		addStartNpc(MIA);
		addTalkId(MIA);

		addQuestItem(FRAGMENT, CHEST);

		for(int npcId : dropChances.keySet())
			addKillId(npcId);
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

		if(npcId == MIA)
		{
			if(st.isCreated())
			{
				if(reply == 139 && player.isQuestComplete(138))
				{
					st.setMemoState(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("warehouse_keeper_mia_q0139_05.htm", player);
					st.setCond(1);
					st.setState(STARTED);
				}
				else if(reply == 1 && player.isQuestComplete(138))
				{
					if(player.getLevel() >= 37)
						showQuestPage("warehouse_keeper_mia_q0139_02.htm", player);
					else
						showQuestPage("warehouse_keeper_mia_q0139_03.htm", player);
				}
			}
			else if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 2 && memoState == 1)
					showPage("warehouse_keeper_mia_q0139_07.htm", player);
				else if(reply == 3 && memoState == 1)
					showPage("warehouse_keeper_mia_q0139_08.htm", player);
				else if(reply == 4 && memoState == 1)
					showPage("warehouse_keeper_mia_q0139_09.htm", player);
				else if(reply == 5 && memoState == 1)
					showPage("warehouse_keeper_mia_q0139_10.htm", player);
				else if(reply == 6 && memoState == 1)
					showPage("warehouse_keeper_mia_q0139_11.htm", player);
				else if(reply == 7 && memoState == 1)
				{
					st.setMemoState(2);
					showPage("warehouse_keeper_mia_q0139_12.htm", player);
				}
				else if(reply == 8 && memoState == 2)
					showPage("warehouse_keeper_mia_q0139_14.htm", player);
				else if(reply == 9 && memoState == 2)
				{
					st.setMemoState(3);
					showPage("warehouse_keeper_mia_q0139_15.htm", player);
					st.playSound(SOUND_MIDDLE);
					st.setCond(2);
					showQuestMark(player);
				}
				else if(reply == 10 && memoState == 3)
				{
				 	int i0 = Rnd.get(20);
					if(i0 == 0)
					{
						st.takeItems(FRAGMENT, 10);
						st.takeItems(CHEST, 1);
						showPage("warehouse_keeper_mia_q0139_18.htm", player);
					}
					else if(i0 == 1)
					{
						st.takeItems(FRAGMENT, 10);
						st.takeItems(CHEST, 1);
						showPage("warehouse_keeper_mia_q0139_19.htm", player);
					}
					else
					{
						st.takeItems(FRAGMENT, -1);
						st.takeItems(CHEST, -1);
						st.setMemoState(4);
						showPage("warehouse_keeper_mia_q0139_20.htm", player);
					}
				}
				else if(reply == 11 && memoState == 4)
					showPage("warehouse_keeper_mia_q0139_21.htm", player);
				else if(reply == 12 && memoState == 4)
				{
					st.rollAndGive(57, 14050, 100);
					if(player.getLevel() < 43)
						st.addExpAndSp(30000, 2000);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					showPage("warehouse_keeper_mia_q0139_23.htm", player);
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
		int cond = st.getMemoState();
		int npcId = npc.getNpcId();
		if(npcId == MIA)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().isQuestComplete(138))
					return "warehouse_keeper_mia_q0139_01.htm";

				st.exitCurrentQuest(true);
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:warehouse_keeper_mia_q0139_06.htm";
				if(cond == 2)
					return "npchtm:warehouse_keeper_mia_q0139_13.htm";
				if(cond == 3)
				{
					if(st.getQuestItemsCount(FRAGMENT) < 10 || st.getQuestItemsCount(CHEST) < 1)
						return "npchtm:warehouse_keeper_mia_q0139_16.htm";

					return "npchtm:warehouse_keeper_mia_q0139_17.htm";
				}
				if(cond == 4)
					return "npchtm:warehouse_keeper_mia_q0139_22.htm";
			}
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 3);
			if(qs != null && Rnd.chance(dropChances.get(npc.getNpcId())))
			{
				if(Rnd.get(11) < 10)
					qs.rollAndGive(FRAGMENT, 1, 100);
				else
					qs.rollAndGive(CHEST, 1, 100);
				qs.playSound(SOUND_ITEMGET);
			}
		}
	}
}