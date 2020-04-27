package quests._10284_AcquisitionofDivineSword;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 16.09.11 23:08
 */
public class _10284_AcquisitionofDivineSword extends Quest
{
	// NPC
	private static final int repre = 32020;
	private static final int jinia_npc = 32760;
	private static final int kroon = 32653;
	private static final int taroon = 32654;
	private static final int kegor_savedun = 18846;

	public _10284_AcquisitionofDivineSword()
	{
		super(10284, "_10284_AcquisitionofDivineSword", "Acquisition of Divine Sword");
		addStartNpc(repre);
		addTalkId(repre, jinia_npc, kroon, kegor_savedun, taroon);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == repre)
		{
			if(st.isCompleted())
				return "repre_q10284_02.htm";

			if(st.isCreated())
			{
				if(talker.getLevel() >= 82 && talker.isQuestComplete(10283))
					return "repre_q10284_01.htm";

				return "repre_q10284_03.htm";
			}

			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:repre_q10284_05.htm";
				if(st.getMemoState() == 2)
					return "npchtm:repre_q10284_09.htm";
			}
		}
		else if(npc.getNpcId() == kroon)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 2)
					return "npchtm:kroon_q10284_01.htm";
				if(st.getMemoState() == 3)
				{
					st.rollAndGive(57, 296425, 100);
					st.addExpAndSp(921805, 82230);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:kroon_q10284_05.htm";
				}
			}
		}
		else if(npc.getNpcId() == taroon)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 2)
					return "npchtm:taroon_q10284_01.htm";
				if(st.getMemoState() == 3)
				{
					st.rollAndGive(57, 296425, 100);
					st.addExpAndSp(921805, 82230);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					return "npchtm:taroon_q10284_05.htm";
				}
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == repre)
		{
			if(reply == 10284)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10283))
				{
					st.setCond(1);
					st.setMemoState(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("repre_q10284_04.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(InstanceManager.enterInstance(140, talker, npc, 0))
				{
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
				else
					showPage("repre_q10284_08.htm", talker);
			}
		}
		else if(npc.getNpcId() == jinia_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					if(st.getInt("ex_1") == 1 && st.getInt("ex_2") == 0 && st.getInt("ex_3") == 0)
					{
						showPage("jinia_npc_q10284_05a.htm", talker);
					}
					else if(st.getInt("ex_1") == 0 && st.getInt("ex_2") == 1 && st.getInt("ex_3") == 0)
					{
						showPage("jinia_npc_q10284_05b.htm", talker);
					}
					else if(st.getInt("ex_1") == 0 && st.getInt("ex_2") == 0 && st.getInt("ex_3") == 1)
					{
						showPage("jinia_npc_q10284_05c.htm", talker);
					}
					else if(st.getInt("ex_1") == 0 && st.getInt("ex_2") == 1 && st.getInt("ex_3") == 1)
					{
						showPage("jinia_npc_q10284_05d.htm", talker);
					}
					else if(st.getInt("ex_1") == 1 && st.getInt("ex_2") == 0 && st.getInt("ex_3") == 1)
					{
						showPage("jinia_npc_q10284_05e.htm", talker);
					}
					else if(st.getInt("ex_1") == 1 && st.getInt("ex_2") == 1 && st.getInt("ex_3") == 0)
					{
						showPage("jinia_npc_q10284_05f.htm", talker);
					}
					else if(st.getInt("ex_1") == 1 && st.getInt("ex_2") == 1 && st.getInt("ex_3") == 1)
					{
						showPage("jinia_npc_q10284_05g.htm", talker);
					}
				}
			}
			else if(reply == 10)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("jinia_npc_q10284_02a.htm", talker);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("jinia_npc_q10284_02b.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.set("ex_1", 1);
					showPage("jinia_npc_q10284_02c.htm", talker);
				}
			}
			else if(reply == 20)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("jinia_npc_q10284_03a.htm", talker);
				}
			}
			else if(reply == 21)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("jinia_npc_q10284_03b.htm", talker);
				}
			}
			else if(reply == 22)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.set("ex_2", 1);
					showPage("jinia_npc_q10284_03c.htm", talker);
				}
			}
			else if(reply == 30)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("jinia_npc_q10284_04a.htm", talker);
				}
			}
			else if(reply == 31)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					showPage("jinia_npc_q10284_04b.htm", talker);
				}
			}
			else if(reply == 32)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.set("ex_3", 1);
					showPage("jinia_npc_q10284_04c.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1 && st.getInt("ex_2") == 1 && st.getInt("ex_3") == 1)
				{
					showPage("jinia_npc_q10284_06.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1 && st.getInt("ex_2") == 1 && st.getInt("ex_3") == 1)
				{
					st.set("ex_1", 0);
					st.set("ex_2", 0);
					st.set("ex_3", 0);
					st.setMemoState(2);
					showPage("jinia_npc_q10284_07.htm", talker);
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					Instance inst = npc.getInstanceZone();
					if(inst != null)
						inst.setNoUserTimeout(0);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					talker.teleToClosestTown();
				}
			}
		}
		else if(npc.getNpcId() == kroon)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("kroon_q10284_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(InstanceManager.enterInstance(138, talker, npc, 0))
				{
					if(st.getMemoState() == 2)
					{
						if(st.getQuestItemsCount(15514) < 1)
						{
							st.giveItems(15514, 1);
						}
						st.setCond(4);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(st.getMemoState() == 2)
				{
					showPage("kroon_q10284_07.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("kroon_q10284_03.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == taroon)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("taroon_q10284_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(InstanceManager.enterInstance(138, talker, npc, 0))
				{
					if(st.getMemoState() == 2)
					{
						if(st.getQuestItemsCount(15514) < 1)
						{
							st.giveItems(15514, 1);
						}
						st.setCond(4);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else if(st.getMemoState() == 2)
				{
					showPage("taroon_q10284_07.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("taroon_q10284_03.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == kegor_savedun)
		{
			if(reply == 2)
			{
				Instance inst = npc.getInstanceZone();
				if(inst != null && inst.getTemplate().getId() == 138)
					inst.stopInstance();
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}
}
