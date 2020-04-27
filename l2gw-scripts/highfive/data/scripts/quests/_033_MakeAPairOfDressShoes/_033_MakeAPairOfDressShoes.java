package quests._033_MakeAPairOfDressShoes;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 15.01.2011 16:22
 */
public class _033_MakeAPairOfDressShoes extends Quest
{
	// NPCs
	private static final int trader_woodley = 30838;
	private static final int iz = 30164;
	private static final int leikar = 31520;

	// Items
	private static final int leather = 1882;
	private static final int thread = 1868;
	private static final int q_box_of_dress_shoes = 7113;

	public _033_MakeAPairOfDressShoes()
	{
		super(33, "_033_MakeAPairOfDressShoes", "Make A Pair Of Dress Shoes");

		addStartNpc(trader_woodley);
		addTalkId(trader_woodley, iz, leikar);
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

		if(npcId == trader_woodley)
		{
			if(reply == 33 && st.isCreated() && player.getLevel() >= 60)
			{
				st.setMemoState(11);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("trader_woodley_q0033_0104.htm", player);
			}
			else if(st.isStarted())
			{
				if(reply == 1)
				{
					if(st.getInt("cookie") == 2)
					{
						showPage("trader_woodley_q0033_0301.htm", player);
						st.setMemoState(31);
						st.setCond(3);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
						st.unset("cookie");
					}
					else if(st.getInt("cookie") == 3)
					{
						if(st.getQuestItemsCount(leather) >= 200 && st.getQuestItemsCount(thread) >= 600 && st.getQuestItemsCount(57) >= 200000)
						{
							st.takeItems(leather, 200);
							st.takeItems(thread, 600);
							st.takeItems(57, 200000);
							showPage("trader_woodley_q0033_0401.htm", player);
							st.setMemoState(41);
							st.setCond(4);
							showQuestMark(player);
							st.playSound(SOUND_MIDDLE);
							st.unset("cookie");
						}
						else
							showPage("trader_woodley_q0033_0402.htm", player);
					}
				}
				else if(reply == 3 && st.getInt("cookie") == 5)
				{
					st.giveItems(q_box_of_dress_shoes, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("trader_woodley_q0033_0601.htm", player);
				}
			}
		}
		else if(st.isStarted())
		{
			if(npcId == iz)
			{
				if(reply == 1 && st.getInt("cookie") == 4)
				{
					if(st.getQuestItemsCount(57) >= 300000)
					{
						st.takeItems(57, 300000);
						showPage("iz_q0033_0501.htm", player);
						st.setMemoState(51);
						st.setCond(5);
						showQuestMark(player);
						st.playSound(SOUND_MIDDLE);
						st.unset("cookie");
					}
					else
						showPage("iz_q0033_0502.htm", player);
				}
			}
			else if(npcId == leikar)
			{
				if(reply == 1 && st.getInt("cookie") == 1)
				{
					showPage("leikar_q0033_0201.htm", player);
					st.setMemoState(21);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
					st.unset("cookie");
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

		if(npcId == trader_woodley)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 60)
					return "trader_woodley_q0033_0101.htm";

				st.exitCurrentQuest(true);
				return "npchtm:trader_woodley_q0033_0103.htm";
			}
			if(st.isStarted())
			{
				if(cond == 11)
					return "npchtm:trader_woodley_q0033_0105.htm";
				if(cond == 21)
				{
					st.set("cookie", 2);
					return "npchtm:trader_woodley_q0033_0201.htm";
				}
				if(cond == 31)
				{
					if(st.getQuestItemsCount(leather) >= 200 && st.getQuestItemsCount(thread) >= 600 && st.getQuestItemsCount(57) >= 500000)
					{
						st.set("cookie", 3);
						return "npchtm:trader_woodley_q0033_0302.htm";
					}
					return "npchtm:trader_woodley_q0033_0303.htm";
				}
				if(cond == 41)
					return "npchtm:trader_woodley_q0033_0403.htm";
				if(cond == 51)
				{
					st.set("cookie", 5);
					return "npchtm:trader_woodley_q0033_0501.htm";
				}
			}
		}
		else if(st.isStarted())
		{
			if(npcId == iz)
			{
				if(cond == 41)
				{
					st.set("cookie", 4);
					return "npchtm:iz_q0033_0401.htm";
				}
				if(cond == 51)
					return "npchtm:iz_q0033_0503.htm";
			}
			else if(npcId == leikar)
			{
				if(cond == 11)
				{
					st.set("cookie", 1);
					return "npchtm:leikar_q0033_0101.htm";
				}
				if(cond == 21)
					return "npchtm:leikar_q0033_0202.htm";
			}
		}

		return "noquest";
	}
}