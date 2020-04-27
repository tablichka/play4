package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class DaimonTheWhiteEyed extends DefaultAI
{
	private static Location[] points = {
			new Location(191276, -49556, -2960),
			new Location(193537, -47182, -2984),
			new Location(194317, -43736, -2872),
			new Location(193336, -42510, -2888),
			new Location(194633, -40843, -2872),
			new Location(194498, -39516, -2912),
			new Location(191985, -35868, -2904),
			new Location(190083, -35015, -2912),
			new Location(186256, -35136, -3072),
			new Location(186256, -35136, -3072),
			new Location(184477, -36749, -3080),
			new Location(180834, -37288, -3104),
			new Location(179653, -38946, -3176),
			new Location(179854, -42412, -3248),
			new Location(177627, -43341, -3336),
			new Location(177842, -45723, -3456),
			new Location(180459, -47145, -3256),
			new Location(175858, -51288, -3496),
			new Location(173028, -49337, -3520),
			new Location(171936, -46364, -3472),
			new Location(173074, -44264, -3488),
			new Location(172575, -42937, -3464),
			new Location(170964, -41753, -3464),
			new Location(170428, -39132, -3432)
	};

	private int current_point = 0;
	private long wait_timeout = 0;
	private boolean wait = false;

	public DaimonTheWhiteEyed(L2Character actor)
	{
		super(actor);
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

		if(System.currentTimeMillis() > wait_timeout && (current_point > 0 || Rnd.chance(5)))
		{
			if(!wait && current_point == points.length - 1)
			{
				wait_timeout = System.currentTimeMillis() + 5000;
				wait = true;
				return true;
			}

			if(wait)
			{
				wait = false;
				current_point = 0;
				_thisActor.teleToLocation(points[0]);
				return true;
			}

			// Добавить новое задание
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = points[current_point];
			_task_list.add(task);
			_def_think = true;
			current_point++;

			return true;
		}

		return randomAnimation();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}