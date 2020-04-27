package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.clientpackets.Say2C;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.entity.category.CategoryManager;
import ru.l2gw.gameserver.model.entity.instance.Instance;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 26.09.11 20:55
 */
public class FreyaController extends DefaultNpc
{
	public String MAKER_freya_1st = "schuttgart29_2314_100m1";
	public String MAKER_freya_2nd = "schuttgart29_2314_102m1";
	public String MAKER_freya_3rd = "schuttgart29_2314_104m1";
	public String MAKER_freya_4th = "schuttgart29_2314_106m1";
	public String MAKER_freya_ending = "schuttgart29_2314_108m1";
	public String MAKER_ice_knight = "schuttgart29_2314_03m1";
	public String MAKER_ice_castle = "schuttgart29_2314_06m1";
	public String MAKER_freya_2nd_spelling = "schuttgart29_2314_102m3";
	public int TIMER_1st_phase_success = 2314201;
	public int TIMER_2nd_phase_success = 2314202;
	public int TIMER_3rd_phase_success = 2314203;
	public int TIMER_4th_phase_success = 2314204;
	public int TIMER_SCENE_15 = 2314501;
	public int TIMER_SCENE_15_END = 2314511;
	public int scene_num_15 = 15;
	public int scene_sec_15 = 53500;
	public int TIMER_SCENE_16 = 2314502;
	public int TIMER_SCENE_16_END = 2314512;
	public int scene_num_16 = 16;
	public int scene_sec_16 = 24100;
	public int TIMER_SCENE_17 = 2314503;
	public int TIMER_SCENE_17_END = 2314513;
	public int scene_num_17 = 17;
	public int scene_sec_17 = 21500;
	public int TIMER_SCENE_18 = 2314504;
	public int TIMER_SCENE_18_END = 2314514;
	public int scene_num_18 = 18;
	public int scene_sec_18 = 27000;
	public int TIMER_SCENE_19 = 2314505;
	public int TIMER_SCENE_19_END = 2314515;
	public int scene_num_19 = 19;
	public int scene_sec_19 = 16000;
	public int TIMER_SCENE_20 = 2314506;
	public int TIMER_SCENE_20_END = 2314516;
	public int scene_num_20 = 20;
	public int scene_sec_20 = 3000;
	public int TIMER_SCENE_22 = 2314508;
	public int TIMER_SCENE_22_END = 2314518;
	public int scene_num_22 = 22;
	public int scene_sec_22 = 22500;
	public int TIMER_SCENE_23 = 2314509;
	public int TIMER_SCENE_23_END = 2314519;
	public int scene_num_23 = 23;
	public int scene_sec_23 = 7000;
	public int TIMER_knight = 2314205;
	public int TIMER_castle = 2314206;
	public int TIMER_start = 2314207;
	public int TIMER_hold_desire = 2314301;
	public int TIMER_ZONEINFO_CHG = 2314302;
	public int TIMER_reset_routine = 2314305;
	public int TIMER_force_defeat = 2314023;
	public int TIMER_2nd_hard_timer = 2314026;
	public int FLAG_SPAWN = 231400;
	public int FLAG_NO_SPAWN = 231401;
	public int phase_to_phase = 60000;
	public String IceQueen_DoorName = "icequeen_door_001";
	public int is_hard_mode = 0;
	public int debug_mode = 0;

	public FreyaController(L2Character actor)
	{
		super(actor);
	}

	@Override
	public boolean isGlobalAI()
	{
		return true;
	}

	@Override
	protected void onEvtSpawn()
	{
		if(debug_mode > 0)
		{
			Functions.npcSay(_thisActor, Say2C.SHOUT, "controller spawned");
		}
		_thisActor.i_ai0 = 23140001;
		_thisActor.i_ai1 = FLAG_NO_SPAWN;
		_thisActor.i_ai2 = 0;
		_thisActor.l_ai3 = 0;
		_thisActor.i_ai4 = 0;
		_thisActor.i_ai5 = 0;
		_thisActor.i_ai6 = 6;
		_thisActor.i_ai7 = 0;
		_thisActor.createOnePrivate(18919, "FreyaEntrance", 0, 0, 114394, -112383, -11200, 0, is_hard_mode, 0, 0);
		Instance inst = _thisActor.getInstanceZone();
		if(inst != null)
			inst.openCloseDoor(IceQueen_DoorName, 0);
	}

