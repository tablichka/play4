package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.Config;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Multisell;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.zone.L2Zone;

/**
 * @author: rage
 * @date: 13.12.11 18:34
 */
public class TraderImmortality extends DefaultNpc
{
	public int FieldCycle = 3;
	public String fnHi = "";
	public String fnHi2 = "trader_immortality002.htm";
	public int multisellno1 = 647;
	public int multisellno2 = 698;
	public int check_item = 13692;

	public TraderImmortality(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtNoDesire()
	{
		addMoveAroundDesire(10, 10);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if( talker.getItemCountByItemId(check_item) >= 1 )
		{
			_thisActor.showPage(talker, fnHi2);
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
		if( ask == -7801 )
		{
			if( reply == 2 && talker.getItemCountByItemId(check_item) >= 1 )
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(multisellno2, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(L2Zone.ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
			else if( reply == 1 )
			{
				talker.setLastMultisellNpc(_thisActor);
				L2Multisell.getInstance().SeparateAndSend(multisellno1, talker, (!Config.SERVICES_OFFSHORE_NO_CASTLE_TAX && !_thisActor.isInZone(L2Zone.ZoneType.offshore)) ? _thisActor.getCastle().getTaxRate() : 0);
			}
		}
	}
}