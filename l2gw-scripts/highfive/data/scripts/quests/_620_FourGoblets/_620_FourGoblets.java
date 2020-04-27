package quests._620_FourGoblets;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author: rage
 * @date: 20.01.12 15:45
 */
public class _620_FourGoblets extends Quest
{
	// NPC
	private static final int printessa_spirit = 31453;
	private static final int el_lord_chamber_ghost = 31919;
	private static final int wigoth_ghost_a = 31452;
	private static final int wigoth_ghost_b = 31454;
	private static final int conquerors_keeper = 31921;
	private static final int lords_keeper = 31922;
	private static final int savants_keeper = 31923;
	private static final int magistrates_keeper = 31924;

	// Mobs
	private static final int halisha_alectia = 25339;
	private static final int halisha_tishas = 25342;
	private static final int halisha_mekara = 25346;
	private static final int halisha_morigul = 25349;

	private static final HashMap<Integer, Integer> mobs1 = new HashMap<>(61);
	private static final HashMap<Integer, Integer> mobs2 = new HashMap<>(10);
	private static final HashMap<Integer, Integer> mobs3 = new HashMap<>(8);

	static
	{
		mobs1.put(18120, 51);
		mobs1.put(18122, 10);
		mobs1.put(18121, 44);
		mobs1.put(18123, 51);
		mobs1.put(18125, 10);
		mobs1.put(18124, 44);
		mobs1.put(18126, 51);
		mobs1.put(18128, 10);
		mobs1.put(18127, 44);
		mobs1.put(18129, 51);
		mobs1.put(18131, 10);
		mobs1.put(18130, 44);
		mobs1.put(18132, 54);
		mobs1.put(18133, 42);
		mobs1.put(18134, 7);
		mobs1.put(18135, 42);
		mobs1.put(18136, 42);
		mobs1.put(18138, 41);
		mobs1.put(18139, 39);
		mobs1.put(18137, 6);
		mobs1.put(18140, 41);
		mobs1.put(18166, 8);
		mobs1.put(18167, 7);
		mobs1.put(18168, 10);
		mobs1.put(18169, 6);
		mobs1.put(18171, 11);
		mobs1.put(18170, 7);
		mobs1.put(18172, 6);
		mobs1.put(18173, 17);
		mobs1.put(18175, 10);
		mobs1.put(18174, 45);
		mobs1.put(18176, 17);
		mobs1.put(18178, 10);
		mobs1.put(18177, 45);
		mobs1.put(18179, 17);
		mobs1.put(18181, 10);
		mobs1.put(18180, 45);
		mobs1.put(18182, 17);
		mobs1.put(18184, 10);
		mobs1.put(18183, 45);
		mobs1.put(18195, 8);
		mobs1.put(18185, 46);
		mobs1.put(18186, 47);
		mobs1.put(18187, 42);
		mobs1.put(18188, 7);
		mobs1.put(18189, 42);
		mobs1.put(18190, 42);
		mobs1.put(18192, 41);
		mobs1.put(18193, 39);
		mobs1.put(18191, 6);
		mobs1.put(18194, 41);
		mobs1.put(18220, 47);
		mobs1.put(18221, 51);
		mobs1.put(18222, 43);
		mobs1.put(18223, 7);
		mobs1.put(18224, 44);
		mobs1.put(18225, 43);
		mobs1.put(18227, 82);
		mobs1.put(18228, 36);
		mobs1.put(18226, 6);
		mobs1.put(18229, 41);

		mobs2.put(18141, 90);
		mobs2.put(18142, 90);
		mobs2.put(18143, 90);
		mobs2.put(18144, 90);
		mobs2.put(18149, 75);
		mobs2.put(18148, 85);
		mobs2.put(18146, 78);
		mobs2.put(18147, 73);
		mobs2.put(18145, 76);
		mobs2.put(18230, 58);

		mobs3.put(18212, 50);
		mobs3.put(18213, 50);
		mobs3.put(18214, 50);
		mobs3.put(18215, 50);
		mobs3.put(18216, 50);
		mobs3.put(18217, 50);
		mobs3.put(18218, 50);
		mobs3.put(18219, 50);
	}

