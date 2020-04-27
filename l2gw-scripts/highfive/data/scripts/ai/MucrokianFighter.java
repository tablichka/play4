package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 23.08.2010 22:34:45
 */
public class MucrokianFighter extends Fighter
{
	private boolean _canTalk = true;
	private long _lastAttacked;

	public MucrokianFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_canTalk = true;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;
		if(attacker.isNpc())
		{
			_lastAttacked = System.currentTimeMillis();
					 
			if(!_thisActor.isMoving && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
			{
				_thisActor.setRunning();
				_thisActor.moveToLocation(Util.getPointInRadius(_thisActor.getLoc(), Rnd.get(150, 250), (int) (Util.calculateAngleFrom(_thisActor, attacker) + Rnd.get(-45, 45))), 0, true);
			}
			return;
		}
		if(attacker.isPlayable() && _canTalk)
		{
			_canTalk = false;
			Functions.npcSayCustomInRange(_thisActor, Say2C.ALL, "MucrokianMsg", null, 600);//TODO: Найти fString и заменить.
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		if(attacker == null || attacker.isNpc())
			return;
		super.onEvtAggression(attacker, aggro, skill);
	}

	@Override
	public boolean randomWalk()
	{
		return _lastAttacked + 30000 >= System.currentTimeMillis() || super.randomWalk();
	}
}
