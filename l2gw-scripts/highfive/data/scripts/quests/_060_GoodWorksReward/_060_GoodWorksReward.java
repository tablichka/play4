package quests._060_GoodWorksReward;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Files;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * Упрощенный квест на вторые профессии, добавлен в Gracia
 *
 * @author HellSinger
 *         http://www2.elliebelly.net/modules/QuestGuides/goodworksreward/goodworksreward.html
 *         http://forums.goha.ru/showpost.php?p=4512023&postcount=12
 */
public class _060_GoodWorksReward extends Quest
{
	//NPC
	private static final int Daeger = 31435;
	private static final int Mark = 32487;
	private static final int Helvetia = 30081;
	private static final int BlackMarketeerOfMammon = 31092;
	//Quest Items
	private static final int BloodyClothFragment = 10867;
	private static final int HelvetiasAntidote = 10868;
	//The List of Basic Occupations
	//Humans
	private static final int Cleric = 15;
	private static final int Human_Wizard = 11;
	private static final int Rogue = 7;
	private static final int Human_Knight = 4;
	private static final int Warrior = 1;
	//Elves
	private static final int Elf_Knight = 19;
	private static final int Scout = 22;
	private static final int Elf_Wizard = 26;
	private static final int Oracle = 29;
	//Dark elves
	private static final int Palus_Knight = 32;
	private static final int Assassin = 35;
	private static final int DE_Wizard = 39;
	private static final int Shillien_Oracle = 42;
	//Orks
	private static final int Raider = 45;
	private static final int Monk = 47;
	private static final int Shaman = 50;
	//Dwarfs
	private static final int Scavenger = 54;
	private static final int Artisan = 56;

	//The List of Second Occupations
	//Humans
	private static final int Gladiator = 2;
	private static final int Warlord = 3;
	private static final int Paladin = 5;
	private static final int Dark_Avenger = 6;
	private static final int Treasure_Hunter = 8;
	private static final int Hawkeye = 9;
	private static final int Sorcerer = 12;
	private static final int Necromancer = 13;
	private static final int Warlock = 14;
	private static final int Bishop = 16;
	private static final int Prophet = 17;
	//Elves
	private static final int Temple_Knight = 20;
	private static final int Sword_Singer = 21;
	private static final int Plains_Walker = 23;
	private static final int Silver_Ranger = 24;
	private static final int Spellsinger = 27;
	private static final int Elemental_Summoner = 28;
	private static final int Elder = 30;
	//Dark elves
	private static final int Shillien_Knight = 33;
	private static final int Bladedancer = 34;
	private static final int Abyss_Walker = 36;
	private static final int Phantom_Ranger = 37;
	private static final int Spellhowler = 40;
	private static final int Phantom_Summoner = 41;
	private static final int Shillien_Elder = 43;
	//Orks
	private static final int Destroyer = 46;
	private static final int Tyrant = 48;
	private static final int Overlord = 51;
	private static final int Warcryer = 52;
	//Dwarfs
	private static final int Bounty_Hunter = 55;
	private static final int Warsmith = 57;

	//Items
	private static final int MarkOfTrust = 2734;
	private static final int MarkOfSearcher = 2809;
	private static final int MarkOfGuildsman = 3119;
	private static final int MarkOfProsperity = 3238;
	private static final int MarkOfMaestro = 2867;
	private static final int MarkOfChallenger = 2627;
	private static final int MarkOfDuty = 2633;
	private static final int MarkOfSeeker = 2673;
	private static final int MarkOfDueslist = 2762;
	private static final int MarkOfHealer = 2820;
	private static final int MarkOfLife = 3140;
	private static final int MarkOfChampion = 3276;
	private static final int MarkOfSagittarius = 3293;
	private static final int MarkOfWitchcraft = 3307;
	private static final int MarkOfScholar = 2674;
	private static final int MarkOfMagus = 2840;
	private static final int MarkOfSummoner = 3336;
	private static final int MarkOfPiligrim = 2721;
	private static final int MarkOfReformer = 2821;
	private static final int MarkOfWarspirit = 2879;
	private static final int MarkOfGlory = 3203;
	private static final int MarkOfLord = 3390;
	private static final int MarkOfFate = 3172;

