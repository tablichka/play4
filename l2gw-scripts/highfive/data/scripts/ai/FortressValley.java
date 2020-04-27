package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressValley extends DefaultAI
{
	private Location[] points = new Location[17];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressValley(L2Character actor)
	{
		super(actor);

		points[0] = new Location(122467, 121082, -3159);
		points[1] = new Location(122826, 121018, -3094);
		points[2] = new Location(123133, 121052, -2986);
		points[3] = new Location(123382, 121065, -2894);
		points[4] = new Location(123600, 121110, -2773);
		points[5] = new Location(123943, 120997, -2686);
		points[6] = new Location(124811, 120672, -2609);
		points[7] = new Location(125291, 120435, -2611);
		points[8] = new Location(125748, 120296, -2613);
		points[9] = new Location(126077, 119997, -2638);
		points[10] = new Location(125997, 119351, -2826);
		points[11] = new Location(126066, 118540, -3108);
		points[12] = new Location(126318, 118153, -3123);
		points[13] = new Location(126520, 117815, -3137);
		points[14] = new Location(126688, 117172, -3378);
		points[15] = new Location(126712, 116420, -3631);
		points[16] = new Location(126767, 115950, -3739);

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