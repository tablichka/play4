package quests._631_DeliciousTopChoiceMeat;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 23.02.12 23:37
 */
public class _631_DeliciousTopChoiceMeat extends Quest
{
	// NPC
	private static final int beast_herder_tunatun = 31537;

	public _631_DeliciousTopChoiceMeat()
	{
		super(631, "_631_DeliciousTopChoiceMeat", "Delicious Top Choice Meat");
		addStartNpc(beast_herder_tunatun);
		addTalkId(beast_herder_tunatun);

		addQuestItem(15534);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == beast_herder_tunatun)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 82)
					return "beast_herder_tunatun_q0631_101n.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 2 && st.getQuestItemsCount(15534) >= 120)
					return "npchtm:beast_herder_tunatun_q0631_105n.htm";
				if(st.getQuestItemsCount(15534) < 120)
					return "npchtm:beast_herder_tunatun_q0631_106n.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == beast_herder_tunatun)
		{
			if(reply == 631)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showPage("beast_herder_tunatun_q0631_104n.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
				else
				{
					showQuestPage("beast_herder_tunatun_q0631_103n.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 2 && st.getQuestItemsCount(15534) >= 120)
				{
					int i0 = Rnd.get(10);
					if(i0 == 0)
					{
						int i1 = Rnd.get(9);
						switch(i1)
						{
							case 0:
								st.rateAndGive(10373, 1);
								break;
							case 1:
								st.rateAndGive(10374, 1);
								break;
							case 2:
								st.rateAndGive(10375, 1);
								break;
							case 3:
								st.rateAndGive(10376, 1);
								break;
							case 4:
								st.rateAndGive(10377, 1);
								break;
							case 5:
								st.rateAndGive(10378, 1);
								break;
							case 6:
								st.rateAndGive(10379, 1);
								break;
							case 7:
								st.rateAndGive(10380, 1);
								break;
							case 8:
								st.rateAndGive(10381, 1);
								break;
						}
					}
					else if(i0 == 1)
					{
						int i1 = Rnd.get(9);
						switch(i1)
						{
							case 0:
								st.rateAndGive(10397, 1);
								break;
							case 1:
								st.rateAndGive(10398, 1);
								break;
							case 2:
								st.rateAndGive(10399, 1);
								break;
							case 3:
								st.rateAndGive(10400, 1);
								break;
							case 4:
								st.rateAndGive(10401, 1);
								break;
							case 5:
								st.rateAndGive(10402, 1);
								break;
							case 6:
								st.rateAndGive(10403, 1);
								break;
							case 7:
								st.rateAndGive(10404, 1);
								break;
							case 8:
								st.rateAndGive(10405, 1);
								break;
						}
					}
					else if(i0 == 2)
					{
						int i1 = Rnd.get(9);
						switch(i1)
						{
							case 0:
								st.rateAndGive(10397, 2);
								break;
							case 1:
								st.rateAndGive(10398, 2);
								break;
							case 2:
								st.rateAndGive(10399, 2);
								break;
							case 3:
								st.rateAndGive(10400, 2);
								break;
							case 4:
								st.rateAndGive(10401, 2);
								break;
							case 5:
								st.rateAndGive(10402, 2);
								break;
							case 6:
								st.rateAndGive(10403, 2);
								break;
							case 7:
								st.rateAndGive(10404, 2);
								break;
							case 8:
								st.rateAndGive(10405, 2);
								break;
						}
					}
					else if(i0 == 3)
					{
						int i1 = Rnd.get(9);
						switch(i1)
						{
							case 0:
								st.rateAndGive(10397, 3);
								break;
							case 1:
								st.rateAndGive(10398, 3);
								break;
							case 2:
								st.rateAndGive(10399, 3);
								break;
							case 3:
								st.rateAndGive(10400, 3);
								break;
							case 4:
								st.rateAndGive(10401, 3);
								break;
							case 5:
								st.rateAndGive(10402, 3);
								break;
							case 6:
								st.rateAndGive(10403, 3);
								break;
							case 7:
								st.rateAndGive(10404, 3);
								break;
							case 8:
								st.rateAndGive(10405, 3);
								break;
						}
					}
					else if(i0 == 4)
					{
						int i1 = Rnd.get(9);
						switch(i1)
						{
							case 0:
								st.rateAndGive(10397, (Rnd.get(5) + 2));
								break;
							case 1:
								st.rateAndGive(10398, (Rnd.get(5) + 2));
								break;
							case 2:
								st.rateAndGive(10399, (Rnd.get(5) + 2));
								break;
							case 3:
								st.rateAndGive(10400, (Rnd.get(5) + 2));
								break;
							case 4:
								st.rateAndGive(10401, (Rnd.get(5) + 2));
								break;
							case 5:
								st.rateAndGive(10402, (Rnd.get(5) + 2));
								break;
							case 6:
								st.rateAndGive(10403, (Rnd.get(5) + 2));
								break;
							case 7:
								st.rateAndGive(10404, (Rnd.get(5) + 2));
								break;
							case 8:
								st.rateAndGive(10405, (Rnd.get(5) + 2));
								break;
						}
					}
					else if(i0 == 5)
					{
						int i1 = Rnd.get(9);
						switch(i1)
						{
							case 0:
								st.rateAndGive(10397, (Rnd.get(7) + 2));
								break;
							case 1:
								st.rateAndGive(10398, (Rnd.get(7) + 2));
								break;
							case 2:
								st.rateAndGive(10399, (Rnd.get(7) + 2));
								break;
							case 3:
								st.rateAndGive(10400, (Rnd.get(7) + 2));
								break;
							case 4:
								st.rateAndGive(10401, (Rnd.get(7) + 2));
								break;
							case 5:
								st.rateAndGive(10402, (Rnd.get(7) + 2));
								break;
							case 6:
								st.rateAndGive(10403, (Rnd.get(7) + 2));
								break;
							case 7:
								st.rateAndGive(10404, (Rnd.get(7) + 2));
								break;
							case 8:
								st.rateAndGive(10405, (Rnd.get(7) + 2));
								break;
						}
					}
					else if(i0 == 6)
					{
						st.rateAndGive(15482, 1);
					}
					else if(i0 == 7)
					{
						st.rateAndGive(15482, 2);
					}
					else if(i0 == 8)
					{
						st.rateAndGive(15483, 1);
					}
					else if(i0 == 9)
					{
						st.rateAndGive(15483, 2);
					}

					st.takeItems(15534, -1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("beast_herder_tunatun_q0631_202n.htm", talker);
				}
			}
		}
	}
}