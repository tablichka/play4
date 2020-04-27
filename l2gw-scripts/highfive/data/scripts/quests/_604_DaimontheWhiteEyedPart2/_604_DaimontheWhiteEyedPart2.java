package quests._604_DaimontheWhiteEyedPart2;

import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.math.Rnd;

import java.util.HashSet;

/**
 * @author: rage
 * @date: 28.01.12 11:53
 */
public class _604_DaimontheWhiteEyedPart2 extends Quest
{
	// NPC
	private static final int eye_of_argos = 31683;
	private static final int daimons_altar = 31541;

	// Mobs
	private static final int daemon_of_hundred_eyes = 25290;

	public _604_DaimontheWhiteEyedPart2()
	{
		super();
		addStartNpc(eye_of_argos);
		addTalkId(eye_of_argos, daimons_altar);
		addKillId(daemon_of_hundred_eyes);
		addQuestItem(7193, 7194);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == eye_of_argos)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() >= 73)
				{
					if(st.getQuestItemsCount(7192) >= 1)
						return "eye_of_argos_q0604_0101.htm";

					return "eye_of_argos_q0604_0102.htm";
				}

				return "eye_of_argos_q0604_0103.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:eye_of_argos_q0604_0105.htm";
				if(st.getMemoState() == 22)
				{
					if(st.getQuestItemsCount(7194) >= 1)
						return "npchtm:eye_of_argos_q0604_0201.htm";

					return "npchtm:eye_of_argos_q0604_0202.htm";
				}
			}
		}
		else if(npc.getNpcId() == daimons_altar)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 11 && st.getQuestItemsCount(7193) >= 1)
					return "npchtm:daimons_altar_q0604_0101.htm";
				if(st.getMemoState() == 21)
				{
					if(npc.av_quest0.compareAndSet(0, 1))
					{
						npc.createOnePrivate(25290, "DaemonOfHundredEyes", 0, 0, 186320, -43904, -3175, 0, npc.getStoredId(), 0, 0);
						npc.onDecay();
						return "npchtm:daimons_altar_q0604_0201.htm";
					}

					return "npchtm:daimons_altar_q0604_0202.htm";
				}
				if(st.getMemoState() == 22)
					return "npchtm:daimons_altar_q0604_0204.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == eye_of_argos)
		{
			if(reply == 604)
			{
				if(st.isCreated() && talker.getLevel() >= 73)
				{
					st.takeItems(7192, 1);
					st.setMemoState(11);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("eye_of_argos_q0604_0104.htm", talker);
					st.giveItems(7193, 1);
				}
			}
			else if(reply == 3 && st.isStarted())
			{
				if(st.getQuestItemsCount(7194) >= 1)
				{
					int i1 = Rnd.get(1000);
					st.takeItems(7194, 1);
					st.takeItems(7193, -1);
					if(i1 < 167)
					{
						st.giveItems(4595, 5);
					}
					else if(i1 < 167 + 167)
					{
						st.giveItems(4596, 5);
					}
					else if(i1 < 167 + 167 + 167)
					{
						st.giveItems(4597, 5);
					}
					else if(i1 < 167 + 167 + 167 + 167)
					{
						st.giveItems(4598, 5);
					}
					else if(i1 < 167 + 167 + 167 + 167 + 167)
					{
						st.giveItems(4599, 5);
					}
					else if(i1 < 167 + 167 + 167 + 167 + 167 + 165)
					{
						st.giveItems(4600, 5);
					}
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("eye_of_argos_q0604_0301.htm", talker);
				}
				else
				{
					showPage("eye_of_argos_q0604_0302.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == daimons_altar)
		{
			if(reply == 1)
			{
				if(st.getQuestItemsCount(7193) >= 1)
				{
					if(npc.av_quest0.compareAndSet(0, 1))
					{
						st.takeItems(7193, 1);
						showPage("daimons_altar_q0604_0201.htm", talker);
						npc.createOnePrivate(25290, "DaemonOfHundredEyes", 0, 0, 186320, -43904, -3175, 0, npc.getStoredId(), 0, 0);
						npc.onDecay();
						st.setMemoState(21);
						st.setCond(2);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						showPage("daimons_altar_q0604_0202.htm", talker);
					}
				}
				else
				{
					showPage("daimons_altar_q0604_0203.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == daemon_of_hundred_eyes)
		{
			HashSet<QuestState> party = new HashSet<>(9);
			party.addAll(getPartyMembersWithMemoState(killer, 11));
			party.addAll(getPartyMembersWithMemoState(killer, 21));
			if(!party.isEmpty())
			{
				for(QuestState st : party)
				{
					st.rollAndGiveLimited(7194, 1, 100, 1);
					st.setCond(3);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
					st.setMemoState(22);
				}
			}
			L2NpcInstance totem = L2ObjectsStorage.getAsNpc(npc.param1);
			if(totem != null)
				totem.av_quest0.set(0);
			else
				_log.info(this + " totem is null!!");
		}
	}
}