package quests._161_FruitsOfMothertree;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _161_FruitsOfMothertree extends Quest
{
	int ANDELLRIAS_LETTER_ID = 1036;
	int MOTHERTREE_FRUIT_ID = 1037;
	int ADENA_ID = 57;

	public _161_FruitsOfMothertree()
	{
		super(161, "_161_FruitsOfMothertree", "Fruits Of Mothertree");

		addStartNpc(30362);

		addTalkId(30362);

		addTalkId(30362);
		addTalkId(30371);

		addQuestItem(MOTHERTREE_FRUIT_ID, ANDELLRIAS_LETTER_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("id", "0");
			htmltext = "30362-04.htm";
			st.giveItems(ANDELLRIAS_LETTER_ID, 1);
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
		if(st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "0");
			st.set("id", "0");
		}
		if(npcId == 30362 && st.getInt("cond") == 0)
		{
			if(st.getInt("cond") < 15)
			{
				if(st.getPlayer().getRace().ordinal() != 1)
					htmltext = "30362-00.htm";
				else if(st.getPlayer().getLevel() >= 3)
				{
					htmltext = "30362-03.htm";
					return htmltext;
				}
				else
				{
					htmltext = "30362-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				htmltext = "30362-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == 30362 && st.getInt("cond") > 0)
		{
			if(st.getQuestItemsCount(ANDELLRIAS_LETTER_ID) == 1 && st.getQuestItemsCount(MOTHERTREE_FRUIT_ID) == 0)
				htmltext = "30362-05.htm";
			else if(st.getQuestItemsCount(MOTHERTREE_FRUIT_ID) == 1)
			{
				htmltext = "30362-06.htm";
				st.giveItems(ADENA_ID, 500);
				st.addExpAndSp(1000, 0);
				st.takeItems(MOTHERTREE_FRUIT_ID, 1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
		}
		else if(npcId == 30371 && st.getInt("cond") == 1)
			if(st.getQuestItemsCount(ANDELLRIAS_LETTER_ID) == 1)
			{
				if(st.getInt("id") != 161)
				{
					st.set("id", "161");
					htmltext = "30371-01.htm";
					st.giveItems(MOTHERTREE_FRUIT_ID, 1);
					st.takeItems(ANDELLRIAS_LETTER_ID, 1);
				}
			}
			else if(st.getQuestItemsCount(MOTHERTREE_FRUIT_ID) == 1)
				htmltext = "30371-02.htm";
		return htmltext;
	}
}