package ai;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.base.ClassId;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 24.08.2010 20:01:32
 */
public class GraveRobberSummoner extends Mystic
{
	private static int[][] _minionIds = {{22683, 22684}, {22685, 22686}};
	private GCSArray<L2MonsterInstance> _minions;
	private int[] _currentIds;
	private long _nextMinionSpawnTime;
	private long _nextMinionCheck;

	public GraveRobberSummoner(L2Character actor)
	{
		super(actor);
		_minions = new GCSArray<>(3);
		if(_thisActor.getNpcId() == 22678)
			_currentIds = _minionIds[0];
		else if(_thisActor.getNpcId() == 22679)
			_currentIds = _minionIds[1];
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(_minions.size() > 0)
			for(L2MonsterInstance minion : _minions)
				minion.deleteMe();
		_minions.clear();

		spawnMinion();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_nextMinionSpawnTime == 0)
			_nextMinionSpawnTime = System.currentTimeMillis() + 2000;

		if(attacker.getPlayer() != null && _minions.size() < 3 && _nextMinionSpawnTime < System.currentTimeMillis() && isLongRangeAttacker(attacker.getPlayer()))
		{
			_nextMinionSpawnTime = System.currentTimeMillis() + Rnd.get(10, 30) * 1000;
			L2MonsterInstance minion = spawnMinion();
			minion.addDamageHate(attacker, 0, damage);
			minion.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}

		for(L2MonsterInstance minion : _minions)
			if(!minion.isDead())
				minion.addDamageHate(attacker, 0, damage);

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(_nextMinionSpawnTime == 0)
			_nextMinionSpawnTime = System.currentTimeMillis() + 2000;

		if(caster != null && caster.getPlayer() != null && _minions.size() < 3 && isLongRangeAttacker(caster.getPlayer()) && _thisActor.getHate(caster) > 0 && _nextMinionSpawnTime < System.currentTimeMillis())
		{
			_nextMinionSpawnTime = System.currentTimeMillis() + Rnd.get(10, 30) * 1000;
			L2MonsterInstance minion = spawnMinion();
			minion.addDamageHate(caster, 0, 999);
			minion.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, caster);
		}
		super.onEvtSeeSpell(skill, caster);
	}

	private L2MonsterInstance spawnMinion()
	{
		L2MonsterInstance spawned = null;
		if(_minions.size() < 3)
		{
			try
			{
				spawned = new L2MonsterInstance(IdFactory.getInstance().getNextId(), NpcTable.getTemplate(_currentIds[Rnd.get(_currentIds.length)]), 0 ,0 , 0, 0);
				spawned.setLeader(_thisActor);
				spawned.setCurrentHp(spawned.getMaxHp());
				spawned.setCurrentMp(spawned.getMaxMp());
				spawned.spawnMe(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 50, 100, _thisActor.getReflection()));
				spawned.onSpawn();
				_minions.add(spawned);
			}
			catch(Exception e)
			{
				_log.warn(_thisActor + " can't spawn minion: " + e);
			}
		}
		return spawned;
	}

	private boolean isLongRangeAttacker(L2Player player)
	{
		if(player.isMageClass())
			return true;

		ClassId	classId = player.getClassId();
		if(classId.getLevel() == 4)
			classId = classId.getParent(player.getSex());

		return classId == ClassId.hawkeye || classId == ClassId.phantomRanger || classId == ClassId.moonlightSentinel || classId == ClassId.arbalester;
	}
}
