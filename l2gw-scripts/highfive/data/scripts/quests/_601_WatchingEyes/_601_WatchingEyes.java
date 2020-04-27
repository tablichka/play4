package quests._601_WatchingEyes;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _601_WatchingEyes extends Quest
{
	//NPC
	private static int EYE_OF_ARGOS = 31683;
	//ITEMS
	private static int PROOF_OF_AVENGER = 7188;
	//CHANCE
	private static int DROP_CHANCE = 50;
	//MOBS
	private static int[] MOBS = {21306, 21308, 21309, 21310, 21311};
	private static int[][] REWARDS = {{6699, 90000, 0, 19}, {6698, 80000, 20, 39}, {6700, 40000, 40, 49}, {0, 230000, 50, 100}};

	public _601_WatchingEyes()
	{
		super(601, "_601_WatchingEyes", "Watching Eyes");

		addStartNpc(EYE_OF_ARGOS);

		addKillId(MOBS);

		addQuestItem(PROOF_OF_AVENGER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("31683-1.htm"))
			if(st.getPlayer().getLevel() < 71)
			{
				htmltext = "31683-0a.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				st.setState(STARTED);
				st.set("cond", "1");
				st.playSound(SOUND_ACCEPT);
			}
		else if(event.equalsIgnoreCase("31683-4.htm"))
		{
			int random = Rnd.get(101);
			int i = 0;
			int item = 0;
			int adena = 0;
			while(i < REWARDS.length)
			{
				item = REWARDS[i][0];
				adena = REWARDS[i][1];
				if(REWARDS[i][2] <= random && random <= REWARDS[i][3])
					break;
				i++;
			}
			st.rollAndGive(57, adena, 100);
			if(item != 0)
			{
				st.giveItems(item, 5);
				st.addExpAndSp(120000, 10000);
			}
			st.takeItems(PROOF_OF_AVENGER, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int cond = st.getInt("cond");

		if(st.isCreated())
			htmltext = "31683-0.htm";
		else if(cond == 1)
			htmltext = "31683-2.htm";
		else if(cond == 2 && st.getQuestItemsCount(PROOF_OF_AVENGER) == 100)
			htmltext = "31683-3.htm";
		else
		{
			htmltext = "31683-4a.htm";
			st.set("cond", "1");
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(PROOF_OF_AVENGER, 1, DROP_CHANCE, 100))
		{
			if(st.getQuestItemsCount(PROOF_OF_AVENGER) == 100)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}