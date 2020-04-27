package ai.rr;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 15:12
 */
public class RoyalRushRoomboss3 extends RoyalRushRoombossBasic
{
	public L2Skill SelfRangeDDMagic1 = SkillTable.getInstance().getInfo(264241153);
	public int SummonSlave = 20130;

	public RoyalRushRoomboss3(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		super.onEvtSpawn();
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
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() != attacker)
				{
					if(SelfRangeDDMagic1.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDDMagic1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfRangeDDMagic1.getId()))
					{
						addUseSkillDesire(_thisActor, SelfRangeDDMagic1, 1, 1, 1000000);
					}
				}
				if(Rnd.get(100) < 33 && _thisActor.i_ai0 == 0)
				{
					_thisActor.createOnePrivate(SummonSlave, "rr.RoyalRushBomb", 0, 0, attacker.getX(), attacker.getY(), attacker.getZ(), 0, 1000, getStoredIdFromCreature(_thisActor.getMostHated()), 0);
					_thisActor.i_ai0 = 1;
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}