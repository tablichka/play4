package quests._360_PlunderTheirSupplies;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _360_PlunderTheirSupplies extends Quest
{
	//NPC
	private static final int COLEMAN = 30873;

	//MOBS
	private static final int TAIK_SEEKER = 20666;
	private static final int TAIK_LEADER = 20669;

	//QUEST ITEMS
	private static final int SUPPLY_ITEM = 5872;
	private static final int SUSPICIOUS_DOCUMENT = 5871;
	private static final int RECIPE_OF_SUPPLY = 5870;
	private static final int ADENA = 57;

	//DROP CHANCES
	private static final int ITEM_DROP_SEEKER = 50;
	private static final int ITEM_DROP_LEADER = 65;
	private static final int DOCUMENT_DROP = 5;

	public _360_PlunderTheirSupplies()
	{
		super(360, "_360_PlunderTheirSupplies", "Plunder Their Supplies");
		addStartNpc(COLEMAN);

		addKillId(TAIK_SEEKER);
		addKillId(TAIK_LEADER);

		addQuestItem(SUPPLY_ITEM, SUSPICIOUS_DOCUMENT, RECIPE_OF_SUPPLY);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30873-2.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30873-6.htm"))
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
		long docs = st.getQuestItemsCount(RECIPE_OF_SUPPLY);
		long supplies = st.getQuestItemsCount(SUPPLY_ITEM);
		if(st.isCreated())
		{
			if(st.getPlayer().getLevel() >= 52)
				htmltext = "30873-0.htm";
			else
				htmltext = "30873-7.htm";
		}
		else if(docs > 0 || supplies > 0)
		{
			long reward = 6000 + supplies * 100 + docs * 6000;
			st.takeItems(SUPPLY_ITEM, -1);
			st.takeItems(RECIPE_OF_SUPPLY, -1);
			st.rollAndGive(ADENA, reward, 100);
			htmltext = "30873-5.htm";
		}
		else
			htmltext = "30873-3.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		if(st.getCond() == 1 && ((npcId == TAIK_SEEKER && st.rollAndGive(SUPPLY_ITEM, 1, ITEM_DROP_SEEKER)) ||
				(npcId == TAIK_LEADER && st.rollAndGive(SUPPLY_ITEM, 1, ITEM_DROP_LEADER))))
			st.playSound(SOUND_ITEMGET);

		if(st.getCond() == 1 && st.rollAndGiveLimited(SUSPICIOUS_DOCUMENT, 1, DOCUMENT_DROP, 5))
		{
			if(st.getQuestItemsCount(SUSPICIOUS_DOCUMENT) == 5)
			{
				st.takeItems(SUSPICIOUS_DOCUMENT, -1);
				st.giveItems(RECIPE_OF_SUPPLY, 1);
				st.playSound(SOUND_MIDDLE);
			}
			else
				st.playSound(SOUND_ITEMGET);
		}
	}
}