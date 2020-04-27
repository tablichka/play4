package ai;

import ai.base.DefaultNpc;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 16.12.11 17:26
 */
public class ImmoCorpse extends DefaultNpc
{
	public ImmoCorpse(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.doDie(null);
	}
}