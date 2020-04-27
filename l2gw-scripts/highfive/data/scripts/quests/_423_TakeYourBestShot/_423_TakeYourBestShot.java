package quests._423_TakeYourBestShot;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author rage
 * @date 06.02.11 14:15
 */
public class _423_TakeYourBestShot extends Quest
{
	// NPCs
	private static final int johny = 32744;
	private static final int batracos = 32740;

	// Items
	private static final int q_approval_of_johny = 15496;

	// Mobs
	private static final int tantaar_lizard_protecter = 18862;

	public _423_TakeYourBestShot()
	{
		super(423, "_423_TakeYourBestShot", "Take Your Best Shot");

		addStartNpc(johny);
		addTalkId(johny, batracos);
		addKillId(tantaar_lizard_protecter);
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player player = st.getPlayer();
		int npcId = player.getLastNpc().getNpcId();

		if(npcId == johny)
		{
			if(st.isCreated())
			{
				if(reply == 423 && player.getLevel() >= 82)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("johny_q0423_06.htm", player);
				}
				else if(reply == 1 && !st.haveQuestItems(q_approval_of_johny) && player.getLevel() >= 82)
					showPage("johny_q0423_04.htm", player);
				else if(reply == 2 && !st.haveQuestItems(q_approval_of_johny) && player.getLevel() >= 82)
					showPage("johny_q0423_05.htm", player);
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		int npcId = npc.getNpcId();
		int cond = st.getMemoState();

		if(npcId == johny)
		{
			if(st.isCreated())
			{
				if(st.getPlayer().getLevel() < 82 || !st.getPlayer().isQuestComplete(249))
				{
					st.exitCurrentQuest(true);
					return "johny_q0423_01.htm";
				}

				if(st.getQuestItemsCount(q_approval_of_johny) >= 1)
					return "johny_q0423_02.htm";

				return "johny_q0423_03.htm";
			}
			if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:johny_q0423_08.htm";
				if(cond == 2)
					return "npchtm:johny_q0423_09.htm";
			}
		}
		else if(npcId == batracos)
		{
			if(st.isCreated() && !st.haveQuestItems(q_approval_of_johny))
				return "npchtm:batracos_q0423_01.htm";
			else if(st.isStarted())
			{
				if(cond == 1)
					return "npchtm:batracos_q0423_02.htm";
				if(cond == 2)
				{
					st.giveItems(q_approval_of_johny, 1);
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:batracos_q0423_03.htm";
				}
			}
			if(st.haveQuestItems(q_approval_of_johny))
				return "npchtm:batracos_q0423_04.htm";
		}

		return "noquest";
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState qs)
	{
		if(!qs.haveQuestItems(q_approval_of_johny) && qs.getMemoState() == 1)
		{
			qs.setMemoState(2);
			qs.setCond(2);
			showQuestMark(qs.getPlayer());
			qs.playSound(SOUND_MIDDLE);
		}
	}
}
