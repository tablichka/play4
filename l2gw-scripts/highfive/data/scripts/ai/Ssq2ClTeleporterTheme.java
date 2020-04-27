package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 06.10.11 13:08
 */
public class Ssq2ClTeleporterTheme extends Citizen
{
	public Ssq2ClTeleporterTheme(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80304, _thisActor.id);
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		ZoneManager.getInstance().areaSetOnOff("21_10_ssq2_theme_center_ori", 1, _thisActor.getReflection());
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if( eventId == 90105 )
		{
			_thisActor.i_ai0++;
			if( _thisActor.i_ai0 == 4 )
			{
				_thisActor.i_ai1 = 1;
				ZoneManager.getInstance().areaSetOnOff("21_10_ssq2_theme_center_ori", 0, _thisActor.getReflection());
				ZoneManager.getInstance().areaSetOnOff("21_10_ssq2_theme_center_new", 1, _thisActor.getReflection());
				//int i0 = ServerVariables.getInt("GM_" + 80008);
				L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32787);
				if( c0 != null )
				{
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90105, 0, null);
					_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90313, 0, null);
				}
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}