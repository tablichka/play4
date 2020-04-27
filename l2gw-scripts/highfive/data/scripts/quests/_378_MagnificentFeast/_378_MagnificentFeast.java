package quests._378_MagnificentFeast;

import javolution.util.FastMap;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _378_MagnificentFeast extends Quest
{
	// NPCs
	private static int RANSPO = 30594;
	// Items
	private static int WINE_15 = 5956;
	private static int WINE_30 = 5957;
	private static int WINE_60 = 5958;
	private static int ADENA = 57;
	private static int Musical_Score__Theme_of_the_Feast = 4421;
	private static int Ritrons_Dessert_Recipe = 5959;
	private static int Jonass_Salad_Recipe = 1455;
	private static int Jonass_Sauce_Recipe = 1456;
	private static int Jonass_Steak_Recipe = 1457;

	private FastMap<Integer, int[]> rewards = new FastMap<Integer, int[]>();

	public _378_MagnificentFeast()
	{
		super(378, "_378_MagnificentFeast", "Magnificent Feast");
		addStartNpc(RANSPO);

		rewards.put(9, new int[]{847, 1, 5700});
		rewards.put(10, new int[]{846, 2, 0});
		rewards.put(12, new int[]{909, 1, 25400});
		rewards.put(17, new int[]{846, 2, 1200});
		rewards.put(18, new int[]{879, 1, 6900});
		rewards.put(20, new int[]{890, 2, 8500});
		rewards.put(33, new int[]{879, 1, 8100});
		rewards.put(34, new int[]{910, 1, 0});
		rewards.put(36, new int[]{910, 1, 0});
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int cond = st.getInt("cond");
		int score = st.getInt("score");

		if(event.equalsIgnoreCase("30594-2.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30594-4a.htm") && st.isStarted())
		{
			if(cond == 1 && st.getQuestItemsCount(WINE_15) > 0)
			{
				st.takeItems(WINE_15, 1);
				st.set("cond", "2");
				st.set("score", String.valueOf(score + 1));
			}
			else
				htmltext = "30594-4.htm";
		}
		else if(event.equalsIgnoreCase("30594-4b.htm") && st.isStarted())
		{
			if(cond == 1 && st.getQuestItemsCount(WINE_30) > 0)
			{
				st.takeItems(WINE_30, 1);
				st.set("cond", "2");
				st.set("score", String.valueOf(score + 2));
			}
			else
				htmltext = "30594-4.htm";
		}
		else if(event.equalsIgnoreCase("30594-4c.htm") && st.isStarted())
		{
			if(cond == 1 && st.getQuestItemsCount(WINE_60) > 0)
			{
				st.takeItems(WINE_60, 1);
				st.set("cond", "2");
				st.set("score", String.valueOf(score + 4));
			}
			else
				htmltext = "30594-4.htm";
		}
		else if(event.equalsIgnoreCase("30594-6.htm") && st.isStarted())
		{
			if(cond == 2 && st.getQuestItemsCount(Musical_Score__Theme_of_the_Feast) > 0)
			{
				st.takeItems(Musical_Score__Theme_of_the_Feast, 1);
				st.set("cond", "3");
			}
			else
				htmltext = "30594-5.htm";
		}
		else if(event.equalsIgnoreCase("30594-8a.htm") && st.isStarted())
		{
			if(cond == 3 && st.getQuestItemsCount(Jonass_Salad_Recipe) > 0)
			{
				st.takeItems(Jonass_Salad_Recipe, 1);
				st.set("cond", "4");
				st.set("score", String.valueOf(score + 8));
			}
			else
				htmltext = "30594-8.htm";
		}
		else if(event.equalsIgnoreCase("30594-8b.htm") && st.isStarted())
		{
			if(cond == 3 && st.getQuestItemsCount(Jonass_Sauce_Recipe) > 0)
			{
				st.takeItems(Jonass_Sauce_Recipe, 1);
				st.set("cond", "4");
				st.set("score", String.valueOf(score + 16));
			}
			else
				htmltext = "30594-8.htm";
		}
		else if(event.equalsIgnoreCase("30594-8c.htm") && st.isStarted())
			if(cond == 3 && st.getQuestItemsCount(Jonass_Steak_Recipe) > 0)
			{
				st.takeItems(Jonass_Steak_Recipe, 1);
				st.set("cond", "4");
				st.set("score", String.valueOf(score + 32));
			}
			else
				htmltext = "30594-8.htm";

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != RANSPO)
			return htmltext;
		int cond = st.getInt("cond");

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 20)
			{
				htmltext = "30594-0.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30594-1.htm";
				st.set("cond", "0");
			}
		}
		else if(cond == 1 && st.isStarted())
			htmltext = "30594-3.htm";
		else if(cond == 2 && st.isStarted())
			htmltext = st.getQuestItemsCount(Musical_Score__Theme_of_the_Feast) > 0 ? "30594-5a.htm" : "30594-5.htm";
		else if(cond == 3 && st.isStarted())
			htmltext = "30594-7.htm";
		else if(cond == 4 && st.isStarted())
		{
			int[] reward = rewards.get(st.getInt("score"));
			if(st.getQuestItemsCount(Ritrons_Dessert_Recipe) > 0 && reward != null)
			{
				htmltext = "30594-10.htm";
				st.takeItems(Ritrons_Dessert_Recipe, 1);
				st.giveItems(reward[0], reward[1]);
				if(reward[2] > 0)
					st.rollAndGive(ADENA, reward[2], 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30594-9.htm";
		}

		return htmltext;
	}
}