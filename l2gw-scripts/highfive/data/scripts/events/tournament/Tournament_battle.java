package events.tournament;

import ru.l2gw.database.mysql;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.handler.IOnEscapeHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.model.zone.L2Zone.ZoneType;
import ru.l2gw.gameserver.serverpackets.Revive;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Files;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;

@SuppressWarnings("unused")
public class Tournament_battle extends Functions implements ScriptFile, IOnDieHandler, IOnEscapeHandler
{
	public static L2Object self;
	public static L2NpcInstance npc;
	public static L2NpcInstance _manager;

	private static L2Zone _zone = ZoneManager.getInstance().getZoneById(ZoneType.battle, 112);
	ZoneListener _zoneListener = new ZoneListener();

	private static boolean _hooks = false;
	private static boolean _die_hooks = false;
	private static boolean _intention_hooks = false;

	private static int _current_cycle;
	private static int _category;

	private static int _time_to_start;

	private static ArrayList<Location> team1_points = new ArrayList<Location>();
	private static ArrayList<Location> team2_points = new ArrayList<Location>();

	public static Team team1;
	public static Team team2;

	public static ArrayList<L2Player> team1_live_list = new ArrayList<L2Player>();
	public static ArrayList<L2Player> team2_live_list = new ArrayList<L2Player>();

	@SuppressWarnings("unchecked")
	private static ScheduledFuture _startBattleTask;
	@SuppressWarnings("unchecked")
	private static ScheduledFuture _endBattleTask;

	public static String DialogAppend_32130(Integer val)
	{
		L2Player player = (L2Player) self;
		if(val == 0)
		{
			if(player.isGM())
				return Files.read("data/scripts/events/tournament/32130.html", player) + Files.read("data/scripts/events/tournament/32130-gm.html", player);
			return Files.read("data/scripts/events/tournament/32130.html", player);
		}
		return "";
	}

	public static void bypass_begin(String[] var)
	{
		if(var.length != 1)
		{
			show("Некорректные данные", (L2Player) self);
			return;
		}
		if(npc == null || !npc.isNpc())
		{
			show("Hacker? :) " + npc, (L2Player) self);
			return;
		}
		try
		{
			_category = Integer.valueOf(var[0]);
		}
		catch(Exception e)
		{
			show("Некорректные данные", (L2Player) self);
			return;
		}

		_manager = npc;
		_time_to_start = 10;
		_current_cycle = 1;

		team1_live_list.clear();
		team2_live_list.clear();

		Tournament_register.endRegistration();

		mysql.set("UPDATE tournament_teams SET status = 1");

		npcSay(_manager, Say2C.SHOUT, "Регистрация на турнир окончена.");

		if(Tournament_data.createTournamentTable(_category))
			return;

		npcSay(_manager, Say2C.SHOUT, "Турнир для категории " + _category + " начнется через 10 минут!");

		addAnnounce("Турнир начнется через 9 минут.", 60000);
		addAnnounce("Турнир начнется через 8 минут.", 120000);
		addAnnounce("Турнир начнется через 7 минут.", 180000);
		addAnnounce("Турнир начнется через 6 минут.", 240000);
		addAnnounce("Турнир начнется через 5 минут.", 300000);
		addAnnounce("Турнир начнется через 4 минуты.", 360000);
		addAnnounce("Турнир начнется через 3 минуты.", 420000);
		addAnnounce("Турнир начнется через 2 минуты.", 480000);
		addAnnounce("Турнир начнется через 1 минуту.", 540000);

		addAnnounce("Турнир проводится в Coliseum.", 121000);
		addAnnounce("Все желающие могут наблюдать за боями через обзорные кристаллы, либо купив места на трибуне. Места продаются у Arena Director.", 181000);
		addAnnounce("Команды будут вызываться по-очереди, в порядке, указанном в турнирной таблице. Просьба заранее приготовиться.", 241000);
		addAnnounce("На каждый бой отводится 2 минуты. Если за 2 минуты не определился победитель, проигравшей считается команда с меньшим количеством жизней.", 301000);
		addAnnounce("Если весь состав команды отсутствует в игре, команде засчитывается поражение.", 361000);
		addAnnounce("Рекомендуется всем участникам заранее прийти в Coliseum, чтобы в начале боя телепортация не заняла много времени.", 421000);

		addTask("nextBattle", 600000);
	}

