package quests._284_MuertosFeather;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Muertos Feather
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _284_MuertosFeather extends Quest
{
	//NPC
	private static final int Trevor = 32166;
	//Quest Item
	private static final int MuertosFeather = 9748;
	//Items
	private static final int Adena = 57;
	//MOBs
	private static final int MuertosGuard = 22239;
	private static final int MuertosScout = 22240;
	private static final int MuertosWarrior = 22242;
	private static final int MuertosCaptain = 22243;
	private static final int MuertosLieutenant = 22245;
	private static final int MuertosCommander = 22246;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 0, MuertosGuard, 0, MuertosFeather, 0, 44, 1},
			{1, 0, MuertosScout, 0, MuertosFeather, 0, 48, 1},
			{1, 0, MuertosWarrior, 0, MuertosFeather, 0, 56, 1},
			{1, 0, MuertosCaptain, 0, MuertosFeather, 0, 60, 1},
			{1, 0, MuertosLieutenant, 0, MuertosFeather, 0, 64, 1},
			{1, 0, MuertosCommander, 0, MuertosFeather, 0, 69, 1}};

	public _284_MuertosFeather()
	{
		super(284, "_284_MuertosFeather", "Muertos Feather");

		addStartNpc(Trevor);

		addTalkId(Trevor);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
		addQuestItem(MuertosFeather);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("32166-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("32166-04.htm"))
		{
			long counts = st.getQuestItemsCount(MuertosFeather) * 45;
			st.takeItems(MuertosFeather, -1);
			st.rollAndGive(Adena, counts, 100);
		}
		else if(event.equalsIgnoreCase("32166-05.htm"))
			st.exitCurrentQuest(true);
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Trevor)
			if(st.getPlayer().getLevel() < 11)
			{
				htmltext = "32166-00.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
				htmltext = "32166-01.htm";
			else if(cond == 1 && st.getQuestItemsCount(MuertosFeather) == 0)
				htmltext = "32166-02.htm";
			else
				htmltext = "32166-03.htm";
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