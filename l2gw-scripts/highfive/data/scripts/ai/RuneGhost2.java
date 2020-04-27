package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author rage
 * @date 31.12.10 12:51
 */
public class RuneGhost2 extends L2CharacterAI
{
	private L2NpcInstance _thisActor;

	public RuneGhost2(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(2201, 120000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2201)
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(_thisActor.c_ai0);
			if(c0 != null)
				c0.i_quest0 = 0;
			Functions.npcSay(_thisActor, Say2C.ALL, 2251);
			_thisActor.deleteMe();
		}
		else if(timerId == 2202)
		{
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(_thisActor.c_ai0);
			if(c0 != null)
				c0.i_quest0 = 0;
			_thisActor.deleteMe();
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
