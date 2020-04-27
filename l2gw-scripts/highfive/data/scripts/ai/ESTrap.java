package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.NpcTrap;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.instances.L2DoorInstance;

/**
 * @author rage
 * @date 26.11.2009 9:43:16
 */
public class ESTrap extends NpcTrap
{
	private long nextSpeach;
	private static int doorId = 24220001;

	public ESTrap(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisTrap.isDead())
			return true;

		if(isDetected != _thisTrap.isDetected())
		{
			if(isDetected)
				for(L2Player player : L2World.getAroundPlayers(_thisTrap))
					if(_thisTrap.getPlayer() == null || player != _thisTrap.getPlayer())
						player.removeVisibleObject(_thisTrap);

			isDetected = _thisTrap.isDetected();
		}

		if(nextSpeach < System.currentTimeMillis() && _thisTrap.getAroundPlayers(trapRange).size() > 0)
		{
			nextSpeach = System.currentTimeMillis() + 30000;
			Functions.npcSay(_thisTrap, Say2C.SHOUT, 1800077);
		}

		return true;
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		for(L2Object obj : L2World.getAroundObjects(_thisTrap))
			if(obj instanceof L2DoorInstance && ((L2DoorInstance) obj).getDoorId() == doorId)
			{
				((L2DoorInstance) obj).openMe();
				break;
			}
	}
}
