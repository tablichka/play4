package ai;

import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.PlaySound;
import ru.l2gw.util.Location;

/**
 * @author: rage
 * @date: 07.11.11 21:32
 */
public class EventAlegria extends Citizen
{
	public EventAlegria(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		_thisActor.i_ai2 = 0;
		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.param3);
		if(c0 != null)
			c0.sendPacket(new PlaySound(1, "HB01", 0, 0, new Location(0, 0, 0)));
		addTimer(20002, 180000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 20002)
		{
			_thisActor.onDecay();
		}
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, "event_alegria001.htm");
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 20001)
		{
			if(reply == 1)
			{
				if(talker.getItemCountByItemId(10250) > 0)
				{
					talker.destroyItemByItemId("Birhday", 10250, 1, _thisActor, true);
					talker.addItem("Birthday", 21594, 1, _thisActor, true);
					_thisActor.showPage(talker, "event_alegria003.htm");
					_thisActor.onDecay();
				}
				else
				{
					_thisActor.showPage(talker, "event_alegria004.htm");
				}
			}
			else if(reply == 2)
			{
				if(talker.getItemCountByItemId(21594) > 0)
				{
					talker.destroyItemByItemId("Birhday", 21594, 1, _thisActor, true);
					talker.addItem("Birthday", 10250, 1, _thisActor, true);
					_thisActor.showPage(talker, "event_alegria003.htm");
					_thisActor.onDecay();
				}
				else
				{
					_thisActor.showPage(talker, "event_alegria004.htm");
				}
			}
		}
	}
}
