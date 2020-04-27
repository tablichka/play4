package quests._629_CleanUpTheSwampOfScreams;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _629_CleanUpTheSwampOfScreams extends Quest
{
	//NPC
	private static final int CaptainPierce = 31553;
	//Quest Items
	private static final int TalonOfStakato = 7250;
	//Items
	private static final int GoldenRamBadgeRecruit = 7246;
	private static final int GoldenRamBadgeSoldier = 7247;
	private static final int GoldenRamCoin = 7251;
	//Chances
	private static final int[][] CHANCE = {
			{21508, 50},
			{21509, 43},
			{21510, 52},
			{21511, 57},
			{21512, 74},
			{21513, 53},
			{21514, 53},
			{21515, 54},
			{21516, 55},
			{21517, 56}};

	public _629_CleanUpTheSwampOfScreams()
	{
		super(629, "_629_CleanUpTheSwampOfScreams", "Clean up the Swamp of Screams");

		addStartNpc(CaptainPierce);

		//Mob Drop
		for(int[] mons : CHANCE) addKillId(mons[0]);

		addQuestItem(TalonOfStakato);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31553-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31553-3.htm"))
		{
			if(st.getQuestItemsCount(TalonOfStakato) >= 100)
			{
				st.takeItems(TalonOfStakato, 100);
				st.rollAndGive(GoldenRamCoin, 20, 100);
			}
			else
				htmltext = "31553-3a.htm";
		}
		else if(event.equalsIgnoreCase("31553-5.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == CaptainPierce)
		{
			if(st.getQuestItemsCount(GoldenRamBadgeRecruit) > 0 || st.getQuestItemsCount(GoldenRamBadgeSoldier) > 0)
			{
				if(st.isCreated())
				{
					if(st.getPlayer().getLevel() < 66)
					{
						htmltext = "31553-0a.htm";
						st.exitCurrentQuest(true);
					}
					else
						htmltext = "31553-0.htm";
				}
				else if(cond == 1)
				{
					if(st.getQuestItemsCount(TalonOfStakato) >= 100)
						htmltext = "31553-2.htm";
					else
						htmltext = "31553-1a.htm";
				}
			}
			else
			{
				htmltext = "31553-6.htm";
				st.exitCurrentQuest(true);
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		for(int[] aCHANCE : CHANCE)
			if(npcId == aCHANCE[0])
			{
				if(st.rollAndGive(TalonOfStakato, 1, aCHANCE[1]))
					st.playSound(SOUND_ITEMGET);
				break;
			}
	}
}