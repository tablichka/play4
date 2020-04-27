package quests._141_ShadowFoxPart3;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author rage
 * @date 23.12.10 17:23
 */
public class _141_ShadowFoxPart3 extends Quest
{
	//NPCs
	private final static int NATOOLS = 30894;

	//ITEMs
	private final static int REPORT = 10350;

	//MONSTERs
	private final static HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(4);

	static
	{
		dropChances.put(20791, 100);
		dropChances.put(20792, 93);
		dropChances.put(20135, 53);
	}

	public _141_ShadowFoxPart3()
	{
		super(141, "_141_ShadowFoxPart3", "Shadow Fox Part 3");

		addStartNpc(NATOOLS);
		addTalkId(NATOOLS);
		addQuestItem(REPORT);
		for(int npcId : dropChances.keySet())
			addKillId(npcId);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == NATOOLS)
		{
			if(st.isCreated() && player.getLevel() >= 37 && player.isQuestComplete(140))
			{
				st.playSound(SOUND_ACCEPT);
				st.setMemoState(1);
				showQuestPage("warehouse_chief_natools_q0141_03.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
			else if(st.isStarted())
			{
				int memoState = st.getMemoState();
				if(memoState == 1)
				{
					if(reply == 1)
						showPage("warehouse_chief_natools_q0141_05.htm", player);
					else if(reply == 2)
					{
						st.setMemoState(2);
						showPage("warehouse_chief_natools_q0141_06.htm", player);
						st.setCond(2);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(memoState == 3)
				{
					if(reply == 3)
						showPage("warehouse_chief_natools_q0141_10.htm", player);
					else if(reply == 4)
						showPage("warehouse_chief_natools_q0141_11.htm", player);
					else if(reply == 5)
						showPage("warehouse_chief_natools_q0141_12.htm", player);
					else if(reply == 6)
						showPage("warehouse_chief_natools_q0141_13.htm", player);
					else if(reply == 7)
						showPage("warehouse_chief_natools_q0141_14.htm", player);
					else if(reply == 8)
					{
						st.setMemoState(4);
						showPage("warehouse_chief_natools_q0141_15.htm", player);
					}
				}
				else if(memoState == 4)
				{
					if(reply == 9)
						showPage("warehouse_chief_natools_q0141_17.htm", player);
					else if(reply == 10)
						showPage("warehouse_chief_natools_q0141_18.htm", player);
					else if(reply == 11)
					{
						st.setMemoState(5);
						showPage("warehouse_chief_natools_q0141_19.htm", player);
						st.setCond(4);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(memoState == 5)
				{
					if(reply == 12)
						showPage("warehouse_chief_natools_q0141_21.htm", player);
					else if(reply == 13)
						showPage("warehouse_chief_natools_q0141_22.htm", player);
					else if(reply == 14)
					{
						showPage("warehouse_chief_natools_q0141_23.htm", player);
						st.playSound(SOUND_FINISH);
						st.exitCurrentQuest(false);
						st.rollAndGive(57, 88888, 100);
						if(player.getLevel() < 43)
							st.addExpAndSp(278005, 17058);
					}
				}
			}
			else if(st.isCompleted())
			{
				if(reply == 15)
				{
					if(!player.isQuestComplete(142) && !player.isQuestComplete(143))
					{
						if(player.getLevel() >= 38)
							showPage("warehouse_chief_natools_q0141_25.htm", player);
						else
							showPage("warehouse_chief_natools_q0141_24.htm", player);
					}
				}
				else if(reply == 16)
				{
					if(!player.isQuestComplete(142) && !player.isQuestComplete(143))
						showPage("warehouse_chief_natools_q0141_26.htm", player);
				}
				else if(reply == 17)
				{
					if(!player.isQuestComplete(142) && !player.isQuestComplete(143))
						showPage("warehouse_chief_natools_q0141_27.htm", player);
				}
				else
					showPage("completed", player);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int cond = st.getMemoState();
		String htmltext = "noquest";

		if(st.isCreated() && st.getPlayer().isQuestComplete(140))
		{
			if(st.getPlayer().getLevel() >= 37)
				return "warehouse_chief_natools_q0141_01.htm";

			return "warehouse_chief_natools_q0141_02.htm";
		}
		else if(st.isStarted())
		{
			if(cond == 1)
				return "npchtm:warehouse_chief_natools_q0141_04.htm";
			if(cond == 2)
			{
				if(st.getQuestItemsCount(REPORT) >= 30)
				{
					st.takeItems(REPORT, -1);
					st.setMemoState(3);
					return "npchtm:warehouse_chief_natools_q0141_08.htm";
				}

				return "npchtm:warehouse_chief_natools_q0141_07.htm";
			}
			if(cond == 3)
				return "npchtm:warehouse_chief_natools_q0141_09.htm";
			if(cond == 4)
				return "npchtm:warehouse_chief_natools_q0141_16.htm";
			if(cond == 5)
				return "npchtm:warehouse_chief_natools_q0141_20.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 2);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
				{
					QuestState qs = party.get(i);
					if(qs.getQuestItemsCount(REPORT) >= 30)
						party.remove(i);
				}

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					if(qs.rollAndGiveLimited(REPORT, 1, dropChances.get(npc.getNpcId()), 30))
					{
						if(qs.getQuestItemsCount(REPORT) >= 30)
						{
							qs.playSound(SOUND_MIDDLE);
							qs.setCond(3);
							showQuestMark(qs.getPlayer());
						}
						else
							qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}