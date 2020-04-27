package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.instancemanager.boss.AntharasManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

import java.util.ArrayList;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * TODO: Дописать АИ, сделать так, что-бы анатрас не танковался. Антарас должен
 * TODO: время от времени выкрикивать имена героев в шаут.
 * TODO: Сделать так, что-бы тарас юзал свои скилы как-то осмысленно
 * TODO: Возможно нужно перенести задачу спавна миников в АИ.
 * TODO: написать миникам свой АИ, что-бы они самоубийство юзали как-то осмысленно.
 * TODO: сделать чтобы миники с тарасом как-то взаимодйствовали
 */

public class Antharas extends Fighter
{
	// Боевые скилы Антараса
	// debuffs
	private static final int s_fear_ID = 4108;
	private static final int s_fear2_ID = 5092;

	private static final int s_curse_ID = 4109;
	private static final int s_paralyze_ID = 4111;

	// damage skills
	private static final int s_shock_ID = 4106;
	private static final int s_shock2_ID = 4107;

	private static final int s_antharas_ordinary_attack_ID = 4112;
	private static final int s_antharas_ordinary_attack2_ID = 4113;
	private static final int s_meteor_ID = 5093;

	private static L2Skill s_regen1 = SkillTable.getInstance().getInfo(4239, 1); // 0.4x regn
	private static L2Skill s_regen2 = SkillTable.getInstance().getInfo(4240, 1); // 0.7x regen
	private static L2Skill s_regen3 = SkillTable.getInstance().getInfo(4241, 1); // 1.0x regen

	private long _retargetTime;


	public Antharas(L2Character actor)
	{
		super(actor);
		_retargetTime = System.currentTimeMillis() + 30000;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void thinkAttack()
	{
		super.thinkAttack();

		if(_thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.50 && _thisActor.getEffectBySkill(s_regen1) == null)
			_thisActor.altUseSkill(s_regen1, _thisActor);
		else if(_thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.25 && _thisActor.getEffectBySkill(s_regen2) == null)
			_thisActor.altUseSkill(s_regen2, _thisActor);
		else if(_thisActor.getEffectBySkill(s_regen3) == null)
			_thisActor.altUseSkill(s_regen3, _thisActor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		AntharasManager.getInstance().updateLastAttack();
		super.onEvtAttacked(attacker, damage, skill);
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
					_temp_attack_target = target;
				}
			}
			else
				_temp_attack_target = hated;
		}
		else
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		L2Skill r_skill = null;
		ArrayList<L2Skill> d_skill = new ArrayList<L2Skill>();
		ArrayList<L2Skill> t_skill = new ArrayList<L2Skill>();

		// Шансы использования скилов
		int s_shock = 0; // >75% хп, s_shock не используем
		int s_fear = 0; // >75% hp, s_fear not used
		int s_breath = 0; // >50% hp, breathe not used
		int s_curse = 5;
		int s_paralyze = 5;

		// <75% хп, добавляем s_shock
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.75)
			s_shock = 3;

		// <50% хп, добавляем s_breath
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5)
			s_breath = 5;

		// <25% хп, добавляем s_fear
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.25)
			s_fear = 1;

		// Вычислим шанс обычной атаки
		int s_antharas_normal_attack = 100 - s_shock - s_fear - s_breath - s_curse - s_paralyze;

		// Выбираем скилл атаки
		if(!Rnd.chance(s_antharas_normal_attack))
		{
			t_skill.clear();
			d_skill.clear();

			L2Skill sk = _thisActor.getTemplate().getSkills().get(s_curse_ID);
			if(sk != null && _temp_attack_target.getEffectBySkill(sk) == null)
				t_skill.add(sk);

			sk = _thisActor.getTemplate().getSkills().get(s_paralyze_ID);
			if(sk != null && _temp_attack_target.getEffectBySkill(sk) == null)
				t_skill.add(sk);

			if(s_shock > 0)
			{
				sk = _thisActor.getTemplate().getSkills().get(s_shock_ID);
				if(sk != null)
					t_skill.add(sk);

				sk = _thisActor.getTemplate().getSkills().get(s_shock2_ID);
				if(sk != null)
					t_skill.add(sk);
			}

			if(s_fear > 0)
			{
				sk = _thisActor.getTemplate().getSkills().get(s_fear_ID);
				if(sk != null && _temp_attack_target.getEffectBySkill(sk) == null)
					t_skill.add(sk);

				sk = _thisActor.getTemplate().getSkills().get(s_fear2_ID);
				if(sk != null && _temp_attack_target.getEffectBySkill(sk) == null)
					t_skill.add(sk);

			}

			// Фильтруем неподходящие по дальности скилы
			double distance = _thisActor.getDistance(_temp_attack_target);
			for(L2Skill skill : t_skill)
			{
				if(skill == null || skill.getCastRange() < distance && skill.getAimingTarget(_thisActor) != _thisActor)
					continue;
				d_skill.add(skill);
			}

			// Выбрать 1 скилл из полученных
			if(d_skill.size() > 0)
				r_skill = d_skill.get(Rnd.get(d_skill.size()));
		}

		// Если в руте, то использовать массовый скилл дальнего боя
		if(_thisActor.isRooted())
			r_skill = _thisActor.getTemplate().getSkills().get(s_meteor_ID);

		// Использовать скилл если можно, иначе атаковать скилом s_antharas_ordinary_attack(2)
		if(r_skill == null || r_skill.getMpConsume() > _thisActor.getCurrentMp())
			r_skill = _thisActor.getTemplate().getSkills().get(Rnd.chance(50) ? s_antharas_ordinary_attack_ID : s_antharas_ordinary_attack2_ID);

		if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;

		// Добавить новое задание
		addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 100);
		return true;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		AntharasManager.getInstance().setCubeSpawn();
		super.onEvtDead(killer);
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		return false;
	}
}