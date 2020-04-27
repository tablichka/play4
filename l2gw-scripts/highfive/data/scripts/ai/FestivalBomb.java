package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.model.instances.L2FestivalMonsterInstance;
import ru.l2gw.gameserver.model.entity.SevenSignsFestival.FestivalManager;
import ru.l2gw.util.Location;


public class FestivalBomb extends DefaultAI
{
	private static final int BOOM_SKILL = 4614;

	public FestivalBomb(L2Character actor)
	{
		super(actor);
		_globalAggro = 0;
	}

	@Override
	protected boolean thinkActive()
	{
		if(_thisActor == null || _thisActor.isDead())
			return true;

		Location BOOM_POINT = FestivalManager.getInstance().getFestivalById(((L2FestivalMonsterInstance) _thisActor).getFestivalId()).getStartLoc();
		if(_def_think)
		{
			if(doTask())
				clearTasks();
			return true;
		}

		if(_thisActor.getDistance(BOOM_POINT.getX(), BOOM_POINT.getY(), BOOM_POINT.getZ()) - _thisActor.getColRadius() > 10)
		{
			_thisActor.setRunning();
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = BOOM_POINT;
			_task_list.add(task);
			_def_think = true;
			return true;
		}
		else
			return createNewTask();
	}

	@Override
	protected boolean createNewTask()
	{
		clearTasks();
		if(_thisActor == null || _thisActor.isDead())
			return false;

		Location BOOM_POINT = FestivalManager.getInstance().getFestivalById(((L2FestivalMonsterInstance) _thisActor).getFestivalId()).getStartLoc();

		if(_thisActor.getDistance(BOOM_POINT.getX(), BOOM_POINT.getY(), BOOM_POINT.getZ()) - _thisActor.getColRadius() <= 10)
		{
			L2Skill boom_skill = _thisActor.getTemplate().getSkills().get(BOOM_SKILL);
			addUseSkillDesire(_thisActor, boom_skill, 1, 1, DEFAULT_DESIRE * 100);
			return true;
		}
		return false;
	}

	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
		_thisActor.addDamage(attacker, damage);
	}

	@Override
	protected void onEvtAggression(L2Character attacker, int aggro, L2Skill skill)
	{
		_thisActor.addDamageHate(attacker, 0, aggro);
	}

}