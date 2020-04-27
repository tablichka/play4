package quests._688_DefeatTheElrokianRaiders;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _688_DefeatTheElrokianRaiders extends Quest
{
	//Settings: drop chance in %
	private static int DROP_CHANCE = 50;

	private static int ADENA = 57;
	private static int DINOSAUR_FANG_NECKLACE = 8785;

	public _688_DefeatTheElrokianRaiders()
	{
		super(688, "_688_DefeatTheElrokianRaiders", "Defeat The Elrokian Raiders");

		addStartNpc(32105);
		addTalkId(32105);
		addKillId(22214);
		addQuestItem(DINOSAUR_FANG_NECKLACE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		long count = st.getQuestItemsCount(DINOSAUR_FANG_NECKLACE);
		if(event.equalsIgnoreCase("32105-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32105-08.htm"))
		{
			if(count > 0)
			{
				st.takeItems(DINOSAUR_FANG_NECKLACE, -1);
				st.rollAndGive(ADENA, count * 3000, 100);
			}
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("32105-06.htm"))
		{
			st.takeItems(DINOSAUR_FANG_NECKLACE, -1);
			st.rollAndGive(ADENA, count * 3000, 100);
		}
		else if(event.equalsIgnoreCase("32105-07.htm"))
		{
			if(count >= 100)
			{
				st.takeItems(DINOSAUR_FANG_NECKLACE, 100);
				st.rollAndGive(ADENA, 450000, 100);
			}
			else
				htmltext = "32105-04.htm";
		}
		else if(event.equalsIgnoreCase("None"))
			htmltext = null;
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		long count = st.getQuestItemsCount(DINOSAUR_FANG_NECKLACE);
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 75)
				htmltext = "32105-01.htm";
			else
			{
				htmltext = "32105-00.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
		{
			if(count == 0)
				htmltext = "32105-04.htm";
			else
				htmltext = "32105-05.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getInt("cond") == 1 && st.rollAndGive(DINOSAUR_FANG_NECKLACE, 1, DROP_CHANCE))
			st.playSound(SOUND_ITEMGET);
	}
}