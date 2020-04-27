package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 18.01.12 16:13
 */
public class QBloodySenior extends Fighter
{
	public QBloodySenior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param1);
		if(c0 != null)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 70955, c0.getName());
		}
		addTimer(70902, 120000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 70902)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 70957);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}