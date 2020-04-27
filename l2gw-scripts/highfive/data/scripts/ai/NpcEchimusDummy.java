package ai;

import ai.base.DefaultNpc;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 17.12.11 14:49
 */
public class NpcEchimusDummy extends DefaultNpc
{
	public String type = "";
	public int tide = 0;
	public String fnHi = "";
	public int wagon_classid = 1022523;
	public int TM_search_wagon = 78001;
	public int TIME_search_wagon = 2;

	public NpcEchimusDummy(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(type.equals("boss_display"))
		{
		}
		else if(type.equals("boss_dummy"))
		{
			addTimer(TM_search_wagon, TIME_search_wagon * 1000);
		}
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, fnHi);
		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_search_wagon)
		{
			broadcastScriptEvent(78010073, 2424, 0, 300);
			addTimer(TM_search_wagon, TIME_search_wagon * 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010070 && type.equals("boss_display") && tide == 1)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800306);
		}
	}
}