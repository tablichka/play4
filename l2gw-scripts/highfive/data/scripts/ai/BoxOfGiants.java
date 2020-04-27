package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2Summon;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 31.08.2010 21:36:13
 */
public class BoxOfGiants extends DefaultAI
{
	private static final L2Skill _debuff = SkillTable.getInstance().getInfo(6033, 1);
	private GArray<L2MonsterInstance> _minions;
	private long _despawnTime;
	
	public BoxOfGiants(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
		_minions = new GArray<L2MonsterInstance>(4);
		_despawnTime = System.currentTimeMillis() + 600000;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_despawnTime < System.currentTimeMillis())
		{
			if(_minions.size() > 0)
				for(L2MonsterInstance minion : _minions)
					minion.deleteMe();

			_thisActor.deleteMe();
			return true;
		}

		return super.thinkActive();
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_despawnTime = System.currentTimeMillis() + 600000;

		if(_minions.size() > 0)
			for(L2MonsterInstance minion : _minions)
				minion.deleteMe();
		_minions.clear();

		for(int i = 0; i < 4; i++)
			try
			{
				L2MonsterInstance spawned = new L2MonsterInstance(IdFactory.getInstance().getNextId(), NpcTable.getTemplate(i < 2 ? 18694 : 18695), 0, 0, 0, 0);
				spawned.setCurrentHp(spawned.getMaxHp());
				spawned.setCurrentMp(spawned.getMaxMp());
				Location loc = Util.getPointInRadius(_thisActor.getLoc(), 100, (int) (Util.convertHeadingToDegree(_thisActor.getHeading()) + 90 * i));
				spawned.setSpawnedLoc(loc);
				spawned.spawnMe(loc);
				spawned.onSpawn();
				_minions.add(spawned);
			}
			catch(Exception e)
			{
				_log.warn(_thisActor + " can't spawn minion: " + e);
			}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null)
		{
			if(attacker.isPlayer())
			{
				_debuff.applyEffects(attacker, attacker, false);
				for(L2NpcInstance npc : _thisActor.getKnownNpc(2000, 300))
					if(!npc.isDead() && !npc.isRaid() && !npc.isMinion())
					{
						npc.addDamageHate(attacker, 0, 10);
						if(npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE)
							npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
					}
			}
			else if(attacker instanceof L2Summon)
				return;
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(attacker != null)
		{
			if(attacker.isPlayer())
			{
				_debuff.applyEffects(attacker, attacker, false);
				for(L2NpcInstance npc : _thisActor.getKnownNpc(2000, 300))
					if(!npc.isDead() && !npc.isRaid() && !npc.isMinion())
					{
						npc.addDamageHate(attacker, 0, 10);
						if(npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE)
							npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
					}
			}
			else if(attacker instanceof L2Summon)
				return;
		}

		super.onEvtAggression(attacker, aggro, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		for(L2MonsterInstance minion : _minions)
			minion.deleteMe();

		_minions.clear();
		if(killer != null)
			_thisActor.dropItem(killer.getPlayer(), 13799, Rnd.get(1, (int) Config.RATE_DROP_ITEMS));
	}
}
