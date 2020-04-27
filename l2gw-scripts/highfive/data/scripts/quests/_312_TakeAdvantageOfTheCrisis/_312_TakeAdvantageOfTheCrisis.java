package quests._312_TakeAdvantageOfTheCrisis;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 15.08.2010 16:14:34
 */
public class _312_TakeAdvantageOfTheCrisis extends Quest
{
	// NPCs
	private static final int FILAUR = 30535;

	private static final int MINERAL_FRAGMENT = 14875;

	private static final int[] MINE_MOBS = {22678, 22679, 22680, 22681, 22682, 22683, 22684, 22685, 22686, 22687, 22688, 22689, 22690};

	private static final HashMap<Integer, Integer> dropChances = new HashMap<Integer, Integer>();

	static
	{
		dropChances.put(22678, 29);
		dropChances.put(22679, 60);
		dropChances.put(22680, 61);
		dropChances.put(22681, 63);
		dropChances.put(22682, 70);
		dropChances.put(22683, 65);
		dropChances.put(22684, 31);
		dropChances.put(22685, 63);
		dropChances.put(22686, 63);
		dropChances.put(22687, 31);
		dropChances.put(22688, 42);
		dropChances.put(22689, 21);
		dropChances.put(22690, 75);
	}

	public _312_TakeAdvantageOfTheCrisis()
	{
		super(312, "_312_TakeAdvantageOfTheCrisis", "Take Advantage Of The Crisis"); // party = true
		addStartNpc(FILAUR);
		addTalkId(FILAUR);

		addKillId(MINE_MOBS);
		addQuestItem(MINERAL_FRAGMENT);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;
		if(event.equals("30535-06.htm"))
		{
			st.set("cond", 1);
			st.setState(STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if(event.matches("^\\d+$"))
		{
			long f = st.getQuestItemsCount(MINERAL_FRAGMENT);
			int evt = Integer.parseInt(event);
			if(evt == 9487 && f >= 366)
				htmltext = onExchangeRequest(evt, st, 366);
			else if(evt == 9488 && f >= 229)
				htmltext = onExchangeRequest(evt, st, 229);
			else if(evt == 9489 && f >= 183)
				htmltext = onExchangeRequest(evt, st, 183);
			else if(evt == 9490 && f >= 122 || evt == 9491 && f >= 122)
				htmltext = onExchangeRequest(evt, st, 122);
			else if(evt == 9497 && f >= 129)
				htmltext = onExchangeRequest(evt, st, 129);
			else if(evt == 9625 && f >= 667)
				htmltext = onExchangeRequest(evt, st, 667);
			else if(evt == 9626 && f >= 1000)
				htmltext = onExchangeRequest(evt, st, 1000);
			else if(evt == 9628 && f >= 24)
				htmltext = onExchangeRequest(evt, st, 24);
			else if(evt == 9629 && f >= 43)
				htmltext = onExchangeRequest(evt, st, 43);
			else if(evt == 9630 && f >= 36)
				htmltext = onExchangeRequest(evt, st, 36);
			else
				htmltext = "30535-15.htm";
		}
		else if(event.equals("30535-09.htm"))
		{
			st.exitCurrentQuest(true);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		if(npcId == FILAUR)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 80)
					htmltext = "30535-01.htm";
				else
				{
					htmltext = "30535-00.htm";
					st.exitCurrentQuest(true);
				}
			}
			else if(st.isStarted())
			{
				if(st.getQuestItemsCount(MINERAL_FRAGMENT) > 0)
					htmltext = "30535-10.htm";
				else
					htmltext = "30535-07.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(dropChances.containsKey(npc.getNpcId()))
		{
			QuestState st = getRandomPartyMemberWithQuest(killer, 1);
			if(st != null)
			{
				if(st.rollAndGive(MINERAL_FRAGMENT, 1, dropChances.get(npc.getNpcId())))
					st.playSound(SOUND_ITEMGET);
			}
		}
	}

	private String onExchangeRequest(int event, QuestState st, int qty)
	{
		st.takeItems(MINERAL_FRAGMENT, qty);
		st.giveItems(event, 1);
		st.playSound(SOUND_FINISH);
		return "30535-16.htm";
	}
}
