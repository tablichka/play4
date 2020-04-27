package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Kreed extends DefaultAI
{
	private Location[] points = new Location[9];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Kreed(L2Character actor)
	{
		super(actor);
		points[0] = new Location(23436, 11164, -3728);
		points[1] = new Location(20256, 11104, -3728);
		points[2] = new Location(17330, 13579, -3720);
		points[3] = new Location(17415, 13044, -3736);
		points[4] = new Location(20153, 12880, -3728);
		points[5] = new Location(21621, 13349, -3648);
		points[6] = new Location(20686, 10432, -3720);
		points[7] = new Location(22426, 10260, -3648);
		points[8] = new Location(23436, 11164, -3728);
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
						wait = true;
						return true;
					case 7:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Через несколько дней начнется затмение. Тьма опустится на землю. Удвойте... Нет... Утройте охрану!");//TODO: Найти fString и заменить.
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