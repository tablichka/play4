package quests._353_PowerOfDarkness;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _353_PowerOfDarkness extends Quest
{
	//NPCs
	private static int GALMAN = 31044;
	//Mobs
	private static int Malruk_Succubus = 20283;
	private static int Malruk_Succubus_Turen = 20284;
	private static int Malruk_Succubus2 = 20244;
	private static int Malruk_Succubus_Turen2 = 20245;
	//Items
	private static int STONE = 5862;
	private static int ADENA = 57;
	//Chances
	private static int STONE_CHANCE = 50;

	public _353_PowerOfDarkness()
	{
		super(353, "_353_PowerOfDarkness", "Power of Darkness");
		addStartNpc(GALMAN);
		addKillId(Malruk_Succubus);
		addKillId(Malruk_Succubus_Turen);
		addKillId(Malruk_Succubus2);
		addKillId(Malruk_Succubus_Turen2);
		addQuestItem(STONE);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("31044-04.htm") && st.isCreated())
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31044-08.htm") && st.isStarted())
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		if(npc.getNpcId() != GALMAN)
			return htmltext;

		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 55)
			{
				htmltext = "31044-02.htm";
			}
			else
			{
				htmltext = "31044-01.htm";
				st.exitCurrentQuest(true);
			}
		}
		else
		{
			long stone_count = st.getQuestItemsCount(STONE);
			if(stone_count > 0)
			{
				htmltext = "31044-06.htm";
				st.takeItems(STONE, -1);
				st.rollAndGive(ADENA, 2500 + 230 * stone_count, 100);
				st.playSound(SOUND_MIDDLE);
			}
			else
				htmltext = "31044-05.htm";
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(!st.isStarted() || st.getCond() != 1)
			return;

		if(st.rollAndGive(STONE, 1, STONE_CHANCE))
			st.playSound(SOUND_ITEMGET);
	}
}