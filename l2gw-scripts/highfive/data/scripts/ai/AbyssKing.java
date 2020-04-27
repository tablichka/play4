package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

public class AbyssKing extends Fighter
{
	public AbyssKing(L2Character actor)
	{
		super(actor);
		addTimer(2336005, 200000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == 2336005)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 33411);
			_thisActor.deleteMe();
		}
	}
}