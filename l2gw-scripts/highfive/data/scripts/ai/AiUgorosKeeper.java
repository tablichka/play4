package ai;

import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.instancemanager.ServerVariables;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 07.09.11 22:33
 */
public class AiUgorosKeeper extends Citizen
{
	public int GM_UGOROS = 37;
	public int ugoros_x = 95984;
	public int ugoros_y = 85692;
	public int ugoros_z = -3692;
	public int ugoros_exile_x = 94224;
	public int ugoros_exile_y = 83019;
	public int ugoros_exile_z = -3552;
	public String fnNoItem = "batracos002.htm";
	public String fnNotAccepted = "batracos003.htm";
	public String fnNoUgoros = "batracos004.htm";
	public String fnWayOut = "batracos005.htm";
	public int TID_EXILE_WAIT = 78001;
	public int TIME_EXILE_WAIT = 3;
	public int clearer_mode = 79;

	public AiUgorosKeeper(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		if(_thisActor.param1 == clearer_mode)
		{
			_thisActor.showPage(talker, fnWayOut);
			return true;
		}
		return super.onTalk(talker);
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -7801)
		{
			if(reply == 1)
			{
				if(_thisActor.param1 == clearer_mode)
				{
					talker.teleToLocation(ugoros_exile_x, ugoros_exile_y, ugoros_exile_z);
				}
				else if(ServerVariables.getLong("GM_" + GM_UGOROS) == -1)
				{
					_thisActor.showPage(talker, fnNoUgoros);
				}
				else if(talker.getItemCountByItemId(15496) > 0 || debug)
				{
					if(ServerVariables.getLong("GM_" + GM_UGOROS) == -2)
					{
						_thisActor.showPage(talker, fnNotAccepted);
					}
					else
					{
						talker.teleToLocation(ugoros_x, ugoros_y, ugoros_z);
						if(!debug)
						{
							talker.destroyItemByItemId(getClass().getSimpleName(), 15496, 1, _thisActor, true);
						}
						L2Character c0 = L2ObjectsStorage.getAsCharacter(ServerVariables.getLong("GM_" + GM_UGOROS));
						if(c0 != null)
						{
							sendScriptEvent(c0, 78010084, talker.getStoredId(), null);
						}
					}
				}
				else
				{
					_thisActor.showPage(talker, fnNoItem);
				}
			}
		}
		super.onMenuSelected(talker, ask, reply);
	}

	@Override
	protected void onEvtSpawn()
	{
		if(_thisActor.param1 == clearer_mode)
		{
			if(debug)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "spawned");
				if(_thisActor.inMyTerritory(_thisActor))
				{
					Functions.npcSay(_thisActor, Say2C.ALL, "inside my trr");
				}
			}
			addTimer(TID_EXILE_WAIT, TIME_EXILE_WAIT * 60000);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TID_EXILE_WAIT && _thisActor.param1 == clearer_mode)
		{
			//myself.InstantTeleportInMyTerritory(ugoros_exile_x, ugoros_exile_y, ugoros_exile_z, 500);
			_thisActor.onDecay();
		}
	}
}
