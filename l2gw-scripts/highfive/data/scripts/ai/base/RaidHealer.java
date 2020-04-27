package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 23.09.11 16:57
 */
public class RaidHealer extends RaidPrivateStandard
{
	public L2Skill HealMagic_a = SkillTable.getInstance().getInfo(458752001);
	public L2Skill SelfRangeBuff_a = SkillTable.getInstance().getInfo(458752001);

	public RaidHealer(L2Character actor)
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
			if(Rnd.get(3) < 1)
			{
				addUseSkillDesire(_thisActor, SelfRangeBuff_a, 1, 1, 1000000);
			}
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			_thisActor.c_ai0 = attacker.getStoredId();
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
		if(victim.getCurrentHp() < (victim.getMaxHp() / 2) && Rnd.get(3) < 1)
		{
			addUseSkillDesire(victim, HealMagic_a, 1, 1, 1000000);
		}
		addUseSkillDesire(victim, HealMagic_a, 1, 1, 100);
		if(_thisActor.isInZonePeace())
		{
			_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			removeAllAttackDesire();
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
		if(c0 != null && skill == HealMagic_a && c0 != _thisActor && _thisActor.getLoc().distance3D(c0.getLoc()) < 200)
		{
			addFleeDesire(c0, 100000000);
		}
	}
}