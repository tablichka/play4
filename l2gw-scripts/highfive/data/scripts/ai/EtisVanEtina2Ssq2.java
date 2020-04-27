package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 06.10.11 19:27
 */
public class EtisVanEtina2Ssq2 extends WarriorUseSkill
{
	public int p_PHASE_NORMAL = 0;
	public int p_PHASE_TRANSFORM = 1;
	public int p_TIMER_THINK = 5000;

	public EtisVanEtina2Ssq2(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
		Aggressive_Time = 1.000000f;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_thisActor.i_ai0 = p_PHASE_NORMAL;
		_thisActor.i_ai1 = 0;
		addTimer(p_TIMER_THINK, 5000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == p_TIMER_THINK)
		{
			if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() < 0.500000 && _thisActor.i_ai0 == p_PHASE_NORMAL)
			{
				_thisActor.i_ai0 = p_PHASE_TRANSFORM;
			}
			if(_thisActor.i_ai0 == p_PHASE_TRANSFORM)
			{
				if(_thisActor.i_ai1 == 0)
				{
					_thisActor.createOnePrivate(18950, "Ssq2EtisVanEtinaDR", 1, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					_thisActor.createOnePrivate(18951, "Ssq2EtisVanEtinaDF", 1, 0, _thisActor.getX(), _thisActor.getY(), _thisActor.getZ(), 0, 0, 0, 0);
					_thisActor.i_ai1 = 1;
				}
			}
			_thisActor.lookNeighbor(300);
			addTimer(p_TIMER_THINK, 5000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90210)
		{
			_thisActor.i_ai1 = 0;
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		//int i0 = ServerVariables.getInt("GM_" + 80406);
		L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 18950);
		if(c0 != null)
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90209, 0, null);
		}

		//i0 = ServerVariables.getInt("GM_" + 80405);
		c0 = InstanceManager.getInstance().getNpcById(_thisActor, 18951);
		if(c0 != null)
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90209, 0, null);
		}

		//i0 = ServerVariables.getInt("GM_" + 80008);
		c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32787);
		if(c0 != null)
		{
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90209, 0, null);
			_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 90316, 0, null);
		}

		super.onEvtDead(killer);
	}
}