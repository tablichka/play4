package ai;

import ai.base.Warrior;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 16:30
 */
public class AiSolinaWarrior extends Warrior
{
	public L2Skill PhysicalSpecial = SkillTable.getInstance().getInfo(413597697);
	public int TIMER = 100;

	public AiSolinaWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		if(_thisActor.param1 == 1000)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.param2);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 10000);
			}
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.getLifeTime() > 7 && _thisActor.inMyTerritory(_thisActor) && _thisActor.getMostHated() == null)
		{
			addAttackDesire(creature, 1, 1000);
		}
		super.onEvtSeeCreature(creature);
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
				if(Rnd.get(100) < 10 && _thisActor.getMostHated() == attacker)
				{
					if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial, 0, 1, 1000000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtNoDesire()
	{
		_thisActor.i_ai0 = 1;
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 21140014 && _thisActor.i_ai0 == 1)
		{
			if(Rnd.get(100) < 30)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				addAttackDesire(c0, 1, 100);
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
		addTimer(TIMER, 30000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER)
		{
			_thisActor.i_ai0 = 0;
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())))
		{
			addAttackDesire(attacker, 1, 1000);
			if(Rnd.get(100) < 10)
			{
				if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
				{
					addUseSkillDesire(attacker, PhysicalSpecial, 0, 1, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}