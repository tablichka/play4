package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 01.12.2009 13:55:09
 */
public class EvasProtector extends DefaultAI
{
	public EvasProtector(L2Character actor)
	{
		super(actor);
		_thisActor.hasChatWindow = false;
		_isMobile = false;
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster != null && caster.isPlayer() && skill != null && caster.getTarget() == _thisActor && _thisActor.getMaxHp() <= (int) _thisActor.getCurrentHp())
			_thisActor.getSpawn().getInstance().notifyKill(_thisActor, caster.getPlayer());
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}
