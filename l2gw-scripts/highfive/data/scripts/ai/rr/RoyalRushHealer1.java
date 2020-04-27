package ai.rr;

import ai.base.Wizard;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 19.01.12 16:39
 */
public class RoyalRushHealer1 extends Wizard
{
	public L2Skill W_RangeHeal = SkillTable.getInstance().getInfo(266403844);
	public L2Skill W_RangeDeBuff = SkillTable.getInstance().getInfo(262209537);
	public L2Skill W_SelfRangeDDMagic = SkillTable.getInstance().getInfo(262209537);

	public RoyalRushHealer1(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
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
				else if(W_SelfRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_SelfRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp())
				{
					if(!_thisActor.isSkillDisabled(W_SelfRangeDDMagic.getId()))
					{
						addUseSkillDesire(_thisActor, W_SelfRangeDDMagic, 0, 1, 1000000);
					}
					else
					{
						addUseSkillDesire(_thisActor, W_SelfRangeDDMagic, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.i_ai0 = 1;
					addAttackDesire(_thisActor, 1, 1000);
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(Rnd.get(100) < 33)
		{
			if(W_RangeHeal.getMpConsume() < _thisActor.getCurrentMp() && W_RangeHeal.getHpConsume() < _thisActor.getCurrentHp())
			{
				if(!_thisActor.isSkillDisabled(W_RangeHeal.getId()))
				{
					addUseSkillDesire(attacker, W_RangeHeal, 0, 1, 1000000);
				}
				else
				{
					addUseSkillDesire(attacker, W_RangeHeal, 0, 1, 1000000);
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

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(_thisActor.getMostHated() != null)
		{
			if(_thisActor.i_ai0 != 1)
			{
				if(W_SelfRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_SelfRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp())
				{
					if(!_thisActor.isSkillDisabled(W_SelfRangeDDMagic.getId()))
					{
						addUseSkillDesire(_thisActor, W_SelfRangeDDMagic, 0, 1, 1000000);
					}
					else
					{
						addUseSkillDesire(_thisActor, W_SelfRangeDDMagic, 0, 1, 1000000);
					}
				}
				else
				{
					_thisActor.i_ai0 = 1;
					addAttackDesire(_thisActor, 1, 1000);
				}
			}
		}
	}
}