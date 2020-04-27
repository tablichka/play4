package ai.rr;

import ai.base.Wizard;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 16:30
 */
public class RoyalRushWizardDdmagic2Hold extends Wizard
{
	public L2Skill W_ShortRangeDDMagic = SkillTable.getInstance().getInfo(272629761);
	public L2Skill W_LongRangeDDMagic = SkillTable.getInstance().getInfo(272039937);

	public RoyalRushWizardDdmagic2Hold(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		super.onEvtAttacked(attacker, damage, skill);

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.i_ai0 == 0)
			{
				int i0 = _thisActor.getMostHated() == attacker ? 1 : 0;

				if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100 && Rnd.get(100) < 80)
				{
					if(i0 == 1)
					{
						if(W_LongRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_LongRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_LongRangeDDMagic.getId()))
						{
							addUseSkillDesire(attacker, W_LongRangeDDMagic, 0, 0, 1000000);
						}
					}
					else if(Rnd.get(100) < 2)
					{
						if(W_LongRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_LongRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_LongRangeDDMagic.getId()))
						{
							addUseSkillDesire(attacker, W_LongRangeDDMagic, 0, 0, 1000000);
						}
					}
				}
				else if(i0 == 1)
				{
					if(W_ShortRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_ShortRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_ShortRangeDDMagic.getId()))
					{
						addUseSkillDesire(attacker, W_ShortRangeDDMagic, 0, 0, 1000000);
					}
				}
				else if(Rnd.get(100) < 2)
				{
					if(W_ShortRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_ShortRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_ShortRangeDDMagic.getId()))
					{
						addUseSkillDesire(attacker, W_ShortRangeDDMagic, 0, 0, 1000000);
					}
				}
			}
			else if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
			{
				float f0 = 0;
				if(SetHateGroup >= 0)
				{
					if(CategoryManager.isInCategory(SetHateGroup, attacker.getActiveClass()))
					{
						f0 += SetHateGroupRatio;
					}
				}
				if(attacker.getActiveClass() == SetHateOccupation)
				{
					f0 += SetHateOccupationRatio;
				}

				if(SetHateRace == attacker.getPlayer().getRace().ordinal())
				{
					f0 += SetHateRaceRatio;
				}
				f0 = (float) (1.000000 * damage / (_thisActor.getLevel() + 7) + f0 / 100 * 1.000000 * damage / (_thisActor.getLevel() + 7));
				_thisActor.addDamageHate(attacker, 0, (long) f0);
				addAttackDesire(attacker, 1, DEFAULT_DESIRE);
			}
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		_thisActor.removeAllHateInfoIF(1, 0);
		if(_thisActor.getLifeTime() > 7 && (attacker.isPlayer() || !CategoryManager.isInCategory(12, attacker.getNpcId())) && _thisActor.getAggroListSize() > 0)
		{
			if(_thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
			{
				if(W_LongRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_LongRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_LongRangeDDMagic.getId()))
				{
					addUseSkillDesire(attacker, W_LongRangeDDMagic, 0, 0, 1000000);
				}
			}
			else if(W_ShortRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_ShortRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_ShortRangeDDMagic.getId()))
			{
				addUseSkillDesire(attacker, W_ShortRangeDDMagic, 0, 0, 1000000);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character c0 = _thisActor.getMostHated();
		if(c0 != null)
		{
			if(_thisActor.i_ai0 != 1)
			{
				if(_thisActor.getLoc().distance3D(c0.getLoc()) > 100)
				{
					if(W_LongRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_LongRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_LongRangeDDMagic.getId()))
					{
						addUseSkillDesire(c0, W_LongRangeDDMagic, 0, 0, 1000000);
					}
				}
				else if(W_ShortRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_ShortRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(W_ShortRangeDDMagic.getId()))
				{
					addUseSkillDesire(c0, W_ShortRangeDDMagic, 0, 0, 1000000);
				}
			}
		}
	}
}