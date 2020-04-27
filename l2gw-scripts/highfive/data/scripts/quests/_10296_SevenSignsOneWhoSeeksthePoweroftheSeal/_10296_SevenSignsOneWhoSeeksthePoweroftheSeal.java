package quests._10296_SevenSignsOneWhoSeeksthePoweroftheSeal;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 07.10.11 18:55
 */
public class _10296_SevenSignsOneWhoSeeksthePoweroftheSeal extends Quest
{
	// NPC
	private static final int ssq2_eris_silence = 32792;
	private static final int ssq2_elcardia1_silence = 32787;
	private static final int ssq2_cl_tel_silence = 32815;
	private static final int priest_wood = 32593;
	private static final int ssq2_elcardia_home1 = 32784;
	private static final int hardin = 30832;
	private static final int inzone_frantz = 32597;

	// Items
	private static final int q10296_ssq2_reward_token = 17265;

	public _10296_SevenSignsOneWhoSeeksthePoweroftheSeal()
	{
		super(10296, "_10296_SevenSignsOneWhoSeeksthePoweroftheSeal", "Seven Signs, One Who Seeks the Power of the Seal");
		addStartNpc(ssq2_eris_silence);
		addStartNpc(ssq2_cl_tel_silence);
		addTalkId(ssq2_eris_silence, ssq2_elcardia1_silence, ssq2_cl_tel_silence);
		addTalkId(priest_wood, ssq2_elcardia_home1, hardin, inzone_frantz);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == ssq2_eris_silence)
		{
			if(st.isCreated() && talker.isQuestComplete(10295) && talker.getLevel() >= 81)
				return "ssq2_eris_silence_q10296_01.htm";
			if(st.isCompleted())
				return "npchtm:ssq2_eris_silence_q10296_02.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					st.setMemoState(2);
					st.setCond(2);
					st.playSound(SOUND_MIDDLE);
					showQuestMark(talker);
					return "npchtm:ssq2_eris_silence_q10296_05.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:ssq2_eris_silence_q10296_06.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia1_silence)
		{
			if(talker.isQuestComplete(10295) && talker.getLevel() >= 81 && st.getMemoState() < 1)
				return "npchtm:ssq2_elcardia1_silence_q10296_01.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() < 2)
					return "npchtm:ssq2_elcardia1_silence_q10296_02.htm";
				if(st.getMemoState() == 2)
					return "npchtm:ssq2_elcardia1_silence_q10296_03.htm";
				if(st.getMemoState() > 2)
				{
					st.setCond(3);
					st.playSound(SOUND_MIDDLE);
					showQuestMark(talker);
					return "npchtm:ssq2_elcardia1_silence_q10296_04.htm";
				}
			}
		}
		else if(npc.getNpcId() == ssq2_cl_tel_silence)
		{
			if(st.isCreated() && talker.isQuestComplete(10295))
				return "npchtm:ssq2_cl_tel_silence_q10296_01.htm";
			if(st.isStarted())
			{
				if(st.getMemoState() > 0 && st.getMemoState() < 3)
					return "npchtm:ssq2_cl_tel_silence_q10296_02.htm";
				if(st.getMemoState() > 2)
					return "npchtm:ssq2_cl_tel_silence_q10296_03.htm";
			}
		}
		else if(npc.getNpcId() == priest_wood)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() < 5)
					return "npchtm:priest_wood_q10296_01.htm";
				if(st.getMemoState() == 5)
					return "npchtm:priest_wood_q10296_02.htm";
				if(st.getMemoState() > 5)
					return "npchtm:priest_wood_q10296_04.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 3)
					return "npchtm:ssq2_elcardia_home1_q10296_01.htm";
				if(st.getMemoState() > 3)
					return "npchtm:ssq2_elcardia_home1_q10296_04.htm";
			}
		}
		else if(npc.getNpcId() == hardin)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() < 4)
					return "npchtm:hardin_q10296_01.htm";
				if(st.getMemoState() == 4)
					return "npchtm:hardin_q10296_02.htm";
				if(st.getMemoState() > 4)
					return "npchtm:hardin_q10296_05.htm";
			}
		}
		else if(npc.getNpcId() == inzone_frantz)
		{
			if(st.isStarted() && st.getMemoState() == 5)
				return "npchtm:inzone_frantz_q10296_01.htm";
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == ssq2_eris_silence)
		{
			if(reply == 10296)
			{
				if(st.isCreated() && talker.isQuestComplete(10295) && talker.getLevel() >= 81)
				{
					st.setMemoState(1);
					st.setCond(1);
					st.playSound(SOUND_ACCEPT);
					st.setState(STARTED);
					showQuestPage("ssq2_eris_silence_q10296_04.htm", talker);
				}

			}
			else if(reply == 1)
			{
				if(st.isCreated() && talker.isQuestComplete(10295) && talker.getLevel() >= 81)
				{
					showQuestPage("ssq2_eris_silence_q10296_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				//i0 = myself.GetGlobalMap(80008);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					npc.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90315, 0, null);
				}
			}
			else if(reply == 3)
			{
				talker.teleToLocation(76736, -241021, -10780);
				//i0 = myself.GetGlobalMap(80008);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(npc, ssq2_elcardia1_silence);
				if(c0 != null)
				{
					c0.teleToLocation(76736, -241021, -10780);
				}
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia1_silence)
		{
			if(reply == 1)
			{
				Instance inst = npc.getInstanceZone();
				if(inst != null)
				{
					inst.rescheduleEndTask(60);
				}
				talker.teleToClosestTown();
			}
		}
		else if(npc.getNpcId() == ssq2_cl_tel_silence)
		{
			if(reply == 1)
			{
				InstanceManager.enterInstance(151, talker, npc, 0);
			}
		}
		else if(npc.getNpcId() == priest_wood)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					showPage("priest_wood_q10296_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				InstanceManager.enterInstance(113, talker, npc, 0);
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					showPage("ssq2_elcardia_home1_q10296_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 3)
				{
					st.setMemoState(4);
					showPage("ssq2_elcardia_home1_q10296_03.htm", talker);
					st.setCond(4);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == hardin)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					showPage("hardin_q10296_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					st.setMemoState(5);
					showPage("hardin_q10296_04.htm", talker);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == inzone_frantz)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					showPage("inzone_frantz_q10296_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 5)
				{
					if(talker.isSubClassActive())
					{
						showPage("inzone_frantz_q10296_04.htm", talker);
					}
					else
					{
						st.addExpAndSp(125000000, 12500000);
						st.giveItems(q10296_ssq2_reward_token, 1);
						st.exitCurrentQuest(false);
						st.playSound(SOUND_FINISH);
						showPage("inzone_frantz_q10296_03.htm", talker);
					}
				}
			}
		}
	}
}
