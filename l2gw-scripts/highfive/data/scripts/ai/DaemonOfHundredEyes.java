package ai;

import ai.base.RaidBossType4;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 28.01.12 12:15
 */
public class DaemonOfHundredEyes extends RaidBossType4
{
	public DaemonOfHundredEyes(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(60401, 1200000);
		Functions.npcSay(_thisActor, Say2C.ALL, 60403);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 60401)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 60404);
			L2NpcInstance npc = L2ObjectsStorage.getAsNpc(_thisActor.param1);
			if(npc != null)
				npc.av_quest0.set(0);
			_thisActor.onDecay();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}