package ai;

import java.util.Calendar;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

public class RoyalRushNpc extends DefaultAI
{
	public RoyalRushNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(Calendar.getInstance().get(Calendar.MINUTE) >= 54)
		{
			((L2NpcInstance) _actor).deleteMe();
			return false;
		}
		return super.thinkActive();
	}
}
