package ai.rr;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.math.Rnd;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.01.12 14:57
 */
public class RoyalRushTriggerbox5 extends RoyalRushTriggerboxBase
{
	public String room_event_1 = "1rd_type1_e";
	public String room_event_2 = "1rd_type2_e";

	public RoyalRushTriggerbox5(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		int i0 = Calendar.getInstance().get(Calendar.MINUTE);
		if( i0 > 49 && i0 < 60 )
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 1010552);
			return true;
		}
		if( _thisActor.i_ai0 == 1 )
		{
			return true;
		}
		else
		{
			_thisActor.i_ai0 = 1;
		}
		i0 = Rnd.get(2);
		switch(i0)
		{
			case 0:
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(room_event_1);
				if( maker0 != null )
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
			case 1:
				maker0 = SpawnTable.getInstance().getNpcMaker(room_event_2);
				if( maker0 != null )
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
		}
		return super.onTalk(talker);
	}
}