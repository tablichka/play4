package instances;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;

import java.util.Map;
import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 20.10.2009 10:35:41
 */
public class DarkCloudMansion extends Instance
{
	private static final boolean DEBUG = false;

	private int instanceStage;
	// Spawn points
	// Start room
	private static Location STARTROOM_LOC1 = new Location(146879, 180568, -6100, 32100);
	private static Location STARTROOM_LOC2 = new Location(146909, 180309, -6100, 32100);

	// Hall
	private static Location HALL_GROUP1_LOC1 = new Location(147230, 180138, -6100, 15713);
	private static Location HALL_GROUP1_LOC2 = new Location(147372, 180174, -6100, 24576);

	private static Location HALL_GROUP2_LOC1 = new Location(148484, 180092, -6100, 28700);
	private static Location HALL_GROUP2_LOC2 = new Location(148432, 180230, -6100, 36200);

	private static Location HALL_GROUP3_LOC1 = new Location(148529, 180893, -6100, 48100);
	private static Location HALL_GROUP3_LOC2 = new Location(148416, 180792, -6100, 60400);

	private static Location HALL_GROUP4_LOC1 = new Location(147281, 180926, -6100, 64900);
	private static Location HALL_GROUP4_LOC2 = new Location(147293, 180814, -6100, 2300);

	// Room #1
	private static Location ROOM1_GROUP1_LOC1 = new Location(147996, 179529, -6100, 17900);
	private static Location ROOM1_GROUP1_LOC2 = new Location(147711, 179431, -6100, 7300);
	private static Location ROOM1_GROUP1_LOC3 = new Location(147683, 179764, -6100, 7700);
	private static Location ROOM1_GROUP1_LOC4 = new Location(147955, 179786, -6100, 26500);
	private static Location ROOM1_SYMBOL_LOC = new Location(147843, 179584, -6100, 26500);

	// Room #2
	private static Location ROOM2_RIP_LOC1 = new Location(147727, 181345, -6100, 0);
	private static Location ROOM2_RIP_LOC2 = new Location(147727, 181222, -6100, 0);
	private static Location ROOM2_RIP_LOC3 = new Location(147838, 181155, -6100, 0);
	private static Location ROOM2_RIP_LOC4 = new Location(147838, 181408, -6100, 0);
	private static Location ROOM2_RIP_LOC5 = new Location(147950, 181345, -6100, 0);
	private static Location ROOM2_RIP_LOC6 = new Location(147950, 181222, -6100, 0);
	private static Location ROOM2_SYMBOL_LOC = new Location(147822, 181300, -6100, 0);

	// Room #3
	private static Location ROOM3_GROUP1_LOC1 = new Location(148920, 180739, -6100);
	private static Location ROOM3_GROUP1_LOC2 = new Location(148806, 180644, -6100);
	private static Location ROOM3_GROUP1_LOC3 = new Location(148767, 180437, -6100);
	private static Location ROOM3_GROUP1_LOC4 = new Location(148932, 180235, -6100);
	private static Location ROOM3_GROUP1_LOC5 = new Location(149038, 180332, -6100);
	private static Location ROOM3_GROUP1_LOC6 = new Location(149077, 180620, -6100);

	// Checkmate Room
	private static Location ROOM4_ROW1_LOC1 = new Location(148660, 179270, -6100, 16000);
	private static Location ROOM4_ROW1_LOC2 = new Location(148780, 179270, -6100, 16000);
	private static Location ROOM4_ROW1_LOC3 = new Location(148910, 179270, -6100, 16000);
	private static Location ROOM4_ROW1_LOC4 = new Location(149040, 179270, -6100, 16000);
	private static Location ROOM4_ROW1_LOC5 = new Location(149160, 179270, -6100, 16000);

	private static Location ROOM4_ROW2_LOC1 = new Location(148660, 179150, -6100, 16000);
	private static Location ROOM4_ROW2_LOC2 = new Location(148780, 179150, -6100, 16000);
	private static Location ROOM4_ROW2_LOC3 = new Location(148910, 179150, -6100, 16000);
	private static Location ROOM4_ROW2_LOC4 = new Location(149040, 179150, -6100, 16000);
	private static Location ROOM4_ROW2_LOC5 = new Location(149160, 179150, -6100, 16000);

