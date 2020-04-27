package ai;

import npc.model.BaiumCubeInstance;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.instancemanager.boss.BaiumManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.zone.L2Zone;
import ru.l2gw.gameserver.serverpackets.Earthquake;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import java.util.ArrayList;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * AI боса Байума.
 * - Мгновенно убивает первого ударившего
 * - Если его не трогали 30 минут, то засыпает обратно в каменную статую
 * - При смерти спаунит обратный портал, который удаляется через 15 минут
 * - Для атаки использует только скилы по следующей схеме:
 * Стандартный набор: 80% - 4127, 10% - 4128, 10% - 4129
 * если хп < 50%: 70% - 4127, 10% - 4128, 10% - 4129, 10% - 4131
 * если хп < 25%: 60% - 4127, 10% - 4128, 10% - 4129, 10% - 4131, 10% - 4130
 *
 * @author SYS
 */
public class Baium extends DefaultAI
{
	//private static final int TELEPORTATION_CUBIC_ID = 31759;

	// Боевые скилы байума
	private static final int s_thunderbolt_ID = 4130;
	private static final int s_group_hold_ID = 4131;
	private static final int s_energy_wave_ID = 4128;
	private static final int s_earth_quake_ID = 4129;
	private static final int s_baium_normal_attack_ID = 4127;
	private long _retargetTime;

	public Baium(L2Character actor)
	{
		super(actor);
		_retargetTime = System.currentTimeMillis() + 60000;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		BaiumManager.getInstance().updateLastAttack();
		super.onEvtAttacked(attacker, damage, skill);
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
		if(_thisActor.isDead())
			return true;

		//если есть задчи на выполнение - выполняем их 
		if(_def_think)
		{
			doTask();
			return true;
		}

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

		L2Skill r_skill = null;
		ArrayList<L2Skill> d_skill = new ArrayList<L2Skill>();
		ArrayList<L2Skill> t_skill = new ArrayList<L2Skill>();

		// Шансы использования скилов
		int s_thunderbolt = 0; // >50% хп, s_thunderbolt не используем
		int s_group_hold = 0; // >50% хп, s_group_hold не используем
		int s_energy_wave = 10;
		int s_earth_quake = 10;

		// <50% хп, добавляем s_group_hold
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5)
			s_group_hold = 10;

		// <25% хп, добавляем s_thunderbolt
		if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.25)
			s_thunderbolt = 10;

		// Вычислим шанс обычной атаки
		int s_baium_normal_attack = 100 - s_thunderbolt - s_group_hold - s_energy_wave - s_earth_quake;

		// Выбираем скилл атаки
		if(!Rnd.chance(s_baium_normal_attack))
		{
			t_skill.clear();
			d_skill.clear();

			L2Skill sk = _thisActor.getTemplate().getSkills().get(s_energy_wave_ID);
			if(sk != null)
				t_skill.add(sk);

			sk = _thisActor.getTemplate().getSkills().get(s_earth_quake_ID);
			if(sk != null)
				t_skill.add(sk);

			if(s_thunderbolt > 0)
			{
				sk = _thisActor.getTemplate().getSkills().get(s_thunderbolt_ID);
				if(sk != null)
					t_skill.add(sk);
			}

			if(s_group_hold > 0)
			{
				sk = _thisActor.getTemplate().getSkills().get(s_group_hold_ID);
				if(sk != null && _temp_attack_target.getEffectBySkill(sk) == null)
					t_skill.add(sk);
			}

			// Фильтруем неподходящие по дальности скилы
			double distance = _thisActor.getDistance(_temp_attack_target);
			for(L2Skill skill : t_skill)
			{
				if(skill == null || skill.getCastRange() > 0 && skill.getCastRange() < distance)
					continue;
				d_skill.add(skill);
			}

			// Выбрать 1 скилл из полученных
			if(d_skill.size() > 0)
				r_skill = d_skill.get(Rnd.get(d_skill.size()));
		}

		// Если в руте, то использовать массовый скилл дальнего боя
		if(_thisActor.isRooted())
			r_skill = _thisActor.getTemplate().getSkills().get(s_thunderbolt_ID);

		// Использовать скилл если можно, иначе атаковать скилом s_baium_normal_attack
		if(r_skill != null)
		{
			// Проверка таргета
			if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
		}
		else
			r_skill = _thisActor.getTemplate().getSkills().get(s_baium_normal_attack_ID);

		// Добавить новое задание
		addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
		return true;
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		return false;
	}

	@Override
	public void onEvtSpawn()
	{
		addTimer(2006, 2000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.no_landing, 440);
		if(zone != null)
		{
			zone.broadcastPacket(new Earthquake(_thisActor.getLoc(), 40, 10));
			zone.broadcastPacket(new PlaySound(1, "BS02_A", 1, _thisActor.getObjectId(), _thisActor.getLoc()));

		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		L2Zone zone = ZoneManager.getInstance().getZoneById(L2Zone.ZoneType.no_landing, 440);
		if(zone != null)
			zone.broadcastPacket(new PlaySound(1, "BS01_D", 1, _thisActor.getObjectId(), _thisActor.getLoc()));

		super.onEvtDead(killer);
		BaiumManager.getInstance().spawnCube();

		try
		{
			BaiumCubeInstance cube = new BaiumCubeInstance(IdFactory.getInstance().getNextId(), NpcTable.getTemplate(31842), 0, 0, 0, 0);
			cube.spawnMe(new Location(115203, 16620, 10078));
			cube.onSpawn();
		}
		catch(Exception e)
		{
		}
	}

	@Override
	protected boolean tryMoveToTarget(L2Character target, int offset)
	{
		if(!_thisActor.followToCharacter(target, offset))
			_pathfind_fails++;

		if(_pathfind_fails >= MAX_PATHFIND_FAILS && GameTimeController.getGameTicks() - (_thisActor.getAttackTimeout() - MAX_ATTACK_TIMEOUT) > TELEPORT_TIMEOUT && _thisActor.isInRange(target, 2000))
		{
			_pathfind_fails = 0;
			_thisActor.stopHate(target);
			return false;
		}

		return true;
	}
}