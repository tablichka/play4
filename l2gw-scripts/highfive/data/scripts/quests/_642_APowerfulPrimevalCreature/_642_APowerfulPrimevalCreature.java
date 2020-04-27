package quests._642_APowerfulPrimevalCreature;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _642_APowerfulPrimevalCreature extends Quest
{
	// NPCs
	private static int Dinn = 32105;
	// Mobs
	private static int Ancient_Egg = 18344;
	private static int[] Dino = {22196, 22197, 22198, 22199, 22200, 22201, 22202, 22203, 22204, 22205, 22218, 22219, 22220, 22223, 22224, 22225};
	// Items
	private static int ADENA = 57;
	private static int[] Rewards = {8690, 8692, 8694, 8696, 8698, 8700, 8702, 8704, 8706, 8708, 8710};
	private static int[] RewardsS80 = {9967, 9968, 9969, 9970, 9971, 9972, 9975, 10544, 9974, 10545, 9973};
	// Quest Items
	private static short Dinosaur_Tissue = 8774;
	private static short Dinosaur_Egg = 8775;
	// Chances
	private static int Dinosaur_Tissue_Chance = 33;
	private static int Dinosaur_Egg_Chance = 1;

	public _642_APowerfulPrimevalCreature()
	{
		super(642, "_642_APowerfulPrimevalCreature", "A Powerful Primeval Creature");
		addStartNpc(Dinn);
		addKillId(Ancient_Egg);
		addKillId(Dino);
		addQuestItem(Dinosaur_Tissue, Dinosaur_Egg);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		long Dinosaur_Tissue_Count = st.getQuestItemsCount(Dinosaur_Tissue);
		if(event.equalsIgnoreCase("32105-04.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32105-08.htm") && st.isStarted())
		{
			if(Dinosaur_Tissue_Count == 0)
				return "32105-08a.htm";
			st.takeItems(Dinosaur_Tissue, -1);
			st.rollAndGive(ADENA, Dinosaur_Tissue_Count * 3000, 100);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("32105-07.htm") && st.isStarted() && Config.ALT_100_RECIPES)
			return "32105-07a.htm";
		else if(event.equalsIgnoreCase("0"))
		{
			st.exitCurrentQuest(true);
			return "32105-04a.htm";
		}
		else if(st.isStarted() && event.startsWith("a"))
			try
			{
				event = event.replace("a", "");
				int rew_id = Integer.valueOf(event);
				if(Dinosaur_Tissue_Count < 150 || st.getQuestItemsCount(Dinosaur_Egg) < 1)
					return "32105-08c.htm";
				for(int reward : Rewards)
					if(reward == rew_id)
					{
						st.takeItems(Dinosaur_Tissue, 150);
						st.takeItems(Dinosaur_Egg, 1);
						st.giveItems(Config.ALT_100_RECIPES ? reward + 1 : reward, 1);
						st.rollAndGive(ADENA, 44000, 100);
						st.playSound(SOUND_MIDDLE);
						return "32105-09c.htm";
					}
				return null;
			}
			catch(Exception E)
			{
				_log.info(this + " wrong event name/number for A reward");
			}
		else if(st.isStarted() && event.startsWith("s"))
			try
			{
				event = event.replace("s", "");
				int rew_id = Integer.valueOf(event);
				if(Dinosaur_Tissue_Count < 450)
					return "32105-09b.htm";
				if(st.getQuestItemsCount(Dinosaur_Egg) < 3)
					return "32105-09a.htm";
				for(int reward : RewardsS80)
					if(reward == rew_id)
					{
						st.takeItems(Dinosaur_Tissue, 450);
						st.takeItems(Dinosaur_Egg, 3);
						st.giveItems(reward, 1);
						st.rollAndGive(ADENA, 44000, 100);
						st.playSound(SOUND_MIDDLE);
						return "32105-09c.htm";
					}
				return null;
			}
			catch(Exception E)
			{
				_log.info(this + " wrong event name/number for S80 reward");
			}

		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(npc.getNpcId() != Dinn)
			return "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 75)
			{
				st.exitCurrentQuest(true);
				return "32105-00.htm";
			}
			return "32105-01.htm";
		}
		if(st.isStarted())
		{
			long Dinosaur_Tissue_Count = st.getQuestItemsCount(Dinosaur_Tissue);
			if(Dinosaur_Tissue_Count > 0)
				return "32105-06.htm";
			else
				return "32105-05.htm";
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1)
		{
			if(npc.getNpcId() == Ancient_Egg)
			{
				if(st.rollAndGive(Dinosaur_Egg, 1, Dinosaur_Egg_Chance))
					st.playSound(SOUND_ITEMGET);
				return;
			}

			if(st.rollAndGive(Dinosaur_Tissue, 1, Dinosaur_Tissue_Chance))
				st.playSound(SOUND_ITEMGET);
		}
	}
}