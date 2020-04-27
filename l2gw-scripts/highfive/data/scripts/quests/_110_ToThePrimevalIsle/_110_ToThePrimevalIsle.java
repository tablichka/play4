package quests._110_ToThePrimevalIsle;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _110_ToThePrimevalIsle extends Quest
{
	// NPC
	int ANTON = 31338;
	int MARQUEZ = 32113;

	// QUEST ITEM and REWARD
	int ANCIENT_BOOK = 8777;

	public _110_ToThePrimevalIsle()
	{
		super(110, "_110_ToThePrimevalIsle", "To The Primeval Isle");

		addStartNpc(ANTON);
		addTalkId(ANTON);

		addTalkId(MARQUEZ);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "scroll_seller_anton_q0110_05.htm";
			st.set("cond", "1");
			st.giveItems(ANCIENT_BOOK, 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("2") && st.getQuestItemsCount(ANCIENT_BOOK) > 0)
		{
			htmltext = "marquez_q0110_05.htm";
			st.playSound(SOUND_FINISH);
			st.rollAndGive(57, 191678, 100);
			st.addExpAndSp(251602, 25242);
			st.takeItems(ANCIENT_BOOK, -1);
			st.exitCurrentQuest(false);
		}
		else if(event.equals("3"))
		{
			htmltext = "marquez_q0110_06.htm";
			st.exitCurrentQuest(true);
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
		if(st.isCreated())
			if(st.getPlayer().getLevel() >= 75)
				htmltext = "scroll_seller_anton_q0110_01.htm";
			else
			{
				st.exitCurrentQuest(true);
				htmltext = "scroll_seller_anton_q0110_02.htm";
			}
		else if(npcId == ANTON)
		{
			if(cond == 1)
				htmltext = "scroll_seller_anton_q0110_07.htm";
		}
		else if(st.isStarted())
			if(npcId == MARQUEZ && cond == 1)
				if(st.getQuestItemsCount(ANCIENT_BOOK) == 0)
					htmltext = "marquez_q0110_07.htm";
				else
					htmltext = "marquez_q0110_01.htm";
		return htmltext;
	}
}