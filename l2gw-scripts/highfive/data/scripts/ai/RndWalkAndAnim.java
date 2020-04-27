package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.commons.math.Rnd;

public class RndWalkAndAnim extends DefaultAI
{
	protected static final int PET_WALK_RANGE = 100;

	public RndWalkAndAnim(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isMoving)
			return false;

		int val = Rnd.get(100);

		if(val < 20)
			randomWalk();
		else if(val < 40)
			_thisActor.onRandomAnimation();

		return false;
	}

	@Override
	protected boolean randomWalk()
	{
		int spawnX = _thisActor.getSpawnedLoc().getX();
		int spawnY = _thisActor.getSpawnedLoc().getY();
		int spawnZ = _thisActor.getSpawnedLoc().getZ();

		int x = spawnX + Rnd.get(2 * PET_WALK_RANGE) - PET_WALK_RANGE;
		int y = spawnY + Rnd.get(2 * PET_WALK_RANGE) - PET_WALK_RANGE;
		int z = GeoEngine.getHeight(x, y, spawnZ, _thisActor.getReflection());

		_thisActor.setRunning();
		_thisActor.moveToLocation(x, y, z, 0, true);

		return true;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}