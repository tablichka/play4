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
 * @date: 14.09.2009 13:03:29
 */
public class ScarletWeak extends DefaultAI
{
	private static L2Skill g_attack_w;
	private static L2Skill g_attack_s;
	private static L2Skill charge_w;
	private static L2Skill charge_s;
	private static L2Skill charge_w_slow;
	private static L2Skill charge_s_slow;
	private static L2Skill magic_field_s;
	private static L2Skill morph;
	//private long _retargetTime;
	//private boolean _retarget = false;

	public ScarletWeak(L2Character actor)
	{
		super(actor);

		g_attack_w = SkillTable.getInstance().getInfo(5014, 1);
		charge_w = SkillTable.getInstance().getInfo(5015, 1);
		charge_w_slow = SkillTable.getInstance().getInfo(5015, 4);

		g_attack_s = SkillTable.getInstance().getInfo(5014, 2);
		charge_s = SkillTable.getInstance().getInfo(5015, 2);
		charge_s_slow = SkillTable.getInstance().getInfo(5015, 5);
		magic_field_s = SkillTable.getInstance().getInfo(5018, 1);

		morph = SkillTable.getInstance().getInfo(5017, 1);
		//_retargetTime = System.currentTimeMillis() + Rnd.get(30000, 120000);
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
		//clearTasks();

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
		ArrayList<L2Skill> t_skill = new ArrayList<>();
		double distance = _thisActor.getDistance(_temp_attack_target);

		// Слабый саммон используем только слабые скилы
		if(_thisActor.getCurrentHp() > _thisActor.getMaxHp() * 0.5)
		{
			int g_attack_chane = 30;
			int charge_chance = 20;
			int magic_chance = 5;

			if(!_thisActor.isSkillDisabled(charge_w.getId()) && distance > 300 && Rnd.chance(50))
				t_skill.add(charge_w_slow);
			else
			{
				if(Rnd.chance(g_attack_chane))
					t_skill.add(g_attack_w);
				if(!_thisActor.isSkillDisabled(charge_w.getId()))
				{
					if(distance > 500 && Rnd.chance(charge_chance))
						t_skill.add(charge_w_slow);
					else if(distance < 200 && Rnd.chance(charge_chance))
						t_skill.add(charge_w);
				}
				if(!_thisActor.isSkillDisabled(magic_field_s.getId()) && Rnd.chance(magic_chance))
					t_skill.add(magic_field_s);
			}
		}
		else
		{
			int g_attack_chane = 20;
			int charge_chance = 20;
			int magic_chance = 20;

			if(!_thisActor.isSkillDisabled(charge_w.getId()) && distance > 300 && Rnd.chance(70))
				t_skill.add(charge_w_slow);
			else
			{
				if(Rnd.chance(g_attack_chane))
					t_skill.add(g_attack_s);
				if(!_thisActor.isSkillDisabled(charge_s.getId()))
				{
					if(distance > 500 && Rnd.chance(charge_chance))
						t_skill.add(charge_s_slow);
					else if(distance < 200 && Rnd.chance(charge_chance))
						t_skill.add(charge_s);
				}
				if(!_thisActor.isSkillDisabled(magic_field_s.getId()) && Rnd.chance(magic_chance))
					t_skill.add(magic_field_s);
			}
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

			// Добавить новое задание
			if(r_skill.getId() == 5018)
			{
				addUseSkillDesire(_thisActor, morph, 1, 1, DEFAULT_DESIRE * 100);
			}
			else if((r_skill == charge_w || r_skill == charge_s) && Rnd.chance(60))
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

}
