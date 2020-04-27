package quests._715_PathtoBecomingaLordGoddard;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 22.03.12 22:51
 */
public class _715_PathtoBecomingaLordGoddard extends Quest
{
	// NPC
	private static final int chamberlain_alfred = 35363;

	// Mobs
	private static final int flame_spirit_nastron = 25306;
	private static final int water_spirit_ashutar = 25316;

	public _715_PathtoBecomingaLordGoddard()
	{
		super();
		addStartNpc(chamberlain_alfred);
		addTalkId(chamberlain_alfred);

		addKillId(flame_spirit_nastron, water_spirit_ashutar);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_alfred)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(87).hasLord())
					return "chamberlain_alfred_q0715_01.htm";

				return "chamberlain_alfred_q0715_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1)
					return "npchtm:chamberlain_alfred_q0715_04a.htm";
				if(st.getMemoState() / 100 == 1 && st.getMemoState() / 100 != 21)
					return "npchtm:chamberlain_alfred_q0715_07.htm";
				if(st.getMemoState() == 11)
					return "npchtm:chamberlain_alfred_q0715_08.htm";
				if(st.getMemoState() / 100 == 2 && st.getMemoState() / 100 != 22)
				{
					if(st.getMemoState() == 201)
						st.setMemoState(st.getMemoState() + 10);
					st.setCond(6);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_alfred_q0715_09.htm";
				}
				if(st.getMemoState() / 10 == 12 || st.getMemoState() / 10 == 2)
				{
					if(st.getMemoState() / 10 == 2)
						st.setMemoState(st.getMemoState() + 100);

					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_alfred_q0715_10.htm";
				}
				if(st.getMemoState() / 10 == 22 && npc.isMyLord(talker))
				{
					if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
						return "npchtm:chamberlain_alfred_q0715_11a.htm";
					if(ResidenceManager.getInstance().getBuildingById(109).getContractCastleId() != 8)
						return "npchtm:chamberlain_alfred_q0715_11b.htm";

					return "npchtm:chamberlain_alfred_q0715_11.htm";
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
		if(npc.getNpcId() == chamberlain_alfred)
		{
			if(reply == 715)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(87).hasLord())
				{
					st.setMemoState(1);
					st.playSound(SOUND_ACCEPT);
					showQuestPage("chamberlain_alfred_q0715_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(87).hasLord())
				{
					showQuestPage("chamberlain_alfred_q0715_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.setMemoState(st.getMemoState() + 100);
					showPage("chamberlain_alfred_q0715_05.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				if(st.isStarted() && st.getMemoState() == 1)
				{
					st.setMemoState(st.getMemoState() + 10);
					showPage("chamberlain_alfred_q0715_06.htm", talker);
					st.setCond(3);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() / 10 == 22 && !TerritoryWarManager.getWar().isInProgress() && npc.isMyLord(talker))
				{
					Functions.npcSay(npc, 71559, talker.getName());
					talker.setVar("territory_lord_87", "true");
					TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(87));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
					showPage("chamberlain_alfred_q0715_12.htm", talker);
				}
			}
		}
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		if(npc.getNpcId() == flame_spirit_nastron)
		{
			Functions.npcSay(npc, 71551, killer.getName());
			QuestState st;
			if(c0 != null && c0.isQuestStarted(715) && (st = c0.getQuestState(715)) != null && st.getMemoState() / 100 == 1)
			{
				if(st.getMemoState() / 10 == 12)
				{
					st.setMemoState(st.getMemoState() + 100);
					st.setCond(8);
					showQuestMark(c0);
					st.playSound(SOUND_MIDDLE);
				}
				else if(st.getMemoState() / 10 == 10)
				{
					st.setMemoState(st.getMemoState() + 100);
					st.setCond(4);
					showQuestMark(c0);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
		else if(npc.getNpcId() == water_spirit_ashutar)
		{
			Functions.npcSay(npc, 71551, killer.getName());
			QuestState st;
			if(c0 != null && c0.isQuestStarted(715) && (st = c0.getQuestState(715)) != null && st.getMemoState() / 10 % 10 == 1)
			{
				if(st.getMemoState() / 10 == 21)
				{
					st.setMemoState(st.getMemoState() + 10);
					st.setCond(9);
					showQuestMark(c0);
					st.playSound(SOUND_MIDDLE);
				}
				else if(st.getMemoState() / 10 == 1)
				{
					st.setMemoState(st.getMemoState() + 10);
					st.setCond(5);
					showQuestMark(c0);
					st.playSound(SOUND_MIDDLE);
				}
			}
		}
	}
}