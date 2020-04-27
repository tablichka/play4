package ai.rr;

import ai.Citizen;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 20.01.12 17:00
 */
public class RoyalRushTeleporter extends Citizen
{
	public String fnNoItem = "";
	public int TelPos_X1 = 0;
	public int TelPos_Y1 = 0;
	public int TelPos_Z1 = 0;
	public int TelPos_X2 = 0;
	public int TelPos_Y2 = 0;
	public int TelPos_Z2 = 0;

	public RoyalRushTeleporter(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 620)
		{
			switch(reply)
			{
				case 101:
					if(talker.getItemCountByItemId(7261) != 0)
					{
						talker.destroyItemByItemId("Quest", 7261, 1, _thisActor, true);
						talker.teleToLocation(TelPos_X1, TelPos_Y1, TelPos_Z1);
					}
					else if(talker.getItemCountByItemId(7262) != 0)
					{
						talker.teleToLocation(TelPos_X1, TelPos_Y1, TelPos_Z1);
					}
					else
					{
						_thisActor.showPage(talker, fnNoItem);
					}
					break;
				case 102:
					if(talker.getItemCountByItemId(7261) != 0)
					{
						talker.destroyItemByItemId("Quest", 7261, 1, _thisActor, true);
						talker.teleToLocation(TelPos_X2, TelPos_Y2, TelPos_Z2);
					}
					else if(talker.getItemCountByItemId(7262) != 0)
					{
						talker.teleToLocation(TelPos_X2, TelPos_Y2, TelPos_Z2);
					}
					else
					{
						_thisActor.showPage(talker, fnNoItem);
					}
					break;
			}
		}
	}
}