package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressAntharas extends DefaultAI
{
	private Location[] points = new Location[36];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressAntharas(L2Character actor)
	{
		super(actor);
		points[0] = new Location(77596, 89244, -2913);
		points[1] = new Location(77435, 89736, -2915);
		points[2] = new Location(77224, 90287, -2920);
		points[3] = new Location(76746, 90836, -2929);
		points[4] = new Location(76254, 91341, -2937);
		points[5] = new Location(75838, 91750, -2930);
		points[6] = new Location(75620, 92103, -2947);
		points[7] = new Location(75441, 92859, -3098);
		points[8] = new Location(75283, 92956, -3118);
		points[9] = new Location(75084, 92861, -3133);
		points[10] = new Location(74890, 91649, -3316);
		points[11] = new Location(74904, 90852, -3351);
		points[12] = new Location(75232, 90298, -3287);
		points[13] = new Location(75571, 89869, -3217);
		points[14] = new Location(76145, 89157, -3232);
		points[15] = new Location(76772, 88433, -3289);
		points[16] = new Location(77600, 87801, -3393);
		points[17] = new Location(77974, 87233, -3459);
		points[18] = new Location(78200, 86387, -3598);
		points[19] = new Location(78405, 85799, -3647);
		points[20] = new Location(78824, 85902, -3628);
		points[21] = new Location(79725, 85953, -3611);
		points[22] = new Location(80895, 85898, -3505);
		points[23] = new Location(81218, 86162, -3470);
		points[24] = new Location(81874, 86841, -3425);
		points[25] = new Location(82565, 87412, -3387);
		points[26] = new Location(83330, 87989, -3386);
		points[27] = new Location(83713, 88398, -3400);
		points[28] = new Location(83715, 89247, -3299);
		points[29] = new Location(83599, 89937, -3176);
		points[30] = new Location(83460, 90367, -3135);
		points[31] = new Location(82888, 90501, -3032);
		points[32] = new Location(82153, 90529, -2925);
		points[33] = new Location(81804, 90853, -2914);
		points[34] = new Location(80870, 92254, -2914);
		points[35] = new Location(80428, 93129, -2915);
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