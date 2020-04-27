package quests._418_PathToArtisan;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Path To Artisan
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _418_PathToArtisan extends Quest
{
	//NPC
	private static final int Silvera = 30527;
	private static final int Kluto = 30317;
	private static final int Pinter = 30298;
	//Quest Item
	private static final int SilverasRing = 1632;
	private static final int BoogleRatmanTooth = 1636;
	private static final int BoogleRatmanLeadersTooth = 1637;
	private static final int PassCertificate1st = 1633;
	private static final int KlutosLetter = 1638;
	private static final int FootprintOfThief = 1639;
	private static final int StolenSecretBox = 1640;
	private static final int PassCertificate2nd = 1634;
	private static final int SecretBox = 1641;
	//Item
	private static final int FinalPassCertificate = 1635;
	//MOB
	private static final int BoogleRatman = 20389;
	private static final int BoogleRatmanLeader = 20390;
	private static final int VukuOrcFighter = 20017;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {
			{1, 0, BoogleRatman, SilverasRing, BoogleRatmanTooth, 10, 35, 1},
			{1, 0, BoogleRatmanLeader, SilverasRing, BoogleRatmanLeadersTooth, 2, 25, 1},
			{5, 6, VukuOrcFighter, FootprintOfThief, StolenSecretBox, 1, 20, 1}};

	private static boolean QuestProf = true;

	public _418_PathToArtisan()
	{
		super(418, "_418_PathToArtisan", "Path To Artisan");
		addStartNpc(Silvera);
		addTalkId(Silvera);
		addTalkId(Kluto);
		addTalkId(Pinter);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
		{
			addKillId(cond[2]);
			addQuestItem(cond[4]);
		}
		addQuestItem(SilverasRing, PassCertificate1st, SecretBox, KlutosLetter, FootprintOfThief, PassCertificate2nd);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30527-06.htm"))
		{
			st.giveItems(SilverasRing, 1);
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30317-04.htm") || event.equalsIgnoreCase("30317-07.htm"))
		{
			st.giveItems(KlutosLetter, 1);
			st.set("cond", "4");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30298-03.htm"))
		{
			st.takeItems(KlutosLetter, -1);
			st.giveItems(FootprintOfThief, 1);
			st.set("cond", "5");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30298-06.htm"))
		{
			st.takeItems(StolenSecretBox, -1);
			st.takeItems(FootprintOfThief, -1);
			st.giveItems(SecretBox, 1);
			st.giveItems(PassCertificate2nd, 1);
			st.set("cond", "7");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30317-10.htm") || event.equalsIgnoreCase("30317-12.htm"))
		{
			st.takeItems(PassCertificate1st, -1);
			st.takeItems(PassCertificate2nd, -1);
			st.takeItems(SecretBox, -1);
			if(st.getPlayer().getClassId().getLevel() == 1)
			{
				st.giveItems(FinalPassCertificate, 1);
				if(!st.getPlayer().getVarB("prof1"))
				{
					st.getPlayer().setVar("prof1", "1");
					if(st.getPlayer().getLevel() >= 20)
						st.addExpAndSp(320534, 32452);
					else if(st.getPlayer().getLevel() == 19)
						st.addExpAndSp(456128, 30150);
					else
						st.addExpAndSp(591724, 36848);
					st.rollAndGive(57, 163800, 100);
				}
			}
			st.showSocial(3);
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
		if(npcId == Silvera)
		{
			if(st.getQuestItemsCount(FinalPassCertificate) > 0)
			{
				htmltext = "30527-04.htm";
				st.exitCurrentQuest(true);
			}
			else if(st.isCreated())
			{
				if(st.getPlayer().getClassId().getId() != 0x35)
				{
					if(st.getPlayer().getClassId().getId() == 0x38)
						htmltext = "30527-02a.htm";
					else
						htmltext = "30527-02.htm";
					st.exitCurrentQuest(true);
				}
				else if(st.getPlayer().getLevel() < 18)
				{
					htmltext = "30527-03.htm";
					st.exitCurrentQuest(true);
				}
				else
					htmltext = "30527-01.htm";
			}
			else if(cond == 1)
				htmltext = "30527-07.htm";
			else if(cond == 2)
			{
				st.takeItems(BoogleRatmanTooth, -1);
				st.takeItems(BoogleRatmanLeadersTooth, -1);
				st.takeItems(SilverasRing, -1);
				st.giveItems(PassCertificate1st, 1);
				htmltext = "30527-08.htm";
				st.set("cond", "3");
			}
			else if(cond == 3)
				htmltext = "30527-09.htm";
		}
		else if(npcId == Kluto)
		{
			if(cond == 3)
				htmltext = "30317-01.htm";
			else if(cond == 4 || cond == 5)
				htmltext = "30317-08.htm";
			else if(cond == 7)
				htmltext = "30317-09.htm";
		}
		else if(npcId == Pinter)
			if(cond == 4)
				htmltext = "30298-01.htm";
			else if(cond == 5)
				htmltext = "30298-04.htm";
			else if(cond == 6)
				htmltext = "30298-05.htm";
			else if(cond == 7)
				htmltext = "30298-07.htm";
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
		if(cond == 1 && st.getQuestItemsCount(BoogleRatmanTooth) >= 10 && st.getQuestItemsCount(BoogleRatmanLeadersTooth) >= 2)
		{
			st.set("cond", "2");
			st.setState(STARTED);
		}
	}
}