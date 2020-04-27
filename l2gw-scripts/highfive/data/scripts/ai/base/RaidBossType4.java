package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 29.12.11 11:41
 */
public class RaidBossType4 extends RaidBossParty
{
	public L2Skill SelfRangeDebuff_a = null;
	public L2Skill SelfRangeDebuffAnother_a = null;
	public L2Skill DDMagic_a = null;

	public RaidBossType4(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null)
			{
				if(_thisActor.getLoc().distance3D(attacker.getLoc()) < 150 && _thisActor.getLoc().distance3D(c0.getLoc()) < 150 && SkillTable.getAbnormalLevel(attacker, SelfRangeDebuff_a) == -1 && SkillTable.getAbnormalLevel(c0, SelfRangeDebuff_a) == -1 && attacker != c0 && Rnd.get(2) < 1)
				{
					addUseSkillDesire(_thisActor, SelfRangeDebuff_a, 0, 1, 1000000);
				}
				if(_thisActor.getLoc().distance3D(attacker.getLoc()) < 150 && _thisActor.getLoc().distance3D(c0.getLoc()) < 150 && SkillTable.getAbnormalLevel(attacker, SelfRangeDebuffAnother_a) == -1 && SkillTable.getAbnormalLevel(c0, SelfRangeDebuffAnother_a) == -1 && attacker != c0 && Rnd.get(5) < 1)
				{
					addUseSkillDesire(_thisActor, SelfRangeDebuffAnother_a, 0, 1, 1000000);
				}
			}
		}
		if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && Rnd.get(5 * 15) < 1)
		{
			addUseSkillDesire(attacker, DDMagic_a, 0, 1, 1000000);
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(caster != null)
		{
			L2Character c0 = _thisActor.getMostHated();
			if(c0 != null)
			{
				if(_thisActor.getLoc().distance3D(caster.getLoc()) < 150 && _thisActor.getLoc().distance3D(c0.getLoc()) < 150 && SkillTable.getAbnormalLevel(caster, SelfRangeDebuff_a) == -1 && SkillTable.getAbnormalLevel(c0, SelfRangeDebuff_a) == -1 && caster != c0 && Rnd.get(2) < 1)
				{
					addUseSkillDesire(_thisActor, SelfRangeDebuff_a, 0, 1, 1000000);
				}
				if(_thisActor.getLoc().distance3D(caster.getLoc()) < 150 && _thisActor.getLoc().distance3D(c0.getLoc()) < 150 && SkillTable.getAbnormalLevel(caster, SelfRangeDebuffAnother_a) == -1 && SkillTable.getAbnormalLevel(c0, SelfRangeDebuffAnother_a) == -1 && caster != c0 && Rnd.get(5) < 1)
				{
					addUseSkillDesire(_thisActor, SelfRangeDebuffAnother_a, 0, 1, 1000000);
				}
			}
		}
		if(Rnd.get(5 * 15) < 1)
		{
			addUseSkillDesire(caster, DDMagic_a, 0, 1, 1000000);
		}
		super.onEvtSeeSpell(skill, caster);
	}
}