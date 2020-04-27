package ai.rr;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 15:41
 */
public class RoyalRushWarriorPhysicalspecial2 extends RoyalRushWarriorPhysicalspecial1
{
	public L2Skill PhysicalSpecial2 = SkillTable.getInstance().getInfo(264241153);
	public int WeaponID = 0;

	public RoyalRushWarriorPhysicalspecial2(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
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
		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.getMostHated() != null)
			{
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker && _thisActor.getLoc().distance3D(attacker.getLoc()) < 100)
				{
					if(_thisActor.i_ai0 == 0)
					{
						if(PhysicalSpecial2.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial2.getId()))
						{
							addUseSkillDesire(attacker, PhysicalSpecial2, 0, 1, 1000000);
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
			if(Rnd.get(100) < 33 && _thisActor.getLoc().distance3D(attacker.getLoc()) < 100)
			{
				if(_thisActor.i_ai0 == 0)
				{
					if(PhysicalSpecial2.getMpConsume() < _thisActor.getCurrentMp() && PhysicalSpecial2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(PhysicalSpecial2.getId()))
					{
						addUseSkillDesire(attacker, PhysicalSpecial2, 0, 1, 1000000);
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
}