	private static Location ROOM4_ROW3_LOC1 = new Location(148660, 179025, -6100, 16000);
	private static Location ROOM4_ROW3_LOC2 = new Location(148780, 179025, -6100, 16000);
	private static Location ROOM4_ROW3_LOC3 = new Location(148910, 179025, -6100, 16000);
	private static Location ROOM4_ROW3_LOC4 = new Location(149040, 179025, -6100, 16000);
	private static Location ROOM4_ROW3_LOC5 = new Location(149160, 179025, -6100, 16000);

	private static Location ROOM4_ROW4_LOC1 = new Location(148660, 178900, -6100, 16000);
	private static Location ROOM4_ROW4_LOC2 = new Location(148780, 178900, -6100, 16000);
	private static Location ROOM4_ROW4_LOC3 = new Location(148910, 178900, -6100, 16000);
	private static Location ROOM4_ROW4_LOC4 = new Location(149040, 178900, -6100, 16000);
	private static Location ROOM4_ROW4_LOC5 = new Location(149160, 178900, -6100, 16000);

	private static Location ROOM4_ROW5_LOC1 = new Location(148660, 178775, -6100, 16000);
	private static Location ROOM4_ROW5_LOC2 = new Location(148780, 178775, -6100, 16000);
	private static Location ROOM4_ROW5_LOC3 = new Location(148910, 178775, -6100, 16000);
	private static Location ROOM4_ROW5_LOC4 = new Location(149040, 178775, -6100, 16000);
	private static Location ROOM4_ROW5_LOC5 = new Location(149160, 178775, -6100, 16000);

	private static Location ROOM4_ROW6_LOC1 = new Location(148660, 178650, -6100, 16000);
	private static Location ROOM4_ROW6_LOC2 = new Location(148780, 178650, -6100, 16000);
	private static Location ROOM4_ROW6_LOC3 = new Location(148910, 178650, -6100, 16000);
	private static Location ROOM4_ROW6_LOC4 = new Location(149040, 178650, -6100, 16000);
	private static Location ROOM4_ROW6_LOC5 = new Location(149160, 178650, -6100, 16000);

	private static Location ROOM4_ROW7_LOC1 = new Location(148660, 178525, -6100, 16000);
	private static Location ROOM4_ROW7_LOC2 = new Location(148780, 178525, -6100, 16000);
	private static Location ROOM4_ROW7_LOC3 = new Location(148910, 178525, -6100, 16000);
	private static Location ROOM4_ROW7_LOC4 = new Location(149040, 178525, -6100, 16000);
	private static Location ROOM4_ROW7_LOC5 = new Location(149160, 178525, -6100, 16000);

	private static Location ROOM4_SYMBOL_LOC = new Location(148910, 178400, -6100, 0);

	private static Location ROOM5_LOC1 = new Location(149090, 182172, -6100, 51200);
	private static Location ROOM5_LOC2 = new Location(149025, 182172, -6100, 51200);
	private static Location ROOM5_LOC3 = new Location(148960, 182172, -6100, 51200);
	private static Location ROOM5_LOC4 = new Location(148900, 182172, -6100, 51200);
	private static Location ROOM5_LOC5 = new Location(148830, 182172, -6100, 51200);
	private static Location ROOM5_LOC6 = new Location(148770, 182172, -6100, 51200);
	private static Location ROOM5_LOC7 = new Location(148700, 182172, -6100, 51200);

	private static Location ROOM5_SYMBOL_LOC = new Location(148900, 182040, -6100, 0);

	// mobs
	private final static int BELETH_MINIONS[] = {22272, 22273, 22274};
	private final static int PARME_HEALER = 22400;
	private final static int CHROMATIC_CRYSTALLINE_GOLEM[] = {18369, 18370};
	private final static int SHADOW_COLUMN = 22402;

