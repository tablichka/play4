package quests._130_PathToHellbound;

import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _130_PathToHellbound extends Quest
{
	//NPCs
	private static int GALATE = 32292;
	private static int CASIAN = 30612;

	//Items
	int CasiansBlueCrystal = 12823;

	public _130_PathToHellbound()
	{
		super(130, "_130_PathToHellbound", "Path To Hellbound");
		addStartNpc(CASIAN);
		addTalkId(GALATE);
		addQuestItem(CasiansBlueCrystal);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30612-02.htm"))
		{
			int HBStage = ServerVariables.getInt("hb_stage", 0);
			if(HBStage < 1)
				htmltext = "30612-00a.htm";
		}
		if(event.equalsIgnoreCase("30612-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if(event.equalsIgnoreCase("32292-03.htm"))
		{
			st.set("cond", "2");
			st.setState(STARTED);
			st.playSound("ItemSound.quest_middle");
		}
		else if(event.equalsIgnoreCase("30612-05.htm"))
		{
			if(st.getQuestItemsCount(CasiansBlueCrystal) < 1)
				st.giveItems(CasiansBlueCrystal, 1);
			st.set("cond", "3");
			st.setState(STARTED);
			st.playSound("ItemSound.quest_middle");
		}
		else if(event.equalsIgnoreCase("32292-06.htm"))
		{
			st.playSound("ItemSound.quest_finish");
			htmltext = "32292-06.htm";
			st.exitCurrentQuest(false);
/*
			if(ServerVariables.getInt("hb_stage", 0) < 1)
			{
				Hellbound.setStage(1);
				Hellbound.spawnStage(1);
			}
*/
		}
		return htmltext;
	}

	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == CASIAN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 78)
				{
					htmltext = "30612-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30612-01.htm";
			}
			else if(cond == 1)
			{
				htmltext = "30612-03.htm";
			}
			else if(cond == 2)
			{
				htmltext = "30612-04.htm";
			}
			else if(cond == 3)
			{
				htmltext = "30612-05.htm";
				if(st.getQuestItemsCount(CasiansBlueCrystal) < 1)
					st.giveItems(CasiansBlueCrystal, 1);
			}
		}
		else if(npcId == GALATE)
		{
			if(cond == 1)
			{
				htmltext = "32292-01.htm";
			}
			else if(cond == 2)
			{
				htmltext = "32292-03.htm";
			}
			else if(cond == 3)
				if(st.getQuestItemsCount(CasiansBlueCrystal) > 0)
					htmltext = "32292-04.htm";
				else
					st.getPlayer().sendMessage("Incorrect item count.");
		}
		return htmltext;
	}
}