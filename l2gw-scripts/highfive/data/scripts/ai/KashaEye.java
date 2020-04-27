package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author rage
 * @date 24.01.11 14:37
 */
public class KashaEye extends L2CharacterAI
{
	private L2NpcInstance _thisActor;
	private int	my_buff_type;
	private static final int TIMER_CHECK_30MIN = 33130;
	private static final int TIMER_DESPAWN = 33122;
	private static final int limit_count = 10;

	public KashaEye(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
		_thisActor.i_ai2 = 0;
		my_buff_type = _thisActor.getAIParams().getInteger("my_buff_type", -1);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(_thisActor.c_ai0);
		if(c0 != null)
			c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 2214010, my_buff_type);

		addTimer(TIMER_CHECK_30MIN, 60000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_CHECK_30MIN)
		{
			_thisActor.i_ai2++;
			broadcastScriptEvent(2214002, _thisActor.i_ai0, limit_count - _thisActor.i_ai2, 4000);
			if(_thisActor.i_ai2 == limit_count)
			{
				_thisActor.i_ai3 = 1;
				addTimer(TIMER_DESPAWN, 3000);
			}
			else
				addTimer(TIMER_CHECK_30MIN, 60000);
		}
		else if(timerId == TIMER_DESPAWN)
			_thisActor.doDie(null);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2214003)
		{
			int group = (Integer) arg1;
			if(group == _thisActor.i_ai0 && _thisActor.i_ai3 == 0)
			{
				_thisActor.i_ai3 = 1;
				addTimer(TIMER_DESPAWN, 3000);
			}
		}
		else if(eventId == 2214011)
		{
			int group = (Integer) arg2;
			if(_thisActor.i_ai0 == group)
				_thisActor.i_ai2 = 0;
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		L2NpcInstance c0 = L2ObjectsStorage.getAsNpc(_thisActor.c_ai0);
		if(c0 != null)
			c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 2214004, _thisActor.i_ai0, _thisActor.i_ai1);
		broadcastScriptEvent(2214011, my_buff_type, _thisActor.i_ai0, 4000);
	}
}
