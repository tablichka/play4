package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.gameserver.tables.TerritoryTable;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * Моб использует телепортацию вместо рандом валка.
 *
 * @author SYS
 */
public class RndTeleportFighter extends Fighter
{
	private long _lastTeleport;

	public RndTeleportFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		if(System.currentTimeMillis() - _lastTeleport < 10000)
			return false;

		boolean randomWalk = _actor.hasRandomWalk();
		if(_thisActor.getSpawnedLoc() == null)
			return false;

		// Random walk or not?
		if(randomWalk && (!Config.RND_WALK || Rnd.chance(Config.RND_WALK_RATE)))
			return false;

		if(!randomWalk && _thisActor.isInRangeZ(_thisActor.getSpawnedLoc(), Config.MAX_DRIFT_RANGE))
			return false;

		int x = _thisActor.getSpawnedLoc().getX() + Rnd.get(-Config.MAX_DRIFT_RANGE, Config.MAX_DRIFT_RANGE);
		int y = _thisActor.getSpawnedLoc().getY() + Rnd.get(-Config.MAX_DRIFT_RANGE, Config.MAX_DRIFT_RANGE);
		int z = GeoEngine.getHeight(x, y, _thisActor.getSpawnedLoc().getZ(), _thisActor.getReflection());

		L2Spawn spawn = _thisActor.getSpawn();
		if(spawn != null && spawn.getLocation() != 0 && !TerritoryTable.getInstance().getLocation(spawn.getLocation()).isInside(x, y))
			return false;

		_thisActor.broadcastPacketToOthers(new MagicSkillUse(_thisActor, _thisActor, 4671, 1, 500, 0));
		ThreadPoolManager.getInstance().scheduleAi(new Teleport(new Location(x, y, z)), 500, false);
		_lastTeleport = System.currentTimeMillis();

		return true;
	}
}