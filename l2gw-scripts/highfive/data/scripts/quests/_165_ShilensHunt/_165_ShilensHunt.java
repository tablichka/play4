package quests._165_ShilensHunt;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _165_ShilensHunt extends Quest
{
	private static final int DARK_BEZOAR = 1160;
	private static final int LESSER_HEALING_POTION = 1060;

	public _165_ShilensHunt()
	{
		super(165, "_165_ShilensHunt", "Shilien's Hunt");

		addStartNpc(30348);

		addTalkId(30348);

		addKillId(20456);
		addKillId(20529);
		addKillId(20532);
		addKillId(20536);

		addQuestItem(DARK_BEZOAR);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "30348-03.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");

		if(st.isCreated())
		{
			if(st.getPlayer().getRace() != Race.darkelf)
				htmltext = "30348-00.htm";
			else if(st.getPlayer().getLevel() >= 3)
			{
				htmltext = "30348-02.htm";
				return htmltext;
			}
			else
			{
				htmltext = "30348-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1 || st.getQuestItemsCount(DARK_BEZOAR) < 13)
			htmltext = "30348-04.htm";
		else if(cond == 2)
		{
			htmltext = "30348-05.htm";
			st.takeItems(DARK_BEZOAR, -1);
			st.giveItems(LESSER_HEALING_POTION, 5);
			st.addExpAndSp(1000, 0);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		if(cond == 1 && st.rollAndGiveLimited(DARK_BEZOAR, 1, 90, 13))
		{
			if(st.getQuestItemsCount(DARK_BEZOAR) == 13)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}