package commands.admin;

import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.ccpGuard.managers.HwidBan;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.AdminCommandDescription;
import ru.l2gw.gameserver.handler.AdminCommandHandler;
import ru.l2gw.gameserver.loginservercon.LSConnection;
import ru.l2gw.gameserver.loginservercon.gspackets.BanIP;
import ru.l2gw.gameserver.loginservercon.gspackets.ChangeAccessLevel;
import ru.l2gw.gameserver.loginservercon.gspackets.SendBanLastIp;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Party;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.util.AutoBan;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminBan extends AdminBase
{
	private static AdminCommandDescription[] _adminCommands = 
			{
					new AdminCommandDescription("admin_ban", "usage: //ban <name> [time min]"),
					new AdminCommandDescription("admin_unban", "usage: //unban <name>"),
					new AdminCommandDescription("admin_chatban", "usage: //chatban <name> <min>"),
					new AdminCommandDescription("admin_ckarma", "usage: //ckarma <name> <karma> \"reason\""),
					new AdminCommandDescription("admin_cban", null),
					new AdminCommandDescription("admin_chatunban", "usage: //chatunban <name>"),
					new AdminCommandDescription("admin_acc_ban", "usage: //acc_ban <account> [days (-1 unlimited)] \"comment\""),
					new AdminCommandDescription("admin_acc_unban", "usage: //acc_unban <account>"),
					new AdminCommandDescription("admin_jail", "usage: //jail [name] <min>"),
					new AdminCommandDescription("admin_unjail", "usage: //unjail [name]"),
					new AdminCommandDescription("admin_banall", "usage: //banall [player_name]")
			};

	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player activeChar)
	{
		if(command.equals("admin_banall"))
		{
			Connection con;
			PreparedStatement statement;
			ResultSet rset;

			L2Player player = null;
			String name = null;

			if(args.length > 0)
			{
				name = args[0];
				player = L2ObjectsStorage.getPlayer(name);
			}
			else if(activeChar.getTarget() instanceof L2Player)
				player = (L2Player) activeChar.getTarget();

			String account = null, hwid = null, ip = null;

			try
			{
				if(player != null)
				{
					account = player.getAccountName();
					hwid = player.getLastHWID();
					ip = player.getNetConnection().getIpAddr();
					player.logout(true, false, true);
				}
				else if(name != null)
				{
					con = DatabaseFactory.getInstance().getConnection();
					statement = con.prepareStatement("SELECT * FROM characters WHERE char_name = ?");
					statement.setString(1, name);
					rset = statement.executeQuery();
					if(rset.next())
					{
						account = rset.getString("account_name");
						hwid = rset.getString("LastHWID");
					}

					DbUtils.closeQuietly(con, statement, rset);
				}

				if(account != null)
				{
					activeChar.sendMessage("Banned: " + name);
					LSConnection.getInstance().sendPacket(new ChangeAccessLevel(account, -100, "Ban all by GM: " + activeChar.getName(), -1));
					activeChar.sendMessage("Account: " + account);

					if(ip != null)
					{
						LSConnection.getInstance().sendPacket(new BanIP(ip, "Ban all from console."));
						activeChar.sendMessage("IP: " + ip);
					}
					else
					{
						LSConnection.getInstance().sendPacket(new SendBanLastIp(account, activeChar.getName(), "Ban all."));
						activeChar.sendMessage("IP: last");
					}

					if(hwid != null && !hwid.isEmpty())
					{
						String comment = args.length > 1 ? args[1] : "no comment";
						comment += " [" + activeChar.getName() + "]";

						HwidBan.addHwidBan(hwid, comment);
						activeChar.sendMessage("HWID: " + hwid);

						con = DatabaseFactory.getInstance().getConnection();
						statement = con.prepareStatement("SELECT * FROM characters WHERE LastHWID = ? and account_name <> ?");
						statement.setString(1, hwid);
						statement.setString(2, account);
						rset = statement.executeQuery();
						while(rset.next())
						{
							activeChar.sendMessage("Banned: " + rset.getString("char_name"));
							L2Player pl = L2ObjectsStorage.getPlayer(rset.getString("char_name"));
							if(pl != null)
								pl.logout(true, false, true);
							account = rset.getString("account_name");
							LSConnection.getInstance().sendPacket(new ChangeAccessLevel(account, -100, "Ban all by GM: " + activeChar.getName(), -1));
							activeChar.sendMessage("Account: " + account);
						}

						DbUtils.closeQuietly(con, statement, rset);
					}

					if(ip != null)
					{
						LSConnection.getInstance().sendPacket(new BanIP(ip, "Ban all by GM: " + activeChar.getName()));
						activeChar.sendMessage("IP: " + ip);
					}
				}
				else
					activeChar.sendMessage("Player: " + name + " not found.");
			}
			catch(Exception e)
			{
				activeChar.sendMessage("Error: " + e);
				e.printStackTrace();
			}
		}
		else if(command.equals("admin_ban"))
		{
			try
			{
				String player = args[0];

				int time = 0;
				String msg = "";

				if(args.length > 1)
				{
					time = Integer.parseInt(args[1]);

					if(args.length > 2)
						msg = args[2];

					L2Player plyr = L2ObjectsStorage.getPlayer(player);
					if(!AdminTemplateManager.checkCommand(command, activeChar, plyr, time, player, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}	
					
					if(plyr != null)
					{
						plyr.sendMessage(new CustomMessage("scripts.commands.admin.AdminBan.YoureBannedByGM", plyr).addString(activeChar.getName()));
						plyr.setAccessLevel(-100);
						AutoBan.Banned(plyr, time, msg, activeChar.getName());
						if(plyr.isInOfflineMode())
							plyr.setOfflineMode(false);
						plyr.logout(false, false, true);
						activeChar.sendMessage("You banned " + plyr.getName());
					}
					else if(AutoBan.Banned(player, -100, time, msg, activeChar.getName()))
						activeChar.sendMessage("You banned " + player);
					else
						activeChar.sendMessage("Can't find char: " + player);

				}
				else
				{
					L2Player plyr = L2ObjectsStorage.getPlayer(player);

					if(!AdminTemplateManager.checkCommand(command, activeChar, plyr, time, player, null))
					{
						Functions.sendSysMessage(activeChar, "Access denied.");
						return false;
					}

					if(plyr != null)
					{
						plyr.sendMessage(new CustomMessage("scripts.commands.admin.AdminBan.YoureBannedByGM", plyr).addString(activeChar.getName()));
						plyr.setAccessLevel(-350);
						AutoBan.Banned(plyr, time, msg, activeChar.getName());
						if(plyr.isInOfflineMode())
							plyr.setOfflineMode(false);
						plyr.logout(false, false, true);
						activeChar.sendMessage("You banned " + plyr.getName());
					}
					else if(AutoBan.Banned(player, -350, time, msg, activeChar.getName()))
						activeChar.sendMessage("You banned " + player);
					else
						activeChar.sendMessage("Can't find char: " + player);
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "usage: //ban <name> [time sec] \"comment\"");
			}
		}
		else if(command.equals("admin_unban"))
		{
			if(args.length > 0)
			{
				String player = args[0];

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, null, player, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(AutoBan.Banned(player, 0, 0, "", activeChar.getName()))
					activeChar.sendMessage("You unbanned " + player);
				else
					activeChar.sendMessage("Can't find char: " + player);
			}
		}
		else if(command.equals("admin_acc_ban"))
		{
			if(args.length > 0)
			{
				String account = args[0];
				int days = args.length > 1 ? Integer.parseInt(args[1]) : -1;
				String reason = args.length > 2 ? args[2] : "";
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, account, days, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				int time = days * 24 * 60 * 60;
				LSConnection.getInstance().sendPacket(new ChangeAccessLevel(account, -100, "GM Ban: " + activeChar.getName() + ". " + reason, time));
				activeChar.sendMessage("You banned account: " + account + ", period: " + time + " days.");
				for(L2Player player : L2ObjectsStorage.getAllPlayers())
				{
					if(!player.getAccountName().equalsIgnoreCase(account))
						continue;

					if(player.isInOfflineMode())
						player.setOfflineMode(false);

					player.sendMessage(new CustomMessage("scripts.commands.admin.AdminBan.YoureBannedAccount", player).addNumber(days));
					player.sendPacket(new SystemMessage(SystemMessage.YOU_HAVE_BEEN_DISCONNECTED_FROM_THE_SERVER_PLEASE_LOGIN_AGAIN));
					final L2Player pl = player;
					ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
					{
						public void run()
						{
							pl.logout(false, false, true);
						}
					}, 500);
					break;
				}
			}
			else 
			{
				Functions.sendSysMessage(activeChar, "usage: //acc_ban <account> [days] \"reason\"");
				return false;
			}
		}
		else if(command.equals("admin_acc_unban"))
		{
			if(args.length > 0)
			{
				String account = args[0];
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, account, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}	

				LSConnection.getInstance().sendPacket(new ChangeAccessLevel(account, 0, "", 0));
				activeChar.sendMessage("You unban account :" + account + ".");
			}
			else
			{
				Functions.sendSysMessage(activeChar, "usage: //acc_unban <account>");
				return false;
			}
		}
		else if(command.equals("admin_chatban"))
		{
			try
			{
				String player = args[0];
				int min = Integer.parseInt(args[1]);
				
				if(!AdminTemplateManager.checkCommand(command, activeChar, null, player, min, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(AutoBan.ChatBan(player, min, "default banchat reason", activeChar.getName()))
					activeChar.sendMessage("Chat banned for character " + player + " for " + min + " minute(s).");
				else
					activeChar.sendMessage("Cannot find character " + player + " online or offline.");
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "usage: //chatban <name> <min>");
				return false;
			}
		}
		else if(command.equals("admin_chatunban"))
		{
			try
			{
				String player = args[0];

				if(!AdminTemplateManager.checkCommand(command, activeChar, null, player, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}
				
				if(AutoBan.ChatUnBan(player, activeChar.getName()))
					activeChar.sendMessage("Chat ban for " + player + " is lifted.");
				else
					activeChar.sendMessage("Cannot find character " + player + " online or offline.");
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, "usage: //chatunban <name>");
				return false;
			}
		}
		else if(command.equals("admin_jail"))
		{
			try
			{
				L2Player target;
				String targetName = null;
				int min;

				if(args.length > 1)
				{
					targetName = args[0];
					min = Integer.parseInt(args[1]);
					target = L2ObjectsStorage.getPlayer(targetName);
				}
				else
				{
					min = Integer.parseInt(args[0]);
					target = activeChar.getTargetPlayer();
					if(target != null)
						targetName = target.getName();
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, targetName, min, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}
				
				if(target != null)
				{
					if(target.isInOlympiadMode())
						target.finishOlympGame();

					if(Olympiad.isRegisteredInComp(target))
					{
						try
						{
							Olympiad.removeFromReg(target);
						}
						catch(Exception e)
						{
							activeChar.sendMessage("cannot remove from olymp reg char name: " + target.getName() + " objId: " + target.getObjectId());
							return true;
						}
					}

					if(target.getStablePoint() != null)
						target.setStablePoint(null);

					if(target.getPrivateStoreType() != L2Player.STORE_PRIVATE_NONE)
					{
						target.setPrivateStoreType(L2Player.STORE_PRIVATE_NONE);
						target.broadcastUserInfo(true);
						target.standUp();
						target.setSellList(null);
						target.setBuyList(null);
						target.setCreateList(null);
						target.saveTradeList();
						if(target.isInOfflineMode())
						{
							target.setVar("jailed", min > 0 ? String.valueOf(System.currentTimeMillis() + min * 60000L) : "-1");
							target.teleToLocation(-114648, -249384, -2984, 0, false);
							target.setOfflineMode(false);
							target.logout(false, false, true);
							return true;
						}
					}

					L2Party party = target.getParty();
					if(party != null)
						party.oustPartyMember(target);

					target.setVar("jailed", min > 0 ? String.valueOf(System.currentTimeMillis() + min * 60000L) : "-1");
					target.teleToLocation(-114648, -249384, -2984, 0, false);
					target.sendMessage(new CustomMessage("admin.jailed", target).addString(min > 0 ? String.valueOf(min) : "forever"));
					target.startJail();

					Functions.sendSysMessage(activeChar, target + " jailed for " + min + "min.");
					logGM.info(activeChar.toFullString() + " jailed " + target.toFullString() + " for " + min + " min. at " + target.getLoc());
				}
				else if(targetName != null)
				{
					Connection con = null;
					PreparedStatement stmt = null;

					try
					{
						String[] name = new String[] { targetName };
						int objectId = Util.getCharIdByNameAndName(name);

						if(objectId > 0)
						{
							targetName = name[0];
							con = DatabaseFactory.getInstance().getConnection();
							stmt = con.prepareStatement("REPLACE INTO character_variables (obj_id, type, name, value, expire_time) VALUES (?,\"user-var\",?,?,?)");
							stmt.setInt(1, objectId);
							stmt.setString(2, "jailed");
							stmt.setString(3, min > 0 ? String.valueOf(System.currentTimeMillis() + min * 60000L) : "-1");
							stmt.setInt(4, -1);
							stmt.executeUpdate();

							Functions.sendSysMessage(activeChar, "Player " + targetName + " jailed for " + min + "min.");
							logGM.info(activeChar.toFullString() + " jailed offline " + targetName + " for " + min + " min.");
						}
						else
						{
							Functions.sendSysMessage(activeChar, "Player " + targetName + " not found.");
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						DbUtils.closeQuietly(con, stmt);
					}
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_unjail"))
		{
			try
			{
				String targetName = null;
				L2Player target;
				if(args.length > 0)
				{
					targetName = args[0];
					target = L2ObjectsStorage.getPlayer(targetName);
				}
				else
				{
					target = activeChar.getTargetPlayer();
					if(target != null)
						targetName = target.getName();
				}

				if(!AdminTemplateManager.checkCommand(command, activeChar, target, targetName, null, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(target != null && target.getVar("jailed") != null)
				{
					target.teleToLocation(17836, 170178, -3507);
					target.unsetVar("jailed");
					target.stopJail();
					Functions.sendSysMessage(activeChar, "Unjailed: " + target);
					logGM.info(activeChar.toFullString() + " unjailed " + target.toFullString());
				}
				else
				{
					Connection con = null;
					PreparedStatement insertion = null;

					// trying to find obj_id for requested player
					try
					{
						con = DatabaseFactory.getInstance().getConnection();

						String[] name = new String[] { targetName };
						int objectId = Util.getCharIdByNameAndName(name);

						if(objectId > 0)
						{
							targetName = name[0];
							try
							{
								insertion = con.prepareStatement("DELETE FROM character_variables WHERE obj_id = ? AND name = 'jailed'");
								insertion.setInt(1, objectId);
								insertion.executeUpdate();
								insertion.close();
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}

							try
							{
								insertion = con.prepareStatement("UPDATE characters SET x=?, y=?, z=? WHERE obj_Id = ?");
								insertion.setInt(1, 17836);
								insertion.setInt(2, 170178);
								insertion.setInt(3, -3507);
								insertion.setInt(4, objectId);
								insertion.executeUpdate();
								insertion.close();
							}
							catch(Exception e)
							{
								e.printStackTrace();
							}
							Functions.sendSysMessage(activeChar, "Unjailed: " + targetName);
							logGM.info(activeChar.toFullString() + " unjailed offline " + targetName);
						}
						else
						{
							Functions.sendSysMessage(activeChar, "Player " + targetName + " not found.");
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						DbUtils.closeQuietly(con, insertion);
					}
				}
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.startsWith("admin_ckarma"))
		{
			try
			{
				String player = args[0];
				int karma = Integer.parseInt(args[1]);
				String msg = args.length > 2 ? args[2] : "";
				L2Player plyr = L2ObjectsStorage.getPlayer(player);

				if(!AdminTemplateManager.checkCommand(command, activeChar, plyr, player, karma, null))
				{
					Functions.sendSysMessage(activeChar, "Access denied.");
					return false;
				}

				if(plyr != null)
				{
					// update karma
					plyr.setKarma(karma);

					plyr.sendMessage("You get karma(" + karma + ") by GM " + activeChar.getName());
					AutoBan.Karma(plyr, karma, msg, activeChar.getName());
					activeChar.sendMessage("You set karma(" + karma + ") " + plyr.getName());
				}
				else if(AutoBan.Karma(player, karma, msg, activeChar.getName()))
					activeChar.sendMessage("You set karma(" + karma + ") " + player);
				else
					activeChar.sendMessage("Can't find char: " + player);
			}
			catch(Exception e)
			{
				Functions.sendSysMessage(activeChar, AdminCommandHandler.getInstance().getCommandUsage(command));
				return false;
			}
		}
		else if(command.equals("admin_cban"))
			AdminHelpPage.showHelpPage(activeChar, "cban.htm");

		return true;
	}

	public AdminCommandDescription[] getAdminCommandList()
	{
		return _adminCommands;
	}
}