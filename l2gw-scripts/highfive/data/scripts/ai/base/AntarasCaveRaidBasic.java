package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GCSArray;
import ru.l2gw.util.Util;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ATTACK;

/**
 * @author: rage
 * @date: 24.09.11 18:02
 */
public class AntarasCaveRaidBasic extends DefaultAI
{
	public int NON_TANKER_ATK_TIME = 1000;
	public int SPAWN_HOLD_MON = 1001;
	public int R_U_IN_BATTLE = 1004;
	public int CORPSE_TIME = 1005;
	public String AreaName = "";
	public L2Skill selfheal_skill = SkillTable.getInstance().getInfo(451280897);
	public L2Skill notank_skill_a = SkillTable.getInstance().getInfo(451346433);
	public L2Skill notank_skill_b = SkillTable.getInstance().getInfo(451411969);
	public L2Skill Skill01_ID = SkillTable.getInstance().getInfo(458752001);
	public L2Skill Skill02_ID = SkillTable.getInstance().getInfo(458752001);
	public L2Skill different_level_9_attacked = SkillTable.getInstance().getInfo(295895041);
	public L2Skill different_level_9_see_spelled = SkillTable.getInstance().getInfo(276234241);
	public float DefaultHate = 100.000000f;
	public float Maximum_Hate = 999999984306749440.000000f;
	public int HateRace1 = -1;
	public float HateRace1Boost = 0.000000f;
	public int HateRace2 = -1;
	public float HateRace2Boost = 0.000000f;
	public int HateClass1 = -1;
	public float HateClass1Boost = 0.000000f;
	public int HateClass2 = -1;
	public float HateClass2Boost = 0.000000f;
	public int HateClass3 = -1;
	public float HateClass3Boost = 0.000000f;
	public int HateClassGroup1 = 5;
	public float HateClassGroup1Boost = 80.000000f;
	public int HateClassGroup2 = 4;
	public float HateClassGroup2Boost = 40.000000f;
	public int FavorClassGroup1 = 3;
	public float FavorClassGroup1Boost = 20.000000f;
	public float ATTACKED_Weight_Point = 2.000000f;
	public float CLAN_ATTACKED_Weight_Point = 0.000000f;
	public float PARTY_ATTACKED_Weight_Point = 0.000000f;
	public float SEE_SPELL_Weight_Point = 1.000000f;
	public float HATE_SKILL_Weight_Point = 10000.000000f;
	public int SPAWN_HOLD_MON_TIME = 50;
	public int underling = 25728;
	public String ai_type = "DeathKnightRaidA";
	public int underling1 = 25729;
	public String ai_type1 = "DeathKnightRaidB";
	public int corpse = 20130;
	public String ai_corpse = "base.Warrior";
	public float Attack_DecayRatio = 6.600000f;
	public float UseSkill_DecayRatio = 66000.000000f;
	public float Attack_BoostValue = 300.000000f;
	public float UseSkill_BoostValue = 100000.000000f;
	protected GCSArray<Long> attackers;

