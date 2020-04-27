package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 28.10.2010 16:13:29
 */
public class HBMasterZelos extends Fighter
{
	public HBMasterZelos(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(_thisActor.getSpawn() != null)
		{
			Instance inst = _thisActor.getSpawn().getInstance();
			if(inst != null)
				inst.addSpawn(18427, new Location(-13226, 273413, -15304), 60);
		}
	}
}
