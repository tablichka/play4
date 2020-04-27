package quests._030_ChestCaughtWithABaitOfFire;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _030_ChestCaughtWithABaitOfFire extends Quest
{
	int Linnaeus = 31577;
	int Rukal = 30629;

	int RedTreasureChest = 6511;
	int RukalsMusicalScore = 7628;
	int NecklaceOfProtection = 916;

	public _030_ChestCaughtWithABaitOfFire()
	{
		super(30, "_030_ChestCaughtWithABaitOfFire", "Chest Caught With A Bait Of Fire");

		addStartNpc(Linnaeus);

		addTalkId(Linnaeus);
		addTalkId(Linnaeus);
		addTalkId(Rukal);
		addTalkId(Linnaeus);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("31577-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);

		}
		else if(event.equals("31577-07.htm"))
		{
			if(st.getQuestItemsCount(RedTreasureChest) > 0)
			{
				st.takeItems(RedTreasureChest, 1);
				st.giveItems(RukalsMusicalScore, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31577-08.htm";
		}
		else if(event.equals("30629-02.htm"))
			if(st.getQuestItemsCount(RukalsMusicalScore) == 1)
			{
				st.takeItems(RukalsMusicalScore, -1);
				st.giveItems(NecklaceOfProtection, 1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
			{
				htmltext = "30629-03.htm";
				st.exitCurrentQuest(true);
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
		int cond = st.getInt("cond");
		if(npcId == Linnaeus)
		{
			if(st.isCreated())
			{
				int PLevel = st.getPlayer().getLevel();
				if(PLevel < 60)
				{
					QuestState LinnaeusSpecialBait = st.getPlayer().getQuestState("_053_LinnaeusSpecialBait");
					if(LinnaeusSpecialBait != null)
					{
						if(LinnaeusSpecialBait.isCompleted())
							htmltext = "31577-01.htm";
						else
						{
							htmltext = "31577-02.htm";
							st.exitCurrentQuest(true);
						}
					}
					else
					{
						htmltext = "31577-03.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
					htmltext = "31577-01.htm";
			}
			else if(cond == 1)
			{
				htmltext = "31577-05.htm";
				if(st.getQuestItemsCount(RedTreasureChest) == 0)
					htmltext = "31577-06.htm";
			}
			else if(cond == 2)
				htmltext = "31577-09.htm";
		}
		else if(npcId == Rukal)
			if(cond == 2)
				htmltext = "30629-01.htm";
		return htmltext;
	}
}