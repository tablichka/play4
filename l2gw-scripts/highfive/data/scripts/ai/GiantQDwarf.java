package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 29.12.11 11:15
 */
public class GiantQDwarf extends DefaultNpc
{
	public GiantQDwarf(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -2519003)
		{
			if(reply == 1)
			{
				talker.teleToLocation(183985, 61424, -3992);
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2519007)
		{
			_thisActor.setHide(true);
			_thisActor.updateAbnormalEffect();
			addTimer(2519008, 60000);
		}
		else if(timerId == 2519008)
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc());
			_thisActor.setHide(false);
			_thisActor.updateAbnormalEffect();
		}
	}
}