package quests._713_PathtoBecomingaLordAden;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;

/**
 * @author: rage
 * @date: 10.01.12 16:26
 */
public class _713_PathtoBecomingaLordAden extends Quest
{
	// NPCs
	private static final int chamberlain_logan = 35274;
	private static final int highpriest_orven = 30857;

	// Mobs
	private static final int taik_orc_seeker = 20666;
	private static final int taik_orc_supply_leader = 20669;

	public _713_PathtoBecomingaLordAden()
	{
		super(713, "_713_PathtoBecomingaLordAden", "Path to Becoming a Lord - Aden");
		addStartNpc(chamberlain_logan);
		addTalkId(chamberlain_logan, highpriest_orven);
		addKillId(taik_orc_seeker, taik_orc_supply_leader);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();

		if(npc.getNpcId() == chamberlain_logan)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(TerritoryWarManager.getTerritoryById(85).hasLord())
				{
					return "chamberlain_logan_q0713_02.htm";
				}
				else
				{
					return "chamberlain_logan_q0713_01.htm";
				}
			}
			if(st.isStarted())
			{
				if(st.getMemoState() > 0 && st.getMemoState() < 1000)
					return "npchtm:chamberlain_logan_q0713_04.htm";
				if(st.getMemoState() == 1000 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_logan_q0713_05a.htm";
					if(ResidenceManager.getInstance().getBuildingById(106).getContractCastleId() != 5 || ResidenceManager.getInstance().getBuildingById(107).getContractCastleId() != 5)
						return "npchtm:chamberlain_logan_q0713_05b.htm";

					return "npchtm:chamberlain_logan_q0713_05.htm";
				}
			}
		}
		else if(npc.getNpcId() == highpriest_orven)
		{
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && npc.isMyLord(talker))
					return "npchtm:highpriest_orven_q0713_01.htm";
				if(st.getMemoState() % 100 == 2 && st.getMemoState() / 100 < 5 && npc.isMyLord(talker))
					return "npchtm:highpriest_orven_q0713_04.htm";
				if(st.getMemoState() % 100 == 12 && st.getMemoState() / 100 < 5 && npc.isMyLord(talker))
					return "npchtm:highpriest_orven_q0713_05.htm";
				if(st.getMemoState() % 100 == 2 && st.getMemoState() / 100 >= 5 && npc.isMyLord(talker))
					return "npchtm:highpriest_orven_q0713_06.htm";
				if(st.getMemoState() % 100 == 12 && st.getMemoState() / 100 >= 5 && npc.isMyLord(talker))
				{
					st.setMemoState(1000);
					st.setCond(7);
					st.playSound(SOUND_MIDDLE);
					showQuestMark(talker);
					return "npchtm:highpriest_orven_q0713_07.htm";
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

		if(npc.getNpcId() == chamberlain_logan)
		{
			if(reply == 713)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(85).hasLord())
				{
					st.setMemoState(1);
					st.setCond(1);
					st.setState(STARTED);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("chamberlain_logan_q0713_03.htm", talker);
				}
			}
			else if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1000 && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(85).hasLord())
				{
					Functions.npcSay(npc, Say2C.ALL, 71351, talker.getName());
					talker.setVar("territory_lord_85", "true");
					TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(85));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("chamberlain_logan_q0713_06.htm", talker);
				}
			}
		}
		else if(npc.getNpcId() == highpriest_orven)
		{
			if(reply == 1)
			{
				if(st.isStarted() && st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					showPage("highpriest_orven_q0713_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					st.setMemoState(2);
					showPage("highpriest_orven_q0713_03.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_FINISH);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Clan clan = killer.getClan();
		if(clan != null)
		{
			L2Player c0 = clan.getLeader().getPlayer();
			if(c0 != null)
			{
				QuestState st = c0.getQuestState(713);
				if(st != null && st.isStarted() && st.getMemoState() % 100 == 2)
				{
					int i0 = st.getMemoState();
					int i1 = st.getInt("ex_1");
					if(i1 >= 99)
					{
						st.setMemoState(i0 + 10);
						if(st.getMemoState() / 100 < 5)
						{
							st.setCond(3);
						}
						else if(st.getMemoState() / 100 >= 5)
						{
							st.setCond(5);
						}
						showQuestMark(c0);
						st.playSound(SOUND_MIDDLE);
					}

					st.set("ex_1", i1 + 1);
				}
			}
		}
	}
}