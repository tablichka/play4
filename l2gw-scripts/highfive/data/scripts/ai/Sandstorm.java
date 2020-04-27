package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 18.01.2010 19:19:14
 */
public class Sandstorm extends DefaultAI
{
	private long lastMove;
	private boolean arrived = false;
	private L2Skill[] _dam_skills;

	public Sandstorm(L2Character actor)
	{
		super(actor);
		_dam_skills = msum(_mdam_skills, _pdam_skills);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			return true;
		}

		if(arrived)
		{
			if(_dam_skills.length > 0)
			{
				Task task = new Task();
				task.type = TaskType.CAST;
				task.skill = _dam_skills[Rnd.get(_dam_skills.length)];
				task.loc = Location.coordsRandomize(_thisActor, Config.MAX_DRIFT_RANGE);
				_task_list.add(task);
				arrived = false;
				_def_think = true;
			}
			return true;
		}

		if(lastMove < System.currentTimeMillis())
		{
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = Location.coordsRandomize(_thisActor, Config.MAX_DRIFT_RANGE);
			_task_list.add(task);
			lastMove = System.currentTimeMillis() + Rnd.get(3000, 15000);
			arrived = false;
			_def_think = true;
		}
		return true;
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();
		arrived = true;
	}


	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{
	}
}
