package ai;

import ru.l2gw.commons.crontab.Crontab;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 08.03.12 22:17
 */
public class SsqNpcDepravityPriest extends Citizen
{
	private static Crontab _dayleReuse = new Crontab("30 6 * * *");

	public SsqNpcDepravityPriest(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "marketeer_of_mammon001.htm");
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 506)
		{
			if(reply == 3)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon_q0506_04.htm");
			}
			else if(reply == 4)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon_q0506_04.htm");
			}
			else if(reply == 5)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon_q0506_04.htm");
			}
		}
		else if(ask == -240)
		{
			if(talker.getItemCountByItemId(5575) < reply)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon_q0506_12.htm");
			}
			else if(reply <= 0)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon_q0506_14.htm");
			}
			else if(talker.isQuestContinuationPossible())
			{
				talker.destroyItemByItemId("Exchange", 5575, reply, _thisActor, true);
				talker.addAdena("Exchange", reply, _thisActor, true);
				_thisActor.showPage(talker, "marketeer_of_mammon_q0506_13.htm");
			}
		}
		else if(ask == 989 && reply == 3)
		{
			if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 20)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon002e.htm");
			}
			else
			{
				_thisActor.showPage(talker, "marketeer_of_mammon003.htm");
			}
		}
		else if(ask == 990 && reply == 3)
		{
			if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < 20)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon002e.htm");
			}
			else if(talker.getAdena() < 2000000)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon002c.htm");
			}
			else if(talker.getVarB("q989"))
			{
				_thisActor.showPage(talker, "marketeer_of_mammon002b.htm");

			}
			else if(talker.getLevel() < 60)
			{
				_thisActor.showPage(talker, "marketeer_of_mammon002d.htm");
			}
			else if(talker.isQuestContinuationPossible())
			{
				talker.setVar("q989", "true", (int) (_dayleReuse.timeNextUsage(System.currentTimeMillis()) / 1000));
				talker.reduceAdena("Exchange", 2000000, _thisActor, true);
				talker.addItem("Exchange", 5575, 500000, _thisActor, true);
				_thisActor.showPage(talker, "marketeer_of_mammon004.htm");
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}
}