package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Spawn;
import ru.l2gw.gameserver.tables.TerritoryTable;

/**
 * @author: rage
 * @date: 18.01.2010 19:13:00
 */
public class WalkingMystic extends Mystic
{
	private long lastMove;

	public WalkingMystic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean maybeMoveToHome()
	{
		if(_thisActor.getSpawnedLoc() == null || lastMove > System.currentTimeMillis())
			return false;

		int x;
		int y;
		int z;
		L2Spawn spawn = _thisActor.getSpawn();
		int c = 0;
		
		while(true)
		{
			c++;
			if(c > 40)
				return false;

			x = _thisActor.getSpawnedLoc().getX() + Rnd.get(-Config.MAX_DRIFT_RANGE * 2, Config.MAX_DRIFT_RANGE * 2);
			y = _thisActor.getSpawnedLoc().getY() + Rnd.get(-Config.MAX_DRIFT_RANGE * 2, Config.MAX_DRIFT_RANGE * 2);
			z = GeoEngine.getHeight(x, y, _thisActor.getSpawnedLoc().getZ(), _thisActor.getReflection());

			if(_thisActor.getSpawnedLoc().getZ() - z > 200)
				continue;

			if(spawn != null && spawn.getLocation() != 0 && !TerritoryTable.getInstance().getLocation(spawn.getLocation()).isInside(x, y))
				continue;

			break;
		}

		lastMove = System.currentTimeMillis() + Rnd.get(1000, 3000);
		_thisActor.setRunning();
		_thisActor.moveToLocation(x, y, z, 0, true);

		return true;
	}
}
