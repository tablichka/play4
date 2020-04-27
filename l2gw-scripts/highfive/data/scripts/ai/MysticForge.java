package ai;

import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.*;

/**
 * @author rage
 * @date 13.01.11 19:56
 */
public class MysticForge extends Mystic
{
	private static final int TID_BONUS_TIME = 78001;
	private static final int TID_SKILL_COOLTIME = 78002;
	private L2Skill skill01 = null;
	private long aggrTime;

	public MysticForge(L2Character actor)
	{
		super(actor);

		if(_mdam_skills.length > 0)
			skill01 = _mdam_skills[0];
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		aggrTime = System.currentTimeMillis() + 10000;
		_thisActor.i_ai1 = 0;
		addTimer(TID_BONUS_TIME, 60000);
		addTimer(TID_SKILL_COOLTIME, 3000);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		_thisActor.i_ai1 = attacker.getObjectId();

		if(aggrTime < System.currentTimeMillis())
			super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(attacker == null)
			return;

		_thisActor.i_ai1 = attacker.getObjectId();

		if(aggrTime < System.currentTimeMillis())
			super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TID_BONUS_TIME)
			_thisActor.doDie(null);
		else if(timerId == TID_SKILL_COOLTIME)
		{
			if(aggrTime < System.currentTimeMillis() && _thisActor.i_ai1 > 0 && skill01 != null)
			{
				L2Object cha = L2ObjectsStorage.findObject(_thisActor.i_ai1);
				if(cha instanceof L2Playable)
					_thisActor.altUseSkill(skill01, (L2Playable) cha);
			}
			addTimer(TID_SKILL_COOLTIME, 3000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
