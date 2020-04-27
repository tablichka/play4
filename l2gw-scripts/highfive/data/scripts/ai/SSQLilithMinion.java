package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author rage
 * @date 27.11.2010 16:35:05
 */
public class SSQLilithMinion extends DefaultAI
{
	public SSQLilithMinion(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(1000, 1000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null || attacker.isPlayer())
			return;

		_thisActor.addDamageHate(attacker, damage, skill != null ? skill.getEffectPoint() : damage);
		if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			setIntention(CtrlIntention.AI_INTENTION_ATTACK, attacker);
	}

	protected boolean createNewTask()
	{
		L2Character _temp_attack_target = _thisActor.getMostHated();
		if(_temp_attack_target == null)
			_temp_attack_target = getAttackTarget();

		if(_temp_attack_target == null)
			_temp_attack_target = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);

		if(_temp_attack_target != null)
		{
			if(_mdam_skills.length > 0 && Rnd.chance(20))
				addUseSkillDesire(_temp_attack_target, _mdam_skills[Rnd.get(_mdam_skills.length)], 1, 1, DEFAULT_DESIRE * 2);
			else
				addAttackDesire(_temp_attack_target, 1, DEFAULT_DESIRE);
		}
		return true;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1000)
		{
			broadcastScriptEvent(50, _thisActor, null, 3000);
			addTimer(1000, 5000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 40)
		{
			L2Character guard = (L2Character) arg1;
			_thisActor.c_ai0 = guard.getStoredId();
			_thisActor.addDamageHate(guard, 0, Rnd.get(1, 100));
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
				setIntention(CtrlIntention.AI_INTENTION_ATTACK, guard);
		}
	}
}
