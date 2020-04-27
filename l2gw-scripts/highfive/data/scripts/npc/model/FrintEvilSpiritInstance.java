package npc.model;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.gameserver.templates.L2NpcTemplate;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 10.09.2009 10:08:11
 */
public class FrintEvilSpiritInstance extends L2MonsterInstance
{
	private L2MonsterInstance _monster;
	private ScheduledFuture<?> _spawner;
	private Location _monsterLoc;
	private int _monsterId = 0;
	private int _spawnDelay = 30000;

	public FrintEvilSpiritInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		_spawner = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new DemonSpawner(), 90000, _spawnDelay);
		try
		{
			_monsterId = getAIParams().getInteger("spawn_monster_id", 0);
		}
		catch(Exception e)
		{ }

		if(_monsterId == 0)
		{
			_log.warn(this + " no spawn_monster_id ai_param!");
			return;
		}

		_monsterLoc = Util.getPointInRadius(getLoc(), 100, (int)Util.convertHeadingToDegree(getHeading()));

		_monster = (L2MonsterInstance) getSpawn().getInstance().addSpawn(_monsterId, _monsterLoc, 0);
		_monster.setImobilised(true);
		_monster.getAI().setGlobalAggro(-50);
		_monster.broadcastPacket(new SocialAction(_monster.getObjectId(), 1));
		ThreadPoolManager.getInstance().scheduleGeneral(new SocialTask(), 47000);
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

	private class DemonSpawner implements Runnable
	{
		public void run()
		{
			getSpawn().getInstance().addSpawn(_monsterId, Location.coordsRandomize(_monsterLoc, 50), 0);
			if(FrintEvilSpiritInstance.this.getEffectBySkillId(5008) != null && FrintEvilSpiritInstance.this.getEffectBySkillId(5008).getSkill().getLevel() == 2 && _spawnDelay != 20000)
			{
				_spawnDelay = 20000;
				_spawner.cancel(false);
				_spawner = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new DemonSpawner(), 90000, _spawnDelay);
			}
			else if(_spawnDelay != 30000)
			{
				_spawnDelay = 30000;
				_spawner.cancel(false);
				_spawner = ThreadPoolManager.getInstance().scheduleGeneralAtFixedRate(new DemonSpawner(), 90000, _spawnDelay);
			}
		}
	}

	private class SocialTask implements Runnable
	{
		public void run()
		{
			if(_monster != null)
			{
				_monster.setImobilised(false);
			}
		}
	}
}
