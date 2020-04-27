package instances;

import javolution.util.FastMap;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.serverpackets.SpecialCamera;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.ReflectionTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.util.Location;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 03.11.2009 10:06:29
 */
public class CrystalCavernsInstance extends Instance
{
	private ScheduledFuture<?> _scDespawnOG = null;
	private static final int DOOR_CG1 = 24220024;
	private static final int DOOR_CG2 = 24220025;
	private static final int DOOR_CG3 = 24220026;
	private static final int DOOR_SC_ES = 24220021;
	private static final int DOOR_SC1 = 24220061;
	private static final int DOOR_SC2 = 24220023;
	public static final int GUARDIAN = 22311;
	public static final int FAFURION = 22312;
	public static final int STAKATO = 22313;
	public static final int POISON_MOTH = 22314;
	public static final int GUARD = 22315;
	public static final int GUARDIAN_TREE = 22316;
	public static final int CASTALIA = 22317;
	public static final int GOLEM = 32328;
	public static final int TEARS = 25534;
	public static final int DARNEL = 25531;
	public static final int KECHI = 25532;
	public static final int DOLPH = 22299;
	public static final int TEROD = 22301;
	public static final int BAYLOR = 29099;
	public static Location BAYLOR_CENTER = new Location(153572, 142079, -12763, 59987);
	public static final int[] DETAINEES = {22279, 22282, 22284, 22285};
	public static final int[] EMERALDMOBS1 = {22289, 22288, 22287, 22286};
	public static final int[] EMERALDMOBS2 = {22292, 22293, 22294, 22280};
	public static final int[] EMERALDMOBS3 = {22294, 22281, 22283, 22280};
	public static final int[] EMERALDROOM1 = {22289, 22288, 22292};
	public static final int GK_LOHAN = 22275;
	public static final int GK_PROVO = 22277;
	public static final int ES_ROOM_RB1 = 22303;
	public static final int ES_ROOM_RB2 = 22302;
	public static final int ES_ROOM_RB3 = 22304;
	public static final int SC_CAPTAIN1 = 22305;
	public static final int SC_CAPTAIN2 = 22306;
	public static final int SC_CAPTAIN3 = 22307;
	public static final int SC_GUARD1 = 22308;
	public static final int SC_GUARD2 = 22309;
	public static final int SC_GUARD3 = 22310;
	public static final int SC_CAPTAIN4 = 22416;
	public static final int SC_CAPTAIN5 = 22417;
	public static final int SC_IRIS1 = 22418;
	public static final int SC_IRIS2 = 22419;
	public static final int SC_IRIS3 = 22420;
	public static final int SC_TRAP1 = 18379;
	public static final int SC_TRAP2 = 18380;
	public static final int SC_TRAP3 = 18381;
	public static final int SC_TRAP4 = 18382;
	public static final int SC_EVA1 = 32284;
	public static final int SC_EVA2 = 32285;
	public static final int SC_EVA3 = 32286;
	public static final int SC_EVA4 = 32287;
	private static final int SC_OG1 = 32276;
	private static final int SC_OG2 = 32277;
	private static final int SC_OG3 = 32278;
	private static final int BLUE_KEY = 9698;
	private static final int RED_KEY = 9699;
	public static final int TIMER_ID = 5239;
	private L2Spawn _startOracle, _tearsOracle, _tearsSpawn, _gkLohan, _gkProvo;
	private L2Spawn _darnelSpawn, _darnelOracle;
	private L2Spawn _kechiSpawn, _kechiOracle;
	private L2Spawn _baylorSpawn;
	private L2GroupSpawn _coralGardenGroup1, _coralGardenGroup2, _scesGroup, _emeraldSquareGroup1, _emeraldSquareGroup2;
	private L2GroupSpawn _emeraldRoom1, _emeraldRoom2, _emeraldRoom3, _emeraldRoom4, _emeraldRoomsRB;
	private L2GroupSpawn _corridorRoom1, _corridorRoom2, _corridorRoom3, _corridorRoom4, _corridorEvas;
	private int guardsOnPlace = 0;
	private L2DoorInstance _doorCG1, _doorCG2, _doorCG3, _doorSCES;
	private L2DoorInstance _doorES1, _doorES2;
	private L2DoorInstance _doorSC1, _doorSC2;
	private boolean _dolpSpawned = false;
	private boolean _dolpKilled = false;
	private boolean _terodSpawned = false;
	private boolean _terodKilled = false;
	private boolean _scOGSpawned = false;
	private DoorZoneListener _doorZoneListener = null;
	private SteamCorridorListener _steamCorridorListener = null;
	private FastMap<Integer, L2GroupSpawn> _emeraldDoorSpawn = null;
	private boolean _steamCorridorActivated = false;
	private int _steamCorridorRoom;
	private ScheduledFuture<?> _baylorMovieTask;

	public CrystalCavernsInstance(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();
		try
		{
			_startOracle = new L2Spawn(NpcTable.getTemplate(32273));
			_startOracle.setAmount(1);
			_startOracle.setLoc(new Location(143144, 148885, -12003));
			_startOracle.setInstance(this);
			_startOracle.setReflection(getReflection());
			_startOracle.stopRespawn();
			_startOracle.doSpawn(true);
		}
		catch(Exception e)
		{
			System.out.println(this + " Can't spawn start Oracle " + e);
			e.printStackTrace();
		}
		_doorCG1 = getDoorById(DOOR_CG1);
		_doorCG2 = getDoorById(DOOR_CG2);
		_doorCG3 = getDoorById(DOOR_CG3);
		_doorSCES = getDoorById(DOOR_SC_ES);
		_doorSC1 = getDoorById(DOOR_SC1);
		_doorSC2 = getDoorById(DOOR_SC2);
		if(_doorCG1 == null || _doorCG2 == null || _doorCG3 == null)
			System.out.println(this + " Can't find Coral Garden door");
		if(_doorSC1 == null || _doorSC2 == null)
			System.out.println(this + " Can't find Steam Corridor door");

	}

