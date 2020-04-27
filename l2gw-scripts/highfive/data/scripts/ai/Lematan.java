package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.idfactory.IdFactory;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.instances.L2MonsterInstance;
import ru.l2gw.gameserver.tables.NpcTable;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 07.10.2010 11:58:49
 * Lematan Boss in Pailaka Devil's Legacy. ID: 18633
 */
public class Lematan extends Fighter
{
	private static final Location _runPoint = new Location(89089, -209139, -3464);
	private static final Location _telePoint = new Location(84984, -208735, -3336);
	private int _runCount;
	private boolean _teleported = false;
	private final GArray<L2MonsterInstance> _minions = new GArray<>(6);

	public Lematan(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();
		if(!_teleported && _thisActor.getCurrentHp() < _thisActor.getMaxHp() * 0.5)
		{
			_runCount++;
			if(_runCount > 20)
			{
				_teleported = true;
				_thisActor.teleToLocation(_telePoint, _thisActor.getReflection());
				spawnMinions();
				return true;
			}
			else if(_thisActor.isInRange(_telePoint, 150))
			{
				_teleported = true;
				spawnMinions();
				return true;
			}

			_thisActor.setRunning();
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = true;
			task.loc = _runPoint;
			_task_list.add(task);
			_def_think = true;
			return true;
		}

		return super.createNewTask();
	}

	@Override
	protected void onEvtArrived()
	{
		if(!_teleported && _runCount > 0 && _thisActor.isInRange(_runPoint, 100))
		{
			_teleported = true;
			_thisActor.teleToLocation(_telePoint, _thisActor.getReflection());
			spawnMinions();
		}
		super.onEvtArrived();
	}

	@Override
	protected void onEvtDead(L2Character killer)
	{
		super.onEvtDead(killer);
		for(L2MonsterInstance minion : _minions)
			minion.deleteMe();
	}

	private void spawnMinions()
	{
		L2MonsterInstance minion;
		int a = 360 / 6;
		for(int i = 0; i < 6; i++)
			try
			{
				minion = new L2MonsterInstance(IdFactory.getInstance().getNextId(), NpcTable.getTemplate(18634), 0, 0, 0, 0);
				minion.setLeader(_thisActor);
				minion.setCurrentHp(minion.getMaxHp());
				minion.setCurrentMp(minion.getMaxMp());
				minion.setReflection(_thisActor.getReflection());
				minion.spawnMe(ru.l2gw.util.Util.getPointInRadius(_telePoint, 100, a * i));
				minion.onSpawn();
				_minions.add(minion);
			}
			catch(Exception e)
			{
				_log.warn(_thisActor + " can't spawn minions: " + e);
				e.printStackTrace();
			}
	}
}
