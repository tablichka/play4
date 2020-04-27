package quests._419_GetaPet;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * Квест Get a Pet
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _419_GetaPet extends Quest
{
	//NPC
	private static final int PET_MANAGER_MARTIN = 30731;
	private static final int GK_BELLA = 30256;
	private static final int MC_ELLIE = 30091;
	private static final int GD_METTY = 30072;

	//Mobs
	//1 humans
	private static final int SPIDER_H1 = 20103; // Giant Spider
	private static final int SPIDER_H2 = 20106; // Talon Spider
	private static final int SPIDER_H3 = 20108; // Blade Spider
	//2 elves
	private static final int SPIDER_LE1 = 20460; // Crimson Spider
	private static final int SPIDER_LE2 = 20308; // Hook Spider
	private static final int SPIDER_LE3 = 20466; // Pincer Spider
	//3 dark elves
	private static final int SPIDER_DE1 = 20025; //Lesser Dark Horror
	private static final int SPIDER_DE2 = 20105; // Dark Horror 
	private static final int SPIDER_DE3 = 20034; // Prowler
	//4 orcs
	private static final int SPIDER_O1 = 20474; // Kasha Spider
	private static final int SPIDER_O2 = 20476; // Kasha Fang Spider
	private static final int SPIDER_O3 = 20478; // Kasha Blade Spider
	//5 dwarves
	private static final int SPIDER_D1 = 20403; // Hunter Tarantula
	private static final int SPIDER_D2 = 20508; // Plunder Tarantula
	//6 kamael 
	private static final int SPIDER_K1 = 22244; // Crimson Spider 

	//Quest Item
	private static final int REQUIRED_SPIDER_LEGS = 50;

	private static final int ANIMAL_LOVERS_LIST1 = 3417;

	private static final int ANIMAL_SLAYER_LIST1 = 3418;
	private static final int ANIMAL_SLAYER_LIST2 = 3419;
	private static final int ANIMAL_SLAYER_LIST3 = 3420;
	private static final int ANIMAL_SLAYER_LIST4 = 3421;
	private static final int ANIMAL_SLAYER_LIST5 = 3422;
	private static final int ANIMAL_SLAYER_LIST6 = 10164;
	private static final int SPIDER_LEG1 = 3423;
	private static final int SPIDER_LEG2 = 3424;
	private static final int SPIDER_LEG3 = 3425;
	private static final int SPIDER_LEG4 = 3426;
	private static final int SPIDER_LEG5 = 3427;
	private static final int SPIDER_LEG6 = 10165;

	private static final int WOLF_COLLAR = 2375;

	//Chance
	@SuppressWarnings("unused")
	private static final int DROP_CHANCE_BUGBEAR_BLOOD_ID = 25;
	@SuppressWarnings("unused")
	private static final int DROP_CHANCE_FORBIDDEN_LOVE_SCROLL_ID = 3;
	@SuppressWarnings("unused")
	private static final int DROP_CHANCE_NECKLACE_OF_GRACE_ID = 4;
	@SuppressWarnings("unused")
	private static final int DROP_CHANCE_GOLD_BAR_ID = 10;

	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 0, SPIDER_H1, ANIMAL_SLAYER_LIST1, SPIDER_LEG1, 50, 100, 1},
			{1, 0, SPIDER_H2, ANIMAL_SLAYER_LIST1, SPIDER_LEG1, 50, 100, 1},
			{1, 0, SPIDER_H3, ANIMAL_SLAYER_LIST1, SPIDER_LEG1, 50, 100, 1},
			{1, 0, SPIDER_LE1, ANIMAL_SLAYER_LIST2, SPIDER_LEG2, 50, 100, 1},
			{1, 0, SPIDER_LE2, ANIMAL_SLAYER_LIST2, SPIDER_LEG2, 50, 100, 1},
			{1, 0, SPIDER_LE3, ANIMAL_SLAYER_LIST2, SPIDER_LEG2, 50, 100, 1},
			{1, 0, SPIDER_DE1, ANIMAL_SLAYER_LIST3, SPIDER_LEG3, 50, 100, 1},
			{1, 0, SPIDER_DE2, ANIMAL_SLAYER_LIST3, SPIDER_LEG3, 50, 100, 1},
			{1, 0, SPIDER_DE3, ANIMAL_SLAYER_LIST3, SPIDER_LEG3, 50, 100, 1},
			{1, 0, SPIDER_O1, ANIMAL_SLAYER_LIST4, SPIDER_LEG4, 50, 100, 1},
			{1, 0, SPIDER_O2, ANIMAL_SLAYER_LIST4, SPIDER_LEG4, 50, 100, 1},
			{1, 0, SPIDER_O3, ANIMAL_SLAYER_LIST4, SPIDER_LEG4, 50, 100, 1},
			{1, 0, SPIDER_D1, ANIMAL_SLAYER_LIST5, SPIDER_LEG5, 50, 100, 1},
			{1, 0, SPIDER_D2, ANIMAL_SLAYER_LIST5, SPIDER_LEG5, 50, 100, 1},
			{1, 0, SPIDER_K1, ANIMAL_SLAYER_LIST6, SPIDER_LEG6, 50, 100, 1}};

	public _419_GetaPet()
	{
		super(419, "_419_GetaPet", "Get a Pet");

		addStartNpc(PET_MANAGER_MARTIN);
		addTalkId(PET_MANAGER_MARTIN, GK_BELLA, MC_ELLIE, GD_METTY);

		addQuestItem(ANIMAL_LOVERS_LIST1,
				ANIMAL_SLAYER_LIST2,
				ANIMAL_SLAYER_LIST3,
				ANIMAL_SLAYER_LIST4,
				ANIMAL_SLAYER_LIST5,
				ANIMAL_SLAYER_LIST6,
				SPIDER_LEG1,
				SPIDER_LEG2,
				SPIDER_LEG3,
				SPIDER_LEG4,
				SPIDER_LEG5,
				SPIDER_LEG6);

		for(int[] cond : DROPLIST_COND) addKillId(cond[2]);
	}

	public long getCount_proof(QuestState st)
	{
		long counts = 0;
		switch(st.getPlayer().getRace())
		{
			case human:
				counts = st.getQuestItemsCount(SPIDER_LEG1);
				break;
			case elf:
				counts = st.getQuestItemsCount(SPIDER_LEG2);
				break;
			case darkelf:
				counts = st.getQuestItemsCount(SPIDER_LEG3);
				break;
			case orc:
				counts = st.getQuestItemsCount(SPIDER_LEG4);
				break;
			case dwarf:
				counts = st.getQuestItemsCount(SPIDER_LEG5);
				break;
			case kamael:
				counts = st.getQuestItemsCount(SPIDER_LEG6);
		}
		return counts;
	}

	public String check_questions(QuestState st)
	{
		String htmltext = "";
		int answers = st.getInt("answers");
		int question = st.getInt("question");
		if(question > 0)
			htmltext = "419_q" + String.valueOf(question) + ".htm";
		else if(answers < 10)
		{
			String[] ANS = st.get("quiz").toString().split(" ");
			int GetQuestion = Rnd.get(ANS.length);
			String index = ANS[GetQuestion];
			st.set("question", index);
			String quiz = "";
			if(GetQuestion + 1 == ANS.length)
			{
				for(int i = 0; i < ANS.length - 2; i++)
					quiz = quiz + ANS[i] + " ";
				quiz = quiz + ANS[ANS.length - 2];
			}
			else
			{
				for(int i = 0; i < ANS.length - 1; i++)
					if(i != GetQuestion)
						quiz = quiz + ANS[i] + " ";
				quiz = quiz + ANS[ANS.length - 1];
			}
			st.set("quiz", quiz);
			htmltext = "419_q" + index + ".htm";
		}
		else
		{
			st.giveItems(WOLF_COLLAR, 1);
			st.playSound(SOUND_FINISH);
			htmltext = "Completed.htm";
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int StateId = st.getInt("id");
		if(event.equalsIgnoreCase("details"))
			htmltext = "419_confirm.htm";
		else if(event.equalsIgnoreCase("agree"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			switch(st.getPlayer().getRace())
			{
				case human:
					st.giveItems(ANIMAL_SLAYER_LIST1, 1);
					htmltext = "419_slay_0.htm";
					break;
				case elf:
					st.giveItems(ANIMAL_SLAYER_LIST2, 1);
					htmltext = "419_slay_1.htm";
					break;
				case darkelf:
					st.giveItems(ANIMAL_SLAYER_LIST3, 1);
					htmltext = "419_slay_2.htm";
					break;
				case orc:
					st.giveItems(ANIMAL_SLAYER_LIST4, 1);
					htmltext = "419_slay_3.htm";
					break;
				case dwarf:
					st.giveItems(ANIMAL_SLAYER_LIST5, 1);
					htmltext = "419_slay_4.htm";
					break;
				case kamael:
					st.giveItems(ANIMAL_SLAYER_LIST6, 1);
					htmltext = "419_slay_5.htm";
			}
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("disagree"))
		{
			htmltext = "419_cancelled.htm";
			st.exitCurrentQuest(true);
		}
		else if(StateId == 1)
		{
			if(event.equalsIgnoreCase("talk"))
				htmltext = "419_talk.htm";
			else if(event.equalsIgnoreCase("talk1"))
				htmltext = "419_bella_2.htm";
			else if(event.equalsIgnoreCase("talk2"))
			{
				st.set("progress", String.valueOf(st.getInt("progress") | 1));
				htmltext = "419_bella_3.htm";
			}
			else if(event.equalsIgnoreCase("talk3"))
			{
				st.set("progress", String.valueOf(st.getInt("progress") | 2));
				htmltext = "419_ellie_2.htm";
			}
			else if(event.equalsIgnoreCase("talk4"))
			{
				st.set("progress", String.valueOf(st.getInt("progress") | 4));
				htmltext = "419_metty_2.htm";
			}
		}
		else if(StateId == 2)
			if(event.equalsIgnoreCase("tryme"))
				htmltext = check_questions(st);
			else if(event.equalsIgnoreCase("wrong"))
			{
				st.set("id", "1");
				st.set("progress", "0");
				st.unset("quiz");
				st.unset("answers");
				st.unset("question");
				st.giveItems(ANIMAL_LOVERS_LIST1, 1);
				htmltext = "419_failed.htm";
			}
			else if(event.equalsIgnoreCase("right"))
			{
				st.set("answers", String.valueOf(st.getInt("answers") + 1));
				st.set("question", "0");
				htmltext = check_questions(st);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int StateId = st.getInt("id");
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(npcId == PET_MANAGER_MARTIN)
				if(st.getPlayer().getLevel() < 15)
				{
					htmltext = "419_low_level.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "Start.htm";
		}
		else if(cond == 1)
			if(npcId == PET_MANAGER_MARTIN)
			{
				if(StateId == 0)
				{
					long counts = getCount_proof(st);
					if(counts == 0)
						htmltext = "419_no_slay.htm";
					else if(counts < REQUIRED_SPIDER_LEGS)
						htmltext = "419_pending_slay.htm";
					else
					{
						switch(st.getPlayer().getRace())
						{
							case human:
								st.takeItems(ANIMAL_SLAYER_LIST1, -1);
								st.takeItems(SPIDER_LEG1, -1);
								break;
							case elf:
								st.takeItems(ANIMAL_SLAYER_LIST2, -1);
								st.takeItems(SPIDER_LEG2, -1);
								break;
							case darkelf:
								st.takeItems(ANIMAL_SLAYER_LIST3, -1);
								st.takeItems(SPIDER_LEG3, -1);
								break;
							case orc:
								st.takeItems(ANIMAL_SLAYER_LIST4, -1);
								st.takeItems(SPIDER_LEG4, -1);
								break;
							case dwarf:
								st.takeItems(ANIMAL_SLAYER_LIST5, -1);
								st.takeItems(SPIDER_LEG5, -1);
								break;
							case kamael:
								st.takeItems(ANIMAL_SLAYER_LIST6, -1);
								st.takeItems(SPIDER_LEG6, -1);
						}
						st.set("id", "1");
						st.giveItems(ANIMAL_LOVERS_LIST1, 1);
						htmltext = "Slayed.htm";
					}
				}
				else if(StateId == 1)
				{
					if(st.getInt("progress") == 7)
					{
						st.takeItems(ANIMAL_LOVERS_LIST1, -1);
						st.set("quiz", "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15");
						st.set("answers", "0");
						st.set("id", "2");
						htmltext = "Talked.htm";
					}
					else
						htmltext = "419_pending_talk.htm";
				}
				else if(StateId == 2)
					htmltext = "Talked.htm";
			}
			else if(StateId == 1)
				if(npcId == GK_BELLA)
					htmltext = "419_bella_1.htm";
				else if(npcId == MC_ELLIE)
					htmltext = "419_ellie_1.htm";
				else if(npcId == GD_METTY)
					htmltext = "419_metty_1.htm";
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