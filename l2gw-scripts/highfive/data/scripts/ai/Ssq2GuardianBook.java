package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 04.10.11 17:23
 */
public class Ssq2GuardianBook extends Citizen
{
	public Ssq2GuardianBook(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80100, _thisActor.id);
		_thisActor.i_ai0 = 0;
		ZoneManager.getInstance().areaSetOnOff("22_10_ssq2_book_center_ori", 1, _thisActor.getReflection());
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 90103 )
		{
			_thisActor.i_ai0++;
			if( _thisActor.i_ai0 == 4 )
			{
				ZoneManager.getInstance().areaSetOnOff("22_10_ssq2_book_center_ori", 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff("22_10_ssq2_book_center_new", 1, _thisActor.getReflection());
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32787);
				if(c0 != null)
				{
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90103, 4, null);
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90311, 0, null);
				}
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}