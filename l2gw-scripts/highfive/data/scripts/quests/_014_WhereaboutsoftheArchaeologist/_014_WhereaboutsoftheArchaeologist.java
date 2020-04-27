package quests._014_WhereaboutsoftheArchaeologist;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solor
 */
public class _014_WhereaboutsoftheArchaeologist extends Quest
{
	private static final int Liesel = 31263;
	private static final int GhostOfAdventurer = 31538;

	private static final short LETTER_TO_ARCHAEOLOGIST = 7253;

	public _014_WhereaboutsoftheArchaeologist()
	{
		super(14, "_014_WhereaboutsoftheArchaeologist", "Whereabouts of the Archaeologist");

		addStartNpc(Liesel);
		addTalkId(GhostOfAdventurer);
		addQuestItem(LETTER_TO_ARCHAEOLOGIST);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("31263-2.htm"))
		{
			st.set("cond", "1");
			st.giveItems(LETTER_TO_ARCHAEOLOGIST, 1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31538-2.htm"))
		{
			st.addExpAndSp(325881, 32524);
			st.rollAndGive(57, 136928, 100);
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
		if(st.isCreated() && npcId == Liesel)
		{
			if(st.getPlayer().getLevel() < 74)
			{
				htmltext = "31263-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31263-1.htm";
		}
		else if(st.isStarted() && st.getInt("cond") == 1)
		{
			switch(npcId)
			{
				case Liesel:
				{
					htmltext = "31263-2.htm";
					break;
				}
				case GhostOfAdventurer:
				{
					if(st.getQuestItemsCount(LETTER_TO_ARCHAEOLOGIST) > 0)
						htmltext = "31538-1.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}