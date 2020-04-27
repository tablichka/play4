package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 05.10.11 17:08
 */
public class Ssq2Watcher3Book extends Citizen
{
	public int p_ASK_OUT_CROSS_ROOM = 10294;
	public int p_REP_OUT_CROSS_ROOM = 3;

	public Ssq2Watcher3Book(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80103, _thisActor.id);
		addTimer(2103, 5000);
		_thisActor.i_ai1 = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2103)
		{
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32829);
			if(c0 != null)
			{
				c0.i_ai1 = 1;
			}

			// хз но на оффе рандомное расположение отключено
			/*
			GArray<Location> locs = new GArray<>(4);
			locs.add(new Location(85793, -253611, -8331));
			locs.add(new Location(86904, -252506, -8328));
			locs.add(new Location(85789, -251429, -8331));
			locs.add(new Location(84712, -252512, -8331));

			for(int i = 0; i < 4; i++)
			{
				L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(_thisActor, 32829 + i);
				if(c1 != null)
				{
					c1.teleToLocation(locs.remove(Rnd.get(locs.size())));
				}
			}
			*/
		}
		else
			super.onEvtTimer(timerId, arg1, arg2);
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 90100)
		{
			_thisActor.i_ai1 = 1;
			//int i0 = ServerVariables.getInt("GM_" + 80107);
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32819);
			if(c0 != null)
			{
				c0.i_ai0 = 1;
			}

			//int i1 = ServerVariables.getInt("GM_" + 80100);
			L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(_thisActor, 32803);
			if(c1 != null)
			{
				_thisActor.notifyAiEvent(c1, CtrlEvent.EVT_SCRIPT_EVENT, 90103, 0, null);
			}

			//int i2 = ServerVariables.getInt("GM_" + 80008);
			L2NpcInstance c2 = InstanceManager.getInstance().getNpcById(_thisActor, 32787);
			if(c2 != null)
			{
				_thisActor.notifyAiEvent(c2, CtrlEvent.EVT_SCRIPT_EVENT, 90108, 0, null);
				_thisActor.createOnePrivate(32797, "Citizen", 0, 1, c2.getX(), c2.getY(), c2.getZ(), Rnd.get(65535), 0, 0, 0);
			}
		}
		else
			super.onEvtScriptEvent(eventId, arg1, arg2);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == 10294 && reply == 2)
		{
			if(_thisActor.i_ai1 == 1)
			{
				_thisActor.showPage(talker, "ssq2_watcher3_book_q10294_06.htm", 10294);
			}
			else
			{
				_thisActor.showPage(talker, "ssq2_watcher3_book_q10294_04.htm", 10294);
			}
		}
		else if(ask == p_ASK_OUT_CROSS_ROOM && reply == p_REP_OUT_CROSS_ROOM)
		{
			talker.teleToLocation(85937, -249618, -8350);
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32787);
			if(c0 != null)
			{
				c0.teleToLocation(85937, -249618, -8350);
			}

			L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(_thisActor, 32889);
			if(c1 != null)
			{
				_thisActor.notifyAiEvent(c1, CtrlEvent.EVT_SCRIPT_EVENT, 90110, 0, null);
			}
		}
		else
			super.onMenuSelected(talker, ask, reply);
	}
}