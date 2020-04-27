package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.tables.SkillTable;

/**
 * @author: rage
 * @date: 06.10.11 16:47
 */
public class Ssq2ThemeGuardian1 extends WarriorUseSkill
{
	public int p_TIMER_START_UP = 1000;
	public int p_TIMER_START_UP_GAP = 1000;

	public Ssq2ThemeGuardian1(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = 1;
		addTimer(2201, 5000);
		addTimer(p_TIMER_START_UP, p_TIMER_START_UP_GAP);
		//ServerVariables.set("GM_" + 80300, _thisActor.id);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_START_UP)
		{
			addUseSkillDesire(_thisActor, 435486721, 1, 0, 90000000);
		}
		else if(timerId == 2201)
		{
			if(!_thisActor.inMyTerritory(_thisActor) && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
			{
				_thisActor.teleToLocation(_thisActor.getSpawnedLoc().getX(), _thisActor.getSpawnedLoc().getY(), _thisActor.getSpawnedLoc().getZ());
				clearTasks();
				_thisActor.stopMove();
			}

			if(_thisActor.i_ai0 == 1)
			{
				addUseSkillDesire(_thisActor, 435486721, 1, 0, 90000000);
			}
			else if(_thisActor.getAbnormalLevelByType(SkillTable.getInstance().getInfo(435486721).getId()) > 0)
			{
				addUseSkillDesire(_thisActor, 435552257, 1, 0, 9999999999L);
			}
			_thisActor.lookNeighbor(300);
			addTimer(2201, 5000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90104)
		{
			addUseSkillDesire(_thisActor, 435552257, 1, 0, 9999999999L);
			_thisActor.i_ai0 = 0;
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		//int i0 = ServerVariables.getInt("GM_" + 80304);
		L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32837);
		if(c0 != null)
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90105, 0, null);
		}
		super.onEvtDead(killer);
	}
}