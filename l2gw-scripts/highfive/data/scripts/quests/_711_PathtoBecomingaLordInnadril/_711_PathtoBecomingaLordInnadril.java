package quests._711_PathtoBecomingaLordInnadril;

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
 * @date: 23.03.12 14:14
 */
public class _711_PathtoBecomingaLordInnadril extends Quest
{
	// NPC
	private static final int chamberlain_neurath = 35316;
	private static final int iason_haine = 30969;

	public _711_PathtoBecomingaLordInnadril()
	{
		super(711, "_711_PathtoBecomingaLordInnadril", "Path to Becoming a Lord Innadril");
		
		addStartNpc(chamberlain_neurath);
		addStartNpc(iason_haine);
		addTalkId(chamberlain_neurath, iason_haine);

		addKillId(20808, 20135, 20792, 20806, 20791, 20805, 20807, 20991, 20993, 20992, 20804);
	}
	
	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player talker = st.getPlayer();
		if(npc.getNpcId() == chamberlain_neurath)
		{
			if(st.isCreated() && npc.isMyLord(talker))
			{
				if(!TerritoryWarManager.getTerritoryById(86).hasLord())
					return "chamberlain_neurath_q0711_01.htm";

				return "chamberlain_neurath_q0711_03.htm";
			}
			if(st.isStarted())
			{
				if(st.getMemoState() == 1 && npc.isMyLord(talker))
				{
					if(Util.getCurrentTime() - st.getMemoStateEx(1) < 60)
						return "npchtm:chamberlain_neurath_q0711_05.htm";

					st.setMemoState(2);
					st.setMemoStateEx(1, 0);
					return "npchtm:chamberlain_neurath_q0711_06.htm";
				}
				if(st.getMemoState() == 2 && npc.isMyLord(talker))
					return "npchtm:chamberlain_neurath_q0711_07.htm";
				if(st.getMemoState() == 3 && npc.isMyLord(talker))
					return "npchtm:chamberlain_neurath_q0711_14.htm";
				if(st.getMemoState() == 4 && npc.isMyLord(talker))
					return "npchtm:chamberlain_neurath_q0711_15.htm";
				if(st.getMemoState() == 5 && npc.isMyLord(talker))
				{
					st.setMemoState(st.getMemoState() + 1000);
					st.setCond(5);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_neurath_q0711_16.htm";
				}
				if(st.getMemoState() / 1000 >= 1 && st.getMemoState() / 1000 <= 100  && npc.isMyLord(talker))
				{
					if(st.getMemoState() % 100 >= 15)
						return "npchtm:chamberlain_neurath_q0711_17.htm";

					return "npchtm:chamberlain_neurath_q0711_18.htm";
				}
				if(st.getMemoState() / 1000 >= 101 && npc.isMyLord(talker))
				{
					if(st.getMemoState() % 100 >= 15)
					{
						if(TerritoryWarManager.getWar().isInProgress() || npc.getCastle().getSiege().isInProgress())
							return "npchtm:chamberlain_neurath_q0711_20a.htm";
						if(ResidenceManager.getInstance().getBuildingById(108).getContractCastleId() != 6)
							return "npchtm:chamberlain_neurath_q0711_20b.htm";

						return "npchtm:chamberlain_neurath_q0711_20.htm";
					}

					st.setCond(7);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
					return "npchtm:chamberlain_neurath_q0711_19.htm";
				}
			}
			if(!npc.isMyLord(talker))
			{
				if(npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
				{
					L2Player c0 = Util.getClanLeader(talker);
					if(c0 != null)
					{
						QuestState qs = c0.getQuestState(711);
						if(qs != null && qs.isStarted() && qs.getMemoState() == 3)
						{
							if(npc.isInRange(c0, 1500))
								return "npchtm:chamberlain_neurath_q0711_11.htm";

							return "npchtm:chamberlain_neurath_q0711_10.htm";
						}
						else if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
							return "npchtm:chamberlain_neurath_q0711_13a.htm";
					}
				}
				return "npchtm:chamberlain_neurath_q0711_09.htm";
			}
		}
		else if(npc.getNpcId() == iason_haine)
		{
			if(!npc.isMyLord(talker))
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(711);
					if(qs != null && qs.isStarted())
					{
						if(qs.getMemoState() <= 3)
							return "npchtm:iason_haine_q0711_02.htm";

						if(qs.getMemoState() == 4)
						{
							if(qs.getMemoStateEx(1) == talker.getObjectId())
								return "npchtm:iason_haine_q0711_03.htm";

							return "npchtm:iason_haine_q0711_03a.htm";
						}
						if(qs.getMemoState() % 100 >= 5 && qs.getMemoState() % 100 < 15)
							return "npchtm:iason_haine_q0711_07.htm";
						if(qs.getMemoState() % 100 >= 15)
						{
							if(qs.getMemoState() / 1000 < 101)
								return "npchtm:iason_haine_q0711_08.htm";

							return "npchtm:iason_haine_q0711_09.htm";
						}
					}
				}
				else
					return "npchtm:iason_haine_q0711_01.htm";
			}

		}
		
		return "noquest";
	}

	@Override
	public void onQuestSelect(int reply, QuestState st)
	{
		L2Player talker = st.getPlayer();
		L2NpcInstance npc = talker.getLastNpc();
		if(npc.getNpcId() == chamberlain_neurath)
		{
			if(reply == 711)
			{
				if(st.isCreated() && npc.isMyLord(talker) && !TerritoryWarManager.getTerritoryById(86).hasLord())
				{
					st.setMemoState(1);
					st.setMemoStateEx(1, Util.getCurrentTime());
					st.playSound(SOUND_ACCEPT);
					showQuestPage("chamberlain_neurath_q0711_04.htm", talker);
					st.setCond(1);
					st.setState(STARTED);
					startQuestTimer("npc_say", 60000, npc, null, true);
				}
			}
			else if(reply == 1)
			{
				if(st.isCreated() && npc.isMyLord(talker))
				{
					showQuestPage("chamberlain_neurath_q0711_02.htm", talker);
				}
			}
			else if(reply == 2)
			{
				if(st.isStarted() && st.getMemoState() == 2 && npc.isMyLord(talker))
				{
					st.setMemoState(3);
					showPage("chamberlain_neurath_q0711_08.htm", talker);
					st.setCond(2);
					showQuestMark(talker);
					st.playSound(SOUND_MIDDLE);
				}
			}
			else if(reply == 3)
			{
				L2Player c0 = Util.getClanLeader(talker);
				QuestState qs;
				if(c0 != null && npc.isInRange(c0, 1500) && (qs = c0.getQuestState(711)) != null && qs.isStarted() && qs.getMemoState() == 3 && npc.getCastle().getOwnerId() == talker.getClanId() && talker.getClanId() != 0)
				{
					showHtmlFile(talker, "chamberlain_neurath_q0711_12.htm", new String[]{ "<?name?>"}, new String[]{ talker.getName() }, false);
					Functions.npcSay(npc, 71152, talker.getName());
					qs.setMemoState(4);
					qs.setCond(3);
					showQuestMark(c0);
					qs.playSound(SOUND_MIDDLE);
					qs.setMemoStateEx(1, talker.getObjectId());
				}
				else
				{
					showPage("chamberlain_neurath_q0711_13.htm", talker);
				}
			}
			else if(reply == 4)
			{
				if(st.isStarted() && st.getMemoState() / 1000 >= 101 && npc.isMyLord(talker) && st.getMemoState() % 100 >= 15 && !TerritoryWarManager.getWar().isInProgress())
				{
					showHtmlFile(talker, "chamberlain_neurath_q0711_21.htm", new String[]{ "<?name?>" }, new String[]{ talker.getName() }, false);
					Functions.npcSay(npc, 71159, talker.getName());
					talker.setVar("territory_lord_86", "true");
					TerritoryWarManager.changeTerritoryLord(TerritoryWarManager.getTerritoryById(86));
					st.exitCurrentQuest(true);
					st.playSound(SOUND_FINISH);
				}
			}
		}
		else if(npc.getNpcId() == iason_haine)
		{
			if(reply == 1)
			{
				showPage("iason_haine_q0711_04.htm", talker);
			}
			else if(reply == 2)
			{
				L2Player c0 = Util.getClanLeader(talker);
				if(c0 != null)
				{
					QuestState qs = c0.getQuestState(711);
					if(qs != null && qs.isStarted() && qs.getMemoState() == 4)
					{
						qs.setMemoState(5);
						showPage("iason_haine_q0711_05.htm", talker);
						qs.setCond(4);
						showQuestMark(c0);
						qs.playSound(SOUND_MIDDLE);
					}
				}
				else
				{
					showPage("iason_haine_q0711_06.htm", talker);
				}
			}
		}
	}

	@Override
	public String onEvent(String event, L2NpcInstance npc, L2Player player)
	{
		if("npc_say".equals(event) && npc != null)
		{
			Functions.npcSay(npc, Say2C.ALL, 71151);
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		L2Player c0 = Util.getClanLeader(killer);
		QuestState st;
		if(c0 != null && npc.isInRange(c0, 1500) && (st = c0.getQuestState(711)) != null && st.isStarted() && st.getMemoState() / 1000 >= 1 && st.getMemoState() / 1000 < 101)
		{
			if(st.getMemoState() / 1000 < 100)
				st.setMemoState(st.getMemoState() + 1000);
			else
			{
				st.setMemoState(st.getMemoState() + 1000);
				st.setCond(6);
				showQuestMark(c0);
				st.playSound(SOUND_MIDDLE);
			}
		}
	}
}
