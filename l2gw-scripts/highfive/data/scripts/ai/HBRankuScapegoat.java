package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.util.Location;
import ru.l2gw.util.MinionList;

/**
 * @author rage
 * @date 27.10.2010 16:50:53
 */
public class HBRankuScapegoat extends DefaultAI
{
	private static final int Eidolon_ID = 25543;

	public HBRankuScapegoat(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		L2MonsterInstance boss = (L2MonsterInstance) _thisActor.getLeader();
		if(boss != null && !boss.isDead())
		{
			Location loc = _thisActor.getLoc();
			L2MonsterInstance newMinion = new L2MonsterInstance(IdFactory.getInstance().getNextId(), NpcTable.getTemplate(Eidolon_ID), 0, 0, 0, 0);
			newMinion.setReflection(_thisActor.getReflection());
			newMinion.setLeader(boss);
			newMinion.setSpawnedLoc(loc);
			newMinion.onSpawn();
			newMinion.spawnMe(loc);

			newMinion.getAI().setGlobalAggro(0);
			newMinion.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, boss.getMostHated(), 1);

			MinionList ml = boss.getMinionList();
			if(ml != null)
				ml.addSpawnedMinion(newMinion);
		}
	}

}
