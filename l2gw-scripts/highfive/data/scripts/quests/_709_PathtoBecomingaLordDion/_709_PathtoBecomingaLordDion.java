package quests._709_PathtoBecomingaLordDion;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 18.01.12 14:58
 */
public class _709_PathtoBecomingaLordDion extends Quest
{
	// NPC
	private static final int chamberlain_crosby = 35142;
	private static final int scroll_seller_rouke = 31418;
	private static final int sophia = 30735;

	// Mobs
	private static final int[] manadragoras = new int[]{20154, 20155, 20156, 20223};
	private static final int[] ol_mahums = new int[]{20211, 20207, 20549, 20208, 20210, 20063, 20209, 20547};
	private static final int q_bloody_senior = 27392;

	// Items
	private static final int q_root_of_mandra = 13849;
	private static final int q_black_badge_od_bloodyaxe = 13850;

	public _709_PathtoBecomingaLordDion()
	{
		super();
		addStartNpc(chamberlain_crosby);
		addStartNpc(scroll_seller_rouke);
		addTalkId(chamberlain_crosby, scroll_seller_rouke, sophia);

		addKillId(manadragoras);
		addKillId(ol_mahums);
		addKillId(q_bloody_senior);
		addQuestItem(q_root_of_mandra, q_black_badge_od_bloodyaxe);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_crosby)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(82).hasLord())
					return "chamberlain_crosby_q0709_01.htm";

				return "chamberlain_crosby_q0709_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					if(Util.getCurrentTime() - st.getMemoStateEx(1) < 60)
						return "npchtm:chamberlain_crosby_q0709_05.htm";

					st.setMemoState(2);
					st.setMemoStateEx(1, 0);
					return "npchtm:chamberlain_crosby_q0709_06.htm";
				}
				if(st.getMemoState() == 2 && npc.isMyLord(talker))
					return "npchtm:chamberlain_crosby_q0709_07.htm";
				if(st.getMemoState() == 3)
					return "npchtm:chamberlain_crosby_q0709_14.htm";
				if(st.getMemoState() == 4)
					return "npchtm:chamberlain_crosby_q0709_15.htm";
				if(st.getMemoState() == 5)
				{
					st.setMemoState(st.getMemoState() + 10);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_crosby_q0709_16.htm";
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
					return "npchtm:chamberlain_crosby_q0709_17.htm";
				}
				if(st.getMemoState() / 10 == 1 && st.getMemoState() % 10 != 9)
					return "npchtm:chamberlain_crosby_q0709_18.htm";
				if((st.getMemoState() / 10 == 2 || st.getMemoState() / 10 == 3) && st.getMemoState() % 10 != 9)
					return "npchtm:chamberlain_crosby_q0709_19.htm";
				if((st.getMemoState() / 10 == 2 || st.getMemoState() / 10 == 3) && st.getMemoState() % 10 == 9)
					return "npchtm:chamberlain_crosby_q0709_20.htm";
				if(st.getMemoState() / 10 == 4 && st.getMemoState() % 10 != 9)
					return "npchtm:chamberlain_crosby_q0709_21.htm";
				if(st.getMemoState() / 10 == 4 && st.getMemoState() % 10 == 9 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_crosby_q0709_22a.htm";
					if(ResidenceManager.getInstance().getBuildingById(103).getContractCastleId() != 2)
						return "npchtm:chamberlain_crosby_q0709_22b.htm";

					return "npchtm:chamberlain_crosby_q0709_22.htm";
				}
			}
			if(!npc.isMyLord(talker))
			{
				if(npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
				{
					L2Player c0 = Util.getClanLeader(talker);
					if(c0 != null)
					{
						QuestState qs = c0.getQuestState(709);
						if(qs != null)
						{
							if(qs.isStarted() && qs.getMemoState() == 3)
							{
								if(npc.isInRange(c0, 1500))
									return "npchtm:chamberlain_crosby_q0709_11.htm";

								return "npchtm:chamberlain_crosby_q0709_10.htm";
							}
							if(qs.isStarted() && qs.getMemoState() == 4)
								return "npchtm:chamberlain_crosby_q0709_13a.htm";
						}
						return "npchtm:chamberlain_crosby_q0709_09c.htm";
					}
					else
						return "npchtm:chamberlain_crosby_q0709_09b.htm";
				}
				else
					return "npchtm:chamberlain_crosby_q0709_09a.htm";
			}
		}
		else if(npc.getNpcId() == scroll_seller_rouke)
		{
			if(!npc.isMyLord(talker))
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(709);
					if(qs != null)
					{
						if(qs.isStarted() && qs.getMemoState() == 4)
						{
							if(talker.getObjectId() == qs.getMemoStateEx(1))
								return "npchtm:scroll_seller_rouke_q0709_03.htm";

							return "scroll_seller_rouke_q0709_02a.htm";
						}
						if(qs.getMemoState() % 10 == 5)
						{
							if(talker.getObjectId() == qs.getMemoStateEx(1))
							{
								if(st.getQuestItemsCount(q_root_of_mandra) >= 100)
									return "npchtm:scroll_seller_rouke_q0709_08.htm";

								return "npchtm:scroll_seller_rouke_q0709_07.htm";
							}
							return "npchtm:scroll_seller_rouke_q0709_07a.htm";
						}
					}
					else
						return "npchtm:scroll_seller_rouke_q0709_02.htm";
				}

				return "npchtm:scroll_seller_rouke_q0709_01.htm";
			}
			if(st.isStarted() && st.getMemoState() % 10 == 9)
				return "npchtm:scroll_seller_rouke_q0709_12.htm";
			if(npc.isMyLord(talker))
				return "npchtm:scroll_seller_rouke_q0709_13.htm";
		}
		else if(npc.getNpcId() == sophia)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() / 10 == 1 && npc.isMyLord(talker))
					return "npchtm:sophia_q0709_01.htm";
				if(st.getMemoState() / 10 == 2 && npc.isMyLord(talker))
					return "npchtm:sophia_q0709_03.htm";
				if(st.getMemoState() / 10 == 3 && npc.isMyLord(talker))
					return "npchtm:sophia_q0709_04.htm";
				if(st.getMemoState() / 10 == 4 && npc.isMyLord(talker))
					return "npchtm:sophia_q0709_07.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == chamberlain_crosby)
		{
			if(reply == 709)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(82).hasLord())
				{
					st.setMemoState(1);
					st.setMemoStateEx(1, Util.getCurrentTime());
					st.playSound(SOUND_ACCEPT);
					showQuestPage("chamberlain_crosby_q0709_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					startQuestTimer("npc_say", 60000, npc, null, true);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(82).hasLord())
				{
					showQuestPage("chamberlain_crosby_q0709_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2)
				{
					st.setMemoState(3);
					showPage("chamberlain_crosby_q0709_08.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(709);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 3)
					{
						if(npc.isInRange(c0, 1500))
						{
							showHtmlFile(talker, "chamberlain_crosby_q0709_12.htm", new String[]{"<?name?>"}, new String[]{talker.getName()}, true);
							Functions.npcSay(npc, Say2C.ALL, 70952, talker.getName());
							qs.setMemoState(4);
							qs.setMemoStateEx(1, talker.getObjectId());
							qs.setCond(3);
							showQuestMark(c0, 709);
							qs.playSound(SOUND_MIDDLE);
						}
						else
						{
							showPage("chamberlain_crosby_q0709_13.htm", talker);
						}
					}
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 49)
				{
					if(!TerritoryWarManager.getWar().isInProgress() && npc.isMyLord(talker))
					{
						Functions.npcSay(npc, Say2C.ALL, 70959, talker.getName());
						talker.setVar("territory_lord_82", "true");
						TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(82));
						st.exitCurrentQuest(true);
						st.playSound(SOUND_FINISH);
						showPage("chamberlain_crosby_q0709_23.htm", talker);
					}
				}
			}
		}
		else if(npc.getNpcId() == scroll_seller_rouke)
		{
			if(reply == 1)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(709);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
					{
						showPage("scroll_seller_rouke_q0709_04.htm", talker);
					}
				}
			}
			else if(reply == 2)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(709);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
					{
						qs.setMemoState(5);
						showPage("scroll_seller_rouke_q0709_05.htm", talker);
						qs.setCond(4);
						showQuestMark(c0, 709);
						qs.playSound(SOUND_MIDDLE);
					}
				}
				else
				{
					showPage("scroll_seller_rouke_q0709_06.htm", talker);
				}
			}
			else if(reply == 3)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(709);
					if(qs != null && qs.isStarted() && qs.getMemoState() % 10 == 5)
					{
						if(st.getQuestItemsCount(q_root_of_mandra) >= 100)
						{
							qs.setMemoState(qs.getMemoState() + 4);
							showPage("scroll_seller_rouke_q0709_09.htm", talker);
							st.takeItems(q_root_of_mandra, -1);
						}
						else
						{
							showPage("scroll_seller_rouke_q0709_10.htm", talker);
						}
					}
				}
				else
				{
					showPage("scroll_seller_rouke_q0709_11.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == sophia)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 1 && npc.isMyLord(talker))
				{
					st.setMemoState(st.getMemoState() + 10);
					showPage("sophia_q0709_02.htm", talker);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 3 && npc.isMyLord(talker))
				{
					if(st.getMemoState() % 10 != 9)
					{
						st.takeItems(q_black_badge_od_bloodyaxe, -1);
						st.setMemoState(st.getMemoState() + 10);
						showPage("sophia_q0709_05.htm", talker);
						st.setCond(8);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
					else
					{
						st.takeItems(q_black_badge_od_bloodyaxe, -1);
						st.setMemoState(st.getMemoState() + 10);
						showPage("sophia_q0709_06.htm", talker);
						st.setCond(9);
						showQuestMark(talker);
						st.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if("npc_say".equals(event) && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 70951);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(contains(manadragoras, npc.getNpcId()))
		{
			L2Player c0 = Util.getClanLeader(killer);
			if(c0 != null && npc.isInRange(c0, 1500))
			{
				QuestState qs = c0.getQuestState(709);
				if(qs != null && qs.isStarted() && qs.getMemoState() % 10 == 5)
				{
					killer.addItem("Quest", q_root_of_mandra, Rnd.get(1, (int) Config.RATE_QUESTS_DROP_REWARD), npc, true);
					killer.sendPacket(new PlaySound(SOUND_ITEMGET));
				}
			}
		}
		else if(contains(ol_mahums, npc.getNpcId()))
		{
			L2Player c0 = Util.getClanLeader(killer);
			if(c0 != null && npc.isInRange(c0, 1500))
			{
				QuestState qs = c0.getQuestState(709);
				if(qs != null && qs.isStarted() && qs.getMemoState() / 10 == 2)
				{
					int i0 = qs.getMemoStateEx(2);
					if(Rnd.chance(i0))
					{
						npc.createOnePrivate(27392, null, 0, 0, npc.getX(), npc.getY(), npc.getZ(), 0, killer.getStoredId(), killer.getObjectId(), 0);
					}
					else
					{
						qs.setMemoStateEx(2, i0 + 1);
					}
				}
			}
		}
		else if(npc.getNpcId() == q_bloody_senior)
		{
			L2Player c0 = Util.getClanLeader(killer);
			if(c0 != null && npc.isInRange(c0, 1500))
			{
				QuestState qs = c0.getQuestState(709);
				if(qs != null && qs.isStarted() && qs.getMemoState() / 10 == 2)
				{
					qs.giveItems(q_black_badge_od_bloodyaxe, 1);
					qs.setMemoState(qs.getMemoState() + 10);
					qs.setCond(7);
					showQuestMark(c0, 709);
					qs.playSound(SOUND_MIDDLE);
					Functions.npcSay(npc, Say2C.ALL, 70956);
				}
			}
		}
	}
}