package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 20.10.2010 15:13:24
 */
public class SoDMovingDevice extends DefaultAI
{
	private boolean _tiatHall;
	private static final int[] MOBS =
			{		
					22538, // Dragon Steed Troop Commander
					22540, // White Dragon Leader
					22547, // Dragon Steed Troop Healer
					22542, // Dragon Steed Troop Magic Leader
					22548 // Dragon Steed Troop Javelin Thrower
			};
	private int _currentMobIndex;
	private int _waveCount = 0;
	private Instance _currentInstance;
	private static final Location TIATROOM_LOC = new Location(-250408, 208568, -11968);
	private static final Location MOVE_LOC = new Location(-251432, 215630, -12208);

	public SoDMovingDevice(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_tiatHall = getBool("tiat_hall", false);
		_currentInstance = _thisActor.getSpawn().getInstance();
		_currentMobIndex = 0;
		addTimer(1, 5000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1 && !_thisActor.isDead())
		{
			if(_currentInstance == null)
			{
				_log.warn(_thisActor + " has no instance!!");
				return;
			}

			if(_currentMobIndex >= MOBS.length)
				_currentMobIndex = 0;

			L2MonsterInstance mob = (L2MonsterInstance) _currentInstance.addSpawn(MOBS[_currentMobIndex], Location.coordsRandomize(_thisActor.getLoc(), 40), 0);
			mob.getAI().setGlobalAggro(0);
			mob.setSpawnedLoc(TIATROOM_LOC);
			mob.setRunning();

			if(_tiatHall)
				mob.moveToLocation(Location.coordsRandomize(TIATROOM_LOC, 300), 0, true);
			else
				mob.moveToLocation(Location.coordsRandomize(MOVE_LOC, 100), 0, true);

			_currentMobIndex++;

			if(_currentMobIndex == MOBS.length)
				_waveCount++;

			if(_waveCount == 2 && _tiatHall)
				broadcastScriptEvent(1, _thisActor, null, 3000);

			addTimer(1, _currentMobIndex == MOBS.length ? 90000 : 3000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(_tiatHall)
			broadcastScriptEvent(2, _thisActor, null, 5000);
	}
}
