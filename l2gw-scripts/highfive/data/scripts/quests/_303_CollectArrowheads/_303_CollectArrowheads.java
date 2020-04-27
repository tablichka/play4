package quests._303_CollectArrowheads;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _303_CollectArrowheads extends Quest
{
	int ORCISH_ARROWHEAD = 963;
	int ADENA = 57;

	public _303_CollectArrowheads()
	{
		super(303, "_303_CollectArrowheads", "Collect Arrowheads");

		addStartNpc(30029);

		addTalkId(30029);

		addKillId(20361);

		addQuestItem(ORCISH_ARROWHEAD);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30029-04.htm"))
		{
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
		String htmltext = "noquest";
		int cond = st.getInt("cond");

		if(st.isCreated())
			if(st.getPlayer().getLevel() >= 10)
				htmltext = "30029-03.htm";
			else
			{
				htmltext = "30029-02.htm";
				st.exitCurrentQuest(true);
			}
		else if(st.getQuestItemsCount(ORCISH_ARROWHEAD) < 10)
			htmltext = "30029-05.htm";
		else
		{
			st.takeItems(ORCISH_ARROWHEAD, -1);
			st.rollAndGive(ADENA, 1000, 100);
			st.addExpAndSp(2000, 200);
			st.set("kolq", String.valueOf(st.getInt("kolq") + 1));
			st.set("cond", "0");
			htmltext = "30029-06.htm";
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGiveLimited(ORCISH_ARROWHEAD, 1, 40, 10))
		{
			if(st.getQuestItemsCount(ORCISH_ARROWHEAD) == 10)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}