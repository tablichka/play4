package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 06.10.11 19:16
 */
public class Ssq2TombGuardian4 extends WarriorUseSkill
{
	public int SLAVE_SPAWN_POS1_X = 56463;
	public int SLAVE_SPAWN_POS1_Y = -252225;
	public int SLAVE_SPAWN_POS1_Z = -6751;
	public int SLAVE_SPAWN_POS2_X = 56469;
	public int SLAVE_SPAWN_POS2_Y = -252108;
	public int SLAVE_SPAWN_POS2_Z = -6752;
	public int SLAVE_SPAWN_POS3_X = 56336;
	public int SLAVE_SPAWN_POS3_Y = -252168;
	public int SLAVE_SPAWN_POS3_Z = -6752;
	public int SLAVE_SPAWN_POS4_X = 56336;
	public int SLAVE_SPAWN_POS4_Y = -252288;
	public int SLAVE_SPAWN_POS4_Z = -6752;

	public Ssq2TombGuardian4(L2Character actor)
	{
		super(actor);
		IsAggressive = 1;
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80403, _thisActor.id);
		addTimer(2202, 5000);
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2202)
		{
			if(_thisActor.i_ai1 < 4 && _thisActor.i_ai0 == 1)
			{
				_thisActor.i_ai1++;
				switch(Rnd.get(4))
				{
					case 0:
						_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS1_X, SLAVE_SPAWN_POS1_Y, SLAVE_SPAWN_POS1_Z, Rnd.get(65535), 0, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS2_X, SLAVE_SPAWN_POS2_Y, SLAVE_SPAWN_POS2_Z, Rnd.get(65535), 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS3_X, SLAVE_SPAWN_POS3_Y, SLAVE_SPAWN_POS3_Z, Rnd.get(65535), 0, 0, 0);
						break;
					case 3:
						_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS4_X, SLAVE_SPAWN_POS4_Y, SLAVE_SPAWN_POS4_Z, Rnd.get(65535), 0, 0, 0);
						break;
				}
			}
			_thisActor.lookNeighbor(300);
			addTimer(2202, 5000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected synchronized void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90208)
		{
			_thisActor.i_ai0 = 1;
			_thisActor.i_ai1++;
			_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS1_X, SLAVE_SPAWN_POS1_Y, SLAVE_SPAWN_POS1_Z, Rnd.get(65535), 0, 0, 0);
			_thisActor.i_ai1++;
			_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS2_X, SLAVE_SPAWN_POS2_Y, SLAVE_SPAWN_POS2_Z, Rnd.get(65535), 0, 0, 0);
			_thisActor.i_ai1++;
			_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS3_X, SLAVE_SPAWN_POS3_Y, SLAVE_SPAWN_POS3_Z, Rnd.get(65535), 0, 0, 0);
			_thisActor.i_ai1++;
			_thisActor.createOnePrivate(27404, "Ssq2TombSlave2", 0, 1, SLAVE_SPAWN_POS4_X, SLAVE_SPAWN_POS4_Y, SLAVE_SPAWN_POS4_Z, Rnd.get(65535), 0, 0, 0);
		}
		else if(eventId == 90206)
		{
			_thisActor.i_ai1--;
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}
}