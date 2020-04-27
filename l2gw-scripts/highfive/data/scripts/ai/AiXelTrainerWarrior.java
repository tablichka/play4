package ai;

import ai.base.AiXelTrainerWiz;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 06.09.11 16:04
 */
public class AiXelTrainerWarrior extends AiXelTrainerWiz
{
	public AiXelTrainerWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2219001)
		{
			if(!_thisActor.isMoving)
			{
				switch(Rnd.get(6))
				{
					case 0:
						addEffectActionDesire(1, (130 * 1000) / 30, 50);
						broadcastScriptEvent(2219011, trainer_id, null, trainning_range);
						break;
					case 1:
						addEffectActionDesire(4, (70 * 1000) / 30, 50);
						broadcastScriptEvent(2219012, trainer_id, null, trainning_range);
						break;
					case 2:
						addEffectActionDesire(5, (30 * 1000) / 30, 50);
						broadcastScriptEvent(2219013, trainer_id, null, trainning_range);
						break;
					case 3:
					case 4:
					case 5:
						addEffectActionDesire(7, (130 * 1000) / 30, 50);
						broadcastScriptEvent(2219014, trainer_id, null, trainning_range);
						break;
				}
			}
			addTimer(2219001, 15000);
		}
		super.onEvtTimer(timerId, arg1, arg2);
	}

}
