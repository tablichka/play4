package ai.rr;

import ai.base.WarriorPhysicalspecial;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 19.01.12 17:40
 */
public class RoyalRushWarriorStone extends WarriorPhysicalspecial
{
	public L2Skill DeBuff = SkillTable.getInstance().getInfo(277348353);
	public L2Skill SelfRangeDDMagic = SkillTable.getInstance().getInfo(277348353);
	public L2Skill SelfBuff1 = SkillTable.getInstance().getInfo(277348353);
	public L2Skill SelfBuff2 = SkillTable.getInstance().getInfo(277348353);

	public RoyalRushWarriorStone(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		if( SelfBuff1.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff1.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff1.getId()) )
		{
			addUseSkillDesire(_thisActor, SelfBuff1, 1, 1, 1000000);
		}
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if( _thisActor.getLifeTime() > 7 && !_thisActor.isMoving )
		{
			if( !creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()) )
			{
				return;
			}
			if( SeeCreatureAttackerTime == -1 )
			{
				if( SetAggressiveTime == -1 )
				{
					if( _thisActor.getLifeTime() >= ( Rnd.get(5) + 3 ) && _thisActor.inMyTerritory(_thisActor) )
					{
						addAttackDesire(creature, 1, 200);
					}
				}
				else if( SetAggressiveTime == 0 )
				{
					if( _thisActor.inMyTerritory(_thisActor) )
					{
						addAttackDesire(creature, 1, 200);
					}
				}
				else if( _thisActor.getLifeTime() > ( SetAggressiveTime + Rnd.get(4) ) && _thisActor.inMyTerritory(_thisActor) )
				{
					addAttackDesire(creature, 1, 200);
				}
			}
			else if( _thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor) )
			{
				addAttackDesire(creature, 1, 200);
			}
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if( attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) )
		{
			if( _thisActor.getMostHated() != null )
			{
				if( Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker )
				{
					if( DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()) )
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
				}
				if( Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker )
				{
					if( SelfRangeDDMagic.getMpConsume() < _thisActor.getCurrentMp() && SelfRangeDDMagic.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfRangeDDMagic.getId()) )
					{
						addUseSkillDesire(_thisActor, SelfRangeDDMagic, 1, 1, 1000000);
					}
				}
				if( Rnd.get(100) < 33 && _thisActor.getMostHated() == attacker )
				{
					if( SelfBuff2.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff2.getId()) )
					{
						addUseSkillDesire(_thisActor, SelfBuff2, 1, 1, 1000000);
					}
				}
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if( ( _thisActor.getLifeTime() > 7 && ( attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()) ) ) && !_thisActor.isMoving )
		{
			if( Rnd.get(100) < 33 )
			{
				if( SelfBuff2.getMpConsume() < _thisActor.getCurrentMp() && SelfBuff2.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(SelfBuff2.getId()) )
				{
					addUseSkillDesire(_thisActor, SelfBuff2, 1, 1, 1000000);
				}
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}
}