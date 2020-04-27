package ai;

import ai.base.WarriorUseSkill;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author: rage
 * @date: 03.09.11 11:25
 */
public class AiMagmaDrakeHatchlingBaby extends WarriorUseSkill
{
	public AiMagmaDrakeHatchlingBaby(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 10023)
			_thisActor.deleteMe();
	}
}
