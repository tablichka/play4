package commands.admin;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdminTeleport extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {
			new AdminCommandDescription("admin_show_moves", null),
			new AdminCommandDescription("admin_show_moves_other", null),
			new AdminCommandDescription("admin_show_teleport", null),
			new AdminCommandDescription("admin_teleport_to_character", null),
			new AdminCommandDescription("admin_teleportto", "usage: //teleportto <name>"),
			new AdminCommandDescription("admin_move_to", "usage: //move_to <x> <y> <z>"),
			new AdminCommandDescription("admin_teleport_character", "usage: //teleport_character <x> <y> <z>"),
			new AdminCommandDescription("admin_recall", "usage: //recall <name>"),
			new AdminCommandDescription("admin_walk", null),
			new AdminCommandDescription("admin_recall_npc", null),
			new AdminCommandDescription("admin_gonorth", "usage: //gonorth [y]"),
			new AdminCommandDescription("admin_gosouth", "usage: //gosouth [y]"),
			new AdminCommandDescription("admin_goeast", "usage: //goeast [x]"),
			new AdminCommandDescription("admin_gowest", "usage: //gowest [x]"),
			new AdminCommandDescription("admin_goup", "usage: //goup [z]"),
			new AdminCommandDescription("admin_godown", "usage: //godown [z]"),
			new AdminCommandDescription("admin_tele", null),
			new AdminCommandDescription("admin_teleto", null),
			new AdminCommandDescription("admin_failed", null),
			new AdminCommandDescription("admin_tonpc", "usage: //tonpc <npcId> or \"<npc name>\""),
			new AdminCommandDescription("admin_correct_merchants", null),
			new AdminCommandDescription("admin_sendhome", null),
			new AdminCommandDescription("admin_instant_move", null)};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(command.equals("admin_correct_merchants"))
		{
			Location[] list = new Location[10];
			list[0] = new Location(82545, 148604, -3495);
			list[1] = new Location(81930, 149193, -3495);
			list[2] = new Location(81375, 149103, -3495);
			list[3] = new Location(81290, 148618, -3495);
			list[4] = new Location(81413, 148125, -3495);
			list[5] = new Location(81923, 148013, -3495);
			list[6] = new Location(82471, 148124, -3495);
			list[7] = new Location(82477, 149107, -3495);
			list[8] = new Location(83496, 148624, -3431);
			list[9] = new Location(84300, 147409, -3431);

			for(L2Player player : L2ObjectsStorage.getAllPlayers())
				if(player.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE && !activeChar.isInOfflineMode())
				{
					Location loc = list[Rnd.get(list.length)];
					loc = Location.coordsRandomize(loc.getX(), loc.getY(), loc.getZ(), player.getHeading(), 100, 400);
					player.teleToLocation(loc);
				}

			for(L2Player player : L2ObjectsStorage.getAllPlayers())
				if(player.isInOfflineMode())
				{
					Location loc = list[Rnd.get(list.length)];
					loc = Location.coordsRandomize(loc.getX(), loc.getY(), loc.getZ(), player.getHeading(), 100, 400);
					player.decayMe();
					player.setXYZ(loc.getX(), loc.getY(), loc.getZ(), false);
					player.spawnMe();
					System.out.println(player.getName());
				}
		}
		if(command.equals("admin_teleto"))
			activeChar.setTeleMode(1);
		if(command.equals("admin_teleto r"))
			activeChar.setTeleMode(2);
		if(command.equals("admin_teleto end"))
			activeChar.setTeleMode(0);
		if(command.equals("admin_show_moves"))
			AdminHelpPage.showHelpPage(activeChar, "teleports.htm");
		if(command.equals("admin_show_moves_other"))
			AdminHelpPage.showHelpPage(activeChar, "tele/other.htm");
		else if(command.equals("admin_show_teleport"))
			showTeleportCharWindow(activeChar);
		else if(command.equals("admin_recall_npc"))
			recallNPC(activeChar);
		else if(command.equals("admin_teleport_to_character"))
			teleportToCharacter(activeChar, activeChar.getTarget());
		else if(command.equals("admin_walk"))
			try
			{
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				int z = Integer.parseInt(args[2]);
				Location pos = new Location(x, y, z);
				activeChar.moveToLocation(pos, 0, true);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		else if(command.equals("admin_move_to"))
			try
			{
				teleportTo(activeChar, args);
			}
			catch(StringIndexOutOfBoundsException e)
			{ // Case of empty coordinates
				activeChar.sendMessage("Wrong or no Coordinates given.");
			}
		else if(command.equals("admin_teleport_character"))
			try
			{
				L2Player target = activeChar.getTargetPlayer();

				if(target == null)
				{
					Functions.sendSysMessage(activeChar, "Select a player target.");
					return false;
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				teleportCharacter(activeChar, target, args);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				showTeleportCharWindow(activeChar); // back to character teleport
				return false;
			}
		else if(command.equals("admin_teleportto"))
			try
			{
				L2Player target = L2ObjectsStorage.getPlayer(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				teleportToCharacter(activeChar, target);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.startsWith("admin_recall"))
			try
			{
				L2Player target = L2ObjectsStorage.getPlayer(args[0]);

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target == null)
				{
					activeChar.sendMessage("->" + args[0] + "<- is incorrect. Trying offline recall.");
					teleportCharacter_offline(args[0], activeChar.getLoc());
				}
				else
				{
					if(target.getVar("jailed") != null)
						activeChar.sendMessage("->" + args[0] + "<- is jailed you cannot recall jaled char try unjail.");
					else
					{
						teleportCharacter(target, activeChar.getLoc());
						logGM.info(activeChar.toFullString() + " " + "teleport character " + target.getName() + " to " + activeChar.getX() + "," + activeChar.getY() + "," + activeChar.getZ());
					}
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equals("admin_failed"))
		{
			activeChar.sendMessage("Trying ActionFailed...");
			activeChar.sendActionFailed();
		}
		else if(command.equals("admin_tele"))
			showTeleportWindow(activeChar);
		else if(command.equals("admin_goup"))
		{
			try
			{
				int z = args.length > 0 ? Integer.parseInt(args[0]) : 150;
				activeChar.teleToLocation(activeChar.getLoc().changeZ(z));
				showTeleportWindow(activeChar);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_godown"))
		{
			try
			{
				int z = args.length > 0 ? Integer.parseInt(args[0]) : -150;
				activeChar.teleToLocation(activeChar.getLoc().changeZ(z));
				showTeleportWindow(activeChar);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_goeast"))
		{
			try
			{
				int x = activeChar.getX();
				int y = activeChar.getY();
				int z = activeChar.getZ();
				x += args.length > 0 ? Integer.parseInt(args[0]) : 150;
				activeChar.teleToLocation(x, y, z);
				showTeleportWindow(activeChar);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_gowest"))
		{
			try
			{
				int x = activeChar.getX();
				int y = activeChar.getY();
				int z = activeChar.getZ();
				x += args.length > 0 ? Integer.parseInt(args[0]) : -150;
				activeChar.teleToLocation(x, y, z);
				showTeleportWindow(activeChar);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_gosouth"))
		{
			try
			{
				int x = activeChar.getX();
				int y = activeChar.getY();
				int z = activeChar.getZ();
				y += args.length > 0 ? Integer.parseInt(args[0]) : 150;
				activeChar.teleToLocation(x, y, z);
				showTeleportWindow(activeChar);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_gonorth"))
			try
			{
				int x = activeChar.getX();
				int y = activeChar.getY();
				int z = activeChar.getZ();
				y += args.length > 0 ? Integer.parseInt(args[0]) : -150;
				activeChar.teleToLocation(x, y, z);
				showTeleportWindow(activeChar);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		else if(command.equals("admin_tonpc"))
			try
			{
				int npc_id = Integer.parseInt(args[0]);
				L2NpcInstance npc = L2ObjectsStorage.getByNpcId(npc_id);
				teleportToCharacter(activeChar, npc);
			}
			catch(Exception e)
			{
				try
				{
					L2NpcInstance npc = L2ObjectsStorage.getNpc(args[0]);
					teleportToCharacter(activeChar, npc);
				}
				catch(Exception e2)
				{
					Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
					return false;
				}
			}
		else if(command.startsWith("admin_sendhome"))
		{
			L2Player target;
			if(args.length > 0)
			{
				target = L2ObjectsStorage.getPlayer(args[0]);
				if(target == null)
				{
					activeChar.sendMessage("Player " + args[0] + " is not online.");
					return false;
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, null, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				target.teleToClosestTown();
				activeChar.sendMessage("Player " + args[0] + " teleported to closest town.");
				return true;
			}

			activeChar.teleToClosestTown();
		}
		else if(command.equals("admin_instant_move"))
		{
			activeChar.setTeleMode(1);
			Functions.sendSysMessage(activeChar, "instant move on");
		}

		return true;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}

	private void teleportTo(L2Player activeChar, String[] cords)
	{
		if(cords.length < 3)
		{
			activeChar.sendMessage("3 coordinates required to teleport");
			return;
		}

		int x = Integer.parseInt(cords[0]);
		int y = Integer.parseInt(cords[1]);
		int z = Integer.parseInt(cords[2]);

		activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
		activeChar.teleToLocation(x, y, z);

		activeChar.sendMessage("You have been teleported to " + x + " " + y + " " + z);

		logGM.info(activeChar.toFullString() + " " + "teleported to " + x + " " + y + " " + z);
	}

	private void showTeleportWindow(L2Player activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><title>Teleport Menu</title>");
		replyMSG.append("<body>");

		replyMSG.append("<br>");
		replyMSG.append("<center><table>");

		replyMSG.append("<tr><td><button value=\"  \" action=\"bypass -h admin_tele\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"North\" action=\"bypass -h admin_gonorth\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Up\" action=\"bypass -h admin_goup\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td><button value=\"West\" action=\"bypass -h admin_gowest\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"  \" action=\"bypass -h admin_tele\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"East\" action=\"bypass -h admin_goeast\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td><button value=\"  \" action=\"bypass -h admin_tele\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"South\" action=\"bypass -h admin_gosouth\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Down\" action=\"bypass -h admin_godown\" width=70 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");

		replyMSG.append("</table></center>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void showTeleportCharWindow(L2Player activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2Player player = null;
		if(target != null && target.isPlayer())
			player = (L2Player) target;
		else
		{
			activeChar.sendPacket(Msg.INVALID_TARGET);
			return;
		}
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><title>Teleport Character</title>");
		replyMSG.append("<body>");
		replyMSG.append("The character you will teleport is " + player.getName() + ".");
		replyMSG.append("<br>");

		replyMSG.append("Co-ordinate x");
		replyMSG.append("<edit var=\"char_cord_x\" width=110>");
		replyMSG.append("Co-ordinate y");
		replyMSG.append("<edit var=\"char_cord_y\" width=110>");
		replyMSG.append("Co-ordinate z");
		replyMSG.append("<edit var=\"char_cord_z\" width=110>");
		replyMSG.append("<button value=\"Teleport\" action=\"bypass -h admin_teleport_character $char_cord_x $char_cord_y $char_cord_z\" width=60 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\">");
		replyMSG.append("<button value=\"Teleport near you\" action=\"bypass -h admin_teleport_character " + activeChar.getX() + " " + activeChar.getY() + " " + activeChar.getZ() + "\" width=115 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\">");
		replyMSG.append("<center><button value=\"Back\" action=\"bypass -h admin_current_player\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></center>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	private void teleportCharacter(L2Player activeChar, L2Player target, String[] cords) throws Exception
	{
		if(target.getObjectId() == activeChar.getObjectId())
			target.sendMessage("You cannot teleport your character.");
		else
		{
			int x = Integer.parseInt(cords[0]);
			int y = Integer.parseInt(cords[1]);
			int z = Integer.parseInt(cords[2]);
			teleportCharacter(target, new Location(x, y, z));
		}
	}

	/**
	 * @param player
	 * @param loc Location
	 */
	private void teleportCharacter(L2Player player, Location loc)
	{
		if(player != null)
		{
			// Common character information
			player.sendMessage("Admin is teleporting you.");
			player.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
			player.teleToLocation(loc);
		}
	}

	private void teleportCharacter_offline(String _name, Location loc)
	{
		if(_name == null)
			return;

		Connection con = null;
		PreparedStatement st = null;
		try
		{
			con = DatabaseFactory.getInstance().getConnection();
			st = con.prepareStatement("UPDATE characters SET x=?,y=?,z=? WHERE char_name=? LIMIT 1");
			st.setInt(1, loc.getX());
			st.setInt(2, loc.getY());
			st.setInt(3, loc.getZ());
			st.setString(4, _name);
			st.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DbUtils.closeQuietly(con, st);
		}
	}

	private void teleportToCharacter(L2Player activeChar, L2Object target)
	{
		if(target == null)
			return;

		activeChar.getAI().setIntention(CtrlIntention.AI_INTENTION_IDLE, null, null);
		activeChar.teleToLocation(target.getLoc(), target.getReflection());

		activeChar.sendMessage("You have teleported to " + target);
	}

	private void recallNPC(L2Player activeChar)
	{
		L2Object obj = activeChar.getTarget();
		if(obj != null && obj instanceof L2NpcInstance)
		{
			L2NpcInstance target = (L2NpcInstance) obj;
			L2Spawn spawn = target.getSpawn();

			int monsterTemplate = target.getTemplate().npcId;

			L2NpcTemplate template1 = NpcTable.getTemplate(monsterTemplate);

			if(template1 == null)
			{
				activeChar.sendMessage("Incorrect monster template.");
				return;
			}

			int respawnTime = spawn.getRespawnDelay();

			target.deleteMe();
			spawn.stopRespawn();
			SpawnTable.getInstance().deleteSpawn(spawn, true);

			try
			{
				// L2MonsterInstance mob = new L2MonsterInstance(monsterTemplate,
				// template1);

				spawn = new L2Spawn(template1);
				spawn.setLoc(activeChar.getLoc());
				spawn.setAmount(1);
				spawn.setRespawnDelay(respawnTime);
				SpawnTable.getInstance().addNewSpawn(spawn, true, activeChar);
				spawn.init();

				activeChar.sendMessage("Created " + template1.name + " on " + target.getObjectId() + ".");

				logGM.info(activeChar.toFullString() + " " + "GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ") moved NPC" + target.getObjectId());
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Target is not in game.");
			}

		}
		else
			activeChar.sendPacket(Msg.INVALID_TARGET);
	}
}