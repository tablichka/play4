package quests._317_CatchTheWind;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Catch The Wind
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _317_CatchTheWind extends Quest
{
	//NPCs
	private static int Rizraell = 30361;
	//Quest Items
	private static int WindShard = 1078;
	//Items
	private static int Adena = 57;
	//Mobs
	private static int Lirein = 20036;
	private static int LireinElder = 20044;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	public final int[][] DROPLIST_COND = {{1, 0, Lirein, 0, WindShard, 0, 60, 1}, {1, 0, LireinElder, 0, WindShard, 0, 60, 1}};

	public _317_CatchTheWind()
	{
		super(317, "_317_CatchTheWind", "Catch The Wind");
		addStartNpc(Rizraell);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
		addQuestItem(WindShard);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("30361-04.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30361-08.htm"))
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
		if(npcId == Rizraell)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 18)
					htmltext = "30361-03.htm";
				else
				{
					htmltext = "30361-02.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 1)
			{
				long count = st.getQuestItemsCount(WindShard);
				if(count > 0)
				{
					st.takeItems(WindShard, -1);
					st.rollAndGive(Adena, 40 * count, 100);
					htmltext = "30361-07.htm";
				}
				else
					htmltext = "30361-05.htm";
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