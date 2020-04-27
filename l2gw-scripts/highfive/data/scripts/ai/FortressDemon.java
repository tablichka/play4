package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressDemon extends DefaultAI
{
	private Location[] points = new Location[45];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressDemon(L2Character actor)
	{
		super(actor);
		points[0] = new Location(98429, -56906, -819);
		points[1] = new Location(98120, -57178, -927);
		points[2] = new Location(97831, -57436, -1043);
		points[3] = new Location(97466, -57602, -1152);
		points[4] = new Location(97157, -57817, -1256);
		points[5] = new Location(97050, -58071, -1343);
		points[6] = new Location(97036, -58647, -1555);
		points[7] = new Location(96970, -58930, -1652);
		points[8] = new Location(96814, -59072, -1709);
		points[9] = new Location(96213, -59076, -1969);
		points[10] = new Location(95916, -59262, -2120);
		points[11] = new Location(95446, -59655, -2349);
		points[12] = new Location(95184, -60023, -2450);
		points[13] = new Location(95723, -60436, -2503);
		points[14] = new Location(96544, -60679, -2572);
		points[15] = new Location(97256, -60888, -2641);
		points[16] = new Location(97838, -60928, -2698);
		points[17] = new Location(98800, -60646, -2804);
		points[18] = new Location(99712, -60272, -2846);
		points[19] = new Location(100431, -60085, -2777);
		points[20] = new Location(100967, -59997, -2663);
		points[21] = new Location(101548, -59969, -2563);
		points[22] = new Location(102182, -59920, -2488);
		points[23] = new Location(102735, -59890, -2424);
		points[24] = new Location(103305, -60186, -2415);
		points[25] = new Location(104053, -60638, -2466);
		points[26] = new Location(104630, -60905, -2554);
		points[27] = new Location(105357, -61337, -2707);
		points[28] = new Location(106231, -61788, -2925);
		points[29] = new Location(107002, -62101, -3029);
		points[30] = new Location(107754, -62403, -3096);
		points[31] = new Location(108847, -62670, -3239);
		points[32] = new Location(108535, -62200, -3115);
		points[33] = new Location(107895, -61530, -2674);
		points[34] = new Location(107690, -61276, -2479);
		points[35] = new Location(107497, -60608, -2216);
		points[36] = new Location(107227, -59941, -1907);
		points[37] = new Location(106915, -59526, -1688);
		points[38] = new Location(106355, -59319, -1436);
		points[39] = new Location(105984, -59206, -1332);
		points[40] = new Location(105479, -58659, -1210);
		points[41] = new Location(105115, -58083, -1069);
		points[42] = new Location(104431, -57381, -935);
		points[43] = new Location(104129, -57096, -877);
		points[44] = new Location(103575, -56683, -807);
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