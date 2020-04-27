package quests._726_LightwithintheDarkness;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.Fortress;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author admin
 * @date 03.12.2010 12:47:54
 */
public class _726_LightwithintheDarkness extends Quest
{
	// NPCs
	private static final int DETENTION_CAMP_KEEPERS[] = {
			35666, // Shanty Fortress
			35698, // Southern Gludio Fortress
			35735, // Hive Fortress
			35767, // Valley Fortress
			35804, // Ivory Tower Fortress
			35835, // Narsell Fortress
			35867, // Bayou Fortress
			35904, // White Sands Fortress
			35936, // Borderland Fortress
			35974, // Swamp Fortress
			36011, // Archaic Fortress
			36043, // Floran Fortress
			36081, // Cloud Mountain
			36118, // Tanor Fortress
			36149, // Dragonspine Fortress
			36181, // Land Dragon Fortress
			36219, // Western Fortress
			36257, // Hunter's Fortress
			36294, // Aaru Fortress
			36326, // Demon Fortress
			36364, // Monastic Fortress
	};

	private static final int MOBs[] = {
			25659,
			25660,
			25661,
			25662,
			25663,
			25664
	};

	public _726_LightwithintheDarkness()
	{
		super(726, "_726_LightwithintheDarkness", "Light within the Darkness");

		addStartNpc(DETENTION_CAMP_KEEPERS);
		addKillId(MOBs);
	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		L2NpcInstance npc = st.getPlayer().getLastNpc();
		L2Player player = st.getPlayer();
		Fortress fort = (Fortress) npc.getBuilding(1);
		if(fort.getOwnerId() == 0)
			return "npchtm:gludio_fort_a_campkeeper_q0726_08.htm";

		if(event.equals("gludio_fort_a_campkeeper_q0726_02.htm"))
		{
			if(st.isCreated() && player.getLevel() >= 70 && fort.getOwnerId() > 0 && fort.getOwnerId() == player.getClanId())
				return event;
		}
		else if(event.equals("gludio_fort_a_campkeeper_q0726_05.htm"))
		{
			if(st.isCreated() && player.getLevel() >= 70 && fort.getOwnerId() > 0 && fort.getOwnerId() == player.getClanId())
			{
				st.setCond(1);
				st.set("ex_cond", 1);
				st.playSound(SOUND_ACCEPT);
				st.setState(STARTED);
				return event;
			}
		}
		else if(event.equals("gludio_fort_a_campkeeper_q0726_06.htm"))
		{
			if(fort.getOwnerId() != player.getClanId())
				return "npchtm:gludio_fort_a_campkeeper_q0726_08.htm";

			if(st.isCreated())
			{
				if(fort.getOwnerId() > 0 && fort.getOwnerId() == player.getClanId())
				{
					if(player.getLevel() >= 75)
						return "npchtm:" + event;

					return "npchtm:gludio_fort_a_campkeeper_q0726_07.htm";
				}

				return "npchtm:gludio_fort_a_campkeeper_q0726_08.htm";
			}

			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
			{
				if(InstanceManager.enterInstance(89, player, npc, 726))
					st.set("ex_cond", 1);
				return null;
			}

			if(System.currentTimeMillis() - fort.getLastSiegeDate() < 60 * 60000)
				return "npchtm:gludio_fort_a_campkeeper_q0726_13a.htm";

			if(fort.getContractCastleId() > 0)
				return "npchtm:gludio_fort_a_campkeeper_q0726_13b.htm";

			long lastTime = ServerVariables.getInt("iz89-" + fort.getId(), 0) * 1000L;
			if(lastTime > System.currentTimeMillis())
				return "npchtm:fortress_campkeeper006.htm";

			L2Party party = player.getParty();
			if(party != null)
			{
				if(party.isLeader(player))
				{
					for(L2Player member : party.getPartyMembers())
					{
						QuestState qs = member.getQuestState(getName());
						if(qs == null || !qs.isStarted())
						{
							showHtmlFile(player, "gludio_fort_a_campkeeper_q0726_12.htm", new String[]{"<\\?name\\?>"}, new String[]{member.getName()}, false);
							return null;
						}
						else if(member.getClanId() != fort.getOwnerId())
						{
							showHtmlFile(player, "gludio_fort_a_campkeeper_q0726_11.htm", new String[]{"<\\?name\\?>"}, new String[]{member.getName()}, false);
							return null;
						}
						else
							qs.set("ex_cond", 1);
					}

					if(InstanceManager.enterInstance(89, player, npc, 726))
					{
						ServerVariables.set("iz89-" + fort.getId(), (int) (System.currentTimeMillis() / 1000) + 4 * 3600);
						showHtmlFile(player, "gludio_fort_a_campkeeper_q0726_13.htm", new String[]{"<\\?pledgename\\?>"}, new String[]{player.getClan().getName()}, false);
						return null;
					}
				}
				else
				{
					showHtmlFile(player, "gludio_fort_a_campkeeper_q0726_10.htm", new String[]{"<\\?name\\?>"}, new String[]{party.getPartyLeader().getName()}, false);
					return null;
				}
			}
			else
				return "npchtm:gludio_fort_a_campkeeper_q0726_09.htm";
		}
		else if(event.equals("gludio_fort_a_campkeeper_q0726_14.htm"))
			return "npchtm:" + event;

		return null;
	}


	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		L2Player player = st.getPlayer();
		Fortress fort = (Fortress) npc.getBuilding(1);
		if(fort == null)
			return "npchtm:noquest";

		if(st.isCreated())
		{
			if(fort.getOwnerId() > 0 && fort.getOwnerId() == player.getClanId())
			{
				if(player.getLevel() >= 70)
					return "gludio_fort_a_campkeeper_q0726_01.htm";

				st.exitCurrentQuest(true);
				return "gludio_fort_a_campkeeper_q0726_04.htm";
			}
			st.exitCurrentQuest(true);
			return "gludio_fort_a_campkeeper_q0726_03.htm";
		}
		else if(st.isStarted())
		{
			if(st.getInt("ex_cond") == 1 || st.getInt("ex_cond") == 2)
				return "npchtm:gludio_fort_a_campkeeper_q0726_15.htm";
			if(st.getInt("ex_cond") == 3)
			{
				st.playSound(SOUND_FINISH);
				st.giveItems(9912, 152);
				st.exitCurrentQuest(true);
				return "npchtm:gludio_fort_a_campkeeper_q0726_16.htm";
			}
		}

		return "npchtm:noquest";
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
