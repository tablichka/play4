package quests._338_AlligatorHunter;

import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Alligator Hunter
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _338_AlligatorHunter extends Quest
{
	//NPC
	private static final int Enverun = 30892;
	//QuestItems
	private static final int AlligatorLeather = 4337;
	//Item
	private static final int Adena = 57;
	//MOB
	private static final int CrokianLad = 20804;
	private static final int DailaonLad = 20805;
	private static final int CrokianLadWarrior = 20806;
	private static final int FarhiteLad = 20807;
	private static final int NosLad = 20808;
	private static final int SwampTribe = 20991;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	public final int[][] DROPLIST_COND = {
			{1, 0, CrokianLad, 0, AlligatorLeather, 0, 60, 1},
			{1, 0, DailaonLad, 0, AlligatorLeather, 0, 60, 1},
			{1, 0, CrokianLadWarrior, 0, AlligatorLeather, 0, 60, 1},
			{1, 0, FarhiteLad, 0, AlligatorLeather, 0, 60, 1},
			{1, 0, NosLad, 0, AlligatorLeather, 0, 60, 1},
			{1, 0, SwampTribe, 0, AlligatorLeather, 0, 60, 1}};

	public _338_AlligatorHunter()
	{
		super(338, "_338_AlligatorHunter", "Alligator Hunter");
		addStartNpc(Enverun);
		//Mob Drop
		for(int[] cond : DROPLIST_COND)
			addKillId(cond[2]);
		addQuestItem(AlligatorLeather);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30892-02.htm"))
		{
			st.playSound(SOUND_ACCEPT);
			st.set("cond", "1");
			st.setState(STARTED);
		}
		else if(event.equalsIgnoreCase("30892-02-afmenu.htm"))
		{
			long AdenaCount = st.getQuestItemsCount(AlligatorLeather) * 40;
			st.takeItems(AlligatorLeather, -1);
			st.rollAndGive(Adena, AdenaCount, 100);
		}
		else if(event.equalsIgnoreCase("quit"))
		{
			if(st.getQuestItemsCount(AlligatorLeather) >= 1)
			{
				long AdenaCount = st.getQuestItemsCount(AlligatorLeather) * 40;
				st.takeItems(AlligatorLeather, -1);
				st.rollAndGive(Adena, AdenaCount, 100);
				htmltext = "30892-havequit.htm";
			}
			else
				htmltext = "30892-havent.htm";
			st.playSound(SOUND_FINISH);
			st.exitCurrentQuest(true);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "<html><body>I have nothing to say you</body></html>";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Enverun)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 40)
					htmltext = "30892-01.htm";
				else
				{
					htmltext = "30892-01.htm";
					st.exitCurrentQuest(true);
				}
			}
			else
			{
				if(st.getQuestItemsCount(AlligatorLeather) == 0)
					htmltext = "30892-02-rep.htm";
				else
					htmltext = "30892-menu.htm";
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