package quests._622_DeliveryofSpecialLiquor;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _622_DeliveryofSpecialLiquor extends Quest
{
	//NPCs
	private static int JEREMY = 31521;
	private static int LIETTA = 31267;
	private static int PULIN = 31543;
	private static int NAFF = 31544;
	private static int CROCUS = 31545;
	private static int KUBER = 31546;
	private static int BEOLIN = 31547;
	//Quest Items
	private static int SpecialDrink = 7207;
	private static int FeeOfSpecialDrink = 7198;
	//Items
	private static int RecipeSealedTateossianRing = 6849;
	private static int RecipeSealedTateossianEarring = 6847;
	private static int RecipeSealedTateossianNecklace = 6851;
	private static int HastePotion = 734;
	private static int ADENA = 57;
	//Chances
	private static int Tateossian_CHANCE = 20;

	public _622_DeliveryofSpecialLiquor()
	{
		super(622, "_622_DeliveryofSpecialLiquor", "Delivery of Special Liquor");
		addStartNpc(JEREMY);
		addTalkId(LIETTA, PULIN, NAFF, CROCUS, KUBER, BEOLIN);
		addQuestItem(SpecialDrink, FeeOfSpecialDrink);
	}

	private static void takeDrink(QuestState st, int setcond)
	{
		st.set("cond", String.valueOf(setcond));
		st.takeItems(SpecialDrink, 1);
		st.giveItems(FeeOfSpecialDrink, 1);
		st.playSound(SOUND_MIDDLE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		int cond = st.getInt("cond");
		long SpecialDrink_count = st.getQuestItemsCount(SpecialDrink);

		if(event.equalsIgnoreCase("31521-02.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.takeItems(SpecialDrink, -1);
			st.takeItems(FeeOfSpecialDrink, -1);
			st.giveItems(SpecialDrink, 5);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31547-02.htm") && cond == 1 && SpecialDrink_count > 0)
			takeDrink(st, 2);
		else if(event.equalsIgnoreCase("31546-02.htm") && cond == 2 && SpecialDrink_count > 0)
			takeDrink(st, 3);
		else if(event.equalsIgnoreCase("31545-02.htm") && cond == 3 && SpecialDrink_count > 0)
			takeDrink(st, 4);
		else if(event.equalsIgnoreCase("31544-02.htm") && cond == 4 && SpecialDrink_count > 0)
			takeDrink(st, 5);
		else if(event.equalsIgnoreCase("31543-02.htm") && cond == 5 && SpecialDrink_count > 0)
			takeDrink(st, 6);
		else if(event.equalsIgnoreCase("31521-04.htm") && cond == 6 && st.getQuestItemsCount(FeeOfSpecialDrink) >= 5)
			st.set("cond", "7");
		else if(event.equalsIgnoreCase("31267-02.htm") && cond == 7 && st.getQuestItemsCount(FeeOfSpecialDrink) >= 5)
		{
			st.takeItems(SpecialDrink, -1);
			st.takeItems(FeeOfSpecialDrink, -1);
			if(Rnd.chance(Tateossian_CHANCE))
			{
				if(Rnd.chance(40))
					st.giveItems(RecipeSealedTateossianRing, 1);
				else if(Rnd.chance(40))
					st.giveItems(RecipeSealedTateossianEarring, 1);
				else
					st.giveItems(RecipeSealedTateossianNecklace, 1);
			}
			else
			{
				st.rollAndGive(ADENA, 18800, 100);
				st.rollAndGive(HastePotion, 1, 100);
			}

			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != JEREMY)
				return htmltext;
			if(st.getPlayer().getLevel() >= 68)
			{
				st.set("cond", "0");
				return "31521-01.htm";
			}
			st.exitCurrentQuest(true);
			return "31521-00.htm";
		}

		int cond = st.getInt("cond");
		long SpecialDrink_count = st.getQuestItemsCount(SpecialDrink);
		long FeeOfSpecialDrink_count = st.getQuestItemsCount(FeeOfSpecialDrink);

		if(cond == 1 && npcId == BEOLIN && SpecialDrink_count > 0)
			htmltext = "31547-01.htm";
		else if(cond == 2 && npcId == KUBER && SpecialDrink_count > 0)
			htmltext = "31546-01.htm";
		else if(cond == 3 && npcId == CROCUS && SpecialDrink_count > 0)
			htmltext = "31545-01.htm";
		else if(cond == 4 && npcId == NAFF && SpecialDrink_count > 0)
			htmltext = "31544-01.htm";
		else if(cond == 5 && npcId == PULIN && SpecialDrink_count > 0)
			htmltext = "31543-01.htm";
		else if(cond == 6 && npcId == JEREMY && FeeOfSpecialDrink_count >= 5)
			htmltext = "31521-03.htm";
		else if(cond == 7 && npcId == JEREMY && FeeOfSpecialDrink_count >= 5)
			htmltext = "31521-05.htm";
		else if(cond == 7 && npcId == LIETTA && FeeOfSpecialDrink_count >= 5)
			htmltext = "31267-01.htm";
		else if(cond > 0 && npcId == JEREMY && SpecialDrink_count > 0)
			htmltext = "31521-02.htm";
		return htmltext;
	}
}