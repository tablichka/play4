package events.TheInfectedMonsters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.commons.crontab.Scheduler;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.Announcements;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.handler.IOnDieHandler;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.instancemanager.TownManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
/**
 * Event "Infected Monsters"
 *
 * @author sandro [city55ru@gmail.com]
 * @version 1.0
 */
public final class InfectedMonsters extends Functions implements ScriptFile, IOnDieHandler
{

	/**
	 * Event progress time (in minutes)
	 */
	private static final int progressTime = 10;

	/**
	 * How many mobs should be selected
	 */
	private static final Integer selectionAmount = 20;

	/**
	 * Visual effects id
	 */
	private static final Integer visualEffectId = 768;

	/**
	 * The max difference between player and monster for drop fetching
	 */
	private static final Integer levelDifference = 9;

	/**
	 * if skill base time is 30 seconds, skill will be effective on monster 5
	 * minutes
	 */
	private static final int visualEffectMul = progressTime * 60000;

	/**
	 * How much HP in percent mob should be got (or higher)
	 */
	private static final Integer hpPercent = 100;

	/**
	 * Low limit for mob selection criteria
	 */
	private static final Integer minMobLevel = 3;

	/**
	 * High limit for mob selection criteria
	 */
	private static final Integer maxMobLevel = 90;

	/**
	 * Does this mob may be a Raid Boss
	 */
	private static final Boolean checkRaid = false;

	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * Effects for infected monsters
	 */
	private static final List<Integer> effects = new LinkedList<Integer>();

	/**
	 * Event's name
	 */
	private static final String eventName = InfectedMonsters.class.getSimpleName();

	/**
	 * Logger
	 */
	private static final Log _logger = LogFactory.getLog(InfectedMonsters.class.getName());

	/**
	 * The execution schedule
	 */
	private static final Map<Integer, String> schedule = new HashMap<Integer, String>();

	/**
	 * There are items these may be dropped from monster
	 */
	private static final List<EventItem> dropItems = new LinkedList<EventItem>();

	/**
	 * Selected monsters
	 */
	private static final GCSArray<L2MonsterInstance> infectedMonsters = new GCSArray<L2MonsterInstance>();

	/**
	 * Item
	 */
	private static EventItem item;

	/**
	 * Executor
	 */
	private static ScheduledFuture<?> executor;

	/**
	 * Event progress controller
	 */
	private static ScheduledFuture<?> progressController;

