package quests._028_ChestCaughtWithABaitOfIcyAir;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _028_ChestCaughtWithABaitOfIcyAir extends Quest
{
	int OFulle = 31572;
	int Kiki = 31442;

	int BigYellowTreasureChest = 6503;
	int KikisLetter = 7626;
	int ElvenRing = 881;

	public _028_ChestCaughtWithABaitOfIcyAir()
	{
		super(28, "_028_ChestCaughtWithABaitOfIcyAir", "Chest Caught With A Bait Of Icy Air");

		addStartNpc(OFulle);

		addTalkId(OFulle);
		addTalkId(Kiki);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		String htmltext = event;
		if(event.equals("31572-04.htm"))
		{
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
			st.setState(STARTED);
		}
		else if(event.equals("31572-07.htm"))
		{
			if(st.getQuestItemsCount(BigYellowTreasureChest) > 0)
			{
				st.set("cond", "2");
				st.setState(STARTED);
				st.takeItems(BigYellowTreasureChest, 1);
				st.giveItems(KikisLetter, 1);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31572-08.htm";
		}
		else if(event.equals("31442-02.htm"))
			if(st.getQuestItemsCount(KikisLetter) == 1)
			{
				htmltext = "31442-02.htm";
				st.takeItems(KikisLetter, -1);
				st.giveItems(ElvenRing, 1);
				st.set("cond", "0");
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
			{
				htmltext = "31442-03.htm";
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
		if(npcId == OFulle)
		{
			if(st.isCreated())
			{
				int PlayerLevel = st.getPlayer().getLevel();
				if(PlayerLevel < 36)
				{
					QuestState OFullesSpecialBait = st.getPlayer().getQuestState("_051_OFullesSpecialBait");
					if(OFullesSpecialBait != null)
					{
						if(OFullesSpecialBait.isCompleted())
							htmltext = "31572-01.htm";
						else
						{
							htmltext = "31572-02.htm";
							st.exitCurrentQuest(true);
						}
					}
					else
					{
						htmltext = "31572-03.htm";
						st.exitCurrentQuest(true);
					}
				}
				else
					htmltext = "31572-01.htm";
			}
			else if(cond == 1)
			{
				htmltext = "31572-05.htm";
				if(st.getQuestItemsCount(BigYellowTreasureChest) == 0)
					htmltext = "31572-06.htm";
			}
			else if(cond == 2)
				htmltext = "31572-09.htm";
		}
		else if(npcId == Kiki)
			if(cond == 2)
				htmltext = "31442-01.htm";
		return htmltext;
	}
}
