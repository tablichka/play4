package ai.rr;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.tables.DoorTable;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 20.01.12 14:10
 */
public class RoyalRushLock extends DefaultNpc
{
	public String fnHi = "black001.htm";
	public String fnNoItem = "";
	public int RoomIDX = 1;
	public String DoorName = "royal_rush_door_1a";
	public int AfflictMonster = 20130;
	public int SpawnX1 = 0;
	public int SpawnY1 = 0;
	public int SpawnZ1 = 0;
	public int SpawnX2 = 0;
	public int SpawnY2 = 0;
	public int SpawnZ2 = 0;
	public int SpawnX3 = 0;
	public int SpawnY3 = 0;
	public int SpawnZ3 = 0;

	public RoyalRushLock(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai0 = 0;
		DoorTable.getInstance().doorOpenClose(DoorName, 1);
		addTimer(3000, 1000);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, fnHi);
		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(talker.getItemCountByItemId(7260) != 0)
		{
			talker.destroyItemByItemId("Consume", 7260, 1, _thisActor, true);
			DoorTable.getInstance().doorOpenClose(DoorName, 0);
			if(_thisActor.i_ai0 == 0)
			{
				addTimer(3001, 15000);
				_thisActor.i_ai0 = 1;
			}
			if(SpawnX1 != 0)
			{
				_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 1000 + RoomIDX, null, 0);
				int i0 = Rnd.get(3);
				switch(i0)
				{
					case 0:
						_thisActor.createOnePrivate(18244, "rr.RoyalRushAfflict", 10, 5, SpawnX1, SpawnY1, SpawnZ1, 32768, 1, 0, 0);
						_thisActor.createOnePrivate(18245, "rr.RoyalRushAfflict", 10, 5, SpawnX2, SpawnY2, SpawnZ2, 32768, 2, 0, 0);
						_thisActor.createOnePrivate(18246, "rr.RoyalRushAfflict", 10, 5, SpawnX3, SpawnY2, SpawnZ3, 32768, 3, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(18244, "rr.RoyalRushAfflict", 10, 5, SpawnX2, SpawnY2, SpawnZ2, 32768, 1, 0, 0);
						_thisActor.createOnePrivate(18245, "rr.RoyalRushAfflict", 10, 5, SpawnX3, SpawnY3, SpawnZ3, 32768, 2, 0, 0);
						_thisActor.createOnePrivate(18246, "rr.RoyalRushAfflict", 10, 5, SpawnX1, SpawnY1, SpawnZ1, 32768, 3, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(18244, "rr.RoyalRushAfflict", 10, 5, SpawnX3, SpawnY3, SpawnZ3, 32768, 1, 0, 0);
						_thisActor.createOnePrivate(18245, "rr.RoyalRushAfflict", 10, 5, SpawnX1, SpawnY1, SpawnZ1, 32768, 2, 0, 0);
						_thisActor.createOnePrivate(18246, "rr.RoyalRushAfflict", 10, 5, SpawnX2, SpawnY2, SpawnZ2, 32768, 3, 0, 0);
						break;
				}
				Functions.npcSay(_thisActor, Say2C.SHOUT, 1000502);
			}
			else
			{
				_thisActor.notifyAiEvent(_thisActor.getLeader(), CtrlEvent.EVT_SCRIPT_EVENT, 1000 + _thisActor.param1, null, 0);
			}
		}
		else
		{
			_thisActor.showPage(talker, fnNoItem);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3000)
		{
			int i0 = Calendar.getInstance().get(Calendar.MINUTE);
			int i1 = Calendar.getInstance().get(Calendar.SECOND);
			if(i0 == 55 && i1 == 0)
			{
				_thisActor.i_ai0 = 0;
				DoorTable.getInstance().doorOpenClose(DoorName, 1);
			}
			addTimer(3000, 1000);
		}
		if(timerId == 3001)
		{
			DoorTable.getInstance().doorOpenClose(DoorName, 1);
			_thisActor.i_ai0 = 0;
		}
	}
}