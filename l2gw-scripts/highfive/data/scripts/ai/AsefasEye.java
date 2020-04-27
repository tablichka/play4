package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 29.01.12 12:41
 */
public class AsefasEye extends DefaultNpc
{
	public AsefasEye(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(61501, 10000);
		Functions.npcSay(_thisActor, Say2C.ALL, 61503);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 61501)
		{
			_thisActor.onDecay();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}