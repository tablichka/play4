package quests._364_JovialAccordion;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _364_JovialAccordion extends Quest
{
	//NPCs
	private static int BARBADO = 30959;
	private static int SWAN = 30957;
	private static int SABRIN = 30060;
	private static int BEER_CHEST = 30960;
	private static int CLOTH_CHEST = 30961;
	//Items
	private static int KEY_1 = 4323;
	private static int KEY_2 = 4324;
	private static int BEER = 4321;
	private static int ECHO = 4421;

	public _364_JovialAccordion()
	{
		super(364, "_364_JovialAccordion", "Jovial Accordion");
		addStartNpc(BARBADO);
		addTalkId(SWAN);
		addTalkId(SABRIN);
		addTalkId(BEER_CHEST);
		addTalkId(CLOTH_CHEST);
		addQuestItem(KEY_1);
		addQuestItem(KEY_2);
		addQuestItem(BEER);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(st.isCreated())
		{
			if(npcId != BARBADO)
				return htmltext;
			st.set("cond", "0");
			st.set("ok", "0");
		}

		int cond = st.getInt("cond");
		if(npcId == BARBADO)
		{
			if(st.isCreated())
				htmltext = "30959-01.htm";
			else if(cond == 3)
			{
				htmltext = "30959-03.htm";
				st.rollAndGive(ECHO, 1, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else if(cond > 0)
				htmltext = "30959-02.htm";
		}
		else if(npcId == SWAN)
		{
			if(cond == 1)
				htmltext = "30957-01.htm";
			else if(cond == 3)
				htmltext = "30957-05.htm";
			else if(cond == 2)
				if(st.getInt("ok") == 1 && st.getQuestItemsCount(KEY_1) == 0)
				{
					st.set("cond", "3");
					htmltext = "30957-04.htm";
				}
				else
					htmltext = "30957-03.htm";
		}
		else if(npcId == SABRIN && cond == 2 && st.getQuestItemsCount(BEER) > 0)
		{
			st.set("ok", "1");
			st.takeItems(BEER, -1);
			htmltext = "30060-01.htm";
		}
		else if(npcId == BEER_CHEST && cond == 2)
			htmltext = "30960-01.htm";
		else if(npcId == CLOTH_CHEST && cond == 2)
			htmltext = "30961-01.htm";

		return htmltext;
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("30959-02.htm") && st.isCreated())
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30957-02.htm") && st.isStarted() && cond == 1)
		{
			st.set("cond", "2");
			st.giveItems(KEY_1, 1);
			st.giveItems(KEY_2, 1);
		}
		else if(event.equalsIgnoreCase("30960-03.htm") && cond == 2 && st.getQuestItemsCount(KEY_2) > 0)
		{
			st.takeItems(KEY_2, -1);
			st.giveItems(BEER, 1);
			htmltext = "30960-02.htm";
		}
		else if(event.equalsIgnoreCase("30961-03.htm") && cond == 2 && st.getQuestItemsCount(KEY_1) > 0)
		{
			st.takeItems(KEY_1, -1);
			htmltext = "30961-02.htm";
		}
		return htmltext;
	}
}
