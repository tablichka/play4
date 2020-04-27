package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 19.12.11 19:52
 */
public class NpcSoldierTomaris extends Citizen
{
	public String my_maker_name = "rumwarsha02_npc1425_lc01m1";
	public int TM_LINDVIOR_SCENE_CHECK = 78003;
	public int TIME_LINDVIOR_SCENE_CHECK = 30;
	public int TM_LINDVIOR_SCENE_1 = 78001;
	public int TIME_LINDVIOR_SCENE_1 = 60;
	public int TM_LINDVIOR_SCENE_2 = 78002;
	public int TIME_LINDVIOR_SCENE_2 = 30;

	public NpcSoldierTomaris(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		addTimer(TM_LINDVIOR_SCENE_CHECK, 1000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 78010057 && (Integer) arg1 == 2 && _thisActor.i_ai0 == 1)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, 1800227);
			_thisActor.i_ai0 = 2;
			addTimer(TM_LINDVIOR_SCENE_2, TIME_LINDVIOR_SCENE_2 * 1000);
		}
		else if(eventId == 78010057 && (Integer) arg1 == 2 && _thisActor.i_ai0 == 2)
		{
			_thisActor.i_ai0 = 0;
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TM_LINDVIOR_SCENE_1)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(my_maker_name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010057, 3, 0);
			}
		}
		else if(timerId == TM_LINDVIOR_SCENE_2)
		{
			DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(my_maker_name);
			if(maker0 != null)
			{
				maker0.onScriptEvent(78010057, 1, 0);
			}
		}
		else if(timerId == TM_LINDVIOR_SCENE_CHECK)
		{
			if((Calendar.getInstance().get(Calendar.MINUTE) == 58 && Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == 18 && (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)) && _thisActor.i_ai0 == 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1800225);
				_thisActor.i_ai0 = 1;
				addTimer(TM_LINDVIOR_SCENE_1, TIME_LINDVIOR_SCENE_1 * 1000);
			}
			addTimer(TM_LINDVIOR_SCENE_CHECK, TIME_LINDVIOR_SCENE_CHECK * 1000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}
}