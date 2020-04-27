package quests._10267_JourneyToGracia;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10267_JourneyToGracia extends Quest
{
	private final static int Orven = 30857;
	private final static int Keucereus = 32548;
	private final static int Papiku = 32564;

	private final static int Letter = 13810;

	public _10267_JourneyToGracia()
	{
		super(10267, "_10267_JourneyToGracia", "Journey To Gracia");

		addStartNpc(Orven);

		addTalkId(Keucereus);
		addTalkId(Papiku);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30857-06.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(Letter, 1);
		}
		else if(event.equalsIgnoreCase("32564-02.htm"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("32548-02.htm"))
		{
			st.rollAndGive(57, 92500, 100);
			st.addExpAndSp(75480, 7570);
			st.unset("cond");
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");

		if(st.isCompleted())
		{
			if(npcId == Keucereus)
				htmltext = "32548-03.htm";
			else if(npcId == Orven)
				htmltext = "30857-0a.htm";
		}
		else if(st.isCreated())
		{
			if(npcId == Orven)
				if(st.getPlayer().getLevel() < 75)
					htmltext = "30857-00.htm";
				else
					htmltext = "30857-01.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == Orven)
				htmltext = "30857-07.htm";
			else if(npcId == Papiku)
			{
				if(cond == 1)
					htmltext = "32564-01.htm";
				else
					htmltext = "32564-03.htm";
			}
			else if(npcId == Keucereus && cond == 2)
				htmltext = "32548-01.htm";
		}
		return htmltext;
	}
}