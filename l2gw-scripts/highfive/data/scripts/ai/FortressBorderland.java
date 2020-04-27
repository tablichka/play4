package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressBorderland extends DefaultAI
{
	private Location[] points = new Location[39];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressBorderland(L2Character actor)
	{
		super(actor);
		points[0] = new Location(161837, -73150, -2988);
		points[1] = new Location(161845, -73491, -3016);
		points[2] = new Location(161895, -74007, -3055);
		points[3] = new Location(161870, -74498, -3088);
		points[4] = new Location(161751, -75095, -3134);
		points[5] = new Location(161561, -75487, -3153);
		points[6] = new Location(161098, -76000, -3236);
		points[7] = new Location(160594, -76546, -3326);
		points[8] = new Location(160283, -76971, -3400);
		points[9] = new Location(159996, -77365, -3497);
		points[10] = new Location(159733, -77650, -3601);
		points[11] = new Location(159479, -77852, -3677);
		points[12] = new Location(159035, -78003, -3776);
		points[13] = new Location(158975, -77549, -3798);
		points[14] = new Location(158925, -77045, -3763);
		points[15] = new Location(158789, -76199, -3765);
		points[16] = new Location(158674, -75670, -3764);
		points[17] = new Location(158469, -75086, -3737);
		points[18] = new Location(158246, -74542, -3676);
		points[19] = new Location(158008, -73959, -3572);
		points[20] = new Location(157742, -73538, -3475);
		points[21] = new Location(157360, -73326, -3431);
		points[22] = new Location(156872, -73166, -3393);
		points[23] = new Location(156399, -72848, -3370);
		points[24] = new Location(155810, -72480, -3355);
		points[25] = new Location(155088, -72124, -3378);
		points[26] = new Location(154364, -71799, -3388);
		points[27] = new Location(153959, -71531, -3412);
		points[28] = new Location(153578, -71266, -3458);
		points[29] = new Location(153343, -70940, -3496);
		points[30] = new Location(152990, -70677, -3536);
		points[31] = new Location(152393, -70366, -3604);
		points[32] = new Location(152549, -70214, -3550);
		points[33] = new Location(152961, -70136, -3456);
		points[34] = new Location(153498, -70068, -3337);
		points[35] = new Location(154009, -69942, -3246);
		points[36] = new Location(154578, -69740, -3138);
		points[37] = new Location(155213, -69498, -3016);
		points[38] = new Location(155884, -69295, -2898);
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

		if(System.currentTimeMillis() > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			wait_timeout = 0;


			if(current_point >= points.length - 1)
			{
				current_point = 0;
				_thisActor.teleToLocation(points[0]);
				return true;
			}

			current_point++;

			// Добавить новое задание
			Task task = new Task();
			task.type = TaskType.MOVE;
			task.usePF = false;
			task.loc = points[current_point];
			_task_list.add(task);
			_def_think = true;
			return true;
		}

		return randomAnimation();
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