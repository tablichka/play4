package quests._286_FabulousFeathers;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _286_FabulousFeathers extends Quest
{
	//NPCs
	private static int ERINU = 32164;
	//Mobs
	private static int Shady_Muertos_Captain = 22251;
	private static int Shady_Muertos_Warrior = 22253;
	private static int Shady_Muertos_Archer = 22254;
	private static int Shady_Muertos_Commander = 22255;
	private static int Shady_Muertos_Wizard = 22256;
	//Quest Items
	private static int Commanders_Feather = 9746;
	//Items
	private static int ADENA = 57;
	//Chances
	private static int Commanders_Feather_Chance = 66;

	public _286_FabulousFeathers()
	{
		super(286, "_286_FabulousFeathers", "Fabulous Feathers");
		addStartNpc(ERINU);
		addKillId(Shady_Muertos_Captain);
		addKillId(Shady_Muertos_Warrior);
		addKillId(Shady_Muertos_Archer);
		addKillId(Shady_Muertos_Commander);
		addKillId(Shady_Muertos_Wizard);
		addQuestItem(Commanders_Feather);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("32164-03.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32164-06.htm") && st.isStarted())
		{
			st.takeItems(Commanders_Feather, -1);
			st.rollAndGive(ADENA, 4160, 100);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != ERINU)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 17)
			{
				htmltext = "32164-01.htm";
				st.set("cond", "0");
			}
			else
			{
				htmltext = "32164-02.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(st.isStarted())
			htmltext = st.getQuestItemsCount(Commanders_Feather) >= 80 ? "32164-05.htm" : "32164-04.htm";

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted())
			return;

		if(st.getCond() == 1 && st.rollAndGiveLimited(Commanders_Feather, 1, Commanders_Feather_Chance, 80))
		{
			if(st.getQuestItemsCount(Commanders_Feather) == 80)
			{
				st.set("cond", "2");
				st.playSound(SOUND_MIDDLE);
				st.setState(STARTED);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}