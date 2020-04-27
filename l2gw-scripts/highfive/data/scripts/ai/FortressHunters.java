package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressHunters extends DefaultAI
{
	private Location[] points = new Location[19];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressHunters(L2Character actor)
	{
		super(actor);

		points[0] = new Location(120461, 94656, -2384);
		points[1] = new Location(120326, 93892, -2439);
		points[2] = new Location(120122, 93529, -2529);
		points[3] = new Location(119803, 93102, -2765);
		points[4] = new Location(119627, 92620, -2965);
		points[5] = new Location(120024, 92781, -2863);
		points[6] = new Location(120418, 92965, -2794);
		points[7] = new Location(121240, 93049, -2729);
		points[8] = new Location(121572, 92952, -2614);
		points[9] = new Location(121973, 92706, -2515);
		points[10] = new Location(122367, 92557, -2398);
		points[11] = new Location(122738, 92501, -2353);
		points[12] = new Location(123367, 92484, -2324);
		points[13] = new Location(123926, 92501, -2310);
		points[14] = new Location(124678, 92403, -2314);
		points[15] = new Location(125368, 92277, -2309);
		points[16] = new Location(125819, 92345, -2287);
		points[17] = new Location(126600, 92714, -2251);
		points[18] = new Location(127020, 92477, -2230);

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