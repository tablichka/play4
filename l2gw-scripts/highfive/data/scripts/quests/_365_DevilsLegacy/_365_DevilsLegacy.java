package quests._365_DevilsLegacy;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _365_DevilsLegacy extends Quest
{
	//NPC
	private static final int RANDOLF = 30095;
	//MOBS
	//MOBS=[20836,29027,20845,21629,21630,29026]
	private static final int PIRATEZOMBIE = 20836;
	private static final int PIRATEZOMBIECAPITAN = 29027;
	//VARIABLES
	private static final int CHANCE_OF_DROP = 25;
	private static final int REWARD_PER_ONE = 5070;
	private static final int ADENA = 57;
	//ITEMS
	private static final int TREASURE_CHEST = 5873;

	public _365_DevilsLegacy()
	{
		super(365, "_365_DevilsLegacy", "Devils Legacy");
		addStartNpc(RANDOLF);

		addKillId(PIRATEZOMBIE);
		addKillId(PIRATEZOMBIECAPITAN);

		addQuestItem(TREASURE_CHEST);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30095-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30095-5.htm"))
		{
			long count = st.getQuestItemsCount(TREASURE_CHEST);
			if(count > 0)
			{
				long reward = count * REWARD_PER_ONE;
				st.takeItems(TREASURE_CHEST, -1);
				st.rollAndGive(ADENA, reward, 100);
			}
			else
				htmltext = "You don't have required items";
		}
		else if(event.equalsIgnoreCase("30095-6.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 39)
				htmltext = "30095-0.htm";
			else
			{
				htmltext = "30095-0a.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(cond == 1)
			if(st.getQuestItemsCount(TREASURE_CHEST) == 0)
				htmltext = "30095-2.htm";
			else
				htmltext = "30095-4.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getCond() == 1 && st.rollAndGive(TREASURE_CHEST, 1, CHANCE_OF_DROP))
			st.playSound(SOUND_ITEMGET);
	}
}