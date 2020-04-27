package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.09.11 17:45
 */
public class AiOlCooker extends WarriorUseSkill
{
	public int mobile_type = 1;

	public AiOlCooker(L2Character actor)
	{
		super(actor);
		SuperPointMethod = 0;
		SuperPointDesire = 50;
		SuperPointName = "";
		Skill01_ID = SkillTable.getInstance().getInfo(414842881);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.createOnePrivate(22779, null, 0, 0, _thisActor.getX(), _thisActor.getY(), (_thisActor.getZ()) + 100, 0, 1, 0, 0);
		_thisActor.createOnePrivate(22779, null, 0, 0, _thisActor.getX(), _thisActor.getY(), (_thisActor.getZ()) + 100, 0, 0, 0, 0);
		if(SuperPointName != null && !SuperPointName.isEmpty())
		{
			if(mobile_type == 1)
			{
				_thisActor.i_ai0 = 1;
				_thisActor.setWalking();
				addMoveSuperPointDesire(SuperPointName, SuperPointMethod, SuperPointDesire);
			}
		}
		else
		{
			_thisActor.i_ai0 = 0;
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2119019)
		{
			_thisActor.i_ai1 = 1;
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				if(!_thisActor.isMoving)
				{
				}
			}
		}
	}

	@Override
	protected void onEvtNodeArrived(SuperpointNode node)
	{
		broadcastScriptEvent(2219017, 0L, null, 300);
		addTimer(2219002, 2000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2219001)
		{
			_thisActor.setWalking();
		}
		if(timerId == 2219002)
		{
			if(_thisActor.i_ai1 == 1)
			{
				if(Rnd.get(2) < 1)
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1801116);
				}
				else
				{
					Functions.npcSay(_thisActor, Say2C.ALL, 1801117);
				}
				_thisActor.i_ai0 = 2;
				addTimer(2219001, 6000);
				_thisActor.i_ai1 = 0;
			}
			else
			{
				_thisActor.i_ai0 = 1;
				addTimer(2219001, 100);
			}
		}
		if(timerId == 2019003)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null && !c0.isDead() && !_thisActor.isMoving && _thisActor.i_ai5 == 1)
			{
				addUseSkillDesire(c0, Skill01_ID, 0, 1, 10000);
			}
			else
			{
				_thisActor.i_ai0 = 1;
				_thisActor.i_ai2 = 0;
				_thisActor.c_ai0 = 0;
				_thisActor.setWalking();
			}
		}
		if(timerId == 2019004)
		{
			_thisActor.i_ai5 = 0;
			addUseSkillDesire(_thisActor, 435486721, 1, 0, 90000000);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
			{
				addAttackDesire(c0, 1, 5000);
			}
		}
		if(timerId == 2019005)
		{
			_thisActor.i_ai6 = 1;
		}
		if(timerId == 2019006)
		{
			addUseSkillDesire(_thisActor, 435486721, 1, 0, 90000000);
		}
	}

	@Override
	protected void onEvtFinishCasting(L2Skill skill)
	{
		if(skill == Skill01_ID)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null && !c0.isDead() && !_thisActor.isMoving && _thisActor.i_ai5 == 1)
			{
				addUseSkillDesire(c0, Skill01_ID, 0, 1, 10000000);
			}
		}

		L2Character target = _thisActor.getCastingTarget();
		if(target != null && target.isDead() && target.isPlayer())
		{
			_thisActor.stopHate(target);
			_thisActor.c_ai0 = 0;
			_thisActor.i_ai0 = 1;
			_thisActor.i_ai2 = 0;
			_thisActor.i_ai5 = 0;
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		if(_thisActor.i_ai2 == 0)
		{
			if(_thisActor.i_ai5 == 0)
			{
				addTimer(2019004, 180000);
				addTimer(2019005, 60000);
				_thisActor.i_ai5 = 1;
				_thisActor.i_ai0 = 4;
			}
			_thisActor.c_ai0 = attacker.getStoredId();
			addTimer(2019003, 1000);
			addTimer(2019006, 60000);
			_thisActor.i_ai2 = 1;
		}
		super.onEvtAttacked(attacker, damage, skill);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		if(_thisActor.i_ai6 != 1 && killer != null && killer.isPlayer())
		{
			if(Rnd.get(10) < 2)
			{
				_thisActor.dropItem(killer.getPlayer(), 15492, 1);
			}
		}
		super.onEvtDead(killer);
	}
}