	//Occupation Change
	//# [BasicProf, First, Second, Third]	
	public final int[][] OCCUP_CHANGE = {
			{Cleric, Bishop, Prophet, 0},
			{Human_Wizard, Sorcerer, Necromancer, Warlock},
			{Rogue, Treasure_Hunter, Hawkeye, 0},
			{Human_Knight, Paladin, Dark_Avenger, 0},
			{Warrior, Warlord, Gladiator, 0},
			{Elf_Knight, Temple_Knight, Sword_Singer, 0},
			{Scout, Plains_Walker, Silver_Ranger, 0},
			{Elf_Wizard, Spellsinger, Elemental_Summoner, 0},
			{Oracle, Elder, 0, 0},
			{Palus_Knight, Shillien_Knight, Bladedancer, 0},
			{Assassin, Abyss_Walker, Phantom_Ranger, 0},
			{DE_Wizard, Spellhowler, Phantom_Summoner, 0},
			{Shillien_Oracle, Shillien_Elder, 0, 0},
			{Raider, Destroyer, 0, 0},
			{Monk, Tyrant, 0, 0},
			{Shaman, Overlord, Warcryer, 0},
			{Scavenger, Bounty_Hunter, 0, 0},
			{Artisan, Warsmith, 0, 0}};

	//Marks to the Profession
	//# [SecondProf, FirstMark_id, SecondMark_id, ThridMark_id]
	public final int[][] MARKS_TO_PROF = {
			{Gladiator, MarkOfChallenger, MarkOfTrust, MarkOfDueslist},
			{Warlord, MarkOfChallenger, MarkOfTrust, MarkOfChampion},
			{Paladin, MarkOfDuty, MarkOfTrust, MarkOfHealer},
			{Dark_Avenger, MarkOfDuty, MarkOfTrust, MarkOfWitchcraft},
			{Treasure_Hunter, MarkOfSeeker, MarkOfTrust, MarkOfSearcher},
			{Hawkeye, MarkOfSeeker, MarkOfTrust, MarkOfSagittarius},
			{Sorcerer, MarkOfScholar, MarkOfTrust, MarkOfMagus},
			{Necromancer, MarkOfScholar, MarkOfTrust, MarkOfWitchcraft},
			{Warlock, MarkOfScholar, MarkOfTrust, MarkOfSummoner},
			{Bishop, MarkOfPiligrim, MarkOfTrust, MarkOfHealer},
			{Prophet, MarkOfPiligrim, MarkOfTrust, MarkOfReformer},
			{Temple_Knight, MarkOfDuty, MarkOfLife, MarkOfHealer},
			{Sword_Singer, MarkOfChallenger, MarkOfLife, MarkOfDueslist},
			{Plains_Walker, MarkOfSeeker, MarkOfLife, MarkOfSearcher},
			{Silver_Ranger, MarkOfSeeker, MarkOfLife, MarkOfSagittarius},
			{Spellsinger, MarkOfScholar, MarkOfLife, MarkOfMagus},
			{Elemental_Summoner, MarkOfScholar, MarkOfLife, MarkOfSummoner},
			{Elder, MarkOfPiligrim, MarkOfLife, MarkOfHealer},
			{Shillien_Knight, MarkOfDuty, MarkOfFate, MarkOfWitchcraft},
			{Bladedancer, MarkOfChallenger, MarkOfFate, MarkOfDueslist},
			{Abyss_Walker, MarkOfSeeker, MarkOfFate, MarkOfSearcher},
			{Phantom_Ranger, MarkOfSeeker, MarkOfFate, MarkOfSagittarius},
			{Spellhowler, MarkOfScholar, MarkOfFate, MarkOfMagus},
			{Phantom_Summoner, MarkOfScholar, MarkOfFate, MarkOfSummoner},
			{Shillien_Elder, MarkOfPiligrim, MarkOfFate, MarkOfReformer},
			{Destroyer, MarkOfChallenger, MarkOfGlory, MarkOfChampion},
			{Tyrant, MarkOfChallenger, MarkOfGlory, MarkOfDueslist},
			{Overlord, MarkOfPiligrim, MarkOfGlory, MarkOfLord},
			{Warcryer, MarkOfPiligrim, MarkOfGlory, MarkOfWarspirit},
			{Bounty_Hunter, MarkOfGuildsman, MarkOfProsperity, MarkOfSearcher},
			{Warsmith, MarkOfGuildsman, MarkOfProsperity, MarkOfMaestro}};

	//MOB
	private static final int Pursuer = 27340;
	//Other
	private static final int RewardAdena = 1000000;
	private static final int PotionCost = 3000000;
	private static final int Adena_ID = 57;

	String[] NPCtext = new String[]{" %player_name%! I must kill you. Blame you own curiosity.", " You are stronger. This was a mistake. "};

	//private static boolean QuestProf = true;

	public _060_GoodWorksReward()
	{
		super(60, "_060_GoodWorksReward", "Good Work's Reward");

		addStartNpc(Daeger);
		addTalkId(Mark);
		addTalkId(Helvetia);
		addTalkId(BlackMarketeerOfMammon);

		addKillId(Pursuer);

		addQuestItem(BloodyClothFragment, HelvetiasAntidote);
	}

