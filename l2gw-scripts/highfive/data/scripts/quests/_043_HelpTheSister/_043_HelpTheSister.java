package quests._043_HelpTheSister;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _043_HelpTheSister extends Quest
{
	private static final int COOPER = 30829;
	private static final int GALLADUCCI = 30097;

	private static final int CRAFTED_DAGGER = 220;
	private static final int MAP_PIECE = 7550;
	private static final int MAP = 7551;
	private static final int PET_TICKET = 7584;

	private static final int SPECTER = 20171;
	private static final int SORROW_MAIDEN = 20197;

	private static final int MAX_COUNT = 30;
	private static final int MIN_LEVEL = 26;

	public _043_HelpTheSister()
	{
		super(43, "_043_HelpTheSister", "Help The Sister");

		addStartNpc(COOPER);

		addTalkId(COOPER);

		addTalkId(COOPER);
		addTalkId(GALLADUCCI);

		addKillId(SPECTER);
		addKillId(SORROW_MAIDEN);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "30829-01.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("3") && st.getQuestItemsCount(CRAFTED_DAGGER) > 0)
		{
			htmltext = "30829-03.htm";
			st.takeItems(CRAFTED_DAGGER, 1);
			st.set("cond", "2");
		}
		else if(event.equals("4") && st.getQuestItemsCount(MAP_PIECE) >= MAX_COUNT)
		{
			htmltext = "30829-05.htm";
			st.takeItems(MAP_PIECE, MAX_COUNT);
			st.giveItems(MAP, 1);
			st.set("cond", "4");
		}
		else if(event.equals("5") && st.getQuestItemsCount(MAP) > 0)
		{
			htmltext = "30097-06.htm";
			st.takeItems(MAP, 1);
			st.set("cond", "5");
		}
		else if(event.equals("7"))
		{
			htmltext = "30829-07.htm";
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
			if(st.getPlayer().getLevel() >= MIN_LEVEL)
				htmltext = "30829-00.htm";
			else
			{
				st.exitCurrentQuest(true);
				htmltext = "<html><head><body>This quest can only be taken by characters that have a minimum level of 26s. Return when you are more experienced.</body></html>";
			}
		}
		else if(st.isStarted())
		{
			int cond = st.getInt("cond");
			if(npcId == COOPER)
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(CRAFTED_DAGGER) == 0)
						htmltext = "30829-01a.htm";
					else
						htmltext = "30829-02.htm";
				}
				else if(cond == 2)
					htmltext = "30829-03a.htm";
				else if(cond == 3)
					htmltext = "30829-04.htm";
				else if(cond == 4)
					htmltext = "30829-05a.htm";
				else if(cond == 5)
					htmltext = "30829-06.htm";
			}
			else if(npcId == GALLADUCCI)
				if(cond == 4 && st.getQuestItemsCount(MAP) > 0)
					htmltext = "30097-05.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getInt("cond");
		if(cond == 2 && st.rollAndGiveLimited(MAP_PIECE, 1, 100, MAX_COUNT))
		{
			if(st.getQuestItemsCount(MAP_PIECE) == MAX_COUNT)
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