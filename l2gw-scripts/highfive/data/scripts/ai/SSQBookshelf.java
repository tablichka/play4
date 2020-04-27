package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 26.11.2010 14:42:17
 */
public class SSQBookshelf extends DefaultAI
{
	private static final Location[] _spawnLoc =
			{
					new Location(-81669, 206090, -7960),
					new Location(-81393, 206152, -7960),
					new Location(-81784, 205690, -7960),
					new Location(-81393, 205565, -7960)
			};

	public SSQBookshelf(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		Instance inst = _thisActor.getSpawn().getInstance();
		if(inst != null)
		{
			int r = Rnd.get(4);
			for(int i = 0; i < 4; i++)
			{
				L2NpcInstance npc = inst.addSpawn(32581, _spawnLoc[i], 0);
				if(r == i)
					npc.i_ai0 = 1;
			}
		}
	}
}
