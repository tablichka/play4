package quests._649_ALooterandaRailroadMan;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест A Looteranda Railroad Man
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _649_ALooterandaRailroadMan extends Quest
{
	//NPC
	private static final int OBI = 32052;
	//Quest Item
	private static final int THIEF_GUILD_MARK = 8099;
	//Item
	private static final int ADENA = 57;
	//Main
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 2, 22017, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22018, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22019, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22021, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22022, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22023, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22024, 0, THIEF_GUILD_MARK, 200, 50, 1},
			{1, 2, 22026, 0, THIEF_GUILD_MARK, 200, 50, 1}};

	public _649_ALooterandaRailroadMan()
	{
		super(649, "_649_ALooterandaRailroadMan", "A Looter and a Railroad Man");

		addStartNpc(OBI);
		addTalkId(OBI);
		//Mob Drop
		for(int[] comd : DROPLIST_COND)
			addKillId(comd[2]);
		addQuestItem(THIEF_GUILD_MARK);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32052-1.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32052-3.htm"))
			if(st.getQuestItemsCount(THIEF_GUILD_MARK) == 200)
			{
				st.takeItems(THIEF_GUILD_MARK, -1);
				st.rollAndGive(ADENA, 21698, 100);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(true);
			}
			else
			//Проверка сработает если игрок во время диалога удалит марки
			{
				st.set("cond", "1");
				htmltext = "32052-3a.htm";
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
		if(npcId == OBI)
			if(st.isCreated())
				if(st.getPlayer().getLevel() < 30)
				{
					htmltext = "32052-0a.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "32052-0.htm";
			else if(cond == 1)
				htmltext = "32052-2a.htm";
			else if(cond == 2 && st.getQuestItemsCount(THIEF_GUILD_MARK) == 200)
				htmltext = "32052-2.htm";
			else
			{
				htmltext = "32052-2a.htm";
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
