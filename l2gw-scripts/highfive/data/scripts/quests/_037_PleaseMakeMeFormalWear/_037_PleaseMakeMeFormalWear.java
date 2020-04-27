package quests._037_PleaseMakeMeFormalWear;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 15.01.2011 14:36
 */
public class _037_PleaseMakeMeFormalWear extends Quest
{
	// NPCs
	private static final int trader_alexis = 30842;
	private static final int leikar = 31520;
	private static final int jeremy = 31521;
	private static final int mist = 31627;

	// Items
	private static final int wedding_dress = 6408;
	private static final int q_mysterious_cloth = 7076;
	private static final int q_box_of_jewel = 7077;
	private static final int q_workbox = 7078;
	private static final int q_box_of_dress_shoes = 7113;
	private static final int q_box_of_cookies = 7159;
	private static final int q_luxury_wine = 7160;
	private static final int q_seal_of_stock = 7164;

	public _037_PleaseMakeMeFormalWear()
	{
		super(37, "_037_PleaseMakeMeFormalWear", "Please Make Me Formal Wear");

		addStartNpc(trader_alexis);
		addTalkId(trader_alexis, leikar, jeremy, mist);
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

		if(npcId == trader_alexis)
		{
			if(reply == 37 && st.isCreated() && player.getLevel() >= 60)
			{
				st.setMemoState(11);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("trader_alexis_q0037_0104.htm", player);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == leikar)
			{
				if(reply == 1)
				{
					if(st.getInt("cookie") == 1)
					{
						st.giveItems(q_seal_of_stock, 1);
						showPage("leikar_q0037_0201.htm", player);
						st.setMemoState(21);
						st.setCond(2);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
						st.unset("cookie");
					}
					else if(st.getInt("cookie") == 5)
					{
						if(st.getQuestItemsCount(q_box_of_cookies) >= 1)
						{
							st.takeItems(q_box_of_cookies, 1);
							showPage("leikar_q0037_0601.htm", player);
							st.setMemoState(61);
							st.setCond(6);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
							st.unset("cookie");
						}
						else
							showPage("leikar_q0037_0602.htm", player);
					}
					else if(st.getInt("cookie") == 6)
					{
						if(st.haveQuestItems(q_mysterious_cloth) && st.haveQuestItems(q_box_of_jewel) && st.haveQuestItems(q_workbox))
						{
							st.takeItems(q_mysterious_cloth, 1);
							st.takeItems(q_box_of_jewel, 1);
							st.takeItems(q_workbox, 1);
							showPage("leikar_q0037_0701.htm", player);
							st.setMemoState(71);
							st.setCond(7);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
							st.unset("cookie");
						}
						else
							showPage("leikar_q0037_0702.htm", player);
					}
				}
				else if(reply == 3 && st.getInt("cookie") == 7)
				{
					if(st.haveQuestItems(q_box_of_dress_shoes) && st.haveQuestItems(q_seal_of_stock))
					{
						st.takeItems(q_box_of_dress_shoes, -1);
						st.takeItems(q_seal_of_stock, -1);
						st.giveItems(wedding_dress, 1);
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("leikar_q0037_0801.htm", player);
					}
					else
						showPage("leikar_q0037_0802.htm", player);
				}
			}
			else if(npcId == jeremy)
			{
				if(reply == 1)
				{
					if(st.getInt("cookie") == 2)
					{
						st.giveItems(q_luxury_wine, 1);
						showPage("jeremy_q0037_0301.htm", player);
						st.setMemoState(31);
						st.setCond(3);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
						st.unset("cookie");
					}
					else if(st.getInt("cookie") == 4)
					{
						st.giveItems(q_box_of_cookies, 1);
						showPage("jeremy_q0037_0501.htm", player);
						st.setMemoState(51);
						st.setCond(5);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
						st.unset("cookie");
					}
				}
			}
			else if(npcId == mist)
			{
				if(reply == 1 && st.getInt("cookie") == 3)
				{
					if(st.haveQuestItems(q_luxury_wine))
					{
						st.takeItems(q_luxury_wine, 1);
						showPage("mist_q0037_0401.htm", player);
						st.setMemoState(41);
						st.setCond(4);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
						st.unset("cookie");
					}
					else
						showPage("mist_q0037_0402.htm", player);
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

		if(npcId == trader_alexis)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 60)
					return "trader_alexis_q0037_0101.htm";

				st.exitCurrentQuest(true);
				return "npchtm:trader_alexis_q0037_0103.htm";
			}
			if(st.isStarted())
			{
				if(cond == 11)
					return "npchtm:trader_alexis_q0037_0105.htm";
			}
		}
		else if(st.isStarted())
		{
			if(npcId == leikar)
			{
				if(cond == 11)
				{
					st.set("cookie", 1);
					return "npchtm:leikar_q0037_0101.htm";
				}
				if(cond == 21)
					return "npchtm:leikar_q0037_0202.htm";
				if(cond == 51 && st.getQuestItemsCount(q_box_of_cookies) >= 1)
				{
					st.set("cookie", 5);
					return "npchtm:leikar_q0037_0501.htm";
				}
				if(cond == 61)
				{
					if(st.getQuestItemsCount(q_mysterious_cloth) >= 1 && st.getQuestItemsCount(q_box_of_jewel) >= 1 && st.getQuestItemsCount(q_workbox) >= 1)
					{
						st.set("cookie", 6);
						return "npchtm:leikar_q0037_0603.htm";
					}
					return "npchtm:leikar_q0037_0604.htm";
				}
				if(cond == 71)
				{
					if(st.getQuestItemsCount(q_box_of_dress_shoes) >= 1)
					{
						st.set("cookie", 7);
						return "npchtm:leikar_q0037_0703.htm";
					}
					return "npchtm:leikar_q0037_0704.htm";
				}
			}
			else if(npcId == jeremy)
			{
				if(cond == 21 && st.haveQuestItems(q_seal_of_stock))
				{
					st.set("cookie", 2);
					return "npchtm:jeremy_q0037_0201.htm";
				}
				if(cond == 31)
					return "npchtm:jeremy_q0037_0303.htm";
				if(cond == 41)
				{
					st.set("cookie", 4);
					return "npchtm:jeremy_q0037_0401.htm";
				}
				if(cond == 51)
					return "npchtm:jeremy_q0037_0502.htm";
			}
			else if(npcId == mist)
			{
				if(cond == 31 && st.haveQuestItems(q_luxury_wine))
				{
					st.set("cookie", 3);
					return "npchtm:mist_q0037_0301.htm";
				}
				if(cond == 41)
					return "npchtm:mist_q0037_0403.htm";
			}
		}

		return "noquest";
	}
}