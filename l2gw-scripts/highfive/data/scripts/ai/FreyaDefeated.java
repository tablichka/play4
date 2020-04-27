package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 28.09.11 0:37
 */
public class FreyaDefeated extends DefaultNpc
{
	public int ITEM_invisi_1hs = 15280;

	public FreyaDefeated(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.equipItem(ITEM_invisi_1hs);
		_thisActor.changeNpcState(0);
		_thisActor.setIsInvul(true);
		_thisActor.show_name_tag = 0;
		_thisActor.targetable = false;
		_thisActor.updateAbnormalEffect();
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140020)
		{
			_thisActor.onDecay();
		}
	}
}
