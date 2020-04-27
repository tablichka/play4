package ai;

import ai.base.RaidBossType4;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 29.01.12 13:05
 */
public class FlameSpiritNastron extends RaidBossType4
{
	public FlameSpiritNastron(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(61601, 1200000);
		Functions.npcSay(_thisActor, Say2C.ALL, 61650);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 61601)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 61651);
			L2NpcInstance npc = L2ObjectsStorage.getAsNpc(_thisActor.param1);
			if(npc != null)
				npc.av_quest0.set(0);

			_thisActor.onDecay();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}