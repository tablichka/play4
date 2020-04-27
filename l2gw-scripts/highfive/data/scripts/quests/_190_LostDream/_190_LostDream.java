package quests._190_LostDream;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 24.12.2010 16:29
 */
public class _190_LostDream extends Quest
{
	// NPCs
	private static final int Kusto = 30512;
	private static final int Nikola = 30621;
	private static final int Lorain = 30673;
	private static final int Juris = 30113;

	public _190_LostDream()
	{
		super(190, "_190_LostDream", "Lost Dream");
		addStartNpc(Kusto);
		addTalkId(Kusto, Lorain, Nikola, Juris);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		if(st.isCompleted())
		{
			showPage("completed", player);
			return;
		}

		int npcId = player.getLastNpc().getNpcId();

		if(npcId == Kusto)
		{
			if(reply == 190 && st.isCreated() && player.getLevel() >= 42 && player.isQuestComplete(187))
			{
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("head_blacksmith_kusto_q0190_03.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
			else if(st.isStarted() && reply == 1 && st.getMemoState() == 2)
			{
				st.setMemoState(3);
				showPage("head_blacksmith_kusto_q0190_06.htm", player);
				st.setCond(3);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Juris && st.getMemoState() == 1)
			{
				if(reply == 1)
					showPage("juria_q0190_02.htm", player);
				else if(reply == 2)
				{
					st.setMemoState(2);
					showPage("juria_q0190_03.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}


	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == Kusto)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().isQuestComplete(187))
				{
					if(st.getPlayer().getLevel() >= 42)
						return "head_blacksmith_kusto_q0190_01.htm";

					st.exitCurrentQuest(true);
					return "head_blacksmith_kusto_q0190_02.htm";
				}
				st.exitCurrentQuest(true);
			}
			else if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:head_blacksmith_kusto_q0190_04.htm";
				if(cond == 2)
					return "npchtm:head_blacksmith_kusto_q0190_05.htm";
				if(cond >= 3 && cond <= 4)
					return "npchtm:head_blacksmith_kusto_q0190_07.htm";
				if(cond == 5)
				{
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 109427, 100);
					if(st.getPlayer().getLevel() < 48)
						st.addExpAndSp(309467, 20614);
					return "npchtm:head_blacksmith_kusto_q0190_08.htm";
				}
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Juris)
			{
				if(cond == 1)
					return "npchtm:juria_q0190_01.htm";
				if(cond == 2)
					return "npchtm:juria_q0190_04.htm";
			}
			else if(npcId == Lorain)
			{
				if(cond == 3)
				{
					st.setMemoState(4);
					st.setCond(4);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:researcher_lorain_q0190_01.htm";
				}
				if(cond == 4)
					return "npchtm:researcher_lorain_q0190_02.htm";
			}
			else if(npcId == Nikola)
			{
				if(cond == 4)
				{
					st.setMemoState(5);
					st.setCond(5);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:maestro_nikola_q0190_01.htm";
				}
				if(cond == 5)
					return "npchtm:maestro_nikola_q0190_02.htm";
			}
		}
		return "noquest";
	}
}
