package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.quest.Quest;
import ru.l2gw.gameserver.tables.SkillTable;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author rage
 * @date 15.07.11 12:18
 */
public class CombatMonster extends DefaultAI
{
	public int basic_hate = 100;
	public L2Skill Skill01_ID = null;
	public int Skill01_Probability = 30;
	public int Skill01_Target_Type = 0;
	public L2Skill Skill02_ID = null;
	public int Skill02_Probability = 30;
	public int Skill02_Target_Type = 0;
	public L2Skill Skill03_ID = null;
	public int Skill03_Probability = 30;
	public int Skill03_Target_Type = 0;
	public int SoulShot = 0;
	public int boss_type = 3;
	public int Dispel_Debuff = 0;
	public int Dispel_Debuff_Prob = 0;

	public CombatMonster(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		addTimer(8001, 4000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.i_ai0 == 0)
			_thisActor.i_ai0 = 1;

		if(attacker instanceof L2Player)
		{
			_thisActor.addDamage(attacker, damage);
			if(skill != null && CategoryManager.isInCategory(5, attacker))
				_thisActor.addDamageHate(attacker, 0, (int) (damage * 1.5));
		}
		else if(attacker instanceof L2Summon)
		{
			L2Player master = attacker.getPlayer();
			if(master != null && !master.isDead())
				_thisActor.addDamageHate(master, 0, damage / 2);

			_thisActor.addDamage(attacker, damage);
		}

		L2Player player = attacker.getPlayer();
		if(player != null)
		{
			Quest[] quests = _thisActor.getTemplate().getEventQuests(Quest.QuestEventType.ON_ATTACKED);
			if(quests != null)
				for(Quest q : quests)
					q.notifyAttack(_thisActor, player, skill);
		}

		_isMovingBack = false;
		_thisActor.callFriends(attacker, damage);

		_thisActor.setAttackTimeout(MAX_ATTACK_TIMEOUT + GameTimeController.getGameTicks());
		setGlobalAggro(0);

		if(!_actor.isRunning())
			startRunningTask(1000);

		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
	}

	@Override
	protected boolean thinkActive()
	{
		_thisActor.i_ai0 = 0;
		return super.thinkActive();
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		int effectPoint = skill.getEffectPoint();
		if(effectPoint > 0 && _thisActor.isInRange(caster, _thisActor.getAggroRange()))
		{
			if(caster instanceof L2Player)
				_thisActor.addDamageHate(caster, 0, (int) (effectPoint * 0.70));
			else if(caster instanceof L2Summon)
			{
				L2Player master = caster.getPlayer();
				if(master != null && !master.isDead())
					_thisActor.addDamageHate(master, 0, (int) (effectPoint * 0.70));

				_thisActor.addDamageHate(caster, 0, (int) (effectPoint * 0.30));
			}
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		if(Math.abs(target.getZ() - _actor.getZ()) > 400)
			return false;
		if(!GeoEngine.canSeeTarget(_actor, target))
			return false;
		if(target.isPlayer() && ((L2Player) target).isGM() && ((L2Player) target).isInvisible())
			return false;
		if(!_thisActor.canAttackCharacter(target))
			return false;

		if(_thisActor.isInRange(target, _thisActor.getAggroRange()))
		{
			if(_thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
				if(target instanceof L2Player)
				{
					L2Player player = (L2Player) target;
					_thisActor.addDamageHate(target, 0, 10);
					L2Party party = player.getParty();
					if(party != null)
						for(L2Player member : party.getPartyMembers())
							if(!member.isDead())
								_thisActor.addDamageHate(member, 0, basic_hate);
				}
				else if(target instanceof L2Summon)
					_thisActor.addDamageHate(target, 0, 1);
			}
			else
			{
				if(target instanceof L2Player)
				{
					L2Player player = (L2Player) target;
					_thisActor.addDamageHate(target, 0, 1);
					L2Party party = player.getParty();
					if(party != null)
						for(L2Player member : party.getPartyMembers())
							if(!member.isDead())
								_thisActor.addDamageHate(member, 0, 1);
				}
				else if(target instanceof L2Summon)
					_thisActor.addDamageHate(target, 0, 1);
			}

			startRunningTask(2000);
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
			return true;
		}

		return false;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 8001)
		{
			randomizeTargets();
			addTimer(8001, 4000 + Rnd.get(1000));
		}
	}

	@Override
	protected boolean createNewTask()
	{
		// Удаляем все задания
		clearTasks();

		L2Character _temp_attack_target = getAttackTarget();

		// Новая цель исходя из агрессивности
		L2Character hated = _thisActor.isConfused() ? _temp_attack_target : _thisActor.getMostHated();

		if(hated != null && hated != _thisActor)
			_temp_attack_target = hated;
		else
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			_temp_attack_target = null;
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		int chance = Rnd.get(100);

		L2Skill r_skill = null;
		if(Skill01_ID != null && chance < Skill01_Probability)
		{
			if(!Skill01_ID.isMuted(_thisActor))
				r_skill = Skill01_ID;
		}
		else if(Skill01_ID != null && chance > Skill01_Probability && chance < Skill01_Probability + Skill02_Probability)
		{
			if(!Skill01_ID.isMuted(_thisActor))
				r_skill = Skill01_ID;
		}
		else if(Skill01_ID != null && chance > Skill01_Probability + Skill02_Probability && chance < Skill01_Probability + Skill02_Probability + Skill03_Probability)
		{
			if(!Skill01_ID.isMuted(_thisActor))
				r_skill = Skill01_ID;
		}

		// Использовать скилл если можно, иначе атаковать
		if(r_skill != null && _thisActor.getCurrentMp() >= r_skill.getMpConsume() && !_thisActor.isSkillDisabled(r_skill.getId()))
		{
			// Проверка таргета
			if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
			else if(!r_skill.isOffensive())
				_temp_attack_target = getFriendTarget(r_skill);

			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}

		// Добавить новое задание
		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}

	@Override
	protected void onEvtAbnormalStatusChanged(L2Character speller, L2Effect effect, boolean added)
	{
		if(added)
		{
			if( Dispel_Debuff == 1 )
			{
				if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 6029313))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 91357185))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 18284545))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 24051713))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 76611585))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 78708737))
				{
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 26411009))
				{
						effect.exit();
				}
			}
			else if( Dispel_Debuff == 2 )
			{
				
				if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 6029313))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 91357185))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 18284545))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 24051713))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 76611585))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 78708737))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
				else if(SkillTable.isAbnormalTypeMatch(effect.getSkill(), 26411009))
				{
					if(Rnd.get(10000) < Dispel_Debuff_Prob)
						effect.exit();
				}
			}
		}
	}
}
