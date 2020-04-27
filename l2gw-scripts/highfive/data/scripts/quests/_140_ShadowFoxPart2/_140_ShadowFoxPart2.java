package quests._140_ShadowFoxPart2;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author rage
 * @date 23.12.10 16:34
 */
public class _140_ShadowFoxPart2 extends Quest
{
	//NPCs
	private final static int KLUCK = 30895;
	private final static int XENOVIA = 30912;

	//ITEMs
	private final static int CRYSTAL = 10347;
	private final static int OXYDE = 10348;
	private final static int CRYPT = 10349;

	//MONSTERs
	private final static HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(4);
	static
	{
		dropChances.put(20789, 45);
		dropChances.put(20791, 100);
		dropChances.put(20790, 58);
		dropChances.put(20792, 92);
	}

	public _140_ShadowFoxPart2()
	{
		super(140, "_140_ShadowFoxPart2", "Shadow Fox Part 2");

		addStartNpc(KLUCK);

		addTalkId(KLUCK);
		addTalkId(XENOVIA);

		addQuestItem(CRYSTAL);
		addQuestItem(OXYDE);
		addQuestItem(CRYPT);

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

		if(npcId == KLUCK)
		{
			if(st.isCreated() && reply == 140)
			{
				if(player.getLevel() >= 37 && player.isQuestComplete(139))
				{
					st.setMemoState(1);
					st.setCond(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("warehouse_keeper_kluck_q0140_04.htm", player);
					st.setState(STARTED);
				}
			}
			else if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(reply == 1 && memoState == 1)
					showPage("warehouse_keeper_kluck_q0140_06.htm", player);
				else if(reply == 2 && memoState == 1)
					showPage("warehouse_keeper_kluck_q0140_07.htm", player);
				else if(reply == 3 && memoState == 1)
					showPage("warehouse_keeper_kluck_q0140_08.htm", player);
				else if(reply == 4 && memoState == 1)
				{
					st.setMemoState(2);
					st.setCond(2);
					st.playSound(SOUND_MIDDLE);
					showPage("warehouse_keeper_kluck_q0140_09.htm", player);
					showQuestMark(player);
				}
				else if(reply == 5 && memoState == 6)
					showPage("warehouse_keeper_kluck_q0140_12.htm", player);
				else if(reply == 6 && memoState == 6)
				{
					st.playSound(SOUND_FINISH);
					showPage("warehouse_keeper_kluck_q0140_14.htm", player);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 18775, 100);
					if(player.getLevel() < 43)
						st.addExpAndSp(30000, 2000);
				}
			}
		}
		else if(npcId == XENOVIA && st.isStarted())
		{
			int memoState = st.getMemoState();
			if(reply == 1 && memoState == 2)
				showPage("magister_xenovia_q0140_03.htm", player);
			if(reply == 2 && memoState == 2)
				showPage("magister_xenovia_q0140_04.htm", player);
			if(reply == 3 && memoState == 2)
				showPage("magister_xenovia_q0140_05.htm", player);
			if(reply == 4 && memoState == 2)
			{
				st.setMemoState(3);
				showPage("magister_xenovia_q0140_06.htm", player);
			}
			if(reply == 5 && memoState == 3)
				showPage("magister_xenovia_q0140_08.htm", player);
			if(reply == 6 && memoState == 3)
			{
				st.setMemoState(4);
				showPage("magister_xenovia_q0140_09.htm", player);
				st.playSound(SOUND_MIDDLE);
				st.setCond(3);
				showQuestMark(player);
			}
			if(reply == 7 && memoState == 4)
			{
				int i0 = Rnd.get(10);
				if(i0 <= 8)
				{
					if(st.getQuestItemsCount(OXYDE) < 2)
					{
						st.giveItems(OXYDE, 1);
						st.takeItems(CRYSTAL, 5);
						showPage("magister_xenovia_q0140_12.htm", player);
					}
					else
					{
						st.giveItems(CRYPT, 1);
						st.takeItems(CRYSTAL, -1);
						st.takeItems(OXYDE, -1);
						st.setMemoState(5);
						showPage("magister_xenovia_q0140_13.htm", player);
						st.playSound(SOUND_MIDDLE);
						st.setCond(4);
						showQuestMark(player);
					}
				}
				else
				{
					st.takeItems(CRYSTAL, 5);
					showPage("magister_xenovia_q0140_14.htm", player);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();
		String htmltext = "noquest";

		if(npcId == KLUCK)
		{
			if(st.isCreated() && st.getPlayer().isQuestComplete(139))
			{
				if(st.getPlayer().getLevel() >= 37)
					return "warehouse_keeper_kluck_q0140_01.htm";
				else
					return "warehouse_keeper_kluck_q0140_02.htm";
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:warehouse_keeper_kluck_q0140_05.htm";
				if(cond >= 2 && cond <= 4)
					return "npchtm:warehouse_keeper_kluck_q0140_10.htm";
				if(cond == 5)
				{
					st.takeItems(CRYPT, -1);
					st.setMemoState(6);
					return "npchtm:warehouse_keeper_kluck_q0140_11.htm";
				}
				if(cond == 6)
					return "npchtm:warehouse_keeper_kluck_q0140_13.htm";
			}
		}
		else if(npcId == XENOVIA)
		{
			if(st.isStarted())
			{
				if(cond < 2)
					return "npchtm:magister_xenovia_q0140_01.htm";
				if(cond == 2)
					return "npchtm:magister_xenovia_q0140_02.htm";
				if(cond == 3)
					return "npchtm:magister_xenovia_q0140_07.htm";
				if(cond == 4 && st.getQuestItemsCount(OXYDE) < 3)
				{
					if(st.getQuestItemsCount(CRYSTAL) < 5)
						return "npchtm:magister_xenovia_q0140_10.htm";

					return "npchtm:magister_xenovia_q0140_11.htm";
				}
				if(cond >= 5)
					return "npchtm:magister_xenovia_q0140_15.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState qs = getRandomPartyMemberWithMemoState(killer, 4);
			if(qs != null && qs.rollAndGive(CRYSTAL, 1, dropChances.get(npc.getNpcId())))
				qs.playSound(SOUND_ITEMGET);
		}
	}
}