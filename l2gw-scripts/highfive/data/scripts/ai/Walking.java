package ai;

import ru.l2gw.gameserver.Config;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 19.01.2010 19:10:09
 */
public class Walking extends DefaultAI
{
	private long lastMove = 0;
	private boolean arrived = false;
	private static L2Skill t_skill = SkillTable.getInstance().getInfo(5494, 1);

	public Walking(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
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

		if(arrived)
		{
			lastMove = System.currentTimeMillis() + Rnd.get(2000, 4000);
			if(t_skill != null)
			{
				addUseSkillDesire(_thisActor, t_skill, 1, 1, DEFAULT_DESIRE * 2);
				arrived = false;
			}
			return true;
		}

		if(lastMove < System.currentTimeMillis())
		{
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = Location.coordsRandomize(_thisActor.getSpawnedLoc(), Config.MAX_DRIFT_RANGE * 2);
			_thisActor.setWalking();
			_task_list.add(task);
			arrived = false;
			_def_think = true;
		}
		return true;
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();
		arrived = true;
	}


	@Override
	protected void onEvtAttacked(L2Character attacker, int damage, L2Skill skill)
	{
	}

	@Override
	protected void onEvtAggression(L2Character target, int aggro, L2Skill skill)
	{
	}
}
