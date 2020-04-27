package ai;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.commons.math.Rnd;

import java.lang.ref.WeakReference;

/**
 * @author rage
 * @date 18.08.2010 15:07:14
 */
public class FighterSummonPrivateAtDying extends Fighter
{
	private final int _summonChance;
	private final GArray<PrivateInfo> _privates;
	protected boolean _aggroOnKiller;

	public FighterSummonPrivateAtDying(L2Character actor)
	{
		super(actor);
		_aggroOnKiller = getBool("aggro_on_killer", true);
		_summonChance = getInt("summon_private_rate", 0);
		_privates = new GArray<PrivateInfo>();

		String privates = getString("privates", "");
		if(!privates.isEmpty())
		{
			for(String privat : privates.split(";"))
				if(!privat.isEmpty())
				{
					try
					{
						String[] priv = privat.split(",");
						_privates.add(new PrivateInfo(Integer.parseInt(priv[0]), Integer.parseInt(priv[1]), Integer.parseInt(priv[2])));
					}
					catch(Exception e)
					{
						_log.warn(_thisActor + " can't parse private: " + privat + " " + e);
					}
				}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(Rnd.chance(_summonChance))
			for(PrivateInfo pi : _privates)
				if(pi.spawnDelay > 0)
					ThreadPoolManager.getInstance().scheduleAi(new SpawnDelayTask(pi, killer), pi.spawnDelay, false);
				else
					spawnPrivate(pi, killer);
	}

	private void spawnPrivate(PrivateInfo pi, L2Character killer)
	{
		pi.spawn.despawnAll();
		pi.spawn.setLoc(GeoEngine.findPointToStay(_thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 50, 100, _thisActor.getReflection()));
		pi.spawn.setReflection(_thisActor.getReflection());
		pi.spawn.init();
		pi.spawn.stopRespawn();
		if(killer != null && _aggroOnKiller)
			for(L2NpcInstance npc : pi.spawn.getAllSpawned())
			{
				npc.addDamageHate(killer, 0, 999);
				npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, killer);
			}
	}

	private class SpawnDelayTask implements Runnable
	{
		private final WeakReference<L2Character> _killer;
		private final PrivateInfo _pi;

		public SpawnDelayTask(PrivateInfo pi, L2Character killer)
		{
			_pi = pi;
			_killer = new WeakReference<L2Character>(killer);
		}

		public void run()
		{
			spawnPrivate(_pi, _killer.get());
		}
	}

	private class PrivateInfo
	{
		public long spawnDelay;
		public L2Spawn spawn;

		public PrivateInfo(int id, int c, int sd)
		{
			spawnDelay = sd * 1000L;
			try
			{
				spawn = new L2Spawn(NpcTable.getTemplate(id));
				spawn.setAmount(c);
				spawn.stopRespawn();
			}
			catch(Exception e)
			{
				_log.warn(_thisActor + " can't create spawn for npcId: " + id + " " + e);
			}
		}
	}
}
