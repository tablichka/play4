package bosses;

import npc.model.SailrenMinion;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.extensions.scripts.ScriptFile;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.serverpackets.SpecialCamera;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.entity.BossState;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.model.instances.L2BossInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 13.10.2009 15:23:45
 */
public class SailrenManager extends Functions implements ScriptFile
{
	private BossState _state = new BossState(29065);
	private static Log _log = LogFactory.getLog("sailren");
	private ScheduledFuture<?> _intervalEndTask;
	private ScheduledFuture<?> _spawnTask;
	private ScheduledFuture<?> _activutyCheck;
	private static SailrenManager _instance;
	private boolean _isStarted = false;
	private int _stage;
	private int _velKilled;
	private SailrenMinion vel1, vel2, vel3, pter, tyran;
	private L2BossInstance sailren;
	private L2NpcInstance dummy, cube;
	private L2Spawn sailrenSpawn, dummySpawn, cubeSpawn;
	private long _lastAttackTime;
	private L2Zone _zone;

	public static SailrenManager getInstance()
	{
		if(_instance == null)
			_instance = new SailrenManager();
		return _instance;
	}

	public void onLoad()
	{
		getInstance().init();
	}

	public void onReload()
	{
	}

	public void onShutdown()
	{
	}

	public void init()
	{
		if(_state.getState() == BossState.State.ALIVE)
		{
			_state.setState(BossState.State.NOTSPAWN);
			_state.update();
		}

		dummySpawn = createNewSpawn(32110, new Location(27222, -6783, -2000, 6655), 0);
		sailrenSpawn = createNewSpawn(29065, new Location(27222, -6783, -2000, 6655), 0);
		cubeSpawn = createNewSpawn(32107, new Location(27222, -6783, -2000, 6655), 0);

		_log.info(this + " State of Sailren is " + _state.getState() + ".");

		if(_state.getState() == BossState.State.DEAD)
		{
			deadEndTask();
			Date dt = new Date(_state.getRespawnDate());
			_log.info(this + " Next spawn date of Sailren is " + dt + ".");
		}

		_log.info(this + " Init SailrenManager.");
	}

	private void deadEndTask()
	{
		_log.info(this + " start Dead task");
		if(!_state.getState().equals(BossState.State.DEAD))
		{
			_state.setRespawnDate(36 * 60 * 60000 + Rnd.get(0, 24 * 60 * 60000 ));
			_state.setState(BossState.State.DEAD);
			_state.update();
		}

		_intervalEndTask = ThreadPoolManager.getInstance().scheduleGeneral(new IntervalEnd(), _state.getInterval());
	}

	private class IntervalEnd implements Runnable
	{
		public void run()
		{
			_state.setState(BossState.State.NOTSPAWN);
			_state.update();
		}
	}

	public BossState.State getState()
	{
		return _state.getState();
	}

	public synchronized void start()
	{
		if(_isStarted)
			return;

		if(_zone == null)
			_zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.no_restart, 4006);