	public String getProfName(int idProf)
	{
		String name = "";
		if(idProf == Gladiator)
			name = "Gladiator";
		else if(idProf == Warlord)
			name = "Warlord";
		else if(idProf == Paladin)
			name = "Paladin";
		else if(idProf == Dark_Avenger)
			name = "Dark Avenger";
		else if(idProf == Treasure_Hunter)
			name = "Treasure Hunter";
		else if(idProf == Hawkeye)
			name = "Hawkeye";
		else if(idProf == Sorcerer)
			name = "Sorcerer";
		else if(idProf == Necromancer)
			name = "Necromancer";
		else if(idProf == Warlock)
			name = "Warlock";
		else if(idProf == Bishop)
			name = "Bishop";
		else if(idProf == Prophet)
			name = "Prophet";
		else if(idProf == Temple_Knight)
			name = "Temple Knight";
		else if(idProf == Sword_Singer)
			name = "Sword Singer";
		else if(idProf == Plains_Walker)
			name = "Plains Walker";
		else if(idProf == Silver_Ranger)
			name = "Silver Ranger";
		else if(idProf == Spellsinger)
			name = "Spellsinger";
		else if(idProf == Elemental_Summoner)
			name = "Elemental Summoner";
		else if(idProf == Elder)
			name = "Elder";
		else if(idProf == Shillien_Knight)
			name = "Shillien Knight";
		else if(idProf == Bladedancer)
			name = "Bladedancer";
		else if(idProf == Abyss_Walker)
			name = "Abyss Walker";
		else if(idProf == Phantom_Ranger)
			name = "Phantom Ranger";
		else if(idProf == Spellhowler)
			name = "Spellhowler";
		else if(idProf == Phantom_Summoner)
			name = "Phantom Summoner";
		else if(idProf == Shillien_Elder)
			name = "Shillien Elder";
		else if(idProf == Destroyer)
			name = "Destroyer";
		else if(idProf == Tyrant)
			name = "Tyrant";
		else if(idProf == Overlord)
			name = "Overlord";
		else if(idProf == Warcryer)
			name = "Warcryer";
		else if(idProf == Bounty_Hunter)
			name = "Bounty Hunter";
		else if(idProf == Warsmith)
			name = "Warsmith";

		return name;
	}

