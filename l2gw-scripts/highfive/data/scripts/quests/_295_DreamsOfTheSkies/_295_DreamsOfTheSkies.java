package quests._295_DreamsOfTheSkies;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _295_DreamsOfTheSkies extends Quest
{
	public static int FLOATING_STONE = 1492;
	public static int RING_OF_FIREFLY = 1509;
	public static int ADENA = 57;

	public static int Arin = 30536;
	public static int MagicalWeaver = 20153;

	public _295_DreamsOfTheSkies()
	{
		super(295, "_295_DreamsOfTheSkies", "Dreams Of The Skies");

		addStartNpc(Arin);
		addTalkId(Arin);
		addKillId(MagicalWeaver);

		addQuestItem(FLOATING_STONE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30536-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";

		if(st.isCreated())
			st.set("cond", "0");
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 11)
			{
				htmltext = "30536-02.htm";
				return htmltext;
			}
			htmltext = "30536-01.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1 || st.getQuestItemsCount(FLOATING_STONE) < 50)
			htmltext = "30536-04.htm";
		else if(cond == 2 && st.getQuestItemsCount(FLOATING_STONE) == 50)
		{
			st.addExpAndSp(0, 500);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			if(st.getQuestItemsCount(RING_OF_FIREFLY) < 1)
			{
				htmltext = "30536-05.htm";
				st.giveItems(RING_OF_FIREFLY, 1);
			}
			else
			{
				htmltext = "30536-06.htm";
				st.rollAndGive(ADENA, 2400, 100);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(FLOATING_STONE, 1, 25, 50))
		{
			if(st.getQuestItemsCount(FLOATING_STONE) == 50)
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