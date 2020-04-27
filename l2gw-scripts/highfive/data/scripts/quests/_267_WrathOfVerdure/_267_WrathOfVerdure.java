package quests._267_WrathOfVerdure;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _267_WrathOfVerdure extends Quest
{
	//NPCs
	private static int Treant_Bremec = 31853;
	//Mobs
	private static int Goblin_Raider = 20325;
	//Quest Items
	private static int Goblin_Club = 1335;
	//Items
	private static int Silvery_Leaf = 1340;
	//Chances
	private static int Goblin_Club_Chance = 50;

	public _267_WrathOfVerdure()
	{
		super(267, "_267_WrathOfVerdure", "Wrath Of Verdure");
		addStartNpc(Treant_Bremec);
		addKillId(Goblin_Raider);
		addQuestItem(Goblin_Club);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		if(event.equalsIgnoreCase("31853-03.htm") && st.isCreated() && st.getPlayer().getRace().ordinal() == 1 && st.getPlayer().getLevel() >= 4)
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31853-06.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(false);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		if(npc.getNpcId() != Treant_Bremec)
			return htmltext;
		if(st.isCreated())
		{
			if(st.getPlayer().getRace().ordinal() != 1)
			{
				htmltext = "31853-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 4)
			{
				htmltext = "31853-01.htm";
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "31853-02.htm";
				st.set("cond", "0");
			}
		}
		else if(st.isStarted())
		{
			long Goblin_Club_Count = st.getQuestItemsCount(Goblin_Club);
			if(Goblin_Club_Count > 0)
			{
				htmltext = "31853-05.htm";
				st.takeItems(Goblin_Club, -1);
				st.giveItems(Silvery_Leaf, Goblin_Club_Count);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31853-04.htm";
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.rollAndGive(Goblin_Club, 1, Goblin_Club_Chance))
			st.playSound(SOUND_ITEMGET);
	}
}
