package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Object;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.L2World;
import ru.l2gw.gameserver.model.instances.L2ItemInstance;
import ru.l2gw.commons.math.Rnd;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: rage
 * @date: 01.09.11 8:38
 */
public class EventJackpotBug extends DefaultAI
{
	public int RandRate = -1;

	private long nextItemLook;

	public EventJackpotBug(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.decayMe();
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai5 = 0;
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(!_thisActor.isMoving && _thisActor.isVisible() && nextItemLook < System.currentTimeMillis())
		{
			HashMap<L2ItemInstance, Integer> items = new HashMap<>();

			for(L2Object obj : L2World.getAroundObjects(_thisActor, 500, 100))
				if(obj instanceof L2ItemInstance && ((L2ItemInstance) obj).isStackable())
					items.put((L2ItemInstance) obj, (int) _thisActor.getDistance3D(obj));

			if(items.size() > 0)
				for(Map.Entry<L2ItemInstance, Integer> entry : items.entrySet())
					addMoveToDesire(entry.getKey().getX(), entry.getKey().getY(), entry.getKey().getZ(), 100000 - entry.getValue());

			nextItemLook = System.currentTimeMillis() + 10000;
		}

		return false;
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();

		L2ItemInstance closestItem = null;
		int minDist = Integer.MAX_VALUE;

		for(L2Object obj : L2World.getAroundObjects(_thisActor, 20, 100))
			if(obj instanceof L2ItemInstance && ((L2ItemInstance) obj).isStackable() && _thisActor.getDistance3D(obj) < minDist)
			{
				minDist = (int) _thisActor.getDistance3D(obj);
				closestItem = (L2ItemInstance) obj;
			}

		if(closestItem != null)
		{
			clearTasks();
			closestItem.deleteMe();
			_thisActor.i_ai0++;
			if(_thisActor.i_ai0 == 10)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1900146);
				_thisActor.onDecay();
			}
			else
			{
				int i0 = Rnd.get(100);
				if(i0 < 50)
				{
					addUseSkillDesire(_thisActor, 377356289, 1, 0, 50000000);
					_thisActor.i_ai1++;
					Functions.npcSay(_thisActor, Say2C.ALL, 1900142);

					if(_thisActor.i_ai1 >= 2 && _thisActor.i_ai1 < 5)
					{
						if(_thisActor.i_ai3 == 0)
						{
							addUseSkillDesire(_thisActor, 1528627201, 1, 0, 2200000);
							_thisActor.i_ai3 = 1;
						}
					}
					else if(_thisActor.i_ai1 >= 5)
					{
						if(_thisActor.i_ai3 == 1)
						{
							addUseSkillDesire(_thisActor, 1528627201, 1, 0, 2200000);
							_thisActor.i_ai3 = 2;
						}
					}
				}
				else if(i0 < 99)
				{
					addUseSkillDesire(_thisActor, 395640833, 1, 0, 2200000);
					_thisActor.i_ai1--;
					Functions.npcSay(_thisActor, Say2C.ALL, 1900143);

					if(_thisActor.i_ai1 >= 2 && _thisActor.i_ai1 < 5)
					{
						if(_thisActor.i_ai3 == 2)
						{
							addUseSkillDesire(_thisActor, 1528692737, 1, 0, 2200000);
							_thisActor.i_ai3 = 1;
						}
					}
					else if(_thisActor.i_ai1 < 5 && _thisActor.i_ai3 == 1)
					{
						addUseSkillDesire(_thisActor, 1528692737, 1, 0, 2200000);
						_thisActor.i_ai3 = 0;
					}
				}
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 7777777)
		{
			_thisActor.i_ai2 = Rnd.get(RandRate) + 1;
			if( (Integer) arg1 == _thisActor.i_ai2 )
			{
				_thisActor.spawnMe();
				Functions.npcSay(_thisActor, Say2C.ALL, 1900139);
				_thisActor.i_ai5 = 1;
				addTimer(7778, 10000);
				addTimer(7779, 600000);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 7778)
			Functions.npcSay(_thisActor, Say2C.ALL, 1900140);
		else if(timerId == 7779)
			_thisActor.onDecay();
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);

		if(_thisActor.i_ai5 == 1)
		{
			if(_thisActor.i_ai1 >= 5)
				_thisActor.createOnePrivate(2503, null, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.getLevel(), _thisActor.i_ai1, 0);
			else
				_thisActor.createOnePrivate(2502, null, 0, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, _thisActor.getLevel(), _thisActor.i_ai1, 0);
		}
	}
}
