package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Alhena extends DefaultAI
{
	private Location[] points = new Location[14];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Alhena(L2Character actor)
	{
		super(actor);
		points[0] = new Location(10968, 14620, -4248);
		points[1] = new Location(11308, 15847, -4584);
		points[2] = new Location(12119, 16441, -4584);
		points[3] = new Location(15104, 15661, -4376);
		points[4] = new Location(15265, 16288, -4376);
		points[5] = new Location(12292, 16934, -4584);
		points[6] = new Location(11777, 17669, -4584);
		points[7] = new Location(11229, 17650, -4576);
		points[8] = new Location(10641, 17282, -4584);
		points[9] = new Location(7683, 18034, -4376);
		points[10] = new Location(10551, 16775, -4584);
		points[11] = new Location(11004, 15942, -4584);
		points[12] = new Location(10827, 14757, -4248);
		points[13] = new Location(10968, 14620, -4248);
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
					case 4:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010212);
						wait = true;
						return true;
					case 9:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010213);
						wait = true;
						return true;
					case 12:
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