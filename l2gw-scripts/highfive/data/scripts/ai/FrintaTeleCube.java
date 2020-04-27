package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 12.09.11 4:33
 */
public class FrintaTeleCube extends Citizen
{
	public FrintaTeleCube(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "teleport_cube_frintessa001.htm");
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -200 && reply == 1)
			talker.teleToLocation(-87760 + Rnd.get(50), -151833 + Rnd.get(50), -9152);
	}
}
