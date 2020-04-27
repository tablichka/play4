package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.ThreadPoolManager;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.serverpackets.MagicSkillUse;
import ru.l2gw.util.Location;

/**<hr>
 * Master Toma, телепортируется раз в 30 минут по 3м разным точкам гномьего острова.
 * Телепортация вынесена в конфиг
 * <hr>
 * @author SYS
 * @edited HellSinger
 * @since 2008.11.20
 */
public class Toma extends DefaultAI
{
	private Location[] _points = new Location[3];
	private static long TELEPORT_PERIOD = 30 * 60 * 1000; // 30 min
	private long _lastTeleport;

	public Toma(L2Character actor)
	{
		super(actor);
		_points[0] = new Location(154132, -220070, -3392);
		_points[1] = new Location(178834, -184336, -352);
		_points[2] = new Location(151680, -174891, -1807, 41400);
		_lastTeleport = System.currentTimeMillis();
	}

	@Override
	protected boolean thinkActive()
	{
		if(!Config.ALT_TOMA_JUMP){ return false; }
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