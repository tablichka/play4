package events.Capture;

import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.handler.*;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.CommunityBoardManager;
import ru.l2gw.gameserver.instancemanager.CommunityBoard.ICommunityBoardHandler;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.ShowBoard;
import ru.l2gw.gameserver.templates.StatsSet;
import ru.l2gw.util.Files;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: rage
 * @date: 22.06.12 2:18
 */
public class CaptureBoard extends Functions implements ScriptFile, ICommunityBoardHandler, IVoicedCommandHandler, IAdminCommandHandler
{
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");
	private static final String[] statKeys = new String[]{"points", "wins_count", "loos_count", "kill_count", "killed_count", "resurrect_count", "resurrected_count", "flag_capture", "flag_attack", "kd", "wl", "heal_amount"};
	private static final String[] commandList = new String[]{"ctf", "flag1", "flag2", "flag3", "flag4"};
	private static final AdminCommandDescription[] adminCommands =	{ new AdminCommandDescription("admin_toctf", "usage: //toctf"), new AdminCommandDescription("admin_ctf_ban", "usage: //ctf_ban <time> - minutes") };
	private static final ReentrantLock exchangeLock = new ReentrantLock();

	public void onLoad()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
		{
			_log.info("CommunityBoard: Capture Event loaded.");
			CommunityBoardManager.getInstance().registerHandler(this);
		}
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(this);
		AdminCommandHandler.getInstance().registerAdminCommandHandler(this);
	}

	public void onReload()
	{
		if(Config.COMMUNITYBOARD_ENABLED)
			CommunityBoardManager.getInstance().unregisterHandler(this);
		AdminCommandHandler.getInstance().unregisterAdminCommandHandler(this);
	}

	public void onShutdown()
	{}

	public String[] getBypassCommands()
	{
		return new String[]{"_capstatus", "_capregister", "_capunregister", "_captele", "_capshowflag", "_capmystat", "_capstat", "_capexchange"};
	}

	public void onBypassCommand(L2Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();
		player.setSessionVar("add_fav", null);

		if("capstatus".equals(cmd))
		{
			HashMap<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/events/Capture/html/cap_status.htm", player, false));
			String html = tpls.get(0);
			String blue = tpls.get(1);
			String red = tpls.get(2);
			String neutral = tpls.get(3);

			for(int i = 1; i < 5; i++)
			{
				L2NpcInstance flag = Capture.getFlags().get(37000 + i);
				if(flag != null)
				{
					String flagStatus = flag.getTeam() == 1 ? blue : flag.getTeam() == 2 ? red : neutral;
					html = html.replace("<?flag" + i + "?>", flagStatus.replace("<?flag_name?>", flag.getName()).replace("<?flag_num?>", String.valueOf(i)));
				}
				else
					html = html.replace("<?flag" + i +"?>", neutral.replace("<?flag_name?>", "N/A").replace("<?flag_num?>", String.valueOf(i)));
			}

			String tab = "";
			if(Capture.getStatus() == Capture.STATE_REGISTRATION)
			{
				html = html.replace("<?event_status?>", tpls.get(5));
			}
			else if(Capture.getStatus() == Capture.STATE_PLAYING)
			{
				html = html.replace("<?event_status?>", tpls.get(6));

				String row = tpls.get(10);
				StringBuilder rows = new StringBuilder();

				List<StatsSet> stats = new ArrayList<>();
				for(L2Player member : Capture.getTeam(1))
				{
					stats.add(Capture.getRoundStats(member));
				}

				StatComparator comparator = new StatComparator("points");
				Collections.sort(stats, comparator);

				int c = 1;
				for(StatsSet stat : stats)
				{
					rows.append(row.replace("<?num?>", String.valueOf(c)).replace("<?name?>", stat.getString("name")).replace("<?points?>", stat.getString("points", "0")));
					c++;
					if(c == 31)
						break;
				}

				String tab1 = tpls.get(11).replace("<?rows?>", rows);

				stats.clear();
				rows = new StringBuilder();
				for(L2Player member : Capture.getTeam(2))
				{
					stats.add(Capture.getRoundStats(member));
				}

				Collections.sort(stats, comparator);

				c = 1;
				for(StatsSet stat : stats)
				{
					rows.append(row.replace("<?num?>", String.valueOf(c)).replace("<?name?>", stat.getString("name")).replace("<?points?>", stat.getString("points", "0")));
					c++;
					if(c == 31)
						break;
				}

				String tab2 = tpls.get(11).replace("<?rows?>", rows);
				tab = tpls.get(12).replace("<?tab1?>", tab1).replace("<?tab2?>", tab2).replace("<?tickets1?>", String.valueOf(Capture.getTicketTeam1())).replace("<?tickets2?>", String.valueOf(Capture.getTicketTeam2()));
			}
			else
			{
				if(!Config.CAPTURE_ENABLED)
					html = html.replace("<?event_status?>", tpls.get(4));
				else
				{
					html = html.replace("<?event_status?>", tpls.get(7).replace("<?next_start?>", dateFormat.format(new Date(Config.CAPTURE_CRON.timeNextUsage(System.currentTimeMillis())))));
				}
			}

			html = html.replace("<?tab?>", tab);
			html = html.replace("<?reg_status?>", Capture.isRegistered(player) ? tpls.get(9) : tpls.get(8));
			html = html.replace("<?reg_count?>", String.valueOf(Capture.getRegisteredSize()));
			ShowBoard.separateAndSend(html, player);
		}
		else if("capregister".equals(cmd))
		{
			Capture.registerPlayer(player);
			onBypassCommand(player, "_capstatus");
		}
		else if("capunregister".equals(cmd))
		{
			Capture.unregisterPlayer(player);
			onBypassCommand(player, "_capstatus");
		}
		else if("capshowflag".equals(cmd))
		{
			if(Capture.isRegistered(player) && Capture.getStatus() == Capture.STATE_PLAYING)
			{
				int flagId = Integer.parseInt(st.nextToken());
				L2NpcInstance flag = Capture.getFlags().get(37000 + flagId);
				if(flag != null)
				{
					player.radar.showRadar(flag.getX(), flag.getY(), flag.getZ(), 1);
				}
			}
			onBypassCommand(player, "_capstatus");
		}
		else if("capmystat".equals(cmd))
		{
			String html = Files.read("data/scripts/events/Capture/html/cap_mystat.htm", player, false);
			StatsSet stats = Capture.getStatistic(player);

			if(stats == null)
			{
				html = html.replace("<?points?>", "0");
				html = html.replace("<?current_points?>", "0");
				html = html.replace("<?wins_count?>", "0");
				html = html.replace("<?loos_count?>", "0");
				html = html.replace("<?kill_count?>", "0");
				html = html.replace("<?killed_count?>", "0");
				html = html.replace("<?resurrect_count?>", "0");
				html = html.replace("<?resurrected_count?>", "0");
				html = html.replace("<?flag_capture?>", "0");
				html = html.replace("<?flag_attack?>", "0");
				html = html.replace("<?kd?>", "0.00");
				html = html.replace("<?wl?>", "0.00");
				html = html.replace("<?heal_amount?>", "0");
				html = html.replace("<?exchange_items?>", "0");
			}
			else
			{
				html = html.replace("<?points?>", stats.getString("points", "0"));
				html = html.replace("<?current_points?>", stats.getString("current_points", "0"));
				html = html.replace("<?wins_count?>", stats.getString("wins_count", "0"));
				html = html.replace("<?loos_count?>", stats.getString("loos_count", "0"));
				html = html.replace("<?kill_count?>", stats.getString("kill_count", "0"));
				html = html.replace("<?killed_count?>", stats.getString("killed_count", "0"));
				html = html.replace("<?resurrect_count?>", stats.getString("resurrect_count", "0"));
				html = html.replace("<?resurrected_count?>", stats.getString("resurrected_count", "0"));
				html = html.replace("<?flag_capture?>", stats.getString("flag_capture", "0"));
				html = html.replace("<?flag_attack?>", stats.getString("flag_attack", "0"));
				html = html.replace("<?kd?>", String.format("%.02f", stats.getInteger("kill_count", 0) > 0 && stats.getDouble("killed_count", 0) > 0 ? stats.getDouble("kill_count", 0) / stats.getDouble("killed_count", 1) : 0));
				html = html.replace("<?wl?>", String.format("%.02f", stats.getInteger("wins_count", 0) > 0 && stats.getInteger("loos_count", 0) > 0 ? stats.getDouble("wins_count", 0) / stats.getDouble("loos_count", 1) : 0));
				html = html.replace("<?heal_amount?>", stats.getString("heal_amount", "0"));
				html = html.replace("<?exchange_items?>", String.valueOf((int)(stats.getInteger("current_points", 0) * Config.CAPTURE_EXCHANGE_RATE)));
			}

			ShowBoard.separateAndSend(html, player);
		}
		else if("capstat".equals(cmd))
		{
			int key = 0;
			if(st.hasMoreTokens())
				key = Integer.parseInt(st.nextToken());

			HashMap<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/events/Capture/html/cap_stat.htm", player, false));
			String html = tpls.get(0);
			StringBuilder rows = new StringBuilder();
			
			List<StatsSet> stat = new ArrayList<>();
			for(StatsSet statsSet : Capture.getStatistic().values())
				stat.add(statsSet);
			
			StatComparator comparator = new StatComparator(statKeys[key]);
			Collections.sort(stat, comparator);
			
			int c = 0;
			for(StatsSet stats : stat)
			{
				String row = tpls.get(1);
				row = row.replace("<?name?>", stats.getString("name"));
				row = row.replace("<?points?>", stats.getString("points", "0"));
				row = row.replace("<?current_points?>", stats.getString("current_points", "0"));
				row = row.replace("<?wins_count?>", stats.getString("wins_count", "0"));
				row = row.replace("<?loos_count?>", stats.getString("loos_count", "0"));
				row = row.replace("<?kill_count?>", stats.getString("kill_count", "0"));
				row = row.replace("<?killed_count?>", stats.getString("killed_count", "0"));
				row = row.replace("<?resurrect_count?>", stats.getString("resurrect_count", "0"));
				row = row.replace("<?resurrected_count?>", stats.getString("resurrected_count", "0"));
				row = row.replace("<?flag_capture?>", stats.getString("flag_capture", "0"));
				row = row.replace("<?flag_attack?>", stats.getString("flag_attack", "0"));
				row = row.replace("<?kd?>", String.format("%.02f", stats.getInteger("kill_count", 0) > 0 && stats.getDouble("killed_count", 0) > 0 ? stats.getDouble("kill_count", 0) / stats.getDouble("killed_count", 1) : 0));
				row = row.replace("<?wl?>", String.format("%.02f", stats.getInteger("wins_count", 0) > 0 && stats.getInteger("loos_count", 0) > 0 ? stats.getDouble("wins_count", 0) / stats.getDouble("loos_count", 1) : 0));
				row = row.replace("<?heal_amount?>", stats.getString("heal_amount", "0"));
				rows.append(row);
				c++;
				if(c == 10)
					break;
			}

			html = html.replace("<?rows?>", rows);

			ShowBoard.separateAndSend(html, player);
		}
		else if("capexchange".equals(cmd))
		{
			StatsSet stat = Capture.getStatistic(player);
			long count = 0;
			int points = 0;

			if(st.hasMoreTokens())
			{
				try
				{
					exchangeLock.lock();

					if(stat != null)
					{
						points = stat.getInteger("current_points", 0);
						count = (long) (points * Config.CAPTURE_EXCHANGE_RATE);
					}

					if(count > 0)
					{
						stat.set("current_points", 0);
						Capture.eventLog.info("exchange: " + player + " points: " + points + " to coins: " + count);
						Capture.saveStat(player.getObjectId());
						player.addItem("CaptureExchange", Config.CAPTURE_EXCHANGE_ITEM_ID, count, null, true);
					}
				}
				finally
				{
					exchangeLock.unlock();
				}

				onBypassCommand(player, "_capexchange");
				return;
			}

			if(stat != null)
			{
				points = stat.getInteger("current_points", 0);
				count = (long) (points * Config.CAPTURE_EXCHANGE_RATE);
			}

			HashMap<Integer, String> tpls = Util.parseTemplate(Files.read("data/scripts/events/Capture/html/cap_exchange.htm", player, false));
			String html = tpls.get(0);

			if(count > 0)
				html = html.replace("<?exchange?>", tpls.get(1));
			else
				html = html.replace("<?exchange?>", tpls.get(2));

			html = html.replace("<?exchange_items?>", String.valueOf(count));
			html = html.replace("<?current_points?>", String.valueOf(points));

			ShowBoard.separateAndSend(html, player);
		}
	}

	public void onWriteCommand(L2Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return commandList;
	}

	@Override
	public boolean useVoicedCommand(String command, L2Player player, String args)
	{
		if("ctf".equals(command))
		{
			onBypassCommand(player, "_capstatus");
			return true;
		}
		else if(command.startsWith("flag"))
		{
			if(Capture.getStatus() == Capture.STATE_PLAYING && Capture.isRegistered(player))
				try
				{
					int flagId = 37000 + Integer.parseInt(command.replace("flag", ""));
					L2NpcInstance flag = Capture.getFlags().get(flagId);
					if(flag != null)
					{
						player.radar.showRadar(flag.getX(), flag.getY(), flag.getZ(), 1);
					}
					return true;
				}
				catch(Exception e)
				{
					// quite
					e.printStackTrace();
				}
		}

		return false;
	}

	@Override
	public boolean useAdminCommand(String command, String[] args, String fullCommand, L2Player player)
	{
		if(!AdminTemplateManager.checkCommand(command, player, player, null, null, null))
			return false;

		if("admin_toctf".equals(command))
		{
			if(Capture.getStatus() != Capture.STATE_PLAYING)
			{
				sendSysMessage(player, "Event not in state playing.");
				return false;
			}

			Capture.teleportToEvent(player);
			return true;
		}
		else if("admin_ctf_ban".equals(command))
		{
			int time = -1;
			if(args.length > 0)
				try
				{
					time = Integer.parseInt(args[0]);
				}
				catch(Exception e)
				{
					// quite
				}

			L2Player target = player.getTargetPlayer();
			if(target != null)
			{
				if(time > 0)
					time *= 60 + Util.getCurrentTime();

				Capture.getBannedHwid().put(target.getLastHWID(), time > 0 ? time * 1000L : time);

				Connection conn = null;
				PreparedStatement stmt = null;

				try
				{
					conn = DatabaseFactory.getInstance().getConnection();
					stmt = conn.prepareStatement("INSERT INTO capture_bans VALUES(?, ?)");
					stmt.setString(1, player.getLastHWID());
					stmt.setInt(2, time);
					stmt.execute();
				}
				catch(Exception e)
				{
					_log.error("Event Capture: can't insert capture ban: " + e, e);
				}
				finally
				{
					DbUtils.closeQuietly(conn, stmt);
				}

				Functions.sendSysMessage(player, target + " banned from capture for " + time + " mins");
				return true;
			}

			Functions.sendSysMessage(player, "no target for ban.");
			return false;
		}

		return false;
	}

	@Override
	public AdminCommandDescription[] getAdminCommandList()
	{
		return adminCommands;
	}
}