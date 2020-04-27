package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * @author rage
 * @date 10.08.2010 11:51:09
 */
public class MammonMerchant extends DefaultAI
{
	private final static Location[] _teleports = {
			new Location(-52172, 78884, -4741, 0),
			new Location(-41350, 209876, -5087, 16384),
			new Location(-21657, 77164, -5173, 0),
			new Location(45029, 123802, -5413, 49152),
			new Location(83175, 208998, -5439, 0),
			new Location(111337, 173804, -5439, 0),
			new Location(118343, 132578, -4831, 0),
			new Location(172373, -17833, -4901, 0)
	};

	private long _nextTeleportTime = 0;

	public MammonMerchant(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_nextTeleportTime < System.currentTimeMillis())
		{
			_nextTeleportTime = System.currentTimeMillis() + 30 * 60000;
			Location loc = _teleports[Rnd.get(_teleports.length)];
			_thisActor.setHeading(loc.getHeading());
			_thisActor.teleToLocation(loc);
			return true;
		}

		return randomAnimation();
	}
}
