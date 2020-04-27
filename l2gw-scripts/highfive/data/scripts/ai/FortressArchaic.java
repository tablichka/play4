package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressArchaic extends DefaultAI
{
	private Location[] points = new Location[34];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressArchaic(L2Character actor)
	{
		super(actor);
		points[0] = new Location(106813, -140205, -3030);
		points[1] = new Location(106501, -139965, -3058);
		points[2] = new Location(106066, -139789, -3101);
		points[3] = new Location(105537, -139880, -3163);
		points[4] = new Location(105271, -140073, -3230);
		points[5] = new Location(105095, -140381, -3293);
		points[6] = new Location(105072, -140995, -3432);
		points[7] = new Location(105074, -141619, -3560);
		points[8] = new Location(105325, -142381, -3683);
		points[9] = new Location(105905, -142723, -3683);
		points[10] = new Location(106325, -142956, -3666);
		points[11] = new Location(106908, -143538, -3686);
		points[12] = new Location(107573, -144321, -3686);
		points[13] = new Location(108129, -144814, -3686);
		points[14] = new Location(108851, -145093, -3688);
		points[15] = new Location(109522, -145427, -3632);
		points[16] = new Location(110043, -145907, -3514);
		points[17] = new Location(110567, -146324, -3416);
		points[18] = new Location(111256, -146781, -3354);
		points[19] = new Location(111987, -147177, -3323);
		points[20] = new Location(112444, -147015, -3296);
		points[21] = new Location(112539, -146430, -3295);
		points[22] = new Location(112685, -145669, -3295);
		points[23] = new Location(112890, -144737, -3295);
		points[24] = new Location(113694, -144094, -3292);
		points[25] = new Location(114235, -143741, -3234);
		points[26] = new Location(114227, -143367, -3229);
		points[27] = new Location(113899, -142903, -3277);
		points[28] = new Location(113368, -142358, -3277);
		points[29] = new Location(112775, -141713, -3271);
		points[30] = new Location(112635, -141152, -3185);
		points[31] = new Location(112392, -140651, -3101);
		points[32] = new Location(112133, -140637, -3031);
		points[33] = new Location(111941, -140924, -2997);
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