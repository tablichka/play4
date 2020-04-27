package quests._712_PathtoBecomingaLordOren;

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
 * @date: 24.03.12 10:46
 */
public class _712_PathtoBecomingaLordOren extends Quest
{
	// NPC
	private static final int chamberlain_brasseur = 35226;
	private static final int marty = 30169;
	private static final int warehouse_chief_croop = 30676;
	private static final int yan = 30176;

	public _712_PathtoBecomingaLordOren()
	{
		super();
		addStartNpc(chamberlain_brasseur);
		addStartNpc(marty);
		addStartNpc(yan);
		addTalkId(chamberlain_brasseur, marty, warehouse_chief_croop, yan);

		addKillId(20161, 20575, 20576, 21261);
		addQuestItem(13851);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_brasseur)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(84).hasLord())
					return "chamberlain_brasseur_q0712_01.htm";

				return "chamberlain_brasseur_q0712_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					if(Util.getCurrentTime() - st.getMemoStateEx(1) < 60)
						return "npchtm:chamberlain_brasseur_q0712_05.htm";
					
					st.setMemoState(3);
					st.setMemoStateEx(1, 0);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_brasseur_q0712_06.htm";
				}
				if(st.getMemoState() == 2 && npc.isMyLord(talker))
				{
					st.setMemoState(3);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_brasseur_q0712_07.htm";
				}
				if(st.getMemoState() == 3 && npc.isMyLord(talker))
					return "npchtm:chamberlain_brasseur_q0712_08.htm";
				if(st.getMemoState() > 3 && st.getMemoState() < 9 && npc.isMyLord(talker))
					return "npchtm:chamberlain_brasseur_q0712_09.htm";
				if(st.getMemoState() == 9 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_brasseur_q0712_10a.htm";
					if(ResidenceManager.getInstance().getBuildingById(105).getContractCastleId() != 4)
						return "npchtm:chamberlain_brasseur_q0712_10b.htm";

					return "npchtm:chamberlain_brasseur_q0712_10.htm";
				}
			}
		}
		else if(npc.getNpcId() == marty)
		{
			if(!npc.isMyLord(talker) && npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(712);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
						return "npchtm:marty_q0712_01.htm";
					if(qs != null && qs.isStarted() && qs.getMemoState() == 5)
						return "npchtm:marty_q0712_03.htm";
				}
			}
		}
		else if(npc.getNpcId() == warehouse_chief_croop)
		{
			if(st.isStarted() && npc.isMyLord(talker))
			{
				if(st.getMemoState() == 3)
					return "npchtm:warehouse_chief_croop_q0712_01.htm";
				if(st.getMemoState() == 4)
					return "npchtm:warehouse_chief_croop_q0712_04.htm";
				if(st.getMemoState() == 5)
					return "npchtm:warehouse_chief_croop_q0712_05.htm";
				if(st.getMemoState() == 6)
					return "npchtm:warehouse_chief_croop_q0712_08.htm";
				if(st.getMemoState() == 7)
				{
					if(st.getQuestItemsCount(13851) == 0)
					{
						st.giveItems(13851, 1);
						return "npchtm:warehouse_chief_croop_q0712_10.htm";
					}

					return "npchtm:warehouse_chief_croop_q0712_11.htm";
				}
				if(st.getMemoState() == 8)
					return "npchtm:warehouse_chief_croop_q0712_12.htm";
				if(st.getMemoState() == 9)
					return "npchtm:warehouse_chief_croop_q0712_14.htm";
			}
		}
		else if(npc.getNpcId() == yan)
		{
			if(!npc.isMyLord(talker) && npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(712);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 5)
						return "npchtm:yan_q0712_01.htm";
					else if(qs != null && qs.getMemoState() >= 5)
						return "npchtm:yan_q0712_03.htm";
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
		if(npc.getNpcId() == chamberlain_brasseur)
		{
			if(reply == 712)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(84).hasLord())
				{
					st.setMemoState(1);
					st.setMemoStateEx(1, Util.getCurrentTime());
					st.playSound(SOUND_ACCEPT);
					showPage("chamberlain_brasseur_q0712_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					startQuestTimer("npc_say", 60000, npc, null, true);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(84).hasLord())
				{
					showQuestPage("chamberlain_brasseur_q0712_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 9 && npc.isMyLord(talker) && !TerritoryWarManager.getWar().isInProgress())
				{
					showHtmlFile(talker, "chamberlain_brasseur_q0712_11.htm", new String[]{ "<?name?>" }, new String[]{ talker.getName() }, false);
					Functions.npcSay(npc, 71259, talker.getName());
					talker.setVar("territory_lord_84", "true");
					TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(84));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
				}
			}
		}
		else if(npc.getNpcId() == marty)
		{
			if(reply == 1 && !npc.isMyLord(talker) && npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(712);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
					{
						qs.setMemoState(5);
						showPage("marty_q0712_02.htm", talker);
						qs.setCond(4);
						showQuestMark(c0);
						qs.playSound(SOUND_MIDDLE);
					}
				}
			}
		}
		else if(npc.getNpcId() == warehouse_chief_croop)
		{
			if(reply == 1)
			{
				if(st.isStarted() && npc.isMyLord(talker) && st.getMemoState() == 3)
				{
					showPage("warehouse_chief_croop_q0712_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && npc.isMyLord(talker) && st.getMemoState() == 3)
				{
					st.setMemoState(4);
					showPage("warehouse_chief_croop_q0712_03.htm", talker);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && npc.isMyLord(talker) && st.getMemoState() == 6)
				{
					st.giveItems(13851, 1);
					st.setMemoState(7);
					showPage("warehouse_chief_croop_q0712_09.htm", talker);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() == 8)
				{
					st.takeItems(13851, -1);
					st.setMemoState(9);
					showPage("warehouse_chief_croop_q0712_13.htm", talker);
					st.setCond(8);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == yan)
		{
			if(reply == 1)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(712);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 5)
					{
						qs.setMemoState(6);
						showPage("yan_q0712_02.htm", talker);
						qs.setCond(5);
						showQuestMark(c0);
						qs.playSound(SOUND_MIDDLE);
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
			Functions.npcSay(npc, Say2C.ALL, 71251);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		if(c0 != null && npc.isInRange(c0, 1500))
		{
			QuestState st = c0.getQuestState(712);
			if(st != null && st.isStarted() && st.getMemoState() == 7 && st.rollAndGiveLimited(13851, 1, 100, 300))
			{
				if(st.getQuestItemsCount(13851) >= 300)
				{
					st.setMemoState(8);
					st.setCond(7);
					showQuestMark(c0);
					st.playSound(SOUND_MIDDLE);
				}
				else 
					st.playSound(SOUND_ITEMGET);
			}
		}
	}
}