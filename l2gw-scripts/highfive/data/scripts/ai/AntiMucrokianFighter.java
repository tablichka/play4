package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 23.08.2010 21:58:39
 */
public class AntiMucrokianFighter extends Fighter
{
	private long _nextAggroTime;
	private long _stopAggroTime;

	public AntiMucrokianFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtArrived()
	{
		if(_intention == CtrlIntention.AI_INTENTION_ACTIVE && _nextAggroTime < System.currentTimeMillis())
			findEnemyToAttack();
		super.onEvtArrived();
	}

	private void findEnemyToAttack()
	{
		GArray<L2NpcInstance> enemys = new GArray<L2NpcInstance>();
		for(L2NpcInstance npc : _thisActor.getKnownNpc(500))
			if(npc.getNpcId() >= 22650 && npc.getNpcId() <= 22655 && !npc.isDead() && npc.getAI().getIntention() == CtrlIntention.AI_INTENTION_ACTIVE)
				enemys.add(npc);

		if(enemys.size() > 0)
		{
			_stopAggroTime = System.currentTimeMillis() + Rnd.get(120000, 180000);
			L2NpcInstance enemy = enemys.get(Rnd.get(enemys.size()));
			_thisActor.addDamageHate(enemy, 0, 2);
			startRunningTask(1000);
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, enemy);
		}
	}

	@Override
	protected void thinkAttack()
	{
		L2Character target = getAttackTarget();
		if(target != null && target.isNpc())
		{
			if(Rnd.chance(5))
			{
				int msgId = -1;
				switch(_thisActor.getNpcId())
				{
					case 22656:
						msgId = 1800852;
						break;
					case 22657:
						msgId = 1800853;
						break;
					case 22658:
						msgId = 1800855;
						break;
					case 22659:
						msgId = 1800856;
						break;
				}
				Functions.npcSayInRange(_thisActor, Say2C.ALL, msgId, 500);
			}

			if(_stopAggroTime < System.currentTimeMillis() || target.getAI().getIntention() != CtrlIntention.AI_INTENTION_ACTIVE)
			{
				_nextAggroTime = System.currentTimeMillis() + Rnd.get(120000, 180000);
				_thisActor.stopHate(target);
				return;
			}
		}
		super.thinkAttack();
	}
}
