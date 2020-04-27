package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 26.08.2010 11:30:28
 */
public class TurkaChieftain extends FighterSummonPrivateAtDying
{
	private GArray<L2MonsterInstance> _minions;
	private long _nextMinionSpawnTime;
	private static final int MAX_MINIONS = 5;
	
	public TurkaChieftain(L2Character actor)
	{
		super(actor);
		_minions = new GArray<>(MAX_MINIONS);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(_minions.size() > 0)
			for(L2MonsterInstance minion : _minions)
				minion.deleteMe();
		_minions.clear();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_nextMinionSpawnTime == 0)
			_nextMinionSpawnTime = System.currentTimeMillis() + 2000;

		if(attacker.getPlayer() != null && _minions.size() < MAX_MINIONS && _nextMinionSpawnTime < System.currentTimeMillis())
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

		if(caster != null && caster.getPlayer() != null && _minions.size() < MAX_MINIONS && _thisActor.getHate(caster) > 0 && _nextMinionSpawnTime < System.currentTimeMillis())
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
		if(_minions.size() < MAX_MINIONS)
		{
			try
			{
				spawned = new L2MonsterInstance(IdFactory.getInstance().getNextId(), NpcTable.getTemplate(22706), 0, 0, 0, 0);
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
}
