package ai;

import ru.l2gw.gameserver.ai.Fighter;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;
import ru.l2gw.commons.math.Rnd;

import static ru.l2gw.gameserver.ai.CtrlIntention.AI_INTENTION_ACTIVE;

public class Gordon extends Fighter
{
	private Location[] points = new Location[32];
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;

	public Gordon(L2Character actor)
	{
		super(actor);
		MAX_PURSUE_RANGE = 30000;
		// spawn: 147316,-64797,-3469
		points[0] = new Location(146268, -64651, -3412);
		points[1] = new Location(143678, -64045, -3434);
		points[2] = new Location(141620, -62316, -3210);
		points[3] = new Location(139466, -60839, -2994);
		points[4] = new Location(138429, -57679, -3548);
		points[5] = new Location(139402, -55879, -3334);
		points[6] = new Location(139660, -52780, -2908);
		points[7] = new Location(139516, -50343, -2591);
		points[8] = new Location(140059, -48657, -2271);
		points[9] = new Location(140319, -46063, -2408);
		points[10] = new Location(142462, -45540, -2432);
		points[11] = new Location(144290, -43543, -2380);
		points[12] = new Location(146494, -43234, -2325);
		points[13] = new Location(148416, -43186, -2329);
		points[14] = new Location(151135, -44084, -2746);
		points[15] = new Location(153040, -42240, -2920);
		points[16] = new Location(154871, -39193, -3294);
		points[17] = new Location(156725, -41827, -3569);
		points[18] = new Location(157788, -45071, -3598);
		points[19] = new Location(159433, -45943, -3547);
		points[20] = new Location(160327, -47404, -3681);
		points[21] = new Location(159106, -48215, -3691);
		points[22] = new Location(159541, -50908, -3563);
		points[23] = new Location(159576, -53782, -3226);
		points[24] = new Location(160918, -56899, -2790);
		points[25] = new Location(160785, -59505, -2662);
		points[26] = new Location(158252, -60098, -2680);
		points[27] = new Location(155962, -59751, -2656);
		points[28] = new Location(154649, -60214, -2701);
		points[29] = new Location(153121, -63319, -2969);
		points[30] = new Location(151511, -64366, -3174);
		points[31] = new Location(149161, -64576, -3316);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	public boolean checkAggression(L2Character target)
	{
		if(_intention != AI_INTENTION_ACTIVE)
			return false;
		// Агрится только на носителей проклятого оружия
		if(!target.isCursedWeaponEquipped())
			return false;
		super.checkAggression(target);
		// Продолжит идти с предыдущей точки
		if(_intention != AI_INTENTION_ACTIVE && current_point > -1)
			current_point--;
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
			doTask();
			return true;
		}

		// BUFF
		if(_selfbuff_skills.length > 0 && Rnd.chance(5))
		{
			L2Skill r_skill = _selfbuff_skills[Rnd.get(_selfbuff_skills.length)];
			if(_thisActor.getEffectBySkill(r_skill) == null)
			{
				// Добавить новое задание
				addUseSkillDesire(_thisActor, r_skill, 1, 1, DEFAULT_DESIRE * 2);
				return true;
			}
		}

		if(System.currentTimeMillis() > wait_timeout && (current_point > -1 || Rnd.chance(5)))
		{
			if(!wait)
				switch(current_point)
				{
					case 31:
						wait_timeout = System.currentTimeMillis() + 60000;
						wait = true;
						return true;
				}

			wait_timeout = 0;
			wait = false;

			if(current_point >= points.length - 1)
				current_point = -1;

			current_point++;

			_thisActor.setWalking();

			// Добавить новое задание
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