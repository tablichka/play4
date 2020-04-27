package quests._040_ASpecialOrder;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * -=* Квест A Special Order*=-
 */

public class _040_ASpecialOrder extends Quest
{
	//NPC
	private final static int HELVETIA = 30081;
	private final static int OFULLE = 31572;
	private final static int GESTO = 30511;

	//ITEMS
	private final static int ORANGE_NIMBLE_FISH = 6450;
	private final static int ORANGE_UNGLY_FISH = 6451;
	private final static int ORANGE_FAT_FISH = 6452;
	private final static int FISH_CHEST = 12764;
	private final static int GOLDEN_COBOL = 5079;
	private final static int THORN_COBOL = 5082;
	private final static int GREAT_COBOL = 5084;
	private final static int SEED_JAR = 12765;
	private final static int WONDROUS_CUBIC = 10632;

	//MINLEVEL
	private final static int MINLEVEL = 40;

	public _040_ASpecialOrder()
	{
		super(40, "_040_ASpecialOrder", "A Special Order");

		addStartNpc(HELVETIA);

		addTalkId(HELVETIA);
		addTalkId(OFULLE);
		addTalkId(GESTO);

		addQuestItem(FISH_CHEST);
		addQuestItem(SEED_JAR);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30081-02.htm"))
		{
			st.set("cond", "1");
			int condition = Rnd.get(1, 2);
			if(condition == 1)
			{
				st.set("cond", "2");
				htmltext = "30081-02a.htm";
			}
			else
			{
				st.set("cond", "5");
				htmltext = "30081-02b.htm";
			}
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30511-03.htm"))
		{
			st.set("cond", "6");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31572-03.htm"))
		{
			st.set("cond", "3");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30081-05a.htm"))
		{
			st.unset("cond");
			st.takeItems(FISH_CHEST, 1);
			st.giveItems(WONDROUS_CUBIC, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		else if(event.equalsIgnoreCase("30081-05b.htm"))
		{
			st.unset("cond");
			st.takeItems(SEED_JAR, 1);
			st.giveItems(WONDROUS_CUBIC, 1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = ("30081-00.htm");
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == HELVETIA)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < MINLEVEL || st.getQuestItemsCount(WONDROUS_CUBIC) > 0)
				{
					htmltext = "30081-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30081-01.htm";
			}
			else if(cond == 2)
				htmltext = "30081-03a.htm";
			else if(cond == 5)
				htmltext = "30081-03b.htm";
			else if(cond == 4)
				htmltext = "30081-04a.htm";
			else if(cond == 7)
				htmltext = "30081-04b.htm";
		}
		else if(npcId == OFULLE)
		{
			if(cond == 2)
				htmltext = "31572-01.htm";
			else if(cond == 3)
				if(st.getQuestItemsCount(ORANGE_NIMBLE_FISH) >= 10 && st.getQuestItemsCount(ORANGE_UNGLY_FISH) >= 10 && st.getQuestItemsCount(ORANGE_FAT_FISH) >= 10)
				{
					st.set("cond", "4");
					st.takeItems(ORANGE_NIMBLE_FISH, 10);
					st.takeItems(ORANGE_UNGLY_FISH, 10);
					st.takeItems(ORANGE_FAT_FISH, 10);
					st.playSound(SOUND_MIDDLE);
					st.giveItems(FISH_CHEST, 1);
					htmltext = "31572-04.htm";
				}
				else
					htmltext = "31572-05.htm";
			else if(cond == 4)
				htmltext = "31572-06.htm";
		}
		else if(npcId == GESTO)
		{
			if(cond == 5)
				htmltext = "30511-01.htm";
			else if(cond == 6)
				if(st.getQuestItemsCount(GOLDEN_COBOL) >= 40 && st.getQuestItemsCount(THORN_COBOL) >= 40 && st.getQuestItemsCount(GREAT_COBOL) >= 40)
				{
					st.set("cond", "7");
					st.takeItems(GOLDEN_COBOL, 40);
					st.takeItems(THORN_COBOL, 40);
					st.takeItems(GREAT_COBOL, 40);
					st.playSound(SOUND_MIDDLE);
					st.giveItems(SEED_JAR, 1);
					htmltext = "30511-04.htm";
				}
				else
					htmltext = "30511-05.htm";
			else if(cond == 7)
				htmltext = "30511-06.htm";
		}
		return htmltext;
	}
}