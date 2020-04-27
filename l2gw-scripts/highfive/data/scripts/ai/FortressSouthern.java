package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressSouthern extends DefaultAI
{
	private Location[] points = new Location[44];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressSouthern(L2Character actor)
	{
		super(actor);

		points[0] = new Location(-18968, 218825, -3422);
		points[1] = new Location(-18811, 218407, -3485);
		points[2] = new Location(-18704, 218028, -3530);
		points[3] = new Location(-18562, 217543, -3441);
		points[4] = new Location(-18322, 216841, -3538);
		points[5] = new Location(-17938, 216520, -3539);
		points[6] = new Location(-17531, 216124, -3563);
		points[7] = new Location(-17177, 215716, -3589);
		points[8] = new Location(-17037, 215134, -3624);
		points[9] = new Location(-16838, 214353, -3679);
		points[10] = new Location(-16656, 213694, -3687);
		points[11] = new Location(-16220, 212827, -3702);
		points[12] = new Location(-16502, 212752, -3728);
		points[13] = new Location(-17221, 212861, -3731);
		points[14] = new Location(-18048, 212834, -3733);
		points[15] = new Location(-19026, 212634, -3703);
		points[16] = new Location(-19713, 212390, -3631);
		points[17] = new Location(-20636, 211966, -3450);
		points[18] = new Location(-21194, 211740, -3354);
		points[19] = new Location(-21601, 211547, -3306);
		points[20] = new Location(-22482, 211262, -3301);
		points[21] = new Location(-23398, 210958, -3301);
		points[22] = new Location(-24348, 210589, -3301);
		points[23] = new Location(-25345, 210178, -3301);
		points[24] = new Location(-25879, 210051, -3301);
		points[25] = new Location(-26604, 209985, -3301);
		points[26] = new Location(-27231, 209988, -3301);
		points[27] = new Location(-27869, 210112, -3301);
		points[28] = new Location(-28408, 210253, -3301);
		points[29] = new Location(-28936, 210438, -3303);
		points[30] = new Location(-29486, 210637, -3343);
		points[31] = new Location(-30201, 210764, -3457);
		points[32] = new Location(-30939, 210991, -3597);
		points[33] = new Location(-31666, 211498, -3685);
		points[34] = new Location(-31688, 211918, -3684);
		points[35] = new Location(-31405, 212545, -3687);
		points[36] = new Location(-31068, 213058, -3684);
		points[37] = new Location(-30439, 213784, -3702);
		points[38] = new Location(-29743, 214424, -3713);
		points[39] = new Location(-29309, 214778, -3709);
		points[40] = new Location(-28729, 215427, -3700);
		points[41] = new Location(-28296, 216284, -3662);
		points[42] = new Location(-28212, 216870, -3586);
		points[43] = new Location(-28066, 217467, -3537);

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