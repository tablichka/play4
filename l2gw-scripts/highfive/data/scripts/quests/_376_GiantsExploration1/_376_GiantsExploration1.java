package quests._376_GiantsExploration1;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _376_GiantsExploration1 extends Quest
{
	// Ancient parchment drop rate in %
	private static final int DROP_RATE = 15;

	private static final int ANCIENT_PARCHMENT = 14841;
	private static final int BOOK1 = 14836;
	private static final int BOOK2 = 14837;
	private static final int BOOK3 = 14838;
	private static final int BOOK4 = 14839;
	private static final int BOOK5 = 14840;

	private static final int[] MOBS = {22670, 22671, 22672, 22673, 22674, 22675, 22676, 22677};

	// NPCs
	private static final int HR_SOBLING = 31147;

	public _376_GiantsExploration1()
	{
		super(376, "_376_GiantsExploration1", "Giants Cave Exploration - Part 1"); // Party true
		addStartNpc(HR_SOBLING);
		addKillId(MOBS);
		addQuestItem(BOOK1, BOOK2, BOOK3, BOOK4, BOOK5);
	}

	public String onExchangeRequest(int event, QuestState st, int qty, int rem)
	{
		if(st.getQuestItemsCount(BOOK1) >= rem && st.getQuestItemsCount(BOOK2) >= rem && st.getQuestItemsCount(BOOK3) >= rem &&
				st.getQuestItemsCount(BOOK4) >= rem && st.getQuestItemsCount(BOOK5) >= rem)
		{
			st.takeItems(BOOK1, rem);
			st.takeItems(BOOK2, rem);
			st.takeItems(BOOK3, rem);
			st.takeItems(BOOK4, rem);
			st.takeItems(BOOK5, rem);
			st.giveItems(event, qty);
			st.playSound(SOUND_FINISH);
			return "31147-ok.htm";
		}
		else
			return "31147-no.htm";

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		int cond = st.getInt("cond");
		if(event.equalsIgnoreCase("31147-02.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31147-quit.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		else if(event.startsWith("exch"))
		{
			String num = event.substring(4, event.length());
			try
			{
				int n = Integer.valueOf(num);
				if(n == 9967)											// Recipe - Dynasty Sword (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9968)										// Recipe - Dynasty Blade (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9969)										// Recipe - Dynasty Phantom (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9970)										// Recipe - Dynasty Bow (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9971)										// Recipe - Dynasty Knife (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9972)										// Recipe - Dynasty Halberd (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9973)										// Recipe - Dynasty Cudgel (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9974)										// Recipe - Dynasty Mace (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9975)										// Recipe - Dynasty Bagh-Nakh (60%)
					htmltext = onExchangeRequest(n, st, 1, 10);
				else if(n == 9628)										// Leonard
					htmltext = onExchangeRequest(n, st, 6, 1);
				else if(n == 9629)										// Adamantine
					htmltext = onExchangeRequest(n, st, 3, 1);
				else if(n == 9630)										// Orichalcum
					htmltext = onExchangeRequest(n, st, 4, 1);

			}
			catch(NumberFormatException e)
			{
				_log.info(getName() + " Illegal Number Format Exception: " + num);
			}
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		if(npcId == HR_SOBLING)
		{
			if(st.isStarted())
			{
				if(st.getQuestItemsCount(BOOK1) > 0 && st.getQuestItemsCount(BOOK2) > 0 && st.getQuestItemsCount(BOOK3) > 0 &&
						st.getQuestItemsCount(BOOK4) > 0 && st.getQuestItemsCount(BOOK5) > 0)
				{
					htmltext = "31147-03.htm";
				}
				else
				{
					htmltext = "31147-02a.htm";
				}
			}
			else
			{
				if(st.getPlayer().getLevel() >= 79)
				{
					htmltext = "31147-01.htm";
				}
				else
				{
					htmltext = "31147-00.htm";
				}
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = null;
		for(int i = 1; i <= 3; i++)
		{
			st = getRandomPartyMemberWithQuest(killer, i);
			if(st != null)
				break;
		}

		if(st != null)
		{
			if(st.rollAndGive(ANCIENT_PARCHMENT, 1, DROP_RATE))
				st.playSound(SOUND_ITEMGET);
		}
	}
}