package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 23.09.11 16:21
 */
public class RaidBossType1 extends RaidBossStandard
{
	public L2Skill PhysicalSpecial_a = null;
	public L2Skill SelfRangePhysicalSpecial_a = null;
	public L2Skill SelfBuff_a = null;
	public L2Skill SelfRangeCancel_a = null;

	public RaidBossType1(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(Rnd.get(5) < 1)
			{
				addUseSkillDesire(_thisActor, SelfBuff_a, 1, 1, 1000000);
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(attacker == _thisActor.getMostHated() && Rnd.get(15) < 1)
				{
					addUseSkillDesire(attacker, PhysicalSpecial_a, 0, 1, 1000000);
				}
				if(attacker != _thisActor.getMostHated() && _thisActor.getLoc().distance3D(attacker.getLoc()) < 150 && Rnd.get((25 * 15)) < 1)
				{
					addUseSkillDesire(_thisActor, SelfRangePhysicalSpecial_a, 0, 1, 1000000);
				}
				if(_thisActor.getLoc().distance3D(attacker.getLoc()) < 150 && Rnd.get((50 * 15)) < 1)
				{
					addUseSkillDesire(_thisActor, SelfRangeCancel_a, 0, 1, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster != null && _thisActor.getMostHated() != null)
		{
			if(caster == _thisActor.getMostHated() && Rnd.get(15) < 1)
			{
				addUseSkillDesire(caster, PhysicalSpecial_a, 0, 1, 1000000);
			}
			if(caster != _thisActor.getMostHated() && _thisActor.getLoc().distance3D(caster.getLoc()) < 150 && Rnd.get((25 * 15)) < 1)
			{
				addUseSkillDesire(_thisActor, SelfRangePhysicalSpecial_a, 0, 1, 1000000);
			}
			if(_thisActor.getLoc().distance3D(caster.getLoc()) < 150 && Rnd.get((50 * 15)) < 1)
			{
				addUseSkillDesire(_thisActor, SelfRangeCancel_a, 0, 1, 1000000);
			}
		}
		super.onEvtSeeSpell(skill, caster);
	}
}