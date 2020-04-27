package ru.l2gw.gameserver.skills.effects;

import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.model.instances.L2TrapInstance;
import ru.l2gw.gameserver.skills.Env;

/**
 * @author rage
 * @date 27.11.2009 15:25:35
 */
public class i_trap_deactivate extends i_effect
{
	public i_trap_deactivate(EffectTemplate template)
	{
		super(template);
	}

	@Override
	public void doEffect(L2Character cha, GArray<Env> targets, int ss, boolean counter)
	{
		for(Env env : targets)
			if(env.target instanceof L2TrapInstance)
			{
				((L2TrapInstance) env.target).setActive(false);
				((L2NpcInstance) env.target).notifyAiEvent(env.target, CtrlEvent.EVT_TRAP_DEFUSED, cha, null, null);
			}
	}
}
