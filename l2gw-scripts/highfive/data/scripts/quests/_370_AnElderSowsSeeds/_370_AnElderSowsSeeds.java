package quests._370_AnElderSowsSeeds;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _370_AnElderSowsSeeds extends Quest
{
	//npc
	private static int CASIAN = 30612;
	//mobs
	private static int[] MOBS = {20082, 20084, 20086, 20089, 20090};
	//items
	private static int SPB_PAGE = 5916;
	//Collection Kranvel's Spellbooks
	private static int[] CHAPTERS = {5917, 5918, 5919, 5920};

	public _370_AnElderSowsSeeds()
	{
		super(370, "_370_AnElderSowsSeeds", "An Elder Sows Seeds");

		addStartNpc(CASIAN);

		for(int npcId : MOBS)
			addKillId(npcId);

		addQuestItem(SPB_PAGE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("30612-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30612-6.htm"))
		{
			if(st.getQuestItemsCount(CHAPTERS[0]) > 0 && st.getQuestItemsCount(CHAPTERS[1]) > 0 && st.getQuestItemsCount(CHAPTERS[2]) > 0 && st.getQuestItemsCount(CHAPTERS[3]) > 0)
			{
				long mincount = st.getQuestItemsCount(CHAPTERS[0]);

				for(int itemId : CHAPTERS)
					mincount = Math.min(mincount, st.getQuestItemsCount(itemId));

				for(int itemId : CHAPTERS)
					st.takeItems(itemId, mincount);

				st.rollAndGive(57, 3600 * mincount, 100);
				htmltext = "30612-8.htm";
			}
			else
				htmltext = "30612-4.htm";
		}
		else if(event.equalsIgnoreCase("30612-9.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int cond = st.getInt("cond");

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() < 28)
			{
				htmltext = "30612-0a.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30612-0.htm";
		}
		else if(cond == 1)
			htmltext = "30612-4.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.getCond() == 1 && st.rollAndGive(SPB_PAGE, 1, 15))
			st.playSound(SOUND_ITEMGET);
	}
}