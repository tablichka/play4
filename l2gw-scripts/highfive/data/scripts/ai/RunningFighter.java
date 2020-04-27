package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.Superpoint;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointManager;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.serverpackets.SocialAction;

/**
 * @author rage
 * @date 21.12.10 14:49
 */
public class RunningFighter extends Fighter
{
	protected Superpoint _superpoint;
	protected SuperpointNode _prevNode;
	protected long _delay = 0;
	public String SuperPointName;

	public RunningFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		if(SuperPointName != null)
			_superpoint = SuperpointManager.getInstance().getSuperpointByName(SuperPointName);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor == null || _thisActor.isDead())
			return true;

		int aggro = _globalAggro;

		if(_globalAggro < 0)
			_globalAggro++;
		else if(_globalAggro > 0)
			_globalAggro--;

		if(_def_think)
		{
			if(debug)
				_log.info(_thisActor + " thinkActive: doTask()");
			doTask();
			return true;
		}

		if(_superpoint != null && _delay < System.currentTimeMillis())
		{
			// Добавить новое задание
			clearTasks();
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = true;
			task.loc = _prevNode = _superpoint.getNextNode(_thisActor, 2);
			if(debug)
				_log.info(_thisActor + " move to: " + _prevNode);
			_thisActor.setRunning();
			_task_list.add(task);
			_def_think = true;
			doTask();
			return true;
		}

		_globalAggro = aggro;
		return super.thinkActive();
	}

	@Override
	protected void onEvtArrived()
	{
		if(debug)
			_log.info(_thisActor + " onEvtArrived: " + _prevNode);
		super.onEvtArrived();

		if(_superpoint != null && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
		{
			clearTasks();

			if(_prevNode != null)
			{
				int message = _prevNode.getFStringId();
				if(message > 0)
					Functions.npcSay(_thisActor, Say2C.ALL, message);

				if(_prevNode.getSocial() >= 0)
					_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), _prevNode.getSocial()));

				if(_prevNode.getDelay() > 0)
				{
					_delay = System.currentTimeMillis() + _prevNode.getDelay();
					return;
				}
			}

			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = true;
			task.loc = _prevNode = _superpoint.getNextNode(_thisActor, 2);
			if(debug)
				_log.info(_thisActor + " onEvtArrived: next node " + _prevNode);
			_task_list.add(task);
			_def_think = true;
			doTask();
		}
	}
}
