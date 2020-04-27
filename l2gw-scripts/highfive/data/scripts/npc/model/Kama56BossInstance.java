package npc.model;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 27.08.2009 10:16:56
 */
public class Kama56BossInstance extends L2RaidBossInstance
{
	private ScheduledFuture _spawner;
	private int MINION_ID = 18569;

	public Kama56BossInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		_spawner = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new MinionSpawner(this, MINION_ID), 180000, 180000);
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
		private Kama56BossInstance _boss;
		private int _minionId;

		public MinionSpawner(Kama56BossInstance boss, int minionId)
		{
			_boss = boss;
			_minionId = minionId;
		}

		public void run()
		{
			try
			{
				if(!_boss.isDead() && _boss.getMinionList().countSpawnedMinions() < 10 && Rnd.chance(50))
					_boss.getMinionList().spawnSingleMinion(_minionId, null, 0, null);
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
}
