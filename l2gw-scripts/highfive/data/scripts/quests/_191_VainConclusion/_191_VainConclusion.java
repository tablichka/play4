package quests._191_VainConclusion;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

public class _191_VainConclusion extends Quest
{
	// NPCs
	private static final int Kusto = 30512;
	private static final int Dorothy = 30970;
	private static final int Lorain = 30673;
	private static final int Shegfield = 30068;

	// Items
	private static final int Metal = 10371;

	public _191_VainConclusion()
	{
		super(191, "_191_VainConclusion", "Vain Conclusion");

		addStartNpc(Dorothy);
		addTalkId(Kusto);
		addTalkId(Lorain);
		addTalkId(Shegfield);

		addQuestItem(Metal);
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
		if(npcId == Dorothy)
		{
			if(reply == 191 && st.isCreated() && player.getLevel() >= 42 && player.isQuestComplete(188))
			{
				st.giveItems(Metal, 1);
				st.setMemoState(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("dorothy_the_locksmith_q0191_04.htm", player);
				st.setCond(1);
				showQuestMark(player);
				return;
			}
			else if(reply == 1 && st.isCreated() && player.getLevel() >= 42 && player.isQuestComplete(188))
			{
				showQuestPage("dorothy_the_locksmith_q0191_03.htm", player);
				return;
			}
		}
		else if(npcId == Kusto)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 4)
			{
				st.playSound(SOUND_FINISH);
				st.setState(COMPLETED);
				showPage("head_blacksmith_kusto_q0191_02.htm", player);

				if(player.getLevel() < 48)
				{
					st.addExpAndSp(309467, 20614);
				}
				st.rollAndGive(57, 117327, 100);
				st.exitCurrentQuest(false);
				return;
			}
		}
		else if(npcId == Lorain)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 1)
			{
				st.takeItems(Metal, -1);
				st.setMemoState(2);
				showPage("researcher_lorain_q0191_02.htm", player);
				st.setCond(2);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
		}
		else if(npcId == Shegfield)
		{
			if(reply == 1 && st.isStarted() && st.getMemoState() == 2)
			{
				showPage("shegfield_q0191_02.htm", player);
				return;
			}
			else if(reply == 2 && st.isStarted() && st.getMemoState() == 2)
			{
				st.setMemoState(3);
				showPage("shegfield_q0191_03.htm", player);
				st.setCond(3);
				showQuestMark(player);
				st.playSound(SOUND_MIDDLE);
				return;
			}
		}
		showPage("noquest", player);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:completed";

		String htmltext = "noquest";
		int npcId = npc.getNpcId();

		if(npcId == Dorothy)
		{
			if(st.isCreated() && st.getPlayer().isQuestComplete(188))
			{
				if(st.getPlayer().getLevel() >= 42)
					htmltext = "dorothy_the_locksmith_q0191_01.htm";
				else
					htmltext = "dorothy_the_locksmith_q0191_02.htm";
			}
			else if(st.isStarted() && st.getMemoState() >= 1)
			{
				htmltext = "npchtm:dorothy_the_locksmith_q0191_05.htm";
			}
		}
		else if(npcId == Kusto)
		{
			if(st.isStarted() && st.getMemoState() == 4)
			{
				htmltext = "npchtm:head_blacksmith_kusto_q0191_01.htm";
			}
		}
		else if(npcId == Lorain)
		{
			if(st.isStarted() && st.getMemoState() == 1)
			{
				htmltext = "npchtm:researcher_lorain_q0191_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 2)
			{
				htmltext = "npchtm:researcher_lorain_q0191_03.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 3)
			{
				st.setMemoState(4);
				st.setCond(4);
				showQuestMark(st.getPlayer());
				st.playSound(SOUND_MIDDLE);
				htmltext = "npchtm:researcher_lorain_q0191_04.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 4)
			{
				htmltext = "npchtm:researcher_lorain_q0191_05.htm";
			}
		}
		else if(npcId == Shegfield)
		{
			if(st.isStarted() && st.getMemoState() == 2)
			{
				htmltext = "npchtm:shegfield_q0191_01.htm";
			}
			else if(st.isStarted() && st.getMemoState() == 3)
			{
				htmltext = "npchtm:shegfield_q0191_04.htm";
			}
		}
		return htmltext;
	}
}

