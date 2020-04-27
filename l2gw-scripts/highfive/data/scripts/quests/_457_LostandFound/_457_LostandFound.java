package quests._457_LostandFound;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 14.10.11 23:49
 */
public class _457_LostandFound extends Quest
{
	// NPC
	private static final int lost_villager = 32759;

	// Mobs
	private static final int n_solina_leader = 22789;
	private static final int n_solina_saver = 22790;
	private static final int n_solina_learner = 22791;
	private static final int n_solina_student = 22793;

	public _457_LostandFound()
	{
		super(457, "_457_LostandFound", "Lost and Found");
		addStartNpc(lost_villager);
		addTalkId(lost_villager);
		addKillId(n_solina_leader, n_solina_saver, n_solina_learner, n_solina_student);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == lost_villager)
		{
			if(st.isCreated() && talker.getLevel() >= 82)
			{
				if(npc.i_quest0 == 0)
					return "lost_villager_q0457_01.htm";

				return "lost_villager_q0457_01a.htm";
			}

			if(st.isCompleted())
				return "lost_villager_q0457_02.htm";

			if(st.isCreated() && talker.getLevel() < 82)
				return "lost_villager_q0457_03.htm";

			if(st.isStarted() && st.getMemoState() == 1)
				return "npchtm:lost_villager_q0457_05.htm";
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == lost_villager)
		{
			if(reply == 457)
			{
				if(st.isCreated() && talker.getLevel() >= 82)
				{
					if(npc.i_quest0 == 0)
					{
						npc.l_ai0 = talker.getStoredId();
						npc.i_quest0 = 1;
						st.playSound(SOUND_ACCEPT);
						st.setMemoState(1);
						showQuestPage("lost_villager_q0457_04.htm", talker);
						st.setCond(1);
						st.setState(STARTED);
					}
					else
					{
						showQuestPage("lost_villager_q0457_01a.htm", talker);
					}
				}
			}
			else if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.setMemoState(2);
					showPage("lost_villager_q0457_06.htm", talker);
					npc.notifyAiEvent(npc, CtrlEvent.EVT_SCRIPT_EVENT, 45703, 0, null);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("lost_villager_q0457_07.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(Rnd.chance(1) && killer.getLevel() >= 82 && !killer.isQuestComplete(457) && !killer.isQuestStarted(457))
		{
			addSpawn(lost_villager, npc.getLoc());
		}
	}
}
