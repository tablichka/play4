package quests._222_TestOfDuelist;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест на вторую профессию Test Of Duelist
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _222_TestOfDuelist extends Quest
{
	//NPC
	private static final int Kaien = 30623;
	//Quest Items
	private static final int OrderGludio = 2763;
	private static final int OrderDion = 2764;
	private static final int OrderGiran = 2765;
	private static final int OrderOren = 2766;
	private static final int OrderAden = 2767;
	private static final int PunchersShard = 2768;
	private static final int NobleAntsFeeler = 2769;
	private static final int DronesChitin = 2770;
	private static final int DeadSeekerFang = 2771;
	private static final int OverlordNecklace = 2772;
	private static final int FetteredSoulsChain = 2773;
	private static final int ChiefsAmulet = 2774;
	private static final int EnchantedEyeMeat = 2775;
	private static final int TamrinOrcsRing = 2776;
	private static final int TamrinOrcsArrow = 2777;
	private static final int FinalOrder = 2778;
	private static final int ExcurosSkin = 2779;
	private static final int KratorsShard = 2780;
	private static final int GrandisSkin = 2781;
	private static final int TimakOrcsBelt = 2782;
	private static final int LakinsMace = 2783;
	//Items
	private static final int MarkOfDuelist = 2762;
	//MOB
	private static final int Puncher = 20085;
	private static final int NobleAntLeader = 20090;
	private static final int MarshStakatoDrone = 20234;
	private static final int DeadSeeker = 20202;
	private static final int BrekaOrcOverlord = 20270;
	private static final int FetteredSoul = 20552;
	private static final int LetoLizardmanOverlord = 20582;
	private static final int EnchantedMonstereye = 20564;
	private static final int TamlinOrc = 20601;
	private static final int TamlinOrcArcher = 20602;
	private static final int Excuro = 20214;
	private static final int Krator = 20217;
	private static final int Grandis = 20554;
	private static final int TimakOrcOverlord = 20588;
	private static final int Lakin = 20604;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{2, 0, Puncher, 0, PunchersShard, 10, 70, 1},
			{2, 0, NobleAntLeader, 0, NobleAntsFeeler, 10, 70, 1},
			{2, 0, MarshStakatoDrone, 0, DronesChitin, 10, 70, 1},
			{2, 0, DeadSeeker, 0, DeadSeekerFang, 10, 70, 1},
			{2, 0, BrekaOrcOverlord, 0, OverlordNecklace, 10, 70, 1},
			{2, 0, FetteredSoul, 0, FetteredSoulsChain, 10, 70, 1},
			{2, 0, LetoLizardmanOverlord, 0, ChiefsAmulet, 10, 70, 1},
			{2, 0, EnchantedMonstereye, 0, EnchantedEyeMeat, 10, 70, 1},
			{2, 0, TamlinOrc, 0, TamrinOrcsRing, 10, 70, 1},
			{2, 0, TamlinOrcArcher, 0, TamrinOrcsArrow, 10, 70, 1},
			{4, 0, Excuro, 0, ExcurosSkin, 3, 70, 1},
			{4, 0, Krator, 0, KratorsShard, 3, 70, 1},
			{4, 0, Grandis, 0, GrandisSkin, 3, 70, 1},
			{4, 0, TimakOrcOverlord, 0, TimakOrcsBelt, 3, 70, 1},
			{4, 0, Lakin, 0, LakinsMace, 3, 70, 1}};

	private static boolean QuestProf = true;

	public _222_TestOfDuelist()
	{
		super(222, "_222_TestOfDuelist", "Test Of Duelist");
		addStartNpc(Kaien);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
		addQuestItem(OrderGludio, OrderDion, OrderGiran, OrderOren, OrderAden, FinalOrder);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30623-04.htm") && st.getPlayer().getRace().ordinal() == 3)
			htmltext = "30623-05.htm";
		else if(event.equalsIgnoreCase("30623-07.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.giveItems(OrderGludio, 1);
			st.giveItems(OrderDion, 1);
			st.giveItems(OrderGiran, 1);
			st.giveItems(OrderOren, 1);
			st.giveItems(OrderAden, 1);
			if(!st.getPlayer().getVarB("dd3"))
			{
				st.giveItems(7562, 64);
				st.getPlayer().setVar("dd3", "1");
			}
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30623-16.htm"))
		{
			st.takeItems(PunchersShard, -1);
			st.takeItems(NobleAntsFeeler, -1);
			st.takeItems(DronesChitin, -1);
			st.takeItems(DeadSeekerFang, -1);
			st.takeItems(OverlordNecklace, -1);
			st.takeItems(FetteredSoulsChain, -1);
			st.takeItems(ChiefsAmulet, -1);
			st.takeItems(EnchantedEyeMeat, -1);
			st.takeItems(TamrinOrcsRing, -1);
			st.takeItems(TamrinOrcsArrow, -1);
			st.takeItems(OrderGludio, -1);
			st.takeItems(OrderDion, -1);
			st.takeItems(OrderGiran, -1);
			st.takeItems(OrderOren, -1);
			st.takeItems(OrderAden, -1);
			st.giveItems(FinalOrder, 1);
			st.set("cond", "4");
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
		if(npcId == Kaien)
			if(st.getQuestItemsCount(MarkOfDuelist) != 0)
			{
				htmltext = "completed";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getClassId().getId() == 0x01 || st.getPlayer().getClassId().getId() == 0x2f || st.getPlayer().getClassId().getId() == 0x13 || st.getPlayer().getClassId().getId() == 0x20)
				{
					if(st.getPlayer().getLevel() >= 39)
						htmltext = "30623-03.htm";
					else
					{
						htmltext = "30623-01.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
				{
					htmltext = "30623-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 2)
				htmltext = "30623-14.htm";
			else if(cond == 3)
				htmltext = "30623-13.htm";
			else if(cond == 4)
				htmltext = "30623-17.htm";
			else if(cond == 5)
			{
				st.takeItems(ExcurosSkin, -1);
				st.takeItems(KratorsShard, -1);
				st.takeItems(GrandisSkin, -1);
				st.takeItems(TimakOrcsBelt, -1);
				st.takeItems(LakinsMace, -1);
				st.takeItems(FinalOrder, -1);
				st.giveItems(MarkOfDuelist, 1);
				if(!st.getPlayer().getVarB("q222"))
				{
					st.addExpAndSp(474444, 30704);
					st.rollAndGive(57, 80000, 100);
					st.getPlayer().setVar("q222", "1");
				}
				htmltext = "30623-18.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
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
		if(cond == 2 && st.getQuestItemsCount(PunchersShard) >= 10 && st.getQuestItemsCount(NobleAntsFeeler) >= 10 && st.getQuestItemsCount(DronesChitin) >= 10 && st.getQuestItemsCount(DeadSeekerFang) >= 10 && st.getQuestItemsCount(OverlordNecklace) >= 10 && st.getQuestItemsCount(FetteredSoulsChain) >= 10 && st.getQuestItemsCount(ChiefsAmulet) >= 10 && st.getQuestItemsCount(EnchantedEyeMeat) >= 10 && st.getQuestItemsCount(TamrinOrcsRing) >= 10 && st.getQuestItemsCount(TamrinOrcsArrow) >= 10)
		{
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(cond == 4 && st.getQuestItemsCount(ExcurosSkin) >= 3 && st.getQuestItemsCount(KratorsShard) >= 3 && st.getQuestItemsCount(LakinsMace) >= 3 && st.getQuestItemsCount(GrandisSkin) >= 3 && st.getQuestItemsCount(TimakOrcsBelt) >= 3)
		{
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "5");
			st.setState(STARTED);
		}
	}
}