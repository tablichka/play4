package events.Capture;

import events.TvT.TvT;
import events.lastHero.LastHero;
import javolution.util.FastList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.commons.utils.DbUtils;
import ru.l2gw.database.DatabaseFactory;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.listeners.MethodCollection;
import ru.l2gw.extensions.listeners.MethodInvokeListener;
import ru.l2gw.extensions.listeners.events.MethodEvent;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.handler.IOnResurrectHandler;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.L2GameServerPacket;
import ru.l2gw.gameserver.templates.StatsSet;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: rage
 * @date: 08.06.12 21:10
 */
public class Capture extends Functions implements ScriptFile, IOnDieHandler, IOnResurrectHandler
{
	protected static final Log eventLog = LogFactory.getLog("capture");
	protected static final int STATE_NO_EVENT = 0;
	protected static final int STATE_REGISTRATION = 1;
	protected static final int STATE_PLAYING = 2;
	private static FastList<L2Player> emptyList = new FastList<L2Player>(0);

	private static int status;
	private static int ticketTeam1;
	private static int ticketPenaltyTeam1;
	private static int ticketTeam2;
	private static int ticketPenaltyTeam2;

	private static final GArray<Integer> registeredPlayers = new GArray<>();
	private static Instance captureInstance;
	private static ScheduledFuture<?> startTask;
	private static ScheduledFuture<?> timeoutTask;
	private static final Map<Integer, StatsSet> statistic = new ConcurrentHashMap<>();
	private static final Map<Integer, StatsSet> roundStatistic = new ConcurrentHashMap<>();
	private static final Map<Integer, ScheduledFuture<?>> resurrectTask = new ConcurrentHashMap<>();
	private static final Map<Integer, L2NpcInstance> flags = new ConcurrentHashMap<>();
	private static final Map<String, Integer> registeredHwids = new ConcurrentHashMap<>();
	private static final Map<String, Long> bannedHwid = new ConcurrentHashMap<>();

	private static final L2Zone zoneTeam1 = ZoneManager.getInstance().getZoneByName("capture_team1");
	private static final L2Zone zoneTeam2 = ZoneManager.getInstance().getZoneByName("capture_team2");
	private static final L2Zone[] zones = new L2Zone[]{null, zoneTeam1, zoneTeam2};
	private static final ZoneListener zoneListener = new ZoneListener();
	private static final OnHealListener healListener = new OnHealListener();

	static
	{
		if(zoneTeam1 != null)
			zoneTeam1.getListenerEngine().addMethodInvokedListener(zoneListener);
		else
			_log.warn("Capture Event: no zone for team1");
		if(zoneTeam2 != null)
			zoneTeam2.getListenerEngine().addMethodInvokedListener(zoneListener);
		else
			_log.warn("Capture Event: no zone for team2");
	}

