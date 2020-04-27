package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.util.Util;

/**
 * @author: rage
 * @date: 06.09.11 15:00
 */
public class AiXelPrivateWarrior extends WarriorUseSkill
{
	public int minDistance = 100;
	public int maxDistance = 200;
	public int OHS_Weapon = 15280;
	public int THS_Weapon = 15281;

	public AiXelPrivateWarrior(L2Character actor)
	{
		super(actor);
		MoveArounding = 0;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.c_ai0 = 0;
		_thisActor.c_ai2 = 0;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.equipItem(THS_Weapon);
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtClanAttacked(L2Character attacked_member, L2Character attacker, int damage)
	{
		_thisActor.equipItem(THS_Weapon);
		addAttackDesire(attacker, 1, 5000);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2219018 && !_thisActor.isMoving)
		{
			if(_thisActor.i_ai3 == 0)
			{
				if(_thisActor.i_ai0 == 1)
				{
					_thisActor.equipItem(THS_Weapon);
				}
				_thisActor.i_ai0 = 1;
				_thisActor.i_ai3 = 1;
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					_thisActor.setRunning();
					if(Rnd.get(3) < 1)
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1801118);
					}
					else
					{
						Functions.npcSay(_thisActor, Say2C.ALL, 1801119);
					}

					Location pos0 = Location.coordsRandomize(c0, minDistance, maxDistance - 100);
					clearTasks();
					addMoveToDesire(pos0.getX(), pos0.getY(), pos0.getZ(), 100);
					_thisActor.i_ai1 = pos0.getX();
					_thisActor.i_ai2 = pos0.getY();
					_thisActor.c_ai0 = c0.getStoredId();
					_thisActor.c_ai2 = c0.getStoredId();
				}
			}
		}
		else if(eventId == 2219021 && !_thisActor.isMoving)
		{
			if(_thisActor.i_ai0 == 0)
			{
				_thisActor.i_ai0 = 1;
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					_thisActor.setWalking();
					Location pos0 = Location.coordsRandomize(c0, minDistance, maxDistance - 100);
					clearTasks();
					addMoveToDesire(pos0.getX(), pos0.getY(), pos0.getZ(), 100);
					_thisActor.i_ai1 = pos0.getX();
					_thisActor.i_ai2 = pos0.getY();
					_thisActor.c_ai2 = (Long) arg1;
				}
			}
		}
		else if(eventId == 2219020 && !_thisActor.isMoving)
		{
			clearTasks();
			_thisActor.i_ai0 = 0;
			_thisActor.i_ai3 = 0;
			_thisActor.equipItem(THS_Weapon);
			addTimer(2219009, 3000);
		}
		else if(eventId == 2219024)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.c_ai1 = c0.getStoredId();
				_thisActor.i_ai4 = 1;
				addTimer(2219012, 180000);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2219009)
		{
			if(_thisActor.i_ai0 == 0 && _thisActor.i_ai3 == 0)
			{
				addMoveToDesire(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ(), 100);
			}
		}
		else if(timerId == 2219010)
		{
			if(_thisActor.i_ai0 == 1)
			{
				_thisActor.i_quest0 = 1;
				addUseSkillDesire(_thisActor, 414908417, 1, 0, 1000000);
				_thisActor.changeNpcState(2);
				_thisActor.setWalking();
				addTimer(2219011, 300000);
			}
			if(_thisActor.i_ai3 == 1)
			{
				_thisActor.i_quest0 = 1;
				addUseSkillDesire(_thisActor, 414973953, 1, 0, 1000000);
				_thisActor.changeNpcState(1);
				_thisActor.setWalking();
				addTimer(2219011, 300000);
			}
		}
		else if(timerId == 2219011)
		{
			_thisActor.i_quest0 = 0;
			_thisActor.changeNpcState(3);
			_thisActor.setRunning();
		}
		else if(timerId == 2219012)
		{
			_thisActor.i_ai4 = 0;
		}
	}

	@Override
	protected void onEvtArrived()
	{
		if(Util.getDistance(_thisActor.getX(), _thisActor.getY(), _thisActor.i_ai1, _thisActor.i_ai2) < 40)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai2);
			if(c0 != null)
				_thisActor.changeHeading(_thisActor.calcHeading(c0.getLoc()));
			_thisActor.equipItem(OHS_Weapon);
			addTimer(2219010, 3000);
		}
		super.onEvtArrived();
	}

	@Override
	public void returnHome()
	{
	}

	@Override
	protected boolean randomWalk()
	{
		return false;
	}

	@Override
	protected boolean randomAnimation()
	{
		return false;
	}
}
