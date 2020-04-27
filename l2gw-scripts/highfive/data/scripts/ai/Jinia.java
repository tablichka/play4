package ai;

import ai.base.IcequeenHelpPc;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 28.09.11 0:21
 */
public class Jinia extends IcequeenHelpPc
{
	public int is_hard_mode = 0;

	public Jinia(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if( is_hard_mode == 0 )
		{
			_thisActor.createOnePrivate(18935, "IcequeenP4Buff", 0, 0, 114707, -114797, -11199, 0, 0, 0, 0);
		}
		super.onEvtSpawn();
	}
}
