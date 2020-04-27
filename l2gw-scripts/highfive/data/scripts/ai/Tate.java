package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Tate extends DefaultAI
{
	private Location[] points = new Location[17];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Tate(L2Character actor)
	{
		super(actor);
		points[0] = new Location(115824, -181564, -1336);
		points[1] = new Location(116048, -181575, -1352);
		points[2] = new Location(116521, -181476, -1400);
		points[3] = new Location(116632, -180022, -1168);
		points[4] = new Location(115355, -178617, -928);
		points[5] = new Location(115763, -177585, -896);
		points[6] = new Location(115795, -177361, -880);
		points[7] = new Location(115877, -177338, -880);
		points[8] = new Location(115783, -177493, -880);
		points[9] = new Location(115112, -179836, -880);
		points[10] = new Location(115102, -180026, -872);
		points[11] = new Location(114876, -180045, -872);
		points[12] = new Location(114840, -179694, -872);
		points[13] = new Location(116322, -179602, -1096);
		points[14] = new Location(116792, -180386, -1240);
		points[15] = new Location(116319, -181573, -1376);
		points[16] = new Location(115824, -181564, -1336);
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
						wait_timeout = System.currentTimeMillis() + 20000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010218);
						wait = true;
						return true;
					case 7:
						wait_timeout = System.currentTimeMillis() + 15000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010219);
						wait = true;
						return true;
					case 11:
						wait_timeout = System.currentTimeMillis() + 30000;
						Functions.npcSay(_thisActor, Say2C.ALL, 1010220);
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