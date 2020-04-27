package quests._10278_MutatedKaneusHeine;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _10278_MutatedKaneusHeine extends Quest
{
	// NPCs
	private static final int Gosta = 30916;
	private static final int Minevia = 30907;

	// MOBs
	private static final int BladeOtis = 18562;
	private static final int WeirdBunei = 18564;

	// Items
	private static final int Tissue1 = 13834;
	private static final int Tissue2 = 13835;

	public _10278_MutatedKaneusHeine()
	{
		super(10278, "_10278_MutatedKaneusHeine", "Mutated Kaneus Heine");
		addStartNpc(Gosta);
		addTalkId(Minevia);
		addKillId(BladeOtis, WeirdBunei);
		addQuestItem(Tissue1, Tissue2);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = event;
		if(event.equalsIgnoreCase("30916-03.htm"))
		{
			st.setCond(1);
			st.setState(STARTED);
			st.playSound(SOUND_ACCEPT);
		}
		else if(event.equalsIgnoreCase("30907-02.htm"))
		{
			st.giveItems(57, 180000);
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
			if(npcId == Gosta)
				htmltext = "30916-0a.htm";
		}
		else if(st.isCreated() && npcId == Gosta)
		{
			if(st.getPlayer().getLevel() >= 38)
				htmltext = "30916-01.htm";
			else
				htmltext = "30916-00.htm";
		}
		else
		{
			if(npcId == Gosta)
			{
				if(cond == 1)
					htmltext = "30916-04.htm";
				else if(cond == 2)
					htmltext = "30916-05.htm";
			}
			else if(npcId == Minevia)
			{
				if(cond == 1)
					htmltext = "30907-01a.htm";
				else if(cond == 2)
					htmltext = "30907-01.htm";
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