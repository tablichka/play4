package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 17.09.11 10:47
 */
public class Repre extends Citizen
{
	public Repre(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(!talker.isQuestStarted(10286) && talker.getLevel() >= 82 && talker.isQuestComplete(10285))
		{
			if(talker.getQuestCount() < 41)
			{
				_thisActor.showQuestPage(talker, "repre003.htm", 10286);
			}
			else
			{
				_thisActor.showPage(talker, "fullquest.htm");
			}
			return true;
		}

		return super.onTalk(talker);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if( ask == -2317 )
		{
			if( reply == 1 )
			{
				if( talker.getItemCountByItemId(16025) > 0 )
				{
					_thisActor.showPage(talker, "repre004.htm");
				}
				else
				{
					_thisActor.showPage(talker, "repre006.htm");
				}
			}
			else if( reply == 2 )
			{
				if( talker.getItemCountByItemId(16027) > 0 )
				{
					_thisActor.showPage(talker, "repre008.htm");
				}
				else
				{
					_thisActor.showPage(talker, "repre007.htm");
				}
			}
			else if( reply == 3 )
			{
				if( talker.getItemCountByItemId(16025) > 0 && talker.getItemCountByItemId(16027) > 0 )
				{
					talker.destroyItemByItemId("Quest", 16025, 1, _thisActor, true);
					talker.destroyItemByItemId("Quest", 16027, 1, _thisActor, true);
					talker.addItem("Quest", 16026, 1, _thisActor, true);
					_thisActor.showPage(talker, "repre009.htm");
				}
				else
				{
					_thisActor.showPage(talker, "repre011.htm");
				}
			}
		}
		super.onMenuSelected(talker, ask, reply);
	}
}
