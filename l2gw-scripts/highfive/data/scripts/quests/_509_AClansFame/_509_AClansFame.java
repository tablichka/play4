package quests._509_AClansFame;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 22.01.12 15:32
 */
public class _509_AClansFame extends Quest
{
	// NPC
	private static final int grandmagister_valdis = 31331;

	// Mobs
	private static final int daemon_of_hundred_eyes = 25290;
	private static final int degeneration_golem = 25523;
	private static final int demonic_agent_falston = 25322;
	private static final int geyser_guardian_hestia = 25293;
	private static final int spike_stakato_qn_shyid = 25514;

	public _509_AClansFame()
	{
		super(509, "_509_AClansFame", "A Clan's Fame");
		addStartNpc(grandmagister_valdis);
		addTalkId(grandmagister_valdis);

		addKillId(daemon_of_hundred_eyes, degeneration_golem, demonic_agent_falston, geyser_guardian_hestia, spike_stakato_qn_shyid);
		addQuestItem(8489, 8490, 8491, 8492, 8493);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == grandmagister_valdis)
		{
			if(st.isCreated())
			{
				if(talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					if(clan != null)
					{
						if(clan.getLevel() >= 6)
							return "grandmagister_valdis_q0509_01.htm";

						return "grandmagister_valdis_q0509_02.htm";
					}
				}
				else
					return "grandmagister_valdis_q0509_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0509_05.htm";
				if(!talker.isClanLeader())
				{
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					return "npchtm:grandmagister_valdis_q0509_05a.htm";
				}
				if(st.getMemoState() == 1 && st.getQuestItemsCount(8489) == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0509_16.htm";
				if(st.getMemoState() == 1 && st.getQuestItemsCount(8489) >= 0 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(1378, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "1378");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8489, -1);
					st.setMemoState(0);
					return "npchtm:grandmagister_valdis_q0509_17.htm";
				}
				if(st.getMemoState() == 2 && st.getQuestItemsCount(8490) == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0509_18.htm";
				if(st.getMemoState() == 2 && st.getQuestItemsCount(8490) >= 0 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(1378, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "1378");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8490, -1);
					st.setMemoState(0);
					return "npchtm:grandmagister_valdis_q0509_19.htm";
				}
				if(st.getMemoState() == 3 && st.getQuestItemsCount(8491) == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0509_20.htm";
				if(st.getMemoState() == 3 && st.getQuestItemsCount(8491) >= 0 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(1070, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "1070");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8491, -1);
					st.setMemoState(0);
					return "npchtm:grandmagister_valdis_q0509_21.htm";
				}
				if(st.getMemoState() == 4 && st.getQuestItemsCount(8492) == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0509_22.htm";
				if(st.getMemoState() == 4 && st.getQuestItemsCount(8492) >= 0 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(782, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "782");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8492, -1);
					st.setMemoState(0);
					return "npchtm:grandmagister_valdis_q0509_23.htm";
				}
				if(st.getMemoState() == 5 && st.getQuestItemsCount(8493) == 0 && talker.isClanLeader())
					return "npchtm:grandmagister_valdis_q0509_24.htm";
				if(st.getMemoState() == 5 && st.getQuestItemsCount(8493) >= 0 && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					clan.incReputation(1348, false, "Quest");
					Functions.showSystemMessageFStr(talker, 50851, "1348");
					st.playSound(SOUND_FANFARE1);
					st.takeItems(8493, -1);
					st.setMemoState(0);
					return "npchtm:grandmagister_valdis_q0509_25.htm";
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
		if(npc.getNpcId() == grandmagister_valdis)
		{
			if(reply == 509)
			{
				if(st.isCreated() && talker.isClanLeader())
				{
					L2Clan clan = talker.getClan();
					if(clan != null && clan.getLevel() >= 6)
					{
						st.setMemoState(0);
						st.setCond(1);
						st.setState(STARTED);
						st.playSound(SOUND_ACCEPT);
						showQuestPage("grandmagister_valdis_q0509_04.htm", talker);
					}
				}
			}
			else if(reply == 100)
			{
				if(st.isStarted())
				{
					showPage("grandmagister_valdis_q0509_06.htm", talker);
				}
			}
			else if(reply == 101)
			{
				st.exitCurrentQuest(true);
				st.playSound(SOUND_FINISH);
				showPage("grandmagister_valdis_q0509_07.htm", talker);
			}
			else if(reply == 102)
			{
				if(st.isStarted())
				{
					st.setMemoState(0);
					showPage("grandmagister_valdis_q0509_08.htm", talker);
				}
			}
			if(reply == 110)
			{
				if(talker.isClanLeader())
				{
					L2Clan pledge0 = talker.getClan();
					if(pledge0 != null && pledge0.getLevel() >= 6)
					{
						showQuestPage("grandmagister_valdis_q0509_01a.htm", talker);
					}
				}
			}
			else if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(1);
					showPage("grandmagister_valdis_q0509_09.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(2);
					showPage("grandmagister_valdis_q0509_10.htm", talker);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(3);
					showPage("grandmagister_valdis_q0509_11.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(4);
					showPage("grandmagister_valdis_q0509_12.htm", talker);
				}
			}
			else if(reply == 5)
			{
				if(st.isStarted() && st.getMemoState() == 0)
				{
					st.setMemoState(5);
					showPage("grandmagister_valdis_q0509_13.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		if(c0 == null || !npc.isInRange(c0, 1500))
			return;

		QuestState st = c0.getQuestState(509);
		if(st == null || !st.isStarted())
			return;

		if(npc.getNpcId() == daemon_of_hundred_eyes && st.getMemoState() == 1 && st.getQuestItemsCount(8489) == 0)
		{
			st.giveItems(8489, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == geyser_guardian_hestia && st.getMemoState() == 2 && st.getQuestItemsCount(8490) == 0)
		{
			st.giveItems(8490, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == degeneration_golem && st.getMemoState() == 3 && st.getQuestItemsCount(8491) == 0)
		{
			st.giveItems(8491, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == demonic_agent_falston && st.getMemoState() == 4 && st.getQuestItemsCount(8492) == 0)
		{
			st.giveItems(8492, 1);
			st.playSound(SOUND_ITEMGET);
		}
		else if(npc.getNpcId() == spike_stakato_qn_shyid && st.getMemoState() == 5 && st.getQuestItemsCount(8493) == 0)
		{
			st.giveItems(8493, 1);
			st.playSound(SOUND_ITEMGET);
		}
	}
}