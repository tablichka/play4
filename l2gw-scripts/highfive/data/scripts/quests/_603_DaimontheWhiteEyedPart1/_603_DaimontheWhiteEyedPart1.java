package quests._603_DaimontheWhiteEyedPart1;

import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 27.01.12 15:52
 */
public class _603_DaimontheWhiteEyedPart1 extends Quest
{
	// NPC
	private static final int eye_of_argos = 31683;
	private static final int ancient_lithography1 = 31548;
	private static final int ancient_lithography2 = 31549;
	private static final int ancient_lithography3 = 31550;
	private static final int ancient_lithography4 = 31551;
	private static final int ancient_lithography5 = 31552;

	// Mobs
	private static final HashMap<Integer, Double> mobs = new HashMap<>(3);
	static
	{
		mobs.put(21297, 50.0);
		mobs.put(21299, 51.9);
		mobs.put(21304, 67.3);
	}

	public _603_DaimontheWhiteEyedPart1()
	{
		super(603, "_603_DaimontheWhiteEyedPart1", "Daimon the White Eyed Part 1");
		addStartNpc(eye_of_argos);
		addTalkId(eye_of_argos);
		addTalkId(ancient_lithography1, ancient_lithography2, ancient_lithography3, ancient_lithography4, ancient_lithography5);

		addKillId(mobs.keySet());
		addQuestItem(7190, 7191);
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
					return "eye_of_argos_q0603_0101.htm";

				return "eye_of_argos_q0603_0103.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:eye_of_argos_q0603_0105.htm";
				if(st.getMemoState() == 61)
				{
					talker.setSessionVar("cookie", "6");
					return "npchtm:eye_of_argos_q0603_0601.htm";
				}
				if(st.getMemoState() == 72 || st.getMemoState() == 71)
				{
					if(st.getMemoState() == 72 && st.getQuestItemsCount(7190) >= 200)
					{
						talker.setSessionVar("cookie", "7");
						return "npchtm:eye_of_argos_q0603_0703.htm";
					}

					return "npchtm:eye_of_argos_q0603_0704.htm";
				}
			}
		}
		else if(npc.getNpcId() == ancient_lithography1)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 11)
					return "npchtm:ancient_lithography1_q0603_0101.htm";
				if(st.getMemoState() == 21)
					return "npchtm:ancient_lithography1_q0603_0203.htm";
			}
		}
		else if(npc.getNpcId() == ancient_lithography2)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 21)
					return "npchtm:ancient_lithography2_q0603_0201.htm";
				if(st.getMemoState() == 31)
					return "npchtm:ancient_lithography2_q0603_0303.htm";
			}
		}
		else if(npc.getNpcId() == ancient_lithography3)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 31)
					return "npchtm:ancient_lithography3_q0603_0301.htm";
				if(st.getMemoState() == 41)
					return "npchtm:ancient_lithography3_q0603_0403.htm";
			}
		}
		else if(npc.getNpcId() == ancient_lithography4)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 41)
					return "npchtm:ancient_lithography4_q0603_0401.htm";
				if(st.getMemoState() == 51)
					return "npchtm:ancient_lithography4_q0603_0503.htm";
			}
		}
		else if(npc.getNpcId() == ancient_lithography5)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 51)
					return "npchtm:ancient_lithography5_q0603_0501.htm";
				if(st.getMemoState() == 61)
					return "npchtm:ancient_lithography5_q0603_0603.htm";
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
			if(reply == 603)
			{
				if(st.isCreated() && talker.getLevel() >= 73)
				{
					st.setMemoState(11);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("eye_of_argos_q0603_0104.htm", talker);
				}
			}
			else if(reply == 1 && st.isStarted())
			{
				String cookie = talker.getSessionVar("cookie");
				if(cookie != null && "6".equals(cookie))
				{
					if(st.getQuestItemsCount(7191) >= 5)
					{
						st.takeItems(7191, 5);
						showPage("eye_of_argos_q0603_0701.htm", talker);
						st.setMemoState(71);
						st.setCond(7);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						showPage("eye_of_argos_q0603_0702.htm", talker);
					}
				}
			}
			else if(reply == 3 && st.isStarted())
			{
				String cookie = talker.getSessionVar("cookie");
				if(cookie != null && "7".equals(cookie))
				{
					if(st.getQuestItemsCount(7190) >= 200)
					{
						st.takeItems(7190, -1);
						st.giveItems(7192, 1);
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("eye_of_argos_q0603_0801.htm", talker);
					}
					else
					{
						showPage("eye_of_argos_q0603_0802.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == ancient_lithography1)
		{
			if(reply == 1 && st.isStarted())
			{
				st.giveItems(7191, 1);
				showPage("ancient_lithography1_q0603_0201.htm", talker);
				st.setMemoState(21);
				st.setCond(2);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ancient_lithography2)
		{
			if(reply == 1 && st.isStarted())
			{
				st.giveItems(7191, 1);
				showPage("ancient_lithography2_q0603_0301.htm", talker);
				st.setMemoState(31);
				st.setCond(3);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ancient_lithography3)
		{
			if(reply == 1 && st.isStarted())
			{
				st.giveItems(7191, 1);
				showPage("ancient_lithography3_q0603_0401.htm", talker);
				st.setMemoState(41);
				st.setCond(4);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ancient_lithography4)
		{
			if(reply == 1 && st.isStarted())
			{
				st.giveItems(7191, 1);
				showPage("ancient_lithography4_q0603_0501.htm", talker);
				st.setMemoState(51);
				st.setCond(5);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
		else if(npc.getNpcId() == ancient_lithography5)
		{
			if(reply == 1 && st.isStarted())
			{
				st.giveItems(7191, 1);
				showPage("ancient_lithography5_q0603_0601.htm", talker);
				st.setMemoState(61);
				st.setCond(6);
				showQuestMark(talker);
				st.playSound(SOUND_MIDDLE);
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		QuestState st = getRandomPartyMemberWithMemoState(killer, 71);
		if(st != null && mobs.containsKey(npc.getNpcId()))
		{
			if(st.rollAndGiveLimited(7190, 1, mobs.get(npc.getNpcId()), 200))
			{
				if(st.getQuestItemsCount(7190) >= 200)
				{
					st.playSound(SOUND_MIDDLE);
					st.setCond(8);
					showQuestMark(st.getPlayer());
					st.setMemoState(72);
				}
				else
				{
					st.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}