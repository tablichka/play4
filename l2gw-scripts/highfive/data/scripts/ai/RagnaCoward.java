package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

/**
 * @author rage
 * @date 17.08.2010 11:38:49
 */
public class RagnaCoward extends Fighter
{
	private static final long reward_adena = (long) (1000000 * Config.RATE_DROP_ADENA);
	private static final long reward_adena_small = (long) (10000 * Config.RATE_DROP_ADENA);
	private static final int reward_num = 10;
	private static final int reward_prob = 10;
	private static final int reward_prob_small = 1000;
	private static final int stop_attack_sec = 10;
	private static final float stop_attack_hp = 0.300000f;
	private static final float	allow_attack_hp = 0.100000f;
	private static final L2Skill SKILL_display = SkillTable.getInstance().getInfo(6234, 1);
	private static final int TIMER_SAY = 33311;
	private static final int TIMER_check_hp = 33312;
	private static final int TIMER_RUNAWAY = 33313;
	private static final int TIMER_DESPAWN = 33314;
	private boolean runAway;
	private Location lastAttackerLoc;
	private int lastAttacker;

	public RagnaCoward(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		runAway = false;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		super.onEvtAttacked(attacker, damage, skill);
		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.i_ai0 = 1;
			addTimer(TIMER_SAY, (Rnd.get(5) + 3) * 1000);
		}
		else if(_thisActor.i_ai0 == 1)
		{
			if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * stop_attack_hp)
			{
				_thisActor.i_ai0 = 2;
				Functions.npcSay(_thisActor, Say2C.ALL, 1800832);
				addTimer(TIMER_check_hp, stop_attack_sec * 1000);
				_thisActor.i_ai1 = (int) _thisActor.getCurrentHp();
			}
		}
		if(attacker != null)
		{
			lastAttackerLoc = attacker.getLoc();
			lastAttacker = attacker.getObjectId();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_SAY)
		{
			if(_thisActor.i_ai0 == 1)
				Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800833 : 1800834);
			addTimer(TIMER_SAY, (Rnd.get(5) + 5) * 1000);
		}
		else if(timerId == TIMER_check_hp)
		{
			if(_thisActor.i_ai1 - (int)_thisActor.getCurrentHp() < _thisActor.getMaxHp() * (stop_attack_hp - allow_attack_hp))
			{
				if(Rnd.get(100000) < reward_prob)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800835 : 1800836);
					_thisActor.i_ai2 = 1;
					_thisActor.stopHate();
					_thisActor.stopMove();
					_thisActor.abortAttack();
					_thisActor.abortCast();
					_thisActor.doCast(SKILL_display, _thisActor, false);
					addTimer(TIMER_RUNAWAY, 3000);
				}
				else if(Rnd.get(100000) < reward_prob_small)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800835 : 1800871);
					_thisActor.i_ai2 = 2;
					_thisActor.stopHate();
					_thisActor.stopMove();
					_thisActor.abortAttack();
					_thisActor.abortCast();
					_thisActor.doCast(SKILL_display, _thisActor, false);
					addTimer(TIMER_RUNAWAY, 3000);
				}
				else
				{
					Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800837 : 1800838);
					addTimer(TIMER_RUNAWAY, 1000);
				}
			}
		}
		else if(timerId == TIMER_RUNAWAY)
		{
			runAway = true;
			_thisActor.stopHate();
			_thisActor.stopMove();
			_thisActor.abortAttack();
			_thisActor.abortCast();
			addTimer(TIMER_DESPAWN, 3000);
			createNewTask();
			setIntention(CtrlIntention.AI_INTENTION_ACTIVE);
		}
		else if(timerId == TIMER_DESPAWN)
			_thisActor.onDecay();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill != null && skill == SKILL_display)
		{
			L2Object obj = L2ObjectsStorage.findObject(lastAttacker);
			L2Player attacker = null;
			if(obj instanceof L2Playable)
				attacker = obj.getPlayer();

			if(attacker != null)
				for(int i = 0; i < reward_num; i++)
				{
					if(_thisActor.i_ai2 == 1)
						_thisActor.dropItem(attacker, 57, reward_adena);
					else if(_thisActor.i_ai2 == 2)
						_thisActor.dropItem(attacker, 57, reward_adena_small);
				}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		_thisActor.i_ai0 = 3;
		Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800839 : 1800840);
	}

	@Override
	protected boolean createNewTask()
	{
		if(runAway && lastAttackerLoc != null)
		{
			clearTasks();
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.loc = Util.getPointInRadius(_thisActor.getLoc(), 900, (int) (Util.calculateAngleFrom(_thisActor.getX(), _thisActor.getY(), lastAttackerLoc.getX(), lastAttackerLoc.getY()) + 180));
			_task_list.add(task);
			_def_think = true;
			_thisActor.stopHate();
			_thisActor.setRunning();
			return true;
		}

		return super.createNewTask();
	}

	@Override
	public void returnHome()
	{
		if(runAway)
			return;

		super.returnHome();
	}
}
