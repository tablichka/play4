package ai;

import ai.base.AiXelRecruitWar;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;

/**
 * @author: rage
 * @date: 06.09.11 15:37
 */
public class AiXelRecruitWarrior extends AiXelRecruitWar
{
	public AiXelRecruitWarrior(L2Character actor)
	{
		super(actor);
		Skill01_Probablity = 100;
		Skill02_Probablity = 100;
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_quest0 = 0;
		if(Rnd.get(18) < 1)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.i_quest0 = 1;
			addTimer(2019999, 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10016 + trainer_id)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(_thisActor.isDead())
				{
					return;
				}
				removeAllAttackDesire();
				if(c0.isPlayable())
				{
					addAttackDesire(c0, 1, 100);
				}
				addAttackDesire(c0, 1, 5000);
			}
		}
		else if(eventId == 2219023 + trainer_id)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.i_ai6 = 1;
				clearTasks();
				addFleeDesire(c0, 50000000);
				if(Rnd.get(4) < 1)
				{
					if(Rnd.get(2) < 1)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1801114);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1801115);
					}
				}
				_thisActor.c_ai1 = c0.getStoredId();
				addTimer(2019777, 10);
				addTimer(2019888, 5000);
			}
		}
		else if(arg1 instanceof Integer && (Integer) arg1 == trainer_id && _thisActor.i_ai6 == 0)
		{
			switch(eventId)
			{
				case 2219011:
					if(_thisActor.i_ai0 != 1)
					{
						_thisActor.i_ai2 = 70;
						_thisActor.i_ai3 = 4;
						_thisActor.i_ai4 = 2;
						addTimer(22201, 100);
					}
					break;
				case 2219012:
					if(_thisActor.i_ai0 != 1)
					{
						_thisActor.i_ai2 = 130;
						_thisActor.i_ai3 = 1;
						_thisActor.i_ai4 = 2;
						addTimer(22201, 100);
					}
					break;
				case 2219013:
					if(_thisActor.i_ai0 != 1)
					{
						_thisActor.i_ai2 = 30;
						_thisActor.i_ai3 = 5;
						_thisActor.i_ai4 = 4;
						addTimer(22201, 100);
					}
					else
					{
						_thisActor.i_ai2 = 30;
						_thisActor.i_ai3 = 6;
						_thisActor.i_ai4 = 4;
						addTimer(22201, 100);
					}
					break;
				case 2219014:
					if(_thisActor.i_ai0 != 1)
					{
						_thisActor.i_ai2 = 30;
						_thisActor.i_ai3 = 7;
						_thisActor.i_ai4 = 2;
						addTimer(22201, 100);
					}
					break;
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2019999)
		{
			addTimer(2019999, 5000);
		}
		if(timerId == 2019888)
		{
			_thisActor.i_ai6 = 0;
		}
		if(timerId == 2019777)
		{
			addFleeDesire(L2ObjectsStorage.getAsCharacter(_thisActor.c_ai1), 50000000);
			if(_thisActor.i_ai6 == 1)
			{
				addTimer(2019777, 1000);
			}
		}
		if(timerId == 22201)
		{
			addEffectActionDesire(_thisActor.i_ai3, _thisActor.i_ai2 * 1000 / 30, 500);
			if(_thisActor.i_ai4 != 0)
			{
				_thisActor.i_ai4 = _thisActor.i_ai4 - 1;
				addTimer(22201, ((_thisActor.i_ai2 * 1000) / 30));
			}
		}
	}
}