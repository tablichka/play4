package quests._160_NerupasFavor;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _160_NerupasFavor extends Quest
{
	int SILVERY_SPIDERSILK_ID = 1026;
	int UNOS_RECEIPT_ID = 1027;
	int CELS_TICKET_ID = 1028;
	int NIGHTSHADE_LEAF_ID = 1029;
	int LESSER_HEALING_POTION_ID = 1060;

	public _160_NerupasFavor()
	{
		super(160, "_160_NerupasFavor", "Nerupas Favor");

		addStartNpc(30370);

		addTalkId(30370);

		addTalkId(30147);
		addTalkId(30149);
		addTalkId(30152);
		addTalkId(30370);

		addQuestItem(SILVERY_SPIDERSILK_ID, UNOS_RECEIPT_ID, CELS_TICKET_ID, NIGHTSHADE_LEAF_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("id", "0");
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			if(st.getQuestItemsCount(SILVERY_SPIDERSILK_ID) == 0)
				st.giveItems(SILVERY_SPIDERSILK_ID, 1);
			htmltext = "30370-04.htm";
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
			st.setState(STARTED);
			st.set("cond", "0");
			st.set("id", "0");
		}
		if(npcId == 30370 && st.getInt("cond") == 0)
		{
			if(st.getInt("cond") < 15)
			{
				if(st.getPlayer().getRace().ordinal() != 1)
					htmltext = "30370-00.htm";
				else if(st.getPlayer().getLevel() >= 3)
				{
					htmltext = "30370-03.htm";
					return htmltext;
				}
				else
				{
					htmltext = "30370-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				htmltext = "30370-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(npcId == 30370 && st.getInt("cond") != 0 && (st.getQuestItemsCount(SILVERY_SPIDERSILK_ID) != 0 || st.getQuestItemsCount(UNOS_RECEIPT_ID) != 0 || st.getQuestItemsCount(CELS_TICKET_ID) != 0))
			htmltext = "30370-05.htm";
		else if(npcId == 30147 && st.getInt("cond") != 0 && st.getQuestItemsCount(SILVERY_SPIDERSILK_ID) != 0)
		{
			st.takeItems(SILVERY_SPIDERSILK_ID, st.getQuestItemsCount(SILVERY_SPIDERSILK_ID));
			if(st.getQuestItemsCount(UNOS_RECEIPT_ID) == 0)
				st.giveItems(UNOS_RECEIPT_ID, 1);
			htmltext = "30147-01.htm";
		}
		else if(npcId == 30147 && st.getInt("cond") != 0 && st.getQuestItemsCount(UNOS_RECEIPT_ID) != 0)
			htmltext = "30147-02.htm";
		else if(npcId == 30149 && st.getInt("cond") != 0 && st.getQuestItemsCount(UNOS_RECEIPT_ID) != 0)
		{
			st.takeItems(UNOS_RECEIPT_ID, st.getQuestItemsCount(UNOS_RECEIPT_ID));
			if(st.getQuestItemsCount(CELS_TICKET_ID) == 0)
				st.giveItems(CELS_TICKET_ID, 1);
			htmltext = "30149-01.htm";
		}
		else if(npcId == 30149 && st.getInt("cond") != 0 && st.getQuestItemsCount(CELS_TICKET_ID) != 0)
			htmltext = "30149-02.htm";
		else if(npcId == 30152 && st.getInt("cond") != 0 && st.getQuestItemsCount(CELS_TICKET_ID) != 0)
		{
			st.takeItems(CELS_TICKET_ID, st.getQuestItemsCount(CELS_TICKET_ID));
			if(st.getQuestItemsCount(NIGHTSHADE_LEAF_ID) == 0)
			{
				st.giveItems(NIGHTSHADE_LEAF_ID, 1);
				htmltext = "30152-01.htm";
			}
		}
		else if(npcId == 30152 && st.getInt("cond") != 0 && st.getQuestItemsCount(NIGHTSHADE_LEAF_ID) != 0)
			htmltext = "30152-02.htm";
		else if(npcId == 30149 && st.getInt("cond") != 0 && st.getQuestItemsCount(NIGHTSHADE_LEAF_ID) != 0)
			htmltext = "30149-03.htm";
		else if(npcId == 30147 && st.getInt("cond") != 0 && st.getQuestItemsCount(NIGHTSHADE_LEAF_ID) != 0)
			htmltext = "30147-03.htm";
		else if(npcId == 30370 && st.getInt("cond") != 0 && st.getQuestItemsCount(NIGHTSHADE_LEAF_ID) != 0)
			if(st.getInt("id") != 160)
			{
				st.set("id", "160");
				st.takeItems(NIGHTSHADE_LEAF_ID, st.getQuestItemsCount(NIGHTSHADE_LEAF_ID));
				st.playSound(SOUND_FINISH);
				st.giveItems(LESSER_HEALING_POTION_ID, 1);
				st.addExpAndSp(1000, 0);
				htmltext = "30370-06.htm";
				st.exitCurrentQuest(false);
			}
		return htmltext;
	}
}