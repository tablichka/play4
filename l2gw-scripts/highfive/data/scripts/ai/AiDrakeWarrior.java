package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 10:27
 */
public class AiDrakeWarrior extends WarriorUseSkill
{

	public AiDrakeWarrior(L2Character actor)
	{
		super(actor);
		Skill01_ID = SkillTable.getInstance().getInfo(443023361);
		Skill01_Probablity = 1000;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1001)
		{
			if(!_thisActor.isMyBossAlive())
			{
				if(!_thisActor.isInCombat())
				{
					_thisActor.onDecay();
				}
			}
			else
				addTimer(1001, 10000);
		}
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1001, 10000);
		_thisActor.i_ai0 = 0;
		super.onEvtSpawn();
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		if(_thisActor.isMyBossAlive() && attacked_member == _thisActor.getLeader())
		{
			if(attacker != null)
			{
				addAttackDesire(attacker, damage, 0);
			}
		}
		super.onEvtClanAttacked(attacked_member, attacker, damage);
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.getCurrentHp() < (_thisActor.getMaxHp() / 2) && _thisActor.i_ai0 == 0)
		{
			broadcastScriptEvent(14004, 0, null, 800);
			_thisActor.i_ai0++;
		}
		super.onEvtAttacked(attacker, damage, skill);

	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 14006)
		{
			if(_thisActor.getLeader() != null && _thisActor.getLoc().distance3D(_thisActor.getLeader().getLoc()) > 500 && _thisActor.isMyBossAlive() && !_thisActor.isMoving)
			{
				removeAllAttackDesire();
				_thisActor.teleToLocation(_thisActor.getLeader().getX(), _thisActor.getLeader().getY(), (_thisActor.getLeader().getZ()));
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
