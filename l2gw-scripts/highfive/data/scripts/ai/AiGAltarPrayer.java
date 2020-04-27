package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 09.09.11 12:36
 */
public class AiGAltarPrayer extends Citizen
{
	public AiGAltarPrayer(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(NoFnHi == 1)
		{
			talker.sendActionFailed();
			return true;
		}
		if(talker.getKarma() > 0)
		{
			if(Rnd.get(3) < 1)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1900160, talker.getName());
			}
			else if(Rnd.get(9) < 1)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, 1900161, talker.getName());
			}
			else
			{
				_thisActor.showPage(talker, fnHi);
			}
		}
		else
		{
			_thisActor.showPage(talker, fnHi);
		}

		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(1000, 3600000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1000)
		{
			if(Rnd.get(3) < 1)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1900152);
			}
			else if(Rnd.get(2) < 1)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1900153);
			}
			else
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1900154);
			}
			addTimer(1000, 3600000);
		}
	}
}