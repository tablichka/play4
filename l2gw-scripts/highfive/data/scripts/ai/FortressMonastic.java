package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressMonastic extends DefaultAI
{
	private Location[] points = new Location[21];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressMonastic(L2Character actor)
	{
		super(actor);

		points[0] = new Location(68574, -91517, -1662);
		points[1] = new Location(68811, -91573, -1599);
		points[2] = new Location(69088, -91733, -1518);
		points[3] = new Location(69402, -91857, -1477);
		points[4] = new Location(69739, -91745, -1543);
		points[5] = new Location(69805, -91241, -1678);
		points[6] = new Location(70203, -90679, -1865);
		points[7] = new Location(70605, -90154, -2100);
		points[8] = new Location(70913, -89698, -2286);
		points[9] = new Location(71278, -89187, -2399);
		points[10] = new Location(71833, -89727, -2337);
		points[11] = new Location(72497, -90460, -2274);
		points[12] = new Location(72857, -90914, -2209);
		points[13] = new Location(73439, -91369, -2085);
		points[14] = new Location(74084, -91865, -1936);
		points[15] = new Location(74515, -92222, -1800);
		points[16] = new Location(74768, -92695, -1652);
		points[17] = new Location(74824, -93053, -1581);
		points[18] = new Location(75371, -93433, -1616);
		points[19] = new Location(75392, -93863, -1570);
		points[20] = new Location(75370, -94417, -1528);

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