package quests._10276_MutatedKaneusGludio;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10276_MutatedKaneusGludio extends Quest
{
	// NPCs
	private static final int Bathis = 30332;
	private static final int Rohmer = 30344;

	// MOBs
	private static final int TomlanKamos = 18554;
	private static final int OlAriosh = 18555;

	// Items
	private static final int Tissue1 = 13830;
	private static final int Tissue2 = 13831;

	public _10276_MutatedKaneusGludio()
	{
		super(10276, "_10276_MutatedKaneusGludio", "Mutated Kaneus Gludio"); // Party true
		addStartNpc(Bathis);
		addTalkId(Rohmer);
		addKillId(TomlanKamos, OlAriosh);
		addQuestItem(Tissue1, Tissue2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30332-03.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30344-02.htm"))
		{
			st.rollAndGive(57, 60000, 100);
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
			if(npcId == Bathis)
				htmltext = "30332-0a.htm";
		}
		else if(st.isCreated() && npcId == Bathis)
		{
			if(st.getPlayer().getLevel() >= 18)
				htmltext = "30332-01.htm";
			else
				htmltext = "30332-00.htm";
		}
		else
		{
			if(npcId == Bathis)
			{
				if(cond == 1)
					htmltext = "30332-04.htm";
				else if(cond == 2)
					htmltext = "30332-05.htm";
			}
			else if(npcId == Rohmer)
			{
				if(cond == 1)
					htmltext = "30344-01a.htm";
				else if(cond == 2)
					htmltext = "30344-01.htm";
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