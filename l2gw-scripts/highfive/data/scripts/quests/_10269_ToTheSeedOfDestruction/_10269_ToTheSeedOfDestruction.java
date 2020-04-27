package quests._10269_ToTheSeedOfDestruction;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10269_ToTheSeedOfDestruction extends Quest
{
	private final static int Keucereus = 32548;
	private final static int Allenos = 32526;

	private final static int Introduction = 13812;

	public _10269_ToTheSeedOfDestruction()
	{
		super(10269, "_10269_ToTheSeedOfDestruction", "To The Seed Of Destruction");

		addStartNpc(Keucereus);
		addTalkId(Allenos);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("32548-05.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(Introduction, 1);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(st.isCompleted())
			if(npcId == Allenos)
				htmltext = "32526-02.htm";
			else
				htmltext = "32548-0a.htm";
		else if(st.isCreated() && npcId == Keucereus)
			if(st.getPlayer().getLevel() < 75)
				htmltext = "32548-00.htm";
			else
				htmltext = "32548-01.htm";
		else if(st.isStarted() && npcId == Keucereus)
			htmltext = "32548-06.htm";
		else if(st.isStarted() && npcId == Allenos)
		{
			htmltext = "32526-01.htm";
			st.rollAndGive(57, 29174, 100);
			st.addExpAndSp(176121, 17671);
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}
}