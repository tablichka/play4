package events.lastHero;

import events.Capture.Capture;
import events.TvT.TvT;
import javolution.util.FastList;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.olympiad.Olympiad;
import ru.l2gw.gameserver.model.gmaccess.AdminTemplateManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Files;
import ru.l2gw.util.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class LastHero extends Functions implements ScriptFile, IOnDieHandler
{
	public L2Object self;
	public L2NpcInstance npc;

	private static FastList<Integer> registered = new FastList<Integer>();
	private static FastList<Integer> participants = new FastList<Integer>();
	private static FastList<L2Player> emptyList = new FastList<L2Player>(0);
	private static Instance lastHeroInstance;

	private static Integer _status = 0;

	private static Boolean _running = false;
	// Статус запуска

	private static Boolean _alternate_form = Config.EVENT_LastHero_heroMod;
	// Алтернативная формула проведения эвента

	private static Boolean _lvlSort = Config.EVENT_LastHero_sortBylvl;
	// Заготовка для сортировки по лвлам

	private static Integer _time_to_start = Config.EVENT_LastHeroTime;
	// Время до начала боя

	private static Integer _event_time = Config.EVENT_LastHero_FightTime;
	// Продолжительность эвента

	private static Integer _bonus_id = Config.EVENT_LastHeroBonusID;
	// Продолжительность эвента

	private static Integer _bonus_count = Config.EVENT_LastHeroBonusCount;
	// Продолжительность эвента

	private static String _ann1 = Config.EVENT_LastHero_ruleMsg1;
	private static String _ann2 = Config.EVENT_LastHero_ruleMsg2;
	private static String _ann3 = Config.EVENT_LastHero_ruleMsg3;
	private static String _ann4 = Config.EVENT_LastHero_ruleMsg4;
	private static String _invMsg = Config.EVENT_LastHero_msgInv;
	private static String _startMsg = Config.EVENT_LastHero_msgStart;
	private static String _stopMsg = Config.EVENT_LastHero_msgStopEv;
	private static String _missMsg = Config.EVENT_LastHero_msgMiss;
	private static String _min_rem = " минут осталось до запуска...";
	private static String _event_end = Config.EVENT_LastHero_msgEndEv;
	private static String _no_winers = Config.EVENT_LastHero_msgNoWIn;
	private static String _die_msg = Config.EVENT_LastHero_msgDie;
	private static String _back_msg = Config.EVENT_LastHero_msgTP;
	private static String _prep_msg = Config.EVENT_LastHero_msgPrep;
	private static String _fight_msg = Config.EVENT_LastHero_msgFight;

	private static boolean dispel = Config.EVENT_LastHero_dispel;

	@SuppressWarnings("unchecked")
	private static ScheduledFuture _endTask;
	private static ScheduledFuture _cycleTask;

	private static ZoneListener zoneListener = new ZoneListener();

	public void onLoad()
	{
		if(Config.EVENT_LastHero_enabled)
		{
			long startTime = Config.EVENT_LastHero_cron.timeNextUsage(System.currentTimeMillis());
			_log.info("Loaded Event: Last Hero [state: activated] event start: " + new Date(startTime));
			_cycleTask = executeTask("events.lastHero.LastHero", "start", new Object[0], startTime - System.currentTimeMillis());
		}
		else
			_log.info("Loaded Event: Last Hero [state: deactivated]");
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	public static boolean isRunned()
	{
		return _running;
	}

	public String DialogAppend_31225(Integer val)
	{
		if(val == 0)
		{
			L2Player player = (L2Player) self;
			return Files.read("data/scripts/events/lastHero/31225.html", player);
		}
		return "";
	}

	public void start()
	{
		if(self != null)
			if(!AdminTemplateManager.checkBoolean("eventMaster", (L2Player) self))
				return;

		if(_status != 0)
		{
			_log.info("Event: Last Hero not started! status: " + _status);
			if(self != null)
				((L2Player) self).sendMessage("Last Hero is running! status: " + _status);

			return;
		}

		_log.info("Event: Last Hero started!");

		_status = 1;
		_running = true;
		_time_to_start = Config.EVENT_LastHeroTime;

		participants.clear();

		Announcements.getInstance().announceToAll(String.valueOf(_startMsg));

		executeTask("events.lastHero.LastHero", "question", new Object[0], 20000L);
		executeTask("events.lastHero.LastHero", "announce", new Object[0], 50000L);
	}

	public static void question()
	{
		for(L2Player player : L2ObjectsStorage.getAllPlayers())
			if(checkPlayerCondition(player)	&& !TvT.isRegistered(player) && !Capture.isRegistered(player))
				player.scriptRequest(String.valueOf(_invMsg), "events.lastHero.LastHero:addPlayer", new Object[0]);
	}

	public static boolean isRegistered(L2Player player)
	{
		return Config.EVENT_LastHero_enabled && registered.contains(player.getObjectId());
	}

	public void addPlayer()
	{
		if(!(self instanceof L2Player))
			return;

		L2Player player = (L2Player) self;

		if(_status != 1)
		{
			player.sendMessage("Нельзя зарегистрироваться на эвент в это время.");
			return;
		}

		if(!checkPlayerCondition(player) || TvT.isRegistered(player) || Capture.isRegistered(player))
		{
			player.sendMessage("Вы не соответсвуете требования для учестия в эвенте.");
			return;
		}

		if(!registered.contains(player.getObjectId()))
		{
			player.sendMessage("Вы зарегистрированы для участия в Last Hero.");
			registered.add(player.getObjectId());
		}
		else
			player.sendMessage("Вы уже зарегистрированы.");
	}

	public static void announce()
	{
		Announcements a = Announcements.getInstance();
		if(registered.size() < Config.EVENT_LastHeroMinParticipants)
		{
			a.announceToAll(String.valueOf(_stopMsg));
			registered.clear();
			_log.info("Event: Last Hero no minimum participants.");
			rescheduleEvent();
			return;
		}

		if(_time_to_start > 1)
		{
			_time_to_start--;
			a.announceToAll(_time_to_start + _min_rem);

			if(_time_to_start % 3 == 0)
				a.announceToAll(String.valueOf(_missMsg));

			executeTask("events.lastHero.LastHero", "announce", new Object[0], 60000L);
		}
		else
		{
			a.announceToAll(_prep_msg);
			executeTask("events.lastHero.LastHero", "prepare", new Object[0], 5000L);
		}
	}

	public static void prepare()
	{
		if(!_running)
			return;

		teleportPlayersToColiseum();
		executeTask("events.lastHero.LastHero", "go", new Object[0], 120000L);

		Announcements a = Announcements.getInstance();
		a.announceToAll(String.valueOf(_ann1));
		a.announceToAll(String.valueOf(_ann2));
		a.announceToAll(String.valueOf(_ann3));
		a.announceToAll(String.valueOf(_ann4));
	}

	public static void teleportPlayersToColiseum()
	{
		_status = 2;

		FastList<L2Player> players = FastList.newInstance();
		for(int objectId : registered)
		{
			L2Player player = L2ObjectsStorage.getPlayer(objectId);
			if(player == null || player.isInOfflineMode() || player.isInOlympiadMode() || player.inObserverMode() || player.isInDuel() || player.isAlikeDead()
					|| player.isInCombat() || player.isCastingNow() || Olympiad.isRegisteredInComp(player) || player.getReflection() != 0 || player.isInBoat())
				continue;

			participants.add(player.getObjectId());
			players.add(player);
		}

		registered.clear();

		if(players.size() < Config.EVENT_LastHeroMinParticipants)
		{
			participants.clear();
			Announcements.getInstance().announceToAll(String.valueOf(_stopMsg));
			_log.info("Event: Last Hero no minimum participants.");
			FastList.recycle(players);
			rescheduleEvent();
			return;
		}

		lastHeroInstance = InstanceManager.getInstance().createNewInstance(-1, emptyList);
		lastHeroInstance.getTemplate().getZone().setActive(true, lastHeroInstance.getReflection());
		lastHeroInstance.getTemplate().getZone().getListenerEngine().addMethodInvokedListener(zoneListener);
		lastHeroInstance.startInstance();

		L2Skill nobleSkill = SkillTable.getInstance().getInfo(1323, 1);
		L2Skill cancelSkill = SkillTable.getInstance().getInfo(4334, 1);

		for(L2Player player : players)
		{
			player.block();
			if(player.getParty() != null)
				player.getParty().removePartyMember(player);
			player.setStablePoint(player.getLoc());
			player.teleToLocation(Location.coordsRandomize(149505, 46719, -3417, 0, 0, 500), lastHeroInstance.getReflection());
			player.setTeam(2);
			if(dispel)
			{
				List<L2Character> targets = new ArrayList<>(1);
				targets.add(player);
				cancelSkill.useSkill(player, targets);
			}
			else
			{
				player.stopEffects("hero");
				player.stopEffects("barrier");
				player.stopEffects("mystic_immunity");
			}

			nobleSkill.applyEffects(player, player, false);
			if(player.getPet() != null)
			{
				player.getPet().block();
				if(dispel)
					cancelSkill.applyEffects(player, player.getPet(), false);
				else
				{
					player.getPet().stopEffects("hero");
					player.getPet().stopEffects("barrier");
					player.getPet().stopEffects("mystic_immunity");
				}
			}
		}

		FastList.recycle(players);
	}

	public static void go()
	{
		_status = 3;

		FastList<L2Player> players = getParticipants();
		if(players.size() < Config.EVENT_LastHeroMinParticipants)
		{
			participants.clear();
			Announcements.getInstance().announceToAll(String.valueOf(_stopMsg));
			_log.info("Event: Last Hero no minimum participants.");
			rescheduleEvent();
			FastList.recycle(players);
			return;
		}

		_log.info("Event: Last Hero start battle, participants: " + players.size());

		ExShowScreenMessage msg = new ExShowScreenMessage(">> Start FIGHT <<", 3000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, true);
		for(L2Player player : players)
		{
			player.unblock();
			player.sendPacket(msg);
			if(player.getPet() != null)
				player.getPet().unblock();
		}

		FastList.recycle(players);
		Announcements.getInstance().announceToAll(_fight_msg);

		_endTask = executeTask("events.lastHero.LastHero", "endBattle", new Object[0], _event_time * 60000L);
	}

	public static FastList<L2Player> getParticipants()
	{
		if(lastHeroInstance == null)
			return emptyList;

		Reflection ref = ReflectionTable.getInstance().getById(lastHeroInstance.getReflection());
		if(ref == null)
			return emptyList;

		FastList<L2Player> players = FastList.newInstance();
		for(L2Object cha : ref.getAllObjects())
			if(cha instanceof L2Player && !((L2Player) cha).isDeleting() && ((L2Player) cha).isOnline() && participants.contains(cha.getObjectId()))
				players.add((L2Player) cha);

		return players;
	}

	public static void endBattle()
	{
		Announcements a = Announcements.getInstance();
		a.announceToAll(_event_end);

		_log.info("Event: Last Hero battle end, no winner.");
		FastList<L2Player> players = getParticipants();

		for(L2Player player : players)
			if(!player.isDead())
				_log.info("Event: Last Hero live " + player + " at " + player.getLoc() + " visible: " + player.isVisible() + " isHide: " + player.isHide() + " isOnline: " + player.isOnline() + " teleport: " + player.isTeleporting());

		a.announceToAll(_no_winers);
		a.announceToAll(_back_msg);

		executeTask("events.lastHero.LastHero", "end", new Object[0], 5000L);
		rescheduleEvent();

		FastList.recycle(players);

		if(_endTask != null)
			_endTask.cancel(false);
		_endTask = null;
	}

	public static void end()
	{
		FastList<L2Player> players = getParticipants();
		for(L2Player player : players)
		{
			player.stopEffectsByName("c_fake_death");
			if(player.isDead())
				player.doRevive();
			player.setCurrentCp(player.getMaxCp());
			player.setCurrentHp(player.getMaxHp());
			player.setCurrentMp(player.getMaxMp());
			player.unsetVar("LH_REWARD");
			player.setTeam(0);
			player.teleToLocation(player.getStablePoint(), 0);
			player.setStablePoint(null);
			player.unblock();
			if(player.getPet() != null)
				player.getPet().unblock();
		}

		lastHeroInstance.getTemplate().getZone().getListenerEngine().removeMethodInvokedListener(zoneListener);
		lastHeroInstance.getTemplate().getZone().setActive(false, lastHeroInstance.getReflection());
		lastHeroInstance.stopInstance();
		lastHeroInstance = null;
		FastList.recycle(players);
	}

	@Override
	public void onDie(L2Character killed, L2Character killer)
	{
		if(_status == 3 && killed instanceof L2Player && killed.getReflection() == lastHeroInstance.getReflection())
		{
			((L2Player) killed).sendMessage(_die_msg);
			((L2Player) killed).setTeam(0);
			if(!_alternate_form)
			{
				if(killer != null && killer.isPlayer())
					if(Config.EVENT_LastHeroRate)
						((L2Player) killer).addItem("last_hero", _bonus_id, ((L2Player) killed).getLevel() * _bonus_count, null, true);
					else
						((L2Player) killer).addItem("last_hero", _bonus_id, _bonus_count, null, true);
			}
			else if(killer != null && killer.getPlayer() != null)
			{
				L2Player player = killer.getPlayer();
				int pReward = player.getVarInt("LH_REWARD");

				if(Config.EVENT_LastHeroRate)
					pReward += ((L2Player) killed).getLevel() * _bonus_count;
				else
					// Give one-time reward to winner, if "Hero Mod" enabled and rate accumulative disabled
					if(pReward <= 0)
						pReward = _bonus_count;

				player.setVar("LH_REWARD", String.valueOf(pReward));

				L2Player lastHero = getLastHero();

				if(lastHero != null)
				{
					endByWinner(lastHero);

					if(_endTask != null)
						_endTask.cancel(false);
					_endTask = null;
				}
			}
		}
	}

	private static void endByWinner(L2Player lastHero)
	{
		lastHero.addItem("last_hero", _bonus_id, lastHero.getVarInt("LH_REWARD"), null, true);
		lastHero.setHero(true);
		Announcements a = Announcements.getInstance();
		a.announceToAll(lastHero.getName() + " has become a 'Last Hero'!!!");
		a.announceToAll(_event_end);
		_log.info("Event: Last Hero battle end, winner: " + lastHero + " at " + lastHero.getLoc() + " visible: " + lastHero.isVisible());
		a.announceToAll(_back_msg);
		executeTask("events.lastHero.LastHero", "end", new Object[0], 5000L);
		rescheduleEvent();
	}

	private static L2Player getLastHero()
	{
		FastList<L2Player> players = getParticipants();
		L2Player lastHero = null;
		try
		{
			int liveCount = 0;
			for(L2Player player : players)
				if(!player.isVisible())
					_log.info("Event: Last Hero getLastHero: " + player + " at " + player.getLoc() + " visible: " + player.isVisible() + " teleport: " + player.isTeleporting());
				else if(!player.isDead() && lastHeroInstance.getTemplate().getZone().isInsideZone(player))
				{
					liveCount++;
					lastHero = player;
					if(liveCount > 1)
					{
						lastHero = null;
						break;
					}
				}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			FastList.recycle(players);
		}
		return lastHero;
	}

	private static void rescheduleEvent()
	{
		_status = 0;
		_running = false;

		if(Config.EVENT_LastHero_enabled)
		{
			if(_cycleTask != null)
				_cycleTask.cancel(true);

			long startTime = Config.EVENT_LastHero_cron.timeNextUsage(System.currentTimeMillis());
			_log.info("Event: Last Hero next start: " + new Date(startTime));
			_cycleTask = executeTask("events.lastHero.LastHero", "start", new Object[0], startTime - System.currentTimeMillis());
		}
	}

	public static void OnPlayerExit(L2Player player)
	{
		if(_status < 2 && registered.contains(player.getObjectId()))
			registered.remove((Integer) player.getObjectId());
		else if(_status >= 2 && participants.contains(player.getObjectId()))
		{
			participants.remove((Integer) player.getObjectId());
			if(_alternate_form)
			{
				L2Player lastHero = getLastHero();
				if(lastHero != null)
				{
					endByWinner(lastHero);

					if(_endTask != null)
						_endTask.cancel(false);
					_endTask = null;
				}
			}
			else
			{
				FastList<L2Player> players = getParticipants();
				int c = 0;
				for(L2Player participant : players)
					if(!participant.isDead())
						c++;
				if(c < 2)
					endBattle();
				FastList.recycle(players);
			}
		}
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

			if(object instanceof L2Player && _status >= 2 && participants.contains(object.getObjectId()))
			{
				_log.info("Event: Last Hero player leaved zone on battle: " + object + " " + object.getLoc());
				participants.remove((Integer) object.getObjectId());
				if(_alternate_form)
				{
					L2Player lastHero = getLastHero();
					if(lastHero != null)
					{
						endByWinner(lastHero);

						if(_endTask != null)
							_endTask.cancel(false);
						_endTask = null;
					}
				}
				else
				{
					FastList<L2Player> players = getParticipants();
					int c = 0;
					for(L2Player participant : players)
						if(!participant.isDead())
							c++;
					if(c < 2)
						endBattle();
					FastList.recycle(players);
				}
				object.setReflection(0);
				object.unblock();
				if(object.getPet() != null)
					object.getPet().unblock();
				((L2Player) object).setTeam(0);
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}
}
