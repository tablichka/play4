package quests._10277_MutatedKaneusDion;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10277_MutatedKaneusDion extends Quest
{
	// NPCs
	private static final int Lucas = 30071;
	private static final int Mirien = 30461;

	// MOBs
	private static final int CrimsonHatuOtis = 18558;
	private static final int SeerFlouros = 18559;

	// Items
	private static final int Tissue1 = 13832;
	private static final int Tissue2 = 13833;

	public _10277_MutatedKaneusDion()
	{
		super(10277, "_10277_MutatedKaneusDion", "Mutated Kaneus Dion");
		addStartNpc(Lucas);
		addTalkId(Mirien);
		addKillId(CrimsonHatuOtis, SeerFlouros);
		addQuestItem(Tissue1, Tissue2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30071-03.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30461-02.htm"))
		{
			st.rollAndGive(57, 120000, 100);
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
			if(npcId == Lucas)
				htmltext = "30071-0a.htm";
		}
		else if(st.isCreated() && npcId == Lucas)
		{
			if(st.getPlayer().getLevel() >= 28)
				htmltext = "30071-01.htm";
			else
				htmltext = "30071-00.htm";
		}
		else
		{
			if(npcId == Lucas)
			{
				if(cond == 1)
					htmltext = "30071-04.htm";
				else if(cond == 2)
					htmltext = "30071-05.htm";
			}
			else if(npcId == Mirien)
			{
				if(cond == 1)
					htmltext = "30461-01a.htm";
				else if(cond == 2)
					htmltext = "30461-01.htm";
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
			st.giveItems(Tissue1, 1);
			st.giveItems(Tissue2, 1);
			st.setCond(2);
			st.playSound(SOUND_MIDDLE);
		}
	}
}