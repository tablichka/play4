package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author rage
 * @date 02.12.2010 17:44:44
 */
public class CastlePailakaInvaderWarrior extends CastlePailakaInvaderBasic
{
	public CastlePailakaInvaderWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();
		L2Character _temp_attack_target = _thisActor.getMostHated();

		if(_temp_attack_target == null)
		{
			_thisActor.setAttackTimeout(Integer.MAX_VALUE);
			setAttackTarget(null);
			clientStopMoving();
			setIntention(AI_INTENTION_ACTIVE);
			return false;
		}

		if(DDSkill_01 != null && DDSkill_01.getMpConsume() < _thisActor.getCurrentMp() && !_thisActor.isSkillDisabled(DDSkill_01.getId()) && Rnd.chance(DDSkill_01_Prob))
		{
			if(DDSkill_01.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, DDSkill_01, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}
		else if(_thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.30 && DDSkill_02 != null && DDSkill_02.getMpConsume() < _thisActor.getCurrentMp() && !_thisActor.isSkillDisabled(DDSkill_02.getId()) && Rnd.chance(DDSkill_02_Prob))
		{
			if(DDSkill_02.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, DDSkill_02, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}

		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}
}