	public void onLoad()
	{
		if(Config.CAPTURE_ENABLED)
		{
			long startTime = Config.CAPTURE_CRON.timeNextUsage(System.currentTimeMillis());
			_log.info("Loaded Event: Capture [state: activated] event start: " + new Date(startTime));
			startTask = executeTask("events.Capture.Capture", "start", new Object[0], startTime - System.currentTimeMillis());
		}
		else
			_log.info("Loaded Event: Capture [state: deactivated]");

		loadStat();
		loadBannedHwid();
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	private static void loadStat()
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			stmt = conn.prepareStatement("SELECT cs.*,c.char_name FROM capture_stat cs INNER JOIN characters c ON (cs.object_id = c.obj_id)");
			rs = stmt.executeQuery();

			while(rs.next())
			{
				StatsSet stat = new StatsSet();
				stat.set("points", rs.getInt("points"));
				stat.set("current_points", rs.getInt("current_points"));
				stat.set("wins_count", rs.getInt("wins_count"));
				stat.set("loos_count", rs.getInt("loos_count"));
				stat.set("kill_count", rs.getInt("kill_count"));
				stat.set("killed_count", rs.getInt("killed_count"));
				stat.set("resurrect_count", rs.getInt("resurrect_count"));
				stat.set("resurrected_count", rs.getInt("resurrected_count"));
				stat.set("flag_capture", rs.getInt("flag_capture"));
				stat.set("flag_attack", rs.getInt("flag_attack"));
				stat.set("heal_amount", rs.getInt("heal_amount"));
				stat.set("name", rs.getString("char_name"));
				statistic.put(rs.getInt("object_id"), stat);
			}
		}
		catch(Exception e)
		{
			_log.error("Event Capture: can't load statistic: ", e);
		}
		finally
		{
			DbUtils.closeQuietly(conn, stmt, rs);
		}
	}

	private static void loadBannedHwid()
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		bannedHwid.clear();

		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			stmt = conn.prepareStatement("DELETE FROM capture_bans WHERE expire_time > 0 and expire_time < " + Util.getCurrentTime());
			stmt.execute();
			DbUtils.closeQuietly(stmt);

			stmt = conn.prepareStatement("SELECT * FROM capture_bans");
			rs = stmt.executeQuery();

			while(rs.next())
			{
				bannedHwid.put(rs.getString("hwid"), rs.getInt("expire_time") > 0 ? rs.getInt("expire_time") * 1000L : rs.getInt("expire_time"));
			}
		}
		catch(Exception e)
		{
			_log.error("Event Capture: can't load banned hwid: " + e, e);
		}
		finally
		{
			DbUtils.closeQuietly(conn, stmt, rs);
		}
	}

	public static Map<Integer, StatsSet> getStatistic()
	{
		return statistic;
	}

	public static Map<Integer, StatsSet> getRoundStatistic()
	{
		return roundStatistic;
	}

	public void start()
	{
		if(self != null)
			if(!AdminTemplateManager.checkBoolean("eventMaster", (L2Player) self))
				return;

		if(status != STATE_NO_EVENT)
		{
			_log.info("Event: Capture not started! status: " + status);
			if(self != null)
				sendSysMessage((L2Player) self, "Capture is running! status: " + status);

			if(Config.CAPTURE_ENABLED)
			{
				if(startTask != null)
				{
					startTask.cancel(true);
					startTask = null;
				}

				long startTime = Config.CAPTURE_CRON.timeNextUsage(System.currentTimeMillis());
				_log.info("Event: Capture next start: " + new Date(startTime));
				eventLog.info("next start: " + new Date(startTime));
				startTask = executeTask("events.Capture.Capture", "start", new Object[0], startTime - System.currentTimeMillis());
			}

			return;
		}

		_log.info("Event: Capture started!");
		eventLog.info("started, set state to STATE_REGISTRATION");

		status = STATE_REGISTRATION;
		synchronized(registeredPlayers)
		{
			registeredPlayers.clear();
		}

		if(Config.CAPTURE_HWID_CHECK)
			registeredHwids.clear();

		loadBannedHwid();

		Announcements.getInstance().announceByCustomMessage("events.Capture.start", null);
		Announcements.getInstance().announceByCustomMessage("events.Capture.registration.time", new String[]{String.valueOf(Config.CAPTURE_REGISTRATION_TIME)});

		executeTask("events.Capture.Capture", "sendRequest", new Object[0], 30000L);

		if(Config.CAPTURE_REGISTRATION_TIME - 5 > 0)
			executeTask("events.Capture.Capture", "registrationAnnounce", new Object[]{5}, (Config.CAPTURE_REGISTRATION_TIME - 5) * 60000L);
		if(Config.CAPTURE_REGISTRATION_TIME - 1 > 0)
			executeTask("events.Capture.Capture", "registrationAnnounce", new Object[]{1}, (Config.CAPTURE_REGISTRATION_TIME - 1) * 60000L);

		executeTask("events.Capture.Capture", "startBattle", new Object[0], Config.CAPTURE_REGISTRATION_TIME * 60000L);
	}

	public void stop()
	{
		if(self != null)
			if(!AdminTemplateManager.checkBoolean("eventMaster", (L2Player) self))
				return;

		if(startTask != null)
		{
			startTask.cancel(true);
			startTask = null;
		}

		if(timeoutTask != null)
		{
			timeoutTask.cancel(true);
			timeoutTask = null;
		}

		if(status == STATE_NO_EVENT)
		{
			_log.info("Event: Capture not started! status: " + status);
			if(self != null)
				sendSysMessage((L2Player) self, "Capture is not running.");

			return;
		}

		_log.info("Event: Capture stopped!");

		if(status == STATE_REGISTRATION)
		{
			status = STATE_NO_EVENT;
			eventLog.info("event stop, set state to STATE_NO_EVENT");
			synchronized(registeredPlayers)
			{
				registeredPlayers.clear();
			}
			if(Config.CAPTURE_HWID_CHECK)
				registeredHwids.clear();
		}
		else if(status == STATE_PLAYING)
		{
			eventLog.info("event stop, set state to STATE_NO_EVENT, return players");
			updateStatistic();
			returnPlayers();
			status = STATE_NO_EVENT;
			captureInstance.stopInstance();
			captureInstance = null;
			for(ScheduledFuture<?> task : resurrectTask.values())
			{
				task.cancel(true);
			}
			resurrectTask.clear();
			flags.clear();
			synchronized(registeredPlayers)
			{
				registeredPlayers.clear();
			}
			if(Config.CAPTURE_HWID_CHECK)
				registeredHwids.clear();
		}
	}

	public static void stopBattle()
	{
		status = STATE_NO_EVENT;
		eventLog.info("battle stop, set state to STATE_NO_EVENT");

		if(startTask != null)
		{
			startTask.cancel(true);
			startTask = null;
		}

		if(timeoutTask != null)
		{
			timeoutTask.cancel(true);
			timeoutTask = null;
		}

		returnPlayers();

		captureInstance.stopInstance();
		captureInstance = null;
		for(ScheduledFuture<?> task : resurrectTask.values())
		{
			task.cancel(true);
		}
		resurrectTask.clear();
		flags.clear();

		synchronized(registeredPlayers)
		{
			registeredPlayers.clear();
		}
		if(Config.CAPTURE_HWID_CHECK)
			registeredHwids.clear();

		updateStatistic();

		if(Config.CAPTURE_ENABLED)
		{
			long startTime = Config.CAPTURE_CRON.timeNextUsage(System.currentTimeMillis());
			_log.info("Event: Capture next start: " + new Date(startTime));
			startTask = executeTask("events.Capture.Capture", "start", new Object[0], startTime - System.currentTimeMillis());
		}
	}

	private static void updateStatistic()
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		try
		{
			conn = DatabaseFactory.getInstance().getConnection();
			stmt = conn.prepareStatement("REPLACE INTO `capture_stat` VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

			for(Map.Entry<Integer, StatsSet> entry : roundStatistic.entrySet())
			{
				StatsSet round = entry.getValue();
				StatsSet stat = statistic.get(entry.getKey());
				if(stat == null)
				{
					stat = new StatsSet(round.getSet());
					stat.set("current_points", round.getInteger("points", 0));
					statistic.put(entry.getKey(), stat);
				}
				else
				{
					for(Map.Entry keyVal : round.getSet().entrySet())
					{
						if(!"name".equals(keyVal.getKey()) && !"current_points".equals(keyVal.getKey()))
							stat.set(keyVal.getKey().toString(), stat.getInteger(keyVal.getKey().toString(), 0) + (Integer) keyVal.getValue());
					}
					stat.set("current_points", stat.getInteger("current_points") + round.getInteger("points", 0));
				}

				stmt.setInt(1, entry.getKey());
				stmt.setInt(2, stat.getInteger("points", 0));
				stmt.setInt(3, stat.getInteger("current_points", 0));
				stmt.setInt(4, stat.getInteger("wins_count", 0));
				stmt.setInt(5, stat.getInteger("loos_count", 0));
				stmt.setInt(6, stat.getInteger("kill_count", 0));
				stmt.setInt(7, stat.getInteger("killed_count", 0));
				stmt.setInt(8, stat.getInteger("resurrect_count", 0));
				stmt.setInt(9, stat.getInteger("resurrected_count", 0));
				stmt.setInt(10, stat.getInteger("flag_capture", 0));
				stmt.setInt(11, stat.getInteger("flag_attack", 0));
				stmt.setInt(12, stat.getInteger("heal_amount", 0));
				stmt.execute();
			}
		}
		catch(Exception e)
		{
			_log.error("Event Capture: statistic update error: ", e);
		}
		finally
		{
			DbUtils.closeQuietly(conn, stmt);
		}

		roundStatistic.clear();
	}

	public static void saveStat(int objectId)
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		StatsSet stat = statistic.get(objectId);
		if(stat != null)
			try
			{
				conn = DatabaseFactory.getInstance().getConnection();
				stmt = conn.prepareStatement("REPLACE INTO `capture_stat` VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");

				stmt.setInt(1, objectId);
				stmt.setInt(2, stat.getInteger("points", 0));
				stmt.setInt(3, stat.getInteger("current_points", 0));
				stmt.setInt(4, stat.getInteger("wins_count", 0));
				stmt.setInt(5, stat.getInteger("loos_count", 0));
				stmt.setInt(6, stat.getInteger("kill_count", 0));
				stmt.setInt(7, stat.getInteger("killed_count", 0));
				stmt.setInt(8, stat.getInteger("resurrect_count", 0));
				stmt.setInt(9, stat.getInteger("resurrected_count", 0));
				stmt.setInt(10, stat.getInteger("flag_capture", 0));
				stmt.setInt(11, stat.getInteger("flag_attack", 0));
				stmt.setInt(12, stat.getInteger("heal_amount", 0));
				stmt.execute();
			}
			catch(Exception e)
			{
				_log.error("Event Capture: statistic update error: ", e);
			}
			finally
			{
				DbUtils.closeQuietly(conn, stmt);
			}
	}

	public static void returnPlayers()
	{
		for(L2Player player : getAllPlayers())
		{
			player.abortAttack();
			player.abortCast();
			player.setTarget(null);

			player.removeMethodInvokeListener(healListener);
			player.setSessionVar("event_team_pvp", null);
			player.setSessionVar("event_no_res", null);
			player.setTeam(0);

			if(player.isDead())
				player.setIsPendingRevive(true);

			if(player.getStablePoint() != null)
			{
				player.teleToLocation(player.getStablePoint(), 0);
				player.setStablePoint(null);
			}
			else
			{
				player.teleToClosestTown();
			}
		}
	}

	public static void sendRequest()
	{
		for(L2Player player : L2ObjectsStorage.getAllPlayers())
			if(!isRegistered(player) && !checkHwidBan(player) && checkPlayerCondition(player) && !TvT.isRegistered(player) && !LastHero.isRegistered(player) && !isRegistered(player) && player.getLevel() >= Config.CAPTURE_PLAYER_MIN_LEVEL && player.getLevel() < Config.CAPTURE_PLAYER_MAX_LEVEL)
				player.scriptRequest(new CustomMessage("events.Capture.registration.request", player).toString(), "events.Capture.Capture:registerPlayer", new Object[0]);
	}

	public static boolean isRegistered(L2Player player)
	{
		synchronized(registeredPlayers)
		{
			return registeredPlayers.contains(player.getObjectId());
		}
	}

	public void registerPlayer()
	{
		if(self instanceof L2Player)
			registerPlayer((L2Player) self);
	}

	private static boolean checkHwidBan(L2Player player)
	{
		return bannedHwid.containsKey(player.getLastHWID()) && (bannedHwid.get(player.getLastHWID()) < 0 || bannedHwid.get(player.getLastHWID()) > System.currentTimeMillis());
	}

	public static void registerPlayer(L2Player player)
	{
		if(status == STATE_NO_EVENT)
		{
			player.sendMessage(new CustomMessage("events.Capture.registration.wrong.time", player).toString());
			return;
		}

		if(!checkCondition(player))
		{
			player.sendMessage(new CustomMessage("events.Capture.registration.wrong.condition", player).toString());
			return;
		}

		if(Config.CAPTURE_HWID_CHECK)
		{
			Integer objectId = registeredHwids.get(player.getLastHWID());
			if(objectId != null && !objectId.equals(player.getObjectId()))
			{
				player.sendMessage(new CustomMessage("events.Capture.registration.wrong.hwid", player).toString());
				return;
			}
		}

		if(checkHwidBan(player))
		{
			player.sendMessage(new CustomMessage("events.Capture.registration.hwid.banned", player).toString());
			return;
		}

		synchronized(registeredPlayers)
		{
			if(status == STATE_REGISTRATION)
			{
				if(registeredPlayers.size() >= Config.CAPTURE_MAX_PARTICIPANTS)
				{
					player.sendMessage(new CustomMessage("events.Capture.registration.max", player).toString());
				}
				else if(!registeredPlayers.contains(player.getObjectId()))
				{
					eventLog.info("register: " + player);
					player.sendMessage(new CustomMessage("events.Capture.registration.ok", player).toString());
					registeredPlayers.add(player.getObjectId());
					if(Config.CAPTURE_HWID_CHECK)
						registeredHwids.put(player.getLastHWID(), player.getObjectId());
				}
				else
					player.sendMessage(new CustomMessage("events.Capture.registration.already", player).toString());
			}
			else
			{
				if(getAllPlayers().size() >= Config.CAPTURE_MAX_PARTICIPANTS)
				{
					player.sendMessage(new CustomMessage("events.Capture.registration.max", player).toString());
					return;
				}

				if(!registeredPlayers.contains(player.getObjectId()))
				{
					eventLog.info("register: " + player);
					registeredPlayers.add(player.getObjectId());
					if(Config.CAPTURE_HWID_CHECK)
						registeredHwids.put(player.getLastHWID(), player.getObjectId());
				}

				eventLog.info("teleport to battle: " + player);
				L2Party party = player.getParty();
				if(party != null)
					party.oustPartyMember(player);

				player.setSessionVar("event_team_pvp", "true");
				player.setSessionVar("event_no_res", "true");
				player.setStablePoint(player.getLoc());
				player.addMethodInvokeListener(healListener);

				if(getTeam(1).size() < 3)
				{
					player.teleToLocation(zoneTeam1.getSpawn(), captureInstance.getReflection());
					player.setTeam(1);
				}
				else if(getTeam(2).size() < 3)
				{
					player.teleToLocation(zoneTeam2.getSpawn(), captureInstance.getReflection());
					player.setTeam(2);
				}
				else if(getTeam(1).size() < getTeam(2).size())
				{
					int chance = 70;
					if(ticketTeam1 > ticketTeam2)
						chance = 30;
					if(Rnd.chance(chance))
					{
						player.teleToLocation(zoneTeam1.getSpawn(), captureInstance.getReflection());
						player.setTeam(1);
					}
					else
					{
						player.teleToLocation(zoneTeam2.getSpawn(), captureInstance.getReflection());
						player.setTeam(2);
					}
				}
				else
				{
					int chance = 70;
					if(ticketTeam2 > ticketTeam1)
						chance = 30;
					if(Rnd.chance(chance))
					{
						player.teleToLocation(zoneTeam2.getSpawn(), captureInstance.getReflection());
						player.setTeam(2);
					}
					else
					{
						player.teleToLocation(zoneTeam1.getSpawn(), captureInstance.getReflection());
						player.setTeam(1);
					}
				}
			}
		}
	}

	public static void OnPlayerExit(L2Player player)
	{
		if(status == STATE_REGISTRATION && registeredPlayers.contains(player.getObjectId()))
		{
			registeredPlayers.remove((Integer) player.getObjectId());
			if(Config.CAPTURE_HWID_CHECK)
				registeredHwids.remove(player.getLastHWID());
		}
	}

	public static void unregisterPlayer(L2Player player)
	{
		try
		{
			synchronized(registeredPlayers)
			{
				if(!registeredPlayers.contains(player.getObjectId()))
				{
					player.sendMessage(new CustomMessage("events.Capture.notregistered", player));
				}
				else if(status == STATE_NO_EVENT)
				{
					player.sendMessage(new CustomMessage("events.Capture.registration.wrong.time", player));
				}
				else
				{
					if(status == STATE_PLAYING && player.getReflection() == captureInstance.getReflection())
					{
						if(player.isDead())
							player.setIsPendingRevive(true);
						player.setTeam(0);
						player.setSessionVar("event_team_pvp", null);
						player.setSessionVar("event_no_res", null);
						player.teleToLocation(player.getStablePoint(), 0);
						player.removeMethodInvokeListener(healListener);
					}

					registeredPlayers.remove((Integer) player.getObjectId());
					if(Config.CAPTURE_HWID_CHECK)
						registeredHwids.remove(player.getLastHWID());
					player.sendMessage(new CustomMessage("events.Capture.registration.unreg", player));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void registrationAnnounce(Integer min)
	{
		Announcements.getInstance().announceByCustomMessage("events.Capture.registration.time", new String[]{String.valueOf(min)});
	}

	public static int getRegisteredSize()
	{
		synchronized(registeredPlayers)
		{
			return registeredPlayers.size();
		}
	}

	public static void startBattle()
	{
		status = STATE_PLAYING;
		eventLog.info("set state to: STATE_PLAYING");

		GArray<L2Player> players = new GArray<>(registeredPlayers.size());

		synchronized(registeredPlayers)
		{
			for(Integer objectId : registeredPlayers)
			{
				L2Player player = L2ObjectsStorage.getPlayer(objectId);
				if(checkCondition(player))
					players.add(player);
			}
		}

		if(players.size() < Config.CAPTURE_MIN_PARTICIPANTS)
		{
			_log.info("Event: Capture canceled: lack of participants: " + registeredPlayers.size());
			eventLog.info("event canceled, lack of participants: " + registeredPlayers.size());

			status = STATE_NO_EVENT;
			synchronized(registeredPlayers)
			{
				registeredPlayers.clear();
			}
			if(Config.CAPTURE_HWID_CHECK)
				registeredHwids.clear();

			Announcements.getInstance().announceByCustomMessage("events.Capture.start.canceled", null);

			long startTime = Config.CAPTURE_CRON.timeNextUsage(System.currentTimeMillis());
			_log.info("Event: Capture next start: " + new Date(startTime));
			startTask = executeTask("events.Capture.Capture", "start", new Object[0], startTime - System.currentTimeMillis());
			return;
		}

		Announcements.getInstance().announceByCustomMessage("events.Capture.start.battle", null);
		captureInstance = InstanceManager.getInstance().createNewInstance(-3, emptyList);
		//zoneTeam1.setActive(true, captureInstance.getReflection());
		//zoneTeam2.setActive(true, captureInstance.getReflection());

		roundStatistic.clear();
		eventLog.info("event start");

		for(L2Player player : players)
		{
			player.setSessionVar("event_team_pvp", "true");
			player.setSessionVar("event_no_res", "true");
			player.setStablePoint(player.getLoc());
			player.addMethodInvokeListener(healListener);

			L2Party party = player.getParty();
			if(party != null)
				party.oustPartyMember(player);

			if(Rnd.chance(50))
			{
				player.teleToLocation(Location.coordsRandomize(zoneTeam1.getSpawn(), 300), captureInstance.getReflection());
				player.setTeam(1);
			}
			else
			{
				player.teleToLocation(Location.coordsRandomize(zoneTeam2.getSpawn(), 300), captureInstance.getReflection());
				player.setTeam(2);
			}
			eventLog.info("teleport: " + player + " team: " + player.getTeam());
		}

		if(Config.CAPTURE_TICKETS > 0)
		{
			ticketTeam1 = Config.CAPTURE_TICKETS;
			ticketTeam2 = Config.CAPTURE_TICKETS;
		}
		else if(Config.CAPTURE_TICKET_PER_PLAYER > 0)
		{
			ticketTeam1 = ticketTeam2 = Config.CAPTURE_TICKET_PER_PLAYER * players.size();
		}

		ticketPenaltyTeam1 = ticketPenaltyTeam2 = 0;

		eventLog.info("start tickets: " + ticketTeam1);
		if(Config.CAPTURE_EVENT_TIME > 0)
			timeoutTask = executeTask("events.Capture.Capture", "stopBattle", new Object[0], Config.CAPTURE_EVENT_TIME * 60000);

		executeTask("events.Capture.Capture", "checkFlags", new Object[0], 600000);
	}

	public static void checkFlags()
	{
		if(status == STATE_PLAYING)
		{
			boolean t1 = false;
			boolean t2 = false;
			for(L2NpcInstance flag : getFlags().values())
			{
				if(flag != null)
				{
					if(flag.getTeam() == 1)
						t1 = true;
					else if(flag.getTeam() == 2)
						t2 = true;
				}
			}

			if(t1)
			{
				ticketPenaltyTeam1 = 0;
			}
			else
			{
				ticketPenaltyTeam1++;
				eventLog.info("team1: penalty tickets: " + ticketPenaltyTeam1);
				ticketTeam1 = Math.max(0, ticketTeam1 - ticketPenaltyTeam1);
			}

			if(t2)
			{
				ticketPenaltyTeam2 = 0;
			}
			else
			{
				ticketPenaltyTeam2++;
				eventLog.info("team2: penalty tickets: " + ticketPenaltyTeam2);
				ticketTeam2 = Math.max(0, ticketTeam2 - ticketPenaltyTeam2);
			}

			checkBattleEnd();

			if(status == STATE_PLAYING)
				executeTask("events.Capture.Capture", "checkFlags", new Object[0], 60000);
		}
	}

	private static boolean checkCondition(L2Player player)
	{
		return checkPlayerCondition(player) && !TvT.isRegistered(player) && !LastHero.isRegistered(player) && player.getLevel() >= Config.CAPTURE_PLAYER_MIN_LEVEL && player.getLevel() < Config.CAPTURE_PLAYER_MAX_LEVEL;
	}

	@Override
	public void onDie(L2Character killed, L2Character cha)
	{
		if(status == STATE_PLAYING && killed instanceof L2Player && killed.getReflection() == captureInstance.getReflection())
		{
			if(cha != null)
			{
				L2Player killer = cha.getPlayer();
				if(killer != null)
				{
					eventLog.info("killed: " + killed + "(" + killed.getTeam() + ") at " + killed.getLoc() + " killer: " + killer + "(" + killer.getTeam() + ") at " + killer.getLoc() + " points: " + Config.CAPTURE_POINTS_KILL);
					addPoints(killer, Config.CAPTURE_POINTS_KILL, "kill_count");
					showOnScreentMsg(killer, 8, 0, 0, 0, 0, 0, 5000, 0, 19, String.valueOf(Config.CAPTURE_POINTS_KILL));
				}
			}

			addPoints((L2Player) killed, 0, "killed_count");
			showOnScreentMsg((L2Player) killed, 2, 0, 0, 0, 0, 0, 5000, 0, 21, String.valueOf(Config.CAPTURE_RESURRECT_DELAY));

			if(killed.getTeam() == 1 && ticketTeam1 > 0)
			{
				ticketTeam1--;
				eventLog.info("team 1 tickets: " + ticketTeam1);
			}
			else if(killed.getTeam() == 2 && ticketTeam2 > 0)
			{
				ticketTeam2--;
				eventLog.info("team 2 tickets: " + ticketTeam1);
			}

			checkBattleEnd();

			ScheduledFuture<?> task = resurrectTask.put(killed.getObjectId(), ThreadPoolManager.getInstance().scheduleGeneral(new ResurrectTask((L2Player) killed), Config.CAPTURE_RESURRECT_DELAY * 1000));
			if(task != null)
				task.cancel(true);
		}
	}

	private static void checkBattleEnd()
	{
		if(ticketTeam1 == 0)
		{
			eventLog.info("team 1 loos. battle end");
			for(L2Player player : getAllPlayers())
			{
				if(player.getTeam() == 2)
				{
					eventLog.info("win: " + player + "(" + player.getTeam() + ") at " + player.getLoc() + " add points: " + Config.CAPTURE_POINTS_WIN);
					addPoints(player, Config.CAPTURE_POINTS_WIN, "wins_count");
				}
				else
				{
					addPoints(player, 0, "loos_count");
				}

				showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 10000, 0, 27);
			}
			Announcements.getInstance().announceByCustomMessage("events.Capture.win.red", null);
			stopBattle();
		}
		else if(ticketTeam2 == 0)
		{
			eventLog.info("team 2 loos. battle end");
			for(L2Player player : getAllPlayers())
			{
				if(player.getTeam() == 1)
				{
					eventLog.info("win: " + player + "(" + player.getTeam() + ") at " + player.getLoc() + " add points: " + Config.CAPTURE_POINTS_WIN);
					addPoints(player, Config.CAPTURE_POINTS_WIN, "wins_count");
				}
				else
				{
					addPoints(player, 0, "loos_count");
				}

				showOnScreentMsg(player, 2, 0, 0, 0, 0, 0, 10000, 0, 26);
			}
			Announcements.getInstance().announceByCustomMessage("events.Capture.win.blue", null);
			stopBattle();
		}
	}

	@Override
	public void onResurrected(L2Player player, long reviverStoredId)
	{
		if(status != STATE_PLAYING)
			return;

		addPoints(player, 0, "resurrected_count");

		if(player.getTeam() == 1 && ticketTeam1 > 0)
			ticketTeam1++;
		else if(player.getTeam() == 2 && ticketTeam2 > 0)
			ticketTeam2++;

		eventLog.info("resurrected: " + player + "(" + player.getTeam() + ") at " + player.getLoc() + " tickets: " + (player.getTeam() == 1 ? ticketTeam1 : ticketTeam2));

		L2Player reviver = L2ObjectsStorage.getAsPlayer(reviverStoredId);
		if(reviver != null && reviver.getReflection() == captureInstance.getReflection() && reviver.getTeam() == player.getTeam() && reviver != player)
		{
			addPoints(reviver, Config.CAPTURE_POINTS_RESURRECT, "resurrect_count");
			showOnScreentMsg(reviver, 8, 0, 0, 0, 0, 0, 5000, 0, 20, String.valueOf(Config.CAPTURE_POINTS_RESURRECT));
			eventLog.info("resurrect: " + reviver + "(" + player.getTeam() + ") at " + player.getLoc() + " add points: " + Config.CAPTURE_POINTS_RESURRECT + " resurrected: " + player + " at " + player.getLoc());
		}
	}

	public static StatsSet getRoundStats(L2Player player)
	{
		StatsSet stats = roundStatistic.get(player.getObjectId());
		if(stats == null)
		{
			stats = new StatsSet();
			stats.set("name", player.getName());
			roundStatistic.put(player.getObjectId(), stats);
		}

		return stats;
	}

	public static StatsSet getStatistic(L2Player player)
	{
		return statistic.get(player.getObjectId());
	}

	public static void addPoints(L2Player player, int points, String statName)
	{
		addPoints(player, points, statName, 1);
	}

	public static void addPoints(L2Player player, int points, String statName, int statAdd)
	{
		StatsSet stat = getRoundStats(player);
		stat.set(statName, stat.getInteger(statName, 0) + statAdd);
		stat.set("points", stat.getInteger("points", 0) + points);
		//eventLog.info("add points: " + player + " " + points + " -> " + stat.getInteger("points", 0));
	}

	public static GArray<L2Player> getAllPlayers()
	{
		GArray<L2Player> players = new GArray<>(registeredPlayers.size());
		synchronized(registeredPlayers)
		{
			for(Integer objectId : registeredPlayers)
			{
				L2Player player = L2ObjectsStorage.getPlayer(objectId);
				if(player != null && player.getReflection() == captureInstance.getReflection())
					players.add(player);
			}
		}

		return players;
	}

	public static GArray<L2Player> getTeam(int team)
	{
		if(team < 0)
			return getAllPlayers();

		GArray<L2Player> players = new GArray<>(registeredPlayers.size());
		synchronized(registeredPlayers)
		{
			for(Integer objectId : registeredPlayers)
			{
				L2Player player = L2ObjectsStorage.getPlayer(objectId);
				if(player != null && player.getReflection() == captureInstance.getReflection() && player.getTeam() == team)
					players.add(player);
			}
		}

		return players;
	}

	public static void broadcastToTeam(int team, L2GameServerPacket packet)
	{
		for(L2Player player : getTeam(team))
			player.sendPacket(packet);
	}

	public static int getStatus()
	{
		return status;
	}

	public static void addFlag(L2NpcInstance flag)
	{
		flags.put(flag.getNpcId(), flag);
	}

	public static Map<Integer, L2NpcInstance> getFlags()
	{
		return flags;
	}

	public static int getTicketTeam1()
	{
		return ticketTeam1;
	}

	public static int getTicketTeam2()
	{
		return ticketTeam2;
	}

	private static class ZoneListener extends L2ZoneEnterLeaveListener
	{
		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{

			if(captureInstance != null && status == STATE_PLAYING && zone.isActive(captureInstance.getReflection()) && object instanceof L2Playable && object.getTeam() == zone.getEntityId())
			{
				_log.info("Event: Capture leaved battle zone: " + object + " " + object.getLoc());
				ThreadPoolManager.getInstance().scheduleGeneral(new ZoneKillTask((L2Playable) object, 6), 100);
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}

	private static class ZoneKillTask implements Runnable
	{
		private int sec;
		private long storedId;

		private ZoneKillTask(L2Playable playable, int sec)
		{
			this.sec = sec;
			this.storedId = playable.getStoredId();
		}

		@Override
		public void run()
		{
			L2Playable playable = L2ObjectsStorage.getAsPlayable(storedId);

			if(playable == null || captureInstance == null || playable.isDead() || playable.getReflection() != captureInstance.getReflection())
				return;
			if(playable.getTeam() == 1 && zoneTeam1.isInsideZone(playable))
				return;
			if(playable.getTeam() == 2 && zoneTeam2.isInsideZone(playable))
				return;

			sec--;
			showOnScreentMsg(playable.getPlayer(), 2, 0, 0, 0, 0, 0, 1000, 0, 18, String.valueOf(sec));
			if(sec > 0)
			{
				ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
			}
			else
			{
				L2Zone zone = zones[playable.getTeam()];
				//playable.doDie(null);
				eventLog.info("outside zone teleported: " + playable);
				if(playable.isPet())
				{
					L2Player owner = playable.getPlayer();
					if(owner != null)
						playable.teleToLocation(owner.getLoc());
					else if(zone != null)
						playable.teleToLocation(Location.coordsRandomize(zone.getSpawn(), 300));
				}
				else if(zone != null)
					playable.teleToLocation(Location.coordsRandomize(zone.getSpawn(), 300));
			}
		}
	}

	private static class ResurrectTask implements Runnable
	{
		private final long storedId;

		public ResurrectTask(L2Player player)
		{
			storedId = player.getStoredId();
		}

		@Override
		public void run()
		{
			L2Player player = L2ObjectsStorage.getAsPlayer(storedId);
			if(player != null && status == STATE_PLAYING && player.isDead())
			{
				if(player.getTeam() == 1)
				{
					player.setIsPendingRevive(true);
					player.teleToLocation(Location.coordsRandomize(zoneTeam1.getSpawn(), 300));
				}
				else if(player.getTeam() == 2)
				{
					player.setIsPendingRevive(true);
					player.teleToLocation(Location.coordsRandomize(zoneTeam2.getSpawn(), 300));
				}
			}
			resurrectTask.remove(L2ObjectsStorage.getStoredObjectId(storedId));
		}
	}

	private static class OnHealListener implements MethodInvokeListener
	{
		@Override
		public final void methodInvoked(MethodEvent e)
		{
			try
			{
				L2Player owner = (L2Player) e.getOwner();
				if(status == STATE_PLAYING && isRegistered(owner) && captureInstance.getReflection() == owner.getReflection())
				{
					if(e.getArgs()[0] instanceof L2Player)
					{
						L2Player target = (L2Player) e.getArgs()[0];
						if(target != owner && isRegistered(target) && target.getReflection() == captureInstance.getReflection())
						{
							double heal = (Double) e.getArgs()[1];
							int points = (int) (heal * Config.CAPTURE_HEAL_RATE);
							if(points > 0)
							{
								eventLog.info("heal: " + owner + "(" + owner.getTeam() + ") at " + owner.getLoc() +" + -> " + target + "(" + target.getTeam() + ") at " + target.getLoc() + " points: " + points);
								addPoints(owner, points, "heal_amount", (int) heal);
								showOnScreentMsg(owner, 8, 0, 0, 0, 0, 0, 5000, 0, 30, String.valueOf(points));
							}
						}
					}
				}
			}
			catch(Exception ex)
			{
				// quite
				ex.printStackTrace();
			}
		}

		@Override
		public final boolean accept(MethodEvent event)
		{
			return event.getMethodName().equals(MethodCollection.onHeal);
		}
	}

	public static void teleportToEvent(L2Player player)
	{
		if(captureInstance != null && status == STATE_PLAYING)
			player.teleToLocation(zoneTeam1.getSpawn(), captureInstance.getReflection());
	}

	public static Map<String, Long> getBannedHwid()
	{
		return bannedHwid;
	}
}
