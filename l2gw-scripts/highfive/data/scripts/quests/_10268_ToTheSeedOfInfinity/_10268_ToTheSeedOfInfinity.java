package quests._10268_ToTheSeedOfInfinity;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10268_ToTheSeedOfInfinity extends Quest
{
	private final static int Keucereus = 32548;
	private final static int Tepios = 32603;

	private final static int Introduction = 13811;

	public _10268_ToTheSeedOfInfinity()
	{
		super(10268, "_10268_ToTheSeedOfInfinity", "To The Seed Of Infinity");

		addStartNpc(Keucereus);
		addTalkId(Tepios);
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
			return "completed";
		if(st.isCreated())
		{
			if(npcId == Keucereus)
				if(st.getPlayer().getLevel() < 75)
					htmltext = "32548-00.htm";
				else
					htmltext = "32548-01.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == Keucereus)
				htmltext = "32548-06.htm";
			else if(npcId == Tepios)
			{
				htmltext = "32530-01.htm";
				st.rollAndGive(57, 16671, 100);
				st.addExpAndSp(100640, 10098);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
			}
		}
		return htmltext;
	}
}