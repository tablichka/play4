package ai;

import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Playable;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.commons.math.Rnd;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

/**
 * @author rage
 * @date 02.12.2010 17:04:44
 */
public class CastlePailakaWarrior extends CastlePailakaNpc
{
	public CastlePailakaWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker == _thisActor)
			return;

		super.onEvtAttacked(attacker, damage, skill);
		if(!(attacker instanceof L2Playable))
		{
			_thisActor.addDamage(attacker, damage);
			if(!_thisActor.isInRange(attacker, 300))
				broadcastScriptEvent(KNIGHT_ATTACKED, attacker, null, 1000);

			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
		}
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(attacker == null || attacker == _thisActor)
			return;

		super.onEvtClanAttacked(attacked_member, attacker, damage);
		if(!(attacker instanceof L2Playable))
			_thisActor.addDamageHate(attacker, 0, damage / 2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == INVADER)
		{
			L2Character cha = (L2Character) arg1;
			_thisActor.addDamageHate(cha, 0, 10);
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, cha);
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
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

		if(Skill01 != null && Skill01.getMpConsume() < _thisActor.getCurrentMp() && !_thisActor.isSkillDisabled(Skill01.getId()) && Rnd.chance(50))
		{
			if(Skill01.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, Skill01, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}
		else if(Skill02 != null && Skill02.getMpConsume() < _thisActor.getCurrentMp() && !_thisActor.isSkillDisabled(Skill02.getId()) && Rnd.chance(50))
		{
			if(Skill02.getAimingTarget(_thisActor) == _thisActor)
				_temp_attack_target = _thisActor;
			// Добавить новое задание
			addUseSkillDesire(_temp_attack_target, Skill02, 1, 1, DEFAULT_DESIRE * 2);
			return true;
		}

		addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		return true;
	}
}