	private final static int BELETH_SAMPLES[] = {18371, 18372, 18373, 18374, 18375, 18376, 18377};

	// npcs
	private final static int SYMBOL_OF_FAITH = 32288;
	private final static int SYMBOL_OF_ADVERSITY = 32289;
	private final static int BLACK_STONE_MONOLITH = 32324;
	private final static int SYMBOL_OF_ADVENTURE = 32290;
	private final static int SYMBOL_OF_TRUTH = 32291;

	// spawns
	private Map<Integer, L2GroupSpawn> _groups;
	private int monolithsGuessed = 0;
	private int mobsGuessed = 0;
	private int golemsSpawned = 0;
	private int monolithChance = 10;
	private long _lastClicked = 0;

	private String guessedShouts[] = {
			"Oh... very sensible?",
			"You've done well!",
			"Huh?! How did you know it was me?",
	};

	private String notguessedShouts[] = {
			"You've been fooled!",
			"Sorry, but...I'm the fake one.",
	};

	private ScheduledFuture<?> _scheduledTask, _scheduledTask2;
	private boolean attacked = false;

	public DarkCloudMansion(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();
		instanceStage = 0;
		_groups = new FastMap<Integer, L2GroupSpawn>().shared();

		L2GroupSpawn groupSpawn = new L2GroupSpawn();
		groupSpawn.setReflection(getReflection());
		groupSpawn.setInstance(this);
		groupSpawn.stopRespawn();
		groupSpawn.addSpawn(BELETH_MINIONS[0], STARTROOM_LOC1);
		groupSpawn.addSpawn(BELETH_MINIONS[1], STARTROOM_LOC2);
		groupSpawn.doSpawn();

		_groups.put(BELETH_MINIONS[0], groupSpawn);
		_groups.put(BELETH_MINIONS[1], groupSpawn);

	}

	@Override
	public void stopInstance()
	{
		try
		{
			if(_scheduledTask != null)
				_scheduledTask.cancel(true);
			_scheduledTask = null;

			if(_scheduledTask2 != null)
				_scheduledTask2.cancel(true);
			_scheduledTask2 = null;

		}
		catch(Exception e)
		{
			_log.info("DarkCloudMansion[" + getReflection() + "]: Can't stop all scheduled tasks:");
			e.printStackTrace();
		}
		finally
		{
			super.stopInstance();
		}

	}

	@Override
	public void notifyKill(L2Character mob, L2Player killer)
	{

		if(mob.getNpcId() == CHROMATIC_CRYSTALLINE_GOLEM[0] || mob.getNpcId() == CHROMATIC_CRYSTALLINE_GOLEM[1])
		{
			golemsSpawned--;
			return;
		}

		final L2GroupSpawn groupSpawn = _groups.get(mob.getNpcId());
		switch(instanceStage)
		{
			case 9:
				if(groupSpawn != null && groupSpawn.isAllDead())
				{
					if(_scheduledTask != null)
						_scheduledTask.cancel(true);
					_scheduledTask = ThreadPoolManager.getInstance().scheduleGeneral(new respawnMobs(groupSpawn), 7000);
					mobsGuessed = 0;
				}
				break;
		}
	}

