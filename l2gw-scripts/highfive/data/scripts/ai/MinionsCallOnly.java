package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author rage
 * @date 26.08.2010 15:14:42
 */
public class MinionsCallOnly extends DefaultAI
{
	public MinionsCallOnly(L2Character actor)
	{
		super(actor);
		_thisActor.setImobilised(true);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(!_thisActor.canAttackCharacter(attacker))
			return;

		int aggro = skill != null ? skill.getEffectPoint() : 0;
		_thisActor.callFriends(attacker, damage > 0 ? damage : aggro);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
	}
}