	/* (non-Javadoc)
	 * @see ru.l2gw.gameserver.model.quest.Quest#onEvent(java.lang.String, ru.l2gw.gameserver.model.quest.QuestState)
	 */

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("31435-03.htm"))
		{
			st.set("cond", "1");
			st.set("marks_calculated", "0");
			st.set("marks_already_have", "0");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32487-02.htm"))
		{
			if(st.getPcSpawn().isSpawnExists(Pursuer))// проверка, вызван ли монстр которого надо убивать
			{
				htmltext = "32487-wait.htm";
			}
			else
			{
				st.getPcSpawn().addSpawn(Pursuer, 72540, 148050, -3320, true, 300000);
				st.playSound(SOUND_MIDDLE);
				L2NpcInstance Pursuernpc = L2ObjectsStorage.getByNpcId(Pursuer);
				if(Pursuernpc != null)
					Pursuernpc.addDamageHate(st.getPlayer(), 0, 9999);
				Pursuernpc.getAI().setIntention(AI_INTENTION_ATTACK, st.getPlayer());
				String pursuersay = NPCtext[0].replace("%player_name%", st.getPlayer().getName());
				Functions.npcSay(Pursuernpc, Say2C.ALL, pursuersay);
			}
		}
		else if(event.equalsIgnoreCase("31435-05.htm"))
		{
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30081-03.htm"))
		{
			st.takeItems(BloodyClothFragment, -1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30081-05.htm"))
		{
			if(st.getPlayer().getAdena() >= PotionCost) // проверка наличия денег
			{
				st.takeItems(Adena_ID, PotionCost);
				st.giveItems(HelvetiasAntidote, 1);
				st.set("cond", "7");
				st.setState(STARTED);
			}
			else
			{
				htmltext = "<html><body>You have not enough money!</body></html>";
			}
		}
		else if(event.equalsIgnoreCase("32487-05.htm"))
		{
			st.takeItems(HelvetiasAntidote, -1);
			st.set("cond", "8");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("31435-07.htm"))
		{
			st.set("cond", "9");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("31092-03.htm") && st.getPlayer().getClassId().getLevel() > 2) // у игрока есть вторая профессия и он пришел к мамону за деньгами.
		{
			htmltext = "31092-06.htm";
			st.giveItems(Adena_ID, RewardAdena * st.getInt("marks_already_have"));
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("31092-06.htm"))
		{
			st.giveItems(Adena_ID, RewardAdena * st.getInt("marks_already_have"));
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("31092-marks.htm"))
		{
			htmltext = Files.read("data/scripts/quests/_060_GoodWorksReward/" + event, st.getPlayer().getVar("lang@"));
			int profId = st.getPlayer().getClassId().getId();

			for(int i = 0; i < OCCUP_CHANGE.length; i++)
				if(profId == OCCUP_CHANGE[i][0])
				{
					htmltext = htmltext.replace("%first%", getProfName(OCCUP_CHANGE[i][1]));
					htmltext = htmltext.replace("%second%", getProfName(OCCUP_CHANGE[i][2]));
					htmltext = htmltext.replace("%third%", getProfName(OCCUP_CHANGE[i][3]));
				}
		}

		for(int i = 0; i < MARKS_TO_PROF.length; i++)
			if(event.equalsIgnoreCase(getProfName(MARKS_TO_PROF[i][0])) && st.getInt("marks_calculated") == 0)
			{ //ибо нефег нагребать марки на все доступные профы
				int marks_already_have = 0;
				if(st.getQuestItemsCount(MARKS_TO_PROF[i][1]) < 1)
					st.giveItems(MARKS_TO_PROF[i][1], 1);
				else
					marks_already_have = marks_already_have + 1;
				if(st.getQuestItemsCount(MARKS_TO_PROF[i][2]) < 1)
					st.giveItems(MARKS_TO_PROF[i][2], 1);
				else
					marks_already_have = marks_already_have + 1;
				if(st.getQuestItemsCount(MARKS_TO_PROF[i][3]) < 1)
					st.giveItems(MARKS_TO_PROF[i][3], 1);
				else
					marks_already_have = marks_already_have + 1;
				if(marks_already_have > 3) // мало ли...
					marks_already_have = 3;
				st.set("marks_already_have", String.valueOf(marks_already_have));
				st.set("marks_calculated", "1"); //ваши марки посчитали ;)
				int count = st.getInt("marks_already_have");
				if(count < 1)
				{
					htmltext = "31092-04.htm";
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
				}
				else if(count < 3)
					htmltext = "31092-05a.htm";
				else
					htmltext = "31092-05.htm";
			}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Daeger)
		{
			if(st.getPlayer().getClassId().getLevel() > 2)
			{
				st.exitCurrentQuest(true);
				return "completed";
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getRace() != Race.kamael)
					if(st.getPlayer().getLevel() < 39)
					{
						htmltext = "31435-00.htm";
						st.exitCurrentQuest(true);
					}
					else
						htmltext = "31435-01.htm";
				else
				{
					htmltext = "31435-kamael.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "31435-03.htm";
			else if(cond == 3)
				htmltext = "31435-04.htm";
			else if(cond == 4)
				htmltext = "31435-05.htm";
			else if(cond == 8)
				htmltext = "31435-06.htm";
			else if(cond > 8)
			{
				htmltext = "31435-08.htm";
				st.set("cond", "10");
				st.setState(STARTED);
			}
		}
		else if(npcId == Mark)
		{
			if(st.getPlayer().getClassId().getLevel() > 2)
			{
				st.exitCurrentQuest(true);
				return "completed";
			}
			else if(cond == 1)
				htmltext = "32487-01.htm";
			else if(cond == 2)
			{
				st.giveItems(BloodyClothFragment, 1);
				htmltext = "32487-03.htm";
				st.set("cond", "3");
			}
			else if(cond == 3)
			{
				htmltext = "32487-hurry.htm";
			}
			else if(cond == 7)
				htmltext = "32487-04.htm";
			else if(cond == 8)
				htmltext = "32487-05.htm";
		}
		else if(npcId == Helvetia)
		{
			if(cond == 4)
				htmltext = "30081-01.htm";
			else if(cond == 5)
			{
				st.set("cond", "6");
				htmltext = "30081-04.htm";
			}
			else if(cond == 6)
				htmltext = "30081-04.htm";
			else if(cond == 7)
				htmltext = "30081-05a.htm";
		}
		else if(npcId == BlackMarketeerOfMammon)
		{
			if(cond == 10)
			{
				if(st.getInt("marks_calculated") > 0) //ваши марки уже посчитали, а необходимые выдали.
				{
					int count = st.getInt("marks_already_have");
					if(count > 0 && count < 3) //специально для игроков, которые любят закрывать диалоги до их завершения.
						htmltext = "31092-05a.htm";
					else if(count == 3)
						htmltext = "31092-05.htm";
				}
				else
					htmltext = "31092-01.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1)
		{
			String pursuersay = NPCtext[1].replace("%player_name%", st.getPlayer().getName());
			Functions.npcSay(npc, Say2C.ALL, pursuersay);
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
			st.setState(STARTED);
			st.getPcSpawn().removeAllSpawn();
		}
	}
}