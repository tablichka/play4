package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Skill;
import ru.l2gw.util.Location;

public class FortressSwamp extends DefaultAI
{
	private Location[] points = new Location[76];
	private int current_point = -1;
	private long wait_timeout = 0;


	public FortressSwamp(L2Character actor)
	{
		super(actor);

		points[0] = new Location(71350, -58128, -2933);
		points[1] = new Location(71511, -57679, -3056);
		points[2] = new Location(71651, -57218, -3133);
		points[3] = new Location(72063, -56968, -3126);
		points[4] = new Location(72671, -56576, -3136);
		points[5] = new Location(73171, -56053, -3138);
		points[6] = new Location(73482, -55646, -3136);
		points[7] = new Location(73899, -55013, -3136);
		points[8] = new Location(74268, -54557, -3144);
		points[9] = new Location(74688, -54108, -3149);
		points[10] = new Location(74541, -53556, -3136);
		points[11] = new Location(74346, -53143, -3136);
		points[12] = new Location(73910, -52737, -3129);
		points[13] = new Location(73450, -52479, -3132);
		points[14] = new Location(73087, -52061, -3151);
		points[15] = new Location(72573, -51708, -3138);
		points[16] = new Location(71845, -51297, -3115);
		points[17] = new Location(71391, -51248, -3045);
		points[18] = new Location(70808, -51225, -3096);
		points[19] = new Location(70153, -51654, -3112);
		points[20] = new Location(69745, -52017, -3149);
		points[21] = new Location(69091, -52355, -3198);
		points[22] = new Location(68446, -52613, -3304);
		points[23] = new Location(67998, -52971, -3302);
		points[24] = new Location(67672, -53408, -3257);
		points[25] = new Location(67443, -54063, -3223);
		points[26] = new Location(67094, -54652, -3119);
		points[27] = new Location(66946, -55115, -3025);
		points[28] = new Location(66745, -55548, -2923);
		points[29] = new Location(66714, -56240, -2812);
		points[30] = new Location(66352, -56556, -2791);
		points[31] = new Location(65566, -56813, -2773);
		points[32] = new Location(64923, -56745, -2766);
		points[33] = new Location(64370, -56963, -2803);
		points[34] = new Location(63576, -57436, -2836);
		points[35] = new Location(62900, -57928, -2861);
		points[36] = new Location(62537, -58465, -2883);
		points[37] = new Location(62102, -59731, -2993);
		points[38] = new Location(61842, -60867, -3110);
		points[39] = new Location(61376, -61831, -3202);
		points[40] = new Location(60825, -62274, -3235);
		points[41] = new Location(60239, -62222, -3288);
		points[42] = new Location(59780, -62129, -3328);
		points[43] = new Location(59410, -62378, -3384);
		points[44] = new Location(58994, -63197, -3486);
		points[45] = new Location(58809, -63870, -3491);
		points[46] = new Location(58857, -64866, -3489);
		points[47] = new Location(58990, -65651, -3397);
		points[48] = new Location(59082, -66281, -3311);
		points[49] = new Location(59115, -66976, -3314);
		points[50] = new Location(59196, -67580, -3392);
		points[51] = new Location(59913, -67949, -3392);
		points[52] = new Location(60241, -68369, -3397);
		points[53] = new Location(60600, -69107, -3363);
		points[54] = new Location(60769, -69837, -3344);
		points[55] = new Location(60865, -70586, -3327);
		points[56] = new Location(61006, -71501, -3350);
		points[57] = new Location(61226, -72091, -3409);
		points[58] = new Location(61698, -72582, -3493);
		points[59] = new Location(62278, -72956, -3569);
		points[60] = new Location(62914, -73134, -3622);
		points[61] = new Location(63573, -73102, -3660);
		points[62] = new Location(64015, -72592, -3692);
		points[63] = new Location(64805, -71525, -3722);
		points[64] = new Location(65374, -71532, -3730);
		points[65] = new Location(66357, -71650, -3834);
		points[66] = new Location(66527, -71028, -3750);
		points[67] = new Location(66794, -70121, -3604);
		points[68] = new Location(67090, -69375, -3496);
		points[69] = new Location(67416, -68832, -3316);
		points[70] = new Location(67599, -68382, -3177);
		points[71] = new Location(67681, -67769, -3023);
		points[72] = new Location(67829, -66937, -2875);
		points[73] = new Location(68075, -66299, -2788);
		points[74] = new Location(68328, -65457, -2766);
		points[75] = new Location(68472, -64908, -2764);

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