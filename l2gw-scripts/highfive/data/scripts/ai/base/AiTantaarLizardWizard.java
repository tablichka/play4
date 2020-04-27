package ai.base;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.commons.math.Rnd;

/**
 * @author: rage
 * @date: 06.09.11 16:14
 */
public class AiTantaarLizardWizard extends WizardUseSkill
{
	public long Max_Desire = 1000000000000000000L;
	public int TID_ATTRACT_TO_FUNGUS_KILLA = 780002;

	public AiTantaarLizardWizard(L2Character actor)
	{
		super(actor);
		AttackRange = 2;
		Skill01_ID = SkillTable.getInstance().getInfo(437518337);
		Skill01_Check_Dist = 0;
		Skill02_Check_Dist = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		super.onEvtAttacked(attacker, damage, skill);

		if(attacker != null)
		{
			_thisActor.c_ai0 = attacker.getStoredId();
		}
	}

	protected void onEvtSpelled(L2Skill skill, L2Character caster)
	{
		super.onEvtSpelled(skill, caster);

		if(skill.getId() == 6427)
			addUseSkillDesire(_thisActor, 433979393, 0, 1, Max_Desire);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		super.onEvtScriptEvent(eventId, arg1, arg2);
		if(_thisActor.getNpcId() == 22773)
		{
			if(L2ObjectsStorage.getAsCharacter((Long) arg2) != null)
			{
				_thisActor.c_ai1 = (Long) arg2;
			}
			addTimer(TID_ATTRACT_TO_FUNGUS_KILLA, 7000);
			if(eventId == 78010087)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					clearTasks();
					_thisActor.setRunning();
					addMoveToDesire(c0.getX() + Rnd.get(25) - Rnd.get(25), c0.getY() + Rnd.get(25) - Rnd.get(25), c0.getZ() + Rnd.get(25) - Rnd.get(25), Max_Desire);
				}
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		super.onEvtTimer(timerId, arg1, arg2);
		if(timerId == 780001)
		{
			if(!_thisActor.isMoving && _thisActor.getCurrentHp() == _thisActor.getMaxHp())
			{
				_thisActor.onDecay();
			}
			else
			{
				addTimer(780001, 180000);
			}
		}
		else if(timerId == TID_ATTRACT_TO_FUNGUS_KILLA)
		{
			L2Character c1 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1);
			if(c1 != null)
			{
				addAttackDesire(c1, 5000, 0);
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		if(_thisActor.c_ai0 != 0)
		{
			_thisActor.createOnePrivate(18919, "AiAuragrafter", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 0, 0);
		}
		if(Rnd.get(1000) == 0 && _thisActor.getNpcId() != 18862)
		{
			_thisActor.createOnePrivate(18862, "AiTantaarLizardProtecter", 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.c_ai0, 0, 0);
		}
	}
}
