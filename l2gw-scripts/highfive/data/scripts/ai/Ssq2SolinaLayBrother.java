package ai;

import ai.base.WizardDdmagic2Heal;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 06.10.11 16:15
 */
public class Ssq2SolinaLayBrother extends WizardDdmagic2Heal
{
	public int IsAggressive = 1;

	public Ssq2SolinaLayBrother(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if( _thisActor.getInstanceZoneId() == 158 )
		{
			//int i0 = ServerVariables.getInt("GM_" + 80217);
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32888);
			if( c0 != null )
			{
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90111, 0, null);
			}
		}
		super.onEvtDead(killer);
	}
}