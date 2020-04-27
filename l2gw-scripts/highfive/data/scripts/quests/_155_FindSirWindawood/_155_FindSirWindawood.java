package quests._155_FindSirWindawood;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _155_FindSirWindawood extends Quest
{
	int OFFICIAL_LETTER = 1019;
	int HASTE_POTION = 734;

	public _155_FindSirWindawood()
	{
		super(155, "_155_FindSirWindawood", "Find Sir Windawood");

		addStartNpc(30042);

		addTalkId(30042);
		addTalkId(30311);

		addQuestItem(OFFICIAL_LETTER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("30042-04.htm"))
		{
			st.giveItems(OFFICIAL_LETTER, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == 30042)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 3)
				{
					htmltext = "30042-03.htm";
					return htmltext;
				}
				htmltext = "30042-02.htm";
				st.exitCurrentQuest(true);
			}
			else if(cond == 1 && st.getQuestItemsCount(OFFICIAL_LETTER) == 1)
				htmltext = "30042-05.htm";
		}
		else if(npcId == 30311 && cond == 1 && st.getQuestItemsCount(OFFICIAL_LETTER) == 1)
		{
			htmltext = "30311-01.htm";
			st.takeItems(OFFICIAL_LETTER, -1);
			st.giveItems(HASTE_POTION, 1);
			st.set("cond", "0");
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}
}