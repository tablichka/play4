package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.instancemanager.ZoneManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 06.10.11 12:47
 */
public class Ssq2Cl2Theme extends Citizen
{
	public Ssq2Cl2Theme(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		ZoneManager.getInstance().areaSetOnOff("21_10_ssq2_sacrificey_eff", 1, _thisActor.getReflection());
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 10295 && reply == 2)
		{
			ZoneManager.getInstance().areaSetOnOff("21_10_ssq2_sacrificey_eff", 0, _thisActor.getReflection());
			//int i0 = ServerVariables.getInt("GM_" + 80301);
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 18953);
			if(c0 != null)
			{
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90104, 0, null);
			}
			talker.destroyItemByItemId("Quest", 17229, 1, _thisActor, true);
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}
}
