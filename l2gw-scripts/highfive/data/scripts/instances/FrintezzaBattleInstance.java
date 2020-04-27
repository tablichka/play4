package instances;

import npc.model.L2FrintezzaInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ru.l2gw.gameserver.Config;
import ru.l2gw.extensions.listeners.L2ZoneEnterLeaveListener;
import ru.l2gw.extensions.multilang.CustomMessage;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.entity.instance.InstanceTemplate;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.*;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.CountdownTimer;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: rage
 * @date: 11.09.11 21:30
 */
public class FrintezzaBattleInstance extends Instance
{
	private static Log _log = LogFactory.getLog("frintezza");
	private long _lastAttackTime = 0;
	private ScheduledFuture<?> _spawnWave1;
	private ScheduledFuture<?> _spawnWave2;
	private InvideTask _invideTask;
	private ScheduledFuture<?> _spawnFrintezza;
	private ScheduledFuture<?> _activutyCheck;
	private static final L2Zone _hallZone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.altered, 4005);
	private int[] _firstDoors = {17130042, 17130043, 17130045, 17130046};
	private L2DoorInstance _hallDoor;
	private L2RaidBossInstance weakScarlet, strongScarlet;
	private L2FrintezzaInstance frintezza;
	private L2Spawn frintezzaSpawn, weakScarletSpawn, strongScarletSpawn, scarletDummySpawn;
	private L2NpcInstance scarletDummy;
	private static ZoneListener zoneListener = new ZoneListener();

	static
	{
		_hallZone.getListenerEngine().addMethodInvokedListener(zoneListener);
	}

	public FrintezzaBattleInstance(InstanceTemplate template, int rId)
	{
		super(template, rId);
	}

	@Override
	public void startInstance()
	{
		super.startInstance();

		_hallDoor = getDoorById(17130046);
		if(_hallDoor == null)
			_log.warn(this + " no hall dooe id: 17130046");

		frintezzaSpawn = createNewSpawn(29045, new Location(-87780, -155086, -9080, 16384), 0);
		scarletDummySpawn = createNewSpawn(29053, new Location(-87785, -153304, -9176, 16384), 0);

		weakScarletSpawn = createNewSpawn(29046, new Location(-87785, -153304, -9176, 16384), 0);
		_hallZone.setActive(true, getReflection());
		_log.info(this + " Start Frintezza.");

		spawnEvent("frint_wave1");

		_spawnWave1 = ThreadPoolManager.getInstance().scheduleGeneral(new RoomSpawn("frint_wave2"), 30000);
		_spawnWave2 = ThreadPoolManager.getInstance().scheduleGeneral(new RoomSpawn("frint_wave3"), 60000);
		_invideTask = new InvideTask();
		_invideTask.startTimer();

		spawnEvent("frint_room2");
	}

	public L2Zone getHallZone()
	{
		return _hallZone;
	}

	public void hallDeviceDisabled()
	{
		for(int doorId : _firstDoors)
		{
			L2DoorInstance door = getDoorById(doorId);
			if(door == null)
			{
				_log.warn(this + ": no door id: " + doorId);
				continue;
			}

			if(!door.isOpen())
				door.openMe();
		}

		if(_spawnWave1 != null)
			_spawnWave1.cancel(true);
		if(_spawnWave2 != null)
			_spawnWave2.cancel(true);

		_spawnWave1 = null;
		_spawnWave2 = null;

		stopEventSpawn("frint_wave1", true);
		stopEventSpawn("frint_wave2", true);
		stopEventSpawn("frint_wave3", true);
	}

	public synchronized void spawnFrintezza()
	{
		if(_spawnFrintezza == null)
		{
			if(_invideTask != null)
			{
				_log.info("Cancel invide task");
				_invideTask.abortTimer(true);
				_invideTask = null;
			}
			else
				_log.warn("WTF _invideTask is null ?? " + _invideTask);

			_log.info("Enter to frintezza room");
			_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(new ShowSpawnMovie(), 120000);
		}
	}

	@Override
	public void stopInstance()
	{
		_log.info(this + " stop instance.");
		_hallZone.setActive(false, getReflection());

		if(_spawnWave1 != null)
			_spawnWave1.cancel(true);
		if(_spawnWave2 != null)
			_spawnWave2.cancel(true);
		if(_spawnFrintezza != null)
			_spawnFrintezza.cancel(true);

		_spawnWave1 = null;
		_spawnWave2 = null;
		_spawnFrintezza = null;

		stopEventSpawn("frint_wave1", true);
		stopEventSpawn("frint_wave2", true);
		stopEventSpawn("frint_wave3", true);
		stopEventSpawn("frint_room2", true);
		stopEventSpawn("frint_up", true);

		if(frintezza != null)
		{
			frintezza.deleteMe();
			frintezza = null;
		}

		if(weakScarlet != null)
		{
			weakScarlet.deleteMe();
			weakScarlet = null;
		}

		if(strongScarlet != null)
		{
			strongScarlet.deleteMe();
			strongScarlet = null;
		}

		_spawnFrintezza = null;

		if(_activutyCheck != null)
			_activutyCheck.cancel(true);

		_activutyCheck = null;

		super.stopInstance();
	}

	private void broadcastPacket(L2GameServerPacket packet)
	{
		for(L2Player player : getPlayersInside())
			player.sendPacket(packet);
	}

	private class RoomSpawn implements Runnable
	{
		private final String _event;

		public RoomSpawn(String event)
		{
			_event = event;
		}

		public void run()
		{
			spawnEvent(_event);
		}
	}

	private class InvideTask extends CountdownTimer
	{
		public InvideTask()
		{
			super("1800;1200;600;300;120;60;0", 2100);
		}

		public void onCheckpoint(long sec) throws Throwable
		{
			_log.info(FrintezzaBattleInstance.this + " InvideTask run: " + (sec / 60));

			broadcastPacket(new ExShowScreenMessage(new CustomMessage("frintInvideTime", Config.DEFAULT_LANG).addNumber(sec / 60).toString(), 10000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, false));
		}

		public void onFinish() throws Throwable
		{
			_log.info(FrintezzaBattleInstance.this + " teleport players.");
			for(L2Player player : getPlayersInside())
				player.teleToClosestTown();
			_log.info(FrintezzaBattleInstance.this + " run stop.");
			stopInstance();
		}

		public void onTimerAborted() throws Throwable
		{
			_log.info(FrintezzaBattleInstance.this + " InvideTask interrupted.");
		}
	}

	private class ShowSpawnMovie implements Runnable
	{
		private int stage = 0;

		public void run()
		{
			try
			{
				if(stage == 0)
				{
					_log.info(FrintezzaBattleInstance.this + " Show Spawn Move start stage: " + stage);
					_log.info(FrintezzaBattleInstance.this + " close door: " + _hallDoor);
					_hallDoor.closeMe();

					for(int doorId : _firstDoors)
					{
						L2DoorInstance door = getDoorById(doorId);
						if(door == null)
						{
							_log.warn(this + ": no door id: " + doorId);
							continue;
						}

						if(door.isOpen())
							door.closeMe();
					}

					for(int doorId = 17130061; doorId <= 17130070; doorId++)
					{
						L2DoorInstance door = getDoorById(doorId);
						if(door == null)
						{
							_log.warn(this + ": no door id: " + doorId);
							continue;
						}

						if(door.isOpen())
							door.closeMe();
					}

					frintezza = (L2FrintezzaInstance) frintezzaSpawn.doSpawn(true);
					frintezza.setImobilised(true);

					scarletDummy = scarletDummySpawn.doSpawn(true);
					scarletDummy.setIsInvul(true);
					scarletDummy.setImobilised(true);

					for(L2Character cha : _hallZone.getCharacters())
						if(cha instanceof L2Playable && cha.getReflection() == getReflection())
						{
							cha.stopMove();
							cha.abortAttack();
							cha.abortCast();
						}

					_log.info(FrintezzaBattleInstance.this + " Spawn Frintezza.");
					SpawnTable.getInstance().stopEventSpawn("frint_room2", true);
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 1800, 95, 20, 0, 4000, 15, 100, true, true), 5000);
					stage = 1;
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 1)
				{
					stage = 2;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 1500, 90, 0, 4000, 7000, 0, 25, true, false), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 4000);
				}
				else if(stage == 2)
				{
					stage = 3;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 150, 90, 0, 6000, 10000, 0, 0, true, false), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 7000);
				}
				else if(stage == 3)
				{
					stage = 4;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 20, 65, -13, 0, 8000, 10, 10, true, true), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 4)
				{
					stage = 5;
					frintezza.broadcastPacket(new SocialAction(frintezza.getObjectId(), 2), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 6000);
				}
				else if(stage == 5)
				{
					stage = 6;
					spawnEvent("frint_up");
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 3100, 75, 0, 0, 6000, -50, 0, true, true), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
				}
				else if(stage == 6)
				{
					stage = 7;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 170, 90, 0, 0, 5000, 0, 0, true, true), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 7)
				{
					stage = 8;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 170, 90, 40, 4000, 7000, 0, 0, true, true), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 4000);
				}
				else if(stage == 8)
				{
					stage = 9;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 50, 190, 40, 0, 9000, 0, 5, true, true), 5000);
					frintezza.broadcastPacket(new SocialAction(frintezza.getObjectId(), 3), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 6000);
				}
				else if(stage == 9)
				{
					stage = 10;
					frintezza.broadcastPacket(new MagicSkillUse(frintezza, frintezza, 5006, 1, 34000, 0, false), 5000);
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 500, 130, 50, 6000, 9000, 20, 0, true, false), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 6000);
				}
				else if(stage == 10)
				{
					stage = 11;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 2500, 95, 5, 10000, 13000, 0, 12, true, false), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 11)
				{
					stage = 12;
					frintezza.broadcastPacket(new MagicSkillUse(scarletDummy, scarletDummy, 5004, 1, 5000, 0, false), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 12)
				{
					stage = 13;
					frintezza.broadcastPacket(new SpecialCamera(scarletDummy.getObjectId(), 600, 150, 0, 0, 7000, -2, 35, true, true), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 2500);
				}
				else if(stage == 13)
				{
					stage = 14;
					weakScarlet = (L2RaidBossInstance) weakScarletSpawn.doSpawn(true);
					frintezza.setDemon(weakScarlet);
					weakScarlet.setDisabled(true);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 500);
				}
				else if(stage == 14)
				{
					stage = 15;
					frintezza.broadcastPacket(new SpecialCamera(scarletDummy.getObjectId(), 650, 150, 0, 2000, 6000, 0, 0, true, false), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
				}
				else if(stage == 15)
				{
					stage = 16;
					frintezza.broadcastPacket(new SpecialCamera(weakScarlet.getObjectId(), 20, 55, 5, 0, 7000, 5, 5, true, false), 5000);
					scarletDummy.deleteMe();
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 16)
				{
					stage = 17;
					frintezza.broadcastPacket(new SpecialCamera(weakScarlet.getObjectId(), 250, 90, 10, 1500, 5000, 0, 5, true, true), 5000);
					_spawnFrintezza = ThreadPoolManager.getInstance().scheduleGeneral(this, 5000);
				}
				else if(stage == 17)
				{
					weakScarlet.setDisabled(false);
					frintezza.setDisabled(false);
					_lastAttackTime = System.currentTimeMillis();
					_activutyCheck = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new ActivityCheck(), 600000, 600000);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
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
			spawn.setInstance(this);
			spawn.setReflection(getReflection());
			spawn.stopRespawn();
		}

		catch(Exception e)
		{
			_log.warn(this + " can't create spawn id: " + templateId);
			_log.warn(e.getMessage());
		}

		return spawn;
	}

	public void updateLastAttack()
	{
		_lastAttackTime = System.currentTimeMillis();
	}

	public void demonKilled()
	{
		_log.info("Demon 1 killed");
		ThreadPoolManager.getInstance().scheduleGeneral(new ShowSecondMorphMovie(), 1000);
	}

	public void showFirstMorph()
	{
		ThreadPoolManager.getInstance().scheduleGeneral(new ShowFirstMorphMovie(), 1000);
	}

	private class ShowFirstMorphMovie implements Runnable
	{
		private int stage = 0;

		public void run()
		{
			try
			{
				if(stage == 0)
				{
					weakScarlet.abortCast();
					weakScarlet.abortAttack();
					weakScarlet.getAI().setGlobalAggro(-20);
					weakScarlet.startAbnormalEffect(L2Skill.AbnormalVisualEffect.big_body);
					//weakScarlet.setXYZ(weakScarlet.getX(), weakScarlet.getY(), weakScarlet.getZ() + 30);

					for(L2Character cha : _hallZone.getCharacters())
						if(cha != null && cha != frintezza && cha.getReflection() == getReflection())
						{
							cha.stopMove();
							cha.abortAttack();
							cha.abortCast();
							cha.setDisabled(true);
							if(cha instanceof L2MonsterInstance)
								cha.getAI().setGlobalAggro(-20);
						}

					stage = 1;
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 1)
				{
					stage = 2;
					frintezza.setDisabled(true);
					frintezza.abortMelody();
					frintezza.doCast(SkillTable.getInstance().getInfo(5007, 1), frintezza, null, false);
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 450, 120, 10, 0, 5000, 5, 0, true, true), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 2)
				{
					stage = 3;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 30, 120, 10, 5000, 8000, -5, 0, true, false), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 5000);
				}
				else if(stage == 3)
				{
					stage = 4;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 20, 60, 10, 0, 5000, 0, 0, true, true), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 2000);
				}
				else if(stage == 4)
				{
					stage = 5;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 1800, 87, 15, 8500, 10000, 0, 13, true, false), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 11000);
				}
				else if(stage == 5)
				{
					stage = 6;
					frintezza.setDisabled(false);
					weakScarlet.startAbnormalEffect(L2Skill.AbnormalVisualEffect.big_body);
					frintezza.broadcastPacket(new MagicSkillUse(weakScarlet, weakScarlet, 5017, 1, 2000, 0, false), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 6)
				{
					frintezza.broadcastPacket(new ValidateLocation(weakScarlet), 5000);
					weakScarlet.setDisabled(false);
					weakScarlet.doCast(SkillTable.getInstance().getInfo(5018, 1), weakScarlet, null, false);
					for(L2Character cha : _hallZone.getCharacters())
						if(cha != null && cha != frintezza && cha.getReflection() == getReflection())
							cha.setDisabled(false);
				}

			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private class ShowSecondMorphMovie implements Runnable
	{
		private int stage = 0;

		public synchronized void run()
		{
			try
			{
				if(stage == 0)
				{
					stage = 1;
					strongScarletSpawn = createNewSpawn(29047, weakScarlet.getLoc(), 0);
					weakScarlet.abortCast();
					weakScarlet.abortAttack();

					for(L2Character cha : _hallZone.getCharacters())
						if(cha != null && cha != frintezza && cha.getReflection() == getReflection())
						{
							cha.stopMove();
							cha.abortAttack();
							cha.abortCast();
							cha.setDisabled(true);
							if(cha instanceof L2MonsterInstance)
								cha.getAI().setGlobalAggro(-20);
						}

					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 1)
				{
					stage = 2;
					frintezza.broadcastPacket(new SpecialCamera(weakScarlet.getObjectId(), 100, Util.calculateCameraAngle(weakScarlet) - 20, 5, 0, 5000, 0, 10, true, true), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 2)
				{
					stage = 3;
					frintezza.broadcastPacket(new SpecialCamera(weakScarlet.getObjectId(), 250, Util.calculateCameraAngle(weakScarlet) + 20, 35, 3000, 8000, 0, 0, true, false), 5000);
					frintezza.broadcastPacket(new SocialAction(weakScarlet.getObjectId(), 2), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
				}
				else if(stage == 3)
				{
					stage = 4;
					strongScarlet = (L2RaidBossInstance) strongScarletSpawn.doSpawn(true);
					strongScarlet.setDisabled(true);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 4)
				{
					stage = 5;
					frintezza.broadcastPacket(new SocialAction(strongScarlet.getObjectId(), 2), 5000);
					frintezza.broadcastPacket(new SpecialCamera(weakScarlet.getObjectId(), 350, Util.calculateCameraAngle(weakScarlet) + 70, 45, 5000, 6000, 0, 0, true, false), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 5)
				{
					stage = 6;
					weakScarlet.deleteMe();
					weakScarlet = null;
					frintezza.setDemon(strongScarlet);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 4000);
				}
				else if(stage == 6)
				{
					for(L2Character cha : _hallZone.getCharacters())
						if(cha != null && cha != frintezza && cha.getReflection() == getReflection())
							cha.setDisabled(false);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	public void demon2Killed()
	{
		_log.info(this + " demon 2 killed");
		successEnd();
		ThreadPoolManager.getInstance().scheduleGeneral(new ShowDeadMovie(), 1000);
	}

	private class ShowDeadMovie implements Runnable
	{
		private int stage = 0;

		public void run()
		{
			try
			{
				if(stage == 0)
				{
					stage = 1;
					frintezza.broadcastPacket(new SpecialCamera(strongScarlet.getObjectId(), 400, Util.calculateCameraAngle(strongScarlet) + 20, 80, 0, 1000, 0, 0, true, true), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 1)
				{
					stage = 2;
					frintezza.broadcastPacket(new SpecialCamera(strongScarlet.getObjectId(), 20, Util.calculateCameraAngle(strongScarlet) - 30, 80, 7000, 9000, 0, 0, true, false), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 6000);
				}
				else if(stage == 2)
				{
					stage = 3;
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 80, 120, 5, 0, 3000, 0, 0, true, true), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 1000);
				}
				else if(stage == 3)
				{
					stage = 4;
					frintezza.doDie(null);
					frintezza.broadcastPacket(new SpecialCamera(frintezza.getObjectId(), 80, 90, 5, 6000, 8000, 0, 0, true, false), 5000);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
				}
				else if(stage == 4)
				{
					stage = 5;
					ThreadPoolManager.getInstance().scheduleGeneral(this, 3000);
				}
				else if(stage == 5)
				{
					stage = 6;
					SpecialCamera sc = new SpecialCamera(frintezza.getObjectId(), 1500, 90, 15, 8000, 9000, 0, 10, true, false);
					for(L2Character cha : _hallZone.getCharacters())
						if(cha instanceof L2Player && cha.getReflection() == getReflection())
							cha.sendPacket(sc);
					ThreadPoolManager.getInstance().scheduleGeneral(this, 9000);
				}
				else if(stage == 6)
				{
					// Cancel activity check
					if(_activutyCheck != null)
						_activutyCheck.cancel(true);

					_activutyCheck = null;

					// Unspawn frintezza spawns
					stopEventSpawn("frint_up", true);

					// Delete all mobs in zone
					for(L2Character cha : _hallZone.getCharacters())
						if(cha instanceof L2MonsterInstance && cha.getReflection() == getReflection())
							cha.deleteMe();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private class ActivityCheck implements Runnable
	{
		public void run()
		{
			_log.info(FrintezzaBattleInstance.this + " timeout check last attack: " + new Date(_lastAttackTime));
			if(_lastAttackTime + 600000 < System.currentTimeMillis())
			{
				_log.info(FrintezzaBattleInstance.this + " timeout shutdown.");
				stopInstance();
			}
		}
	}

	private L2DoorInstance getDoorById(int doorId)
	{
		for(L2DoorInstance door : _doors)
			if(door.getDoorId() == doorId)
				return door;

		return null;
	}

	private static class ZoneListener extends L2ZoneEnterLeaveListener
	{
		@Override
		public void objectEntered(L2Zone zone, L2Character object)
		{
			Instance inst = null;
			if(object instanceof L2Player)
				inst = InstanceManager.getInstance().getInstanceByPlayer((L2Player) object);
			if(inst instanceof FrintezzaBattleInstance)
				((FrintezzaBattleInstance) inst).spawnFrintezza();
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

}
