package ai.rr;

import ai.base.Warrior;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 15:38
 */
public class RoyalRushWarriorPhysicalspecial1 extends Warrior
{
	public L2Skill PhysicalSpecial1 = SkillTable.getInstance().getInfo(262209537);

	public RoyalRushWarriorPhysicalspecial1(L2Character actor)
	{
		super(actor);
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
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker && _thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
				{
					if(PhysicalSpecial1.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial1.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial1, 0, 1, 1000000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && !_thisActor.isMoving)
		{
			if(Rnd.get(100) < 33 && _thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
			{
				if(PhysicalSpecial1.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial1.getId()))
				{
					addUseSkillDesire(attacker, PhysicalSpecial1, 0, 1, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 1234)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(Rnd.get(100) < 80)
				{
					addAttackDesire(c0, 1, 300);
				}
				else
				{
					removeAllAttackDesire();
					addAttackDesire(c0, 1, 1000);
				}
			}
		}
	}
}