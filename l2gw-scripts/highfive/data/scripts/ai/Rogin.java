package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Rogin extends DefaultAI
{
	private Location[] points = new Location[10];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Rogin(L2Character actor)
	{
		super(actor);
		points[0] = new Location(115756, -183472, -1480);
		points[1] = new Location(115866, -183287, -1480);
		points[2] = new Location(116280, -182918, -1520);
		points[3] = new Location(116587, -184306, -1568);
		points[4] = new Location(116392, -184090, -1560);
		points[5] = new Location(117083, -182538, -1528);
		points[6] = new Location(117802, -182541, -1528);
		points[7] = new Location(116720, -182479, -1528);
		points[8] = new Location(115857, -183295, -1480);
		points[9] = new Location(115756, -183472, -1480);
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
						Functions.npcSay(_thisActor, Say2C.ALL, 1010215);
						wait = true;
						return true;
					case 6:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010216);
						wait = true;
						return true;
					case 7:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010217);
						wait = true;
						return true;
					case 8:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010218);
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