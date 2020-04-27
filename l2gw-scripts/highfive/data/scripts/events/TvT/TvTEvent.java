package events.TvT;

import events.Capture.Capture;
import events.lastHero.LastHero;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.InZoneListener;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.Say2;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.StatsSet;
import ru.l2gw.util.Location;
import ru.l2gw.commons.crontab.Crontab;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 22.06.11 13:17
 */
public class TvTEvent implements InZoneListener
{
	private static final Log _log = LogFactory.getLog(TvTEvent.class);
	private static final Announcements announce = Announcements.getInstance();
	private static final FastList<L2Player> emptyList = new FastList<L2Player>(0);
	private static final PlayersComparator comparator = new PlayersComparator();
	private static final StatsComparator statsComparator = new StatsComparator();
	private static final L2Skill cancelSkill = SkillTable.getInstance().getInfo(4334, 1);
	private static final L2Zone battleZone = ZoneManager.getInstance().getZoneByName("[colosseum_battle]");
	private static final int doorId1 = 24190002;
	private static final int doorId2 = 24190003;

	private final Crontab crontab;
	private final int minLevel;
	private final int maxLevel;
	private final int minParticipants;
	private final int rewardItemId;
	private final long rewardItemCount;
	private final int rewardTopPlayerItemId;
	private final long rewardTopPlayerCount;
	private final String startAnnounce;
	private final String registrationAnnounce;
	private final String registrationEndAnnounce;
	private final boolean dispel;
	private final Say2 ruleAnnounce1;
	private final Say2 ruleAnnounce2;
	private final Say2 ruleAnnounce3;
	private final Say2 ruleAnnounce4;
	private final Say2 ruleAnnounce5;
	private final String noParticipantsAnnounce;
	private final String winnerTeamAnnounce;
	private final String noWinnerTeamAnnounce;
	private final String topPlayerAnnounce;

	private int status, regTime;
	private ScheduledFuture<?> startTask;
	private Instance tvtInstance;
	private FastList<Integer> registered = FastList.newInstance();
	private FastList<Integer> participants = FastList.newInstance();
	private FastList<Integer> team1 = FastList.newInstance();
	private FastList<Integer> team2 = FastList.newInstance();
	private FastMap<String, StatsSet> team1Stat;
	private FastMap<String, StatsSet> team2Stat;
	private final Map<String, Integer> registeredHwids = new ConcurrentHashMap<>();

	public TvTEvent(StatsSet config)
	{
		crontab = new Crontab(config.getString("Crontab"));
		minLevel = config.getInteger("MinLevel");
		maxLevel = config.getInteger("MaxLevel");
		minParticipants = config.getInteger("MinParticipants");
		rewardItemId = config.getInteger("RewardItemId");
		rewardItemCount = config.getLong("RewardItemCount");
		rewardTopPlayerItemId = config.getInteger("RewardTopPlayerItemId");
		rewardTopPlayerCount = config.getLong("RewardTopPlayerCount");
		startAnnounce = config.getString("StartAnnounce");
		registrationAnnounce = config.getString("RegistrationAnnounce");
		registrationEndAnnounce = config.getString("RegistrationEndAnnounce");
		dispel = config.getBool("Dispel");
		ruleAnnounce1 = new Say2(0, Say2C.ANNOUNCEMENT, "", config.getString("RuleAnnounce1"));
		ruleAnnounce2 = new Say2(0, Say2C.ANNOUNCEMENT, "", config.getString("RuleAnnounce2"));
		ruleAnnounce3 = new Say2(0, Say2C.ANNOUNCEMENT, "", config.getString("RuleAnnounce3"));
		ruleAnnounce4 = new Say2(0, Say2C.ANNOUNCEMENT, "", config.getString("RuleAnnounce4"));
		ruleAnnounce5 = new Say2(0, Say2C.ANNOUNCEMENT, "", config.getString("RuleAnnounce5"));
		noParticipantsAnnounce = config.getString("NoParticipantsAnnounce");
		winnerTeamAnnounce = config.getString("WinnerTeamAnnounce");
		noWinnerTeamAnnounce = config.getString("NoWinnerTeamAnnounce");
		topPlayerAnnounce = config.getString("TopPlayerAnnounce");
		team1Stat = new FastMap<String, StatsSet>();
		team2Stat = new FastMap<String, StatsSet>();
	}

	public void onLoad()
	{
		scheduleNextStart();
	}

