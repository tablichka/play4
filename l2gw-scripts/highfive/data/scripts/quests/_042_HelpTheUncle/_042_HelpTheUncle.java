package quests._042_HelpTheUncle;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _042_HelpTheUncle extends Quest
{
	private static final int WATERS = 30828;
	private static final int SOPHYA = 30735;

	private static final int TRIDENT = 291;
	private static final int MAP_PIECE = 7548;
	private static final int MAP = 7549;
	private static final int PET_TICKET = 7583;

	private static final int MONSTER_EYE_DESTROYER = 20068;
	private static final int MONSTER_EYE_GAZER = 20266;

	private static final int MAX_COUNT = 30;
	private static final int MIN_LEVEL = 25;

	public _042_HelpTheUncle()
	{
		super(42, "_042_HelpTheUncle", "Help The Uncle");

		addStartNpc(WATERS);

		addTalkId(WATERS);
		addTalkId(SOPHYA);

		addKillId(MONSTER_EYE_DESTROYER);
		addKillId(MONSTER_EYE_GAZER);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			htmltext = "30828-01.htm";
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("3") && st.getQuestItemsCount(TRIDENT) > 0)
		{
			htmltext = "30828-03.htm";
			st.takeItems(TRIDENT, 1);
			st.set("cond", "2");
		}
		else if(event.equals("4") && st.getQuestItemsCount(MAP_PIECE) >= MAX_COUNT)
		{
			htmltext = "30828-05.htm";
			st.takeItems(MAP_PIECE, MAX_COUNT);
			st.giveItems(MAP, 1);
			st.set("cond", "4");
		}
		else if(event.equals("5") && st.getQuestItemsCount(MAP) > 0)
		{
			htmltext = "30735-06.htm";
			st.takeItems(MAP, 1);
			st.set("cond", "5");
		}
		else if(event.equals("7"))
		{
			htmltext = "30828-07.htm";
			st.giveItems(PET_TICKET, 1);
			st.playSound(SOUND_FINISH);
			st.unset("cond");
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
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= MIN_LEVEL)
				htmltext = "30828-00.htm";
			else
			{
				htmltext = "<html><head><body>This quest can only be taken by characters that have a minimum level of " + MIN_LEVEL + ". Return when you are more experienced.</body></html>";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			if(npcId == WATERS)
			{
				if(cond == 1)
					if(st.getQuestItemsCount(TRIDENT) == 0)
						htmltext = "30828-01a.htm";
					else
						htmltext = "30828-02.htm";
				else if(cond == 2)
					htmltext = "30828-03a.htm";
				else if(cond == 3)
					htmltext = "30828-04.htm";
				else if(cond == 4)
					htmltext = "30828-05a.htm";
				else if(cond == 5)
					htmltext = "30828-06.htm";
			}
			else if(npcId == SOPHYA)
				if(cond == 4 && st.getQuestItemsCount(MAP) > 0)
					htmltext = "30735-05.htm";
				else if(cond == 5)
					htmltext = "30735-06a.htm";
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