package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 30.08.2010 15:15:55
 */
public class GCScout extends Fighter
{
	private static final Location[][] _paths = {
			{ // path=0
					new Location(185852, 57506, -4568),
					new Location(185855, 55016, -4568),
					new Location(187858, 55053, -4568),
					new Location(187870, 57299, -4568),
					new Location(187555, 57790, -4568),
					new Location(187556, 57981, -4568),
					new Location(187560, 59540, -4976),
					new Location(187534, 60787, -4984),
					new Location(187566, 62576, -4976),
					new Location(185713, 62568, -4976),
					new Location(190361, 62566, -4976),
					new Location(193868, 62591, -4976),
					new Location(193851, 60546, -4976),
					new Location(190361, 60468, -4976)
			},
			{ // path=1
					new Location(192358, 60487, -6096),
					new Location(192369, 61679, -6096),
					new Location(189167, 61662, -6104),
					new Location(192368, 61684, -6096),
					new Location(192291, 62771, -6096),
					new Location(188168, 62760, -7232),
					new Location(188049, 61017, -7232),
					new Location(190971, 61022, -7240),
					new Location(192883, 61017, -7240),
					new Location(188052, 61019, -7232),
					new Location(188039, 59922, -7232),
					new Location(182477, 59961, -7232)
			},
			{ // path=2
					new Location(185328, 56315, -7232),
					new Location(186923, 56320, -7240),
					new Location(188159, 56448, -7232),
					new Location(190243, 56428, -7376),
					new Location(191960, 56424, -7624),
					new Location(192050, 58739, -7280),
					new Location(191959, 61003, -7232),
					new Location(187536, 61023, -7232),
					new Location(187414, 59984, -7232),
					new Location(182478, 59969, -7232)
			}
	};

	private Location[] _currentPath;
	private int _currentPos;
	private long _nextAggroCheck;
	private long _nextMove;
	private long _nextHelpMessage;
	private int _moveFailCount;
	private boolean _revese;

	public GCScout(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_currentPath = _paths[getInt("path", 0)];
		_currentPos = 0;
		_moveFailCount = 0;
		_revese = false;
		MAX_PURSUE_RANGE = 100000;
		_nextHelpMessage = 0;
	}

	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(!super.thinkActive() && !_thisActor.isMoving && _nextMove < System.currentTimeMillis())
		{
			try
			{
				if(_currentPos >= _currentPath.length)
				{
					_currentPos = _currentPath.length - 2;
					_revese = true;
				}
				else if(_currentPos <= 0)
				{
					_currentPos = 1;
					_revese = false;
				}

				if(!_thisActor.moveToLocation(_currentPath[_currentPos], 0, true))
					_moveFailCount++;
				else
				{
					_moveFailCount = 0;
					if(_thisActor.isInRange(_currentPath[_currentPos], 20))
					{
						if(_revese)
							_currentPos--;
						else
							_currentPos++;
					}
				}

				if(_moveFailCount > 10)
				{
					_thisActor.teleToLocation(_currentPath[_currentPos]);
					if(_revese)
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
	protected void thinkAttack()
	{
		super.thinkAttack();
		L2Character attackTarget = getAttackTarget();

		if(_nextAggroCheck < System.currentTimeMillis())
		{
			_nextAggroCheck = System.currentTimeMillis() + 3000;
			if(_nextHelpMessage < System.currentTimeMillis())
			{
				_nextHelpMessage = System.currentTimeMillis() + 180000;
				Functions.npcSayInRange(_thisActor, Say2C.SHOUT, 1800861, 900);
			}
			for(L2NpcInstance npc : _thisActor.getKnownNpc(600, 128))
				if(!npc.isDead() && npc.getNpcId() >= 22661 && npc.getNpcId() <= 22667)
				{
					npc.addDamageHate(attackTarget, 0, 10);
					if(npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE)
						npc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, attackTarget);
				}
		}
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();
		if(_intention == CtrlIntention.AI_INTENTION_ACTIVE)
		{
			if(_revese)
				_currentPos--;
			else
				_currentPos++;

			_nextMove = System.currentTimeMillis() + 3000;
		}
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		_nextAggroCheck = System.currentTimeMillis() + 2000;
		if(super.checkAggression(target))
		{
			Functions.npcSayInRange(_thisActor, Say2C.ALL, 1800876, 900);
			return true;
		}
		return false;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
}
