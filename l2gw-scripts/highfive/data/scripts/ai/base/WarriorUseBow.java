package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 07.09.11 15:29
 */
public class WarriorUseBow extends Warrior
{
	public WarriorUseBow(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if( _thisActor.i_ai2 == 0 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 100 )
		{
			addTimer(100002, 2000);
			_thisActor.i_ai2 = 1;
			_thisActor.c_ai1 = attacker.getStoredId();
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == 100002 )
		{
			addFleeDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1), 100000);
			_thisActor.i_ai2 = 0;
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}
