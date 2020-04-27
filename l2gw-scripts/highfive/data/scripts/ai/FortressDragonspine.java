package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressDragonspine extends DefaultAI
{
	private Location[] points = new Location[20];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressDragonspine(L2Character actor)
	{
		super(actor);
		points[0] = new Location(13577, 92516, -3487);
		points[1] = new Location(13787, 92216, -3567);
		points[2] = new Location(14192, 91690, -3681);
		points[3] = new Location(14338, 91238, -3695);
		points[4] = new Location(14432, 90798, -3709);
		points[5] = new Location(14695, 90056, -3849);
		points[6] = new Location(14739, 89638, -3868);
		points[7] = new Location(14150, 89778, -3882);
		points[8] = new Location(13167, 90002, -3868);
		points[9] = new Location(12482, 90078, -3842);
		points[10] = new Location(11659, 90114, -3776);
		points[11] = new Location(10499, 90133, -3710);
		points[12] = new Location(9776, 90110, -3661);
		points[13] = new Location(9196, 90105, -3619);
		points[14] = new Location(9175, 90553, -3605);
		points[15] = new Location(9162, 91167, -3649);
		points[16] = new Location(9212, 91942, -3653);
		points[17] = new Location(9304, 92295, -3553);
		points[18] = new Location(9493, 92653, -3478);
		points[19] = new Location(9871, 93007, -3457);
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