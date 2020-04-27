package ai.rr;

import ai.base.WizardDdmagic2;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 17:06
 */
public class RoyalRushWizardRangedebuff extends WizardDdmagic2
{
	public L2Skill W_RangeDeBuff = SkillTable.getInstance().getInfo(272629761);

	public RoyalRushWizardRangedebuff(L2Character actor)
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
			if(_thisActor.i_ai0 == 0)
			{
				if(Rnd.get(100) < 33)
				{
					if(W_RangeDeBuff.getMpConsume() < _thisActor.getCurrentMp() && W_RangeDeBuff.getHpConsume() < _thisActor.getCurrentHp())
					{
						if(!_thisActor.isSkillDisabled(W_RangeDeBuff.getId()))
						{
							addUseSkillDesire(attacker, W_RangeDeBuff, 0, 1, 1000000);
						}
						else
						{
							addUseSkillDesire(attacker, W_RangeDeBuff, 0, 1, 1000000);
						}
					}
					else
					{
						_thisActor.i_ai0 = 1;
						addAttackDesire(attacker, 1, 1000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}
}