package quests._298_LizardmensConspiracy;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _298_LizardmensConspiracy extends Quest
{
	//	npc
	public final int PRAGA = 30333;
	public final int ROHMER = 30344;
	//mobs
	public final int MAILLE_LIZARDMAN_WARRIOR = 20922;
	public final int MAILLE_LIZARDMAN_SHAMAN = 20923;
	public final int MAILLE_LIZARDMAN_MATRIARCH = 20924;
	//public final int GIANT_ARANEID = 20925; //в клиенте о нем ни слова
	public final int POISON_ARANEID = 20926;
	public final int KING_OF_THE_ARANEID = 20927;
	//items
	public final int REPORT = 7182;
	public final int SHINING_GEM = 7183;
	public final int SHINING_RED_GEM = 7184;
	//MobsTable {MOB_ID, ITEM_ID}
	public final int[][] MobsTable = {
			{MAILLE_LIZARDMAN_WARRIOR, SHINING_GEM},
			{MAILLE_LIZARDMAN_SHAMAN, SHINING_GEM},
			{MAILLE_LIZARDMAN_MATRIARCH, SHINING_GEM},
			//{ GIANT_ARANEID, SHINING_RED_GEM },
			{POISON_ARANEID, SHINING_RED_GEM},
			{KING_OF_THE_ARANEID, SHINING_RED_GEM}};

	public _298_LizardmensConspiracy()
	{
		super(298, "_298_LizardmensConspiracy", "Lizardmen's Conspiracy");

		addStartNpc(PRAGA);

		addTalkId(PRAGA);
		addTalkId(ROHMER);

		for(int[] element : MobsTable)
			addKillId(element[0]);

		addQuestItem(REPORT, SHINING_GEM, SHINING_RED_GEM);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30333-02.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
			st.giveItems(REPORT, 1);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30344-02.htm"))
		{
			st.takeItems(REPORT, -1);
			st.set("cond", "2");
			st.playSound(SOUND_MIDDLE);
		}
		else if(event.equalsIgnoreCase("30344-04.htm") && st.getQuestItemsCount(SHINING_GEM) + st.getQuestItemsCount(SHINING_RED_GEM) > 99)
		{
			st.takeItems(SHINING_GEM, -1);
			st.takeItems(SHINING_RED_GEM, -1);
			st.addExpAndSp(0, 42000);
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == PRAGA)
		{
			if(cond < 1)
				if(st.getPlayer().getLevel() < 25)
				{
					htmltext = "30333-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30333-01.htm";
			if(cond == 1)
				htmltext = "30333-02r.htm";
		}
		else if(npcId == ROHMER)
			if(cond < 1)
				htmltext = "30344-0.htm";
			else if(cond == 1)
				htmltext = "30344-01.htm";
			else if(cond == 2 | st.getQuestItemsCount(SHINING_GEM) + st.getQuestItemsCount(SHINING_RED_GEM) < 100)
			{
				htmltext = "30344-02r.htm";
				st.set("cond", "2");
			}
			else if(cond == 3 && st.getQuestItemsCount(SHINING_GEM) + st.getQuestItemsCount(SHINING_RED_GEM) > 99)
				htmltext = "30344-03.htm";
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int rand = Rnd.get(10);
		if(st.getInt("cond") == 2)
			for(int[] element : MobsTable)
				if(npcId == element[0])
					if(rand < 6 && st.getQuestItemsCount(element[1]) < 50)
					{
						if(rand < 2 && element[1] == SHINING_GEM)
							st.rollAndGiveLimited(element[1], 2, 100, 50);
						else
							st.rollAndGiveLimited(element[1], 1, 100, 50);
						if(st.getQuestItemsCount(SHINING_GEM) + st.getQuestItemsCount(SHINING_RED_GEM) > 99)
						{
							st.set("cond", "3");
							st.playSound(SOUND_MIDDLE);
							st.setState(STARTED);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
	}
}