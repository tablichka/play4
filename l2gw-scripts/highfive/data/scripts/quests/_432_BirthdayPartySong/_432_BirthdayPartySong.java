package quests._432_BirthdayPartySong;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _432_BirthdayPartySong extends Quest
{
	//NPC
	private static int MELODY_MAESTRO_OCTAVIA = 31043;
	//MOB
	private static int ROUGH_HEWN_ROCK_GOLEMS = 21103;
	//Quest items
	private static int RED_CRYSTALS = 7541;
	private static int BIRTHDAY_ECHO_CRYSTAL = 7061;

	public _432_BirthdayPartySong()
	{
		super(432, "_432_BirthdayPartySong", "Birthday Party Song");

		addStartNpc(MELODY_MAESTRO_OCTAVIA);

		addKillId(ROUGH_HEWN_ROCK_GOLEMS);

		addQuestItem(RED_CRYSTALS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("31043-02.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31043-05.htm"))
			if(st.getQuestItemsCount(RED_CRYSTALS) == 50)
			{
				st.takeItems(RED_CRYSTALS, -1);
				st.rollAndGive(BIRTHDAY_ECHO_CRYSTAL, 25, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "31043-06.htm";

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int condition = st.getInt("cond");
		int npcId = npc.getNpcId();

		if(npcId == MELODY_MAESTRO_OCTAVIA)
			if(condition == 0)
			{
				if(st.getPlayer().getLevel() >= 31)
					htmltext = "31043-01.htm";
				else
				{
					htmltext = "31043-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(condition == 1)
				htmltext = "31043-03.htm";
			else if(condition == 2 && st.getQuestItemsCount(RED_CRYSTALS) == 50)
				htmltext = "31043-04.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();

		if(npcId == ROUGH_HEWN_ROCK_GOLEMS)
			if(st.getInt("cond") == 1 && st.rollAndGiveLimited(RED_CRYSTALS, 1, 100, 50))
			{
				if(st.getQuestItemsCount(RED_CRYSTALS) == 50)
				{
					st.playSound(SOUND_MIDDLE);
					st.set("cond", "2");
					st.setState(STARTED);
				}
				else
					st.playSound(SOUND_ITEMGET);
			}
	}
}