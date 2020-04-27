package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 16.09.11 15:42
 */
public class Keier extends Citizen
{
	public Keier(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(!talker.isQuestComplete(115))
		{
			if(!talker.isQuestStarted(115))
			{
				_thisActor.showPage(talker, "keier001.htm");
				return true;
			}
			else
			{
				_thisActor.showPage(talker, "keier002.htm");
				return true;
			}
		}
		else if(talker.isQuestStarted(10283) && talker.getQuestState(10283).getMemoState() == 2)
		{
			_thisActor.showPage(talker, "keier003.htm");
			return true;
		}
		else if(talker.isQuestComplete(10283))
		{
			_thisActor.showPage(talker, "keier004.htm");
			return true;
		}

		return super.onTalk(talker);
	}
}
