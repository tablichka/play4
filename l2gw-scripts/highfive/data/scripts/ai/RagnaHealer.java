package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.Priest;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 23.08.2010 21:13:32
 */
public class RagnaHealer extends Priest
{
	private long lastFactionNotifyTime;

	public RagnaHealer(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(System.currentTimeMillis() - lastFactionNotifyTime > 10000)
		{
			lastFactionNotifyTime = System.currentTimeMillis();
			for(L2NpcInstance npc : _thisActor.getKnownNpc(700))
				if(npc.isMonster() && npc.getNpcId() >= 22691 && npc.getNpcId() <= 22702)
					npc.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, attacker, damage);
		}
		super.onEvtAttacked(attacker, damage, skill);
	}
}
