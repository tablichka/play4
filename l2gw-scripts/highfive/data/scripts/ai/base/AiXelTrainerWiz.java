package ai.base;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;

/**
 * @author: rage
 * @date: 06.09.11 14:37
 */
public class AiXelTrainerWiz extends WarriorUseSkill
{
	public int trainer_id = 0;
	public int trainning_range = 1000;
	public int direction = 0;

	public AiXelTrainerWiz(L2Character actor)
	{
		super(actor);
		MoveArounding = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(2219001, 1000);
	}

	@Override
	protected boolean thinkActive()
	{
		if(!_thisActor.isDead())
		{
			_thisActor.i_ai0 = 0;
			if(_thisActor.getX() == _thisActor.getSpawnedLoc().getX() && _thisActor.getSpawnedLoc().getY() == _thisActor.getY())
				_thisActor.changeHeading(direction);
			else if(!_thisActor.isInCombat())
			{
				clearTasks();
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
			}
		}

		return super.thinkActive();
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(attacker == null)
			return;

		if(_thisActor.i_ai0 == 0)
		{
			_thisActor.c_ai0 = attacker.getStoredId();
			broadcastScriptEvent(10016 + trainer_id, getStoredIdFromCreature(attacker), null, trainning_range);
			if(_thisActor.getMostHated() != null)
			{
				_thisActor.i_ai0 = 1;
				_thisActor.i_ai1 = 1;
				addTimer(2219002, 60000);
			}
		}

		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2219002)
		{
			_thisActor.i_ai1 = 0;
		}
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.i_ai1 == 1)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
			{
				broadcastScriptEvent(2219023 + trainer_id, _thisActor.c_ai0, null, trainning_range);
			}
		}
		super.onEvtDead(killer);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10016 + trainer_id)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(Rnd.get(10) < 1)
				{
					if(Rnd.get(2) < 1)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1801112);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1801113);
					}
				}
				if(_thisActor.i_ai0 == 0)
				{
					addAttackDesire(c0, 1, 5000);
				}
			}
		}
	}
}