	public static void addAnnounce(String text, Integer time)
	{
		Object[] args = new Object[1];
		args[0] = text;
		executeTask(_manager, "events.tournament.Tournament_battle", "announce", args, time);
	}

	@SuppressWarnings("unchecked")
	public static ScheduledFuture addTask(String task, Integer time)
	{
		return executeTask(_manager, "events.tournament.Tournament_battle", task, new Object[0], time);
	}

	public static void announce(String text)
	{
		npcSay(_manager, Say2C.SHOUT, text);
	}

	public static void nextBattle()
	{
		resurrectTeams();
		healTeams();
		teleportPlayersToSavedCoords();

		if(!fillNextTeams())
			return;

		team1_live_list.clear();
		team2_live_list.clear();

		team1_live_list.addAll(team1.getOnlineMembers());
		team2_live_list.addAll(team2.getOnlineMembers());

		_hooks = true;

		npcSay(_manager, Say2C.SHOUT, "Бой начнется через 2 минуты.");

		clearArena();
		saveBackCoords();
		teleportTeams();
		root();
		deBuffTeams();
		resurrectTeams();
		healTeams();
		_startBattleTask = addTask("startBattle", 120000);
		_endBattleTask = addTask("endBattle", 240000);
	}

	public static void startBattle()
	{
		endRoot();
		npcSay(_manager, Say2C.SHOUT, "FIGHT!!!");
	}

	public static void endBattle()
	{
		_hooks = false;
		npcSay(_manager, Say2C.SHOUT, "Время истекло.");
		calculateWinner();
		npcSay(_manager, Say2C.SHOUT, "Следующий бой начнется через 1 минуту.");
		executeTask(_manager, "events.tournament.Tournament_battle", "nextBattle", new Object[0], 60000);
	}

	public static boolean fillNextTeams()
	{
		npcSay(_manager, Say2C.SHOUT, _current_cycle + " этап.");
		while(!Tournament_data.fillNextTeams(_category))
		{
			// начало следующего цикла
			if(Tournament_data.createTournamentTable(_category))
				return false;
			_current_cycle++;
			//npcSayToAll(_manager, _current_cycle + " этап.");
		}
		return true;
	}

	public static void endTournament()
	{
		Tournament_register.endTournament();
	}

	public static void calculateWinner()
	{
		int hp1 = 0;
		int hp2 = 0;

		for(L2Player player : team1_live_list)
			if(player != null && !player.isDead())
				hp1 += player.getCurrentHp();

		for(L2Player player : team2_live_list)
			if(player != null && !player.isDead())
				hp2 += player.getCurrentHp();

		if(hp2 > hp1)
		{
			Tournament_data.teamWin(team2.getId());
			Tournament_data.teamLost(team1.getId());
			Tournament_data.removeRecordFromTournamentTable(team1.getId());
		}
		else
		{
			Tournament_data.teamWin(team1.getId());
			Tournament_data.teamLost(team2.getId());
			Tournament_data.removeRecordFromTournamentTable(team1.getId());
		}
	}

	public static void clearArena()
	{
		for(L2Player player : L2ObjectsStorage.getAllPlayers())
			if(player != null && _zone.isInsideZone(player))
				teleportToColiseumSpawn(player);
	}

	public static void teleportToColiseumSpawn(L2Player player)
	{
		player.teleToLocation(_zone.getSpawn());
	}

