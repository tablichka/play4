package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 10.09.11 19:47
 */
public class NpcCuteHarry extends Citizen
{
	public NpcCuteHarry(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		super.onMenuSelected(talker, ask, reply);
		if(ask == -7801 && reply == 2)
		{
			if(talker.isQuestComplete(250))
			{
				_thisActor.showPage(talker, "cute_harry003.htm");
			}
			else
			{
				_thisActor.showPage(talker, "cute_harry002.htm");
			}
		}
	}
}
