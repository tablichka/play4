package quests._232_TestOfLord;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _232_TestOfLord extends Quest
{
	// NPCs
	private static int Somak = 30510;
	private static int Manakia = 30515;
	private static int Jakal = 30558;
	private static int Sumari = 30564;
	private static int Kakai = 30565;
	private static int Varkees = 30566;
	private static int Tantus = 30567;
	private static int Hatos = 30568;
	private static int Takuna = 30641;
	private static int Chianta = 30642;
	private static int First_Orc = 30643;
	private static int Ancestor_Martankus = 30649;
	// Mobs
	private static int Marsh_Spider = 20233;
	private static int Breka_Orc_Shaman = 20269;
	private static int Breka_Orc_Overlord = 20270;
	private static int Enchanted_Monstereye = 20564;
	private static int Timak_Orc = 20583;
	private static int Timak_Orc_Archer = 20584;
	private static int Timak_Orc_Soldier = 20585;
	private static int Timak_Orc_Warrior = 20586;
	private static int Timak_Orc_Shaman = 20587;
	private static int Timak_Orc_Overlord = 20588;
	private static int Ragna_Orc_Overlord = 20778;
	private static int Ragna_Orc_Seer = 20779;
	// Items
	private static int ADENA = 57;
	private static int MARK_OF_LORD = 3390;
	private static int BONE_ARROW = 1341;
	private static int Dimensional_Diamond = 7562;
	// Quest Items (Drop)
	private static int TIMAK_ORC_SKULL = 3403;
	private static int BREKA_ORC_FANG = 3398;
	private static int RAGNA_ORC_HEAD = 3414;
	private static int RAGNA_CHIEF_NOTICE = 3415;
	private static int MARSH_SPIDER_FEELER = 3407;
	private static int MARSH_SPIDER_FEET = 3408;
	private static int CORNEA_OF_EN_MONSTEREYE = 3410;
	// Quest Items
	private static int ORDEAL_NECKLACE = 3391;
	private static int VARKEES_CHARM = 3392;
	private static int TANTUS_CHARM = 3393;
	private static int HATOS_CHARM = 3394;
	private static int TAKUNA_CHARM = 3395;
	private static int CHIANTA_CHARM = 3396;
	private static int MANAKIAS_ORDERS = 3397;
	private static int MANAKIAS_AMULET = 3399;
	private static int HUGE_ORC_FANG = 3400;
	private static int SUMARIS_LETTER = 3401;
	private static int URUTU_BLADE = 3402;
	private static int SWORD_INTO_SKULL = 3404;
	private static int NERUGA_AXE_BLADE = 3405;
	private static int AXE_OF_CEREMONY = 3406;
	private static int HANDIWORK_SPIDER_BROOCH = 3409;
	private static int MONSTEREYE_WOODCARVING = 3411;
	private static int BEAR_FANG_NECKLACE = 3412;
	private static int MARTANKUS_CHARM = 3413;
	private static int IMMORTAL_FLAME = 3416;

	public _232_TestOfLord()
	{
		super(232, "_232_TestOfLord", "Test Of Lord");
		addStartNpc(Kakai);

		addTalkId(Somak);
		addTalkId(Manakia);
		addTalkId(Jakal);
		addTalkId(Sumari);
		addTalkId(Varkees);
		addTalkId(Tantus);
		addTalkId(Hatos);
		addTalkId(Takuna);
		addTalkId(Chianta);
		addTalkId(First_Orc);
		addTalkId(Ancestor_Martankus);


		addQuestItem(TIMAK_ORC_SKULL, BREKA_ORC_FANG, RAGNA_ORC_HEAD, RAGNA_CHIEF_NOTICE, MARSH_SPIDER_FEELER, MARSH_SPIDER_FEET, CORNEA_OF_EN_MONSTEREYE);

		addKillId(Timak_Orc, Timak_Orc_Archer, Timak_Orc_Soldier, Timak_Orc_Warrior, Timak_Orc_Shaman, Timak_Orc_Overlord,
				Breka_Orc_Shaman, Breka_Orc_Overlord, Ragna_Orc_Overlord, Ragna_Orc_Seer, Marsh_Spider, Enchanted_Monstereye);

		addQuestItem(ORDEAL_NECKLACE, VARKEES_CHARM, TANTUS_CHARM,
				HATOS_CHARM, TAKUNA_CHARM, CHIANTA_CHARM,
				MANAKIAS_ORDERS, MANAKIAS_AMULET, HUGE_ORC_FANG,
				SUMARIS_LETTER, URUTU_BLADE, SWORD_INTO_SKULL,
				NERUGA_AXE_BLADE, AXE_OF_CEREMONY, HANDIWORK_SPIDER_BROOCH,
				MONSTEREYE_WOODCARVING, BEAR_FANG_NECKLACE, MARTANKUS_CHARM, IMMORTAL_FLAME);
	}

	private static void spawn_First_Orc(QuestState st)
	{
		st.getPcSpawn().removeAllSpawn();
		st.getPcSpawn().addSpawn(First_Orc, 21036, -107690, -3038);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30565-05.htm") && st.isCreated())
		{
			st.giveItems(ORDEAL_NECKLACE, 1);
			if(!st.getPlayer().getVarB("dd3"))
			{
				st.giveItems(Dimensional_Diamond, 64);
				st.getPlayer().setVar("dd3", "1");
			}
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30565-12.htm") && st.isStarted() && st.getQuestItemsCount(IMMORTAL_FLAME) > 0)
		{
			st.takeItems(IMMORTAL_FLAME, -1);
			st.giveItems(MARK_OF_LORD, 1);
			if(!st.getPlayer().getVarB("q232"))
			{
				st.addExpAndSp(447444, 30704);
				st.rollAndGive(57, 80903, 100);
				st.getPlayer().setVar("q232", "1");
			}
			st.playSound(SOUND_FINISH);
			st.unset("cond");
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30565-08.htm") && st.isStarted())
		{
			st.takeItems(SWORD_INTO_SKULL, -1);
			st.takeItems(AXE_OF_CEREMONY, -1);
			st.takeItems(MONSTEREYE_WOODCARVING, -1);
			st.takeItems(HANDIWORK_SPIDER_BROOCH, -1);
			st.takeItems(ORDEAL_NECKLACE, -1);
			st.takeItems(HUGE_ORC_FANG, -1);
			st.giveItems(BEAR_FANG_NECKLACE, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30566-02.htm") && st.isStarted())
			st.giveItems(VARKEES_CHARM, 1);
		else if(event.equalsIgnoreCase("30567-02.htm") && st.isStarted())
			st.giveItems(TANTUS_CHARM, 1);
		else if(event.equalsIgnoreCase("30558-02.htm") && st.isStarted() && st.getQuestItemsCount(ADENA) >= 1000)
		{
			st.takeItems(ADENA, 1000);
			st.giveItems(NERUGA_AXE_BLADE, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30568-02.htm") && st.isStarted())
			st.giveItems(HATOS_CHARM, 1);
		else if(event.equalsIgnoreCase("30641-02.htm") && st.isStarted())
			st.giveItems(TAKUNA_CHARM, 1);
		else if(event.equalsIgnoreCase("30642-02.htm") && st.isStarted())
			st.giveItems(CHIANTA_CHARM, 1);
		else if(event.equalsIgnoreCase("30649-04.htm") && st.isStarted() && st.getQuestItemsCount(BEAR_FANG_NECKLACE) > 0)
		{
			st.takeItems(BEAR_FANG_NECKLACE, -1);
			st.giveItems(MARTANKUS_CHARM, 1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30649-07.htm") && st.isStarted())
			spawn_First_Orc(st);

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.getQuestItemsCount(MARK_OF_LORD) > 0)
		{
			st.exitCurrentQuest(true);
			return "completed";
		}
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != Kakai)
				return "noquest";
			if(st.getPlayer().getRace().ordinal() != 3)
			{
				st.exitCurrentQuest(true);
				return "30565-01.htm";
			}
			if(st.getPlayer().getClassId().getId() != 0x32)
			{
				st.exitCurrentQuest(true);
				return "30565-02.htm";
			}
			if(st.getPlayer().getLevel() < 39)
			{
				st.exitCurrentQuest(true);
				return "30565-03.htm";
			}
			st.set("cond", "0");
			return "30565-04.htm";
		}

		if(!st.isStarted() || st.getInt("cond") != 1)
			return "noquest";

		long ORDEAL_NECKLACE_COUNT = st.getQuestItemsCount(ORDEAL_NECKLACE);
		long HUGE_ORC_FANG_COUNT = st.getQuestItemsCount(HUGE_ORC_FANG);
		long SWORD_INTO_SKULL_COUNT = st.getQuestItemsCount(SWORD_INTO_SKULL);
		long AXE_OF_CEREMONY_COUNT = st.getQuestItemsCount(AXE_OF_CEREMONY);
		long MONSTEREYE_WOODCARVING_COUNT = st.getQuestItemsCount(MONSTEREYE_WOODCARVING);
		long HANDIWORK_SPIDER_BROOCH_COUNT = st.getQuestItemsCount(HANDIWORK_SPIDER_BROOCH);
		long BEAR_FANG_NECKLACE_COUNT = st.getQuestItemsCount(BEAR_FANG_NECKLACE);
		long MARTANKUS_CHARM_COUNT = st.getQuestItemsCount(MARTANKUS_CHARM);
		long IMMORTAL_FLAME_COUNT = st.getQuestItemsCount(IMMORTAL_FLAME);
		long VARKEES_CHARM_COUNT = st.getQuestItemsCount(VARKEES_CHARM);
		long MANAKIAS_AMULET_COUNT = st.getQuestItemsCount(MANAKIAS_AMULET);
		long MANAKIAS_ORDERS_COUNT = st.getQuestItemsCount(MANAKIAS_ORDERS);
		long BREKA_ORC_FANG_COUNT = st.getQuestItemsCount(BREKA_ORC_FANG);
		long TANTUS_CHARM_COUNT = st.getQuestItemsCount(TANTUS_CHARM);
		long NERUGA_AXE_BLADE_COUNT = st.getQuestItemsCount(NERUGA_AXE_BLADE);
		long HATOS_CHARM_COUNT = st.getQuestItemsCount(HATOS_CHARM);
		long URUTU_BLADE_COUNT = st.getQuestItemsCount(URUTU_BLADE);
		long TIMAK_ORC_SKULL_COUNT = st.getQuestItemsCount(TIMAK_ORC_SKULL);
		long SUMARIS_LETTER_COUNT = st.getQuestItemsCount(SUMARIS_LETTER);
		long TAKUNA_CHARM_COUNT = st.getQuestItemsCount(TAKUNA_CHARM);

		if(npcId == Kakai)
		{
			if(ORDEAL_NECKLACE_COUNT > 0)
				return HUGE_ORC_FANG_COUNT > 0 && SWORD_INTO_SKULL_COUNT > 0 && AXE_OF_CEREMONY_COUNT > 0 && MONSTEREYE_WOODCARVING_COUNT > 0 && HANDIWORK_SPIDER_BROOCH_COUNT > 0 ? "30565-07.htm" : "30565-06.htm";
			if(BEAR_FANG_NECKLACE_COUNT > 0)
				return "30565-09.htm";
			if(MARTANKUS_CHARM_COUNT > 0)
				return "30565-10.htm";
			if(IMMORTAL_FLAME_COUNT > 0)
				return "30565-11.htm";
		}

		if(npcId == Varkees && ORDEAL_NECKLACE_COUNT > 0)
		{
			if(HUGE_ORC_FANG_COUNT > 0)
				return "30566-05.htm";
			if(VARKEES_CHARM_COUNT == 0)
				return "30566-01.htm";
			if(MANAKIAS_AMULET_COUNT == 0)
				return "30566-03.htm";
			st.takeItems(VARKEES_CHARM, -1);
			st.takeItems(MANAKIAS_AMULET, -1);
			st.giveItems(HUGE_ORC_FANG, 1);
			st.playSound(SOUND_MIDDLE);
			return "30566-04.htm";
		}

		if(npcId == Manakia && ORDEAL_NECKLACE_COUNT > 0)
			if(VARKEES_CHARM_COUNT > 0 && HUGE_ORC_FANG_COUNT == 0)
			{
				if(MANAKIAS_AMULET_COUNT == 0)
				{
					if(MANAKIAS_ORDERS_COUNT == 0)
					{
						st.giveItems(MANAKIAS_ORDERS, 1);
						return "30515-01.htm";
					}
					if(BREKA_ORC_FANG_COUNT < 20)
						return "30515-02.htm";
					st.takeItems(MANAKIAS_ORDERS, -1);
					st.takeItems(BREKA_ORC_FANG, -1);
					st.giveItems(MANAKIAS_AMULET, 1);
					st.playSound(SOUND_MIDDLE);
					return "30515-03.htm";
				}
				else if(MANAKIAS_ORDERS_COUNT == 0)
					return "30515-04.htm";
			}
			else if(VARKEES_CHARM_COUNT == 0 && HUGE_ORC_FANG_COUNT > 0 && MANAKIAS_AMULET_COUNT == 0 && MANAKIAS_ORDERS_COUNT == 0)
				return "30515-05.htm";

		if(npcId == Tantus)
		{
			if(AXE_OF_CEREMONY_COUNT == 0)
			{
				if(TANTUS_CHARM_COUNT == 0)
					return "30567-01.htm";
				if(NERUGA_AXE_BLADE_COUNT == 0 || st.getQuestItemsCount(BONE_ARROW) < 1000)
					return "30567-03.htm";
				st.takeItems(TANTUS_CHARM, -1);
				st.takeItems(NERUGA_AXE_BLADE, -1);
				st.takeItems(BONE_ARROW, 1000);
				st.giveItems(AXE_OF_CEREMONY, 1);
				st.playSound(SOUND_MIDDLE);
				return "30567-04.htm";
			}
			if(TANTUS_CHARM_COUNT == 0)
				return "30567-05.htm";
		}

		if(npcId == Jakal)
		{
			if(TANTUS_CHARM_COUNT > 0 && AXE_OF_CEREMONY_COUNT == 0)
			{
				if(NERUGA_AXE_BLADE_COUNT > 0)
					return "30558-04.htm";
				return st.getQuestItemsCount(ADENA) < 1000 ? "30558-03.htm" : "30558-01.htm";
			}
			if(TANTUS_CHARM_COUNT == 0 && AXE_OF_CEREMONY_COUNT > 0)
				return "30558-05.htm";
		}

		if(npcId == Hatos)
		{
			if(SWORD_INTO_SKULL_COUNT == 0)
			{
				if(HATOS_CHARM_COUNT == 0)
					return "30568-01.htm";
				if(URUTU_BLADE_COUNT == 0 || TIMAK_ORC_SKULL_COUNT < 10)
					return "30568-03.htm";
				st.takeItems(HATOS_CHARM, -1);
				st.takeItems(URUTU_BLADE, -1);
				st.takeItems(TIMAK_ORC_SKULL, -1);
				st.giveItems(SWORD_INTO_SKULL, 1);
				st.playSound(SOUND_MIDDLE);
				return "30568-04.htm";
			}
			if(HATOS_CHARM_COUNT == 0)
				return "30568-05.htm";
		}

		if(npcId == Sumari)
			if(HATOS_CHARM_COUNT > 0 && SWORD_INTO_SKULL_COUNT == 0)
			{
				if(URUTU_BLADE_COUNT == 0)
				{
					if(SUMARIS_LETTER_COUNT > 0)
						return "30564-02.htm";
					st.giveItems(SUMARIS_LETTER, 1);
					st.playSound(SOUND_MIDDLE);
					return "30564-01.htm";
				}
				else if(SUMARIS_LETTER_COUNT == 0)
					return "30564-03.htm";
			}
			else if(HATOS_CHARM_COUNT == 0 && SWORD_INTO_SKULL_COUNT > 0 && URUTU_BLADE_COUNT == 0 && SUMARIS_LETTER_COUNT == 0)
				return "30564-04.htm";

		if(npcId == Somak)
			if(SWORD_INTO_SKULL_COUNT == 0)
			{
				if(URUTU_BLADE_COUNT == 0 && HATOS_CHARM_COUNT > 0 && SUMARIS_LETTER_COUNT > 0)
				{
					st.takeItems(SUMARIS_LETTER, -1);
					st.giveItems(URUTU_BLADE, 1);
					st.playSound(SOUND_MIDDLE);
					return "30510-01.htm";
				}
				if(URUTU_BLADE_COUNT > 0 && HATOS_CHARM_COUNT > 0 && SUMARIS_LETTER_COUNT == 0)
					return "30510-02.htm";
			}
			else if(URUTU_BLADE_COUNT == 0 && HATOS_CHARM_COUNT == 0 && SUMARIS_LETTER_COUNT == 0)
				return "30510-03.htm";

		if(npcId == Takuna)
		{
			if(HANDIWORK_SPIDER_BROOCH_COUNT == 0)
			{
				if(TAKUNA_CHARM_COUNT == 0)
					return "30641-01.htm";
				if(st.getQuestItemsCount(MARSH_SPIDER_FEELER) < 10 || st.getQuestItemsCount(MARSH_SPIDER_FEET) < 10)
					return "30641-03.htm";
				st.takeItems(MARSH_SPIDER_FEELER, -1);
				st.takeItems(MARSH_SPIDER_FEET, -1);
				st.takeItems(TAKUNA_CHARM, -1);
				st.giveItems(HANDIWORK_SPIDER_BROOCH, 1);
				st.playSound(SOUND_MIDDLE);
				return "30641-04.htm";
			}
			if(TAKUNA_CHARM_COUNT == 0)
				return "30641-05.htm";
		}

		if(npcId == Chianta)
		{
			long CHIANTA_CHARM_COUNT = st.getQuestItemsCount(CHIANTA_CHARM);
			if(MONSTEREYE_WOODCARVING_COUNT == 0)
			{
				if(CHIANTA_CHARM_COUNT == 0)
					return "30642-01.htm";
				if(st.getQuestItemsCount(CORNEA_OF_EN_MONSTEREYE) < 20)
					return "30642-03.htm";
				st.takeItems(CORNEA_OF_EN_MONSTEREYE, -1);
				st.takeItems(CHIANTA_CHARM, -1);
				st.giveItems(MONSTEREYE_WOODCARVING, 1);
				st.playSound(SOUND_JACKPOT);
				return "30642-04.htm";
			}
			if(CHIANTA_CHARM_COUNT == 0)
				return "30642-05.htm";
		}

		if(npcId == Ancestor_Martankus)
		{
			if(BEAR_FANG_NECKLACE_COUNT > 0)
				return "30649-01.htm";
			if(MARTANKUS_CHARM_COUNT > 0)
			{
				if(st.getQuestItemsCount(RAGNA_CHIEF_NOTICE) == 0 || st.getQuestItemsCount(RAGNA_ORC_HEAD) == 0)
					return "30649-05.htm";
				st.takeItems(MARTANKUS_CHARM, -1);
				st.takeItems(RAGNA_ORC_HEAD, -1);
				st.takeItems(RAGNA_CHIEF_NOTICE, -1);
				st.giveItems(IMMORTAL_FLAME, 1);
				st.playSound(SOUND_MIDDLE);
				return "30649-06.htm";
			}
			if(st.getQuestItemsCount(IMMORTAL_FLAME) > 0)
			{
				spawn_First_Orc(st);
				return "30649-08.htm";
			}
		}

		if(npcId == First_Orc && MARTANKUS_CHARM_COUNT > 0 && st.getQuestItemsCount(IMMORTAL_FLAME) > 0)
			return "30643-01.htm";

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();

		int cond = st.getInt("cond");

		if(cond == 1)
		{
			long ORDEAL_NECKLACE_COUNT = st.getQuestItemsCount(ORDEAL_NECKLACE);
			if((npcId == Timak_Orc || npcId == Timak_Orc_Archer || npcId == Timak_Orc_Overlord || npcId == Timak_Orc_Shaman || npcId == Timak_Orc_Soldier || npcId == Timak_Orc_Warrior)
					&& ORDEAL_NECKLACE_COUNT > 0 && st.getQuestItemsCount(HATOS_CHARM) > 0 && st.getQuestItemsCount(SWORD_INTO_SKULL) == 0 && st.rollAndGiveLimited(TIMAK_ORC_SKULL, 1, (npcId - 20583) * 5 + 50, 10))
			{
				st.playSound(st.getQuestItemsCount(TIMAK_ORC_SKULL) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
			}

			if((npcId == Breka_Orc_Shaman || npcId == Breka_Orc_Overlord)
					&& ORDEAL_NECKLACE_COUNT > 0 && st.getQuestItemsCount(VARKEES_CHARM) > 0 && st.getQuestItemsCount(MANAKIAS_ORDERS) > 0 && st.rollAndGiveLimited(BREKA_ORC_FANG, 1, 45, 20))
			{
				st.playSound(st.getQuestItemsCount(BREKA_ORC_FANG) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
			}

			if(npcId == Marsh_Spider && ORDEAL_NECKLACE_COUNT > 0 && st.getQuestItemsCount(TAKUNA_CHARM) > 0)
			{
				if(Rnd.chance(50))
				{
					if(st.rollAndGiveLimited(MARSH_SPIDER_FEELER, 1, 100, 10))
					{
						st.playSound(st.getQuestItemsCount(MARSH_SPIDER_FEELER) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
					}
				}
				else if(st.rollAndGiveLimited(MARSH_SPIDER_FEET, 1, 100, 10))
				{
					st.playSound(st.getQuestItemsCount(MARSH_SPIDER_FEET) == 10 ? SOUND_MIDDLE : SOUND_ITEMGET);
				}

			}

			if(npcId == Ragna_Orc_Overlord && st.getQuestItemsCount(MARTANKUS_CHARM) > 0 && st.rollAndGiveLimited(RAGNA_ORC_HEAD, 1, 100, 1))
			{
				st.playSound(SOUND_MIDDLE);
			}

			if(npcId == Ragna_Orc_Seer && st.getQuestItemsCount(MARTANKUS_CHARM) > 0 && st.rollAndGiveLimited(RAGNA_CHIEF_NOTICE, 1, 100, 1))
			{
				st.playSound(SOUND_MIDDLE);
			}

			if(npcId == Enchanted_Monstereye && ORDEAL_NECKLACE_COUNT > 0 && st.getQuestItemsCount(CHIANTA_CHARM) > 0 && st.rollAndGiveLimited(CORNEA_OF_EN_MONSTEREYE, 1, 90, 20))
			{
				st.playSound(st.getQuestItemsCount(CORNEA_OF_EN_MONSTEREYE) == 20 ? SOUND_MIDDLE : SOUND_ITEMGET);
			}
		}
	}
}