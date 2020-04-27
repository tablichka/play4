package npc.model;

import instances.DelusionChamberInstance;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.StringTokenizer;

/**
 * @author rage
 * @date 12.01.11 15:02
 * Delusion Chamber Manager
 */
public class DreamManagerInstance extends L2NpcInstance
{
	private static final int chance_item = 15311;
	private static final String _path = "data/html/instances/";
	private final String fnHi;
	private final String enter_success;
	private int room_number;

	public DreamManagerInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
		fnHi = getAIParams() != null ? getAIParams().getString("fnHi", "") : "";
		enter_success = getAIParams() != null ? getAIParams().getString("enter_success", "") : "";
	}

	@Override
	public void onBypassFeedback(L2Player player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken(); // Get actual command

		if(actualCommand.equalsIgnoreCase("instance"))
		{
			int instId = Integer.parseInt(st.nextToken());
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);

			if(InstanceManager.enterInstance(instId, player, this, 0))
			{
				if(inst == null)
				{
					long c;
					if(player.getParty() != null)
					{
						for(L2Player member : player.getParty().getPartyMembers())
							if((c = member.getItemCountByItemId(chance_item)) > 0)
								member.destroyItemByItemId("DCEnter", chance_item, c, this, true);

						player.getParty().getPartyLeader().addItem("DCEnter", chance_item, 1, this, true);
					}
					else
					{
						if((c = player.getItemCountByItemId(chance_item)) > 0)
							player.destroyItemByItemId("DCEnter", chance_item, c, this, true);
						player.addItem("DCEnter", chance_item, 1, this, true);
					}
					showChatWindow(player, _path + enter_success);
				}
			}
		}
		else if(actualCommand.equals("back"))
		{
			int back = player.getVarInt("dc");
			if(back < 0)
			{
				player.unsetVar("dc");
				player.teleToLocation(43835, -47749, -792);
			}
			else if ( back == 1 ) // Gludio
			{
				player.unsetVar("dc");
				player.teleToLocation(-14023, 123677, -3112);
			}
			else if(back == 2 ) // Dion
			{
				player.unsetVar("dc");
				player.teleToLocation(18101, 145936, -3088);
			}
			else if(back == 3 ) // Oren
			{
				player.unsetVar("dc");
				player.teleToLocation(80905, 56361, -1552);
			}
			else if(back == 4 ) // Heine
			{
				player.unsetVar("dc");
				player.teleToLocation(108469, 221690, -3592);
			}
			else if(back == 5 ) // Rune
			{
				player.unsetVar("dc");
				player.teleToLocation(42772, -48062, -792);
			}
			else if(back == 6 ) // Schutgart
			{
				player.unsetVar("dc");
				player.teleToLocation(85991, -142234, -1336);
			}
			else
			{
				player.setVar("dc", -1);
				Functions.npcSay(this, Say2C.ALL, 1600019);
			}
		}
		else if(actualCommand.equalsIgnoreCase("nextRoom"))
		{
			if(player.getParty() == null)
				showChatWindow(player, _path + "manager_dream001b.htm");
			else if(!player.getParty().isLeader(player))
				showChatWindow(player, _path + "manager_dream001a.htm");
			else if(player.getItemCountByItemId(chance_item) < 1)
				showChatWindow(player, _path + "manager_dream001c.htm");
			else if(player.destroyItemByItemId("DCNextRoom", chance_item, 1, this, true))
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst instanceof DelusionChamberInstance)
					inst.notifyEvent("next_room", null, null);
			}
		}
		else if(actualCommand.equals("party"))
		{
			Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
			if(inst instanceof DelusionChamberInstance && room_number > 0 && ((DelusionChamberInstance) inst)._currentRoom + 1 != room_number)
				inst.notifyEvent("party", null, player);
		}
		else if(actualCommand.equalsIgnoreCase("leave"))
		{
			if(player.getParty() == null)
				showChatWindow(player, _path + "manager_dream001b.htm");
			else if(!player.getParty().isLeader(player))
				showChatWindow(player, _path + "manager_dream001a.htm");
			else
			{
				Instance inst = InstanceManager.getInstance().getInstanceByPlayer(player);
				if(inst instanceof DelusionChamberInstance)
				{
					for(L2Player member : inst.getPlayersInside())
						member.teleToClosestTown();
				}
			}
		}
		else
			super.onBypassFeedback(player, command);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		room_number = getAIParams() != null ? getAIParams().getInteger("room_number", 0) : 0;
	}

	@Override
	public void showChatWindow(L2Player player, int val)
	{
		String filename = _path;

		if(val == 0)
			filename += fnHi;
		else
			filename += getNpcId() + "-" + val + ".htm";

		NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
		html.setFile(filename);

		player.setLastNpc(this);
		player.sendPacket(html);
	}

	@Override
	public boolean isAttackable(L2Character attacker, boolean forceUse, boolean sendMessage)
	{
		return room_number == 0 && forceUse;
	}

	@Override
	public L2Skill.TargetType getTargetRelation(L2Character target, boolean offensive)
	{
		return room_number > 0 ? L2Skill.TargetType.invalid : super.getTargetRelation(target, offensive);
	}
}
