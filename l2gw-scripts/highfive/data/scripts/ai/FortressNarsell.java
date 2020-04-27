package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressNarsell extends DefaultAI
{
	private Location[] points = new Location[39];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressNarsell(L2Character actor)
	{
		super(actor);

		points[0] = new Location(158408, 52670, -3291);
		points[1] = new Location(158751, 52609, -3300);
		points[2] = new Location(159198, 52474, -3325);
		points[3] = new Location(159548, 52662, -3364);
		points[4] = new Location(160115, 53145, -3474);
		points[5] = new Location(160760, 53725, -3571);
		points[6] = new Location(161331, 54146, -3615);
		points[7] = new Location(161820, 54364, -3646);
		points[8] = new Location(162250, 54523, -3671);
		points[9] = new Location(162501, 55095, -3703);
		points[10] = new Location(162701, 55854, -3730);
		points[11] = new Location(162798, 56595, -3719);
		points[12] = new Location(162744, 57230, -3662);
		points[13] = new Location(162549, 58098, -3604);
		points[14] = new Location(161879, 58787, -3484);
		points[15] = new Location(161383, 58990, -3417);
		points[16] = new Location(160908, 59229, -3365);
		points[17] = new Location(160430, 59669, -3304);
		points[18] = new Location(160007, 60198, -3250);
		points[19] = new Location(159547, 60793, -3224);
		points[20] = new Location(159088, 61337, -3281);
		points[21] = new Location(158667, 61973, -3413);
		points[22] = new Location(158226, 62460, -3483);
		points[23] = new Location(157742, 63112, -3538);
		points[24] = new Location(157132, 63389, -3550);
		points[25] = new Location(156462, 63639, -3558);
		points[26] = new Location(155802, 63676, -3566);
		points[27] = new Location(155215, 63748, -3585);
		points[28] = new Location(154216, 64393, -3670);
		points[29] = new Location(153696, 64312, -3672);
		points[30] = new Location(153427, 63872, -3669);
		points[31] = new Location(153088, 62569, -3730);
		points[32] = new Location(152651, 61587, -3692);
		points[33] = new Location(152142, 60736, -3621);
		points[34] = new Location(151827, 60159, -3573);
		points[35] = new Location(151272, 59447, -3520);
		points[36] = new Location(150969, 58825, -3466);
		points[37] = new Location(151271, 58420, -3407);
		points[38] = new Location(151616, 58115, -3322);

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