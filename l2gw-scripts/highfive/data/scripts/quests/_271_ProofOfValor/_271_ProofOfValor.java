package quests._271_ProofOfValor;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Proof Of Valor
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _271_ProofOfValor extends Quest
{
	//NPC
	private static final int RUKAIN = 30577;
	//Quest Item
	private static final int KASHA_WOLF_FANG_ID = 1473;
	private static final int NECKLACE_OF_VALOR_ID = 1507;
	private static final int NECKLACE_OF_COURAGE_ID = 1506;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {{1, 2, 20475, 0, KASHA_WOLF_FANG_ID, 50, 25, 2}};

	public _271_ProofOfValor()
	{
		super(271, "_271_ProofOfValor", "Proof of Valor");

		addStartNpc(RUKAIN);
		addTalkId(RUKAIN);

		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);

		addQuestItem(KASHA_WOLF_FANG_ID);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30577-03.htm"))
		{
			st.playSound(SOUND_ACCEPT);
			if(st.getQuestItemsCount(NECKLACE_OF_COURAGE_ID) > 0 || st.getQuestItemsCount(NECKLACE_OF_VALOR_ID) > 0)
				htmltext = "30577-07.htm";
			st.set("cond", "1");
			st.setState(STARTED);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == RUKAIN)
			if(st.isCreated())
			{
				if(st.getPlayer().getRace().ordinal() != 3)
				{
					htmltext = "30577-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 4)
				{
					htmltext = "30577-01.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getQuestItemsCount(NECKLACE_OF_COURAGE_ID) > 0 || st.getQuestItemsCount(NECKLACE_OF_VALOR_ID) > 0)
				{
					htmltext = "30577-06.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30577-02.htm";
			}
			else if(cond == 1)
				htmltext = "30577-04.htm";
			else if(cond == 2 && st.getQuestItemsCount(KASHA_WOLF_FANG_ID) == 50)
			{
				st.takeItems(KASHA_WOLF_FANG_ID, -1);
				if(Rnd.chance(14))
				{
					st.takeItems(NECKLACE_OF_VALOR_ID, -1);
					st.giveItems(NECKLACE_OF_VALOR_ID, 1);
				}
				else
				{
					st.takeItems(NECKLACE_OF_COURAGE_ID, -1);
					st.giveItems(NECKLACE_OF_COURAGE_ID, 1);
				}
				htmltext = "30577-05.htm";
				st.exitCurrentQuest(true);
			}
			else if(cond == 2 && st.getQuestItemsCount(KASHA_WOLF_FANG_ID) < 50)
			{
				htmltext = "30577-04.htm";
				st.set("cond", "1");
				st.setState(STARTED);
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
