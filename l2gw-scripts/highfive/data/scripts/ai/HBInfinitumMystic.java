package ai;

import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

import static ai.HBInfinitumFighter.getCurrentFloor;
import static ai.HBInfinitumFighter.teleToNextFloor;

/**
 * @author rage
 * @date 27.10.2010 16:03:20
 */
public class HBInfinitumMystic extends Mystic
{
	private int _currentFloor;

	public HBInfinitumMystic(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_currentFloor = getCurrentFloor(_thisActor);
		if(_currentFloor < 0)
			_log.warn(_thisActor + " " + _thisActor.getLoc() + " can't find floor!");
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || _thisActor.isDead())
			return;

		if(attacker.getPlayer() != null && teleToNextFloor(attacker.getPlayer(), _currentFloor))
			return;

		super.onEvtAttacked(attacker, damage, skill);
	}
}
