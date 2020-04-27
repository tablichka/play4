package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;

public class Leandro extends DefaultAI
{
	private Location[] points = new Location[18];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Leandro(L2Character actor)
	{
		super(actor);
		points[0] = new Location(-82428, 245204, -3720);
		points[1] = new Location(-82422, 245448, -3704);
		points[2] = new Location(-82080, 245401, -3720);
		points[3] = new Location(-82108, 244974, -3720);
		points[4] = new Location(-83595, 244051, -3728);
		points[5] = new Location(-83898, 242776, -3728);
		points[6] = new Location(-85966, 241371, -3728);
		points[7] = new Location(-86079, 240868, -3720);
		points[8] = new Location(-86076, 240392, -3712);
		points[9] = new Location(-86519, 240706, -3712);
		points[10] = new Location(-86343, 241130, -3720);
		points[11] = new Location(-86519, 240706, -3712);
		points[12] = new Location(-86076, 240392, -3712);
		points[13] = new Location(-86079, 240868, -3720);
		points[14] = new Location(-85966, 241371, -3728);
		points[15] = new Location(-83898, 242776, -3728);
		points[16] = new Location(-83595, 244051, -3728);
		points[17] = new Location(-82108, 244974, -3720);
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
						Functions.npcSay(_thisActor, Say2C.ALL, 1010205);
						wait = true;
						return true;
					case 10:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010206);
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

		return randomAnimation();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}