package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 30.09.2009 14:23:27
 */
public class DungeonBossInstance extends L2RaidBossInstance
{
	private ScheduledFuture<?> _attackCheckTask;
	private long _lastAttack;
	private Instance _inst;

	public DungeonBossInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		_inst = getSpawn().getInstance();
		if(_inst == null)
			_log.warn(this + " has no instance WTF??");

		_lastAttack = System.currentTimeMillis();

		_attackCheckTask = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new AttackCheck(), 60000, 60000);
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		_lastAttack = System.currentTimeMillis();
		super.decreaseHp(damage, attacker, directHp, reflect);
	}

	@Override
	public void decayMe()
	{
		if(_attackCheckTask != null)
			_attackCheckTask.cancel(true);
		super.decayMe();
	}

	@Override
	public void doDie(L2Character killer)
	{
		if(_attackCheckTask != null)
			_attackCheckTask.cancel(true);
		super.doDie(killer);
	}

	@Override
	public void addDamageHate(L2Character attacker, long damage, long aggro)
	{
		if(damage > 0 && aggro == 0)
			aggro = damage;

		if(attacker == null)
			return;

		AggroInfo ai = getAggroList().get(attacker.getObjectId());

		if(ai != null)
		{
			ai.damage += damage;
			ai.hate += aggro;
			ai.level = attacker.getLevel();
			if(ai.getAttacker() != attacker)
				ai.setAttacker(attacker);
			if(ai.hate < 0)
				ai.hate = 0;
		}
		else if(aggro > 0)
		{
			ai = new AggroInfo(attacker);
			ai.damage = damage;
			ai.hate = aggro;
			getAggroList().put(attacker.getObjectId(), ai);
		}
	}

	private class AttackCheck implements Runnable
	{
		public void run()
		{
			if(_inst != null && _lastAttack + 600000 < System.currentTimeMillis())
				_inst.stopInstance();
		}
	}
}
