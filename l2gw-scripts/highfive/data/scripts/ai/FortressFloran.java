package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressFloran extends DefaultAI
{
	private Location[] points = new Location[15];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressFloran(L2Character actor)
	{
		super(actor);
		points[0] = new Location(10813, 152753, -3065);
		points[1] = new Location(10749, 152205, -3146);
		points[2] = new Location(10730, 151427, -3317);
		points[3] = new Location(10764, 150324, -3247);
		points[4] = new Location(10431, 149822, -3240);
		points[5] = new Location(10047, 149115, -3237);
		points[6] = new Location(9905, 148469, -3232);
		points[7] = new Location(9550, 147068, -3358);
		points[8] = new Location(9192, 145773, -3314);
		points[9] = new Location(8866, 145389, -3308);
		points[10] = new Location(8258, 145128, -3343);
		points[11] = new Location(7673, 144886, -3438);
		points[12] = new Location(6989, 144416, -3555);
		points[13] = new Location(6332, 143766, -3652);
		points[14] = new Location(6023, 143682, -3705);

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

		if(System.currentTimeMillis() > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			wait_timeout = 0;


			if(current_point >= points.length - 1)
			{
				current_point = 0;
				_thisActor.teleToLocation(points[0]);
				return true;
			}

			current_point++;

			// Добавить новое задание
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = points[current_point];
			_task_list.add(task);
			_def_think = true;
			return true;
		}

		return randomAnimation();
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