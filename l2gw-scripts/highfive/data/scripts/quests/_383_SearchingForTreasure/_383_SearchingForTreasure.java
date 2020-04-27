package quests._383_SearchingForTreasure;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 12.01.12 13:53
 */
public class _383_SearchingForTreasure extends Quest
{
	//NPC
	private static final int trader_espen = 30890;
	private static final int pirates_t_chest = 31148;

	public _383_SearchingForTreasure()
	{
		super(383, "_383_SearchingForTreasure", "Searching For Treasure");

		addStartNpc(trader_espen);
		addTalkId(trader_espen, pirates_t_chest);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == trader_espen)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() < 42)
					return "npchtm:trader_espen_q0383_01.htm";
				if(talker.getLevel() >= 42 && st.getQuestItemsCount(5915) == 0)
					return "npchtm:trader_espen_q0383_02.htm";
				if(talker.getLevel() >= 42 && st.getQuestItemsCount(5915) > 0)
					return "trader_espen_q0383_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:trader_espen_q0383_13.htm";
				if(st.getMemoState() == 2)
					return "npchtm:trader_espen_q0383_14.htm";
			}
		}
		else if(npc.getNpcId() == pirates_t_chest)
		{
			if(st.isStarted() && st.getMemoState() == 2)
				return "npchtm:pirates_t_chest_q0383_01.htm";
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == trader_espen)
		{
			if(reply == 383)
			{
				if(st.isCreated() && talker.getLevel() >= 42 && st.getQuestItemsCount(5915) > 0)
				{
					st.takeItems(5915, -1);
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("trader_espen_q0383_08.htm", talker);
				}
			}
			else if(reply == 1)
			{
				showQuestPage("trader_espen_q0383_04.htm", talker);
			}
			else if(reply == 2 && st.getQuestItemsCount(5915) > 0)
			{
				st.rollAndGive(57, 1000, 100);
				st.takeItems(5915, 1);
				st.exitCurrentQuest(true);
				showQuestPage("trader_espen_q0383_05.htm", talker);
			}
			else if(reply == 3)
			{
				if(st.getQuestItemsCount(5915) > 0)
				{
					showQuestPage("trader_espen_q0383_06.htm", talker);
				}
				else
				{
					showQuestPage("trader_espen_q0383_07.htm", talker);
				}
			}
			else if(reply == 4)
			{
				showQuestPage("trader_espen_q0383_09.htm", talker);
			}
			else if(reply == 5)
			{
				showQuestPage("trader_espen_q0383_10.htm", talker);
			}
			else if(reply == 6)
			{
				showQuestPage("trader_espen_q0383_11.htm", talker);
			}
			else if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.setMemoState(2);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					showQuestPage("trader_espen_q0383_12.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == pirates_t_chest)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(1661) == 0)
				{
					showPage("pirates_t_chest_q0383_02.htm", talker);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(1661) >= 1)
				{
					st.takeItems(1661, 1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("pirates_t_chest_q0383_03.htm", talker);
					int i1 = 0;
					int i0 = Rnd.get(100);
					if(i0 < 5)
					{
						st.giveItems(2450, 1);
					}
					else if(i0 < 6)
					{
						st.giveItems(2451, 1);
					}
					else if(i0 < 18)
					{
						st.giveItems(956, 1);
					}
					else if(i0 < 28)
					{
						st.giveItems(952, 1);
					}
					else
					{
						i1 = (i1 + 500);
					}

					i0 = Rnd.get(1000);
					if(i0 < 25)
					{
						st.giveItems(4481, 1);
					}
					else if(i0 < 50)
					{
						st.giveItems(4482, 1);
					}
					else if(i0 < 75)
					{
						st.giveItems(4483, 1);
					}
					else if(i0 < 100)
					{
						st.giveItems(4484, 1);
					}
					else if(i0 < 125)
					{
						st.giveItems(4485, 1);
					}
					else if(i0 < 150)
					{
						st.giveItems(4486, 1);
					}
					else if(i0 < 175)
					{
						st.giveItems(4487, 1);
					}
					else if(i0 < 200)
					{
						st.giveItems(4488, 1);
					}
					else if(i0 < 225)
					{
						st.giveItems(4489, 1);
					}
					else if(i0 < 250)
					{
						st.giveItems(4490, 1);
					}
					else if(i0 < 275)
					{
						st.giveItems(4491, 1);
					}
					else if(i0 < 300)
					{
						st.giveItems(4492, 1);
					}
					else
					{
						i1 = (i1 + 300);
					}

					i0 = Rnd.get(100);
					if(i0 < 4)
					{
						st.giveItems(1337, 1);
					}
					else if(i0 < 8)
					{
						st.giveItems(1338, 2);
					}
					else if(i0 < 12)
					{
						st.giveItems(1339, 2);
					}
					else if(i0 < 16)
					{
						st.giveItems(3447, 2);
					}
					else if(i0 < 20)
					{
						st.giveItems(3450, 1);
					}
					else if(i0 < 25)
					{
						st.giveItems(3453, 1);
					}
					else if(i0 < 27)
					{
						st.giveItems(3456, 1);
					}
					else
					{
						i1 = (i1 + 500);
					}

					i0 = Rnd.get(100);
					if(i0 < 20)
					{
						st.giveItems(4408, 1);
					}
					else if(i0 < 40)
					{
						st.giveItems(4409, 1);
					}
					else if(i0 < 60)
					{
						st.giveItems(4418, 1);
					}
					else if(i0 < 80)
					{
						st.giveItems(4419, 1);
					}
					else
					{
						i1 = (i1 + 500);
					}

					st.rollAndGive(57, i1, 100);
				}
			}
		}
	}
}