	@Override
	public void notifyEvent(String event, L2Character cha, L2Player player)
	{
		if(event.equalsIgnoreCase("monolith") && instanceStage == 4 && System.currentTimeMillis() > _lastClicked + 7500)
		{
			_lastClicked = System.currentTimeMillis();
			int doorId = 24230005;
			Reflection ref = ReflectionTable.getInstance().getById(getReflection());
			for(L2Object object : ref.getAllObjects())
				if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
					((L2DoorInstance) object).closeMe();

			L2Skill skill = SkillTable.getInstance().getInfo(5441, 1);
			if(skill != null && Rnd.chance(monolithChance) && golemsSpawned < 2)
			{
				cha.doCast(skill, skill.getAimingTarget(cha), true);
				if(monolithsGuessed < 3)
					monolithsGuessed++;

				if(monolithsGuessed == 3 && instanceStage == 4)
				{
					instanceStage = 5;
					try
					{
						L2NpcTemplate symbolTemplate = NpcTable.getTemplate(SYMBOL_OF_ADVERSITY);
						L2Spawn symbolSpawn = new L2Spawn(symbolTemplate);
						symbolSpawn.setAmount(1);
						symbolSpawn.setLoc(ROOM2_SYMBOL_LOC);
						symbolSpawn.setInstance(this);
						symbolSpawn.setReflection(ref.getId());
						symbolSpawn.stopRespawn();
						symbolSpawn.doSpawn(true);
					}
					catch(Exception e)
					{
						System.out.println("DarkCloudMansion[" + getReflection() + "]: Can't spawn symbol of adversity " + e);
						e.printStackTrace();
					}

					L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
					groupSpawn2.setReflection(getReflection());
					groupSpawn2.stopRespawn();
					groupSpawn2.setInstance(this);
					groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP1_LOC1);
					groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP1_LOC2);
					groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP2_LOC1);
					groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP2_LOC2);
					groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP3_LOC1);
					groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP3_LOC2);
					groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP4_LOC1);
					groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP4_LOC2);
					groupSpawn2.doSpawn();

					_groups.put(BELETH_MINIONS[0], groupSpawn2);
					_groups.put(BELETH_MINIONS[1], groupSpawn2);

				}
			}
			else
			{
				try
				{
					if(golemsSpawned < 2)
					{
						L2NpcTemplate template = NpcTable.getTemplate(CHROMATIC_CRYSTALLINE_GOLEM[Rnd.get(CHROMATIC_CRYSTALLINE_GOLEM.length)]);
						Location loc = cha.getLoc();
						loc.setX(loc.getX() + (50 - Rnd.get(100)));
						loc.setY(loc.getY() + (50 - Rnd.get(100)));
						L2Spawn spawn = new L2Spawn(template);
						spawn.setAmount(1);
						spawn.setInstance(this);
						spawn.setLoc(loc);
						spawn.setReflection(getReflection());
						spawn.stopRespawn();
						spawn.doSpawn(true);
						golemsSpawned++;
						monolithsGuessed = 0;
						if(monolithChance < 100)
							monolithChance += 5;
					}
				}
				catch(Exception e)
				{
					_log.info("DarkCloudMansion[" + getReflection() + "]: Can't spawn Chromatic Crystalline Golem " + e);
					e.printStackTrace();
				}
			}


		}
		else if(event.equalsIgnoreCase("faith") && instanceStage == 3)
		{
			int doorId = 24230002;
			Reflection ref = ReflectionTable.getInstance().getById(getReflection());
			for(L2Object object : ref.getAllObjects())
				if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
				{
					((L2DoorInstance) object).openMe();
					break;
				}

		}
		else if(event.equalsIgnoreCase("adversity") && instanceStage == 5)
		{
			int doorId = 24230005;
			Reflection ref = ReflectionTable.getInstance().getById(getReflection());
			for(L2Object object : ref.getAllObjects())
				if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
				{
					((L2DoorInstance) object).openMe();
					break;
				}
		}
		else if(event.equalsIgnoreCase("adventury") && instanceStage == 7)
		{
			int doorId = 24230004;
			Reflection ref = ReflectionTable.getInstance().getById(getReflection());
			for(L2Object object : ref.getAllObjects())
				if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
				{
					((L2DoorInstance) object).openMe();

					instanceStage = 8;

					L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
					groupSpawn2.setReflection(getReflection());
					groupSpawn2.stopRespawn();
					groupSpawn2.setInstance(this);
					groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC1);
					groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC2);
					groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC3);
					groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC4);
					groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC5);
					groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC6);
					groupSpawn2.doSpawn();

					_groups.put(BELETH_MINIONS[0], groupSpawn2);
					_groups.put(BELETH_MINIONS[1], groupSpawn2);
					_groups.put(BELETH_MINIONS[2], groupSpawn2);

					break;
				}
		}
		else if(event.equalsIgnoreCase("guessed_mob") && instanceStage == 9)
		{
			if(mobsGuessed < 3)
				mobsGuessed++;

			if(mobsGuessed == 3)
			{
				L2GroupSpawn groupSpawn = _groups.get(cha.getNpcId());
				if(groupSpawn != null)
				{
					groupSpawn.despawnAll();
					_groups.clear();
					instanceStage = 10;

					try
					{
						L2NpcTemplate symbolTemplate = NpcTable.getTemplate(SYMBOL_OF_TRUTH);
						L2Spawn symbolSpawn = new L2Spawn(symbolTemplate);
						symbolSpawn.setAmount(1);
						symbolSpawn.setLoc(ROOM5_SYMBOL_LOC);
						symbolSpawn.setInstance(this);
						symbolSpawn.setReflection(getReflection());
						symbolSpawn.stopRespawn();
						symbolSpawn.doSpawn(true);
					}
					catch(Exception e)
					{
						System.out.println("DarkCloudMansion[" + getReflection() + "]: Can't spawn symbol of Truth " + e);
						e.printStackTrace();
					}

				}
			}

		}


	}

	@Override
	public void notifyDecayd(L2NpcInstance npc)
	{
		final L2GroupSpawn groupSpawn = _groups.get(npc.getNpcId());
		switch(instanceStage)
		{
			case 0:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						int doorId = 24230001;
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								L2DoorInstance door = (L2DoorInstance) object;
								door.openMe();
								instanceStage = 1;
								_groups.clear();
								L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
								groupSpawn2.setReflection(getReflection());
								groupSpawn2.setInstance(this);
								groupSpawn2.stopRespawn();
								groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP1_LOC1);
								groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP1_LOC2);
								groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP2_LOC1);
								groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP2_LOC2);
								groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP3_LOC1);
								groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP3_LOC2);
								groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP4_LOC1);
								groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP4_LOC2);
								groupSpawn2.doSpawn();

								_groups.put(BELETH_MINIONS[0], groupSpawn2);
								_groups.put(BELETH_MINIONS[1], groupSpawn2);

								break;
							}

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group decayed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;

			case 1:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						int doorId = 24230002;
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								L2DoorInstance door = (L2DoorInstance) object;
								door.openMe();
								instanceStage = 2;
								_groups.clear();
								L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
								groupSpawn2.setReflection(getReflection());
								groupSpawn2.setInstance(this);
								groupSpawn2.stopRespawn();
								groupSpawn2.addSpawn(PARME_HEALER, ROOM1_GROUP1_LOC1);
								groupSpawn2.addSpawn(PARME_HEALER, ROOM1_GROUP1_LOC2);
								groupSpawn2.addSpawn(PARME_HEALER, ROOM1_GROUP1_LOC3);
								groupSpawn2.addSpawn(PARME_HEALER, ROOM1_GROUP1_LOC4);
								groupSpawn2.doSpawn();
								_groups.put(PARME_HEALER, groupSpawn2);
								break;
							}

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group decayed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;

			case 2:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						instanceStage = 3;
						_groups.clear();

						try
						{
							L2NpcTemplate symbolTemplate = NpcTable.getTemplate(SYMBOL_OF_FAITH);
							L2Spawn symbolSpawn = new L2Spawn(symbolTemplate);
							symbolSpawn.setAmount(1);
							symbolSpawn.setLoc(ROOM1_SYMBOL_LOC);
							symbolSpawn.setInstance(this);
							symbolSpawn.setReflection(ref.getId());
							symbolSpawn.stopRespawn();
							symbolSpawn.doSpawn(true);
						}
						catch(Exception e)
						{
							System.out.println("DarkCloudMansion[" + getReflection() + "]: Can't spawn symbol of faith " + e);
							e.printStackTrace();
						}

						L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
						groupSpawn2.setReflection(getReflection());
						groupSpawn2.stopRespawn();
						groupSpawn2.setInstance(this);
						groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP1_LOC1);
						groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP1_LOC2);
						groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP2_LOC1);
						groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP2_LOC2);
						groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP3_LOC1);
						groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP3_LOC2);
						groupSpawn2.addSpawn(BELETH_MINIONS[0], HALL_GROUP4_LOC1);
						groupSpawn2.addSpawn(BELETH_MINIONS[1], HALL_GROUP4_LOC2);
						groupSpawn2.doSpawn();

						_groups.put(BELETH_MINIONS[0], groupSpawn2);
						_groups.put(BELETH_MINIONS[1], groupSpawn2);

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group decayed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;

			case 3:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						int doorId = 24230005;
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								L2DoorInstance door = (L2DoorInstance) object;
								door.openMe();
								instanceStage = 4;
								_groups.clear();
								L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
								groupSpawn2.setReflection(getReflection());
								groupSpawn2.setInstance(this);
								groupSpawn2.stopRespawn();
								groupSpawn2.addSpawn(BLACK_STONE_MONOLITH, ROOM2_RIP_LOC1);
								groupSpawn2.addSpawn(BLACK_STONE_MONOLITH, ROOM2_RIP_LOC2);
								groupSpawn2.addSpawn(BLACK_STONE_MONOLITH, ROOM2_RIP_LOC3);
								groupSpawn2.addSpawn(BLACK_STONE_MONOLITH, ROOM2_RIP_LOC4);
								groupSpawn2.addSpawn(BLACK_STONE_MONOLITH, ROOM2_RIP_LOC5);
								groupSpawn2.addSpawn(BLACK_STONE_MONOLITH, ROOM2_RIP_LOC6);
								groupSpawn2.doSpawn();
								break;
							}

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group decayed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;

			case 5:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						int doorId = 24230003;
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								L2DoorInstance door = (L2DoorInstance) object;
								door.openMe();
								instanceStage = 6;
								_groups.clear();

								L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
								groupSpawn2.setReflection(getReflection());
								groupSpawn2.stopRespawn();
								groupSpawn2.setInstance(this);
								groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC1);
								groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC2);
								groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC3);
								groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC4);
								groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC5);
								groupSpawn2.addSpawn(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)], ROOM3_GROUP1_LOC6);
								groupSpawn2.doSpawn();

								_groups.put(BELETH_MINIONS[0], groupSpawn2);
								_groups.put(BELETH_MINIONS[1], groupSpawn2);
								_groups.put(BELETH_MINIONS[2], groupSpawn2);

								break;
							}

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group decayed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;
			case 6:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						int doorId = 24230004;
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								L2DoorInstance door = (L2DoorInstance) object;
								door.openMe();
								instanceStage = 7;
								_groups.clear();
								L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
								groupSpawn2.setReflection(getReflection());
								groupSpawn2.stopRespawn();
								groupSpawn2.setInstance(this);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW1_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW1_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW1_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW1_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW1_LOC5);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW2_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW2_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW2_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW2_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW2_LOC5);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW3_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW3_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW3_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW3_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW3_LOC5);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW4_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW4_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW4_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW4_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW4_LOC5);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW5_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW5_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW5_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW5_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW5_LOC5);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW6_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW6_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW6_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW6_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW6_LOC5);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW7_LOC1);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW7_LOC2);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW7_LOC3);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW7_LOC4);
								groupSpawn2.addSpawn(SHADOW_COLUMN, ROOM4_ROW7_LOC5);
								groupSpawn2.addSpawn(SYMBOL_OF_ADVENTURE, ROOM4_SYMBOL_LOC);
								groupSpawn2.doSpawn();
								_groups.put(SHADOW_COLUMN, groupSpawn2);
								break;
							}

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group decayed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;
			case 7:
				if(groupSpawn != null)
				{
					int columnRow = getRowByColumn(npc);
					if(Rnd.chance(90))
					{
						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Opening invisible door at row " + columnRow);
						int doorId = getDoorIdByRow(columnRow);
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());
						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								((L2DoorInstance) object).openMe();
								break;
							}
					}
					else if(groupSpawn.isAllDecayed())
					{
						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: All mobs killed. Opening invisible door at row " + columnRow);
						int doorId = getDoorIdByRow(columnRow);
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());
						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								((L2DoorInstance) object).openMe();
								break;
							}
					}
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);

				break;

			case 8:
				if(groupSpawn != null)
				{
					if(groupSpawn.isAllDecayed())
					{
						int doorId = 24230006;
						Reflection ref = ReflectionTable.getInstance().getById(getReflection());

						for(L2Object object : ref.getAllObjects())
							if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
							{
								L2DoorInstance door = (L2DoorInstance) object;
								door.openMe();
								instanceStage = 9;
								_groups.clear();
								L2GroupSpawn groupSpawn2 = new L2GroupSpawn();
								groupSpawn2.setReflection(getReflection());
								groupSpawn2.stopRespawn();
								groupSpawn2.setInstance(this);
								groupSpawn2.addSpawn(BELETH_SAMPLES[0], ROOM5_LOC1);
								groupSpawn2.addSpawn(BELETH_SAMPLES[1], ROOM5_LOC2);
								groupSpawn2.addSpawn(BELETH_SAMPLES[2], ROOM5_LOC3);
								groupSpawn2.addSpawn(BELETH_SAMPLES[3], ROOM5_LOC4);
								groupSpawn2.addSpawn(BELETH_SAMPLES[4], ROOM5_LOC5);
								groupSpawn2.addSpawn(BELETH_SAMPLES[5], ROOM5_LOC6);
								groupSpawn2.addSpawn(BELETH_SAMPLES[6], ROOM5_LOC7);

								groupSpawn2.doSpawn();
								for(int i : BELETH_SAMPLES)
									_groups.put(i, groupSpawn2);
								break;
							}

						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group killed.");
					}
					else if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Instance group mobs left alive.");
				}
				else if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Instance WTF!! No spawn group for mob: " + npc);
				break;
			case 9:
				if(groupSpawn != null)
				{
					groupSpawn.despawnAll();
					if(_scheduledTask != null)
						_scheduledTask.cancel(true);
					_scheduledTask = ThreadPoolManager.getInstance().scheduleGeneral(new respawnMobs(groupSpawn), 2000);
					mobsGuessed = 0;

					if(DEBUG)
						_log.info("DarkCloudMansion[" + getReflection() + "]: Stage 9 (Room #5, Truth or Dare) In Progress. Respawned all mobs.");
				}
				break;

		}
	}

	@Override
	public void notifyAttacked(L2Character npc, L2Player player)
	{
		switch(instanceStage)
		{
			case 2:
				if(npc.getNpcId() == PARME_HEALER)
				{
					int doorId = 24230002;
					Reflection ref = ReflectionTable.getInstance().getById(getReflection());
					for(L2Object object : ref.getAllObjects())
						if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
						{
							((L2DoorInstance) object).closeMe();
							break;
						}
				}
				break;
			case 7:
				if(npc.getNpcId() == SHADOW_COLUMN)
				{
					int doorId = 24230004;
					Reflection ref = ReflectionTable.getInstance().getById(getReflection());
					for(L2Object object : ref.getAllObjects())
						if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
						{
							((L2DoorInstance) object).closeMe();
							break;
						}

					if(Rnd.chance(5))
					{
						L2Skill skill = SkillTable.getInstance().getInfo(4614, 10);
						if(skill != null)
						{
							npc.doCast(skill, skill.getAimingTarget(npc), true);
						}
					}
					else if(Rnd.chance(5))
					{
						try
						{
							L2NpcTemplate template = NpcTable.getTemplate(BELETH_MINIONS[Rnd.get(BELETH_MINIONS.length)]);
							Location loc = npc.getLoc();
							loc.setX(loc.getX() + (50 - Rnd.get(100)));
							loc.setY(loc.getY() + (50 - Rnd.get(100)));
							L2Spawn spawn = new L2Spawn(template);
							spawn.setAmount(1);
							spawn.setInstance(this);
							spawn.setLoc(loc);
							spawn.setReflection(getReflection());
							spawn.stopRespawn();
							spawn.doSpawn(true);
						}
						catch(Exception e)
						{

						}


					}

				}
				break;
			case 9:

				if(!attacked)
				{
					attacked = true;

					if(Rnd.chance(40))
					{
						notifyEvent("guessed_mob", npc, player);
						if(npc instanceof L2NpcInstance)
						{
							Functions.npcSay((L2NpcInstance) npc, Say2C.ALL, guessedShouts[Rnd.get(guessedShouts.length)]);
							final L2NpcInstance _npc = (L2NpcInstance) npc;
							if(_scheduledTask2 != null)
								_scheduledTask2.cancel(true);
							_scheduledTask2 = ThreadPoolManager.getInstance().scheduleGeneral(new deleteMob(_npc), 500);
						}
						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Guessed mob. Guessed mobs is now " + mobsGuessed);

					}
					else
					{
						mobsGuessed = 0;
						if(DEBUG)
							_log.info("DarkCloudMansion[" + getReflection() + "]: Unluck. Guessed mobs is now " + mobsGuessed + ". Despawn and respawn all mobs on kill.");
						Functions.npcSay((L2NpcInstance) npc, Say2C.ALL, notguessedShouts[Rnd.get(notguessedShouts.length)]);
						npc.getAI().notifyEvent(CtrlEvent.EVT_ATTACKED, player, 100);
					}
				}
				break;
		}
	}

	private int getDoorIdByRow(int row)
	{
		int doorId = 0;
		switch(row)
		{
			case 1:
				doorId = 24230007;
				break;
			case 2:
				doorId = 24230008;
				break;
			case 3:
				doorId = 24230009;
				break;
			case 4:
				doorId = 24230010;
				break;
			case 5:
				doorId = 24230011;
				break;
			case 6:
				doorId = 24230012;
				break;
			case 7:
				doorId = 24230013;
				break;
		}

		return doorId;
	}

	private int getRowByColumn(L2Character mob)
	{
		int row = 0;

		if(mob.getY() > 179265 && mob.getY() < 179275)
			row = 1;
		else if(mob.getY() > 179145 && mob.getY() < 179155)
			row = 2;
		else if(mob.getY() > 179020 && mob.getY() < 179030)
			row = 3;
		else if(mob.getY() > 178895 && mob.getY() < 178905)
			row = 4;
		else if(mob.getY() > 178770 && mob.getY() < 178780)
			row = 5;
		else if(mob.getY() > 178645 && mob.getY() < 178655)
			row = 6;
		else if(mob.getY() > 178520 && mob.getY() < 178530)
			row = 7;

		return row;
	}

	public class respawnMobs implements Runnable
	{
		L2GroupSpawn _groupSpawn;

		public respawnMobs(L2GroupSpawn groupSpawn)
		{
			_groupSpawn = groupSpawn;
		}

		public void run()
		{
			try
			{
				_groupSpawn.doSpawn();
				attacked = false;
				if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Stage " + instanceStage + " respawnAllMobs.");
			}
			catch(Throwable e)
			{
				System.out.println("DarkCloudMansion respawnMobs Exception Stage " + instanceStage + ": " + e);
				e.printStackTrace();
			}
		}
	}

	public class deleteMob implements Runnable
	{
		L2NpcInstance _npc;

		public deleteMob(L2NpcInstance mob)
		{
			_npc = mob;
		}

		public void run()
		{
			try
			{
				_npc.deleteMe();
				attacked = false;

				if(DEBUG)
					_log.info("DarkCloudMansion[" + getReflection() + "]: Stage " + instanceStage + " deleteMe: " + _npc);
			}
			catch(Throwable e)
			{
				System.out.println("DarkCloudMansion deleteMob Exception Stage " + instanceStage + ": " + e);
				e.printStackTrace();
			}
		}
	}

}
