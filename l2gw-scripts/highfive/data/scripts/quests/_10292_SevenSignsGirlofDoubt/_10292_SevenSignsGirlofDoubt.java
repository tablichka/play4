package quests._10292_SevenSignsGirlofDoubt;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 10.09.11 12:20
 */
public class _10292_SevenSignsGirlofDoubt extends Quest
{
	// NPC
	private static final int priest_wood = 32593;
	private static final int inzone_frantz = 32597;
	private static final int ssq2_elcardia_home1 = 32784;
	private static final int ssq2_door_elcardia = 32862;
	private static final int hardin = 30832;

	// Items
	private static final int q10292_ssq2_token1 = 17226;

	public _10292_SevenSignsGirlofDoubt()
	{
		super(10292, "_10292_SevenSignsGirlofDoubt", "Seven Signs, Girl of Doubt");

		addStartNpc(priest_wood);
		addTalkId(priest_wood, inzone_frantz, ssq2_elcardia_home1, ssq2_door_elcardia, hardin);
		addKillId(22801, 22802, 22803, 22804, 22805, 22806);
		addQuestItem(q10292_ssq2_token1);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == priest_wood)
		{
			if(st.isCompleted())
				return "npchtm:priest_wood_q10292_02.htm";

			if(st.isCreated())
				if(talker.getLevel() > 80 && talker.isQuestComplete(198))
					return "priest_wood_q10292_01.htm";
				else
					return "npchtm:priest_wood_q10292_03.htm";

			if(st.isStarted() && st.getMemoState() > 0 && st.getMemoState() < 9)
				return "npchtm:priest_wood_q10292_07.htm";
		}
		else if(npc.getNpcId() == inzone_frantz)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:inzone_frantz_q10292_01.htm";
				if(st.getMemoState() == 2)
					return "npchtm:inzone_frantz_q10292_03.htm";
				if(st.getMemoState() == 7)
					return "npchtm:inzone_frantz_q10292_04.htm";
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 2)
					return "npchtm:ssq2_elcardia_home1_q10292_01.htm";
				if(st.getMemoState() == 3 && st.getQuestItemsCount(q10292_ssq2_token1) < 10)
					return "npchtm:ssq2_elcardia_home1_q10292_04.htm";
				if(st.getMemoState() == 3 && st.getQuestItemsCount(q10292_ssq2_token1) >= 10)
				{
					st.takeItems(q10292_ssq2_token1, 10);
					st.setMemoState(4);
					st.setCond(4);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:ssq2_elcardia_home1_q10292_05.htm";
				}
				if(st.getMemoState() == 5)
					return "npchtm:ssq2_elcardia_home1_q10292_08.htm";
				if(st.getMemoState() == 6)
					return "npchtm:ssq2_elcardia_home1_q10292_10.htm";
				if(st.getMemoState() == 7)
					return "npchtm:ssq2_elcardia_home1_q10292_14.htm";
				if(st.getMemoState() == 8)
				{
					if(talker.isSubClassActive())
						return "npchtm:ssq2_elcardia_home1_q10292_17.htm";

					st.addExpAndSp(10000000, 1000000);
					st.playSound(SOUND_FINISH);
					st.exitCurrentQuest(false);
					return "npchtm:ssq2_elcardia_home1_q10292_15.htm";
				}
			}
		}
		else if(npc.getNpcId() == hardin)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 7)
					return "npchtm:hardin_q10292_01.htm";
				if(st.getMemoState() == 8)
					return "npchtm:hardin_q10292_04.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == priest_wood)
		{
			if(st.isCreated())
			{
				if(reply == 10292 && talker.getLevel() > 80)
				{
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					st.setMemoState(1);
					showQuestPage("priest_wood_q10292_05.htm", talker);
					st.setCond(1);
				}
				else if(reply == 1)
				{
					if(talker.getLevel() > 80 && talker.isQuestComplete(198))
						showQuestPage("priest_wood_q10292_04.htm", talker);
				}
			}
			else if(reply == 2)
			{
				InstanceManager.enterInstance(113, talker, npc, 0);
			}
		}
		else if(npc.getNpcId() == inzone_frantz)
		{
			if(reply == 1)
			{
				showPage("inzone_frantz_q10292_02.htm", talker);
			}
			else if(reply == 2)
			{
				st.setMemoState(2);
				showPage("inzone_frantz_q10292_07.htm", talker);
				st.setCond(2);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ssq2_elcardia_home1)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					showPage("ssq2_elcardia_home1_q10292_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.setMemoState(3);
					showPage("ssq2_elcardia_home1_q10292_03.htm", talker);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_FINISH);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					showPage("ssq2_elcardia_home1_q10292_06.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 4)
				{
					st.setMemoState(5);
					showPage("ssq2_elcardia_home1_q10292_07.htm", talker);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_FINISH);
				}
			}
			else if(reply == 5)
			{
				if(npc.i_ai1 == 0)
				{
					npc.createOnePrivate(27422, "Ssq2TestMonster2", 0, 1, 89440, -238016, -9632, Rnd.get(65535), 0, 0, 0);
					npc.createOnePrivate(27422, "Ssq2TestMonster4", 0, 1, 89440, -238016, -9632, Rnd.get(65535), 0, 0, 0);
				}
				else
				{
					showPage("ssq2_elcardia_home1_q10292_16.htm", talker);
				}
			}
			else if(reply == 6)
			{
				if(st.isStarted() && st.getMemoState() == 6)
				{
					showPage("ssq2_elcardia_home1_q10292_11.htm", talker);
				}
			}
			else if(reply == 7)
			{
				if(st.isStarted() && st.getMemoState() == 6)
				{
					st.setMemoState(7);
					showPage("ssq2_elcardia_home1_q10292_13.htm", talker);
					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_FINISH);
				}
			}
			else if(reply == 10)
			{
				talker.teleToClosestTown();
			}
		}
		else if(npc.getNpcId() == hardin)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 7)
				{
					st.setMemoState(8);
					showPage("hardin_q10292_02.htm", talker);
					st.setCond(8);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 8)
				{
					showPage("hardin_q10292_03.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		GArray<QuestState> p = getPartyMembersWithMemoState(killer, 3);
		if(!p.isEmpty())
		{
			GArray<QuestState> party = new GArray<>(p.size());
			for(QuestState st : p)
				if(st.getQuestItemsCount(q10292_ssq2_token1) < 10)
					party.add(st);

			if(!party.isEmpty())
			{
				QuestState st = party.get(Rnd.get(party.size()));
				if(st.rollAndGiveLimited(q10292_ssq2_token1, 1, 70, 10))
				{
					if(st.getQuestItemsCount(q10292_ssq2_token1) >= 10)
					{
						st.playSound(SOUND_MIDDLE);
						st.setCond(4);
						showQuestMark(st.getPlayer());
					}
					else
					{
						st.playSound(SOUND_ITEMGET);
					}
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState qs)
	{
		return "npchtm:" + event;
	}
}