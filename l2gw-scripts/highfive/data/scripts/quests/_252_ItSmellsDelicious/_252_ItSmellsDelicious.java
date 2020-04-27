package quests._252_ItSmellsDelicious;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 04.02.11 19:00
 */
public class _252_ItSmellsDelicious extends Quest
{
	// NPCs
	private static final int stan = 30200;

	// Items
	private static final int q_diary_of_xel = 15500;
	private static final int q_cooknote_piece = 15501;

	public _252_ItSmellsDelicious()
	{
		super(252, "_252_ItSmellsDelicious", "It Smells Delicious!");

		addStartNpc(stan);
		addTalkId(stan);
		addKillId(18908, 22786, 22787, 22788);
		addQuestItem(q_diary_of_xel, q_cooknote_piece);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		if(st.isCompleted())
		{
			showPage("cute_harry_q0250_12.htm", st.getPlayer());
			return;
		}

		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == stan)
		{
			if(st.isCreated() && player.getLevel() >= 82)
			{
				if(reply == 252)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("stan_q0252_05.htm", player);
				}
				else if(reply == 1)
					showQuestPage("stan_q0252_04.htm", player);
			}
			else if(st.isStarted() && reply == 2 && st.getQuestItemsCount(q_diary_of_xel) >= 10 && st.getQuestItemsCount(q_cooknote_piece) >= 5)
			{
				st.rollAndGive(57, 147656, 100);
				st.addExpAndSp(716238, 78324);
				st.takeItems(q_diary_of_xel, -1);
				st.takeItems(q_cooknote_piece, -1);
				st.exitCurrentQuest(false);
				st.playSound(SOUND_FINISH);
				showPage("stan_q0252_08.htm", player);
			}
		}
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st.isCompleted())
			return "npchtm:stan_q0252_03.htm";

		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == stan)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() >= 82)
					return "stan_q0252_01.htm";

				st.exitCurrentQuest(true);
				return "stan_q0252_02.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
				{
					if(st.getQuestItemsCount(q_diary_of_xel) < 10 || st.getQuestItemsCount(q_cooknote_piece) < 5)
						return "npchtm:stan_q0252_06.htm";

					return "npchtm:stan_q0252_07.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		int npcId = npc.getNpcId();
		if(npcId >= 22786 && npcId <= 22788)
		{
			if(npc.i_quest0 == 1)
			{
				QuestState qs = getRandomPartyMemberWithQuest(killer, 1);
				if(qs != null && qs.rollAndGiveLimited(q_diary_of_xel, 1, 59.9, 10))
					if(qs.getQuestItemsCount(q_diary_of_xel) >= 10 && qs.getQuestItemsCount(q_cooknote_piece) >= 5)
					{
						qs.setCond(2);
						showQuestMark(qs.getPlayer());
						qs.playSound(SOUND_MIDDLE);
					}
					else
						qs.playSound(SOUND_ITEMGET);
			}
		}
		else if(npcId == 18908)
		{
			QuestState qs = killer.getQuestState(getName());
			if(qs != null && qs.getCond() == 1 && qs.rollAndGiveLimited(q_cooknote_piece, 1, 36, 5))
				if(qs.getQuestItemsCount(q_diary_of_xel) >= 10 && qs.getQuestItemsCount(q_cooknote_piece) >= 5)
				{
					qs.setCond(2);
					showQuestMark(qs.getPlayer());
					qs.playSound(SOUND_MIDDLE);
				}
				else
					qs.playSound(SOUND_ITEMGET);
		}
	}
}