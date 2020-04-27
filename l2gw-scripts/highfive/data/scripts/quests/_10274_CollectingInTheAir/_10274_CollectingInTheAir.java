package quests._10274_CollectingInTheAir;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10274_CollectingInTheAir extends Quest
{
	private final static int Lekon = 32557;

	private final static int StarStoneExtractionScroll = 13844;
	private final static int ExpertTextStarStoneExtractionSkillLevel1 = 13728;
	private final static int ExtractedCoarseRedStarStone = 13858;
	private final static int ExtractedCoarseBlueStarStone = 13859;
	private final static int ExtractedCoarseGreenStarStone = 13860;

	public _10274_CollectingInTheAir()
	{
		super(10274, "_10274_CollectingInTheAir", "Collecting In The Air");

		addStartNpc(Lekon);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "32557-0a.htm";
		if(event.equalsIgnoreCase("32557-03.htm"))
		{
			st.set("cond", "1");
			st.giveItems(StarStoneExtractionScroll, 8);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext;
		if(st.isCompleted())
			htmltext = "32557-0a.htm";
		else if(st.isCreated())
		{
			QuestState qs = st.getPlayer().getQuestState("_10273_GoodDayToFly");
			if(qs != null && qs.isCompleted() && st.getPlayer().getLevel() >= 75)
				htmltext = "32557-01.htm";
			else
				htmltext = "32557-00.htm";
		}
		else if(st.getQuestItemsCount(ExtractedCoarseRedStarStone) + st.getQuestItemsCount(ExtractedCoarseBlueStarStone) + st.getQuestItemsCount(ExtractedCoarseGreenStarStone) >= 8)
		{
			htmltext = "32557-05.htm";
			st.takeItems(ExtractedCoarseRedStarStone, -1);
			st.takeItems(ExtractedCoarseBlueStarStone, -1);
			st.takeItems(ExtractedCoarseGreenStarStone, -1);
			st.giveItems(ExpertTextStarStoneExtractionSkillLevel1, 1);
			st.addExpAndSp(25160, 2525);
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		else
			htmltext = "32557-04.htm";
		return htmltext;
	}
}