package ai;

import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.serverpackets.Earthquake;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 10.09.11 18:18
 */
public class AiDragonValleyController extends L2CharacterAI
{
	public int RandRate = 14;
	public String EventMakerName = "";

	private L2NpcInstance _thisActor;

	public AiDragonValleyController(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 99999)
		{
			_thisActor.i_ai9 = 0;
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20100504)
		{
			if(Rnd.get(RandRate) == (Integer) arg1 || (Integer) arg1 == 777)
			{
				_thisActor.broadcastPacket(new Earthquake(_thisActor.getLoc(), 50, 10));
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(EventMakerName);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
		}
	}
}
