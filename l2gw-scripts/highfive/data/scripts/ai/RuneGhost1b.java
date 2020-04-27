package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.superpoint.Superpoint;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointManager;
import ru.l2gw.gameserver.instancemanager.superpoint.SuperpointNode;
import ru.l2gw.gameserver.model.*;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.commons.arrays.GArray;

/**
 * @author rage
 * @date 30.12.10 13:01
 */
public class RuneGhost1b extends DefaultAI
{
	private GArray<Integer> _players;
	protected Superpoint _superpoint;
	protected SuperpointNode _prevNode;
	protected long _delay = 0;
	public String SuperPointName;

	public RuneGhost1b(L2Character actor)
	{
		super(actor);
		_players = new GArray<Integer>();
	}

	@Override
	public void onEvtSpawn()
	{
		super.onEvtSpawn();
		addTimer(2102, 500);
		addTimer(2103, 120000);
		_thisActor.i_quest2 = 0;
		addTimer(3000, 60000);
		lookNeighbors(300);
		if(_thisActor.getAIParams() != null && SuperPointName != null)
			_superpoint = SuperpointManager.getInstance().getSuperpointByName(SuperPointName);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		if(_def_think)
		{
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
		super.onEvtArrived();

		if(_superpoint != null && _intention == CtrlIntention.AI_INTENTION_ACTIVE)
		{
			clearTasks();

			if(_prevNode != null)
			{
				if(_prevNode.getNodeId() == 4)
				{
					_thisActor.i_quest2 = 1;
					addTimer(2104, 15000);
				}

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
			_task_list.add(task);
			_def_think = true;
			doTask();
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2102)
		{
			L2Player player = L2ObjectsStorage.getPlayer(_thisActor.i_quest0);
			if(player != null)
				if(_thisActor.i_quest2 == 0)
					Functions.npcSay(_thisActor, Say2C.ALL, 2151, player.getName());
				else if(_thisActor.i_quest2 == 1)
					Functions.npcSay(_thisActor, Say2C.ALL, 2152, player.getName());
			addTimer(2102, 9000);
		}
		else if(timerId == 2103 || timerId == 2104 || timerId == 2105)
		{
			L2Object npc = L2ObjectsStorage.findObject(_thisActor.i_quest1);
			if(npc instanceof L2NpcInstance)
				((L2NpcInstance) npc).i_quest0--;

			_thisActor.deleteMe();
		}
		else if(timerId == 3000)
		{
			lookNeighbors(300);
			addTimer(3000, 60000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	private void seeCreature(L2Player player)
	{
		player.sendPacket(new PlaySound("horror_01"));
	}

	private void lookNeighbors(int range)
	{
		for(L2Player player : _thisActor.getAroundPlayers(range))
			if(!_players.contains(player.getObjectId()))
			{
				_players.add(player.getObjectId());
				seeCreature(player);
			}

		for(int i = 0; i < _players.size(); i++)
		{
			L2Player player = L2ObjectsStorage.getPlayer(_players.get(i));
			if(player == null || !_thisActor.isInRange(player, 300))
				_players.remove(i);
		}
	}
}
