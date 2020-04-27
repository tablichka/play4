package quests._329_CuriosityOfDwarf;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _329_CuriosityOfDwarf extends Quest
{
	private int GOLEM_HEARTSTONE = 1346;
	private int BROKEN_HEARTSTONE = 1365;
	private int ADENA = 57;

	public _329_CuriosityOfDwarf()
	{
		super(329, "_329_CuriosityOfDwarf", "Curiosity Of Dwarf");

		addStartNpc(30437);
		addKillId(20083);
		addKillId(20085);

		addQuestItem(BROKEN_HEARTSTONE, GOLEM_HEARTSTONE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30437-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30437-06.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext;
		long heart;
		long broken;
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 33)
				htmltext = "30437-02.htm";
			else
			{
				htmltext = "30437-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else
		{
			heart = st.getQuestItemsCount(GOLEM_HEARTSTONE);
			broken = st.getQuestItemsCount(BROKEN_HEARTSTONE);
			if(broken + heart > 0)
			{
				st.rollAndGive(ADENA, 50 * broken + 1000 * heart, 100);
				st.takeItems(BROKEN_HEARTSTONE, -1);
				st.takeItems(GOLEM_HEARTSTONE, -1);
				htmltext = "30437-05.htm";
			}
			else
				htmltext = "30437-04.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() != 1)
			return;

		int npcId = npc.getNpcId();
		int n = Rnd.get(1, 100);
		if(npcId == 20085)
		{
			if(n < 5)
			{
				st.rollAndGive(GOLEM_HEARTSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
			else if(n < 58)
			{
				st.rollAndGive(BROKEN_HEARTSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 20083)
			if(n < 6)
			{
				st.rollAndGive(GOLEM_HEARTSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
			else if(n < 56)
			{
				st.rollAndGive(BROKEN_HEARTSTONE, 1, 100);
				st.playSound(SOUND_ITEMGET);
			}
	}
}