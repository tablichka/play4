package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressIvory extends DefaultAI
{
	private Location[] points = new Location[34];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressIvory(L2Character actor)
	{
		super(actor);

		points[0] = new Location(71366, 6805, -3084);
		points[1] = new Location(71701, 7406, -3183);
		points[2] = new Location(72047, 7851, -3248);
		points[3] = new Location(72346, 8089, -3289);
		points[4] = new Location(72856, 8373, -3343);
		points[5] = new Location(73298, 8500, -3388);
		points[6] = new Location(74195, 8700, -3507);
		points[7] = new Location(75002, 8858, -3603);
		points[8] = new Location(75803, 9223, -3601);
		points[9] = new Location(76411, 9352, -3599);
		points[10] = new Location(77246, 9238, -3587);
		points[11] = new Location(77988, 9135, -3562);
		points[12] = new Location(78459, 9028, -3544);
		points[13] = new Location(79111, 8663, -3548);
		points[14] = new Location(79917, 8328, -3533);
		points[15] = new Location(80561, 8071, -3520);
		points[16] = new Location(81281, 7840, -3464);
		points[17] = new Location(82002, 7574, -3404);
		points[18] = new Location(82527, 7236, -3340);
		points[19] = new Location(82253, 6864, -3243);
		points[20] = new Location(81793, 6526, -3186);
		points[21] = new Location(81348, 6167, -3166);
		points[22] = new Location(80895, 5833, -3144);
		points[23] = new Location(80369, 5524, -3128);
		points[24] = new Location(79693, 5042, -3113);
		points[25] = new Location(79100, 4552, -3114);
		points[26] = new Location(78627, 4102, -3147);
		points[27] = new Location(77693, 3144, -3463);
		points[28] = new Location(77135, 2419, -3662);
		points[29] = new Location(76796, 1547, -3622);
		points[30] = new Location(76264, 1382, -3507);
		points[31] = new Location(75590, 1363, -3360);
		points[32] = new Location(74955, 1596, -3216);
		points[33] = new Location(74617, 2032, -3102);

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