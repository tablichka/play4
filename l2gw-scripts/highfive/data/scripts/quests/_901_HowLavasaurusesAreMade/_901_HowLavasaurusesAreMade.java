package quests._901_HowLavasaurusesAreMade;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 08.10.11 0:08
 */
public class _901_HowLavasaurusesAreMade extends Quest
{
	// NPC
	private static final int warsmith_rooney = 32049;

	// Mobs
	private static final int lavasaurus_lv1 = 18799;
	private static final int lavasaurus_lv2 = 18800;
	private static final int lavasaurus_lv3 = 18801;
	private static final int lavasaurus_lv4 = 18802;

	// Items
	private static final int g_item_totem_of_body1 = 21899;
	private static final int g_item_totem_of_mind1 = 21900;
	private static final int g_item_totem_of_bravery1 = 21901;
	private static final int g_item_totem_of_fortitude1 = 21902;
	private static final int g_stone_of_lavasaurus1 = 21909;
	private static final int g_head_part_of_lavasaurus1 = 21910;
	private static final int g_body_part_of_lavasaurus1 = 21911;
	private static final int g_horn_part_of_lavasaurus1 = 21912;

	private static final HashMap<Integer, Integer> items = new HashMap<>(4);

	static
	{
		items.put(lavasaurus_lv1, g_stone_of_lavasaurus1);
		items.put(lavasaurus_lv2, g_head_part_of_lavasaurus1);
		items.put(lavasaurus_lv3, g_body_part_of_lavasaurus1);
		items.put(lavasaurus_lv4, g_horn_part_of_lavasaurus1);
	}

	public _901_HowLavasaurusesAreMade()
	{
		super(901, "_901_HowLavasaurusesAreMade", "How Lavasauruses Are Made");
		addStartNpc(warsmith_rooney);
		addTalkId(warsmith_rooney);

		addKillId(lavasaurus_lv1, lavasaurus_lv2, lavasaurus_lv3, lavasaurus_lv4);
		addQuestItem(g_stone_of_lavasaurus1, g_head_part_of_lavasaurus1, g_body_part_of_lavasaurus1, g_horn_part_of_lavasaurus1);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == warsmith_rooney)
		{
			if(st.isCreated() && talker.getLevel() >= 76)
				return "warsmith_rooney_q0901_01.htm";
			if(st.isCompleted())
				return "warsmith_rooney_q0901_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(g_stone_of_lavasaurus1) >= 10 && st.getQuestItemsCount(g_body_part_of_lavasaurus1) >= 10 && st.getQuestItemsCount(g_horn_part_of_lavasaurus1) >= 10)
				{
					st.takeItems(g_head_part_of_lavasaurus1, -1);
					st.takeItems(g_body_part_of_lavasaurus1, -1);
					st.takeItems(g_horn_part_of_lavasaurus1, -1);
					st.takeItems(g_stone_of_lavasaurus1, -1);
					st.setMemoState(2);
					return "npchtm:warsmith_rooney_q0901_07.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:warsmith_rooney_q0901_08.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == warsmith_rooney)
		{
			if(reply == 901)
			{
				if(st.isCreated() && talker.getLevel() >= 76)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.set("ex_1", 1);
					showQuestPage("warsmith_rooney_q0901_05.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(talker.getLevel() < 76)
				{
					showPage("warsmith_rooney_q0901_03.htm", talker);
				}
				else if(st.isCreated() && talker.getLevel() >= 76)
				{
					showQuestPage("warsmith_rooney_q0901_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				st.giveItems(g_item_totem_of_body1, 1);
				showPage("warsmith_rooney_q0901_13.htm", talker);
				st.exitCurrentQuest(false, true);
			}
			else if(reply == 3)
			{
				st.giveItems(g_item_totem_of_mind1, 1);
				showPage("warsmith_rooney_q0901_14.htm", talker);
				st.exitCurrentQuest(false, true);
			}
			else if(reply == 4)
			{
				st.giveItems(g_item_totem_of_fortitude1, 1);
				showPage("warsmith_rooney_q0901_15.htm", talker);
				st.exitCurrentQuest(false, true);
			}
			else if(reply == 5)
			{
				st.giveItems(g_item_totem_of_bravery1, 1);
				showPage("warsmith_rooney_q0901_16.htm", talker);
				st.exitCurrentQuest(false, true);
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> party = getPartyMembersWithMemoState(killer, 1);

		if(party.isEmpty())
			return;

		if(items.containsKey(npc.getNpcId()))
		{
			int itemId = items.get(npc.getNpcId());
			for(QuestState st : party)
			{
				if(st.getQuestItemsCount(itemId) < 10 && st.rollAndGiveLimited(itemId, 1, 100, 10))
				{
					if(st.getQuestItemsCount(g_stone_of_lavasaurus1) >= 10 && st.getQuestItemsCount(g_head_part_of_lavasaurus1) >= 10 && st.getQuestItemsCount(g_body_part_of_lavasaurus1) >= 10 && st.getQuestItemsCount(g_horn_part_of_lavasaurus1) >= 10)
					{
						st.setCond(2);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						st.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}
}