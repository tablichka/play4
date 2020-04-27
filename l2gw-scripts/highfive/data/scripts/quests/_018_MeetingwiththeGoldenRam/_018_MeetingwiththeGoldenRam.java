package quests._018_MeetingwiththeGoldenRam;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _018_MeetingwiththeGoldenRam extends Quest
{
	private static final int Donal = 31314;
	private static final int Daisy = 31315;
	private static final int Abercrombie = 31555;

	private static final short SupplyBox = 7245;

	public _018_MeetingwiththeGoldenRam()
	{
		super(18, "_018_MeetingwiththeGoldenRam", "Meeting with the Golden Ram");

		addStartNpc(Donal);
		addTalkId(Daisy);
		addTalkId(Abercrombie);
		addQuestItem(SupplyBox);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("31314-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31315-2.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.giveItems(SupplyBox, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31555-2.htm"))
		{
			st.rollAndGive(57, 40000, 100);
			st.addExpAndSp(126668, 11731);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
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
		if(st.isCreated() && npcId == Donal)
		{
			if(st.getPlayer().getLevel() < 66)
			{
				htmltext = "31314-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31314-1.htm";
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case Donal:
				{
					if(cond == 1)
						htmltext = "31314-2r.htm";
					break;
				}
				case Daisy:
				{
					if(cond == 1)
						htmltext = "31315-1.htm";
					else if(cond == 2)
						htmltext = "31315-2r.htm";
					break;
				}
				case Abercrombie:
				{
					if(cond == 2 && st.getQuestItemsCount(SupplyBox) > 0)
						htmltext = "31555-1.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}