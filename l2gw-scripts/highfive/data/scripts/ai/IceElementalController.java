package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 27.09.11 20:45
 */
public class IceElementalController extends DefaultNpc
{
	public int debug_mode = 0;
	public int TIMER_release_lock = 2314025;

	public IceElementalController(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 23140012 )
		{
			if( _thisActor.i_ai0 == 0 )
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if( c0 != null )
				{
					Location pos0 = Location.coordsRandomize(c0, 20, 50);
					_thisActor.createOnePrivate(18854, "IceCastleBreathing", 0, 0, pos0.getX(), pos0.getY(), pos0.getZ(), 0, (Long) arg2, 0, 0);
				}
			}
		}
		else if( eventId == 23140020 )
		{
			_thisActor.i_ai0 = 1;
			addTimer(TIMER_release_lock, 30000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if( timerId == TIMER_release_lock )
		{
			_thisActor.i_ai0 = 0;
		}
	}
}