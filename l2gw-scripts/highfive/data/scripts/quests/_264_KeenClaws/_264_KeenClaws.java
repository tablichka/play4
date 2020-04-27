package quests._264_KeenClaws;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * Квест Keen Claws
 *
 * @author Sergey Ibryaev aka Artful
 */

public class _264_KeenClaws extends Quest
{
	//NPC
	private static final int Payne = 30136;
	//Quest Items
	private static final int WolfClaw = 1367;
	//Items
	private static final int LeatherSandals = 36;
	private static final int Adena = 57;
	private static final int WoodenHelmet = 43;
	private static final int Stockings = 462;
	private static final int HealingPotion = 1061;
	private static final int ShortGloves = 48;
	private static final int ClothShoes = 35;
	//MOB
	private static final int Goblin = 20003;
	private static final int AshenWolf = 20456;
	//Drop Cond
	//# [COND, NEWCOND, ID, REQUIRED, ITEM, NEED_COUNT, CHANCE, DROP]	
	private static final int[][] DROPLIST_COND = {{1, 2, Goblin, 0, WolfClaw, 50, 50, 2}, {1, 2, AshenWolf, 0, WolfClaw, 50, 50, 2}};

	public _264_KeenClaws()
	{
		super(264, "_264_KeenClaws", "Keen Claws");

		addStartNpc(Payne);

		addKillId(Goblin);
		addKillId(AshenWolf);

		addQuestItem(WolfClaw);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equalsIgnoreCase("30136-03.htm"))
		{
			st.set("cond", "1");
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == Payne)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 3)
					htmltext = "30136-02.htm";
				else
				{
					st.exitCurrentQuest(true);
					return "30136-01.htm";
				}
			}
			else if(cond == 1)
				htmltext = "30136-04.htm";
			else if(cond == 2)
			{
				st.takeItems(WolfClaw, -1);
				int n = Rnd.get(17);
				if(n == 0)
				{
					st.giveItems(WoodenHelmet, 1);
					st.playSound(SOUND_JACKPOT);
				}
				else if(n < 2)
					st.giveItems(Adena, 1000);
				else if(n < 5)
					st.giveItems(LeatherSandals, 1);
				else if(n < 8)
				{
					st.giveItems(Stockings, 1);
					st.rollAndGive(Adena, 50, 100);
				}
				else if(n < 11)
					st.rollAndGive(HealingPotion, 1, 100);
				else if(n < 14)
					st.giveItems(ShortGloves, 1);
				else
					st.giveItems(ClothShoes, 1);
				htmltext = "30136-05.htm";
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