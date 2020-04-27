package ai;

import instances.FrintezzaBattleInstance;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.arrays.GArray;

import java.util.ArrayList;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author: rage
 * @date: 15.09.2009 21:19:38
 */
public class ScarletStrong extends DefaultAI
{
	private static L2Skill g_attack;
	private static L2Skill charge;
	private static L2Skill charge_slow;
	private static L2Skill drain;
	private static L2Skill magic_field;
	//private long _retargetTime;
	//private boolean _retarget = false;

	public ScarletStrong(L2Character actor)
	{
		super(actor);

		g_attack = SkillTable.getInstance().getInfo(5014, 3);
		charge = SkillTable.getInstance().getInfo(5015, 3);
		charge_slow = SkillTable.getInstance().getInfo(5015, 6);
		drain = SkillTable.getInstance().getInfo(5019, 1);

		magic_field = SkillTable.getInstance().getInfo(5018, 2);

		//_retargetTime = System.currentTimeMillis() + Rnd.get(15000, 60000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		((FrintezzaBattleInstance) _thisActor.getSpawn().getInstance()).updateLastAttack();
		super.onEvtAttacked(attacker, damage, skill);
	}
/*
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
*/
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
			/*
			if(_retargetTime < System.currentTimeMillis())
			{
				_retargetTime = System.currentTimeMillis() + Rnd.get(15000, 60000);
				GArray<L2Player> players = ((FrintezzaBattleInstance) _thisActor.getSpawn().getInstance()).getHallZone().getPlayers();
				L2Player target = players.get(Rnd.get(players.size()));
				if(!target.isDead() && target != hated && !target.isInvisible())
				{
					if(!_thisActor.getAggroList().containsKey(target.getObjectId()))
						_thisActor.addDamageHate(target, 0, _thisActor.getAggroList().get(hated.getObjectId()).hate);
					else
						_thisActor.getAggroList().get(target.getObjectId()).hate = _thisActor.getAggroList().get(hated.getObjectId()).hate;

					_thisActor.getAggroList().get(hated.getObjectId()).hate = 0;
					_retarget = true;
				}
				return false;
			}
			*/
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
		ArrayList<L2Skill> t_skill = new ArrayList<L2Skill>();
		double distance = _thisActor.getDistance(_temp_attack_target);

		int g_attack_chane = 20;
		int charge_chance = 40;
		int magic_chance = 15;
		int drain_chance = 15;

		if(!_thisActor.isSkillDisabled(charge.getId()) && distance > 300 && Rnd.chance(70))
			t_skill.add(charge_slow);
		else
		{
			if(Rnd.chance(g_attack_chane))
				t_skill.add(g_attack);
			if(!_thisActor.isSkillDisabled(charge.getId()))
			{
				if(distance > 500 && Rnd.chance(charge_chance))
					t_skill.add(charge_slow);
				else if(distance < 200 && Rnd.chance(charge_chance))
					t_skill.add(charge);
			}
			if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5 && !_thisActor.isSkillDisabled(drain.getId()) && Rnd.chance(drain_chance))
				t_skill.add(drain);
			if(!_thisActor.isSkillDisabled(magic_field.getId()) && Rnd.chance(magic_chance))
				t_skill.add(magic_field);
		}
		
		//_retarget = false;

		// Выбираем скилл атаки
		if(t_skill.size() > 0)
			r_skill = t_skill.get(Rnd.get(t_skill.size()));

		// Использовать скилл если можно, иначе атаковать скилом s_baium_normal_attack
		if(r_skill != null)
		{
			// Проверка таргета
			if(r_skill.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;

			if(r_skill == charge && Rnd.chance(60))
			{
				//_retargetTime = System.currentTimeMillis() + Rnd.get(15000, 60000);
				GArray<L2Player> players = ((FrintezzaBattleInstance) _thisActor.getSpawn().getInstance()).getHallZone().getPlayers();
				L2Player target = players.get(Rnd.get(players.size()));
				if(target != hated && !target.isInvisible())
				{
					if(!_thisActor.getAggroList().containsKey(target.getObjectId()))
						_thisActor.addDamageHate(target, 0, _thisActor.getAggroList().get(hated.getObjectId()).hate);
					else
						_thisActor.getAggroList().get(target.getObjectId()).hate = _thisActor.getAggroList().get(hated.getObjectId()).hate;

					_thisActor.getAggroList().get(hated.getObjectId()).hate = 0;
				}
			}

			addUseSkillDesire(_temp_attack_target, r_skill, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}

		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		return false;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		((FrintezzaBattleInstance) _thisActor.getSpawn().getInstance()).demon2Killed();
	}
}
