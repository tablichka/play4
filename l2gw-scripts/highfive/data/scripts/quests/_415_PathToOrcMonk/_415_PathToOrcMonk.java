package quests._415_PathToOrcMonk;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Path To Orc Monk
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _415_PathToOrcMonk extends Quest
{
	//NPC
	private static final int Urutu = 30587;
	private static final int Rosheek = 30590;
	private static final int Kasman = 30501;
	private static final int Toruku = 30591;
	//Quest Items
	private static final int Pomegranate = 1593;
	private static final int KashaBearClaw = 1600;
	private static final int KashaBladeSpiderTalon = 1601;
	private static final int ScarletSalamanderScale = 1602;
	private static final int LeatherPouch1st = 1594;
	private static final int LeatherPouchFull1st = 1597;
	private static final int LeatherPouch2st = 1595;
	private static final int LeatherPouchFull2st = 1598;
	private static final int LeatherPouch3st = 1596;
	private static final int LeatherPouchFull3st = 1599;
	private static final int LeatherPouch4st = 1607;
	private static final int LeatherPouchFull4st = 1608;
	private static final int FierySpiritScroll = 1603;
	private static final int RosheeksLetter = 1604;
	private static final int GantakisLetterOfRecommendation = 1605;
	private static final int Fig = 1606;
	private static final int VukuOrcTusk = 1609;
	private static final int RatmanFang = 1610;
	private static final int LangkLizardmanTooth = 1611;
	private static final int FelimLizardmanTooth = 1612;
	private static final int IronWillScroll = 1613;
	private static final int TorukusLetter = 1614;
	//Items
	private static final int KhavatariTotem = 1615;
	//MOB
	private static final int KashaBear = 20479;
	private static final int KashaBladeSpider = 20478;
	private static final int ScarletSalamander = 20415;
	private static final int VukuOrcFighter = 20017;
	private static final int RatmanWarrior = 20359;
	private static final int LangkLizardmanWarrior = 20024;
	private static final int FelimLizardmanWarrior = 20014;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{2, 3, KashaBear, LeatherPouch1st, KashaBearClaw, 5, 70, 1},
			{4, 5, KashaBladeSpider, LeatherPouch2st, KashaBladeSpiderTalon, 5, 70, 1},
			{6, 7, ScarletSalamander, LeatherPouch3st, ScarletSalamanderScale, 5, 70, 1},
			{11, 0, VukuOrcFighter, LeatherPouch4st, VukuOrcTusk, 3, 70, 1},
			{11, 0, RatmanWarrior, LeatherPouch4st, RatmanFang, 3, 70, 1},
			{11, 0, LangkLizardmanWarrior, LeatherPouch4st, LangkLizardmanTooth, 3, 70, 1},
			{11, 0, FelimLizardmanWarrior, LeatherPouch4st, FelimLizardmanTooth, 3, 70, 1}};

	private static boolean QuestProf = true;

	public _415_PathToOrcMonk()
	{
		super(415, "_415_PathToOrcMonk", "Path To Orc Monk");
		addStartNpc(Urutu);
		addTalkId(Urutu);
		addTalkId(Rosheek);
		addTalkId(Kasman);
		addTalkId(Toruku);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
		addQuestItem(Pomegranate,
				LeatherPouch1st,
				LeatherPouchFull1st,
				LeatherPouch2st,
				LeatherPouchFull2st,
				LeatherPouch3st,
				LeatherPouchFull3st,
				Fig,
				FierySpiritScroll,
				RosheeksLetter,
				GantakisLetterOfRecommendation,
				LeatherPouch4st,
				LeatherPouchFull4st,
				IronWillScroll,
				TorukusLetter);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30587-06.htm"))
		{
			st.giveItems(Pomegranate, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Urutu)
		{
			if(st.getQuestItemsCount(KhavatariTotem) > 0)
			{
				htmltext = "30587-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(cond < 1)
			{
				if(st.getPlayer().getClassId().getId() != 0x2c)
				{
					if(st.getPlayer().getClassId().getId() == 0x2f)
						htmltext = "30587-02a.htm";
					else
						htmltext = "30587-02.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 18)
				{
					htmltext = "30587-03.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30587-01.htm";
			}
			else if(cond == 1)
				htmltext = "30587-07.htm";
			else if(cond >= 2 && cond <= 7)
				htmltext = "30587-08.htm";
			else if(cond == 8)
			{
				st.takeItems(RosheeksLetter, 1);
				st.giveItems(GantakisLetterOfRecommendation, 1);
				htmltext = "30587-09.htm";
				st.set("cond", "9");
				st.setState(STARTED);
			}
			else if(cond == 9)
				htmltext = "30587-10.htm";
			else if(cond >= 10)
				htmltext = "30587-11.htm";
		}
		else if(npcId == Rosheek)
		{
			if(cond == 1)
			{
				st.takeItems(Pomegranate, -1);
				st.giveItems(LeatherPouch1st, 1);
				htmltext = "30590-01.htm";
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else if(cond == 2)
				htmltext = "30590-02.htm";
			else if(cond == 3)
			{
				htmltext = "30590-03.htm";
				st.takeItems(LeatherPouchFull1st, -1);
				st.giveItems(LeatherPouch2st, 1);
				st.set("cond", "4");
				st.setState(STARTED);
			}
			else if(cond == 4)
				htmltext = "30590-04.htm";
			else if(cond == 5)
			{
				st.takeItems(LeatherPouchFull2st, -1);
				st.giveItems(LeatherPouch3st, 1);
				htmltext = "30590-05.htm";
				st.set("cond", "6");
				st.setState(STARTED);
			}
			else if(cond == 6)
				htmltext = "30590-06.htm";
			else if(cond == 7)
			{
				st.takeItems(LeatherPouchFull3st, -1);
				st.giveItems(FierySpiritScroll, 1);
				st.giveItems(RosheeksLetter, 1);
				htmltext = "30590-07.htm";
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else if(cond == 8)
				htmltext = "30590-08.htm";
			else if(cond == 9)
				htmltext = "30590-09.htm";
		}
		else if(npcId == Kasman)
		{
			if(cond == 9)
			{
				st.takeItems(GantakisLetterOfRecommendation, -1);
				st.giveItems(Fig, 1);
				htmltext = "30501-01.htm";
				st.set("cond", "10");
				st.setState(STARTED);
			}
			else if(cond == 10)
				htmltext = "30501-02.htm";
			else if(cond == 11 || cond == 12)
				htmltext = "30501-03.htm";
			else if(cond == 13)
			{
				st.takeItems(FierySpiritScroll, -1);
				st.takeItems(IronWillScroll, -1);
				st.takeItems(TorukusLetter, -1);
				htmltext = "30501-04.htm";
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(KhavatariTotem, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 25292);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 31990);
						else
							st.addExpAndSp(591724, 38688);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.showSocial(3);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == Toruku)
			if(cond == 10)
			{
				st.takeItems(Fig, -1);
				st.giveItems(LeatherPouch4st, 1);
				htmltext = "30591-01.htm";
				st.set("cond", "11");
				st.setState(STARTED);
			}
			else if(cond == 11)
				htmltext = "30591-02.htm";
			else if(cond == 12)
			{
				st.takeItems(LeatherPouchFull4st, -1);
				st.giveItems(IronWillScroll, 1);
				st.giveItems(TorukusLetter, 1);
				htmltext = "30591-03.htm";
				st.set("cond", "13");
				st.setState(STARTED);
			}
			else if(cond == 13)
				htmltext = "30591-04.htm";
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
		if(cond == 3 && st.getQuestItemsCount(LeatherPouchFull1st) == 0)
		{
			st.takeItems(KashaBearClaw, -1);
			st.takeItems(LeatherPouch1st, -1);
			st.giveItems(LeatherPouchFull1st, 1);
		}
		else if(cond == 5 && st.getQuestItemsCount(LeatherPouchFull2st) == 0)
		{
			st.takeItems(KashaBladeSpiderTalon, -1);
			st.takeItems(LeatherPouch2st, -1);
			st.giveItems(LeatherPouchFull2st, 1);
		}
		else if(cond == 7 && st.getQuestItemsCount(LeatherPouchFull3st) == 0)
		{
			st.takeItems(ScarletSalamanderScale, -1);
			st.takeItems(LeatherPouch3st, -1);
			st.giveItems(LeatherPouchFull3st, 1);
		}
		else if(cond == 11 && st.getQuestItemsCount(RatmanFang) >= 3 && st.getQuestItemsCount(LangkLizardmanTooth) >= 3 && st.getQuestItemsCount(FelimLizardmanTooth) >= 3 && st.getQuestItemsCount(VukuOrcTusk) >= 3)
		{
			st.takeItems(VukuOrcTusk, -1);
			st.takeItems(RatmanFang, -1);
			st.takeItems(LangkLizardmanTooth, -1);
			st.takeItems(FelimLizardmanTooth, -1);
			st.takeItems(LeatherPouch4st, -1);
			st.giveItems(LeatherPouchFull4st, 1);
			st.set("cond", "12");
			st.setState(STARTED);
		}
	}
}