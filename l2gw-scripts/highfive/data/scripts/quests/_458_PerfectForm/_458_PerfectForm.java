package quests._458_PerfectForm;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 25.09.11 22:19
 */
public class _458_PerfectForm extends Quest
{
	// NPC
	private static final int keleia = 32768;

	public _458_PerfectForm()
	{
		super(458, "_458_PerfectForm", "Perfect Form");
		addStartNpc(keleia);
		addTalkId(keleia);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == keleia)
		{
			if(st.isCreated() && talker.getLevel() >= 82)
				return "keleia_q0458_01.htm";
			if(st.isCompleted() && talker.getLevel() >= 82)
				return "keleia_q0458_02.htm";
			if(st.isCreated() && talker.getLevel() < 82)
				return "keleia_q0458_03.htm";

			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getInt("k18879") < 1 && st.getInt("k18886") < 1 && st.getInt("k18893") < 1 && st.getInt("k18900") < 1)
					return "npchtm:keleia_q0458_13.htm";
				if(st.getMemoState() == 1 && (st.getInt("k18879") >= 1 || st.getInt("k18886") >= 1 || st.getInt("k18893") >= 1 || st.getInt("k18900") >= 1))
					return "npchtm:keleia_q0458_14.htm";
				if(st.getMemoState() == 2)
					return "npchtm:keleia_q0458_15.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == keleia)
		{
			if(reply == 458)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					st.setCond(1);
					showQuestPage("keleia_q0458_12.htm", talker);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showPage("keleia_q0458_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showPage("keleia_q0458_05.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showPage("keleia_q0458_06.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showPage("keleia_q0458_07.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showQuestPage("keleia_q0458_08.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showQuestPage("keleia_q0458_09.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showQuestPage("keleia_q0458_10.htm", talker);
				}
			}
			else if(reply == 8)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					showQuestPage("keleia_q0458_11.htm", talker);
				}
			}
			else if(reply == 10)
			{
				if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_1") >= 35)
				{
					showHtmlFile(talker, "keleia_q0458_16a.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_1"))}, false);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_1") < 35 && st.getInt("ex_1") >= 10)
				{
					showHtmlFile(talker, "keleia_q0458_16b.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_1"))}, false);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_1") < 10)
				{
					showHtmlFile(talker, "keleia_q0458_16c.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_1"))}, false);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_2") >= 30)
				{
					showHtmlFile(talker, "keleia_q0458_17a.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_2"))}, false);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_2") < 30 && st.getInt("ex_2") >= 5)
				{
					showHtmlFile(talker, "keleia_q0458_17b.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_2"))}, false);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_2") < 5)
				{
					showHtmlFile(talker, "keleia_q0458_17c.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_2"))}, false);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_3") >= 20)
				{
					showHtmlFile(talker, "keleia_q0458_18a.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_3"))}, false);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_3") < 20 && st.getInt("ex_3") >= 7)
				{
					showHtmlFile(talker, "keleia_q0458_18b.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_3"))}, false);
				}
				else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_3") < 7)
				{
					showHtmlFile(talker, "keleia_q0458_18c.htm", new String[]{"<?number?>"}, new String[]{String.valueOf(st.getInt("ex_3"))}, false);
				}
			}
			else if(reply == 13)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_3") >= 20)
					{
						int i0 = Rnd.get(9);
						switch(i0)
						{
							case 0:
								st.giveItems(10373, 1);
								break;
							case 1:
								st.giveItems(10374, 1);
								break;
							case 2:
								st.giveItems(10375, 1);
								break;
							case 3:
								st.giveItems(10376, 1);
								break;
							case 4:
								st.giveItems(10377, 1);
								break;
							case 5:
								st.giveItems(10378, 1);
								break;
							case 6:
								st.giveItems(10379, 1);
								break;
							case 7:
								st.giveItems(10380, 1);
								break;
							case 8:
								st.giveItems(10381, 1);
								break;
						}
					}
					else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_3") < 20 && st.getInt("ex_3") >= 7)
					{
						int i0 = Rnd.get(9);
						switch(i0)
						{
							case 0:
								st.giveItems(10397, 5);
								break;
							case 1:
								st.giveItems(10398, 5);
								break;
							case 2:
								st.giveItems(10399, 5);
								break;
							case 3:
								st.giveItems(10400, 5);
								break;
							case 4:
								st.giveItems(10401, 5);
								break;
							case 5:
								st.giveItems(10402, 5);
								break;
							case 6:
								st.giveItems(10403, 5);
								break;
							case 7:
								st.giveItems(10404, 5);
								break;
							case 8:
								st.giveItems(10405, 5);
								break;
						}
					}
					else if(st.isStarted() && st.getMemoState() == 2 && st.getInt("ex_3") < 7)
					{
						int i0 = Rnd.get(9);
						switch(i0)
						{
							case 0:
								st.giveItems(10397, 2);
								break;
							case 1:
								st.giveItems(10398, 2);
								break;
							case 2:
								st.giveItems(10399, 2);
								break;
							case 3:
								st.giveItems(10400, 2);
								break;
							case 4:
								st.giveItems(10401, 2);
								break;
							case 5:
								st.giveItems(10402, 2);
								break;
							case 6:
								st.giveItems(10403, 2);
								break;
							case 7:
								st.giveItems(10404, 2);
								break;
							case 8:
								st.giveItems(10405, 2);
								break;
						}
						st.giveItems(15482, 10);
						st.giveItems(15483, 10);
					}

					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false, true);
					showPage("keleia_q0458_19.htm", talker);
				}
			}
		}
	}
}
