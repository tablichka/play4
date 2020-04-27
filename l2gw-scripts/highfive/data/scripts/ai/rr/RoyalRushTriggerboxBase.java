package ai.rr;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 19.01.12 14:50
 */
public class RoyalRushTriggerboxBase extends RoyalRushDefaultNpc
{
	public int RoomIDX = 1;

	public RoyalRushTriggerboxBase(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.onDecay();
		return true;
	}
}