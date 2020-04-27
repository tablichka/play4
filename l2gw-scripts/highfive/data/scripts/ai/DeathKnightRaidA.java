package ai;

import ai.base.AntarasCaveRaidBasic;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.Die;
import ru.l2gw.gameserver.tables.SkillTable;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

/**
 * @author: rage
 * @date: 24.09.11 21:56
 */
public class DeathKnightRaidA extends AntarasCaveRaidBasic
{
	public int USE_SKILL_TIME = 2000;
	public int SPAWN_TIME = 20001;
	public L2Skill hold_skill = SkillTable.getInstance().getInfo(441581569);

	public DeathKnightRaidA(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 == 1000)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(c0 != null)
			{
				_thisActor.addDamageHate(c0, 0, 1);
				addUseSkillDesire(c0, hold_skill, 0, 1, 10000000000L);
			}
		}
		addTimer(USE_SKILL_TIME, 5000);
		addTimer(SPAWN_TIME, 15000);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster.isPlayer())
		{
			L2NpcInstance.AggroInfo h0 = _thisActor.getAggroList().get(caster.getObjectId());
			if(h0 == null)
			{
				_thisActor.addDamageHate(caster, 0, 1);
				addUseSkillDesire(caster, hold_skill, 0, 1, 10000000000L);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null && attacker.isPlayer())
		{
			L2NpcInstance.AggroInfo h0 = _thisActor.getAggroList().get(attacker.getObjectId());
			if(h0 == null)
			{
				_thisActor.addDamageHate(attacker, 0, 1);
				addUseSkillDesire(attacker, hold_skill, 0, 1, 10000000000L);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == USE_SKILL_TIME)
		{
			addTimer(USE_SKILL_TIME, 5000);
			L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
			if(h0 != null)
			{
				L2Character c0 = h0.getAttacker();
				if(c0 != null)
				{
					addUseSkillDesire(c0, hold_skill, 0, 1, 10000000000L);
				}
			}
		}
		else if(timerId == SPAWN_TIME)
		{
			if(_thisActor.isDead())
			{
				L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
				if(h0 != null)
				{
					L2Character c0 = h0.getAttacker();
					if(c0 != null)
					{
						_thisActor.createOnePrivate(underling1, ai_type1, 0, 0, _thisActor.getX() + Rnd.get(100), _thisActor.getY() + Rnd.get(100), _thisActor.getZ(), 0, 1000, getStoredIdFromCreature(c0), 0);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15008)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		stopAITask();

		_thisActor.stopHate();

		// 10 seconds timeout of ATTACK after respawn
		setGlobalAggro(getInt("global_aggro", -10));

		_thisActor.setAttackTimeout(Integer.MAX_VALUE);

		// Удаляем все задания
		clearTasks();

		_actor.breakAttack();
		_actor.breakCast(true, false);
		_actor.stopMove();
		_actor.broadcastPacket(new Die(_actor));
		_intention = AI_INTENTION_IDLE;
		setAttackTarget(null);
		debug = false;
		_useUD = false;
	}

	@Override
	protected void onEvtOutOfMyTerritory()
	{
	}
}