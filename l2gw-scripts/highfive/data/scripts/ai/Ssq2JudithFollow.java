package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;

/**
 * @author: rage
 * @date: 04.10.11 17:32
 */
public class Ssq2JudithFollow extends DefaultNpc
{
	public int p_TIMER_LOOK = 1000;
	public int p_TIMER_LOOK_GAP = 1000;
	public int p_TIMER_TALK = 1001;
	public int p_TIMER_TALK_GAP = 3000;

	public Ssq2JudithFollow(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80216, _thisActor.id);
		addTimer(p_TIMER_LOOK, p_TIMER_LOOK_GAP);
		addTimer(p_TIMER_TALK, p_TIMER_TALK_GAP);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(creature != null && creature.isPlayer())
		{
			_thisActor.c_ai0 = creature.getStoredId();
			_thisActor.setRunning();
			addFollowDesire(creature, 9000);
		}
		super.onEvtSeeCreature(creature);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_LOOK)
		{
			_thisActor.lookNeighbor(300);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.c_ai0);
			if(c0 != null)
			{
				_thisActor.setRunning();
				addFollowDesire(c0, 9000);
				if(_thisActor.getLoc().distance3D(c0.getLoc()) >= 800)
				{
					_thisActor.teleToLocation(c0.getLoc());
				}
			}
			addTimer(p_TIMER_LOOK, p_TIMER_LOOK_GAP);
		}
		if(timerId == p_TIMER_TALK)
		{
			switch(Rnd.get(3))
			{
				case 0:
					Functions.npcSay(_thisActor, Say2C.ALL, 1029460);
					break;
				case 1:
					Functions.npcSay(_thisActor, Say2C.ALL, 1029461);
					break;
				case 2:
					Functions.npcSay(_thisActor, Say2C.ALL, 1029462);
					break;
			}
			addTimer(p_TIMER_TALK, p_TIMER_TALK_GAP);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90110)
		{
			_thisActor.onDecay();
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}