package quests._017_LightAndDarkness;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _017_LightAndDarkness extends Quest
{
	//npc
	private static final int HIERARCH = 31517;
	//ALTAR_LIST (MOB_ID, cond)
	private static final int[][] altarList = {{31508, 1}, {31509, 2}, {31510, 3}, {31511, 4}};
	//items	
	private static final short BloodOfSaint = 7168;

	public _017_LightAndDarkness()
	{
		super(17, "_017_LightAndDarkness", "Light And Darkness");

		addStartNpc(HIERARCH);

		for(int[] element : altarList)
			addTalkId(element[0]);

		addQuestItem(BloodOfSaint);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equalsIgnoreCase("31517-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.giveItems(BloodOfSaint, 4);
			st.playSound(SOUND_ACCEPT);
		}
		for(int[] element : altarList)
			if(event.equalsIgnoreCase(String.valueOf(element[0]) + "-02.htm"))
			{
				st.takeItems(BloodOfSaint, 1);
				st.set("cond", String.valueOf(element[1] + 1));
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				break;
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
		if(st.isCreated() && npcId == HIERARCH)
		{
			if(st.getPlayer().getLevel() >= 61)
				htmltext = "31517-01.htm";
			else
			{
				htmltext = "31517-00.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == HIERARCH)
			{
				if(cond > 0 && cond < 5)
					if(st.getQuestItemsCount(BloodOfSaint) > 0)
						htmltext = "31517-02r.htm";
					else
					{
						htmltext = "31517-proeb.htm";
						st.exitCurrentQuest(false);
					}
				else if(cond == 5 && st.getQuestItemsCount(BloodOfSaint) < 1)
				{
					htmltext = "31517-03.htm";
					st.addExpAndSp(697040, 54887);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
				}
			}
			else
			{
				for(int[] element : altarList)
					if(npcId == element[0])
					{
						if(cond == element[1])
						{
							if(st.getQuestItemsCount(BloodOfSaint) > 0)
								htmltext = String.valueOf(element[0]) + "-01.htm";
							else
								htmltext = String.valueOf(element[0]) + "-03.htm";
						}
						else if(cond > element[1])
							htmltext = String.valueOf(element[0]) + "-04.htm";
						break;
					}
			}
		}
		return htmltext;
	}
}