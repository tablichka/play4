package quests._016_TheComingDarkness;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * One-time
 * Solo
 */
public class _016_TheComingDarkness extends Quest
{
	//npc
	private static final int HIERARCH = 31517;
	//ALTAR_LIST (MOB_ID, cond)
	private static final int[][] altarList = {{31512, 1}, {31513, 2}, {31514, 3}, {31515, 4}, {31516, 5}};
	//items
	private static final short CrystalOfSeal = 7167;

	public _016_TheComingDarkness()
	{
		super(16, "_016_TheComingDarkness", "The Coming Darkness");

		addStartNpc(HIERARCH);
		for(int[] element : altarList)
			addTalkId(element[0]);
		addQuestItem(CrystalOfSeal);
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
			st.giveItems(CrystalOfSeal, 5);
			st.playSound(SOUND_ACCEPT);
		}
		for(int[] element : altarList)
			if(event.equalsIgnoreCase(String.valueOf(element[0]) + "-02.htm"))
			{
				st.takeItems(CrystalOfSeal, 1);
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
			if(st.getPlayer().getLevel() < 61)
			{
				htmltext = "31517-00.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31517-01.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == HIERARCH)
			{
				if(cond > 0 && cond < 6)
					if(st.getQuestItemsCount(CrystalOfSeal) > 0)
						htmltext = "31517-02r.htm";
					else
					{
						htmltext = "31517-proeb.htm";
						st.exitCurrentQuest(false);
					}
				else if(cond > 5 && st.getQuestItemsCount(CrystalOfSeal) < 1)
				{
					htmltext = "31517-03.htm";
					st.addExpAndSp(865187, 69172);
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
							if(st.getQuestItemsCount(CrystalOfSeal) > 0)
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