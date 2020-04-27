package commands.admin;

import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.cache.Msg;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.instancemanager.SiegeManager;
import ru.l2gw.gameserver.model.L2Clan;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.PledgeShowInfoUpdate;
import ru.l2gw.gameserver.serverpackets.PledgeStatusChanged;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.tables.ClanTable;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Pledge Manipulation //pledge <create|dismiss|setlevel|resetcreate|resetwait|addrep>
 */
public class AdminPledge extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = { new AdminCommandDescription("admin_pledge", "usage: //pledge <create|dismiss|setlevel|resetcreate|resetwait|addrep> <value>") };

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		L2Player target = activeChar.getTargetPlayer();
		if(target == null)
		{
			Functions.sendSysMessage(activeChar, "Select a player target.");
			return false;
		}

		if(args.length < 1)
		{
			Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
			return false;
		}


		if(command.equals("admin_pledge"))
		{
			String action = args[0]; // create|dismiss|setlevel|resetcreate|resetwait|addrep

			if(action.equals("create"))
				try
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, args[1], null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					String pledgeName = args[1];
					L2Clan clan = ClanTable.getInstance().createClan(target, pledgeName);
					if(clan != null)
					{
						target.sendPacket(new PledgeShowInfoUpdate(clan));
						target.sendUserInfo(true);
						target.sendPacket(new SystemMessage(SystemMessage.CLAN_HAS_BEEN_CREATED));
						return true;
					}
				}
				catch(Exception e)
				{
					Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
					return false;
				}
			else if(action.equals("dismiss"))
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target.getClanId() == 0 || !target.isClanLeader())
				{
					activeChar.sendPacket(new SystemMessage(SystemMessage.ONLY_THE_CLAN_LEADER_IS_ENABLED));
					return false;
				}

				SiegeManager.removeSiegeSkills(target);
				SystemMessage sm = new SystemMessage(SystemMessage.CLAN_HAS_DISPERSED);
				for(L2Player clanMember : target.getClan().getOnlineMembers(null))
				{
					clanMember.setClan(null);
					clanMember.setTitle(null);
					clanMember.sendPacket(sm);
					clanMember.broadcastUserInfo(true);
				}

				Connection con = null;
				PreparedStatement statement = null;
				try
				{
					con = DatabaseFactory.getInstance().getConnection();
					statement = con.prepareStatement("UPDATE characters SET clanid = 0 WHERE clanid=?");
					statement.setInt(1, target.getClanId());
					statement.execute();
					DbUtils.closeQuietly(statement);

					statement = con.prepareStatement("DELETE FROM clan_data WHERE clan_id=?");
					statement.setInt(1, target.getClanId());
					statement.execute();
					DbUtils.closeQuietly(statement);
					statement = null;
					target.sendPacket(sm);
					target.broadcastUserInfo(true);
				}
				catch(Exception e)
				{}
				finally
				{
					DbUtils.closeQuietly(con, statement);
				}
				return true;
			}
			else if(action.equals("setlevel"))
			{
				if(target.getClanId() == 0 || !target.isClanLeader())
				{
					activeChar.sendPacket(new SystemMessage(SystemMessage.ONLY_THE_CLAN_LEADER_IS_ENABLED));
					return false;
				}

				try
				{
					byte level = Byte.parseByte(args[1]);
					L2Clan clan = target.getClan();

					if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, level, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					activeChar.sendMessage("You set level " + level + " for clan " + clan.getName());
					clan.setLevel(level);
					clan.updateClanInDB();

					if(level < 4)
						SiegeManager.removeSiegeSkills(target);
					else if(level > 3)
						SiegeManager.addSiegeSkills(target);

					if(level == 5)
						target.sendPacket(new SystemMessage(SystemMessage.NOW_THAT_YOUR_CLAN_LEVEL_IS_ABOVE_LEVEL_5_IT_CAN_ACCUMULATE_CLAN_REPUTATION_POINTS));

					SystemMessage sm = new SystemMessage(SystemMessage.CLANS_SKILL_LEVEL_HAS_INCREASED);
					PledgeShowInfoUpdate pu = new PledgeShowInfoUpdate(clan);

					for(L2Player member : clan.getOnlineMembers(null))
					{
						member.updatePledgeClass();
						member.sendPacket(sm);
						member.sendPacket(pu);
						member.broadcastUserInfo(true);
					}

					clan.broadcastToOnlineMembers(new PledgeStatusChanged(clan));
					clan.broadcastToOnlineMembers(new PledgeShowInfoUpdate(clan));

					return true;
				}
				catch(Exception e)
				{}
			}
			else if(action.equals("resetcreate"))
			{
				if(target.getClanId() == 0)
				{
					activeChar.sendPacket(Msg.INVALID_TARGET);
					return false;
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				target.getClan().setExpelledMemberTime(0);
				activeChar.sendMessage("The penalty for creating a clan has been lifted for" + target.getName());
			}
			else if(action.equals("resetwait"))
			{
				if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				target.setLeaveClanTime(0);
				activeChar.sendMessage("The penalty for leaving a clan has been lifted for " + target.getName());
			}
			else if(action.equals("addrep"))
				try
				{
					int rep = Integer.parseInt(args[1]);

					if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, rep, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					if(target.getClanId() == 0 || target.getClan().getLevel() < 5)
					{
						activeChar.sendPacket(Msg.INVALID_TARGET);
						return false;
					}
					target.getClan().incReputation(rep, false, "admin_manual");
					activeChar.sendMessage("Added " + rep + " clan points to clan " + target.getClan().getName() + ".");
				}
				catch(Exception nfe)
				{
					activeChar.sendMessage("Please specify a number of clan points to add.");
					return false;
				}
			else if(action.startsWith("start_war"))
			{
				try
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					String pledge1 = args[1];
					String pledge2 = args[2];
					L2Clan clan1 = ClanTable.getInstance().getClanByName(pledge1);
					if(clan1 == null)
					{
						Functions.sendSysMessage(activeChar, "Clan: " + pledge1 + " not found!");
						return false;
					}

					L2Clan clan2 = ClanTable.getInstance().getClanByName(pledge2);
					if(clan2 == null)
					{
						Functions.sendSysMessage(activeChar, "Clan: " + pledge2 + " not found!");
						return false;
					}

					ClanTable.getInstance().startClanWar(clan1, clan2);
					Functions.sendSysMessage(activeChar, "Start war: " + pledge1 + " -> " + pledge2);
				}
				catch(Exception e)
				{
					Functions.sendSysMessage(activeChar, "//pledge start_war pledge1 pledge2");
					return false;
				}
			}
			else if(action.startsWith("stop_war"))
			{
				try
				{
					if(!AdminTemplateManager.checkCommand(command, activeChar, target, action, null, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					String pledge1 = args[1];
					String pledge2 = args[2];
					L2Clan clan1 = ClanTable.getInstance().getClanByName(pledge1);
					if(clan1 == null)
					{
						Functions.sendSysMessage(activeChar, "Clan: " + pledge1 + " not found!");
						return false;
					}

					L2Clan clan2 = ClanTable.getInstance().getClanByName(pledge2);
					if(clan2 == null)
					{
						Functions.sendSysMessage(activeChar, "Clan: " + pledge2 + " not found!");
						return false;
					}

					ClanTable.getInstance().stopClanWar(clan1, clan2);
					Functions.sendSysMessage(activeChar, "Stop war: " + pledge1 + " -> " + pledge2);
				}
				catch(Exception e)
				{
					Functions.sendSysMessage(activeChar, "//pledge stop_war pledge1 pledge2");
					return false;
				}
			}
		}

		return false;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}