package quests._602_ShadowofLight;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Shadowof Light
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _602_ShadowofLight extends Quest
{
	//NPC
	private static final int ARGOS = 31683;
	//Items
	private static final int ADENA = 57;
	//Quest Item
	private static final int EYE_OF_DARKNESS = 7189;
	//Bonus
	private static final int[][] REWARDS = {
			{6699, 40000, 120000, 20000, 1, 19},
			{6698, 60000, 110000, 15000, 20, 39},
			{6700, 40000, 150000, 10000, 40, 49},
			{0, 100000, 140000, 11250, 50, 100}};
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]
	private static final int[][] DROPLIST_COND = {{1, 2, 21304, 0, EYE_OF_DARKNESS, 100, 40, 1}, {1, 2, 21299, 0, EYE_OF_DARKNESS, 100, 35, 1}};

	public _602_ShadowofLight()
	{
		super(602, "_602_ShadowofLight", "Shadowof Light");

		addStartNpc(ARGOS);

		addTalkId(ARGOS);

		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
		addQuestItem(EYE_OF_DARKNESS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("31683-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31683-04.htm"))
		{
			st.takeItems(EYE_OF_DARKNESS, -1);
			int random = Rnd.get(100) + 1;
			for(int i = 0; i < REWARDS.length; i++)
				if(REWARDS[i][4] <= random && random <= REWARDS[i][5])
				{
					st.rollAndGive(ADENA, REWARDS[i][1], 100);
					st.addExpAndSp(REWARDS[i][2], REWARDS[i][3]);
					if(REWARDS[i][0] != 0)
						st.giveItems(REWARDS[i][0], 3);
				}
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
		int cond = 0;
		if(!st.isCreated())
			cond = st.getInt("cond");
		if(npcId == ARGOS)
			if(st.isCreated())
				if(st.getPlayer().getLevel() < 68)
				{
					htmltext = "31683-00.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "31683-01.htm";
			else if(cond == 1)
				htmltext = "31683-02r.htm";
			else if(cond == 2 && st.getQuestItemsCount(EYE_OF_DARKNESS) == 100)
				htmltext = "31683-03.htm";
			else
			{
				htmltext = "31683-02r.htm";
				st.set("cond", "1");
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		for(int[] aDROPLIST_COND : DROPLIST_COND)
			if(cond == aDROPLIST_COND[0] && npcId == aDROPLIST_COND[2])
				if(aDROPLIST_COND[3] == 0 || st.getQuestItemsCount(aDROPLIST_COND[3]) > 0)
				{
					if(aDROPLIST_COND[5] == 0)
						st.rollAndGive(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6]);
					else if(st.rollAndGiveLimited(aDROPLIST_COND[4], aDROPLIST_COND[7], aDROPLIST_COND[6], aDROPLIST_COND[5]))
					{
						if(st.getQuestItemsCount(aDROPLIST_COND[4]) == aDROPLIST_COND[5] && aDROPLIST_COND[1] != cond && aDROPLIST_COND[1] != 0)
						{
							st.playSound(SOUND_MIDDLE);
							st.setCond(aDROPLIST_COND[1]);
							st.setState(STARTED);
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
	}
}
