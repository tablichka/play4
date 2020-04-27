package ru.l2gw.gameserver.skills.effects;

import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.extensions.listeners.MethodCollection;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.skills.Env;
import ru.l2gw.gameserver.skills.Stats;

/**
 * User: ic
 * Date: 23.04.2010
 */
public class i_cp_per_max extends i_effect
{
	public i_cp_per_max(EffectTemplate template)
	{
		super(template);
	}

	@Override
	public void doEffect(L2Character cha, GArray<Env> targets, int ss, boolean counter)
	{
		for(Env env : targets)
		{
			if(env.target == null || env.target.isDead())
				continue;

			double newCp = calc();

			if(env.target.isStatActive(Stats.BLOCK_HP))
				newCp = 0;

			newCp = 0.01 * newCp * env.target.getMaxCp();

			int cpLimit = (int) env.target.calcStat(Stats.CP_LIMIT, env.target.getMaxCp(), null, null);
			int oldCp = (int) env.target.getCurrentCp();
			if(newCp > 0 && env.target.getCurrentCp() + newCp > cpLimit)  // Positive effect
				newCp = Math.max(0, cpLimit - env.target.getCurrentCp());

			env.target.setCurrentCp(env.target.getCurrentCp() + newCp);
			newCp = env.target.getCurrentCp() - oldCp;

			if(newCp >= 0)
			{
				if(env.target == cha)
					env.target.sendPacket(new SystemMessage(SystemMessage.S1_CPS_HAVE_BEEN_RESTORED).addNumber((int) newCp));
				else
					env.target.sendPacket(new SystemMessage(SystemMessage.S2_CP_HAS_BEEN_RESTORED_BY_C1).addCharName(cha).addNumber((int) newCp));

				cha.fireMethodInvoked(MethodCollection.onHeal, new Object[]{env.target, newCp});
			}
		}
	}
}
