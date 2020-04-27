package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 04.09.11 21:00
 */
public class AiDragonKnight3 extends WarriorUseSkill
{
	public AiDragonKnight3(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Skill01_ID = SkillTable.getInstance().getInfo(443219969);
		Skill01_Probablity = 1000;
		Skill01_Target = 1;
		Skill02_ID = SkillTable.getInstance().getInfo(443088897);
		Skill02_Probablity = 500;
		Skill02_Check_Dist = 1;
		Skill02_Dist_Max = 150;
		HATE_SKILL_Weight_Point = 10000.000000f;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.createOnePrivate(22847, "AiDragonWarrior", 0, 0, _thisActor.getX() + 30, _thisActor.getY() + 10, _thisActor.getZ(), 0, 0, 0, 0);
		_thisActor.createOnePrivate(22847, "AiDragonWarrior", 0, 0, _thisActor.getX() + 30, _thisActor.getY() - 10, _thisActor.getZ(), 0, 0, 0, 0);
		_thisActor.i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if( _thisActor.i_ai0 == 0 )
		{
			broadcastScriptEvent(14001, 0, null, 300);
			_thisActor.i_ai0++;
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		addAttackDesire(target, (int)( aggro * HATE_SKILL_Weight_Point ), 0);
		super.onEvtManipulation(target, aggro, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(true )// gg.GetItemCollectable(myself.sm) == 1 )
		{
			if( Rnd.get(5) < 2 )
			{
				_thisActor.createOnePrivate(22845, "AiDragonKnight5", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1000, getStoredIdFromCreature(_thisActor.getMostHated()), 0);
			}
		}
		else if( Rnd.get(5) < 1 )
		{
			_thisActor.createOnePrivate(22845, "AiDragonKnight5", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 1000, getStoredIdFromCreature(_thisActor.getMostHated()), 0);
		}
		super.onEvtDead(killer);
	}
}


