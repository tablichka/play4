package quests._173_ToTheIsleOfSouls;

import ru.l2gw.gameserver.model.base.Race;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _173_ToTheIsleOfSouls extends Quest
{
	private static final int GALLADUCCIS_ORDER_DOCUMENT_ID_1 = 7563;
	private static final int MAGIC_SWORD_HILT_ID = 7568;
	private static final int MARK_OF_TRAVELER_ID = 7570;
	private static final int SCROLL_OF_ESCAPE_SPECIAL = 9716;

	public _173_ToTheIsleOfSouls()
	{
		super(173, "_173_ToTheIsleOfSouls", "To the Isle of Souls");

		addStartNpc(30097);

		addTalkId(30097);
		addTalkId(30094);
		addTalkId(30090);
		addTalkId(30116);

		addQuestItem(GALLADUCCIS_ORDER_DOCUMENT_ID_1, MAGIC_SWORD_HILT_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			st.giveItems(GALLADUCCIS_ORDER_DOCUMENT_ID_1, 1);
			htmltext = "30097-03.htm";
		}
		else if(event.equals("2"))
		{
			st.set("cond", "2");
			st.takeItems(GALLADUCCIS_ORDER_DOCUMENT_ID_1, 1);
			st.giveItems(MAGIC_SWORD_HILT_ID, 1);
			htmltext = "30094-02.htm";
		}
		else if(event.equals("3"))
		{
			st.unset("cond");
			st.takeItems(MAGIC_SWORD_HILT_ID, 1);
			st.giveItems(SCROLL_OF_ESCAPE_SPECIAL, 1);
			htmltext = "30097-12.htm";
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
			st.set("cond", "0");
			if(st.getPlayer().getRace() == Race.kamael && st.getQuestItemsCount(MARK_OF_TRAVELER_ID) > 0)
				htmltext = "30097-02.htm";
			else
			{
				htmltext = "30097-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == 30097 && st.getInt("cond") == 1)
			htmltext = "30097-04.htm";
		else if(npcId == 30097 && st.getInt("cond") == 2)
			htmltext = "30097-05.htm";
		else if(npcId == 30094 && st.getInt("cond") == 1)
			htmltext = "30094-01.htm";
		else if(npcId == 30094 && st.getInt("cond") == 2)
			htmltext = "30094-03.htm";
		return htmltext;
	}
}