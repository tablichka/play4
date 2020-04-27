package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 10.09.11 10:50
 */
public class AiFalsepriestAquilani extends Citizen
{
	public String fnHi1 = "falsepriest_aquilani001.htm";
	public String fnHi2 = "falsepriest_aquilani002.htm";
	public int PosX = 118833;
	public int PosY = -80589;
	public int PosZ = -2688;

	public AiFalsepriestAquilani(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(!talker.isQuestComplete(10288))
		{
			_thisActor.showPage(talker, "falsepriest_aquilani001.htm");
		}
		else
		{
			_thisActor.showPage(talker, "falsepriest_aquilani002.htm");
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -5555)
		{
			if(reply == 1)
			{
				talker.teleToLocation(PosX, PosY, PosZ);
			}
		}
	}
}
