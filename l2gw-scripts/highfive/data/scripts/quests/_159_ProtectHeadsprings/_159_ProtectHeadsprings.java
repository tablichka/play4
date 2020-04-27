package quests._159_ProtectHeadsprings;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _159_ProtectHeadsprings extends Quest
{
	int PLAGUE_DUST_ID = 1035;
	int HYACINTH_CHARM1_ID = 1071;
	int HYACINTH_CHARM2_ID = 1072;

	public _159_ProtectHeadsprings()
	{
		super(159, "_159_ProtectHeadsprings", "Protect Headsprings");

		addStartNpc(30154);

		addKillId(27017);

		addQuestItem(PLAGUE_DUST_ID, HYACINTH_CHARM1_ID, HYACINTH_CHARM2_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			if(st.getQuestItemsCount(HYACINTH_CHARM1_ID) == 0)
			{
				st.giveItems(HYACINTH_CHARM1_ID, 1);
				htmltext = "30154-04.htm";
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(cond == 0)
		{
			if(st.getPlayer().getRace() != Race.elf)
			{
				htmltext = "30154-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() >= 12)
			{
				htmltext = "30154-03.htm";
				return htmltext;
			}
			else
			{
				htmltext = "30154-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
			htmltext = "30154-05.htm";
		else if(cond == 2)
		{
			st.takeItems(PLAGUE_DUST_ID, -1);
			st.takeItems(HYACINTH_CHARM1_ID, -1);
			st.giveItems(HYACINTH_CHARM2_ID, 1);
			st.set("cond", "3");
			htmltext = "30154-06.htm";
		}
		else if(cond == 3)
			htmltext = "30154-07.htm";
		else if(cond == 4)
		{
			st.takeItems(PLAGUE_DUST_ID, -1);
			st.takeItems(HYACINTH_CHARM2_ID, -1);
			st.rollAndGive(57, 18250, 100);
			st.playSound(SOUND_FINISH);
			htmltext = "30154-08.htm";
			st.exitCurrentQuest(false);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");

		if(cond == 1 && st.rollAndGiveLimited(PLAGUE_DUST_ID, 1, 60, 1))
		{
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
			st.setState(STARTED);
		}
		else if(cond == 3 && st.rollAndGiveLimited(PLAGUE_DUST_ID, 1, 60, 4))
		{
			if(st.getQuestItemsCount(PLAGUE_DUST_ID) == 4)
			{
				st.set("cond", "4");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}