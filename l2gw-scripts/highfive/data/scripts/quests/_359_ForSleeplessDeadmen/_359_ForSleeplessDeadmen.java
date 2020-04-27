package quests._359_ForSleeplessDeadmen;

import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.ExShowQuestMark;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;

/**
 * @author: rage
 * @date: 10.01.12 16:08
 */
public class _359_ForSleeplessDeadmen extends Quest
{
	//NPCs
	private static final int highpriest_orven = 30857;

	//Mobs
	private static final int doom_servant = 21006;
	private static final int doom_guard = 21007;
	private static final int doom_archer = 21008;
	private static final HashMap<Integer, Double> chances = new HashMap<>(3);

	static
	{
		chances.put(doom_archer, 36.5);
		chances.put(doom_guard, 39.2);
		chances.put(doom_servant, 50.3);
	}

	// Items
	private static final int ash_of_doom_peoples = 5869;

	public _359_ForSleeplessDeadmen()
	{
		super(359, "_359_ForSleeplessDeadmen", "For Sleepless Deadmen");
		addStartNpc(highpriest_orven);
		addTalkId(highpriest_orven);

		addKillId(doom_servant, doom_guard, doom_archer);
		addQuestItem(ash_of_doom_peoples);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == highpriest_orven)
		{
			if(st.isCreated())
			{
				if(talker.getLevel() < 60)
					return "npchtm:highpriest_orven_q0359_01.htm";

				return "highpriest_orven_q0359_02.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && st.getQuestItemsCount(ash_of_doom_peoples) < 60)
					return "npchtm:highpriest_orven_q0359_07.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(ash_of_doom_peoples) >= 60)
				{
					st.takeItems(ash_of_doom_peoples, -1);
					st.setMemoState(2);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:highpriest_orven_q0359_08.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:highpriest_orven_q0359_09.htm";
			}

		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();

		if(npc.getNpcId() == highpriest_orven)
		{
			if(reply == 359)
			{
				st.setMemoState(1);
				st.setCond(1);
				st.setState(STARTED);
				st.playSound(SOUND_ACCEPT);
				showQuestPage("highpriest_orven_q0359_06.htm", talker);
			}
			else if(reply == 1)
			{
				showQuestPage("highpriest_orven_q0359_05.htm", talker);
			}
			else if(reply == 2 && st.getMemoState() == 2)
			{
				int i0 = Rnd.get(8);
				switch(i0)
				{
					case 0:
						st.giveItems(6341, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 1:
						st.giveItems(6343, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 2:
						st.giveItems(6345, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 3:
						st.giveItems(6342, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 4:
						st.giveItems(6344, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 5:
						st.giveItems(6346, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 6:
						st.giveItems(5494, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
					case 7:
						st.giveItems(5495, 4);
						showPage("highpriest_orven_q0359_10.htm", talker);
						break;
				}

				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);

				L2Clan clan = talker.getClan();
				if(clan != null)
				{
					L2Player c0 = clan.getLeader().getPlayer();
					if(c0 != null)
					{
						QuestState qs = c0.getQuestState(713);
						if(qs != null && qs.isStarted() && (qs.getMemoState() % 100 == 2 || qs.getMemoState() % 100 == 12) && qs.getMemoState() / 100 < 5)
						{
							int i1 = qs.getMemoState();
							if(i1 / 100 >= 4)
							{
								if(i1 % 100 == 2)
								{
									qs.setCond(4);
								}
								else if(i1 % 100 == 12)
								{
									qs.setCond(6);
								}
							}

							qs.setMemoState(i1 + 100);
							c0.sendPacket(new ExShowQuestMark(713));
							qs.playSound(SOUND_MIDDLE);
						}
					}
				}
			}
		}
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		return "npchtm:" + event;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState st)
	{
		if(st.getQuestItemsCount(ash_of_doom_peoples) < 60)
		{
			if(st.rollAndGiveLimited(ash_of_doom_peoples, 1, chances.get(npc.getNpcId()), 60))
			{
				if(st.getQuestItemsCount(ash_of_doom_peoples) >= 60)
				{
					st.setCond(2);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
				else
				{
					st.playSound(SOUND_ITEMGET);
				}
			}
		}
	}
}