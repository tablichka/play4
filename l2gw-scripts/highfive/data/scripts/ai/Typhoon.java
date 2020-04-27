package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.Mystic;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.gameserver.tables.SkillTable;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 18.01.2010 19:42:24
 */
public class Typhoon extends Mystic
{
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;
	private final static L2Skill t_skill = SkillTable.getInstance().getInfo(5382, 4);
	private final static Location[] points = {
			new Location(-11144, 250807, -2828),
			new Location(-8787, 251027, -2868),
			new Location(-6223, 251669, -3177),
			new Location(-6064, 253438, -3352),
			new Location(-8091, 255282, -3267),
			new Location(-10527, 256220, -3342),
			new Location(-11994, 255439, -3311),
			new Location(-14361, 254127, -3479),
			new Location(-16793, 252455, -3458),
			new Location(-19598, 251602, -3301),
			new Location(-21414, 253134, -3331),
			new Location(-21374, 255481, -3274),
			new Location(-19846, 256982, -3189),
			new Location(-18227, 256508, -3152),
			new Location(-16315, 255200, -3209),
			new Location(-14923, 255302, -3143),
			new Location(-12791, 256343, -3354),
			new Location(-11175, 256677, -3402),
			new Location(-9612, 256084, -3298),
			new Location(-8772, 254717, -3209),
			new Location(-8292, 252358, -3075)
	};

	public Typhoon(L2Character actor)
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

		// Update every 1s the _globalAggro counter to come close to 0
		if(_globalAggro < 0)
			_globalAggro++;
		else if(_globalAggro > 0)
			_globalAggro--;

		if(_def_think)
		{
			Task currTask = null;
			try
			{
				currTask = _task_list.first();
			}
			catch(Exception e)
			{
			}

			if(t_skill != null && currTask != null && currTask.type == TaskType.MOVE)
			{
				int c = _thisActor.getAroundLivePlayers(1000).size();

				if(c > 0 && Rnd.chance(80))
				{
					clearTasks();
					addUseSkillDesire(_thisActor, t_skill, 1, 1, DEFAULT_DESIRE * 100);
				}
			}

			doTask();
			return true;
		}

		// BUFF
		if(_selfbuff_skills.length > 0 && Rnd.chance(5))
		{
			L2Skill r_skill = _selfbuff_skills[Rnd.get(_selfbuff_skills.length)];
			addUseSkillDesire(_thisActor, r_skill, 1, 1, DEFAULT_DESIRE * 100);
			return true;
		}

		if(System.currentTimeMillis() > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			if(!wait && current_point == 20)
			{
				wait_timeout = System.currentTimeMillis() + 10000;
				wait = true;
				return true;
			}

			wait_timeout = 0;
			wait = false;

			if(current_point >= points.length - 1)
				current_point = -1;

			current_point++;

			_thisActor.setWalking();

			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = points[current_point];
			_task_list.add(task);
			_def_think = true;
			return true;
		}

		if(randomAnimation())
			return false;

		return false;
	}
}
