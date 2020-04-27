package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressHive extends DefaultAI
{
	private Location[] points = new Location[39];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressHive(L2Character actor)
	{
		super(actor);

		points[0] = new Location(15230, 185636, -2955);
		points[1] = new Location(15183, 185452, -2970);
		points[2] = new Location(15173, 185114, -3001);
		points[3] = new Location(15218, 184658, -3103);
		points[4] = new Location(15369, 183805, -3361);
		points[5] = new Location(15348, 183276, -3510);
		points[6] = new Location(15362, 182794, -3619);
		points[7] = new Location(15025, 182676, -3614);
		points[8] = new Location(14520, 183395, -3591);
		points[9] = new Location(14196, 183975, -3592);
		points[10] = new Location(13848, 184659, -3549);
		points[11] = new Location(13676, 185125, -3546);
		points[12] = new Location(13530, 185666, -3577);
		points[13] = new Location(13163, 186211, -3619);
		points[14] = new Location(12717, 186817, -3691);
		points[15] = new Location(12347, 187463, -3747);
		points[16] = new Location(12072, 188043, -3753);
		points[17] = new Location(11942, 188561, -3736);
		points[18] = new Location(12204, 189197, -3708);
		points[19] = new Location(12611, 189546, -3646);
		points[20] = new Location(13481, 190181, -3646);
		points[21] = new Location(14491, 190886, -3660);
		points[22] = new Location(15266, 191304, -3660);
		points[23] = new Location(16143, 191686, -3648);
		points[24] = new Location(16677, 191857, -3652);
		points[25] = new Location(18123, 192247, -3765);
		points[26] = new Location(18976, 192271, -3770);
		points[27] = new Location(19756, 192104, -3749);
		points[28] = new Location(20800, 191342, -3698);
		points[29] = new Location(20971, 190816, -3624);
		points[30] = new Location(21053, 190264, -3545);
		points[31] = new Location(21025, 189855, -3458);
		points[32] = new Location(20739, 189305, -3406);
		points[33] = new Location(20278, 188412, -3420);
		points[34] = new Location(20040, 188045, -3433);
		points[35] = new Location(19827, 188419, -3398);
		points[36] = new Location(19428, 189322, -3183);
		points[37] = new Location(19215, 189602, -3103);
		points[38] = new Location(18749, 189999, -3004);

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