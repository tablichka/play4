package quests._187_NikolasHeart;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 24.12.2010 15:22
 */
public class _187_NikolasHeart extends Quest
{
	// NPCs
	private static final int Kusto = 30512;
	private static final int Nikola = 30621;
	private static final int Lorain = 30673;

	// Items
	private static final int Certificate = 10362;
	private static final int Metal = 10368;

	public _187_NikolasHeart()
	{
		super(187, "_187_NikolasHeart", "Nikolas Heart");
		addStartNpc(Lorain);
		addTalkId(Nikola, Kusto);
		addQuestItem(Certificate, Metal);
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

		if(npcId == Lorain)
		{
			if(reply == 187 && st.isCreated() && player.getLevel() >= 41 && player.isQuestComplete(185))
			{
				st.giveItems(Metal, 1);
				st.takeItems(Certificate, -1);
				st.setMemoState(1);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("researcher_lorain_q0187_03.htm", player);
				st.setCond(1);
				st.setState(STARTED);
			}
		}
		else if(st.isStarted())
		{
			if(npcId == Nikola && st.getMemoState() == 1)
			{
				if(reply == 1)
					showPage("maestro_nikola_q0187_02.htm", player);
				else if(reply == 2)
				{
					st.setMemoState(2);
					showPage("maestro_nikola_q0187_03.htm", player);
					st.setCond(2);
					showQuestMark(player);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(npcId == Kusto && st.getMemoState() == 2)
			{
				if(reply == 1)
					showPage("head_blacksmith_kusto_q0187_02.htm", player);
				else if(reply == 2)
				{
					st.takeItems(Metal, -1);
					showPage("head_blacksmith_kusto_q0187_03.htm", player);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					st.rollAndGive(57, 93383, 100);
					if(player.getLevel() < 47)
						st.addExpAndSp(285935, 18711);
				}
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "completed";
		String htmltext = "noquest";
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == Lorain)
		{
			if(st.isCreated() && st.haveQuestItems(Certificate) && st.getPlayer().isQuestComplete(185))
			{
				if(st.getPlayer().getLevel() >= 41)
					return "researcher_lorain_q0187_01.htm";
				return "researcher_lorain_q0187_02.htm";
			}
			else if(st.isStarted() && cond >= 1)
				return "npchtm:researcher_lorain_q0187_04.htm";
		}
		else if(st.isStarted())
		{
			if(npcId == Nikola)
			{
				if(cond == 1)
					return "npchtm:maestro_nikola_q0187_01.htm";
				if(cond == 2)
					return "npchtm:maestro_nikola_q0187_04.htm";
			}
			else if(npcId == Kusto && cond == 2)
				return "npchtm:head_blacksmith_kusto_q0187_01.htm";
		}
		return htmltext;
	}
}
