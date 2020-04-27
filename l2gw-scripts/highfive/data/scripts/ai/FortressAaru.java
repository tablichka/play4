package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressAaru extends DefaultAI
{
	private Location[] points = new Location[20];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressAaru(L2Character actor)
	{
		super(actor);
		points[0] = new Location(73416, 183390, -2662);
		points[1] = new Location(73658, 182822, -2802);
		points[2] = new Location(74161, 182041, -3105);
		points[3] = new Location(74181, 181742, -3179);
		points[4] = new Location(73786, 181617, -3198);
		points[5] = new Location(73035, 181906, -3213);
		points[6] = new Location(72308, 182076, -3131);
		points[7] = new Location(71331, 182231, -3059);
		points[8] = new Location(70619, 182550, -3021);
		points[9] = new Location(70128, 183145, -3006);
		points[10] = new Location(69533, 183866, -3008);
		points[11] = new Location(69070, 184490, -3009);
		points[12] = new Location(68726, 185100, -3013);
		points[13] = new Location(68643, 185827, -3015);
		points[14] = new Location(68917, 186364, -3031);
		points[15] = new Location(69361, 186922, -3031);
		points[16] = new Location(69870, 187289, -3017);
		points[17] = new Location(70491, 187517, -2905);
		points[18] = new Location(71138, 187783, -2773);
		points[19] = new Location(71678, 187965, -2654);
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