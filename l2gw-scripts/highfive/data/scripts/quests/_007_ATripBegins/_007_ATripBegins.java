package quests._007_ATripBegins;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _007_ATripBegins extends Quest
{
	//NPC
	private static final int MIRABEL = 30146;
	private static final int ARIEL = 30148;
	private static final int ASTERIOS = 30154;
	//Quest Item
	private static final short ARIELS_RECOMMENDATION = 7572;
	//Items
	private static final short SCROLL_OF_ESCAPE_GIRAN = 7126;
	private static final short MARK_OF_TRAVELER = 7570;

	public _007_ATripBegins()
	{
		super(7, "_007_ATripBegins", "A Trip Begins");

		addStartNpc(MIRABEL);
		addTalkId(ARIEL, ASTERIOS);
		addQuestItem(ARIELS_RECOMMENDATION);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("30146-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30148-02.htm"))
		{
			st.giveItems(ARIELS_RECOMMENDATION, 1);
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30154-02.htm"))
		{
			st.takeItems(ARIELS_RECOMMENDATION, -1);
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30146-06.htm"))
		{
			st.giveItems(SCROLL_OF_ESCAPE_GIRAN, 1);
			st.giveItems(MARK_OF_TRAVELER, 1);
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

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(st.isCreated() && npcId == MIRABEL)
		{
			if(st.getPlayer().getRace() == Race.elf && st.getPlayer().getLevel() > 2)
				htmltext = "30146-02.htm";
			else
			{
				htmltext = "30146-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case MIRABEL:
				{
					if(cond == 1)
						htmltext = "30146-04.htm";
					else if(cond == 3)
						htmltext = "30146-05.htm";
					break;
				}
				case ARIEL:
				{
					if(cond == 1)
						htmltext = "30148-01.htm";
					else if(cond == 2)
						htmltext = "30148-03.htm";
					break;
				}
				case ASTERIOS:
				{
					if(cond == 2 && st.getQuestItemsCount(ARIELS_RECOMMENDATION) > 0)
						htmltext = "30154-01.htm";
					else if(cond == 2)
					{
						htmltext = "30154-havent.htm";
						st.set("cond", "1");
						st.setState(STARTED);
					}
					else if(cond == 3)
						htmltext = "30154-02r.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}