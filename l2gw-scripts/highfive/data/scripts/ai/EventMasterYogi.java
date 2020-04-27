package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author rage
 * @date 30.11.2010 20:04:29
 */
public class EventMasterYogi extends L2CharacterAI
{
	public EventMasterYogi(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(1000, 60000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1000)
		{
			if(Rnd.get(5) < 1)
			{
				if(Rnd.get(2) < 1)
					Functions.npcSay((L2NpcInstance) _actor, Say2C.ALL, 1600023);
				else
					Functions.npcSay((L2NpcInstance) _actor, Say2C.ALL, 1600024);
			}
			addTimer(1000, 60000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
