package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

public class SSQMammon extends DefaultAI
{
	private static final int IASON_HEINE = 30969;

	public SSQMammon(L2Character actor)
	{
		super(actor);
		addTimer(1, 1000);
		addTimer(19601, 120000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == 1)
		{
			Functions.npcSayInRange(_thisActor, Say2C.ALL, 19604, 500);
		}
		else if(timerId == 19601)
		{
			L2NpcInstance iason = L2ObjectsStorage.getByNpcId(IASON_HEINE);
			if(iason != null)
			{
				iason.getAIParams().set("q0", 0);
			}
			Functions.npcSayInRange(_thisActor, Say2C.ALL, 19605, 500);
			_thisActor.deleteMe();
		}
		else if(timerId == 19602)
		{
			L2NpcInstance iason = L2ObjectsStorage.getByNpcId(IASON_HEINE);
			if(iason != null)
			{
				iason.getAIParams().set("q0", 0);
			}
			_thisActor.deleteMe();
		}

	}
}