		_log.info(this + " started");
		_isStarted = true;
		_stage = 1;
		_velKilled = 0;
		_lastAttackTime = System.currentTimeMillis();
		_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnTask(1), 60000);
		_activutyCheck = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new ActivityCheck(), 600000, 600000);
	}

	private class SpawnTask implements Runnable
	{
		private int stage;

		public SpawnTask(int stage)
		{
			this.stage = stage;
		}

		public synchronized void run()
		{
			try
			{
				switch(stage)
				{
					// Spawn 3 Velociraports
					case 1:
						L2NpcTemplate template = NpcTable.getTemplate(22197);
						vel1 = new SailrenMinion(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
						vel2 = new SailrenMinion(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
						vel3 = new SailrenMinion(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);

						vel1.setCurrentHpMp(vel1.getMaxHp(), vel1.getMaxMp());
						vel2.setCurrentHpMp(vel2.getMaxHp(), vel2.getMaxMp());
						vel3.setCurrentHpMp(vel3.getMaxHp(), vel3.getMaxMp());

						Location pos = new Location(27300, -6490, -2008, 54788);
						vel1.setHeading(pos.getHeading());
						vel1.spawnMe(pos);
						vel1.setSpawnedLoc(pos);
						vel1.onSpawn();

						pos = new Location(27300, -6580, -2005, 64134);
						vel2.setHeading(pos.getHeading());
						vel2.spawnMe(pos);
						vel2.setSpawnedLoc(pos);
						vel2.onSpawn();

						pos = new Location(27300, -6680, -2000, 64545);
						vel3.setHeading(pos.getHeading());
						vel3.spawnMe(pos);
						vel3.setSpawnedLoc(pos);
						vel3.onSpawn();
						break;
					// Spawn Pterosaur
					case 2:
						template = NpcTable.getTemplate(22199);
						pter = new SailrenMinion(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
						pter.setCurrentHpMp(pter.getMaxHp(), pter.getMaxMp());

						pos = new Location(27300, -6580, -2005, 64134);
						pter.setHeading(pos.getHeading());
						pter.spawnMe(pos);
						pter.setSpawnedLoc(pos);
						pter.onSpawn();
						break;
					// Spawn Tyranosaur
					case 3:
						template = NpcTable.getTemplate(22217);
						tyran = new SailrenMinion(IdFactory.getInstance().getNextId(), template, 0, 0, 0, 0);
						tyran.setCurrentHpMp(tyran.getMaxHp(), tyran.getMaxMp());

						pos = new Location(27300, -6580, -2005, 64134);
						tyran.setHeading(pos.getHeading());
						tyran.spawnMe(pos);
						tyran.setSpawnedLoc(pos);
						tyran.onSpawn();
						break;
					// Show Sailren spawn move
					case 4:
						dummy = dummySpawn.doSpawn(true);
						dummy.setIsInvul(true);
						dummy.setDisabled(true);

						_state.setState(BossState.State.ALIVE);
						_state.update();

						try
						{
							_zone.broadcastPacket(new SpecialCamera(dummy.getObjectId(), 120, 200, 30, 2000, 6000, 0, 50, true, false));
							wait(1000);
							_zone.broadcastPacket(new MagicSkillUse(dummy, dummy, 5090, 1, 1000, 0));
							wait(2000);
							_zone.broadcastPacket(new SpecialCamera(dummy.getObjectId(), 150, 290, 25, 2000, 6000, 0, 50, true, false));
							_zone.broadcastPacket(new MagicSkillUse(dummy, dummy, 5090, 1, 1000, 0));
							wait(2000);
							_zone.broadcastPacket(new SpecialCamera(dummy.getObjectId(), 180, 380, 25, 2000, 6000, 0, 40, true, false));
							_zone.broadcastPacket(new MagicSkillUse(dummy, dummy, 5090, 1, 1000, 0));
							wait(2000);
							_zone.broadcastPacket(new SpecialCamera(dummy.getObjectId(), 210, 470, 15, 2000, 6000, 0, 20, true, false));
							_zone.broadcastPacket(new MagicSkillUse(dummy, dummy, 5090, 1, 1000, 0));
							wait(1000);
							sailren = (L2BossInstance) sailrenSpawn.doSpawn(true);
							sailren.setIsInvul(true);
							sailren.setDisabled(true);
							wait(1000);
							_zone.broadcastPacket(new SpecialCamera(dummy.getObjectId(), 260, 530, 2, 2000, 6000, 0, 0, true, false));
							_zone.broadcastPacket(new MagicSkillUse(dummy, dummy, 5090, 1, 1000, 0));
							wait(2000);
							_zone.broadcastPacket(new MagicSkillUse(dummy, dummy, 5091, 1, 1000, 0));
							wait(2000);
							_zone.broadcastPacket(new SpecialCamera(dummy.getObjectId(), 160, 510, 10, 2000, 6000, 0, 5, true, false));
							wait(2000);

							dummy.deleteMe();
							dummy = null;
							sailren.setIsInvul(false);
							sailren.setDisabled(false);
						}
						catch(InterruptedException e)
						{
						}

						break;
				}
			}
			catch(NullPointerException e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean isStarted()
	{
		return _isStarted;
	}

	public void notifyDead()
	{
		if(_stage == 1)
		{
			_velKilled++;
			if(_velKilled > 2)
				_stage++;
			else
				return;
		}
		else
			_stage++;

		_spawnTask = ThreadPoolManager.getInstance().scheduleGeneral(new SpawnTask(_stage), 60000);
	}

	public void updateLastAttack()
	{
		_lastAttackTime = System.currentTimeMillis();
	}

	private class ActivityCheck implements Runnable
	{
		public void run()
		{
			_log.info(SailrenManager.this + " timeout check last attack: " + new Date(_lastAttackTime));
			if(_lastAttackTime + 600000 < System.currentTimeMillis())
			{
				_log.info(SailrenManager.this + " timeout shutdown.");
				_zone.telePlayers();

				if(vel1 != null)
					vel1.deleteMe();
				vel1 = null;

				if(vel2 != null)
					vel2.deleteMe();
				vel2 = null;

				if(vel3 != null)
					vel3.deleteMe();
				vel3 = null;

				if(pter != null)
					pter.deleteMe();
				pter = null;

				if(tyran != null)
					tyran.deleteMe();
				tyran = null;

				sailrenSpawn.despawnAll();

				if(sailren != null)
					sailren.deleteMe();

				sailren = null;
				
				if(_state.getState() == BossState.State.ALIVE)
				{
					_state.setState(BossState.State.NOTSPAWN);
					_state.update();
				}

				_isStarted = false;

				if(_activutyCheck != null)
					_activutyCheck.cancel(true);

				_activutyCheck = null;
			}
		}
	}

	private L2Spawn createNewSpawn(int templateId, Location loc, int respawnDelay)
	{
		L2Spawn spawn = null;

		L2NpcTemplate template;

		try
		{
			template = NpcTable.getTemplate(templateId);
			spawn = new L2Spawn(template);
			spawn.setLoc(loc);
			spawn.setAmount(1);
			spawn.setRespawnDelay(respawnDelay);
			spawn.stopRespawn();
		}

		catch(Exception e)
		{
			_log.warn(this + " can't create spawn id: " + templateId);
			_log.warn(e.getMessage());
		}

		return spawn;
	}

	public void sailrenKilled()
	{
		_log.info(this + " Sailren killed");

		// Cancel activity check
		if(_activutyCheck != null)
			_activutyCheck.cancel(true);

		_activutyCheck = null;
		// Start dead task and set respawn time
		deadEndTask();

		_isStarted = false;

		cube = cubeSpawn.doSpawn(true);

		// unspawn cube in 15 min
		ThreadPoolManager.getInstance().scheduleGeneral(new Runnable(){
			public void run()
			{
				if(cube != null)
					cube.deleteMe();
				cube = null;
				_zone.telePlayers();
			}
		}, 900000);
	}

	@Override
	public String toString()
	{
		return "SailrenManager:";
	}

}
