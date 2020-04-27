package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2RaidBossInstance;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.MinionList;

/**
 * @author rage
 * @date 27.08.2009 10:21:08
 */
public class Kama56Boss extends Fighter
{
	private long _lastOrder = 0;

	public Kama56Boss(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void thinkAttack()
	{
		if(_thisActor == null)
			return;
		if(_lastOrder < System.currentTimeMillis() && _thisActor.isInCombat())
		{
			_lastOrder = System.currentTimeMillis() + 30000;
			MinionList ml = ((L2RaidBossInstance) _thisActor).getMinionList();
			if(ml == null || !ml.hasMinions())
			{
				super.thinkAttack();
				return;
			}

			GArray<L2Player> alive = _thisActor.getAroundLivePlayers(3000);

			if(alive.isEmpty())
			{
				super.thinkAttack();
				return;
			}

			L2Player target = alive.get(Rnd.get(alive.size()));
			Functions.npcSay(_thisActor, Say2C.ALL, 1800182, target.getName());
			for(L2NpcInstance m : ml.getSpawnedMinions())
			{
				m.clearAggroList();
				m.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, target, 10000000);
				m.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
			}
		}
		super.thinkAttack();
	}
}
