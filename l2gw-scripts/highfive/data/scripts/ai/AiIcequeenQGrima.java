package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 17.09.11 15:11
 */
public class AiIcequeenQGrima extends WarriorUseSkill
{
	public int TIMER_attack_me = 2314002;
	public int is_invader = -1;
	public int party_member_num = 5;

	public AiIcequeenQGrima(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		if(is_invader == 1)
		{
			addTimer(TIMER_attack_me, (10 * 1000));
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_attack_me)
		{
			broadcastScriptEvent(2117001, getStoredIdFromCreature(_thisActor), null, 800);
			addTimer(TIMER_attack_me, (10 * 1000));
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		addAttackDesire(attacker, 1, (damage * 2));
		if(attacker.isPlayer())
		{
			broadcastScriptEvent(23140100, getStoredIdFromCreature(_thisActor), null, 800);
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23141001)
		{
			if(L2ObjectsStorage.getAsCharacter((Long) arg1) != _thisActor)
			{
				_thisActor.i_ai0++;
			}
		}
		else if(eventId == 2117001)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(c0.getNpcId() != _thisActor.getNpcId())
				{
					addAttackDesire(c0, 1, 1000);
				}
			}
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(is_invader == 1)
		{
			if(_thisActor.i_ai0 == (party_member_num - 1))
			{
				broadcastScriptEvent(23141002, 0, null, 3500);
			}
			broadcastScriptEvent(23141001, getStoredIdFromCreature(_thisActor), null, 3500);
		}
	}
}
