package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class Elpy extends Fighter
{
	public Elpy(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker != null && Rnd.chance(50))
		{
			int posX = _thisActor.getX();
			int posY = _thisActor.getY();
			int posZ = _thisActor.getZ();

			int signx = -1;
			int signy = -1;

			if(posX > attacker.getX())
				signx = 1;
			if(posX > attacker.getY())
				signy = 1;

			int range = 200;

			posX += Math.round(signx * range);
			posY += Math.round(signy * range);
			posZ = GeoEngine.getHeight(posX, posY, posZ, _thisActor.getReflection());

			if(GeoEngine.canMoveToCoord(attacker.getX(), attacker.getY(), attacker.getZ(), posX, posY, posZ, attacker.getReflection()))
			{
				Task task = new Task();
				task.type = TaskType.MOVE;
				task.usePF = false;
				task.loc = new Location(posX, posY, posZ);
				_task_list.add(task);
				_def_think = true;
			}
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return false;
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}