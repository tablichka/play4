package quests._348_ArrogantSearch;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.RadarControl;
import ru.l2gw.gameserver.serverpackets.Say2;

import java.util.HashMap;

public class _348_ArrogantSearch extends Quest
{
	private final static int ARK_GUARDIAN_ELBEROTH = 27182;
	private final static int ARK_GUARDIAN_SHADOWFANG = 27183;
	private final static int ANGEL_KILLER = 27184;
	private final static int PLATINUM_TRIBE_SHAMAN = 20828;
	private final static int PLATINUM_TRIBE_OVERLORD = 20829;
	private final static int YINTZU = 20647;
	private final static int PALIOTE = 20648;

	private final static int GUARDIAN_ANGEL_1 = 20830;
	private final static int GUARDIAN_ANGEL_2 = 20859;
	private final static int SEAL_ANGEL_1 = 20831;
	private final static int SEAL_ANGEL_2 = 20860;


	private final static int HANELLIN = 30864;
	private final static int HOLY_ARK_OF_SECRECY_1 = 30977;
	private final static int HOLY_ARK_OF_SECRECY_2 = 30978;
	private final static int HOLY_ARK_OF_SECRECY_3 = 30979;
	private final static int ARK_GUARDIANS_CORPSE = 30980;
	private final static int HARNE = 30144;
	private final static int CLAUDIA_ATHEBALT = 31001;
	private final static int MARTIEN = 30645;

	private final static int SHELL_OF_MONSTERS = 14857;
	private final static int HANELLINS_FIRST_LETTER = 4288;
	private final static int HANELLINS_SECOND_LETTER = 4289;
	private final static int HANELLINS_THIRD_LETTER = 4290;
	private final static int FIRST_KEY_OF_ARK = 4291;
	private final static int SECOND_KEY_OF_ARK = 4292;
	private final static int THIRD_KEY_OF_ARK = 4293;
	private final static int WHITE_FABRIC_1 = 4294;//to use on Platinum Tribe Shamans/Overlords
	private final static int BLOODED_FABRIC = 4295;
	private final static int HANELLINS_WHITE_FLOWER = 4394;
	private final static int HANELLINS_RED_FLOWER = 4395;
	private final static int HANELLINS_YELLOW_FLOWER = 4396;
	private final static int BOOK_OF_SAINT = 4397;//Ark2 (after fight with Elberoth)
	private final static int BLOOD_OF_SAINT = 4398;//Ark1 (after fight with Angel Killer)
	private final static int BRANCH_OF_SAINT = 4399;//Ark3 (after fight with Shadowfang)
	private final static int WHITE_FABRIC_0 = 4400;//talk to Hanellin to see what to do (for companions)
	private final static int WHITE_FABRIC_2 = 5232;//to use on Guardian Angels and Seal Angels
	private final static int ANTIDOTE = 1831;
	private final static int HEALING_POTION = 1061;
	private final static int DIP_CHANCE = 5000;
	private final static int SYNTCOKES = 1888;
	private final static int ADENA = 57;
	private final static String ANGEL_KILLER_TEXT = "I have the key. Why don't you come and take it?";
	private final static String ANGEL_KILLER_TEXT2 = "Ha, that was fun! If you wish to find the key, search the corpse";
	private static L2NpcInstance spawnedAngelKiller = null;
	private static L2NpcInstance spawnedElberoth = null;
	private static L2NpcInstance spawnedShadowfang = null;

	//ARK: [key, summon, no-key text, openning-with-key text, already-openned text, content item]
	private static HashMap<Integer, Integer[]> ARKS = new HashMap<Integer, Integer[]>();
	private static HashMap<Integer, String[]> ARKS_TEXT = new HashMap<Integer, String[]>();
	//npc: letter to take, item to check for, 1st time htm, return htm, completed part htm, [x,y,z of chest]
	private static HashMap<Integer, Integer[]> ARK_OWNERS = new HashMap<Integer, Integer[]>();
	private static HashMap<Integer, String[]> ARK_OWNERS_TEXT = new HashMap<Integer, String[]>();
	//mob: cond, giveItem, amount, chance%, takeItem (assumed to take only 1 of it)
	private static HashMap<Integer, Integer[]> DROPS = new HashMap<Integer, Integer[]>();
	private final static int[] LOWA = {8341, 8342, 8721, 5532, 5535, 5536, 5539, 5542, 5547, 5548, 8331};

