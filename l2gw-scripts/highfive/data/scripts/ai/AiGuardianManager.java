package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 06.09.11 13:38
 */
public class AiGuardianManager extends DefaultAI
{
	public int Max_Spawn_Roaming = 3;
	public int Spawn_Interval = 1800;
	public int Course1_Check_Timer = 7734;
	public int Course2_Check_Timer = 7735;
	public int Course3_Check_Timer = 7736;
	public int Course4_Check_Timer = 7737;
	public int SuperPointMethod = 0;
	public int SuperPointDesire = 2000;
	public int SpawnPosX_1_1 = 140641;
	public int SpawnPosY_1_1 = 114525;
	public int SpawnPosZ_1_1 = -3752;
	public int SpawnPosX_2_1 = 143789;
	public int SpawnPosY_2_1 = 110205;
	public int SpawnPosZ_2_1 = -3968;
	public int SpawnPosX_3_1 = 146466;
	public int SpawnPosY_3_1 = 109789;
	public int SpawnPosZ_3_1 = -3440;
	public int SpawnPosX_4_1 = 145482;
	public int SpawnPosY_4_1 = 120250;
	public int SpawnPosZ_4_1 = -3944;

	public AiGuardianManager(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(Course1_Check_Timer, Rnd.get(10000));
		addTimer(Course2_Check_Timer, Rnd.get(10000));
		addTimer(Course3_Check_Timer, Rnd.get(10000));
		addTimer(Course4_Check_Timer, Rnd.get(10000));
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai3 = 0;
		_thisActor.i_ai4 = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == Course1_Check_Timer)
		{
			if(_thisActor.i_ai0 < Max_Spawn_Roaming)
			{
				_thisActor.createOnePrivate(22857, "AiGuardianOfAntaras", 0, 0, SpawnPosX_1_1, SpawnPosY_1_1, SpawnPosZ_1_1, 0, 1, 0, 0);
				_thisActor.i_ai0++;
			}
			addTimer(Course1_Check_Timer, (Spawn_Interval * 1000));
		}
		else if(timerId == Course2_Check_Timer)
		{
			if(_thisActor.i_ai1 < Max_Spawn_Roaming)
			{
				_thisActor.createOnePrivate(22857, "AiGuardianOfAntaras", 0, 0, SpawnPosX_2_1, SpawnPosY_2_1, SpawnPosZ_2_1, 0, 2, 0, 0);
				_thisActor.i_ai1++;
			}
			addTimer(Course2_Check_Timer, (Spawn_Interval * 1000));
		}
		else if(timerId == Course3_Check_Timer)
		{
			if(_thisActor.i_ai2 < Max_Spawn_Roaming)
			{
				_thisActor.createOnePrivate(22857, "AiGuardianOfAntaras", 0, 0, SpawnPosX_3_1, SpawnPosY_3_1, SpawnPosZ_3_1, 0, 3, 0, 0);
				_thisActor.i_ai2++;
			}
			addTimer(Course3_Check_Timer, (Spawn_Interval * 1000));
		}
		else if(timerId == Course4_Check_Timer)
		{
			if(_thisActor.i_ai3 < Max_Spawn_Roaming)
			{
				_thisActor.createOnePrivate(22857, "AiGuardianOfAntaras", 0, 0, SpawnPosX_4_1, SpawnPosY_4_1, SpawnPosZ_4_1, 0, 4, 0, 0);
				_thisActor.i_ai3++;
			}
			addTimer(Course4_Check_Timer, (Spawn_Interval * 1000));
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 20100501)
		{
			switch((Integer) arg1)
			{
				case 1:
					if(_thisActor.i_ai0 > 0)
						_thisActor.i_ai0--;
					break;
				case 2:
					if(_thisActor.i_ai1 > 0)
						_thisActor.i_ai1--;
					break;
				case 3:
					if(_thisActor.i_ai2 > 0)
						_thisActor.i_ai2--;
					break;
				case 4:
					if(_thisActor.i_ai3 > 0)
						_thisActor.i_ai3--;
					break;
			}
		}
		super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}
