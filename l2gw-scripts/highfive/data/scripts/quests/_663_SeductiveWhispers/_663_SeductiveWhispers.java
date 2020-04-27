package quests._663_SeductiveWhispers;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Strings;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 02.02.12 18:01
 */
public class _663_SeductiveWhispers extends Quest
{
	// NPC
	private static final int blacksmith_wilbert = 30846;

	// Mobs
	private static final HashMap<Integer, Double> mobs = new HashMap<>(27);
	static
	{
		mobs.put(20958, 42.5);
		mobs.put(20960, 37.2);
		mobs.put(20961, 54.7);
		mobs.put(20963, 49.8);
		mobs.put(20962, 52.2);
		mobs.put(20998, 37.7);
		mobs.put(20959, 68.2);
		mobs.put(21008, 69.2);
		mobs.put(21007, 54.0);
		mobs.put(20674, 80.7);
		mobs.put(21002, 47.2);
		mobs.put(21006, 50.2);
		mobs.put(21009, 74.0);
		mobs.put(21010, 59.5);
		mobs.put(20955, 53.7);
		mobs.put(20954, 46.0);
		mobs.put(20996, 38.5);
		mobs.put(20957, 56.5);
		mobs.put(20956, 54.0);
		mobs.put(20678, 37.2);
		mobs.put(20999, 45.0);
		mobs.put(20997, 34.2);
		mobs.put(21000, 39.5);
		mobs.put(20976, 82.5);
		mobs.put(20974, 100.0);
		mobs.put(20975, 97.5);
		mobs.put(21001, 53.5);
	}

