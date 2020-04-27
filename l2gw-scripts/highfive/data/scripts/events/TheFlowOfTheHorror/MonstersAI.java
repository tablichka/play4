package events.TheFlowOfTheHorror;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.util.Location;

public class MonstersAI extends Fighter
{
	private Location[] _points = new Location[14];
	private int current_point = -1;

	public void setPoints(Location[] points)
	{
		_points = points;
	}

	public MonstersAI(L2Character actor)
	{
		super(actor);
		MAX_PURSUE_RANGE = 30000;
		AI_TASK_DELAY = 500;
		MAX_ATTACK_TIMEOUT = Integer.MAX_VALUE;
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

		if(_globalAggro < 0)
			_globalAggro++;
		else if(_globalAggro > 0)
			_globalAggro--;

		if(_def_think)
		{
			doTask();
			return true;
		}

		if(current_point > -1 || Rnd.chance(5))
		{
			if(current_point >= _points.length - 1)
			{
				L2Character target = L2ObjectsStorage.getByNpcId(30754);
				if(target != null && !target.isDead())
				{
					clearTasks();
					// TODO _thisActor.addDamageHate(target, 0, 1000);
					setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
					return true;
				}
				return true;
			}

			current_point++;

			_thisActor.setRunning();

			clearTasks();

			// Добавить новое задание
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.loc = _points[current_point];
			_task_list.add(task);
			_def_think = true;
			return true;
		}

		if(randomAnimation())
			return true;

		return false;
	}
}