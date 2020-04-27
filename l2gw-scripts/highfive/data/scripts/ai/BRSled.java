package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.DefaultAI;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.serverpackets.SocialAction;
import ru.l2gw.util.Location;

/**
 * @author rage
 * @date 29.11.2010 20:47:46
 */
public class BRSled extends DefaultAI
{
	private static final int MOVE_DELAY = 14401;
	private static final int CHECK_EVENT = 14402;
	private static final int START_DELAY = 14403;
	private static final int PHASE_BUFFSKILL = 14404;
	private int _param1;
	private int _pos;
	private static final Location loc1 = new Location(81505, 141709, -2732);
	private static final Location loc2 = new Location(81549, 154725, -2732);

	public BRSled(L2Character actor)
	{
		super(actor);
		_thisActor.setRunning();
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
	
	@Override
	protected void onEvtSpawn()
	{
		super.onEvtSpawn();
		_param1 = getInt("param1");
		if(_param1 == 0)
		{
			addTimer(CHECK_EVENT, 30000);
			_pos = 1;
			_thisActor.moveToLocation(loc2, 0, false);
		}
		else
			addTimer(START_DELAY, 2000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == CHECK_EVENT)
		{
			if(ServerVariables.getInt("br_xmas_event") == 1)
				_thisActor.deleteMe();
			else
			{
				addTimer(CHECK_EVENT, 30000);
				if(!_thisActor.isMoving)
				{
					if(_pos == 1)
					{
						_thisActor.moveToLocation(loc2, 0, false);
						_pos = 2;
					}
					else if(_pos == 2)
					{
						_thisActor.moveToLocation(loc1, 0, false);
						_pos = 1;
					}
				}
			}
		}
		else if(timerId == MOVE_DELAY)
		{
			_thisActor.broadcastPacket(new SocialAction(_thisActor.getObjectId(), 0));
			addTimer(MOVE_DELAY, 42000);
		}
		else if(timerId == START_DELAY)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 1800738);
			addTimer(PHASE_BUFFSKILL, 5000);
		}
		if(timerId == PHASE_BUFFSKILL)
		{
			L2Player player = L2ObjectsStorage.getPlayer(ServerVariables.getInt("br_xmas_event_pc"));
			if(player != null)
				/* TODO: Перевести на NpcString Announcements.getInstance().announceToAll(new SystemMessage(SystemMessage.S1).addString(new CustomMessage("fs1900027", Config.DEFAULT_LANG).addCharName(player).toString()));*/

			_thisActor.moveToLocation(_thisActor.getX() - 500, _thisActor.getY() - 2000, _thisActor.getZ() + 1000, 0, true);
		}
	}

	@Override
	protected void onEvtArrived()
	{
		super.onEvtArrived();
		if(_param1 == 0)
		{
			if(_pos == 1)
			{
				_thisActor.moveToLocation(loc2, 0, false);
				_pos = 2;
			}
			else if(_pos == 2)
			{
				_thisActor.moveToLocation(loc1, 0, false);
				_pos = 1;
			}
		}
		else
			_thisActor.deleteMe();
	}
}
