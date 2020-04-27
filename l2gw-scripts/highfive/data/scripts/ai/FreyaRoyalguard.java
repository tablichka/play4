package ai;

import ai.base.RaidBossAgType1;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;

/**
 * @author: rage
 * @date: 23.09.11 16:24
 */
public class FreyaRoyalguard extends RaidBossAgType1
{
	public FreyaRoyalguard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_mb2314_05m1");
		if( maker0 != null )
		{
			maker0.onScriptEvent(10005, 0, 0);
		}
		broadcastScriptEvent(11036, 3, null, 7000);
	}
}
