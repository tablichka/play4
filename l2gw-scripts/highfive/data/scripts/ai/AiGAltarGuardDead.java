package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 09.09.11 12:39
 */
public class AiGAltarGuardDead extends Citizen
{
	public String fnYouAreHero = "g_altar_guard_dead002.htm";

	public AiGAltarGuardDead(L2Character actor)
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
		if(talker.isHero())
		{
			_thisActor.showPage(talker, fnYouAreHero);
		}
		else
		{
			_thisActor.showPage(talker, fnHi);
		}

		return true;
	}
}
