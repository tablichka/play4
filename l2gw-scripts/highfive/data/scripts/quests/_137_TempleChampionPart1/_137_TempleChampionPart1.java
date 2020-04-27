package quests._137_TempleChampionPart1;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author admin
 * @date 23.12.10 13:03
 */
public class _137_TempleChampionPart1 extends Quest
{
	// NPCs
	private static final int SYLVAIN = 30070;

	// ITEMs
	private static final int FRAGMENT = 10340;
	private static final int BadgeTempleExecutor = 10334;
	private static final int BadgeTempleMissionary = 10339;

	private final static HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>(6);
	static
	{
		dropChances.put(20200, 78);
		dropChances.put(20144, 100);
		dropChances.put(20083, 100);
		dropChances.put(20201, 92);
		dropChances.put(20202, 92);
		dropChances.put(20199, 89);
	}

	public _137_TempleChampionPart1()
	{
		super(137, "_137_TempleChampionPart1", "Temple Champion - Part 1");

		addStartNpc(SYLVAIN);
		for(int npcId : dropChances.keySet())
			addKillId(npcId);
		addQuestItem(FRAGMENT);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		if(reply == 137 && st.isCreated())
		{
			if(player.isQuestComplete(134) && player.isQuestComplete(135) && player.getLevel() >= 35)
			{
				st.setState(STARTED);
				st.setCond(1);
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("sylvain_q0137_04.htm", player);
			}
		}
		else if(st.isStarted())
		{
			int memoState = st.getMemoState();
			if(reply == 1 && memoState == 1)
				showPage("sylvain_q0137_05.htm", player);
			else if(reply == 2 && memoState >= 1)
				showPage("sylvain_q0137_07.htm", player);
			else if(reply == 3 && memoState == 1)
			{
				st.setMemoState(2);
				showPage("sylvain_q0137_08.htm", player);
			}
			else if(reply == 5 && memoState == 2)
			{
				st.setMemoState(3);
				showPage("sylvain_q0137_10.htm", player);
			}
			else if(reply == 6 && memoState == 3)
				showPage("sylvain_q0137_12.htm", player);
			else if(reply == 7 && memoState == 3)
			{
				st.setMemoState(5);
				showPage("sylvain_q0137_13.htm", player);
				st.setCond(2);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
			}
			else if(reply == 8 && memoState == 6)
				showPage("sylvain_q0137_16.htm", player);
			else if(reply == 9 && memoState == 6)
				showPage("sylvain_q0137_18.htm", player);
			else if(reply == 10 && memoState == 6)
			{
				st.setMemoState(7);
				showPage("sylvain_q0137_19.htm", player);
			}
			else if(reply == 11 && memoState == 7)
				showPage("sylvain_q0137_21.htm", player);
			else if(reply == 12 && memoState == 7)
				showPage("sylvain_q0137_22.htm", player);
			else if(reply == 13 && memoState == 7)
				showPage("sylvain_q0137_23.htm", player);
			else if(reply == 14 && memoState == 7)
			{
				st.takeItems(BadgeTempleExecutor, -1);
				st.takeItems(BadgeTempleMissionary, -1);
				st.playSound(SOUND_FINISH);
				showPage("sylvain_q0137_24.htm", player);
				st.exitCurrentQuest(false);
				st.rollAndGive(57, 69146, 100);
				if(player.getLevel() < 41)
					st.addExpAndSp(219975, 13047);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();
		if(npcId == SYLVAIN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().isQuestComplete(134) && st.getPlayer().isQuestComplete(135))
				{
					if(st.getPlayer().getLevel() >= 35)
						return "sylvain_q0137_01.htm";

					return "sylvain_q0137_02.htm";
				}
				return "npchtm:sylvain_q0137_03.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:sylvain_q0137_06.htm";
				if(cond == 2)
					return "npchtm:sylvain_q0137_09.htm";
				if(cond == 3)
					return "npchtm:sylvain_q0137_11.htm";
				if(cond == 5)
				{
					if(st.getQuestItemsCount(FRAGMENT) >= 30)
					{
						st.takeItems(FRAGMENT, -1);
						st.setMemoState(6);
						return "npchtm:sylvain_q0137_15.htm";
					}
					return "sylvain_q0137_14.htm";
				}
				if(cond == 6)
					return "npchtm:sylvain_q0137_17.htm";
				if(cond == 7)
					return "npchtm:sylvain_q0137_20.htm";

			}
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			GArray<QuestState> party = getPartyMembersWithMemoState(killer, 5);
			if(party.size() > 0)
			{
				for(int i = 0; i < party.size(); i++)
				{
					QuestState qs = party.get(i);
					if(qs.getQuestItemsCount(FRAGMENT) >= 30)
						party.remove(i);
				}

				if(party.size() > 0)
				{
					QuestState qs = party.get(Rnd.get(party.size()));
					if(qs.rollAndGiveLimited(FRAGMENT, 1, dropChances.get(npc.getNpcId()), 30))
					{
						if(qs.getQuestItemsCount(FRAGMENT) >= 30)
						{
							qs.setCond(3);
							showQuestMark(qs.getPlayer());
							qs.playSound(SOUND_MIDDLE);
						}
						else
							qs.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}
}