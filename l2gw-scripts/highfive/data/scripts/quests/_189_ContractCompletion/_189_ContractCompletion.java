package quests._189_ContractCompletion;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 24.12.2010 15:58
 */
public class _189_ContractCompletion extends Quest
{
	// NPCs
	private static final int Kusto = 30512;
	private static final int Lorain = 30673;
	private static final int Luka = 31437;
	private static final int Shegfield = 30068;

	// Items
	private static final int Metal = 10370;

	public _189_ContractCompletion()
	{
		super(189, "_189_ContractCompletion", "Contract Completion");

		addStartNpc(Luka);
		addTalkId(Kusto, Lorain, Luka, Shegfield);
		addQuestItem(Metal);
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

		if(npcId == Luka)
		{
			if(reply == 189 && st.isCreated() && player.getLevel() >= 42 && player.isQuestComplete(186))
			{
				st.giveItems(Metal, 1);
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("blueprint_seller_luka_q0189_03.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Lorain)
			{
				if(reply == 1 && st.getMemoState() == 1)
				{
					st.takeItems(Metal, -1);
					st.setMemoState(2);
					showPage("researcher_lorain_q0189_02.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(npcId == Shegfield && st.getMemoState() == 2)
			{
				if(reply == 1)
					showPage("shegfield_q0189_02.htm", player);
				else if(reply == 2)
				{
					st.setMemoState(3);
					showPage("shegfield_q0189_03.htm", player);
					st.setCond(3);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(npcId == Kusto && st.getMemoState() == 4)
			{
				if(reply == 1)
				{
					showPage("head_blacksmith_kusto_q0189_02.htm", player);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 121527, 100);
					if(player.getLevel() < 48)
						st.addExpAndSp(309467, 20614);
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

		if(npcId == Luka)
		{
			if(st.isCreated() && st.getPlayer().isQuestComplete(186))
			{
				if(st.getPlayer().getLevel() >= 42)
					return "blueprint_seller_luka_q0189_01.htm";

				st.exitCurrentQuest(true);
				return "blueprint_seller_luka_q0189_02.htm";
			}
			else if(st.isStarted() && cond >= 1)
				return "npchtm:blueprint_seller_luka_q0189_04.htm";
		}
		if(st.isStarted())
		{
			if(npcId == Lorain)
			{
				if(cond == 1)
					return "npchtm:researcher_lorain_q0189_01.htm";
				if(cond == 2)
					return "npchtm:researcher_lorain_q0189_03.htm";
				if(cond == 3)
				{
					st.setMemoState(4);
					st.setCond(4);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					return "npchtm:researcher_lorain_q0189_04.htm";
				}
				if(cond == 4)
					return "npchtm:researcher_lorain_q0189_05.htm";
			}
			else if(npcId == Shegfield)
			{
				if(cond == 2)
					return "npchtm:shegfield_q0189_01.htm";
				if(cond == 3)
					return "npchtm:shegfield_q0189_04.htm";
			}
			else if(npcId == Kusto)
				if(cond == 4)
					return "npchtm:head_blacksmith_kusto_q0189_01.htm";
		}
		return "noquest";
	}

	@Override
	public String onFirstTalk(L2NpcInstance npc, L2Player player)
	{
		QuestState st = player.getQuestState("_189_ContractCompletion");
		QuestState qs = player.getQuestState("_186_ContractExecution");
		if(st == null && qs != null && qs.isCompleted())
		{
			st = newQuestState(player);
			st.setState(STARTED);
		}
		npc.showChatWindow(player, 0);
		return null;
	}
}

