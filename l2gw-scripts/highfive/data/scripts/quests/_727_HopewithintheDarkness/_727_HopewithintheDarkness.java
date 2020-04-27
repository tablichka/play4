package quests._727_HopewithintheDarkness;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.commons.arrays.GArray;


public class _727_HopewithintheDarkness extends Quest
{
	private static final int WARDENS[] = {
			36403, // Gludio
			36404, // Dion
			36405, // Giran
			36406, // Oren
			36407, // Aden
			36408, // Innadril
			36409, // Goddard
			36410, // Rune
			36411, // Schuttgart
	};

	private static final int MOBS[] = {
			25653,
			25654,
			25655,
			25656,
			25657,
			25658
	};

	private static final int KE = 9912;

	public _727_HopewithintheDarkness()
	{
		super(727, "_727_HopewithintheDarkness", "Hope within the Darkness"); // Party true

		addStartNpc(WARDENS);
		addKillId(MOBS);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		String htmltext = event;

		L2NpcInstance npc = st.getPlayer().getLastNpc();
		L2Player player = st.getPlayer();

		if(event.equalsIgnoreCase("accept"))
		{
			if(player != null && player.getClanId() > 0 && player.getClanId() == npc.getCastle().getOwnerId())
			{
				st.setState(STARTED);
				st.setCond(1);
				st.set("ex_cond", 1);
				htmltext = "gludio_prison_keeper_q0727_05.htm";
				st.playSound(SOUND_ACCEPT);
			}
			else
				htmltext = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("727_1"))
		{
			if(st.isCreated() && player.getLevel() >= 80)
			{
				htmltext = "gludio_prison_keeper_q0727_02.htm";
			}
			else
				htmltext = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("727_3"))
		{
			if(st.isStarted() && st.getCond() == 1)
			{
				htmltext = "npchtm:gludio_prison_keeper_q0727_14.htm";
			}
			else
				htmltext = "npchtm:noquest";
		}
		else if(event.equalsIgnoreCase("leave"))
		{
			npc.getAIParams().set("PL", "");
			player.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(player, MapRegionTable.TeleportWhereType.ClosestTown), 0);
		}
		else if(event.equalsIgnoreCase("enter"))
		{
			int fortressUnderContract = npc.getCastle().getContractedFortressId();
			int castleId = npc.getCastle().getId();
			long nextSiegeTime = npc.getCastle().getSiege().getSiegeDate().getTimeInMillis();
			long lastTime = ServerVariables.getInt("iz80-" + castleId, 0) * 1000L;
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);

			if(inst != null)
			{
				if(InstanceManager.enterInstance(80, player, npc, 727))
					st.set("ex_cond", 1);
				return null;
			}

