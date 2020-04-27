package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author rage
 * @date 31.08.2010 18:31:22
 */
public class SilentValleyFighter extends FighterSummonPrivateAtDying
{
	public SilentValleyFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;
		super.onEvtAttacked(attacker.getPlayer() != null ? attacker.getPlayer() : attacker, damage, skill);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(attacker == null)
			return;
		super.onEvtAggression(attacker.getPlayer() != null ? attacker.getPlayer() : attacker, aggro, skill);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		return target.getEffectBySkillId(6033) != null && super.checkAggression(target);
	}
}
