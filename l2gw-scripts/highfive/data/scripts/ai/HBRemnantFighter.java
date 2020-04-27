package ai;

import ru.l2gw.extensions.listeners.engine.MethodInvocationResult;
import ru.l2gw.extensions.listeners.events.MethodEvent;
import ru.l2gw.extensions.listeners.reduceHp.ReduceCurrentHpListener;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 20.01.2010 21:03:35
 */
public class HBRemnantFighter extends Fighter
{
	private static int hollyWaterSkillId = 2358;

	public HBRemnantFighter(L2Character actor)
	{
		super(actor);
		_thisActor.getListenerEngine().addMethodInvokedListener(new HPListener());
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		super.onEvtSeeSpell(skill, caster);
		if(skill.getId() == hollyWaterSkillId && caster.getTarget() == _thisActor && _thisActor.getCurrentHp() < 60)
			_thisActor.doDie(caster);
	}

	private class HPListener extends ReduceCurrentHpListener
	{
		@Override
		public void onReduceCurrentHp(L2Character actor, double damage, L2Character attacker, boolean directHp, MethodEvent e)
		{
			if(_thisActor.getCurrentHp() - damage < 50)
			{
				if(_thisActor.getCurrentHp() > 50)
					_thisActor.decreaseHp(_thisActor.getCurrentHp() - 50, attacker, directHp, false);
				e.setInvocationResult(MethodInvocationResult.BLOCK);
			}
		}
	}
}
