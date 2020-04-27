package ai.rr;

import ai.base.Wizard;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 16:40
 */
public class RoyalRushHealer2 extends Wizard
{
	public L2Skill W_RangeHeal = SkillTable.getInstance().getInfo(266403841);
	public L2Skill W_LongRangeDDMagic = SkillTable.getInstance().getInfo(266403841);
	public L2Skill W_SelfRangeDDMagic = SkillTable.getInstance().getInfo(262209537);
	public L2Skill W_SelfRangeDeBuff = SkillTable.getInstance().getInfo(262209537);

	public RoyalRushHealer2(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
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
					if(W_SelfRangeDeBuff.getMpConsume() < _thisActor.getCurrentMp() && W_SelfRangeDeBuff.getHpConsume() < _thisActor.getCurrentHp())
					{
						if(!_thisActor.isSkillDisabled(W_SelfRangeDeBuff.getId()))
						{
							addUseSkillDesire(_thisActor, W_SelfRangeDeBuff, 0, 1, 1000000);
						}
						else
						{
							addUseSkillDesire(_thisActor, W_SelfRangeDeBuff, 0, 1, 1000000);
						}
					}
					else
					{
						_thisActor.i_ai0 = 1;
						addAttackDesire(_thisActor, 1, 1000);
					}
				}
				if(Rnd.get(100) < 33 && _thisActor.getLoc().distance3D(attacker.getLoc()) > 100)
				{
					if(W_LongRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_LongRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp())
					{
						if(!_thisActor.isSkillDisabled(W_LongRangeDDMagic.getId()))
						{
							addUseSkillDesire(attacker, W_LongRangeDDMagic, 0, 1, 1000000);
						}
						else
						{
							addUseSkillDesire(attacker, W_LongRangeDDMagic, 0, 1, 1000000);
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
		L2Character c0 = _thisActor.getMostHated();
		if(c0 != null)
		{
			if(_thisActor.i_ai0 != 1)
			{
				if(Rnd.get(100) < 33 && _thisActor.getLoc().distance3D(c0.getLoc()) > 100)
				{
					if(W_LongRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && W_LongRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp())
					{
						if(!_thisActor.isSkillDisabled(W_LongRangeDDMagic.getId()))
						{
							addUseSkillDesire(c0, W_LongRangeDDMagic, 0, 1, 1000000);
						}
						else
						{
							addUseSkillDesire(c0, W_LongRangeDDMagic, 0, 1, 1000000);
						}
					}
					else
					{
						_thisActor.i_ai0 = 1;
						addAttackDesire(c0, 1, 1000);
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
	}
}