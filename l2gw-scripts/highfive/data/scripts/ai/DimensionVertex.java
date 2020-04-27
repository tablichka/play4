package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 26.10.11 14:23
 */
public class DimensionVertex extends Citizen
{
	public String fnNoItem = "";
	public String fnYouAreChaotic = "";
	public String fnNobless = "";
	public String fnNoNobless = "";

	public DimensionVertex(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(talker.getItemCountByItemId(13560) > 0 || talker.getItemCountByItemId(13561) > 0 || talker.getItemCountByItemId(13562) > 0 || talker.getItemCountByItemId(13563) > 0 || talker.getItemCountByItemId(13564) > 0 || talker.getItemCountByItemId(13565) > 0 || talker.getItemCountByItemId(13566) > 0 || talker.getItemCountByItemId(13567) > 0 || talker.getItemCountByItemId(13568) > 0)
		{
			_thisActor.showPage(talker, fnFlagMan);
		}
		else if(talker.getKarma() > 0)
		{
			_thisActor.showPage(talker, fnYouAreChaotic);
		}
		else
		{
			_thisActor.showPage(talker, fnHi);
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -6)
		{
			if(reply == 10)
			{
				if(talker.getItemCountByItemId(4403) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4403, 1, _thisActor, true);
					talker.teleToLocation(118507, 16605, 5984);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 9)
			{
				if(talker.getItemCountByItemId(4403) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4403, 1, _thisActor, true);
					talker.teleToLocation(114649, 14144, 4976);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 8)
			{
				if(talker.getItemCountByItemId(4403) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4403, 1, _thisActor, true);
					talker.teleToLocation(115571, 13723, 3960);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 7)
			{
				if(talker.getItemCountByItemId(4403) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4403, 1, _thisActor, true);
					talker.teleToLocation(113026, 17687, 2952);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 6)
			{
				if(talker.getItemCountByItemId(4402) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4402, 1, _thisActor, true);
					talker.teleToLocation(117131, 16044, 1944);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 5)
			{
				if(talker.getItemCountByItemId(4402) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4402, 1, _thisActor, true);
					talker.teleToLocation(114152, 19902, 928);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 4)
			{
				if(talker.getItemCountByItemId(4402) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4402, 1, _thisActor, true);
					talker.teleToLocation(114636, 13413, -650);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 3)
			{
				if(talker.getItemCountByItemId(4401) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4401, 1, _thisActor, true);
					talker.teleToLocation(111982, 16028, -2100);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 2)
			{
				if(talker.getItemCountByItemId(4401) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4401, 1, _thisActor, true);
					talker.teleToLocation(114666, 13380, -3600);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
			else if(reply == 1)
			{
				if(talker.getItemCountByItemId(4401) != 0)
				{
					talker.destroyItemByItemId("Teleport", 4401, 1, _thisActor, true);
					talker.teleToLocation(114356, 13423, -5127);
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
		}
	}
}
