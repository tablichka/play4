package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class GhostOfVonHellmannsPage extends DefaultAI
{
	private Location[] points = new Location[7];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public GhostOfVonHellmannsPage(L2Character actor)
	{
		super(actor);
		points[0] = new Location(51462, -54539, -3176);
		points[1] = new Location(51870, -54398, -3176);
		points[2] = new Location(52164, -53964, -3176);
		points[3] = new Location(52390, -53282, -3176);
		points[4] = new Location(52058, -52071, -3104);
		points[5] = new Location(52237, -51483, -3112);
		points[6] = new Location(52024, -51262, -3096);
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
					case 6:
						wait_timeout = System.currentTimeMillis() + 15000;
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