package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.controllers.GameTimeController;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.L2CharacterAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 24.09.11 5:15
 */
public class XelCampfire extends L2CharacterAI
{
	public int campfire_range = 600;
	public L2Skill Skill01_ID = null;
	public L2Skill Skill02_ID = null;
	private L2NpcInstance _thisActor;

	public XelCampfire(L2Character actor)
	{
		super(actor);
		_thisActor = (L2NpcInstance) actor;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		addTimer(2219001, 1000);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2219017)
		{
			_thisActor.i_ai0 = 2;
			L2NpcInstance c0 = L2ObjectsStorage.getAsNpc((Long) arg1);
			if(c0 != null)
			{
				c0.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 2119019, 0, null);
			}
			_thisActor.changeNpcState(1);
			_thisActor.createOnePrivate(18933, "XelCampfireDummy", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
			addTimer(2219002, 3000);
		}
		else if(eventId == 2219022)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.c_ai0 = c0.getStoredId();
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2219001)
		{
			addTimer(2219001, ((30 * 1000) + Rnd.get(5000)));
			_thisActor.changeNpcState(2);
			int i1;
			if(GameTimeController.getInstance().isNowNight())
			{
				i1 = 2;
			}
			else
			{
				i1 = 4;
			}

			if(Rnd.get(i1) < 1)
			{
				_thisActor.i_ai0 = 1;
				_thisActor.changeNpcState(1);
				broadcastScriptEvent(2219021, _thisActor.getStoredId(), null, campfire_range);
			}
			else
			{
				_thisActor.i_ai0 = 0;
				_thisActor.changeNpcState(2);
				broadcastScriptEvent(2219020, 0, null, campfire_range);
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
				if(c0 != null)
				{
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 2219022, 1, null);
				}
			}
		}
		else if(timerId == 2219002)
		{
			broadcastScriptEvent(2219018, _thisActor.getStoredId(), null, campfire_range);
		}
	}
}