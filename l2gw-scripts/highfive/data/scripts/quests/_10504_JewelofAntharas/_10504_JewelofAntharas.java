package quests._10504_JewelofAntharas;

import ru.l2gw.gameserver.model.L2CommandChannel;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 15.09.11 22:57
 */
public class _10504_JewelofAntharas extends Quest
{
	// NPC
	private static final int watcher_antaras_theodric = 30755;

	// Items
	private static final int q_g_empty_orb1 = 21905;
	private static final int q_brimful_orb1 = 21907;
	private static final int g_orb_of_earth_dragon1 = 21898;
	private static final int q_portal_stone_1 = 3865;

	public _10504_JewelofAntharas()
	{
		super(10504, "_10504_JewelofAntharas", "Jewel of Antharas");
		addStartNpc(watcher_antaras_theodric);
		addTalkId(watcher_antaras_theodric);
		addKillId(29019, 29066, 29067, 29068);
		addQuestItem(q_g_empty_orb1, q_brimful_orb1);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == watcher_antaras_theodric)
		{
			if(st.isCompleted())
				return "npchtm:watcher_antaras_theodric_q10504_03.htm";

			if(st.isCreated())
			{
				if(talker.getLevel() >= 84 && st.getQuestItemsCount(q_portal_stone_1) >= 1)
					return "watcher_antaras_theodric_q10504_01.htm";
				if(talker.getLevel() < 84)
					return "watcher_antaras_theodric_q10504_02.htm";
				if(talker.getLevel() >= 84 && st.getQuestItemsCount(q_portal_stone_1) < 1)
					return "watcher_antaras_theodric_q10504_04.htm";
			}

			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_g_empty_orb1) >= 1)
					return "npchtm:watcher_antaras_theodric_q10504_08.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(q_g_empty_orb1) == 0)
				{
					st.giveItems(q_g_empty_orb1, 1);
					return "npchtm:watcher_antaras_theodric_q10504_09.htm";
				}
				if(st.getMemoState() == 2)
				{
					st.giveItems(g_orb_of_earth_dragon1, 1);
					st.takeItems(q_brimful_orb1, 1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:watcher_antaras_theodric_q10504_10.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == watcher_antaras_theodric)
		{
			if(st.isCompleted())
			{
				showPage("watcher_antaras_theodric_q10504_03.htm", talker);
				return;
			}

			if(reply == 10504)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) >= 1 && talker.getLevel() >= 84)
				{
					st.giveItems(q_g_empty_orb1, 1);
					st.setCond(1);
					st.setMemoState(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("watcher_antaras_theodric_q10504_07.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) >= 1 && talker.getLevel() >= 84)
				{
					showPage("watcher_antaras_theodric_q10504_05.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && st.getQuestItemsCount(q_portal_stone_1) >= 1 && talker.getLevel() >= 84)
				{
					showQuestPage("watcher_antaras_theodric_q10504_06.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Party party = killer.getParty();
		if(party != null)
		{
			L2CommandChannel cc = party.getCommandChannel();
			if(cc != null)
				for(L2Party pp : cc.getParties())
					for(L2Player member : pp.getPartyMembers())
						if(npc.isInRange(member, 8000))
						{
							QuestState qs = member.getQuestState(10504);
							if(qs != null && qs.getMemoState() == 1 && qs.getQuestItemsCount(q_g_empty_orb1) > 0)
								{
									qs.takeItems(q_g_empty_orb1, -1);
									qs.giveItems(q_brimful_orb1, 1);
									qs.setMemoState(2);
									qs.setCond(2);
									showQuestMark(member);
									qs.playSound(SOUND_MIDDLE);
								}
						}
		}
	}
}
