package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 14:37
 */
public class WizardDdmagic2Heal extends WizardDdmagic2
{
	public L2Skill MagicHeal = SkillTable.getInstance().getInfo(266403844);

	public WizardDdmagic2Heal(L2Character actor)
	{
		super(actor);
		W_ShortRangeDDMagic = SkillTable.getInstance().getInfo(272039937);
		W_LongRangeDDMagic = SkillTable.getInstance().getInfo(272629761);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		super.onEvtAttacked(attacker, damage, skill);

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(Rnd.get(100) < 33 && _thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 < 70)
			{
				if(MagicHeal.getMpConsume() < _thisActor.getCurrentMp() && MagicHeal.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(MagicHeal.getId()))
				{
					addUseSkillDesire(_thisActor, MagicHeal, 1, 1, 1000000);
				}
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(attacker == null)
			return;

		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || !CategoryManager.isInCategory(12, attacker.getNpcId())) && _thisActor.getAggroListSize() > 0 && Rnd.get(100) < 33)
		{
			if(MagicHeal.getMpConsume() < _thisActor.getCurrentMp() && MagicHeal.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(MagicHeal.getId()))
			{
				addUseSkillDesire(attacked_member, MagicHeal, 1, 1, 1000000);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}