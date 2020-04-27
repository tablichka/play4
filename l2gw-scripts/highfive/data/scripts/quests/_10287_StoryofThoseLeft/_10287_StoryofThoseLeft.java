package quests._10287_StoryofThoseLeft;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 29.09.11 2:04
 */
public class _10287_StoryofThoseLeft extends Quest
{
	// NPC
	private static final int repre = 32020;
	private static final int jinia_npc = 32760;
	private static final int kegor_npc = 32761;

	public _10287_StoryofThoseLeft()
	{
		super(10287, "_10287_StoryofThoseLeft", "Story of Those Left");
		addStartNpc(repre);
		addTalkId(repre);
		addTalkId(jinia_npc, kegor_npc);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == repre)
		{
			if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10286))
				return "repre_q10287_01.htm";

			if(st.isCompleted())
				return "npchtm:repre_q10287_02.htm";

			if(st.isCreated() && (talker.getLevel() < 82 || !talker.isQuestComplete(10286)))
				return "repre_q10287_03.htm";

			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:repre_q10287_05.htm";
				if(st.getMemoState() == 2)
					return "npchtm:repre_q10287_09.htm";
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
			if(reply == 10287)
			{
				if(st.isCreated() && talker.getLevel() >= 82 && talker.isQuestComplete(10286))
				{
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("repre_q10287_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(InstanceManager.enterInstance(146, talker, npc, 0))
				{
					if(st.getMemoState() == 1)
					{
						st.setCond(2);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
				else
				{
					showPage("repre_q10287_08.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("repre_q10287_10.htm", talker);
				}
			}
			else if(reply == 11)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.giveItems(10549, 1);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					showPage("repre_q10287_11.htm", talker);
				}
			}
			else if(reply == 12)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.giveItems(10550, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("repre_q10287_11.htm", talker);
				}
			}
			else if(reply == 13)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.giveItems(10551, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("repre_q10287_11.htm", talker);
				}
			}
			else if(reply == 14)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.giveItems(10552, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("repre_q10287_11.htm", talker);
				}
			}
			else if(reply == 15)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.giveItems(10553, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("repre_q10287_11.htm", talker);
				}
			}
			else if(reply == 16)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.giveItems(14219, 1);
					st.exitCurrentQuest(false);
					st.playSound(SOUND_FINISH);
					showPage("repre_q10287_11.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == jinia_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					if(npc.getInstanceZoneId() == 146)
					{
						showPage("jinia_npc_q10287_02.htm", talker);
					}
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 0)
				{
					if(npc.getInstanceZoneId() == 146)
					{
						st.set("ex_1", 1);
						showPage("jinia_npc_q10287_03.htm", talker);
						st.setCond(3);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					if(npc.getInstanceZoneId() == 146)
					{
						talker.teleToClosestTown();
						st.setCond(5);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npc.getNpcId() == kegor_npc)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1 && st.getInt("ex_2") == 0)
				{
					if(npc.getInstanceZoneId() == 146)
					{
						showPage("kegor_q10287_03.htm", talker);
					}
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && st.getInt("ex_1") == 1 && st.getInt("ex_2") == 0)
				{
					if(npc.getInstanceZoneId() == 146)
					{
						st.set("ex_2", 1);
						showPage("kegor_q10287_04.htm", talker);
						st.setCond(4);
						showQuestMark(st.getPlayer());
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
	}
}
