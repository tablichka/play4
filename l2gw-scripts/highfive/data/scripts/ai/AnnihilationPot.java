package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 13.12.11 10:05
 */
public class AnnihilationPot extends DefaultNpc
{
	public int type = 0;
	public int POT_TIMER = 1111;

	public AnnihilationPot(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(POT_TIMER, 5000);
		_thisActor.changeNpcState(0);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		int i0 = -1;
		if(timerId == POT_TIMER)
		{
			if(type == 0)
			{
				i0 = ServerVariables.getInt("GM_" + 34, -1);
			}
			else if(type == 1)
			{
				i0 = ServerVariables.getInt("GM_" + 35, -1);
			}
			else if(type == 2)
			{
				i0 = ServerVariables.getInt("GM_" + 36, -1);
			}
			if(i0 != -1)
			{
				_thisActor.changeNpcState(i0);
			}
			addTimer(POT_TIMER, 5000);
		}
	}
}