package ai.rr;

import ai.base.WizardDdmagic2;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 17:03
 */
public class RoyalRushWizardClanbuff extends WizardDdmagic2
{
	public L2Skill W_ClanBuff = SkillTable.getInstance().getInfo(272039937);

	public RoyalRushWizardClanbuff(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		_thisActor.removeAllHateInfoIF(1, 0);
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || !CategoryManager.isInCategory(12, attacker.getNpcId())) && _thisActor.getAggroListSize() > 0)
		{
			if(W_ClanBuff.isOffensive() && W_ClanBuff.getMpConsume() < _thisActor.getCurrentMp() && W_ClanBuff.getHpConsume() < _thisActor.getCurrentHp())
			{
				if(!_thisActor.isSkillDisabled(W_ClanBuff.getId()))
				{
					addUseSkillDesire(attacker, W_ClanBuff, 0, 1, 1000000);
				}
				else
				{
					addUseSkillDesire(attacker, W_ClanBuff, 0, 1, 1000000);
				}
			}
			else
			{
				_thisActor.i_ai0 = 1;
				addAttackDesire(attacker, 1, 1000);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}