package quests._012_SecretMeetingWithVarkaSilenos;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _012_SecretMeetingWithVarkaSilenos extends Quest
{
	private static final int CADMON = 31296;
	private static final int HELMUT = 31258;
	private static final int NARAN_ASHANUK = 31378;

	private static final short MUNITIONS_BOX = 7232;

	public _012_SecretMeetingWithVarkaSilenos()
	{
		super(12, "_012_SecretMeetingWithVarkaSilenos", "Secret Meeting with Varka Silenos");

		addStartNpc(CADMON);
		addTalkId(HELMUT, NARAN_ASHANUK);
		addQuestItem(MUNITIONS_BOX);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("31296-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31258-2.htm"))
		{
			st.giveItems(MUNITIONS_BOX, 1);
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31378-2.htm"))
		{
			st.addExpAndSp(233125, 18142);
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
		if(st.isCreated() && npcId == CADMON)
		{
			if(st.getPlayer().getLevel() < 74)
			{
				htmltext = "31296-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31296-1.htm";
		}
		else if(st.isStarted())
		{
			switch(npcId)
			{
				case CADMON:
				{
					if(cond == 1)
						htmltext = "31296-2r.htm";
					break;
				}
				case HELMUT:
				{
					if(cond == 1)
						htmltext = "31258-1.htm";
					else if(cond == 2)
						htmltext = "31258-2r.htm";
					break;
				}
				case NARAN_ASHANUK:
				{
					if(cond == 2 && st.getQuestItemsCount(MUNITIONS_BOX) > 0)
						htmltext = "31378-1.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}
