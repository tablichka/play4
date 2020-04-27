package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * <hr>AI моба <strong>Water Dragon Detractor</strong> npc_id=22270,22271<hr>
 * <li>спавнятся из АИ Fafurion
 * <li>деспавнятся через 5 минут
 * <li>после убийства цели отсчет деспавна заново
 * <li>если увидел игрока поблизости, то отсчет деспавна заново
 * <li>не используют функцию Random Walk
 * <hr>
 * @author rage
 * @since 2009.10.21
 */
public class WaterDragonDetractor extends Fighter
{
	private long _lastAttack;

	public WaterDragonDetractor(L2Character actor)
	{
		super(actor);
		_lastAttack = System.currentTimeMillis() + 5 * 60000;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_lastAttack = System.currentTimeMillis() + 5 * 60000;
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onIntentionAttack(L2Character target)
	{
		_lastAttack = System.currentTimeMillis() + 5 * 60000;
		super.onIntentionAttack(target);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_lastAttack < System.currentTimeMillis())
		{
			if(_thisActor.getSpawn() != null)
				_thisActor.getSpawn().stopRespawn();
			_thisActor.deleteMe();
			return false;
		}
		return super.thinkActive();
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}
}