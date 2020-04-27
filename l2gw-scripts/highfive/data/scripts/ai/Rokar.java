package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class Rokar extends DefaultAI
{
	private Location[] points = new Location[10];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Rokar(L2Character actor)
	{
		super(actor);
		points[0] = new Location(-46516, -117700, -264);
		points[1] = new Location(-45550, -115420, -256);
		points[2] = new Location(-44052, -114575, -256);
		points[3] = new Location(-44024, -112688, -256);
		points[4] = new Location(-45748, -111665, -256);
		points[5] = new Location(-46512, -109390, -232);
		points[6] = new Location(-45748, -111665, -256);
		points[7] = new Location(-44024, -112688, -256);
		points[8] = new Location(-44052, -114575, -256);
		points[9] = new Location(-45550, -115420, -256);
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
					case 5:
						wait_timeout = System.currentTimeMillis() + 30000;
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