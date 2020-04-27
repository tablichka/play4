package quests._045_ToTalkingIsland;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _045_ToTalkingIsland extends Quest
{
	private static final int GALLADUCCIS_ORDER_DOCUMENT_ID_1 = 7563;
	private static final int GALLADUCCIS_ORDER_DOCUMENT_ID_2 = 7564;
	private static final int GALLADUCCIS_ORDER_DOCUMENT_ID_3 = 7565;
	private static final int MAGIC_SWORD_HILT_ID = 7568;
	private static final int GEMSTONE_POWDER_ID = 7567;
	private static final int PURIFIED_MAGIC_NECKLACE_ID = 7566;
	private static final int MARK_OF_TRAVELER_ID = 7570;
	private static final int SCROLL_OF_ESCAPE_SPECIAL = 7554;
	private static final int RACE = 0;

	public _045_ToTalkingIsland()
	{
		super(45, "_045_ToTalkingIsland", "To Talking Island");

		addStartNpc(30097);

		addTalkId(30097);

		addTalkId(30097);
		addTalkId(30094);
		addTalkId(30090);
		addTalkId(30116);

		addQuestItem(GALLADUCCIS_ORDER_DOCUMENT_ID_1,
				GALLADUCCIS_ORDER_DOCUMENT_ID_2,
				GALLADUCCIS_ORDER_DOCUMENT_ID_3,
				MAGIC_SWORD_HILT_ID,
				GEMSTONE_POWDER_ID,
				PURIFIED_MAGIC_NECKLACE_ID);
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
			st.set("cond", "3");
			st.takeItems(MAGIC_SWORD_HILT_ID, 1);
			st.giveItems(GALLADUCCIS_ORDER_DOCUMENT_ID_2, 1);
			htmltext = "30097-06.htm";
		}
		else if(event.equals("4"))
		{
			st.set("cond", "4");
			st.takeItems(GALLADUCCIS_ORDER_DOCUMENT_ID_2, 1);
			st.giveItems(GEMSTONE_POWDER_ID, 1);
			htmltext = "30090-02.htm";
		}
		else if(event.equals("5"))
		{
			st.set("cond", "5");
			st.takeItems(GEMSTONE_POWDER_ID, 1);
			st.giveItems(GALLADUCCIS_ORDER_DOCUMENT_ID_3, 1);
			htmltext = "30097-09.htm";
		}
		else if(event.equals("6"))
		{
			st.set("cond", "6");
			st.takeItems(GALLADUCCIS_ORDER_DOCUMENT_ID_3, 1);
			st.giveItems(PURIFIED_MAGIC_NECKLACE_ID, 1);
			htmltext = "30116-02.htm";
		}
		else if(event.equals("7"))
		{
			st.giveItems(SCROLL_OF_ESCAPE_SPECIAL, 1);
			st.takeItems(PURIFIED_MAGIC_NECKLACE_ID, 1);
			htmltext = "30097-12.htm";
			st.set("cond", "0");
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
			if(st.getPlayer().getRace().ordinal() == RACE && st.getQuestItemsCount(MARK_OF_TRAVELER_ID) > 0)
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
		else if(npcId == 30097 && st.getInt("cond") == 3)
			htmltext = "30097-07.htm";
		else if(npcId == 30097 && st.getInt("cond") == 4)
			htmltext = "30097-08.htm";
		else if(npcId == 30097 && st.getInt("cond") == 5)
			htmltext = "30097-10.htm";
		else if(npcId == 30097 && st.getInt("cond") == 6)
			htmltext = "30097-11.htm";
		else if(npcId == 30094 && st.getInt("cond") == 1)
			htmltext = "30094-01.htm";
		else if(npcId == 30094 && st.getInt("cond") == 2)
			htmltext = "30094-03.htm";
		else if(npcId == 30090 && st.getInt("cond") == 3)
			htmltext = "30090-01.htm";
		else if(npcId == 30090 && st.getInt("cond") == 4)
			htmltext = "30090-03.htm";
		else if(npcId == 30116 && st.getInt("cond") == 5)
			htmltext = "30116-01.htm";
		else if(npcId == 30116 && st.getInt("cond") == 6)
			htmltext = "30116-03.htm";
		return htmltext;
	}
}