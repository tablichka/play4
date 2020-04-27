package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;

/**
 * @author: rage
 * @date: 09.10.11 15:02
 */
public class MonasteryAnaisBurner extends DefaultNpc
{
	public int BURNER_NUMBER = 0;
	public int TIME_FOR_TARGET = 2000;
	public int TARGET = 300;
	public int POSX = 10101;
	public int POSY = 20202;
	public int POSZ = 30303;

	public MonasteryAnaisBurner(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.changeNpcState(2);
		_thisActor.i_quest3 = 0;
		_thisActor.i_ai0 = 0;
		_thisActor.c_ai0 = 0;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2114008)
		{
			_thisActor.c_ai0 = (Long) arg1;
		}
		else if(eventId == 2114006 && (Integer) arg1 == BURNER_NUMBER)
		{
			_thisActor.changeNpcState(1);
			_thisActor.i_ai0 = 1;
			addTimer(TIME_FOR_TARGET, 1000);
		}
		else if(eventId == 2114009)
		{
			_thisActor.changeNpcState(1);
			if(_thisActor.i_quest3 == 1)
			{
				return;
			}
			_thisActor.createOnePrivate(18929, "GrailProtection", 0, 0, POSX, POSY, POSZ, 0, (Long) arg1, 0, 0);
		}
		else if(eventId == 2114007)
		{
			_thisActor.changeNpcState(2);
			_thisActor.doDie(null);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIME_FOR_TARGET)
		{
			_thisActor.changeNpcState(2);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 21140010, _thisActor.getStoredId(), null);
			addTimer(TIME_FOR_TARGET, 20000);
			if(_thisActor.i_quest3 == 1)
			{
			}
		}
	}
}