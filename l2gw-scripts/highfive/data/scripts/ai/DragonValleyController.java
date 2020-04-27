package ai;

import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.serverpackets.Earthquake;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 01.09.11 10:38
 */
public class DragonValleyController extends L2CharacterAI
{
	public int RandRate = 14;
	public String EventMakerName = "";

	public DragonValleyController(L2Character actor)
	{
		super(actor);
	}

	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20100504)
		{
			if(Rnd.get(RandRate) == (Integer) arg1 || (Integer) arg1 == 777)
			{
				_actor.broadcastPacket(new Earthquake(_actor.getLoc(), 50, 10));
				DefaultMaker maker = SpawnTable.getInstance().getNpcMaker(EventMakerName);
				if(maker != null)
					maker.onScriptEvent(1001, 0, 0);
			}
		}
	}
}
