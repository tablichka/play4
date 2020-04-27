package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SocialAction;

/**
 * @author rage
 * @date 29.11.2010 19:21:38
 * АИ для новогоднего эвента Saving Santa
 * http://www.lineage2.com/archive/2008/12/saving_santa_ev.html
 */
public class BRSanta extends DefaultAI
{
	public BRSanta(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
	
	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(1229, 5000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1229)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800739 : 1800740);
			addTimer(1230, 5000);
		}
		else if(timerId == 1230)
		{
			L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_ai0);
			if(player == null)
				Functions.npcSay(_thisActor, Say2C.ALL, 1800741, "!");
			else
				Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800741 : 1800742, player.getName());

			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 4));
			addTimer(1231, 5000);
		}
		else if(timerId == 1231)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, Rnd.chance(50) ? 1800743 : 1800744);
			addTimer(1232, 5000);
		}
		else if(timerId == 1232)
		{
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 3));
			addTimer(1233, 6000);
		}
		else if(timerId == 1233)
			_thisActor.deleteMe();
	}
}
