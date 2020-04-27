package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 24.10.11 19:51
 */
public class ResearcherOfOmanTower extends Citizen
{
	public String fnFail1 = "";
	public String fnFail2 = "";

	public ResearcherOfOmanTower(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -6)
		{
			if(reply == 1)
			{
				if(talker.getInventory().slotsLeft() >= 1 && talker.getInventory().getTotalWeight() <= talker.getMaxLoad() * 0.900000)
				{
					if(talker.getAdena() >= 10000)
					{
						if(talker.reduceAdena("Buy", 10000, _thisActor, true))
							talker.addItem("Buy", 4401, 1, _thisActor, true);
					}
					else
					{
						_thisActor.showPage(talker, fnFail2);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnFail1);
				}
			}
			else if(reply == 2)
			{
				if(talker.getInventory().slotsLeft() >= 1 && talker.getInventory().getTotalWeight() <= talker.getMaxLoad() * 0.900000)
				{
					if(talker.getAdena() >= 10000)
					{
						if(talker.reduceAdena("Buy", 10000, _thisActor, true))
							talker.addItem("Buy", 4402, 1, _thisActor, true);
					}
					else
					{
						_thisActor.showPage(talker, fnFail2);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnFail1);
				}
			}
			else if(reply == 3)
			{
				if(talker.getInventory().slotsLeft() >= 1 && talker.getInventory().getTotalWeight() <= talker.getMaxLoad() * 0.900000)
				{
					if(talker.getAdena() >= 10000)
					{
						if(talker.reduceAdena("Buy", 10000, _thisActor, true))
							talker.addItem("Buy", 4403, 1, _thisActor, true);
					}
					else
					{
						_thisActor.showPage(talker, fnFail2);
					}
				}
				else
				{
					_thisActor.showPage(talker, fnFail1);
				}
			}
		}
	}
}
