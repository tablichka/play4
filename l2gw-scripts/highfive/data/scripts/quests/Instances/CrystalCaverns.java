package quests.Instances;

import instances.CrystalCavernsInstance;
import javolution.util.FastList;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.MapRegionTable;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.util.List;

/**
 * @author rage
 * @date 11.11.2009 10:09:13
 */
public class CrystalCaverns extends Quest
{
	private static boolean GMTEST = false;
	private static int OG_START = 32281;
	private static int CONTAMINATED_CRYSTAL = 9690;
	private static int WHITE_SEED = 9597;
	private static int BLACK_SEED = 9598;
	private static int BLUE_CRYSTAL = 9695;
	private static int RED_CRYSTAL = 9696;
	private static int CLEAR_CRYSTAL = 9697;
	private static int BLUE_CORAL_KEY = 9698;
	private static int RED_CORAL_KEY = 9699;

	private static Location parmeLoc = new Location(153544, 142232, -9744);

	public CrystalCaverns()
	{
		super(22031, "CrystalCaverns", "Crystal Caverns", true);
		addStartNpc(OG_START);
		addKillId(CrystalCavernsInstance.CASTALIA);
		addKillId(CrystalCavernsInstance.FAFURION);
		addKillId(CrystalCavernsInstance.GUARDIAN);
		addKillId(CrystalCavernsInstance.GUARDIAN_TREE);
		addKillId(CrystalCavernsInstance.POISON_MOTH);
		addKillId(CrystalCavernsInstance.GUARD);
		addKillId(CrystalCavernsInstance.STAKATO);
		addAttackId(CrystalCavernsInstance.TEARS);
		addKillId(CrystalCavernsInstance.TEARS);
		addKillId(CrystalCavernsInstance.GK_LOHAN);
		addKillId(CrystalCavernsInstance.GK_PROVO);
		addKillId(CrystalCavernsInstance.TEROD);
		addKillId(CrystalCavernsInstance.DOLPH);
		addKillId(CrystalCavernsInstance.EMERALDMOBS1);
		addKillId(CrystalCavernsInstance.EMERALDMOBS2);
		addKillId(CrystalCavernsInstance.EMERALDMOBS3);
		addKillId(CrystalCavernsInstance.DARNEL);
		addAttackId(CrystalCavernsInstance.DARNEL);
		addKillId(CrystalCavernsInstance.ES_ROOM_RB1);
		addKillId(CrystalCavernsInstance.ES_ROOM_RB2);
		addKillId(CrystalCavernsInstance.ES_ROOM_RB3);
		addKillId(CrystalCavernsInstance.SC_CAPTAIN1);
		addKillId(CrystalCavernsInstance.SC_CAPTAIN2);
		addKillId(CrystalCavernsInstance.SC_CAPTAIN3);
		addKillId(CrystalCavernsInstance.SC_CAPTAIN4);
		addKillId(CrystalCavernsInstance.SC_CAPTAIN5);
		addKillId(CrystalCavernsInstance.SC_GUARD1);
		addKillId(CrystalCavernsInstance.SC_GUARD2);
		addKillId(CrystalCavernsInstance.SC_GUARD3);
		addKillId(CrystalCavernsInstance.SC_IRIS1);
		addKillId(CrystalCavernsInstance.SC_IRIS2);
		addKillId(CrystalCavernsInstance.SC_IRIS3);
		addAttackId(CrystalCavernsInstance.KECHI);
		addKillId(CrystalCavernsInstance.KECHI);
		addKillId(CrystalCavernsInstance.BAYLOR);
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st == null)
			return "You are either not carrying out your quest or don't meet the criteria.";

		InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(10);
		L2Player player = st.getPlayer();
		Instance existingInstance = InstanceManager.getInstance().getInstanceByPlayer(player);