	public AntarasCaveRaidBasic(L2Character actor)
	{
		super(actor);
		attackers = new GCSArray<>();
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai5 = 0;
		_thisActor.i_ai6 = 0;
		_thisActor.i_ai8 = 0;
		attackers.clear();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer())
		{
			_thisActor.addDamageHate(creature, 0, 1);
			if(_thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
				addTimer(NON_TANKER_ATK_TIME, 20000);
				addTimer(R_U_IN_BATTLE, 3000);
				if(_thisActor.i_ai8 == 0)
				{
					_thisActor.i_ai8 = 1;
					addTimer(SPAWN_HOLD_MON, (SPAWN_HOLD_MON_TIME + Rnd.get(20)) * 1000);
				}
			}
			if(!_thisActor.isDead())
				addAttackDesire(creature, 1, 1000);
		}
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster.isPlayer())
		{
			_thisActor.addDamageHate(caster, 0, 1);
			addAttackDesire(caster, 1, 1000);
			if(Rnd.get(2000) < 2)
			{
				if(CategoryManager.isInCategory(112, caster.getActiveClass()))
				{
					addUseSkillDesire(caster, Skill01_ID, 0, 1, 10000000000L);
				}
				else
				{
					addUseSkillDesire(caster, Skill02_ID, 0, 1, 10000000000L);
				}
			}
			if(caster.getLevel() > _thisActor.getLevel() + 8)
			{
				if(caster.getAbnormalLevelByType(different_level_9_see_spelled.getId()) == -1)
				{
					if(different_level_9_see_spelled.getId() == 4515)
					{
						_thisActor.altUseSkill(different_level_9_see_spelled, caster);
						removeAttackDesire(caster);
					}
					else
					{
						_thisActor.altUseSkill(different_level_9_see_spelled, caster);
					}
				}
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer())
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
				if(f0 + 1 < 0)
				{
					f0 = 0;
				}
				else
				{
					f0 = damage * (f0 + 1);
				}
				addAttackDesire(attacker, 1, (long) (f0 * 100));
			}
			if(Rnd.get(2000) < 2)
			{
				if(CategoryManager.isInCategory(112, attacker.getActiveClass()))
				{
					addUseSkillDesire(attacker, Skill01_ID, 0, 1, 10000000000L);
				}
				else
				{
					addUseSkillDesire(attacker, Skill02_ID, 0, 1, 10000000000L);
				}
			}

			if(attacker.getLevel() > (_thisActor.getLevel() + 8))
			{
				if(attacker.getAbnormalLevelByType(different_level_9_attacked.getId()) == -1)
				{
					if(different_level_9_attacked.getId() == 4515)
					{
						_thisActor.altUseSkill(different_level_9_attacked, attacker);
						removeAttackDesire(attacker);
						return;
					}
					else
					{
						_thisActor.altUseSkill(different_level_9_attacked, attacker);
					}
				}
			}
		}
		if(skill != null && skill.getId() == 985)
		{
			addUseSkillDesire(_thisActor, 453443585, 0, 1, 10000000000L);
			removeAllAttackDesire();
			addAttackDesire(attacker, 1, damage);
		}
		if(!attacker.isPlayer() && CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(attacker.getPlayer() != null && !attacker.getPlayer().isDead())
			{
				addAttackDesire(attacker.getPlayer(), 1, damage);
				_thisActor.addDamageHate(attacker.getPlayer(), 0, 1);
			}
		}
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		if(aggro > 0)
		{
			addAttackDesire(target, 1, (long) (aggro * HATE_SKILL_Weight_Point));
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == R_U_IN_BATTLE)
		{
			if(_thisActor.i_ai2 == 0)
			{
				_thisActor.removeAllHateInfoIF(1, 0);
				_thisActor.removeAllHateInfoIF(3, 1000);
				int i10 = _thisActor.getAggroListSize();
				if(i10 == 0)
				{
					removeAllAttackDesire();
					_thisActor.i_ai0 = 0;
					addMoveToDesire(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 10000000000L);
					if(selfheal_skill.getMpConsume() < _thisActor.getCurrentMp() && selfheal_skill.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(selfheal_skill.getId()))
					{
						addUseSkillDesire(_thisActor, selfheal_skill, 1, 1, 1000000);
					}
					broadcastScriptEvent(15008, 0, null, 5000);
					broadcastScriptEvent(15007, 0, null, 5000);

					ZoneManager.getInstance().areaSetOnOff(AreaName, 0);
					_thisActor.i_ai5 = 0;
					_thisActor.i_ai6 = 0;
				}
				if(_thisActor.i_ai0 == 1)
				{
					addTimer(R_U_IN_BATTLE, 3000);
				}
				return;
			}
			if(_thisActor.i_ai0 == 1)
			{
				addTimer(R_U_IN_BATTLE, 3000);
			}
		}
		else if(timerId == NON_TANKER_ATK_TIME)
		{
			if(_thisActor.i_ai0 == 1)
			{
				addTimer(NON_TANKER_ATK_TIME, 20000);
				L2Character c0 = _thisActor.getMostHated();
				if(c0 != null)
				{
					if(c0.isDead() && c0.isPlayer())
					{
						if(CategoryManager.isInCategory(71, c0.getActiveClass()))
						{
						}
						else if(CategoryManager.isInCategory(70, c0.getActiveClass()) || CategoryManager.isInCategory(78, c0.getActiveClass()))
						{
							addUseSkillDesire(c0, notank_skill_b, 0, 1, 10000000000L);
						}
						else
						{
							addUseSkillDesire(c0, notank_skill_a, 0, 1, 10000000000L);
						}
					}
				}
			}
		}
		else if(timerId == SPAWN_HOLD_MON)
		{
			_thisActor.i_ai8 = 0;
			if(_thisActor.i_ai0 == 1)
			{
				DefaultMaker maker0 = _thisActor.getMyMaker();
				if(maker0 != null && maker0.maximum_npc - maker0.npc_count >= 50)
				{
					addTimer(SPAWN_HOLD_MON, (SPAWN_HOLD_MON_TIME + Rnd.get(20)) * 1000);
					for(int i2 = 0; i2 < 15; i2++)
					{
						L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
						if(h0 != null)
						{
							L2Character c0 = h0.getAttacker();
							if(c0 != null && _thisActor.inMyTerritory(c0))
							{
								_thisActor.createOnePrivate(underling, ai_type, 0, 0, c0.getX(), c0.getY(), c0.getZ(), 0, 1000, getStoredIdFromCreature(c0), 0);
							}
						}
					}
					for(int i3 = 0; i3 < 9; i3++)
					{
						L2NpcInstance.AggroInfo h0 = _thisActor.getRandomHateInfo();
						if(h0 != null)
						{
							L2Character c0 = h0.getAttacker();
							if(c0 != null && _thisActor.inMyTerritory(c0))
							{
								_thisActor.createOnePrivate(underling1, ai_type1, 0, 0, c0.getX(), c0.getY(), c0.getZ(), 0, 1000, getStoredIdFromCreature(c0), 0);
							}
						}
					}
				}
			}
		}
		else if(timerId == CORPSE_TIME)
		{
			_thisActor.onDecay();
			_thisActor.changeNpcState(2);
			L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai1);
			if(c0 != null)
			{
				_thisActor.createOnePrivate(corpse, ai_corpse, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), _thisActor.getHeading(), getStoredIdFromCreature(_thisActor), getStoredIdFromCreature(c0), Util.getMPCCId(c0));
			}
		}
	}

	@Override
	protected void onEvtOutOfMyTerritory()
	{
		removeAllAttackDesire();
		_thisActor.removeAllHateInfoIF(0, 0);
		_thisActor.i_ai0 = 0;
		addTimer(R_U_IN_BATTLE, 5000);
		addMoveToDesire(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 10000000000L);
		if(selfheal_skill.getMpConsume() < _thisActor.getCurrentMp() && selfheal_skill.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(selfheal_skill.getId()))
		{
			addUseSkillDesire(_thisActor, selfheal_skill, 1, 1, 1000000);
		}
		broadcastScriptEvent(15008, 0, null, 5000);
		broadcastScriptEvent(15007, 0, null, 5000);
		ZoneManager.getInstance().areaSetOnOff(AreaName, 0);
		_thisActor.i_ai5 = 0;
		_thisActor.i_ai6 = 0;
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 15005)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(attackers.size() > 0)
				{
					for(Long storedId : attackers)
					{
						_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 15006, storedId, null);
					}
				}
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		_thisActor.lookNeighbor(1500);
		_thisActor.i_ai0 = 2;
		for(L2NpcInstance.AggroInfo ai : _thisActor.getAggroList().values())
		{
			if(ai != null)
			{
				L2Character cha = ai.getAttacker();
				if(cha != null && !attackers.contains(cha.getStoredId()))
					attackers.add(cha.getStoredId());
			}
		}
		broadcastScriptEvent(15008, 0, null, 5000);
		broadcastScriptEvent(15007, 0, null, 5000);
		if(killer != null && killer.getPlayer() != null)
			_thisActor.c_ai1 = killer.getPlayer().getStoredId();
		addTimer(CORPSE_TIME, 2000);
		super.onEvtDead(killer);
	}

	@Override
	public void addAttackDesire(L2Character target, int p1, long desire)
	{
		if(target == null)
			return;
		Task task = new Task();
		task.type = TaskType.ATTACK;
		task.targetId = target.getStoredId();
		task.p1 = p1;
		task.weight = desire;
		_task_list.add(task);
		_globalAggro = 0;
		_def_think = true;

		_thisActor.addDamageHate(target, 0, desire);

		if(!_actor.isRunning())
			startRunningTask(1000);

		if(_intention != AI_INTENTION_ATTACK)
			setIntention(AI_INTENTION_ATTACK, target);
	}
}