	@Override
	protected void onEvtSeeCreature(L2Character creature)
	{
		if(_thisActor.i_ai4 == 0)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "SEE_CREATURE");
			}
			if(creature.isPlayer() || CategoryManager.isInCategory(12, creature.getNpcId()) || CategoryManager.isInCategory(123, creature.getNpcId()))
			{
				_thisActor.i_ai4 = 1;
				addTimer(TIMER_SCENE_15, 10000);
			}
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 23140014)
		{
			if(_thisActor.i_ai2 == 0)
			{
				_thisActor.i_ai2 = 1;

				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 2, 0, 0, 0, 0, 1, 7000, 0, 1801086);
				Instance inst = _thisActor.getInstanceZone();
				if(inst != null)
					inst.openCloseDoor(IceQueen_DoorName, 1);

				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
				if(c0 != null)
				{
					c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140014, 0);
				}
				_thisActor.i_ai1 = FLAG_SPAWN;
				addTimer(TIMER_knight, 1000);
				addTimer(TIMER_castle, 1000);
			}
			else if(_thisActor.i_ai2 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase1 battle already started. event duplicated");
				}
			}
		}
		else if(eventId == 23140016)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase1 battle end");
			}
			_thisActor.i_ai1 = FLAG_NO_SPAWN;
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(FLAG_NO_SPAWN, 0, 0);
			}
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140055, 0);
			}
			if((Integer) arg1 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase1 battle success. wait 1min");
				}
				addTimer(TIMER_SCENE_16, 1000);
				broadcastScriptEvent(23140020, 1, 0, 4000);
			}
			else if((Integer) arg1 == 2)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase1 battle failed.");
				}
				addTimer(TIMER_SCENE_22, 1000);
			}
		}
		else if(eventId == 23140017)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase2 battle end");
			}
			_thisActor.i_ai1 = FLAG_NO_SPAWN;

			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(FLAG_NO_SPAWN, 0, 0);
			}
			if((Integer) arg1 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase2 battle success. wait 1min");
				}
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
				if(c0 != null)
				{
					c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140050, 1000);
				}
				addTimer(TIMER_SCENE_17, phase_to_phase);
				broadcastScriptEvent(23140020, 2, 0, 4000);
			}
			else if((Integer) arg1 == 2)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase2 battle failed.");
				}
				addTimer(TIMER_SCENE_22, 1000);
			}
		}
		else if(eventId == 23140018)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase3 battle end");
			}
			_thisActor.i_ai1 = FLAG_NO_SPAWN;
			if((Integer) arg1 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase3 battle success. no wait and goint 4th phase");
				}
				addTimer(TIMER_hold_desire, 1000);
			}
			else if((Integer) arg1 == 2)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase3 battle failed.");
				}
				addTimer(TIMER_SCENE_22, 1000);
			}
		}
		else if(eventId == 23140019)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase4 battle end");
			}
			_thisActor.i_ai1 = FLAG_NO_SPAWN;
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140055, 0);
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140019, 0);
			}
			if((Integer) arg1 == 1)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase4 battle success.");
				}
				addTimer(TIMER_SCENE_19, 1000);
				DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
				if(maker0 != null)
				{
					maker0.onScriptEvent(23140006, 0, 0);
				}
				broadcastScriptEvent(23140020, 4, 0, 4000);
			}
			else if((Integer) arg1 == 2)
			{
				if(debug_mode > 0)
				{
					Functions.npcSay(_thisActor, Say2C.SHOUT, "Phase4 battle failed.");
				}
				addTimer(TIMER_SCENE_22, 1000);
			}
		}
		else if(eventId == 23140022)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
			if(c0 != null)
			{
				_thisActor.l_ai3 = (Long) arg1;
			}
		}
		else if(eventId == 23140048)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140055, 0);
			}
			_thisActor.i_ai5 = 1;
		}
		else if(eventId == 23140049)
		{
			_thisActor.i_ai5 = 0;
		}
		else if(eventId == 23140053)
		{
			addTimer(TIMER_SCENE_23, 1000);
		}
		else if(eventId == 23140057)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140057, 0);
			}
		}
		else if(eventId == 23140058)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140058, 0);
			}
		}
		else if(eventId == 23141234)
		{
			addTimer(TIMER_SCENE_22_END, 1);
		}
		else if(eventId == 23140064)
		{
			if(_thisActor.i_ai1 == FLAG_NO_SPAWN)
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg1);
				if(c0 != null)
				{
					c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140065, 0);
				}
			}
		}
		else if(eventId == 23140066 || eventId == 23140067 || eventId == 23140068)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, eventId, 0);
			}
		}
		else if(eventId == 23140070)
		{
			if((Integer) arg1 == 1)
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 1, 5000, 0, 1801189);
			}
			else
			{
				Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 5, 1, 0, 0, 0, 1, 5000, 0, 1801111);
			}
		}
		else if(eventId == 23140059)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, eventId, 0);
			}
		}
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == TIMER_1st_phase_success)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 2, 0, 0, 0, 0, 1, 7000, 0, 1801087);
			_thisActor.i_ai0 = 23140002;
			_thisActor.i_ai1 = FLAG_SPAWN;
			addTimer(TIMER_knight, 1000);
			addTimer(TIMER_castle, 1000);
			if(is_hard_mode == 1)
			{
				addTimer(TIMER_2nd_hard_timer, 1000);
			}

			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_2nd);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_2nd_spelling);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(FLAG_SPAWN, 0, 0);
				maker0.onScriptEvent(1001, 0, 0);
				maker0.onScriptEvent(23140002, 0, 0);
			}
		}
		else if(timerId == TIMER_2nd_phase_success)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 2, 0, 0, 0, 0, 1, 7000, 0, 1801088);
			_thisActor.i_ai0 = 23140003;
			_thisActor.i_ai1 = FLAG_SPAWN;
			addTimer(TIMER_knight, 1000);
			addTimer(TIMER_castle, 1000);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_3rd);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(FLAG_SPAWN, 0, 0);
				maker0.onScriptEvent(1001, 0, 0);
				maker0.onScriptEvent(23140003, 0, 0);
			}
		}
		else if(timerId == TIMER_3rd_phase_success)
		{
			Functions.broadcastOnScreenMsgFStr(_thisActor, 4000, 2, 0, 0, 0, 0, 1, 7000, 0, 1801089);
			_thisActor.i_ai0 = 23140005;
			_thisActor.i_ai1 = FLAG_SPAWN;
			addTimer(TIMER_knight, 1000);
			addTimer(TIMER_castle, 1000);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_4th);
			if(maker0 != null)
			{
				if(is_hard_mode == 0)
				{
					maker0.onScriptEvent(1001, 0, 0);
				}
			}
			maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140005, 0, 0);
			}
		}
		else if(timerId == TIMER_4th_phase_success)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_ending);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
		}
		else if(timerId == TIMER_knight)
		{
			if(_thisActor.i_ai1 == FLAG_SPAWN)
			{
				int i1 = 0, i2 = 0;
				if(is_hard_mode == 1)
				{
					switch(_thisActor.i_ai0)
					{
						case 23140001:
							i1 = 30;
							i2 = 2;
							break;
						case 23140002:
							i1 = 25;
							i2 = 4;
							break;
						case 23140003:
							i1 = 20;
							i2 = 4;
							break;
						case 23140005:
							i1 = 30;
							i2 = 2;
							break;
					}
				}
				else
				{
					switch(_thisActor.i_ai0)
					{
						case 23140001:
							i1 = 30;
							i2 = 1;
							break;
						case 23140002:
							i1 = 30;
							i2 = 2;
							break;
						case 23140003:
							i1 = 45;
							i2 = 2;
							break;
						case 23140005:
							i1 = 30;
							i2 = 2;
							break;
					}
				}

				if(i1 > 0)
				{
					DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
					if(maker0 != null)
					{
						if(_thisActor.i_ai5 == 0)
						{
							maker0.onScriptEvent(23140015, i2, 0);
						}
					}

					addTimer(TIMER_knight, i1 * 1000);
				}
			}
		}
		else if(timerId == TIMER_castle)
		{
			if(_thisActor.i_ai1 == FLAG_SPAWN)
			{
				int i1 = 0;
				if(is_hard_mode == 1)
				{
					switch(_thisActor.i_ai0)
					{
						case 23140001:
							i1 = 25;
							break;
						case 23140002:
							i1 = 20;
							break;
						case 23140003:
							i1 = 20;
							break;
						case 23140005:
							i1 = 20;
							break;
					}
				}
				else
				{
					switch(_thisActor.i_ai0)
					{
						case 23140001:
							i1 = 45;
							break;
						case 23140002:
							i1 = 30;
							break;
						case 23140003:
							i1 = 30;
							break;
						case 23140005:
							i1 = 0;
							break;
					}
				}
				if(i1 > 0)
				{
					DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_castle);
					if(maker0 != null)
					{
						maker0.onScriptEvent(23140015, 0, 0);
						if(is_hard_mode == 1)
						{
							if(Rnd.get(2) == 1)
							{
								maker0.onScriptEvent(23140015, 0, 0);
							}
						}
					}
					addTimer(TIMER_castle, (i1 * 1000));
				}
			}
		}
		else if(timerId == TIMER_SCENE_15)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_15");
			}

			Functions.startScenePlayerAround(_thisActor, scene_num_15, 4000, 1000);
			addTimer(TIMER_SCENE_15_END, scene_sec_15);
		}
		else if(timerId == TIMER_SCENE_15_END)
		{
			_thisActor.i_ai1 = FLAG_SPAWN;
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_1st);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
			}
			maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1001, 0, 0);
				maker0.onScriptEvent(23140001, 0, 0);
			}
			addTimer(TIMER_start, 5 * 60000);
		}
		else if(timerId == TIMER_SCENE_16)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_16");
			}
			Functions.startScenePlayerAround(_thisActor, scene_num_16, 4000, 1000);
			addTimer(TIMER_SCENE_16_END, scene_sec_16);
		}
		else if(timerId == TIMER_SCENE_16_END)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140050, 0);
			}
			addTimer(TIMER_1st_phase_success, phase_to_phase);
		}
		else if(timerId == TIMER_SCENE_17)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_17");
			}
			Functions.startScenePlayerAround(_thisActor, scene_num_17, 4000, 1000);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_freya_2nd_spelling);
			if(maker0 != null)
			{
				maker0.onScriptEvent(1000, 0, 0);
			}
			addTimer(TIMER_ZONEINFO_CHG, 15000);
			addTimer(TIMER_SCENE_17_END, scene_sec_17);
		}
		else if(timerId == TIMER_SCENE_17_END)
		{
			addTimer(TIMER_2nd_phase_success, 1000);
		}
		else if(timerId == TIMER_SCENE_18)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_18");
			}
			_thisActor.getInstanceZone().openCloseDoor(IceQueen_DoorName, 0);
			Functions.startScenePlayerAround(_thisActor, scene_num_18, 4000, 1000);
			addTimer(TIMER_SCENE_18_END, scene_sec_18);
		}
		else if(timerId == TIMER_SCENE_18_END)
		{
			_thisActor.getInstanceZone().openCloseDoor(IceQueen_DoorName, 1);
			broadcastScriptEvent(23140049, 0, null, 4000);
			addTimer(TIMER_3rd_phase_success, 1000);
		}
		else if(timerId == TIMER_SCENE_19)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_19");
			}
			Functions.startScenePlayerAround(_thisActor, scene_num_19, 4000, 1000);
			addTimer(TIMER_ZONEINFO_CHG, 500);
			_thisActor.getInstanceZone().openCloseDoor(IceQueen_DoorName, 0);
			addTimer(TIMER_SCENE_19_END, scene_sec_19);
		}
		else if(timerId == TIMER_SCENE_19_END)
		{
			addTimer(TIMER_4th_phase_success, 1000);
		}
		else if(timerId == TIMER_SCENE_22)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_22");
			}
			Functions.startScenePlayerAround(_thisActor, scene_num_22, 4000, 1000);
			broadcastScriptEvent(23140048, 0, null, 4000);
			broadcastScriptEvent(23140020, 0, null, 4000);
			addTimer(TIMER_SCENE_22_END, scene_sec_22);
		}
		else if(timerId == TIMER_SCENE_22_END)
		{
			broadcastScriptEvent(23140049, 0, null, 4000);
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140059, 0);
			}
			addTimer(TIMER_reset_routine, 2000);
		}
		else if(timerId == TIMER_SCENE_23)
		{
			if(debug_mode > 0)
			{
				Functions.npcSay(_thisActor, Say2C.ALL, "TIMER_SCENE_23");
			}
			Functions.startScenePlayerAround(_thisActor, scene_num_23, 4000, 1000);
			broadcastScriptEvent(23140048, 0, null, 4000);
			addTimer(TIMER_SCENE_23_END, scene_sec_23);
		}
		else if(timerId == TIMER_SCENE_23_END)
		{
			broadcastScriptEvent(23140049, 0, null, 4000);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140054, 0, 0);
			}
		}
		else if(timerId == TIMER_ZONEINFO_CHG)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140021, 0);
			}
		}
		else if(timerId == TIMER_hold_desire)
		{
			broadcastScriptEvent(23140048, 0, null, 4000);
			addTimer(TIMER_SCENE_18, 1000);
		}
		else if(timerId == TIMER_start)
		{
			_thisActor.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140014, 0);
			broadcastScriptEvent(23140010, 1L, null, 4000);
		}
		else if(timerId == TIMER_2nd_hard_timer)
		{
			if(_thisActor.i_ai0 == 23140002 && _thisActor.i_ai1 == FLAG_NO_SPAWN)
			{
				return;
			}
			else if(_thisActor.i_ai6 >= 1 && _thisActor.i_ai7 == 0)
			{
				_thisActor.i_ai6 = (_thisActor.i_ai6 - 1);
				_thisActor.i_ai7 = 59;
			}
			else
			{
				_thisActor.i_ai7 = (_thisActor.i_ai7 - 1);
			}
			if(_thisActor.i_ai6 == 0 && _thisActor.i_ai7 == 1)
			{
				addTimer(TIMER_force_defeat, 1000);
			}
			else if(_thisActor.i_ai6 == 0 && _thisActor.i_ai7 < 10)
			{
				addTimer(TIMER_2nd_hard_timer, 1000);
			}
			else
			{
				L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
				if(c0 != null)
				{
					if(_thisActor.i_ai5 == 1)
					{
						_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 23140061, 0, 0);
						addTimer(TIMER_2nd_hard_timer, 1000);
					}
					else
					{
						_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 23140061, _thisActor.i_ai6, _thisActor.i_ai7);
						addTimer(TIMER_2nd_hard_timer, 1000);
					}
				}
			}
		}
		else if(timerId == TIMER_force_defeat)
		{
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(FLAG_NO_SPAWN, 0, 0);
			}
			addTimer(TIMER_SCENE_22, 1000);
		}
		else if(timerId == TIMER_reset_routine)
		{
			broadcastScriptEvent(23140020, 0, null, 4000);
			DefaultMaker maker0 = _thisActor.getInstanceZone().getMaker(MAKER_ice_knight);
			if(maker0 != null)
			{
				maker0.onScriptEvent(23140063, 0, 0);
			}
			L2Character c0 = L2ObjectsStorage.getAsCharacter(_thisActor.l_ai3);
			if(c0 != null)
			{
				c0.getAI().notifyEvent(CtrlEvent.EVT_SCRIPT_EVENT, 23140063, 0);
			}
			_thisActor.onDecay();
			Instance inst = _thisActor.getInstanceZone();
			if(inst != null)
				inst.stopInstance();
		}
	}
}
