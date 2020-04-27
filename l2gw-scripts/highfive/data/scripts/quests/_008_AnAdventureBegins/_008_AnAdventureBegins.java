package quests._008_AnAdventureBegins;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _008_AnAdventureBegins extends Quest
{
	//NPC
	private static final int JASMINE = 30134;
	private static final int ROSELYN = 30355;
	private static final int HARNE = 30144;
	//Quest Item
	private static final short ROSELYNS_NOTE = 7573;
	//Items
	private static final short SCROLL_OF_ESCAPE_GIRAN = 7126;
	private static final short MARK_OF_TRAVELER = 7570;

	public _008_AnAdventureBegins()
	{
		super(8, "_008_AnAdventureBegins", "An Adventure Begins");

		addStartNpc(JASMINE);
		addTalkId(ROSELYN, HARNE);
		addQuestItem(ROSELYNS_NOTE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("30134-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30355-02.htm"))
		{
			st.giveItems(ROSELYNS_NOTE, 1);
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30144-02.htm"))
		{
			st.takeItems(ROSELYNS_NOTE, -1);
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30134-06.htm"))
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
		if(st.isCreated() && npcId == JASMINE)
		{
			if(st.getPlayer().getRace() == Race.darkelf && st.getPlayer().getLevel() > 2)
				htmltext = "30134-02.htm";
			else
			{
				htmltext = "30134-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case JASMINE:
				{
					if(cond == 1)
						htmltext = "30134-04.htm";
					else if(cond == 3)
						htmltext = "30134-05.htm";
					break;
				}
				case ROSELYN:
				{
					if(cond == 1)
						htmltext = "30355-01.htm";
					else if(cond == 2)
						htmltext = "30355-03.htm";
					break;
				}
				case HARNE:
				{
					if(cond == 2 && st.getQuestItemsCount(ROSELYNS_NOTE) > 0)
						htmltext = "30144-01.htm";
					else if(cond == 2)
					{
						htmltext = "30144-havent.htm";
						st.set("cond", "1");
						st.setState(STARTED);
					}
					else if(cond == 3)
						htmltext = "30144-02r.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}