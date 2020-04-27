package ai;

import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.L2Player;

/**
 * @author: rage
 * @date: 09.10.11 22:19
 */
public class MinigameInstructor extends Citizen
{
	public int TIMER_0 = 3344;
	public int TIMER_1 = 3345;
	public int TIMER_2 = 3346;
	public int TIMER_3 = 3347;
	public int TIMER_4 = 3348;
	public int TIMER_5 = 3349;
	public int TIMER_6 = 3350;
	public int TIMER_7 = 3351;
	public int TIMER_8 = 3352;
	public int TIMER_9 = 3356;
	public int PC_TURN = 3357;
	public int GAME_TIME_EXPIRED = 3354;
	public int PosX = 113187;
	public int PosY = -85388;
	public int PosZ = -3424;
	public int HURRY_UP = 3358;
	public int HURRY_UP2 = 3359;
	public int GAME_TIME = 3360;
	public int interval_time = 3;
	public int PosX1 = 118833;
	public int PosY1 = -80589;
	public int PosZ1 = -2688;
	public int PosX2 = 118833;
	public int PosY2 = -80589;
	public int PosZ2 = -2688;
	public int Inven_Check_SysMsg = 1118;

	public MinigameInstructor(L2Character actor)
	{
		super(actor);
		fnHi = "minigame_instructor001.htm";
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.c_ai0 = 0;
		_thisActor.i_quest1 = 0;
		_thisActor.i_quest2 = 0;
		_thisActor.i_quest0 = 0;
	}

	@Override
	public boolean onTalk(L2Player talker)
	{
		L2Player c0 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai0);
		if(c0 == null && _thisActor.i_quest2 == 0)
		{
			_thisActor.showPage(talker, "minigame_instructor001.htm");
			_thisActor.c_ai1 = talker.getStoredId();
		}
		else if(c0 == null && _thisActor.i_quest2 == 1)
		{
			_thisActor.showPage(talker, "minigame_instructor008.htm");
		}
		else if(c0 == talker && _thisActor.i_quest0 == 1 && _thisActor.i_quest1 == 0)
		{
			_thisActor.showPage(talker, "minigame_instructor002.htm");
		}
		else if(c0 == talker && _thisActor.i_quest0 == 2 && _thisActor.i_quest1 == 0)
		{
			_thisActor.showPage(talker, "minigame_instructor003.htm");
		}
		else if(c0 != talker)
		{
			_thisActor.showPage(talker, "minigame_instructor004.htm");
		}
		else if(_thisActor.i_quest1 == 1)
		{
			_thisActor.showPage(talker, "minigame_instructor007.htm");
		}

