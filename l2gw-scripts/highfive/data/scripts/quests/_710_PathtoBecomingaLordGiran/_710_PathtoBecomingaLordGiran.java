package quests._710_PathtoBecomingaLordGiran;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 18.01.12 18:23
 */
public class _710_PathtoBecomingaLordGiran extends Quest
{
	// NPC
	private static final int chamberlain_saul = 35184;
	private static final int box_of_secret_q065 = 32243;
	private static final int warehouse_chief_gesto = 30511;
	private static final int wharf_manager_felton = 30879;

	// Items
	private static final int q_box_of_gesto_q0710 = 13013;
	private static final int q_seal_of_box_q0710 = 13014;

	// Mobs
	private static final int[] mobs = new int[]{21634, 21637, 20840, 20846, 20841, 21616, 21622, 20844, 21628, 20842, 20836, 20845, 21619,
			20986, 20987, 20988, 21613, 20839, 20847, 21610, 20833, 21607, 21604, 21625, 20843, 20832, 20835, 21631};

	public _710_PathtoBecomingaLordGiran()
	{
		super();
		addStartNpc(chamberlain_saul);
		addStartNpc(warehouse_chief_gesto);
		addTalkId(chamberlain_saul, box_of_secret_q065, warehouse_chief_gesto, wharf_manager_felton);

		addKillId(mobs);
		addQuestItem(q_box_of_gesto_q0710, q_seal_of_box_q0710);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_saul)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(83).hasLord())
					return "chamberlain_saul_q0710_01.htm";

				return "chamberlain_saul_q0710_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					if(Util.getCurrentTime() - st.getMemoStateEx(1) < 60)
						return "npchtm:chamberlain_saul_q0710_05.htm";

					st.setMemoState(3);
					st.setMemoStateEx(1, 0);
					st.setCond(2);
					showQuestMark(talker);
					return "npchtm:chamberlain_saul_q0710_06.htm";
				}
				if(st.getMemoState() == 3 && npc.isMyLord(talker))
					return "npchtm:chamberlain_saul_q0710_08.htm";
				if(st.getMemoState() > 3 && st.getMemoState() < 10 && npc.isMyLord(talker))
					return "npchtm:chamberlain_saul_q0710_09.htm";
				if(st.getMemoState() == 10 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_saul_q0710_10a.htm";
					if(ResidenceManager.getInstance().getBuildingById(104).getContractCastleId() != 3)
						return "npchtm:chamberlain_saul_q0710_10b.htm";

					return "npchtm:chamberlain_saul_q0710_10.htm";
				}
			}
		}
		else if(npc.getNpcId() == box_of_secret_q065)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 5)
				{
					st.giveItems(q_seal_of_box_q0710, 1);
					st.setMemoState(6);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:box_of_secret_q065_q0710_01.htm";
				}
				if(st.getMemoState() == 6)
				{
					if(st.getQuestItemsCount(q_seal_of_box_q0710) > 0)
						return "npchtm:box_of_secret_q065_q0710_02.htm";
					Integer last = (Integer) talker.getProperty("quest_last_reward_time");
					if(last == null || Util.getCurrentTime() - last > 600)
					{
						talker.addProperty("quest_last_reward_time", Util.getCurrentTime());
						st.giveItems(q_seal_of_box_q0710, 1);
						return "npchtm:box_of_secret_q065_q0710_02a.htm";
					}

					return "npchtm:box_of_secret_q065_q0710_02b.htm";
				}
				if(st.getMemoState() == 7)
				{
					st.setMemoState(8);
					st.setMemoStateEx(1, 0);
					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:box_of_secret_q065_q0710_03.htm";
				}
				if(st.getMemoState() > 7)
					return "npchtm:box_of_secret_q065_q0710_05.htm";
			}
		}
		else if(npc.getNpcId() == warehouse_chief_gesto)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 3 && npc.isMyLord(talker))
					return "npchtm:warehouse_chief_gesto_q0710_01.htm";
				if(st.getMemoState() > 3 && st.getMemoState() < 6 && npc.isMyLord(talker))
					return "npchtm:warehouse_chief_gesto_q0710_04.htm";
				if(st.getMemoState() > 5 && st.getMemoState() < 9 && npc.isMyLord(talker))
					return "npchtm:warehouse_chief_gesto_q0710_05.htm";
				if(st.getMemoState() == 9 && npc.isMyLord(talker))
				{
					if(st.getQuestItemsCount(q_box_of_gesto_q0710) >= 300)
					{
						st.takeItems(q_box_of_gesto_q0710, -1);
						st.setCond(9);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
						st.setMemoState(10);
						return "npchtm:warehouse_chief_gesto_q0710_08.htm";
					}

					return "npchtm:warehouse_chief_gesto_q0710_09.htm";
				}
			}
			if(!npc.isMyLord(talker))
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					if(npc.isMyLord(c0))
					{
						QuestState qs = c0.getQuestState(710);
						if(qs != null)
						{
							if(qs.isStarted() && qs.getMemoState() == 6)
							{
								if(st.getQuestItemsCount(q_seal_of_box_q0710) >= 1)
								{
									qs.setMemoState(7);
									qs.setMemoStateEx(1, talker.getObjectId());
									qs.setCond(6);
									showQuestMark(c0);
									qs.playSound(SOUND_MIDDLE);
									st.takeItems(q_seal_of_box_q0710, -1);
									return "npchtm:warehouse_chief_gesto_q0710_06.htm";
								}
								return "npchtm:warehouse_chief_gesto_q0710_06a.htm";
							}
							else if(qs.isStarted() && qs.getMemoState() > 6 && qs.getMemoState() <= 8)
								return "npchtm:warehouse_chief_gesto_q0710_07.htm";
						}
					}
				}
				else
					return "npchtm:warehouse_chief_gesto_q0710_10.htm";
			}
		}
		else if(npc.getNpcId() == wharf_manager_felton)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 4 && npc.isMyLord(talker))
					return "npchtm:wharf_manager_felton_q0710_01.htm";
				if(st.getMemoState() == 5 && npc.isMyLord(talker))
					return "npchtm:wharf_manager_felton_q0710_03.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == chamberlain_saul)
		{
			if(reply == 710)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(83).hasLord())
				{
					st.setMemoState(1);
					st.setMemoStateEx(1, Util.getCurrentTime());
					st.playSound(SOUND_ACCEPT);
					showQuestPage("chamberlain_saul_q0710_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					startQuestTimer("npc_say", 60000, npc, null, true);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(83).hasLord())
				{
					showQuestPage("chamberlain_saul_q0710_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 10 && npc.isMyLord(talker))
				{
					if(!TerritoryWarManager.getWar().isInProgress())
					{
						Functions.npcSay(npc, Say2C.ALL, 71059, talker.getName());
						talker.setVar("territory_lord_83", "true");
						TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(83));
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("chamberlain_saul_q0710_11.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == warehouse_chief_gesto)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 3 && npc.isMyLord(talker))
				{
					showPage("warehouse_chief_gesto_q0710_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 3 && npc.isMyLord(talker))
				{
					st.setMemoState(4);
					showPage("warehouse_chief_gesto_q0710_03.htm", talker);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == wharf_manager_felton)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 4 && npc.isMyLord(talker))
				{
					st.setMemoState(5);
					showPage("wharf_manager_felton_q0710_02.htm", talker);
					st.setCond(4);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if("npc_say".equals(event) && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 71051);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(contains(mobs, npc.getNpcId()))
		{
			L2Player c0 = Util.getClanLeader(killer);
			if(c0 != null)
			{
				QuestState qs = c0.getQuestState(710);
				if(qs != null && qs.isStarted() && qs.getMemoState() >= 8)
				{
					if(qs.getMemoStateEx(1) >= 299)
					{
						if(qs.getMemoState() == 8)
						{
							qs.setCond(8);
							showQuestMark(c0);
							qs.playSound(SOUND_MIDDLE);
							qs.setMemoState(9);
							qs.setMemoStateEx(1, qs.getMemoStateEx(1) + 1);
						}
					}
					else
					{
						qs.setMemoStateEx(1, qs.getMemoStateEx(1) + 1);
						qs.playSound(SOUND_ITEMGET);
					}
					killer.addItem("Quest", q_box_of_gesto_q0710, 1, npc, true);
				}
			}
		}
	}
}