package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SystemMessage;

/**
 * @author: rage
 * @date: 13.12.11 18:28
 */
public class GuideUndeath extends DefaultNpc
{
	public int FieldCycle = 3;
	public int deathroom_x = -183296;
	public int deathroom_y = 206038;
	public int deathroom_z = -12896;
	public String fnHi = "";

	public GuideUndeath(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, fnHi);
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -7801)
		{
			if(reply == 1)
			{
				if(talker.getTransformation() != 260 && talker.getTransformation() != 8 && talker.getTransformation() != 9)
				{
					talker.teleToLocation(deathroom_x + Rnd.get(100) - Rnd.get(100), deathroom_y + Rnd.get(100) - Rnd.get(100), deathroom_z);
				}
				else
				{
					talker.sendPacket(new SystemMessage(2924));
				}
			}
		}
	}
}