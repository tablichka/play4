package quests._406_PathToElvenKnight;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест на профессию Path To Elven Knight
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _406_PathToElvenKnight extends Quest
{
	//NPC
	private static final int Sorius = 30327;
	private static final int Kluto = 30317;
	//QuestItems
	private static final int SoriussLetter = 1202;
	private static final int KlutoBox = 1203;
	private static final int TopazPiece = 1205;
	private static final int EmeraldPiece = 1206;
	private static final int KlutosMemo = 1276;
	//Items
	private static final int ElvenKnightBrooch = 1204;
	//MOB
	private static final int TrackerSkeleton = 20035;
	private static final int TrackerSkeletonLeader = 20042;
	private static final int SkeletonScout = 20045;
	private static final int SkeletonBowman = 20051;
	private static final int RagingSpartoi = 20060;
	private static final int OlMahumNovice = 20782;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 2, TrackerSkeleton, 0, TopazPiece, 20, 70, 1},
			{1, 2, TrackerSkeletonLeader, 0, TopazPiece, 20, 70, 1},
			{1, 2, SkeletonScout, 0, TopazPiece, 20, 70, 1},
			{1, 2, SkeletonBowman, 0, TopazPiece, 20, 70, 1},
			{1, 2, RagingSpartoi, 0, TopazPiece, 20, 70, 1},
			{4, 5, OlMahumNovice, 0, EmeraldPiece, 20, 50, 1}};

	private static boolean QuestProf = true;

	public _406_PathToElvenKnight()
	{
		super(406, "_406_PathToElvenKnight", "Path To Elven Knight");

		addStartNpc(Sorius);
		addTalkId(Kluto);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
		addQuestItem(SoriussLetter, KlutosMemo, KlutoBox);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30327-05.htm"))
		{
			if(st.getPlayer().getClassId().getId() != 0x12)
			{
				if(st.getPlayer().getClassId().getId() == 0x13)
					htmltext = "30327-02a.htm";
				else
					htmltext = "30327-02.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getQuestItemsCount(ElvenKnightBrooch) > 0)
			{
				htmltext = "30327-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.getPlayer().getLevel() < 18)
			{
				htmltext = "30327-03.htm";
				st.exitCurrentQuest(true);
			}
		}
		else if(event.equalsIgnoreCase("30327-06.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}

		else if(event.equalsIgnoreCase("30317-02.htm"))
		{
			st.takeItems(SoriussLetter, -1);
			st.giveItems(KlutosMemo, 1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else
			htmltext = "noquest";

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		String htmltext = "noquest";
		int cond = st.getInt("cond");
		if(npcId == Sorius)
		{
			if(st.isCreated())
				htmltext = "30327-01.htm";
			else if(cond == 1)
			{
				if(st.getQuestItemsCount(TopazPiece) < 1)
					htmltext = "30327-07.htm";
				else
					htmltext = "30327-08.htm";
			}
			else if(cond == 2)
			{
				st.takeItems(TopazPiece, -1);
				st.giveItems(SoriussLetter, 1);
				htmltext = "30327-09.htm";
				st.set("cond", "3");
				st.setState(STARTED);
			}
			else if(cond == 3 || cond == 4 || cond == 5)
				htmltext = "30327-11.htm";
			else if(cond == 6)
			{
				st.takeItems(KlutoBox, -1);
				if(st.getPlayer().getClassId().getLevel() == 1)
				{
					st.giveItems(ElvenKnightBrooch, 1);
					if(!st.getPlayer().getVarB("prof1"))
					{
						st.getPlayer().setVar("prof1", "1");
						if(st.getPlayer().getLevel() >= 20)
							st.addExpAndSp(320534, 23152);
						else if(st.getPlayer().getLevel() == 19)
							st.addExpAndSp(456128, 29850);
						else
							st.addExpAndSp(591724, 33328);
						st.rollAndGive(57, 163800, 100);
					}
				}
				st.exitCurrentQuest(true);
				st.showSocial(3);
				st.playSound(SOUND_FINISH);
				htmltext = "30327-10.htm";
			}
		}
		else if(npcId == Kluto)
			if(cond == 3)
				htmltext = "30317-01.htm";
			else if(cond == 4)
			{
				if(st.getQuestItemsCount(EmeraldPiece) < 1)
					htmltext = "30317-03.htm";
				else
					htmltext = "30317-04.htm";
			}
			else if(cond == 5)
			{
				st.takeItems(EmeraldPiece, -1);
				st.takeItems(KlutosMemo, -1);
				st.giveItems(KlutoBox, 1);
				htmltext = "30317-05.htm";
				st.set("cond", "6");
				st.setState(STARTED);
			}
			else if(cond == 6)
				htmltext = "30317-06.htm";
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
