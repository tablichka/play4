package quests._272_WrathOfAncestors;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Wrath Of Ancestors
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _272_WrathOfAncestors extends Quest
{
	//NPC
	private static final int Livina = 30572;
	//Quest Item
	private static final int GraveRobbersHead = 1474;
	//Item
	private static final int Adena = 57;
	//MOB
	private static final int GoblinGraveRobber = 20319;
	private static final int GoblinTombRaiderLeader = 20320;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 2, GoblinGraveRobber, 0, GraveRobbersHead, 50, 100, 1},
			{1, 2, GoblinTombRaiderLeader, 0, GraveRobbersHead, 50, 100, 1}};

	public _272_WrathOfAncestors()
	{
		super(272, "_272_WrathOfAncestors", "Wrath Of Ancestors");
		addStartNpc(Livina);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
		addQuestItem(GraveRobbersHead);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("1"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
			htmltext = "30572-03.htm";
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Livina)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getRace().ordinal() != 3)
				{
					htmltext = "30572-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 5)
				{
					htmltext = "30572-01.htm";
					st.exitCurrentQuest(true);
				}
				else
				{
					htmltext = "30572-02.htm";
					return htmltext;
				}
			}
			else if(cond == 1)
				htmltext = "30572-04.htm";
			else if(cond == 2)
			{
				st.takeItems(GraveRobbersHead, -1);
				st.rollAndGive(Adena, 1500, 100);
				htmltext = "30572-05.htm";
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
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
	}
}