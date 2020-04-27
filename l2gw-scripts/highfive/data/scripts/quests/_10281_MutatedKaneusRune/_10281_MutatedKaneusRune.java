package quests._10281_MutatedKaneusRune;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10281_MutatedKaneusRune extends Quest
{
	// NPCs
	private static final int Mathias = 31340;
	private static final int Kayan = 31335;

	// MOBs
	private static final int WhiteAllosce = 18577;

	// Items
	private static final int Tissue = 13840;

	public _10281_MutatedKaneusRune()
	{
		super(10281, "_10281_MutatedKaneusRune", "Mutated Kaneus Rune");
		addStartNpc(Mathias);
		addTalkId(Kayan);
		addKillId(WhiteAllosce);
		addQuestItem(Tissue);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("31340-03.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31335-02.htm"))
		{
			st.giveItems(57, 360000);
			st.exitCurrentQuest(false);
			st.playSound(SOUND_FINISH);
		}
		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		int cond = st.getCond();
		int npcId = npc.getNpcId();
		if(st.isCompleted())
		{
			if(npcId == Mathias)
				htmltext = "31340-0a.htm";
		}
		else if(st.isCreated() && npcId == Mathias)
		{
			if(st.getPlayer().getLevel() >= 68)
				htmltext = "31340-01.htm";
			else
				htmltext = "31340-00.htm";
		}
		else
		{
			if(npcId == Mathias)
			{
				if(cond == 1)
					htmltext = "31340-04.htm";
				else if(cond == 2)
					htmltext = "31340-05.htm";
			}
			else if(npcId == Kayan)
			{
				if(cond == 1)
					htmltext = "31335-01a.htm";
				else if(cond == 2)
					htmltext = "31335-01.htm";
			}
		}
		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithQuest(killer, 1);
		if(st != null && st.isStarted())
		{
			st.giveItems(Tissue, 1);
			st.setCond(2);
			st.playSound(SOUND_MIDDLE);
		}
	}
}