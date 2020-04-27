package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 9:59
 */
public class AiDrakeLeader extends WarriorUseSkill
{
	public L2Skill SpecialSkill01_ID = SkillTable.getInstance().getInfo(446824449);
	public L2Skill SpecialSkill02_ID = SkillTable.getInstance().getInfo(446955521);
	public int mobile_type = 0;

	public AiDrakeLeader(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		HATE_SKILL_Weight_Point = 10000.000000f;
		Skill01_ID = SkillTable.getInstance().getInfo(443219969);
		Skill01_Probablity = 1000;
		SuperPointMethod = 2;
		SuperPointDesire = 50;
		SuperPointName = "";
		HATE_SKILL_Weight_Point = 10000.000000f;
	}

	@Override
	protected void onEvtSpawn()
	{
		if(mobile_type == 0)
		{
			for(int i0 = 0; i0 < 4; i0 = (i0 + 1))
			{
				int i1 = Rnd.get(3);
				switch(i1)
				{
					case 0:
						_thisActor.createOnePrivate(22849, "AiDrakeWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(22850, "AiDrakeScout", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(22851, "AiDrakeMage", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
				}
			}
		}
		else if(mobile_type == 1)
		{
			_thisActor.createOnePrivate(22849, "AiDrakeWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
			_thisActor.createOnePrivate(22849, "AiDrakeWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
			for(int i0 = 0; i0 < 2; i0 = (i0 + 1))
			{
				int i1 = Rnd.get(3);
				switch(i1)
				{
					case 0:
						_thisActor.createOnePrivate(22849, "AiDrakeWarrior", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(22850, "AiDrakeScout", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(22851, "AiDrakeMage", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
						break;
				}
			}
		}
		if(SuperPointName != null && !SuperPointName.isEmpty())
		{
			if(mobile_type == 1)
			{
				_thisActor.i_ai0 = 1;
				_thisActor.setRunning();
				addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			}
		}
		else
		{
			_thisActor.i_ai0 = 0;
		}
		addTimer(1001, 120000);
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.getCurrentHp() <= (_thisActor.getMaxHp() * 0.900000))
		{
			if(Rnd.get(100) < 50)
			{
				broadcastScriptEvent(14002, 0, null, 800);
			}
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()))
		{
			addAttackDesire(creature, 1, 150);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 14005)
		{
			addUseSkillDesire(_thisActor, SpecialSkill01_ID, 1, 1, 1000000);
		}
		if(eventId == 14004)
		{
			addUseSkillDesire(_thisActor, SpecialSkill02_ID, 1, 1, 1000000);
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtManipulation(L2Character target, int aggro, L2Skill skill)
	{
		addAttackDesire(target, (int) (aggro * HATE_SKILL_Weight_Point), 0);
		super.onEvtManipulation(target, aggro, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(_thisActor.getMostHated() == null && (_thisActor.getSpawnedLoc().getZ() - (_thisActor.getZ())) > 200)
			{
				removeAllAttackDesire();
				randomTeleportInMyTerritory();
				broadcastScriptEvent(14006, 0, null, 800);
			}
			addTimer(1001, 120000);
		}
	}
}
