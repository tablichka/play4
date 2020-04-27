package quests._044_HelpTheSon;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _044_HelpTheSon extends Quest
{
	private static final int LUNDY = 30827;
	private static final int DRIKUS = 30505;

	private static final int WORK_HAMMER = 168;
	private static final int GEMSTONE_FRAGMENT = 7552;
	private static final int GEMSTONE = 7553;
	private static final int PET_TICKET = 7585;

	private static final int MAILLE_GUARD = 20921;
	private static final int MAILLE_SCOUT = 20920;
	private static final int MAILLE_LIZARDMAN = 20919;

	public _044_HelpTheSon()
	{
		super(44, "_044_HelpTheSon", "Help The Son");

		addStartNpc(LUNDY);

		addTalkId(LUNDY);
		addTalkId(DRIKUS);

		addKillId(MAILLE_GUARD);
		addKillId(MAILLE_SCOUT);
		addKillId(MAILLE_LIZARDMAN);

		addQuestItem(GEMSTONE_FRAGMENT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "30827-01.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("3") && st.getQuestItemsCount(WORK_HAMMER) > 0)
		{
			htmltext = "30827-03.htm";
			st.takeItems(WORK_HAMMER, 1);
			st.set("cond", "2");
		}
		else if(event.equals("4") && st.getQuestItemsCount(GEMSTONE_FRAGMENT) >= 30)
		{
			htmltext = "30827-05.htm";
			st.takeItems(GEMSTONE_FRAGMENT, -1);
			st.giveItems(GEMSTONE, 1);
			st.set("cond", "4");
		}
		else if(event.equals("5") && st.getQuestItemsCount(GEMSTONE) > 0)
		{
			htmltext = "30505-06.htm";
			st.takeItems(GEMSTONE, 1);
			st.set("cond", "5");
		}
		else if(event.equals("7"))
		{
			htmltext = "30827-07.htm";
			st.giveItems(PET_TICKET, 1);
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
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 24)
				htmltext = "30827-00.htm";
			else
			{
				st.exitCurrentQuest(true);
				htmltext = "30827-000.htm";
			}
		}
		else if(st.isStarted())
		{
			int cond = st.getInt("cond");
			if(npcId == LUNDY)
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(WORK_HAMMER) == 0)
						htmltext = "30827-01a.htm";
					else
						htmltext = "30827-02.htm";
				}
				else if(cond == 2)
					htmltext = "30827-03a.htm";
				else if(cond == 3)
					htmltext = "30827-04.htm";
				else if(cond == 4)
					htmltext = "30827-05a.htm";
				else if(cond == 5)
					htmltext = "30827-06.htm";
			}
			else if(npcId == DRIKUS)
				if(cond == 4 && st.getQuestItemsCount(GEMSTONE) > 0)
					htmltext = "30505-05.htm";
				else if(cond == 5)
					htmltext = "30505-06a.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		if(cond == 2 && st.rollAndGiveLimited(GEMSTONE_FRAGMENT, 1, 100, 30))
		{
			if(st.getQuestItemsCount(GEMSTONE_FRAGMENT) == 30)
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "3");
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}