package ai.rr;

import ai.Citizen;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2Player;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;
import ru.l2gw.gameserver.tables.SpawnTable;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.util.Util;

import java.util.Calendar;

/**
 * @author: rage
 * @date: 20.01.12 13:43
 */
public class RoyalRushNpc extends Citizen
{
	public String room_trigger_1 = "1rd_trigger_a";
	public String room_trigger_2 = "1rd_trigger_b";
	public String room_trigger_3 = "1rd_trigger_c";
	public String room_trigger_4 = "1rd_trigger_d";
	public String room_trigger_5 = "1rd_trigger_e";
	public String room_trigger_boss = "1rd_type2_boss_e";
	public int StartTelPosX = 0;
	public int StartTelPosY = 0;
	public int StartTelPosZ = 0;
	public int EscapeTelPosX = 0;
	public int EscapeTelPosY = 0;
	public int EscapeTelPosZ = 0;
	public int ShoutMsg = 0;
	public int lock_npc_id1 = 31075;
	public int lock_npc_id2 = 31075;
	public int lock_npc_id3 = 31075;
	public int lock_npc_id4 = 31075;
	public int lock_npc_id5 = 31075;
	public String lock_npc_ai1 = "rr.RoyalRushLock";
	public String lock_npc_ai2 = "rr.RoyalRushLock";
	public String lock_npc_ai3 = "rr.RoyalRushLock";
	public String lock_npc_ai4 = "rr.RoyalRushLock";
	public String lock_npc_ai5 = "rr.RoyalRushLock";
	public int lock_x1 = 0;
	public int lock_y1 = 0;
	public int lock_z1 = 0;
	public int lock_d1 = 0;
	public int lock_x2 = 0;
	public int lock_y2 = 0;
	public int lock_z2 = 0;
	public int lock_d2 = 0;
	public int lock_x3 = 0;
	public int lock_y3 = 0;
	public int lock_z3 = 0;
	public int lock_d3 = 0;
	public int lock_x4 = 0;
	public int lock_y4 = 0;
	public int lock_z4 = 0;
	public int lock_d4 = 0;
	public int lock_x5 = 0;
	public int lock_y5 = 0;
	public int lock_z5 = 0;
	public int lock_d5 = 0;

	public RoyalRushNpc(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		_thisActor.showPage(talker, fnHi);
		return true;
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		addTimer(3000, 1000);
		_thisActor.i_ai0 = 0;
		_thisActor.i_ai1 = 0;
		if(lock_x1 != 0)
		{
			_thisActor.createOnePrivate(lock_npc_id1, lock_npc_ai1, 0, 0, lock_x1, lock_y1, lock_z1, lock_d1, 0, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id2, lock_npc_ai2, 0, 0, lock_x2, lock_y2, lock_z2, lock_d2, 0, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id3, lock_npc_ai3, 0, 0, lock_x3, lock_y3, lock_z3, lock_d3, 0, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id4, lock_npc_ai4, 0, 0, lock_x4, lock_y4, lock_z4, lock_d4, 0, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id5, lock_npc_ai5, 0, 0, lock_x5, lock_y5, lock_z5, lock_d5, 0, 0, 0);
		}
		else
		{
			_thisActor.createOnePrivate(lock_npc_id1, "rr.RoyalRushLock", 0, 0, 182727, -85493, -7200, -32584, 1, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id2, "rr.RoyalRushLock", 0, 0, 184547, -85479, -7200, -32584, 2, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id3, "rr.RoyalRushLock", 0, 0, 186349, -85473, -7200, -32584, 3, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id4, "rr.RoyalRushLock", 0, 0, 188154, -85463, -7200, -32584, 4, 0, 0);
			_thisActor.createOnePrivate(lock_npc_id5, "rr.RoyalRushLock", 0, 0, 189947, -85466, -7200, -32584, 5, 0, 0);
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 3001)
		{
			if(_thisActor.i_ai1 == 1)
			{
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(room_trigger_1);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
				_thisActor.i_ai1 = 0;
			}
		}
		else if(timerId == 3000)
		{
			int i0 = Calendar.getInstance().get(Calendar.MINUTE);
			int i1 = Calendar.getInstance().get(Calendar.SECOND);
			if(i0 == 0 && i1 < 5)
			{
				if(_thisActor.i_ai1 == 0)
				{
					DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(room_trigger_1);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1000, 0, 0);
					}
					_thisActor.i_ai1 = 1;
					addTimer(3001, 5000);
				}
			}
			else if((i0 == 5 || i0 == 10 || i0 == 15 || i0 == 20 || i0 == 25 || i0 == 30 || i0 == 35 || i0 == 40 || i0 == 45) && i1 == 0)
			{
				if(ShoutMsg == 1)
				{
					//Functions.npcSayInRange(_thisActor, Say2C.SHOUT, 1000455 + i0 + 1000456, 11500);
				}
			}
			else if(i0 == 50 && i1 == 0)
			{
				if(ShoutMsg == 1)
				{
					Functions.npcSayInRange(_thisActor, Say2C.SHOUT, 1000457, 11500);
				}
				if(EscapeTelPosX != 0 && EscapeTelPosY != 0 && EscapeTelPosZ != 0)
				{
					Util.teleportInMyTerritory(_thisActor, EscapeTelPosX, EscapeTelPosY, EscapeTelPosZ, 100);
				}
			}
			else if(i0 == 54 && i1 >= 0 && i1 <= 30)
			{
				_thisActor.av_quest0.set(0);
			}
			else if(i0 == 55 && i1 == 0)
			{
				Functions.npcSayInRange(_thisActor, Say2C.SHOUT, 1000500, 11500);
				Functions.npcSayInRange(_thisActor, Say2C.SHOUT, 1000501, 11500);
			}
			addTimer(3000, 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		switch(eventId)
		{
			case 1001:
				DefaultMaker maker0 = SpawnTable.getInstance().getNpcMaker(room_trigger_2);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
			case 1002:
				maker0 = SpawnTable.getInstance().getNpcMaker(room_trigger_3);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
			case 1003:
				maker0 = SpawnTable.getInstance().getNpcMaker(room_trigger_4);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
			case 1004:
				maker0 = SpawnTable.getInstance().getNpcMaker(room_trigger_5);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
			case 1005:
				int i0 = Rnd.get(4);
				i0 = (i0 + 1);
				String s0 = room_trigger_boss + "_type" + i0;
				maker0 = SpawnTable.getInstance().getNpcMaker(s0);
				if(maker0 != null)
				{
					maker0.onScriptEvent(1000, 0, 0);
					maker0.onScriptEvent(1001, 0, 0);
				}
				break;
		}
	}
}