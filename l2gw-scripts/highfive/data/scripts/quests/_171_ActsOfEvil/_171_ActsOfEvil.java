package quests._171_ActsOfEvil;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.model.quest.QuestTimer;

/**
 * Квест Acts Of Evil
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _171_ActsOfEvil extends Quest
{
	//NPC
	private static final int Alvah = 30381;
	private static final int Tyra = 30420;
	private static final int Arodin = 30207;
	private static final int Rolento = 30437;
	private static final int Neti = 30425;
	private static final int Burai = 30617;
	//Quest Item
	private static final int BladeMold = 4239;
	private static final int OlMahumCaptainHead = 4249;
	private static final int TyrasBill = 4240;
	private static final int RangerReportPart1 = 4241;
	private static final int RangerReportPart2 = 4242;
	private static final int RangerReportPart3 = 4243;
	private static final int RangerReportPart4 = 4244;
	private static final int WeaponsTradeContract = 4245;
	private static final int AttackDirectives = 4246;
	private static final int CertificateOfTheSilverScaleGuild = 4247;
	private static final int RolentoCargobox = 4248;
	//Items
	private static final int Adena = 57;
	//MOB
	private static final int TurekOrcArcher = 20496;
	private static final int TurekOrcSkirmisher = 20497;
	private static final int TurekOrcSupplier = 20498;
	private static final int TurekOrcFootman = 20499;
	private static final int TumranBugbear = 20062;
	private static final int OlMahumGeneral = 20438;
	private static final int OlMahumCaptain = 20066;
	private static final int OlMahumSupportTroop = 27190;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{2, 0, TurekOrcArcher, 0, BladeMold, 20, 50, 1},
			{2, 0, TurekOrcSkirmisher, 0, BladeMold, 20, 50, 1},
			{2, 0, TurekOrcSupplier, 0, BladeMold, 20, 50, 1},
			{2, 0, TurekOrcFootman, 0, BladeMold, 20, 50, 1},
			{10, 0, OlMahumGeneral, 0, OlMahumCaptainHead, 30, 50, 2},
			{10, 0, OlMahumCaptain, 0, OlMahumCaptainHead, 30, 50, 2}};

	//TumranBugbear Drop
	//# [REQUIRED, ITEM, CHANCE]	
	private static final int[][] TumranBugbear_DROPLIST = {
			{RangerReportPart1, RangerReportPart2, 20},
			{RangerReportPart2, RangerReportPart3, 20},
			{RangerReportPart3, RangerReportPart4, 20}};

	public _171_ActsOfEvil()
	{
		super(171, "_171_ActsOfEvil", "Acts Of Evil");

		addStartNpc(Alvah);
		addTalkId(Arodin);
		addTalkId(Tyra);
		addTalkId(Rolento);
		addTalkId(Neti);
		addTalkId(Burai);

		addKillId(TumranBugbear);
		addKillId(OlMahumGeneral);
		addKillId(OlMahumSupportTroop);

		addQuestItem(RolentoCargobox,
				TyrasBill,
				CertificateOfTheSilverScaleGuild,
				RangerReportPart1,
				RangerReportPart2,
				RangerReportPart3,
				RangerReportPart4,
				WeaponsTradeContract,
				AttackDirectives,
				BladeMold,
				OlMahumCaptainHead);

		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equals("30381-02.htm") && st.isCreated())
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30207-02.htm") && cond == 1)
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
		else if(event.equals("30381-04.htm") && cond == 4)
		{
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equals("30381-07.htm") && cond == 6)
		{
			st.set("cond", "7");
			st.setState(STARTED);
			st.takeItems(WeaponsTradeContract, -1);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equals("30437-03.htm") && cond == 8)
		{
			st.giveItems(RolentoCargobox, 1);
			st.giveItems(CertificateOfTheSilverScaleGuild, 1);
			st.set("cond", "9");
			st.setState(STARTED);
		}
		else if(event.equals("30617-04.htm") && cond == 9)
		{
			st.takeItems(CertificateOfTheSilverScaleGuild, -1);
			st.takeItems(AttackDirectives, -1);
			st.takeItems(RolentoCargobox, -1);
			st.set("cond", "10");
			st.setState(STARTED);
		}
		else if(event.equals("Wait1"))
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(OlMahumSupportTroop);
			if(isQuest != null)
				isQuest.deleteMe();
			QuestTimer timer = st.getQuestTimer("Wait1");
			if(timer != null)
				timer.cancel();
			return null;
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Alvah)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() <= 26)
				{
					htmltext = "30381-01a.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30381-01.htm";
			}
			else if(cond == 1)
				htmltext = "30381-02a.htm";
			else if(cond == 4)
				htmltext = "30381-03.htm";
			else if(cond == 5)
			{
				if(st.getQuestItemsCount(RangerReportPart1) > 0 && st.getQuestItemsCount(RangerReportPart2) > 0 && st.getQuestItemsCount(RangerReportPart3) > 0 && st.getQuestItemsCount(RangerReportPart4) > 0)
				{
					htmltext = "30381-05.htm";
					st.takeItems(RangerReportPart1, -1);
					st.takeItems(RangerReportPart2, -1);
					st.takeItems(RangerReportPart3, -1);
					st.takeItems(RangerReportPart4, -1);
					st.set("cond", "6");
					st.setState(STARTED);
				}
				else
					htmltext = "30381-04a.htm";
			}
			else if(cond == 6)
			{
				if(st.getQuestItemsCount(WeaponsTradeContract) > 0 && st.getQuestItemsCount(AttackDirectives) > 0)
					htmltext = "30381-06.htm";
				else
					htmltext = "30381-05a.htm";
			}
			else if(cond == 7)
				htmltext = "30381-07a.htm";
			else if(cond == 11)
			{
				htmltext = "30381-08.htm";
				st.rollAndGive(57, 95000, 100);
				st.addExpAndSp(159820, 9182);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == Arodin)
		{
			if(cond == 1)
				htmltext = "30207-01.htm";
			else if(cond == 2)
				htmltext = "30207-01a.htm";
			else if(cond == 3)
			{
				if(st.getQuestItemsCount(TyrasBill) > 0)
				{
					st.takeItems(TyrasBill, -1);
					htmltext = "30207-03.htm";
					st.set("cond", "4");
					st.setState(STARTED);
				}
				else
					htmltext = "30207-01a.htm";
			}
			else if(cond == 4)
				htmltext = "30207-03a.htm";
		}
		else if(npcId == Tyra)
		{
			if(cond == 2)
			{
				if(st.getQuestItemsCount(BladeMold) >= 20)
				{
					st.takeItems(BladeMold, -1);
					st.giveItems(TyrasBill, 1);
					htmltext = "30420-01.htm";
					st.set("cond", "3");
					st.setState(STARTED);
				}
				else
					htmltext = "30420-01b.htm";
			}
			else if(cond == 3)
				htmltext = "30420-01a.htm";
			else if(cond > 3)
				htmltext = "30420-02.htm";
		}
		else if(npcId == Neti)
		{
			if(cond == 7)
			{
				htmltext = "30425-01.htm";
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else if(cond == 8)
				htmltext = "30425-02.htm";
		}
		else if(npcId == Rolento)
		{
			if(cond == 8)
				htmltext = "30437-01.htm";
			else if(cond == 9)
				htmltext = "30437-03a.htm";
		}
		else if(npcId == Burai)
		{
			if(cond == 9 && st.getQuestItemsCount(CertificateOfTheSilverScaleGuild) > 0 && st.getQuestItemsCount(RolentoCargobox) > 0 && st.getQuestItemsCount(AttackDirectives) > 0)
				htmltext = "30617-01.htm";
			if(cond == 10)
				if(st.getQuestItemsCount(OlMahumCaptainHead) < 30)
					htmltext = "30617-04a.htm";
				else
				{
					htmltext = "30617-05.htm";
					st.giveItems(Adena, 8000);
					st.takeItems(OlMahumCaptainHead, -1);
					st.set("cond", "11");
					st.setState(STARTED);
					st.playSound(SOUND_ITEMGET);
				}
		}
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
		if(npcId == OlMahumSupportTroop)
		{
			L2NpcInstance isQuest = L2ObjectsStorage.getByNpcId(OlMahumSupportTroop);
			if(isQuest != null)
				isQuest.deleteMe();
		}
		else if(cond == 2 && Rnd.chance(10))
		{
			if(L2ObjectsStorage.getByNpcId(OlMahumSupportTroop) == null)
				st.getPcSpawn().addSpawn(OlMahumSupportTroop);
			else if(st.getQuestTimer("Wait1") == null)
				st.startQuestTimer("Wait1", 300000);
		}
		else if(cond == 5 && npcId == TumranBugbear)
		{
			for(int[] aTumranBugbear_DROPLIST : TumranBugbear_DROPLIST)
			{
				if(st.getQuestItemsCount(RangerReportPart1) < 1)
				{
					st.giveItems(RangerReportPart1, 1);
					st.playSound(SOUND_ITEMGET);
				}
				else if(Rnd.chance(aTumranBugbear_DROPLIST[2]) && st.getQuestItemsCount(aTumranBugbear_DROPLIST[0]) > 0 && st.getQuestItemsCount(aTumranBugbear_DROPLIST[1]) < 1)
				{
					st.giveItems(aTumranBugbear_DROPLIST[1], 1);
					if(st.getQuestItemsCount(RangerReportPart4) > 0)
					{
						st.playSound(SOUND_MIDDLE);
					}
					else
						st.playSound(SOUND_ITEMGET);
				}
			}
		}
		else if(cond == 6 && npcId == OlMahumGeneral)
		{
			if(st.getQuestItemsCount(WeaponsTradeContract) < 1 && Rnd.chance(10))
			{
				st.giveItems(WeaponsTradeContract, 1);
				if(st.getQuestItemsCount(AttackDirectives) > 0)
				{
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
			else if(st.getQuestItemsCount(AttackDirectives) < 1 && Rnd.chance(10))
			{
				st.giveItems(AttackDirectives, 1);
				if(st.getQuestItemsCount(WeaponsTradeContract) > 0)
				{
					st.playSound(SOUND_MIDDLE);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}