package quests._289_NoMoreSoupForYou;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 05.02.11 19:10
 */
public class _289_NoMoreSoupForYou extends Quest
{
	// NPCs
	private static final int stan = 30200;

	// Items
	private static final int q_worst_taste_fruit = 15507;
	private static final int q_full_barrel_of_xel = 15712;
	private static final int q_empty_barrel_of_xel = 15713;

	public _289_NoMoreSoupForYou()
	{
		super(289, "_289_NoMoreSoupForYou", "No More Soup For You");

		addStartNpc(stan);
		addTalkId(stan);
		addKillId(18908, 22779, 22786, 22787, 22788);
		addQuestItem(q_worst_taste_fruit, q_full_barrel_of_xel, q_empty_barrel_of_xel);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == stan)
		{
			if(st.isCreated())
			{
				if(reply == 289 && player.getLevel() >= 82 && player.isQuestComplete(252))
				{
					st.giveItems(q_worst_taste_fruit, 500);
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("stan_q0289_04.htm", player);
				}
				else if(reply == 1 && player.getLevel() >= 82 && player.isQuestComplete(252))
					showQuestPage("stan_q0289_03.htm", player);
			}
			else if(st.isStarted() && st.getMemoState() == 1)
			{
				if(reply == 7)
				{
					st.giveItems(q_worst_taste_fruit, 500);
					showPage("stan_q0289_06.htm", player);
				}
				else if(reply == 2)
				{
					if(st.getQuestItemsCount(q_full_barrel_of_xel) < 500)
						showPage("stan_q0289_08.htm", player);
					else if(st.getQuestItemsCount(q_full_barrel_of_xel) >= 500)
					{
						st.takeItems(q_full_barrel_of_xel, 500);
						int i0 = Rnd.get(5);
						switch(i0)
						{
							case 0:
							{
								st.giveItems(10377, 1);
								break;
							}
							case 1:
							{
								st.giveItems(10401, 3);
								break;
							}
							case 2:
							{
								st.giveItems(10401, 4);
								break;
							}
							case 3:
							{
								st.giveItems(10401, 5);
								break;
							}
							case 4:
							{
								st.giveItems(10401, 6);
								break;
							}
						}

						st.playSound(SOUND_MIDDLE);
						showPage("stan_q0289_09.htm", player);
					}
				}
				else if(reply == 3)
				{
					if(st.getQuestItemsCount(q_full_barrel_of_xel) < 100)
						showPage("stan_q0289_10.htm", player);
					else if(st.getQuestItemsCount(q_full_barrel_of_xel) >= 100)
					{
						st.takeItems(q_full_barrel_of_xel, 100);
						int i0 = Rnd.get(10);
						switch(i0)
						{
							case 0:
							{
								int i1 = Rnd.get(10);
								if(i1 < 4)
									st.giveItems(15775, 1);
								else if(i1 >= 4 && i1 < 7)
									st.giveItems(15778, 1);
								else
									st.giveItems(15781, 1);
								break;
							}
							case 1:
							{
								st.giveItems(15784, 1);
								break;
							}
							case 2:
							{
								st.giveItems(15787, 1);
								break;
							}
							case 3:
							{
								st.giveItems(15791, 1);
								break;
							}
							case 4:
							{
								int i1 = Rnd.get(10);
								if(i1 < 4)
									st.giveItems(15812, 1);
								else if(i1 >= 4 && i1 < 8)
									st.giveItems(15813, 1);
								else
									st.giveItems(15814, 1);
								break;
							}
							case 5:
							{
								int i1 = Rnd.get(10);
								if(i1 < 4)
									st.giveItems(15645, 3);
								else if(i1 >= 4 && i1 < 7)
									st.giveItems(15648, 3);
								else
									st.giveItems(15651, 3);
								break;
							}
							case 6:
							{
								st.giveItems(15654, 3);
								break;
							}
							case 7:
							{
								st.giveItems(15657, 3);
								break;
							}
							case 8:
							{
								st.giveItems(15693, 3);
								break;
							}
							case 9:
							{
								int i1 = Rnd.get(10);
								if(i1 < 4)
									st.giveItems(15772, 3);
								else if(i1 >= 4 && i1 < 8)
									st.giveItems(15773, 3);
								else
									st.giveItems(15774, 3);
								break;
							}
						}

						st.playSound(SOUND_MIDDLE);
						showPage("stan_q0289_11.htm", player);
					}
				}
				else if(reply == 4)
					showPage("stan_q0289_12.htm", player);
				if(reply == 5)
				{
					if(st.getQuestItemsCount(q_full_barrel_of_xel) >= 1 || st.getQuestItemsCount(q_empty_barrel_of_xel) >= 1)
						showPage("stan_q0289_13.htm", player);
					else
					{
						st.takeItems(q_worst_taste_fruit, -1);
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("stan_q0289_14.htm", player);
					}
				}
				else if(reply == 6)
				{
					st.takeItems(q_full_barrel_of_xel, -1);
					st.takeItems(q_empty_barrel_of_xel, -1);
					st.takeItems(q_worst_taste_fruit, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("stan_q0289_15.htm", player);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == stan)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82 && st.getPlayer().isQuestComplete(252))
					return "stan_q0289_01.htm";

				st.exitCurrentQuest(true);
				return "stan_q0289_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(q_full_barrel_of_xel) + st.getQuestItemsCount(q_empty_barrel_of_xel) * 2 < 100)
						return "npchtm:stan_q0289_05.htm";

					if(st.haveQuestItems(q_empty_barrel_of_xel))
					{
						long c = st.getQuestItemsCount(q_empty_barrel_of_xel) / 2;
						st.takeItems(q_empty_barrel_of_xel, c * 2);
						st.giveItems(q_full_barrel_of_xel, c);
					}

					return "npchtm:stan_q0289_07.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState qs)
	{
		if(qs != null && qs.getMemoState() == 1)
		{
			if(npc.getNpcId() == 18908)
			{
				if(qs.rollAndGive(q_full_barrel_of_xel, 1, 85.3))
					qs.playSound(SOUND_ITEMGET);
			}
			else if(npc.getNpcId() == 22779)
			{
				if(qs.rollAndGive(q_empty_barrel_of_xel, 1, 59.9))
					qs.playSound(SOUND_ITEMGET);
			}
			else if(npc.i_quest0 == qs.getPlayer().getObjectId() && npc.i_ai3 == 1)
			{
				if(qs.rollAndGive(q_full_barrel_of_xel, 1, 69.9))
					qs.playSound(SOUND_ITEMGET);
			}
			else if(qs.rollAndGive(q_empty_barrel_of_xel, 1, 69.9))
				qs.playSound(SOUND_ITEMGET);
		}
	}
}