	static
	{
		/** Event schedule */
		// Less verbose output for scheduler
		Scheduler.getInstance().setDebug(true);
		//
		schedule.put(Calendar.MONDAY, "08:00,10:00,12:00,14:00,16:00,18:10,18:30,19:00,19:30,20:00,20:30,21:00,22:00,23:00");
		schedule.put(Calendar.TUESDAY, "03:00,08:00,12:00,15:00,17:00,19:00,22:00");
		schedule.put(Calendar.WEDNESDAY, "03:00,08:00,12:00,15:00,17:00,19:00,22:00");
		schedule.put(Calendar.THURSDAY, "03:00,08:00,12:00,15:00,17:00,19:00,22:00");
		schedule.put(Calendar.FRIDAY, "03:00,08:00,12:00,15:00,17:00,19:00,22:00");
		schedule.put(Calendar.SATURDAY, "03:00,08:00,12:00,15:00,17:00,19:00,22:00");
		schedule.put(Calendar.SUNDAY, "03:00,08:00,12:00,15:00,17:00,19:00,22:00");

		/** Monster effects identifiers */
		effects.add(768); // Кораблик
		effects.add(4345); // Might
		effects.add(4355); // Acumen
		effects.add(4356); // Empower
		effects.add(4357); // Haste
		effects.add(4359); // Focus
		effects.add(7059); // Wild Magic
		effects.add(1389); // Greater Shield
		effects.add(4346); // Mental Shield
		effects.add(1182); // Resist Aqua
		effects.add(1189); // Resist Wind
		effects.add(1191); // Resist Fire
		effects.add(1352); // Elemental Protection
		effects.add(4342); // Wind Walk
		effects.add(4347); // Bless the Body
		effects.add(4348); // Bless the Soul
		effects.add(4352); // Berserker Spirit
		effects.add(271); // Dance of Warrior
		effects.add(273); // Dance of Mystic
		effects.add(274); // Dance of Fire
		effects.add(275); // Dance of Fury
		effects.add(307); // Dance of Aqua Guard
		effects.add(309); // Dance of Earth Guard
		effects.add(310); // Dance of Vampire
		effects.add(311); // Dance of Protection
		effects.add(530); // Dance of Alignment
		effects.add(765); // Dance of Blade Storm
		effects.add(1363); // Chant of Victory
		effects.add(4700); // Gift of Queen
		effects.add(4703); // Gift of Seraphim

		/** Drop items */

		// First item
		item = new EventItem();
		item.setId(57);
		item.setAmount(1000000);
		item.setProbability(10);

		item = new EventItem();
		item.setId(57);
		item.setAmount(1000000);
		item.setProbability(20);

		item = new EventItem();
		item.setId(4356);
		item.setAmount(1);
		item.setProbability(50);

		item = new EventItem();
		item.setId(4356);
		item.setAmount(2);
		item.setProbability(1);

		item = new EventItem();
		item.setId(4356);
		item.setAmount(3);
		item.setProbability(1);

		item = new EventItem();
		item.setId(4357);
		item.setAmount(1);
		item.setProbability(100);

		item = new EventItem();
		item.setId(4357);
		item.setAmount(2);
		item.setProbability(10);

		item = new EventItem();
		item.setId(4357);
		item.setAmount(3);
		item.setProbability(10);

		item = new EventItem();
		item.setId(4357);
		item.setAmount(10);
		item.setProbability(1);

		item = new EventItem();
		item.setId(13457);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13458);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13459);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13460);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13461);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13462);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13463);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13464);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13465);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13466);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13467);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13468);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13469);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13470);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13471);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(13884);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(14111);
		item.setAmount(1);
		item.setProbability(1 / 10);

		item = new EventItem();
		item.setId(9441);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9442);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9443);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9444);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9445);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9446);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9447);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9448);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9449);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9450);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(9529);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(10004);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(10252);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(10253);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(11532);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(11569);
		item.setAmount(1);
		item.setProbability(2 / 10);

		item = new EventItem();
		item.setId(13882);
		item.setAmount(1);
		item.setProbability(2 / 10);
	}

	/**
	 * Returns event's activity
	 *
	 * @return true if event has activated
	 */
	private static boolean isActive()
	{
		return "on".equalsIgnoreCase(ServerVariables.getString(eventName, "off"));
	}

	/**
	 * Starts task for next execution
	 */
	private static void startScheduleListening()
	{

		Scheduler.getInstance().register(eventName, schedule);

		final long nextExecution = Scheduler.getInstance().getNextInterval(eventName);

		executor = ThreadPoolManager.getInstance().scheduleGeneral(new ExecuteService(), nextExecution);
	}

	/**
	 * Starts up an event
	 */
	public static void activateEvent()
	{

		if(isActive())
		{
			_logger.warn("Event " + eventName + " already activated.");
			return;
		}

		ServerVariables.set(eventName, "on");
		_logger.info("Event " + eventName + " activated.");

		startScheduleListening();
	}

	/**
	 * Stops an event
	 */
	public static void deactivateEvent()
	{

		if(!isActive())
		{
			_logger.warn("Event " + eventName + " not activate.");
			return;
		}

		stopEvent();

		ServerVariables.set(eventName, "off");
		executor.cancel(true);
		_logger.info("Event " + eventName + " deactivated.");
	}

	/**
	 * Returns true if event is running
	 *
	 * @return
	 */
	private static Boolean inProgress()
	{
		return (progressController != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see l2p.extensions.scripts.ScriptFile#onLoad()
	 */

	@Override
	public void onLoad()
	{
		if(isActive())
		{
			_logger.info("Loaded Event: \"" + eventName
					+ "\" [state: activated]");
			startScheduleListening();
		}
		else
			_logger.info("Loaded Event: \"" + eventName
					+ "\" [state: deactivated]");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see l2p.extensions.scripts.ScriptFile#onReload()
	 */

	@Override
	public void onReload()
	{
		stopEvent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see l2p.extensions.scripts.ScriptFile#onShutdown()
	 */

	@Override
	public void onShutdown()
	{
		stopEvent();
	}

	/**
	 * Invokes onDIE event
	 *
	 * @param victim
	 * @param killer
	 */
	@Override
	public void onDie(final L2Character victim, final L2Character killer)
	{

		if(!(isActive() && inProgress()))
		{
			return;
		}

		if(victim != null && victim.isMonster() && infectedMonsters.contains(victim))
		{
			((L2MonsterInstance) victim).setDisplayId(0);
			infectedMonsters.remove(victim);
			_logger.info("Infected mob killed! Remaining " + infectedMonsters.size() + " monsters");
			Announcements.getInstance().announceToAll("Избранник Шилен был убит игроком " + killer.getName() + ", осталось " + infectedMonsters.size() + ".");

			for(final EventItem item : dropItems)
			{
				if(killer.getLevel() - victim.getLevel() < levelDifference && Rnd.chance(item.getProbability()))
				{
					addItem(killer.getPlayer(), item.getId(), item.getAmount());
				}
			}

			if(infectedMonsters.isEmpty())
			{
				stopEvent();
			}
		}
	}

	/**
	 * Starts up event
	 */
	public static void startEvent()
	{
		_logger.info("Event " + eventName + " started");

		extractMonsters();

		if(infectedMonsters.isEmpty())
		{
			_logger.info("There are no infected monsters found! Please check selection conditions and restart event");
			stopEvent();
			return;
		}

		Announcements.getInstance().announceToAll("Дух Шилен спускается в наш Мир!");

		progressController = ThreadPoolManager.getInstance().scheduleGeneral(new StopService(), progressTime * 60 * 1000L);

		final L2Skill visSkill = SkillTable.getInstance().getInfo(visualEffectId, 1);

		for(final L2MonsterInstance monster : infectedMonsters)
		{
			monster.setTitle("-INFECTED-");
			monster.setDisplayId(monster.getNpcId());
			// set up visual effect
			visSkill.applyEffects(monster, monster, false, visualEffectMul);

			for(final int skillId : effects)
			{
				final L2Skill skill = SkillTable.getInstance().getInfo(skillId,	1);
				skill.applyEffects(monster, monster, false, visualEffectMul);
				monster.broadcastPacket(new MagicSkillUse(monster, monster, skill.getDisplayId(), skill.getLevel(), 0, 0));
			}

			sendAnnounce(monster);
		}
	}

	/**
	 * Broadcasts announce through the world
	 *
	 * @param monster
	 */
	private static void sendAnnounce(final L2MonsterInstance monster)
	{
		final String nearestTown = TownManager.getInstance().getClosestTownName(monster);
		Announcements.getInstance().announceToAll("Дух вселился в " + monster.getName() + " около " + nearestTown + "(x:" + monster.getX() + " y:" + monster.getY() + ")");
	}

	/**
	 * Stops event
	 */
	private static void stopEvent()
	{
		new Thread(new StopService()).start();
	}

	/**
	 * Selects monsters according event criteria
	 */
	private static void extractMonsters()
	{
		final GCSArray<L2MonsterInstance> allMonsters = new GCSArray<L2MonsterInstance>();
		final GArray<L2NpcInstance> npcs = L2ObjectsStorage.getAllNpcs();

		for(final L2Character npc : npcs)
		{
			if(npc.isMonster())
			{
				final L2MonsterInstance monster = (L2MonsterInstance) npc;

				// business logic
				final int level = monster.getLevel();
				final boolean boss = monster.isRaid();
				final int hp = (int) monster.getCurrentHp() / monster.getMaxHp() * 100;
				if(level >= minMobLevel && level <= maxMobLevel && boss == checkRaid && hp >= hpPercent)
					allMonsters.add(monster);
			}
		}

		if(allMonsters.size() <= selectionAmount)
		{
			infectedMonsters.addAll(allMonsters);
			return;
		}

		while(infectedMonsters.size() < selectionAmount)
			infectedMonsters.add(allMonsters.remove(Rnd.get(allMonsters.size())));
	}

	/**
	 * Class performs event execution
	 *
	 * @author sandro [city55ru@gmail.com]
	 */
	static class ExecuteService implements Runnable
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */

		@Override
		public void run()
		{
			startEvent();
		}

	}

	/**
	 * Class performs event stopping
	 *
	 * @author sandro [city55ru@gmail.com]
	 */
	static class StopService implements Runnable
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */

		@Override
		public void run()
		{
			Scheduler.getInstance().unregister(eventName);

			if(inProgress())
			{
				Announcements.getInstance().announceToAll(
						"Дух Шилен покинул тела монстров.");
				_logger.info("Event " + eventName + " finished");
				infectedMonsters.clear();
				progressController.cancel(true);
				progressController = null;
			}

			if(isActive())
			{
				startScheduleListening();
			}
		}

	}

	/**
	 * Class for storing drop item data
	 *
	 * @author sandro [city55ru@gmail.com]
	 */
	static class EventItem
	{
		/**
		 * Item id
		 */
		private Integer id;

		/**
		 * Amount of item
		 */
		private Integer amount;

		/**
		 * Drop probability
		 */
		private Integer probability;

		/**
		 * Initialization and registration item
		 */
		public EventItem()
		{
			dropItems.add(this);
		}

		/**
		 * Sets new id
		 */
		public void setId(final Integer id)
		{
			this.id = id;
		}

		/**
		 * Sets new amount
		 */
		public void setAmount(final Integer amount)
		{
			this.amount = amount;
		}

		/**
		 * Sets new probability
		 */
		public void setProbability(final Integer probability)
		{
			this.probability = probability;
		}

		/**
		 * @return id Id of item
		 */
		public Integer getId()
		{
			return id;
		}

		/**
		 * @return amount Amount of items
		 */
		public Integer getAmount()
		{
			return amount;
		}

		/**
		 * @return probability Drop probability
		 */
		public Integer getProbability()
		{
			return probability;
		}

	}
}