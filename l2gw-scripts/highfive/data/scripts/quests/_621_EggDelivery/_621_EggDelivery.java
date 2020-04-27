package quests._621_EggDelivery;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _621_EggDelivery extends Quest
{
	//NPC
	private static int JEREMY = 31521;
	private static int VALENTINE = 31584;
	private static int PULIN = 31543;
	private static int NAFF = 31544;
	private static int CROCUS = 31545;
	private static int KUBER = 31546;
	private static int BEOLIN = 31547;
	//Quest Items
	private static final int BoiledEgg = 7206;
	private static final int FeeOfBoiledEgg = 7196;
	//Items
	private static final int Adena = 57;
	private static final int HastePotion = 734;
	private static final int RecipeSealedTateossianRing = 6849;
	private static final int RecipeSealedTateossianEarring = 6847;
	private static final int RecipeSealedTateossianNecklace = 6851;
	//Chances
	private static int Tateossian_CHANCE = 20;

	public _621_EggDelivery()
	{
		super(621, "_621_EggDelivery", "Egg Delivery");
		addStartNpc(JEREMY);
		addTalkId(VALENTINE, PULIN, NAFF, CROCUS, KUBER, BEOLIN);
		addQuestItem(BoiledEgg, FeeOfBoiledEgg);
	}

	private static void takeEgg(QuestState st, int setcond)
	{
		st.set("cond", String.valueOf(setcond));
		st.takeItems(BoiledEgg, 1);
		st.giveItems(FeeOfBoiledEgg, 1);
		st.playSound(SOUND_MIDDLE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		int cond = st.getInt("cond");
		long BoiledEgg_count = st.getQuestItemsCount(BoiledEgg);

		if(event.equalsIgnoreCase("31521-02.htm") && st.isCreated())
		{
			st.takeItems(BoiledEgg, -1);
			st.takeItems(FeeOfBoiledEgg, -1);
			st.setState(STARTED);
			st.set("cond", "1");
			st.giveItems(BoiledEgg, 5);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31543-02.htm") && cond == 1 && BoiledEgg_count > 0)
			takeEgg(st, 2);
		else if(event.equalsIgnoreCase("31544-02.htm") && cond == 2 && BoiledEgg_count > 0)
			takeEgg(st, 3);
		else if(event.equalsIgnoreCase("31545-02.htm") && cond == 3 && BoiledEgg_count > 0)
			takeEgg(st, 4);
		else if(event.equalsIgnoreCase("31546-02.htm") && cond == 4 && BoiledEgg_count > 0)
			takeEgg(st, 5);
		else if(event.equalsIgnoreCase("31547-02.htm") && cond == 5 && BoiledEgg_count > 0)
			takeEgg(st, 6);
		else if(event.equalsIgnoreCase("31521-04.htm") && cond == 6 && st.getQuestItemsCount(FeeOfBoiledEgg) >= 5)
			st.set("cond", "7");
		else if(event.equalsIgnoreCase("31584-02.htm") && cond == 7 && st.getQuestItemsCount(FeeOfBoiledEgg) >= 5)
		{
			st.takeItems(BoiledEgg, -1);
			st.takeItems(FeeOfBoiledEgg, -1);
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
				st.rollAndGive(Adena, 18800, 100);
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
		long BoiledEgg_count = st.getQuestItemsCount(BoiledEgg);
		long FeeOfBoiledEgg_count = st.getQuestItemsCount(FeeOfBoiledEgg);

		if(cond == 1 && npcId == PULIN && BoiledEgg_count > 0)
			htmltext = "31543-01.htm";
		if(cond == 2 && npcId == NAFF && BoiledEgg_count > 0)
			htmltext = "31544-01.htm";
		if(cond == 3 && npcId == CROCUS && BoiledEgg_count > 0)
			htmltext = "31545-01.htm";
		if(cond == 4 && npcId == KUBER && BoiledEgg_count > 0)
			htmltext = "31546-01.htm";
		if(cond == 5 && npcId == BEOLIN && BoiledEgg_count > 0)
			htmltext = "31547-01.htm";
		if(cond == 6 && npcId == JEREMY && FeeOfBoiledEgg_count >= 5)
			htmltext = "31521-03.htm";
		if(cond == 7 && npcId == JEREMY && FeeOfBoiledEgg_count >= 5)
			htmltext = "31521-05.htm";
		if(cond == 7 && npcId == VALENTINE && FeeOfBoiledEgg_count >= 5)
			htmltext = "31584-01.htm";
		else if(cond > 0 && npcId == JEREMY && BoiledEgg_count > 0)
			htmltext = "31521-02.htm";
		return htmltext;
	}
}