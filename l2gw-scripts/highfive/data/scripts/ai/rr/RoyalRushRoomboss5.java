package ai.rr;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 15:14
 */
public class RoyalRushRoomboss5 extends RoyalRushRoombossBasic
{
	public L2Skill SelfRangeDDMagic1 = SkillTable.getInstance().getInfo(264241153);
	public L2Skill DeBuff = SkillTable.getInstance().getInfo(264241153);
	public L2Skill DDMagic = SkillTable.getInstance().getInfo(264241153);

	public RoyalRushRoomboss5(L2Character actor)
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
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() != attacker)
				{
					if(SelfRangeDDMagic1.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDDMagic1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfRangeDDMagic1.getId()))
					{
						addUseSkillDesire(_thisActor, SelfRangeDDMagic1, 0, 1, 1000000);
					}
				}
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
				{
					if(DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()))
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
				}
				if(Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker)
				{
					if(DDMagic.getMpConsume() < _thisActor.getCurrentMp() && DDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DDMagic.getId()))
					{
						addUseSkillDesire(attacker, DDMagic, 0, 1, 1000000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}