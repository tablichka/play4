package quests._292_BrigandsSweep;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

/**
 * Квест Brigands Sweep
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _292_BrigandsSweep extends Quest
{
	// NPCs
	private static int Spiron = 30532;
	private static int Balanki = 30533;
	// Mobs
	private static int GoblinBrigand = 20322;
	private static int GoblinBrigandLeader = 20323;
	private static int GoblinBrigandLieutenant = 20324;
	private static int GoblinSnooper = 20327;
	private static int GoblinLord = 20528;
	// Items
	private static int Adena = 57;
	// Quest Items
	private static short GoblinNecklace = 1483;
	private static short GoblinPendant = 1484;
	private static short GoblinLordPendant = 1485;
	private static short SuspiciousMemo = 1486;
	private static short SuspiciousContract = 1487;
	// Chances
	private static int Chance = 10;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 0, GoblinBrigand, 0, GoblinNecklace, 0, 40, 1},
			{1, 0, GoblinBrigandLeader, 0, GoblinNecklace, 0, 40, 1},
			{1, 0, GoblinSnooper, 0, GoblinNecklace, 0, 40, 1},
			{1, 0, GoblinBrigandLieutenant, 0, GoblinPendant, 0, 40, 1},
			{1, 0, GoblinLord, 0, GoblinLordPendant, 0, 40, 1}};

	public _292_BrigandsSweep()
	{
		super(292, "_292_BrigandsSweep", "Brigands Sweep");
		addStartNpc(Spiron);
		addTalkId(Balanki);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);

		addQuestItem(SuspiciousMemo, SuspiciousContract, GoblinNecklace, GoblinPendant, GoblinLordPendant);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30532-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30532-06.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Spiron)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace().ordinal() != 4)
				{
					htmltext = "30532-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 5)
				{
					htmltext = "30532-01.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30532-02.htm";
			}
			else if(cond == 1)
			{
				long reward = st.getQuestItemsCount(GoblinNecklace) * 12 + st.getQuestItemsCount(GoblinPendant) * 36 + st.getQuestItemsCount(GoblinLordPendant) * 33 + st.getQuestItemsCount(SuspiciousContract) * 100;
				if(reward == 0)
					return "30532-04.htm";
				if(st.getQuestItemsCount(SuspiciousContract) != 0)
					htmltext = "30532-10.htm";
				else if(st.getQuestItemsCount(SuspiciousMemo) == 0)
					htmltext = "30532-05.htm";
				else if(st.getQuestItemsCount(SuspiciousMemo) == 1)
					htmltext = "30532-08.htm";
				else
					htmltext = "30532-09.htm";
				st.takeItems(GoblinNecklace, -1);
				st.takeItems(GoblinPendant, -1);
				st.takeItems(GoblinLordPendant, -1);
				st.takeItems(SuspiciousContract, -1);
				st.rollAndGive(Adena, reward, 100);
			}
		}
		else if(npcId == Balanki && cond == 1)
		{
			if(st.getQuestItemsCount(SuspiciousContract) == 0)
				htmltext = "30533-01.htm";
			else
			{
				st.takeItems(SuspiciousContract, -1);
				st.rollAndGive(Adena, 120, 100);
				htmltext = "30533-02.htm";
			}
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
		if(st.getQuestItemsCount(SuspiciousContract) == 0 && Rnd.chance(Chance))
		{
			if(st.getQuestItemsCount(SuspiciousMemo) < 3)
			{
				st.giveItems(SuspiciousMemo, 1);
				st.playSound(SOUND_ITEMGET);
			}
			else
			{
				st.takeItems(SuspiciousMemo, -1);
				st.giveItems(SuspiciousContract, 1);
				st.playSound(SOUND_MIDDLE);
			}
		}
	}
}