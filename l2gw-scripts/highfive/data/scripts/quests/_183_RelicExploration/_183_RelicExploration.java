package quests._183_RelicExploration;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _183_RelicExploration extends Quest
{
	private static final int Kusto = 30512;
	private static final int Lorain = 30673;
	private static final int Nikola = 30621;

	public _183_RelicExploration()
	{
		super(183, "_183_RelicExploration", "Relic Exploration");

		addStartNpc(Kusto);
		addTalkId(Nikola, Lorain);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		if(npcId == Kusto)
		{
			if(reply == 183 && st.isCreated() && player.getLevel() >= 40)
			{
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				st.setState(STARTED);
				showQuestPage("head_blacksmith_kusto_q0183_04.htm", player);
				st.setCond(1);
				showQuestMark(player);
				return;
			}
			else if(reply == 1)
			{
				showQuestPage("head_blacksmith_kusto_q0183_02.htm", player);
				return;
			}
		}
		else if(npcId == Lorain)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 1)
			{
				showPage("researcher_lorain_q0183_02.htm", player);
				return;
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 1)
			{
				showPage("researcher_lorain_q0183_03.htm", player);
				return;
			}
			else if(reply == 3 && st.isStarted() && st.getMemoState() == 1)
			{
				st.setMemoState(2);
				showPage("researcher_lorain_q0183_04.htm", player);
				st.setCond(2);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
		}
		else if(npcId == Nikola)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("maestro_nikola_q0183_02.htm", player);
					st.playSound(SOUND_FINISH);

					if(player.getLevel() < 46)
						st.addExpAndSp(60000, 3000);

					st.rollAndGive(57, 18100, 100);
					st.setState(COMPLETED);
					st.exitCurrentQuest(false);
					return;
				}
			}
		}
		showPage("noquest", player);
	}


	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();

		if(npcId == Kusto)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 40)
					htmltext = "head_blacksmith_kusto_q0183_01.htm";
				else
					htmltext = "head_blacksmith_kusto_q0183_03.htm";
			}
			else if(st.isStarted())
			{
				htmltext = "npchtm:head_blacksmith_kusto_q0183_05.htm";
			}

		}
		else if(npcId == Lorain)
		{
			if(st.isStarted() && st.getMemoState() == 1)
				htmltext = "npchtm:researcher_lorain_q0183_01.htm";
			else if(st.isStarted() && st.getMemoState() == 2)
				htmltext = "npchtm:researcher_lorain_q0183_05.htm";
		}
		else if(npcId == Nikola)
		{
			if(st.isStarted() && st.getMemoState() == 2)
			{
				htmltext = "npchtm:maestro_nikola_q0183_01.htm";
			}
		}
		return htmltext;
	}
}