package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Balanced;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.ValidateLocation;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 17.08.2010 16:44:39
 */
public class DarkShamanVarangka extends Balanced
{
	private long _nextMinionSpawn;
	private GArray<L2MonsterInstance> _minions;
	private static final int[] _minionsId = { 18809, 18810, 18836 };
	private L2NpcInstance _guard;
	private long _nextJamp;

	public DarkShamanVarangka(L2Character actor)
	{
		super(actor);
		_minions = new GArray<L2MonsterInstance>();
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		checkMinionSpawn();
		_nextJamp = System.currentTimeMillis() + Rnd.get(30, 90) * 1000;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(_guard != null)
		{
			if(killer != null)
				_guard.addDamage(killer, _guard.getMaxHp());
			_guard.doDie(killer);
		}

		_guard = null;
	}

	@Override
	public void stopAITask()
	{
		super.stopAITask();
		for(L2MonsterInstance minion : _minions)
			minion.deleteMe();

		_minions.clear();
	}


	@Override
	protected boolean thinkActive()
	{
		if(!_thisActor.isDead())
			checkMinionSpawn();
		return super.thinkActive();
	}

	@Override
	protected void thinkAttack()
	{
		if(!_thisActor.isDead())
			checkMinionSpawn();

		super.thinkAttack();
	}

	@Override
	protected boolean createNewTask()
	{
		if(_nextJamp < System.currentTimeMillis())
		{
			_nextJamp = System.currentTimeMillis() + Rnd.get(30, 90) * 1000;
			_thisActor.setXYZ(Rnd.get(73544, 74772), Rnd.get(-102426, -101418), -960, false);
			_thisActor.broadcastPacket(new ValidateLocation(_thisActor));
		}

		return super.createNewTask();
	}

	public void setGuard(L2NpcInstance monster)
	{
		_guard = monster;
	}

	private void checkMinionSpawn()
	{
		if(_thisActor.isDead())
			return;

		if(_nextMinionSpawn < System.currentTimeMillis())
		{
			boolean canSpawn = true;
			for(L2MonsterInstance minion : _minions)
				if(!minion.isDead())
				{
					canSpawn = false;
					break;
				}

			if(canSpawn)
			{
				for(L2MonsterInstance minion : _minions)
					minion.deleteMe();

				_minions.clear();
				_nextMinionSpawn = System.currentTimeMillis() + 60000;

				L2Character attacker = _thisActor.getMostHated();
				try
				{
					L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(_minionsId[Rnd.get(_minionsId.length)]));
					spawn.setLoc(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 50, 100, _thisActor.getReflection()));
					spawn.setAmount(1);
					spawn.stopRespawn();
					L2MonsterInstance monster = (L2MonsterInstance) spawn.spawnOne();
					monster.addDamageHate(attacker, 0, 999);
					monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
					_minions.add(monster);

					spawn = new L2Spawn(NpcTable.getTemplate(_minionsId[Rnd.get(_minionsId.length)]));
					spawn.setLoc(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 50, 100, _thisActor.getReflection()));
					spawn.setAmount(1);
					spawn.stopRespawn();
					monster = (L2MonsterInstance) spawn.spawnOne();
					monster.addDamageHate(attacker, 0, 999);
					monster.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
					_minions.add(monster);
				}
				catch(Exception e)
				{
					_log.warn(_thisActor + " can't spawn Kasha Eye! " + e);
				}
			}
			else
				_nextMinionSpawn = System.currentTimeMillis() + 30000;
		}
	}
}
