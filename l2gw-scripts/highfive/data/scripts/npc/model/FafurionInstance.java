package npc.model;

import javolution.util.FastList;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.gameserver.skills.funcs.FuncAdd;
import ru.l2gw.gameserver.tables.ItemTable;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.templates.L2NpcTemplate;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author rage
 * @date 21.10.2009 14:37:37
 */
public class FafurionInstance extends L2MonsterInstance
{
	private List<L2Spawn> _spawns;
	private static int DETRACTOR1 = 22270;
	private static int DETRACTOR2 = 22271;
	private static int SCALE_ID = 9691;
	private static int CLAW_ID = 9700;
	private ScheduledFuture<?> _deadTask;
	private ScheduledFuture<?> _liveTask;

	public FafurionInstance(int objectId, L2NpcTemplate template, long bossIndex, long p1, long p2, long p3)
	{
		super(objectId, template, bossIndex, p1, p2, p3);
	}

	@Override
	public void onSpawn()
	{
		_spawns = new FastList<L2Spawn>();
		addStatFunc(new FuncAdd(Stats.BLOCK_HEAL, 0x40, this, 1));
		
		_liveTask = ThreadPoolManager.getInstance().scheduleAi(new LiveTask(), Rnd.get(5 * 60000, 7 * 60000), false);
		_deadTask = ThreadPoolManager.getInstance().scheduleAiAtFixedRate(new DeadTask(), 5000, 5000);

		for(int i = 0; i < 5; i++)
		{
			try
			{
				L2Spawn spawn = new L2Spawn(NpcTable.getTemplate(Rnd.chance(50) ? DETRACTOR1 : DETRACTOR2));
				spawn.setLoc(GeoEngine.findPointToStay(getX(), getY(), getZ(), 100, 120, getReflection()));
				spawn.setReflection(getReflection());
				spawn.setAmount(1);
				spawn.setRespawnDelay(30);
				spawn.init();
				_spawns.add(spawn);
			}
			catch(Exception e)
			{
				_log.warn(this + " spawn error: " + e);
				e.printStackTrace();
			}
		}
		startAbnormalEffect(L2Skill.AbnormalVisualEffect.poison);
	}

	@Override
	public void deleteMe()
	{
		for(L2Spawn spawn : _spawns)
			spawn.stopRespawn();

		if(_deadTask != null)
			_deadTask.cancel(true);

		if(_liveTask != null)
			_liveTask.cancel(true);

		super.deleteMe();
	}

	@Override
	public void doDie(L2Character killer)
	{
		for(L2Spawn spawn : _spawns)
			spawn.stopRespawn();

		if(_deadTask != null)
			_deadTask.cancel(true);

		if(_liveTask != null)
			_liveTask.cancel(true);

		super.doDie(killer);
	}

	private class LiveTask implements Runnable
	{
		public void run()
		{
			if(!FafurionInstance.this.isDead())
			{
				FafurionInstance fafurion = FafurionInstance.this;
				if(Rnd.chance(20))
				{
					L2ItemInstance item = ItemTable.getInstance().createItem("Loot", CLAW_ID, 1, null, fafurion);
					item.dropToTheGround(null, fafurion);
				}
				if(Rnd.chance(50))
				{
					L2ItemInstance item = ItemTable.getInstance().createItem("Loot", SCALE_ID, 1, null, fafurion);
					item.dropToTheGround(null, fafurion);
				}
				fafurion.decayMe();
			}
		}
	}

	private class DeadTask implements Runnable
	{
		public void run()
		{
			FafurionInstance fafurion = FafurionInstance.this;
			if(!fafurion.isDead())
				fafurion.decreaseHp(380, fafurion, true, false);
		}
	}

	@Override
	public boolean hasRandomWalk()
	{
		return false;
	}
}
