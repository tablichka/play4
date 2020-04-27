package quests._299_GatherIngredientsforPie;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _299_GatherIngredientsforPie extends Quest
{
	// NPCs
	private static int Emily = 30620;
	private static int Lara = 30063;
	private static int Bright = 30466;
	// Mobs
	private static int Wasp_Worker = 20934;
	private static int Wasp_Leader = 20935;
	// Items
	private static int ADENA = 57;
	private static int Varnish = 1865;
	// Quest Items
	private static short Fruit_Basket = 7136;
	private static short Avellan_Spice = 7137;
	private static short Honey_Pouch = 7138;
	// Chances
	private static int Wasp_Worker_Chance = 55;
	private static int Wasp_Leader_Chance = 70;
	private static int Reward_Varnish_Chance = 50;

	public _299_GatherIngredientsforPie()
	{
		super(299, "_299_GatherIngredientsforPie", "Gather Ingredients for Pie");
		addStartNpc(Emily);
		addTalkId(Lara, Bright);
		addKillId(Wasp_Worker);
		addKillId(Wasp_Leader);
		addQuestItem(Fruit_Basket, Avellan_Spice, Honey_Pouch);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		int cond = st.getInt("cond");

		if(event.equalsIgnoreCase("30620-02.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30620-04.htm") && st.isStarted())
		{
			if(st.getQuestItemsCount(Honey_Pouch) < 100)
				return "bug1.htm";
			st.takeItems(Honey_Pouch, -1);
			st.set("cond", "3");
		}
		else if(event.equalsIgnoreCase("30063-02.htm") && st.isStarted() && cond == 3)
		{
			st.giveItems(Avellan_Spice, 1);
			st.set("cond", "4");
		}
		else if(event.equalsIgnoreCase("30620-06.htm") && st.isStarted())
		{
			if(st.getQuestItemsCount(Avellan_Spice) < 1)
				return "bug2.htm";
			st.takeItems(Avellan_Spice, -1);
			st.set("cond", "5");
		}
		else if(event.equalsIgnoreCase("30466-02.htm") && st.isStarted() && cond == 5)
		{
			st.giveItems(Fruit_Basket, 1);
			st.set("cond", "6");
		}
		else if(event.equalsIgnoreCase("30620-08.htm") && st.isStarted())
		{
			if(st.getQuestItemsCount(Fruit_Basket) < 1)
				return "bug3.htm";
			st.takeItems(Fruit_Basket, -1);
			st.rollAndGive(Varnish, 50, Reward_Varnish_Chance);
			st.rollAndGive(ADENA, 25000, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != Emily)
				return "noquest";
			if(st.getPlayer().getLevel() >= 34)
			{
				st.set("cond", "0");
				return "30620-01.htm";
			}
			st.exitCurrentQuest(true);
			return "30620-00.htm";
		}

		int cond = st.getInt("cond");
		if(npcId == Emily && st.isStarted())
		{
			if(cond == 1 && st.getQuestItemsCount(Honey_Pouch) <= 99)
				return "30620-02r.htm";
			if(cond == 2 && st.getQuestItemsCount(Honey_Pouch) >= 100)
				return "30620-03.htm";
			if(cond == 3 && st.getQuestItemsCount(Avellan_Spice) == 0)
				return "30620-04r.htm";
			if(cond == 4 && st.getQuestItemsCount(Avellan_Spice) == 1)
				return "30620-05.htm";
			if(cond == 5 && st.getQuestItemsCount(Fruit_Basket) == 0)
				return "30620-06r.htm";
			if(cond == 6 && st.getQuestItemsCount(Fruit_Basket) == 1)
				return "30620-07.htm";
		}
		if(npcId == Lara && st.isStarted() && cond == 3)
			return "30063-01.htm";
		if(npcId == Lara && st.isStarted() && cond == 4)
			return "30063-02r.htm";
		if(npcId == Bright && st.isStarted() && cond == 5)
			return "30466-01.htm";
		if(npcId == Bright && st.isStarted() && cond == 5)
			return "30466-02r.htm";

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") != 1)
			return;

		int npcId = npc.getNpcId();
		if((npcId == Wasp_Worker || npcId == Wasp_Leader) && st.rollAndGiveLimited(Honey_Pouch, 1, npcId == Wasp_Leader ? Wasp_Leader_Chance : Wasp_Worker_Chance, 100))
		{
			if(st.getQuestItemsCount(Honey_Pouch) < 100)
				st.playSound(SOUND_ITEMGET);
			else
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
		}

	}
}