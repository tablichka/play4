package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Priest;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 23.08.2010 22:46:35
 */
public class BrazierOfPurity extends Priest
{
	private long _nextFactionNotifyTime;

	public BrazierOfPurity(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_nextFactionNotifyTime < System.currentTimeMillis())
		{
			_nextFactionNotifyTime = System.currentTimeMillis() + 10000;
			for(L2NpcInstance npc : _thisActor.getKnownNpc(1500))
				if(npc.isMonster() && npc.getNpcId() >= 22658 && npc.getNpcId() <= 22659)
					npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, damage);
		}
	}

	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
	}
}
