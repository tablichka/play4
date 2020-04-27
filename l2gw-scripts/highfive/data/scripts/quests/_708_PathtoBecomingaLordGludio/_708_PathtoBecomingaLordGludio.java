package quests._708_PathtoBecomingaLordGludio;

import ru.l2gw.commons.math.Rnd;
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
 * @date: 17.01.12 17:51
 */
public class _708_PathtoBecomingaLordGludio extends Quest
{
	// NPC
	private static final int chamberlain_saius = 35100;
	private static final int blacksmith_pinter = 30298;
	private static final int captain_bathia = 30332;

	// Mobs
	private static final int q_duahan_of_glodio = 27393;
	private static final int tracker_skeleton = 20035;
	private static final int tracker_skeleton_leader = 20042;
	private static final int scout_skeleton = 20045;
	private static final int sniper_skeleton = 20051;
	private static final int ruin_spartoi = 20054;
	private static final int raging_spartoi = 20060;
	private static final int shield_skeleton = 20514;
	private static final int skeleton_infantry = 20515;

	// Items
	private static final int q_armor_of_dura = 13848;

	public _708_PathtoBecomingaLordGludio()
	{
		super();
		addStartNpc(chamberlain_saius);
		addStartNpc(blacksmith_pinter);
		addTalkId(chamberlain_saius, blacksmith_pinter, captain_bathia);

		addKillId(q_duahan_of_glodio);
		addKillId(tracker_skeleton, tracker_skeleton_leader, scout_skeleton, sniper_skeleton, ruin_spartoi, raging_spartoi, shield_skeleton, skeleton_infantry);
		addQuestItem(q_armor_of_dura);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_saius)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(81).hasLord())
					return "chamberlain_saius_q0708_01.htm";

				return "chamberlain_saius_q0708_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					if(Util.getCurrentTime() - st.getMemoStateEx(1) < 60)
						return "npchtm:chamberlain_saius_q0708_05.htm";

					st.setMemoState(2);
					st.setMemoStateEx(1, 0);
					return "npchtm:chamberlain_saius_q0708_06.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:chamberlain_saius_q0708_07.htm";
				if(st.getMemoState() == 3)
					return "npchtm:chamberlain_saius_q0708_14.htm";
				if(st.getMemoState() == 4)
					return "npchtm:chamberlain_saius_q0708_15.htm";
				if(st.getMemoState() == 5)
				{
					int i0 = st.getMemoState();
					st.setMemoState(i0 + 10);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_saius_q0708_16.htm";
				}
				if(st.getMemoState() / 10 <= 1 && st.getMemoState() % 10 == 9)
				{
					int i0 = st.getMemoState() / 10;
					int i1 = st.getMemoState();
					if(i0 == 0)
					{
						st.setMemoState(i1 + 10);
						st.setCond(5);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					return "npchtm:chamberlain_saius_q0708_17.htm";
				}
				if(st.getMemoState() / 10 == 1 && st.getMemoState() % 10 != 9)
					return "npchtm:chamberlain_saius_q0708_18.htm";
				if((st.getMemoState() / 10 == 2 || st.getMemoState() / 10 == 3) && st.getMemoState() % 10 == 9)
					return "npchtm:chamberlain_saius_q0708_19.htm";
				if(st.getMemoState() / 10 == 4 && st.getMemoState() % 10 != 9)
					return "npchtm:chamberlain_saius_q0708_21.htm";
				if(st.getMemoState() / 10 == 4 && st.getMemoState() % 10 == 9 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_saius_q0708_22a.htm";
					if(ResidenceManager.getInstance().getBuildingById(101).getContractCastleId() != 1 || ResidenceManager.getInstance().getBuildingById(102).getContractCastleId() != 1)
						return "npchtm:chamberlain_saius_q0708_22b.htm";

					return "chamberlain_saius_q0708_22.htm";
				}
			}
			if(!npc.isMyLord(talker))
			{
				if(npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
				{
					L2Player c0 = Util.getClanLeader(talker);
					if(c0 != null)
					{
						QuestState qs = c0.getQuestState(708);
						if(qs != null && qs.isStarted())
						{
							if(qs.getMemoState() == 3)
							{
								if(npc.isInRange(c0, 1500))
									return "npchtm:chamberlain_saius_q0708_11.htm";

								return "npchtm:chamberlain_saius_q0708_10.htm";
							}
							else if(qs.getMemoState() == 4)
								return "npchtm:chamberlain_saius_q0708_13a.htm";
						}
					}
				}
				return "npchtm:chamberlain_saius_q0708_09.htm";
			}
		}
		else if(npc.getNpcId() == blacksmith_pinter)
		{
			if(!npc.isMyLord(talker))
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(708);
					if(qs != null)
					{
						if(qs.isStarted() && qs.getMemoState() <= 3)
							return "npchtm:blacksmith_pinter_q0708_02.htm";
						else if(qs.getMemoState() == 4)
						{
							int i1 = qs.getMemoStateEx(1);
							if(talker.getObjectId() == i1)
								return "npchtm:blacksmith_pinter_q0708_03.htm";

							return "npchtm:blacksmith_pinter_q0708_03a.htm";
						}
						else if(qs.getMemoState() % 10 == 5)
						{
							if(st.getQuestItemsCount(1867) >= 100 && st.getQuestItemsCount(1865) >= 100 && st.getQuestItemsCount(1869) >= 100 && st.getQuestItemsCount(1879) >= 50)
								return "npchtm:blacksmith_pinter_q0708_08.htm";

							return "npchtm:blacksmith_pinter_q0708_07.htm";
						}
						else if(qs.getMemoState() % 10 == 9)
							return "npchtm:blacksmith_pinter_q0708_12.htm";
					}
					else
						return "npchtm:blacksmith_pinter_q0708_01.htm";
				}
			}
			else
			{
				return "npchtm:blacksmith_pinter_q0708_13.htm";
			}
		}
		else if(npc.getNpcId() == captain_bathia)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() / 10 == 1)
					return "npchtm:captain_bathia_q0708_01.htm";
				if(st.getMemoState() / 10 == 2)
					return "npchtm:captain_bathia_q0708_03.htm";
				if(st.getMemoState() / 10 == 3)
					return "npchtm:captain_bathia_q0708_04.htm";
				if(st.getMemoState() / 10 == 4)
					return "npchtm:captain_bathia_q0708_06.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == chamberlain_saius)
		{
			if(reply == 708)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(81).hasLord())
				{
					st.setMemoState(1);
					st.setMemoStateEx(1, Util.getCurrentTime());
					showQuestPage("chamberlain_saius_q0708_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_MIDDLE);
					startQuestTimer("npc_say", 60000, npc, null, true);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(81).hasLord())
				{
					showQuestPage("chamberlain_saius_q0708_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2 && !TerritoryWarManager.getTerritoryById(81).hasLord())
				{
					st.setMemoState(3);
					showPage("chamberlain_saius_q0708_08.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null && npc.isInRange(c0, 1500) && c0.isQuestStarted(708) && c0.getQuestState(708).getMemoState() == 3 && npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
				{
					showHtmlFile(talker, "chamberlain_saius_q0708_12.htm", new String[] { "<?name?>" }, new String[] { talker.getName() }, false);
					Functions.npcSay(npc, Say2C.ALL, 70852, talker.getName());
					QuestState qs = c0.getQuestState(708);
					qs.setMemoState(4);
					qs.setCond(3);
					showQuestMark(c0, 708);
					qs.playSound(SOUND_MIDDLE);
					qs.setMemoStateEx(1, talker.getObjectId());
				}
				else
				{
					showQuestPage("chamberlain_saius_q0708_13.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 49)
				{
					if(!TerritoryWarManager.getWar().isInProgress() && npc.isMyLord(talker))
					{
						Functions.npcSay(npc, Say2C.ALL, 70859, talker.getName());
						talker.setVar("territory_lord_81", "true");
						TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(81));
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("chamberlain_saius_q0708_23.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == blacksmith_pinter)
		{
			if(reply == 1)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(708);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
					{
						showQuestPage("blacksmith_pinter_q0708_04.htm", talker);
					}
				}
			}
			else if(reply == 2)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(708);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
					{
						Functions.showOnScreentMsg(c0, 2, 0, 0, 0, 1, 0, 5000, 0, 70853);
						showPage("blacksmith_pinter_q0708_05.htm", talker);
						qs.setMemoState(5);
						qs.setMemoStateEx(1, 0);
						qs.setCond(4);
						showQuestMark(c0, 708);
						qs.playSound(SOUND_MIDDLE);
					}
				}
				else
				{
					showPage("blacksmith_pinter_q0708_06.htm", talker);
				}
			}
			else if(reply == 3)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(708);
					if(qs != null && qs.isStarted() && qs.getMemoState() % 10 == 5)
					{
						int i0 = qs.getMemoState() /10;
						if( st.getQuestItemsCount(1867) >= 100 && st.getQuestItemsCount(1865) >= 100 && st.getQuestItemsCount(1869) >= 100 && st.getQuestItemsCount(1879) >= 50 )
						{
							st.takeItems(1867, 100);
							st.takeItems(1865, 100);
							st.takeItems(1869, 100);
							st.takeItems(1879, 50);
							qs.setMemoState(9 + i0 * 10);
							showPage("blacksmith_pinter_q0708_09.htm", talker);
						}
						else
						{
							showPage("blacksmith_pinter_q0708_10.htm", talker);
						}
					}
				}
				else
				{
					showPage("blacksmith_pinter_q0708_11.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == captain_bathia)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 1)
				{
					st.setMemoState(st.getMemoState() + 10);
					showPage("captain_bathia_q0708_02.htm", talker);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 3)
				{
					if(st.getMemoState() % 10 == 9)
					{
						st.takeItems(q_armor_of_dura, -1);
						st.setMemoState(st.getMemoState() + 10);
						showPage("captain_bathia_q0708_05.htm", talker);
						st.setCond(9);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						st.takeItems(q_armor_of_dura, -1);
						st.setMemoState(st.getMemoState() + 10);
						showPage("captain_bathia_q0708_05.htm", talker);
						st.setCond(8);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					Functions.npcSay(npc, Say2C.ALL, 70854);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if("npc_say".equals(event) && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 70851);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc.getNpcId() == q_duahan_of_glodio)
		{
			L2Player c0 = Util.getClanLeader(killer);
			if(c0 != null && npc.isInRange(c0, 1500) && c0.isQuestStarted(708) && c0.getQuestState(708).getMemoState() / 10 == 2)
			{
				QuestState qs = c0.getQuestState(708);
				qs.giveItems(q_armor_of_dura, 1);
				qs.setMemoState(qs.getMemoState() + 10);
				qs.setCond(7);
				showQuestMark(c0, 708);
				qs.playSound(SOUND_MIDDLE);
				Functions.npcSay(npc, Say2C.ALL, 70856);
			}
		}
		else
		{
			L2Player c0 = Util.getClanLeader(killer);
			if(c0 != null && npc.isInRange(c0, 1500))
			{
				QuestState qs = c0.getQuestState(708);
				if(qs != null && qs.isStarted() && qs.getMemoState() / 10 == 2)
				{
					int i0 = qs.getMemoStateEx(1);
					if(Rnd.chance(i0))
					{
						L2NpcInstance mob = npc.createOnePrivate(27393, null, 0, 0, npc.getX(), npc.getY(), npc.getZ(), 0, killer.getStoredId(), killer.getObjectId(), 0);
						if(mob != null && killer.isPlayer())
						{
							Functions.npcSay(mob, Say2C.ALL, 70855, killer.getName());
						}
					}
					else
					{
						qs.setMemoStateEx(1, i0 + 1);
					}
				}
			}
		}
	}
}