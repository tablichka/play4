package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 20.01.12 20:43
 */
public class RaidWizard extends RaidPrivateStandard
{
	public L2Skill Debuff_a = SkillTable.getInstance().getInfo(458752001);
	public L2Skill DDMagic_a = SkillTable.getInstance().getInfo(458752001);

	public RaidWizard(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.c_ai0 = _thisActor.getStoredId();
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null && c0 != _thisActor && _thisActor.getLoc().distance3D(c0.getLoc()) < 200)
			{
				addFleeDesire(c0, 100000000);
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.c_ai0 = attacker.getStoredId();
		if(!_thisActor.isMyBossAlive())
		{
			if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && Rnd.get(2 * 15) < 1)
			{
				addUseSkillDesire(attacker, DDMagic_a, 0, 1, 1000000);
			}
			if(SkillTable.getAbnormalLevel(attacker, Debuff_a) == -1 && Rnd.get(5 * 15) < 1)
			{
				addUseSkillDesire(attacker, Debuff_a, 0, 1, 1000000);
			}
		}
		if(_thisActor.isInZonePeace())
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			removeAllAttackDesire();
		}
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(attacker.getLevel() >= (_thisActor.getLevel() + 8))
		{
			if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && Rnd.get(2 * 15 * 10) < 1)
			{
				addUseSkillDesire(attacker, DDMagic_a, 0, 1, 1000000);
			}
			if(SkillTable.getAbnormalLevel(attacker, Debuff_a) == -1 && Rnd.get(5 * 15 * 10) < 1)
			{
				addUseSkillDesire(attacker, Debuff_a, 0, 1, 1000000);
			}
		}
		else if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && Rnd.get(2 * 15) < 1)
		{
			addUseSkillDesire(attacker, DDMagic_a, 0, 1, 1000000);
		}
		if(SkillTable.getAbnormalLevel(attacker, Debuff_a) == -1 && Rnd.get(5 * 15) < 1)
		{
			addUseSkillDesire(attacker, Debuff_a, 0, 1, 1000000);
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(Rnd.get(2 * 15) < 1)
		{
			addUseSkillDesire(caster, DDMagic_a, 0, 1, 1000000);
		}
		if(SkillTable.getAbnormalLevel(caster, Debuff_a) == -1 && Rnd.get(5 * 15) < 1)
		{
			addUseSkillDesire(caster, Debuff_a, 0, 1, 1000000);
		}
		super.onEvtSeeSpell(skill, caster);
	}
}