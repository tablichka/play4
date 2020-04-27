package ai;

import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.instances.L2NpcInstance;

/**
 * @author: rage
 * @date: 05.10.11 16:57
 */
public class Ssq2Watcher2Book extends Citizen
{
	public int p_ASK_OUT_CROSS_ROOM = 10294;
	public int p_REP_OUT_CROSS_ROOM = 3;

	public Ssq2Watcher2Book(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		//ServerVariables.set("GM_" + 80102, _thisActor.id);
		addTimer(2102, 5000);
		_thisActor.i_ai1 = 0;
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 2102)
		{
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32825);
			if(c0 != null)
			{
				c0.i_ai1 = 1;
			}

			// хз но на оффе рандомное расположение отключено
			/*
			GArray<Location> locs = new GArray<>(4);
			locs.add(new Location(82528, -250734, -8327));
			locs.add(new Location(83630, -249615, -8333));
			locs.add(new Location(82525, -248525, -8328));
			locs.add(new Location(81441, -249621, -8331));

			for(int i = 0; i < 4; i++)
			{
				L2NpcInstance c1 = InstanceManager.getInstance().getNpcById(_thisActor, 32825 + i);
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
			//int i0 = ServerVariables.getInt("GM_" + 80106);
			L2NpcInstance c0 = InstanceManager.getInstance().getNpcById(_thisActor, 32818);
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
				_thisActor.notifyAiEvent(c2, CtrlEvent.EVT_SCRIPT_EVENT, 90107, 0, null);
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
				_thisActor.showPage(talker, "ssq2_watcher2_book_q10294_06.htm", 10294);
			}
			else
			{
				_thisActor.showPage(talker, "ssq2_watcher2_book_q10294_04.htm", 10294);
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
