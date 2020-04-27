package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;

/**
 * @author: rage
 * @date: 15.09.11 16:50
 */
public class WarriorCastingEnchantClan extends WarriorCastingEnchant
{
	public WarriorCastingEnchantClan(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId())) && !_thisActor.isMoving && _thisActor.i_ai1 == 0)
		{
			if(Rnd.get(100) < 50 && attacked_member.getAbnormalLevelByType(Buff.getId()) <= 0 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 50)
			{
				if(Buff.getMpConsume() < _thisActor.getCurrentMp() && Buff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(Buff.getId()))
				{
					addUseSkillDesire(attacked_member, Buff, 1, 1, 1000000);
				}
			}
		}
		_thisActor.i_ai1 = 1;
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}
