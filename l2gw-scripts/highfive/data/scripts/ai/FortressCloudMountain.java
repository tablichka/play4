package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressCloudMountain extends DefaultAI
{
	private Location[] points = new Location[26];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressCloudMountain(L2Character actor)
	{
		super(actor);
		points[0] = new Location(-60372, 91641, -3102);
		points[1] = new Location(-61145, 91602, -3166);
		points[2] = new Location(-61636, 91409, -3218);
		points[3] = new Location(-61948, 90914, -3249);
		points[4] = new Location(-61822, 90422, -3268);
		points[5] = new Location(-61133, 89752, -3389);
		points[6] = new Location(-60738, 89279, -3440);
		points[7] = new Location(-60662, 88876, -3461);
		points[8] = new Location(-60578, 88142, -3562);
		points[9] = new Location(-60087, 87781, -3643);
		points[10] = new Location(-59544, 87437, -3712);
		points[11] = new Location(-59221, 86719, -3771);
		points[12] = new Location(-59022, 85911, -3797);
		points[13] = new Location(-58950, 85059, -3792);
		points[14] = new Location(-58281, 85206, -3736);
		points[15] = new Location(-57658, 85397, -3703);
		points[16] = new Location(-57074, 85554, -3662);
		points[17] = new Location(-56498, 85810, -3635);
		points[18] = new Location(-56121, 85874, -3626);
		points[19] = new Location(-55834, 86090, -3611);
		points[20] = new Location(-55471, 86434, -3512);
		points[21] = new Location(-55284, 86640, -3422);
		points[22] = new Location(-55261, 87137, -3229);
		points[23] = new Location(-55242, 87685, -3039);
		points[24] = new Location(-55142, 88088, -2953);
		points[25] = new Location(-54916, 88455, -2878);
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