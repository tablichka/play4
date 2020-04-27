package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressWestern extends DefaultAI
{
	private Location[] points = new Location[32];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressWestern(L2Character actor)
	{
		super(actor);

		points[0] = new Location(118716, -19990, -2691);
		points[1] = new Location(118265, -20062, -2614);
		points[2] = new Location(117863, -19974, -2544);
		points[3] = new Location(117222, -19720, -2459);
		points[4] = new Location(116403, -19398, -2250);
		points[5] = new Location(115397, -18987, -2072);
		points[6] = new Location(114794, -18850, -1932);
		points[7] = new Location(114318, -18745, -1811);
		points[8] = new Location(113485, -18735, -1772);
		points[9] = new Location(112646, -18802, -1729);
		points[10] = new Location(112089, -19398, -1717);
		points[11] = new Location(111518, -20004, -1596);
		points[12] = new Location(111136, -20159, -1537);
		points[13] = new Location(110609, -20088, -1434);
		points[14] = new Location(110113, -20052, -1394);
		points[15] = new Location(109643, -19529, -1357);
		points[16] = new Location(109416, -18863, -1213);
		points[17] = new Location(109138, -18037, -1085);
		points[18] = new Location(109004, -17533, -1013);
		points[19] = new Location(109117, -17246, -1023);
		points[20] = new Location(110029, -17226, -1023);
		points[21] = new Location(111367, -17230, -1023);
		points[22] = new Location(113084, -17212, -1023);
		points[23] = new Location(114010, -17193, -1023);
		points[24] = new Location(114686, -17195, -1026);
		points[25] = new Location(115248, -17333, -1113);
		points[26] = new Location(115765, -17594, -1259);
		points[27] = new Location(116207, -17899, -1424);
		points[28] = new Location(116865, -18166, -1692);
		points[29] = new Location(117564, -18684, -2081);
		points[30] = new Location(118197, -19322, -2445);
		points[31] = new Location(118721, -19853, -2661);

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