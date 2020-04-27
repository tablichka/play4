package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Remy extends DefaultAI
{
	private Location[] points = new Location[17];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Remy(L2Character actor)
	{
		super(actor);
		points[0] = new Location(-81926, 243894, -3712);
		points[1] = new Location(-82134, 243600, -3728);
		points[2] = new Location(-83165, 243987, -3728);
		points[3] = new Location(-84501, 243245, -3728);
		points[4] = new Location(-85100, 243285, -3728);
		points[5] = new Location(-86152, 242898, -3728);
		points[6] = new Location(-86288, 242962, -3720);
		points[7] = new Location(-86348, 243223, -3720);
		points[8] = new Location(-86522, 242762, -3720);
		points[9] = new Location(-86500, 242615, -3728);
		points[10] = new Location(-86123, 241606, -3728);
		points[11] = new Location(-85167, 240589, -3728);
		points[12] = new Location(-84323, 241245, -3728);
		points[13] = new Location(-83215, 241170, -3728);
		points[14] = new Location(-82364, 242944, -3728);
		points[15] = new Location(-81674, 243391, -3712);
		points[16] = new Location(-81926, 243894, -3712);
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
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010201);
						wait = true;
						return true;
					case 3:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010202);
						wait = true;
						return true;
					case 7:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010203);
						wait = true;
						return true;
					case 12:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010204);
						wait = true;
						return true;
					case 15:
						wait_timeout = System.currentTimeMillis() + 60000;
						wait = true;
						return true;
				}

			wait_timeout = 0;
			wait = false;

			if(current_point >= points.length - 1)
				current_point = -1;

			current_point++;

			// Remy всегда бегает
			_thisActor.setRunning();

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