	@Override
	public void stopInstance()
	{
		super.stopInstance();
		if(_doorZoneListener != null)
		{
			L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3201);

			if(zone != null)
				zone.getListenerEngine().removeMethodInvokedListener(_doorZoneListener);

			zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3202);

			if(zone != null)
				zone.getListenerEngine().removeMethodInvokedListener(_doorZoneListener);

			zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3203);

			if(zone != null)
				zone.getListenerEngine().removeMethodInvokedListener(_doorZoneListener);
		}
		if(_steamCorridorListener != null)
		{
			L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3204);

			if(zone != null)
				zone.getListenerEngine().removeMethodInvokedListener(_steamCorridorListener);
		}
		if(_scDespawnOG != null)
		{
			_scDespawnOG.cancel(true);
			_scDespawnOG = null;
		}

		if(_baylorMovieTask != null)
		{
			_baylorMovieTask.cancel(true);
			_baylorMovieTask = null;
		}
	}

	public void successEnd()
	{
		_terminate = true;
		for(Integer objectId : _members)
		{
			L2Player player = L2ObjectsStorage.getPlayer(objectId);
			if(player != null && _template.getZone().isInsideZone(player))
				player.setVar("instance-" + _template.getType(), String.valueOf(_template.getId()), (int)(System.currentTimeMillis()/ 1000 + 24 * 60 * 60));
		}

		if(_endTask != null)
			_endTask.cancel(true);

		_endTime = System.currentTimeMillis() + 300000;
		_endTask = ThreadPoolManager.getInstance().scheduleGeneral(new EndTask(5), 500);
	}

	public void enterCoralGarden()
	{
		_startOracle.despawnAll();
		_doorCG1.openMe();
		_doorCG2.openMe();

		_coralGardenGroup1 = SpawnTable.getInstance().getEventGroupSpawn("cg_group1", this);
		_coralGardenGroup1.setRespawnDelay(0);
		_coralGardenGroup1.doSpawn();

	}

	public void enterSCandES()
	{
		_startOracle.despawnAll();
		_scesGroup = new L2GroupSpawn();
		_scesGroup.setReflection(getReflection());
		_scesGroup.setInstance(this);
		_scesGroup.setRespawnDelay(0);

		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144301, 150119, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144294, 150280, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144280, 150441, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144398, 149409, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144179, 149362, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(145380, 149647, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(146532, 149532, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(145221, 150973, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(145439, 150958, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(146607, 150979, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(146500, 151279, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(147573, 151201, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144125, 149527, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144389, 149340, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144425, 151300, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144321, 151216, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(144189, 151132, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(145236, 150936, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(146679, 151237, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(147705, 150957, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(145491, 149599, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(145240, 149596, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(146361, 149332, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(146266, 149558, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(147683, 149308, -12163));
		_scesGroup.addSpawn(DETAINEES[Rnd.get(DETAINEES.length)], new Location(147971, 149368, -12163));

		try
		{
			_gkLohan = new L2Spawn(NpcTable.getTemplate(GK_LOHAN));
			_gkLohan.setAmount(1);
			_gkLohan.setLoc(new Location(147744, 149656, -12163));
			_gkLohan.setInstance(this);
			_gkLohan.setReflection(getReflection());
			_gkLohan.stopRespawn();
			_gkLohan.spawnOne();

			_gkProvo = new L2Spawn(NpcTable.getTemplate(GK_PROVO));
			_gkProvo.setAmount(1);
			_gkProvo.setLoc(new Location(147736, 151183, -12163));
			_gkProvo.setInstance(this);
			_gkProvo.setReflection(getReflection());
			_gkProvo.stopRespawn();
			_gkProvo.spawnOne();
		}
		catch(Exception e)
		{
			System.out.println(this + " Can't spawn start Oracle " + e);
			e.printStackTrace();
		}

		_scesGroup.doSpawn();
		_doorSCES.openMe();

	}

	private L2DoorInstance getDoorById(int doorId)
	{
		Reflection ref = ReflectionTable.getInstance().getById(getReflection());

		for(L2Object object : ref.getAllObjects())
			if(object instanceof L2DoorInstance && ((L2DoorInstance) object).getDoorId() == doorId)
				return (L2DoorInstance) object;

		return null;
	}

	@Override
	public void notifyKill(L2Character mob, L2Player killer)
	{
		if(mob.getNpcId() >= 22311 && mob.getNpcId() <= 22317 && _coralGardenGroup1.isAllDead())
		{
			_coralGardenGroup2 = SpawnTable.getInstance().getEventGroupSpawn("cg_group2", this);
			_coralGardenGroup2.setRespawnDelay(0);
			_coralGardenGroup2.doSpawn();
		}
		else if(mob.getNpcId() == GOLEM)
		{
			guardsOnPlace++;
			if(guardsOnPlace == 2)
			{
				_doorCG3.openMe();
				try
				{
					_tearsSpawn = new L2Spawn(NpcTable.getTemplate(TEARS));
					_tearsSpawn.setAmount(1);
					_tearsSpawn.setReflection(getReflection());
					_tearsSpawn.setInstance(this);
					_tearsSpawn.setLoc(new Location(144312, 154420, -11879, 32489));
					_tearsSpawn.stopRespawn();
					_tearsSpawn.spawnOne();
				}
				catch(Exception e)
				{
					System.out.println(this + " can't spawn Tears: " + TEARS);
					e.printStackTrace();
				}
			}
		}
		else if(mob.getNpcId() == TEARS)
		{
			try
			{
				_tearsOracle = new L2Spawn(NpcTable.getTemplate(32274));
				_tearsOracle.setAmount(1);
				_tearsOracle.setReflection(getReflection());
				_tearsOracle.setInstance(this);
				_tearsOracle.setLoc(new Location(144312, 154420, -11879, 32489));
				_tearsOracle.stopRespawn();
				_tearsOracle.spawnOne();
			}
			catch(Exception e)
			{
				System.out.println(this + " can't spawn Tears Oracle: 32274");
				e.printStackTrace();
			}
		}
		else if(mob.getNpcId() == GK_LOHAN)
		{
			_gkProvo.despawnAll();
			L2MonsterInstance gk = (L2MonsterInstance) mob;
			gk.dropItem(killer, BLUE_KEY, 1);
			spawnEmerald();
		}
		else if(mob.getNpcId() == GK_PROVO)
		{
			_gkLohan.despawnAll();
			L2MonsterInstance gk = (L2MonsterInstance) mob;
			gk.dropItem(killer, RED_KEY, 1);

			_steamCorridorListener = new SteamCorridorListener();

			L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3204);

			if(zone != null)
				zone.getListenerEngine().addMethodInvokedListener(_steamCorridorListener);
			else
				System.out.println(this + " zone id: 3204 not found!!");

		}
		else if(mob.getNpcId() == DOLPH)
		{
			_dolpKilled = true;
			if(_terodKilled)
				activateDoorZones();
		}
		else if(mob.getNpcId() == TEROD)
		{
			_terodKilled = true;
			if(_dolpKilled)
				activateDoorZones();
		}
		else
		if((mob.getNpcId() == ES_ROOM_RB1 || mob.getNpcId() == ES_ROOM_RB2 || mob.getNpcId() == ES_ROOM_RB3) && _emeraldRoomsRB.isAllDead())
		{
			try
			{
				_darnelSpawn = new L2Spawn(NpcTable.getTemplate(DARNEL));
				_darnelSpawn.setAmount(1);
				_darnelSpawn.setReflection(getReflection());
				_darnelSpawn.setInstance(this);
				_darnelSpawn.setLoc(new Location(152760, 145944, -12584, 65417));
				_darnelSpawn.stopRespawn();
				_darnelSpawn.spawnOne();
				spawnDarnelTraps();
			}
			catch(Exception e)
			{
				System.out.println(this + " can't spawn Darnel: " + DARNEL);
				e.printStackTrace();
			}

			_doorES1.openMe();
			_doorES2.openMe();
		}
		else if(mob.getNpcId() == DARNEL)
		{
			try
			{
				_darnelOracle = new L2Spawn(NpcTable.getTemplate(32275));
				_darnelOracle.setAmount(1);
				_darnelOracle.setReflection(getReflection());
				_darnelOracle.setInstance(this);
				_darnelOracle.setLoc(new Location(152763, 145936, -12610, 65417));
				_darnelOracle.stopRespawn();
				_darnelOracle.spawnOne();
			}
			catch(Exception e)
			{
				System.out.println(this + " can't spawn Darnel Oracle: 32275");
				e.printStackTrace();
			}
		}
		else if(mob.getNpcId() == KECHI)
		{
			try
			{
				_kechiOracle = new L2Spawn(NpcTable.getTemplate(32279));
				_kechiOracle.setAmount(1);
				_kechiOracle.setReflection(getReflection());
				_kechiOracle.setInstance(this);
				_kechiOracle.setLoc(new Location(154074, 149527, -12184, 33738));
				_kechiOracle.stopRespawn();
				_kechiOracle.spawnOne();
			}
			catch(Exception e)
			{
				System.out.println(this + " can't spawn Kechi Oracle: 32279");
				e.printStackTrace();
			}
		}
		else if((mob.getNpcId() == SC_CAPTAIN1 || mob.getNpcId() == SC_CAPTAIN2 || mob.getNpcId() == SC_CAPTAIN3 ||
				mob.getNpcId() == SC_CAPTAIN4 || mob.getNpcId() == SC_CAPTAIN5 ||
				mob.getNpcId() == SC_GUARD1 || mob.getNpcId() == SC_GUARD2 || mob.getNpcId() == SC_GUARD3 ||
				mob.getNpcId() == SC_IRIS1 || mob.getNpcId() == SC_IRIS2 || mob.getNpcId() == SC_IRIS3))
		{
			if(_steamCorridorRoom == 1 && _corridorRoom1.isAllDead())
			{
				_corridorEvas = SpawnTable.getInstance().getEventGroupSpawn("sc_evas_room1", this);
				_corridorEvas.setRespawnDelay(0);
				_corridorEvas.doSpawn();

				for(L2NpcInstance npc : _corridorEvas.getAllSpawned())
					npc.setCurrentHp(1);
			}
			else if(_steamCorridorRoom == 2 && _corridorRoom2.isAllDead())
			{
				_corridorEvas = SpawnTable.getInstance().getEventGroupSpawn("sc_evas_room2", this);
				_corridorEvas.setRespawnDelay(0);
				_corridorEvas.doSpawn();

				for(L2NpcInstance npc : _corridorEvas.getAllSpawned())
					npc.setCurrentHp(1);
			}
			else if(_steamCorridorRoom == 3 && _corridorRoom3.isAllDead())
			{
				_corridorEvas = SpawnTable.getInstance().getEventGroupSpawn("sc_evas_room3", this);
				_corridorEvas.setRespawnDelay(0);
				_corridorEvas.doSpawn();

				for(L2NpcInstance npc : _corridorEvas.getAllSpawned())
					npc.setCurrentHp(1);
			}
			else if(_steamCorridorRoom == 4 && _corridorRoom4.isAllDead())
			{
				_corridorEvas = SpawnTable.getInstance().getEventGroupSpawn("sc_evas_room4", this);
				_corridorEvas.setRespawnDelay(0);
				_corridorEvas.doSpawn();

				for(L2NpcInstance npc : _corridorEvas.getAllSpawned())
					npc.setCurrentHp(1);
			}
		}
		else
		if((mob.getNpcId() == SC_EVA1 || mob.getNpcId() == SC_EVA2 || mob.getNpcId() == SC_EVA3 || mob.getNpcId() == SC_EVA4))
		{
			if(_steamCorridorRoom == 1 && mob.getNpcId() == SC_EVA2 && !_scOGSpawned)
			{
				_scOGSpawned = true;
				_corridorEvas.despawnAll();

				L2Skill timer = SkillTable.getInstance().getInfo(TIMER_ID, 2);

				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
						if(mob.isInRange(member, 1000))
						{
							member.stopEffect(TIMER_ID);
							timer.applyEffects(member, member, false);
						}
				try
				{
					L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(SC_OG1));
					spawn.setAmount(1);
					spawn.setReflection(getReflection());
					spawn.setInstance(this);
					spawn.setLoc(new Location(147090, 152572, -12195, 33380));
					spawn.stopRespawn();
					_scDespawnOG = ThreadPoolManager.getInstance().scheduleGeneral(new DespawnTask(spawn.spawnOne()), 60000);
				}
				catch(Exception e)
				{
					System.out.println(this + " steam corridor 1 oracle spawn error: " + e);
					e.printStackTrace();
				}
			}
			else if(_steamCorridorRoom == 2 && mob.getNpcId() == SC_EVA4 && !_scOGSpawned)
			{
				_scOGSpawned = true;
				_corridorEvas.despawnAll();

				L2Skill timer = SkillTable.getInstance().getInfo(TIMER_ID, 3);

				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
						if(mob.isInRange(member, 1000))
						{
							member.stopEffect(TIMER_ID);
							timer.applyEffects(member, member, false);
						}

				try
				{
					L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(SC_OG2));
					spawn.setAmount(1);
					spawn.setReflection(getReflection());
					spawn.setInstance(this);
					spawn.setLoc(new Location(149785, 152715, -12195, 33380));
					spawn.stopRespawn();
					_scDespawnOG = ThreadPoolManager.getInstance().scheduleGeneral(new DespawnTask(spawn.spawnOne()), 60000);
				}
				catch(Exception e)
				{
					System.out.println(this + " steam corridor 2 oracle spawn error: " + e);
					e.printStackTrace();
				}
			}
			else if(_steamCorridorRoom == 3 && mob.getNpcId() == SC_EVA3 && !_scOGSpawned)
			{
				_scOGSpawned = true;
				_corridorEvas.despawnAll();

				L2Skill timer = SkillTable.getInstance().getInfo(TIMER_ID, 4);

				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
						if(mob.isInRange(member, 1000))
						{
							member.stopEffect(TIMER_ID);
							timer.applyEffects(member, member, false);
						}

				try
				{
					L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(SC_OG3));
					spawn.setAmount(1);
					spawn.setReflection(getReflection());
					spawn.setInstance(this);
					spawn.setLoc(new Location(152470, 152645, -12195, 33380));
					spawn.stopRespawn();
					_scDespawnOG = ThreadPoolManager.getInstance().scheduleGeneral(new DespawnTask(spawn.spawnOne()), 60000);
				}
				catch(Exception e)
				{
					System.out.println(this + " steam corridor 3 oracle spawn error: " + e);
					e.printStackTrace();
				}
			}
			else if(_steamCorridorRoom == 4 && mob.getNpcId() == SC_EVA1 && !_scOGSpawned)
			{
				_scOGSpawned = true;
				_corridorEvas.despawnAll();

				L2Skill timer = SkillTable.getInstance().getInfo(TIMER_ID, 1);

				if(killer.getParty() != null)
					for(L2Player member : killer.getParty().getPartyMembers())
					{
						member.stopEffect(TIMER_ID);
						timer.applyEffects(member, member, false);
					}

				try
				{
					_kechiSpawn = new L2Spawn(NpcTable.getTemplate(KECHI));
					_kechiSpawn.setAmount(1);
					_kechiSpawn.setReflection(getReflection());
					_kechiSpawn.setInstance(this);
					_kechiSpawn.setLoc(new Location(154074, 149527, -12184, 33738));
					_kechiSpawn.stopRespawn();
					_kechiSpawn.spawnOne();
				}
				catch(Exception e)
				{
					System.out.println(this + " can't spawn Kechi: " + KECHI);
					e.printStackTrace();
				}

				_doorSC1.openMe();
				_doorSC2.openMe();
			}
		}
		else if(mob.getNpcId() == BAYLOR)
		{
			try
			{
				L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(Rnd.chance(50) ? 29116 : 29117));
				spawn.setAmount(1);
				spawn.setReflection(getReflection());
				spawn.setInstance(this);
				spawn.setLoc(mob.getLoc());
				spawn.spawnOne();

				spawn = new L2Spawn(NpcTable.getTemplate(32280));
				spawn.setAmount(1);
				spawn.setReflection(getReflection());
				spawn.setInstance(this);
				spawn.setLoc(BAYLOR_CENTER);
				spawn.spawnOne();
			}
			catch(Exception e)
			{
				_log.warn(this + " can't spawn Baylor chest: " + e);
				e.printStackTrace();
			}

			successEnd();
		}
		else
		{
			if(!_dolpSpawned)
			{
				for(int npcId : EMERALDMOBS1)
					if(npcId == mob.getNpcId() && _emeraldSquareGroup1.isAllDead())
					{
						spawnEmeraldRb1();
						return;
					}

				for(int npcId : EMERALDMOBS2)
					if(npcId == mob.getNpcId() && _emeraldSquareGroup1.isAllDead())
					{
						spawnEmeraldRb1();
						return;
					}
			}

			if(!_terodSpawned)
				for(int npcId : EMERALDMOBS3)
					if(npcId == mob.getNpcId() && _emeraldSquareGroup2.isAllDead())
					{
						spawnEmeraldRb2();
						return;
					}
		}
	}

	private void spawnEmerald()
	{
		_emeraldSquareGroup1 = new L2GroupSpawn();
		_emeraldSquareGroup1.setReflection(getReflection());
		_emeraldSquareGroup1.setInstance(this);
		_emeraldSquareGroup1.setRespawnDelay(0);

		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144370, 147538, -12164));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144413, 147444, -12157));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144443, 147527, -12164));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144777, 147683, -12163));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144959, 147572, -12166));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(145084, 147296, -12149));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(145214, 147196, -12130));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144974, 146805, -12066));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144856, 146839, -12065));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144766, 146954, -12073));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144732, 147011, -12081));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144237, 146847, -12069));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(144117, 146955, -12093));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(143959, 147113, -12109));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(143945, 146655, -12061));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS1[Rnd.get(EMERALDMOBS1.length)], new Location(143768, 146717, -12062));

		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(142853, 146301, -12059));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143311, 145879, -12062));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143740, 145542, -12055));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143465, 144951, -12054));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(142818, 145422, -12060));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(141868, 144992, -12042));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(141649, 144518, -11979));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(141793, 144455, -11976));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(142440, 144359, -11978));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143375, 144445, -12036));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143503, 142726, -11910));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143674, 142717, -11913));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143756, 142833, -11912));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143801, 142947, -11921));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(143948, 142501, -11913));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(144072, 143325, -11957));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(144075, 143430, -11973));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(144099, 143684, -12009));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(144106, 143917, -12034));
		_emeraldSquareGroup1.addSpawn(EMERALDMOBS2[Rnd.get(EMERALDMOBS2.length)], new Location(144143, 144058, -12047));

		_emeraldSquareGroup2 = new L2GroupSpawn();
		_emeraldSquareGroup2.setReflection(getReflection());
		_emeraldSquareGroup2.setInstance(this);
		_emeraldSquareGroup2.setRespawnDelay(0);

		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(147684, 143980, -12250));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(147809, 143701, -12250));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(147687, 143485, -12250));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(147608, 146378, -12293));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(147564, 146644, -12298));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(147694, 146649, -12306));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(149320, 146961, -12387));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(149308, 146880, -12385));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(149259, 144101, -12261));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(149178, 143959, -12261));
		_emeraldSquareGroup2.addSpawn(EMERALDMOBS3[Rnd.get(EMERALDMOBS3.length)], new Location(149043, 143720, -12261));

		_emeraldRoom1 = new L2GroupSpawn();
		_emeraldRoom1.setReflection(getReflection());
		_emeraldRoom1.setInstance(this);
		_emeraldRoom1.setRespawnDelay(0);

		_emeraldRoom1.addSpawn(EMERALDROOM1[Rnd.get(EMERALDROOM1.length)], new Location(143139, 140992, -11911));
		_emeraldRoom1.addSpawn(EMERALDROOM1[Rnd.get(EMERALDROOM1.length)], new Location(142091, 140609, -11911));
		_emeraldRoom1.addSpawn(EMERALDROOM1[Rnd.get(EMERALDROOM1.length)], new Location(142151, 140544, -11911));
		_emeraldRoom1.addSpawn(EMERALDROOM1[Rnd.get(EMERALDROOM1.length)], new Location(143128, 139879, -11911));
		_emeraldRoom1.addSpawn(EMERALDROOM1[Rnd.get(EMERALDROOM1.length)], new Location(143221, 139921, -11911));
		_emeraldRoom1.addSpawn(22298, new Location(142677, 140474, -11910));
		_emeraldRoom1.addSpawn(32359, new Location(142199, 140015, -11911));
		_emeraldRoom1.addSpawn(18378, new Location(143675, 142818, -11910, 16384));

		_emeraldSquareGroup1.doSpawn();
		_emeraldSquareGroup2.doSpawn();
		_emeraldRoom1.doSpawn();

		_doorES1 = getDoorById(24220005);
		_doorES2 = getDoorById(24220006);

	}

	private void spawnEmeraldRb1()
	{
		_dolpSpawned = true;
		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(DOLPH));
			spawn.setAmount(1);
			spawn.setReflection(getReflection());
			spawn.setInstance(this);
			spawn.setLoc(new Location(142045, 143410, -11860));
			spawn.stopRespawn();
			spawn.spawnOne();
		}
		catch(Exception e)
		{
			System.out.println(this + " can't spawn: " + DOLPH);
			e.printStackTrace();
		}
	}

	private void spawnEmeraldRb2()
	{
		_terodSpawned = true;
		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(TEROD));
			spawn.setAmount(1);
			spawn.setReflection(getReflection());
			spawn.setInstance(this);
			spawn.setLoc(new Location(147692, 146559, -12300));
			spawn.stopRespawn();
			spawn.spawnOne();
		}
		catch(Exception e)
		{
			System.out.println(this + " can't spawn: " + TEROD);
			e.printStackTrace();
		}
	}

	@Override
	public void notifyAttacked(L2Character mob, L2Player atacker)
	{
		if(mob.getNpcId() == TEARS && _doorCG3.isOpen())
			_doorCG3.closeMe();
		else if(mob.getNpcId() == DARNEL && _doorES2.isOpen())
		{
			_doorES1.closeMe();
			_doorES2.closeMe();
		}
		else if(mob.getNpcId() == KECHI && _doorSC1.isOpen())
		{
			_doorSC1.closeMe();
			_doorSC2.closeMe();
		}
	}

	private void activateDoorZones()
	{
		_doorZoneListener = new DoorZoneListener();

		L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3201);

		if(zone != null)
			zone.getListenerEngine().addMethodInvokedListener(_doorZoneListener);
		else
			System.out.println(this + " zone id: 3201 not found!!");

		zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3202);

		if(zone != null)
			zone.getListenerEngine().addMethodInvokedListener(_doorZoneListener);
		else
			System.out.println(this + " zone id: 3201 not found!!");

		zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 3203);

		if(zone != null)
			zone.getListenerEngine().addMethodInvokedListener(_doorZoneListener);
		else
			System.out.println(this + " zone id: 3201 not found!!");

		_emeraldRoomsRB = new L2GroupSpawn();
		_emeraldRoomsRB.setReflection(getReflection());
		_emeraldRoomsRB.setInstance(this);
		_emeraldRoomsRB.setRespawnDelay(0);

		_emeraldRoomsRB.addSpawn(ES_ROOM_RB1, new Location(146626, 141838, -11900, 11900));
		_emeraldRoomsRB.addSpawn(ES_ROOM_RB2, new Location(145124, 143820, -12830, 772));
		_emeraldRoomsRB.addSpawn(ES_ROOM_RB3, new Location(150559, 141848, -12139, -12139));

		_emeraldRoomsRB.doSpawn();

		_emeraldRoom2 = new L2GroupSpawn();
		_emeraldRoom2.setReflection(getReflection());
		_emeraldRoom2.setInstance(this);
		_emeraldRoom2.setRespawnDelay(0);

		_emeraldRoom2.addSpawn(22287, new Location(145783, 142060, -11900, 120));
		_emeraldRoom2.addSpawn(22287, new Location(145783, 142060, -11900, 120));
		_emeraldRoom2.addSpawn(22288, new Location(146828, 142080, -11900, 120));
		_emeraldRoom2.addSpawn(22288, new Location(146828, 142080, -11900, 120));
		_emeraldRoom2.addSpawn(22289, new Location(146877, 140887, -11900, 49800));
		_emeraldRoom2.addSpawn(22289, new Location(146877, 140887, -11900, 49800));

		_emeraldDoorSpawn = new FastMap<Integer, L2GroupSpawn>().shared();
		_emeraldDoorSpawn.put(24220002, _emeraldRoom2);

		_emeraldRoom3 = new L2GroupSpawn();
		_emeraldRoom3.setReflection(getReflection());
		_emeraldRoom3.setInstance(this);
		_emeraldRoom3.setRespawnDelay(0);

		_emeraldRoom3.addSpawn(22293, new Location(145119, 143707, -12830, 772));
		_emeraldRoom3.addSpawn(22293, new Location(145119, 143707, -12830, 772));
		_emeraldRoom3.addSpawn(22294, new Location(145119, 143707, -12830, 772));
		_emeraldRoom3.addSpawn(22294, new Location(145119, 143707, -12830, 772));

		_emeraldDoorSpawn.put(24220003, _emeraldRoom3);

		_emeraldRoom4 = new L2GroupSpawn();
		_emeraldRoom4.setReflection(getReflection());
		_emeraldRoom4.setInstance(this);
		_emeraldRoom4.setRespawnDelay(0);

		_emeraldRoom4.addSpawn(22280, new Location(150563, 142227, -12138, 16384));
		_emeraldRoom4.addSpawn(22280, new Location(150563, 142227, -12138, 16384));
		_emeraldRoom4.addSpawn(22281, new Location(150969, 141822, -12139, 65411));
		_emeraldRoom4.addSpawn(22281, new Location(150969, 141822, -12139, 65411));
		_emeraldRoom4.addSpawn(22283, new Location(150169, 141800, -12139, 65411));
		_emeraldRoom4.addSpawn(22283, new Location(150169, 141800, -12139, 65411));

		_emeraldDoorSpawn.put(24220004, _emeraldRoom4);
	}

	private void spawnDarnelTraps()
	{
		try
		{
			int trapId = 18387;
			for(int x = 152160; x <= 153360; x += 300)
				for(int y = 145344; y <= 146544; y += 300)
				{
					L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(trapId));
					spawn.setAmount(1);
					spawn.setRespawnDelay(20);
					spawn.setReflection(getReflection());
					spawn.setInstance(this);
					spawn.setLoc(new Location(x, y, -12584));
					spawn.init();
					trapId++;
				}
		}
		catch(Exception e)
		{
			System.out.println(this + " can't spawn Darnel's trap: " + e);
			e.printStackTrace();
		}
	}

	private void enterSCRoom1()
	{
		_steamCorridorRoom = 1;

		_corridorRoom1 = SpawnTable.getInstance().getEventGroupSpawn("sc_room1", this);
		_corridorRoom1.setRespawnDelay(0);

		L2GroupSpawn guards = SpawnTable.getInstance().getEventGroupSpawn("sc_room1o", this);
		guards.setRespawnDelay(0);
		guards.doSpawn();

		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(SC_TRAP1));
			spawn.setAmount(1);
			spawn.setRespawnDelay(30);
			spawn.setReflection(getReflection());
			spawn.setInstance(this);
			spawn.setLoc(new Location(145526, 152828, -12195));
			spawn.init();

			spawn = new L2Spawn(NpcTable.getTemplate(SC_TRAP2));
			spawn.setAmount(1);
			spawn.setRespawnDelay(30);
			spawn.setReflection(getReflection());
			spawn.setInstance(this);
			spawn.setLoc(new Location(146391, 152391, -12195));
			spawn.init();
		}
		catch(Exception e)
		{
			System.out.println(this + " can't spawn steam corridor room 1 traps: " + e);
			e.printStackTrace();
		}

		_corridorRoom1.doSpawn();
	}

	public void enterSCRoom2()
	{
		_scOGSpawned = false;
		_steamCorridorRoom = 2;

		_corridorRoom2 = SpawnTable.getInstance().getEventGroupSpawn("sc_room2", this);
		_corridorRoom2.setRespawnDelay(0);

		L2GroupSpawn guards = SpawnTable.getInstance().getEventGroupSpawn("sc_room2o", this);
		guards.setRespawnDelay(0);
		guards.doSpawn();

		_corridorRoom2.doSpawn();
	}

	public void enterSCRoom3()
	{
		_scOGSpawned = false;
		_steamCorridorRoom = 3;

		_corridorRoom3 = SpawnTable.getInstance().getEventGroupSpawn("sc_room3", this);
		_corridorRoom3.setRespawnDelay(0);

		try
		{
			L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(SC_TRAP3));
			spawn.setAmount(1);
			spawn.setRespawnDelay(30);
			spawn.setReflection(getReflection());
			spawn.setInstance(this);
			spawn.setLoc(new Location(150906, 152841, -12195));
			spawn.init();

			spawn = new L2Spawn(NpcTable.getTemplate(SC_TRAP4));
			spawn.setAmount(1);
			spawn.setRespawnDelay(30);
			spawn.setReflection(getReflection());
			spawn.setInstance(this);
			spawn.setLoc(new Location(151776, 152386, -12195));
			spawn.init();
		}
		catch(Exception e)
		{
			System.out.println(this + " can't spawn steam corridor room 1 traps: " + e);
			e.printStackTrace();
		}

		L2GroupSpawn guards = SpawnTable.getInstance().getEventGroupSpawn("sc_room3o", this);
		guards.setRespawnDelay(0);
		guards.doSpawn();

		_corridorRoom3.doSpawn();
	}

	public void enterSCRoom4()
	{
		_scOGSpawned = false;
		_steamCorridorRoom = 4;

		_corridorRoom4 = SpawnTable.getInstance().getEventGroupSpawn("sc_room4", this);
		_corridorRoom4.setRespawnDelay(0);

		L2GroupSpawn guards = SpawnTable.getInstance().getEventGroupSpawn("sc_room4o", this);
		guards.setRespawnDelay(0);
		guards.doSpawn();

		_corridorRoom4.doSpawn();
	}

	public void enterBaylor()
	{
		L2GroupSpawn golems = SpawnTable.getInstance().getEventGroupSpawn("baylor_start", this);
		golems.doSpawn();
		_baylorMovieTask = ThreadPoolManager.getInstance().scheduleGeneral(new BaylorMovieTask(), 5000);
	}

	private class DoorZoneListener extends L2ZoneEnterLeaveListener
	{
		private FastMap<Integer, L2Player> _openers = new FastMap<Integer, L2Player>().shared();

		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
			if(object.isPlayer() && object.getReflection() == getReflection())
			{
				L2DoorInstance door = getDoorById(zone.getEntityId());
				L2Player player = (L2Player) object;
				if(!door.isOpen() && player.getItemCountByItemId(9694) > 0 && player.destroyItemByItemId("EmeraldDoor", 9694, 1, null, true))
				{
					_openers.put(door.getDoorId(), player);
					if(_emeraldDoorSpawn != null && _emeraldDoorSpawn.containsKey(door.getDoorId()))
						_emeraldDoorSpawn.get(door.getDoorId()).doSpawn();
					door.openMe();
				}
			}
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
			if(object.isPlayer() && object.getReflection() == getReflection())
			{
				L2DoorInstance door = getDoorById(zone.getEntityId());
				L2Player player = (L2Player) object;
				if(door.isOpen() && _openers.get(door.getDoorId()) == player)
				{
					_openers.remove(door.getDoorId());
					door.closeMe();
					if(_emeraldDoorSpawn != null && _emeraldDoorSpawn.containsKey(door.getDoorId()))
						_emeraldDoorSpawn.get(door.getDoorId()).despawnAll();
				}
			}
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}

	private class SteamCorridorListener extends L2ZoneEnterLeaveListener
	{
		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
			if(object.getReflection() == getReflection() && object.isPlayer() && !_steamCorridorActivated)
			{
				_steamCorridorActivated = true;
				enterSCRoom1();
				L2Player player = (L2Player) object;
				if(player.getParty() != null)
				{
					L2Skill timer = SkillTable.getInstance().getInfo(TIMER_ID, 1);
					for(L2Player member : player.getParty().getPartyMembers())
						if(member != null)
						{
							member.stopEffect(TIMER_ID);
							timer.applyEffects(member, member, false);
						}
				}
			}
		}

		@Override
		public void objectLeaved(L2Zone zone, L2Character object)
		{
		}

		@Override
		public void sendZoneStatus(L2Zone zone, L2Player object)
		{
		}
	}

	private class DespawnTask implements Runnable
	{
		L2NpcInstance _npc;

		public DespawnTask(L2NpcInstance npc)
		{
			_npc = npc;
		}

		public void run()
		{
			if(_npc != null)
				_npc.deleteMe();
		}
	}

	private class BaylorMovieTask implements Runnable
	{
		public synchronized void run()
		{
			try
			{
				_baylorSpawn = new L2Spawn(NpcTable.getTemplate(BAYLOR));
				_baylorSpawn.setAmount(1);
				_baylorSpawn.setLoc(BAYLOR_CENTER);
				_baylorSpawn.setInstance(CrystalCavernsInstance.this);
				_baylorSpawn.setReflection(getReflection());
				_baylorSpawn.stopRespawn();

				L2Spawn dummySpawn1 = new L2Spawn(NpcTable.getTemplate(29106));
				dummySpawn1.setAmount(1);
				dummySpawn1.setLoc(BAYLOR_CENTER);
				dummySpawn1.setInstance(CrystalCavernsInstance.this);
				dummySpawn1.setReflection(getReflection());
				dummySpawn1.stopRespawn();
				L2NpcInstance dummy1 = dummySpawn1.spawnOne();

				L2Spawn dummySpawn2 = new L2Spawn(NpcTable.getTemplate(29107));
				dummySpawn2.setAmount(1);
				dummySpawn2.setLoc(new Location(152471, 142713, -12763));
				dummySpawn2.setInstance(CrystalCavernsInstance.this);
				dummySpawn2.setReflection(getReflection());
				dummySpawn2.stopRespawn();
				L2NpcInstance dummy2 = dummySpawn2.spawnOne();

				L2Spawn guardSpawn = new L2Spawn(NpcTable.getTemplate(29104));
				guardSpawn.setAmount(1);
				guardSpawn.setLoc(new Location(153308, 142067, -12763, 583));
				guardSpawn.setInstance(CrystalCavernsInstance.this);
				guardSpawn.setReflection(getReflection());
				guardSpawn.stopRespawn();

				L2GroupSpawn guard1 = new L2GroupSpawn();
				guard1.setReflection(getReflection());
				guard1.setInstance(CrystalCavernsInstance.this);
				guard1.setRespawnDelay(0);

				guard1.addSpawn(29104, new Location(153326, 141959, -12763, 5016));
				guard1.addSpawn(29104, new Location(153403, 141865, -12763, 10523));

				L2GroupSpawn guard2 = new L2GroupSpawn();
				guard2.setReflection(getReflection());
				guard2.setInstance(CrystalCavernsInstance.this);
				guard2.setRespawnDelay(0);

				guard2.addSpawn(29104, new Location(153505, 141819, -12763, 13781));
				guard2.addSpawn(29104, new Location(153618, 141807, -12763, 19456));
				guard2.addSpawn(29104, new Location(153735, 141863, -12763, 24650));

				L2GroupSpawn guard3 = new L2GroupSpawn();
				guard3.setReflection(getReflection());
				guard3.setInstance(CrystalCavernsInstance.this);
				guard3.setRespawnDelay(0);

				guard3.addSpawn(29104, new Location(153828, 141982, -12763, 30025));
				guard3.addSpawn(29104, new Location(153846, 142116, -12763, 34808));
				guard3.addSpawn(29104, new Location(153783, 142248, -12763, 40705));

				L2GroupSpawn guard4 = new L2GroupSpawn();
				guard4.setReflection(getReflection());
				guard4.setInstance(CrystalCavernsInstance.this);
				guard4.setRespawnDelay(0);

				guard4.addSpawn(29104, new Location(153633, 142340, -12763, 46216));
				guard4.addSpawn(29104, new Location(153453, 142318, -12763, 52602));
				guard4.addSpawn(29104, new Location(153340, 142215, -12763, 59559));

				dummy1.broadcastPacket(new SpecialCamera(dummy1, 2305, 0, 90, 0, 5000, 0, 20, true, true));
				wait(1000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 2305, 0, 90, 2000, 3000, 0, 180, true, false));
				wait(2000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 90, -180, 0, 16000, 20000, 0, 20, true, false));
				wait(16500);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 90, -310, 2, 4000, 20000, 0, 10, true, false));
				wait(4000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 90, -440, 2, 4000, 20000, 0, 10, true, false));
				wait(4000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 90, -570, 2, 4000, 20000, 0, 10, true, false));
				wait(4000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 90, -700, 2, 4000, 20000, 0, 10, true, false));
				wait(4000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 350, -710, 2, 900, 20000, 0, 10, true, false));
				wait(900);
				L2NpcInstance guard = guardSpawn.spawnOne();
				guard1.doSpawn();
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 580, -820, 2, 4000, 20000, 0, 10, true, false));
				wait(1000);
				guard2.doSpawn();
				wait(1000);
				guard3.doSpawn();
				wait(1000);
				guard4.doSpawn();
				wait(1000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 610, -820, 70, 2000, 20000, 0, 0, true, false));
				wait(2000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 0, -820, 70, 600, 20000, 0, 0, true, false));
				wait(600);
				dummy1.broadcastPacket(new SpecialCamera(dummy2, 600, -144, 0, 0, 10000, -5, 16, true, true));
				L2NpcInstance baylor = _baylorSpawn.spawnOne();
				baylor.setDisabled(true);
				dummy1.broadcastPacket(new SocialAction(baylor.getObjectId(), 1));
				wait(5000);
				dummy1.broadcastPacket(new SpecialCamera(dummy2, 800, -143, 0, 4000, 10000, -5, 8, true, false));
				wait(6000);
				dummy1.broadcastPacket(new SpecialCamera(guard, -80, -175, -18, 0, 10000, 0, 12, true, true));
				dummy1.broadcastPacket(new SocialAction(guard.getObjectId(), 2));
				wait(1000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 30, -140, 0, 2000, 10000, 0, 10, true, true));
				wait(2000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 30, -140, 0, 1000, 10000, 0, 40, true, false));
				wait(1000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 450, -140, 0, 2000, 10000, 0, 5, true, false));
				wait(3000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 10, -140, 0, 3000, 10000, 0, 22, true, false));
				wait(4000);
				baylor.doCast(SkillTable.getInstance().getInfo(5402, 1), baylor, false);
				wait(1000);
				dummy1.broadcastPacket(new SpecialCamera(dummy1, 250, -140, 60, 3000, 3000, 0, 20, true, false));
				wait(3500);
				baylor.altUseSkill(SkillTable.getInstance().getInfo(5401, 1), baylor);

				guard.doDie(null);
				for(L2NpcInstance npc : guard1.getAllSpawned())
					npc.doDie(null);
				for(L2NpcInstance npc : guard2.getAllSpawned())
					npc.doDie(null);
				for(L2NpcInstance npc : guard3.getAllSpawned())
					npc.doDie(null);
				for(L2NpcInstance npc : guard4.getAllSpawned())
					npc.doDie(null);

				for(L2Player player : dummy1.getAroundPlayers(2000))
					player.setDisabled(false);

				baylor.setDisabled(false);
				dummySpawn1.despawnAll();
				dummySpawn2.despawnAll();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

			
		}
	}
}
