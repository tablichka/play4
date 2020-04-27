package quests._319_ScentOfDeath;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Scent Of Death
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _319_ScentOfDeath extends Quest
{
	//NPC
	private static final int MINALESS = 30138;
	//Item
	private static final int ADENA = 57;
	private static final int HealingPotion = 1061;
	//Quest Item
	private static final int ZombieSkin = 1045;

	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]
	private static final int[][] DROPLIST_COND = {
			{1, 2, 20015, 0, ZombieSkin, 5, 20, 1},
			{1, 2, 20016, 0, ZombieSkin, 5, 20, 1},
			{1, 2, 20017, 0, ZombieSkin, 5, 20, 1},
			{1, 2, 20018, 0, ZombieSkin, 5, 20, 1},
			{1, 2, 20019, 0, ZombieSkin, 5, 20, 1},
			{1, 2, 20020, 0, ZombieSkin, 5, 20, 1}};

	public _319_ScentOfDeath()
	{
		super(319, "_319_ScentOfDeath", "Scent Of Death");

		addStartNpc(MINALESS);
		addTalkId(MINALESS);
		//Mob Drop
		for(int i = 0; i < DROPLIST_COND.length; i++)
			addKillId(DROPLIST_COND[i][2]);

		addQuestItem(ZombieSkin);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30138-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
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
		if(npcId == MINALESS)
			if(st.isCreated())
				if(st.getPlayer().getLevel() < 11)
				{
					htmltext = "30138-02.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30138-03.htm";
			else if(cond == 1)
				htmltext = "30138-05.htm";
			else if(cond == 2 && st.getQuestItemsCount(ZombieSkin) >= 5)
			{
				htmltext = "30138-06.htm";
				st.takeItems(ZombieSkin, -1);
				st.rollAndGive(ADENA, 3350, 100);
				st.rollAndGive(HealingPotion, 1, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
			{
				htmltext = "30138-05.htm";
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
