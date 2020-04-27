package quests._291_RevengeOfTheRedbonnet;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

public class _291_RevengeOfTheRedbonnet extends Quest
{
	//NPC
	int MaryseRedbonnet = 30553;
	//Quest Items
	int BlackWolfPelt = 1482;
	//Item
	int ScrollOfEscape = 736;
	int GrandmasPearl = 1502;
	int GrandmasMirror = 1503;
	int GrandmasNecklace = 1504;
	int GrandmasHairpin = 1505;
	//Mobs
	int BlackWolf = 20317;

	public _291_RevengeOfTheRedbonnet()
	{
		super(291, "_291_RevengeOfTheRedbonnet", "Revenge of the Redbonnet");

		addStartNpc(MaryseRedbonnet);
		addTalkId(MaryseRedbonnet);

		addKillId(BlackWolf);

		addQuestItem(BlackWolfPelt);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30553-03.htm"))
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
			if(st.getPlayer().getLevel() < 4)
			{
				htmltext = "30553-01.htm";
				st.exitCurrentQuest(true);
			}
			else
				htmltext = "30553-02.htm";
		}
		else if(cond == 1)
			htmltext = "30553-04.htm";
		else if(cond == 2 && st.getQuestItemsCount(BlackWolfPelt) < 40)
		{
			htmltext = "30553-04.htm";
			st.set("cond", "1");
		}
		else if(cond == 2 && st.getQuestItemsCount(BlackWolfPelt) >= 40)
		{
			int random = Rnd.get(100);
			st.takeItems(BlackWolfPelt, -1);
			if(random < 3)
				st.giveItems(GrandmasPearl, 1);
			else if(random < 21)
				st.giveItems(GrandmasMirror, 1);
			else if(random < 46)
				st.giveItems(GrandmasNecklace, 1);
			else
			{
				st.giveItems(ScrollOfEscape, 1);
				st.giveItems(GrandmasHairpin, 1);
			}
			htmltext = "30553-05.htm";
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGiveLimited(BlackWolfPelt, 1, 100, 40))
		{
			if(st.getQuestItemsCount(BlackWolfPelt) < 40)
				st.playSound(SOUND_ITEMGET);
			else
			{
				st.playSound(SOUND_MIDDLE);
				st.set("cond", "2");
				st.setState(STARTED);
			}
		}
	}
}
