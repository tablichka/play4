package quests._661_TheHarvestGroundsSafe;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _661_TheHarvestGroundsSafe extends Quest
{
	//NPC
	private static int NORMAN = 30210;

	// MOBS
	private static int GIANT_POISON_BEE = 21095;
	private static int CLOYDY_BEAST = 21096;
	private static int YOUNG_ARANEID = 21097;

	//QUEST ITEMS
	private static int STING_OF_GIANT_POISON = 8283;
	private static int TALON_OF_YOUNG_ARANEID = 8285;
	private static int CLOUDY_GEM = 8284;
	private static int ADENA = 57;

	public _661_TheHarvestGroundsSafe()
	{
		super(661, "_661_TheHarvestGroundsSafe", "The Harvest Grounds Safe");

		addStartNpc(NORMAN);

		addKillId(GIANT_POISON_BEE);
		addKillId(CLOYDY_BEAST);
		addKillId(YOUNG_ARANEID);

		addQuestItem(STING_OF_GIANT_POISON, TALON_OF_YOUNG_ARANEID, CLOUDY_GEM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(st.getState() == null)
			return null;

		if(event.equalsIgnoreCase("30210-03.htm") || event.equalsIgnoreCase("30210-09.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30210-08.htm"))
		{
			long STING = st.getQuestItemsCount(STING_OF_GIANT_POISON);
			long TALON = st.getQuestItemsCount(TALON_OF_YOUNG_ARANEID);
			long GEM = st.getQuestItemsCount(CLOUDY_GEM);

			if(STING + GEM + TALON >= 10)
			{
				st.rollAndGive(ADENA, STING * 50 + GEM * 60 + TALON * 70 + 2800, 100);
				st.takeItems(STING_OF_GIANT_POISON, -1);
				st.takeItems(TALON_OF_YOUNG_ARANEID, -1);
				st.takeItems(CLOUDY_GEM, -1);
			}
			else
			{
				st.rollAndGive(ADENA, STING * 50 + GEM * 60 + TALON * 70, 100);
				st.takeItems(STING_OF_GIANT_POISON, -1);
				st.takeItems(TALON_OF_YOUNG_ARANEID, -1);
				st.takeItems(CLOUDY_GEM, -1);
			}
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30210-06.htm"))
		{
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
		String htmltext = "noquest";
		if(st.getState() == null)
			return htmltext;
		int cond = st.getInt("cond");

		if(st.isCreated())
			if(st.getPlayer().getLevel() >= 21)
				htmltext = "30210-02.htm";
			else
			{
				htmltext = "30210-01.htm";
				st.exitCurrentQuest(true);
			}
		else if(cond == 1)
			if(st.getQuestItemsCount(STING_OF_GIANT_POISON) + st.getQuestItemsCount(TALON_OF_YOUNG_ARANEID) + st.getQuestItemsCount(CLOUDY_GEM) > 0)
				htmltext = "30210-05.htm";
			else
				htmltext = "30210-04.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;
		int npcId = npc.getNpcId();

		if(st.getInt("cond") == 1)
		{
			if(npcId == GIANT_POISON_BEE && st.rollAndGive(STING_OF_GIANT_POISON, 1, 75))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == CLOYDY_BEAST && st.rollAndGive(CLOUDY_GEM, 1, 71))
				st.playSound(SOUND_ITEMGET);
			else if(npcId == YOUNG_ARANEID && st.rollAndGive(TALON_OF_YOUNG_ARANEID, 1, 67))
				st.playSound(SOUND_ITEMGET);
		}
	}

}