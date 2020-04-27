package quests._109_InSearchOfTheNest;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _109_InSearchOfTheNest extends Quest
{
	//NPC
	private static final int PIERCE = 31553;
	private static final int CORPSE = 32015;
	private static final int KAHMAN = 31554;
	//QUEST ITEMS
	private static final int SCOUT_NOTE = 14858;

	public _109_InSearchOfTheNest()
	{
		super(109, "_109_InSearchOfTheNest", "In Search Of The Nest");
		addStartNpc(PIERCE);
		addTalkId(CORPSE);
		addTalkId(KAHMAN);

		addQuestItem(SCOUT_NOTE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("accept"))
		{
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
			htmltext = "31553-02.htm";
		}
		else if(event.equalsIgnoreCase("search"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
			st.giveItems(SCOUT_NOTE, 1);
			htmltext = "32015-02.htm";
		}
		else if(event.equalsIgnoreCase("readmemo"))
		{
			st.set("cond", "3");
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
			st.takeItems(SCOUT_NOTE, 1);
			htmltext = "31553-05.htm";
		}
		else if(event.equalsIgnoreCase("piercesentme"))
		{
			htmltext = "31554-02.htm";
			st.addExpAndSp(701500, 50000);
			st.giveItems(57, 161500);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		String htmltext = "noquest";

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 81)
			{
				htmltext = "31553-01.htm";
			}
			else
			{
				htmltext = "31553-00.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			if(npcId == CORPSE)
			{
				if(cond == 1)
					htmltext = "32015-01.htm";
				else if(cond == 2)
					htmltext = "32015-03.htm";
			}
			else if(npcId == PIERCE)
			{
				if(cond == 1)
					htmltext = "31553-03.htm";
				if(cond == 2)
					htmltext = "31553-04.htm";
				if(cond == 3)
					htmltext = "31553-06.htm";

			}
			else if(npcId == KAHMAN && cond == 3)
			{
				htmltext = "31554-01.htm";
			}
		return htmltext;
	}
}