package quests._10279_MutatedKaneusOren;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10279_MutatedKaneusOren extends Quest
{
	// NPCs
	private static final int Mouen = 30196;
	private static final int Rovia = 30189;

	// MOBs
	private static final int KaimAbigore = 18566;
	private static final int KnightMontagnar = 18568;

	// Items
	private static final int Tissue1 = 13836;
	private static final int Tissue2 = 13837;

	public _10279_MutatedKaneusOren()
	{
		super(10279, "_10279_MutatedKaneusOren", "Mutated Kaneus Oren");
		addStartNpc(Mouen);
		addTalkId(Rovia);
		addKillId(KaimAbigore, KnightMontagnar);
		addQuestItem(Tissue1, Tissue2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30196-03.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30189-02.htm"))
		{
			st.rollAndGive(57, 240000, 100);
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
			if(npcId == Mouen)
				htmltext = "30196-0a.htm";
		}
		else if(st.isCreated() && npcId == Mouen)
		{
			if(st.getPlayer().getLevel() >= 48)
				htmltext = "30196-01.htm";
			else
				htmltext = "30196-00.htm";
		}
		else
		{
			if(npcId == Mouen)
			{
				if(cond == 1)
					htmltext = "30196-04.htm";
				else if(cond == 2)
					htmltext = "30196-05.htm";
			}
			else if(npcId == Rovia)
			{
				if(cond == 1)
					htmltext = "30189-01a.htm";
				else if(cond == 2)
					htmltext = "30189-01.htm";
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