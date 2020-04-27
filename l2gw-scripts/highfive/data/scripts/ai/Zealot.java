package ai;

import npc.model.WharfPatrolInstance;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author rage
 * @date 03.09.2010 15:32:35
 */
public class Zealot extends Fighter
{
	public Zealot(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker instanceof WharfPatrolInstance)
		{
			_thisActor.addDamage(attacker, damage);
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
}
