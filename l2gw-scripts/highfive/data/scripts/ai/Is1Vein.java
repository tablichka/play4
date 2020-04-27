package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 14.12.11 20:25
 */
public class Is1Vein extends DefaultNpc
{
	public int regen_value = 1;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = 10;

	public Is1Vein(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		addTimer(1001, 15000);
		broadcastScriptEvent(98914, 0, getStoredIdFromCreature(_thisActor), 3000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			broadcastScriptEvent(98914, 0, getStoredIdFromCreature(_thisActor), 3000);
			addTimer(1001, (12 + Rnd.get(4)) * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 998915)
		{
			_thisActor.i_ai0++;
		}
		else if(eventId == 989812)
		{
			_thisActor.i_ai0 = 1;
		}
		else if(eventId == 998916)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		broadcastScriptEvent(9898903, _thisActor.i_ai0, getStoredIdFromCreature(_thisActor), 3000);
	}
}