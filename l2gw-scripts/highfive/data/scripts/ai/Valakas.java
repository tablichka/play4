package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.instancemanager.boss.ValakasManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Effect;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.ArrayList;
import java.util.Arrays;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * Created by IntelliJ IDEA.
 * User: admin
 * Date: 23.01.2009
 * Time: 11:08:48
 */
public class Valakas extends Fighter
{
	private long _retargetTime;
	private L2Skill[] _dam_skills;

	private static int s_regen = 4691;
	private static L2Skill s_regen1 = SkillTable.getInstance().getInfo(4691, 1); // 0.15x regen
	private static L2Skill s_regen2 = SkillTable.getInstance().getInfo(4691, 2); // 0.5x regen
	private static L2Skill s_regen3 = SkillTable.getInstance().getInfo(4691, 3); // 0.8x regen
	private static L2Skill s_regen4 = SkillTable.getInstance().getInfo(4691, 4); // 1.0x regen

	public Valakas(L2Character actor)
	{
		super(actor);
		_retargetTime = System.currentTimeMillis() + 60000;
		_dam_skills = msum(_pdam_skills, _mdam_skills);
		addTimer(1002, 60000);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void thinkAttack()
	{
		if(_retargetTime < System.currentTimeMillis())
		{
			createNewTask();
			return;
		}

		super.thinkAttack();
	}

	@Override
	protected boolean thinkActive()
	{
		return super.thinkActive();
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
		{
			if(_retargetTime < System.currentTimeMillis())
			{
				_retargetTime = System.currentTimeMillis() + Rnd.get(25000, 60000);
				L2Player target = getRandomTarget();
				if(target != null && target != hated)
				{
					if(!_thisActor.getAggroList().containsKey(target.getObjectId()))
						_thisActor.addDamageHate(target, 0, _thisActor.getAggroList().get(hated.getObjectId()).hate);
					else
						_thisActor.getAggroList().get(target.getObjectId()).hate = _thisActor.getAggroList().get(hated.getObjectId()).hate;

					_thisActor.getAggroList().get(hated.getObjectId()).hate = 0;
				}

				return false;
			}
			_temp_attack_target = hated;
		}
		else
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			_temp_attack_target = null;
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		// Базовые параметры
		int phys_per = 25;
		int debuff_per = 50;
		int pdam_per = 50;

		ArrayList<L2Skill> d_skill = new ArrayList<L2Skill>();
		ArrayList<L2Skill> t_skill = new ArrayList<L2Skill>();
		L2Skill r_skill = null;

		double distance = _thisActor.getDistance(_temp_attack_target);
		double _att_hp = _temp_attack_target.getCurrentHp();
		int _att_max_hp = _temp_attack_target.getMaxHp();
		double _def_mp = _thisActor.getCurrentMp();

		if(!Rnd.chance(phys_per))
		{
			int rnd_per = Rnd.get(100);

			// DEBUFF
			if(rnd_per < debuff_per && _att_hp / _att_max_hp > 0.25 && _debuff_skills.length > 0)
			{
				t_skill.clear();
				for(L2Skill sk : _debuff_skills)
				{
					if(sk == null || _temp_attack_target.getEffectBySkill(sk) != null)
						continue;
					if(sk.getCastRange() > 200 && distance <= 200)
						continue;
					if(sk.getCastRange() <= 200 && distance > 200)
						continue;
					t_skill.add(sk);
				}
				if(t_skill.size() > 0)
					d_skill.add(t_skill.get(Rnd.get(t_skill.size())));
			}

			// PDAM
			if(rnd_per < pdam_per && _dam_skills.length > 0)
			{
				t_skill.clear();
				for(L2Skill sk : _dam_skills)
				{
					if(sk == null || sk.getCastRange() > 200 && distance <= 200)
						continue;
					if(sk.getCastRange() <= 200 && distance > 200)
						continue;
					t_skill.add(sk);
				}
				if(t_skill.size() > 0)
					d_skill.add(t_skill.get(Rnd.get(t_skill.size())));
			}

			// HEAL
			if(_heal_skills.length > 0)
			{
				t_skill.clear();
				t_skill.addAll(Arrays.asList(_heal_skills));
				if(t_skill.size() > 0)
					d_skill.add(t_skill.get(Rnd.get(t_skill.size())));
			}

			// Выбрать 1 скилл из полученных
			if(d_skill.size() > 0)
				r_skill = d_skill.get(Rnd.get(d_skill.size()));

		}

		// Использовать скилл если можно, иначе атаковать
		if(r_skill != null && !r_skill.isMuted(_thisActor) && _def_mp >= r_skill.getMpConsume() && !_thisActor.isSkillDisabled(r_skill.getId()))
		{
			// Проверка таргета
			if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;

			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}

		// Добавить новое задание
		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		ValakasManager.getInstance().updateLastAttack();
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		ValakasManager.getInstance().setCubeSpawn();
		super.onEvtDead(killer);
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		return false;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(_thisActor == null || _thisActor.isDead())
		{
			_log.info("Timer Fired: " + timerId + " but " + this + " is dead/null.");
			return;
		}
		if(timerId == 1002)
		{
			L2Effect regenEffect = _thisActor.getEffectBySkillId(s_regen);
			int regAbnormalLevel = regenEffect != null ? regenEffect.getAbnormalLevel() : 0;
			if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.25) && regAbnormalLevel < 14)
				addUseSkillDesire(_thisActor, s_regen4, 1, 1, DEFAULT_DESIRE * 1000);
			else if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.5) && regAbnormalLevel < 13)
				addUseSkillDesire(_thisActor, s_regen3, 1, 1, DEFAULT_DESIRE * 1000);
			else if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() * 0.75) && regAbnormalLevel < 12)
				addUseSkillDesire(_thisActor, s_regen2, 1, 1, DEFAULT_DESIRE * 1000);
			else if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() && regAbnormalLevel < 11)
				addUseSkillDesire(_thisActor, s_regen1, 1, 1, DEFAULT_DESIRE * 1000);

			addTimer(1002, 60000);
		}
	}

}
