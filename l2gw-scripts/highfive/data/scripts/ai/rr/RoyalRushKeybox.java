package ai.rr;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 19.01.12 18:08
 */
public class RoyalRushKeybox extends RoyalRushDefaultNpc
{
	public int RoomIDX = 1;

	public RoyalRushKeybox(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(_thisActor.i_ai0 == 0)
		{
			talker.addItem("Loot", 7260, 1, _thisActor, true);
			_thisActor.i_ai0 = 1;
		}
		_thisActor.onDecay();
		return true;
	}
}