	public static void teleportTeams()
	{
		int i = 0;
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				player.teleToLocation(team1_points.get(i));
				player.setTeam(1);
				i++;
			}
		i = 0;
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				player.teleToLocation(team2_points.get(i));
				player.setTeam(2);
				i++;
			}
	}

	public static void resurrectTeams()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
				player.setCurrentCp(player.getMaxCp());
				player.restoreExp();
				player.broadcastPacket(new Revive(player));
				player.doRevive();
			}
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
				player.setCurrentCp(player.getMaxCp());
				player.restoreExp();
				player.broadcastPacket(new Revive(player));
				player.doRevive();
			}
	}

	public static void healTeams()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
				player.setCurrentCp(player.getMaxCp());
				if(player.isPetSummoned())
				{
					player.getPet().setCurrentHpMp(player.getPet().getMaxHp(), player.getPet().getMaxMp());
					player.getPet().setCurrentCp(player.getPet().getMaxCp());
				}
			}
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				player.setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
				player.setCurrentCp(player.getMaxCp());
				if(player.isPetSummoned())
				{
					player.getPet().setCurrentHpMp(player.getPet().getMaxHp(), player.getPet().getMaxMp());
					player.getPet().setCurrentCp(player.getPet().getMaxCp());
				}
			}
	}

	public static void deBuffTeams()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				player.stopAllEffects();
				if(player.isPetSummoned())
					player.getPet().stopAllEffects();
			}
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				player.stopAllEffects();
				if(player.isPetSummoned())
					player.getPet().stopAllEffects();
			}
	}

	public static void root()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				player.setImobilised(true);
				if(player.isPetSummoned())
					player.getPet().setImobilised(true);
			}
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				player.setImobilised(true);
				if(player.isPetSummoned())
					player.getPet().setImobilised(true);
			}
	}

	public static void endRoot()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				player.setImobilised(false);
				if(player.isPetSummoned())
					player.getPet().setImobilised(false);
			}
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				player.setImobilised(false);
				if(player.isPetSummoned())
					player.getPet().setImobilised(false);
			}
	}

	public static void saveBackCoords()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
				player.setVar("Tournament_backCoords", player.getX() + " " + player.getY() + " " + player.getZ());
		for(L2Player player : team1_live_list)
			if(player != null)
				player.setVar("Tournament_backCoords", player.getX() + " " + player.getY() + " " + player.getZ());
	}

	public static void teleportPlayersToSavedCoords()
	{
		for(L2Player player : team1_live_list)
			if(player != null)
			{
				String var = player.getVar("Tournament_backCoords");
				if(var == null || var.equals(""))
					continue;
				String[] coords = var.split(" ");
				if(coords.length != 3)
					continue;
				Location pos = new Location(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2]));
				player.teleToLocation(pos);
				player.unsetVar("Tournament_backCoords");
				player.setTeam(0);
			}
		for(L2Player player : team2_live_list)
			if(player != null)
			{
				String var = player.getVar("Tournament_backCoords");
				if(var == null || var.equals(""))
					continue;
				String[] coords = var.split(" ");
				if(coords.length != 3)
					continue;
				player.teleToLocation(new Location(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])));
				player.unsetVar("Tournament_backCoords");
				player.setTeam(0);
			}
	}

	public static void giveItemsToWinner(Team team)
	{
	// TODO
	}

	@Override
	public Location onEscape(L2Player player)
	{
		if(_hooks && player != null)
			OnPlayerExit(player);
		return null;
	}

	@Override
	public void onDie(L2Character self, L2Character killer)
	{
		if(_hooks && self != null && self.isPlayer())
			OnPlayerExit((L2Player) self);
	}

	public static void OnPlayerExit(L2Player player)
	{
		if(_hooks && player != null)
			if(team1_live_list.contains(player))
			{
				team1_live_list.remove(player);
				player.setTeam(0);
				if(team1_live_list.size() == 0)
				{
					Tournament_data.teamWin(team2.getId());
					Tournament_data.teamLost(team1.getId());
					Tournament_data.removeRecordFromTournamentTable(team1.getId());

					if(_endBattleTask != null)
						_endBattleTask.cancel(true);
					_endBattleTask = null;

					_hooks = false;

					npcSay(_manager, Say2C.SHOUT, "Следующий бой начнется через 1 минуту.");
					executeTask(_manager, "events.tournament.Tournament_battle", "nextBattle", new Object[0], 60000);
				}
			}
			else if(team2_live_list.contains(player))
			{
				team2_live_list.remove(player);
				player.setTeam(0);
				if(team2_live_list.size() == 0)
				{
					Tournament_data.teamWin(team1.getId());
					Tournament_data.teamLost(team2.getId());
					Tournament_data.removeRecordFromTournamentTable(team1.getId());

					if(_endBattleTask != null)
						_endBattleTask.cancel(true);
					_endBattleTask = null;

					_hooks = false;

					npcSay(_manager, Say2C.SHOUT, "Следующий бой начнется через 1 минуту.");
					executeTask(_manager, "events.tournament.Tournament_battle", "nextBattle", new Object[0], 60000);
				}
			}
	}

	private class ZoneListener extends L2ZoneEnterLeaveListener
	{
		public void objectEntered(L2Zone zone, L2Character object)
		{
			if(_hooks && object != null && object.getPlayer() != null && !team1_live_list.contains(object.getPlayer()) && !team2_live_list.contains(object.getPlayer()))
				object.teleToLocation(147451, 46728, -3410);
		}

		public void objectLeaved(L2Zone zone, L2Character object)
		{
			L2Player player = object.getPlayer();
			if(_hooks && player != null && (team1_live_list.contains(player) || team2_live_list.contains(player)))
			{
				L2Playable playable = (L2Playable) object;
				double angle = Util.convertHeadingToDegree(playable.getHeading()); // угол в градусах
				double radian = Math.toRadians(angle - 90); // угол в радианах
				playable.teleToLocation((int) (playable.getX() + 50 * Math.sin(radian)), (int) (playable.getY() - 50 * Math.cos(radian)), playable.getZ());
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}

	private static ArrayList<L2Spawn> _spawns = new ArrayList<L2Spawn>();

	private static boolean _active = false;

	/**
	 * Читает статус эвента из базы.
	 * @return
	 */
	private static boolean isActive()
	{
		return ServerVariables.getString("Tournament", "off").equalsIgnoreCase("on");
	}

	/**
	* Запускает эвент
	*/
	public void startEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;

		if(!isActive())
		{
			ServerVariables.set("Tournament", "on");
			spawnEventManagers();
			_log.info("Event 'Tournament' started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.Tournament.AnnounceEventStarted", null);
		}
		else
			player.sendMessage("Event 'Tournament' already started.");

		_active = true;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	* Останавливает эвент
	*/
	public void stopEvent()
	{
		L2Player player = (L2Player) self;
		if(!AdminTemplateManager.checkBoolean("eventMaster", player))
			return;
		if(isActive())
		{
			ServerVariables.unset("Tournament");
			unSpawnEventManagers();
			_log.info("Event 'Tournament' stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.Tournament.AnnounceEventStoped", null);
		}
		else
			player.sendMessage("Event 'Tournament' not started.");

		_active = false;

		show(Files.read("data/html/admin/events.htm", player), player);
	}

	/**
	 * Спавнит эвент менеджеров
	 */
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] = { { 82545, 148600, -3505, -3395 } };

		L2NpcTemplate template = NpcTable.getTemplate(32130);
		for(int[] element : EVENT_MANAGERS)
			try
			{
				L2Spawn sp = new L2Spawn(template);
				sp.setLocx(element[0]);
				sp.setLocy(element[1]);
				sp.setLocz(element[2]);
				sp.setAmount(1);
				sp.setHeading(element[3]);
				sp.setRespawnDelay(0);
				sp.init();
				sp.getLastSpawn().setAI(new Tournament_ai(sp.getLastSpawn()));
				_spawns.add(sp);
			}
			catch(ClassNotFoundException e)
			{
				e.printStackTrace();
			}
	}

	/**
	 * Удаляет спавн эвент менеджеров
	 */
	private void unSpawnEventManagers()
	{
		for(L2Spawn sp : _spawns)
		{
			sp.stopRespawn();
			sp.getLastSpawn().deleteMe();
		}
		_spawns.clear();
	}

	public void onLoad()
	{
		team1_points.add(new Location(148780, 46719, -3448));
		team1_points.add(new Location(148789, 46568, -3448));
		team1_points.add(new Location(148785, 46907, -3448));
		team2_points.add(new Location(150117, 46725, -3448));
		team2_points.add(new Location(150129, 46901, -3448));
		team2_points.add(new Location(150128, 46546, -3448));

		_zone.getListenerEngine().addMethodInvokedListener(_zoneListener);

		if(isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: Tournament [state: activated]");
		}
		else
			_log.info("Loaded Event: Tournament [state: deactivated]");
	}

	public void onReload()
	{
		_zone.getListenerEngine().removeMethodInvokedListener(_zoneListener);
		unSpawnEventManagers();
	}

	public void onShutdown()
	{
		onReload();
	}
}