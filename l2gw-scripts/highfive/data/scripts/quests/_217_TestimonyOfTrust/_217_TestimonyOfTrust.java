package quests._217_TestimonyOfTrust;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.quest.QuestTimer;

/**
 * Квест на вторую профессию Testimony Of Trust
 *
 * @author Sergey Ibryaev aka Artful
 */
public class _217_TestimonyOfTrust extends Quest
{
	//NPC
	private static final int Hollint = 30191;
	private static final int Asterios = 30154;
	private static final int Thifiell = 30358;
	private static final int Clayton = 30464;
	private static final int Seresin = 30657;
	private static final int LordKakai = 30565;
	private static final int Manakia = 30515;
	private static final int Lockirin = 30531;
	private static final int Nikola = 30621;
	private static final int Biotin = 30031;
	//Quest Items
	private static final int LetterToElf = 1558;
	private static final int LetterToDarkElf = 1556;
	private static final int OrderOfAsterios = 2745;
	private static final int BreathOfWinds = 2746;
	private static final int SeedOfVerdure = 2747;
	private static final int ScrollOfElfTrust = 2741;
	private static final int LetterFromThifiell = 2748;
	private static final int OrderOfClayton = 2755;
	private static final int GiantAphid = 2750;
	private static final int HoneyDew = 2753;
	private static final int BloodOfGuardianBasilisk = 2749;
	private static final int BasiliskPlasma = 2752;
	private static final int StakatosFluids = 2751;
	private static final int StakatoIchor = 2754;
	private static final int ScrollOfDarkElfTrust = 2740;
	private static final int LetterToSeresin = 2739;
	private static final int LetterToDwar = 2737;
	private static final int LetterToOrc = 2738;
	private static final int LetterToManakia = 2757;
	private static final int ParasiteOfLota = 2756;
	private static final int LetterOfManakia = 2758;
	private static final int ScrollOfOrcTrust = 2743;
	private static final int LetterToNikola = 2759;
	private static final int HeartstoneOfPorta = 2761;
	private static final int OrderOfNikola = 2760;
	private static final int ScrollOfDwarfTrust = 2742;
	private static final int RecommendationOfHollint = 2744;
	//Items
	private static final int MarkOfTrust = 2734;
	//MOB
	private static final int Lirein = 20036;
	private static final int LireinElder = 20044;
	private static final int LuellOfZephyrWinds = 27120;
	private static final int Dryad = 20013;
	private static final int DryadElder = 20019;
	private static final int ActeaOfVerdantWilds = 27121;
	private static final int AntRecruit = 20082;
	private static final int AntGuard = 20086;
	private static final int AntSoldier = 20087;
	private static final int AntPatrol = 20084;
	private static final int AntWarriorCaptain = 20088;
	private static final int GuardianBasilisk = 20550;
	private static final int MarshStakato = 20157;
	private static final int MarshStakatoWorker = 20230;
	private static final int MarshStakatoSoldier = 20232;
	private static final int MarshStakatoDrone = 20234;
	private static final int Windsus = 20553;
	private static final int Porta = 20213;
	//Other
	private static final int RewardExp = 695149;
	private static final int RewardSP = 46391;
	private static final int RewardAdena = 126106;

	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{14, 15, Windsus, 0, ParasiteOfLota, 10, 50, 1},
			{19, 20, Porta, 0, HeartstoneOfPorta, 1, 100, 1}};

	private static boolean QuestProf = true;

	public _217_TestimonyOfTrust()
	{
		super(217, "_217_TestimonyOfTrust", "Testimony Of Trust");

		addStartNpc(Hollint);
		addTalkId(Asterios);
		addTalkId(Thifiell);
		addTalkId(Clayton);
		addTalkId(Seresin);
		addTalkId(LordKakai);
		addTalkId(Manakia);
		addTalkId(Lockirin);
		addTalkId(Nikola);
		addTalkId(Biotin);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
		addKillId(Lirein);
		addKillId(LireinElder);
		addKillId(LuellOfZephyrWinds);
		addKillId(Dryad);
		addKillId(DryadElder);
		addKillId(MarshStakato);
		addKillId(MarshStakatoWorker);
		addKillId(MarshStakatoSoldier);
		addKillId(MarshStakatoDrone);
		addKillId(ActeaOfVerdantWilds);
		addKillId(GuardianBasilisk);
		addKillId(AntRecruit);
		addKillId(AntPatrol);
		addKillId(AntGuard);
		addKillId(AntSoldier);
		addKillId(AntWarriorCaptain);

		addQuestItem(LetterToElf,
				LetterToDarkElf,
				OrderOfAsterios,
				BreathOfWinds,
				SeedOfVerdure,
				ScrollOfElfTrust,
				LetterFromThifiell,
				OrderOfClayton,
				GiantAphid,
				HoneyDew,
				BloodOfGuardianBasilisk,
				BasiliskPlasma,
				StakatosFluids,
				StakatoIchor,
				ScrollOfDarkElfTrust,
				LetterToSeresin,
				LetterToDwar,
				LetterToOrc,
				LetterToManakia,
				LetterOfManakia,
				ScrollOfOrcTrust,
				LetterToNikola,
				OrderOfNikola,
				ScrollOfDwarfTrust,
				RecommendationOfHollint);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30191-04.htm"))
		{
			st.giveItems(LetterToElf, 1);
			st.giveItems(LetterToDarkElf, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			if(!st.getPlayer().getVarB("dd2"))
			{
				st.giveItems(7562, 64);
				st.getPlayer().setVar("dd2", "1");
			}
		}
		else if(event.equalsIgnoreCase("30154-03.htm"))
		{
			st.takeItems(LetterToElf, -1);
			st.giveItems(OrderOfAsterios, 1);
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30358-02.htm"))
		{
			st.takeItems(LetterToDarkElf, -1);
			st.giveItems(LetterFromThifiell, 1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30657-03.htm"))
		{
			st.takeItems(LetterToSeresin, -1);
			st.giveItems(LetterToOrc, 1);
			st.giveItems(LetterToDwar, 1);
			st.set("cond", "12");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30565-02.htm"))
		{
			st.takeItems(LetterToOrc, -1);
			st.giveItems(LetterToManakia, 1);
			st.set("cond", "13");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30515-02.htm"))
		{
			st.takeItems(LetterToManakia, -1);
			st.set("cond", "14");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30531-02.htm"))
		{
			st.takeItems(LetterToDwar, -1);
			st.giveItems(LetterToNikola, 1);
			st.set("cond", "18");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30621-02.htm"))
		{
			st.takeItems(LetterToNikola, -1);
			st.giveItems(OrderOfNikola, 1);
			st.set("cond", "19");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("LuellOfZephyrWinds_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(LuellOfZephyrWinds);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		else if(event.equalsIgnoreCase("ActeaOfVerdantWilds_Fail"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(ActeaOfVerdantWilds);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Hollint)
		{
			if(st.getQuestItemsCount(MarkOfTrust) > 0)
			{
				st.exitCurrentQuest(true);
				return "completed";
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getRace().ordinal() == 0)
					if(st.getPlayer().getLevel() >= 37)
						htmltext = "30191-03.htm";
					else
					{
						htmltext = "30191-01.htm";
						st.exitCurrentQuest(true);
					}
				else
				{
					htmltext = "30191-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "30191-08.htm";
			else if(cond == 8)
				htmltext = "30191-09.htm";
			else if(cond == 9)
			{
				st.takeItems(ScrollOfDarkElfTrust, -1);
				st.takeItems(ScrollOfElfTrust, -1);
				st.giveItems(LetterToSeresin, 1);
				htmltext = "30191-05.htm";
				st.set("cond", "10");
				st.setState(STARTED);
			}
			else if(cond == 22)
			{
				st.takeItems(ScrollOfDwarfTrust, 1);
				st.takeItems(ScrollOfOrcTrust, 1);
				st.giveItems(RecommendationOfHollint, 1);
				htmltext = "30191-06.htm";
				st.set("cond", "23");
				st.setState(STARTED);
			}
			else if(cond == 19)
				htmltext = "30191-07.htm";
		}
		else if(npcId == Asterios)
		{
			if(cond == 1)
				htmltext = "30154-01.htm";
			else if(cond == 2)
				htmltext = "30154-04.htm";
			else if(cond == 3)
			{
				st.takeItems(OrderOfAsterios, -1);
				st.takeItems(BreathOfWinds, -1);
				st.takeItems(SeedOfVerdure, -1);
				st.giveItems(ScrollOfElfTrust, 1);
				htmltext = "30154-05.htm";
				st.set("cond", "4");
				st.setState(STARTED);
			}
			else if(cond == 4)
				htmltext = "30154-06.htm";
		}
		else if(npcId == Thifiell)
		{
			if(cond == 4)
				htmltext = "30358-01.htm";
			else if(cond == 5)
				htmltext = "30358-03.htm";
			else if(cond == 7)
				htmltext = "30358-04.htm";
			else if(cond == 8)
			{
				st.takeItems(BasiliskPlasma, 1);
				st.takeItems(StakatoIchor, 1);
				st.takeItems(HoneyDew, 1);
				st.giveItems(ScrollOfDarkElfTrust, 1);
				htmltext = "30358-05.htm";
				st.set("cond", "9");
				st.setState(STARTED);
			}
		}
		else if(npcId == Clayton)
		{
			if(cond == 5)
			{
				st.takeItems(LetterFromThifiell, -1);
				st.giveItems(OrderOfClayton, 1);
				htmltext = "30464-01.htm";
				st.set("cond", "6");
				st.setState(STARTED);
			}
			else if(cond == 6)
				htmltext = "30464-02.htm";
			else if(cond == 7)
			{
				st.takeItems(OrderOfClayton, -1);
				htmltext = "30464-03.htm";
				st.set("cond", "8");
				st.setState(STARTED);
			}
		}
		else if(npcId == Seresin)
		{
			if(cond == 10 || cond == 11)
			{
				if(st.getPlayer().getLevel() < 38)
				{
					htmltext = "30657-02.htm";
					if(cond == 10)
					{
						st.set("cond", "11");
						st.setState(STARTED);
					}
				}
				else
					htmltext = "30657-01.htm";
			}
			else if(cond == 12)
				htmltext = "30657-04.htm";
			else if(cond == 18)
				htmltext = "30657-05.htm";
		}
		else if(npcId == LordKakai)
		{
			if(cond == 12)
				htmltext = "30565-01.htm";
			else if(cond == 13)
				htmltext = "30565-03.htm";
			else if(cond == 16)
			{
				htmltext = "30565-04.htm";
				st.takeItems(LetterOfManakia, -1);
				st.giveItems(ScrollOfOrcTrust, 1);
				st.set("cond", "17");
				st.setState(STARTED);
			}
			else if(cond >= 17)
				htmltext = "30565-05.htm";
		}
		else if(npcId == Manakia)
		{
			if(cond == 13)
				htmltext = "30515-01.htm";
			else if(cond == 14)
				htmltext = "30515-03.htm";
			else if(cond == 15)
			{
				st.takeItems(ParasiteOfLota, -1);
				st.giveItems(LetterOfManakia, 1);
				htmltext = "30515-04.htm";
				st.set("cond", "16");
				st.setState(STARTED);
			}
			else if(cond == 16)
				htmltext = "30515-05.htm";
		}
		else if(npcId == Lockirin)
		{
			if(cond == 17)
				htmltext = "30531-01.htm";
			else if(cond == 18)
				htmltext = "30531-03.htm";
			else if(cond == 21)
			{
				st.giveItems(ScrollOfDwarfTrust, 1);
				htmltext = "30531-04.htm";
				st.set("cond", "22");
				st.setState(STARTED);
			}
			else if(cond == 22)
				htmltext = "30531-05.htm";
		}
		else if(npcId == Nikola)
		{
			if(cond == 18)
				htmltext = "30621-01.htm";
			else if(cond == 19)
				htmltext = "30621-03.htm";
			else if(cond == 20)
			{
				st.takeItems(HeartstoneOfPorta, -1);
				st.takeItems(OrderOfNikola, 1);
				htmltext = "30621-04.htm";
				st.set("cond", "21");
				st.setState(STARTED);
			}
			else if(cond == 21)
				htmltext = "30621-05.htm";
		}
		else if(npcId == Biotin && cond == 23)
		{
			htmltext = "30031-01.htm";
			st.takeItems(RecommendationOfHollint, -1);
			st.giveItems(MarkOfTrust, 1);
			if(!st.getPlayer().getVarB("q217"))
			{
				st.addExpAndSp(RewardExp, RewardSP);
				st.rollAndGive(57, RewardAdena, 100);
				st.getPlayer().setVar("q217", "1");
			}
			st.playSound(SOUND_FINISH);
			st.unset("cond");
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(cond == 2)
		{
			if((npcId == Lirein || npcId == LireinElder) && st.getQuestItemsCount(BreathOfWinds) == 0 && Rnd.chance(15))
			{
				L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(LuellOfZephyrWinds);
				if(isQuest == null)
				{
					st.startQuestTimer("LuellOfZephyrWinds_Fail", 300000);
					st.getPcSpawn().addSpawn(LuellOfZephyrWinds);
					st.playSound(SOUND_BEFORE_BATTLE);
				}
				QuestTimer timer = st.getQuestTimer("LuellOfZephyrWinds_Fail");
				if(timer == null)
					st.startQuestTimer("LuellOfZephyrWinds_Fail", 300000);
			}
			else if(npcId == LuellOfZephyrWinds)
			{
				L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(LuellOfZephyrWinds);
				if(isQuest != null)
					isQuest.deleteMe();
				QuestTimer timer = st.getQuestTimer("LuellOfZephyrWinds_Fail");
				if(timer != null)
					timer.cancel();
				if(st.getQuestItemsCount(BreathOfWinds) == 0)
					if(st.getQuestItemsCount(SeedOfVerdure) > 0)
					{
						st.giveItems(BreathOfWinds, 1);
						st.set("cond", "3");
						st.setState(STARTED);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						st.giveItems(BreathOfWinds, 1);
						st.playSound(SOUND_ITEMGET);
					}
			}
			else if((npcId == Dryad || npcId == DryadElder) && st.getQuestItemsCount(SeedOfVerdure) == 0 && Rnd.chance(15))
			{
				L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(ActeaOfVerdantWilds);
				if(isQuest == null)
				{
					st.startQuestTimer("ActeaOfVerdantWilds_Fail", 300000);
					st.getPcSpawn().addSpawn(ActeaOfVerdantWilds);
					st.playSound(SOUND_BEFORE_BATTLE);
				}
				QuestTimer timer = st.getQuestTimer("ActeaOfVerdantWilds_Fail");
				if(timer == null)
					st.startQuestTimer("ActeaOfVerdantWilds_Fail", 300000);
			}
			else if(npcId == ActeaOfVerdantWilds)
			{
				L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(ActeaOfVerdantWilds);
				if(isQuest != null)
					isQuest.deleteMe();
				QuestTimer timer = st.getQuestTimer("ActeaOfVerdantWilds_Fail");
				if(timer != null)
					timer.cancel();
				if(st.getQuestItemsCount(SeedOfVerdure) == 0)
					if(st.getQuestItemsCount(BreathOfWinds) > 0)
					{
						st.giveItems(SeedOfVerdure, 1);
						st.set("cond", "3");
						st.setState(STARTED);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						st.giveItems(SeedOfVerdure, 1);
						st.playSound(SOUND_ITEMGET);
					}
			}
		}
		else if(cond == 6 && st.getQuestItemsCount(HoneyDew) == 0 && (npcId == AntRecruit || npcId == AntGuard || npcId == AntSoldier || npcId == AntPatrol || npcId == AntWarriorCaptain))
		{
			st.rollAndGiveLimited(GiantAphid, 1, 100, 9);
			if(st.getQuestItemsCount(GiantAphid) == 9)
			{
				st.takeItems(GiantAphid, -1);
				st.giveItems(HoneyDew, 1);
				st.playSound(SOUND_ITEMGET);
				if(st.getQuestItemsCount(StakatoIchor) != 0 && st.getQuestItemsCount(BasiliskPlasma) != 0 && st.getQuestItemsCount(HoneyDew) != 0)
				{
					st.set("cond", "7");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else
			{
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(cond == 6 && npcId == GuardianBasilisk && st.getQuestItemsCount(BasiliskPlasma) == 0)
		{
			st.rollAndGiveLimited(BloodOfGuardianBasilisk, 1, 100, 9);
			if(st.getQuestItemsCount(BloodOfGuardianBasilisk) == 9)
			{
				st.takeItems(BloodOfGuardianBasilisk, -1);
				st.giveItems(BasiliskPlasma, 1);
				st.playSound(SOUND_MIDDLE);
				if(st.getQuestItemsCount(StakatoIchor) != 0 && st.getQuestItemsCount(BasiliskPlasma) != 0 && st.getQuestItemsCount(HoneyDew) != 0)
				{
					st.set("cond", "7");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else
			{
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(cond == 6 && st.getQuestItemsCount(StakatoIchor) == 0 && (npcId == MarshStakato || npcId == MarshStakatoWorker || npcId == MarshStakatoSoldier || npcId == MarshStakatoDrone))
		{
			st.rollAndGiveLimited(StakatosFluids, 1, 100, 9);
			if(st.getQuestItemsCount(StakatosFluids) == 9)
			{
				st.takeItems(StakatosFluids, -1);
				st.giveItems(StakatoIchor, 1);
				st.playSound(SOUND_MIDDLE);
				if(st.getQuestItemsCount(StakatoIchor) != 0 && st.getQuestItemsCount(BasiliskPlasma) != 0 && st.getQuestItemsCount(HoneyDew) != 0)
				{
					st.set("cond", "7");
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else
			{
				st.playSound(SOUND_ITEMGET);
			}
		}
		for(int[] aDROPLIST_COND : DROPLIST_COND)
			if(cond == aDROPLIST_COND[0] && npcId == aDROPLIST_COND[2])
				if(aDROPLIST_COND[3] == 0 || st.getQuestItemsCount(aDROPLIST_COND[3]) > 0)
				{
					if(aDROPLIST_COND[5] == 0)
						st.rollAndGive(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6]);
					else if(st.rollAndGiveLimited(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6], aDROPLIST_COND[5]))
					{
						if(st.getQuestItemsCount(aDROPLIST_COND[4]) == aDROPLIST_COND[5] && aDROPLIST_COND[1] != cond && aDROPLIST_COND[1] != 0)
						{
							st.playSound(SOUND_MIDDLE);
							st.setCond(aDROPLIST_COND[1]);
							st.setState(STARTED);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
	}
}