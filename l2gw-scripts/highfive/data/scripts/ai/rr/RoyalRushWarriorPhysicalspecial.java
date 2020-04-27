package ai.rr;

import ai.base.WarriorPhysicalspecial;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 16:17
 */
public class RoyalRushWarriorPhysicalspecial extends WarriorPhysicalspecial
{
	public L2Skill SelfBuff = null;
	public int WeaponID = 0;

	public RoyalRushWarriorPhysicalspecial(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(SelfBuff != null)
		{
			if(SelfBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff.getId()))
			{
				addUseSkillDesire(_thisActor, SelfBuff, 1, 1, 1000000);
			}
			addTimer(3000, 120000);
		}
		_thisActor.i_ai0 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 50 && WeaponID > 0 && _thisActor.i_ai0 == 0)
		{
			_thisActor.equipItem(WeaponID);
			_thisActor.changeWeaponEnchant(15);
			_thisActor.i_ai0 = 1;
		}
		if((attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && _thisActor.i_ai0 == 0)
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker && _thisActor.getLoc().distance3D(attacker.getLoc()) < 100)
				{
					if(_thisActor.i_ai0 == 0)
					{
						if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
						{
							addUseSkillDesire(attacker, PhysicalSpecial, 0, 1, 1000000);
						}
					}
					else
					{
						_thisActor.useSoulShot(20);
					}
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if((_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))) && !_thisActor.isMoving)
		{
			if(Rnd.get(100) < 33 && _thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
			{
				if(_thisActor.i_ai0 == 0)
				{
					if(PhysicalSpecial.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.useSoulShot(20);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3000)
		{
			if(SkillTable.getAbnormalLevel(_thisActor, SelfBuff) <= 0)
			{
				if(SelfBuff.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff.getId()))
				{
					addUseSkillDesire(_thisActor, SelfBuff, 1, 1, 1000000);
				}
			}
			addTimer(3000, 120000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
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