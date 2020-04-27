package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Jaradine extends DefaultAI
{
	private Location[] points = new Location[7];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Jaradine(L2Character actor)
	{
		super(actor);
		points[0] = new Location(44964, 50568, -3056);
		points[1] = new Location(44435, 50025, -3056);
		points[2] = new Location(44399, 49078, -3056);
		points[3] = new Location(45058, 48437, -3056);
		points[4] = new Location(46132, 48724, -3056);
		points[5] = new Location(46452, 49743, -3056);
		points[6] = new Location(45730, 50590, -3056);
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
					case 3:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010208);
						wait = true;
						return true;
					case 4:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010209);
						wait = true;
						return true;
					case 6:
						wait_timeout = System.currentTimeMillis() + 60000;
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