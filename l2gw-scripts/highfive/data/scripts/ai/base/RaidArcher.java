package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 20.01.12 20:40
 */
public class RaidArcher extends RaidPrivateStandard
{
	public L2Skill LongRangePhysicalSpecial_a = SkillTable.getInstance().getInfo(458752001);

	public RaidArcher(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai2 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai2 == 0 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 100)
		{
			addTimer(100002, 2000);
			_thisActor.i_ai2 = 1;
			_thisActor.c_ai1 = attacker.getStoredId();
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 100002)
		{
			addFleeDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1), DEFAULT_DESIRE * 2);
			_thisActor.i_ai2 = 0;
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtPartyAttacked(L2Character attacker, L2Character victim, int damage)
	{
		if(_thisActor.getMostHated() != null)
		{
			if(((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && attacker == _thisActor.getMostHated()) && Rnd.get(20) < 1)
			{
				addUseSkillDesire(attacker, LongRangePhysicalSpecial_a, 0, 1, 1000000);
			}
		}
		super.onEvtPartyAttacked(attacker, victim, damage);
	}

	@Override
	protected void onEvtSeeSpell(L2Skill skill, L2Character caster)
	{
		if(_thisActor.getMostHated() != null)
		{
			if(caster == _thisActor.getMostHated() && Rnd.get(20) < 1)
			{
				addUseSkillDesire(caster, LongRangePhysicalSpecial_a, 0, 1, 1000000);
			}
		}
		super.onEvtSeeSpell(skill, caster);
	}
}