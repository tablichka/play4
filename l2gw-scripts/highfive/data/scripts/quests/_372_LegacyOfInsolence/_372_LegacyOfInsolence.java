package quests._372_LegacyOfInsolence;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

public class _372_LegacyOfInsolence extends Quest
{
	// NPCs
	private static int HOLLY = 30839;
	private static int WALDERAL = 30844;
	private static int DESMOND = 30855;
	private static int PATRIN = 30929;
	private static int CLAUDIA = 31001;
	// Mobs
	private static int CORRUPT_SAGE = 20817;
	private static int ERIN_EDIUNCE = 20821;
	private static int HALLATE_INSP = 20825;
	private static int PLATINUM_OVL = 20829;
	private static int PLATINUM_PRE = 21069;
	private static int MESSENGER_A2 = 21063;
	// Items
	private static int ADENA = 57;
	private static int Ancient_Red_Papyrus = 5966;
	private static int Ancient_Blue_Papyrus = 5967;
	private static int Ancient_Black_Papyrus = 5968;
	private static int Ancient_White_Papyrus = 5969;

	private static int[] Revelation_of_the_Seals_Range = {5972, 5978};
	private static int[] Ancient_Epic_Chapter_Range = {5979, 5983};
	private static int[] Imperial_Genealogy_Range = {5984, 5988};
	private static int[] Blueprint_Tower_of_Insolence_Range = {5989, 6001};
	// Rewards
	private static int[] Reward_Dark_Crystal = {5368, 5392, 5426};
	private static int[] Reward_Tallum = {5370, 5394, 5428};
	private static int[] Reward_Nightmare = {5380, 5404, 5430};
	private static int[] Reward_Majestic = {5382, 5406, 5432};
	// Chances
	private static int Three_Recipes_Reward_Chance = 1;
	private static int Two_Recipes_Reward_Chance = 2;
	private static int Adena4k_Reward_Chance = 2;

	public _372_LegacyOfInsolence()
	{
		super(372, "_372_LegacyOfInsolence", "Legacy of Insolence"); // Party true
		addStartNpc(WALDERAL);

		addTalkId(HOLLY);
		addTalkId(DESMOND);
		addTalkId(PATRIN);
		addTalkId(CLAUDIA);

		addKillId(CORRUPT_SAGE);
		addKillId(ERIN_EDIUNCE);
		addKillId(HALLATE_INSP);
		addKillId(PLATINUM_OVL);
		addKillId(PLATINUM_PRE);
		addKillId(MESSENGER_A2);
	}

	private static void giveRecipe(QuestState st, int recipe_id)
	{
		st.giveItems(Config.ALT_100_RECIPES ? recipe_id + 1 : recipe_id, 1);
	}

	private static boolean check_and_reward(QuestState st, int[] items_range, int[] reward)
	{
		for(int item_id = items_range[0]; item_id <= items_range[1]; item_id++)
			if(st.getQuestItemsCount(item_id) < 1)
				return false;

		for(int item_id = items_range[0]; item_id <= items_range[1]; item_id++)
			st.takeItems(item_id, 1);

		if(Rnd.chance(Three_Recipes_Reward_Chance))
		{
			for(int reward_item_id : reward)
				giveRecipe(st, reward_item_id);
			st.playSound(SOUND_JACKPOT);
		}
		else if(Rnd.chance(Two_Recipes_Reward_Chance))
		{
			int ignore_reward_id = reward[Rnd.get(reward.length)];
			for(int reward_item_id : reward)
				if(reward_item_id != ignore_reward_id)
					giveRecipe(st, reward_item_id);
			st.playSound(SOUND_JACKPOT);
		}
		else if(Rnd.chance(Adena4k_Reward_Chance))
			st.rollAndGive(ADENA, 4000, 100);
		else
			giveRecipe(st, reward[Rnd.get(reward.length)]);

		return true;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30844-6.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30844-9.htm") && st.isStarted())
			st.set("cond", "2");
		else if(event.equalsIgnoreCase("30844-7.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("30839-exchange") && st.isStarted())
			htmltext = check_and_reward(st, Imperial_Genealogy_Range, Reward_Dark_Crystal) ? "30839-2.htm" : "30839-3.htm";
		else if(event.equalsIgnoreCase("30855-exchange") && st.isStarted())
			htmltext = check_and_reward(st, Revelation_of_the_Seals_Range, Reward_Majestic) ? "30855-2.htm" : "30855-3.htm";
		else if(event.equalsIgnoreCase("30929-exchange") && st.isStarted())
			htmltext = check_and_reward(st, Ancient_Epic_Chapter_Range, Reward_Tallum) ? "30839-2.htm" : "30839-3.htm";
		else if(event.equalsIgnoreCase("31001-exchange") && st.isStarted())
			htmltext = check_and_reward(st, Revelation_of_the_Seals_Range, Reward_Nightmare) ? "30839-2.htm" : "30839-3.htm";
		else if(event.equalsIgnoreCase("30844-DarkCrystal") && st.isStarted())
			htmltext = check_and_reward(st, Blueprint_Tower_of_Insolence_Range, Reward_Dark_Crystal) ? "30844-11.htm" : "30844-12.htm";
		else if(event.equalsIgnoreCase("30844-Tallum") && st.isStarted())
			htmltext = check_and_reward(st, Blueprint_Tower_of_Insolence_Range, Reward_Tallum) ? "30844-11.htm" : "30844-12.htm";
		else if(event.equalsIgnoreCase("30844-Nightmare") && st.isStarted())
			htmltext = check_and_reward(st, Blueprint_Tower_of_Insolence_Range, Reward_Nightmare) ? "30844-11.htm" : "30844-12.htm";
		else if(event.equalsIgnoreCase("30844-Majestic") && st.isStarted())
			htmltext = check_and_reward(st, Blueprint_Tower_of_Insolence_Range, Reward_Majestic) ? "30844-11.htm" : "30844-12.htm";

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();

		if(st.isCreated())
		{
			if(npcId != WALDERAL)
				return htmltext;
			if(st.getPlayer().getLevel() >= 59)
			{
				htmltext = "30844-4.htm";
				st.set("cond", "0");
			}
			else
			{
				htmltext = "30844-5.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			htmltext = String.valueOf(npcId) + "-1.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> pm = new GArray<QuestState>();
		QuestState st1 = getRandomPartyMemberWithQuest(killer, 1);
		QuestState st2 = getRandomPartyMemberWithQuest(killer, 2);
		if(st1 != null)
			pm.add(st1);
		if(st2 != null)
			pm.add(st2);

		if(pm.isEmpty())
			return;

		QuestState st = pm.get(Rnd.get(pm.size()));
		if(st != null)
		{
			int npcId = npc.getNpcId();
			int chance = 0;
			if(npcId == CORRUPT_SAGE)
				chance = 35;
			else if(npcId == ERIN_EDIUNCE)
				chance = 40;
			else if(npcId == HALLATE_INSP)
				chance = 45;
			if(st.rollAndGive(Ancient_Red_Papyrus, 1, chance))
			{
				st.playSound(SOUND_ITEMGET);
				return;
			}

			if(npcId == PLATINUM_OVL)
				chance = 40;
			if(st.rollAndGive(Ancient_Blue_Papyrus, 1, chance))
			{
				st.playSound(SOUND_ITEMGET);
				return;
			}

			if(npcId == PLATINUM_PRE && st.rollAndGive(Ancient_Black_Papyrus, 1, 25))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == MESSENGER_A2 && st.rollAndGive(Ancient_White_Papyrus, 1, 25))
				st.playSound(SOUND_ITEMGET);
		}
	}
}