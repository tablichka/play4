package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 31.08.2009 11:10:32
 */
public class Kama63MinionInstance extends L2MonsterInstance
{
	private ScheduledFuture _healBossTask;

	public Kama63MinionInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		super.onSpawn();
		_healBossTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new HealBoss(), 10000, 10000);
	}

	@Override
	public void decayMe()
	{
		if(_healBossTask != null)
			_healBossTask.cancel(true);
		super.decayMe();
	}

	@Override
	public void doDie(L2Character killer)
	{
		if(_healBossTask != null)
			_healBossTask.cancel(true);
		super.doDie(killer);
	}

	private class HealBoss implements Runnable
	{
		public void run()
		{
			if(Rnd.chance(50) && getLeader() != null && !getLeader().isDead() && getLeader().getCurrentHp() < getLeader().getMaxHp())
			{
				getLeader().setCurrentHp(getLeader().getMaxHp());
				Kama63MinionInstance.this.doDie(null);
			}
		}
	}
}
