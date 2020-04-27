package quests._011_SecretMeetingWithKetraOrcs;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _011_SecretMeetingWithKetraOrcs extends Quest
{
	private static final int CADMON = 31296;
	private static final int LEON = 31256;
	private static final int WAHKAN = 31371;

	private static final short MUNITIONS_BOX = 7231;

	public _011_SecretMeetingWithKetraOrcs()
	{
		super(11, "_011_SecretMeetingWithKetraOrcs", "Secret Meeting with Ketra Orcs");

		addStartNpc(CADMON);
		addTalkId(LEON, WAHKAN);
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
		else if(event.equalsIgnoreCase("31256-2.htm"))
		{
			st.giveItems(MUNITIONS_BOX, 1);
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("31371-2.htm"))
		{
			st.addExpAndSp(82045, 6047);
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
				case LEON:
				{
					if(cond == 1)
						htmltext = "31256-1.htm";
					else if(cond == 2)
						htmltext = "31256-2r.htm";
					break;
				}
				case WAHKAN:
				{
					if(cond == 2 && st.getQuestItemsCount(MUNITIONS_BOX) > 0)
						htmltext = "31371-1.htm";
					break;
				}
			}
		}
		return htmltext;
	}
}
