package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * AI охраны входа в Pagan Temple.
 * кидаются на всех игроков, у которых в кармане нету предмета 8064 или 8067
 * не умеют ходить
 *
 * @author SYS
 */
public class GatekeeperZombie extends Fighter
{
	public GatekeeperZombie(L2Character actor)
	{
		super(actor);
		_actor.setImobilised(true);
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		if(!(target instanceof L2Playable))
			return false;

		if(_intention != AI_INTENTION_ACTIVE)
			return false;
		if(_globalAggro < 0)
			return false;
		if(!_thisActor.isInRange(target, _thisActor.getAggroRange()))
			return false;
		if(Math.abs(target.getZ() - _actor.getZ()) > 400)
			return false;
		if(Functions.getItemCount((L2Playable) target, 8067) != 0 || Functions.getItemCount((L2Playable) target, 8064) != 0)
			return false;
		if(!GeoEngine.canSeeTarget(_actor, target))
			return false;
		_thisActor.addDamageHate(target, 0, 1);
		setIntention(CtrlIntention.AI_INTENTION_ATTACK, target);
		return true;
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}