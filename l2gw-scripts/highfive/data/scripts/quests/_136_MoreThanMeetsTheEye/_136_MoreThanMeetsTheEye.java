package quests._136_MoreThanMeetsTheEye;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест More Than Meets The Eye
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _136_MoreThanMeetsTheEye extends Quest
{
	//NPC
	private static final int HARDIN = 30832;
	private static final int ERRICKIN = 30701;
	private static final int CLAYTON = 30464;
	//Item
	private static final int TransformSealbook = 9648;
	//Quest Item
	private static final int Ectoplasm = 9787;
	private static final int StabilizedEctoplasm = 9786;
	private static final int HardinsInstructions = 9788;
	private static final int GlassJaguarCrystal = 9789;
	private static final int BlankSealbook = 9790;

	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{3, 4, 20636, 0, Ectoplasm, 35, 100, 1},
			{3, 4, 20637, 0, Ectoplasm, 35, 100, 1},
			{3, 4, 20638, 0, Ectoplasm, 35, 100, 1},
			{3, 4, 20639, 0, Ectoplasm, 35, 100, 2},
			{7, 8, 20250, 0, GlassJaguarCrystal, 5, 100, 1}};

	public _136_MoreThanMeetsTheEye()
	{
		super(136, "_136_MoreThanMeetsTheEye", "More Than Meets The Eye");

		addStartNpc(HARDIN);
		addTalkId(HARDIN);
		addTalkId(ERRICKIN);
		addTalkId(CLAYTON);

		addQuestItem(StabilizedEctoplasm, HardinsInstructions, BlankSealbook, Ectoplasm, GlassJaguarCrystal);

		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30832-06.htm"))
		{
			st.set("cond", "2");
			st.set("id", "0");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30701-02.htm"))
		{
			st.set("cond", "3");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30832-12.htm"))
		{
			st.giveItems(HardinsInstructions, 1);
			st.set("cond", "6");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30464-02.htm"))
		{
			st.set("cond", "7");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30832-17.htm"))
		{
			st.playSound(SOUND_FINISH);
			st.giveItems(TransformSealbook, 1);
			st.rollAndGive(57, 67550, 100);
			st.unset("id");
			st.unset("cond");
			st.exitCurrentQuest(false);
		}
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
		if(npcId == HARDIN)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 50)
				{
					st.set("cond", "1");
					htmltext = "30832-02.htm";
				}
				else
				{
					htmltext = "30832-01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(cond == 2 || cond == 3 || cond == 4)
				htmltext = "30832-07.htm";
			else if(cond == 5)
			{
				st.takeItems(StabilizedEctoplasm, -1);
				htmltext = "30832-08.htm";
			}
			else if(cond == 6)
				htmltext = "30832-13.htm";
			else if(cond == 9)
			{
				st.takeItems(BlankSealbook, -1);
				htmltext = "30832-14.htm";
			}
		}
		else if(npcId == ERRICKIN)
		{
			if(cond == 2)
				htmltext = "30701-01.htm";
			else if(cond == 3)
				htmltext = "30701-02.htm";
			else if(cond == 4 && st.getQuestItemsCount(Ectoplasm) < 35 && st.getInt("id") == 0)
			{
				st.set("cond", "3");
				htmltext = "30701-02.htm";
			}
			else if(cond == 4 && st.getInt("id") == 0)
			{
				st.takeItems(Ectoplasm, -1);
				htmltext = "30701-03.htm";
				st.set("id", "1");
			}
			else if(cond == 4 && st.getInt("id") == 1)
			{
				htmltext = "30701-04.htm";
				st.giveItems(StabilizedEctoplasm, 1);
				st.set("id", "0");
				st.set("cond", "5");
				st.setState(STARTED);
			}
			else if(cond == 5)
				htmltext = "30701-05.htm";
		}
		else if(npcId == CLAYTON)
			if(cond == 6)
			{
				st.takeItems(HardinsInstructions, -1);
				htmltext = "30464-01.htm";
			}
			else if(cond == 7)
				htmltext = "30464-02.htm";
			else if(cond == 8 && st.getQuestItemsCount(GlassJaguarCrystal) < 5)
			{
				htmltext = "30464-03.htm";
				st.set("cond", "7");
			}
			else if(cond == 8)
			{
				htmltext = "30464-04.htm";
				st.takeItems(GlassJaguarCrystal, -1);
				st.giveItems(BlankSealbook, 1);
				st.set("cond", "9");
				st.setState(STARTED);
			}
			else if(cond == 9)
				htmltext = "30464-05.htm";
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
					if(st.rollAndGiveLimited(aDROPLIST_COND[4], 1, aDROPLIST_COND[6], aDROPLIST_COND[5]))
					{
						if(st.getQuestItemsCount(aDROPLIST_COND[4]) == aDROPLIST_COND[5])
						{
							st.playSound(SOUND_MIDDLE);
							if(aDROPLIST_COND[1] != 0)
							{
								st.set("cond", String.valueOf(aDROPLIST_COND[1]));
								st.setState(STARTED);
							}
						}
						else
							st.playSound(SOUND_ITEMGET);
					}
				}
	}
}