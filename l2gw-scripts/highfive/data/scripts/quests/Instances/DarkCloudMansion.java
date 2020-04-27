package quests.Instances;

import javolution.util.FastList;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.model.quest.QuestState;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.MapRegionTable;

import java.util.List;

/**
 * @author rage
 * @date 20.10.2009 10:44:49
 */
public class DarkCloudMansion extends Quest
{

	public final static int BELETH_MINIONS[] = {22272, 22273, 22274};
	public final static int PARME_HEALER = 22400;
	public final static int SHADOW_COLUMN = 22402;
	public final static int BELETH_SAMPLES[] = {18371, 18372, 18373, 18374, 18375, 18376, 18377};
	public final static int CHROMATIC_CRYSTALLINE_GOLEM[] = {18369, 18370};

	public final static int YIYEN = 32282;
	public final static boolean GMTEST = false;

	public DarkCloudMansion()
	{
		super(22030, "DarkCloudMansion", "Dark Clous Mansion", true);
		addStartNpc(YIYEN);
		for(int i : BELETH_MINIONS)
		{
			addDecayId(i);
			addAttackId(i);
		}
		for(int i : BELETH_SAMPLES)
		{
			addKillId(i);
			addDecayId(i);
			addAttackId(i);
		}
		for(int i : CHROMATIC_CRYSTALLINE_GOLEM)
			addKillId(i);

		addDecayId(PARME_HEALER);
		addAttackId(PARME_HEALER);
		addDecayId(SHADOW_COLUMN);
		addAttackId(SHADOW_COLUMN);

	}

	@Override
	public String onEvent(String event, QuestState st)
	{
		if(event.equalsIgnoreCase("leave"))
		{
			L2Player player = st.getPlayer();
			player.teleToLocation(MapRegionTable.getInstance().getTeleToLocation(player, MapRegionTable.TeleportWhereType.ClosestTown), 0);
			st.exitCurrentQuest(true);
		}
		return null;
	}

	@Override
	public String onTalk(L2NpcInstance npc, QuestState st)
	{
		if(st == null)
			return "You are either not carrying out your quest or don't meet the criteria.";
		InstanceTemplate it = InstanceManager.getInstance().getInstanceTemplateById(9);
		L2Player player = st.getPlayer();
		Instance existingInstance = InstanceManager.getInstance().getInstanceByPlayer(player);
		if(existingInstance != null && existingInstance.getTemplate().getId() == 9)
		{
			if(player.getParty() != null && player.getLevel() > 77)
			{
				for(L2Player member : player.getParty().getPartyMembers())
				{
					if(!existingInstance.isInside(member.getObjectId()))
					{
						player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_ENTERED_ANOTHER_INSTANCE_ZONE_THEREFORE_YOU_CANNOT_ENTER_CORRESPONDING_DUNGEON));
						return null;
					}
				}
				player.setStablePoint(player.getLoc());
				player.teleToLocation(it.getStartLoc(), existingInstance.getReflection());
				return null;
			}
			else
				return "DarkCloudMansion-wrongparty.htm";
		}

		if(!GMTEST)
		{
			if(player.getParty() != null)
			{
				boolean canEnter = true;
				if(player.getParty().getPartyLeader() != player)
				{
					player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.ONLY_A_PARTY_LEADER_CAN_TRY_TO_ENTER));
					return null;
				}

				for(L2Player member : player.getParty().getPartyMembers())
					if(InstanceManager.getInstance().getInstanceByPlayer(member) != null)
					{
						player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_MAY_NOT_RE_ENTER_YET).addCharName(member));
						canEnter = false;
					}

				for(L2Player member : player.getParty().getPartyMembers())
					if(!npc.isInRange(member, 300))
					{
						player.getParty().broadcastToPartyMembers(new SystemMessage(SystemMessage.C1_IS_IN_A_LOCATION_WHICH_CANNOT_BE_ENTERED_THEREFORE_IT_CANNOT_BE_PROCESSED).addCharName(member));
						canEnter = false;
					}
				if(!canEnter)
					return null;
			}
			else
			{
				player.sendPacket(new SystemMessage(SystemMessage.YOU_ARE_NOT_CURRENTLY_IN_A_PARTY_SO_YOU_CANNOT_ENTER));
				return "DarkCloudMansion-wrongparty.htm";
			}

			QuestState qs = player.getQuestState("_131_BirdInACage");
			if(qs == null || (!qs.isCompleted() && qs.getInt("cond") != 2))
				return "DarkCloudMansion-wrongquest.htm";
		}
		List<L2Player> party = new FastList<L2Player>();

		if(player.getParty() != null)
			party.addAll(player.getParty().getPartyMembers());
		else
			party.add(player);

		if(!GMTEST)
		{
			for(L2Player member : party)
				if(member.getLevel() > it.getMaxLevel() || member.getLevel() < it.getMinLevel())
					return "DarkCloudMansion-wrongparty.htm";
			if(party.size() < it.getMinParty() || party.size() > it.getMaxParty())
				return "DarkCloudMansion-wrongparty.htm";
		}
		Instance inst = InstanceManager.getInstance().createNewInstance(9, party);

		for(L2Player member : party)
			if(npc.isInRange(member, 300))
			{
				member.setStablePoint(member.getLoc());
				member.teleToLocation(it.getStartLoc(), inst.getReflection());
			}

		return null;
	}

	@Override
	public void onKill(L2NpcInstance npc, L2Player player)
	{
		if(npc != null && npc.getSpawn() != null && player != null && npc.getSpawn().getInstance() != null)
			npc.getSpawn().getInstance().notifyKill(npc, player);
	}

	@Override
	public void onDecay(L2NpcInstance npc)
	{
		if(npc != null && npc.getSpawn() != null && npc.getSpawn().getInstance() != null)
			npc.getSpawn().getInstance().notifyDecayd(npc);
	}

	@Override
	public String onAttack(L2NpcInstance npc, L2Player player, L2Skill skill)
	{
		if(npc != null && npc.getSpawn() != null && player != null && npc.getSpawn().getInstance() != null)
			npc.getSpawn().getInstance().notifyAttacked(npc, player);
		return null;
	}
}
