package ai;

import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 04.10.11 2:08
 */
public class Ssq2DirectorSophia3 extends Citizen
{
	public Ssq2DirectorSophia3(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(2002, 3000);
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2002)
		{
			L2NpcInstance npc = InstanceManager.getInstance().getNpcById(_thisActor, 32809);
			if(npc != null)
				npc.i_ai1 = 1;
		}
	}
}
