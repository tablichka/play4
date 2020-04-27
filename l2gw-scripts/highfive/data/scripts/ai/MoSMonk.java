package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * AI монахов в Monastery of Silence<br>
 * - агрятся на чаров с оружием в руках
 * - перед тем как броситься в атаку кричат
 */
public class MoSMonk extends Fighter
{
	private long _lastSpeach = 0;

	public MoSMonk(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onIntentionAttack(L2Character target)
	{
		if(getIntention() == AI_INTENTION_ACTIVE && _lastSpeach < System.currentTimeMillis() && Rnd.chance(50))
		{
			_lastSpeach = System.currentTimeMillis() + 120000;
			Functions.npcSay(_thisActor, Say2C.ALL, 1121006);
		}
		super.onIntentionAttack(target);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return target.getActiveWeaponInstance() != null && super.checkAggression(target);
	}
}