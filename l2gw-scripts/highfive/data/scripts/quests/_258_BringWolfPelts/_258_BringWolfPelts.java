package quests._258_BringWolfPelts;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _258_BringWolfPelts extends Quest
{
	int WOLF_PELT = 702;

	int Cotton_Shirt = 390;
	int Leather_Pants = 29;
	int Leather_Shirt = 22;
	int Short_Leather_Gloves = 1119;
	int Tunic = 426;

	public _258_BringWolfPelts()
	{
		super(258, "_258_BringWolfPelts", "Bring Wolf Pelts");

		addStartNpc(30001);
		addTalkId(30001);
		addKillId(20120);
		addKillId(20442);

		addQuestItem(WOLF_PELT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.intern().equalsIgnoreCase("30001-03.htm"))
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
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 3)
			{
				htmltext = "30001-02.htm";
				return htmltext;
			}
			htmltext = "30001-01.htm";
			st.exitCurrentQuest(true);
		}
		else if(cond == 1 && st.getQuestItemsCount(WOLF_PELT) >= 0 && st.getQuestItemsCount(WOLF_PELT) < 40)
			htmltext = "30001-05.htm";
		else if(cond == 2 && st.getQuestItemsCount(WOLF_PELT) >= 40)
		{
			st.takeItems(WOLF_PELT, 40);
			int n = Rnd.get(16);
			if(n == 0)
			{
				st.giveItems(Cotton_Shirt, 1);
				st.playSound(SOUND_JACKPOT);
			}
			else if(n < 6)
				st.giveItems(Leather_Pants, 1);
			else if(n < 9)
				st.giveItems(Leather_Shirt, 1);
			else if(n < 13)
				st.giveItems(Short_Leather_Gloves, 1);
			else
				st.giveItems(Tunic, 1);
			htmltext = "30001-06.htm";
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(WOLF_PELT, 1, 100, 40))
		{
			if(st.getQuestItemsCount(WOLF_PELT) == 40)
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