		return true;
	}

	@Override
	public void onMenuSelected(L2Player talker, int ask, int reply)
	{
		if(ask == -1)
		{
			if(reply == 1)
			{
				if(!talker.isQuestContinuationPossible())
				{
					return;
				}

				L2Player c1 = L2ObjectsStorage.getAsPlayer(_thisActor.c_ai1);

				if(talker.getItemCountByItemId(15540) == 0)
				{
					_thisActor.showPage(talker, "minigame_instructor005.htm");
				}
				else if(c1 != talker)
				{
					_thisActor.showPage(talker, "minigame_instructor004.htm");
				}
				else
				{
					_thisActor.i_quest1 = 1;
					_thisActor.i_quest2 = 1;
					talker.destroyItemByItemId("Minigame", 15540, 1, _thisActor, true);
					talker.addItem("Minigame", 15485, 1, _thisActor, true);
					Functions.npcSay(_thisActor, Say2C.ALL, 60000);
					_thisActor.i_ai1 = Rnd.get(9) + 1;
					_thisActor.i_ai2 = Rnd.get(9) + 1;
					_thisActor.i_ai3 = Rnd.get(9) + 1;
					_thisActor.i_ai4 = Rnd.get(9) + 1;
					_thisActor.i_ai5 = Rnd.get(9) + 1;
					_thisActor.i_ai6 = Rnd.get(9) + 1;
					_thisActor.i_ai7 = Rnd.get(9) + 1;
					_thisActor.i_ai8 = Rnd.get(9) + 1;
					_thisActor.i_ai9 = Rnd.get(9) + 1;
					_thisActor.c_ai0 = talker.getStoredId();
					addTimer(HURRY_UP, 2 * 60000);
					addTimer(GAME_TIME, 3 * 60000 + 10000);
					addTimer(TIMER_0, 1000);
				}
			}
		}
		else if(ask <= -2 && ask >= -4)
		{
			if(reply == 2)
			{
				_thisActor.i_quest1 = 1;
				_thisActor.i_ai1 = Rnd.get(9) + 1;
				_thisActor.i_ai2 = Rnd.get(9) + 1;
				_thisActor.i_ai3 = Rnd.get(9) + 1;
				_thisActor.i_ai4 = Rnd.get(9) + 1;
				_thisActor.i_ai5 = Rnd.get(9) + 1;
				_thisActor.i_ai6 = Rnd.get(9) + 1;
				_thisActor.i_ai7 = Rnd.get(9) + 1;
				_thisActor.i_ai8 = Rnd.get(9) + 1;
				_thisActor.i_ai9 = Rnd.get(9) + 1;
				_thisActor.c_ai0 = talker.getStoredId();
				addTimer(TIMER_0, 1000);
			}
		}
		if(ask == -7801)
		{
			if(reply == 2)
			{
				talker.teleToLocation(PosX1, PosY1, PosZ1);
			}
		}
		if(ask == -7801)
		{
			if(reply == 1)
			{
				talker.teleToLocation(PosX2, PosY2, PosZ2);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_0)
		{
			broadcastScriptEvent(2114002, 0, null, 1200);
			addTimer(TIMER_1, interval_time * 2000);
		}
		else if(timerId == TIMER_1)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai1, null, 1200);
			addTimer(TIMER_2, interval_time * 1000);
		}
		else if(timerId == TIMER_2)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai2, null, 1200);
			addTimer(TIMER_3, interval_time * 1000);
		}
		else if(timerId == TIMER_3)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai3, null, 1200);
			addTimer(TIMER_4, interval_time * 1000);
		}
		else if(timerId == TIMER_4)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai4, null, 1200);
			addTimer(TIMER_5, interval_time * 1000);
		}
		else if(timerId == TIMER_5)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai5, null, 1200);
			addTimer(TIMER_6, interval_time * 1000);
		}
		else if(timerId == TIMER_6)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai6, null, 1200);
			addTimer(TIMER_7, interval_time * 1000);
		}
		else if(timerId == TIMER_7)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai7, null, 1200);
			addTimer(TIMER_8, interval_time * 1000);
		}
		else if(timerId == TIMER_8)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai8, null, 1200);
			addTimer(TIMER_9, interval_time * 1000);
		}
		else if(timerId == TIMER_9)
		{
			broadcastScriptEvent(2114001, _thisActor.i_ai9, null, 1200);
			addTimer(PC_TURN, interval_time * 1000);
		}
		else if(timerId == HURRY_UP)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 60001);
			addTimer(HURRY_UP2, 60000);
		}
		else if(timerId == HURRY_UP2)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 60002);
			addTimer(GAME_TIME_EXPIRED, 10000);
		}
		else if(timerId == PC_TURN)
		{
			Functions.npcSay(_thisActor, Say2C.ALL, 60003);
			broadcastScriptEvent(21140015, 0, null, 1200);
			_thisActor.i_ai0 = 1;
		}
		else if(timerId == GAME_TIME_EXPIRED)
		{
			broadcastScriptEvent(2114003, 0, null, 1200);
			Functions.npcSay(_thisActor, Say2C.ALL, 60004);
			_thisActor.c_ai0 = 0;
			_thisActor.i_quest0 = 0;
			_thisActor.i_quest1 = 0;
		}
		else if(timerId == GAME_TIME)
		{
			_thisActor.i_quest2 = 0;
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 2114005)
		{
			if((Integer) arg1 == _thisActor.i_ai1 && _thisActor.i_ai0 == 1)
			{
				_thisActor.i_ai0 = 2;
			}
			else if((Integer) arg1 == _thisActor.i_ai2 && _thisActor.i_ai0 == 2)
			{
				_thisActor.i_ai0 = 3;
			}
			else if((Integer) arg1 == _thisActor.i_ai3 && _thisActor.i_ai0 == 3)
			{
				_thisActor.i_ai0 = 4;
			}
			else if((Integer) arg1 == _thisActor.i_ai4 && _thisActor.i_ai0 == 4)
			{
				_thisActor.i_ai0 = 5;
			}
			else if((Integer) arg1 == _thisActor.i_ai5 && _thisActor.i_ai0 == 5)
			{
				_thisActor.i_ai0 = 6;
			}
			else if((Integer) arg1 == _thisActor.i_ai6 && _thisActor.i_ai0 == 6)
			{
				_thisActor.i_ai0 = 7;
			}
			else if((Integer) arg1 == _thisActor.i_ai7 && _thisActor.i_ai0 == 7)
			{
				_thisActor.i_ai0 = 8;
			}
			else if((Integer) arg1 == _thisActor.i_ai8 && _thisActor.i_ai0 == 8)
			{
				_thisActor.i_ai0 = 9;
			}
			else if((Integer) arg1 == _thisActor.i_ai9 && _thisActor.i_ai0 == 9)
			{
				broadcastScriptEvent(2114003, 0, null, 1200);
				_thisActor.createOnePrivate(18934, "npc", 0, 0, PosX, PosY, PosZ, 0, 0, 0, 0);
				Functions.npcSay(_thisActor, Say2C.ALL, 60005);
				blockTimer(HURRY_UP);
				blockTimer(HURRY_UP2);
				_thisActor.c_ai0 = 0;
				_thisActor.i_quest0 = 0;
				_thisActor.i_quest1 = 0;
				_thisActor.i_ai0 = 0;
				_thisActor.i_ai9 = 0;
			}
			else
			{
				broadcastScriptEvent(2114004, 0, null, 1200);
				if(_thisActor.i_quest0 < 2)
				{
					_thisActor.i_quest0++;
					Functions.npcSay(_thisActor, Say2C.ALL, 60006);
					_thisActor.i_quest1 = 0;
				}
				else
				{
					blockTimer(HURRY_UP);
					blockTimer(HURRY_UP2);
					Functions.npcSay(_thisActor, Say2C.ALL, 60007);
					_thisActor.c_ai0 = 0;
					_thisActor.i_quest0 = 0;
					_thisActor.i_quest1 = 0;
				}
			}
		}
	}
}