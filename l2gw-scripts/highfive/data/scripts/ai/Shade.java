package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;

/**
 * AI мобов Shade на Isle of Prayer, спавнятся из АИ DarkWaterDragon.<br>
 * - Деспавнятся при простое более 5(?) минут<br>
 * - Не используют функцию Random Walk<br>
 * ID: 22268, 22269
 * @author SYS
 */
public class Shade extends Fighter
{
	private long _wait_timeout = 0;
	private boolean _wait = false;
	private static final int DESPAWN_TIME = 5 * 60 * 1000; // 5 min

	public Shade(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_def_think)
		{
			doTask();
			_wait = false;
			return true;
		}

		if(!_wait)
		{
			_wait = true;
			_wait_timeout = System.currentTimeMillis() + DESPAWN_TIME;
		}

		if(_wait_timeout != 0 && _wait && _wait_timeout < System.currentTimeMillis())
			_thisActor.deleteMe();

		return super.thinkActive();
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}