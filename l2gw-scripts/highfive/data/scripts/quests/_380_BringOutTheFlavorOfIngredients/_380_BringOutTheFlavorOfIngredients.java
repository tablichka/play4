package quests._380_BringOutTheFlavorOfIngredients;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * Квест Bring Out The Flavor Of Ingredients
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _380_BringOutTheFlavorOfIngredients extends Quest
{
	//NPCs
	private static final int Rollant = 30069;
	//Quest Items
	private static final int RitronsFruit = 5895;
	private static final int MoonFaceFlower = 5896;
	private static final int LeechFluids = 5897;
	//Items
	private static final int Antidote = 1831;
	private static final int RitronsDessertRecipe = 5959;
	private static final int RitronJelly = 5960;
	//Chances
	private static final int RitronsDessertRecipeChance = 55;
	//Mobs
	private static final int DireWolf = 20205;
	private static final int KadifWerewolf = 20206;
	private static final int GiantMistLeech = 20225;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 0, DireWolf, 0, RitronsFruit, 4, 10, 1},
			{1, 0, KadifWerewolf, 0, MoonFaceFlower, 20, 50, 1},
			{1, 0, GiantMistLeech, 0, LeechFluids, 10, 50, 1}};

	public _380_BringOutTheFlavorOfIngredients()
	{
		super(380, "_380_BringOutTheFlavorOfIngredients", "Bring Out The Flavor Of Ingredients");
		addStartNpc(Rollant);

		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30069-4.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30069-12.htm"))
		{
			st.giveItems(RitronsDessertRecipe, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Rollant)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 24)
					htmltext = "30069-1.htm";
				else
				{
					htmltext = "30069-0.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
				htmltext = "30069-6.htm";
			else if(cond == 2 && st.getQuestItemsCount(Antidote) >= 2)
			{
				st.takeItems(Antidote, 2);
				st.takeItems(RitronsFruit, -1);
				st.takeItems(MoonFaceFlower, -1);
				st.takeItems(LeechFluids, -1);
				htmltext = "30069-7.htm";
				st.set("cond", "3");
				st.setState(STARTED);
			}
			else if(cond == 2)
				htmltext = "30069-6.htm";
			else if(cond == 3)
			{
				htmltext = "30069-8.htm";
				st.set("cond", "4");
			}
			else if(cond == 4)
			{
				htmltext = "30069-9.htm";
				st.set("cond", "5");
			}
			if(cond == 5)
			{
				htmltext = "30069-10.htm";
				st.set("cond", "6");
			}
			if(cond == 6)
			{
				st.giveItems(RitronJelly, 1);
				if(Rnd.chance(RitronsDessertRecipeChance))
					htmltext = "30069-11.htm";
				else
				{
					htmltext = "30069-13.htm";
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(true);
				}
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
		if(cond == 1 && st.getQuestItemsCount(RitronsFruit) >= 4 && st.getQuestItemsCount(MoonFaceFlower) >= 20 && st.getQuestItemsCount(LeechFluids) >= 10)
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
	}
}