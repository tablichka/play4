package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.geodata.GeoEngine;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.entity.SevenSignsFestival.FestivalManager;
import ru.l2gw.gameserver.model.instances.L2FestivalMonsterInstance;
import ru.l2gw.commons.arrays.GArray;
import ru.l2gw.util.Location;


public class FestivalFighter extends Fighter
{
	public FestivalFighter(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor.isDead())
			return true;

		_thisActor.setRunning();

		if(isMoveToCenter() && checkTargetsAround())
			return super.createNewTask();

		if(_def_think)
		{
			doTask();
			return true;
		}

		Location CENTER;

		try
		{
			CENTER = FestivalManager.getInstance().getFestivalById(((L2FestivalMonsterInstance) _thisActor).getFestivalId()).getStartLoc();
		}
		catch (NullPointerException e)
		{

			_log.warn(_thisActor + " has no festival id: " + ((L2FestivalMonsterInstance) _thisActor).getFestivalId() + " at " + _thisActor.getLoc() + " despawn.");
			_thisActor.deleteMe();
			return false;
		}

		if(_thisActor.getDistance(CENTER.getX(), CENTER.getY(), CENTER.getZ()) - _thisActor.getColRadius() > 100)
		{
			_thisActor.setRunning();
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.loc = CENTER;
			_task_list.add(task);
			_def_think = true;
			return true;
		}
		return super.thinkActive();
	}

	private boolean isMoveToCenter()
	{
		return _task_list.size() > 0 && _task_list.first() != null && _task_list.first().type == TaskType.MOVE && _task_list.first().loc == FestivalManager.getInstance().getFestivalById(((L2FestivalMonsterInstance) _thisActor).getFestivalId()).getStartLoc();
	}

	private boolean checkTargetsAround()
	{
		boolean aggro = false;
		GArray<L2Player> players = _thisActor.getAroundPlayers(_thisActor.getAggroRange());
		for(L2Player player : players)
		{
			if(GeoEngine.canSeeTarget(_thisActor, player))
			{
				_thisActor.addDamageHate(player, 0, Rnd.get(10));
				aggro = true;
			}

		}
		if(aggro)
			checkAggression(players.get(Rnd.get(players.size())));
		return aggro;
	}
}