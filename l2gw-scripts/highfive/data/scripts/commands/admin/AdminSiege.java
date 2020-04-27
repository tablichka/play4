package commands.admin;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.instancemanager.ResidenceManager;
import ru.l2gw.gameserver.instancemanager.TerritoryWarManager;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.Castle;
import ru.l2gw.gameserver.model.entity.ClanHall;
import ru.l2gw.gameserver.model.entity.Fortress;
import ru.l2gw.gameserver.model.entity.Territory;
import ru.l2gw.gameserver.model.entity.siege.ClanHall.ClanHallSiegeDatabase;
import ru.l2gw.gameserver.model.entity.siege.SiegeDatabase;
import ru.l2gw.gameserver.model.entity.siege.SiegeUnit;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.NpcHtmlMessage;
import ru.l2gw.gameserver.tables.ClanTable;

public class AdminSiege extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = {
			new AdminCommandDescription("admin_siege", null),
			new AdminCommandDescription("admin_add_attacker", null),
			new AdminCommandDescription("admin_add_defender", null),
			new AdminCommandDescription("admin_add_guard", null),
			new AdminCommandDescription("admin_list_siege_clans", null),
			new AdminCommandDescription("admin_clear_siege_list", null),
			new AdminCommandDescription("admin_move_defenders", null),
			new AdminCommandDescription("admin_spawn_doors", null),
			new AdminCommandDescription("admin_endsiege", null),
			new AdminCommandDescription("admin_startsiege", null),
			new AdminCommandDescription("admin_setcastle", null),
			new AdminCommandDescription("admin_castledel", null)};

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		SiegeUnit siegeUnit = null;
		int siegeUnitId = 0;
		if(args.length > 0)
			siegeUnitId = Integer.parseInt(args[0]);

		if(!AdminTemplateManager.checkCommand(command, activeChar, null, siegeUnitId, null, null))
		{
			Functions.sendSysMessage(activeChar, "Access denied.");
			return false;
		}

		if(siegeUnitId != 0)
		{
			siegeUnit = ResidenceManager.getInstance().getBuildingById(siegeUnitId);
		}

		if(siegeUnit == null || siegeUnit.getId() < 0)
			showSiegeUnitSelectPage(activeChar);
		else
		{
			L2Object target = activeChar.getTarget();
			L2Player player = null;
			if(target != null)
			{
				if(target.isPlayer())
					player = (L2Player) target;
			}
			else
				player = activeChar;

			if(command.equals("admin_add_attacker"))
			{
				if(player == null)
					activeChar.sendPacket(Msg.THAT_IS_THE_INCORRECT_TARGET);
				else
					siegeUnit.getSiege().registerAttacker(player, true);
			}
			else if(command.equals("admin_add_defender"))
			{
				if(player == null)
					activeChar.sendPacket(Msg.THAT_IS_THE_INCORRECT_TARGET);
				else
					siegeUnit.getSiege().registerDefender(player, true);
			}
			else if(command.equals("admin_add_guard"))
			{
				// Get value
				String val = "";
				if(args.length > 1)
					val = args[1];

				if(!val.equals(""))
					try
					{
						int npcId = Integer.parseInt(val);
						siegeUnit.getSiege().getSiegeGuardManager().addSiegeGuard(activeChar, npcId);
					}
					catch(Exception e)
					{
						activeChar.sendMessage("Value entered for Npc Id wasn't an integer");
					}
				else
					activeChar.sendMessage("Missing Npc Id");
			}
			else if(command.equals("admin_clear_siege_list"))
			{
				if(!siegeUnit.getSiege().isInProgress())//&& !siegeUnit.getSiege().isEnding()
				{
					if(siegeUnit.isClanHall)
						ClanHallSiegeDatabase.clearSiegeClan(siegeUnit.getSiege());
					else
						SiegeDatabase.clearSiegeClan(siegeUnit.getSiege());
				}
			}
			else if(command.equals("admin_endsiege"))
				siegeUnit.getSiege().endSiege();
			else if(command.equals("admin_list_siege_clans"))
			{
				siegeUnit.getSiege().listRegisterClan(activeChar);
				return true;
			}
			else if(command.equals("admin_move_defenders"))
				activeChar.sendPacket(Msg.NOT_WORKING_PLEASE_TRY_AGAIN_LATER);
			else if(command.equals("admin_setcastle"))
			{
				if(player == null || player.getClanId() == 0)
					activeChar.sendPacket(Msg.THAT_IS_THE_INCORRECT_TARGET);
				else
				{
					siegeUnit.changeOwner(player.getClanId());
					if(siegeUnit.isCastle)
					{
						TerritoryWarManager.changeTerritoryOwner(siegeUnit.getId() + 80, player.getClanId());
						TerritoryWarManager.getTerritoryById(siegeUnit.getId() + 80).spawnNpc();
						Territory terr = TerritoryWarManager.getTerritoryById(siegeUnit.getId() + 80);
						for(int wardId : terr.getWards())
							terr.addWardSkill(wardId);
					}
					System.out.println("Castle " + siegeUnit.getName() + " owned by clan " + player.getClan().getName());
				}
			}
			else if(command.equals("admin_castledel"))
			{
				siegeUnit.changeOwner(0);
				if(siegeUnit.isCastle)
				{
					TerritoryWarManager.changeTerritoryOwner(siegeUnit.getId() + 80, 0);
					TerritoryWarManager.getTerritoryById(siegeUnit.getId() + 80).despawnNpc();
					Territory terr = TerritoryWarManager.getTerritoryById(siegeUnit.getId() + 80);
					for(int wardId : terr.getWards())
						terr.removeWardSkill(wardId);
				}
				System.out.println("Castle " + siegeUnit.getName() + " owned by clan " + null);
			}
			else if(command.equals("admin_spawn_doors"))
				siegeUnit.spawnDoor();
			else if(command.equals("admin_startsiege"))
				siegeUnit.getSiege().startSiege();

			showSiegePage(activeChar, siegeUnit);
		}

		return true;
	}

	public void showSiegeUnitSelectPage(L2Player activeChar)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);

		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center><font color=\"LEVEL\">Siege Units</font></center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table><br>");

		replyMSG.append("<table width=260>");
		replyMSG.append("<tr><td>Unit Name</td><td>Owner</td></tr>");

		for(Castle castle : ResidenceManager.getInstance().getCastleList())
			if(castle != null)
			{
				replyMSG.append("<tr><td>");
				replyMSG.append("<a action=\"bypass -h admin_siege " + castle.getId() + "\">" + castle.getName() + "</a>");
				replyMSG.append("</td><td>");

				L2Clan owner = castle.getOwnerId() == 0 ? null : ClanTable.getInstance().getClan(castle.getOwnerId());
				if(owner == null)
					replyMSG.append("NPC");
				else
					replyMSG.append(owner.getName());

				replyMSG.append("</td></tr>");
			}

		for(Fortress fortress : ResidenceManager.getInstance().getFortressList())
			if(fortress != null)
			{
				replyMSG.append("<tr><td>");
				replyMSG.append("<a action=\"bypass -h admin_siege " + fortress.getId() + "\">" + fortress.getName() + "</a>");
				replyMSG.append("</td><td>");

				L2Clan owner = fortress.getOwnerId() == 0 ? null : ClanTable.getInstance().getClan(fortress.getOwnerId());
				if(owner == null)
					replyMSG.append("NPC");
				else
					replyMSG.append(owner.getName());

				replyMSG.append("</td></tr>");
			}
		for(ClanHall clanhall : ResidenceManager.getInstance().getClanHallList())
			if(clanhall != null && clanhall.getSiegeZone() != null)
			{
				replyMSG.append("<tr><td>");
				replyMSG.append("<a action=\"bypass -h admin_siege " + clanhall.getId() + "\">" + clanhall.getName() + "</a>");
				replyMSG.append("</td><td>");

				L2Clan owner = clanhall.getOwnerId() == 0 ? null : ClanTable.getInstance().getClan(clanhall.getOwnerId());
				if(owner == null)
					replyMSG.append("NPC");
				else
					replyMSG.append(owner.getName());

				replyMSG.append("</td></tr>");
			}
		replyMSG.append("</table>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	public void showSiegePage(L2Player activeChar, SiegeUnit siegeUnit)
	{
		NpcHtmlMessage adminReply = new NpcHtmlMessage(5);
		StringBuffer replyMSG = new StringBuffer("<html><body>");
		replyMSG.append("<table width=260><tr>");
		replyMSG.append("<td width=40><button value=\"Main\" action=\"bypass -h admin_admin\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td width=180><center>Siege Menu</center></td>");
		replyMSG.append("<td width=40><button value=\"Back\" action=\"bypass -h admin_siege\" width=40 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("</tr></table>");
		replyMSG.append("<center>");
		replyMSG.append("<br><br><br>Siege Unit: " + siegeUnit.getName() + "<br><br>");
		replyMSG.append("Unit Owner: ");

		L2Clan owner = siegeUnit.getOwnerId() == 0 ? null : ClanTable.getInstance().getClan(siegeUnit.getOwnerId());
		if(owner == null)
			replyMSG.append("NPC");
		else
			replyMSG.append(owner.getName());

		replyMSG.append("<br><br><table>");
		replyMSG.append("<tr><td><button value=\"Add Attacker\" action=\"bypass -h admin_add_attacker " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Add Defender\" action=\"bypass -h admin_add_defender " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("<tr><td><button value=\"List Clans\" action=\"bypass -h admin_list_siege_clans " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Clear List\" action=\"bypass -h admin_clear_siege_list " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<br>");
		replyMSG.append("<table>");
		replyMSG.append("<tr><td><button value=\"Move Defenders\" action=\"bypass -h admin_move_defenders " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Spawn Doors\" action=\"bypass -h admin_spawn_doors " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<br>");
		replyMSG.append("<table>");
		replyMSG.append("<tr><td><button value=\"Start Siege\" action=\"bypass -h admin_startsiege " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"End Siege\" action=\"bypass -h admin_endsiege " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<br>");
		replyMSG.append("<table>");
		replyMSG.append("<tr><td><button value=\"Give Unit\" action=\"bypass -h admin_setcastle " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td>");
		replyMSG.append("<td><button value=\"Take Unit\" action=\"bypass -h admin_castledel " + siegeUnit.getId() + "\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("<br>");
		replyMSG.append("<table>");
		replyMSG.append("<tr><td>NpcId: <edit var=\"value\" width=40>");
		replyMSG.append("<td><button value=\"Add Guard\" action=\"bypass -h admin_add_guard " + siegeUnit.getId() + " $value\" width=80 height=15 back=\"L2UI_CT1.Button_DF\" fore=\"L2UI_CT1.Button_DF\"></td></tr>");
		replyMSG.append("</table>");
		replyMSG.append("</center>");
		replyMSG.append("</body></html>");

		adminReply.setHtml(replyMSG.toString());
		activeChar.sendPacket(adminReply);
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}