			if(fortressUnderContract == 0)
			{
				htmltext = "npchtm:gludio_prison_keeper_q0727_13a.htm";
			}
			else if(lastTime > System.currentTimeMillis())
			{
				htmltext = "npchtm:castle_prison_keeper006.htm";
			}
			else if((nextSiegeTime - System.currentTimeMillis()) / 1000 > 0 && (nextSiegeTime - System.currentTimeMillis()) / 1000 < 7200)
			{
				htmltext = "npchtm:castle_prison_keeper007.htm";
			}
			else if(st.isCreated())
			{
				if(player != null && player.getClanId() > 0 && player.getClanId() == npc.getCastle().getOwnerId())
				{
					if(player.getLevel() >= 75)
					{
						htmltext = "npchtm:gludio_prison_keeper_q0727_06.htm";
					}
					else
					{
						htmltext = "npchtm:gludio_prison_keeper_q0727_07.htm";
					}
				}
				else
				{
					htmltext = "npchtm:gludio_prison_keeper_q0727_08.htm";
				}
			}
			else
			{
				L2Party party = player.getParty();
				if(party != null && party.getPartyLeader() == player)
				{

					if(inst != null)
					{
						if(InstanceManager.enterInstance(80, player, player.getLastNpc(), 727))
							st.set("ex_cond", 1);
						return null;
					}

					for(L2Player member : party.getPartyMembers())
					{
						QuestState q727 = member.getQuestState(getName());
						if(q727 == null || !q727.isStarted())
						{
							showHtmlFile(player, "gludio_prison_keeper_q0727_12.htm", new String[]{"<\\?name\\?>"}, new String[]{member.getName()}, false);
							return null;
						}
						else if(member.getClanId() != npc.getCastle().getOwnerId())
						{
							showHtmlFile(player, "gludio_prison_keeper_q0727_11.htm", new String[]{"<\\?name\\?>"}, new String[]{member.getName()}, false);
							return null;
						}
					}

					if(player.getClanId() > 0 && player.getClanId() == npc.getCastle().getOwnerId())
					{
						npc.getAIParams().set("PL", party.getPartyLeader().getName());
						if(InstanceManager.enterInstance(80, player, player.getLastNpc(), 727))
						{
							ServerVariables.set("iz80-" + npc.getCastle().getId(), System.currentTimeMillis() / 1000 + 4 * 3600);
							for(L2Player member : party.getPartyMembers())
							{
								QuestState qs = member.getQuestState(getName());
								qs.set("ex_cond", 1);
							}
							showHtmlFile(player, "gludio_prison_keeper_q0727_13.htm", new String[]{"<\\?pledgename\\?>"}, new String[]{player.getClan().getName()}, false);
							return null;
						}
						else
							return null;
					}
				}
				else if(party != null && party.getPartyLeader() != player)
				{

					if(inst != null || !npc.getAIParams().getString("PL", "").equalsIgnoreCase(party.getPartyLeader().getName()))
					{
						showHtmlFile(player, "gludio_prison_keeper_q0727_11.htm", new String[]{"<\\?name\\?>"}, new String[]{party.getPartyLeader().getName()}, false);
						return null;
					}
					else
					{
						if(InstanceManager.enterInstance(80, player, player.getLastNpc(), 727))
						{
							st.set("ex_cond", 1);
							showHtmlFile(player, "gludio_prison_keeper_q0727_13.htm", new String[]{"<\\?pledgename\\?>"}, new String[]{player.getClan().getName()}, false);
							return null;
						}
						else
							return null;
					}
				}
				else
				{
					htmltext = "gludio_prison_keeper_q0727_09.htm";
				}
			}
		}

		return htmltext;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		String htmltext = "noquest";
		L2Player player = st.getPlayer();

		if(st.isCreated())
		{
			if(player != null && player.getClanId() > 0 && player.getClanId() == npc.getCastle().getOwnerId())
			{
				if(player.getLevel() >= 80)
				{
					htmltext = "gludio_prison_keeper_q0727_01.htm";
				}
				else
				{
					htmltext = "gludio_prison_keeper_q0727_04.htm";
				}
			}
			else
			{
				htmltext = "gludio_prison_keeper_q0727_03.htm";
			}
		}
		else if(st.isStarted())
		{
			if(st.getInt("ex_cond") == 1 || st.getInt("ex_cond") == 2)
				htmltext = "npchtm:gludio_prison_keeper_q0727_15.htm";
			else if(st.getInt("ex_cond") == 3)
			{
				st.playSound(SOUND_FINISH);
				st.giveItems(KE, 159);
				st.set("ex_cond", 1);
				st.exitCurrentQuest(true);
				htmltext = "npchtm:gludio_prison_keeper_q0727_16.htm";
			}
		}

		return htmltext;
	}

	@Override
	public void onKill(L2NpcInstance npc, QuestState qs)
	{
		GArray<QuestState> list = getPartyMembersWithQuest(qs.getPlayer(), 1);
		for(QuestState st : list)
			if(st.getInt("ex_cond") == 1)
				st.set("ex_cond", 2);
	}
}