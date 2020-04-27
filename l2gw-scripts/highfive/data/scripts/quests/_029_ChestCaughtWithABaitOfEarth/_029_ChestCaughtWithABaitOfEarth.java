package quests._029_ChestCaughtWithABaitOfEarth;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _029_ChestCaughtWithABaitOfEarth extends Quest
{
	int Willie = 31574;
	int Anabel = 30909;

	int SmallPurpleTreasureChest = 6507;
	int SmallGlassBox = 7627;
	int PlatedLeatherGloves = 2455;

	public _029_ChestCaughtWithABaitOfEarth()
	{
		super(29, "_029_ChestCaughtWithABaitOfEarth", "Chest Caught With A Bait Of Earth");

		addStartNpc(Willie);

		addTalkId(Willie);
		addTalkId(Willie);
		addTalkId(Anabel);
		addTalkId(Willie);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equals("31574-04.htm"))
		{
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
		}
		else if(event.equals("31574-07.htm"))
		{
			if(st.getQuestItemsCount(SmallPurpleTreasureChest) > 0)
			{
				st.set("cond", "2");
				st.setState(STARTED);
				st.playSound(SOUND_MIDDLE);
				st.takeItems(SmallPurpleTreasureChest, 1);
				st.giveItems(SmallGlassBox, 1);
			}
			else
				htmltext = "31574-08.htm";
		}
		else if(event.equals("29_GiveGlassBox"))
			if(st.getQuestItemsCount(SmallGlassBox) == 1)
			{
				htmltext = "30909-02.htm";
				st.takeItems(SmallGlassBox, -1);
				st.giveItems(PlatedLeatherGloves, 1);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
			{
				htmltext = "30909-03.htm";
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
		if(npcId == Willie)
		{
			if(st.isCreated())
			{
				int PlayerLevel = st.getPlayer().getLevel();
				if(PlayerLevel < 48)
				{
					QuestState WilliesSpecialBait = st.getPlayer().getQuestState("_052_WilliesSpecialBait");
					if(WilliesSpecialBait != null)
					{
						if(WilliesSpecialBait.isCompleted())
							htmltext = "31574-01.htm";
						else
						{
							htmltext = "31574-02.htm";
							st.exitCurrentQuest(true);
						}
					}
					else
					{
						htmltext = "31574-03.htm";
						st.exitCurrentQuest(true);
					}
				}
			}
			else if(cond == 1)
			{
				htmltext = "31574-05.htm";
				if(st.getQuestItemsCount(SmallPurpleTreasureChest) == 0)
					htmltext = "31574-06.htm";
			}
			else if(cond == 2)
				htmltext = "31574-09.htm";
		}
		else if(npcId == Anabel)
			if(cond == 2)
				htmltext = "30909-01.htm";
		return htmltext;
	}
}
