package quests._326_VanquishRemnants;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Vanquish Remnants
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _326_VanquishRemnants extends Quest
{
	//NPC
	private static final int Leopold = 30435;
	//Quest Items
	private static final int RedCrossBadge = 1359;
	private static final int BlueCrossBadge = 1360;
	private static final int BlackCrossBadge = 1361;
	//Items
	private static final int BlackLionMark = 1369;
	//MOB
	private static final int OlMahumPatrol = 20053;
	private static final int OlMahumGuard = 20058;
	private static final int OlMahumRecruit = 20437;
	private static final int OlMahumStraggler = 20061;
	private static final int OlMahumShooter = 20063;
	private static final int OlMahumSupplier = 20436;
	private static final int OlMahumCaptain = 20066;
	private static final int OlMahumGeneral = 20438;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	public final int[][] DROPLIST_COND = {
			{1, 0, OlMahumPatrol, 0, RedCrossBadge, 0, 25, 1},
			{1, 0, OlMahumGuard, 0, RedCrossBadge, 0, 25, 1},
			{1, 0, OlMahumRecruit, 0, RedCrossBadge, 0, 25, 1},
			{1, 0, OlMahumStraggler, 0, BlueCrossBadge, 0, 25, 1},
			{1, 0, OlMahumShooter, 0, BlueCrossBadge, 0, 25, 1},
			{1, 0, OlMahumSupplier, 0, BlueCrossBadge, 0, 25, 1},
			{1, 0, OlMahumCaptain, 0, BlackCrossBadge, 0, 35, 1},
			{1, 0, OlMahumGeneral, 0, BlackCrossBadge, 0, 25, 1}};

	public _326_VanquishRemnants()
	{
		super(326, "_326_VanquishRemnants", "Vanquish Remnants");
		addStartNpc(Leopold);
		addTalkId(Leopold);
		//Mob Drop
		for(int i = 0; i < DROPLIST_COND.length; i++)
			addKillId(DROPLIST_COND[i][2]);
		addQuestItem(RedCrossBadge);
		addQuestItem(BlueCrossBadge);
		addQuestItem(BlackCrossBadge);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30435-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30435-03.htm"))
		{
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
		int cond = st.getInt("cond");
		if(npcId == Leopold)
			if(st.getPlayer().getLevel() < 21)
			{
				htmltext = "30435-01.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
				htmltext = "30435-02.htm";
			else if(cond == 1 && st.getQuestItemsCount(RedCrossBadge) == 0 && st.getQuestItemsCount(BlueCrossBadge) == 0 && st.getQuestItemsCount(BlackCrossBadge) == 0)
				htmltext = "30435-04.htm";
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(RedCrossBadge) + st.getQuestItemsCount(BlueCrossBadge) + st.getQuestItemsCount(BlackCrossBadge) >= 100)
				{
					if(st.getQuestItemsCount(BlackLionMark) == 0)
					{
						htmltext = "30435-09.htm";
						st.giveItems(BlackLionMark, 1);
					}
					else
						htmltext = "30435-06.htm";
				}
				else
					htmltext = "30435-05.htm";
				long adena = st.getQuestItemsCount(RedCrossBadge) * 60 + st.getQuestItemsCount(BlueCrossBadge) * 65 + st.getQuestItemsCount(BlackCrossBadge) * 70;
				st.takeItems(RedCrossBadge, -1);
				st.takeItems(BlueCrossBadge, -1);
				st.takeItems(BlackCrossBadge, -1);
				st.rollAndGive(57, adena, 100);
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