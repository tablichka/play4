package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class Edwin extends DefaultAI
{
	private Location[] points = new Location[17];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Edwin(L2Character actor)
	{
		super(actor);
		points[0] = new Location(89991, -144601, -1467); // start
		points[1] = new Location(90538, -143470, -1467);
		points[2] = new Location(90491, -142848, -1467);
		points[3] = new Location(89563, -141455, -1467);
		points[4] = new Location(89138, -140621, -1467);
		points[5] = new Location(87459, -140192, -1467);
		points[6] = new Location(85625, -140699, -1467);
		points[7] = new Location(84538, -142382, -1467);
		points[8] = new Location(84527, -143913, -1467); // finish
		points[9] = new Location(84538, -142382, -1467);
		points[10] = new Location(85625, -140699, -1467);
		points[11] = new Location(87459, -140192, -1467);
		points[12] = new Location(89138, -140621, -1467);
		points[13] = new Location(89563, -141455, -1467);
		points[14] = new Location(90491, -142848, -1467);
		points[15] = new Location(90538, -143470, -1467);
		points[16] = new Location(89991, -144601, -1467); // start
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
			if(!wait)
				switch(current_point)
				{
					case 0:
						wait_timeout = System.currentTimeMillis() + 10000;
						wait = true;
						return true;
					case 8:
						wait_timeout = System.currentTimeMillis() + 10000;
						wait = true;
						return true;
				}

			wait_timeout = 0;
			wait = false;

			if(current_point >= points.length - 1)
				current_point = -1;

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

		if(randomAnimation())
			return true;

		return false;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}