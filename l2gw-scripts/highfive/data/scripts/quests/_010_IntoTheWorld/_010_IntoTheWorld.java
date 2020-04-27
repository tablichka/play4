package quests._010_IntoTheWorld;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _010_IntoTheWorld extends Quest
{
	//NPC
	private static final int BALANKI = 30533;
	private static final int REED = 30520;
	private static final int GERALD = 30650;
	//Quest Item
	private static final short VERY_EXPENSIVE_NECKLACE = 7574;
	//Items
	private static final short SCROLL_OF_ESCAPE_GIRAN = 7126;
	private static final short MARK_OF_TRAVELER = 7570;

	public _010_IntoTheWorld()
	{
		super(10, "_010_IntoTheWorld", "Into the World");

		addStartNpc(BALANKI);
		addTalkId(REED, GERALD);
		addQuestItem(VERY_EXPENSIVE_NECKLACE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30533-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30520-02.htm"))
		{
			st.giveItems(VERY_EXPENSIVE_NECKLACE, 1);
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30650-02.htm"))
		{
			st.takeItems(VERY_EXPENSIVE_NECKLACE, -1);
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30520-05.htm"))
		{
			st.set("cond", "4");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30533-06.htm"))
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
		if(st.isCreated() && npcId == BALANKI)
		{
			if(st.getPlayer().getRace() == Race.dwarf && st.getPlayer().getLevel() > 2)
				htmltext = "30533-02.htm";
			else
			{
				htmltext = "30533-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case BALANKI:
				{
					if(cond == 1)
						htmltext = "30533-04.htm";
					else if(cond == 4)
						htmltext = "30533-05.htm";
					break;
				}
				case REED:
				{
					if(cond == 1)
						htmltext = "30520-01.htm";
					else if(cond == 2)
						htmltext = "30520-03.htm";
					else if(cond == 3)
						htmltext = "30520-04.htm";
					else if(cond == 4)
						htmltext = "30520-06.htm";
					break;
				}
				case GERALD:
				{
					if(cond == 2 && st.getQuestItemsCount(VERY_EXPENSIVE_NECKLACE) > 0)
						htmltext = "30650-01.htm";
					else if(cond == 2)
					{
						htmltext = "30650-03.htm";
						st.set("cond", "1");
						st.setState(STARTED);
					}
					else if(cond == 3)
						htmltext = "30650-04.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}