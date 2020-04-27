package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 26.01.12 19:09
 */
public class UdansEye extends DefaultNpc
{
	public UdansEye(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(60901, 10000);
		Functions.npcSay(_thisActor, Say2C.ALL, 60903);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 60901)
		{
			_thisActor.onDecay();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}