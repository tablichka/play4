package ru.l2gw.gameserver.skills.effects;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.serverpackets.SystemMessage;
import ru.l2gw.gameserver.skills.Env;
import ru.l2gw.gameserver.skills.Formulas;
import ru.l2gw.gameserver.skills.Stats;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author: rage
 * @date: 18.10.2009 17:06:10
 */
public class i_m_attack_by_debuff extends i_effect
{
	private final int debuffMod;

	public i_m_attack_by_debuff(EffectTemplate template)
	{
		super(template);
		debuffMod = template._attrs.getInteger("debuffMod", 0);
	}

	@Override
	public void doEffect(L2Character cha, GArray<Env> targets, int ss, boolean counter)
	{
		for(Env env : targets)
		{
			if(env.target.isDead())
				continue;

			boolean shield = Formulas.calcShldUse(cha, env.target);
			double damage = Formulas.calcMagicDam(cha, env.target, getSkill(), shield, ss);

			damage *= 1 + debuffMod / 100 * env.target.getAllEffects().size();

			boolean blockHp = env.target.isStatActive(Stats.BLOCK_HP);
			if(blockHp)
				damage = 0;

			if(getSkill().getPower(cha, env.target) > 0)
			{
				if(damage > 0 && damage < 2)
					damage = Math.ceil(damage);
				if(damage == 0 && !blockHp)
					env.target.sendPacket(new SystemMessage(SystemMessage.S1_HAS_RECEIVED_DAMAGE_OF_S3_FROM_S2).addCharName(env.target).addCharName(cha).addNumber(0));

				cha.sendDamageMessage(env.target, (int) damage, false, false, blockHp);
				env.target.reduceHp(damage, cha, false, false);
			}
		}
	}
}
