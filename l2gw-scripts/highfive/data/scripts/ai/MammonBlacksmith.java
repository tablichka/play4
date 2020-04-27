package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.L2WorldRegion;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 10.08.2010 13:52:13
 */
public class MammonBlacksmith extends DefaultAI
{
	private final static Location[] _teleports = {
			new Location(-19360, 13278, -4901, 0),
			new Location(-53190, -250493, -7908, 0),
			new Location(46303, 170091, -4981, 0),
			new Location(-20543, -251010, -8164, 0),
			new Location(12620, -248690, -9580, 0),
			new Location(140519, 79464, -5429, 0)
	};

	private long _nextTeleportTime = 0;

	public MammonBlacksmith(L2Character actor)
	{
		super(actor);
		_nextTeleportTime = System.currentTimeMillis() + Rnd.get(0, 30) * 60000;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_nextTeleportTime < System.currentTimeMillis())
		{
			_nextTeleportTime = System.currentTimeMillis() + 30 * 60000;
			Location loc;
			do
			{
				loc = _teleports[Rnd.get(_teleports.length)];
			}
			while(!checkTeleportLocation(loc));

			_thisActor.setHeading(loc.getHeading());
			_thisActor.teleToLocation(loc);

			int i1 = Rnd.get(30);
			if( i1 < 10 )
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1000431);
			}
			else if( i1 >= 10 && i1 < 20 )
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1000432);
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1000433);
			}

			return true;
		}

		return randomAnimation();
	}

	private boolean checkTeleportLocation(Location loc)
	{
		L2WorldRegion region = L2World.getRegion(loc.getX(), loc.getY(), loc.getZ());
		for(L2NpcInstance npc : region.getNpcsList(_thisActor.getReflection()))
			if(npc.getNpcId() == _thisActor.getNpcId())
				return false;

		return true;
	}
}
