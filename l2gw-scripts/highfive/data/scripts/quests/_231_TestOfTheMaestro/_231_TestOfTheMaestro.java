package quests._231_TestOfTheMaestro;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.L2Spawn;

/**
 * Квест на вторую профессию Test Of The Maestro
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _231_TestOfTheMaestro extends Quest
{
	//NPC
	private static final int Lockirin = 30531;
	private static final int Balanki = 30533;
	private static final int Arin = 30536;
	private static final int Filaur = 30535;
	private static final int Spiron = 30532;
	private static final int Croto = 30671;
	private static final int Kamur = 30675;
	private static final int Dubabah = 30672;
	private static final int Toma = 30556;
	private static final int Lorain = 30673;
	//Quest Items
	private static final int RecommendationOfBalanki = 2864;
	private static final int RecommendationOfFilaur = 2865;
	private static final int RecommendationOfArin = 2866;
	private static final int LetterOfSolderDetachment = 2868;
	private static final int PaintOfKamuru = 2869;
	private static final int NecklaceOfKamuru = 2870;
	private static final int PaintOfTeleportDevice = 2871;
	private static final int TeleportDevice = 2872;
	private static final int ArchitectureOfCruma = 2873;
	private static final int ReportOfCruma = 2874;
	private static final int IngredientsOfAntidote = 2875;
	private static final int StingerWaspNeedle = 2876;
	private static final int MarshSpidersWeb = 2877;
	private static final int BloodOfLeech = 2878;
	private static final int BrokenTeleportDevice = 2916;
	//Items
	private static final int DD = 7562;
	private static final int MarkOfMaestro = 2867;
	//MOB
	private static final int QuestMonsterEvilEyeLord = 27133;
	private static final int GiantMistLeech = 20225;
	private static final int StingerWasp = 20229;
	private static final int MarshSpider = 20233;
	private static final int KingBugbear = 20150;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{4, 5, QuestMonsterEvilEyeLord, 0, NecklaceOfKamuru, 1, 100, 1},
			{13, 0, GiantMistLeech, 0, BloodOfLeech, 10, 100, 1},
			{13, 0, StingerWasp, 0, StingerWaspNeedle, 10, 100, 1},
			{13, 0, MarshSpider, 0, MarshSpidersWeb, 10, 100, 1}};

	private static boolean QuestProf = true;

	public _231_TestOfTheMaestro()
	{
		super(231, "_231_TestOfTheMaestro", "Test Of The Maestro");
		addStartNpc(Lockirin);
		addTalkId(Balanki);
		addTalkId(Arin);
		addTalkId(Filaur);
		addTalkId(Spiron);
		addTalkId(Croto);
		addTalkId(Kamur);
		addTalkId(Dubabah);
		addTalkId(Toma);
		addTalkId(Lorain);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
		addQuestItem(PaintOfKamuru,
				LetterOfSolderDetachment,
				PaintOfTeleportDevice,
				BrokenTeleportDevice,
				TeleportDevice,
				ArchitectureOfCruma,
				IngredientsOfAntidote,
				RecommendationOfBalanki,
				RecommendationOfFilaur,
				RecommendationOfArin,
				ReportOfCruma);
	}

	public void recommendationCount(QuestState st)
	{
		if(st.getQuestItemsCount(RecommendationOfArin) != 0 && st.getQuestItemsCount(RecommendationOfFilaur) != 0 && st.getQuestItemsCount(RecommendationOfBalanki) != 0)
			st.set("cond", "17");
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30531-04.htm"))
		{
			if(!st.getPlayer().getVarB("dd3"))
			{
				st.giveItems(DD, 64);
				st.getPlayer().setVar("dd3", "1");
			}
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if(event.equalsIgnoreCase("30533-02.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30671-02.htm"))
		{
			st.giveItems(PaintOfKamuru, 1);
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30556-05.htm"))
		{
			st.takeItems(PaintOfTeleportDevice, -1);
			st.giveItems(BrokenTeleportDevice, 1);
			st.set("cond", "9");
			st.setState(STARTED);
			st.getPlayer().teleToLocation(140352, -194133, -2028);
			L2Spawn spawn = null;
			if(spawn == null)
			{
				st.getPcSpawn().addSpawn(KingBugbear, 140244, -194134, -3160);
				st.getPcSpawn().addSpawn(KingBugbear, 140487, -194219, -3155);
				st.getPcSpawn().addSpawn(KingBugbear, 140390, -194019, -3206);
				for(L2Spawn spawnBugbear : st.getPcSpawn().getSpawns())
				{
					L2NpcInstance Bugbear = spawnBugbear.getLastSpawn();
					Bugbear.addDamageHate(st.getPlayer(), 0, 999);
					Bugbear.getAI().setIntention(AI_INTENTION_ATTACK, st.getPlayer());
				}
			}
		}
		else if(event.equalsIgnoreCase("30673-04.htm"))
		{
			st.takeItems(BloodOfLeech, -1);
			st.takeItems(StingerWaspNeedle, -1);
			st.takeItems(MarshSpidersWeb, -1);
			st.takeItems(IngredientsOfAntidote, -1);
			st.giveItems(ReportOfCruma, 1);
			st.set("cond", "15");
			st.setState(STARTED);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Lockirin)
		{
			if(st.getQuestItemsCount(MarkOfMaestro) != 0)
			{
				htmltext = "completed";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getClassId().getId() == 0x38)
				{
					if(st.getPlayer().getLevel() >= 39)
						htmltext = "30531-03.htm";
					else
					{
						htmltext = "30531-01.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				{
					htmltext = "30531-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond >= 1 && cond <= 16)
				htmltext = "30531-05.htm";
			else if(cond == 17)
			{
				if(!st.getPlayer().getVarB("q231"))
				{
					st.addExpAndSp(1029122, 70620);
					st.rollAndGive(57, 186077, 100);
					st.getPlayer().setVar("q231", "1");
				}
				htmltext = "30531-06.htm";
				st.takeItems(RecommendationOfBalanki, -1);
				st.takeItems(RecommendationOfFilaur, -1);
				st.takeItems(RecommendationOfArin, -1);
				st.giveItems(MarkOfMaestro, 1);
				st.playSound("ItemSound.quest_finish");
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == Balanki)
		{
			if((cond == 1 || cond == 11 || cond == 16) && st.getQuestItemsCount(RecommendationOfBalanki) == 0)
				htmltext = "30533-01.htm";
			else if(cond == 2)
				htmltext = "30533-03.htm";
			else if(cond == 6)
			{
				st.takeItems(LetterOfSolderDetachment, -1);
				st.giveItems(RecommendationOfBalanki, 1);
				htmltext = "30533-04.htm";
				st.set("cond", "7");
				recommendationCount(st);
				st.setState(STARTED);
			}
			else if(cond == 7 || cond == 17)
				htmltext = "30533-05.htm";
		}
		else if(npcId == Arin)
		{
			if((cond == 1 || cond == 7 || cond == 16) && st.getQuestItemsCount(RecommendationOfArin) == 0)
			{
				st.giveItems(PaintOfTeleportDevice, 1);
				htmltext = "30536-01.htm";
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else if(cond == 8)
				htmltext = "30536-02.htm";
			else if(cond == 10)
			{
				st.takeItems(TeleportDevice, -1);
				st.giveItems(RecommendationOfArin, 1);
				htmltext = "30536-03.htm";
				st.set("cond", "11");
				recommendationCount(st);
				st.setState(STARTED);
			}
			else if(cond == 11 || cond == 17)
				htmltext = "30536-04.htm";
		}
		else if(npcId == Filaur)
		{
			if((cond == 1 || cond == 7 || cond == 11) && st.getQuestItemsCount(RecommendationOfFilaur) == 0)
			{
				st.giveItems(ArchitectureOfCruma, 1);
				htmltext = "30535-01.htm";
				st.set("cond", "12");
				st.setState(STARTED);
			}
			else if(cond == 12)
				htmltext = "30535-02.htm";
			else if(cond == 15)
			{
				st.takeItems(ReportOfCruma, 1);
				st.giveItems(RecommendationOfFilaur, 1);
				st.set("cond", "16");
				htmltext = "30535-03.htm";
				recommendationCount(st);
				st.setState(STARTED);
			}
			else if(cond > 15)
				htmltext = "30535-04.htm";
		}
		else if(npcId == Croto)
		{
			if(cond == 2)
				htmltext = "30671-01.htm";
			else if(cond == 3)
				htmltext = "30671-03.htm";
			else if(cond == 5)
			{
				st.takeItems(NecklaceOfKamuru, -1);
				st.takeItems(PaintOfKamuru, -1);
				st.giveItems(LetterOfSolderDetachment, 1);
				htmltext = "30671-04.htm";
				st.set("cond", "6");
				st.setState(STARTED);
			}
			else if(cond == 6)
				htmltext = "30671-05.htm";
		}
		else if(npcId == Dubabah && cond == 3)
			htmltext = "30672-01.htm";
		else if(npcId == Kamur && cond == 3)
		{
			htmltext = "30675-01.htm";
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(npcId == Toma)
		{
			if(cond == 8)
				htmltext = "30556-01.htm";
			else if(cond == 9)
			{
				st.takeItems(BrokenTeleportDevice, -1);
				st.giveItems(TeleportDevice, 5);
				htmltext = "30556-06.htm";
				st.set("cond", "10");
				st.setState(STARTED);
			}
			else if(cond == 10)
				htmltext = "30556-07.htm";
		}
		else if(npcId == Lorain)
		{
			if(cond == 12)
			{
				st.takeItems(ArchitectureOfCruma, -1);
				st.giveItems(IngredientsOfAntidote, 1);
				st.set("cond", "13");
				htmltext = "30673-01.htm";
			}
			else if(cond == 13)
				htmltext = "30673-02.htm";
			else if(cond == 14)
				htmltext = "30673-03.htm";
			else if(cond == 15)
				htmltext = "30673-05.htm";
		}
		else if(npcId == Spiron && (cond == 1 || cond == 7 || cond == 11 || cond == 16))
			htmltext = "30532-01.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
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
		if(cond == 13 && st.getQuestItemsCount(BloodOfLeech) >= 10 && st.getQuestItemsCount(StingerWaspNeedle) >= 10 && st.getQuestItemsCount(MarshSpidersWeb) >= 10)
		{
			st.set("cond", "14");
			st.setState(STARTED);
		}
	}
}