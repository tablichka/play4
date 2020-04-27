package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

import java.lang.ref.WeakReference;

/**
 * @author rage
 * @date 25.10.2010 11:13:13
 */
public class HBBodyDestroyer extends Fighter
{
	private static final L2Skill _deathClock = SkillTable.getInstance().getInfo(5256, 1);
	private static final L2Skill _killPc = SkillTable.getInstance().getInfo(5257, 1);

	private WeakReference<L2Player> _firstAttacker;
	private boolean _castKillPc;

	public HBBodyDestroyer(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_firstAttacker = null;
		_castKillPc = false;
	}

	@Override
	protected boolean createNewTask()
	{
		// Удаляем все задания
		clearTasks();
		L2Character hated =  _thisActor.getMostHated();

		if(_firstAttacker == null && hated != null && hated.getPlayer() != null)
		{
			_firstAttacker = new WeakReference<L2Player>(getAttackTarget().getPlayer());
			addUseSkillDesire(hated.getPlayer(), _deathClock, 1, 1, DEFAULT_DESIRE * 100);
			return true;
		}
		else if(_firstAttacker != null && _castKillPc)
		{
			L2Player target = _firstAttacker.get();
			if(target != null && !target.isDead() && _thisActor.knowsObject(target))
			{
				addUseSkillDesire(target, _killPc, 1, 1, DEFAULT_DESIRE * 100);
				return true;
			}
			else
				_castKillPc = false;
		}

		return super.createNewTask();
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == _deathClock)
		{
			if(getAttackTarget() != null && getAttackTarget().getPlayer() != null)
				addTimer(1, 29000);
		}
		else if(skill == _killPc)
		{
			L2Player target = _firstAttacker.get();
			if(target != null && !target.isDead() && _thisActor.knowsObject(target))
			{
				target.doDie(_thisActor);
				_castKillPc = false;
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1)
		{
			L2Player target = _firstAttacker.get();
			if(target != null && !target.isDead() && _thisActor.knowsObject(target) && target.getEffectBySkill(_deathClock) != null)
				_castKillPc = true;
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
