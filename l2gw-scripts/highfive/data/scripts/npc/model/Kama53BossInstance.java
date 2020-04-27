package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.commons.math.Rnd;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 26.08.2009 16:39:36
 */
public class Kama53BossInstance extends L2RaidBossInstance
{
	private ScheduledFuture _spawner;
	private int MINION_ID = 18567;

	public Kama53BossInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		_spawner = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new MinionSpawner(this, MINION_ID), 300000, 120000);
		super.onSpawn();
	}

	@Override
	public void decayMe()
	{
		if(_spawner != null)
			_spawner.cancel(true);
		super.decayMe();
	}

	@Override
	public void doDie(L2Character killer)
	{
		if(_spawner != null)
			_spawner.cancel(true);
		super.doDie(killer);
	}

	public static class MinionSpawner implements Runnable
	{
		private Kama53BossInstance _boss;
		private int _minionId;

		public MinionSpawner(Kama53BossInstance boss, int minionId)
		{
			_boss = boss;
			_minionId = minionId;
		}

		public void run()
		{
			try
			{
				if(!_boss.isDead() && _boss.getMinionList().countSpawnedMinions() < 4 && Rnd.chance(30))
					_boss.getMinionList().spawnSingleMinion(_minionId, null, 0, null);
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
}