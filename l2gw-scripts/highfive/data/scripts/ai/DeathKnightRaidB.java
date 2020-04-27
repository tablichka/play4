package ai;

import ai.base.AntarasCaveRaidBasic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.Die;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_IDLE;

/**
 * @author: rage
 * @date: 24.09.11 22:11
 */
public class DeathKnightRaidB extends AntarasCaveRaidBasic
{
	public int USE_SKILL_TIME = 3000;
	public int SPAWN_TIME = 3001;
	public L2Skill fan_attack_skill = SkillTable.getInstance().getInfo(451477505);
	public L2Skill debuff_skill = SkillTable.getInstance().getInfo(451543041);

	public DeathKnightRaidB(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 == 1000)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) _thisActor.param2);
			if(c0 != null)
			{
				_thisActor.addDamageHate(c0, 0, 1);
				if(Rnd.get(5) == 1)
				{
					addUseSkillDesire(c0, fan_attack_skill, 0, 1, 10000000000L);
				}
				else
				{
					addUseSkillDesire(c0, debuff_skill, 0, 1, 10000000000L);
				}
			}
		}
		addTimer(USE_SKILL_TIME, 5000);
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
				if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
				{
					float f0 = DefaultHate;
					if(HateRace1 > -1)
					{
						if(HateRace1 == attacker.getPlayer().getRace().ordinal())
						{
							f0 += HateRace1Boost;
						}
					}
					if(HateRace2 > -1)
					{
						if(HateRace2 == attacker.getPlayer().getRace().ordinal())
						{
							f0 += HateRace2Boost;
						}
					}
					if(HateClass1 > -1)
					{
						if(attacker.getActiveClass() == HateClass1)
						{
							f0 += HateClass1Boost;
						}
					}
					if(HateClass2 > -1)
					{
						if(attacker.getActiveClass() == HateClass2)
						{
							f0 += HateClass2Boost;
						}
					}
					if(HateClass3 > -1)
					{
						if(attacker.getActiveClass() == HateClass3)
						{
							f0 += HateClass3Boost;
						}
					}
					if(HateClassGroup1 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup1, attacker.getActiveClass()))
						{
							f0 += HateClassGroup1Boost;
						}
					}
					if(HateClassGroup2 > -1)
					{
						if(CategoryManager.isInCategory(HateClassGroup2, attacker.getActiveClass()))
						{
							f0 += HateClassGroup2Boost;
						}
					}
					if((f0 + 1) < 0)
					{
						f0 = 0;
					}
					else
					{
						f0 += damage * (f0 + 1);
					}
					addAttackDesire(attacker, 1, (long) (f0 * 100));
				}
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
					if(Rnd.get(5) == 1)
					{
						addUseSkillDesire(c0, fan_attack_skill, 0, 1, 10000000000L);
					}
					else
					{
						addUseSkillDesire(c0, debuff_skill, 0, 1, 10000000000L);
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
