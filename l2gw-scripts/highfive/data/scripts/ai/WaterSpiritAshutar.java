package ai;

import ai.base.RaidBossType4;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 26.01.12 19:58
 */
public class WaterSpiritAshutar extends RaidBossType4
{
	public WaterSpiritAshutar(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(61001, 1200000);
		Functions.npcSay(_thisActor, Say2C.ALL, 61050);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 61001)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 61051);
			L2NpcInstance npc = L2ObjectsStorage.getAsNpc(_thisActor.param1);
			if(npc != null)
				npc.av_quest0.set(0);
			_thisActor.onDecay();
		}
	}
}