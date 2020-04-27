package quests._356_DigUpTheSeaOfSpores;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _356_DigUpTheSeaOfSpores extends Quest
{
	//NPC
	private static final int GAUEN = 30717;

	//MOBS
	private static final int SPORE_ZOMBIE = 20562;
	private static final int ROTTING_TREE = 20558;

	//QUEST ITEMS
	private static final int CARNIVORE_SPORE = 5865;
	private static final int HERBIBOROUS_SPORE = 5866;

	public _356_DigUpTheSeaOfSpores()
	{
		super(356, "_356_DigUpTheSeaOfSpores", "Dig Up The Sea Of Spores");
		addStartNpc(GAUEN);

		addKillId(SPORE_ZOMBIE);
		addKillId(ROTTING_TREE);

		addQuestItem(CARNIVORE_SPORE, HERBIBOROUS_SPORE);

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		long carn = st.getQuestItemsCount(CARNIVORE_SPORE);
		long herb = st.getQuestItemsCount(HERBIBOROUS_SPORE);
		if(event.equalsIgnoreCase("30717-5.htm"))
		{
			if(st.getPlayer().getLevel() >= 43)
			{
				st.set("cond", "1");
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
			}
			else
			{
				htmltext = "30717-4.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if((event.equalsIgnoreCase("30717-10.htm") || event.equalsIgnoreCase("30717-9.htm")) && carn >= 50 && herb >= 50)
		{
			st.takeItems(CARNIVORE_SPORE, -1);
			st.takeItems(HERBIBOROUS_SPORE, -1);
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
			if(event.equalsIgnoreCase("30717-9.htm"))
				st.rollAndGive(57, 44000, 100);
			else
				st.addExpAndSp(36000, 2600);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(st.isCreated())
			htmltext = "30717-0.htm";
		else if(cond != 3)
			htmltext = "30717-6.htm";
		else htmltext = "30717-7.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(npcId == SPORE_ZOMBIE && st.getCond() == 1 && st.rollAndGiveLimited(CARNIVORE_SPORE, 1, 100, 50))
			st.playSound(SOUND_ITEMGET);
		else if(npcId == ROTTING_TREE && st.getCond() == 1 && st.rollAndGiveLimited(HERBIBOROUS_SPORE, 1, 100, 50))
			st.playSound(SOUND_ITEMGET);

		if(st.getQuestItemsCount(CARNIVORE_SPORE) == 50 && st.getQuestItemsCount(HERBIBOROUS_SPORE) == 50)
		{
			st.playSound(SOUND_MIDDLE);
			st.set("cond", "3");
			st.setState(STARTED);
		}
	}
}