	public _620_FourGoblets()
	{
		super(620, "_620_FourGoblets", "Four Goblets");
		addStartNpc(printessa_spirit);
		addStartNpc(wigoth_ghost_a);
		addTalkId(printessa_spirit, el_lord_chamber_ghost, wigoth_ghost_a, wigoth_ghost_b);
		addTalkId(conquerors_keeper, lords_keeper, savants_keeper, magistrates_keeper);

		addKillId(halisha_alectia, halisha_tishas, halisha_mekara, halisha_morigul);
		addKillId(mobs1.keySet());
		addKillId(mobs2.keySet());
		addKillId(mobs3.keySet());

		addQuestItem(7255, 7256, 7257, 7258, 7259, 7261);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == printessa_spirit)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 74)
					return "printessa_spirit_q0620_01.htm";

				return "printessa_spirit_q0620_12.htm";
			}
			if(st.isStarted())
			{
				if(st.getQuestItemsCount(7262) == 0 && (st.getQuestItemsCount(7256) == 0 || st.getQuestItemsCount(7257) == 0 || st.getQuestItemsCount(7258) == 0 || st.getQuestItemsCount(7259) == 0))
					return "npchtm:printessa_spirit_q0620_14.htm";
				if(st.getQuestItemsCount(7262) == 0 && st.getQuestItemsCount(7256) > 0 && st.getQuestItemsCount(7257) > 0 && st.getQuestItemsCount(7258) > 0 && st.getQuestItemsCount(7259) > 0)
					return "npchtm:printessa_spirit_q0620_15.htm";
				if(st.getQuestItemsCount(7262) > 0)
					return "npchtm:printessa_spirit_q0620_17.htm";
			}
		}
		else if(npc.getNpcId() == el_lord_chamber_ghost)
		{
			if(st.isStarted())
				return "npchtm:el_lord_chamber_ghost_q0620_01.htm";
		}
		else if(npc.getNpcId() == wigoth_ghost_a)
		{
			if(st.isCreated())
				return "npchtm:wigoth_ghost_a_q0620_01.htm";
			if(st.isStarted())
			{
				if(st.getQuestItemsCount(7262) == 0 && (st.getQuestItemsCount(7256) == 0 || st.getQuestItemsCount(7257) == 0 || st.getQuestItemsCount(7258) == 0 || st.getQuestItemsCount(7259) == 0) && st.getQuestItemsCount(7256) + st.getQuestItemsCount(7257) + st.getQuestItemsCount(7258) + st.getQuestItemsCount(7259) < 3)
					return "npchtm:wigoth_ghost_a_q0620_01.htm";
				if(st.getQuestItemsCount(7262) == 0 && (st.getQuestItemsCount(7256) == 0 || st.getQuestItemsCount(7257) == 0 || st.getQuestItemsCount(7258) == 0 || st.getQuestItemsCount(7259) == 0) && st.getQuestItemsCount(7256) + st.getQuestItemsCount(7257) + st.getQuestItemsCount(7258) + st.getQuestItemsCount(7259) >= 3)
					return "npchtm:wigoth_ghost_a_q0620_02.htm";
				if(st.getQuestItemsCount(7262) == 0 && st.getQuestItemsCount(7256) >= 1 && st.getQuestItemsCount(7257) >= 1 && st.getQuestItemsCount(7258) >= 1 && st.getQuestItemsCount(7259) >= 1)
					return "npchtm:wigoth_ghost_a_q0620_04.htm";
				if(st.getQuestItemsCount(7262) >= 1)
					return "npchtm:wigoth_ghost_a_q0620_05.htm";
			}
		}
		else if(npc.getNpcId() == wigoth_ghost_b)
		{
			if(st.isStarted())
			{
				if(st.getMemoStateEx(1) == 2 && st.getQuestItemsCount(7256) >= 1 && st.getQuestItemsCount(7257) >= 1 && st.getQuestItemsCount(7258) >= 1 && st.getQuestItemsCount(7259) >= 1)
				{
					if(st.getQuestItemsCount(7255) == 0 && st.getQuestItemsCount(7254) < 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_01.htm";
					}
					if(st.getQuestItemsCount(7255) >= 1 && st.getQuestItemsCount(7254) < 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_02.htm";
					}
					if(st.getQuestItemsCount(7255) == 0 && st.getQuestItemsCount(7254) >= 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_03.htm";
					}
					if(st.getQuestItemsCount(7255) >= 1 && st.getQuestItemsCount(7254) >= 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_04.htm";
					}
				}
				if(st.getMemoStateEx(1) == 2 && (st.getQuestItemsCount(7256) == 0 || st.getQuestItemsCount(7257) == 0 || st.getQuestItemsCount(7258) == 0 || st.getQuestItemsCount(7259) == 0))
				{
					if(st.getQuestItemsCount(7255) == 0 && st.getQuestItemsCount(7254) < 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_05.htm";
					}
					if(st.getQuestItemsCount(7255) >= 1 && st.getQuestItemsCount(7254) < 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_06.htm";
					}
					if(st.getQuestItemsCount(7255) == 0 && st.getQuestItemsCount(7254) >= 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_07.htm";
					}
					if(st.getQuestItemsCount(7255) >= 1 && st.getQuestItemsCount(7254) >= 1000)
					{
						st.setMemoStateEx(1, 3);
						return "npchtm:wigoth_ghost_b_q0620_08.htm";
					}
				}
				if(st.getMemoStateEx(1) == 3)
				{
					if(st.getQuestItemsCount(7255) == 0 && st.getQuestItemsCount(7254) < 1000)
					{
						return "npchtm:wigoth_ghost_b_q0620_09.htm";
					}
					if(st.getQuestItemsCount(7255) >= 1 && st.getQuestItemsCount(7254) < 1000)
					{
						return "npchtm:wigoth_ghost_b_q0620_10.htm";
					}
					if(st.getQuestItemsCount(7255) == 0 && st.getQuestItemsCount(7254) >= 1000)
					{
						return "npchtm:wigoth_ghost_b_q0620_11.htm";
					}
					if(st.getQuestItemsCount(7255) >= 1 && st.getQuestItemsCount(7254) >= 1000)
					{
						return "npchtm:wigoth_ghost_b_q0620_12.htm";
					}
				}
			}
		}
		else if(npc.getNpcId() == conquerors_keeper)
		{
			if(st.isStarted())
				return "npchtm:conquerors_keeper_q0620_01.htm";
		}
		else if(npc.getNpcId() == lords_keeper)
		{
			if(st.isStarted())
				return "npchtm:lords_keeper_q0620_01.htm";
		}
		else if(npc.getNpcId() == savants_keeper)
		{
			if(st.isStarted())
				return "npchtm:savants_keeper_q0620_01.htm";
		}
		else if(npc.getNpcId() == magistrates_keeper)
		{
			if(st.isStarted())
				return "npchtm:magistrates_keeper_q0620_01.htm";
		}


		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == printessa_spirit)
		{
			if(reply == 620)
			{
				st.setCond(1);
				st.setState(STARTED);
				st.setMemoState(0);
				st.playSound(SOUND_ACCEPT);
				if(st.getQuestItemsCount(7262) >= 1)
				{
					st.setCond(2);
					showQuestPage("printessa_spirit_q0620_13.htm", talker);
				}
			}
			else if(reply == 5)
			{
				showQuestPage("printessa_spirit_q0620_02.htm", talker);
			}
			else if(reply == 6)
			{
				showQuestPage("printessa_spirit_q0620_03.htm", talker);
			}
			else if(reply == 7)
			{
				showQuestPage("printessa_spirit_q0620_04.htm", talker);
			}
			else if(reply == 8)
			{
				showQuestPage("printessa_spirit_q0620_05.htm", talker);
			}
			else if(reply == 9)
			{
				showQuestPage("printessa_spirit_q0620_06.htm", talker);
			}
			else if(reply == 10)
			{
				showQuestPage("printessa_spirit_q0620_07.htm", talker);
			}
			else if(reply == 11)
			{
				showQuestPage("printessa_spirit_q0620_08.htm", talker);
			}
			else if(reply == 12)
			{
				showQuestPage("printessa_spirit_q0620_09.htm", talker);
			}
			else if(reply == 13)
			{
				showQuestPage("printessa_spirit_q0620_10.htm", talker);
			}
			else if(reply == 14)
			{
				showQuestPage("printessa_spirit_q0620_11.htm", talker);
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getQuestItemsCount(7262) == 0 && (st.getQuestItemsCount(7256) >= 1 && st.getQuestItemsCount(7257) >= 1 && st.getQuestItemsCount(7258) >= 1 && st.getQuestItemsCount(7259) >= 1))
				{
					st.giveItems(7262, 1);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					st.takeItems(7256, 1);
					st.takeItems(7257, 1);
					st.takeItems(7258, 1);
					st.takeItems(7259, 1);
					showPage("printessa_spirit_q0620_16.htm", talker);
				}
			}
			else if(reply == 1)
			{
				st.takeItems(7260, -1);
				st.takeItems(7261, -1);
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				showPage("printessa_spirit_q0620_18.htm", talker);
			}
			else if(reply == 2)
			{
				showPage("printessa_spirit_q0620_19.htm", talker);
			}
			else if(reply == 3)
			{
				showPage("printessa_spirit_q0620_20.htm", talker);
			}
		}
		else if(npc.getNpcId() == el_lord_chamber_ghost)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(7255) >= 1)
				{
					if(Rnd.get(100) < 50)
					{
						int i0 = Rnd.get(5);
						int i2 = 0;
						if(i0 == 0)
						{
							i2 = 1;
							st.rateAndGive(57, 10000);
						}
						else if(i0 == 1)
						{
							if(Rnd.get(1000) < 848)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 43)
								{
									st.rateAndGive(1884, 42);
								}
								else if(i1 < 66)
								{
									st.rateAndGive(1895, 36);
								}
								else if(i1 < 184)
								{
									st.rateAndGive(1876, 4);
								}
								else if(i1 < 250)
								{
									st.rateAndGive(1881, 6);
								}
								else if(i1 < 287)
								{
									st.rateAndGive(5549, 8);
								}
								else if(i1 < 484)
								{
									st.rateAndGive(1874, 1);
								}
								else if(i1 < 681)
								{
									st.rateAndGive(1889, 1);
								}
								else if(i1 < 799)
								{
									st.rateAndGive(1877, 1);
								}
								else if(i1 < 902)
								{
									st.rateAndGive(1894, 1);
								}
								else
								{
									st.rateAndGive(4043, 1);
								}
							}
							if(Rnd.get(1000) < 323)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 335)
								{
									st.rateAndGive(1888, 1);
								}
								else if(i1 < 556)
								{
									st.rateAndGive(4040, 1);
								}
								else if(i1 < 725)
								{
									st.rateAndGive(1890, 1);
								}
								else if(i1 < 872)
								{
									st.rateAndGive(5550, 1);
								}
								else if(i1 < 962)
								{
									st.rateAndGive(1893, 1);
								}
								else if(i1 < 986)
								{
									st.rateAndGive(4046, 1);
								}
								else
								{
									st.rateAndGive(4048, 1);
								}
							}
						}
						else if(i0 == 2)
						{
							if(Rnd.get(1000) < 847)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 148)
								{
									st.rateAndGive(1878, 8);
								}
								else if(i1 < 175)
								{
									st.rateAndGive(1882, 24);
								}
								else if(i1 < 273)
								{
									st.rateAndGive(1879, 4);
								}
								else if(i1 < 322)
								{
									st.rateAndGive(1880, 6);
								}
								else if(i1 < 357)
								{
									st.rateAndGive(1885, 6);
								}
								else if(i1 < 554)
								{
									st.rateAndGive(1875, 1);
								}
								else if(i1 < 685)
								{
									st.rateAndGive(1883, 1);
								}
								else if(i1 < 803)
								{
									st.rateAndGive(5220, 1);
								}
								else if(i1 < 901)
								{
									st.rateAndGive(4039, 1);
								}
								else
								{
									st.rateAndGive(4044, 1);
								}
							}
							if(Rnd.get(1000) < 251)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 350)
								{
									st.rateAndGive(1887, 1);
								}
								else if(i1 < 587)
								{
									st.rateAndGive(4042, 1);
								}
								else if(i1 < 798)
								{
									st.rateAndGive(1886, 1);
								}
								else if(i1 < 922)
								{
									st.rateAndGive(4041, 1);
								}
								else if(i1 < 966)
								{
									st.rateAndGive(1892, 1);
								}
								else if(i1 < 996)
								{
									st.rateAndGive(1891, 1);
								}
								else
								{
									st.rateAndGive(4047, 1);
								}
							}
						}
						else if(i0 == 3)
						{
							if(Rnd.get(1000) < 31)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 223)
								{
									st.rateAndGive(730, 1);
								}
								else if(i1 < 893)
								{
									st.rateAndGive(948, 1);
								}
								else
								{
									st.rateAndGive(960, 1);
								}
							}
							if(Rnd.get(1000) < 5)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 202)
								{
									st.rateAndGive(729, 1);
								}
								else if(i1 < 928)
								{
									st.rateAndGive(947, 1);
								}
								else
								{
									st.rateAndGive(959, 1);
								}
							}
						}
						else if(i0 == 4)
						{
							if(Rnd.get(1000) < 329)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 88)
								{
									st.rateAndGive(6698, 1);
								}
								else if(i1 < 185)
								{
									st.rateAndGive(6699, 1);
								}
								else if(i1 < 238)
								{
									st.rateAndGive(6700, 1);
								}
								else if(i1 < 262)
								{
									st.rateAndGive(6701, 1);
								}
								else if(i1 < 292)
								{
									st.rateAndGive(6702, 1);
								}
								else if(i1 < 356)
								{
									st.rateAndGive(6703, 1);
								}
								else if(i1 < 420)
								{
									st.rateAndGive(6704, 1);
								}
								else if(i1 < 482)
								{
									st.rateAndGive(6705, 1);
								}
								else if(i1 < 554)
								{
									st.rateAndGive(6706, 1);
								}
								else if(i1 < 576)
								{
									st.rateAndGive(6707, 1);
								}
								else if(i1 < 640)
								{
									st.rateAndGive(6708, 1);
								}
								else if(i1 < 704)
								{
									st.rateAndGive(6709, 1);
								}
								else if(i1 < 777)
								{
									st.rateAndGive(6710, 1);
								}
								else if(i1 < 799)
								{
									st.rateAndGive(6711, 1);
								}
								else if(i1 < 863)
								{
									st.rateAndGive(6712, 1);
								}
								else if(i1 < 927)
								{
									st.rateAndGive(6713, 1);
								}
								else
								{
									st.rateAndGive(6714, 1);
								}
							}
							if(Rnd.get(1000) < 54)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 100)
								{
									st.rateAndGive(6688, 1);
								}
								else if(i1 < 198)
								{
									st.rateAndGive(6689, 1);
								}
								else if(i1 < 298)
								{
									st.rateAndGive(6690, 1);
								}
								else if(i1 < 398)
								{
									st.rateAndGive(6691, 1);
								}
								else if(i1 < 499)
								{
									st.rateAndGive(7579, 1);
								}
								else if(i1 < 601)
								{
									st.rateAndGive(6693, 1);
								}
								else if(i1 < 703)
								{
									st.rateAndGive(6694, 1);
								}
								else if(i1 < 801)
								{
									st.rateAndGive(6695, 1);
								}
								else if(i1 < 902)
								{
									st.rateAndGive(6696, 1);
								}
								else
								{
									st.rateAndGive(6697, 1);
								}
							}
						}

						st.takeItems(7255, 1);
						if(i2 == 1)
						{
							showPage("el_lord_chamber_ghost_q0620_03.htm", talker);
						}
						else if(i2 == 0)
						{
							showPage("el_lord_chamber_ghost_q0620_04.htm", talker);
						}
					}
					else
					{
						st.takeItems(7255, 1);
						showPage("el_lord_chamber_ghost_q0620_05.htm", talker);
					}
				}
				else if(st.isStarted() && st.getQuestItemsCount(7255) == 0)
				{
					showPage("el_lord_chamber_ghost_q0620_06.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == wigoth_ghost_a)
		{
			if(reply == 1)
			{
				showPage("wigoth_ghost_a_q0620_03.htm", talker);
			}
			else if(reply == 2)
			{
				st.takeItems(7260, -1);
				talker.teleToLocation(169584, -91008, -2912);
			}
		}
		else if(npc.getNpcId() == wigoth_ghost_b)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getQuestItemsCount(7255) >= 1)
				{
					if(Rnd.get(100) < 50)
					{
						int i0 = Rnd.get(5);
						int i2 = 0;
						if(i0 == 0)
						{
							i2 = 1;
							st.rateAndGive(57, 10000);
						}
						else if(i0 == 1)
						{
							if(Rnd.get(1000) < 848)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 43)
								{
									st.rateAndGive(1884, 42);
								}
								else if(i1 < 66)
								{
									st.rateAndGive(1895, 36);
								}
								else if(i1 < 184)
								{
									st.rateAndGive(1876, 4);
								}
								else if(i1 < 250)
								{
									st.rateAndGive(1881, 6);
								}
								else if(i1 < 287)
								{
									st.rateAndGive(5549, 8);
								}
								else if(i1 < 484)
								{
									st.rateAndGive(1874, 1);
								}
								else if(i1 < 681)
								{
									st.rateAndGive(1889, 1);
								}
								else if(i1 < 799)
								{
									st.rateAndGive(1877, 1);
								}
								else if(i1 < 902)
								{
									st.rateAndGive(1894, 1);
								}
								else
								{
									st.rateAndGive(4043, 1);
								}
							}
							if(Rnd.get(1000) < 323)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 335)
								{
									st.rateAndGive(1888, 1);
								}
								else if(i1 < 556)
								{
									st.rateAndGive(4040, 1);
								}
								else if(i1 < 725)
								{
									st.rateAndGive(1890, 1);
								}
								else if(i1 < 872)
								{
									st.rateAndGive(5550, 1);
								}
								else if(i1 < 962)
								{
									st.rateAndGive(1893, 1);
								}
								else if(i1 < 986)
								{
									st.rateAndGive(4046, 1);
								}
								else
								{
									st.rateAndGive(4048, 1);
								}
							}
						}
						else if(i0 == 2)
						{
							if(Rnd.get(1000) < 847)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 148)
								{
									st.rateAndGive(1878, 8);
								}
								else if(i1 < 175)
								{
									st.rateAndGive(1882, 24);
								}
								else if(i1 < 273)
								{
									st.rateAndGive(1879, 4);
								}
								else if(i1 < 322)
								{
									st.rateAndGive(1880, 6);
								}
								else if(i1 < 357)
								{
									st.rateAndGive(1885, 6);
								}
								else if(i1 < 554)
								{
									st.rateAndGive(1875, 1);
								}
								else if(i1 < 685)
								{
									st.rateAndGive(1883, 1);
								}
								else if(i1 < 803)
								{
									st.rateAndGive(5220, 1);
								}
								else if(i1 < 901)
								{
									st.rateAndGive(4039, 1);
								}
								else
								{
									st.rateAndGive(4044, 1);
								}
							}
							if(Rnd.get(1000) < 251)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 350)
								{
									st.rateAndGive(1887, 1);
								}
								else if(i1 < 587)
								{
									st.rateAndGive(4042, 1);
								}
								else if(i1 < 798)
								{
									st.rateAndGive(1886, 1);
								}
								else if(i1 < 922)
								{
									st.rateAndGive(4041, 1);
								}
								else if(i1 < 966)
								{
									st.rateAndGive(1892, 1);
								}
								else if(i1 < 996)
								{
									st.rateAndGive(1891, 1);
								}
								else
								{
									st.rateAndGive(4047, 1);
								}
							}
						}
						else if(i0 == 3)
						{
							if(Rnd.get(1000) < 31)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 223)
								{
									st.rateAndGive(730, 1);
								}
								else if(i1 < 893)
								{
									st.rateAndGive(948, 1);
								}
								else
								{
									st.rateAndGive(960, 1);
								}
							}
							if(Rnd.get(1000) < 5)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 202)
								{
									st.rateAndGive(729, 1);
								}
								else if(i1 < 928)
								{
									st.rateAndGive(947, 1);
								}
								else
								{
									st.rateAndGive(959, 1);
								}
							}
						}
						else if(i0 == 4)
						{
							if(Rnd.get(1000) < 329)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 88)
								{
									st.rateAndGive(6698, 1);
								}
								else if(i1 < 185)
								{
									st.rateAndGive(6699, 1);
								}
								else if(i1 < 238)
								{
									st.rateAndGive(6700, 1);
								}
								else if(i1 < 262)
								{
									st.rateAndGive(6701, 1);
								}
								else if(i1 < 292)
								{
									st.rateAndGive(6702, 1);
								}
								else if(i1 < 356)
								{
									st.rateAndGive(6703, 1);
								}
								else if(i1 < 420)
								{
									st.rateAndGive(6704, 1);
								}
								else if(i1 < 482)
								{
									st.rateAndGive(6705, 1);
								}
								else if(i1 < 554)
								{
									st.rateAndGive(6706, 1);
								}
								else if(i1 < 576)
								{
									st.rateAndGive(6707, 1);
								}
								else if(i1 < 640)
								{
									st.rateAndGive(6708, 1);
								}
								else if(i1 < 704)
								{
									st.rateAndGive(6709, 1);
								}
								else if(i1 < 777)
								{
									st.rateAndGive(6710, 1);
								}
								else if(i1 < 799)
								{
									st.rateAndGive(6711, 1);
								}
								else if(i1 < 863)
								{
									st.rateAndGive(6712, 1);
								}
								else if(i1 < 927)
								{
									st.rateAndGive(6713, 1);
								}
								else
								{
									st.rateAndGive(6714, 1);
								}
							}
							if(Rnd.get(1000) < 54)
							{
								i2 = 1;
								int i1 = Rnd.get(1000);
								if(i1 < 100)
								{
									st.rateAndGive(6688, 1);
								}
								else if(i1 < 198)
								{
									st.rateAndGive(6689, 1);
								}
								else if(i1 < 298)
								{
									st.rateAndGive(6690, 1);
								}
								else if(i1 < 398)
								{
									st.rateAndGive(6691, 1);
								}
								else if(i1 < 499)
								{
									st.rateAndGive(7579, 1);
								}
								else if(i1 < 601)
								{
									st.rateAndGive(6693, 1);
								}
								else if(i1 < 703)
								{
									st.rateAndGive(6694, 1);
								}
								else if(i1 < 801)
								{
									st.rateAndGive(6695, 1);
								}
								else if(i1 < 902)
								{
									st.rateAndGive(6696, 1);
								}
								else
								{
									st.rateAndGive(6697, 1);
								}
							}
						}

						st.takeItems(7255, 1);
						if(i2 == 1)
						{
							showPage("wigoth_ghost_b_q0620_13.htm", talker);
						}
						else if(i2 == 0)
						{
							showPage("wigoth_ghost_b_q0620_14.htm", talker);
						}
					}
					else
					{
						st.takeItems(7255, 1);
						showPage("wigoth_ghost_b_q0620_15.htm", talker);
					}
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					showPage("wigoth_ghost_b_q0620_16.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6881, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_17.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6883, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_18.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6885, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_19.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6887, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_20.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(7580, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_21.htm", talker);
				}
			}
			else if(reply == 8)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6891, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_22.htm", talker);
				}
			}
			else if(reply == 9)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6893, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_23.htm", talker);
				}
			}
			else if(reply == 10)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6895, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_24.htm", talker);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6897, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_25.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && (st.getMemoStateEx(1) == 2 || st.getMemoStateEx(1) == 3) && st.getQuestItemsCount(7254) >= 1000)
				{
					st.giveItems(6899, 1);
					st.takeItems(7254, 1000);
					showPage("wigoth_ghost_b_q0620_26.htm", talker);
				}
			}
			else if(reply == 13)
			{
				if(st.isStarted())
				{
					talker.teleToLocation(170000, -88250, -2912);
					showPage("wigoth_ghost_b_q0620_01a.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == conquerors_keeper)
		{
			if(reply == 1)
			{
				if(st.isStarted())
				{
					int i0 = Calendar.getInstance().get(Calendar.MINUTE);
					L2Party party0 = talker.getParty();
					if(i0 >= 0 && i0 < 55)
					{
						showPage("conquerors_keeper_q0620_02.htm", talker);
					}
					else if(party0 == null || party0.getMemberCount() < 4)
					{
						showPage("conquerors_keeper_q0620_04.htm", talker);
					}
					else if(talker != party0.getPartyLeader())
					{
						showPage("conquerors_keeper_q0620_03.htm", talker);
					}
					else
					{
						for(L2Player member : party0.getPartyMembers())
						{
							if(member.getItemCountByItemId(7075) == 0)
							{
								showHtmlFile(talker, "conquerors_keeper_q0620_05.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
							else if(!member.isQuestStarted(620))
							{
								showHtmlFile(talker, "conquerors_keeper_q0620_06.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
						}
						if(!npc.av_quest0.compareAndSet(0, 1))
						{
							showPage("conquerors_keeper_q0620_07.htm", talker);
						}
						else
						{
							for(L2Player member : party0.getPartyMembers())
							{
								QuestState qs = member.getQuestState(620);
								if(qs.getQuestItemsCount(7262) == 0)
								{
									qs.giveItems(7261, 1);
								}
								qs.takeItems(7075, 1);
								qs.setMemoStateEx(1, 1);
								qs.takeItems(7260, -1);
							}

							npc.teleportParty(party0, 181528, -85583, -7216, 1000, 0);
						}
					}
				}
			}
		}
		else if(npc.getNpcId() == lords_keeper)
		{
			if(reply == 1)
			{
				if(st.isStarted())
				{
					int i0 = Calendar.getInstance().get(Calendar.MINUTE);
					L2Party party0 = talker.getParty();
					if(i0 >= 0 && i0 < 55)
					{
						showPage("lords_keeper_q0620_02.htm", talker);
					}
					else if(party0 == null || party0.getMemberCount() < 4)
					{
						showPage("lords_keeper_q0620_04.htm", talker);
					}
					else if(talker != party0.getPartyLeader())
					{
						showPage("lords_keeper_q0620_03.htm", talker);
					}
					else
					{
						for(L2Player member : party0.getPartyMembers())
						{
							if(member.getItemCountByItemId(7075) == 0)
							{
								showHtmlFile(talker, "lords_keeper_q0620_05.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
							else if(!member.isQuestStarted(620))
							{
								showHtmlFile(talker, "lords_keeper_q0620_06.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
						}
						if(!npc.av_quest0.compareAndSet(0, 1))
						{
							showPage("lords_keeper_q0620_07.htm", talker);
						}
						else
						{
							for(L2Player member : party0.getPartyMembers())
							{
								QuestState qs = member.getQuestState(620);
								if(qs.getQuestItemsCount(7262) == 0)
								{
									qs.giveItems(7261, 1);
								}
								qs.takeItems(7075, 1);
								qs.setMemoStateEx(1, 1);
								qs.takeItems(7260, -1);
							}

							npc.teleportParty(party0, 179849, -88990, -7216, 1000, 0);
						}
					}
				}
			}
		}
		else if(npc.getNpcId() == savants_keeper)
		{
			if(reply == 1)
			{
				if(st.isStarted())
				{
					int i0 = Calendar.getInstance().get(Calendar.MINUTE);
					L2Party party0 = talker.getParty();
					if(i0 >= 0 && i0 < 55)
					{
						showPage("savants_keeper_q0620_02.htm", talker);
					}
					else if(party0 == null || party0.getMemberCount() < 4)
					{
						showPage("savants_keeper_q0620_04.htm", talker);
					}
					else if(talker != party0.getPartyLeader())
					{
						showPage("savants_keeper_q0620_03.htm", talker);
					}
					else
					{
						for(L2Player member : party0.getPartyMembers())
						{
							if(member.getItemCountByItemId(7075) == 0)
							{
								showHtmlFile(talker, "savants_keeper_q0620_05.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
							else if(!member.isQuestStarted(620))
							{
								showHtmlFile(talker, "savants_keeper_q0620_06.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
						}
						if(!npc.av_quest0.compareAndSet(0, 1))
						{
							showPage("savants_keeper_q0620_07.htm", talker);
						}
						else
						{
							for(L2Player member : party0.getPartyMembers())
							{
								QuestState qs = member.getQuestState(620);
								if(qs.getQuestItemsCount(7262) == 0)
								{
									qs.giveItems(7261, 1);
								}
								qs.takeItems(7075, 1);
								qs.setMemoStateEx(1, 1);
								qs.takeItems(7260, -1);
							}

							npc.teleportParty(party0, 173216, -86195, -7216, 1000, 0);
						}
					}
				}
			}
		}
		else if(npc.getNpcId() == magistrates_keeper)
		{
			if(reply == 1)
			{
				if(st.isStarted())
				{
					int i0 = Calendar.getInstance().get(Calendar.MINUTE);
					L2Party party0 = talker.getParty();
					if(i0 >= 0 && i0 < 55)
					{
						showPage("magistrates_keeper_q0620_02.htm", talker);
					}
					else if(party0 == null || party0.getMemberCount() < 4)
					{
						showPage("magistrates_keeper_q0620_04.htm", talker);
					}
					else if(talker != party0.getPartyLeader())
					{
						showPage("magistrates_keeper_q0620_03.htm", talker);
					}
					else
					{
						for(L2Player member : party0.getPartyMembers())
						{
							if(member.getItemCountByItemId(7075) == 0)
							{
								showHtmlFile(talker, "magistrates_keeper_q0620_05.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
							else if(!member.isQuestStarted(620))
							{
								showHtmlFile(talker, "magistrates_keeper_q0620_06.htm", new String[]{"<?member1?>"}, new String[]{member.getName()}, false);
								return;
							}
						}
						if(!npc.av_quest0.compareAndSet(0, 1))
						{
							showPage("magistrates_keeper_q0620_07.htm", talker);
						}
						else
						{
							for(L2Player member : party0.getPartyMembers())
							{
								QuestState qs = member.getQuestState(620);
								if(qs.getQuestItemsCount(7262) == 0)
								{
									qs.giveItems(7261, 1);
								}
								qs.takeItems(7075, 1);
								qs.setMemoStateEx(1, 1);
								qs.takeItems(7260, -1);
							}

							npc.teleportParty(party0, 175615, -82365, -7216, 1000, 0);
						}
					}
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		return "npchtm:" + event;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == halisha_alectia || npc.getNpcId() == halisha_mekara || npc.getNpcId() == halisha_tishas || npc.getNpcId() == halisha_morigul)
		{
			npc.createOnePrivate(31452, "WigothGhostA", 0, 0, npc.getX(), npc.getY(), npc.getZ(), 0, 0, 0, 0);
			for(QuestState st : getPartyMembersWithQuest(killer, -1))
			{
				if(st.getQuestItemsCount(7262) == 0 )
				{
					if(npc.getNpcId() == halisha_alectia && st.getQuestItemsCount(7256) == 0)
						st.giveItems(7256, 1);
					else if(npc.getNpcId() == halisha_tishas && st.getQuestItemsCount(7257) == 0)
						st.giveItems(7257, 1);
					else if(npc.getNpcId() == halisha_mekara && st.getQuestItemsCount(7258) == 0)
						st.giveItems(7258, 1);
					else if(npc.getNpcId() == halisha_morigul && st.getQuestItemsCount(7259) == 0)
						st.giveItems(7259, 1);
				}
				st.setMemoStateEx(1, 2);
			}
		}
		else if(mobs1.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, -1);
			if(st != null)
			{
				if(st.rollAndGive(7255, Rnd.chance(mobs1.get(npc.getNpcId())) ? 2 : 1, 100))
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(mobs2.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, -1);
			if(st != null)
			{
				if(st.rollAndGive(7255, 1, mobs2.get(npc.getNpcId())))
					st.playSound(SOUND_ITEMGET);
			}
		}
		else if(mobs3.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, -1);
			if(st != null)
			{
				if(st.rollAndGive(7255, Rnd.chance(mobs3.get(npc.getNpcId())) ? 5 : 4, 100))
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}