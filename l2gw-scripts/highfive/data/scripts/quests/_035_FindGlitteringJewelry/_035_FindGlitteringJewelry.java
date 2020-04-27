package quests._035_FindGlitteringJewelry;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 15.01.2011 13:19
 */
public class _035_FindGlitteringJewelry extends Quest
{
	// NPCs
	private static final int elliany = 30091;
	private static final int wharf_manager_felton = 30879;

	// Items
	private static final int q_rough_jewel = 7162;
	private static final int oriharukon = 1893;
	private static final int silver_nugget = 1873;
	private static final int thons = 4044;
	private static final int q_box_of_jewel = 7077;

	public _035_FindGlitteringJewelry()
	{
		super(35, "_035_FindGlitteringJewelry", "Find Glittering Jewelry");

		addStartNpc(elliany);
		addTalkId(elliany, wharf_manager_felton);
		addKillId(20135);
		addQuestItem(q_rough_jewel);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("completed", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == elliany)
		{
			if(reply == 35 && player.getLevel() >= 60)
			{
				st.setMemoState(11);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("elliany_q0035_0104.htm", player);
			}
			else if(reply == 1 && st.isStarted() && st.getInt("cookie") == 2)
			{
				if(st.getQuestItemsCount(q_rough_jewel) >= 10)
				{
					st.takeItems(q_rough_jewel, 10);
					showPage("elliany_q0035_0301.htm", player);
					st.setMemoState(31);
					st.setCond(4);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else
					showPage("elliany_q0035_0302.htm", player);
			}
			else if(reply == 3 && st.isStarted() && st.getInt("cookie") == 3)
			{
				if(st.getQuestItemsCount(oriharukon) >= 5 && st.getQuestItemsCount(silver_nugget) >= 500 && st.getQuestItemsCount(thons) >= 150)
				{
					st.takeItems(oriharukon, 5);
					st.takeItems(silver_nugget, 500);
					st.takeItems(thons, 150);
					st.giveItems(q_box_of_jewel, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("elliany_q0035_0401.htm", player);
				}
				else
					showPage("elliany_q0035_0402.htm", player);
			}
		}
		else if(st.isStarted() && npcId == wharf_manager_felton)
		{
			if(reply == 1 && st.getInt("cookie") == 1)
			{
				showPage("wharf_manager_felton_q0035_0201.htm", player);
				st.setMemoState(21);
				st.setCond(2);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
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

		if(npcId == elliany)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 60)
					return "elliany_q0035_0101.htm";

				st.exitCurrentQuest(true);
				return "npchtm:elliany_q0035_0103.htm";
			}
			if(st.isStarted())
			{
				if(cond == 11)
					return "npchtm:elliany_q0035_0105.htm";
				if(cond == 22)
				{
					if(st.getQuestItemsCount(q_rough_jewel) >= 10)
					{
						st.set("cookie", 2);
						return "npchtm:elliany_q0035_0201.htm";
					}

					return "npchtm:elliany_q0035_0202.htm";
				}
				if(cond == 31)
				{
					if(st.getQuestItemsCount(oriharukon) >= 5 && st.getQuestItemsCount(silver_nugget) >= 500 && st.getQuestItemsCount(thons) >= 150)
					{
						st.set("cookie", 3);
						return "npchtm:elliany_q0035_0303.htm";
					}

					return "npchtm:elliany_q0035_0304.htm";
				}
			}
		}
		else if(st.isStarted() && npcId == wharf_manager_felton)
		{
			if(cond == 11)
			{
				st.set("cookie", 1);
				return "npchtm:wharf_manager_felton_q0035_0101.htm";
			}
			if(cond == 21)
				return "npchtm:wharf_manager_felton_q0035_0202.htm";
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState qs = getRandomPartyMemberWithMemoState(killer, 21);
		if(qs != null && qs.getQuestItemsCount(q_rough_jewel) < 10)
			if(qs.rollAndGiveLimited(q_rough_jewel, 1, 50, 10))
			{
				if(qs.getQuestItemsCount(q_rough_jewel) >= 10)
				{
					qs.playSound(SOUND_MIDDLE);
					qs.setCond(3);
					showQuestMark(qs.getPlayer());
					qs.setMemoState(22);
				}
				else
					qs.playSound(SOUND_ITEMGET);
			}
	}
}