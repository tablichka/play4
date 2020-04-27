package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressShanty extends DefaultAI
{
	private Location[] points = new Location[32];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressShanty(L2Character actor)
	{

		super(actor);

		points[0] = new Location(-50013, 155631, -2086);
		points[1] = new Location(-49555, 155490, -2140);
		points[2] = new Location(-49238, 155328, -2208);
		points[3] = new Location(-49003, 155077, -2287);
		points[4] = new Location(-48983, 154855, -2322);
		points[5] = new Location(-49140, 154503, -2397);
		points[6] = new Location(-49448, 154033, -2524);
		points[7] = new Location(-49723, 153513, -2601);
		points[8] = new Location(-49874, 153036, -2635);
		points[9] = new Location(-50132, 152477, -2739);
		points[10] = new Location(-50367, 151942, -2801);
		points[11] = new Location(-50734, 151553, -2787);
		points[12] = new Location(-51108, 151472, -2672);
		points[13] = new Location(-51499, 151523, -2598);
		points[14] = new Location(-52079, 151781, -2602);
		points[15] = new Location(-52683, 152237, -2612);
		points[16] = new Location(-53230, 152690, -2616);
		points[17] = new Location(-53679, 153078, -2591);
		points[18] = new Location(-54317, 153512, -2610);
		points[19] = new Location(-54976, 153889, -2604);
		points[20] = new Location(-55625, 154301, -2576);
		points[21] = new Location(-56289, 154584, -2569);
		points[22] = new Location(-57122, 154602, -2584);
		points[23] = new Location(-57877, 154547, -2654);
		points[24] = new Location(-58535, 154688, -2707);
		points[25] = new Location(-58345, 155276, -2762);
		points[26] = new Location(-57918, 156010, -2703);
		points[27] = new Location(-57392, 156646, -2552);
		points[28] = new Location(-56924, 156792, -2439);
		points[29] = new Location(-56502, 156835, -2319);
		points[30] = new Location(-56087, 156937, -2193);
		points[31] = new Location(-55631, 157041, -2105);

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
		_thisActor.setWalking();
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