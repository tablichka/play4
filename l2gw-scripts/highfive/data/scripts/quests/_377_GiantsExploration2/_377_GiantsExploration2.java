package quests._377_GiantsExploration2;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _377_GiantsExploration2 extends Quest
{
	// Titan Ancient Books drop rate in %
	private static final int DROP_RATE = 40;

	private static final int TITAN_ANCIENT_BOOK = 14847;
	private static final int BOOK1 = 14842;
	private static final int BOOK2 = 14843;
	private static final int BOOK3 = 14844;
	private static final int BOOK4 = 14845;
	private static final int BOOK5 = 14846;

	// NPCs
	private static final int HR_SOBLING = 31147;

	// Mobs
	private static final int[] MOBS = {22661, 22662, 22663, 22664, 22665, 22666, 22667, 22668, 22669};

	public _377_GiantsExploration2()
	{
		super(377, "_377_GiantsExploration2", "Giants Cave Exploration - Part 2"); // Party true
		addStartNpc(HR_SOBLING);
		addQuestItem(BOOK1, BOOK2, BOOK3, BOOK4, BOOK5);
		addKillId(MOBS);
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
		if(event.equalsIgnoreCase("31147-02.htm"))
		{
			st.setState(STARTED);
			st.set("cond", "1");
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
				if(n == 9625)											// Giant's Codex - Oblivion
					htmltext = onExchangeRequest(n, st, 1, 5);
				else if(n == 9626)										// Giant's Codex - Discipline
					htmltext = onExchangeRequest(n, st, 1, 5);
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
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.rollAndGive(TITAN_ANCIENT_BOOK, 1, DROP_RATE))
			st.playSound(SOUND_ITEMGET);
	}
}