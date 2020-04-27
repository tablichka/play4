package npc.model;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.concurrent.ScheduledFuture;


public class Kama43BossInstance extends L2RaidBossInstance
{
	private ScheduledFuture _spawner;
	private int MINION_ID = 18563;

	public Kama43BossInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
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
		private Kama43BossInstance _boss;
		private int _minionId;

		public MinionSpawner(Kama43BossInstance boss, int minionId)
		{
			_boss = boss;
			_minionId = minionId;
		}

		public void run()
		{
			try
			{
				if(!_boss.isDead() && _boss.getMinionList().countSpawnedMinions() < 8)
				{
					_boss.getMinionList().spawnSingleMinion(_minionId, null, 0, null);
					Functions.npcSayCustom(_boss, Say2C.ALL, "Kama26Boss.helpme", null);
				}
			}
			catch(Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void decreaseHp(double damage, L2Character attacker, boolean directHp, boolean reflect)
	{
		if(getCurrentHp() < getMaxHp() * 0.6 && _spawner == null)
			_spawner = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new MinionSpawner(this, MINION_ID), 15000, 30000);
		super.decreaseHp(damage, attacker, directHp, reflect);
	}
}