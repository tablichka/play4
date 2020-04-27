package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressTanor extends DefaultAI
{
	private Location[] points = new Location[43];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressTanor(L2Character actor)
	{
		super(actor);

		points[0] = new Location(61589, 141590, -1792);
		points[1] = new Location(61617, 142087, -1861);
		points[2] = new Location(61912, 142485, -1943);
		points[3] = new Location(62284, 142661, -1992);
		points[4] = new Location(62659, 142610, -2047);
		points[5] = new Location(62832, 142431, -2105);
		points[6] = new Location(63008, 142049, -2221);
		points[7] = new Location(63195, 141533, -2380);
		points[8] = new Location(63316, 141157, -2500);
		points[9] = new Location(63582, 140791, -2662);
		points[10] = new Location(63834, 140444, -2826);
		points[11] = new Location(64083, 139833, -3052);
		points[12] = new Location(64341, 139208, -3263);
		points[13] = new Location(64529, 138264, -3522);
		points[14] = new Location(64807, 137323, -3711);
		points[15] = new Location(64803, 136598, -3716);
		points[16] = new Location(64779, 135946, -3707);
		points[17] = new Location(64650, 135316, -3701);
		points[18] = new Location(64248, 134846, -3739);
		points[19] = new Location(63460, 134774, -3680);
		points[20] = new Location(62649, 134620, -3622);
		points[21] = new Location(61766, 134358, -3548);
		points[22] = new Location(61165, 134145, -3472);
		points[23] = new Location(60487, 134060, -3401);
		points[24] = new Location(59781, 134094, -3278);
		points[25] = new Location(58868, 134244, -3155);
		points[26] = new Location(58188, 134506, -3056);
		points[27] = new Location(57783, 134897, -2977);
		points[28] = new Location(57075, 135509, -2875);
		points[29] = new Location(56393, 136087, -2745);
		points[30] = new Location(55890, 136536, -2645);
		points[31] = new Location(55171, 136979, -2620);
		points[32] = new Location(54975, 137433, -2589);
		points[33] = new Location(55554, 137396, -2486);
		points[34] = new Location(56035, 137319, -2382);
		points[35] = new Location(56760, 137192, -2300);
		points[36] = new Location(57289, 136976, -2212);
		points[37] = new Location(57650, 136696, -2132);
		points[38] = new Location(58123, 136331, -2064);
		points[39] = new Location(58512, 136142, -2010);
		points[40] = new Location(58759, 136394, -1956);
		points[41] = new Location(58814, 136750, -1901);
		points[42] = new Location(58852, 137090, -1832);

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