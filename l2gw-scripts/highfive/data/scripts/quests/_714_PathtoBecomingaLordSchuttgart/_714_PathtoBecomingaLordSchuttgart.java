package quests._714_PathtoBecomingaLordSchuttgart;

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
 * @date: 22.03.12 20:21
 */
public class _714_PathtoBecomingaLordSchuttgart extends Quest
{
	// NPC
	private static final int chamberlain_august = 35555;
	private static final int head_blacksmith_newyear = 31961;
	private static final int warehouse_chief_yaseni = 31958;

	public _714_PathtoBecomingaLordSchuttgart()
	{
		super(714, "_714_PathtoBecomingaLordSchuttgart", "Path to Becoming a Lord Schuttgart");
		addStartNpc(chamberlain_august);
		addTalkId(chamberlain_august, head_blacksmith_newyear, warehouse_chief_yaseni);

		addKillId(22812, 22809, 22810, 22811);
		addQuestItem(17162);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_august)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(89).hasLord())
					return "chamberlain_august_q0714_01.htm";

				return "chamberlain_august_q0714_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
				{
					if(Util.getCurrentTime() - st.getMemoStateEx(1) < 60)
						return "npchtm:chamberlain_august_q0714_05.htm";
					
					st.setMemoState(2);
					st.setMemoStateEx(1, 0);
					return "npchtm:chamberlain_august_q0714_06.htm";
				}
				if(st.getMemoState() == 2)
					return "npchtm:chamberlain_august_q0714_07.htm";
				if(st.getMemoState() > 2 && st.getMemoState() < 5)
					return "npchtm:chamberlain_august_q0714_09.htm";
				if(st.getMemoState() == 5)
					return "npchtm:chamberlain_august_q0714_10.htm";
				if(st.getMemoState() > 5 && st.getMemoState() < 8)
					return "npchtm:chamberlain_august_q0714_11.htm";
				if(st.getMemoState() == 8 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_august_q0714_12a.htm";
					if(ResidenceManager.getInstance().getBuildingById(111).getContractCastleId() != 9)
						return "npchtm:chamberlain_august_q0714_12b.htm";

					return "npchtm:chamberlain_august_q0714_12.htm";
				}
			}
		}
		else if(npc.getNpcId() == head_blacksmith_newyear)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() >= 3 && st.getMemoState() <= 4 && talker.isQuestComplete(120))
				{
					st.setMemoState(5);
					st.setCond(4);
					showQuestMark(talker);
					return "npchtm:head_blacksmith_newyear_q0714_01.htm";
				}
				if(st.getMemoState() == 3 && !talker.isQuestComplete(120))
					return "npchtm:head_blacksmith_newyear_q0714_02.htm";
				if(st.getMemoState() == 4 && !talker.isQuestComplete(120))
				{
					if(!talker.isQuestComplete(121))
						return "npchtm:head_blacksmith_newyear_q0714_07.htm";
					if(!talker.isQuestComplete(114))
						return "npchtm:head_blacksmith_newyear_q0714_08.htm";

					return "npchtm:head_blacksmith_newyear_q0714_09.htm";
				}
			}
		}
		else if(npc.getNpcId() == warehouse_chief_yaseni)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 5 && npc.isMyLord(talker))
					return "npchtm:warehouse_chief_yaseni_q0714_01.htm";
				if(st.getMemoState() == 6 && npc.isMyLord(talker))
					return "npchtm:warehouse_chief_yaseni_q0714_03.htm";
				if(st.getMemoState() == 7 && npc.isMyLord(talker))
				{
					st.takeItems(17162, -1);
					st.setMemoState(8);
					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:warehouse_chief_yaseni_q0714_04.htm";
				}
				if(st.getMemoState() == 8 && npc.isMyLord(talker))
					return "npchtm:warehouse_chief_yaseni_q0714_05.htm";
			}
		}

		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == chamberlain_august)
		{
			if(reply == 714)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(89).hasLord())
				{
					st.setMemoState(1);
					st.setMemoStateEx(1, Util.getCurrentTime());
					st.playSound(SOUND_ACCEPT);
					showQuestPage("chamberlain_august_q0714_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					startQuestTimer("npc_say", 60000, npc, null, true);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(89).hasLord())
				{
					showQuestPage("chamberlain_august_q0714_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2 && npc.isMyLord(talker))
				{
					st.setMemoState(3);
					showPage("chamberlain_august_q0714_08.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 8 && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(89).hasLord())
				{
					showHtmlFile(talker, "chamberlain_august_q0714_13.htm", new String[]{ "<?name?>" }, new String[]{ talker.getName() }, false);
					Functions.npcSay(npc, 71459, talker.getName());
					talker.setVar("territory_lord_89", "true");
					TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(89));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
				}

			}
		}
		else if(npc.getNpcId() == head_blacksmith_newyear)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 3 && !talker.isQuestComplete(120))
				{
					showPage("head_blacksmith_newyear_q0714_03.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 3 && npc.isMyLord(talker))
				{
					if(!talker.isQuestComplete(121))
					{
						showPage("head_blacksmith_newyear_q0714_04.htm", talker);
					}
					else if(!talker.isQuestComplete(114))
					{
						showPage("head_blacksmith_newyear_q0714_05.htm", talker);
					}
					else if(!talker.isQuestComplete(120))
					{
						showPage("head_blacksmith_newyear_q0714_06.htm", talker);
					}
					st.setMemoState(4);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == warehouse_chief_yaseni)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 5 && npc.isMyLord(talker))
				{
					st.setMemoState(6);
					showPage("warehouse_chief_yaseni_q0714_02.htm", talker);
					st.setCond(5);
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
			Functions.npcSay(npc, Say2C.ALL, 71451);
		}
		return null;
	}
	
	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		QuestState st;
		if(c0 != null && npc.isInRange(c0, 1500) && (st = c0.getQuestState(714)) != null && st.getMemoState() == 6)
		{
			int i0 = st.getMemoStateEx(1);
			if(i0 > 0 && i0 != st.getQuestItemsCount(17162))
			{
				st.takeItems(17162, -1);
				st.giveItems(17162, i0);
				st.playSound(SOUND_ITEMGET);
			}
			if(st.rollAndGiveLimited(17162, 1, 100, 300))
			{
				i0 = (int) st.getQuestItemsCount(17162); 
				st.setMemoStateEx(1, i0);
				if(i0 < 300)
					st.playSound(SOUND_ITEMGET);
				else
				{
					st.setMemoState(7);
					st.setCond(6);
					showQuestMark(st.getPlayer());
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}
}