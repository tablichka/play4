package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.ExShowScreenMessage;
import ru.l2gw.gameserver.serverpackets.ExStartScenePlayer;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 20.10.2010 16:01:52
 */
public class SoDTiat extends Fighter
{
	private long _lastAttackTime = 0;
	private static final L2Skill _transformSkill = SkillTable.getInstance().getInfo(5974, 1);
	private boolean _transformCast = true;
	private GArray<L2Spawn> _movingDevices;
	private boolean _secondWaveSpawned = false;
	private int _reflection;
	private static final L2Zone _zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.dummy, 4007);

	public SoDTiat(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.getSpawn().setLocation(84100);
		addTimer(1, 300000); // Check Timer
		_reflection = _thisActor.getReflection();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_lastAttackTime = System.currentTimeMillis();
		if(attacker != null)
			for(L2NpcInstance npc : _thisActor.getKnownNpc(3000))
				if(npc.getNpcId() == 29162 && !npc.isDead())
					npc.addDamageHate(attacker, 0, (int)(damage * 0.7));

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		_lastAttackTime = System.currentTimeMillis();
		super.onEvtAggression(attacker, aggro, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		Instance inst = _thisActor.getSpawn().getInstance();
		if(inst != null)
			for(L2Spawn spawn : inst.getInstanceSpawns())
				if(spawn != null)
					spawn.despawnAll();

		if(_movingDevices != null)
			for(L2Spawn spawn : _movingDevices)
				if(spawn != null)
					spawn.despawnAll();

		ThreadPoolManager.getInstance().scheduleAi(new Runnable()
		{
			public void run()
			{
				for(L2Player player : _zone.getPlayers())
					if(player != null && player.getReflection() == _reflection)
					{
						if(Config.DEBUG_INSTANCES)
							Instance._log.info(player + " start scene play: " + ExStartScenePlayer.SCENE_TIAT_SUCCESS);
						player.showQuestMovie(ExStartScenePlayer.SCENE_TIAT_SUCCESS);
					}
			}
		}, 1000, false);
	}

	@Override
	protected boolean createNewTask()
	{
		if(_transformCast && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5)
		{
			_transformCast = false;
			clearTasks();
			_thisActor.abortCast();

			addUseSkillDesire(_thisActor, _transformSkill, 1, 1, DEFAULT_DESIRE * 1000);
			_movingDevices = SpawnTable.getInstance().getEventSpawn("sod_mdt", _thisActor.getSpawn().getInstance());

			for(L2Spawn spawn : _movingDevices)
				spawn.init();

			_thisActor.broadcastPacket(new ExShowScreenMessage(3000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, false, 1800297));
			_thisActor.setImobilised(false);
			return true;
		}

		return super.createNewTask();
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1)
		{
			if(!_secondWaveSpawned)
			{
				_secondWaveSpawned = true;
				_thisActor.broadcastPacket(new ExShowScreenMessage(3000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, false, 1800706));
			}
		}
		else if(eventId == 2)
		{
			boolean allDead = true;
			if(_movingDevices != null)
				for(L2Spawn spawn : _movingDevices)
					if(!spawn.getLastSpawn().isDead())
					{
						allDead = false;
						break;
					}

			if(allDead)
				_thisActor.broadcastPacket(new ExShowScreenMessage(3000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, false, false, 1800707));
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1 && !_thisActor.isDead())
		{
			if(_lastAttackTime > 0)
			{
				Instance inst = _thisActor.getSpawn().getInstance();
				if(inst == null)
				{
					_log.warn(_thisActor + " has no instance!!");
					return;
				}

				if(_lastAttackTime + 600000 < System.currentTimeMillis())
				{
					for(L2Spawn spawn : inst.getInstanceSpawns())
						spawn.despawnAll();

					if(_movingDevices != null)
						for(L2Spawn spawn : _movingDevices)
							spawn.despawnAll();

					inst.rescheduleEndTask(300);
					ThreadPoolManager.getInstance().scheduleAi(new Runnable()
					{
						public void run()
						{
							for(L2Player player : _zone.getPlayers())
								if(player.getReflection() == _reflection)
									player.showQuestMovie(ExStartScenePlayer.SCENE_TIAT_FAIL);
						}
					}, 1000, false);
					return;
				}
				else
				{
					boolean allDead = true;
					for(L2Player player : inst.getPlayersInside())
						if(!player.isDead())
						{
							allDead = false;
							break;
						}

					if(allDead)
					{
						for(L2Spawn spawn : inst.getInstanceSpawns())
							spawn.despawnAll();

						if(_movingDevices != null)
							for(L2Spawn spawn : _movingDevices)
								spawn.despawnAll();

						inst.rescheduleEndTask(300);

						ThreadPoolManager.getInstance().scheduleAi(new Runnable()
						{
							public void run()
							{
								for(L2Player player : _zone.getPlayers())
									if(player.getReflection() == _reflection)
										player.showQuestMovie(ExStartScenePlayer.SCENE_TIAT_FAIL);
							}
						}, 1000, false);
						return;
					}
				}
			}

			addTimer(1, 300000);
		}
		else if(timerId == 2)
		{
			for(L2Player player : _zone.getPlayers())
				if(player.getReflection() == _reflection)
					player.showQuestMovie(ExStartScenePlayer.SCENE_TIAT_FAIL);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}