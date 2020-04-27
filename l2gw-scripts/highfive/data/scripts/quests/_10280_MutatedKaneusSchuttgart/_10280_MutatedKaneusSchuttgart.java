package quests._10280_MutatedKaneusSchuttgart;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10280_MutatedKaneusSchuttgart extends Quest
{
	// NPCs
	private static final int Vishotsky = 31981;
	private static final int Atraxia = 31972;

	// MOBs
	private static final int VenomousStorace = 18571;
	private static final int KelBilette = 18573;

	// Items
	private static final int Tissue1 = 13838;
	private static final int Tissue2 = 13839;

	public _10280_MutatedKaneusSchuttgart()
	{
		super(10280, "_10280_MutatedKaneusSchuttgart", "Mutated Kaneus Schuttgart");
		addStartNpc(Vishotsky);
		addTalkId(Atraxia);
		addKillId(VenomousStorace, KelBilette);
		addQuestItem(Tissue1, Tissue2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("31981-03.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("31972-02.htm"))
		{
			st.giveItems(57, 300000);
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
			if(npcId == Vishotsky)
				htmltext = "31981-0a.htm";
		}
		else if(st.isCreated() && npcId == Vishotsky)
		{
			if(st.getPlayer().getLevel() >= 58)
				htmltext = "31981-01.htm";
			else
				htmltext = "31981-00.htm";
		}
		else
		{
			if(npcId == Vishotsky)
			{
				if(cond == 1)
					htmltext = "31981-04.htm";
				else if(cond == 2)
					htmltext = "31981-05.htm";
			}
			else if(npcId == Atraxia)
			{
				if(cond == 1)
					htmltext = "31972-01a.htm";
				else if(cond == 2)
					htmltext = "31972-01.htm";
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