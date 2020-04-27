package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressWhiteSands extends DefaultAI
{
	private Location[] points = new Location[38];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressWhiteSands(L2Character actor)
	{
		super(actor);

		points[0] = new Location(116036, 203550, -3353);
		points[1] = new Location(115809, 203397, -3346);
		points[2] = new Location(115256, 203038, -3456);
		points[3] = new Location(114860, 202792, -3422);
		points[4] = new Location(114506, 202473, -3454);
		points[5] = new Location(114139, 202000, -3513);
		points[6] = new Location(113981, 201328, -3603);
		points[7] = new Location(113841, 200427, -3752);
		points[8] = new Location(114340, 200209, -3752);
		points[9] = new Location(115250, 200013, -3737);
		points[10] = new Location(116126, 199860, -3692);
		points[11] = new Location(116871, 199911, -3676);
		points[12] = new Location(117643, 200138, -3631);
		points[13] = new Location(118246, 200402, -3613);
		points[14] = new Location(119044, 200686, -3619);
		points[15] = new Location(119760, 200913, -3644);
		points[16] = new Location(120486, 200840, -3625);
		points[17] = new Location(121192, 200764, -3535);
		points[18] = new Location(122242, 200677, -3337);
		points[19] = new Location(122903, 201027, -3188);
		points[20] = new Location(123077, 201714, -3153);
		points[21] = new Location(123142, 202331, -3152);
		points[22] = new Location(122859, 203033, -3257);
		points[23] = new Location(122488, 203977, -3473);
		points[24] = new Location(122498, 204529, -3584);
		points[25] = new Location(123238, 205085, -3605);
		points[26] = new Location(123878, 205361, -3610);
		points[27] = new Location(124169, 205897, -3557);
		points[28] = new Location(124800, 206508, -3497);
		points[29] = new Location(124990, 206954, -3401);
		points[30] = new Location(124663, 207496, -3262);
		points[31] = new Location(124163, 207676, -3264);
		points[32] = new Location(123419, 207582, -3307);
		points[33] = new Location(122473, 207525, -3388);
		points[34] = new Location(122005, 207419, -3394);
		points[35] = new Location(121566, 207127, -3439);
		points[36] = new Location(121211, 206805, -3353);
		points[37] = new Location(120872, 206472, -3361);

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