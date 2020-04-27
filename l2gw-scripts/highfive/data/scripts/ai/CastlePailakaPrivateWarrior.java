package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlIntention;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;

/**
 * @author admin
 * @date 02.12.2010 18:10:34
 */
public class CastlePailakaPrivateWarrior extends CastlePailakaInvaderWarrior
{
	public CastlePailakaPrivateWarrior(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();

		addTimer(1400, 9000);
		addTimer(1401, 9000);
		addTimer(1601, 101000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1400)
		{
			if(_intention != CtrlIntention.AI_INTENTION_ATTACK)
			{
				if(String_Num1 > 0)
					Functions.npcSay(_thisActor, Say2C.ALL, String_Num1);
				_thisActor.setRunning();
				_thisActor.moveToLocation(pos, 0, true);
			}
		}
		else if(timerId == 1401)
		{
			if(SelfBuff != null)
				_thisActor.altUseSkill(SelfBuff, _thisActor);

			addTimer(1401, 30000);
		}
		else if(timerId == 1601)
		{
			broadcastScriptEvent(INVADER, _thisActor, null, 2000);
			addTimer(1601, 10000);
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}
}
