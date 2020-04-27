package quests._039_RedEyedInvaders;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

//import ru.l2gw.commons.math.Rnd;

/**
 * Квест Red Eyed Invaders
 *
 * @author Copyleft, ибо код устарел морально
 * @Last_Fix by HellSinger
 */

public class _039_RedEyedInvaders extends Quest
{
	//NPC
	private final static int Bathis = 30332;
	private final static int Babenco = 30334;
	//MONSTERS
	private final static int MailleLizardman = 20919;
	private final static int MailleLizardmanScout = 20920;
	private final static int MailleLizardmanGuard = 20921;
	private final static int GiantAraneid = 20925;
	//ITEMS
	private final static int BlackBoneNecklace = 7178;
	private final static int RedBoneNecklace = 7179;
	private final static int IncensePouch = 7180;
	private final static int GemMailleLizardman = 7181;
	private final static int[] REWARD = {6521, 6529, 6535};
	//Drop Cond
	//# [COND, NPC, ITEM, NEED_COUNT, NEXT_COND]
	private static final int[][] DROPLIST_COND = {
			{2, MailleLizardman, BlackBoneNecklace, 100, 3},
			{2, MailleLizardmanScout, BlackBoneNecklace, 100, 3},
			{2, MailleLizardmanGuard, RedBoneNecklace, 100, 3},
			{4, MailleLizardmanScout, IncensePouch, 30, 5},
			{4, MailleLizardmanGuard, IncensePouch, 30, 5},
			{4, GiantAraneid, GemMailleLizardman, 30, 5}};

	public _039_RedEyedInvaders()
	{
		super(39, "_039_RedEyedInvaders", "Red Eyed Invaders");

		addStartNpc(Babenco);

		addTalkId(Bathis);
		addTalkId(Babenco);

		for(int[] cond : DROPLIST_COND)
			addKillId(cond[1]);

		addQuestItem(BlackBoneNecklace, IncensePouch, RedBoneNecklace, GemMailleLizardman);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equals("30334-02.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30332-02.htm"))
		{
			st.set("cond", "2");
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equals("30332-04.htm"))
		{
			if(st.getQuestItemsCount(BlackBoneNecklace) > 99 && st.getQuestItemsCount(RedBoneNecklace) > 99)
			{
				st.set("cond", "4");
				st.takeItems(BlackBoneNecklace, -1);
				st.takeItems(RedBoneNecklace, -1);
				st.playSound(SOUND_ACCEPT);
			}
			else
				htmltext = "30332-02r.htm";
		}
		else if(event.equals("30332-06.htm"))
			if(st.getQuestItemsCount(IncensePouch) > 29 && st.getQuestItemsCount(GemMailleLizardman) > 29)
			{
				st.takeItems(IncensePouch, -1);
				st.takeItems(GemMailleLizardman, -1);
				st.rollAndGive(REWARD[0], 60, 100);
				st.giveItems(REWARD[1], 1);
				st.rollAndGive(REWARD[2], 500, 100);
				st.addExpAndSp(62366, 2783);
				st.playSound(SOUND_FINISH);
				st.exitCurrentQuest(false);
			}
			else
				htmltext = "30332-04r.htm";
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Babenco)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 20)
				{
					htmltext = "30334-00.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() >= 20)
					htmltext = "30334-01.htm";
			}
			else if(cond == 1)
				htmltext = "30334-02r.htm";
		}
		else if(npcId == Bathis)
			if(cond == 1)
				htmltext = "30332-01.htm";
			else if(cond == 2 || cond == 3)
			{
				if(st.getQuestItemsCount(BlackBoneNecklace) < 100 || st.getQuestItemsCount(RedBoneNecklace) < 100)
				{
					if(st.getInt("cond") == 3) //защита от "дурака"
						st.set("cond", "2");
					htmltext = "30332-02r.htm";
				}
				else if(cond == 3 && st.getQuestItemsCount(BlackBoneNecklace) > 99 && st.getQuestItemsCount(RedBoneNecklace) > 99)
					htmltext = "30332-03.htm";
			}
			else if(cond == 4 || cond == 5)
			{
				if(st.getQuestItemsCount(IncensePouch) < 30 || st.getQuestItemsCount(GemMailleLizardman) < 30)
				{
					if(st.getInt("cond") == 5) //защита от "дурака"
						st.set("cond", "4");
					htmltext = "30332-04r.htm";
				}
				else if(cond == 5 && st.getQuestItemsCount(IncensePouch) > 29 && st.getQuestItemsCount(GemMailleLizardman) > 29)
					htmltext = "30332-05.htm";
			}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		int cond = st.getCond();
		int chance = 60;
		//# [COND, NPC, ITEM, NEED_COUNT, NEXT_COND]
		for(int[] aDROPLIST_COND : DROPLIST_COND)
		{
			if(cond == aDROPLIST_COND[0] && npc.getNpcId() == aDROPLIST_COND[1])
			{
				if(st.getQuestItemsCount(aDROPLIST_COND[2]) < aDROPLIST_COND[3])
				{
					st.rollAndGive(aDROPLIST_COND[2], 1, chance);
					st.playSound(SOUND_ITEMGET);
					if(aDROPLIST_COND[4] != cond && (st.getQuestItemsCount(BlackBoneNecklace) > 99 && st.getQuestItemsCount(RedBoneNecklace) > 99 || st.getQuestItemsCount(IncensePouch) > 29 && st.getQuestItemsCount(GemMailleLizardman) > 29))
					{
						st.set("cond", String.valueOf(aDROPLIST_COND[4]));
						st.setState(STARTED);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
	}
}