	public _663_SeductiveWhispers()
	{
		super(663, "_663_SeductiveWhispers", "Seductive Whispers");
		addStartNpc(blacksmith_wilbert);
		addTalkId(blacksmith_wilbert);

		addKillId(mobs.keySet());
		addQuestItem(8766);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == blacksmith_wilbert)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 50)
					return "blacksmith_wilbert_q0663_01.htm";

				return "npchtm:blacksmith_wilbert_q0663_02.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() < 4 && st.getMemoState() >= 1 && st.getQuestItemsCount(8766) == 0)
					return "npchtm:blacksmith_wilbert_q0663_04.htm";
				if(st.getMemoState() < 4 && st.getMemoState() >= 1 && st.getQuestItemsCount(8766) > 0)
					return "npchtm:blacksmith_wilbert_q0663_05.htm";
				if(st.getMemoState() % 10 == 4 && st.getMemoState() / 1000 == 0)
					return "npchtm:blacksmith_wilbert_q0663_05a.htm";
				if(st.getMemoState() % 10 == 5 && st.getMemoState() / 1000 == 0)
					return "npchtm:blacksmith_wilbert_q0663_11.htm";
				if(st.getMemoState() % 10 == 6 && st.getMemoState() / 1000 == 0)
					return "npchtm:blacksmith_wilbert_q0663_15.htm";
				if(st.getMemoState() % 10 == 7 && st.getMemoState() / 1000 == 0)
				{
					int i0 = st.getMemoState() % 100;
					if(i0 / 10 >= 7)
					{
						st.setMemoState(1);
						st.rateAndGive(57, 2384000);
						st.giveItems(729, 1);
						st.giveItems(730, 2);
						return "npchtm:blacksmith_wilbert_q0663_17.htm";
					}
					else
					{
						int i5 = st.getMemoState() / 10 + 1;
						showHtmlFile(talker, "blacksmith_wilbert_q0663_16.htm", new String[]{"<?wincount?>"}, new String[]{String.valueOf(i5)}, true);
						return null;
					}
				}
				if(st.getMemoState() == 1005)
					return "npchtm:blacksmith_wilbert_q0663_23.htm";
				if(st.getMemoState() == 1006)
					return "npchtm:blacksmith_wilbert_q0663_26.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == blacksmith_wilbert)
		{
			int i0 = 0;
			int i1 = 0;
			int i2 = 0;
			int i3 = 0;
			int i4 = 0;
			int i5 = 0;
			int param1 = 0;
			int param2 = 0;
			int param3 = 0;
			if(reply == 663)
			{
				if(st.isCreated() && talker.getLevel() >= 50)
				{
					showQuestPage("blacksmith_wilbert_q0663_03.htm", talker);
					st.setCond(1);
					st.setMemoState(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
				}
			}
			else if(reply == 1 && st.isCreated() && talker.getLevel() >= 50)
			{
				showQuestPage("blacksmith_wilbert_q0663_01a.htm", talker);
			}
			else if(reply == 4 && st.isStarted() && st.getMemoState() % 10 <= 4)
			{
				if(st.isStarted() && st.getMemoState() / 10 < 1)
				{
					if(st.getQuestItemsCount(8766) >= 50)
					{
						st.takeItems(8766, 50);
						st.setMemoState(5);
						st.setMemoStateEx(1, 0);
						showPage("blacksmith_wilbert_q0663_09.htm", talker);
					}
					else
					{
						showPage("blacksmith_wilbert_q0663_10.htm", talker);
					}
				}
				else
				{
					i0 = st.getMemoState() / 10;
					i1 = (i0 * 10 + 5);
					st.setMemoState(i1);
					st.setMemoStateEx(1, 0);
					showPage("blacksmith_wilbert_q0663_09a.htm", talker);
				}
			}
			else if(reply == 5 && st.isStarted() && st.getMemoState() % 10 == 5 && st.getMemoState() / 1000 == 0)
			{
				i0 = st.getMemoStateEx(1);
				if(i0 < 0)
				{
					i0 = 0;
				}
				i1 = i0 % 10;
				i2 = (i0 - i1) / 10;
				param1 = (Rnd.get(2) + 1);
				param2 = (Rnd.get(5) + 1);
				i5 = st.getMemoState() / 10;
				param3 = (param1 * 10 + param2);
				if(param1 == i2)
				{
					i3 = param2 + i1;
					if(i3 % 5 == 0 && i3 != 10)
					{
						if(st.getMemoState() % 100 / 10 >= 7)
						{
							showHtmlFile(talker, "blacksmith_wilbert_q0663_14.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
									new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
							st.rateAndGive(57, 2384000);
							st.giveItems(729, 1);
							st.giveItems(730, 2);
							st.setMemoState(4);
						}
						else
						{
							showHtmlFile(talker, "blacksmith_wilbert_q0663_13.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>", "<?wincount?>"},
									new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName(), String.valueOf(i5 + 1)}, false);
							i4 = st.getMemoState() / 10 * 10 + 7;
							st.setMemoState(i4);
						}
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_12.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoStateEx(1, param3);
						i4 = st.getMemoState() / 10 * 10 + 6;
						st.setMemoState(i4);
					}
				}
				else if(param1 != i2)
				{
					if(param2 == 5 || i1 == 5)
					{
						if(st.getMemoState() % 100 / 10 >= 7)
						{
							showHtmlFile(talker, "blacksmith_wilbert_q0663_14.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
									new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
							st.rateAndGive(57, 2384000);
							st.giveItems(729, 1);
							st.giveItems(730, 2);
							st.setMemoState(4);
						}
						else
						{
							showHtmlFile(talker, "blacksmith_wilbert_q0663_13.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>", "<?wincount?>"},
									new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName(), String.valueOf(i5 + 1)}, false);
							i4 = st.getMemoState() / 10 * 10 + 7;
							st.setMemoState(i4);
						}
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_12.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						param3 = param1 * 10 + param2;
						st.setMemoStateEx(1, param3);
						i4 = st.getMemoState() / 10 * 10 + 6;
						st.setMemoState(i4);
					}
				}
			}
			else if(reply == 6 && st.isStarted() && st.getMemoState() % 10 == 6 && st.getMemoState() / 1000 == 0)
			{
				i0 = st.getMemoStateEx(1);
				if(i0 < 0)
				{
					i0 = 0;
				}
				i1 = i0 % 10;
				i2 = (i0 - i1) / 10;
				param1 = Rnd.get(2) + 1;
				param2 = Rnd.get(5) + 1;
				param3 = param1 * 10 + param2;
				if(param1 == i2)
				{
					i3 = (param2 + i1);
					if(i3 % 5 == 0 && i3 != 10)
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_19.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoState(1);
						st.setMemoStateEx(1, 0);
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_18.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						param3 = param1 * 10 + param2;
						st.setMemoStateEx(1, param3);
						i4 = st.getMemoState() / 10 * 10 + 5;
						st.setMemoState(i4);
					}
				}
				else if(param1 != i2)
				{
					if(param2 == 5 || i1 == 5)
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_19.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoState(1);
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_18.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						param3 = param1 * 10 + param2;
						st.setMemoStateEx(1, param3);
						i4 = st.getMemoState() / 10 * 10 + 5;
						st.setMemoState(i4);
					}
				}
			}
			else if(reply == 8 && st.isStarted() && st.getMemoState() % 10 == 7 && st.getMemoState() / 1000 == 0)
			{
				i0 = st.getMemoState() / 10;
				i1 = (i0 + 1) * 10 + 4;
				st.setMemoState(i1);
				st.setMemoStateEx(1, 0);
				showPage("blacksmith_wilbert_q0663_20.htm", talker);
			}
			else if(reply == 9 && st.isStarted() && st.getMemoState() % 10 == 7 && st.getMemoState() / 1000 == 0)
			{
				i0 = st.getMemoState() / 10;
				if(i0 == 0)
				{
					st.rateAndGive(57, 40000);
				}
				else if(i0 == 1)
				{
					st.rateAndGive(57, 80000);
				}
				else if(i0 == 2)
				{
					st.rateAndGive(57, 110000);
					st.giveItems(955, 1);
				}
				else if(i0 == 3)
				{
					st.rateAndGive(57, 199000);
					st.giveItems(951, 1);
				}
				else if(i0 == 4)
				{
					st.rateAndGive(57, 388000);
					i1 = Rnd.get(18) + 1;
					if(i1 == 1)
					{
						st.giveItems(4963, 1);
					}
					else if(i1 == 2)
					{
						st.giveItems(4964, 1);
					}
					else if(i1 == 3)
					{
						st.giveItems(4965, 1);
					}
					else if(i1 == 4)
					{
						st.giveItems(4966, 1);
					}
					else if(i1 == 5)
					{
						st.giveItems(4967, 1);
					}
					else if(i1 == 6)
					{
						st.giveItems(4968, 1);
					}
					else if(i1 == 7)
					{
						st.giveItems(4969, 1);
					}
					else if(i1 == 8)
					{
						st.giveItems(4970, 1);
					}
					else if(i1 == 9)
					{
						st.giveItems(4971, 1);
					}
					else if(i1 == 10)
					{
						st.giveItems(4972, 1);
					}
					else if(i1 == 11)
					{
						st.giveItems(5000, 1);
					}
					else if(i1 == 12)
					{
						st.giveItems(5001, 1);
					}
					else if(i1 == 13)
					{
						st.giveItems(5002, 1);
					}
					else if(i1 == 14)
					{
						st.giveItems(5003, 1);
					}
					else if(i1 == 15)
					{
						st.giveItems(5004, 1);
					}
					else if(i1 == 16)
					{
						st.giveItems(5005, 1);
					}
					else if(i1 == 17)
					{
						st.giveItems(5006, 1);
					}
					else if(i1 == 18)
					{
						st.giveItems(5007, 1);
					}
				}
				else if(i0 == 5)
				{
					st.rateAndGive(57, 675000);
					i1 = Rnd.get(18) + 1;
					if(i1 == 1)
					{
						st.giveItems(4104, 12);
					}
					else if(i1 == 2)
					{
						st.giveItems(4113, 12);
					}
					else if(i1 == 3)
					{
						st.giveItems(4112, 12);
					}
					else if(i1 == 4)
					{
						st.giveItems(4108, 12);
					}
					else if(i1 == 5)
					{
						st.giveItems(4111, 12);
					}
					else if(i1 == 6)
					{
						st.giveItems(4106, 12);
					}
					else if(i1 == 7)
					{
						st.giveItems(4109, 12);
					}
					else if(i1 == 8)
					{
						st.giveItems(4107, 12);
					}
					else if(i1 == 9)
					{
						st.giveItems(4105, 12);
					}
					else if(i1 == 10)
					{
						st.giveItems(4110, 12);
					}
					else if(i1 == 11)
					{
						st.giveItems(4114, 13);
					}
					else if(i1 == 12)
					{
						st.giveItems(4115, 13);
					}
					else if(i1 == 13)
					{
						st.giveItems(4120, 13);
					}
					else if(i1 == 14)
					{
						st.giveItems(4118, 13);
					}
					else if(i1 == 15)
					{
						st.giveItems(4116, 13);
					}
					else if(i1 == 16)
					{
						st.giveItems(4117, 13);
					}
					else if(i1 == 17)
					{
						st.giveItems(4119, 13);
					}
					else if(i1 == 18)
					{
						st.giveItems(4121, 13);
					}
				}
				else if(i0 == 6)
				{
					st.rateAndGive(57, 1284000);
					st.giveItems(947, 2);
					st.giveItems(948, 2);
				}
				st.setMemoState(1);
				st.setMemoStateEx(1, 0);
				showPage("blacksmith_wilbert_q0663_21.htm", talker);
			}
			else if(reply == 10 && st.isStarted() && st.getMemoState() == 1 && st.getMemoState() / 1000 == 0)
			{
				showPage("blacksmith_wilbert_q0663_21a.htm", talker);
			}
			else if(reply == 14 && st.isStarted() && st.getMemoState() % 10 == 1)
			{
				if(st.getQuestItemsCount(8766) >= 1)
				{
					st.takeItems(8766, 1);
					st.setMemoState(1005);
					showPage("blacksmith_wilbert_q0663_22.htm", talker);
				}
				else
				{
					showPage("blacksmith_wilbert_q0663_22a.htm", talker);
				}
			}
			else if(reply == 15 && st.isStarted() && st.getMemoState() == 1005)
			{
				i0 = st.getMemoStateEx(1);
				if(i0 < 0)
				{
					i0 = 0;
				}
				i1 = i0 % 10;
				i2 = (i0 - i1) / 10;
				param1 = Rnd.get(2) + 1;
				param2 = Rnd.get(5) + 1;
				param3 = param1 * 10 + param2;
				if(param1 == i2)
				{
					i3 = (param2 + i1);
					i4 = (66310 + i2);
					i5 = (66310 + param1);
					if(i3 % 5 == 0 && i3 != 10)
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_25.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>", "<?card1?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName(), String.valueOf(i0)}, false);
						st.setMemoState(1);
						st.rateAndGive(57, 800);
						st.setMemoStateEx(1, 0);
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_24.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoStateEx(1, param3);
						st.setMemoState(1006);
					}
				}
				else if(param1 != i2)
				{
					if(param2 == 5 || i1 == 5)
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_25.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.rateAndGive(57, 800);
						st.setMemoState(1);
						st.setMemoStateEx(1, 0);
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_24.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoStateEx(1, param3);
						st.setMemoState(1006);
					}
				}
			}
			else if(reply == 16 && st.isStarted() && st.getMemoState() == 1006)
			{
				i0 = st.getMemoStateEx(1);
				if(i0 < 0)
				{
					i0 = 0;
				}
				i1 = i0 % 10;
				i2 = (i0 - i1) / 10;
				param1 = Rnd.get(2) + 1;
				param2 = Rnd.get(5) + 1;
				param3 = param1 * 10 + param2;
				if(param1 == i2)
				{
					i3 = param2 + i1;
					if(i3 % 5 == 0 && i3 != 10)
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_29.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoState(1);
						st.setMemoStateEx(1, 0);
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_28.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						param3 = param1 * 10 + param2;
						st.setMemoStateEx(1, param3);
						st.setMemoState(1005);
					}
				}
				else if(param1 != i2)
				{
					if(param2 == 5 || i1 == 5)
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_29.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						st.setMemoState(1);
						st.setMemoStateEx(1, 0);
					}
					else
					{
						showHtmlFile(talker, "blacksmith_wilbert_q0663_28.htm", new String[]{"<?card1pic?>", "<?card2pic?>", "<?name?>"},
								new String[]{Strings.getFString(66300 + i0), Strings.getFString(66300 + param3), talker.getName()}, false);
						param3 = param1 * 10 + param2;
						st.setMemoStateEx(1, param3);
						st.setMemoState(1005);
					}
				}
			}
			else if(reply == 20 && st.isStarted())
			{
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				showPage("blacksmith_wilbert_q0663_30.htm", talker);
			}
			else if(reply == 21 && st.isStarted())
			{
				showPage("blacksmith_wilbert_q0663_31.htm", talker);
			}
			else if(reply == 22 && st.isStarted())
			{
				showPage("blacksmith_wilbert_q0663_32.htm", talker);
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(mobs.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithMemoState(killer, 1, 4);
			if(st != null && st.rollAndGive(8766, 1, mobs.get(npc.getNpcId())))
				st.playSound(SOUND_ITEMGET);
		}
	}
}