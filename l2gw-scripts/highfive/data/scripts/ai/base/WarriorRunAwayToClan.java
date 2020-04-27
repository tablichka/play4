package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 08.09.11 0:01
 */
public class WarriorRunAwayToClan extends Warrior
{
	public int flee_x = 0;
	public int flee_y = 0;
	public int flee_z = 0;

	public WarriorRunAwayToClan(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai4 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		int i6 = Rnd.get(100);
		if( _thisActor.i_ai4 == 0 )
		{
			_thisActor.i_ai4 = 1;
		}
		else if( _thisActor.getCurrentHp() < ( _thisActor.getMaxHp() / 2.000000 ) && _thisActor.getCurrentHp() > ( _thisActor.getMaxHp() / 3.000000 ) && attacker.getCurrentHp() > ( attacker.getMaxHp() / 4.000000 ) && i6 < 10 && _thisActor.i_ai0 == 0 && flee_x != 0 && flee_y != 0 && flee_z != 0 )
		{
			int i5 = Rnd.get(100);
			if( i5 < 7 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000007);
			}
			else if( i5 < 14 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000008);
			}
			else if( i5 < 21 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000009);
			}
			else if( i5 < 28 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000010);
			}
			else if( i5 < 35 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000011);
			}
			else if( i5 < 42 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000012);
			}
			else if( i5 < 49 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000013);
			}
			else if( i5 < 56 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000014);
			}
			else if( i5 < 63 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000015);
			}
			else if( i5 < 70 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000016);
			}
			else if( i5 < 77 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000017);
			}
			else if( i5 < 79 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000018);
			}
			else if( i5 < 81 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000019);
			}
			else if( i5 < 83 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000020);
			}
			else if( i5 < 85 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000021);
			}
			else if( i5 < 87 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000022);
			}
			else if( i5 < 89 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000023);
			}
			else if( i5 < 91 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000024);
			}
			else if( i5 < 93 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000025);
			}
			else if( i5 < 95 )
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000026);
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1000027);
			}
			addMoveToDesire(flee_x, flee_y, flee_z, 100000000);
			_thisActor.i_ai0 = 1;
			_thisActor.c_ai0 = attacker.getStoredId();
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
	}

	@Override
	protected void onEvtArrived()
	{
		if( _thisActor.i_ai0 == 1 )
		{
			if( _thisActor.getDistance(flee_x, flee_y, flee_z) < 40 )
			{
				addTimer(2001, 15000);
				_thisActor.i_ai0 = 2;
				broadcastScriptEvent(10000, _thisActor.c_ai0, null, 400);
			}
			else
			{
				addMoveToDesire(flee_x, flee_y, flee_z, 100000000);
			}
		}
		if( _thisActor.i_ai0 == 3 )
		{
			if( _thisActor.isInRange(_thisActor.getSpawnedLoc(), 40))
			{
				_thisActor.i_ai0 = 0;
			}
		}
		super.onEvtArrived();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 2001 )
		{
			if( _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100.000000 > 70.000000 && _thisActor.i_ai0 == 2 && !_thisActor.isMoving )
			{
				addMoveToDesire(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 1000000);
				_thisActor.i_ai0 = 3;
			}
			else if( !_thisActor.isMoving )
			{
				removeAllAttackDesire();
				addMoveToDesire(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 50);
				_thisActor.i_ai0 = 0;
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( _thisActor.isDead() )
		{
			return;
		}
		if( eventId == 10000 && !_thisActor.isMoving )
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if( c0 != null )
			{
				addAttackDesire(c0, 1, 1000000);
				_thisActor.i_ai0 = 3;
			}
		}
	}
}
