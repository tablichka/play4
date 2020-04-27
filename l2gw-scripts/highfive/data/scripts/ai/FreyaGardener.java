package ai;

import ai.base.PartyLeader;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 21.09.11 19:20
 */
public class FreyaGardener extends PartyLeader
{
	public L2Skill RangeHold_a = SkillTable.getInstance().getInfo(458752001);
	public L2Skill DeBuff = SkillTable.getInstance().getInfo(458752001);

	public FreyaGardener(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai1 = 0;
		_thisActor.c_ai0 = 0;
		_thisActor.c_ai1 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(attacker.isPlayer() || CategoryManager.isInCategory(12, attacker.getNpcId()))
		{
			if(_thisActor.i_ai1 < 3 && attacker.getAbnormalLevelByType(DeBuff.getId()) == -1)
			{
				if(_thisActor.i_ai1 == 0)
				{
					_thisActor.c_ai0 = attacker.getStoredId();
					if(DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()))
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
					_thisActor.i_ai1++;
				}
				else if(_thisActor.i_ai1 == 1 && _thisActor.c_ai0 != attacker.getStoredId())
				{
					_thisActor.c_ai1 = attacker.getStoredId();
					if(DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()))
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
					_thisActor.i_ai1++;
				}
				else if(_thisActor.i_ai1 == 2 && _thisActor.c_ai0 != attacker.getStoredId() && _thisActor.c_ai1 != attacker.getStoredId())
				{
					if(DeBuff.getMpConsume() < _thisActor.getCurrentMp() && DeBuff.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(DeBuff.getId()))
					{
						addUseSkillDesire(attacker, DeBuff, 0, 1, 1000000);
					}
					_thisActor.i_ai1++;
				}
			}
			else if(Rnd.get(100) < 20)
			{
				if(RangeHold_a.getMpConsume() < _thisActor.getCurrentMp() && RangeHold_a.getHpConsume() < _thisActor.getCurrentHp() && !_thisActor.isSkillDisabled(RangeHold_a.getId()))
				{
					addUseSkillDesire(_thisActor, RangeHold_a, 0, 1, 1000000);
				}
			}
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(!creature.isPlayer() && !CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			return;
		}
		if(SeeCreatureAttackerTime == -1)
		{
			if(SetAggressiveTime == -1)
			{
				if(_thisActor.getLifeTime() >= (Rnd.get(5) + 3) && _thisActor.inMyTerritory(_thisActor))
				{
					addAttackDesire(creature, 1, 200);
				}
			}
			else if(SetAggressiveTime == 0)
			{
				if(_thisActor.inMyTerritory(_thisActor))
				{
					addAttackDesire(creature, 1, 200);
				}
			}
			else if(_thisActor.getLifeTime() > (SetAggressiveTime + Rnd.get(4)) && _thisActor.inMyTerritory(_thisActor))
			{
				addAttackDesire(creature, 1, 200);
			}
		}
		else if(_thisActor.getLifeTime() > SeeCreatureAttackerTime && _thisActor.inMyTerritory(_thisActor))
		{
			addAttackDesire(creature, 1, 200);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		L2Character target = _thisActor.getCastingTarget();

		if(target != null && skill == DeBuff)
		{
			removeAttackDesire(target);
		}
		super.onEvtFinishCasting(skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker("schuttgart13_npc2314_2m1");
		if(maker0 != null)
		{
			maker0.onScriptEvent(10005, 0, 0);
		}
		super.onEvtDead(killer);
	}
}