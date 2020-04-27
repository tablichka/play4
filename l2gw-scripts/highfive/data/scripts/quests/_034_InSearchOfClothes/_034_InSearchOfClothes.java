package quests._034_InSearchOfClothes;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 15.01.2011 12:59
 */
public class _034_InSearchOfClothes extends Quest
{
	// NPCs
	private static final int radia = 30088;
	private static final int rapin = 30165;
	private static final int trader_varanket = 30294;

	// MOBs
	private static final int[] MOBs = {20560, 20561};

	// Items
	private static final int q_base_of_cobweb = 7528;
	private static final int suede = 1866;
	private static final int thread = 1868;
	private static final int q_mysterious_cloth = 7076;
	private static final int q_skein_of_yarn = 7161;

	public _034_InSearchOfClothes()
	{
		super(34, "_034_InSearchOfClothes", "In Search Of Clothes");

		addStartNpc(radia);
		addTalkId(radia, rapin, trader_varanket);
		addKillId(MOBs);
		addQuestItem(q_base_of_cobweb);
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

		if(npcId == radia)
		{
			if(reply == 34 && st.isCreated() && player.getLevel() >= 60)
			{
				st.setMemoState(11);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("radia_q0034_0104.htm", player);
			}
			else if(st.isStarted())
			{
				if(reply == 1 && st.getInt("cookie") > 0)
				{
					showPage("radia_q0034_0301.htm", player);
					st.setMemoState(31);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
				else if(reply == 3 && st.getInt("cookie") > 0)
				{
					if(st.getQuestItemsCount(q_skein_of_yarn) >= 1 && st.getQuestItemsCount(thread) >= 5000 && st.getQuestItemsCount(suede) >= 3000)
					{
						st.takeItems(q_skein_of_yarn, 1);
						st.takeItems(thread, 5000);
						st.takeItems(suede, 3000);
						st.giveItems(q_mysterious_cloth, 1);
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("radia_q0034_0601.htm", player);
					}
					else
						showPage("radia_q0034_0602.htm", player);
				}
			}
		}
		else if(st.isStarted())
		{
			if(npcId == rapin)
			{
				if(reply == 1)
				{
					if(st.getInt("cookie") == 3)
					{
						showPage("rapin_q0034_0401.htm", player);
						st.setMemoState(41);
						st.setCond(4);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
					}
					else if(st.getInt("cookie") == 4)
					{
						if(st.getQuestItemsCount(q_base_of_cobweb) >= 10)
						{
							st.takeItems(q_base_of_cobweb, 10);
							st.giveItems(q_skein_of_yarn, 1);
							showPage("rapin_q0034_0501.htm", player);
							st.setMemoState(51);
							st.setCond(6);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
						}
						else
							showPage("rapin_q0034_0502.htm", player);
					}
				}
			}
			else if(npcId == trader_varanket)
			{
				if(reply == 1 && st.getInt("cookie") == 1)
				{
					showPage("trader_varanket_q0034_0201.htm", player);
					st.setMemoState(21);
					st.setCond(2);
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

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == radia)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 60)
					return "radia_q0034_0101.htm";

				st.exitCurrentQuest(true);
				return "npchtm:radia_q0034_0103.htm";
			}
			if(st.isStarted())
			{
				if(cond == 11)
					return "npchtm:radia_q0034_0105.htm";
				if(cond == 21)
				{
					st.set("cookie", 2);
					return "npchtm:radia_q0034_0201.htm";
				}
				if(cond == 31)
					return "npchtm:radia_q0034_0302.htm";
				if(cond == 51)
				{
					st.set("cookie", 5);
					if(st.getQuestItemsCount(suede) >= 3000 && st.getQuestItemsCount(thread) >= 5000)
						return "npchtm:radia_q0034_0501.htm";

					return "npchtm:radia_q0034_0502.htm";
				}

			}
		}
		else if(st.isStarted())
		{
			if(npcId == rapin)
			{
				if(cond == 31)
				{
					st.set("cookie", 3);
					return "npchtm:rapin_q0034_0301.htm";
				}
				if(cond <= 42 && cond >= 41)
				{
					if(cond == 42 && st.getQuestItemsCount(q_base_of_cobweb) >= 10)
					{
						st.set("cookie", 4);
						return "npchtm:rapin_q0034_0402.htm";
					}

					return "npchtm:rapin_q0034_0403.htm";
				}
				if(cond == 51)
					return "npchtm:rapin_q0034_0503.htm";
			}
			else if(npcId == trader_varanket)
			{
				if(cond == 11)
				{
					st.set("cookie", 1);
					return "npchtm:trader_varanket_q0034_0101.htm";
				}
				if(cond == 21)
					return "npchtm:trader_varanket_q0034_0202.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState qs = getRandomPartyMemberWithMemoState(killer, 41);

		if(qs != null && qs.getQuestItemsCount(q_base_of_cobweb) < 10)
			if(qs.rollAndGiveLimited(q_base_of_cobweb, 1, 50, 10))
			{
				if(qs.getQuestItemsCount(q_base_of_cobweb) >= 10)
				{
					qs.playSound(SOUND_MIDDLE);
					qs.setCond(5);
					showQuestMark(qs.getPlayer());
					qs.setMemoState(42);
				}
				else
					qs.playSound(SOUND_ITEMGET);
			}
	}
}