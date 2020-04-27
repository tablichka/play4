package ai;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 03.09.2010 14:51:17
 */
public class WharfPatrol extends Fighter
{
	private static final Location[][] _paths =
			{
					{ // path=0
							new Location(-148262, 255331, -184),
							new Location(-148314, 254864, -184),
							new Location(-148672, 254429, -184),
							new Location(-149249, 254143, -184)
					},
					{ // path=1
							new Location(-148226, 255312, -184),
							new Location(-148273, 254856, -184),
							new Location(-148642, 254392, -184),
							new Location(-149201, 254102, -184)
					},
					{ // path=2
							new Location(-149506, 254121, -184),
							new Location(-150082, 254345, -184),
							new Location(-150453, 254793, -184),
							new Location(-150503, 255290, -184)
					},
					{ // path=3
							new Location(-150467, 255328, -184),
							new Location(-150429, 254866, -184),
							new Location(-150055, 254386, -184),
							new Location(-149491, 254146, -184)
					}
			};

	private Location[] _currentPath;
	private int _currentPos = 0;
	private boolean _reverse = false;
	private int _moveFailCount;

	public WharfPatrol(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_currentPath = _paths[getInt("path", 0)];
		_currentPos = 0;
		MAX_PURSUE_RANGE = 100000;
	}

	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		GArray<L2NpcInstance> targets = _thisActor.getKnownNpc(1000);
		for(L2NpcInstance npc : targets)
			if(npc.getNpcId() == 18782)
			{
				_thisActor.addDamageHate(npc, 0, 9999);
				_thisActor.setRunning();
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, npc);
				return true;
			}

		if(!_thisActor.isMoving)
		{
			try
			{
				if(_currentPos >= _currentPath.length)
				{
					_currentPos = _currentPath.length - 2;
					_reverse = true;
				}
				else if(_currentPos < 0)
				{
					_currentPos = 1;
					_reverse = false;
				}

				_thisActor.setWalking();
				if(!_thisActor.moveToLocation(_currentPath[_currentPos], 0, true))
					_moveFailCount++;
				else
				{
					_moveFailCount = 0;
					if(_thisActor.isInRange(_currentPath[_currentPos], 20))
					{
						if(_reverse)
							_currentPos--;
						else
							_currentPos++;
					}
				}

				if(_moveFailCount > 10)
				{
					_thisActor.teleToLocation(_currentPath[_currentPos]);
					if(_reverse)
						_currentPos--;
					else
						_currentPos++;
				}
			}
			catch(Exception e)
			{
			}
		}

		return true;
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();
		if(_intention == CtrlIntention.AI_INTENTION_ACTIVE)
		{
			if(_reverse)
				_currentPos--;
			else
				_currentPos++;
			thinkActive();
		}
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
}