		if(existingInstance != null && existingInstance.getTemplate().getId() == 10)
		{
			if(player.getItemCountByItemId(CONTAMINATED_CRYSTAL) < 1)
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1S_ITEM_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
				return null;
			}
			else if(player.getLevel() < it.getMinLevel() || player.getLevel() > it.getMaxLevel())
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(player));
				return null;
			}
			else if(!npc.isInRange(player, 300))
			{
				player.sendPacket(new SystemMessage(SystemMessage.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED).addCharName(player));
				return null;
			}
			player.setStablePoint(player.getLoc());
			player.teleToLocation(it.getStartLoc(), existingInstance.getReflection());
			return null;
		}

		if(!GMTEST)
		{
			if(player.getParty() != null)
			{
				if(player.getParty().getPartyLeader() != player)
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.ONLY_A_PARTY_LEADER_CAN_TRY_TO_ENTER));
					return null;
				}

				List<L2Player> partyList = new FastList<L2Player>();

				for(L2Player member : player.getParty().getPartyMembers())
					if(member.getLevel() < it.getMinLevel() || member.getLevel() > it.getMaxLevel())
						partyList.add(member);

				if(!partyList.isEmpty())
				{
					for(L2Player member : partyList)
						player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1S_LEVEL_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(member));
					return null;
				}

				partyList.clear();
				for(L2Player member : player.getParty().getPartyMembers())
					if(member.getItemCountByItemId(CONTAMINATED_CRYSTAL) < 1)
						partyList.add(member);

				if(!partyList.isEmpty())
				{
					for(L2Player member : partyList)
						player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1S_ITEM_REQUIREMENT_IS_NOT_SUFFICIENT_AND_CANNOT_BE_ENTERED).addCharName(member));
					return null;
				}

				partyList.clear();
				for(L2Player member : player.getParty().getPartyMembers())
					if(member.getVar("instance-" + it.getType()) != null || InstanceManager.getInstance().getInstanceByPlayer(member) != null)
						partyList.add(member);

				if(!partyList.isEmpty())
				{
					for(L2Player member : partyList)
						player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(member));
					return null;
				}

				for(L2Player member : player.getParty().getPartyMembers())
					if(!npc.isInRange(member, 300))
					{
						player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED).addCharName(member));
						return null;
					}
			}
			else
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER));
				return "CrystalCaverns-wrongparty.htm";
			}
		}

		List<L2Player> party = new FastList<L2Player>();
		if(player.getParty() != null)
			party.addAll(player.getParty().getPartyMembers());
		else
			party.add(player);

		Instance inst = InstanceManager.getInstance().createNewInstance(10, party);

		for(L2Player member : party)
			if(npc.isInRange(member, 300))
			{
				if(member.getItemCountByItemId(BLUE_CORAL_KEY) > 0)
					member.destroyItemByItemId("CrystalCavernsTeleporter", BLUE_CORAL_KEY, member.getItemCountByItemId(BLUE_CORAL_KEY), npc, true);
				if(member.getItemCountByItemId(RED_CORAL_KEY) > 0)
					member.destroyItemByItemId("CrystalCavernsTeleporter", RED_CORAL_KEY, member.getItemCountByItemId(RED_CORAL_KEY), npc, true);
				member.setStablePoint(member.getLoc());
				member.teleToLocation(it.getStartLoc(), inst.getReflection());
			}

		return null;
	}

	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("CoralGarden"))
		{
			L2Player player = st.getPlayer();
			CrystalCavernsInstance inst = (CrystalCavernsInstance) InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.enterCoralGarden();
		}
		else if(event.equalsIgnoreCase("SCandES"))
		{
			L2Player player = st.getPlayer();
			CrystalCavernsInstance inst = (CrystalCavernsInstance) InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst != null)
				inst.enterSCandES();
		}
		else if(event.equalsIgnoreCase("leave"))
		{
			L2Player player = st.getPlayer();
			if(player != null)
				if(player.getParty() != null)
					for(L2Player member : player.getParty().getPartyMembers())
					{
						if(member != null)
							member.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(member, MapRegionTable.TeleportWhereType.ClosestTown), 0);
					}
				else
					player.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(player, MapRegionTable.TeleportWhereType.ClosestTown), 0);

			st.exitCurrentQuest(true);
		}
		else if(event.equalsIgnoreCase("corridor2"))
		{
			L2Player player = st.getPlayer();
			if(player != null && player.getParty() != null)
			{
				CrystalCavernsInstance inst = (CrystalCavernsInstance) InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					inst.enterSCRoom2();
					Location loc = new Location(147462, 152606, -12186);
					L2NpcInstance npc = player.getLastNpc();
					for(L2Player member : player.getParty().getPartyMembers())
						if(npc.isInRange(member, 300))
							member.teleToLocation(loc);
				}
			}
		}
		else if(event.equalsIgnoreCase("corridor3"))
		{
			L2Player player = st.getPlayer();
			if(player != null && player.getParty() != null)
			{
				CrystalCavernsInstance inst = (CrystalCavernsInstance) InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					inst.enterSCRoom3();
					Location loc = new Location(150159, 152609, -12190);
					L2NpcInstance npc = player.getLastNpc();
					for(L2Player member : player.getParty().getPartyMembers())
						if(npc.isInRange(member, 300))
							member.teleToLocation(loc);
				}
			}
		}
		else if(event.equalsIgnoreCase("corridor4"))
		{
			L2Player player = st.getPlayer();
			if(player != null && player.getParty() != null)
			{
				CrystalCavernsInstance inst = (CrystalCavernsInstance) InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					inst.enterSCRoom4();
					Location loc = new Location(149799, 149982, -12187);
					L2NpcInstance npc = player.getLastNpc();
					for(L2Player member : player.getParty().getPartyMembers())
						if(npc.isInRange(member, 300))
							member.teleToLocation(loc);
				}
			}
		}
		else if(event.equalsIgnoreCase("baylor"))
		{
			L2Player player = st.getPlayer();
			L2Party party = Util.getParty(player);
			if(player != null && party != null)
			{
				CrystalCavernsInstance inst = (CrystalCavernsInstance) InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst != null)
				{
					L2NpcInstance npc = player.getLastNpc();
					for(L2Player member : party.getPartyMembers())
					{
						if(member.getReflection() == npc.getReflection())
						{
							if(npc.isInRange(member, 1000))
							{
								if(member.getItemCountByItemId(BLUE_CRYSTAL) < 1)
								{
									Functions.npcSay(npc, Say2C.SHOUT, 1800028, member.getName());
									return null;
								}
								if(member.getItemCountByItemId(RED_CRYSTAL) < 1)
								{
									Functions.npcSay(npc, Say2C.SHOUT, 1800027, member.getName());
									return null;
								}
								if(member.getItemCountByItemId(CLEAR_CRYSTAL) < 1)
								{
									Functions.npcSay(npc, Say2C.SHOUT, 1800029, member.getName());
									return null;
								}
							}
							else
							{
								Functions.npcSay(npc, Say2C.SHOUT, 1800030, member.getName());
								return null;
							}
						}
					}

					int a = 360 / party.getMemberCount();
					int i = 0;
					for(L2Player member : party.getPartyMembers())
					{
						if(npc.isInRange(member, 300))
						{
							member.setDisabled(true);
							Location h = Util.getPointInRadius(CrystalCavernsInstance.BAYLOR_CENTER, 200, a * i);
							Location t = Util.getPointInRadius(CrystalCavernsInstance.BAYLOR_CENTER, 100, a * i);
							member.setHeading((int) (Math.atan2(t.getY() - h.getY(), t.getX() - h.getX()) * 10430.378350470452724949566316381) + 32768);
							member.teleToLocation(t);
						}
						i++;
					}
					inst.enterBaylor();
				}
			}
			else
				return "CrystalCaverns-nobaylor.htm";
		}
		else if(event.equalsIgnoreCase("parme"))
		{
			L2Player player = st.getPlayer();
			QuestState qsB = player.getQuestState("_131_BirdInACage");
			if(qsB != null && qsB.getInt("cond") == 2)
			{
				player.teleToLocation(parmeLoc);
			}
			else
				return "CrystalCaverns-noparme.htm";
		}
		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player killer)
	{
		if(npc != null && npc.getSpawn() != null && killer != null && npc.getSpawn().getInstance() != null)
		{
			npc.getSpawn().getInstance().notifyKill(npc, killer);
			if(npc.getNpcId() == CrystalCavernsInstance.TEARS)
			{
				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
						if(member != null && member.isInRange(npc, 1000) && member.destroyItemByItemId("CrystalCaverns", CONTAMINATED_CRYSTAL, 1, npc, true))
						{
							member.addItem("CrystalCaverns", CLEAR_CRYSTAL, 1, npc, true);
							member.addItem("CrystalCaverns", BLACK_SEED, Rnd.get(1, (int) Config.RATE_QUESTS_DROP_REWARD), npc, true);
						}
			}
			else if(npc.getNpcId() == CrystalCavernsInstance.DARNEL)
			{
				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
						if(member != null && member.isInRange(npc, 1000) && member.destroyItemByItemId("CrystalCaverns", CONTAMINATED_CRYSTAL, 1, npc, true))
						{
							member.addItem("CrystalCaverns", BLUE_CRYSTAL, 1, npc, true);
							member.addItem("CrystalCaverns", WHITE_SEED, Rnd.get(1, (int) Config.RATE_QUESTS_DROP_REWARD), npc, true);
						}
			}
			else if(npc.getNpcId() == CrystalCavernsInstance.KECHI)
			{
				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
						if(member != null && member.isInRange(npc, 1000) && member.destroyItemByItemId("CrystalCaverns", CONTAMINATED_CRYSTAL, 1, npc, true))
						{
							member.addItem("CrystalCaverns", RED_CRYSTAL, 1, npc, true);
							member.addItem("CrystalCaverns", WHITE_SEED, Rnd.get(1, (int) Config.RATE_QUESTS_DROP_REWARD), npc, true);
						}
			}
		}
	}

	@Override
	public String onAttack(L2NpcInstance npc, L2Player player, L2Skill skill)
	{
		if(npc != null && npc.getSpawn() != null && player != null && npc.getSpawn().getInstance() != null)
			npc.getSpawn().getInstance().notifyAttacked(npc, player);
		return null;
	}
}
