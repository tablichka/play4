package quests._213_TrialOfSeeker;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест на вторую профессию Trial Of Seeker
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _213_TrialOfSeeker extends Quest
{
	//NPC
	private static final int Dufner = 30106;
	private static final int Terry = 30064;
	private static final int Viktor = 30684;
	private static final int Marina = 30715;
	private static final int Brunon = 30526;
	//Quest Item
	private static final int DufnersLetter = 2647;
	private static final int Terrys1stOrder = 2648;
	private static final int Terrys2ndOrder = 2649;
	private static final int TerrysLetter = 2650;
	private static final int ViktorsLetter = 2651;
	private static final int HawkeyesLetter = 2652;
	private static final int MysteriousRunestone = 2653;
	private static final int OlMahumRunestone = 2654;
	private static final int TurekRunestone = 2655;
	private static final int AntRunestone = 2656;
	private static final int TurakBugbearRunestone = 2657;
	private static final int TerrysBox = 2658;
	private static final int ViktorsRequest = 2659;
	private static final int MedusaScales = 2660;
	private static final int ShilensRunestone = 2661;
	private static final int AnalysisRequest = 2662;
	private static final int MarinasLetter = 2663;
	private static final int ExperimentTools = 2664;
	private static final int AnalysisResult = 2665;
	private static final int Terrys3rdOrder = 2666;
	private static final int ListOfHost = 2667;
	private static final int AbyssRunestone1 = 2668;
	private static final int AbyssRunestone2 = 2669;
	private static final int AbyssRunestone3 = 2670;
	private static final int AbyssRunestone4 = 2671;
	private static final int TerrysReport = 2672;
	private static final int MarkofSeeker = 2673;
	//MOBs
	private static final int NeerGhoulBerserker = 20198;
	private static final int OlMahumCaptain = 20211;
	private static final int TurekOrcWarlord = 20495;
	private static final int AntCaptain = 20080;
	private static final int TurakBugbearWarrior = 20249;
	private static final int Medusa = 20158;
	private static final int MarshStakatoDrone = 20234;
	private static final int BrekaOrcOverlord = 20270;
	private static final int AntWarriorCaptain = 20088;
	private static final int LetoLizardmanWarrior = 20580;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{2, 3, NeerGhoulBerserker, Terrys1stOrder, MysteriousRunestone, 1, 10, 1},
			{4, 0, OlMahumCaptain, Terrys2ndOrder, OlMahumRunestone, 1, 20, 1},
			{4, 0, TurekOrcWarlord, Terrys2ndOrder, TurekRunestone, 1, 20, 1},
			{4, 0, AntCaptain, Terrys2ndOrder, AntRunestone, 1, 20, 1},
			{4, 0, TurakBugbearWarrior, Terrys2ndOrder, TurakBugbearRunestone, 1, 20, 1},
			{9, 10, Medusa, ViktorsRequest, MedusaScales, 10, 30, 1},
			{16, 0, MarshStakatoDrone, ListOfHost, AbyssRunestone1, 1, 25, 1},
			{16, 0, BrekaOrcOverlord, ListOfHost, AbyssRunestone2, 1, 25, 1},
			{16, 0, AntWarriorCaptain, ListOfHost, AbyssRunestone3, 1, 25, 1},
			{16, 0, LetoLizardmanWarrior, ListOfHost, AbyssRunestone4, 1, 25, 1}};

	private static boolean QuestProf = true;

	public _213_TrialOfSeeker()
	{
		super(213, "_213_TrialOfSeeker", "Trial Of Seeker");

		addStartNpc(Dufner);

		addTalkId(Dufner);
		addTalkId(Terry);
		addTalkId(Viktor);
		addTalkId(Marina);
		addTalkId(Brunon);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);

		addQuestItem(DufnersLetter,
				Terrys1stOrder,
				Terrys2ndOrder,
				TerrysLetter,
				TerrysBox,
				ViktorsLetter,
				ViktorsRequest,
				HawkeyesLetter,
				ShilensRunestone,
				AnalysisRequest,
				MarinasLetter,
				ExperimentTools,
				AnalysisResult,
				ListOfHost,
				Terrys3rdOrder,
				TerrysReport,
				MysteriousRunestone,
				OlMahumRunestone,
				TurekRunestone,
				AntRunestone,
				TurakBugbearRunestone,
				MedusaScales,
				AbyssRunestone1,
				AbyssRunestone2,
				AbyssRunestone3,
				AbyssRunestone4);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30106-05.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(DufnersLetter, 1);
			if(!st.getPlayer().getVarB("dd1"))
			{
				st.giveItems(7562, 64);
				st.getPlayer().setVar("dd1", "1");
			}
		}
		else if(event.equalsIgnoreCase("30064-03.htm"))
		{
			st.giveItems(Terrys1stOrder, 1);
			st.takeItems(DufnersLetter, -1);
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30064-07.htm"))
		{
			st.takeItems(Terrys1stOrder, -1);
			st.takeItems(MysteriousRunestone, -1);
			st.giveItems(Terrys2ndOrder, 1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30064-10.htm"))
		{
			st.takeItems(Terrys2ndOrder, -1);
			st.takeItems(OlMahumRunestone, -1);
			st.takeItems(TurekRunestone, -1);
			st.takeItems(AntRunestone, -1);
			st.takeItems(TurakBugbearRunestone, -1);
			st.giveItems(TerrysLetter, 1);
			st.giveItems(TerrysBox, 1);
			st.set("cond", "6");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30684-05.htm"))
		{
			st.takeItems(TerrysLetter, -1);
			st.giveItems(ViktorsLetter, 1);
			st.set("cond", "7");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30684-11.htm"))
		{
			st.takeItems(TerrysLetter, -1);
			st.takeItems(TerrysBox, -1);
			st.takeItems(HawkeyesLetter, -1);
			st.giveItems(ViktorsRequest, 1);
			st.set("cond", "9");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30684-15.htm"))
		{
			st.takeItems(ViktorsRequest, -1);
			st.takeItems(MedusaScales, -1);
			st.giveItems(ShilensRunestone, 1);
			st.giveItems(AnalysisRequest, 1);
			st.set("cond", "11");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30715-02.htm"))
		{
			st.takeItems(ShilensRunestone, -1);
			st.takeItems(AnalysisRequest, -1);
			st.giveItems(MarinasLetter, 1);
			st.set("cond", "12");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30715-05.htm"))
		{
			st.takeItems(ExperimentTools, 1);
			st.giveItems(AnalysisResult, 1);
			st.set("cond", "14");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30064-18.htm"))
			if(st.getPlayer().getLevel() < 36)
			{
				htmltext = "30064-17.htm";
				st.takeItems(AnalysisResult, -1);
				st.giveItems(Terrys3rdOrder, 1);
			}
			else
			{
				htmltext = "30064-18.htm";
				st.giveItems(ListOfHost, 1);
				st.takeItems(AnalysisResult, -1);
				st.set("cond", "16");
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
		if(npcId == Dufner)
		{
			if(st.getQuestItemsCount(MarkofSeeker) != 0)
			{
				htmltext = "completed";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated() && st.getQuestItemsCount(TerrysReport) == 0)
			{
				if(st.getPlayer().getClassId().ordinal() == 0x07 || st.getPlayer().getClassId().ordinal() == 0x16 || st.getPlayer().getClassId().ordinal() == 0x23)
					if(st.getPlayer().getLevel() < 35)
					{
						htmltext = "30630-02.htm";
						st.exitCurrentQuest(true);
					}
					else
						htmltext = "30106-03.htm";
				else
				{
					htmltext = "30106-01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(st.getQuestItemsCount(DufnersLetter) == 1 && st.getQuestItemsCount(TerrysReport) == 0)
				htmltext = "30106-06.htm";
			else if(st.getQuestItemsCount(DufnersLetter) == 0 && st.getQuestItemsCount(TerrysReport) == 0)
				htmltext = "30106-07.htm";
			else if(st.getQuestItemsCount(TerrysReport) != 0)
			{
				if(!st.getPlayer().getVarB("q213"))
				{
					st.addExpAndSp(514739, 33384);
					st.rollAndGive(57, 93803, 100);
					st.getPlayer().setVar("q213", "1");
				}
				htmltext = "30106-08.htm";
				st.playSound(SOUND_FINISH);
				st.takeItems(TerrysReport, -1);
				st.giveItems(MarkofSeeker, 1);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == Terry)
		{
			if(cond == 1)
				htmltext = "30064-01.htm";
			else if(cond == 2)
				htmltext = "30064-04.htm";
			else if(cond == 3 && st.getQuestItemsCount(MysteriousRunestone) == 0)
			{
				htmltext = "30064-04.htm";
				st.set("cond", "2");
			}
			else if(cond == 3)
				htmltext = "30064-05.htm";
			else if(cond == 4)
				htmltext = "30064-07.htm";
			else if(cond == 5 && st.getQuestItemsCount(OlMahumRunestone) != 0 && st.getQuestItemsCount(TurekRunestone) != 0 && st.getQuestItemsCount(AntRunestone) != 0 && st.getQuestItemsCount(TurakBugbearRunestone) != 0)
				htmltext = "30064-09.htm";
			else if(cond == 5)
			{
				htmltext = "30064-07.htm";
				st.set("cond", "4");
			}
			else if(cond == 6)
				htmltext = "30064-11.htm";
			else if(cond == 7)
			{
				st.takeItems(ViktorsLetter, -1);
				st.giveItems(HawkeyesLetter, 1);
				htmltext = "30064-12.htm";
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else if(cond == 8)
				htmltext = "30064-13.htm";
			else if(cond > 8 && cond < 14)
				htmltext = "30064-14.htm";
			else if(cond == 14 && st.getQuestItemsCount(AnalysisResult) > 0)
				htmltext = "30064-15.htm";
			else if((cond == 14 || cond == 15) && st.getQuestItemsCount(Terrys3rdOrder) > 0)
			{
				if(st.getPlayer().getLevel() < 36)
					htmltext = "30064-20.htm";
				else
				{
					htmltext = "30064-21.htm";
					st.takeItems(Terrys3rdOrder, -1);
					st.giveItems(ListOfHost, 1);
					st.set("cond", "16");
					st.setState(STARTED);
				}
			}
			else if(cond == 15 || cond == 16) //15 конда пока нету, и хз как он получается
				htmltext = "30064-22.htm";
			else if(cond == 17)
				if(st.getQuestItemsCount(AbyssRunestone1) != 0 && st.getQuestItemsCount(AbyssRunestone2) != 0 && st.getQuestItemsCount(AbyssRunestone3) != 0 && st.getQuestItemsCount(AbyssRunestone4) != 0)
				{
					htmltext = "30064-23.htm";
					st.takeItems(ListOfHost, -1);
					st.takeItems(AbyssRunestone1, -1);
					st.takeItems(AbyssRunestone2, -1);
					st.takeItems(AbyssRunestone3, -1);
					st.takeItems(AbyssRunestone4, -1);
					st.giveItems(TerrysReport, 1);
					st.set("cond", "0"); //Тут непонятки, в клиенте нету когда 18, а последний 17 говорит что нада идти к Терри, но после него есть еще один пункт.  Нужна проверка с офф сервера.
				}
				else
				{
					htmltext = "30064-22.htm";
					st.set("cond", "16");
				}
		}
		else if(npcId == Viktor)
		{
			if(cond == 6)
				htmltext = "30684-01.htm";
			else if(cond == 8)
				htmltext = "30684-12.htm";
			else if(cond == 9)
				htmltext = "30684-13.htm";
			else if(cond == 10 && st.getQuestItemsCount(MedusaScales) >= 10)
				htmltext = "30684-14.htm";
			else if(cond == 10)
			{
				st.set("cond", "9");
				htmltext = "30684-13.htm";
			}
		}
		else if(npcId == Marina)
		{
			if(cond == 11)
				htmltext = "30715-01.htm";
			else if(cond == 12)
				htmltext = "30715-03.htm";
			else if(cond == 13)
				htmltext = "30715-04.htm";
			else if(cond > 13)
				htmltext = "30715-06.htm";

		}
		else if(npcId == Brunon)
			if(cond == 12)
			{
				htmltext = "30526-01.htm";
				st.takeItems(MarinasLetter, 1);
				st.giveItems(ExperimentTools, 1);
				st.set("cond", "13");
				st.setState(STARTED);
			}
			else if(cond == 13)
				htmltext = "30526-02.htm";
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
		if(cond == 4 && st.getQuestItemsCount(OlMahumRunestone) != 0 && st.getQuestItemsCount(TurekRunestone) != 0 && st.getQuestItemsCount(AntRunestone) != 0 && st.getQuestItemsCount(TurakBugbearRunestone) != 0)
		{
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(cond == 16 && st.getQuestItemsCount(AbyssRunestone1) != 0 && st.getQuestItemsCount(AbyssRunestone2) != 0 && st.getQuestItemsCount(AbyssRunestone3) != 0 && st.getQuestItemsCount(AbyssRunestone4) != 0)
		{
			st.set("cond", "17");
			st.setState(STARTED);
		}
	}
}