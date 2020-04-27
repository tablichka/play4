package ai;

import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

/**
 * Blacksmith of Wind Rooney, телепортируется раз в 15 минут по 5м разным точкам FoG.
 *
 * @author SYS
 */
public class Rooney extends DefaultAI
{
	private Location[] _points = new Location[5];
	private static long TELEPORT_PERIOD = 15 * 60 * 1000; // 15 min
	private long _lastTeleport;

	public Rooney(L2Character actor)
	{
		super(actor);
		_points[0] = new Location(184022, -117083, -3342);
		_points[1] = new Location(183516, -118815, -3093);
		_points[2] = new Location(185007, -115651, -1587);
		_points[3] = new Location(186191, -116465, -1587);
		_points[4] = new Location(189630, -115611, -1587);
		_lastTeleport = System.currentTimeMillis();
	}

	@Override
	protected boolean thinkActive()
	{
		if(System.currentTimeMillis() - _lastTeleport < TELEPORT_PERIOD)
			return false;

		for(int i = 0; i < _points.length; i++)
		{
			Location loc = _points[Rnd.get(_points.length)];
			if(_thisActor.getLoc().equals(loc))
				continue;

			_thisActor.broadcastPacketToOthers(new MagicSkillUse(_thisActor, _thisActor, 4671, 1, 500, 0));
			ThreadPoolManager.getInstance().scheduleAi(new Teleport(loc), 500, false);
			_lastTeleport = System.currentTimeMillis();
			break;
		}
		return true;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
}