	private void scheduleNextStart()
	{
		if(startTask != null)
			startTask.cancel(true);

		long startTime = crontab.timeNextUsage(System.currentTimeMillis());
		_log.info("Event: TvT levels: " + minLevel + "-" + maxLevel + " next start: " + new Date(startTime));
		startTask = ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			public void run()
			{
				start();
			}
		}, startTime - System.currentTimeMillis());
	}

	public void start()
	{
		registered.clear();
		if(Config.EVENT_TvT_CheckHWID)
			registeredHwids.clear();

		status = 1;
		announce.announceToAll(startAnnounce);
		regTime = Config.EVENT_TvT_PrepareTime;
		announce.announceToAll(registrationAnnounce.replace("MIN", String.valueOf(regTime)));
		_log.info("Event: " + this + " start.");

		for(L2Player player : L2ObjectsStorage.getAllPlayers())
			if(player.getLevel() >= minLevel && player.getLevel() <= maxLevel && Functions.checkPlayerCondition(player) && !LastHero.isRegistered(player) && !Capture.isRegistered(player))
				player.scriptRequest(new CustomMessage("scripts.events.TvT.AskPlayer", player).toString(), "events.TvT.TvT:addPlayer", new Object[0]);

		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			public void run()
			{
				startEvent();
			}
		}, 60000);
	}

	public void register(L2Player player)
	{
		if(status == 1)
		{
			if(Config.EVENT_TvT_CheckHWID)
			{
				if(registeredHwids.containsKey(player.getLastHWID()))
					return;

				registeredHwids.put(player.getLastHWID(), player.getObjectId());
			}

			if(!registered.contains(player.getObjectId()))
				registered.add(player.getObjectId());
		}
	}

	public void unRegister(L2Player player)
	{
		if(registered.contains(player.getObjectId()))
		{
			registered.remove((Integer) player.getObjectId());
			if(Config.EVENT_TvT_CheckHWID)
				registeredHwids.remove(player.getLastHWID());
		}
	}

	private void startEvent()
	{
		if(status != 1)
			return;

		regTime--;

		if(regTime > 0)
		{
			announce.announceToAll(registrationAnnounce.replace("MIN", String.valueOf(regTime)));

			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				public void run()
				{
					startEvent();
				}
			}, 60000);
		}
		else
		{
			status = 2;
			announce.announceToAll(registrationEndAnnounce);
			FastList<L2Player> players = FastList.newInstance();

			for(int objectId : registered)
			{
				L2Player player = L2ObjectsStorage.getPlayer(objectId);
				if(player == null || player.isInOfflineMode() || player.isInOlympiadMode() || player.inObserverMode() || player.isInDuel() || player.isAlikeDead()
						|| player.isInCombat() || player.isCastingNow() || Olympiad.isRegisteredInComp(player) || player.getReflection() != 0 || player.isInBoat() ||
						player.getLevel() < minLevel || player.getLevel() > maxLevel)
					continue;

				participants.add(player.getObjectId());
				players.add(player);
			}

			_log.info("Event: " + this + " participants: " + participants.size());

			if(players.size() < minParticipants)
			{
				_log.info("Event: " + this + " no min participants.");
				FastList.recycle(players);
				participants.clear();
				status = 0;
				scheduleNextStart();
				announce.announceToAll(noParticipantsAnnounce);
				return;
			}

			team1.clear();
			team2.clear();
			team1Stat.clear();
			team2Stat.clear();

			if(Config.EVENT_TvT_TeamRandomType == 0)
			{
				int teamSize = players.size() / 2;
				FastList<L2Player> playersCopy = FastList.newInstance();
				playersCopy.addAll(players);
				for(int i = 0; i < teamSize; i++)
					team1.add(playersCopy.remove(Rnd.get(playersCopy.size())).getObjectId());
				for(L2Player player : playersCopy)
					team2.add(player.getObjectId());
			}
			else
			{
				FastList<L2Player> playersCopy = FastList.newInstance();
				playersCopy.addAll(players);
				Collections.sort(playersCopy, comparator);
				while(playersCopy.size() > 0)
				{
					L2Player p1 = playersCopy.removeFirst();
					L2Player p2 = null;
					if(playersCopy.size() > 0)
						p2 = playersCopy.removeFirst();
					if(Rnd.chance(50))
					{
						team1.add(p1.getObjectId());
						if(p2 != null)
							team2.add(p2.getObjectId());
					}
					else
					{
						team2.add(p1.getObjectId());
						if(p2 != null)
							team1.add(p2.getObjectId());
					}
				}
			}

			tvtInstance = InstanceManager.getInstance().createNewInstance(-2, emptyList);

			for(L2Player player : players)
			{
				if(team1.contains(player.getObjectId()))
				{
					if(player.getParty() != null)
						player.getParty().removePartyMember(player);

					player.setSessionVar("event_team_pvp", "true");
					player.setSessionVar("event_no_res", "true");
					player.setStablePoint(player.getLoc());
					player.teleToLocation(Location.coordsRandomize(tvtInstance.getTemplate().getRestartPoints().get(0), 0, 100), tvtInstance.getReflection());
					player.setTeam(1);
				}
				else if(team2.contains(player.getObjectId()))
				{
					if(player.getParty() != null)
						player.getParty().removePartyMember(player);

					player.setSessionVar("event_team_pvp", "true");
					player.setSessionVar("event_no_res", "true");
					player.setStablePoint(player.getLoc());
					player.teleToLocation(Location.coordsRandomize(tvtInstance.getTemplate().getRestartPoints().get(1), 0, 100), tvtInstance.getReflection());
					player.setTeam(2);
				}
				else
				{
					_log.info("Event: " + this + " waring! " + player + " no in teams list.");
					participants.remove((Integer) player.getObjectId());
					continue;
				}

				if(dispel)
				{
					List<L2Character> targets = new ArrayList<>(1);
					targets.add(player);
					cancelSkill.useSkill(player, targets);
					if(player.getPet() != null)
						cancelSkill.applyEffects(player, player.getPet(), false);
				}

				player.sendPacket(ruleAnnounce1);
				player.sendPacket(ruleAnnounce2);
				player.sendPacket(ruleAnnounce3);
				player.sendPacket(ruleAnnounce4);
				player.sendPacket(ruleAnnounce5);
			}

			tvtInstance.getTemplate().getZone().setActive(true, tvtInstance.getReflection());

			ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
			{
				public void run()
				{
					go();
				}
			}, Config.EVENT_TvT_PrepareTime * 60000L);
		}
	}

	private void go()
	{
		if(status != 2)
			return;

		status = 3;

		FastList<L2Player> players = getParticipants();
		if(players.size() < minParticipants)
		{
			status = 0;
			_log.info("Event: " + this + " no min participants.");
			tvtInstance.getTemplate().getZone().setActive(false, tvtInstance.getReflection());
			teleportBack(players);
			FastList.recycle(players);
			participants.clear();
			team1.clear();
			team2.clear();
			scheduleNextStart();
			announce.announceToAll(noParticipantsAnnounce);
			tvtInstance.stopInstance();
			tvtInstance = null;
			return;
		}

		ExShowScreenMessage msg = new ExShowScreenMessage(">> Start FIGHT <<", 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
		for(L2Player player : players)
			player.sendPacket(msg);

		for(L2DoorInstance door : tvtInstance.getDoors())
			if(door.getDoorId() == doorId1 || door.getDoorId() == doorId2)
				door.openMe();

		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			public void run()
			{
				end();
			}
		}, Config.EVENT_TvT_FightTime * 60000L);
	}

	private void end()
	{
		_log.info("Event: " + this + " battle end.");
		for(L2DoorInstance door : tvtInstance.getDoors())
			if(door.getDoorId() == doorId1 || door.getDoorId() == doorId2)
				door.closeMe();

		FastList<L2Player> players = getParticipants();

		ExShowScreenMessage msg = new ExShowScreenMessage(">> STOP FIGHT <<", 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);

		for(L2Player player : players)
		{
			player.sendPacket(msg);
			if(battleZone.isInsideZone(player))
				if(team1.contains(player.getObjectId()))
					player.teleToLocation(Location.coordsRandomize(tvtInstance.getTemplate().getRestartPoints().get(0), 0, 200));
				else if(team2.contains(player.getObjectId()))
					player.teleToLocation(Location.coordsRandomize(tvtInstance.getTemplate().getRestartPoints().get(1), 0, 200));
		}

		int team1Kills = 0, team2Kills = 0;
		for(StatsSet stat : team1Stat.values())
			team1Kills += stat.getInteger("kills", 0);

		for(StatsSet stat : team2Stat.values())
			team2Kills += stat.getInteger("kills", 0);

		FastList<StatsSet> team1Sorted = FastList.newInstance();
		FastList<StatsSet> team2Sorted = FastList.newInstance();
		team1Sorted.addAll(team1Stat.values());
		team2Sorted.addAll(team2Stat.values());

		Collections.sort(team1Sorted, statsComparator);
		Collections.sort(team2Sorted, statsComparator);

		FastList<Integer> winningTeam = null;
		if(team1Kills > team2Kills)
		{
			announce.announceToAll(winnerTeamAnnounce.replace("COLOR", "Синяя").replace("KILLS", String.valueOf(team1Kills)));
			winningTeam = team1;
		}
		else if(team1Kills < team2Kills)
		{
			announce.announceToAll(winnerTeamAnnounce.replace("COLOR", "Красная").replace("KILLS", String.valueOf(team2Kills)));
			winningTeam = team2;
		}
		else
			announce.announceToAll(noWinnerTeamAnnounce);

		if(winningTeam != null)
		{
			for(L2Player player : players)
				if(winningTeam.contains(player.getObjectId()))
					player.addItem("TvT", rewardItemId, rewardItemCount, null, true);

			StatsSet s1 = team1Sorted.getFirst();
			StatsSet s2 = team2Sorted.getFirst();
			int k1 = s1.getInteger("kills", 0);
			int k2 = s2.getInteger("kills", 0);
			String winnerName = null;
			String color = null;
			int k = 0;

			if(k1 > 0 && k1 > k2)
			{
				winnerName = s1.getString("name");
				color = "синяя";
				k = k1;
			}
			else if(k2 > 0 && k1 < k2)
			{
				winnerName = s2.getString("name");
				color = "красная";
				k = k2;
			}

			if(winnerName != null)
			{
				announce.announceToAll(topPlayerAnnounce.replace("NAME", winnerName).replace("KILLS", String.valueOf(k)).replace("COLOR", color));
				if(rewardTopPlayerItemId > 0)
				{
					L2Player winner = L2ObjectsStorage.getPlayer(winnerName);
					if(winner != null && winner.getReflection() == tvtInstance.getReflection())
						winner.addItem("TvT", rewardTopPlayerItemId, rewardTopPlayerCount, null, true);
				}
			}
		}

		FastList.recycle(players);
		FastList.recycle(team1Sorted);
		FastList.recycle(team2Sorted);

		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable()
		{
			public void run()
			{
				close();
			}
		}, 10000L);
	}

	private void close()
	{
		status = 0;

		tvtInstance.getTemplate().getZone().setActive(false, tvtInstance.getReflection());
		FastList<L2Player> players = getParticipants();
		teleportBack(players);

		participants.clear();
		registered.clear();
		if(Config.EVENT_TvT_CheckHWID)
			registeredHwids.clear();
		team1.clear();
		team2.clear();
		team1Stat.clear();
		team2Stat.clear();
		scheduleNextStart();
		tvtInstance.stopInstance();
		tvtInstance = null;
	}

	private void teleportBack(FastList<L2Player> players)
	{
		for(L2Player player : players)
		{
			player.setSessionVar("event_team_pvp", null);
			player.setSessionVar("event_no_res", null);
			player.stopEffectsByName("c_fake_death");
			if(player.isDead())
				player.doRevive();
			player.setCurrentCp(player.getMaxCp());
			player.setCurrentHp(player.getMaxHp());
			player.setCurrentMp(player.getMaxMp());
			player.setTeam(0);
			Location loc = player.getStablePoint();
			if(loc != null)
				player.teleToLocation(loc, 0);
			else
				player.teleToClosestTown();
			player.setStablePoint(null);
		}
	}

	public FastList<L2Player> getParticipants()
	{
		if(tvtInstance == null)
			return emptyList;

		Reflection ref = ReflectionTable.getInstance().getById(tvtInstance.getReflection());
		if(ref == null)
			return emptyList;

		FastList<L2Player> players = FastList.newInstance();

		for(L2Object obj : ref.getAllObjects())
			if(obj instanceof L2Player && participants.contains(obj.getObjectId()))
				players.add((L2Player) obj);

		return players;
	}

	public boolean isParticipant(L2Player player)
	{
		return participants.contains(player.getObjectId());
	}

	public boolean isRegistered(L2Player player)
	{
		return Config.EVENT_TvT_CheckHWID && registeredHwids.containsKey(player.getLastHWID()) || registered.contains(player.getObjectId());
	}

	public void onDie(L2Player killed, L2Player killer)
	{
		if(killer.getTeam() == 1 && killed.getTeam() == 2)
		{
			StatsSet killerStat = team1Stat.get(killer.getName());
			StatsSet killedStat = team2Stat.get(killed.getName());
			if(killerStat == null)
			{
				killerStat = new StatsSet();
				killerStat.set("name", killer.getName());
				killerStat.set("kills", 1);
				killerStat.set("killed", 0);
				team1Stat.put(killer.getName(), killerStat);
			}
			else
				killerStat.set("kills", killerStat.getInteger("kills") + 1);

			if(killedStat == null)
			{
				killedStat = new StatsSet();
				killedStat.set("name", killed.getName());
				killedStat.set("kills", 0);
				killedStat.set("killed", 1);
				team2Stat.put(killed.getName(), killedStat);
			}
			else
				killedStat.set("killed", killedStat.getInteger("killed") + 1);
		}
		else if(killer.getTeam() == 2 && killed.getTeam() == 1)
		{
			StatsSet killerStat = team2Stat.get(killer.getName());
			StatsSet killedStat = team1Stat.get(killed.getName());
			if(killerStat == null)
			{
				killerStat = new StatsSet();
				killerStat.set("name", killer.getName());
				killerStat.set("kills", 1);
				killerStat.set("killed", 0);
				team2Stat.put(killer.getName(), killerStat);
			}
			else
				killerStat.set("kills", killerStat.getInteger("kills") + 1);

			if(killedStat == null)
			{
				killedStat = new StatsSet();
				killedStat.set("name", killed.getName());
				killedStat.set("kills", 0);
				killedStat.set("killed", 1);
				team1Stat.put(killed.getName(), killedStat);
			}
			else
				killedStat.set("killed", killedStat.getInteger("killed") + 1);
		}

		killed.sendMessage(new CustomMessage("scripts.events.TvT.resurrect", killed));
		if(killed.getTeam() == 1)
			teleportDelayed(killed, Location.coordsRandomize(tvtInstance.getTemplate().getRestartPoints().get(0), 0, 100));
		else if(killed.getTeam() == 2)
			teleportDelayed(killed, Location.coordsRandomize(tvtInstance.getTemplate().getRestartPoints().get(1), 0, 100));

		if(killer.getTarget() == killed)
			killer.setTarget(null);
	}

	public boolean checkLevel(L2Player player)
	{
		return player.getLevel() >= minLevel && player.getLevel() <= maxLevel;
	}

	public int getStatus()
	{
		return status;
	}

	private void teleportDelayed(L2Player player, Location loc)
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new DelayedTeleport(player, loc), 5000);
	}

	public StatsSet getPlayerStat(L2Player player)
	{
		if(player.getTeam() == 1)
			return team1Stat.get(player.getName());
		else if(player.getTeam() == 2)
			return team2Stat.get(player.getName());
		return null;
	}

	public int getTeamKills(int team)
	{
		int kills = 0;
		FastMap<String, StatsSet> stat = null;
		if(team == 1)
			stat = team1Stat;
		else if(team == 2)
			stat = team2Stat;

		if(stat != null)
			for(StatsSet set : stat.values())
				kills += set.getInteger("kills", 0);

		return kills;
	}

	public StatsSet getTopKillerStat()
	{
		FastList<StatsSet> teamSorted = FastList.newInstance();
		try
		{
			teamSorted.addAll(team1Stat.values());
			teamSorted.addAll(team2Stat.values());
			if(teamSorted.size() > 0)
			{
				Collections.sort(teamSorted, statsComparator);
				return teamSorted.getFirst();
			}
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			FastList.recycle(teamSorted);
		}
		return null;
	}

	public StatsSet getTopLooserStat()
	{
		FastList<StatsSet> teamSorted = FastList.newInstance();
		try
		{
			teamSorted.addAll(team1Stat.values());
			teamSorted.addAll(team2Stat.values());
			if(teamSorted.size() > 0)
			{
				int maxDeath = 0;
				StatsSet topLooser = null;
				for(StatsSet stat : teamSorted)
					if(stat.getInteger("killed", 0) > maxDeath)
					{
						maxDeath = stat.getInteger("killed", 0);
						topLooser = stat;
					}

				return topLooser;
			}
			return null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			FastList.recycle(teamSorted);
		}
		return null;
	}

	@Override
	public void onStartInstance(GArray<L2Player> party)
	{
	}

	@Override
	public void onSuccessEnd(GArray<L2Player> party)
	{
	}

	@Override
	public void onStopInstance(GArray<L2Player> party)
	{
	}

	@Override
	public void onPlayerEnter(L2Player player)
	{
	}

	@Override
	public void onPlayerExit(L2Player player)
	{
		player.setSessionVar("event_team_pvp", null);
		player.setSessionVar("event_no_res", null);
	}

	@Override
	public String toString()
	{
		return "TvT levels: " + minLevel + "-" + maxLevel;
	}
}
