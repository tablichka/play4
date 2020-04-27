package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 10.09.11 11:26
 */
public class AiClemis extends Citizen
{
	public int pos_x = -180218;
	public int pos_y = 185923;
	public int pos_z = -10576;
	public String fnEnterFailed = "clemis002.htm";

	public AiClemis(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -415 )
		{
			if( reply == 1 )
			{
				if( talker.getLevel() > 79 )
				{
					talker.teleToLocation(pos_x, pos_y, pos_z);
				}
				else
				{
					_thisActor.showPage(talker, fnEnterFailed);
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 20091023 )
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			_thisActor.changeHeading(_thisActor.calcHeading(c0.getLoc()));
		}
	}
}
