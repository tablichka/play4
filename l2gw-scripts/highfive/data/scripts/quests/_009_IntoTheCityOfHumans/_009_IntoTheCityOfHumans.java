package quests._009_IntoTheCityOfHumans;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _009_IntoTheCityOfHumans extends Quest
{
	//NPC
	private static final int PETUKAI = 30583;
	private static final int TANAPI = 30571;
	private static final int TAMIL = 30576;
	//Items
	private static final short SCROLL_OF_ESCAPE_GIRAN = 7126;
	private static final short MARK_OF_TRAVELER = 7570;

	public _009_IntoTheCityOfHumans()
	{
		super(9, "_009_IntoTheCityOfHumans", "Into the City of Humans");

		addStartNpc(PETUKAI);
		addTalkId(TANAPI, TAMIL);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("30583-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30571-02.htm"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30576-02.htm"))
		{
			st.giveItems(SCROLL_OF_ESCAPE_GIRAN, 1);
			st.giveItems(MARK_OF_TRAVELER, 1);
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
		if(st.isCreated() && npcId == PETUKAI)
		{
			if(st.getPlayer().getRace() == Race.orc && st.getPlayer().getLevel() > 2)
				htmltext = "30583-02.htm";
			else
			{
				htmltext = "30583-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case PETUKAI:
				{
					if(cond == 1)
						htmltext = "30583-04.htm";
					break;
				}
				case TANAPI:
				{
					if(cond == 1)
						htmltext = "30571-01.htm";
					else if(cond == 2)
						htmltext = "30571-03.htm";
					break;
				}
				case TAMIL:
				{
					if(cond == 2)
						htmltext = "30576-01.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}
