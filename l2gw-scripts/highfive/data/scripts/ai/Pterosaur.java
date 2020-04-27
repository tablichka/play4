package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.Superpoint;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointManager;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.serverpackets.SocialAction;

public class Pterosaur extends DefaultAI
{
	protected Superpoint _superpoint;
	protected SuperpointNode _prevNode;
	protected long _delay = 0;
	public String SuperPointName;

	public Pterosaur(L2Character actor)
	{
		super(actor);
		_thisActor.setFlying(true);
		_thisActor.hasChatWindow = false;
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
			task.usePF = false;
			task.loc = _prevNode = _superpoint.getNextNode(_thisActor, 0);
			if(debug)
				_log.info(_thisActor + " move from: " + _thisActor.getLoc() + " to: " + _prevNode);
			_task_list.add(task);
			_def_think = true;
			doTask();
			return true;
		}

		return true;
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
			task.usePF = false;
			task.loc = _prevNode = _superpoint.getNextNode(_thisActor, 0);
			_task_list.add(task);
			if(debug)
				_log.info(_thisActor + " onEvtArrived: next node " + _prevNode);
			_def_think = true;
			doTask();
		}
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{}
}