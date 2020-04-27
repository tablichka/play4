package quests._215_TrialOfThePilgrim;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест на вторую профессию Trial Of Pilgrim
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _215_TrialOfThePilgrim extends Quest
{
	//NPC
	private static final int Santiago = 30648;
	private static final int Tanapi = 30571;
	private static final int Martankus = 30649;
	private static final int Twinklerock = 30550;
	private static final int Dorf = 30651;
	private static final int Primos = 30117;
	private static final int Petron = 30036;
	private static final int Andellia = 30362;
	private static final int Uruha = 30652;
	private static final int Casian = 30612;
	//Quest Items
	private static final int VoucherOfTrial = 2723;
	private static final int EssenceOfFlame = 2725;
	private static final int SpiritOfFlame = 2724;
	private static final int TagOfRumor = 2733;
	private static final int GrayBadge = 2727;
	private static final int HairOfNahir = 2729;
	private static final int PictureOfNahir = 2728;
	private static final int StatueOfEinhasad = 2730;
	private static final int DebrisOfWillow = 2732;
	private static final int BookOfDarkness = 2731;
	private static final int BookOfSage = 2722;
	//Items
	private static final int MarkOfPilgrim = 2721;
	//MOB
	private static final int LavaSalamander = 27116;
	private static final int Nahir = 27117;
	private static final int BlackWillow = 27118;
	//Other
	private static final int RewardExp = 629125;
	private static final int RewardSP = 40803;
	private static final int RewardAdena = 114649;

	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{3, 4, LavaSalamander, 0, EssenceOfFlame, 1, 30, 1},
			{10, 11, Nahir, 0, HairOfNahir, 1, 100, 1},
			{13, 14, BlackWillow, 0, DebrisOfWillow, 1, 20, 1}};

	private static boolean QuestProf = true;

	public _215_TrialOfThePilgrim()
	{
		super(215, "_215_TrialOfThePilgrim", "Trial Of The Pilgrim");

		addStartNpc(Santiago);

		addTalkId(Tanapi);
		addTalkId(Martankus);
		addTalkId(Twinklerock);
		addTalkId(Dorf);
		addTalkId(Primos);
		addTalkId(Petron);
		addTalkId(Andellia);
		addTalkId(Uruha);
		addTalkId(Casian);

		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}

		addQuestItem(VoucherOfTrial,
				SpiritOfFlame,
				TagOfRumor,
				GrayBadge,
				PictureOfNahir,
				StatueOfEinhasad,
				DebrisOfWillow,
				BookOfDarkness,
				BookOfSage);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30648-04.htm"))
		{
			st.giveItems(VoucherOfTrial, 1);
			st.set("cond", "1");
			if(!st.getPlayer().getVarB("dd1"))
			{
				st.giveItems(7562, 64);
				st.getPlayer().setVar("dd1", "1");
			}
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30649-04.htm"))
		{
			st.takeItems(EssenceOfFlame, -1);
			st.giveItems(SpiritOfFlame, 1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30362-05.htm"))
		{
			st.takeItems(BookOfDarkness, -1);
			st.set("cond", "16");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30362-04.htm"))
		{
			st.set("cond", "16");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30652-02.htm"))
		{
			st.takeItems(DebrisOfWillow, -1);
			st.giveItems(BookOfDarkness, 1);
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
		if(npcId == Santiago)
		{
			if(st.getQuestItemsCount(MarkOfPilgrim) > 0)
			{
				st.exitCurrentQuest(true);
				return "completed";
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getClassId().getId() == 0x0f || st.getPlayer().getClassId().getId() == 0x1d || st.getPlayer().getClassId().getId() == 0x2a || st.getPlayer().getClassId().getId() == 0x32)
				{
					if(st.getPlayer().getLevel() < 35)
					{
						htmltext = "30648-01.htm";
						st.exitCurrentQuest(true);
					}
					else
						htmltext = "30648-03.htm";
				}
				else
				{
					htmltext = "30648-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "30648-09.htm";
			else if(cond == 17)
			{
				st.takeItems(BookOfSage, -1);
				st.giveItems(MarkOfPilgrim, 1);
				htmltext = "30648-10.htm";
				if(!st.getPlayer().getVarB("q215"))
				{
					st.addExpAndSp(RewardExp, RewardSP);
					st.rollAndGive(57, RewardAdena, 100);
					st.getPlayer().setVar("q215", "1");
				}
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == Tanapi)
		{
			if(cond == 1)
			{
				st.takeItems(VoucherOfTrial, -1);
				htmltext = "30571-01.htm";
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else if(cond == 2)
				htmltext = "30571-02.htm";
			else if(cond == 5)
			{
				htmltext = "30571-03.htm";
				st.set("cond", "6");
				st.setState(STARTED);
			}
			else if(cond == 6)
				htmltext = "30571-03.htm";
		}
		else if(npcId == Martankus)
		{
			if(cond == 2)
			{
				htmltext = "30649-01.htm";
				st.set("cond", "3");
				st.setState(STARTED);
			}
			else if(cond == 3)
				htmltext = "30649-02.htm";
			else if(cond == 4)
				htmltext = "30649-03.htm";
		}
		else if(npcId == Twinklerock)
		{
			if(cond == 6)
			{
				st.giveItems(TagOfRumor, 1);
				htmltext = "30550-01.htm";
				st.set("cond", "7");
				st.setState(STARTED);
			}
			else if(cond == 7)
				htmltext = "30550-02.htm";
		}
		else if(npcId == Dorf)
		{
			if(cond == 7)
			{
				st.takeItems(TagOfRumor, -1);
				st.giveItems(GrayBadge, 1);
				htmltext = "30651-01.htm";
				st.set("cond", "8");
				st.setState(STARTED);
			}
			else if(cond == 8)
				htmltext = "30651-02.htm";
		}
		else if(npcId == Primos)
		{
			if(cond == 8)
			{
				htmltext = "30117-01.htm";
				st.set("cond", "9");
				st.setState(STARTED);
			}
			else if(cond == 9)
				htmltext = "30117-02.htm";
		}
		else if(npcId == Petron)
		{
			if(cond == 9)
			{
				st.giveItems(PictureOfNahir, 1);
				htmltext = "30036-01.htm";
				st.set("cond", "10");
				st.setState(STARTED);
			}
			else if(cond == 10)
				htmltext = "30036-02.htm";
			else if(cond == 11)
			{
				st.takeItems(PictureOfNahir, -1);
				st.takeItems(HairOfNahir, -1);
				st.giveItems(StatueOfEinhasad, 1);
				htmltext = "30036-03.htm";
				st.set("cond", "12");
				st.setState(STARTED);
			}
			else if(cond == 12)
				htmltext = "30036-04.htm";
		}
		else if(npcId == Andellia)
		{
			if(cond == 12)
			{
				htmltext = "30362-01.htm";
				st.set("cond", "13");
				st.setState(STARTED);
			}
			else if(cond == 13)
				htmltext = "30362-02.htm";
			else if(cond == 15)
				htmltext = "30362-03.htm";
			else if(cond == 16)
				htmltext = "30362-06.htm";
		}
		else if(npcId == Uruha)
		{
			if(cond == 14)
				htmltext = "30652-01.htm";
			else if(cond == 15)
				htmltext = "30652-03.htm";
		}
		else if(npcId == Casian)
		{
			if(cond == 16)
			{
				st.takeItems(BookOfDarkness, -1);
				st.takeItems(GrayBadge, -1);
				st.takeItems(SpiritOfFlame, -1);
				st.takeItems(StatueOfEinhasad, 1);
				st.giveItems(BookOfSage, 1);
				htmltext = "30612-01.htm";
				st.set("cond", "17");
				st.setState(STARTED);
			}
			else if(cond == 17)
				htmltext = "30612-02.htm";
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
	}
}