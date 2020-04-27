package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.util.Location;

public class Kasiel extends DefaultAI
{
	private Location[] points = new Location[19];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Kasiel(L2Character actor)
	{
		super(actor);
		points[0] = new Location(43932, 51096, -2992);
		points[1] = new Location(43304, 50364, -2992);
		points[2] = new Location(43041, 49312, -2992);
		points[3] = new Location(43612, 48322, -2992);
		points[4] = new Location(44009, 47645, -2992);
		points[5] = new Location(45309, 47341, -2992);
		points[6] = new Location(46726, 47762, -2992);
		points[7] = new Location(47509, 49004, -2992);
		points[8] = new Location(47443, 50456, -2992);
		points[9] = new Location(47013, 51287, -2992);
		points[10] = new Location(46380, 51254, -2900);
		points[11] = new Location(46389, 51584, -2800);
		points[12] = new Location(46009, 51593, -2800);
		points[13] = new Location(46027, 52156, -2800);
		points[14] = new Location(44692, 52141, -2800);
		points[15] = new Location(44692, 51595, -2800);
		points[16] = new Location(44346, 51564, -2850);
		points[17] = new Location(44357, 51259, -2900);
		points[18] = new Location(44111, 51252, -2992);
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
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Mother Tree всегда великолепно!");
						wait = true;
						return true;
					case 9:
						wait_timeout = System.currentTimeMillis() + 60000;
						Functions.npcSay(_thisActor, Say2C.ALL, "Lady Mirabel, мне нравится быть рядом с вами в этом тихом уголке нашего мира!");
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