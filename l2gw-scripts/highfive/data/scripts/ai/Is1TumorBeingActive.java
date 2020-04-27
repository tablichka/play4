package ai;

import ai.base.DefaultNpc;
import ru.l2gw.commons.math.Rnd;
import ru.l2gw.extensions.scripts.Functions;
import ru.l2gw.gameserver.ai.CtrlEvent;
import ru.l2gw.gameserver.instancemanager.InstanceManager;
import ru.l2gw.gameserver.model.L2Character;
import ru.l2gw.gameserver.model.L2ObjectsStorage;
import ru.l2gw.gameserver.model.npcmaker.DefaultMaker;

/**
 * @author: rage
 * @date: 14.12.11 20:09
 */
public class Is1TumorBeingActive extends DefaultNpc
{
	public int type = 0;
	public int start_hp_rate = 80;
	public int regen_value = 20;
	public int Vein_Convergency = 9;
	public int Vein_Size = 50;
	public int FieldCycle = 3;
	public int FieldCycle_Quantity = -500;
	public String active_tumor_maker = "";
	public String my_maker = "";

	public Is1TumorBeingActive(L2Character actor)
	{
		super(actor);
	}

	@Override
	protected void onEvtSpawn()
	{
		_thisActor.i_ai2 = 0;
		_thisActor.i_ai0 = 0;
		broadcastScriptEvent(989806, 0, null, 6000);
		_thisActor.setCurrentHp(_thisActor.getMaxHp() / 100 * start_hp_rate);
		DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), my_maker);
		if(maker0 != null)
		{
			for(int i1 = 0; i1 < 3; i1++)
			{
				int i0 = maker0.npc_count;
				if(i0 < maker0.maximum_npc)
				{
					switch(Rnd.get(3))
					{
						case 0:
							_thisActor.createOnePrivate(18706, "Is1Vein", 0, 0, (_thisActor.getX() + Rnd.get(Vein_Convergency + 1) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, (_thisActor.getY() + Rnd.get(Vein_Convergency + 1) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
							break;
						case 1:
							_thisActor.createOnePrivate(18706, "Is1Vein", 0, 0, (_thisActor.getX() + Rnd.get(Vein_Convergency + 2) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, (_thisActor.getY() + Rnd.get(Vein_Convergency + 2) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
							break;
						case 2:
							_thisActor.createOnePrivate(18706, "Is1Vein", 0, 0, (_thisActor.getX() + Rnd.get(Vein_Convergency + 3) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, (_thisActor.getY() + Rnd.get(Vein_Convergency + 3) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
							break;
					}
				}
			}
		}
		_thisActor.i_ai1 = 3;
		addTimer(1002, (10 + Rnd.get(5)) * 1000);
		addTimer(1003, 1000);
	}

	@Override
	protected void onEvtTimer(int timerId, Object arg1, Object arg2)
	{
		if(timerId == 1002)
		{
			DefaultMaker maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), my_maker);
			int i0 = maker0.npc_count;
			if(i0 < maker0.maximum_npc)
			{
				switch(Rnd.get(3))
				{
					case 0:
						_thisActor.createOnePrivate(18706, "Is1Vein", 0, 0, (_thisActor.getX() + Rnd.get(Vein_Convergency + 1) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, (_thisActor.getY() + Rnd.get(Vein_Convergency + 1) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
						break;
					case 1:
						_thisActor.createOnePrivate(18706, "Is1Vein", 0, 0, (_thisActor.getX() + Rnd.get(Vein_Convergency + 2) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, (_thisActor.getY() + Rnd.get(Vein_Convergency + 2) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
						break;
					case 2:
						_thisActor.createOnePrivate(18706, "Is1Vein", 0, 0, (_thisActor.getX() + Rnd.get(Vein_Convergency + 3) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, (_thisActor.getY() + Rnd.get(Vein_Convergency + 3) * Vein_Size) - Rnd.get(Vein_Convergency) * Vein_Size, _thisActor.getZ(), Rnd.get(61440), 0, 0, 0);
						break;
				}

				if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 > 98)
				{
					maker0 = InstanceManager.getInstance().getNpcMaker(_thisActor.getReflection(), active_tumor_maker);
					if(maker0 != null)
					{
						maker0.onScriptEvent(1001, 0, 0);
						broadcastScriptEvent(989812, 0, null, 3000);
						_thisActor.onDecay();
					}
				}
			}
			addTimer(1002, (10 + Rnd.get(5)) * 1000);
			_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg);
		}
		else if(timerId == 1003)
		{
			if(_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100 != _thisActor.i_ai2)
			{
				_thisActor.i_ai2 = (int) (_thisActor.getCurrentHp() / _thisActor.getMaxHp() * 100);
				if(_thisActor.i_ai2 == 85)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 6000, 2, 0, 0, 0, 0, 1, 10000, 0, 1800264);
				}
				else if(_thisActor.i_ai2 == 95)
				{
					Functions.broadcastOnScreenMsgFStr(_thisActor, 6000, 2, 0, 0, 0, 0, 1, 10000, 0, 1800265);
				}
			}
			addTimer(1003, 1000);
		}
	}

	@Override
	protected void onEvtScriptEvent(int eventId, Object arg1, Object arg2)
	{
		if(eventId == 98914)
		{
			L2Character c0 = L2ObjectsStorage.getAsCharacter((Long) arg2);
			_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg + regen_value);
			if(c0 != null)
			{
				_thisActor.notifyAiEvent(c0, CtrlEvent.EVT_SCRIPT_EVENT, 998915, 0, null);
			}
		}
		else if(eventId == 9898903)
		{
			_thisActor.i_ai1--;
			_thisActor.setHpRegen(_thisActor.getTemplate().baseHpReg);
		}
		else if(eventId == 998916)
		{
			broadcastScriptEvent(998916, 0, null, 6000);
			_thisActor.onDecay();
		}
		else if(eventId == 998917)
		{
			_thisActor.onDecay();
		}
	}
}