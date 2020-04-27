package quests._431_WeddingMarch;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _431_WeddingMarch extends Quest
{
	private static int MELODY_MAESTRO_KANTABILON = 31042;
	private static int SILVER_CRYSTAL = 7540;
	private static int WEDDING_ECHO_CRYSTAL = 7062;

	public _431_WeddingMarch()
	{
		super(431, "_431_WeddingMarch", "Wedding March");

		addStartNpc(MELODY_MAESTRO_KANTABILON);

		addKillId(20786);
		addKillId(20787);

		addQuestItem(SILVER_CRYSTAL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("31042-02.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31042-05.htm"))
			if(st.getQuestItemsCount(SILVER_CRYSTAL) == 50)
			{
				st.takeItems(SILVER_CRYSTAL, -1);
				st.rollAndGive(WEDDING_ECHO_CRYSTAL, 25, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31042-06.htm";

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int condition = st.getInt("cond");
		int npcId = npc.getNpcId();

		if(npcId == MELODY_MAESTRO_KANTABILON)
			if(!st.isStarted())
			{
				if(st.getPlayer().getLevel() < 38)
				{
					htmltext = "31042-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "31042-01.htm";
			}
			else if(condition == 1)
				htmltext = "31042-03.htm";
			else if(condition == 2 && st.getQuestItemsCount(SILVER_CRYSTAL) == 50)
				htmltext = "31042-04.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();

		if(npcId == 20786 || npcId == 20787)
			if(st.getInt("cond") == 1 && st.rollAndGiveLimited(SILVER_CRYSTAL, 1, 100, 50))
			{
				if(st.getQuestItemsCount(SILVER_CRYSTAL) == 50)
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