	public _348_ArrogantSearch()
	{
		super(348, "_348_ArrogantSearch", "An Arrogant Search"); // Party true

		ARKS.put(HOLY_ARK_OF_SECRECY_1, new Integer[]{FIRST_KEY_OF_ARK, 0, BLOOD_OF_SAINT});
		ARKS.put(HOLY_ARK_OF_SECRECY_2, new Integer[]{SECOND_KEY_OF_ARK, ARK_GUARDIAN_ELBEROTH, BOOK_OF_SAINT});
		ARKS.put(HOLY_ARK_OF_SECRECY_3, new Integer[]{THIRD_KEY_OF_ARK, ARK_GUARDIAN_SHADOWFANG, BRANCH_OF_SAINT});
		ARKS_TEXT.put(HOLY_ARK_OF_SECRECY_1, new String[]{"", "30977-01.htm", "30977-02.htm", "30977-03.htm"});
		ARKS_TEXT.put(HOLY_ARK_OF_SECRECY_2, new String[]{"That doesn't belong to you.  Don't touch it!", "30978-01.htm", "30978-02.htm", "30978-03.htm"});
		ARKS_TEXT.put(HOLY_ARK_OF_SECRECY_3, new String[]{"Get off my sight, you infidels!", "30979-01.htm", "30979-02.htm", "30979-03.htm"});

		ARK_OWNERS.put(HARNE, new Integer[]{HANELLINS_FIRST_LETTER, BLOOD_OF_SAINT, -418, 44174, -3568});
		ARK_OWNERS.put(CLAUDIA_ATHEBALT, new Integer[]{HANELLINS_SECOND_LETTER, BOOK_OF_SAINT, 181472, 7158, -2725});
		ARK_OWNERS.put(MARTIEN, new Integer[]{HANELLINS_THIRD_LETTER, BRANCH_OF_SAINT, 50693, 158674, 376});
		ARK_OWNERS_TEXT.put(HARNE, new String[]{"30144-01.htm", "30144-02.htm", "30144-03.htm"});
		ARK_OWNERS_TEXT.put(CLAUDIA_ATHEBALT, new String[]{"31001-01.htm", "31001-02.htm", "31001-03.htm"});
		ARK_OWNERS_TEXT.put(MARTIEN, new String[]{"30645-01.htm", "30645-02.htm", "30645-03.htm"});

		DROPS.put(YINTZU, new Integer[]{2, SHELL_OF_MONSTERS, 1, 10, 0});
		DROPS.put(PALIOTE, new Integer[]{2, SHELL_OF_MONSTERS, 1, 10, 0});
		DROPS.put(ANGEL_KILLER, new Integer[]{5, FIRST_KEY_OF_ARK, 1, 100, 0});
		DROPS.put(ARK_GUARDIAN_ELBEROTH, new Integer[]{5, SECOND_KEY_OF_ARK, 1, 100, 0});
		DROPS.put(ARK_GUARDIAN_SHADOWFANG, new Integer[]{5, THIRD_KEY_OF_ARK, 1, 100, 0});
		DROPS.put(PLATINUM_TRIBE_SHAMAN, new Integer[]{25, BLOODED_FABRIC, 1, 10, WHITE_FABRIC_1});
		DROPS.put(PLATINUM_TRIBE_OVERLORD, new Integer[]{25, BLOODED_FABRIC, 1, 10, WHITE_FABRIC_1});
		DROPS.put(GUARDIAN_ANGEL_1, new Integer[]{26, BLOODED_FABRIC, 10, 10, WHITE_FABRIC_2});
		DROPS.put(GUARDIAN_ANGEL_2, new Integer[]{26, BLOODED_FABRIC, 10, 10, WHITE_FABRIC_2});
		DROPS.put(SEAL_ANGEL_1, new Integer[]{26, BLOODED_FABRIC, 10, 10, WHITE_FABRIC_2});
		DROPS.put(SEAL_ANGEL_2, new Integer[]{26, BLOODED_FABRIC, 10, 10, WHITE_FABRIC_2});

		addStartNpc(HANELLIN);

		addTalkId(ARK_GUARDIANS_CORPSE);

		for(int i : ARK_OWNERS.keySet())
			addTalkId(i);

		for(int i : ARKS.keySet())
			addTalkId(i);

		for(int i : DROPS.keySet())
			addKillId(i);


		addAttackId(PLATINUM_TRIBE_SHAMAN);
		addAttackId(PLATINUM_TRIBE_OVERLORD);

		addQuestItem(HANELLINS_FIRST_LETTER,
				HANELLINS_SECOND_LETTER,
				HANELLINS_THIRD_LETTER,
				HANELLINS_WHITE_FLOWER,
				HANELLINS_RED_FLOWER,
				HANELLINS_YELLOW_FLOWER,
				BOOK_OF_SAINT,
				WHITE_FABRIC_1,
				BLOOD_OF_SAINT,
				BRANCH_OF_SAINT,
				WHITE_FABRIC_0,
				WHITE_FABRIC_2);

		for(Integer[] i : DROPS.values())
			if(i[1] != BLOODED_FABRIC)
				addQuestItem(i[1]);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		L2Player player = st.getPlayer();

		if(event.equals("30864_02a"))
		{
			htmltext = "30864-02a.htm";
		}
		else if(event.equals("30864_02b"))
		{
			htmltext = "30864-02b.htm";
		}
		else if(event.equals("30864_03"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
			htmltext = "30864-03.htm";
		}
		else if(event.equals("30864_04a"))//work alone
		{
			st.set("cond", "4");
			st.playSound(SOUND_MIDDLE);
			st.takeItems(SHELL_OF_MONSTERS, -1);
			htmltext = "30864-04c.htm";
			st.set("companions", "0");
		}
		else if(event.equals("30864_04d"))// talking about relics
		{
			st.set("cond", "5");
			st.playSound(SOUND_MIDDLE);
			st.giveItems(HANELLINS_FIRST_LETTER, 1);
			st.giveItems(HANELLINS_SECOND_LETTER, 1);
			st.giveItems(HANELLINS_THIRD_LETTER, 1);
			htmltext = "30864-05.htm";// Go get the 3 sacred relics
		}
		else if(event.equals("30864_05a"))
			htmltext = "30864-05a.htm";  // Hanellin talks about relics
		else if(event.equals("30864_05b"))
			htmltext = "30864-05b.htm";  // Hanelling talks about ToI, Baium
		else if(event.equals("30864_05c"))
			htmltext = "30864-05c.htm";  // Hanelling keeps doing it even more
		else if(event.equals("30864_07c"))
			htmltext = "30864-07c.htm";  // Hanelling talks about how to dip the blood
		else if(event.equals("30864_07d"))
			htmltext = "30864-07d.htm";  // Hanelling talks about how to dip the blood
		else if(event.equals("30864_07e"))
			htmltext = "30864-07e.htm";  // Hanelling asks if u want to work for money or just visit the baium
		else if(event.equals("30864_07f"))
		{
			st.set("formoney", "0");
			htmltext = "30864-07f.htm";  // I want to visit the emperor (quest should end when you dip 1 fabric
			// HTML text is NOT YET RIGHT
		}
		else if(event.equals("30864_07g"))
		{
			st.set("formoney", "1");
			htmltext = "30864-07g.htm";  // I work for money
		}
		else if(event.equals("31001_01a"))
		{
			st.getPlayer().sendPacket(new RadarControl(0, 1, ARK_OWNERS.get(CLAUDIA_ATHEBALT)[2], ARK_OWNERS.get(CLAUDIA_ATHEBALT)[3], ARK_OWNERS.get(CLAUDIA_ATHEBALT)[4]));
			htmltext = "31001-01a.htm"; // Claudia Athebaldt talks
		}
		else if(event.equals("30144_01a"))
		{
			st.getPlayer().sendPacket(new RadarControl(0, 1, ARK_OWNERS.get(HARNE)[2], ARK_OWNERS.get(HARNE)[3], ARK_OWNERS.get(HARNE)[4]));
			htmltext = "30144-01a.htm"; // Magister Harne talks
		}
		else if(event.equals("30645_01a"))
		{
			st.getPlayer().sendPacket(new RadarControl(0, 1, ARK_OWNERS.get(MARTIEN)[2], ARK_OWNERS.get(MARTIEN)[3], ARK_OWNERS.get(MARTIEN)[4]));
			htmltext = "30645-01a.htm"; // Martien talks
		}
		else if(event.equals("30977_01a"))
		{
			//if you do not have the key (first meeting)
			if(st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_1)[0]) == 0 && st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_1)[2]) == 0)
				htmltext = "30977-01.htm";
				// if the player already has openned the chest and has its content, show "chest empty"
			else if(st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_1)[2]) == 1)
				htmltext = ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_1)[3];
			else
			// the player has the key and doesn't have the contents, give the contents
			{
				htmltext = ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_1)[2];
				st.takeItems(ARKS.get(HOLY_ARK_OF_SECRECY_1)[0], 1);
				st.giveItems(ARKS.get(HOLY_ARK_OF_SECRECY_1)[2], 1);
			}
		}
		else if(event.equals("30978_01a"))
		{ //spawning Ark2 guardian
			// if you do not have the key (first meeting) and elberoth isnt spawned already
			if(st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_2)[0]) == 0 && st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_2)[2]) == 0)
			{
				if(st.getPcSpawn().getSpawn(ARK_GUARDIAN_ELBEROTH) == null || st.getPcSpawn().getSpawn(ARK_GUARDIAN_ELBEROTH).getLastSpawn().isDead())
				{
					st.getPcSpawn().addSpawn(ARK_GUARDIAN_ELBEROTH, player.getX(), player.getY(), player.getZ(), true, 120000);
					spawnedElberoth = st.getPcSpawn().getSpawn(ARK_GUARDIAN_ELBEROTH).getLastSpawn();
					if(spawnedElberoth != null)
						spawnedElberoth.broadcastPacket(new Say2(spawnedElberoth.getObjectId(), Say2C.ALL, spawnedElberoth.getName(), ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_2)[0]));

					htmltext = "30978-01a.htm";
				}
				// quest mob is already spawned
				else
					htmltext = "30978-01b.htm";
			}
			// if the player already has openned the chest and has its content, show "chest empty"
			else if(st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_2)[2]) == 1)
				htmltext = ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_2)[3];
				// the player has the key and doesn't have the contents, give the contents
			else
			{
				htmltext = ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_2)[2];
				st.takeItems(ARKS.get(HOLY_ARK_OF_SECRECY_2)[0], 1);
				st.giveItems(ARKS.get(HOLY_ARK_OF_SECRECY_2)[2], 1);
			}
		}
		else if(event.equals("30979_01a"))
		{ //spawning Ark3 guardian
			// if you do not have the key (first meeting)
			if(st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_3)[0]) == 0 && st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_3)[2]) == 0)
			{
				if(st.getPcSpawn().getSpawn(ARK_GUARDIAN_SHADOWFANG) == null || st.getPcSpawn().getSpawn(ARK_GUARDIAN_SHADOWFANG).getLastSpawn().isDead())
				{
					st.getPcSpawn().addSpawn(ARK_GUARDIAN_SHADOWFANG, player.getX(), player.getY(), player.getZ(), true, 120000);
					spawnedShadowfang = st.getPcSpawn().getSpawn(ARK_GUARDIAN_SHADOWFANG).getLastSpawn();
					if(spawnedShadowfang != null)
						spawnedShadowfang.broadcastPacket(new Say2(spawnedShadowfang.getObjectId(), Say2C.ALL, spawnedShadowfang.getName(), ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_3)[0]));

					htmltext = "30979-01a.htm";
				}
				// quest mob is already spawned
				else
					htmltext = "30979-01b.htm";
			}
			// if the player already has openned the chest and has its content, show "chest empty"
			else if(st.getQuestItemsCount(ARKS.get(HOLY_ARK_OF_SECRECY_3)[2]) == 1)
				htmltext = ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_3)[3];
				// the player has the key and doesn't have the contents, give the contents
			else
			{
				htmltext = ARKS_TEXT.get(HOLY_ARK_OF_SECRECY_3)[2];
				st.takeItems(ARKS.get(HOLY_ARK_OF_SECRECY_3)[0], 1);
				st.giveItems(ARKS.get(HOLY_ARK_OF_SECRECY_3)[2], 1);
			}
		}
		else if(event.equals("30980_01a"))
		{
			// you dont have the key yet
			if(st.getQuestItemsCount(FIRST_KEY_OF_ARK) == 0 && st.getInt("angelKillerIsDefeated") == 0)
			{
				if(st.getPcSpawn().getSpawn(ANGEL_KILLER) == null || st.getPcSpawn().getSpawn(ANGEL_KILLER).getLastSpawn().isDead())
				{
					st.getPcSpawn().addSpawn(ANGEL_KILLER, player.getX(), player.getY(), player.getZ(), true, 300000);
					spawnedAngelKiller = st.getPcSpawn().getSpawn(ANGEL_KILLER).getLastSpawn();
					if(spawnedAngelKiller != null)
						spawnedAngelKiller.broadcastPacket(new Say2(spawnedAngelKiller.getObjectId(), Say2C.ALL, spawnedAngelKiller.getName(), ANGEL_KILLER_TEXT));

					htmltext = "30980-01a.htm";
				}
				// quest mob is already spawned
				else
					htmltext = "30980-01b.htm";
			}
			else if(st.getQuestItemsCount(FIRST_KEY_OF_ARK) == 0 && st.getInt("angelKillerIsDefeated") == 1)
			{
				st.giveItems(FIRST_KEY_OF_ARK, 1);
				htmltext = "30980-02.htm";
			}
			else
				htmltext = "30980-03.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == HANELLIN)
		{
			if(st.isCreated())
			// if the quest was completed and the player still has a blooded fabric
			// tell them the "secret" that they can use it in order to visit Baium.
			{
				st.set("cond", "0");
				if(st.getPlayer().getLevel() < 60)
				{
					htmltext = "30864-01.htm";//not qualified
					st.exitCurrentQuest(true);
				}
				else if(st.isCreated())
				{
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					st.set("cond", "1");
					st.set("platinum", "0");
					htmltext = "30864-02.htm";// Successful start: begin the dialog which will set cond=2
				}

			}
			// Player abandoned in the middle of last dialog...repeat the dialog.
			else if(cond == 1)
				htmltext = "30864-02.htm";// begin the dialog which will set cond=2
				// Has returned before getting the powerstone
			else if(cond == 2 && st.getQuestItemsCount(SHELL_OF_MONSTERS) == 0)
				htmltext = "30864-03a.htm";// go get the shell of monsters
			else if(cond == 2)
				htmltext = "30864-04.htm";// Ask "work alone or in group?"...only alone is implemented in v0.1
			else if(cond == 4)
				htmltext = "30864-04c.htm";// Decided to work alone
			else if(cond == 5 && (st.getQuestItemsCount(BOOK_OF_SAINT) < 1 || st.getQuestItemsCount(BLOOD_OF_SAINT) < 1 || st.getQuestItemsCount(BRANCH_OF_SAINT) < 1))
				htmltext = "30864-05.htm";// Repeat: Go get the 3 sacred relics
			else if(cond == 5)
			{
				htmltext = "30864-06.htm";// All relics collected!...Get me antidotes & greater healing
				st.takeItems(BOOK_OF_SAINT, -1);
				st.takeItems(BLOOD_OF_SAINT, -1);
				st.takeItems(BRANCH_OF_SAINT, -1);
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "22");
			}
			else if(cond == 22 && st.getQuestItemsCount(ANTIDOTE) < 5 && st.getQuestItemsCount(HEALING_POTION) < 1)
				htmltext = "30864-06a.htm";// where are my antidotes & greater healing
			else if(cond == 22)
			{
				st.takeItems(ANTIDOTE, 5);
				st.takeItems(HEALING_POTION, 1);
				st.set("cond", "25");
				st.playSound(SOUND_MIDDLE);
				htmltext = "30864-07.htm";// go get platinum tribe blood...
				st.giveItems(WHITE_FABRIC_1, 1);
			}
			else if(cond == 25 && st.getQuestItemsCount(BLOODED_FABRIC) > 0 && st.getInt("formoney") == 1 && st.getInt("platinum") == 1)
			{
				st.set("cond", "26");
				st.playSound(SOUND_MIDDLE);
				htmltext = "30864-08.htm";	// go get angels blood...
				st.giveItems(WHITE_FABRIC_2, 9);
				st.giveItems(LOWA[Rnd.get(LOWA.length)], 1);
				st.giveItems(SYNTCOKES, 1);
				st.giveItems(ADENA, 49000);
			}
			else if(cond == 25 && st.getQuestItemsCount(BLOODED_FABRIC) > 0 && st.getInt("platinum") == 0)
				htmltext = "30864-07a.htm"; // remind player to kill platinum tribes
			else if(cond == 25 && st.getQuestItemsCount(BLOODED_FABRIC) < 1 && st.getQuestItemsCount(WHITE_FABRIC_1) > 0)
				htmltext = "30864-07a.htm"; // remind player to kill platinum tribes
			else if(cond == 25 && st.getQuestItemsCount(WHITE_FABRIC_1) == 0 && st.getQuestItemsCount(BLOODED_FABRIC) == 0)
			{
				htmltext = "30864-07b.htm"; // player has no fabrics at all, quest is finished, ask to start over.
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else if(cond == 25 && st.getQuestItemsCount(BLOODED_FABRIC) > 0 && st.getQuestItemsCount(WHITE_FABRIC_1) == 0 && st.getInt("formoney") == 0 && st.getInt("platinum") == 1)
			{
				htmltext = "30864-Baium.htm"; // player has done the quest to visit the Emperor
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else if(cond == 26 && st.getInt("platinum") == 1)
			{

				long nBF = st.getQuestItemsCount(BLOODED_FABRIC);
				long nWF = st.getQuestItemsCount(WHITE_FABRIC_2);
				if(nBF == 0 && nWF > 0)
					htmltext = "30864-09a.htm"; // tell player to dip blood from angels
				else if(nBF == 10 && nWF == 0)
				{
					st.takeItems(BLOODED_FABRIC, nBF);
					st.giveItems(WHITE_FABRIC_2, 9);
					st.giveItems(LOWA[Rnd.get(LOWA.length)], 1);
					st.giveItems(SYNTCOKES, 1);
					st.giveItems(ADENA, 49000);
					htmltext = "30864-08a.htm";	// go get angels blood...
				}
				else if(nBF > 0 && nWF > 0)
				{
					st.takeItems(BLOODED_FABRIC, nBF);
					st.giveItems(ADENA, nBF * 150000);
					htmltext = "30864-09.htm"; //  player is rewarded, but still has white fabrics
				}
				else if(nBF > 0 && nWF == 0 && nBF < 10)
				{
					st.takeItems(BLOODED_FABRIC, nBF);
					st.giveItems(ADENA, nBF * 150000);
					htmltext = "30864-09c.htm"; // player is rewarded, but have no fabrics left
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
				}
				else if(nBF == 0 && nWF == 0)
				{
					htmltext = "30864-09b.htm"; // player has no more fabrics left, quest finished, ask player to start over.
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
				}
			}
		}
		// Other NPCs follow:
		else if(cond == 5)
			if(ARK_OWNERS.containsKey(npcId))
			{
				// first meeting...have the letter
				if(st.getQuestItemsCount(ARK_OWNERS.get(npcId)[0]) == 1)
				{
					st.takeItems(ARK_OWNERS.get(npcId)[0], 1);
					htmltext = ARK_OWNERS_TEXT.get(npcId)[0];
					//st.getPlayer().sendPacket(new RadarControl(0, 1, ARK_OWNERS.get(npcId)[2], ARK_OWNERS.get(npcId)[3], ARK_OWNERS.get(npcId)[4]));
				}
				// do not have letter and do not have the item
				else if(st.getQuestItemsCount(ARK_OWNERS.get(npcId)[1]) < 1)
				{
					htmltext = ARK_OWNERS_TEXT.get(npcId)[0];
					st.getPlayer().sendPacket(new RadarControl(0, 1, ARK_OWNERS.get(npcId)[2], ARK_OWNERS.get(npcId)[3], ARK_OWNERS.get(npcId)[4]));
				}
				else
					//have the item (done)
					htmltext = ARK_OWNERS_TEXT.get(npcId)[2];
			}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();

		if(DROPS.containsKey(npcId))
		{
			int cond = DROPS.get(npcId)[0];
			int chance = DROPS.get(npcId)[3];
			int random = 100;

			if(npcId == PLATINUM_TRIBE_SHAMAN || npcId == PLATINUM_TRIBE_OVERLORD)
				random = DIP_CHANCE;

			if(st.getInt("cond") == cond && Rnd.get(random) < chance)
			{
				int amount = DROPS.get(npcId)[2];

				if((npcId == PLATINUM_TRIBE_SHAMAN || npcId == PLATINUM_TRIBE_OVERLORD) && cond == 25)
					st.set("platinum", "1");

				if(npcId == PLATINUM_TRIBE_SHAMAN || npcId == PLATINUM_TRIBE_OVERLORD || npcId == GUARDIAN_ANGEL_1 || npcId == GUARDIAN_ANGEL_2 || npcId == SEAL_ANGEL_1 || npcId == SEAL_ANGEL_2)
					amount = 1;

				if(st.getInt("cond") == 25 && st.getInt("formoney") == 1)
				{
					long nWF1 = st.getQuestItemsCount(WHITE_FABRIC_1);
					if(nWF1 > 0)
					{
						st.giveItems(DROPS.get(npcId)[1], 1);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(st.getInt("cond") == 26 && st.getInt("formoney") == 1)
				{
					long nWF2 = st.getQuestItemsCount(WHITE_FABRIC_2);
					if(nWF2 > 1)
					{
						st.giveItems(DROPS.get(npcId)[1], 1);
						st.playSound(SOUND_ITEMGET);
					}
					else if(nWF2 == 1)
					{
						st.giveItems(DROPS.get(npcId)[1], 1);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(st.getInt("cond") == 25 && st.getInt("formoney") == 0)
				{  // Quest ends if you chose "Visit the Emperor"
					if(DROPS.get(npcId)[4] == 0 || st.getQuestItemsCount(DROPS.get(npcId)[4]) > 0)
					{
						st.giveItems(DROPS.get(npcId)[1], 1);
						st.playSound(SOUND_FINISH);
					}
				}
				else if(st.getQuestItemsCount(DROPS.get(npcId)[1]) < DROPS.get(npcId)[2])
				{
					if(DROPS.get(npcId)[4] == 0 || st.getQuestItemsCount(DROPS.get(npcId)[4]) > 0)
					{
						st.giveItems(DROPS.get(npcId)[1], amount);
						st.playSound(SOUND_ITEMGET);
					}
				}

				if(DROPS.get(npcId)[4] != 0 && st.getQuestItemsCount(DROPS.get(npcId)[4]) > 0)
					st.takeItems(DROPS.get(npcId)[4], 1);
			}
		}
		if(npcId == ANGEL_KILLER)
		{
			if(spawnedAngelKiller != null)
				spawnedAngelKiller.broadcastPacket(new Say2(spawnedAngelKiller.getObjectId(), Say2C.ALL, spawnedAngelKiller.getName(), ANGEL_KILLER_TEXT2));
		}
	}

// On retail you don't have to kill Platinum Tribe Shamans and Overlords to
// dip the blood from mobs.  You just have to attack them. 

	@Override
	public String onAttack(L2NpcInstance npc, QuestState st, L2Skill skill)
	{
		int npcId = npc.getNpcId();
		if(DROPS.containsKey(npcId))
		{
			int cond = DROPS.get(npcId)[0];
			int chance = DROPS.get(npcId)[3];
			int amount = DROPS.get(npcId)[2];
			if(st.getInt("cond") == cond && Rnd.get(DIP_CHANCE) < chance)
			{
				if((npcId == PLATINUM_TRIBE_SHAMAN || npcId == PLATINUM_TRIBE_OVERLORD) && st.getInt("cond") == 25)
					st.set("platinum", "1");

				if(DROPS.get(npcId)[4] != 0 && st.getQuestItemsCount(DROPS.get(npcId)[4]) > 0)
					st.takeItems(DROPS.get(npcId)[4], 1);

				if(st.getInt("cond") == 25 && st.getQuestItemsCount(WHITE_FABRIC_1) > 0)
				{
					st.giveItems(DROPS.get(npcId)[1], 1);
					if(st.getInt("formoney") == 1)
						st.playSound(SOUND_MIDDLE);
					else
						st.playSound(SOUND_FINISH);
				}
				else if(st.getQuestItemsCount(DROPS.get(npcId)[1]) < DROPS.get(npcId)[2])
				{
					st.giveItems(DROPS.get(npcId)[1], amount);
					st.playSound(SOUND_ITEMGET);
				}

			}
		}

		return null;
	}
}
