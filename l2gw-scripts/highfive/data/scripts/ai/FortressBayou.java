package ai;

import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

public class FortressBayou extends DefaultAI
{
	private Location[] points = new Location[32];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressBayou(L2Character actor)
	{
		super(actor);
		points[0] = new Location(190384, 43221, -3611);
		points[1] = new Location(190299, 43616, -3712);
		points[2] = new Location(190153, 44102, -3830);
		points[3] = new Location(189931, 44682, -3961);
		points[4] = new Location(189796, 45469, -4192);
		points[5] = new Location(189618, 45970, -4300);
		points[6] = new Location(189335, 45802, -4344);
		points[7] = new Location(188821, 45230, -4517);
		points[8] = new Location(188347, 44709, -4674);
		points[9] = new Location(187826, 44199, -4832);
		points[10] = new Location(187239, 43668, -4876);
		points[11] = new Location(186877, 43246, -4790);
		points[12] = new Location(186492, 42645, -4731);
		points[13] = new Location(186155, 42172, -4603);
		points[14] = new Location(185822, 41507, -4483);
		points[15] = new Location(185733, 41015, -4395);
		points[16] = new Location(185608, 40471, -4314);
		points[17] = new Location(185442, 39694, -4274);
		points[18] = new Location(185261, 38915, -4231);
		points[19] = new Location(185139, 38387, -4229);
		points[20] = new Location(185052, 37344, -4210);
		points[21] = new Location(184835, 36727, -4172);
		points[22] = new Location(184539, 36475, -4134);
		points[23] = new Location(184818, 36139, -4088);
		points[24] = new Location(185421, 35919, -3948);
		points[25] = new Location(185913, 35879, -3855);
		points[26] = new Location(186493, 35721, -3795);
		points[27] = new Location(187064, 35621, -3777);
		points[28] = new Location(187932, 35933, -3660);
		points[29] = new Location(188586, 36175, -3521);
		points[30] = new Location(189248, 36443, -3440);
		points[31] = new Location(189700, 36589, -3437);
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