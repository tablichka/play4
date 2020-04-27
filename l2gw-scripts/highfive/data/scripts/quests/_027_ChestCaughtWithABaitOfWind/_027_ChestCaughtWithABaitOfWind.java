package quests._027_ChestCaughtWithABaitOfWind;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _027_ChestCaughtWithABaitOfWind extends Quest
{
	// NPC List
	private static final int Lanosco = 31570;
	private static final int Shaling = 31434;
	//Quest Items
	private static final int StrangeGolemBlueprint = 7625;
	//Items
	private static final int BigBlueTreasureChest = 6500;
	private static final int BlackPearlRing = 880;

	public _027_ChestCaughtWithABaitOfWind()
	{
		super(27, "_027_ChestCaughtWithABaitOfWind", "Chest Caught With A Bait Of Wind");

		addStartNpc(Lanosco);

		addTalkId(Lanosco);
		addTalkId(Shaling);

		addQuestItem(StrangeGolemBlueprint);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equals("31570-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("31570-07.htm"))
		{
			if(st.getQuestItemsCount(BigBlueTreasureChest) > 0)
			{
				st.takeItems(BigBlueTreasureChest, 1);
				st.giveItems(StrangeGolemBlueprint, 1);
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31570-08.htm";
		}
		else if(event.equals("31434-02.htm"))
			if(st.getQuestItemsCount(StrangeGolemBlueprint) == 1)
			{
				st.takeItems(StrangeGolemBlueprint, -1);
				st.giveItems(BlackPearlRing, 1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
			{
				htmltext = "31434-03.htm";
				st.exitCurrentQuest(true);
			}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Lanosco)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 27)
				{
					QuestState LanoscosSpecialBait = st.getPlayer().getQuestState("50_LanoscosSpecialBait");
					if(LanoscosSpecialBait != null)
						if(LanoscosSpecialBait.isCompleted())
							htmltext = "31570-01.htm";
						else
						{
							htmltext = "31570-02.htm";
							st.exitCurrentQuest(true);
						}
					else
					{
						htmltext = "31570-03.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
					htmltext = "31570-01.htm";
			}
			else if(cond == 1)
			{
				htmltext = "31570-05.htm";
				if(st.getQuestItemsCount(BigBlueTreasureChest) == 0)
					htmltext = "31570-06.htm";
			}
			else if(cond == 2)
				htmltext = "31570-09.htm";
		}
		else if(npcId == Shaling)
			if(cond == 2)
				htmltext = "31434-01.htm";
		return htmltext;
	}

}