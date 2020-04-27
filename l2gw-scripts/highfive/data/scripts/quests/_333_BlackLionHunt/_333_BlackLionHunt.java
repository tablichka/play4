package quests._333_BlackLionHunt;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _333_BlackLionHunt extends Quest
{
	//Items
	private static final int BLACK_LION_MARK = 1369;
	private static final int ADENA_ID = 57;

	//Drops
	private static final int CARGO_BOX1 = 3440;
	//private static final int CARGO_BOX2 = 3441; //TODO выяснить зачем одинаковым итемам разные ID
	//private static final int CARGO_BOX3 = 3442;
	//private static final int CARGO_BOX4 = 3443;
	private static final int UNDEAD_ASH = 3848;
	private static final int BLOODY_AXE_INSIGNIAS = 3849;
	private static final int DELU_FANG = 3850;
	private static final int STAKATO_TALONS = 3851;
	private static final int SOPHIAS_LETTER1 = 3671;
	private static final int SOPHIAS_LETTER2 = 3672;
	private static final int SOPHIAS_LETTER3 = 3673;
	private static final int SOPHIAS_LETTER4 = 3674;

	//Rewards
	private static final int LIONS_CLAW = 3675;
	private static final int LIONS_EYE = 3676;
	private static final int GUILD_COIN = 3677;
	private static final int COMPLETE_STATUE = 3461;
	private static final int COMPLETE_TABLET = 3466;
	private static final int ALACRITY_POTION = 735;
	private static final int SCROLL_ESCAPE = 736;
	private static final int SOULSHOT_D = 1463;
	private static final int SPIRITSHOT_D = 2510;
	private static final int HEALING_POTION = 1061;

	//Price to Open a Box
	private static final int OPEN_BOX_PRICE = 650;

	//Box rewards
	private static final int GLUDIO_APPLE = 3444;
	private static final int CORN_MEAL = 3445;
	private static final int WOLF_PELTS = 3446;
	private static final int MONNSTONE = 3447;
	private static final int GLUDIO_WEETS_FLOWER = 3448;
	private static final int SPIDERSILK_ROPE = 3449;
	private static final int ALEXANDRIT = 3450;
	private static final int SILVER_TEA = 3451;
	private static final int GOLEM_PART = 3452;
	private static final int FIRE_EMERALD = 3453;
	private static final int SILK_FROCK = 3454;
	private static final int PORCELAN_URN = 3455;
	private static final int IMPERIAL_DIAMOND = 3456;
	private static final int STATUE_SHILIEN_HEAD = 3457;
	private static final int STATUE_SHILIEN_TORSO = 3458;
	private static final int STATUE_SHILIEN_ARM = 3459;
	private static final int STATUE_SHILIEN_LEG = 3460;
	private static final int FRAGMENT_ANCIENT_TABLE1 = 3462;
	private static final int FRAGMENT_ANCIENT_TABLE2 = 3463;
	private static final int FRAGMENT_ANCIENT_TABLE3 = 3464;
	private static final int FRAGMENT_ANCIENT_TABLE4 = 3465;

	//List of all NPCs this Quest: Sophya, Redfoot, Rupio, Undrias(Shilien Temple),Lockirin(Dwarfen Village),Morgan
	private static final int[] NPC = {30735, 30736, 30471, 30130, 30531, 30737};

	//List for some Item Groups
	private int[] statue_list = {STATUE_SHILIEN_HEAD, STATUE_SHILIEN_TORSO, STATUE_SHILIEN_ARM, STATUE_SHILIEN_LEG};
	private int[] tablet_list = {FRAGMENT_ANCIENT_TABLE1, FRAGMENT_ANCIENT_TABLE2, FRAGMENT_ANCIENT_TABLE3, FRAGMENT_ANCIENT_TABLE4};

	//DROPLIST [npcId, questPart, dropCount, itemIdChance, BoxChance, partItem]
	//--questPart, the Quest has 4 Parts 1=Execution Ground, 2=Fortress of Resistance 3=Near Giran Town, Delu Lizzards 4=Cruma Tower Area.
	//--itemIdChance --> set the dropchance for Ash in % for the mob with the npcId in same Line.
	//--BoxChance --> set the dropchance for Boxes in % to the mob with the npcId in same Line.
	//--partItem --> this defines which Item should this Mob drop, because 4 Parts.. 4 Different Items.
	private static final int[][] DROPLIST = {
			//Execturion Ground - Part 1
			{20160, 1, 1, 67, 29, UNDEAD_ASH}, //Neer Crawler
			{20171, 1, 1, 76, 31, UNDEAD_ASH}, //Specter
			{20197, 1, 1, 89, 25, UNDEAD_ASH}, //Sorrow Maiden
			{20200, 1, 1, 60, 28, UNDEAD_ASH}, //Strain
			{20201, 1, 1, 70, 29, UNDEAD_ASH}, //Ghoul
			{20202, 1, 0, 60, 24, UNDEAD_ASH}, //Dead Seeker (not official Monster for this Quest)
			{20198, 1, 1, 60, 35, UNDEAD_ASH}, //Neer Ghoul Berserker
			//Fortress of Resistance - Part 2
			{20207, 2, 1, 69, 29, BLOODY_AXE_INSIGNIAS}, //Ol Mahum Guerilla
			{20208, 2, 1, 67, 32, BLOODY_AXE_INSIGNIAS}, //Ol Mahum Raider
			{20209, 2, 1, 62, 33, BLOODY_AXE_INSIGNIAS}, //Ol Mahum Marksman
			{20210, 2, 1, 78, 23, BLOODY_AXE_INSIGNIAS}, //Ol Mahum Sergeant
			{20211, 2, 1, 71, 22, BLOODY_AXE_INSIGNIAS}, //Ol Mahum Captain
			//Delu Lizzardmans near Giran - Part 3
			{20251, 3, 1, 70, 30, DELU_FANG}, //Delu Lizardman
			{20252, 3, 1, 67, 28, DELU_FANG}, //Delu Lizardman Scout
			{20253, 3, 1, 65, 26, DELU_FANG}, //Delu Lizardman Warrior
			{27151, 3, 1, 69, 31, DELU_FANG}, //Delu Lizardman Headhunter
			//Cruma Area - Part 4
			{20157, 4, 1, 66, 32, STAKATO_TALONS}, //Marsh Stakato
			{20230, 4, 1, 68, 26, STAKATO_TALONS}, //Marsh Stakato Worker
			{20232, 4, 1, 67, 28, STAKATO_TALONS}, //Marsh Stakato Soldier
			{20234, 4, 1, 69, 32, STAKATO_TALONS}, //Marsh Stakato Drone
			{27152, 4, 8, 69, 32, STAKATO_TALONS} //Marsh Stakato Marquess
	};

	public _333_BlackLionHunt()
	{
		super(333, "_333_BlackLionHunt", "Hunt of the Black Lion");
		addStartNpc(NPC[0]);
		addTalkId(NPC);

		for(int[] npc : DROPLIST)
			addKillId(npc[0]);

		addQuestItem(CARGO_BOX1,
				UNDEAD_ASH,
				BLOODY_AXE_INSIGNIAS,
				DELU_FANG,
				STAKATO_TALONS,
				SOPHIAS_LETTER1,
				SOPHIAS_LETTER2,
				SOPHIAS_LETTER3,
				SOPHIAS_LETTER4,
				LIONS_CLAW,
				LIONS_EYE,
				GUILD_COIN);
	}

	public void giveRewards(QuestState st, int item, long count)
	{
		st.rollAndGive(ADENA_ID, 35 * count, 100);
		st.takeItems(item, count);
		if(count < 20)
			return;
		if(count < 50)
			st.giveItems(LIONS_CLAW, 1);
		else if(count < 100)
			st.giveItems(LIONS_CLAW, 2);
		else
			st.giveItems(LIONS_CLAW, 3);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		int part = st.getInt("part");
		if(event.equalsIgnoreCase("start"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			return "30735-01.htm";
		}
		else if(event.equalsIgnoreCase("p1_t"))
		{
			st.set("part", "1");
			st.giveItems(SOPHIAS_LETTER1, 1);
			return "30735-02.htm";
		}
		else if(event.equalsIgnoreCase("p2_t"))
		{
			st.set("part", "2");
			st.giveItems(SOPHIAS_LETTER2, 1);
			return "30735-03.htm";
		}
		else if(event.equalsIgnoreCase("p3_t"))
		{
			st.set("part", "3");
			st.giveItems(SOPHIAS_LETTER3, 1);
			return "30735-04.htm";
		}
		else if(event.equalsIgnoreCase("p4_t"))
		{
			st.set("part", "4");
			st.giveItems(SOPHIAS_LETTER4, 1);
			return "30735-05.htm";
		}
		else if(event.equalsIgnoreCase("exit"))
		{
			st.takeItems(BLACK_LION_MARK, 1);
			st.exitCurrentQuest(true);
			return "30735-exit.htm";
		}
		else if(event.equalsIgnoreCase("continue"))
		{
			long claw = st.getQuestItemsCount(LIONS_CLAW) / 10;
			long check_eye = st.getQuestItemsCount(LIONS_EYE);
			if(claw > 0)
			{
				st.giveItems(LIONS_EYE, claw);
				long eye = st.getQuestItemsCount(LIONS_EYE);
				st.takeItems(LIONS_CLAW, claw * 10);
				int ala_count = 3;
				int soul_count = 100;
				int soe_count = 20;
				int heal_count = 20;
				int spir_count = 50;
				if(eye > 9)
				{
					ala_count = 4;
					soul_count = 400;
					soe_count = 30;
					heal_count = 50;
					spir_count = 200;
				}
				else if(eye > 4)
				{
					spir_count = 100;
					soul_count = 200;
					heal_count = 25;
				}
				while(claw > 0)
				{
					int n = Rnd.get(1, 5);
					if(n < 1)
						st.rollAndGive(ALACRITY_POTION, ala_count, 100);
					else if(n < 2)
						st.rollAndGive(SOULSHOT_D, soul_count, 100);
					else if(n < 3)
						st.rollAndGive(SCROLL_ESCAPE, soe_count, 100);
					else if(n < 4)
						st.rollAndGive(SPIRITSHOT_D, spir_count, 100);
					else if(n == 4)
						st.rollAndGive(HEALING_POTION, heal_count, 100);
					claw -= 1;
				}
				if(check_eye > 0)
					return "30735-06.htm";
				return "30735-06.htm";
			}
			return "30735-start.htm";
		}
		else if(event.equalsIgnoreCase("leave"))
		{
			int order;
			if(part == 1)
				order = SOPHIAS_LETTER1;
			else if(part == 2)
				order = SOPHIAS_LETTER2;
			else if(part == 3)
				order = SOPHIAS_LETTER3;
			else if(part == 4)
				order = SOPHIAS_LETTER4;
			else
				order = 0;
			st.set("part", "0");
			if(order > 0)
				st.takeItems(order, 1);
			return "30735-07.htm";
		}
		else if(event.equalsIgnoreCase("f_info"))
		{
			int text = st.getInt("text");
			if(text < 4)
			{
				st.set("text", String.valueOf(text + 1));
				return "red_foor_text_" + Rnd.get(1, 19) + ".htm";
			}
			return "red_foor-01.htm";
		}
		else if(event.equalsIgnoreCase("f_give"))
		{
			if(st.getQuestItemsCount(CARGO_BOX1) > 0)
			{
				if(st.getQuestItemsCount(ADENA_ID) >= OPEN_BOX_PRICE)
				{
					st.takeItems(CARGO_BOX1, 1);
					st.takeItems(ADENA_ID, OPEN_BOX_PRICE);
					int rand = Rnd.get(162);
					if(rand < 21)
					{
						st.giveItems(GLUDIO_APPLE, 1);
						return "red_foor-02.htm";
					}
					else if(rand < 41)
					{
						st.giveItems(CORN_MEAL, 1);
						return "red_foor-03.htm";
					}
					else if(rand < 61)
					{
						st.giveItems(WOLF_PELTS, 1);
						return "red_foor-04.htm";
					}
					else if(rand < 74)
					{
						st.giveItems(MONNSTONE, 1);
						return "red_foor-05.htm";
					}
					else if(rand < 86)
					{
						st.giveItems(GLUDIO_WEETS_FLOWER, 1);
						return "red_foor-06.htm";
					}
					else if(rand < 98)
					{
						st.giveItems(SPIDERSILK_ROPE, 1);
						return "red_foor-07.htm";
					}
					else if(rand < 99)
					{
						st.giveItems(ALEXANDRIT, 1);
						return "red_foor-08.htm";
					}
					else if(rand < 109)
					{
						st.giveItems(SILVER_TEA, 1);
						return "red_foor-09.htm";
					}
					else if(rand < 119)
					{
						st.giveItems(GOLEM_PART, 1);
						return "red_foor-10.htm";
					}
					else if(rand < 123)
					{
						st.giveItems(FIRE_EMERALD, 1);
						return "red_foor-11.htm";
					}
					else if(rand < 127)
					{
						st.giveItems(SILK_FROCK, 1);
						return "red_foor-12.htm";
					}
					else if(rand < 131)
					{
						st.giveItems(PORCELAN_URN, 1);
						return "red_foor-13.htm";
					}
					else if(rand < 132)
					{
						st.giveItems(IMPERIAL_DIAMOND, 1);
						return "red_foor-13.htm";
					}
					else if(rand < 147)
					{
						int random_stat = Rnd.get(4);
						if(random_stat == 0)
						{
							st.giveItems(STATUE_SHILIEN_HEAD, 1);
							return "red_foor-14.htm";
						}
						else if(random_stat == 1)
						{
							st.giveItems(STATUE_SHILIEN_TORSO, 1);
							return "red_foor-14.htm";
						}
						else if(random_stat == 2)
						{
							st.giveItems(STATUE_SHILIEN_ARM, 1);
							return "red_foor-14.htm";
						}
						else if(random_stat == 3)
						{
							st.giveItems(STATUE_SHILIEN_LEG, 1);
							return "red_foor-14.htm";
						}
					}
					else if(rand < 162)
					{
						int random_tab = Rnd.get(4);
						if(random_tab == 0)
						{
							st.giveItems(FRAGMENT_ANCIENT_TABLE1, 1);
							return "red_foor-15.htm";
						}
						else if(random_tab == 1)
						{
							st.giveItems(FRAGMENT_ANCIENT_TABLE2, 1);
							return "red_foor-15.htm";
						}
						else if(random_tab == 2)
						{
							st.giveItems(FRAGMENT_ANCIENT_TABLE3, 1);
							return "red_foor-15.htm";
						}
						else if(random_tab == 3)
						{
							st.giveItems(FRAGMENT_ANCIENT_TABLE4, 1);
							return "red_foor-15.htm";
						}
					}
				}
				else
					return "red_foor-no_adena.htm";
			}
			else
				return "red_foor-no_box.htm";
		}
		else if(event.equalsIgnoreCase("r_give_statue") || event.equalsIgnoreCase("r_give_tablet"))
		{
			int[] items = statue_list;
			int item = COMPLETE_STATUE;
			String pieces = "rupio-01.htm";
			String brockes = "rupio-02.htm";
			String complete = "rupio-03.htm";
			if(event.equalsIgnoreCase("r_give_tablet"))
			{
				items = tablet_list;
				item = COMPLETE_TABLET;
				pieces = "rupio-04.htm";
				brockes = "rupio-05.htm";
				complete = "rupio-06.htm";
			}
			int count = 0;
			for(int id : items)
				if(st.getQuestItemsCount(id) > 0)
					count += 1;
			if(count > 3)
			{
				for(int id : items)
					st.takeItems(id, 1);
				if(Rnd.chance(50))
				{
					st.giveItems(item, 1);
					return complete;
				}
				return brockes;
			}
			else if(count < 4 && count > 0)
				return pieces;
			else
				return "rupio-07.htm";
		}
		else if(event.equalsIgnoreCase("l_give"))
		{
			if(st.getQuestItemsCount(COMPLETE_TABLET) > 0)
			{
				st.takeItems(COMPLETE_TABLET, 1);
				st.rollAndGive(ADENA_ID, 30000, 100);
				return "lockirin-01.htm";
			}
			return "lockirin-02.htm";
		}
		else if(event.equalsIgnoreCase("u_give"))
		{
			if(st.getQuestItemsCount(COMPLETE_STATUE) > 0)
			{
				st.takeItems(COMPLETE_STATUE, 1);
				st.rollAndGive(ADENA_ID, 30000, 100);
				return "undiras-01.htm";
			}
			return "undiras-02.htm";
		}
		else if(event.equalsIgnoreCase("m_give"))
		{
			if(st.getQuestItemsCount(CARGO_BOX1) > 0)
			{
				long coins = st.getQuestItemsCount(GUILD_COIN);
				long count = coins / 40;
				if(count > 2)
					count = 2;
				st.giveItems(GUILD_COIN, 1);
				st.rollAndGive(ADENA_ID, (1 + count) * 100, 100);
				st.takeItems(CARGO_BOX1, 1);
				int rand = Rnd.get(0, 3);
				if(rand == 0)
					return "morgan-01.htm";
				else if(rand == 1)
					return "morgan-02.htm";
				else
					return "morgan-02.htm";
			}
			return "morgan-03.htm";
		}
		else if(event.equalsIgnoreCase("start_parts"))
			return "30735-08.htm";
		else if(event.equalsIgnoreCase("m_reward"))
			return "morgan-05.htm";
		else if(event.equalsIgnoreCase("u_info"))
			return "undiras-03.htm";
		else if(event.equalsIgnoreCase("l_info"))
			return "lockirin-03.htm";
		else if(event.equalsIgnoreCase("p_redfoot"))
			return "30735-09.htm";
		else if(event.equalsIgnoreCase("p_trader_info"))
			return "30735-10.htm";
		else if(event.equalsIgnoreCase("start_chose_parts"))
			return "30735-11.htm";
		else if(event.equalsIgnoreCase("p1_explanation"))
			return "30735-12.htm";
		else if(event.equalsIgnoreCase("p2_explanation"))
			return "30735-13.htm";
		else if(event.equalsIgnoreCase("p3_explanation"))
			return "30735-14.htm";
		else if(event.equalsIgnoreCase("p4_explanation"))
			return "30735-15.htm";
		else if(event.equalsIgnoreCase("f_more_help"))
			return "red_foor-16.htm";
		else if(event.equalsIgnoreCase("r_exit"))
			return "30735-16.htm";
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(st.isCreated() && npcId == NPC[0])
		{
			st.set("part", "0");
			st.set("text", "0");
			if(st.getQuestItemsCount(BLACK_LION_MARK) > 0)
			{
				if(st.getPlayer().getLevel() > 24)
					return "30735-17.htm";
				st.exitCurrentQuest(true);
				return "30735-18.htm";
			}
			st.exitCurrentQuest(true);
			return "30735-19.htm";
		}
		else if(st.isStarted() && st.getInt("cond") > 0)
		{
			int part = st.getInt("part");
			if(npcId == NPC[0])
			{
				int item;
				if(part == 1)
					item = UNDEAD_ASH;
				else if(part == 2)
					item = BLOODY_AXE_INSIGNIAS;
				else if(part == 3)
					item = DELU_FANG;
				else if(part == 4)
					item = STAKATO_TALONS;
				else
					return "30735-20.htm";
				long count = st.getQuestItemsCount(item);
				long box = st.getQuestItemsCount(CARGO_BOX1);
				if(box > 0 && count > 0)
				{
					giveRewards(st, item, count);
					return "30735-21.htm";
				}
				else if(box > 0)
					return "30735-22.htm";
				else if(count > 0)
				{
					giveRewards(st, item, count);
					return "30735-23.htm";
				}
				else
					return "30735-24.htm";
			}
			else if(npcId == NPC[1])
			{
				if(st.getQuestItemsCount(CARGO_BOX1) > 0)
					return "red_foor_text_20.htm";
				return "red_foor_text_21.htm";
			}
			else if(npcId == NPC[2])
			{
				int count = 0;
				for(int i = 3457; i <= 3460; i++)
					if(st.getQuestItemsCount(i) > 0)
						count += 1;
				for(int i = 3462; i <= 3465; i++)
					if(st.getQuestItemsCount(i) > 0)
						count += 1;
				if(count > 0)
					return "rupio-08.htm";
				return "rupio-07.htm";
			}
			else if(npcId == NPC[3])
			{
				if(st.getQuestItemsCount(COMPLETE_STATUE) > 0)
					return "undiras-04.htm";
				int count = 0;
				int i;
				for(i = 3457; i <= 3460; i++)
					if(st.getQuestItemsCount(i) > 0)
						count += 1;
				if(count > 0)
					return "undiras-05.htm";
				return "undiras-02.htm";
			}
			else if(npcId == NPC[4])
			{
				if(st.getQuestItemsCount(COMPLETE_TABLET) > 0)
					return "lockirin-04.htm";
				int count = 0;
				int i;
				for(i = 3462; i <= 3465; i++)
					if(st.getQuestItemsCount(i) > 0)
						count += 1;
				if(count > 0)
					return "lockirin-05.htm";
				return "lockirin-06.htm";
			}
			else if(npcId == NPC[5])
			{
				if(st.getQuestItemsCount(CARGO_BOX1) > 0)
					return "morgan-06.htm";
				return "morgan-07.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		//DROPLIST [npcId, questPart, dropCount, itemIdChance, BoxChance, partItem]
		for(int[] aDROPLIST : DROPLIST)
			if(npcId == aDROPLIST[0] && st.getInt("part") == aDROPLIST[1] && aDROPLIST[2] > 0)
			{
				st.rollAndGive(aDROPLIST[5], aDROPLIST[2], aDROPLIST[3]);
				st.rollAndGive(CARGO_BOX1, 1, aDROPLIST[4]);
			}

		// Delu Lizardman, Delu Lizardman Scout, Delu Lizardman Warrior
		if(Rnd.chance(4) && (npcId == 20251 || npcId == 20252 || npcId == 20253))
		{
			// Delu Lizardman Headhunter
			st.getPcSpawn().addSpawn(21105);
			st.getPcSpawn().addSpawn(21105);
		}

		// Marsh Stakato, Marsh Stakato Worker, Marsh Stakato Soldier, Marsh Stakato Drone
		if(npcId == 20157 || npcId == 20230 || npcId == 20232 || npcId == 20234)
		{
			// Marsh Stakato Marquess
			if(Rnd.chance(2))
				st.getPcSpawn().addSpawn(27152);
			// Cargo Box
			//if(Rnd.chance(15)) //непонятная трава, убрал пока
			//	st.giveItems(CARGO_BOX4, 1);
		}
	}
}
